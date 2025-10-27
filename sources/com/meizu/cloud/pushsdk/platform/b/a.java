package com.meizu.cloud.pushsdk.platform.b;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class a extends c {

    /* renamed from: a, reason: collision with root package name */
    private int[] f9531a;

    /* renamed from: b, reason: collision with root package name */
    private int f9532b;

    /* renamed from: c, reason: collision with root package name */
    private String f9533c;

    public a(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f9547k = MinSdkChecker.isSupportSetDrawableSmallIcon();
    }

    public a(Context context, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, null, null, null, scheduledExecutorService);
        this.f9548l = z2;
    }

    public void a(int i2) {
        this.f9532b = i2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public void a(BasicPushStatus basicPushStatus) {
    }

    public void a(String str) {
        this.f9533c = str;
    }

    public void a(int... iArr) {
        this.f9531a = iArr;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public boolean a() {
        int i2 = this.f9532b;
        if (i2 == 0) {
            return true;
        }
        int[] iArr = this.f9531a;
        if (iArr == null || iArr.length <= 0 || i2 != 1) {
            return i2 == 2 && !TextUtils.isEmpty(this.f9533c);
        }
        return true;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public BasicPushStatus b() {
        return null;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public Intent c() {
        Intent intent = new Intent();
        intent.putExtra("strategy_package_name", this.f9541e.getPackageName());
        intent.putExtra("strategy_type", g());
        intent.putExtra("strategy_child_type", this.f9532b);
        int i2 = this.f9532b;
        if (i2 == 2) {
            intent.putExtra("strategy_params", this.f9533c);
            return intent;
        }
        if (i2 == 1) {
            return null;
        }
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public Intent[] d() {
        int[] iArr = this.f9531a;
        if (iArr == null) {
            return null;
        }
        Intent[] intentArr = new Intent[iArr.length];
        for (int i2 = 0; i2 < this.f9531a.length; i2++) {
            DebugLogger.i("Strategy", "send notifyId " + this.f9531a[i2] + " to PushManagerService");
            Intent intent = new Intent();
            intent.putExtra("strategy_package_name", this.f9541e.getPackageName());
            intent.putExtra("strategy_type", g());
            intent.putExtra("strategy_child_type", this.f9532b);
            intent.putExtra("strategy_params", "" + this.f9531a[i2]);
            intentArr[i2] = intent;
        }
        return intentArr;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public BasicPushStatus e() {
        int i2 = this.f9532b;
        if (i2 == 0) {
            if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
                DebugLogger.e("Strategy", "android 6.0 blow so cancel all by context");
                com.meizu.cloud.pushsdk.notification.c.b.a(this.f9541e);
            }
            com.meizu.cloud.pushsdk.notification.c.b.a(this.f9541e, this.f9544h);
            return null;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                return null;
            }
            com.meizu.cloud.pushsdk.notification.c.b.a(this.f9541e, this.f9544h, this.f9533c);
            return null;
        }
        int[] iArr = this.f9531a;
        if (iArr == null) {
            return null;
        }
        for (int i3 : iArr) {
            DebugLogger.e("Strategy", "clear notifyId " + i3);
            com.meizu.cloud.pushsdk.notification.c.b.a(this.f9541e, this.f9544h, i3);
        }
        return null;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public BasicPushStatus f() {
        return null;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public int g() {
        return 64;
    }
}
