package ru.yusupov.thirdproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yusupov.thirdproject.models.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findByName(String name);
}
