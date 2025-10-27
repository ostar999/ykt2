package com.meizu.cloud.pushsdk.platform.b;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class d extends c<SubAliasStatus> {

    /* renamed from: a, reason: collision with root package name */
    private String f9550a;

    /* renamed from: b, reason: collision with root package name */
    private int f9551b;

    /* renamed from: c, reason: collision with root package name */
    private String f9552c;

    /* renamed from: m, reason: collision with root package name */
    private Map<String, Boolean> f9553m;

    public d(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, null, aVar, scheduledExecutorService);
    }

    public d(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f9548l = z2;
    }

    public d(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f9553m = new HashMap();
    }

    public d(Context context, String str, String str2, String str3, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, str, str2, aVar, scheduledExecutorService);
        this.f9550a = str3;
    }

    private void b(boolean z2) {
        this.f9553m.put(this.f9544h + StrPool.UNDERLINE + this.f9551b, Boolean.valueOf(z2));
    }

    private void f(String str) {
        com.meizu.cloud.pushsdk.util.b.g(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), str);
    }

    private String p() {
        return com.meizu.cloud.pushsdk.util.b.g(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName());
    }

    private boolean q() {
        Boolean bool = this.f9553m.get(this.f9544h + StrPool.UNDERLINE + this.f9551b);
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    private boolean r() {
        return !this.f9547k && "com.meizu.cloud".equals(this.f9544h);
    }

    public void a(int i2) {
        this.f9551b = i2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public void a(SubAliasStatus subAliasStatus) {
        PlatformMessageSender.a(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), subAliasStatus);
    }

    public void a(String str) {
        this.f9552c = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public boolean a() {
        return (TextUtils.isEmpty(this.f9542f) || TextUtils.isEmpty(this.f9543g) || TextUtils.isEmpty(this.f9550a)) ? false : true;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public Intent c() {
        if (this.f9551b == 2) {
            return null;
        }
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f9542f);
        intent.putExtra(com.alipay.sdk.cons.b.f3222h, this.f9543g);
        intent.putExtra("strategy_package_name", this.f9541e.getPackageName());
        intent.putExtra("push_id", this.f9550a);
        intent.putExtra("strategy_type", g());
        intent.putExtra("strategy_child_type", this.f9551b);
        intent.putExtra("strategy_params", this.f9552c);
        return intent;
    }

    public void e(String str) {
        this.f9550a = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public int g() {
        return 8;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: h, reason: merged with bridge method [inline-methods] */
    public SubAliasStatus b() {
        String str;
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        subAliasStatus.setCode("20001");
        if (TextUtils.isEmpty(this.f9542f)) {
            str = "appId not empty";
        } else {
            if (!TextUtils.isEmpty(this.f9543g)) {
                if (TextUtils.isEmpty(this.f9550a)) {
                    str = "pushId not empty";
                }
                return subAliasStatus;
            }
            str = "appKey not empty";
        }
        subAliasStatus.setMessage(str);
        return subAliasStatus;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0090  */
    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: i, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.meizu.cloud.pushsdk.platform.message.SubAliasStatus e() {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.platform.b.d.e():com.meizu.cloud.pushsdk.platform.message.SubAliasStatus");
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public SubAliasStatus f() {
        if (this.f9551b != 2) {
            return null;
        }
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        subAliasStatus.setCode("200");
        subAliasStatus.setPushId(this.f9550a);
        subAliasStatus.setAlias(p());
        subAliasStatus.setMessage("check alias success");
        return subAliasStatus;
    }
}
