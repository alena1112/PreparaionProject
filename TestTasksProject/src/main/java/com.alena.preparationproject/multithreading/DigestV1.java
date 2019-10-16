package com.alena.preparationproject.multithreading;

import java.util.HashMap;
import java.util.Map;

public class DigestV1 implements Digest {
    private Map<String, String> cash;

    @Override
    public String doTask(String key) {
        String result = cash.get(key);//проверка нужна для исключения блокировки всей мапы, если ключ уже есть в ней
        if (result == null) {
            synchronized (cash) {//блокировка на объекте кеша. При попытке добавления значения в кеш код всегда будет последовательным
                result = cash.get(key);
                if (result == null) {
                    result = doDigest(key);//а если еще doDigest будет долго работать...
                    cash.put(key, result);
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, String> getCash() {
        if (cash == null) {
            this.cash = new HashMap<>();
        }
        return cash;
    }
}
