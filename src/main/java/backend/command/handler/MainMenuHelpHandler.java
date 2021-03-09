package backend.command.handler;

import backend.command.ChatSession;
import backend.command.MessageCommand;
import backend.service.response.KeyboardMarkupContainer;
import backend.service.response.KeyboardButtonHelper;
import org.telegram.telegrambots.meta.api.objects.Update;

import static backend.Constants.LOGGER;
import static backend.command.ChatSession.getUserInfo;

public class MainMenuHelpHandler extends SessionMenuStrategyHandler {
    public MainMenuHelpHandler(ChatSession session) {
        super(session);
    }

    @Override
    public void handle(Update update) {
        LOGGER.info(String.format("Help message command is invoked by %1$s", getUserInfo(update)));
        String text =
                "Этот бот может получить для вас информацию о номере телефона, который, " +
                        "вероятно, был добавлен другими пользователями в базу данных." +
                        "\nИнформация об абоненте хранится в списках «тегов», которые вы можете дополнять." +
                        "\nЧтобы получить информацию о номере телефона, необходимо ввести его в формате +7**********: " +
                        "\nЧтобы добавить информацию о номере телефона, необходимо отправить сообщение: " + KeyboardButtonHelper.ADD_COMMAND;

        session.setPreviousBotMessage(MessageCommand.sendMessageToChat(update.getMessage(), text, KeyboardMarkupContainer.MAIN_MENU_REPLY_KEYBOARD_MARKUP));
    }
}
