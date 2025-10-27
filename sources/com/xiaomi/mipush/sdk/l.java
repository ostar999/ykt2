package com.xiaomi.mipush.sdk;

import com.xiaomi.push.ic;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap<f, a> f24571a = new HashMap<>();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f24572a;

        /* renamed from: b, reason: collision with root package name */
        public String f24573b;

        public a(String str, String str2) {
            this.f24572a = str;
            this.f24573b = str2;
        }
    }

    static {
        a(f.ASSEMBLE_PUSH_HUAWEI, new a("com.xiaomi.assemble.control.HmsPushManager", "newInstance"));
        a(f.ASSEMBLE_PUSH_FCM, new a("com.xiaomi.assemble.control.FCMPushManager", "newInstance"));
        a(f.ASSEMBLE_PUSH_COS, new a("com.xiaomi.assemble.control.COSPushManager", "newInstance"));
    }

    public static be a(f fVar) {
        int i2 = m.f24574a[fVar.ordinal()];
        if (i2 == 1) {
            return be.UPLOAD_HUAWEI_TOKEN;
        }
        if (i2 == 2) {
            return be.UPLOAD_FCM_TOKEN;
        }
        if (i2 != 3) {
            return null;
        }
        return be.UPLOAD_COS_TOKEN;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static a m177a(f fVar) {
        return f24571a.get(fVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static ic m178a(f fVar) {
        return ic.AggregatePushSwitch;
    }

    private static void a(f fVar, a aVar) {
        if (aVar != null) {
            f24571a.put(fVar, aVar);
        }
    }
}
