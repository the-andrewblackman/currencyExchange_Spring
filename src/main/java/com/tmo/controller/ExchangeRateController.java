package com.tmo.controller;

import com.tmo.entity.ExchangeRate;
import com.tmo.exception.ResourceNotFoundException;
import com.tmo.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
@Validated
public class ExchangeRateController {

    private Logger LOGGER = LoggerFactory.getLogger("myLogger");

    @Autowired
    private ExchangeRateService service;

    @GetMapping("/exchange-rate/findAll")
    public ResponseEntity<List<ExchangeRate>> findAll() {
        return service.findAllEntities();
    }

    @GetMapping("/exchange-rate/from/to/amount/")
    public ResponseEntity<ExchangeRate> retrieveExchangeRate(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String amount)
            throws ResourceNotFoundException {

        ResponseEntity<ExchangeRate> exchange = service.exchange(from, to, amount);

        return exchange;

    }

}
