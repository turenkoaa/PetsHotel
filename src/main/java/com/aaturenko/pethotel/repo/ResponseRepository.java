package com.aaturenko.pethotel.repo;

import com.aaturenko.pethotel.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findAllByRequest_Id(long requestId);
    List<Response> findAllByUser_Id(long authorId);
}
