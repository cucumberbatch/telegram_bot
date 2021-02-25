package backend.dto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class AbstractDto<T> implements Dto<T> {
    private final ReplyKeyboardMarkup keyboardMarkup;
    private final T replyMessage;

    AbstractDto(T replyMessage, ReplyKeyboardMarkup markup) {
        this.replyMessage = replyMessage;
        this.keyboardMarkup = markup;
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return keyboardMarkup;
    }

    @Override
    public T getReplyMessage() {
        return replyMessage;
    }
}
