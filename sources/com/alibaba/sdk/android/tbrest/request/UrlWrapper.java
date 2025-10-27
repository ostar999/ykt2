package com.alibaba.sdk.android.tbrest.request;

import com.alibaba.sdk.android.tbrest.SendService;
import com.just.agentweb.DefaultWebClient;

/* loaded from: classes2.dex */
public class UrlWrapper {
    private static final int MAX_CONNECTION_TIME_OUT = 10000;
    private static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 60000;
    public static int mErrorCode;
    private static a mRestSslSocketFactory;

    static {
        System.setProperty("http.keepAlive", k.a.f27523u);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0188  */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v33 */
    /* JADX WARN: Type inference failed for: r1v34 */
    /* JADX WARN: Type inference failed for: r1v35 */
    /* JADX WARN: Type inference failed for: r1v36 */
    /* JADX WARN: Type inference failed for: r1v37 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.alibaba.sdk.android.tbrest.request.BizResponse sendRequest(com.alibaba.sdk.android.tbrest.SendService r7, java.lang.String r8, java.lang.String r9, byte[] r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 502
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.request.UrlWrapper.sendRequest(com.alibaba.sdk.android.tbrest.SendService, java.lang.String, java.lang.String, byte[]):com.alibaba.sdk.android.tbrest.request.BizResponse");
    }

    public static BizResponse sendRequestByUrl(SendService sendService, String str, byte[] bArr) {
        return sendRequest(sendService, sendService.appKey, str, bArr);
    }

    public static BizResponse sendRequest(SendService sendService, String str, byte[] bArr) {
        String str2;
        String str3 = sendService.appKey;
        if (sendService.openHttp.booleanValue()) {
            str2 = DefaultWebClient.HTTP_SCHEME + str + "/upload";
        } else {
            str2 = DefaultWebClient.HTTPS_SCHEME + str + "/upload";
        }
        return sendRequest(sendService, str3, str2, bArr);
    }
}
