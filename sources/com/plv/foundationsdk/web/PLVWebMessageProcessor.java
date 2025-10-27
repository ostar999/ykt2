package com.plv.foundationsdk.web;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class PLVWebMessageProcessor<T> {
    protected T callback;
    protected List<String> protocols = new ArrayList();
    protected PLVWebview webview;

    public PLVWebMessageProcessor(PLVWebview pLVWebview) {
        this.webview = pLVWebview;
    }

    public void bindWebView(PLVWebview pLVWebview) {
        this.webview = pLVWebview;
    }

    public abstract void callMessage(String str, String str2);

    public abstract void destroy();

    public PLVWebview getWebview() {
        return this.webview;
    }

    public abstract void registerJSHandler(T t2);
}
