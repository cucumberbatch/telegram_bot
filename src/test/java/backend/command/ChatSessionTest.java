package backend.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChatSessionTest {

    static String ADD_COMMAND    = "/add";
    static String CANCEL_COMMAND = "/cancel";


    @Test
    void testChatSessionMainMenuInitialisation() {
        ChatSession session = new ChatSession();
        Assertions.assertInstanceOf(MainMenuStrategy.class, session.getMenuStrategy());
    }


}