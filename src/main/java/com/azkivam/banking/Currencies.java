package com.azkivam.banking;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public interface Currencies {

	public static final CurrencyUnit IRR = Monetary.getCurrency("IRR");
}