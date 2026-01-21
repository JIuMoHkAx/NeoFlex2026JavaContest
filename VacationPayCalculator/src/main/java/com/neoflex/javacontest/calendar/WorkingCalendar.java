package com.neoflex.javacontest.calendar;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface WorkingCalendar {
    boolean isPayableDay(LocalDate date);
    boolean isHoliday(LocalDate date);
    boolean isNonWorkingDay(LocalDate date);
    BigDecimal getAvgDaysInMonth();
}
