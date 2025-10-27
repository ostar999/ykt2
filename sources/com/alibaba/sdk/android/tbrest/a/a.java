package com.alibaba.sdk.android.tbrest.a;

import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.alibaba.sdk.android.tbrest.utils.MD5Utils;
import com.alibaba.sdk.android.tbrest.utils.RC4;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private byte[] f2874a = null;

    /* renamed from: c, reason: collision with root package name */
    private boolean f2875c;

    /* renamed from: h, reason: collision with root package name */
    private String f2876h;

    /* renamed from: i, reason: collision with root package name */
    private String f2877i;

    public a(String str, String str2, boolean z2) {
        this.f2876h = str;
        this.f2877i = str2;
        this.f2875c = z2;
    }

    public static String a(byte[] bArr, byte[] bArr2) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(bArr, mac.getAlgorithm()));
        return MD5Utils.toHexString(mac.doFinal(bArr2));
    }

    public String b(String str) {
        String str2;
        if (this.f2876h == null || (str2 = this.f2877i) == null) {
            LogUtil.e("There is no appkey,please check it!");
            return null;
        }
        if (str == null) {
            return null;
        }
        try {
            return this.f2875c ? a(str2.getBytes(), str.getBytes()) : a(a(), str.getBytes());
        } catch (Exception unused) {
            return "";
        }
    }

    private byte[] a() {
        if (this.f2874a == null) {
            this.f2874a = RC4.rc4(new byte[]{66, 37, 42, -119, 118, -104, -30, 4, -95, 15, -26, -12, -75, -102, 71, 23, -3, -120, -1, -57, 42, 99, -16, -101, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, -74, 93, -114, 112, -26, -24, -24});
        }
        return this.f2874a;
    }
}
