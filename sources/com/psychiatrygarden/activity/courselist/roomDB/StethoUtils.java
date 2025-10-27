package com.psychiatrygarden.activity.courselist.roomDB;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/* loaded from: classes5.dex */
public class StethoUtils {
    public static OkHttpClient configureInterceptor(OkHttpClient httpClient) throws ClassNotFoundException {
        try {
            return httpClient.newBuilder().addNetworkInterceptor((Interceptor) Class.forName("com.facebook.stetho.okhttp3.StethoInterceptor").newInstance()).build();
        } catch (Exception e2) {
            e2.printStackTrace();
            return httpClient;
        }
    }

    public static void init(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Class.forName("com.facebook.stetho.Stetho").getMethod("initializeWithDefaults", Context.class).invoke(null, context);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
