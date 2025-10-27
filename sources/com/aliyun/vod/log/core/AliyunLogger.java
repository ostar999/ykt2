package com.aliyun.vod.log.core;

import android.content.Context;
import android.util.Log;
import com.aliyun.vod.common.global.AliyunTag;
import com.aliyun.vod.common.global.Version;
import com.aliyun.vod.common.utils.DeviceUtils;
import com.aliyun.vod.common.utils.ManifestUtils;
import com.aliyun.vod.common.utils.ProcessUtil;
import com.aliyun.vod.log.util.UUIDGenerator;
import com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback;
import com.aliyun.vod.qupaiokhttp.HttpRequest;
import java.lang.ref.WeakReference;
import java.util.Map;
import okhttp3.Headers;

/* loaded from: classes2.dex */
public class AliyunLogger {
    private static final String KEY_SHARED_PREFERENCE = "aliyun_svideo_global_info";
    private static final String TAG = "AliyunLogger";
    private String appVersion;
    private WeakReference<Context> mContextRef;
    private LogService mLogService;
    private String mRequestID = null;
    private boolean canModify = true;
    private boolean productSVideo = false;
    private String domainRegion = null;
    private LogService mHttpService = new LogService(String.valueOf(System.currentTimeMillis()));

    public AliyunLogger(LogService logService) {
        this.mLogService = logService;
    }

    private void initGlobalInfo() {
        Context context = this.mContextRef.get();
        if (context == null) {
            Log.w(TAG, "context release??");
        } else if (AliyunLogCommon.APPLICATION_ID == null) {
            AliyunLogCommon.APPLICATION_ID = context.getPackageName();
            AliyunLogCommon.APPLICATION_NAME = ManifestUtils.getAppName(context);
        }
        if (AliyunLogCommon.UUID == null) {
            AliyunLogCommon.UUID = UUIDGenerator.generateUUID();
        }
    }

    private void setDomainRegion(String str) {
        this.domainRegion = str;
    }

    public void destroy() {
        LogService logService = this.mLogService;
        if (logService != null) {
            logService.quit();
            this.mLogService = null;
        }
        LogService logService2 = this.mHttpService;
        if (logService2 != null) {
            logService2.quit();
            this.mHttpService = null;
        }
    }

    public LogService getLogService() {
        return this.mLogService;
    }

    public String getRequestID() {
        return this.mRequestID;
    }

    public void init(Context context) {
        this.mContextRef = new WeakReference<>(context.getApplicationContext());
        initGlobalInfo();
    }

    public void pushLog(final Map<String, String> map, final String str, final String str2, final String str3, final String str4, final int i2, final String str5, final String str6) {
        final Context context = this.mContextRef.get();
        if (ProcessUtil.isMainThread()) {
            this.mHttpService.execute(new Runnable() { // from class: com.aliyun.vod.log.core.AliyunLogger.1
                @Override // java.lang.Runnable
                public void run() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(AliyunLogCommon.generateDomainWithRegion(AliyunLogger.this.domainRegion));
                    sb.append(AliyunLogger.this.productSVideo ? "svideo" : str5);
                    sb.append(AliyunLogCommon.LOG_PUSH_TRACK_APIVERSION);
                    Map map2 = map;
                    String str7 = AliyunLogger.this.productSVideo ? "svideo" : str;
                    String str8 = str2;
                    String str9 = str3;
                    String str10 = str4;
                    int i3 = i2;
                    String str11 = str6;
                    if (str11 == null) {
                        str11 = AliyunLogger.this.mRequestID;
                    }
                    sb.append(AliyunLogParam.generatePushParams(map2, str7, str8, str9, str10, i3, str11, DeviceUtils.getNetWorkType(context), AliyunLogger.this.canModify ? Version.VERSION : AliyunLogger.this.appVersion));
                    HttpRequest.get(sb.toString(), new BaseHttpRequestCallback() { // from class: com.aliyun.vod.log.core.AliyunLogger.1.1
                        @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                        public void onFailure(int i4, String str12) {
                            super.onFailure(i4, str12);
                            Log.d(AliyunTag.TAG, "Push log failure, error Code " + i4 + ", msg:" + str12);
                        }

                        @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                        public void onSuccess(Headers headers, Object obj) {
                            super.onSuccess(headers, obj);
                            Log.d(AliyunTag.TAG, "Push log success");
                        }
                    });
                }
            });
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(AliyunLogCommon.generateDomainWithRegion(this.domainRegion));
        sb.append(this.productSVideo ? "svideo" : str5);
        sb.append(AliyunLogCommon.LOG_PUSH_TRACK_APIVERSION);
        sb.append(AliyunLogParam.generatePushParams(map, this.productSVideo ? "svideo" : str, str2, str3, str4, i2, str6 == null ? this.mRequestID : str6, DeviceUtils.getNetWorkType(context), this.canModify ? Version.VERSION : this.appVersion));
        HttpRequest.get(sb.toString(), new BaseHttpRequestCallback() { // from class: com.aliyun.vod.log.core.AliyunLogger.2
            @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
            public void onFailure(int i3, String str7) {
                super.onFailure(i3, str7);
                Log.d(AliyunTag.TAG, "Push log failure, error Code " + i3 + ", msg:" + str7);
            }

            @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
            public void onSuccess(Headers headers, Object obj) {
                super.onSuccess(headers, obj);
                Log.d(AliyunTag.TAG, "Push log success");
            }
        });
    }

    public void setAppVersion(String str) {
        this.appVersion = str;
    }

    public void setProductSVideo(boolean z2) {
        this.productSVideo = z2;
    }

    public void setRequestID(String str, boolean z2) {
        this.mRequestID = str;
        this.canModify = z2;
    }

    public void updateRequestID() {
        if (this.canModify) {
            this.mRequestID = UUIDGenerator.generateRequestID();
        }
    }
}
