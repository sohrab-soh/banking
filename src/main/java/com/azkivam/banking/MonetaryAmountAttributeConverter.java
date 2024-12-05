package com.azkivam.banking;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

@Converter(autoApply = true)
public class MonetaryAmountAttributeConverter implements AttributeConverter<MonetaryAmount, String> {

	private static final MonetaryAmountFormat FORMAT = MonetaryFormats.getAmountFormat(Locale.ROOT);

	@Override
	public String convertToDatabaseColumn(MonetaryAmount amount) {

		return amount == null ? null
				: String.format("%s %s", amount.getCurrency().toString(), amount.getNumber().toString());
	}

	@Override
	public Money convertToEntityAttribute(String source) {

		if (source == null) {
			return null;
		}

		try {
			return Money.parse(source);
		} catch (RuntimeException e) {

			try {
				return Money.parse(source, FORMAT);
			} catch (RuntimeException inner) {

				// Propagate the original exception in case the fallback fails
				throw e;
			}
		}
	}
}