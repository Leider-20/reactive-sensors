package com.leider.reactive_sensors.repository;

import com.leider.reactive_sensors.model.Alert;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AlertRepository extends ReactiveMongoRepository<Alert, String> {
}
