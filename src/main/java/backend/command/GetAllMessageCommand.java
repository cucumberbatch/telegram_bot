package backend.command;

import backend.dao.SubscriberDao;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GetAllMessageCommand extends MessageCommand {
    @Override
    public void execute(Update update) {
        String text = "List of phone numbers..";
        SubscriberDao dao = new SubscriberDao();
//        sendMessageToChat(update.getMessage(), dao.getAll().toString());
    }
}
