package com.hjq.http.config;

import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.Type;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes4.dex */
public interface IRequestHandler {
    Object readCache(LifecycleOwner lifecycleOwner, IRequestApi iRequestApi, Type type) throws Throwable;

    Exception requestFail(LifecycleOwner lifecycleOwner, IRequestApi iRequestApi, Exception exc);

    Request requestStart(LifecycleOwner lifecycleOwner, IRequestApi iRequestApi, Request.Builder builder);

    Object requestSucceed(LifecycleOwner lifecycleOwner, IRequestApi iRequestApi, Response response, Type type) throws Exception;

    boolean writeCache(LifecycleOwner lifecycleOwner, IRequestApi iRequestApi, Response response, Object obj) throws Throwable;
}
