package com.alena.jewelryproject.admin.htmlreader;

public enum Shops {
    GREEN_BIRD("greenBird"),
    PANDAHALL("pandahall"),
    STILNAYA("stilnaya"),
    LUXFURNITURA("luxfurnitura");

    private String id;

    Shops(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Shops getShopById(String id) {
        for (Shops shop : Shops.values()) {
            if (shop.getId().equals(id)) {
                return shop;
            }
        }
        return null;
    }


}
