package com.alibaba.sdk.android.httpdns.j;

import android.content.Context;
import android.text.Html;
import android.util.Pair;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.text.StrPool;
import com.alibaba.sdk.android.httpdns.RequestIpType;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static Pattern f2811a = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

    /* renamed from: com.alibaba.sdk.android.httpdns.j.a$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {

        /* renamed from: d, reason: collision with root package name */
        static final /* synthetic */ int[] f2812d;

        static {
            int[] iArr = new int[RequestIpType.values().length];
            f2812d = iArr;
            try {
                iArr[RequestIpType.v4.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2812d[RequestIpType.v6.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static String a(Context context, String str) {
        try {
            return context.getResources().getString(b(context, str));
        } catch (Exception unused) {
            HttpDnsLog.w("AMSConfigUtils " + str + " is NULL");
            return null;
        }
    }

    public static String a(String str, RequestIpType requestIpType, String str2) {
        if (requestIpType != RequestIpType.both) {
            return b(str, requestIpType, str2);
        }
        throw new IllegalArgumentException("type can not be both");
    }

    public static String a(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (i2 != 0) {
                sb.append(",&#");
            }
            sb.append(iArr[i2]);
        }
        return sb.toString();
    }

    public static String a(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (i2 != 0) {
                sb.append(",&#");
            }
            sb.append(strArr[i2]);
        }
        return sb.toString();
    }

    public static Map<String, String> a(String str) {
        HashMap map = new HashMap();
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(Html.fromHtml(Html.fromHtml(str).toString()).toString());
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.get(next) == null ? null : jSONObject.get(next).toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }

    public static boolean a(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        return equals(str, str2);
    }

    public static String[] a(String[] strArr, int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            arrayList.add(new Pair(strArr[i2], Integer.valueOf(iArr[i2])));
        }
        Collections.sort(arrayList, new Comparator<Pair<String, Integer>>() { // from class: com.alibaba.sdk.android.httpdns.j.a.1
            @Override // java.util.Comparator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(Pair<String, Integer> pair, Pair<String, Integer> pair2) {
                return ((Integer) pair.second).intValue() - ((Integer) pair2.second).intValue();
            }
        });
        String[] strArr2 = new String[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            strArr2[i3] = (String) ((Pair) arrayList.get(i3)).first;
        }
        return strArr2;
    }

    private static int b(Context context, String str) {
        return context.getResources().getIdentifier(str, TypedValues.Custom.S_STRING, context.getPackageName());
    }

    public static String b(String str) {
        return str == null ? "" : str;
    }

    public static String b(String str, RequestIpType requestIpType, String str2) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        int i2 = AnonymousClass2.f2812d[requestIpType.ordinal()];
        if (i2 != 1) {
            sb.append(":");
            str3 = i2 != 2 ? "both" : "v6";
        } else {
            sb.append(":");
            str3 = "v4";
        }
        sb.append(str3);
        if (str2 != null) {
            sb.append(":");
            sb.append(str2);
        }
        return sb.toString();
    }

    public static String b(Map<String, String> map) {
        if (map == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(StrPool.BRACKET_START);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(",");
        }
        sb.append(StrPool.BRACKET_END);
        return sb.toString();
    }

    /* renamed from: b, reason: collision with other method in class */
    public static int[] m55b(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            return new int[0];
        }
        String[] strArrSplit = str.split(",&#");
        int[] iArr = new int[strArrSplit.length];
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            try {
                iArr[i2] = Integer.parseInt(strArrSplit[i2]);
            } catch (Throwable unused) {
                return null;
            }
        }
        return iArr;
    }

    /* renamed from: b, reason: collision with other method in class */
    public static String[] m56b(String str) {
        if (str == null) {
            return null;
        }
        return str.equals("") ? new String[0] : str.split(",&#");
    }

    public static String c(Context context) {
        return a(context, "ams_accountId");
    }

    public static String c(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(str.getBytes());
        byte[] bArrDigest = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArrDigest) {
            String hexString = Integer.toHexString(b3 & 255);
            while (hexString.length() < 2) {
                hexString = "0" + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String d(Context context) {
        return a(context, "ams_httpdns_secretKey");
    }

    public static String d(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return str.split(":")[0];
    }

    /* renamed from: d, reason: collision with other method in class */
    public static boolean m57d(String str) {
        if (str != null) {
            try {
                char[] charArray = str.toCharArray();
                if (charArray.length > 0 && charArray.length <= 255) {
                    for (char c3 : charArray) {
                        if ((c3 < 'A' || c3 > 'Z') && ((c3 < 'a' || c3 > 'z') && !((c3 >= '0' && c3 <= '9') || c3 == '.' || c3 == '-'))) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    public static boolean e(String str) {
        return str != null && str.length() >= 7 && str.length() <= 15 && !str.equals("") && f2811a.matcher(str).matches();
    }

    public static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static String toString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
