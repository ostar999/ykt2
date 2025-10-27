package com.mobile.auth.gatewayauth.network;

import android.content.Context;
import android.net.Network;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.model.LimitConfig;
import com.mobile.auth.gatewayauth.model.popsdkconfig.ConfigData;
import com.mobile.auth.gatewayauth.model.popsdkconfig.SDKConfigRespone;
import com.mobile.auth.gatewayauth.model.popsdkconfig.UploadData;
import com.mobile.auth.v.e;
import com.nirvana.tools.jsoner.JsonType;
import java.io.IOException;

/* loaded from: classes4.dex */
public class RequestUtil {
    public static final String PUBLIC_SECKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLShWjAtxJv3g2VPIYOOAv4rnVDdLkdseKm7+KOkCBLV9SKY5oqksFaXcLZ+nRnjnczhze5eGKhevwliUyag6x96GyXI2WagKIoB7Uwl2byl0xB5bNvYzf+x/DKHTSoGJshU6shXWXcjGFq+mUiPhM3WGZoqdY+vvqOWD+tga8XQIDAQAB";
    private static final String SERVEL_URL = "https://dypnsapi.aliyuncs.com/?";

    /* renamed from: com.mobile.auth.gatewayauth.network.RequestUtil$1, reason: invalid class name */
    public static class AnonymousClass1 extends JsonType<SDKConfigRespone> {
    }

    /* renamed from: com.mobile.auth.gatewayauth.network.RequestUtil$2, reason: invalid class name */
    public static class AnonymousClass2 extends JsonType<UploadData> {
    }

    /* renamed from: com.mobile.auth.gatewayauth.network.RequestUtil$3, reason: invalid class name */
    public static class AnonymousClass3 extends JsonType<DispatchInfoRespone> {
    }

    /* renamed from: com.mobile.auth.gatewayauth.network.RequestUtil$4, reason: invalid class name */
    public static class AnonymousClass4 extends JsonType<DispatchInfoItem> {
    }

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    @SafeProtector
    private static native String assembleCustomizeToken(Context context, String str, String str2);

    @SafeProtector
    private static native String assembleToken(Context context, String str, String str2, String str3);

    public static native String getCmOpeatorInfo(String str, String str2);

    public static native String getCmOpeatorMsg(Context context, String str, Network network);

    private static native LimitConfig getConfig(ConfigData configData);

    public static native e getPrivateKey(Context context, String str, String str2);

    public static native String getSDKConfigByPop(String str, String str2);

    public static native DispatchInfoRespone queryDispatchInfo(Context context, String str, String str2, String str3, int i2);

    public static native String uploadUserTrackInfoByPop(String str, String str2) throws IOException;
}
