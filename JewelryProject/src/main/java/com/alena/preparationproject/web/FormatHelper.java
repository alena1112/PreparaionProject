package com.alena.preparationproject.web;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatHelper {
    public static DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
    static {
        decimalFormatSymbols.setGroupingSeparator(' ');
    }
    public static DecimalFormat formatter = new DecimalFormat("###,###", decimalFormatSymbols);

    public enum Currency {RUB, USD}

    public static String getPriceFormat(Double price, Currency currency) {
        price = price == null ? 0 : price;
        StringBuilder sb = new StringBuilder();
        sb.append(formatter.format(price));
//        sb.append(currency == Currency.RUB ? " \u20BD" : "");
        return sb.toString();
    }
}
