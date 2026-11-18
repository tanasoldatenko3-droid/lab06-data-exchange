package by.bseu.pp.lab06;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DateParser {
    private static final String ISO_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final String DOT_PATTERN = "\\d{2}\\.\\d{2}\\.\\d{4}";

    private static final DateTimeFormatter DOT_FORMATTER = DateTimeFormatter
            .ofPattern("dd.MM.uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private DateParser() { }
    /** TODO 1. ISO uuuu-MM-dd или dd.MM.uuuu, без пробелов; невозможные даты запрещены. */
    public static LocalDate parseDate(String text) {
        // TODO 1: реализуйте по условию лабораторной работы.
        if (text == null || text.isEmpty()) {
            throw new DataFormatException("date: пустая дата ", (Throwable) null);
        }
        try {
            if(text.matches(ISO_PATTERN)) {
                return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
            }
            if (text.matches(DOT_PATTERN)) {
                return LocalDate.parse(text, DOT_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            throw new DataFormatException("date: некорректная дата " + text, e);
        }
        throw new DataFormatException("date: неверный формат даты " + text, (Throwable) null);
    }
}
