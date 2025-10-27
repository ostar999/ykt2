package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.a.a;
import com.alipay.apmobilesecuritysdk.e.d;
import com.alipay.apmobilesecuritysdk.e.g;
import com.alipay.apmobilesecuritysdk.e.h;
import com.alipay.apmobilesecuritysdk.e.i;
import com.alipay.apmobilesecuritysdk.otherid.UmidSdkWrapper;
import com.alipay.apmobilesecuritysdk.otherid.UtdidWrapper;
import com.alipay.sdk.cons.b;
import com.tencent.connect.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class APSecuritySdk {

    /* renamed from: a, reason: collision with root package name */
    private static APSecuritySdk f3048a;

    /* renamed from: c, reason: collision with root package name */
    private static Object f3049c = new Object();

    /* renamed from: b, reason: collision with root package name */
    private Context f3050b;

    public interface InitResultListener {
        void onResult(TokenResult tokenResult);
    }

    public class TokenResult {
        public String apdid;
        public String apdidToken;
        public String clientKey;
        public String umidToken;

        public TokenResult() {
        }
    }

    private APSecuritySdk(Context context) {
        this.f3050b = context;
    }

    public static APSecuritySdk getInstance(Context context) {
        if (f3048a == null) {
            synchronized (f3049c) {
                if (f3048a == null) {
                    f3048a = new APSecuritySdk(context);
                }
            }
        }
        return f3048a;
    }

    public static String getUtdid(Context context) {
        return UtdidWrapper.getUtdid(context);
    }

    public String getApdidToken() {
        String strA = a.a(this.f3050b, "");
        if (com.alipay.security.mobile.module.a.a.a(strA)) {
            initToken(0, new HashMap(), null);
        }
        return strA;
    }

    public String getSdkName() {
        return "APPSecuritySDK-ALIPAY";
    }

    public String getSdkVersion() {
        return "3.2.2-20180331";
    }

    public synchronized TokenResult getTokenResult() {
        TokenResult tokenResult;
        tokenResult = new TokenResult();
        try {
            tokenResult.apdidToken = a.a(this.f3050b, "");
            tokenResult.clientKey = h.f(this.f3050b);
            tokenResult.apdid = a.a(this.f3050b);
            tokenResult.umidToken = UmidSdkWrapper.getSecurityToken(this.f3050b);
            if (com.alipay.security.mobile.module.a.a.a(tokenResult.apdid) || com.alipay.security.mobile.module.a.a.a(tokenResult.apdidToken) || com.alipay.security.mobile.module.a.a.a(tokenResult.clientKey)) {
                initToken(0, new HashMap(), null);
            }
        } catch (Throwable unused) {
        }
        return tokenResult;
    }

    public void initToken(int i2, Map<String, String> map, final InitResultListener initResultListener) {
        com.alipay.apmobilesecuritysdk.b.a.a().a(i2);
        String strB = h.b(this.f3050b);
        String strC = com.alipay.apmobilesecuritysdk.b.a.a().c();
        if (com.alipay.security.mobile.module.a.a.b(strB) && !com.alipay.security.mobile.module.a.a.a(strB, strC)) {
            com.alipay.apmobilesecuritysdk.e.a.a(this.f3050b);
            d.a(this.f3050b);
            g.a(this.f3050b);
            i.h();
        }
        if (!com.alipay.security.mobile.module.a.a.a(strB, strC)) {
            h.c(this.f3050b, strC);
        }
        String strA = com.alipay.security.mobile.module.a.a.a(map, "utdid", "");
        String strA2 = com.alipay.security.mobile.module.a.a.a(map, b.f3217c, "");
        String strA3 = com.alipay.security.mobile.module.a.a.a(map, "userId", "");
        if (com.alipay.security.mobile.module.a.a.a(strA)) {
            strA = UtdidWrapper.getUtdid(this.f3050b);
        }
        final HashMap map2 = new HashMap();
        map2.put("utdid", strA);
        map2.put(b.f3217c, strA2);
        map2.put("userId", strA3);
        map2.put("appName", "");
        map2.put("appKeyClient", "");
        map2.put("appchannel", "");
        map2.put("rpcVersion", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        com.alipay.apmobilesecuritysdk.f.b.a().a(new Runnable() { // from class: com.alipay.apmobilesecuritysdk.face.APSecuritySdk.1
            @Override // java.lang.Runnable
            public void run() {
                new a(APSecuritySdk.this.f3050b).a(map2);
                InitResultListener initResultListener2 = initResultListener;
                if (initResultListener2 != null) {
                    initResultListener2.onResult(APSecuritySdk.this.getTokenResult());
                }
            }
        });
    }
}
