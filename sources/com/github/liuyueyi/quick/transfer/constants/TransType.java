package com.github.liuyueyi.quick.transfer.constants;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public enum TransType {
    SIMPLE_TO_TRADITIONAL("s2t"),
    SIMPLE_TO_HONGKONG("s2hk"),
    SIMPLE_TO_TAIWAN("s2tw"),
    TRADITIONAL_TO_SIMPLE("t2s"),
    HONGKONG_TO_SIMPLE("hk2s"),
    TAIWAN_TO_SIMPLE("tw2s");

    static Map<String, TransType> typeMap = new HashMap(8, 1.0f);
    private final String type;

    static {
        for (TransType transType : values()) {
            typeMap.put(transType.getType(), transType);
        }
    }

    TransType(String str) {
        this.type = str;
    }

    public static TransType typeOf(String str) {
        return typeMap.get(str);
    }

    public String getType() {
        return this.type;
    }
}
