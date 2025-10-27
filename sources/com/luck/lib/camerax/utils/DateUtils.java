package com.luck.lib.camerax.utils;

import java.text.SimpleDateFormat;

/* loaded from: classes4.dex */
public class DateUtils {
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static String getCreateFileName(String str) {
        return str + sf.format(Long.valueOf(System.currentTimeMillis()));
    }
}
