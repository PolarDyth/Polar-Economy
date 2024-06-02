package me.polardyth.polareconomy.placeholders;

import java.text.NumberFormat;

public class Format {

    public static String formatCurrencyUnits(long amount) {
        if (amount < 1000) {
            return Long.toString(amount);
        } else if (amount < 1000000) {
            return String.format("%.1fk", Math.floor(amount / 1000.0));
        } else if (amount < 1000000000) {
            return String.format("%.1fm", Math.floor(amount / 1000000.0));
        } else {
            return String.format("%.1fb", Math.floor(amount / 1000000000.0));
        }
    }

    public static String formatCurrency(long amount) {
        return NumberFormat.getInstance().format(amount);
    }

    public static String formatTime(long timeInSeconds) {
        long days = timeInSeconds / 86400;
        long hours = (timeInSeconds % 86400) / 3600;
        long minutes = (timeInSeconds % 3600) / 60;
        long seconds = timeInSeconds % 60;

        StringBuilder time = new StringBuilder();
        if (days > 0) {
            time.append(days).append(" days, ");
        }
        if (hours > 0) {
            time.append(hours).append(" hours, ");
        }
        if (minutes > 0) {
            time.append(minutes).append(" minutes, ");
        }
        if (seconds > 0) {
            time.append(seconds).append(" seconds");
        }

        // Remove trailing comma and space if present
        if (time.length() > 0 && time.charAt(time.length() - 2) == ',') {
            time.setLength(time.length() - 2);
        }

        return time.toString();
    }
}
