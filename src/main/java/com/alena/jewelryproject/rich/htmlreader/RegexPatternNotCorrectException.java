package com.alena.jewelryproject.rich.htmlreader;

public class RegexPatternNotCorrectException extends RuntimeException {
    private String pattern;
    private String shop;
    private String line;

    public RegexPatternNotCorrectException(String pattern, String shop, String line) {
        this.pattern = pattern;
        this.shop = shop;
        this.line = line;
    }

    @Override
    public String getMessage() {
        return String.format("Pattern %s is not correct for shop %s, line: %s", pattern, shop, line);
    }
}
