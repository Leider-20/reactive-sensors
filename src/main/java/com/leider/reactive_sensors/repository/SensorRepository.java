package com.leider.reactive_sensors.repository;

import com.leider.reactive_sensors.model.Sensor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SensorRepository extends ReactiveMongoRepository<Sensor, String> {

}
