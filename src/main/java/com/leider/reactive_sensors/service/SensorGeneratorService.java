package com.leider.reactive_sensors.service;

import com.leider.reactive_sensors.model.SensorData;
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

    public Flux<SensorData> generateSensorData() {

        Flux<SensorData> temperaturaFlux = Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> new SensorData(
                        "TEMP-01",
                        "temperature",
                        20 + random.nextDouble() * 20,
                        LocalDateTime.now()
                ));


        Flux<SensorData> humidityFlux = Flux
                .interval(Duration.ofSeconds(2))
                .map(i -> new SensorData(
                        "HUM-01",
                        "humidity",
                        40 + random.nextDouble() * 40,
                        LocalDateTime.now()

                ));


        Flux<SensorData> pressureFlux = Flux
                .interval(Duration.ofSeconds(3))
                .map(i -> new SensorData(
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

    public Flux<SensorData> getSensorData(){
        return sensorRepository.findAll();
    }
}
