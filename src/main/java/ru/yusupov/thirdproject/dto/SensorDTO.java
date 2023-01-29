package ru.yusupov.thirdproject.dto;

import ru.yusupov.thirdproject.models.Measurement;

import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

public class SensorDTO {
    @Size(min = 3, max = 30, message = "Кол-во символов должно быть от 3 до 30")
    private String name;

    public SensorDTO() {
    }

    public SensorDTO(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
