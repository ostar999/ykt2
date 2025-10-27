package com.tencent.tbs.one.impl.e.a;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.impl.common.Statistics;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.common.f;
import com.tencent.tbs.one.impl.e.e;
import com.tencent.tbs.one.optional.TBSOneRuntimeExtension;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class a extends com.tencent.tbs.one.impl.a.b<e<File>> {

    /* renamed from: b, reason: collision with root package name */
    public final Context f22056b;

    /* renamed from: c, reason: collision with root package name */
    public final String f22057c;

    /* renamed from: d, reason: collision with root package name */
    public final String f22058d;

    /* renamed from: e, reason: collision with root package name */
    public final String f22059e;

    /* renamed from: f, reason: collision with root package name */
    public final int f22060f;

    /* renamed from: g, reason: collision with root package name */
    public final File f22061g;

    /* renamed from: h, reason: collision with root package name */
    public com.tencent.tbs.one.impl.d.a f22062h;

    /* renamed from: i, reason: collision with root package name */
    public int f22063i;

    /* renamed from: j, reason: collision with root package name */
    public JSONObject f22064j;

    /* renamed from: k, reason: collision with root package name */
    public int f22065k;

    /* renamed from: l, reason: collision with root package name */
    public String f22066l;

    /* renamed from: m, reason: collision with root package name */
    public long f22067m = -1;

    /* renamed from: n, reason: collision with root package name */
    public final Bundle f22068n;

    /* renamed from: o, reason: collision with root package name */
    public final d.a f22069o;

    public a(Context context, String str, String str2, d.a aVar, File file, Bundle bundle) {
        this.f22056b = context;
        this.f22057c = str;
        String str3 = aVar.f21995d;
        this.f22058d = TextUtils.isEmpty(str3) ? str2 : str3;
        this.f22059e = aVar.f21992a;
        this.f22060f = aVar.f21994c;
        this.f22061g = file;
        this.f22068n = bundle;
        this.f22069o = aVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void a(com.tencent.tbs.one.impl.e.a.a r15, android.content.Context r16, java.lang.String r17, java.lang.String r18, final int r19, java.lang.String r20, final int r21) throws org.json.JSONException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.e.a.a.a(com.tencent.tbs.one.impl.e.a.a, android.content.Context, java.lang.String, java.lang.String, int, java.lang.String, int):void");
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        int iShouldOverrideInstallationJob;
        final Context context = this.f22056b;
        final String str = this.f22057c;
        final String str2 = this.f22059e;
        final int i2 = this.f22060f;
        final File file = this.f22061g;
        TBSOneRuntimeExtension tBSOneRuntimeExtensionA = com.tencent.tbs.one.impl.common.a.a(context, str);
        if (tBSOneRuntimeExtensionA != null && (iShouldOverrideInstallationJob = tBSOneRuntimeExtensionA.shouldOverrideInstallationJob(str, str2, i2, null, file, new TBSOneCallback<Void>() { // from class: com.tencent.tbs.one.impl.e.a.a.1
            @Override // com.tencent.tbs.one.TBSOneCallback
            public final /* synthetic */ void onCompleted(Void r4) {
                g.a("[%s] {%s} Finished intercepting component installation job by runtime extension", str, str2);
                a aVar = a.this;
                aVar.f22063i = 0;
                aVar.a((a) e.a(e.a.EXTENSION, file));
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onError(int i3, String str3) {
                a aVar = a.this;
                aVar.f22063i = 0;
                aVar.a(i3, str3, null);
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onProgressChanged(int i3, int i4) {
                a.this.a(i4);
            }
        })) != 0) {
            g.a("[%s] {%s} Intercepted component installation job by runtime extension", str, str2);
            this.f22063i = iShouldOverrideInstallationJob;
        } else {
            if (file.exists()) {
                com.tencent.tbs.one.impl.a.d.a(file);
            }
            o.d(new Runnable() { // from class: com.tencent.tbs.one.impl.e.a.a.2
                @Override // java.lang.Runnable
                public final void run() throws JSONException, PackageManager.NameNotFoundException, NumberFormatException {
                    if (!a.this.c() && !com.tencent.tbs.one.impl.a.e.f(context)) {
                        a.super.a(201, "current network is unavialable,no need sendrequest to server", null);
                        return;
                    }
                    Statistics.Builder componentVersion = Statistics.create(Statistics.EVENT_ACTION, a.this.d() ? 1013 : 1003).setComponentName(a.this.f22059e).setComponentVersion(a.this.f22060f);
                    a aVar = a.this;
                    Statistics.Builder componentLocalVersion = componentVersion.setComponentLocalVersion(f.a(aVar.f22056b, aVar.f22057c, aVar.f22059e));
                    a aVar2 = a.this;
                    componentLocalVersion.setDEPSVersion(f.a(aVar2.f22056b, aVar2.f22057c)).report();
                    int iC = com.tencent.tbs.one.impl.e.f.c(f.b(f.a(context.getDir("tbs", 0), str), str2));
                    a aVar3 = a.this;
                    a.a(aVar3, context, str, str2, i2, aVar3.f22058d, iC);
                }
            });
        }
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a(int i2, String str, Throwable th) {
        d.a aVar = this.f22069o;
        String str2 = aVar == null ? "" : aVar.f21992a;
        int i3 = aVar == null ? -1 : aVar.f21994c;
        boolean z2 = i2 == 219;
        Statistics.create(Statistics.EVENT_ACTION, d() ? z2 ? 1017 : 1014 : z2 ? 1007 : 1004).setComponentName(str2).setComponentVersion(i3).setComponentLocalVersion(f.a(this.f22056b, this.f22057c, this.f22059e)).setDEPSVersion(f.a(this.f22056b, this.f22057c)).report();
        com.tencent.tbs.one.impl.e.f.a(this.f22061g, System.currentTimeMillis());
        super.a(i2, str, th);
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void b() {
        TBSOneRuntimeExtension tBSOneRuntimeExtensionA;
        super.b();
        com.tencent.tbs.one.impl.d.a aVar = this.f22062h;
        if (aVar != null) {
            aVar.b();
        }
        if (this.f22063i == 0 || (tBSOneRuntimeExtensionA = com.tencent.tbs.one.impl.common.a.a(this.f22056b, this.f22057c)) == null) {
            return;
        }
        tBSOneRuntimeExtensionA.cancel(this.f22063i);
    }

    public final boolean c() {
        Bundle bundle = this.f22068n;
        if (bundle != null) {
            return bundle.getBoolean(TBSOneConfigurationKeys.IGNORE_WIFI_STATE, false);
        }
        return false;
    }

    public final boolean d() {
        Bundle bundle = this.f22068n;
        return bundle != null && bundle.getInt("info_from", -1) == 1;
    }
}
