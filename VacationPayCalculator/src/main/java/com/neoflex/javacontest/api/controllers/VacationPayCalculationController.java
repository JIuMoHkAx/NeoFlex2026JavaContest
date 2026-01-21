package com.neoflex.javacontest.api.controllers;

import com.neoflex.javacontest.service.VacationPayCalculationService;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RestController
@Validated
public class VacationPayCalculationController {

    private final VacationPayCalculationService service;

    public VacationPayCalculationController(VacationPayCalculationService service) {
        this.service = service;
    }

    // Контракт указан в ТЗ - GET "/calculacte" - перенаправляем на правильный
    @GetMapping("/calculacte")
    public BigDecimal calculacte(
            @RequestParam @DecimalMin(value = "1.00", message = "Средняя з/п не может быть отрицательной или нулевой") BigDecimal averageSalary,
            @RequestParam @Min(value = 1, message = "Дней отпуска должно быть не меньше 1") Integer vacationDays,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        log.info("Пришел запрос на /calculacte - направляем на calculate");
        return calculate(averageSalary, vacationDays, startDate);
    }

    @GetMapping("/calculate")
    public BigDecimal calculate(
            @RequestParam @DecimalMin(value = "1.00", message = "Средняя з/п не может быть отрицательной или нулевой")
                BigDecimal averageSalary,
            @RequestParam @Min(value = 1, message = "Дней отпуска должно быть не меньше 1")
                Integer vacationDays,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate startDate) {

        log.info("Запрос на расчет отпускных: salary={}, days={}, startDate={}",
                averageSalary, vacationDays, startDate);

        BigDecimal result = startDate == null ?
                service.calculate(averageSalary,vacationDays) :
                service.calculate(averageSalary, vacationDays, startDate);

        log.info("Отправлен результат расчета: result={}", result);
        return result;
    }
}
