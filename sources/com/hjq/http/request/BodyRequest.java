package com.hjq.http.request;

import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import com.hjq.http.body.JsonBody;
import com.hjq.http.body.ProgressBody;
import com.hjq.http.body.StringBody;
import com.hjq.http.body.UpdateBody;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.http.listener.OnUpdateListener;
import com.hjq.http.model.BodyType;
import com.hjq.http.model.CacheMode;
import com.hjq.http.model.HttpHeaders;
import com.hjq.http.model.HttpParams;
import com.hjq.http.request.BodyRequest;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: classes4.dex */
public abstract class BodyRequest<T extends BodyRequest<?>> extends BaseRequest<T> {
    private RequestBody mRequestBody;
    private OnUpdateListener<?> mUpdateListener;

    public BodyRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    private RequestBody createBody(HttpParams httpParams, BodyType bodyType) {
        RequestBody requestBodyBuild;
        if (httpParams.isMultipart() && !httpParams.isEmpty()) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (String str : httpParams.getNames()) {
                Object obj = httpParams.get(str);
                if (obj instanceof File) {
                    MultipartBody.Part partCreatePart = EasyUtils.createPart(str, (File) obj);
                    if (partCreatePart != null) {
                        builder.addPart(partCreatePart);
                    }
                } else if (obj instanceof InputStream) {
                    MultipartBody.Part partCreatePart2 = EasyUtils.createPart(str, (InputStream) obj);
                    if (partCreatePart2 != null) {
                        builder.addPart(partCreatePart2);
                    }
                } else if (obj instanceof MultipartBody.Part) {
                    builder.addPart((MultipartBody.Part) obj);
                } else if (!(obj instanceof RequestBody)) {
                    if (obj instanceof List) {
                        List list = (List) obj;
                        if (EasyUtils.isFileList(list)) {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                MultipartBody.Part partCreatePart3 = EasyUtils.createPart(str, (File) it.next());
                                if (partCreatePart3 != null) {
                                    builder.addPart(partCreatePart3);
                                }
                            }
                        }
                    }
                    builder.addFormDataPart(str, String.valueOf(obj));
                } else if (obj instanceof UpdateBody) {
                    builder.addFormDataPart(str, EasyUtils.encodeString(((UpdateBody) obj).getKeyName()), (RequestBody) obj);
                } else {
                    builder.addFormDataPart(str, null, (RequestBody) obj);
                }
            }
            try {
                requestBodyBuild = builder.build();
            } catch (IllegalStateException unused) {
                requestBodyBuild = new FormBody.Builder().build();
            }
        } else if (bodyType == BodyType.JSON) {
            requestBodyBuild = new JsonBody(httpParams.getParams());
        } else {
            FormBody.Builder builder2 = new FormBody.Builder();
            if (!httpParams.isEmpty()) {
                for (String str2 : httpParams.getNames()) {
                    builder2.add(str2, String.valueOf(httpParams.get(str2)));
                }
            }
            requestBodyBuild = builder2.build();
        }
        return this.mUpdateListener == null ? requestBodyBuild : new ProgressBody(requestBodyBuild, getLifecycleOwner(), this.mUpdateListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T body(RequestBody requestBody) {
        this.mRequestBody = requestBody;
        return this;
    }

    @Override // com.hjq.http.request.BaseRequest
    public Request createRequest(String str, String str2, HttpParams httpParams, HttpHeaders httpHeaders, BodyType bodyType) {
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        EasyLog.print("RequestUrl", str);
        EasyLog.print("RequestMethod", getRequestMethod());
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
        RequestBody requestBodyCreateBody = this.mRequestBody;
        if (requestBodyCreateBody == null) {
            requestBodyCreateBody = createBody(httpParams, bodyType);
        }
        builder.method(getRequestMethod(), requestBodyCreateBody);
        if (EasyConfig.getInstance().isLogEnabled()) {
            if (!httpHeaders.isEmpty() || !httpParams.isEmpty()) {
                EasyLog.print();
            }
            for (String str4 : httpHeaders.getNames()) {
                EasyLog.print(str4, httpHeaders.get(str4));
            }
            if (!httpHeaders.isEmpty() && !httpParams.isEmpty()) {
                EasyLog.print();
            }
            if ((requestBodyCreateBody instanceof FormBody) || (requestBodyCreateBody instanceof MultipartBody) || (requestBodyCreateBody instanceof ProgressBody)) {
                for (String str5 : httpParams.getNames()) {
                    Object obj = httpParams.get(str5);
                    if (obj instanceof String) {
                        EasyLog.print(str5, "\"" + obj + "\"");
                    } else {
                        EasyLog.print(str5, String.valueOf(obj));
                    }
                }
            } else if (requestBodyCreateBody instanceof JsonBody) {
                EasyLog.json(requestBodyCreateBody.toString());
            } else {
                EasyLog.print(requestBodyCreateBody.toString());
            }
            if (!httpHeaders.isEmpty() || !httpParams.isEmpty()) {
                EasyLog.print();
            }
        }
        return getRequestHandler().requestStart(getLifecycleOwner(), getRequestApi(), builder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T json(Map<?, ?> map) {
        return map == null ? this : (T) body(new JsonBody(map));
    }

    @Override // com.hjq.http.request.BaseRequest
    public void request(OnHttpListener<?> onHttpListener) {
        if (onHttpListener instanceof OnUpdateListener) {
            this.mUpdateListener = (OnUpdateListener) onHttpListener;
        }
        super.request(onHttpListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T text(String str) {
        return str == null ? this : (T) body(new StringBody(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T json(List<?> list) {
        return list == null ? this : (T) body(new JsonBody(list));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T json(String str) {
        return str == null ? this : (T) body(new JsonBody(str));
    }
}
