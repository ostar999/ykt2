package com.plv.foundationsdk.web;

import com.plv.foundationsdk.web.PLVWebMessageProcessor;

/* loaded from: classes4.dex */
public interface IPLVWebViewBinder<T extends PLVWebMessageProcessor> {
    void bindWebProcessor(T t2);

    void destroy();

    void sendWebMessage(String str);

    void sendWebMessage(String str, String str2);
}
