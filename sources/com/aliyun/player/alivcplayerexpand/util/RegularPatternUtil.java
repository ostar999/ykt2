package com.aliyun.player.alivcplayerexpand.util;

/* loaded from: classes2.dex */
public class RegularPatternUtil {
    public static boolean isNumber(String str) {
        return str.matches("^(\\-|\\+)?\\d+(\\.\\d+)?$");
    }
}
