package com.neoflex.javacontest.service;

import com.neoflex.javacontest.calendar.WorkingCalendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
@Service
public class VacationPayCalculationService {

    private final WorkingCalendar calendar;

    public VacationPayCalculationService(WorkingCalendar calendar) {
        this.calendar = calendar;
    }

    public BigDecimal calculate(BigDecimal averageSalary, Integer vacationDays){
        BigDecimal dayRate = averageSalary.divide(calendar.getAvgDaysInMonth(), 10, RoundingMode.HALF_UP);
        log.info("Используем простой расчет по дням");
        return dayRate.multiply(BigDecimal.valueOf(vacationDays))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculate(BigDecimal averageSalary, Integer vacationDays, LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate не может быть null");
        }

        BigDecimal dayRate = averageSalary.divide(calendar.getAvgDaysInMonth(), 10, RoundingMode.HALF_UP);
        LocalDate currentDate = startDate;
        int payableDaysFound = 0;
        for (int i = 0; i < vacationDays; i++) {
            if (calendar.isPayableDay(currentDate)) {
                payableDaysFound++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return dayRate.multiply(BigDecimal.valueOf(payableDaysFound))
                .setScale(2, RoundingMode.HALF_UP);
    }
}