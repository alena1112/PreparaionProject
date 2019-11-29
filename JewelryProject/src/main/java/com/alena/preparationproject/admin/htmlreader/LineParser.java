package com.alena.preparationproject.admin.htmlreader;

import com.alena.preparationproject.admin.model.JewelryItem;
import com.alena.preparationproject.admin.model.JewelryName;
import org.jsoup.select.Elements;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

    public static Function<Elements, JewelryItem> parseLine(Shops shop) {
        switch (shop) {
            case GREEN_BIRD:
                return LineParser::parseGreenBirdLine;
            case PANDAHALL:
                return LineParser::parsePandahallLine;
            case STILNAYA:
                return LineParser::parseStilnayaLine;
            case LUXFURNITURA:
                return LineParser::parseLuxfurnituraLine;
        }
        return null;
    }

    private static JewelryItem parseGreenBirdLine(Elements tags) {
        JewelryName name;
        int numberInPackage = 1;
        Pattern pattern = Pattern.compile("^\\[([\\w-]+)\\]\\s([\\w\\sа-яА-Я-\"#&quot;,]+)(,\\s(\\d)\\sшт.|,\\sпара)*\\s\\(([\\w#-]+)\\)$");
        Matcher m = pattern.matcher(tags.get(1).text());
        if (m.matches()) {
            name = new JewelryName(m.group(1), m.group(2));
            if (m.group(4) != null) {
                numberInPackage = Integer.parseInt(m.group(4));
            }
        } else {
            throw new RegexPatternNotCorrectException(pattern.toString(), Shops.GREEN_BIRD.getId(), tags.get(1).text());
        }
        return new JewelryItem(name,
                Integer.parseInt(tags.get(2).text()) * numberInPackage,
                Double.parseDouble(tags.get(3).text()));
    }

    private static JewelryItem parsePandahallLine(Elements tags) {
        String code = tags.get(1).text();
        String nameStr = tags.get(2).text();
        int numberInPackage = 1;
        if (nameStr.contains(";")) {
            Pattern pattern = Pattern.compile("^([\\wа-яА-Я-,~:.&\\s]+);\\s[a-zA-Zа-яА-Я\\s]*(\\d+)[\\wа-яА-Я\"',.~\\s\\/\\(\\)]+$");
            Matcher m = pattern.matcher(nameStr);
            if (m.matches()) {
                numberInPackage = Integer.parseInt(m.group(2));
            } else {
                throw new RegexPatternNotCorrectException(pattern.toString(), Shops.PANDAHALL.getId(), nameStr);
            }
        }
        int packageSize = 1;
        Pattern pattern = Pattern.compile("^(\\d+)\\s[\\wа-яА-Я]+$");
        Matcher m = pattern.matcher(tags.get(3).text());
        if (m.matches()) {
            packageSize = Integer.parseInt(m.group(1));
        } else {
            throw new RegexPatternNotCorrectException(pattern.toString(), Shops.PANDAHALL.getId(), tags.get(3).text());
        }
        double price = 0;
        pattern = Pattern.compile("^[A-Za-zа-яА-Я.]+([\\d,.]+)$");
        m = pattern.matcher(tags.get(7).text());
        if (m.matches()) {
            price = Double.parseDouble(m.group(1).replace(",", ""));
        } else {
            throw new RegexPatternNotCorrectException(pattern.toString(), Shops.PANDAHALL.getId(), tags.get(7).text());
        }
        int orderQty = Integer.parseInt(tags.get(5).text());

        JewelryName name = new JewelryName(code, nameStr);
        return new JewelryItem(name,
                orderQty * numberInPackage * packageSize,
                price / orderQty);
    }

    private static JewelryItem parseStilnayaLine(Elements tags) {
        JewelryName name;
        int numberInPackage = 1;
        String text = tags.get(1).text();
        if (text.contains("шт.") || text.contains("пара")) {
            Pattern pattern = Pattern.compile("^[\\wа-яА-Я\\s\\(\\)*,.-]+(?:,\\s(\\d+)\\sшт.|пара)+$");
            Matcher m = pattern.matcher(text);
            if (m.matches()) {
                name = new JewelryName(tags.get(0).text(), tags.get(1).text());
                String number = m.group(1);
                if (number != null) {
                    numberInPackage = Integer.parseInt(number);
                } else if (text.contains("пара")) {
                    numberInPackage = 2;
                }
            } else {
                throw new RegexPatternNotCorrectException(pattern.toString(), Shops.STILNAYA.getId(), tags.get(1).text());
            }
        } else {
            name = new JewelryName(tags.get(0).text(), text);
        }
        double price;
        Pattern pattern = Pattern.compile("^([\\d,]+)\\s[A-Za-zа-яА-Я]+$");
        Matcher m = pattern.matcher(tags.get(6).text());
        if (m.matches()) {
            price = Double.parseDouble(m.group(1).replace(",", "."));
        } else {
            throw new RegexPatternNotCorrectException(pattern.toString(), Shops.STILNAYA.getId(), tags.get(6).text());
        }
        return new JewelryItem(name,
                Integer.parseInt(tags.get(4).text()) * numberInPackage,
                price);
    }

    private static JewelryItem parseLuxfurnituraLine(Elements tags) {
        String imageURL = tags.get(0).getElementsByAttribute("src").attr("src");
        if (imageURL != null && !imageURL.isEmpty()) {
            String nameStr = tags.get(1).text();
            int numberInPackage = 1;
//        Pattern pattern = Pattern.compile("^([\\wа-яА-Я-,~:.&\\s]+);\\s[a-zA-Zа-яА-Я\\s]*(\\d+)[\\wа-яА-Я\"',.~\\s\\/\\(\\)]+$");
//        Matcher m = pattern.matcher(nameStr);
//        if (m.matches()) {
//            numberInPackage = Integer.parseInt(m.group(2));
//        } else {
//            throw new RegexPatternNotCorrectException(pattern.toString(), Shops.LUXFURNITURA.getId(), nameStr);
//        }
            int packageSize = 1;
            Pattern pattern = Pattern.compile("^×\\p{Z}(\\d+)\\p{Z}[а-яА-Я]+$");
            Matcher m = pattern.matcher(tags.get(3).text());
            if (m.matches()) {
                packageSize = Integer.parseInt(m.group(1));
            } else {
                throw new RegexPatternNotCorrectException(pattern.toString(), Shops.LUXFURNITURA.getId(), tags.get(3).text());
            }
            double price = 0;
            pattern = Pattern.compile("^([\\d\\p{Z}]+)\\p{Z}[а-яА-Я]+$");
            m = pattern.matcher(tags.get(2).text());
            if (m.matches()) {
                price = Double.parseDouble(m.group(1).replace(" ", ""));
            } else {
                throw new RegexPatternNotCorrectException(pattern.toString(), Shops.LUXFURNITURA.getId(), tags.get(2).text());
            }

            JewelryName name = new JewelryName(null, nameStr);
            return new JewelryItem(name, imageURL,
                    numberInPackage * packageSize,
                    price / numberInPackage);
        }
        return null;
    }
}
