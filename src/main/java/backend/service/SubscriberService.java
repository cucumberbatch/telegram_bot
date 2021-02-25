package backend.service;

import backend.dao.SubscriberDao;
import backend.dto.AbstractDto;
import backend.dto.SubscriberDto;
import backend.model.Subscriber;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static backend.Constants.LOGGER;

public class SubscriberService {

    private static final SubscriberDao subscriberDao = new SubscriberDao();


    public static AbstractDto addTagByNumber(String number, String tag) {
        LOGGER.log(Level.INFO, "Adding subscriber tag [ {1} ] by phone number [ {0} ]", new String[]{number, tag});
        Subscriber subscriber = new Subscriber(number, Arrays.asList(tag));
        subscriberDao.insert(subscriber);
        return null;
    }

    public static AbstractDto getSubscriberByPhoneNumber(String number) {
        LOGGER.log(Level.INFO, "Getting subscriber by phone number {0}", number);
        return new SubscriberDto(subscriberDao.getByNumber(number).get(), KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP);
    }

    public static Set<String> getSetOfAllTags() {
        LOGGER.log(Level.INFO, "Getting list of tags");
        Set<String> tags = new HashSet<>();

        subscriberDao.getAll().stream().map(Subscriber::getTags).forEach(tags::addAll);
        return tags;
    }
}
