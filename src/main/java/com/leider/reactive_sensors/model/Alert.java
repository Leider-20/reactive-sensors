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
@Document(collection = "alerts")
public class Alert {

    @Id
    private String id;
    private String message;
    private Double value;
    private LocalDateTime timestamp;

    public Alert(String message, Double value, LocalDateTime timestamp) {
        this.message = message;
        this.value = value;
        this.timestamp = timestamp;
    }
}
