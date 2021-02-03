package backend.dto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface Dto {
    ReplyKeyboardMarkup getButtonCaptionList();
    String getReplyMessage();
}
