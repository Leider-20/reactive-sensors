package com.leider.reactive_sensors.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sensors")
public class SensorData {

    @Id
    private String id;
    private String sensorId;
    private String type;
    private Double value;
    private LocalDateTime timestamp;

    public SensorData(String sensorId, String type, Double value, LocalDateTime timestamp) {
        this.sensorId = sensorId;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }
}
