package com.hjq.http.request;

import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.model.HttpMethod;

/* loaded from: classes4.dex */
public final class HeadRequest extends UrlRequest<HeadRequest> {
    public HeadRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override // com.hjq.http.request.BaseRequest
    public String getRequestMethod() {
        return HttpMethod.HEAD.toString();
    }
}
