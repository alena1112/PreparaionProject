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
        StringBuilder sb = new StringBuilder();
        sb.append(price != null ? formatter.format(price) : "");
        sb.append(price != null && currency == Currency.RUB ? " \u20BD" : "");
        return sb.toString();
    }
}
