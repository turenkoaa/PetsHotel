package com.aaturenko.pethotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> petsIds;
    private int cost;

    private RequestDto(LocalDate startDate, LocalDate endDate, List<Long> petsIds, int cost) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.petsIds = petsIds;
        this.cost = cost;
    }

    public static RequestDtoBuilder builder() {
        return new RequestDtoBuilder();
    }

    public static class RequestDtoBuilder {
        private LocalDate startDate;
        private LocalDate endDate;
        private List<Long> petsIds;
        private int cost;

        RequestDtoBuilder() {
        }

        public RequestDtoBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public RequestDtoBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public RequestDtoBuilder petsIds(List<Long> petsIds) {
            this.petsIds = petsIds;
            return this;
        }

        public RequestDtoBuilder cost(int cost) {
            this.cost = cost;
            return this;
        }

        public RequestDto build() {
            return new RequestDto(startDate, endDate, petsIds, cost);
        }
    }
}
