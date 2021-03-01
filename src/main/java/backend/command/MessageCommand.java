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

    public static String formatPhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "\\+(\\d)\\((\\d*)\\)(\\d*)\\-(\\d*)\\-(\\d*)";
        Pattern pattern = Pattern.compile(phoneNumberRegex);
        Matcher matcher = pattern.matcher(phoneNumber);

//        if (!matcher.find()) { return null; }
//        if (matcher.group(0).length() <= 10 || matcher.group(0).length() >= 17) { return null; }



        return String.format(
                PHONE_NUMBER_FORMAT,
                phoneNumber.substring(0, 2),
                phoneNumber.substring(2, 5),
                phoneNumber.substring(5, 8),
                phoneNumber.substring(8, 10),
                phoneNumber.substring(10, 12));
    }

    public static String formatTag(String text) {
        String regex = "([\\p{L}|\\d]+)\\s*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        return matcher.group(1);
    }

}
