package com.javalearnings.securitydemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DateUtils {

    public static ZonedDateTime getZonedDateTimePlusDays(ZonedDateTime zonedDateTime, int days) {
        return zonedDateTime.plusDays(days);
    }

    /**
     * Get last day of month for current date
     */
    public static String lastDateOfCurrentMonth() {
        LocalDate currentDate = DateUtils.getZonedDateEST().toLocalDate();
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

    public static String getInstantAsString(ZonedDateTime zonedDateTime) {
        if (zonedDateTime != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").
                    withZone(ZoneId.systemDefault());
            return dateTimeFormatter.format(zonedDateTime);
        }
        return null;
    }

    public static String getInstantWithTimeAsString(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        return formatter.format(instant);
    }

    public List<LocalDate> getDatesBetween(
            LocalDate startDate, LocalDate endDate) {
        endDate = endDate.plusDays(1);

        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }

    public static Integer getYear(){
        return DateUtils.getLocalDateEST().getYear();
    }

    public static boolean compareDates(ZonedDateTime zonedDateTime) {
        ZonedDateTime currentDate = DateUtils.getZonedDateEST();
        return (currentDate.getYear() == zonedDateTime.getYear() && currentDate.getMonth() == zonedDateTime.getMonth()
                && currentDate.getDayOfMonth() == zonedDateTime.getDayOfMonth());
    }

    /*
    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now().plusDays(1);
        log.debug("zonedDateTime {}", zonedDateTime);
        boolean flag = compareDates(zonedDateTime);
        log.debug("flag {}", flag);
    }*/

    public static int calculateYears(LocalDate dateOfBirth) {
        ZonedDateTime currentDate = DateUtils.getZonedDateEST();
        Period period = Period.between(dateOfBirth, currentDate.toLocalDate());
        return period.getYears();
    }

    public static String calculateAge(LocalDate dateOfBirth) {
        ZonedDateTime currentDate = DateUtils.getZonedDateEST();
        Period period = Period.between(dateOfBirth, currentDate.toLocalDate());
        return period.getYears() + " Y " + period.getMonths() + " Months";
    }

    public ZonedDateTime sameMonth(ZonedDateTime zonedDateTime, int months) {
        return zonedDateTime.plusMonths(months);
    }

    public static ZonedDateTime getInstantEST(Instant date) {
        return ZonedDateTime.ofInstant(date, ZoneId.of("US/Eastern"));
    }

    /*
    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();//ZonedDateTime.now(ZoneId.of("US/Eastern"));
        log.debug("zonedDateTime {}", zonedDateTime);
        String str = getZonedDateTimeAsString(zonedDateTime);
        log.debug("str {}", str);
    }*/

    public static String getZonedDateTimeAsString(ZonedDateTime zonedDateTime) {
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("US/Eastern"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return dateTimeFormatter.format(zonedDateTime);
    }

    public static ZonedDateTime getInstantEST() {
        return ZonedDateTime.now(ZoneId.of("US/Eastern"));
    }

    public static Instant getInstantEST1() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("US/Eastern"));

        //ZonedDateTime zonedDateTimeOf = Instant.(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(), 0, 0, 0, 0, ZoneId.of("UTC"));

        log.debug("zonedDateTime {} ", zonedDateTime);
        //System.out.println("zonedDateTime : "+zonedDateTime);
        //System.out.println("zonedDateTime.toLocalDateTime() : "+zonedDateTime.toLocalDateTime());

        Instant instant =  zonedDateTime.toInstant();
        //Instant instant = Instant.ofEpochSecond(zonedDateTime.toEpochSecond());

        return instant;
    }

    public static LocalDate getLocalDateEST() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        return zonedDateTime.toLocalDate();
    }

    public static ZonedDateTime getZonedDateEST() {
        return ZonedDateTime.now(ZoneId.of("US/Eastern"));
    }

    public static LocalDateTime getLocalDateTimeEST() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("US/Eastern"));
        System.out.println("zonedDateTime : "+zonedDateTime);
        System.out.println("zonedDateTime.toLocalDateTime() : "+zonedDateTime.toLocalDateTime());

        return zonedDateTime.toLocalDateTime();
    }

    public static LocalTime getLocalTimeEST() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("US/Eastern"));
        return zonedDateTime.toLocalTime();
    }

    public static String convertToTwelveHourFormat(LocalTime localTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return localTime.format(dateTimeFormatter);
    }

    /*public static void main(String[] args) {
        LocalDate dob = LocalDate.of(2020, 11, 23);
        System.out.println(new DateUtils().calculateYears(dob));
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
