package com.beizi.ad.a;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.azhon.appupdate.config.Constant;
import com.beizi.ad.DownloadService;
import com.beizi.ad.a.a.i;
import com.beizi.ad.a.a.k;
import com.beizi.ad.internal.download.a;
import com.beizi.ad.internal.g;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static b f3708b;

    /* renamed from: a, reason: collision with root package name */
    private Context f3709a;

    /* renamed from: c, reason: collision with root package name */
    private com.beizi.ad.internal.download.a f3710c;

    /* renamed from: d, reason: collision with root package name */
    private a f3711d;

    private b(Context context) {
        this.f3709a = context;
    }

    public static b a(Context context) {
        if (f3708b == null) {
            synchronized (b.class) {
                if (f3708b == null) {
                    f3708b = new b(context);
                }
            }
        }
        return f3708b;
    }

    private void f() {
        Context context;
        if (this.f3711d == null || (context = this.f3709a) == null) {
            return;
        }
        this.f3710c = null;
        a.C0052a c0052a = new a.C0052a(context);
        c0052a.a(new a.b() { // from class: com.beizi.ad.a.b.1
            @Override // com.beizi.ad.internal.download.a.b
            public void a() {
            }

            @Override // com.beizi.ad.internal.download.a.b
            public void b() {
                b.this.c();
            }
        });
        c0052a.a(this.f3711d);
        com.beizi.ad.internal.download.a aVarA = c0052a.a();
        this.f3710c = aVarA;
        aVarA.show();
    }

    private boolean g() {
        a aVar = this.f3711d;
        if (aVar == null) {
            return false;
        }
        if (TextUtils.isEmpty(aVar.a())) {
            i.c("DownloadManager", "apkUrl can not be empty!");
            return false;
        }
        if (TextUtils.isEmpty(this.f3711d.b())) {
            i.c("DownloadManager", "apkName can not be empty!");
            return false;
        }
        if (this.f3711d.b().endsWith(Constant.APK_SUFFIX)) {
            return true;
        }
        i.c("DownloadManager", "apkName must endsWith .apk!");
        return false;
    }

    public b b(Context context) {
        this.f3709a = context;
        return this;
    }

    public void c() {
        if (!g()) {
            Log.d("lance", "startDownloadService download:下载必要参数为null");
            return;
        }
        if (!k.a(this.f3709a)) {
            Log.d("lance", "startDownloadService:checkStoragePermission false");
            return;
        }
        try {
            this.f3709a.startService(new Intent(this.f3709a, (Class<?>) DownloadService.class));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void d() {
        com.beizi.ad.internal.download.a aVar = this.f3710c;
        if (aVar == null || !aVar.isShowing()) {
            return;
        }
        this.f3710c.dismiss();
    }

    public void e() {
        this.f3709a = null;
        f3708b = null;
    }

    public void b() {
        if (g.a().m()) {
            c();
        } else {
            f();
        }
    }

    public a a() {
        return this.f3711d;
    }

    public b a(a aVar) {
        this.f3711d = aVar;
        return this;
    }
}
