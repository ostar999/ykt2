package com.mobile.auth.y;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.psychiatrygarden.utils.MimeTypes;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.crypto.Cipher;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class k {

    /* renamed from: b, reason: collision with root package name */
    public l f10604b;

    /* renamed from: c, reason: collision with root package name */
    private ExecutorService f10605c = Executors.newSingleThreadExecutor();

    /* renamed from: a, reason: collision with root package name */
    public ScheduledExecutorService f10603a = Executors.newScheduledThreadPool(1);

    public static String a(Context context) {
        String string = "";
        try {
            try {
                String strC = u.c();
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis());
                String string2 = sb.toString();
                String strB = p.b();
                String packageName = context.getPackageName();
                String strG = v.g();
                String strA = v.a();
                String strA2 = v.a(strG, strA.substring(0, 16), strA.substring(16, 32));
                PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(j.b("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVc1ecjpc5k7TkabF935iQONDZ0/E5XWPVv9FEsI59XTRW0+BCMK1MODRSWMvHFrPMh9ZilnRr7qXuAKCBEynQEghmpIVvMYhFu48FAI9bKfkI5lKuQK+tc4X0+zTbNrpedNoKXK4C7dDjTETBH6prwWE9j5WsAf0gbjUbIs3FxwIDAQAB")));
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                cipher.init(1, publicKeyGeneratePublic);
                String strA3 = j.a(cipher.doFinal(strA.getBytes()));
                String strA4 = v.a(context, context.getPackageName(), p.f10626b);
                String strB2 = u.b();
                if (!TextUtils.isEmpty(strB2)) {
                    strB2 = "0";
                }
                String str = strA4 + "\n" + strC + "\n2.1\n" + AliyunVodHttpCommon.Format.FORMAT_JSON + "\n" + strB2 + "\n" + packageName + "\n" + strA2 + "\n" + strB + "\n" + strA3 + "\n" + string2;
                String strA5 = v.a(str.replaceAll("\n", ""));
                t.a("unSignDebugInfo=".concat(str));
                String strA6 = j.a(strA2);
                String strA7 = j.a(strA3);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("androidMd5", strA4);
                jSONObject.put("apiKey", strC);
                jSONObject.put(com.alipay.sdk.cons.c.f3238m, "2.1");
                jSONObject.put("format", AliyunVodHttpCommon.Format.FORMAT_JSON);
                jSONObject.put("operator", strB2);
                jSONObject.put("packName", packageName);
                jSONObject.put("privateIp", strA6);
                jSONObject.put(com.heytap.mcssdk.constant.b.C, strB);
                jSONObject.put("secretKey", strA7);
                jSONObject.put("timeStamp", string2);
                jSONObject.put("sign", strA5);
                string = jSONObject.toString();
                t.c("getPreCheckParam_CU_Oath: param ok  \n");
                return string;
            } catch (Exception e2) {
                e2.printStackTrace();
                return string;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ void a(k kVar) {
        try {
            try {
                ScheduledExecutorService scheduledExecutorService = kVar.f10603a;
                if (scheduledExecutorService != null) {
                    scheduledExecutorService.shutdownNow();
                    kVar.f10603a = null;
                }
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public final void a(final Context context, final int i2, final Object obj, final m mVar) {
        try {
            synchronized (this) {
                try {
                    this.f10605c.submit(new Runnable() { // from class: com.mobile.auth.y.k.4
                        @Override // java.lang.Runnable
                        public final void run() {
                            try {
                                try {
                                    String str = "";
                                    int i3 = i2;
                                    if (i3 != 2) {
                                        mVar.a(i3, 410009, "410009no this type");
                                    } else {
                                        str = u.a() + s.a(k.a(context), "&");
                                    }
                                    n nVar = new n();
                                    Context context2 = context;
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("user-agent", "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1");
                                    map.put("netType", "2");
                                    map.put("os", "android");
                                    map.put("Accept", MimeTypes.ANY_TYPE);
                                    String strA = nVar.a(context2, str, map, obj);
                                    if (u.h() == 1) {
                                        try {
                                            q.a().b();
                                            t.c("\n  WIFI + 流量 \n call releaseNetwork() \n");
                                        } catch (Exception unused) {
                                            t.b();
                                        }
                                    }
                                    if (TextUtils.isEmpty(strA)) {
                                        mVar.a(i2, 410002, "网络请求响应为空");
                                    } else {
                                        mVar.a(i2, 1, strA);
                                    }
                                } catch (Exception unused2) {
                                    t.b();
                                }
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }
                    });
                } catch (Exception e2) {
                    mVar.a(i2, 410009, "410009" + e2.getMessage());
                }
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
