package backend.command;

import backend.service.KeyboardMarkupContainer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpMessageCommand extends MessageCommand {
    @Override
    public void execute(Update update) {
        String text = "This is Help message!";
        sendMessageToChat(update.getMessage(), text, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP);
    }
}
