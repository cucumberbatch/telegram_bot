package backend.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMessageValidator {

    public static boolean isContainsPhoneNumber(Message message) {
        return Objects.nonNull(message.getEntities()) && message.getEntities().get(0).getType().equals("phone_number");
    }

    // TODO: change validation logic and/or make error message for specific phone numbers
    public static boolean isPhoneNumberValid(String phoneNumber) {
        String regex = "\\+7\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (!matcher.find())                 return false;
        if (matcher.group(0).length() != 12) return false;
        return true;
    }

    public static boolean isCommand(Message message) {
        return message.isCommand();
    }
}
