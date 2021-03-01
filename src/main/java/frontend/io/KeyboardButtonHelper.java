package frontend.io;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.stream.Collectors;

public class KeyboardButtonHelper {

    public static final String HELP_COMMAND     = "/help";
    public static final String EXIT_COMMAND     = "/exit";
    public static final String WELCOME_COMMAND  = "/welcome";
    public static final String GET_COMMAND      = "/get";
    public static final String ADD_COMMAND      = "/add";
    public static final String CANCEL_COMMAND   = "/cancel";

    public static final KeyboardButton HELP_COMMAND_BUTTON      = new KeyboardButton(HELP_COMMAND);
    public static final KeyboardButton EXIT_COMMAND_BUTTON      = new KeyboardButton(EXIT_COMMAND);
    public static final KeyboardButton WELCOME_COMMAND_BUTTON   = new KeyboardButton(WELCOME_COMMAND);
    public static final KeyboardButton GET_COMMAND_BUTTON       = new KeyboardButton(GET_COMMAND);
    public static final KeyboardButton ADD_COMMAND_BUTTON       = new KeyboardButton(ADD_COMMAND);
    public static final KeyboardButton CANCEL_COMMAND_BUTTON    = new KeyboardButton(CANCEL_COMMAND);


    public static List<KeyboardRow> collectKeyboardRows(List<List<KeyboardButton>> buttonLists) {
        return buttonLists.stream().map(KeyboardButtonHelper::translateKeyboardButtonListToKeyboardRow).collect(Collectors.toList());
    }

    private static KeyboardRow translateKeyboardButtonListToKeyboardRow(List<KeyboardButton> buttons) {
        KeyboardRow row = new KeyboardRow();
        row.addAll(buttons);
        return row;
    }
}

