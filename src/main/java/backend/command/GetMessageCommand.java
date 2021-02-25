package backend.command;

import backend.dto.Dto;
import backend.dto.SubscriberDto;
import backend.model.Subscriber;
import backend.service.SubscriberService;
import backend.service.Validator;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.logging.Level;

import static backend.Constants.LOGGER;
import static backend.service.KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP;

public class GetMessageCommand extends MessageCommand {

    @Override
    public void execute(Update update) {
        LOGGER.info("Execution of get command message...");
        Subscriber subscriber;
        if (Validator.isContainsPhoneNumber(update.getMessage())) {
            List<MessageEntity> entityList = update.getMessage().getEntities();
            String formattedNumber = formatPhoneNumber(entityList.get(1).getText());
            SubscriberDto dto = (SubscriberDto) SubscriberService.getSubscriberByPhoneNumber(formattedNumber);
            subscriber = dto.getReplyMessage();
            LOGGER.log(Level.INFO, "Founded tags: " + subscriber.getTags() + " for subscriber " + subscriber.getPhoneNumber());
            sendMessageToChat(
                    update.getMessage(),
                    subscriber.getTags().isEmpty() ? "Данный абонент не помечен тегами." : subscriber.getTags().toString(),
                    dto.getReplyKeyboardMarkup());
            return;
        }
        sendMessageToChat(
                update.getMessage(),
                "Чтобы получить информацию о номере телефона, необходимо отправить сообщение: /get +7xxxxxxxxxx",
                MAIN_MENU_REPLY_KEYBOARD_MARKUP);
    }

}
