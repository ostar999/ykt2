package com.sina.weibo.sdk.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.sina.weibo.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: com.sina.weibo.sdk.b.a$a, reason: collision with other inner class name */
    public static class C0313a {
        public int ah;
        public String packageName = BuildConfig.APPLICATION_ID;
        public String ag = "com.sina.weibo.SSOActivity";
    }

    public static boolean a(Context context, Intent intent) {
        PackageManager packageManager;
        ResolveInfo resolveInfoResolveActivity;
        if (context == null || (packageManager = context.getPackageManager()) == null || (resolveInfoResolveActivity = packageManager.resolveActivity(intent, 0)) == null) {
            return false;
        }
        try {
            Signature[] signatureArr = packageManager.getPackageInfo(resolveInfoResolveActivity.activityInfo.packageName, 64).signatures;
            if (signatureArr == null) {
                return false;
            }
            for (Signature signature : signatureArr) {
                if ("18da2bf10352443a00a5e046d9fca6bd".equals(d.a(signature.toByteArray()))) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static C0313a c(Context context) {
        return e(context);
    }

    public static boolean d(Context context) throws IOException {
        C0313a c0313aE = e(context);
        return c0313aE != null && c0313aE.ah >= 10791;
    }

    public static C0313a e(Context context) throws IOException {
        List<ResolveInfo> listQueryIntentServices;
        Intent intent = new Intent("com.sina.weibo.action.sdkidentity");
        intent.addCategory("android.intent.category.DEFAULT");
        C0313a c0313a = null;
        if (context != null && (listQueryIntentServices = context.getPackageManager().queryIntentServices(intent, 0)) != null && !listQueryIntentServices.isEmpty()) {
            for (ResolveInfo resolveInfo : listQueryIntentServices) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                if (serviceInfo != null && serviceInfo.applicationInfo != null && !TextUtils.isEmpty(serviceInfo.packageName)) {
                    String str = resolveInfo.serviceInfo.packageName;
                    C0313a c0313aA = a(context, str);
                    if (c0313aA != null) {
                        c0313a = c0313aA;
                    }
                    if (BuildConfig.APPLICATION_ID.equals(str) || "com.sina.weibog3".equals(str)) {
                        break;
                    }
                }
            }
        }
        return c0313a;
    }

    private static C0313a a(Context context, String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            InputStream inputStreamOpen = context.createPackageContext(str, 2).getAssets().open("weibo_for_sdk.json");
            StringBuilder sb = new StringBuilder();
            byte[] bArr = new byte[4096];
            while (true) {
                int i2 = inputStreamOpen.read(bArr, 0, 4096);
                if (i2 != -1) {
                    sb.append(new String(bArr, 0, i2));
                } else {
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    C0313a c0313a = new C0313a();
                    c0313a.ah = jSONObject.optInt("support_api", -1);
                    c0313a.ag = jSONObject.optString("authActivityName", null);
                    c0313a.packageName = str;
                    return c0313a;
                }
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        } catch (JSONException e4) {
            e4.printStackTrace();
            return null;
        }
    }
}
