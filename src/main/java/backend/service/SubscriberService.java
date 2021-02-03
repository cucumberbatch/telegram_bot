package backend.service;

import backend.dao.SubscriberDao;
import backend.dto.AbstractDto;
import backend.dto.ExceptionDto;
import backend.dto.SubscriberDto;
import backend.model.Subscriber;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static backend.Constants.LOGGER;

public class SubscriberService {

    private static final SubscriberDao subscriberDao = new SubscriberDao();


    public static String addTagByNumber(String message) {
        return "";
    }

    public static AbstractDto findSubscriberByNumber(String message) {
        String incorrectNumberMessage = "The number you entered isn't correct!";
        Optional<Subscriber> subscriber = Optional.empty();
        long number;

        try {
            number = Long.parseLong(message);
            subscriber = subscriberDao.getByNumber(number);

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, incorrectNumberMessage, e);
        }

        return new SubscriberDto(subscriber.get(), KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP);
    }

    public static String getSubscribersList() {
        return subscriberDao.getAll().toString();
    }
}
