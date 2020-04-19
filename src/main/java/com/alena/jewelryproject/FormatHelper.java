package com.alena.jewelryproject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;

public class FormatHelper {
    public static DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
    public static SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");

    static {
        decimalFormatSymbols.setGroupingSeparator(' ');
    }

    public static DecimalFormat formatter = new DecimalFormat("###,###", decimalFormatSymbols);

    public enum Currency {RUB, USD}

    public static String getPriceFormat(Double price) {
        price = price == null ? 0 : price;
        StringBuilder sb = new StringBuilder();
        sb.append(formatter.format(price));
        return sb.toString();
    }

    public static String getPriceFormat(Double price, Currency currency) {
        price = price == null ? 0 : price;
        StringBuilder sb = new StringBuilder();
        sb.append(formatter.format(price));
        sb.append(currency == Currency.RUB ? " \u20BD" : "");
        return sb.toString();
    }
}
