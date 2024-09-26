package com.tmo.util;

import com.tmo.exception.InvalidRequestException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("exchangeRateValidation")
@Aspect
public class ExchangeRateValidation {

    private Logger LOGGER = LoggerFactory.getLogger("myLogger");

    // the service I want to target
    @Pointcut("this(com.tmo.service.ExchangeRateService)")
    private void exchangeRateService() {
    }

    // the arguments I want to validate
    @Pointcut("args(to,from,amount)")
    private void exchangeRateName(String to, String from, String amount) {
    }

    // the service I am targeting & name of function
    @Before("exchangeRateService() && exchangeRateName(to,from,amount)")
    public void exchangeRateValidation(String to, String from, String amount) {
        validateCurrencyName(to);
        validateCurrencyName(from);
        validateAmount(amount);
    }

    public void validateCurrencyName(String currency) {
        // validates length
        if (currency.length() <= 2 || currency.length() >= 4) {
            LOGGER.error("Currency needs to be three letters: [{}]", currency);
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, String.format("Currency needs to be three letters: %s", currency));
        }
        //validates A - Z
        if (currency.matches("[a-zA-Z]+")) {
            return;
        } else {
            LOGGER.error("Currency needs to be letters from the alphabet: [{}]", currency);
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, String.format("Currency needs to be letters from the alphabet: %s", currency));
        }
    }

    public void validateAmount(String amount) {
        // validates 0-9 and two spots past decimal
        if (amount.matches("^[0-9]*\\.[0-9]{2,100}$") || amount.matches("^[0-9]*$")) {
            return;
        } else {
            LOGGER.error("Amount has to be a number 0-9: [{}]", amount);
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, String.format("Amount '%s' has to be a number 0-9 ", amount));
        }
    }
}
