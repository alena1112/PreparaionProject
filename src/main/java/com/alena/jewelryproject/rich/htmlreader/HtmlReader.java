package com.alena.jewelryproject.rich.htmlreader;

import com.alena.jewelryproject.model.Material;
import com.alena.jewelryproject.model.MaterialOrder;
import com.alena.jewelryproject.model.enums.Shop;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlReader {
    private static final Logger log = LoggerFactory.getLogger(HtmlReader.class);

    private String charsetName;
    private String tableTag;
    private String tableAttributeName;
    private String tableName;
    private String lineTag;
    private String columnTag;
    private int columnSize;

    public HtmlReader() {
    }

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public String getTableTag() {
        return tableTag;
    }

    public void setTableTag(String tableTag) {
        this.tableTag = tableTag;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getLineTag() {
        return lineTag;
    }

    public void setLineTag(String lineTag) {
        this.lineTag = lineTag;
    }

    public String getColumnTag() {
        return columnTag;
    }

    public void setColumnTag(String columnTag) {
        this.columnTag = columnTag;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public String getTableAttributeName() {
        return tableAttributeName;
    }

    public void setTableAttributeName(String tableAttributeName) {
        this.tableAttributeName = tableAttributeName;
    }

    public MaterialOrder parse(File file, Function<Elements, Material> parseLineFnc,
                               Function<Document, Double> parseDeliveryFnc, Shop shop) {
        log.info(String.format("Starting parse file %s", file.getName()));
        String result = parseDocument(file);
        Document doc = Jsoup.parse(result);
        Elements tableElements = doc.select(tableTag);
        Iterator<Element> tables = tableElements.iterator();

        List<Material> items = new ArrayList<>();
        MaterialOrder order = new MaterialOrder();
        order.setMaterials(items);
        order.setDeliveryPrice(parseDeliveryFnc.apply(doc));
        order.setShop(shop);
        order.setPurchaseDate(getDate(file.getName()));

        while (tables.hasNext()) {
            Element table = tables.next();
            String tableAttribute = table.attributes().get(tableAttributeName);
            if (tableAttribute != null) {
                if (tableAttribute.equals(tableName)) {
                    Elements trTags = table.select(lineTag);
                    for (Element trTag : trTags) {
                        Elements tdTags = new Elements();
                        for (String tag : columnTag.split(",")) {
                            tdTags.addAll(trTag.select(tag));
                        }
                        if (tdTags.size() == columnSize && !"list_title".equals(trTag.attributes().get("class"))/*special for pandahall*/) {
                            Material item = parseLineFnc.apply(tdTags);
                            if (item != null) {
                                item.setOrder(order);
                                items.add(item);
                            }
                        }
                    }
                }
            }
        }

        log.info(String.format("File %s was successfully parsed", file.getName()));
        return order;
    }

    private String parseDocument(File file) {
        StringBuilder result = new StringBuilder();
        try (Scanner in = new Scanner(new FileInputStream(file), charsetName)) {
            while (in.hasNextLine()) {
                result.append(in.nextLine()).append("\n");
            }
        } catch (IOException e) {
            log.error(String.format("Error while parsing file %s", file.getName()), e);
        }
        return result.toString();
    }

    private Date getDate(String fileName) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+_([\\d.]+).html$");
        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            try {
                return dateFormat.parse(m.group(1));
            } catch (ParseException e) {
                log.error(String.format("Error while parsing file date %s", fileName), e);
            }
        }
        return null;
    }
}
