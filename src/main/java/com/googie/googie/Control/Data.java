package com.googie.googie.Control;

import java.util.HashMap;
import java.util.List;

public class Data {
    public static HashMap<String, List<String>> results = new HashMap<>();
    public static List<String> titles() {
        return results.keySet().stream().toList();
    }
}
