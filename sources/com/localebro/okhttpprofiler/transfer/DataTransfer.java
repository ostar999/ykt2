package com.localebro.okhttpprofiler.transfer;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes4.dex */
public interface DataTransfer {
    void sendDuration(String str, long j2);

    void sendException(String str, Exception exc);

    void sendRequest(String str, Request request) throws IOException;

    void sendResponse(String str, Response response) throws IOException;
}
