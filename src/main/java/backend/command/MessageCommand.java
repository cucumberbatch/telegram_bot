package backend.command;

import backend.bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MessageCommand implements Command {

    public static final String PHONE_NUMBER_FORMAT = "%1$s(%2$s)%3$s-%4$s-%5$s";

    public static Message sendMessageToChat(Message message, String text, ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage.setReplyMarkup(markup);
            Bot bot = Bot.getInstance();
            return bot.execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return message;
    }


}
