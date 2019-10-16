package com.alena.preparationproject.multithreading;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DigestV2 implements Digest {
    private Map<String, String> cash;

    @Override
    public String doTask(String key) {
        String result = cash.get(key);//возможное решение проблемы get и put
        if (result == null) {
            result = cash.putIfAbsent(key, doDigest(key));
        }
        return result;
    }

    @Override
    public Map<String, String> getCash() {
        if (cash == null) {
            this.cash = new ConcurrentHashMap<>();//можно без блокировок использовать эту мапу,
                                                // но поскольку у ConcurrentHashMap на время чтения не происходит блокировки
                                                // один поток может добавлять значение, когда как другой поток
                                                // после этого может просто перезаписать его
        }
        return cash;
    }
}
