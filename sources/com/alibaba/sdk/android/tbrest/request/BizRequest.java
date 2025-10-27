package com.alibaba.sdk.android.tbrest.request;

import android.content.Context;
import android.content.pm.PackageManager;
import com.alibaba.sdk.android.tbrest.SendService;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.alibaba.sdk.android.tbrest.utils.ByteUtils;
import com.alibaba.sdk.android.tbrest.utils.GzipUtils;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.ut.device.UTDevice;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes2.dex */
public class BizRequest {
    private static final byte FLAGS_GET_CONFIG = 32;
    private static final byte FLAGS_GZIP = 1;
    private static final byte FLAGS_GZIP_FLUSH_DIC = 2;
    private static final byte FLAGS_KEEP_ALIVE = 8;
    private static final byte FLAGS_NO_GZIP = 0;
    private static final byte FLAGS_REAL_TIME_DEBUG = 16;
    private static final int HEAD_LENGTH = 8;
    private static final int PAYLOAD_MAX_LENGTH = 16777216;
    private static long mReceivedDataLen = 0;
    static String mResponseAdditionalData = null;
    static boolean needConfigByResponse = false;

    public static String getHead(SendService sendService, String str, Context context) {
        String str2;
        String str3 = sendService.appVersion;
        if (str3 == null) {
            str3 = "";
        }
        try {
            str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            str2 = "Unknown";
        }
        String str4 = sendService.channel;
        String str5 = String.format("ak=%s&av=%s&avsys=%s&c=%s&d=%s&sv=%s", str, str3, str2, str4 != null ? str4 : "", UTDevice.getUtdid(context), RestConstants.UT_SDK_VRESION);
        LogUtil.i("send url :" + str5);
        return str5;
    }

    public static byte[] getPackRequest(Context context, SendService sendService, Map<String, String> map) throws Exception {
        return getPackRequest(sendService, sendService.appKey, context, map, 1);
    }

    public static byte[] getPackRequestByRealtime(Context context, SendService sendService, Map<String, String> map) throws Exception {
        return getPackRequest(sendService, sendService.appKey, context, map, 2);
    }

    private static byte[] getPayload(SendService sendService, String str, Context context, Map<String, String> map) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String head = getHead(sendService, str, context);
        if (head == null || head.length() <= 0) {
            byteArrayOutputStream.write(ByteUtils.intToBytes2(0));
        } else {
            byteArrayOutputStream.write(ByteUtils.intToBytes2(head.getBytes().length));
            byteArrayOutputStream.write(head.getBytes());
        }
        if (map != null && map.size() > 0) {
            for (String str2 : map.keySet()) {
                byteArrayOutputStream.write(ByteUtils.intToBytes4(Integer.valueOf(str2).intValue()));
                String str3 = map.get(str2);
                if (str3 != null) {
                    byteArrayOutputStream.write(ByteUtils.intToBytes4(str3.getBytes().length));
                    byteArrayOutputStream.write(str3.getBytes());
                } else {
                    byteArrayOutputStream.write(ByteUtils.intToBytes4(0));
                }
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e2) {
            LogUtil.e(e2.toString());
        }
        return byteArray;
    }

    public static int parseResult(byte[] bArr) throws Throwable {
        int iBytesToInt = -1;
        if (bArr == null || bArr.length < 12) {
            LogUtil.e("recv errCode UNKNOWN_ERROR");
        } else {
            mReceivedDataLen = bArr.length;
            if (ByteUtils.bytesToInt(bArr, 1, 3) + 8 != bArr.length) {
                LogUtil.e("recv len error");
            } else {
                boolean z2 = 1 == (bArr[5] & 1);
                iBytesToInt = ByteUtils.bytesToInt(bArr, 8, 4);
                int length = bArr.length - 12 >= 0 ? bArr.length - 12 : 0;
                if (length <= 0) {
                    mResponseAdditionalData = null;
                } else if (z2) {
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArr, 12, bArr2, 0, length);
                    byte[] bArrUnGzip = GzipUtils.unGzip(bArr2);
                    mResponseAdditionalData = new String(bArrUnGzip, 0, bArrUnGzip.length);
                } else {
                    mResponseAdditionalData = new String(bArr, 12, length);
                }
            }
        }
        LogUtil.d("errCode:" + iBytesToInt);
        return iBytesToInt;
    }

    public static byte[] getPackRequest(SendService sendService, String str, Context context, Map<String, String> map) throws Exception {
        return getPackRequest(sendService, str, context, map, 1);
    }

    public static byte[] getPackRequestByRealtime(SendService sendService, String str, Context context, Map<String, String> map) throws Exception {
        return getPackRequest(sendService, str, context, map, 2);
    }

    public static byte[] getPackRequest(SendService sendService, String str, Context context, Map<String, String> map, int i2) throws Exception {
        byte[] bArrGzip = GzipUtils.gzip(getPayload(sendService, str, context, map));
        if (bArrGzip == null || bArrGzip.length >= 16777216) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(1);
        byteArrayOutputStream.write(ByteUtils.intToBytes3(bArrGzip.length));
        byteArrayOutputStream.write(i2);
        byte b3 = (byte) 9;
        if (needConfigByResponse) {
            b3 = (byte) (b3 | 32);
        }
        byteArrayOutputStream.write(b3);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(bArrGzip);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e2) {
            LogUtil.e(e2.toString());
        }
        return byteArray;
    }
}
