package com.normalsmp.freeProtect.Util;

import java.util.HashMap;
import java.util.Map;

public class ArgParser {
    public static Map<String, String> parseKeyValueArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            if (arg.contains(":")) {
                String[] parts = arg.split(":", 2);
                map.put(parts[0].toLowerCase(), parts.length > 1 ? parts[1] : "");
            }
        }
        return map;
    }
    public static long parseTimeString(String str) throws NumberFormatException {
        str = str.toLowerCase();
        if (str.endsWith("s")) return Long.parseLong(str.replace("s","")) * 1000L;
        if (str.endsWith("m")) return Long.parseLong(str.replace("m","")) * 60_000L;
        if (str.endsWith("h")) return Long.parseLong(str.replace("h","")) * 3_600_000L;
        if (str.endsWith("d")) return Long.parseLong(str.replace("d","")) * 86_400_000L;
        return Long.parseLong(str); // assume raw milliseconds
    }

}
