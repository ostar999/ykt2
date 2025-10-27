package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.otherid.UtdidWrapper;
import com.alipay.sdk.cons.b;
import com.alipay.security.mobile.module.a.a;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.tencent.connect.common.Constants;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class TMNTokenClient {

    /* renamed from: a, reason: collision with root package name */
    private static TMNTokenClient f3055a;

    /* renamed from: b, reason: collision with root package name */
    private Context f3056b;

    public interface InitResultListener {
        void onResult(String str, int i2);
    }

    private TMNTokenClient(Context context) {
        this.f3056b = null;
        if (context == null) {
            throw new IllegalArgumentException("TMNTokenClient initialization error: context is null.");
        }
        this.f3056b = context;
    }

    public static TMNTokenClient getInstance(Context context) {
        if (f3055a == null) {
            synchronized (TMNTokenClient.class) {
                if (f3055a == null) {
                    f3055a = new TMNTokenClient(context);
                }
            }
        }
        return f3055a;
    }

    public void intiToken(final String str, String str2, String str3, final InitResultListener initResultListener) {
        if (a.a(str) && initResultListener != null) {
            initResultListener.onResult("", 2);
        }
        if (a.a(str2) && initResultListener != null) {
            initResultListener.onResult("", 3);
        }
        final HashMap map = new HashMap();
        map.put("utdid", UtdidWrapper.getUtdid(this.f3056b));
        map.put(b.f3217c, "");
        map.put("userId", "");
        map.put("appName", str);
        map.put("appKeyClient", str2);
        map.put("appchannel", "openapi");
        map.put(PLVLinkMicManager.SESSION_ID, str3);
        map.put("rpcVersion", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        com.alipay.apmobilesecuritysdk.f.b.a().a(new Runnable() { // from class: com.alipay.apmobilesecuritysdk.face.TMNTokenClient.1
            @Override // java.lang.Runnable
            public void run() {
                int iA = new com.alipay.apmobilesecuritysdk.a.a(TMNTokenClient.this.f3056b).a(map);
                InitResultListener initResultListener2 = initResultListener;
                if (initResultListener2 == null) {
                    return;
                }
                if (iA != 0) {
                    initResultListener2.onResult("", iA);
                } else {
                    initResultListener.onResult(com.alipay.apmobilesecuritysdk.a.a.a(TMNTokenClient.this.f3056b, str), 0);
                }
            }
        });
    }
}
