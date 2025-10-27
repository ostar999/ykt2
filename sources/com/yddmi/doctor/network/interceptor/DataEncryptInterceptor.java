package com.yddmi.doctor.network.interceptor;

import com.catchpig.mvvm.utils.AESUtil;
import com.catchpig.mvvm.utils.Base64;
import com.catchpig.utils.ext.LogExtKt;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.utils.RsaUtil;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/yddmi/doctor/network/interceptor/DataEncryptInterceptor;", "Lokhttp3/Interceptor;", "()V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class DataEncryptInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Response responseProceed = chain.proceed(chain.request());
        try {
            if (200 != responseProceed.code()) {
                return responseProceed;
            }
            String str = responseProceed.headers().get("encrypt-key");
            if (str == null || str.length() == 0) {
                return responseProceed;
            }
            byte[] key2 = Base64.decode(YddHostConfig.YddHost.FORMAL == YddHostConfig.INSTANCE.getInstance().getCurrentHost() ? RsaUtil.decryptByPrivateKey(str, YddConfig.flksaO8or) : RsaUtil.decryptByPrivateKey(str, YddConfig.flksaO8o));
            ResponseBody responseBodyBody = responseProceed.body();
            String strString = responseBodyBody != null ? responseBodyBody.string() : null;
            Intrinsics.checkNotNullExpressionValue(key2, "key2");
            String newBodyStr = AESUtil.decrypt(strString, new String(key2, Charsets.UTF_8));
            if (responseBodyBody != null) {
                responseBodyBody.close();
            }
            ResponseBody.Companion companion = ResponseBody.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(newBodyStr, "newBodyStr");
            responseProceed = responseProceed.newBuilder().body(ResponseBody.Companion.create$default(companion, newBodyStr, (MediaType) null, 1, (Object) null)).build();
            responseProceed.close();
            return responseProceed;
        } catch (Throwable th) {
            LogExtKt.loge("61异常：" + th + " ", YddConfig.TAG);
            return responseProceed;
        }
    }
}
