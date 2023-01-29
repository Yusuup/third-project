package ru.yusupov.thirdproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yusupov.thirdproject.models.Measurement;
import ru.yusupov.thirdproject.repositories.MeasurementRepository;
import ru.yusupov.thirdproject.util.sensor.NoSuchSensorException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> measurementList() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        addOptions(measurement);
        measurementRepository.save(measurement);
    }

    public long rainyDays() {
        return measurementRepository.findAll().stream().filter(Measurement::isRaining).count();
    }

    private void addOptions(Measurement measurement) {
        measurement.setSensor(sensorService.getPersonByName(measurement.getSensor().getName()).orElseThrow(NoSuchSensorException::new));
        measurement.setMeasurementTime(LocalDateTime.now());
    }
}
