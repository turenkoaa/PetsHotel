package com.aaturenko.pethotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto implements Serializable {
    String message;
    boolean success;
}
