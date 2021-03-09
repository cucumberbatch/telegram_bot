package backend.command.handler;

import backend.command.ChatSession;
import backend.command.MainMenuStrategy;
import backend.command.MessageCommand;
import backend.service.response.KeyboardMarkupContainer;
import org.telegram.telegrambots.meta.api.objects.Update;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;

public class GeneralMenuCancelHandler extends SessionMenuStrategyHandler {
    public GeneralMenuCancelHandler(ChatSession session) {
        super(session);
    }

    @Override
    public void handle(Update update) {
        LOGGER.info(String.format("Cancel message command is invoked by %1$s.\tTransition: tag selection -> main menu", getUserInfo(update)));

        String text = "Вы в главном меню";

        // returning to the main menu state
        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(update.getMessage(), text, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP));
        session.setMenuStrategy(new MainMenuStrategy(session));
    }
}
