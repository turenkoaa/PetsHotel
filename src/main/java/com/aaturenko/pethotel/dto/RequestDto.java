package com.aaturenko.pethotel.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class RequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> petsIds;
}
