package backend.dto;

import backend.model.Subscriber;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class SubscriberDto extends AbstractDto<Subscriber> {
    public SubscriberDto(Subscriber subscriber, ReplyKeyboardMarkup markup) {
        super(subscriber, markup);
    }
}
