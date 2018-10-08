package com.aaturenko.pethotel.service.impl;

import com.aaturenko.pethotel.dto.PaymentRequestDto;
import com.aaturenko.pethotel.dto.PaymentResponseDto;
import com.aaturenko.pethotel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PaymentService {

    public boolean pay(User user, int cost) {
//        boolean success = false;
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            PaymentResponseDto response = restTemplate.postForObject("http://localhost:8300/pay/process", new PaymentRequestDto(cost, user.getEmail()), PaymentResponseDto.class);
//            success = response.isSuccess();
//            log.info(response.getMessage());
//        } catch (Exception ex) {
//            log.error("Request failed: " + ex.getMessage());
//        }
        return true;
    }
}
