package com.yddmi.doctor.network.api;

import com.catchpig.annotation.ServiceApi;
import com.catchpig.mvvm.network.converter.SerializationResponseBodyConverter;
import com.google.android.exoplayer2.ExoPlayer;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;
import com.yddmi.doctor.entity.result.WxToken;
import com.yddmi.doctor.entity.result.WxUserInfo;
import com.yddmi.doctor.network.interceptor.RequestInterceptor;
import com.yddmi.doctor.network.interceptor.ResponseInterceptor;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.http.GET;
import retrofit2.http.Query;

@ServiceApi(baseUrl = OtherApi.wxBaseUrl, connectTimeout = ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, debugInterceptors = {OkHttpProfilerInterceptor.class}, interceptors = {RequestInterceptor.class, ResponseInterceptor.class}, readTimeout = 6000, responseConverter = SerializationResponseBodyConverter.class)
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u001d\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J'\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0001\u0010\t\u001a\u00020\u00052\b\b\u0001\u0010\n\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, d2 = {"Lcom/yddmi/doctor/network/api/WxService;", "", "wxGetAccessToken", "Lcom/yddmi/doctor/entity/result/WxToken;", "code", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "wxGetUserInfo", "Lcom/yddmi/doctor/entity/result/WxUserInfo;", "token", "openid", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface WxService {
    @GET(OtherApi.wxGetAccessToken)
    @Nullable
    Object wxGetAccessToken(@NotNull @Query("code") String str, @NotNull Continuation<? super WxToken> continuation);

    @GET("userinfo")
    @Nullable
    Object wxGetUserInfo(@NotNull @Query("access_token") String str, @NotNull @Query("openid") String str2, @NotNull Continuation<? super WxUserInfo> continuation);
}
