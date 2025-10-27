package com.aliyun.vod.common.utils;

import android.net.Uri;

/* loaded from: classes2.dex */
public class UriUtil {
    public static final String ASSETS = "assets";
    public static final String FILE = "file";
    public static final String MULI_SPLIT = ",";
    public static final String PROVIDER = "content";
    public static final String QUERY_CATEGORY = "category";
    public static final String QUERY_ID = "id";
    public static final String QUERY_TYPE = "type";
    public static final String QUPAI_ASSETS = "qupai-assets";

    public static String formatAssetURI(int i2, int i3) {
        return String.format("%s://?type=%d&id=%d", QUPAI_ASSETS, Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static String formatProvideURI(int[] iArr, String str) {
        StringBuilder sb = new StringBuilder();
        for (int i2 : iArr) {
            sb.append(i2);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return String.format("%s://%s?category=%s", "content", str, sb.toString());
    }

    public static boolean getQueryB(Uri uri, String str, boolean z2) {
        String queryParameter = uri.getQueryParameter(str);
        if (queryParameter == null) {
            return z2;
        }
        try {
            return Boolean.parseBoolean(queryParameter);
        } catch (Throwable unused) {
            return z2;
        }
    }

    public static <E extends Enum<E>> E getQueryE(Uri uri, String str, E e2) {
        if (uri.getQueryParameter(str) == null) {
            return e2;
        }
        try {
            return (E) Enum.valueOf(e2.getDeclaringClass(), str);
        } catch (Throwable unused) {
            return e2;
        }
    }

    public static int getQueryI(Uri uri, String str, int i2) {
        String queryParameter = uri.getQueryParameter(str);
        if (queryParameter == null) {
            return i2;
        }
        try {
            return Integer.parseInt(queryParameter);
        } catch (Throwable unused) {
            return i2;
        }
    }

    public static int[] getQueryIA(Uri uri) {
        String queryParameter = uri.getQueryParameter(QUERY_CATEGORY);
        if (queryParameter == null) {
            return null;
        }
        String[] strArrSplit = queryParameter.split(",");
        int[] iArr = new int[strArrSplit.length];
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            iArr[i2] = Integer.parseInt(strArrSplit[i2]);
        }
        return iArr;
    }

    public static long getQueryL(Uri uri, String str, long j2) {
        String queryParameter = uri.getQueryParameter(str);
        if (queryParameter == null) {
            return j2;
        }
        try {
            return Long.parseLong(queryParameter);
        } catch (Throwable unused) {
            return j2;
        }
    }
}
