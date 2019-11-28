package com.alena.preparationproject.admin.htmlreader;

import com.bijou.web.model.JewelryItem;
import com.bijou.web.model.JewelryOrder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.lang.Nullable;

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

    @Nullable
    public JewelryOrder parse(File file, Function<Elements, JewelryItem> parseLineFnc,
                              Function<Document, Double> parseDeliveryFnc, String shopName) {
        String result = parseDocument(file);
        Document doc = Jsoup.parse(result);
        Elements tableElements = doc.select(tableTag);
        Iterator<Element> tables = tableElements.iterator();

        List<JewelryItem> items = new ArrayList<>();
        JewelryOrder order = new JewelryOrder(items, parseDeliveryFnc.apply(doc), shopName, getDate(file.getName()));

        while (tables.hasNext()) {
            Element table = tables.next();
            String tableAttribute = table.attributes().get(tableAttributeName);
            if (tableAttribute != null) {
                if (tableAttribute.equals(tableName)) {
                    Elements trTags = table.select(lineTag);
                    for (Element trTag : trTags) {
                        Elements tdTags = trTag.select(columnTag);
                        if (tdTags.size() == columnSize && !"list_title".equals(trTag.attributes().get("class"))/*special for pandahall*/) {
                            JewelryItem item = parseLineFnc.apply(tdTags);
                            if (item != null) {
                                items.add(item);
                            }
                        }
                    }
                }
            }
        }

        return order;
    }

    private String parseDocument(File file) {
        StringBuilder result = new StringBuilder();
        try (Scanner in = new Scanner(new FileInputStream(file), charsetName)) {
            while (in.hasNextLine()) {
                result.append(in.nextLine()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private Date getDate(String fileName) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+_([\\d.]+).html$");
        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
            try {
                return dateFormat.parse(m.group(1));
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }
}
