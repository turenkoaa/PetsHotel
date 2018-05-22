package com.aaturenko.pethotel.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.AspectException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Around("execution(* com.aaturenko.pethotel.controllers..*(..))n(* com.epam.lstrsum.controller.AnswerController.addAnswer(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws AspectException {
        return logMethod(joinPoint);
    }

    private Object logMethod(ProceedingJoinPoint joinPoint) throws AspectException {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        try {
            log.debug("{} method called with args {}", methodName, OBJECT_MAPPER.writeValueAsString(args));
        } catch (JsonProcessingException e) {
            log.error("{} method thrown exception {}", methodName, e.getMessage());
            throw new AspectException(e.getMessage(), e);
        }

        Object result;
        try {
            result = joinPoint.proceed(args);

            if (result instanceof ResponseEntity) {
                ResponseEntity response = (ResponseEntity) result;
                log.debug("{} method status code = {}", methodName, response.getStatusCode());
            } else {
                log.debug("{} method was completed", methodName);
            }
        } catch (Throwable t) {
            String message = t.getMessage();
            log.error("{} method thrown exception {}", methodName, message);
            result = ResponseEntity.badRequest().body(getMessageDetails(HttpStatus.BAD_REQUEST, message));
        }

        return result;
    }

    private Map<String, String> getMessageDetails(HttpStatus httpStatus, String message) {
        Map<String, String> messageDetails = new LinkedHashMap<>();
        messageDetails.put("code", httpStatus.toString());
        messageDetails.put("message", message);
        messageDetails.put("timestamp", Instant.now().toString());
        return messageDetails;
    }
}
