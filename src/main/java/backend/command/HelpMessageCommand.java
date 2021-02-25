package backend.command;

import backend.service.KeyboardMarkupContainer;
import org.telegram.telegrambots.meta.api.objects.Update;

import static backend.Constants.LOGGER;

public class HelpMessageCommand extends MessageCommand {
    @Override
    public void execute(Update update) {
        LOGGER.info("Execution of help command message...");
        String text =
                "Этот бот может получить для вас информацию о номере телефона, который, вероятно, был добавлен другими пользователями в базу данных.\n" +
                "Информация об абоненте хранится в списках «тегов», которые вы можете дополнять.\n" +
                "Чтобы получить информацию о номере телефона, необходимо отправить сообщение: /get +7xxxxxxxxxx";
        sendMessageToChat(update.getMessage(), text, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP);
    }
}
