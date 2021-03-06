package com.aaturenko.pethotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDto {
    private long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long petId;
    private int cost;

    public static RequestDtoBuilder builder() {
        return new RequestDtoBuilder();
    }

    public RequestDto(LocalDate startDate, LocalDate endDate, Long petId, int cost) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.petId = petId;
        this.cost = cost;
    }

    public static class RequestDtoBuilder {
        private LocalDate startDate;
        private LocalDate endDate;
        private Long petsId;
        private int cost;

        RequestDtoBuilder() {}

        public RequestDtoBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public RequestDtoBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public RequestDtoBuilder petsIds(Long petsId) {
            this.petsId = petsId;
            return this;
        }

        public RequestDtoBuilder cost(int cost) {
            this.cost = cost;
            return this;
        }

        public RequestDto build() {
            return new RequestDto(startDate, endDate, petsId, cost);
        }
    }
}
