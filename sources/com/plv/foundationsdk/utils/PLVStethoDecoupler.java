package com.plv.foundationsdk.utils;

import android.content.Context;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/* loaded from: classes4.dex */
public class PLVStethoDecoupler {
    public static Interceptor createStethoInterceptor() {
        return new Interceptor() { // from class: com.plv.foundationsdk.utils.PLVStethoDecoupler.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        };
    }

    public static void initStetho(Context context) {
    }
}
