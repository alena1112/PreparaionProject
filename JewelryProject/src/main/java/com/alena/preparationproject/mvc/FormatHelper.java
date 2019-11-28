package com.alena.preparationproject.mvc;

import com.alena.preparationproject.mvc.model.Jewelry;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class FormatHelper {
    public static DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();

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

    public static String getFormatListJewelries(List<Jewelry> jewelries) {
        StringBuilder sb = new StringBuilder();
        jewelries.forEach(jewelry -> {
            sb.append(String.format("<a href=\"#\">%s</a>", jewelry.getName()));
            sb.append(", ");
        });
        sb.delete(sb.length() - 2, sb.length() - 1);
        return sb.toString();
    }
}
