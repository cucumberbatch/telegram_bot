package backend.service;

import backend.command.MessageCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMessageFormatter {

    public static String formatTag(String text) {
//        String regex = "([\\p{L}|\\d]+)\\s*$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(text);
//        matcher.find();
//        return matcher.group(1);
        return text;
    }


    public static String formatPhoneNumber(String phoneNumber) {
        return String.format(
                MessageCommand.PHONE_NUMBER_FORMAT,
                phoneNumber.substring(0, 2),
                phoneNumber.substring(2, 5),
                phoneNumber.substring(5, 8),
                phoneNumber.substring(8, 10),
                phoneNumber.substring(10, 12));
    }

//    public static String formatPhoneNumber(String phoneNumber) {
//        String phoneNumberRegex = "\\+(\\d)\\((\\d*)\\)(\\d*)\\-(\\d*)\\-(\\d*)";
//        Pattern pattern = Pattern.compile(phoneNumberRegex);
//        Matcher matcher = pattern.matcher(phoneNumber);
//
////        if (!matcher.find()) { return null; }
////        if (matcher.group(0).length() <= 10 || matcher.group(0).length() >= 17) { return null; }
//
//
//
//        return String.format(
//                PHONE_NUMBER_FORMAT,
//                phoneNumber.substring(0, 2),
//                phoneNumber.substring(2, 5),
//                phoneNumber.substring(5, 8),
//                phoneNumber.substring(8, 10),
//                phoneNumber.substring(10, 12));
//    }
}
