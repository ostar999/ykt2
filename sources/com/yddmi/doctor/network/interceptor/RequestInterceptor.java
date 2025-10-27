package com.yddmi.doctor.network.interceptor;

import android.content.pm.PackageManager;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.mvvm.utils.DateUtil;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.yddmi.doctor.config.YddUserManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/yddmi/doctor/network/interceptor/RequestInterceptor;", "Lokhttp3/Interceptor;", "()V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class RequestInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws PackageManager.NameNotFoundException {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Request.Builder builderNewBuilder = chain.request().newBuilder();
        builderNewBuilder.addHeader("os", "android");
        String timeInMillis = DateUtil.getTimeInMillis();
        Intrinsics.checkNotNullExpressionValue(timeInMillis, "getTimeInMillis()");
        builderNewBuilder.addHeader(CrashHianalyticsData.TIME, timeInMillis);
        String versionName = AppInformationUtil.getVersionName();
        Intrinsics.checkNotNullExpressionValue(versionName, "getVersionName()");
        builderNewBuilder.addHeader("version", versionName);
        builderNewBuilder.addHeader("Device-Type", "app-skill");
        String uMChannel = AppInformationUtil.getUMChannel();
        Intrinsics.checkNotNullExpressionValue(uMChannel, "getUMChannel()");
        builderNewBuilder.addHeader("umChannel", uMChannel);
        builderNewBuilder.addHeader("appSource", "DYIUHD");
        builderNewBuilder.addHeader("appCustom", "");
        builderNewBuilder.addHeader("Authorization", YddUserManager.INSTANCE.getInstance().userToken());
        return chain.proceed(builderNewBuilder.build());
    }
}
