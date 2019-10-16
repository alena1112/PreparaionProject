package com.alena.preparationproject.multithreading;

import java.util.Map;

public interface Digest extends ThreadFunction<String> {
    //реализация порождающего шаблона - Фабричный метод
    Map<String, String> getCash();

    default String doDigest(String digest) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return digest + "!";
    }

    default String getResult() {
        StringBuilder builder = new StringBuilder();
        getCash().forEach((key, value) ->
                builder.append(key)
                        .append(" ")
                        .append(value)
                        .append("; "));
        return builder.toString();
    }
}
