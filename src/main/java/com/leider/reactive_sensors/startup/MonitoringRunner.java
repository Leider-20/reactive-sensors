package com.leider.reactive_sensors.startup;

import com.leider.reactive_sensors.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringRunner implements CommandLineRunner {

    private final AlertService alertService;

    @Override
    public void run(String @NonNull ... args) {

        alertService
                .getAlerts()
                .subscribe(alert ->
                        System.out.println(
                                "MONITOREO: " + alert.getMessage()
                        )
                );
    }
}