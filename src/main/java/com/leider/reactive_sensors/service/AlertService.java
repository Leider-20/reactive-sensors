package com.leider.reactive_sensors.service;

import com.leider.reactive_sensors.model.Alert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class AlertService {

    public Mono<Alert> sendAlert(Alert alert){

        return Mono.just(alert)
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(a ->
                        System.out.println("ALERTA ENVIADA: " + a.getMessage())
                );
    }
}
