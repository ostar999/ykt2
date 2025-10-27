package com.tencent.bugly.crashreport.crash.h5;

import android.webkit.JavascriptInterface;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.inner.InnerApi;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.bb;
import com.umeng.analytics.pro.d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class H5JavaScriptInterface {

    /* renamed from: a, reason: collision with root package name */
    private static HashSet<Integer> f17394a = new HashSet<>();

    /* renamed from: b, reason: collision with root package name */
    private String f17395b = null;

    /* renamed from: c, reason: collision with root package name */
    private Thread f17396c = null;

    /* renamed from: d, reason: collision with root package name */
    private String f17397d = null;

    /* renamed from: e, reason: collision with root package name */
    private Map<String, String> f17398e = null;

    private H5JavaScriptInterface() {
    }

    private static bb a(String str) {
        String string;
        if (str != null && str.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                bb bbVar = new bb();
                String string2 = jSONObject.getString("projectRoot");
                bbVar.f17669a = string2;
                if (string2 == null) {
                    return null;
                }
                String string3 = jSONObject.getString(d.R);
                bbVar.f17670b = string3;
                if (string3 == null) {
                    return null;
                }
                String string4 = jSONObject.getString("url");
                bbVar.f17671c = string4;
                if (string4 == null) {
                    return null;
                }
                String string5 = jSONObject.getString("userAgent");
                bbVar.f17672d = string5;
                if (string5 == null) {
                    return null;
                }
                String string6 = jSONObject.getString("language");
                bbVar.f17673e = string6;
                if (string6 == null) {
                    return null;
                }
                String string7 = jSONObject.getString("name");
                bbVar.f17674f = string7;
                if (string7 == null || string7.equals("null") || (string = jSONObject.getString("stacktrace")) == null) {
                    return null;
                }
                int iIndexOf = string.indexOf("\n");
                if (iIndexOf < 0) {
                    al.d("H5 crash stack's format is wrong!", new Object[0]);
                    return null;
                }
                bbVar.f17676h = string.substring(iIndexOf + 1);
                String strSubstring = string.substring(0, iIndexOf);
                bbVar.f17675g = strSubstring;
                int iIndexOf2 = strSubstring.indexOf(":");
                if (iIndexOf2 > 0) {
                    bbVar.f17675g = bbVar.f17675g.substring(iIndexOf2 + 1);
                }
                bbVar.f17677i = jSONObject.getString("file");
                if (bbVar.f17674f == null) {
                    return null;
                }
                long j2 = jSONObject.getLong("lineNumber");
                bbVar.f17678j = j2;
                if (j2 < 0) {
                    return null;
                }
                long j3 = jSONObject.getLong("columnNumber");
                bbVar.f17679k = j3;
                if (j3 < 0) {
                    return null;
                }
                al.a("H5 crash information is following: ", new Object[0]);
                al.a("[projectRoot]: " + bbVar.f17669a, new Object[0]);
                al.a("[context]: " + bbVar.f17670b, new Object[0]);
                al.a("[url]: " + bbVar.f17671c, new Object[0]);
                al.a("[userAgent]: " + bbVar.f17672d, new Object[0]);
                al.a("[language]: " + bbVar.f17673e, new Object[0]);
                al.a("[name]: " + bbVar.f17674f, new Object[0]);
                al.a("[message]: " + bbVar.f17675g, new Object[0]);
                al.a("[stacktrace]: \n" + bbVar.f17676h, new Object[0]);
                al.a("[file]: " + bbVar.f17677i, new Object[0]);
                al.a("[lineNumber]: " + bbVar.f17678j, new Object[0]);
                al.a("[columnNumber]: " + bbVar.f17679k, new Object[0]);
                return bbVar;
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static H5JavaScriptInterface getInstance(CrashReport.a aVar) {
        String string = null;
        if (aVar == null || f17394a.contains(Integer.valueOf(aVar.hashCode()))) {
            return null;
        }
        H5JavaScriptInterface h5JavaScriptInterface = new H5JavaScriptInterface();
        f17394a.add(Integer.valueOf(aVar.hashCode()));
        Thread threadCurrentThread = Thread.currentThread();
        h5JavaScriptInterface.f17396c = threadCurrentThread;
        if (threadCurrentThread != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (int i2 = 2; i2 < threadCurrentThread.getStackTrace().length; i2++) {
                StackTraceElement stackTraceElement = threadCurrentThread.getStackTrace()[i2];
                if (!stackTraceElement.toString().contains("crashreport")) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
            string = sb.toString();
        }
        h5JavaScriptInterface.f17397d = string;
        HashMap map = new HashMap();
        StringBuilder sb2 = new StringBuilder();
        sb2.append((Object) aVar.c());
        map.put("[WebView] ContentDescription", sb2.toString());
        h5JavaScriptInterface.f17398e = map;
        return h5JavaScriptInterface;
    }

    @JavascriptInterface
    public void printLog(String str) {
        al.d("Log from js: %s", str);
    }

    @JavascriptInterface
    public void reportJSException(String str) {
        if (str == null) {
            al.d("Payload from JS is null.", new Object[0]);
            return;
        }
        String strC = ap.c(str.getBytes());
        String str2 = this.f17395b;
        if (str2 != null && str2.equals(strC)) {
            al.d("Same payload from js. Please check whether you've injected bugly.js more than one times.", new Object[0]);
            return;
        }
        this.f17395b = strC;
        al.d("Handling JS exception ...", new Object[0]);
        bb bbVarA = a(str);
        if (bbVarA == null) {
            al.d("Failed to parse payload.", new Object[0]);
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        String str3 = bbVarA.f17669a;
        if (str3 != null) {
            linkedHashMap2.put("[JS] projectRoot", str3);
        }
        String str4 = bbVarA.f17670b;
        if (str4 != null) {
            linkedHashMap2.put("[JS] context", str4);
        }
        String str5 = bbVarA.f17671c;
        if (str5 != null) {
            linkedHashMap2.put("[JS] url", str5);
        }
        String str6 = bbVarA.f17672d;
        if (str6 != null) {
            linkedHashMap2.put("[JS] userAgent", str6);
        }
        String str7 = bbVarA.f17677i;
        if (str7 != null) {
            linkedHashMap2.put("[JS] file", str7);
        }
        long j2 = bbVarA.f17678j;
        if (j2 != 0) {
            linkedHashMap2.put("[JS] lineNumber", Long.toString(j2));
        }
        linkedHashMap.putAll(linkedHashMap2);
        linkedHashMap.putAll(this.f17398e);
        linkedHashMap.put("Java Stack", this.f17397d);
        InnerApi.postH5CrashAsync(this.f17396c, bbVarA.f17674f, bbVarA.f17675g, bbVarA.f17676h, linkedHashMap);
    }
}
