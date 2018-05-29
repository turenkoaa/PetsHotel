package com.aaturenko.pethotel.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private long authorId;
    private long requestId;
    private String details;
    private int cost;
}
