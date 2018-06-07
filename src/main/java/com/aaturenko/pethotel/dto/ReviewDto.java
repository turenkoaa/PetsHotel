package com.aaturenko.pethotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewDto {
    private String comment;
    private boolean like;
    private long userId;
}
