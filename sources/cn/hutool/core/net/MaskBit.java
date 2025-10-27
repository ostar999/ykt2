package cn.hutool.core.net;

import cn.hutool.core.map.BiMap;
import java.util.HashMap;

/* loaded from: classes.dex */
public class MaskBit {
    private static final BiMap<Integer, String> MASK_BIT_MAP;

    static {
        BiMap<Integer, String> biMap = new BiMap<>(new HashMap(32));
        MASK_BIT_MAP = biMap;
        biMap.put(1, "128.0.0.0");
        biMap.put(2, "192.0.0.0");
        biMap.put(3, "224.0.0.0");
        biMap.put(4, "240.0.0.0");
        biMap.put(5, "248.0.0.0");
        biMap.put(6, "252.0.0.0");
        biMap.put(7, "254.0.0.0");
        biMap.put(8, "255.0.0.0");
        biMap.put(9, "255.128.0.0");
        biMap.put(10, "255.192.0.0");
        biMap.put(11, "255.224.0.0");
        biMap.put(12, "255.240.0.0");
        biMap.put(13, "255.248.0.0");
        biMap.put(14, "255.252.0.0");
        biMap.put(15, "255.254.0.0");
        biMap.put(16, "255.255.0.0");
        biMap.put(17, "255.255.128.0");
        biMap.put(18, "255.255.192.0");
        biMap.put(19, "255.255.224.0");
        biMap.put(20, "255.255.240.0");
        biMap.put(21, "255.255.248.0");
        biMap.put(22, "255.255.252.0");
        biMap.put(23, "255.255.254.0");
        biMap.put(24, "255.255.255.0");
        biMap.put(25, "255.255.255.128");
        biMap.put(26, "255.255.255.192");
        biMap.put(27, "255.255.255.224");
        biMap.put(28, "255.255.255.240");
        biMap.put(29, "255.255.255.248");
        biMap.put(30, "255.255.255.252");
        biMap.put(31, "255.255.255.254");
        biMap.put(32, "255.255.255.255");
    }

    public static String get(int i2) {
        return MASK_BIT_MAP.get(Integer.valueOf(i2));
    }

    public static Integer getMaskBit(String str) {
        return MASK_BIT_MAP.getKey(str);
    }
}
