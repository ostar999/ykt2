package com.aliyun.vod.log.report;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.aliyun.auth.core.AliyunVodKey;
import com.aliyun.vod.common.global.AliyunTag;
import com.aliyun.vod.common.global.Version;
import com.aliyun.vod.common.utils.DeviceUtils;
import com.aliyun.vod.common.utils.MD5Util;
import com.aliyun.vod.common.utils.ManifestUtils;
import com.aliyun.vod.common.utils.ProcessUtil;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.aliyun.vod.log.util.UUIDGenerator;
import com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback;
import com.aliyun.vod.qupaiokhttp.HttpRequest;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import okhttp3.Headers;

/* loaded from: classes2.dex */
public class AliyunUploadProgressReporter {
    private static final String KEY_SHARED_PREFERENCE = "aliyun_svideo_global_info";
    private static final String TAG = "AliyunUploadProgressReporter";
    private String mTerminalType;
    private String mAction = "ReportUploadProgress";
    private String mSource = "AndroidSDK";
    private String mClientId = AliyunLogCommon.UUID;
    private String mBusinessType = "UploadVideo";
    private String mDeviceModel = Build.MODEL;
    private String mAppVersion = Version.VERSION;
    private String mAuthTimestamp = "";
    private String mAuthInfo = "";
    private String mFileName = "";
    private Long mFileSize = 0L;
    private String mFileCreateTime = "";
    private String mFileHash = "";
    private Float mUploadRatio = Float.valueOf(0.0f);
    private String mUploadId = "todo";
    private Integer mDonePartsCount = 0;
    private Integer mTotalPart = 0;
    private Long mPartSize = 0L;

    @Deprecated
    private String mUploadPoint = "todo";

    @Deprecated
    private Long mUserId = -1L;
    private String mVideoId = "";
    private String mUploadAddress = "todo";
    private String INNER_SECRET_KEY = "FqQ^jDLpi0PVZ74A";
    private String mDomainRegion = null;

    public AliyunUploadProgressReporter(Context context) {
        this.mTerminalType = "APhone";
        initGlobalInfo(context);
        this.mTerminalType = DeviceUtils.isTabletDevice(context) ? "APad" : "APhone";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRun(String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String strGenerateDomainWithRegion = AliyunReportParam.generateDomainWithRegion(this.mDomainRegion);
        String strGenerateUploadProgressParams = AliyunReportParam.generateUploadProgressParams(generatePublicParams(), str);
        String str2 = TAG;
        Log.d(str2, "domain : " + strGenerateDomainWithRegion);
        Log.d(str2, "params : " + strGenerateUploadProgressParams);
        HttpRequest.get(strGenerateDomainWithRegion + strGenerateUploadProgressParams, new BaseHttpRequestCallback() { // from class: com.aliyun.vod.log.report.AliyunUploadProgressReporter.2
            @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
            public void onFailure(int i2, String str3) {
                super.onFailure(i2, str3);
                Log.d(AliyunTag.TAG, "Push log failure, error Code " + i2 + ", msg:" + str3);
            }

            @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
            public void onSuccess(Headers headers, Object obj) {
                super.onSuccess(headers, obj);
                Log.d(AliyunTag.TAG, "Push log success");
            }
        });
    }

    private Map<String, String> generatePublicParams() {
        HashMap map = new HashMap();
        map.put(AliyunVodKey.KEY_VOD_ACTION, this.mAction);
        map.put("Source", this.mSource);
        map.put("ClientId", this.mClientId);
        map.put("BusinessType", this.mBusinessType);
        map.put("TerminalType", this.mTerminalType);
        map.put("DeviceModel", this.mDeviceModel);
        map.put("AppVersion", this.mAppVersion);
        map.put("AuthTimestamp", this.mAuthTimestamp);
        map.put("AuthInfo", this.mAuthInfo);
        map.put(AliyunVodKey.KEY_VOD_FILENAME, this.mFileName);
        map.put(AliyunVodKey.KEY_VOD_FILESIZE, String.valueOf(this.mFileSize));
        map.put("FileCreateTime", this.mFileCreateTime);
        map.put("FileHash", this.mFileHash);
        map.put("UploadRatio", String.valueOf(this.mUploadRatio));
        map.put("UploadId", this.mUploadId);
        map.put("DonePartsCount", String.valueOf(this.mDonePartsCount));
        map.put("TotalPart", String.valueOf(this.mTotalPart));
        map.put("PartSize", String.valueOf(this.mPartSize));
        map.put("UploadPoint", this.mUploadPoint);
        if (!TextUtils.isEmpty(this.mVideoId)) {
            map.put(AliyunVodKey.KEY_VOD_VIDEOID, this.mVideoId);
        }
        if (!TextUtils.isEmpty(this.mUploadAddress)) {
            map.put("UploadAddress", this.mUploadAddress);
        }
        return map;
    }

    private void initGlobalInfo(Context context) {
        if (context != null) {
            if (AliyunLogCommon.APPLICATION_ID == null) {
                AliyunLogCommon.APPLICATION_ID = context.getPackageName();
                AliyunLogCommon.APPLICATION_NAME = ManifestUtils.getAppName(context);
            }
            if (AliyunLogCommon.UUID == null) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCE, 0);
                if (sharedPreferences.contains(AliyunLogKey.KEY_UUID)) {
                    AliyunLogCommon.UUID = sharedPreferences.getString(AliyunLogKey.KEY_UUID, null);
                }
                if (AliyunLogCommon.UUID == null) {
                    AliyunLogCommon.UUID = UUIDGenerator.generateUUID();
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.putString(AliyunLogKey.KEY_UUID, AliyunLogCommon.UUID);
                    editorEdit.commit();
                }
                this.mClientId = AliyunLogCommon.UUID;
            }
        }
    }

    public void pushUploadProgress(final String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Log.d(TAG, "pushUploadProgress");
        setAuthInfo();
        if (ProcessUtil.isMainThread()) {
            Executors.newSingleThreadExecutor().submit(new Runnable() { // from class: com.aliyun.vod.log.report.AliyunUploadProgressReporter.1
                @Override // java.lang.Runnable
                public void run() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
                    AliyunUploadProgressReporter.this.doRun(str);
                }
            });
        } else {
            doRun(str);
        }
    }

    public void setAuthInfo() {
        this.mAuthInfo = MD5Util.encryptToHexStr(this.mClientId + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.INNER_SECRET_KEY + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.mAuthTimestamp);
    }

    public void setAuthTimestamp(String str) {
        this.mAuthTimestamp = str;
    }

    public void setDomainRegion(String str) {
        this.mDomainRegion = str;
    }

    public void setDonePartsCount(Integer num) {
        this.mDonePartsCount = num;
    }

    public void setFileCreateTime(String str) {
        this.mFileCreateTime = str;
    }

    public void setFileHash(String str) {
        this.mFileHash = str;
    }

    public void setFileName(String str) {
        this.mFileName = str;
    }

    public void setFileSize(Long l2) {
        this.mFileSize = l2;
    }

    public void setPartSize(Long l2) {
        this.mPartSize = l2;
    }

    public void setTotalPart(Integer num) {
        this.mTotalPart = num;
    }

    public void setUploadAddress(String str) {
        this.mUploadAddress = str;
    }

    public void setUploadId(String str) {
        this.mUploadId = str;
    }

    @Deprecated
    public void setUploadPoint(String str) {
        this.mUploadPoint = str;
    }

    public void setUploadRatio(Float f2) {
        this.mUploadRatio = f2;
    }

    @Deprecated
    public void setUserId(Long l2) {
        this.mUserId = l2;
    }

    public void setVideoId(String str) {
        this.mVideoId = str;
    }
}
