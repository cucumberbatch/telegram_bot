package backend.command;

import backend.service.KeyboardMarkupContainer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class WelcomeMessageCommand extends MessageCommand {
    @Override
    public void execute(Update update) {
        String text = "It's welcoming message!";
        sendMessageToChat(update.getMessage(), text, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP);
    }
}
