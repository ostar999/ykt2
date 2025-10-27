package com.hjq.http.request;

import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyLog;
import com.hjq.http.model.BodyType;
import com.hjq.http.model.CacheMode;
import com.hjq.http.model.HttpHeaders;
import com.hjq.http.model.HttpParams;
import com.hjq.http.request.UrlRequest;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Request;

/* loaded from: classes4.dex */
public abstract class UrlRequest<T extends UrlRequest<?>> extends BaseRequest<T> {
    public UrlRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override // com.hjq.http.request.BaseRequest
    public Request createRequest(String str, String str2, HttpParams httpParams, HttpHeaders httpHeaders, BodyType bodyType) {
        Request.Builder builder = new Request.Builder();
        if (str2 != null) {
            builder.tag(str2);
        }
        if (getRequestCache().getMode() == CacheMode.NO_CACHE) {
            builder.cacheControl(new CacheControl.Builder().noCache().build());
        }
        if (!httpHeaders.isEmpty()) {
            for (String str3 : httpHeaders.getNames()) {
                builder.addHeader(str3, httpHeaders.get(str3));
            }
        }
        HttpUrl.Builder builderNewBuilder = HttpUrl.get(str).newBuilder();
        if (!httpParams.isEmpty()) {
            for (String str4 : httpParams.getNames()) {
                builderNewBuilder.addQueryParameter(str4, String.valueOf(httpParams.get(str4)));
            }
        }
        HttpUrl httpUrlBuild = builderNewBuilder.build();
        builder.url(httpUrlBuild);
        builder.method(getRequestMethod(), null);
        EasyLog.print("RequestUrl", String.valueOf(httpUrlBuild));
        EasyLog.print("RequestMethod", getRequestMethod());
        if (EasyConfig.getInstance().isLogEnabled()) {
            if (!httpHeaders.isEmpty() || !httpParams.isEmpty()) {
                EasyLog.print();
            }
            for (String str5 : httpHeaders.getNames()) {
                EasyLog.print(str5, httpHeaders.get(str5));
            }
            if (!httpHeaders.isEmpty() && !httpParams.isEmpty()) {
                EasyLog.print();
            }
            for (String str6 : httpParams.getNames()) {
                EasyLog.print(str6, String.valueOf(httpParams.get(str6)));
            }
            if (!httpHeaders.isEmpty() || !httpParams.isEmpty()) {
                EasyLog.print();
            }
        }
        return getRequestHandler().requestStart(getLifecycleOwner(), getRequestApi(), builder);
    }
}
