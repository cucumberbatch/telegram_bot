package backend.command;

import backend.command.handler.AddingContactInformationMenuContextHandler;
import backend.command.handler.MenuStrategyHandler;
import backend.command.handler.GeneralMenuCancelHandler;
import backend.command.handler.TagSelectionMenuHelpHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AddingContactInformationMenuStrategy implements IMenuStrategy {

    private final MenuStrategyHandler cancelHandler;
    private final MenuStrategyHandler helpHandler;
    private final MenuStrategyHandler contextHandler;

    public AddingContactInformationMenuStrategy(ChatSession session) {
        cancelHandler  = new GeneralMenuCancelHandler(session);
        helpHandler    = new TagSelectionMenuHelpHandler(session);
        contextHandler = new AddingContactInformationMenuContextHandler(session);
    }

    @Override
    public void add(Update update) {
        /* no implementation */
    }

    @Override
    public void help(Update update) {
        helpHandler.handle(update);
    }

    @Override
    public void cancel(Update update) {
        cancelHandler.handle(update);
    }

    @Override
    public void handle(Update update) {
        contextHandler.handle(update);
    }
}
