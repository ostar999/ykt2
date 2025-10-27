package com.tencent.connect.a;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.b.e;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.g;
import com.yikaobang.yixue.R2;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static Class<?> f17936a = null;

    /* renamed from: b, reason: collision with root package name */
    private static Class<?> f17937b = null;

    /* renamed from: c, reason: collision with root package name */
    private static Method f17938c = null;

    /* renamed from: d, reason: collision with root package name */
    private static Method f17939d = null;

    /* renamed from: e, reason: collision with root package name */
    private static Method f17940e = null;

    /* renamed from: f, reason: collision with root package name */
    private static Method f17941f = null;

    /* renamed from: g, reason: collision with root package name */
    private static boolean f17942g = false;

    public static boolean a(Context context, QQToken qQToken) {
        return g.a(context, qQToken.getAppId()).b("Common_ta_enable");
    }

    public static void b(Context context, QQToken qQToken) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            if (a(context, qQToken)) {
                f17941f.invoke(f17936a, Boolean.TRUE);
            } else {
                f17941f.invoke(f17936a, Boolean.FALSE);
            }
        } catch (Exception e2) {
            SLog.e("OpenConfig", "checkStatStatus exception: " + e2.toString());
        }
    }

    public static void c(Context context, QQToken qQToken) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        String str = "Aqc" + qQToken.getAppId();
        try {
            f17936a = Class.forName("com.tencent.stat.StatConfig");
            Class<?> cls = Class.forName("com.tencent.stat.StatService");
            f17937b = cls;
            f17938c = cls.getMethod("reportQQ", Context.class, String.class);
            f17939d = f17937b.getMethod("trackCustomEvent", Context.class, String.class, String[].class);
            Class<?> cls2 = f17937b;
            Class<?> cls3 = Integer.TYPE;
            f17940e = cls2.getMethod("commitEvents", Context.class, cls3);
            Class<?> cls4 = f17936a;
            Class<?> cls5 = Boolean.TYPE;
            f17941f = cls4.getMethod("setEnableStatService", cls5);
            b(context, qQToken);
            f17936a.getMethod("setAutoExceptionCaught", cls5).invoke(f17936a, Boolean.FALSE);
            f17936a.getMethod("setEnableSmartReporting", cls5).invoke(f17936a, Boolean.TRUE);
            f17936a.getMethod("setSendPeriodMinutes", cls3).invoke(f17936a, Integer.valueOf(R2.attr.ease_round_radius));
            Class<?> cls6 = Class.forName("com.tencent.stat.StatReportStrategy");
            f17936a.getMethod("setStatSendStrategy", cls6).invoke(f17936a, cls6.getField("PERIOD").get(null));
            f17937b.getMethod("startStatService", Context.class, String.class, String.class).invoke(f17937b, context, str, Class.forName("com.tencent.stat.common.StatConstants").getField("VERSION").get(null));
            f17942g = true;
        } catch (Exception e2) {
            SLog.e("OpenConfig", "start4QQConnect exception: " + e2.toString());
        }
    }

    public static void d(Context context, QQToken qQToken) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!TextUtils.isEmpty(qQToken.getOpenId())) {
            e.a().a(qQToken.getOpenId(), qQToken.getAppId(), "2", "1", Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE, "0", "0", "0");
        }
        if (f17942g) {
            b(context, qQToken);
            if (qQToken.getOpenId() != null) {
                try {
                    f17938c.invoke(f17937b, context, qQToken.getOpenId());
                } catch (Exception e2) {
                    SLog.e("OpenConfig", "reportQQ exception: " + e2.toString());
                }
            }
        }
    }

    public static void a(Context context, QQToken qQToken, String str, String... strArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (f17942g) {
            b(context, qQToken);
            try {
                f17939d.invoke(f17937b, context, str, strArr);
            } catch (Exception e2) {
                SLog.e("OpenConfig", "trackCustomEvent exception: " + e2.toString());
            }
        }
    }
}
