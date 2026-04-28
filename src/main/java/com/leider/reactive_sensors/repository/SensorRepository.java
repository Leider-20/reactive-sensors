package com.leider.reactive_sensors.repository;

import com.leider.reactive_sensors.model.SensorData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SensorRepository extends ReactiveMongoRepository<SensorData, String> {

}
