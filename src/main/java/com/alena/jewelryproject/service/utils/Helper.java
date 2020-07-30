package com.alena.jewelryproject.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static Formula parseChangeMoneyFormula(String formulaStr) {
        Pattern pattern = Pattern.compile("^([+\\-*\\/])(\\d+)(%*)$");
        Matcher m = pattern.matcher(formulaStr);
        if (m.matches()) {
            return new Formula(m.group(3).equals("%"), m.group(1), Integer.parseInt(m.group(2)));
        }
        return null;
    }
}
