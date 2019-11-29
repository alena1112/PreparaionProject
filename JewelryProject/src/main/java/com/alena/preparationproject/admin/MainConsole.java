package com.alena.preparationproject.admin;

import com.alena.preparationproject.admin.htmlreader.*;
import com.alena.preparationproject.admin.model.JewelryItem;
import com.alena.preparationproject.admin.model.JewelryOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainConsole {

    public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
            Calculator calculator = (Calculator) context.getBean("calculator");

            ClassLoader classLoader = MainConsole.class.getClassLoader();
            File dirFile = new File("C:/Users/KovalenkoAI/Desktop/Личное/PreparaionProject/JewelryProject/src/main/java/com/alena/preparationproject/admin/html");

            for (File dir : dirFile.listFiles()) {
                Shops shop = Shops.getShopById(dir.getName());
                if (shop != null) {
                    HtmlReader htmlReader = (HtmlReader) context.getBean(shop.getId() + "Parser");
                    JewelryOrder order;
                    for (File file : dir.listFiles()) {
                        order = htmlReader.parse(file, LineParser.parseLine(shop), DeliveryCostParser.parseDelivery(shop),
                                shop.name());
                        calculator.calculate(order);
                        if (order != null) {
                            System.out.print(generateConsoleOutputString(order));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateFirstLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("Название")
                .append(generateSpaces(140 - 8 + 4))
                .append("Кол.")
                .append(generateSpaces(5 - 4 + 4))
                .append("Стоимость")
                .append(generateSpaces(8 - 9 + 4))
                .append("Цена за ед.");
        return sb.toString();
    }

    private static String generateConsoleOutputString(JewelryOrder order) {
        StringBuilder sb = new StringBuilder();
        sb.append(order.getShopName())
                .append(" ")
                .append(new SimpleDateFormat("dd.mm.yyyy").format(order.getDate()))
                .append("\n");
        sb.append(generateFirstLine())
                .append("\n");
        for (JewelryItem item : order.getItems()) {
            sb.append(item.getJewelryName().getName())
                    .append(generateSpaces(140 - item.getJewelryName().getName().length() + 4))
                    .append(item.getNumber())
                    .append(generateSpaces(5 - String.valueOf(item.getNumber()).length() + 4))
                    .append(item.getPrice())
                    .append(generateSpaces(8 - String.valueOf(item.getPrice()).length() + 4))
                    .append((double) Math.round(item.getPriceWithDelivery() * 100d) / 100d);
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    private static String generateSpaces(int number) {
        return String.format("%-" + number + "s", " ");
    }

    public static void marshaller(JewelryOrder order, String fileName) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(JewelryOrder.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        ClassLoader classLoader = MainConsole.class.getClassLoader();
        File file = new File(classLoader.getResource("html").getPath() + "/" +
                fileName.replace("html", "xml"));

        jaxbMarshaller.marshal(order, file);
    }

    public static JewelryOrder unmarshalling(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(JewelryOrder.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (JewelryOrder) jaxbUnmarshaller.unmarshal(file);
    }

    private static Map<String, File> getStorageFiles() {
        List<File> files = new ArrayList<>();
        ClassLoader classLoader = MainConsole.class.getClassLoader();
        URL url = classLoader.getResource("xml");
        if (url != null) {
            String storageDir = url.getFile();
            if (storageDir != null) {
                File storageDirFile = new File(storageDir);
                File[] storageShopDirs = storageDirFile.listFiles();
                if (storageShopDirs != null) {
                    for (File storageShopDir : storageShopDirs) {
                        Shops storageShop = Shops.getShopById(storageShopDir.getName());
                        if (storageShop != null) {
                            File[] storageFiles = storageShopDir.listFiles();
                            if (storageFiles != null) {
                                files.addAll(Arrays.asList(storageFiles));
                            }
                        }
                    }
                }
            }
        }
        return files.stream().collect(Collectors.toMap(File::getName, x -> x));
    }
}
