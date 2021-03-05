package backend.command;

import backend.service.SubscriberService;
import backend.service.DefaultConfigurationReplyKeyboardMarkupFactory;
import backend.service.KeyboardMarkupContainer;
import backend.service.UserMessageFormatter;
import frontend.io.KeyboardButtonHelper;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;
import static backend.service.UserMessageValidator.isContainsPhoneNumber;
import static backend.service.UserMessageValidator.isPhoneNumberValid;

public class MainMenuStrategy implements IMenuStrategy {

    private ChatSession session;


    public MainMenuStrategy(ChatSession session) {
        this.session = session;
    }

    // TODO: take out the logic into separate objects in each method
    @Override
    public void add(Update update) {
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

    // TODO: take out the logic into separate objects in each method
    @Override
    public void help(Update update) {
        LOGGER.info(String.format("Help message command is invoked by %1$s", getUserInfo(update)));
        String text =
                "Этот бот может получить для вас информацию о номере телефона, который, " +
                "вероятно, был добавлен другими пользователями в базу данных." +
                "\nИнформация об абоненте хранится в списках «тегов», которые вы можете дополнять." +
                "\nЧтобы получить информацию о номере телефона, необходимо ввести его в формате +7**********: " +
                "\nЧтобы добавить информацию о номере телефона, необходимо отправить сообщение: " + KeyboardButtonHelper.ADD_COMMAND;

        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(update.getMessage(), text, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP));
    }

    @Override
    public void cancel(Update update) { /* no implementation */ }

    // TODO: take out the logic into separate objects in each method
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
