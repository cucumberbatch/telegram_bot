package backend.bot;

import backend.Constants;
import backend.command.ChatSession;
import backend.service.Pool;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static backend.Constants.*;

public class Bot extends TelegramLongPollingBot {
    private static Bot instance;
    private final Map<Long, ChatSession> sessionMap = new HashMap<>();
    private final Pool<ChatSession> sessionPool     = new Pool<>(ChatSession::new);

    public static Bot getInstance() {
        return instance == null ? new Bot() : instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();

        // checks all sessions. if they timers are up then
        // we need to reserve them
        // TODO: needs to check for correctness with multiple sessions
        for (Long id : sessionMap.keySet()) {
            if (isSessionTimedOut(sessionMap.get(id)) && !isTheSameChatId(update, id)) {
                LOGGER.info(String.format("Session [%1$s] is time out!", chatId));
                sessionPool.put(sessionMap.remove(id));
            }
        }

        // instantiate a new session for new chat id
        if (!sessionMap.containsKey(chatId)) {
            LOGGER.info(String.format("Created a new chat session [%1$s]", chatId));
            sessionMap.put(chatId, sessionPool.get());
        }

        LOGGER.info(String.format("Handling message [%1$s] for chat id [%2$s]", update.getMessage().getText(), chatId));
        sessionMap.get(chatId).handle(update);
    }

    private boolean isTheSameChatId(Update update, Long id) {
        return id.equals(update.getMessage().getChatId());
    }

    private boolean isSessionTimedOut(ChatSession session) {
        if (session.getPreviousBotMessage() == null) return false;
        return (Instant.now().getEpochSecond() - session.getPreviousBotMessage().getDate()) > Constants.CHAT_SESSION_TIMEOUT;
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
