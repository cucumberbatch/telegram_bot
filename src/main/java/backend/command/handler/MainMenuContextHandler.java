package backend.command.handler;

import backend.command.ChatSession;
import backend.command.MessageCommand;
import backend.service.response.KeyboardMarkupContainer;
import backend.service.SubscriberService;
import backend.service.request.UserMessageFormatter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;
import static backend.service.request.UserMessageValidator.isContainsPhoneNumber;
import static backend.service.request.UserMessageValidator.isPhoneNumberValid;

public class MainMenuContextHandler extends SessionMenuStrategyHandler {
    public MainMenuContextHandler(ChatSession session) {
        super(session);
    }

    @Override
    public void handle(Update update) {
        LOGGER.info(String.format("Specific message handler is invoked by %1$s with parameter %2$s", getUserInfo(update), update.getMessage().getText()));

        // handle the message with entered phone number
        Message message         = update.getMessage();
        String tagsNotFoundText = "К данному номеру не привязано никаких тегов";
        String foundedTags      = "По данному контакту были найдены следующие теги: ";
        List<String> tags       = null;

        if (isContainsPhoneNumber(message) && isPhoneNumberValid(message.getText())) {
            session.setPhoneNumber(message.getText());
            String formattedNumber = UserMessageFormatter.formatPhoneNumber(message.getText());
            tags = SubscriberService.getPhoneNumberTags(formattedNumber);
        }

        String responseMessageText = (tags == null || tags.isEmpty())
                ? tagsNotFoundText
                : foundedTags + tags.toString();

        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(message, responseMessageText, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP));
    }
}
