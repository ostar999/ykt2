package com.hjq.http.request;

import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.model.HttpMethod;

/* loaded from: classes4.dex */
public final class DeleteRequest extends UrlRequest<DeleteRequest> {
    public DeleteRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override // com.hjq.http.request.BaseRequest
    public String getRequestMethod() {
        return HttpMethod.DELETE.toString();
    }
}
