package backend.command;

import backend.bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static backend.command.GetMessageCommand.PHONE_NUMBER_FORMAT;

public abstract class MessageCommand implements Command {

    public static final String PHONE_NUMBER_FORMAT = "%1$s(%2$s)%3$s-%4$s-%5$s";

    public void sendMessageToChat(Message message, String text, ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage.setReplyMarkup(markup);
            Bot bot = Bot.getInstance();
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String formatPhoneNumber(String phoneNumber) {
        String formattedNumber = String.format(
                PHONE_NUMBER_FORMAT,
                phoneNumber.substring(0, 2),
                phoneNumber.substring(2, 5),
                phoneNumber.substring(5, 8),
                phoneNumber.substring(8, 10),
                phoneNumber.substring(10, 12));
        return formattedNumber;
    }

    public String formatTag(String text) {
        String regex = "([\\p{L}|\\d]+)\\s*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        return matcher.group(1);
    }

}
