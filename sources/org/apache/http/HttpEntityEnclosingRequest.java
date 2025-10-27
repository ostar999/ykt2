package org.apache.http;

/* loaded from: classes9.dex */
public interface HttpEntityEnclosingRequest extends HttpRequest {
    boolean expectContinue();

    HttpEntity getEntity();

    void setEntity(HttpEntity httpEntity);
}
