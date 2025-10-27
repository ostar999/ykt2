package com.meizu.cloud.pushsdk.platform.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public abstract class c<T extends BasicPushStatus> {

    /* renamed from: d, reason: collision with root package name */
    protected ScheduledExecutorService f9540d;

    /* renamed from: e, reason: collision with root package name */
    protected Context f9541e;

    /* renamed from: f, reason: collision with root package name */
    protected String f9542f;

    /* renamed from: g, reason: collision with root package name */
    protected String f9543g;

    /* renamed from: h, reason: collision with root package name */
    protected String f9544h;

    /* renamed from: i, reason: collision with root package name */
    protected volatile String f9545i;

    /* renamed from: j, reason: collision with root package name */
    protected com.meizu.cloud.pushsdk.platform.a.a f9546j;

    /* renamed from: k, reason: collision with root package name */
    protected boolean f9547k = true;

    /* renamed from: l, reason: collision with root package name */
    protected boolean f9548l = true;

    /* renamed from: a, reason: collision with root package name */
    private String f9539a = null;

    public c(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this.f9540d = scheduledExecutorService;
        this.f9541e = context;
        this.f9542f = str;
        this.f9543g = str2;
        this.f9546j = aVar;
    }

    private boolean a(int i2) {
        return i2 >= 110000 && i2 <= 200000;
    }

    private boolean b(T t2) {
        int iIntValue = Integer.valueOf(t2.getCode()).intValue();
        return (iIntValue > 200 && iIntValue < 600) || (iIntValue > 1000 && iIntValue < 2000) || iIntValue == 0;
    }

    private boolean h() {
        return this.f9548l && !this.f9541e.getPackageName().equals(this.f9539a);
    }

    public String a(Context context, String str) {
        String str2 = null;
        if (!TextUtils.isEmpty(str)) {
            List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(new Intent(str), 0);
            if (listQueryIntentServices != null) {
                Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ResolveInfo next = it.next();
                    if ("com.meizu.cloud".equals(next.serviceInfo.packageName)) {
                        ServiceInfo serviceInfo = next.serviceInfo;
                        this.f9539a = serviceInfo.packageName;
                        str2 = serviceInfo.name;
                        break;
                    }
                }
                if (TextUtils.isEmpty(str2) && listQueryIntentServices.size() > 0) {
                    this.f9539a = listQueryIntentServices.get(0).serviceInfo.packageName;
                    str2 = listQueryIntentServices.get(0).serviceInfo.name;
                }
            }
        }
        DebugLogger.i("Strategy", "current process packageName " + this.f9539a);
        return str2;
    }

    public void a(Intent intent) {
        try {
            intent.setPackage(this.f9539a);
            intent.setAction(PushConstants.MZ_PUSH_MANAGER_SERVICE_ACTION);
            this.f9541e.startService(intent);
        } catch (Exception e2) {
            DebugLogger.e("Strategy", "start RemoteService error " + e2.getMessage());
        }
    }

    public abstract void a(T t2);

    public void a(boolean z2) {
        this.f9547k = z2;
    }

    public abstract boolean a();

    public abstract T b();

    public void b(String str) {
        this.f9542f = str;
    }

    public abstract Intent c();

    public void c(String str) {
        this.f9543g = str;
    }

    public void d(String str) {
        this.f9544h = str;
    }

    public Intent[] d() {
        return null;
    }

    public abstract T e();

    public abstract T f();

    public abstract int g();

    public boolean k() {
        return this.f9548l && this.f9547k && !TextUtils.isEmpty(a(this.f9541e, PushConstants.MZ_PUSH_MANAGER_SERVICE_ACTION));
    }

    public boolean l() {
        return 2 == g() || 32 == g();
    }

    public boolean m() {
        ScheduledExecutorService scheduledExecutorService = this.f9540d;
        if (scheduledExecutorService == null) {
            return n();
        }
        scheduledExecutorService.execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.platform.b.c.1
            @Override // java.lang.Runnable
            public void run() {
                c.this.n();
            }
        });
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean n() {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.platform.b.c.n():boolean");
    }

    public String o() {
        if (TextUtils.isEmpty(this.f9545i)) {
            this.f9545i = MzSystemUtils.getDeviceId(this.f9541e);
            DebugLogger.e("Strategy", "deviceId " + this.f9545i);
        }
        return this.f9545i;
    }
}
