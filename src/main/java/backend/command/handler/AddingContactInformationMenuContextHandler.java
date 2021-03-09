package backend.command.handler;

import backend.command.ChatSession;
import backend.command.MessageCommand;
import backend.command.TagSelectionMenuStrategy;
import backend.service.response.DefaultConfigurationReplyKeyboardMarkupFactory;
import backend.service.response.KeyboardMarkupContainer;
import backend.service.SubscriberService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;
import static backend.service.request.UserMessageValidator.isContainsPhoneNumber;
import static backend.service.request.UserMessageValidator.isPhoneNumberValid;

public class AddingContactInformationMenuContextHandler extends SessionMenuStrategyHandler {
    public AddingContactInformationMenuContextHandler(ChatSession session) {
        super(session);
    }

    @Override
    public void handle(Update update) {
        LOGGER.info(String.format("Specific message handler is invoked by %1$s with parameter %2$s. Transition: info adding -> tag selection", getUserInfo(update), update.getMessage().getText()));

        Message message = update.getMessage();
        ReplyKeyboardMarkup keyboardMarkup = KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP;
        String text = "Выберите тег из клавиатуры или введите новый";

        if (isContainsPhoneNumber(message) && isPhoneNumberValid(message.getText())) {
            List<String> tags = SubscriberService.getAllTags();
            keyboardMarkup = KeyboardMarkupContainer.insertInKeyboardMarkupFromTop(
                    DefaultConfigurationReplyKeyboardMarkupFactory
                            .getInstance()
                            .newKeyboardInstance()
                            .setKeyboard(new ArrayList<>(
                                    KeyboardMarkupContainer.GET_OR_ADD_MENU_REPLY_KEYBOARD_MARKUP.getKeyboard())),
                    tags);

            session.setPhoneNumber(message.getText());
        }
        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(message, text, keyboardMarkup));
        session.setMenuStrategy(new TagSelectionMenuStrategy(session));
    }
}
