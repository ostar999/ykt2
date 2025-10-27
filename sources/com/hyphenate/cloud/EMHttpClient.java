package com.hyphenate.cloud;

import android.util.Pair;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.core.EMChatConfigPrivate;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMHttpClient {
    public static String DELETE = "DELETE";
    public static String GET = "GET";
    public static String POST = "POST";
    public static String PUT = "PUT";
    private static final String TAG = "EMHttpClient";
    private static EMHttpClient instance;
    private EMChatConfigPrivate configPrivate = null;

    private EMHttpClient() {
    }

    public static synchronized EMHttpClient getInstance() {
        if (instance == null) {
            instance = new EMHttpClient();
        }
        return instance;
    }

    public EMChatConfigPrivate chatConfig() {
        return this.configPrivate;
    }

    public void downloadFile(final String str, final String str2, final Map<String, String> map, final EMCloudOperationCallback eMCloudOperationCallback) {
        new Thread() { // from class: com.hyphenate.cloud.EMHttpClient.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                String message;
                try {
                    new a().a(str, str2, map, eMCloudOperationCallback);
                } catch (Exception e2) {
                    EMCloudOperationCallback eMCloudOperationCallback2 = eMCloudOperationCallback;
                    if (eMCloudOperationCallback2 != null) {
                        if (e2.getMessage() != null) {
                            message = e2.getMessage();
                        } else {
                            message = "failed to download the file : " + str;
                        }
                        eMCloudOperationCallback2.onError(message);
                    }
                }
            }
        }.start();
    }

    public HttpResponse httpExecute(String str, Map<String, String> map, String str2, String str3) throws IOException {
        return HttpClientManager.httpExecute(str, map, str2, str3);
    }

    public void onInit(EMChatConfigPrivate eMChatConfigPrivate) {
        this.configPrivate = eMChatConfigPrivate;
    }

    public Pair<Integer, String> sendRequest(String str, Map<String, String> map, String str2, String str3) throws HyphenateException, IOException {
        return HttpClientManager.sendRequest(str, map, str2, str3);
    }

    public Pair<Integer, String> sendRequestWithToken(String str, String str2, String str3) throws HyphenateException {
        return HttpClientManager.sendRequestWithToken(str, str2, str3);
    }

    public void uploadFile(final String str, final String str2, final Map<String, String> map, final EMCloudOperationCallback eMCloudOperationCallback) {
        EMLog.d(TAG, "upload file :  localFilePath : " + str + " remoteUrl : " + str2);
        new Thread() { // from class: com.hyphenate.cloud.EMHttpClient.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                String message;
                try {
                    EMLog.d(EMHttpClient.TAG, "run HttpFileManager().uploadFile");
                    new a().a(str, str2, EMClient.getInstance().getOptions().getAppKey(), EMClient.getInstance().getCurrentUser(), map, eMCloudOperationCallback);
                } catch (Exception e2) {
                    EMCloudOperationCallback eMCloudOperationCallback2 = eMCloudOperationCallback;
                    if (eMCloudOperationCallback2 != null) {
                        if (e2.getMessage() != null) {
                            message = e2.getMessage();
                        } else {
                            message = "failed to upload the file : " + str2;
                        }
                        eMCloudOperationCallback2.onError(message);
                    }
                }
            }
        }.start();
    }
}
