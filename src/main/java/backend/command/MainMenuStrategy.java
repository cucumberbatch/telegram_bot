package backend.command;

import backend.command.handler.MainMenuAdditionHandler;
import backend.command.handler.MainMenuContextHandler;
import backend.command.handler.MainMenuHelpHandler;
import backend.command.handler.MenuStrategyHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MainMenuStrategy implements IMenuStrategy {

    private final MenuStrategyHandler contextHandler;
    private final MenuStrategyHandler additionHandler;
    private final MenuStrategyHandler helpHandler;


    public MainMenuStrategy(ChatSession session) {
        contextHandler  = new MainMenuContextHandler(session);
        additionHandler = new MainMenuAdditionHandler(session);
        helpHandler     = new MainMenuHelpHandler(session);
    }

    @Override
    public void add(Update update) {
        additionHandler.handle(update);
    }

    @Override
    public void help(Update update) {
        helpHandler.handle(update);
    }

    @Override
    public void cancel(Update update) {
        /* no implementation */
    }

    @Override
    public void handle(Update update) {
        contextHandler.handle(update);
    }
}
