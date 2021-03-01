package backend.service;

import frontend.io.KeyboardButtonHelper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardMarkupContainer {

    public static final ReplyKeyboardMarkup MAIN_MENU_REPLY_KEYBOARD_MARKUP = new ReplyKeyboardMarkup(KeyboardButtonHelper.collectKeyboardRows(Arrays.asList(
            Arrays.asList(
                    KeyboardButtonHelper.ADD_COMMAND_BUTTON),
            Arrays.asList(
                    KeyboardButtonHelper.HELP_COMMAND_BUTTON)
            )))
            .setSelective(true)
            .setResizeKeyboard(true)
            .setOneTimeKeyboard(false);

    public static final ReplyKeyboardMarkup GET_OR_ADD_MENU_REPLY_KEYBOARD_MARKUP = new ReplyKeyboardMarkup(KeyboardButtonHelper.collectKeyboardRows(Arrays.asList(
            Arrays.asList(
                    KeyboardButtonHelper.CANCEL_COMMAND_BUTTON,
                    KeyboardButtonHelper.HELP_COMMAND_BUTTON)
            )))
            .setSelective(true)
            .setResizeKeyboard(true)
            .setOneTimeKeyboard(false);

    public static ReplyKeyboardMarkup insertInKeyboardMarkupFromTop(ReplyKeyboardMarkup keyboardMarkup, List<String> buttonCaptions) {
        List<KeyboardRow> rows = new ArrayList<>();
        for (String caption : buttonCaptions) {
            KeyboardRow row = new KeyboardRow();
            row.add(caption);
            rows.add(row);
        }
        keyboardMarkup.getKeyboard().addAll(0, rows);
        return keyboardMarkup;
    }
}
