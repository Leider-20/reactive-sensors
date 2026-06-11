package com.leider.reactive_sensors.service;

import com.leider.reactive_sensors.model.Alert;
import com.leider.reactive_sensors.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final SensorGeneratorService sensorGeneratorService;
    private final AlertRepository alertRepository;

    public Mono<Alert> sendAlert(Alert alert) {

        return Mono.just(alert)
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(a ->
                        System.out.println("ALERTA ENVIADA: " + a.getMessage())
                );
    }

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

                    //Se guardan y envían las alertas de forma secuencial
                    return alertRepository.save(alert)
                            .flatMap(savedAlert -> sendAlert(savedAlert));


                    //Se guardan y envían las alertas de forma paralela
                    /*
                    Mono<Alert> saveMonoAlert = alertRepository.save(alert);
                    Mono<Alert> sendMonoAlert = alertService.sendAlert(alert);

                    return Mono.zip(saveMonoAlert, sendMonoAlert)
                            .map(tuple -> tuple.getT1());
                    */
                });
    }

}
