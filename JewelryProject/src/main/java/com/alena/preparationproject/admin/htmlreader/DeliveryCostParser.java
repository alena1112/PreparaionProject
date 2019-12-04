package com.alena.preparationproject.admin.htmlreader;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Iterator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliveryCostParser {

    public static Function<Document, Double> parseDelivery(Shops shop) {
        switch (shop) {
            case GREEN_BIRD:
                return document -> 0.0;
            case PANDAHALL:
                return DeliveryCostParser::parsePandahallDelivery;
            case STILNAYA:
                return DeliveryCostParser::parseStilnayaDelivery;
            case LUXFURNITURA:
                return DeliveryCostParser::parseLuxfurnituraDelivery;
        }
        return null;
    }

    private static double parsePandahallDelivery(Document doc) {
        Elements tableElements = doc.select("div");
        Iterator<Element> tables = tableElements.iterator();

        while (tables.hasNext()) {
            Element table = tables.next();
            String attributeClass = table.attributes().get("class");
            if (attributeClass != null && attributeClass.equals("content_rit")) {
                Elements pTags = table.select("p");
                double delivery = parsePandahallDouble(pTags.get(1).text());
                double discount = 0;
                if (pTags.size() == 5) {
                    discount = parsePandahallDouble(pTags.get(2).text());
                }
                return delivery - discount;
            }
        }
        return 0;
    }

    private static double parsePandahallDouble(String number) {
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я.]+([\\d.,]+)$");
        Matcher m = pattern.matcher(number);
        if (m.matches()) {
            try {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator('.');
                symbols.setGroupingSeparator(',');
                return new DecimalFormat("###,###.##", symbols).parse(m.group(1)).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private static double parseStilnayaDelivery(Document doc) {
        Elements tableElements = doc.select("table");
        Iterator<Element> tables = tableElements.iterator();

        while (tables.hasNext()) {
            Element table = tables.next();
            String attributeClass = table.attributes().get("class");
            if (attributeClass != null) {
                if (attributeClass.equals("user-order-items")) {
                    Elements trTags = table.select("tr");
                    for (Element trTag : trTags) {
                        Elements tdTags = trTag.select("td");
                        if (tdTags.size() == 2 && tdTags.get(0).text().equals("Доставка:")) {
                            String deliveryStr = tdTags.get(1).text();
                            Pattern pattern = Pattern.compile("^([\\d.,]+)\\s[a-zA-Zа-яА-Я.]+");
                            Matcher m = pattern.matcher(deliveryStr);
                            if (m.matches()) {
                                return Double.parseDouble(m.group(1).replace(",", "."));
                            }
                        }
                    }
                }
            }
        }

        return 0;
    }

    private static double parseLuxfurnituraDelivery(Document doc) {
        Elements tableElements = doc.select("table");
        Element purchasesTable = tableElements.stream()
                .filter(element -> "purchases".equals(element.attributes().get("id")))
                .findFirst()
                .orElse(null);
        if (purchasesTable != null) {
            Elements trs = purchasesTable.select("tr");
            Element tr = trs.get(trs.size() - 2);
            Elements tds = tr.select("td");
            Element price = tds.stream()
                    .filter(element -> "price".equals(element.className()) && StringUtils.isNotBlank(element.text()))
                    .findFirst()
                    .orElse(null);
            if (price != null) {
                return 0;
            }
        }

        return 0;
    }
}
