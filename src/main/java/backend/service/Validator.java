package backend.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isContainsPhoneNumber(Message message) {
        List<MessageEntity> entities = message.getEntities();
        return entities.get(0).getType().equals("phone_number");
    }

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
