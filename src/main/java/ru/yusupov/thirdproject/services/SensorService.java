package ru.yusupov.thirdproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yusupov.thirdproject.models.Sensor;
import ru.yusupov.thirdproject.repositories.SensorRepository;
import ru.yusupov.thirdproject.util.sensor.SensorAvailableException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Sensor sensor) {
        if (getPersonByName(sensor.getName()).isPresent()) throw new SensorAvailableException();
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> getPersonByName(String name) {
        return Optional.ofNullable(sensorRepository.findByName(name));
    }
}
