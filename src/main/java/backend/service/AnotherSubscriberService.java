package backend.service;

import backend.db.dao.SubscriberNumberToTagLinksDao;
import backend.db.dao.SubscriberNumbersDao;
import backend.db.dao.SubscriberTagsDao;
import backend.dto.SubscriberNumberDto;
import backend.dto.SubscriberNumberToTagLinkDto;
import backend.dto.SubscriberTagDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnotherSubscriberService {

    private static final SubscriberNumbersDao          numbersDao = new SubscriberNumbersDao();
    private static final SubscriberTagsDao             tagsDao    = new SubscriberTagsDao();
    private static final SubscriberNumberToTagLinksDao linksDao   = new SubscriberNumberToTagLinksDao();


    public static void addSubscriberTag(String number, String tag) {
        List<SubscriberNumberToTagLinkDto> linkDtoList;
        Optional<SubscriberNumberDto>      numberDto = Optional.empty();
        Optional<SubscriberTagDto>         tagDto    = Optional.empty();
        Optional<Integer>                  numberId  = Optional.empty();
        Optional<Integer>                  tagId     = Optional.empty();


        // we are trying to find the link that might be already exist in database
        linkDtoList = linksDao.getAll()
                .stream()
                .filter(link -> numbersDao.get(link.getSubscriberId())
                        .orElseThrow()
                        .getPhoneNumber()
                        .equals(number))
                .filter(link -> tagsDao.get(link.getTagId())
                        .orElseThrow()
                        .getTag()
                        .equals(tag))
                .collect(Collectors.toList());

        // a case when we founded a link that already exists
        if (!linkDtoList.isEmpty()) {
            return;
        }

        // searching for a phone record in database
        numberDto = numbersDao.getAll()
                .stream()
                .filter(record -> record.getPhoneNumber().equals(number))
                .findFirst();

        // searching for a tag record in database
        tagDto = tagsDao.getAll()
                .stream()
                .filter(record -> record.getTag().equals(tag))
                .findFirst();

        // if a specific number wasn't found we need to insert it
        if (numberDto.isEmpty()) {
            numberId = numbersDao.insert(new SubscriberNumberDto(number));
        }

        // if a specific tag wasn't found we need to insert it
        if (tagDto.isEmpty()) {
            tagId = tagsDao.insert(new SubscriberTagDto(tag));
        }

        // finally an insertion of a number-to-tag link
        linksDao.insert(new SubscriberNumberToTagLinkDto(numberId.get(), tagId.get()));
    }

    public static List<String> getPhoneNumberTags(String number) {
        return linksDao.getAll()
                .stream()
                .filter(link -> numbersDao.get(link.getSubscriberId())
                        .orElseThrow()
                        .getPhoneNumber()
                        .equals(number))
                .map(link -> tagsDao.get(link.getTagId())
                        .orElseThrow()
                        .getTag())
                .collect(Collectors.toList());
    }

    public static List<String> getAllTags() {
        return tagsDao.getAll()
                .stream()
                .map(SubscriberTagDto::getTag)
                .collect(Collectors.toList());
    }

}
