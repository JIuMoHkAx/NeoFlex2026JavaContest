package com.neoflex.javacontest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "calendar")
@Getter
@Setter
public class ServiceConfig {
    private Set<LocalDate> holidays;
    private Set<LocalDate> weekends;            // может быть null, если в yml не задано
    private Double averageDaysInMonth = 29.3;   // значение по умолчанию, если в yml не задано
}