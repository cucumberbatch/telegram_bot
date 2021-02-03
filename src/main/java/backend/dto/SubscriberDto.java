package backend.dto;

import backend.model.Subscriber;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public class SubscriberDto extends AbstractDto {
    public SubscriberDto(Subscriber subscriber, ReplyKeyboardMarkup markup) {
        super(subscriber.getPhoneNumber().toString(), markup);
    }
}
