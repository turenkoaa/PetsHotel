package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.models.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Page<Request> findAllByStatusAndEndDateGreaterThanOrderByStartDate(RequestStatus requestStatus, LocalDate date, Pageable pageRequest);
    Page<Request> findAllByUser_IdOrderByStartDate(long userId, Pageable pageRequest);
}
