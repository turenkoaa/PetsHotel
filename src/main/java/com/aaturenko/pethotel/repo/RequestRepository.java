package com.aaturenko.pethotel.repo;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByUser_Id(long id);
    List<Request> findAllByStatus(RequestStatus status);
}
