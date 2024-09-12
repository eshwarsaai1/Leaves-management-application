package com.wavemaker.leavemanager.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CookieStore {
    private static final Map<String, Integer> TOKEN_MAP = new ConcurrentHashMap<>();

    public static void add(String token, int value) {
        TOKEN_MAP.put(token, value);
    }

    public static boolean has(String token) {
        return TOKEN_MAP.containsKey(token);
    }

    public static int get(String token) {
        return TOKEN_MAP.get(token);
    }

    public static void delete(String token) {
        TOKEN_MAP.remove(token);
    }
}
