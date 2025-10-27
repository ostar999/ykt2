package com.umeng.analytics.pro;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.i;
import com.umeng.analytics.vshelper.PageNameMonitor;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class v {

    /* renamed from: c, reason: collision with root package name */
    private static final int f22947c = 5;

    /* renamed from: d, reason: collision with root package name */
    private static JSONArray f22948d = new JSONArray();

    /* renamed from: e, reason: collision with root package name */
    private static Object f22949e = new Object();

    /* renamed from: f, reason: collision with root package name */
    private final Map<String, Long> f22952f = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    Stack<String> f22950a = new Stack<>();

    /* renamed from: b, reason: collision with root package name */
    com.umeng.analytics.vshelper.a f22951b = PageNameMonitor.getInstance();

    public static void a(Context context) {
        String string;
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                synchronized (f22949e) {
                    string = f22948d.toString();
                    f22948d = new JSONArray();
                }
                if (string.length() > 0) {
                    jSONObject.put("__a", new JSONArray(string));
                    if (jSONObject.length() > 0) {
                        i.a(context).a(u.a().c(), jSONObject, i.a.PAGE);
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    public int a() {
        return 2;
    }

    public void b(String str) {
        Long l2;
        Context appContext;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!this.f22952f.containsKey(str)) {
            if (UMConfigure.isDebugLog() && this.f22950a.size() == 0) {
                UMLog.aq(j.G, 0, "\\|", new String[]{"@"}, new String[]{str}, null, null);
                return;
            }
            return;
        }
        synchronized (this.f22952f) {
            l2 = this.f22952f.get(str);
            this.f22952f.remove(str);
        }
        if (l2 == null) {
            return;
        }
        if (UMConfigure.isDebugLog() && this.f22950a.size() > 0 && str.equals(this.f22950a.peek())) {
            this.f22950a.pop();
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - l2.longValue();
        synchronized (f22949e) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(d.f22700v, str);
                jSONObject.put("duration", jCurrentTimeMillis);
                jSONObject.put(d.f22702x, l2);
                jSONObject.put("type", a());
                f22948d.put(jSONObject);
                if (f22948d.length() >= 5 && (appContext = UMGlobalContext.getAppContext(null)) != null) {
                    UMWorkDispatch.sendEvent(appContext, 4099, CoreProtocol.getInstance(appContext), null);
                }
            } catch (Throwable unused) {
            }
        }
        if (!UMConfigure.isDebugLog() || this.f22950a.size() == 0) {
            return;
        }
        UMLog.aq(j.E, 0, "\\|", new String[]{"@"}, new String[]{str}, null, null);
    }

    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (UMConfigure.isDebugLog() && this.f22950a.size() != 0) {
            UMLog.aq(j.F, 0, "\\|", new String[]{"@"}, new String[]{this.f22950a.peek()}, null, null);
        }
        this.f22951b.customPageBegin(str);
        synchronized (this.f22952f) {
            this.f22952f.put(str, Long.valueOf(System.currentTimeMillis()));
            if (UMConfigure.isDebugLog()) {
                this.f22950a.push(str);
            }
        }
    }

    public void b() {
        String key;
        synchronized (this.f22952f) {
            key = null;
            long j2 = 0;
            for (Map.Entry<String, Long> entry : this.f22952f.entrySet()) {
                if (entry.getValue().longValue() > j2) {
                    long jLongValue = entry.getValue().longValue();
                    key = entry.getKey();
                    j2 = jLongValue;
                }
            }
        }
        if (key != null) {
            b(key);
        }
    }
}
