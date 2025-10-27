package com.google.zxing.common;

import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING;
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static {
        String strName = Charset.defaultCharset().name();
        PLATFORM_DEFAULT_ENCODING = strName;
        ASSUME_SHIFT_JIS = SHIFT_JIS.equalsIgnoreCase(strName) || EUC_JP.equalsIgnoreCase(strName);
    }

    private StringUtils() {
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0052 A[PHI: r10
      0x0052: PHI (r10v6 int) = (r10v1 int), (r10v5 int), (r10v1 int) binds: [B:32:0x005d, B:41:0x0076, B:26:0x0050] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String guessEncoding(byte[] r19, java.util.Map<com.google.zxing.DecodeHintType, ?> r20) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.StringUtils.guessEncoding(byte[], java.util.Map):java.lang.String");
    }
}
