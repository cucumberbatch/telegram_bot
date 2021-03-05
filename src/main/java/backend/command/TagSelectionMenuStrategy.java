package backend.command;

import backend.service.SubscriberService;
import backend.service.KeyboardMarkupContainer;
import backend.service.UserMessageFormatter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;

public class TagSelectionMenuStrategy implements IMenuStrategy {

    private ChatSession session;


    public TagSelectionMenuStrategy(ChatSession session) {
        this.session = session;
    }

    @Override
    public void add(Update update) { /* no implementation */ }

    @Override
    public void help(Update update) {

    }

    // TODO: take out the logic into separate objects in each method
    @Override
    public void cancel(Update update) {
        LOGGER.info(String.format("Cancel message command is invoked by %1$s.\tTransition: tag selection -> main menu", getUserInfo(update)));

        String text = "Вы в главном меню";

        // returning to the main menu state
        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(update.getMessage(), text, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP));
        session.setMenuStrategy(new MainMenuStrategy(session));
    }

    // TODO: take out the logic into separate objects in each method
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
