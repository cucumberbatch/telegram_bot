package backend.dto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class AbstractDto implements Dto {
    private final ReplyKeyboardMarkup keyboardMarkup;
    private final String replyMessage;

    AbstractDto(String replyMessage, ReplyKeyboardMarkup markup) {
        this.replyMessage = replyMessage;
        this.keyboardMarkup = markup;
    }

    @Override
    public ReplyKeyboardMarkup getButtonCaptionList() {
        return keyboardMarkup;
    }

    @Override
    public String getReplyMessage() {
        return replyMessage;
    }
}
