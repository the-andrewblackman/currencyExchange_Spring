package com.tmo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "currency_from")
    private String from;
    @Column(name = "currency_to")
    private String to;
    @Column(name = "conversion_rate")
    private BigDecimal conversionRate;

    private ExchangeRate(Builder builder) {
        this.from = builder.from;
        this.to = builder.to;
        this.conversionRate = builder.conversionRate;
    }

    public static class Builder {
        private int id;
        private String from;
        private String to;
        private BigDecimal conversionRate;

        public Builder from(String from) {
            this.from = from;
            return this;
        }
        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder conversionRate(BigDecimal conversionRate){
            this.conversionRate = conversionRate;
            return this;
        }

        public ExchangeRate build(){
            ExchangeRate exchangeRate = new ExchangeRate(this);
            validateUserObect(exchangeRate);
            return exchangeRate;
        }

        private void validateUserObect(ExchangeRate exchangeRate){
            //implement basic validation
        }
    }
}

