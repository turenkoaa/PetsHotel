package com.aaturenko.pethotel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/maps")
public class YandexMapsAPIController {

    private RestTemplate restTemplate = new RestTemplate();
    private final String geoUrl = "https://geocode-maps.yandex.ru/1.x/?geocode=";

    @GetMapping("/address/{address}")
    public ResponseEntity<String> getGeoCoordinates(@PathVariable String address) {
        //"Москва,+Тверская+улица,+дом+7"
        return restTemplate.getForEntity(geoUrl + address, String.class);
    }
}
