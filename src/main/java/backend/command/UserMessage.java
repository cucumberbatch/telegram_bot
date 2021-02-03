package backend.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import static frontend.io.KeyboardButtonHelper.*;

public class UserMessage {
    public static Command GET_ALL   = new GetAllMessageCommand();
    public static Command INSERT    = new InsertMessageCommand();
    public static Command WELCOME   = new WelcomeMessageCommand();
    public static Command HELP      = new HelpMessageCommand();
    public static Command EXIT      = new ExitMessageCommand();
    public static Command GET       = new GetMessageCommand();

    public static void answerOn(Update update) {
        Message message = update.getMessage();
        String entityText = message.getEntities().get(0).getText();

        if (GET_COMMAND.equals(entityText)) {
            GET.execute(update);
        }

        if (WELCOME_COMMAND.equals(entityText)) {
            WELCOME.execute(update);
            return;
        }
        if (HELP_COMMAND.equals(entityText)) {
            HELP.execute(update);
            return;
        }
        if (EXIT_COMMAND.equals(entityText)) {
            EXIT.execute(update);
            return;
        }
    }
}
