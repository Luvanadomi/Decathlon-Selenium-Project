package utils;

import java.util.List;

public class CollectionUtils {

    public static boolean isAscending(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return false;
        }

        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i) > values.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDescending(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return false;
        }

        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i) < values.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}