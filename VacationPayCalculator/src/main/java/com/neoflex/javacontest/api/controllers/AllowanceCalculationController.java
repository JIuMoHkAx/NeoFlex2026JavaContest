package com.neoflex.javacontest.api.controllers;

import com.neoflex.javacontest.service.AllowanceCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
public class AllowanceCalculationController {

    private final AllowanceCalculationService service;
    public AllowanceCalculationController(AllowanceCalculationService service) {
        this.service = service;
    }

    // Контракт указан в ТЗ - GET "/calculacte" - перенаправляем на правильный
    @GetMapping("/calculacte")
    public Double calculacte(
            @RequestParam Double averageSalary,
            @RequestParam Integer vacationDays,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        log.info("Пришел запрос на /calculacte - перенаправляем на /calculate");
        return calculate(averageSalary, vacationDays, startDate, endDate);
    }

    @GetMapping("/calculate")
    public Double calculate(
            @RequestParam Double averageSalary,
            @RequestParam Integer vacationDays,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        log.info("Запрос на расчет отпускных: salary={}, days={}, startDate={}, endDate={}",
                averageSalary, vacationDays, startDate, endDate);

        double result = service.calculate(averageSalary, vacationDays, startDate, endDate);
        log.info("Отправлен результат расчета: result={}", result);
        return result;
    }
}
