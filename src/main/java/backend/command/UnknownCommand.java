package backend.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Command {
    @Override
    public void execute(Update update) {

    }
}