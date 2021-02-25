package backend.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

public class Validator {
    public static boolean isContainsPhoneNumber(Message message) {
        List<MessageEntity> entities = message.getEntities();
        return entities.size() > 1 && entities.get(1).getType().equals("phone_number");
    }
}
