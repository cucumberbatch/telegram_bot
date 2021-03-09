package backend.command.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MenuStrategyHandler {
    void handle(Update update);
}
