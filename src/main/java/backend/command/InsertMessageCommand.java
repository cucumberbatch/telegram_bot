package backend.command;

import backend.Constants;
import backend.dto.SubscriberDto;
import backend.model.Subscriber;
import backend.service.SubscriberService;
import backend.service.Validator;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;
import java.util.logging.Level;

import static backend.Constants.LOGGER;
import static backend.service.KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP;

public class InsertMessageCommand extends MessageCommand {
    @Override
    public void execute(Update update) {
        LOGGER.info("Execution of insert command message...");
        if (Validator.isContainsPhoneNumber(update.getMessage())) {
            LOGGER.log(Level.INFO, "Params are valid!");
            List<MessageEntity> entities    = update.getMessage().getEntities();
            LOGGER.log(Level.INFO, "Entities: {0}", entities);
            String formattedNumber          = formatPhoneNumber(entities.get(1).getText());
            LOGGER.log(Level.INFO, "Formatted number: {0}", formattedNumber);
            String formattedTag             = formatTag(update.getMessage().getText());
            LOGGER.log(Level.INFO, "Formatted tag: {0}", formattedTag);
            SubscriberDto dto               = (SubscriberDto) SubscriberService.addTagByNumber(formattedNumber, formattedTag);
            Subscriber subscriber           = dto.getReplyMessage();
            ReplyKeyboardMarkup markup      = dto.getReplyKeyboardMarkup();
            LOGGER.log(Level.INFO, "Inserted new tag [" + subscriber.getTags().get(0) + "] for '" + subscriber.getPhoneNumber() + "'");
            sendMessageToChat(
                    update.getMessage(),
                    subscriber.getTags().isEmpty() ? Constants.SUBSCRIBER_DOES_NOT_TAGGED_TEXT : subscriber.getTags().toString(),
                    markup);
            return;
        }
        sendMessageToChat(update.getMessage(), Constants.INSERT_MESSAGE_COMMAND_TEXT, MAIN_MENU_REPLY_KEYBOARD_MARKUP);
    }
}
