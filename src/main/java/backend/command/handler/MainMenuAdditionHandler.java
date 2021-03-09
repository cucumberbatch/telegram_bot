package backend.command.handler;

import backend.command.AddingContactInformationMenuStrategy;
import backend.command.ChatSession;
import backend.command.MessageCommand;
import backend.service.response.DefaultConfigurationReplyKeyboardMarkupFactory;
import backend.service.response.KeyboardMarkupContainer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.Arrays;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;

public class MainMenuAdditionHandler extends SessionMenuStrategyHandler {
    public MainMenuAdditionHandler(ChatSession session) {
        super(session);
    }

    @Override
    public void handle(Update update) {
        LOGGER.info(String.format("Add message command is invoked by %1$s.\tTransition: main menu -> info adding", getUserInfo(update)));

        String text = "Введите номер контакта";
        ReplyKeyboardMarkup keyboardMarkup = DefaultConfigurationReplyKeyboardMarkupFactory
                .getInstance()
                .newKeyboardInstance()
                .setKeyboard(new ArrayList<>(
                        KeyboardMarkupContainer.GET_OR_ADD_MENU_REPLY_KEYBOARD_MARKUP.getKeyboard()));

        // add hot key button with last entered phone number
        if (session.getPhoneNumber() != null) {
            KeyboardMarkupContainer.insertInKeyboardMarkupFromTop(keyboardMarkup, Arrays.asList(session.getPhoneNumber()));
        }

        // switch to the add contact info menu
        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(update.getMessage(), text, keyboardMarkup));
        session.setMenuStrategy(new AddingContactInformationMenuStrategy(session));
    }
}
