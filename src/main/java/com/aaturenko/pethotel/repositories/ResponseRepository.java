package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.models.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    Page<Response> findAllByUser_Id(long userId, Pageable pageable);
    List<Response> findAllByUser_Id(long userId);
    Page<Response> findAllByRequest_Id(long requestId, Pageable pageable);
    List<Response> findAllByRequest_Id(long requestId);

    @Modifying
    @Query("update Response r set r.status=?1 where r.id in ?2")
    void updateResponsesStatus(ResponseStatus status, List<Long> ids);
}
