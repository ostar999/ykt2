package com.arialyy.aria.util;

import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ALog {
    public static final boolean DEBUG = true;
    public static final int LOG_CLOSE = 8;
    public static final int LOG_DEFAULT = 3;
    public static int LOG_LEVEL = 3;
    public static final int LOG_LEVEL_ASSERT = 7;
    public static final int LOG_LEVEL_DEBUG = 3;
    public static final int LOG_LEVEL_ERROR = 6;
    public static final int LOG_LEVEL_INFO = 4;
    public static final int LOG_LEVEL_VERBOSE = 2;
    public static final int LOG_LEVEL_WARN = 5;

    public static int d(String str, String str2) {
        return println(3, str, str2);
    }

    public static int e(String str, String str2) {
        return println(6, str, str2);
    }

    public static String getExceptionString(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ExceptionDetailed:\n");
        sb.append("====================Exception Info====================\n");
        sb.append(th.toString());
        sb.append("\n");
        for (StackTraceElement stackTraceElement : th.getStackTrace()) {
            sb.append(stackTraceElement.toString());
            sb.append("\n");
        }
        Throwable cause = th.getCause();
        if (cause != null) {
            sb.append("【Caused by】: ");
            sb.append(cause.toString());
            sb.append("\n");
            for (StackTraceElement stackTraceElement2 : cause.getStackTrace()) {
                sb.append(stackTraceElement2.toString());
                sb.append("\n");
            }
        }
        sb.append("===================================================");
        return sb.toString();
    }

    public static int i(String str, String str2) {
        return println(4, str, str2);
    }

    public static void j(String str, String str2) throws JSONException {
        if (LOG_LEVEL <= 3) {
            try {
                if (str2.startsWith(StrPool.DELIM_START)) {
                    str2 = new JSONObject(str2).toString(4);
                } else if (str2.startsWith(StrPool.BRACKET_START)) {
                    str2 = new JSONArray(str2).toString(4);
                }
            } catch (JSONException unused) {
            }
            println(3, str, str2);
        }
    }

    public static void m(String str, Map map) {
        if (LOG_LEVEL <= 3) {
            Set<Map.Entry> setEntrySet = map.entrySet();
            if (setEntrySet.size() < 1) {
                d(str, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                return;
            }
            String[] strArr = new String[setEntrySet.size()];
            int i2 = 0;
            for (Map.Entry entry : setEntrySet) {
                strArr[i2] = entry.getKey() + " = " + entry.getValue() + ",\n";
                i2++;
            }
            println(3, str, Arrays.toString(strArr));
        }
    }

    private static int println(int i2, String str, String str2) {
        if (LOG_LEVEL > i2) {
            return -1;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "null";
        }
        return Log.println(i2, str, str2);
    }

    public static int v(String str, String str2) {
        return println(2, str, str2);
    }

    public static int w(String str, String str2) {
        return println(5, str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        Log.e(str, str2, th);
    }
}
