package com.yddmi.doctor.network.api;

import com.catchpig.annotation.ServiceApi;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;
import com.yddmi.doctor.network.ResponseBodyConverter;
import com.yddmi.doctor.network.interceptor.DataEncryptInterceptor;
import com.yddmi.doctor.network.interceptor.RequestInterceptor;
import kotlin.Metadata;

@ServiceApi(baseUrl = "https://www.medmeta.com/api/", connectTimeout = 1000, debugInterceptors = {OkHttpProfilerInterceptor.class}, interceptors = {RequestInterceptor.class, DataEncryptInterceptor.class}, readTimeout = 5000, responseConverter = ResponseBodyConverter.class)
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lcom/yddmi/doctor/network/api/YddServiceFormal;", "Lcom/yddmi/doctor/network/api/YddService;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface YddServiceFormal extends YddService {
}
