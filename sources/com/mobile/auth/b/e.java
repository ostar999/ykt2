package com.mobile.auth.b;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9595a = "e";

    /* renamed from: b, reason: collision with root package name */
    private static int f9596b;

    /* renamed from: c, reason: collision with root package name */
    private static Map<String, b> f9597c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    private static List<String> f9598d = new ArrayList();

    public static synchronized b a(String str) {
        b bVar;
        try {
            bVar = f9597c.containsKey(str) ? f9597c.get(str) : null;
            if (bVar == null) {
                bVar = new b(str);
                f9597c.put(str, bVar);
            }
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return new b(str);
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
        return bVar;
    }

    public static /* synthetic */ void a(Context context) {
        try {
            b(context);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(final Context context, String str) {
        try {
            synchronized (e.class) {
                if (f9597c.containsKey(str)) {
                    f9598d.add(f9597c.get(str).toString());
                    f9597c.remove(str);
                }
                if (f9596b != 1 && !f9598d.isEmpty()) {
                    f9596b = 1;
                    new Timer().schedule(new TimerTask() { // from class: com.mobile.auth.b.e.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            try {
                                e.a(context);
                            } catch (Throwable th) {
                                try {
                                    ExceptionProcessor.processException(th);
                                } catch (Throwable th2) {
                                    ExceptionProcessor.processException(th2);
                                }
                            }
                        }
                    }, RtspMediaSource.DEFAULT_TIMEOUT_MS);
                }
            }
        } catch (Throwable th) {
            try {
                th.printStackTrace();
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                }
            }
        }
    }

    public static void a(String str, String str2, String str3) {
        String strOptString = "";
        int i2 = -1;
        try {
            try {
                if (!TextUtils.isEmpty(str2)) {
                    JSONObject jSONObject = new JSONObject(str2);
                    i2 = jSONObject.getInt("result");
                    strOptString = jSONObject.optString("msg");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (i2 == 0) {
                a(str).a(i2).f(strOptString);
            } else {
                a(str).a(i2).f(strOptString).e(str3);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static void b(Context context) {
        if (context == null) {
            return;
        }
        try {
            ArrayList arrayList = new ArrayList();
            synchronized (e.class) {
                arrayList.addAll(f9598d);
                f9596b = 0;
                f9598d.clear();
            }
            if (arrayList.isEmpty()) {
                return;
            }
            d.a(context, arrayList);
        } catch (Throwable th) {
            try {
                th.printStackTrace();
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                }
            }
        }
    }

    public static void b(Context context, String str) {
        try {
            d.a(context, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
