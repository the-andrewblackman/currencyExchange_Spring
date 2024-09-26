package com.tmo.repository;

import com.tmo.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>  {

    ExchangeRate findByFromAndTo(String from, String to);

    List<ExchangeRate> findByFrom(String from);

    List<ExchangeRate> findByTo(String to);

    List<ExchangeRate> findAll();
}
