package com.easefun.polyv.businesssdk.vodplayer;

import android.text.TextUtils;
import com.easefun.polyv.businesssdk.PolyvCommonSDKClient;
import com.plv.business.model.video.PLVLogVideoLableVO;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
public class PolyvVodSDKClient extends PolyvCommonSDKClient {

    /* renamed from: a, reason: collision with root package name */
    private static final String f6539a = "PolyvVodSDKClient";

    /* renamed from: f, reason: collision with root package name */
    private static volatile PolyvVodSDKClient f6540f;

    /* renamed from: g, reason: collision with root package name */
    private static final char[] f6541g = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: b, reason: collision with root package name */
    private String f6542b = "";

    /* renamed from: c, reason: collision with root package name */
    private String f6543c = "";

    /* renamed from: d, reason: collision with root package name */
    private String f6544d = "";

    /* renamed from: e, reason: collision with root package name */
    private PLVLogVideoLableVO f6545e;

    private native byte[] getData(String str, String str2);

    public static PolyvVodSDKClient getInstance() {
        if (f6540f == null) {
            synchronized (PolyvVodSDKClient.class) {
                if (f6540f == null) {
                    f6540f = new PolyvVodSDKClient();
                }
            }
        }
        return f6540f;
    }

    public static String getSHA1(String str) throws NoSuchAlgorithmException {
        int length;
        byte[] bytes = str.getBytes();
        if (bytes != null && bytes.length > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(bytes);
                byte[] bArrDigest = messageDigest.digest();
                if (bArrDigest == null || (length = bArrDigest.length) <= 0) {
                    return null;
                }
                char[] cArr = new char[length << 1];
                int i2 = 0;
                for (byte b3 : bArrDigest) {
                    int i3 = i2 + 1;
                    char[] cArr2 = f6541g;
                    cArr[i2] = cArr2[(b3 >>> 4) & 15];
                    i2 = i3 + 1;
                    cArr[i3] = cArr2[b3 & 15];
                }
                return new String(cArr);
            } catch (NoSuchAlgorithmException e2) {
                PLVCommonLog.e(f6539a, e2.getMessage());
            }
        }
        return null;
    }

    private native byte[] getSign(String str, String str2);

    private native byte[] getTokenSign(String str);

    private native byte[] getVideoPoolIdSign(String str);

    private native byte[] getWebSign(String str, String str2);

    private native void initVideoSettings(String str, String str2, String str3, String str4);

    public static String md5(String str) {
        return PLVUtils.MD5(str);
    }

    private native void setLocalPath(String str);

    public native int download15xKey(String str, int i2);

    public native int download15xKeyToPath(String str, String str2, int i2);

    public native int downloadKey(String str, int i2);

    public native int downloadKeyToPath(String str, String str2, int i2);

    public String getDataToString(String str, String str2) {
        return new String(getData(str, str2));
    }

    public PLVLogVideoLableVO getPolyvLogVideoLable() {
        return this.f6545e;
    }

    public String getReadtoken() {
        return this.f6544d;
    }

    public native byte[] getSign1(String str);

    public String getUserId() {
        PLVLogVideoLableVO pLVLogVideoLableVO;
        String str = this.f6542b;
        return (!TextUtils.isEmpty(str) || (pLVLogVideoLableVO = this.f6545e) == null || TextUtils.isEmpty(pLVLogVideoLableVO.getVideoId()) || this.f6545e.getVideoId().length() <= 10) ? str : this.f6545e.getVideoId().substring(0, 10);
    }

    public String getWebSignToString(String str, String str2) {
        return new String(getWebSign(str, str2));
    }

    public String getWritetoken() {
        return this.f6543c;
    }

    @Override // com.easefun.polyv.businesssdk.PolyvCommonSDKClient
    public void initUA() {
        PLVCommonLog.d(f6539a, "initUA");
    }

    public native void setConfig(String str, String str2, String str3);

    public void setPolyvLogVideoLable(PLVLogVideoLableVO pLVLogVideoLableVO) {
        this.f6545e = pLVLogVideoLableVO;
    }

    public void setReadtoken(String str) {
        this.f6544d = str;
    }

    public void setUserId(String str) {
        this.f6542b = str;
    }

    public void setWritetoken(String str) {
        this.f6543c = str;
    }
}
