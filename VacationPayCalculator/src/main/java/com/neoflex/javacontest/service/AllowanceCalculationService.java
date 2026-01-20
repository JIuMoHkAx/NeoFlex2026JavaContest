package com.neoflex.javacontest.service;

import com.neoflex.javacontest.config.ServiceConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class AllowanceCalculationService {

    private final Set<LocalDate> holidays;
    private final Set<LocalDate> weekends; // может быть null
    private final Double AVG_DAYS_IN_MONTH;

    public AllowanceCalculationService(ServiceConfig cfg) {
        this.holidays = cfg.getHolidays();
        this.weekends = cfg.getWeekends(); // null если в yml не указано
        this.AVG_DAYS_IN_MONTH = cfg.getAverageDaysInMonth();
    }

    public Double calculate(Double averageSalary, Integer vacationDays, LocalDate startDate, LocalDate endDate) {
        double dayRate = averageSalary / AVG_DAYS_IN_MONTH;

        // Если даты не переданы — считаем "простым способом - по дням"
        if (startDate == null || endDate == null) {
            return round(dayRate * vacationDays);
        }

        // Если даты переданы - вычисляем диапазон дат и кол-во оплачиваемых дней
        long payableDays = Stream.iterate(startDate, date -> date.plusDays(1)) // перебираем дни
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)          // лимит - граница перебора
                .filter(this::isPayableDay)                                              // считаем только оплачиваемые дни
                .count();
        return round(dayRate * payableDays);
    }

    // Util
    public boolean isPayableDay(LocalDate date) {
        return !isHoliday(date) && !isWeekend(date);
    }
    public boolean isHoliday(LocalDate date) {
        return holidays != null && holidays.contains(date);
    }
    public boolean isWeekend(LocalDate date) {
        // Если weekends задан в yml и не пуст — используем его
        if (weekends != null && !weekends.isEmpty()) {
            return weekends.contains(date);
        }
        // Иначе стандартные выходные: суббота/воскресенье
        DayOfWeek dow = date.getDayOfWeek();
        return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
    }
    private Double round(Double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}