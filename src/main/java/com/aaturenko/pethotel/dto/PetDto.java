package com.aaturenko.pethotel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetDto {
    private long userId;
    private String petType;
    private String name;
    private Integer age;
    private String passport;
}
