package com.lib.utils;

import static java.util.Objects.isNull;

public class ValueHelper {
    public static <T> T getOrDefault(T value, T defaultValue) {
        return isNull(value) ? defaultValue : value;
    }
}
