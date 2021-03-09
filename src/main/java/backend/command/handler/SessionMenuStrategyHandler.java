package backend.command.handler;

import backend.command.ChatSession;

public abstract class SessionMenuStrategyHandler implements MenuStrategyHandler {
    protected ChatSession session;

    public SessionMenuStrategyHandler(ChatSession session) {
        this.session = session;
    }
}
