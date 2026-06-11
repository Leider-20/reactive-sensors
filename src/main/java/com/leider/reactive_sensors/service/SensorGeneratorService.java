package com.leider.reactive_sensors.service;

import com.leider.reactive_sensors.model.Sensor;
import com.leider.reactive_sensors.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SensorGeneratorService {

    private final Random random = new Random();
    private final SensorRepository sensorRepository;

    public Flux<Sensor> generateSensorData() {

        Flux<Sensor> temperaturaFlux = Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> new Sensor(
                        "TEMP-01",
                        "temperature",
                        20 + random.nextDouble() * 20,
                        LocalDateTime.now()
                ));


        Flux<Sensor> humidityFlux = Flux
                .interval(Duration.ofSeconds(2))
                .map(i -> new Sensor(
                        "HUM-01",
                        "humidity",
                        40 + random.nextDouble() * 40,
                        LocalDateTime.now()

                ));


        Flux<Sensor> pressureFlux = Flux
                .interval(Duration.ofSeconds(3))
                .map(i -> new Sensor(
                        "PRESS-01",
                        "pressure",
                        900 + random.nextDouble() * 200,
                        LocalDateTime.now()
                ));


        return Flux.merge(temperaturaFlux, humidityFlux, pressureFlux)
                .flatMap(sensor -> {
                    return sensorRepository.save(sensor);
                });
    }

    public Flux<Sensor> getSensorData(){
        return sensorRepository.findAll();
    }
}
