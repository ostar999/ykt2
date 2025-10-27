package com.meizu.cloud.pushsdk.platform.b;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class f extends c<PushSwitchStatus> {

    /* renamed from: a, reason: collision with root package name */
    boolean f9557a;

    /* renamed from: b, reason: collision with root package name */
    private String f9558b;

    /* renamed from: c, reason: collision with root package name */
    private int f9559c;

    /* renamed from: m, reason: collision with root package name */
    private Map<String, Boolean> f9560m;

    public f(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, null, aVar, scheduledExecutorService);
    }

    public f(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f9548l = z2;
    }

    public f(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f9559c = 0;
        this.f9560m = new HashMap();
    }

    public f(Context context, String str, String str2, String str3, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, str, str2, aVar, scheduledExecutorService);
        this.f9558b = str3;
    }

    private void c(boolean z2) {
        com.meizu.cloud.pushsdk.util.b.a(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), z2);
    }

    private void d(boolean z2) {
        com.meizu.cloud.pushsdk.util.b.b(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), z2);
    }

    private void e(boolean z2) {
        com.meizu.cloud.pushsdk.util.b.a(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), z2);
        com.meizu.cloud.pushsdk.util.b.b(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), z2);
    }

    private void f(boolean z2) {
        this.f9560m.put(this.f9544h + StrPool.UNDERLINE + this.f9559c, Boolean.valueOf(z2));
    }

    private boolean p() {
        return com.meizu.cloud.pushsdk.util.b.e(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName());
    }

    private boolean q() {
        return com.meizu.cloud.pushsdk.util.b.f(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName());
    }

    private boolean r() {
        return com.meizu.cloud.pushsdk.util.b.h(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName());
    }

    private boolean s() {
        return com.meizu.cloud.pushsdk.util.b.i(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName());
    }

    private boolean t() {
        Boolean bool = this.f9560m.get(this.f9544h + StrPool.UNDERLINE + this.f9559c);
        boolean zBooleanValue = bool != null ? bool.booleanValue() : true;
        DebugLogger.e("Strategy", "isSyncPushStatus " + this.f9544h + " switch type->" + this.f9559c + " flag->" + zBooleanValue);
        return zBooleanValue;
    }

    public void a(int i2) {
        this.f9559c = i2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public void a(PushSwitchStatus pushSwitchStatus) {
        PlatformMessageSender.a(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), pushSwitchStatus);
    }

    public void a(String str) {
        this.f9558b = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public boolean a() {
        return (TextUtils.isEmpty(this.f9542f) || TextUtils.isEmpty(this.f9543g) || TextUtils.isEmpty(this.f9558b)) ? false : true;
    }

    public void b(boolean z2) {
        this.f9557a = z2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public Intent c() {
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f9542f);
        intent.putExtra(com.alipay.sdk.cons.b.f3222h, this.f9543g);
        intent.putExtra("strategy_package_name", this.f9541e.getPackageName());
        intent.putExtra("push_id", this.f9558b);
        intent.putExtra("strategy_type", g());
        intent.putExtra("strategy_child_type", this.f9559c);
        intent.putExtra("strategy_params", this.f9557a ? "1" : "0");
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public int g() {
        return 16;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: h, reason: merged with bridge method [inline-methods] */
    public PushSwitchStatus b() {
        String str;
        PushSwitchStatus pushSwitchStatus = new PushSwitchStatus();
        pushSwitchStatus.setCode("20001");
        if (TextUtils.isEmpty(this.f9542f)) {
            str = "appId not empty";
        } else {
            if (!TextUtils.isEmpty(this.f9543g)) {
                if (TextUtils.isEmpty(this.f9558b)) {
                    str = "pushId not empty";
                }
                return pushSwitchStatus;
            }
            str = "appKey not empty";
        }
        pushSwitchStatus.setMessage(str);
        return pushSwitchStatus;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01d0  */
    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: i, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus e() {
        /*
            Method dump skipped, instructions count: 474
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.platform.b.f.e():com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus");
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public PushSwitchStatus f() {
        int i2 = this.f9559c;
        if (i2 == 0) {
            c(this.f9557a);
            return null;
        }
        if (i2 == 1) {
            d(this.f9557a);
            return null;
        }
        if (i2 != 3) {
            return null;
        }
        e(this.f9557a);
        return null;
    }
}
