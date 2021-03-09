package backend.service;

import backend.db.JdbcConnection;
import backend.db.dao.SubscriberNumberToTagLinksDao;
import backend.db.dao.SubscriberPhoneNumbersDao;
import backend.db.dao.SubscriberTagsDao;
import backend.dto.SubscriberPhoneNumberDto;
import backend.dto.SubscriberNumberToTagLinkDto;
import backend.dto.SubscriberTagDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubscriberService {

    private static SubscriberPhoneNumbersDao     numbersDao;
    private static SubscriberTagsDao             tagsDao;
    private static SubscriberNumberToTagLinksDao linksDao;


    public static void setConnection(JdbcConnection connection) {
        numbersDao = new SubscriberPhoneNumbersDao(connection);
        tagsDao    = new SubscriberTagsDao(connection);
        linksDao   = new SubscriberNumberToTagLinksDao(connection);
    }

    /**
     * Returns a boolean result of subscriber new tag insertion.
     * If that tag was already added to this subscriber
     * then returns false, otherwise - true.
     *
     * @param number
     *        a subscriber phone number represented as a string
     * @param tag
     *        a subscriber tag represented as a string
     * @return true if subscriber tag is added, false when that tag was already added to subscriber
     */
    public static boolean addSubscriberTag(String number, String tag) {
        List<SubscriberNumberToTagLinkDto> linkDtoList = new ArrayList<>();
        Optional<SubscriberPhoneNumberDto> numberDto   = Optional.empty();
        Optional<SubscriberTagDto>         tagDto      = Optional.empty();
        Optional<Integer>                  numberId    = Optional.empty();
        Optional<Integer>                  tagId       = Optional.empty();


        for (SubscriberNumberToTagLinkDto link : linksDao.getAll()) {
            numberDto = numbersDao.get(link.getPhoneNumberId());
            tagDto    = tagsDao.get(link.getTagId());

            if (numberDto.isPresent() && numberDto.get().getPhoneNumber().equals(number)) {
                numberId = Optional.of(link.getPhoneNumberId());
            }
            if (tagDto.isPresent() && tagDto.get().getTag().equals(tag)) {
                tagId = Optional.of(link.getTagId());
            }
            if (
                    numberDto.isPresent() && tagDto.isPresent() &&
                    numberDto.get().getPhoneNumber().equals(number) &&
                    tagDto.get().getTag().equals(tag)
            ) {
                linkDtoList.add(link);
                break;
            }
        }

        // a case when we founded a link that already exists
        if (!linkDtoList.isEmpty()) {
            return false;
        }

        // if a specific number wasn't found we need to insert it
        if (numberDto.isEmpty() || numberId.isEmpty()) {
            numberId = numbersDao.insert(new SubscriberPhoneNumberDto(number));
        }

        // if a specific tag wasn't found we need to insert it
        if (tagDto.isEmpty() || numberId.isEmpty()) {
            tagId = tagsDao.insert(new SubscriberTagDto(tag));
        }

        // finally an insertion of a number-to-tag link
        linksDao.insert(new SubscriberNumberToTagLinkDto(numberId.get(), tagId.get()));
        return true;
    }

    /**
     * Returns a list of tags that describes a subscriber
     * by his phone number.
     *
     * @param phoneNumber
     *        a subscriber phone number as a string
     * @return a list of string that describes tags of that subscriber
     */
    public static List<String> getPhoneNumberTags(String phoneNumber) {
        return linksDao.getAll()
                .stream()
                .filter(link -> numbersDao.get(link.getPhoneNumberId())
                        .orElseThrow()
                        .getPhoneNumber()
                        .equals(phoneNumber))
                .map(link -> tagsDao.get(link.getTagId())
                        .orElseThrow()
                        .getTag())
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of all tags that storing in database.
     *
     * @return a list of all string that describes tags in database
     */
    public static List<String> getAllTags() {
        return tagsDao.getAll()
                .stream()
                .map(SubscriberTagDto::getTag)
                .collect(Collectors.toList());
    }

}
