package backend.command.handler;

import backend.command.ChatSession;
import backend.command.MainMenuStrategy;
import backend.command.MessageCommand;
import backend.service.response.KeyboardMarkupContainer;
import backend.service.SubscriberService;
import backend.service.request.UserMessageFormatter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;

public class TagSelectionMenuContextHandler extends SessionMenuStrategyHandler {
    public TagSelectionMenuContextHandler(ChatSession session) {
        super(session);
    }

    @Override
    public void handle(Update update) {
        LOGGER.info(String.format("Specific message handler is invoked by %1$s with parameter %2$s.\tTransition: tag selection -> main menu", getUserInfo(update), update.getMessage().getText()));

        String tagInsertionSuccessText = "Тег успешно добавлен.";
        String tagInsertionFailureText = "Возникла ошибка при добавлении тега!";

        // handle the message with entered tag
        Message message             = update.getMessage();
        String formattedTag         = UserMessageFormatter.formatTag(message.getText());
        String formattedPhoneNumber = UserMessageFormatter.formatPhoneNumber(session.getPhoneNumber());
        SubscriberService.addSubscriberTag(formattedPhoneNumber, formattedTag);

        LOGGER.info(String.format("Inserted new tag [%1$s] for '%2$s'", formattedTag, formattedPhoneNumber));

//        String responseMessageText = isInsertionSuccess ? tagInsertionSuccessText : tagInsertionFailureText;
        String responseMessageText = tagInsertionSuccessText;

        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(message, responseMessageText, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP));
        session.setMenuStrategy(new MainMenuStrategy(session));
    }
}
