package com.leider.reactive_sensors.controller;

import com.leider.reactive_sensors.model.Alert;
import com.leider.reactive_sensors.model.Sensor;
import com.leider.reactive_sensors.service.SensorGeneratorService;
import com.leider.reactive_sensors.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;


@RestController
@RequiredArgsConstructor
public class SensorController {

    private final SensorGeneratorService sensorGeneratorService;
    private final AlertService alertService;

    /**
     * MediaType.TEXT_EVENT_STREAM_VALUE: Se usa para flujos continuos.
     * Al usar Mono es mejor no usar este MediaType ya que mono devuelve un solo dato no un flujo de datos.

     * MediaType.APPLICATION_JSON_VALUE: Se puede usar para mostrar datos en flujos no continuos.
     * Se usa en flujos de tipo mono, aunque no es tan necesario. Al final, simplemente se puede no poner nada y ya.
     */

    /*@ResponseStatus(HttpStatus.FOUND)
    @GetMapping(value = "/mensaje", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getMensaje() {
        return Mono.just("Hola Mundo");
    }*/

    @GetMapping(value = "/sensors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Sensor> getSensors(){
        return sensorGeneratorService.generateSensorData();
    }

    @GetMapping(value = "/alerts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Alert> getAlerts(){
        return alertService.getAlerts();
    }

    @GetMapping(value = "/history", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Sensor> getHistory(){
        return sensorGeneratorService.getSensorHistory()
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/history-v2")
    public Flux<Sensor> getHistoryV2(){
        return sensorGeneratorService.getSensorHistory();
    }


}
