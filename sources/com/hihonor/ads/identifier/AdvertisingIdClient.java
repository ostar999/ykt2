package com.hihonor.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.exoplayer2.ExoPlayer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class AdvertisingIdClient {

    public static final class Info {
        public String id;
        public boolean isLimit;
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException {
        a aVar = new a();
        aVar.f7268b = context;
        if (!aVar.a(context)) {
            throw new IOException("Service not found or advertisingId not available");
        }
        try {
            String string = Settings.Global.getString(context.getContentResolver(), "oaid_limit_state");
            String string2 = Settings.Global.getString(context.getContentResolver(), "oaid");
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                boolean z2 = Boolean.parseBoolean(string);
                Info info = new Info();
                info.isLimit = z2;
                info.id = string2;
                Log.i("AdvertisingIdPlatform", "getAdvertisingIdInfo cache isLimit=" + z2 + "id " + string2);
                return info;
            }
        } catch (Throwable th) {
            Log.e("AdvertisingIdPlatform", "getAdvertisingIdInfo cache error=" + th);
        }
        try {
            try {
                Log.i("AdvertisingIdPlatform", "bindService start");
                Intent intent = new Intent();
                intent.setAction("com.hihonor.id.HnOaIdService");
                intent.setPackage("com.hihonor.id");
                context.bindService(intent, aVar, 1);
                aVar.f7271e.await(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                aVar.a();
                return aVar.f7267a;
            } catch (Exception e2) {
                Log.e("AdvertisingIdPlatform", "getAdvertisingIdInfo error=" + e2.getMessage());
                aVar.a();
                return null;
            }
        } catch (Throwable th2) {
            aVar.a();
            throw th2;
        }
    }

    public static boolean isAdvertisingIdAvailable(Context context) {
        return new a().a(context);
    }
}
