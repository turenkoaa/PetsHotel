package com.aaturenko.pethotel.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseDto {
    private long requestId;
    private long authorId;
    private String details;
    private int cost;
}
