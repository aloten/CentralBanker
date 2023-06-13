package com.aidanloten.centralbanker.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CBUtility {

    public static String formatCurrency(double number) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(number);
    }

    public static int getRandomIntId() {
        return getRandomIntInRange(1, 1000000000);
    }

    public static int getRandomIntInRange(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
