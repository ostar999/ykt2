package com.hyphenate.chat.adapter;

import android.text.TextUtils;
import com.hyphenate.cloud.HttpClientManager;
import com.hyphenate.cloud.HttpResponse;
import com.hyphenate.util.EMLog;
import java.io.IOException;
import java.util.Map;
import org.apache.http.conn.ConnectTimeoutException;

/* loaded from: classes4.dex */
public class EMARHttpAPI {
    public static int REQUEST_FAILED_CODE = 408;
    public static final String TAG = "EMARHttpAPI";

    private static HttpResponse _httpExecute(String str, Map<String, String> map, String str2, String str3, int i2) throws IOException {
        return HttpClientManager.httpExecute(str, i2, map, str2, str3);
    }

    public static int download(String str, String str2, Map<String, String> map, int i2, StringBuilder sb, EMARHttpCallback eMARHttpCallback) {
        if (TextUtils.isEmpty(str2)) {
            return 400;
        }
        return HttpClientManager.downloadFile(str, i2, str2, map, sb, eMARHttpCallback);
    }

    public static int download(String str, String str2, Map<String, String> map, EMARHttpCallback eMARHttpCallback) {
        if (TextUtils.isEmpty(str2)) {
            return 400;
        }
        return HttpClientManager.downloadFile(str, str2, map, eMARHttpCallback);
    }

    public static int httpExecute(String str, Map<String, String> map, String str2, String str3, int i2, StringBuilder sb) {
        int i3 = REQUEST_FAILED_CODE;
        try {
            HttpResponse httpResponse_httpExecute = _httpExecute(str, map, str2, str3, i2);
            sb.append(httpResponse_httpExecute.content);
            i3 = httpResponse_httpExecute.code;
            EMLog.d(TAG, "httpExecute code: " + i3);
            return i3;
        } catch (ConnectTimeoutException unused) {
            EMLog.e(TAG, "ConnectTimeoutException");
            EMLog.e(TAG, "can't catch exceptions");
            return i3;
        } catch (Exception e2) {
            EMLog.e(TAG, e2.getClass().getSimpleName() + ": " + e2.getMessage());
            e2.printStackTrace();
            EMLog.e(TAG, "can't catch exceptions");
            return i3;
        }
    }

    public static int upload(String str, String str2, String str3, Map<String, String> map, int i2, StringBuilder sb, EMARHttpCallback eMARHttpCallback) {
        if (TextUtils.isEmpty(str)) {
            return 400;
        }
        EMLog.d(TAG, "upload uri = " + str + " remoteFilePath = " + str2);
        return HttpClientManager.uploadFile(str, str2, i2, str3, map, sb, eMARHttpCallback);
    }
}
