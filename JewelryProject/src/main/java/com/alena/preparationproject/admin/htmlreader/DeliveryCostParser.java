package com.alena.preparationproject.admin.htmlreader;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
                return document -> 0.0;
        }
        return null;
    }

    private static double parsePandahallDelivery(Document doc) {
        Elements tableElements = doc.select("div");
        Iterator<Element> tables = tableElements.iterator();

        while (tables.hasNext()) {
            Element table = tables.next();
            String attributeClass = table.attributes().get("class");
            if (attributeClass != null && attributeClass.equals("order_info shipping_method")) {
                Iterator<Element> pTagsIterator = table.select("p").iterator();
                while (pTagsIterator.hasNext()) {
                    Element pTag = pTagsIterator.next();
                    if (pTag.attributes().get("class").equals("delivery")) {
                        String deliveryStr = pTag.select("span").text();
                        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я.]+([\\d.]+)$");
                        Matcher m = pattern.matcher(deliveryStr);
                        if (m.matches()) {
                            return Double.parseDouble(m.group(1));
                        }
                    }
                }
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
}
