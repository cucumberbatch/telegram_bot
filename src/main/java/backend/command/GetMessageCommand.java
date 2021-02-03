package backend.command;

import backend.dto.SubscriberDto;
import backend.service.KeyboardMarkupContainer;
import backend.service.SubscriberService;
import backend.service.Validator;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class GetMessageCommand extends MessageCommand {
    @Override
    public void execute(Update update) {
        if (!Validator.isContainsPhoneNumber(update.getMessage())) {
            return;
        }
        SubscriberDto dto = (SubscriberDto) SubscriberService.findSubscriberByNumber(update.getMessage().getEntities().get(1).getText());
        sendMessageToChat(update.getMessage(), dto.getReplyMessage(), KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP);
    }
}
