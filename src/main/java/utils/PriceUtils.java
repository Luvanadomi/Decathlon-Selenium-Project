package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceUtils {

    private static final Pattern PRICE_PATTERN = Pattern.compile("\\$?(\\d{1,3}(?:,\\d{3})*(?:\\.\\d{2})?)");

    private PriceUtils() {}

    public static Double extractPrice(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            return null;
        }
        Matcher matcher = PRICE_PATTERN.matcher(rawText.trim());
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1).replace(",", ""));
        }
        return null;
    }
}