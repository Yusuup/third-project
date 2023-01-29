package ru.yusupov.thirdproject.dto;

import java.util.List;

public class ListOfMeasurements {
    List<MeasurementDTO> measurementsDTO;

    public ListOfMeasurements(List<MeasurementDTO> measurementsDTO) {
        this.measurementsDTO = measurementsDTO;
    }

    public List<MeasurementDTO> getMeasurementsDTO() {
        return measurementsDTO;
    }

    public void setMeasurementsDTO(List<MeasurementDTO> measurementsDTO) {
        this.measurementsDTO = measurementsDTO;
    }
}
