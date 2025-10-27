package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.service.XMPushService;
import com.yikaobang.yixue.R2;
import java.io.File;

/* loaded from: classes6.dex */
public class hr implements XMPushService.l {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f25076a = false;

    /* renamed from: a, reason: collision with other field name */
    private int f545a;

    /* renamed from: a, reason: collision with other field name */
    private Context f546a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f25077b;

    public hr(Context context) {
        this.f546a = context;
    }

    private String a(String str) {
        return "com.xiaomi.xmsf".equals(str) ? "1000271" : this.f546a.getSharedPreferences("pref_registered_pkg_names", 0).getString(str, null);
    }

    private void a(Context context) {
        this.f25077b = com.xiaomi.push.service.ao.a(context).a(ic.TinyDataUploadSwitch.a(), true);
        int iA = com.xiaomi.push.service.ao.a(context).a(ic.TinyDataUploadFrequency.a(), R2.dimen.dp_584);
        this.f545a = iA;
        this.f545a = Math.max(60, iA);
    }

    public static void a(boolean z2) {
        f25076a = z2;
    }

    private boolean a() {
        return Math.abs((System.currentTimeMillis() / 1000) - this.f546a.getSharedPreferences("mipush_extra", 4).getLong("last_tiny_data_upload_timestamp", -1L)) > ((long) this.f545a);
    }

    private boolean a(hv hvVar) {
        return (!as.b(this.f546a) || hvVar == null || TextUtils.isEmpty(a(this.f546a.getPackageName())) || !new File(this.f546a.getFilesDir(), "tiny_data.data").exists() || f25076a) ? false : true;
    }

    @Override // com.xiaomi.push.service.XMPushService.l
    /* renamed from: a, reason: collision with other method in class */
    public void mo495a() {
        a(this.f546a);
        if (this.f25077b && a()) {
            com.xiaomi.channel.commonutils.logger.b.m117a("TinyData TinyDataCacheProcessor.pingFollowUpAction ts:" + System.currentTimeMillis());
            hv hvVarA = hu.a(this.f546a).a();
            if (a(hvVarA)) {
                f25076a = true;
                hs.a(this.f546a, hvVarA);
            } else {
                com.xiaomi.channel.commonutils.logger.b.m117a("TinyData TinyDataCacheProcessor.pingFollowUpAction !canUpload(uploader) ts:" + System.currentTimeMillis());
            }
        }
    }
}
