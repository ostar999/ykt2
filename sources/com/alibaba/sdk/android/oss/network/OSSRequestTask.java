package com.alibaba.sdk.android.oss.network;

import com.alibaba.sdk.android.oss.common.HttpMethod;
import com.alibaba.sdk.android.oss.internal.OSSRetryHandler;
import com.alibaba.sdk.android.oss.internal.RequestMessage;
import com.alibaba.sdk.android.oss.internal.ResponseMessage;
import com.alibaba.sdk.android.oss.internal.ResponseParser;
import com.alibaba.sdk.android.oss.model.OSSResult;
import java.util.HashMap;
import java.util.concurrent.Callable;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class OSSRequestTask<T extends OSSResult> implements Callable<T> {
    private OkHttpClient client;
    private ExecutionContext context;
    private int currentRetryCount = 0;
    private RequestMessage message;
    private ResponseParser<T> responseParser;
    private OSSRetryHandler retryHandler;

    /* renamed from: com.alibaba.sdk.android.oss.network.OSSRequestTask$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$sdk$android$oss$common$HttpMethod;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$com$alibaba$sdk$android$oss$common$HttpMethod = iArr;
            try {
                iArr[HttpMethod.POST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$oss$common$HttpMethod[HttpMethod.PUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$oss$common$HttpMethod[HttpMethod.GET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$oss$common$HttpMethod[HttpMethod.HEAD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$oss$common$HttpMethod[HttpMethod.DELETE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public OSSRequestTask(RequestMessage requestMessage, ResponseParser responseParser, ExecutionContext executionContext, int i2) {
        this.responseParser = responseParser;
        this.message = requestMessage;
        this.context = executionContext;
        this.client = executionContext.getClient();
        this.retryHandler = new OSSRetryHandler(i2);
    }

    private ResponseMessage buildResponseMessage(RequestMessage requestMessage, Response response) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setRequest(requestMessage);
        responseMessage.setResponse(response);
        HashMap map = new HashMap();
        Headers headers = response.headers();
        for (int i2 = 0; i2 < headers.size(); i2++) {
            map.put(headers.name(i2), headers.value(i2));
        }
        responseMessage.setHeaders(map);
        responseMessage.setStatusCode(response.code());
        responseMessage.setContentLength(response.body().getContentLength());
        responseMessage.setContent(response.body().byteStream());
        return responseMessage;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x031b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0194 A[Catch: Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:3:0x0004, B:5:0x000c, B:6:0x0019, B:8:0x003a, B:10:0x0043, B:12:0x0050, B:13:0x0062, B:15:0x0068, B:16:0x007f, B:70:0x01f9, B:27:0x00c6, B:28:0x00cc, B:29:0x00d2, B:33:0x00dd, B:35:0x00ea, B:63:0x0194, B:65:0x019c, B:66:0x01a7, B:68:0x01c8, B:69:0x01e5, B:38:0x0101, B:40:0x010b, B:43:0x0127, B:44:0x012e, B:45:0x012f, B:47:0x0137, B:50:0x0165, B:56:0x016f, B:57:0x0172, B:58:0x0173, B:60:0x017b, B:61:0x0189, B:11:0x004a, B:88:0x02c2, B:89:0x02c9), top: B:155:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01c6  */
    @Override // java.util.concurrent.Callable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public T call() throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 1095
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.oss.network.OSSRequestTask.call():com.alibaba.sdk.android.oss.model.OSSResult");
    }
}
