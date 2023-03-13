package com.hyuuny.noriter.support.common.money.infrastructure;

import com.hyuuny.noriter.support.common.money.domain.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Long> {

    @Override
    public Long convertToDatabaseColumn(Money money) {
        return Optional.ofNullable(money)
                .map(Money::longValue)
                .orElse(0L);
    }

    @Override
    public Money convertToEntityAttribute(Long amount) {
        return Money.wons(amount);
    }

}
