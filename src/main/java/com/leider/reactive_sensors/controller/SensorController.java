package com.leider.reactive_sensors.controller;

import com.leider.reactive_sensors.model.Alert;
import com.leider.reactive_sensors.model.SensorData;
import com.leider.reactive_sensors.service.SensorGeneratorService;
import com.leider.reactive_sensors.service.SensorProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SensorController {

    private final SensorGeneratorService sensorGeneratorService;
    private final SensorProcessorService sensorProcessorService;

    @GetMapping(value = "/sensors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<SensorData> getSensors(){
        return sensorGeneratorService.generateSensorData();
    }

    @GetMapping(value = "/alerts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Alert> getAlerts(){
        return sensorProcessorService.getAlerts();
    }

    @GetMapping(value = "/history")
    public Flux<SensorData> getHistory(){
        return sensorGeneratorService.getSensorData();
    }


}
