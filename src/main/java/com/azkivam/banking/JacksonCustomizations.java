package com.azkivam.banking;

import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryFormats;
import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Configuration(proxyBeanMethods = false)
class JacksonCustomizations {

	@Bean
	Module moneyModule() {
		return new MoneyModule();
	}

	@SuppressWarnings("serial")
	static class MoneyModule extends SimpleModule {

		public MoneyModule() {

			addSerializer(MonetaryAmount.class, new MonetaryAmountSerializer());
			addValueInstantiator(MonetaryAmount.class, new MoneyInstantiator());
		}

		static class MonetaryAmountSerializer extends StdSerializer<MonetaryAmount> {

			private static final Pattern MONEY_PATTERN;

			static {

				StringBuilder builder = new StringBuilder();

				builder.append("(?=.)^"); // Start
				builder.append("[A-Z]{3}?"); // 3-digit currency code
				builder.append("\\s"); // single whitespace character
				builder.append("(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?"); // digits with optional grouping by "," every 3)
				builder.append("(\\.[0-9]{1,2})?$"); // End with a dot and two digits

				MONEY_PATTERN = Pattern.compile(builder.toString());
			}

			public MonetaryAmountSerializer() {
				super(MonetaryAmount.class);
			}

			@Override
			public void serialize(@Nullable MonetaryAmount value, JsonGenerator jgen, SerializerProvider provider)
					throws IOException {

				if (value != null) {
					jgen.writeString(MonetaryFormats.getAmountFormat(Locale.ROOT).format(value));
				} else {
					jgen.writeNull();
				}
			}


		}

		static class MoneyInstantiator extends ValueInstantiator {

			@Override
			public String getValueTypeDesc() {
				return MonetaryAmount.class.toString();
			}


			@Override
			public boolean canCreateFromString() {
				return true;
			}

			@Override
			public Object createFromString(DeserializationContext context, String value) throws IOException {
				return Money.parse(value, MonetaryFormats.getAmountFormat(Locale.ROOT));
			}
		}
	}
}
