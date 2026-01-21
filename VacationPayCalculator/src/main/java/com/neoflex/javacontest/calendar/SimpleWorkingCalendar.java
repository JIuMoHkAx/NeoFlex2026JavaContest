package com.neoflex.javacontest.calendar;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Component
public class SimpleWorkingCalendar implements WorkingCalendar {

    private final Set<LocalDate> holidays;
    private final Set<LocalDate> nonWorkingDays; // может быть null
    private final BigDecimal avgDaysInMonth;

    public SimpleWorkingCalendar(CalendarProperties cfg) {
        this.holidays = cfg.getHolidays();
        this.nonWorkingDays = cfg.getNonWorkingDays();
        this.avgDaysInMonth = cfg.getAverageDaysInMonth();// null если в yml не указано
    }

    @Override
    public boolean isPayableDay(LocalDate date) {
        return !isHoliday(date) && !isNonWorkingDay(date);
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidays != null && holidays.contains(date);
    }

    @Override
    public boolean isNonWorkingDay(LocalDate date) {
        // Если nonWorkingDays задан в yml и не пуст — используем его
        if (nonWorkingDays != null && !nonWorkingDays.isEmpty()) {
            return nonWorkingDays.contains(date);
        }
        // Иначе стандартные выходные: суббота/воскресенье
        DayOfWeek dow = date.getDayOfWeek();
        return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
    }

    @Override
    public BigDecimal getAvgDaysInMonth() {
        return avgDaysInMonth;
    }
}
