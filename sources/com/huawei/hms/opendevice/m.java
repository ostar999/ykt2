package com.huawei.hms.opendevice;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.support.log.HMSLog;
import java.util.Map;
import org.slf4j.Logger;

/* loaded from: classes4.dex */
public class m extends Thread {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Context f7918a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ String f7919b;

    public m(Context context, String str) {
        this.f7918a = context;
        this.f7919b = str;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws Throwable {
        if (!p.b()) {
            HMSLog.d("ReportAaidToken", "Not HW Phone.");
            return;
        }
        if (n.b(this.f7918a)) {
            return;
        }
        String strA = o.a(this.f7918a);
        if (TextUtils.isEmpty(strA)) {
            HMSLog.w("ReportAaidToken", "AAID is empty.");
            return;
        }
        if (!n.d(this.f7918a, strA, this.f7919b)) {
            HMSLog.d("ReportAaidToken", "This time need not report.");
            return;
        }
        String string = AGConnectServicesConfig.fromContext(this.f7918a).getString(TtmlNode.TAG_REGION);
        if (TextUtils.isEmpty(string)) {
            HMSLog.i("ReportAaidToken", "The data storage region is empty.");
            return;
        }
        String strA2 = e.a(this.f7918a, "com.huawei.hms.opendevicesdk", Logger.ROOT_LOGGER_NAME, null, string);
        if (TextUtils.isEmpty(strA2)) {
            return;
        }
        String strC = n.c(this.f7918a, strA, this.f7919b);
        n.b(this.f7918a, d.a(this.f7918a, strA2 + "/rest/appdata/v1/aaid/report", strC, (Map<String, String>) null), strA, this.f7919b);
    }
}
