package com.hyphenate.cloud;

import android.content.Context;
import android.text.TextUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.cloud.HttpClientManager;
import com.hyphenate.util.EMLog;
import java.io.File;
import java.util.Map;

/* loaded from: classes4.dex */
class a extends CloudFileManager {

    /* renamed from: b, reason: collision with root package name */
    private static final int f8626b = 20;

    /* renamed from: a, reason: collision with root package name */
    private Context f8627a;

    public a() {
        this.f8627a = EMClient.getInstance().getContext();
    }

    public a(Context context) {
        this.f8627a = context.getApplicationContext();
    }

    public a(Context context, String str) {
        this.f8627a = context.getApplicationContext();
    }

    private void a(String str, String str2, String str3, String str4, Map<String, String> map, final CloudOperationCallback cloudOperationCallback, int i2, boolean z2) {
        EMLog.d("CloudFileManager", "sendFileToServerHttpWithCountDown .....");
        if (new File(str).isFile()) {
            new HttpClientManager.Builder(this.f8627a).uploadFile(str).setUrl(str2).setConnectTimeout(HttpClientConfig.getTimeout(map)).setParam("app", str3).setParam("id", str4).setHeaders(map).setRetryTimes(i2).execute(new HttpCallback() { // from class: com.hyphenate.cloud.a.1
                @Override // com.hyphenate.cloud.HttpCallback
                public void onError(int i3, String str5) {
                    CloudOperationCallback cloudOperationCallback2 = cloudOperationCallback;
                    if (cloudOperationCallback2 != null) {
                        cloudOperationCallback2.onError(str5);
                    }
                }

                @Override // com.hyphenate.cloud.HttpCallback
                public void onProgress(long j2, long j3) {
                    CloudOperationCallback cloudOperationCallback2 = cloudOperationCallback;
                    if (cloudOperationCallback2 != null) {
                        cloudOperationCallback2.onProgress((int) ((j3 * 100) / j2));
                    }
                }

                @Override // com.hyphenate.cloud.HttpCallback
                public void onSuccess(String str5) {
                    CloudOperationCallback cloudOperationCallback2 = cloudOperationCallback;
                    if (cloudOperationCallback2 != null) {
                        cloudOperationCallback2.onSuccess(str5);
                    }
                }
            });
        } else {
            EMLog.e("CloudFileManager", "Source file doesn't exist");
            cloudOperationCallback.onError("Source file doesn't exist");
        }
    }

    private void a(String str, String str2, Map<String, String> map, final CloudOperationCallback cloudOperationCallback, int i2) {
        if (str == null || str.length() <= 0) {
            cloudOperationCallback.onError("invalid remoteUrl");
            return;
        }
        int timeout = HttpClientConfig.getTimeout(map);
        String fileRemoteUrl = HttpClientConfig.getFileRemoteUrl(str);
        EMLog.d("CloudFileManager", "remoteUrl:" + fileRemoteUrl + " localFilePath:" + str2);
        new HttpClientManager.Builder(this.f8627a).downloadFile(str2).setUrl(fileRemoteUrl).setConnectTimeout(timeout).setHeaders(map).setRetryTimes(i2).execute(new HttpCallback() { // from class: com.hyphenate.cloud.a.3
            @Override // com.hyphenate.cloud.HttpCallback
            public void onError(int i3, String str3) {
                CloudOperationCallback cloudOperationCallback2 = cloudOperationCallback;
                if (cloudOperationCallback2 != null) {
                    cloudOperationCallback2.onError(str3);
                }
            }

            @Override // com.hyphenate.cloud.HttpCallback
            public void onProgress(long j2, long j3) {
                CloudOperationCallback cloudOperationCallback2 = cloudOperationCallback;
                if (cloudOperationCallback2 != null) {
                    cloudOperationCallback2.onProgress((int) ((j3 * 100) / j2));
                }
            }

            @Override // com.hyphenate.cloud.HttpCallback
            public void onSuccess(String str3) {
                CloudOperationCallback cloudOperationCallback2 = cloudOperationCallback;
                if (cloudOperationCallback2 != null) {
                    cloudOperationCallback2.onSuccess(str3);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, String str2, String str3, String str4, Map<String, String> map, CloudOperationCallback cloudOperationCallback) {
        a(str, str2, str3, str4, map, cloudOperationCallback, -1, false);
    }

    public void a(String str, String str2, String str3, String str4, Map<String, String> map, CloudOperationCallback cloudOperationCallback) {
        try {
            b(str, str2, str3, str4, map, cloudOperationCallback);
        } catch (Exception e2) {
            EMLog.e("CloudFileManager", "uploadFile error:" + e2.toString());
            cloudOperationCallback.onError(e2.toString());
        }
    }

    public void a(String str, String str2, Map<String, String> map, CloudOperationCallback cloudOperationCallback) {
        try {
            a(str, str2, map, cloudOperationCallback, 20);
        } catch (Exception e2) {
            String string = "failed to download file : " + str;
            if (e2.toString() != null) {
                string = e2.toString();
            }
            if (cloudOperationCallback != null) {
                cloudOperationCallback.onError(string);
            }
        }
    }

    @Override // com.hyphenate.cloud.CloudFileManager
    public void downloadFile(String str, String str2, String str3, Map<String, String> map, CloudOperationCallback cloudOperationCallback) {
        if (!TextUtils.isEmpty(str)) {
            a(HttpClientConfig.getFileRemoteUrl(str), str2, map, cloudOperationCallback);
            return;
        }
        if (cloudOperationCallback != null) {
            cloudOperationCallback.onError("remote file path is null or empty");
        }
        EMLog.e("CloudFileManager", "remote file path is null or empty");
    }

    @Override // com.hyphenate.cloud.CloudFileManager
    public void uploadFileInBackground(final String str, final String str2, final String str3, final String str4, final Map<String, String> map, final CloudOperationCallback cloudOperationCallback) {
        new Thread() { // from class: com.hyphenate.cloud.a.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    a.this.b(str, str2, str3, str4, map, cloudOperationCallback);
                } catch (Exception e2) {
                    if (e2.toString() != null) {
                        EMLog.e("CloudFileManager", e2.toString());
                        cloudOperationCallback.onError(e2.toString());
                        return;
                    }
                    cloudOperationCallback.onError("failed to upload the file : " + str + " remote path : " + str2);
                }
            }
        }.start();
    }
}
