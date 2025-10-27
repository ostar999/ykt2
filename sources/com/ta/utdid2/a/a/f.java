package com.ta.utdid2.a.a;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static final Pattern f17238a = Pattern.compile("([\t\r\n])+");

    public static int a(String str) {
        if (str.length() <= 0) {
            return 0;
        }
        int i2 = 0;
        for (char c3 : str.toCharArray()) {
            i2 = (i2 * 31) + c3;
        }
        return i2;
    }

    public static boolean b(String str) {
        return str == null || str.length() <= 0;
    }

    public static String f(String str) {
        return (str == null || "".equals(str)) ? str : f17238a.matcher(str).replaceAll("");
    }

    public static Map<String, String> a(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        TreeMap treeMap = new TreeMap(new Comparator<String>() { // from class: com.ta.utdid2.a.a.f.1
            @Override // java.util.Comparator
            public int compare(String str, String str2) {
                return str.compareTo(str2);
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }
}
