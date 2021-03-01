package backend.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IMenuStrategy {
    void add(Update update);
    void help(Update update);
    void cancel(Update update);
    void handle(Update update);
}

