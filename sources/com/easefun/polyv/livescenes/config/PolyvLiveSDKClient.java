package com.easefun.polyv.livescenes.config;

import android.content.Context;
import com.easefun.polyv.businesssdk.PolyvCommonSDKClient;
import com.plv.foundationsdk.PLVUAClient;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.socket.socketio.PLVSocketIOClient;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.tencent.bugly.crashreport.CrashReport;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public class PolyvLiveSDKClient extends PolyvCommonSDKClient {
    private static final String POLYV_LIVE_ANDROID_SDK = "polyv-android-live-sdk-1.10.1.1-20220909";
    private static final String POLYV_LIVE_ANDROID_SDK_NAME = "polyv-android-live-sdk";
    private static final String POLYV_LIVE_ANDROID_VERSION = "1.10.1.1-20220909";
    private static volatile PolyvLiveSDKClient instance;
    private String appId;
    private String appSecret;
    private String channelId;
    private String currentStream;
    private String streamId;
    private String userId;

    public static String dataToScrData(String str, String str2, String str3) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, new SecretKeySpec(str.getBytes(), "AES"), new IvParameterSpec(str2.getBytes()));
        return new String(cipher.doFinal(ConvertUtils.hexString2Bytes(str3)), "UTF-8");
    }

    private native byte[] getChannelData(String str);

    public static PolyvLiveSDKClient getInstance() {
        if (instance == null) {
            synchronized (PolyvLiveSDKClient.class) {
                if (instance == null) {
                    instance = new PolyvLiveSDKClient();
                }
            }
        }
        return instance;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getCurrentStream() {
        return this.currentStream;
    }

    public String getPolyvLiveAndroidSdk() {
        return POLYV_LIVE_ANDROID_SDK;
    }

    public String getPolyvLiveAndroidSdkName() {
        return POLYV_LIVE_ANDROID_SDK_NAME;
    }

    public String getPolyvLiveAndroidVersion() {
        return "1.10.1.1-20220909";
    }

    public String getStreamId() {
        return this.streamId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void initCrashReport(Context context) {
        CrashReport.initCrashReport(context, "32945280d8", false);
        CrashReport.putUserData(context, "PolyvAndroidLiveSDK", POLYV_LIVE_ANDROID_SDK);
    }

    @Override // com.easefun.polyv.businesssdk.PolyvCommonSDKClient
    public void initUA() {
        PLVUAClient.generateUserAgent(PLVUtils.getPid(), getPolyvLiveAndroidSdk());
    }

    public void setAppIdSecret(String str, String str2, String str3) {
        this.userId = str;
        this.appId = str2;
        this.appSecret = str3;
        PLVSocketIOClient.getInstance().setAccountUserId(str);
        PLVSocketIOClient.getInstance().setAccountAppId(str2);
        PLVSocketIOClient.getInstance().setAccountAppSecret(str3);
        PLVSocketIOClient.getInstance().setSdkVersion(getPolyvLiveAndroidVersion());
    }

    public void setChannelId(String str) {
        this.channelId = str;
        PLVSocketIOClient.getInstance().setChannelId(str);
    }

    public void setCurrentStream(String str) {
        this.currentStream = str;
    }

    public void setStreamId(String str) {
        this.streamId = str;
    }
}
