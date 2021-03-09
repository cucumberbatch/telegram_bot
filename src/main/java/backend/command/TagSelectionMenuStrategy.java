package backend.command;

import backend.command.handler.GeneralMenuCancelHandler;
import backend.command.handler.MenuStrategyHandler;
import backend.command.handler.TagSelectionMenuContextHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TagSelectionMenuStrategy implements IMenuStrategy {

    private MenuStrategyHandler cancelHandler;
    private MenuStrategyHandler contextHandler;

    public TagSelectionMenuStrategy(ChatSession session) {
        cancelHandler  = new GeneralMenuCancelHandler(session);
        contextHandler = new TagSelectionMenuContextHandler(session);
    }

    @Override
    public void add(Update update) { /* no implementation */ }

    @Override
    public void help(Update update) { /* no implementation */ }

    @Override
    public void cancel(Update update) {
        cancelHandler.handle(update);
    }

    @Override
    public void handle(Update update) {
        contextHandler.handle(update);
    }

}
