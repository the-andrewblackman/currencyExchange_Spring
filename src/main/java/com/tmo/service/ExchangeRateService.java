package com.tmo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tmo.entity.ExchangeRate;
import com.tmo.repository.ExchangeRateRepository;

import javax.xml.ws.Response;

@Service("exchangeRateService")
public class ExchangeRateService {

    private Logger LOGGER = LoggerFactory.getLogger("myLogger");

    @Autowired
    ExchangeRateRepository repository;

    public ResponseEntity<ExchangeRate> exchange(String from, String to, String amount) {

        List<ExchangeRate> validateFrom = this.repository.findByFrom(from.toUpperCase());
        List<ExchangeRate> validateTo = this.repository.findByTo(to.toUpperCase());

        // since coming back in List, can check .isEmpty()
        if (validateFrom.isEmpty() && validateTo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Both currencies were not found: %s and %s", from, to));
        } else if (validateFrom.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Currency was not found: %s", from));
        } else if (validateTo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Currency was not found: %s", to));
        }

        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        ExchangeRate exchangeRate = repository.findByFromAndTo(from.toUpperCase(), to.toUpperCase());

        BigDecimal conversionRate = exchangeRate.getConversionRate();
        BigDecimal multiplyOutcome = conversionRate.multiply(bigDecimalAmount).setScale(2, RoundingMode.HALF_UP).abs();

        ExchangeRate rate = new ExchangeRate.Builder()
                .from(from.toUpperCase())
                .to(to.toUpperCase())
                .conversionRate(multiplyOutcome)
                .build();

        LOGGER.info("The exchange rate of [{}] to [{}] equals [{}]", from, to, rate.getConversionRate());

        return ResponseEntity.status(HttpStatus.OK).body(rate);

    }

    public ResponseEntity<List<ExchangeRate>> findAllEntities() {
    // sorts curencies alphabetically
        List<ExchangeRate> findAll = this.repository.findAll(Sort.by(Sort.Direction.ASC, "to"));

        return ResponseEntity.status(HttpStatus.OK).body(findAll);
    }


}
