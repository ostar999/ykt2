package com.beizi.fusion.g;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.e.a.b;
import com.beizi.fusion.model.RequestInfo;

/* loaded from: classes2.dex */
public class af {

    /* renamed from: a, reason: collision with root package name */
    static String f5099a = "OaidUtil";

    /* renamed from: c, reason: collision with root package name */
    private static String f5101c;

    /* renamed from: b, reason: collision with root package name */
    public static b.a f5100b = new b.a() { // from class: com.beizi.fusion.g.af.1
        @Override // com.beizi.fusion.e.a.b.a
        public void a(@NonNull String str) {
            Log.e(af.f5099a, "code cn Oaid:" + str);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            String unused = af.f5101c = str;
            Context contextE = com.beizi.fusion.d.b.a().e();
            aq.a(contextE, "__OAID__", (Object) af.f5101c);
            aq.a(contextE, "__CNOAID__", (Object) af.f5101c);
            if (RequestInfo.getInstance(contextE).getDevInfo() != null) {
                if (ag.b() && BeiZis.isLimitPersonalAds()) {
                    return;
                }
                RequestInfo.getInstance(contextE).getDevInfo().setOaid(af.f5101c);
                RequestInfo.getInstance(contextE).getDevInfo().setCnOaid(af.f5101c);
            }
        }
    };

    /* renamed from: d, reason: collision with root package name */
    private static boolean f5102d = true;
}
