package backend.bot;

import backend.command.UserMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static backend.Constants.BOT_TOKEN;
import static backend.Constants.BOT_USERNAME;

public class Bot extends TelegramLongPollingBot {
    private static Bot instance;

    public Bot() {
    }

    public static Bot getInstance() {
        return instance == null ? new Bot() : instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message == null || !message.hasText()) {
            return;
        }
        UserMessage.answerOn(update);
    }


    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}
