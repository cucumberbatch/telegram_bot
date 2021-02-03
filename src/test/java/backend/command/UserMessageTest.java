package backend.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserMessageTest {

    String welcomeCommand = "  /welcome  serb / weg";
    String helpCommand = "/help";
    String settingsCommand = "/settings";
    String exitCommand = "/exit";

    @Test
    void valueOfWelcomeUserMessage() {
//        Assertions.assertEquals(UserMessage.WELCOME, UserMessage.valueOf(UserMessage.truncateCommand(welcomeCommand)));
    }

    @Test
    void valueOfHelpUserMessage() {
//        Assertions.assertEquals(UserMessage.HELP, UserMessage.valueOf(UserMessage.truncateCommand(helpCommand)));
    }

}