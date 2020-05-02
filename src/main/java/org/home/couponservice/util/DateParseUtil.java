package org.home.couponservice.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParseUtil {

    private static final String REGEXP_DATE_GROUP_NAME = "date";
    private static final String REGEXP_MONTH_GROUP_NAME = "month";
    private static final String REGEXP_YEAR_GROUP_NAME = "year";
    private static final List<DateTimeFormatter> SUPPORTED_DATE_DATE_FORMATTER_LIST = List.of(
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
            DateTimeFormatter.ofPattern("dd.MM.yy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy")
    );

    public static LocalDate parseDate(List<Pattern> patterns, String dateToParse, Locale locale) {

        LocalDate localDate = null;

        Pattern datePattern = patterns.stream()
                .filter(pattern -> pattern.matcher(dateToParse).matches())
                .findFirst()
                .orElseThrow();

        Matcher matcher = datePattern.matcher(dateToParse);
        if (matcher.matches()) {
            Map<String, String> groupValuesFromDateStr = getGroupValuesFromMatchedString(matcher);
            localDate = getDate(groupValuesFromDateStr, locale);
        }

        return localDate;
    }


    private static Map<String, String> getGroupValuesFromMatchedString(Matcher matcher) {
        Map<String, String> result  = new HashMap<>();

        List.of(REGEXP_DATE_GROUP_NAME, REGEXP_MONTH_GROUP_NAME, REGEXP_YEAR_GROUP_NAME).forEach(
                (groupName) -> {
                    try {
                        String groupValue = matcher.group(groupName);
                        result.put(groupName, groupValue);
                    } catch (Exception ignored) {}
                }
        );
        return result;
    }

    private static LocalDate getDate(Map<String, String> groupValuesFromDateStr, Locale locale) {

        LocalDate validUntil = null;


        if(groupValuesFromDateStr.containsKey(REGEXP_DATE_GROUP_NAME)) {

            String dateToParse = groupValuesFromDateStr.get(REGEXP_DATE_GROUP_NAME);

            for (DateTimeFormatter dateTimeFormatter : SUPPORTED_DATE_DATE_FORMATTER_LIST) {
                try {
                    validUntil = LocalDate.parse(dateToParse, dateTimeFormatter);
                    break;
                } catch (DateTimeParseException ignored) {}
            }

        } else {
            try {

                String monthStr = groupValuesFromDateStr.get(REGEXP_MONTH_GROUP_NAME);
                int year = Integer.parseInt(groupValuesFromDateStr.get(REGEXP_YEAR_GROUP_NAME));

                Month parsedMonth = Arrays.stream(Month.values()).filter(
                        (month) -> {
                            String translatedName = month.getDisplayName(TextStyle.FULL_STANDALONE , locale);
                            return translatedName.equalsIgnoreCase(monthStr);
                        }).findFirst().orElseThrow();

                validUntil = LocalDate.of(year, parsedMonth, parsedMonth.length(isLeapYear(year)));

            } catch (Exception ex) {
                ex.fillInStackTrace();
            }

        }

        return validUntil;
    }

    private static boolean isLeapYear(int year) {
        return LocalDate.of(year, Month.JANUARY, 1).isLeapYear();
    }
}
