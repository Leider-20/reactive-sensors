package com.leider.reactive_sensors.startup;

import com.leider.reactive_sensors.service.SensorProcessorService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringRunner implements CommandLineRunner {

    private final SensorProcessorService sensorProcessorService;

    @Override
    public void run(String @NonNull ... args) {

        sensorProcessorService
                .getAlerts()
                .subscribe(alert ->
                        System.out.println(
                                "MONITOREO: " + alert.getMessage()
                        )
                );
    }
}