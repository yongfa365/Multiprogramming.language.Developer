package yongfa365.ExtensionMethods;

import lombok.experimental.ExtensionMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class ExtensionMethods {
    public static String toString(ZonedDateTime input, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(input);
    }
    public static String toString(LocalDateTime input, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(input);
    }
    public static String toString(LocalDate input, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(input);
    }
    public static String toString(LocalTime input, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(input);
    }

    public static String trimOrNull(String input) {
        if (input==null || input.isBlank()) {
            return  null;
        }
        return  input.strip();
    }

    public static String trimOrEmpty(String input) {
        if (input==null || input.isBlank()) {
            return  "";
        }
        return  input.strip();
    }
}
