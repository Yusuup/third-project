package ru.yusupov.thirdproject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.yusupov.thirdproject.dto.ListOfMeasurements;
import ru.yusupov.thirdproject.dto.MeasurementDTO;
import ru.yusupov.thirdproject.models.Measurement;
import ru.yusupov.thirdproject.services.MeasurementService;
import ru.yusupov.thirdproject.util.measurement.MeasurementErrorResponse;
import ru.yusupov.thirdproject.util.measurement.MeasurementNotCreatedException;
import ru.yusupov.thirdproject.util.sensor.NoSuchSensorException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ListOfMeasurements getMeasurements() {
        return new ListOfMeasurements(measurementService.measurementList()
                .stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<MeasurementDTO> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError error : errorList) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }

        measurementService.save(convertToMeasurement(measurementDTO));
        return new ResponseEntity<>(measurementDTO, HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public long numberOfRainyDays() {
        return measurementService.rainyDays();
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(NoSuchSensorException e) {
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                "Сенсора с таким именем нету",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
