package me.polardyth.polareconomy.utils;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class MessageUtil {

    // Standard Error Messages
    private static String permissionErrorMessage;
    private static String notNumberMessage;
    private static String negativeNumberMessage;
    private static String playerNotFoundMessage;
    private static String notPlayerMessage;
    private static String tooManyDecimalsMessage;

    // Balance command messages
    private static String balanceMessage;

    // Pay command messages
    private static String selfPaymentErrorMessage;
    private static String paymentSuccessMessage;
    private static String paymentReceivedMessage;
    private static String insufficientFundsMessage;

    // SetBalance command messages
    private static String successToPlayerMessage;
    private static boolean sendSuccessMessageToTarget;
    private static String successToTargetMessage;

    // Interest Messages
    private static double interestRate;
    private static String interestMessage;
    private static int interestInterval;
    private static boolean interestEnabled;

    // List of usages
    private static final List<Map<String, String>> usages = new ArrayList<>();

    public static void loadMessages(FileConfiguration config) {
        // Standard Error Messages
        permissionErrorMessage = config.getString("error-messages.permission-error");
        notNumberMessage = config.getString("error-messages.not-number");
        negativeNumberMessage = config.getString("error-messages.negative-number");
        playerNotFoundMessage = config.getString("error-messages.player-not-found");
        notPlayerMessage = config.getString("error-messages.not-player");
        tooManyDecimalsMessage = config.getString("error-messages.too-many-decimals");

        // Balance command messages
        balanceMessage = config.getString("balance.balance-message");

        // Pay command messages
        selfPaymentErrorMessage = config.getString("pay.error.self-payment");
        paymentSuccessMessage = config.getString("pay.payment-success");
        paymentReceivedMessage = config.getString("pay.payment-received");
        insufficientFundsMessage = config.getString("pay.error.insufficient-funds");

        // SetBalance command messages
        successToPlayerMessage = config.getString("set-balance.success-to-player");
        sendSuccessMessageToTarget = config.getBoolean("set-balance.send-message-to-target");
        successToTargetMessage = config.getString("set-balance.success-to-target");

        usages.clear(); // Clear existing usages
        usages.add(Map.of("balance", config.getString("balance.usage")));
        usages.add(Map.of("pay", config.getString("pay.usage")));
        usages.add(Map.of("setbalance", config.getString("set-balance.usage")));

        // Interest Messages
        interestRate = config.getDouble("interest.rate");
        interestMessage = config.getString("interest.message");
        interestInterval = config.getInt("interest.interval");
        interestEnabled = config.getBoolean("interest.enabled");
    }

    // Standard Error Messages Getters
    public static String getPermissionErrorMessage() { return permissionErrorMessage; }
    public static String getNotNumberMessage() { return notNumberMessage; }
    public static String getNegativeNumberMessage() { return negativeNumberMessage; }
    public static String getPlayerNotFoundMessage() { return playerNotFoundMessage; }
    public static String getNotPlayerMessage() { return notPlayerMessage; }
    public static String getTooManyDecimalsMessage() { return tooManyDecimalsMessage; }

    // Balance command getters
    @Deprecated
    public static String getBalanceMessage() { return balanceMessage; }

    // Pay command getters
    @Deprecated
    public static String getSelfPaymentErrorMessage() { return selfPaymentErrorMessage; }
    @Deprecated
    public static String getInsufficientFundsMessage() { return insufficientFundsMessage; }
    @Deprecated
    public static String getPaymentSuccessMessage(String targetName, double amount) {
        return paymentSuccessMessage.replace("{target}", targetName).replace("{amount}", Double.toString(amount));
    }
    @Deprecated
    public static String getPaymentReceivedMessage(String playerName, double amount) {
        return paymentReceivedMessage.replace("{player}", playerName).replace("{amount}", Double.toString(amount));
    }

    // SetBalance command getters
    @Deprecated
    public static String getSuccessToPlayerMessage() { return successToPlayerMessage; }
    @Deprecated
    public static boolean isSendSuccessMessageToTarget() { return sendSuccessMessageToTarget; }
    @Deprecated
    public static String getSuccessToTargetMessage() { return successToTargetMessage; }

    @Deprecated
    public static String getUsages(String command) {
        for (Map<String, String> usageMap : usages) {
            if (usageMap.containsKey(command)) {
                return usageMap.get(command);
            }
        }
        return "Usage information not available.";
    }

    // Interest getters
    @Deprecated
    public static double getInterestRate() { return interestRate; }
    @Deprecated
    public static String getInterestMessage() { return interestMessage; }
    @Deprecated
    public static int getInterestInterval() { return interestInterval; }
    @Deprecated
    public static boolean isInterestEnabled() { return interestEnabled; }
}