package backend.service;

import frontend.io.KeyboardButtonHelper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class DefaultConfigurationReplyKeyboardMarkupFactory {

    private static DefaultConfigurationReplyKeyboardMarkupFactory factory;
    private ReplyKeyboardMarkup markup;


    public static DefaultConfigurationReplyKeyboardMarkupFactory getInstance(List<List<KeyboardButton>> keyboardButtons) {
        return factory == null ? new DefaultConfigurationReplyKeyboardMarkupFactory(keyboardButtons) : factory;
    }

    public static DefaultConfigurationReplyKeyboardMarkupFactory getInstance() {
        return getInstance(new ArrayList<>(new ArrayList<>()));
    }

    public DefaultConfigurationReplyKeyboardMarkupFactory(List<List<KeyboardButton>> keyboardButtons) {
        markup = new ReplyKeyboardMarkup(KeyboardButtonHelper.collectKeyboardRows(keyboardButtons))
                .setSelective(true)
                .setResizeKeyboard(true)
                .setOneTimeKeyboard(false);
    }

    public ReplyKeyboardMarkup newKeyboardInstance() {
        ReplyKeyboardMarkup clonedMarkup = new ReplyKeyboardMarkup(markup.getKeyboard());
        clonedMarkup.setSelective(markup.getSelective());
        clonedMarkup.setResizeKeyboard(markup.getResizeKeyboard());
        clonedMarkup.setOneTimeKeyboard(markup.getOneTimeKeyboard());
        return clonedMarkup;
    }
}
