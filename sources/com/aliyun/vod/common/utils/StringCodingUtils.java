package com.aliyun.vod.common.utils;

import java.nio.charset.Charset;

/* loaded from: classes2.dex */
public class StringCodingUtils {
    public static byte[] getBytes(String str, Charset charset) {
        return str.getBytes(charset);
    }
}
