package backend.dto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class ExceptionDto extends AbstractDto {
    public ExceptionDto(String message, ReplyKeyboardMarkup markup) {
        super(message, markup);
    }
}
