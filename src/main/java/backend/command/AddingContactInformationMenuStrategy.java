package backend.command;

import backend.service.DefaultConfigurationReplyKeyboardMarkupFactory;
import backend.service.KeyboardMarkupContainer;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.*;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;
import static backend.service.Validator.isContainsPhoneNumber;
import static backend.service.Validator.isPhoneNumberValid;

public class AddingContactInformationMenuStrategy implements IMenuStrategy {

    private ChatSession session;


    public AddingContactInformationMenuStrategy(ChatSession session) {
        this.session = session;
    }

    @Override
    public void add(Update update) { /* no implementation */ }

    @Override
    public void help(Update update) {
        LOGGER.info(String.format("Help message command is invoked by %1$s", getUserInfo(update)));

        Message message = update.getMessage();
        String text =
                "Для добавления тегов или ярлыков контакту " +
                "отправьте номер телефона этого контакта";

        ReplyKeyboardMarkup keyboardMarkup =
                DefaultConfigurationReplyKeyboardMarkupFactory
                        .getInstance()
                        .newKeyboardInstance()
                        .setKeyboard(new ArrayList<>(
                                KeyboardMarkupContainer.GET_OR_ADD_MENU_REPLY_KEYBOARD_MARKUP.getKeyboard()));

//        session.getPreviousBotMessage().

        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(message, text, KeyboardMarkupContainer.GET_OR_ADD_MENU_REPLY_KEYBOARD_MARKUP));
    }

    @Override
    public void cancel(Update update) {
        LOGGER.info(String.format("Cancel message command is invoked by %1$s.\tTransition: info adding -> main menu", getUserInfo(update)));

        Message message = update.getMessage();
        String text = "Вы в главном меню";

        // returning to the main menu state
        MessageCommand.sendMessageToChat(message, text, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP);
        session.setMenuStrategy(new MainMenuStrategy(session));
    }

    @Override
    public void handle(Update update) {
        LOGGER.info(String.format("Specific message handler is invoked by %1$s with parameter %2$s. Transition: info adding -> tag selection", getUserInfo(update), update.getMessage().getText()));

        Message message = update.getMessage();
        ReplyKeyboardMarkup keyboardMarkup = KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP;
        String  text    = "Выберите тег из клавиатуры или введите новый";

        if (isContainsPhoneNumber(message) && isPhoneNumberValid(message.getText())) {
            List<String> tags = session.getDao().getAllTags().get().getTags();
            keyboardMarkup = KeyboardMarkupContainer.insertInKeyboardMarkupFromTop(
                    DefaultConfigurationReplyKeyboardMarkupFactory
                            .getInstance()
                            .newKeyboardInstance()
                            .setKeyboard(new ArrayList<>(
                                    KeyboardMarkupContainer.GET_OR_ADD_MENU_REPLY_KEYBOARD_MARKUP.getKeyboard())),
                    tags);

            session.setPhoneNumber(message.getText());
        }
        MessageCommand.sendMessageToChat(message, text, keyboardMarkup);
        session.setMenuStrategy(new TagSelectionMenuStrategy(session));
    }

    public static String formatPhoneNumber(String phoneNumber) {
        return String.format(
                MessageCommand.PHONE_NUMBER_FORMAT,
                phoneNumber.substring(0, 2),
                phoneNumber.substring(2, 5),
                phoneNumber.substring(5, 8),
                phoneNumber.substring(8, 10),
                phoneNumber.substring(10, 12));
    }

}
