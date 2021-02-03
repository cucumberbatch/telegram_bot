package backend.command;

import backend.dao.SubscriberDao;
import backend.model.Subscriber;
import org.telegram.telegrambots.meta.api.objects.Update;

public class InsertMessageCommand extends MessageCommand {
    @Override
    public void execute(Update update) {
        String text = "Phone number added!";
        SubscriberDao dao = new SubscriberDao();
//        dao.insert(new Subscriber());
//        sendMessageToChat(update.getMessage(), text);
    }
}
