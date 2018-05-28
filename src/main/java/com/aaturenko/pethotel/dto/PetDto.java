package com.aaturenko.pethotel.dto;

import com.aaturenko.pethotel.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetDto {
    private String petType;
    private String name;
    private Integer age;
    private String passport;
}
