package com.alibaba.sdk.android.httpdns.e;

import com.alibaba.sdk.android.httpdns.RequestIpType;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: com.alibaba.sdk.android.httpdns.e.d$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: d, reason: collision with root package name */
        static final /* synthetic */ int[] f2735d;

        static {
            int[] iArr = new int[RequestIpType.values().length];
            f2735d = iArr;
            try {
                iArr[RequestIpType.v6.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2735d[RequestIpType.both.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static com.alibaba.sdk.android.httpdns.g.d a(com.alibaba.sdk.android.httpdns.d.d dVar, String str, RequestIpType requestIpType, Map<String, String> map, String str2, Map<String, String> map2, com.alibaba.sdk.android.httpdns.d.i iVar) {
        HashMap map3;
        if (str2 != null) {
            map3 = new HashMap();
            if (map2 != null) {
                map3.putAll(map2);
            }
            if (map != null) {
                map3.putAll(map);
            }
        } else {
            map3 = null;
        }
        return new com.alibaba.sdk.android.httpdns.g.d(dVar.c(), dVar.d(), dVar.getPort(), a(dVar, str, requestIpType, map3, iVar.a(str)), dVar.getTimeout());
    }

    public static com.alibaba.sdk.android.httpdns.g.d a(com.alibaba.sdk.android.httpdns.d.d dVar, ArrayList<String> arrayList, RequestIpType requestIpType, com.alibaba.sdk.android.httpdns.d.i iVar) {
        String strA = a(requestIpType);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (i2 != 0) {
                sb.append(",");
            }
            sb.append(arrayList.get(i2));
        }
        String string = sb.toString();
        HashMap<String, String> mapA = iVar.a(string);
        return new com.alibaba.sdk.android.httpdns.g.d(dVar.c(), dVar.d(), dVar.getPort(), "/" + dVar.getAccountId() + "/" + ((mapA == null || !mapA.keySet().contains("s")) ? "resolve" : "sign_resolve") + "?host=" + string + "&sdk=android_2.0.4" + strA + f() + g() + a(mapA), dVar.getTimeout());
    }

    private static String a(RequestIpType requestIpType) {
        int i2 = AnonymousClass1.f2735d[requestIpType.ordinal()];
        return i2 != 1 ? i2 != 2 ? "" : "&query=4,6" : "&query=6";
    }

    public static String a(com.alibaba.sdk.android.httpdns.d.d dVar, String str, RequestIpType requestIpType, Map<String, String> map, HashMap<String, String> map2) {
        String strA;
        String strA2 = a(requestIpType);
        try {
            strA = a(map);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            strA = null;
        }
        String strA3 = a(map2);
        return "/" + dVar.getAccountId() + "/" + ((map2 == null || !map2.keySet().contains("s")) ? "d" : "sign_d") + "?host=" + str + "&sdk=android_2.0.4" + strA2 + f() + g() + strA + strA3;
    }

    private static String a(HashMap<String, String> map) {
        if (map == null || map.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("&");
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    private static String a(Map<String, String> map) {
        boolean z2;
        boolean z3;
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (true) {
                z2 = true;
                if (!it.hasNext()) {
                    z3 = true;
                    break;
                }
                Map.Entry<String, String> next = it.next();
                sb.append("&sdns-");
                sb.append(next.getKey());
                sb.append("=");
                sb.append(URLEncoder.encode(next.getValue(), "UTF-8"));
                z3 = false;
                if (!b(next.getKey())) {
                    HttpDnsLog.e("设置自定义参数失败，自定义key不合法：" + next.getKey());
                    z3 = true;
                    z2 = false;
                    break;
                }
                if (!c(next.getValue())) {
                    HttpDnsLog.e("设置自定义参数失败，自定义value不合法：" + next.getValue());
                    break;
                }
            }
            if (z2 && z3) {
                String string = sb.toString();
                if (string.getBytes("UTF-8").length <= 1000) {
                    return string;
                }
                HttpDnsLog.e("设置自定义参数失败，自定义参数过长");
            }
        }
        return "";
    }

    private static boolean b(String str) {
        return str.matches("[a-zA-Z0-9\\-_]+");
    }

    private static boolean c(String str) {
        return str.matches("[a-zA-Z0-9\\-_=]+");
    }

    public static String f() {
        String sessionId = com.alibaba.sdk.android.httpdns.i.a.a().getSessionId();
        if (sessionId == null) {
            return "";
        }
        return "&sid=" + sessionId;
    }

    public static String g() {
        String strG = com.alibaba.sdk.android.httpdns.net.c.a().g();
        if (strG == null) {
            return "";
        }
        return "&net=" + strG;
    }
}
