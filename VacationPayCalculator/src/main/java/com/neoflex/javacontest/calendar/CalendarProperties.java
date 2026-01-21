package com.neoflex.javacontest.calendar;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "calendar")
@Getter
@Setter
public class CalendarProperties {
    private Set<LocalDate> holidays;
    private Set<LocalDate> nonWorkingDays;            // может быть null, если в yml не задано
    private BigDecimal averageDaysInMonth = new BigDecimal("29.3");  // значение по умолчанию, если в yml не задано
}