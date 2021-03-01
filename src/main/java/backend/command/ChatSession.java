package backend.command;

import backend.dao.SubscriberDao;
import backend.service.Validator;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static frontend.io.KeyboardButtonHelper.*;

public class ChatSession {

    private IMenuStrategy strategy = new MainMenuStrategy(this);
    private SubscriberDao dao      = new SubscriberDao();
    private String lastEnteredPhoneNumber;
    private Message previousBotMessage;


    public void handle(Update update) {
        Message message    = update.getMessage();
        String messageText = message.getText();

        if (Validator.isCommand(message) && !Validator.isContainsPhoneNumber(message)) {

            if (ADD_COMMAND.equals(messageText)) {
                strategy.add(update);
                return;
            }
            if (HELP_COMMAND.equals(messageText)) {
                strategy.help(update);
                return;
            }
            if (CANCEL_COMMAND.equals(messageText)) {
                strategy.cancel(update);
                return;
            }
        }

        strategy.handle(update);

    }

    public void setMenuStrategy(IMenuStrategy strategy) {
        this.strategy = strategy;
    }

    public IMenuStrategy getMenuStrategy() {
        return this.strategy;
    }

    public static String getUserInfo(Update update) {
        return update.getMessage().getChat().getUserName();
    }

    public SubscriberDao getDao() {
        return this.dao;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.lastEnteredPhoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.lastEnteredPhoneNumber;
    }

    public void setPreviousBotMessage(Message message) {
        this.previousBotMessage = message;
    }

    public Message getPreviousBotMessage() {
        return this.previousBotMessage;
    }
}
