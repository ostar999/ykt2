package com.xiaomi.push;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes6.dex */
public class ek extends ei {

    /* renamed from: a, reason: collision with root package name */
    private boolean f24760a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f24761b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f24762c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f24763d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f24764e;

    public ek(Context context, int i2, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        super(context, i2);
        this.f24760a = z2;
        this.f24761b = z3;
        this.f24762c = z4;
        this.f24763d = z5;
        this.f24764e = z6;
    }

    private String b() {
        if (!this.f24760a) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) ((ei) this).f339a.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.heightPixels + "," + displayMetrics.widthPixels;
        } catch (Throwable unused) {
            return "";
        }
    }

    private String c() {
        if (!this.f24761b) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable unused) {
            return "";
        }
    }

    private String d() {
        if (!this.f24762c) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            return String.valueOf(Build.VERSION.SDK_INT);
        } catch (Throwable unused) {
            return "";
        }
    }

    private String e() {
        if (!this.f24763d) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            return Settings.Secure.getString(((ei) this).f339a.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } catch (Throwable unused) {
            return "";
        }
    }

    private String f() {
        if (!this.f24764e) {
            return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
        }
        try {
            return ((TelephonyManager) ((ei) this).f339a.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimOperator();
        } catch (Throwable unused) {
            return "";
        }
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 3;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.DeviceInfoV2;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        return b() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + c() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + d() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + e() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + f();
    }
}
