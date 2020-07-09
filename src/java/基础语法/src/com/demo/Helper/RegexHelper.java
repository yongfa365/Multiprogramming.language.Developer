package com.demo.Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    public static Matcher match(String input, String regex) {
        var match = Pattern.compile(regex).matcher(input);
        if (match.find()) {
            return match;
        }
        return match;
    }

    public static String match(String input, String regex, int groupId) {
        var match = Pattern.compile(regex).matcher(input);
        if (match.find()) {
            return match.group(groupId);
        }
        return "";
    }

}
