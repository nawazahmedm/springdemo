package com.javalearnings.securitydemo.utils;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DateUtils {

    /**
     * Get last day of month for current date
     */
    public static String lastDateOfCurrentMonth() {
        LocalDate currentDate = DateUtils.getLocalDateEST();
        LocalDate lastDateOfCurrentMonth  = currentDate.withDayOfMonth(
                currentDate.getMonth().length(currentDate.isLeapYear()));
        return getLocalDateAsString(lastDateOfCurrentMonth);
    }

    public static String getLocalDateAsString(LocalDate localDate) {
        if (localDate != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            return localDate.format(dateTimeFormatter);
        }
        return null;
    }

    public static String getInstantAsString(Instant instantDate) {
        if (instantDate != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").
                    withZone(ZoneId.systemDefault());
            return dateTimeFormatter.format(instantDate);
        }
        return null;
    }

    public List<LocalDate> getDatesBetween(
            LocalDate startDate, LocalDate endDate) {
        endDate = endDate.plusDays(1);

        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }

    public Integer getYear(){
        return DateUtils.getLocalDateEST().getYear();
    }

    public String calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = DateUtils.getLocalDateEST();
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears() + " Y " + period.getMonths() + " Months";
    }

    public LocalDate sameMonth(LocalDate localDate, int months) {
        return localDate.plusMonths(months);
    }

    public static Instant getInstantEST(Instant date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date, ZoneId.of("America/New_York"));
        return zonedDateTime.toInstant();
    }

    public static Instant getInstantEST() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        return zonedDateTime.toInstant();
    }

    public static LocalDate getLocalDateEST() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        return zonedDateTime.toLocalDate();
    }

    public static LocalDateTime getLocalDateTimeEST() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        return zonedDateTime.toLocalDateTime();
    }

    public static LocalTime getLocalTimeEST() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        return zonedDateTime.toLocalTime();
    }

    /*public static void main(String[] args) {
        LocalDate dob = LocalDate.of(1988, 12, 13);
        System.out.println(new DateUtils().calculateAge(dob));
    }*/

    /*public static void main(String[] args) {
        LocalDate fromDate = LocalDate.of(2023, 02, 01);
        LocalDate toDate = LocalDate.of(2023, 02, 28);
        toDate = toDate.plusDays(1);

        List<LocalDate> localDateList = new DateUtils().getDatesBetween(fromDate, toDate);
        System.out.println(localDateList);

        System.out.println(new DateUtils().getYear());
    }*/

}
