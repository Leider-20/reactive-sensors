package com.leider.reactive_sensors.service;

import com.leider.reactive_sensors.model.Alert;
import com.leider.reactive_sensors.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class SensorProcessorService {

    private final SensorGeneratorService sensorGeneratorService;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    public Flux<Alert> getAlerts() {

        return sensorGeneratorService.generateSensorData()
                .filter(sensor ->

                        switch (sensor.getType()) {
                            case "temperature" -> sensor.getValue() > 35;
                            case "humidity" -> sensor.getValue() > 70;
                            case "pressure" -> sensor.getValue() > 1050;
                            default -> false;
                        })
                .flatMap(sensor -> {

                    Alert alert = new Alert(
                            sensor.getType() + " fuera de rango",
                            sensor.getValue(),
                            sensor.getTimestamp()
                    );

                    return alertRepository.save(alert)
                            .flatMap(savedAlert -> alertService.sendAlert(savedAlert));


                    /*Mono<Alert> saveMonoAlert = alertRepository.save(alert);
                    Mono<Alert> sendMonoAlert = alertService.sendAlert(alert);

                    return Mono.zip(saveMonoAlert, sendMonoAlert)
                            .map(tuple -> tuple.getT1());*/
                });
    }

}
