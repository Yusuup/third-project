package ru.yusupov.thirdproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yusupov.thirdproject.models.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
