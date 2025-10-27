package com.xiaomi.push;

import android.annotation.TargetApi;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Iterator;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes6.dex */
public class ej extends ei {

    /* renamed from: a, reason: collision with root package name */
    private boolean f24756a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f24757b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f24758c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f24759d;

    public ej(Context context, int i2, boolean z2, boolean z3, boolean z4, boolean z5) {
        super(context, i2);
        this.f24756a = z2;
        this.f24757b = z3;
        if (n.d()) {
            this.f24757b = false;
        }
        this.f24758c = z4;
        this.f24759d = z5;
    }

    private String a(Context context) {
        if (!this.f24759d) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            if (n.d()) {
                return "";
            }
            Iterator<String> it = j.m587a(context).iterator();
            String str = "";
            while (it.hasNext()) {
                String next = it.next();
                if (!TextUtils.isEmpty(str)) {
                    str = str + com.alipay.sdk.util.h.f3376b;
                }
                str = str + ay.a(next) + "," + ay.b(next);
            }
            return str;
        } catch (Throwable unused) {
            return "";
        }
    }

    private String b() {
        if (!this.f24756a) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            String strC = c();
            if (TextUtils.isEmpty(strC)) {
                return "";
            }
            return ay.a(strC) + "," + ay.b(strC);
        } catch (Throwable unused) {
            return "";
        }
    }

    @TargetApi(9)
    private String c() throws SocketException {
        if (n.d() || !TextUtils.isEmpty("")) {
            return "";
        }
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if ("wlan0".equalsIgnoreCase(networkInterface.getName())) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b3 : hardwareAddress) {
                        sb.append(String.format("%02x:", Byte.valueOf(b3)));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString().toUpperCase();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return "";
    }

    private String d() {
        if (!this.f24757b) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            String subscriberId = ((TelephonyManager) ((ei) this).f339a.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSubscriberId();
            if (TextUtils.isEmpty(subscriberId)) {
                return "";
            }
            return ay.a(subscriberId) + "," + ay.b(subscriberId);
        } catch (Throwable unused) {
            return "";
        }
    }

    private String e() {
        if (!this.f24758c) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            String simSerialNumber = ((TelephonyManager) ((ei) this).f339a.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimSerialNumber();
            if (TextUtils.isEmpty(simSerialNumber)) {
                return "";
            }
            return ay.a(simSerialNumber) + "," + ay.b(simSerialNumber);
        } catch (Throwable unused) {
            return "";
        }
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 13;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.DeviceBaseInfo;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        return b() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + d() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + e() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + a(((ei) this).f339a);
    }
}
