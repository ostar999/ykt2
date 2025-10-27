package com.ykb.ebook.network;

import androidx.lifecycle.LifecycleOwner;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestHandler;
import com.hjq.http.exception.HttpException;
import java.lang.reflect.Type;
import kotlin.Metadata;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import x0.c;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0016J0\u0010\n\u001a\u00020\u000b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lcom/ykb/ebook/network/RequestHandler;", "Lcom/hjq/http/config/IRequestHandler;", "()V", "requestFail", "Ljava/lang/Exception;", RequestParameters.SUBRESOURCE_LIFECYCLE, "Landroidx/lifecycle/LifecycleOwner;", "api", "Lcom/hjq/http/config/IRequestApi;", AliyunLogKey.KEY_EVENT, "requestSucceed", "", "response", "Lokhttp3/Response;", "type", "Ljava/lang/reflect/Type;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class RequestHandler implements IRequestHandler {
    @Override // com.hjq.http.config.IRequestHandler
    public /* synthetic */ Object readCache(LifecycleOwner lifecycleOwner, IRequestApi iRequestApi, Type type) {
        return c.a(this, lifecycleOwner, iRequestApi, type);
    }

    @Override // com.hjq.http.config.IRequestHandler
    @NotNull
    public Exception requestFail(@Nullable LifecycleOwner lifecycle, @Nullable IRequestApi api, @Nullable Exception e2) {
        return new HttpException(e2 != null ? e2.getMessage() : null, e2);
    }

    @Override // com.hjq.http.config.IRequestHandler
    public /* synthetic */ Request requestStart(LifecycleOwner lifecycleOwner, IRequestApi iRequestApi, Request.Builder builder) {
        return c.b(this, lifecycleOwner, iRequestApi, builder);
    }

    @Override // com.hjq.http.config.IRequestHandler
    @NotNull
    public Object requestSucceed(@Nullable LifecycleOwner lifecycle, @Nullable IRequestApi api, @Nullable Response response, @Nullable Type type) {
        return new Object();
    }

    @Override // com.hjq.http.config.IRequestHandler
    public /* synthetic */ boolean writeCache(LifecycleOwner lifecycleOwner, IRequestApi iRequestApi, Response response, Object obj) {
        return c.c(this, lifecycleOwner, iRequestApi, response, obj);
    }
}
