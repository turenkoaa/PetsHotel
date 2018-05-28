package com.aaturenko.pethotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long petsId;
    private int cost;

    public static RequestDtoBuilder builder() {
        return new RequestDtoBuilder();
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
