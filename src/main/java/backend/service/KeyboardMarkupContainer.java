package backend.service;

import frontend.io.KeyboardButtonHelper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Arrays;

public class KeyboardMarkupContainer {

    public static final ReplyKeyboardMarkup MAIN_MENU_REPLY_KEYBOARD_MARKUP = new ReplyKeyboardMarkup(KeyboardButtonHelper.collectKeyboardRows(Arrays.asList(
            Arrays.asList(
                    KeyboardButtonHelper.GET_COMMAND_BUTTON),
            Arrays.asList(
                    KeyboardButtonHelper.WELCOME_COMMAND_BUTTON,
                    KeyboardButtonHelper.EXIT_COMMAND_BUTTON,
                    KeyboardButtonHelper.HELP_COMMAND_BUTTON)
            )))
            .setSelective(true)
            .setResizeKeyboard(true)
            .setOneTimeKeyboard(false);

}