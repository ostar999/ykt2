package com.alibaba.sdk.android.oss.callback;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;

/* loaded from: classes2.dex */
public interface OSSCompletedCallback<T1 extends OSSRequest, T2 extends OSSResult> {
    void onFailure(T1 t12, ClientException clientException, ServiceException serviceException);

    void onSuccess(T1 t12, T2 t2);
}
