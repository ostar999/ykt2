package com.meizu.cloud.pushsdk.platform.b;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class e extends c<SubTagsStatus> {

    /* renamed from: a, reason: collision with root package name */
    private String f9554a;

    /* renamed from: b, reason: collision with root package name */
    private int f9555b;

    /* renamed from: c, reason: collision with root package name */
    private String f9556c;

    public e(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, null, aVar, scheduledExecutorService);
    }

    public e(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f9548l = z2;
    }

    public e(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f9555b = 3;
    }

    public e(Context context, String str, String str2, String str3, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, str, str2, aVar, scheduledExecutorService);
        this.f9554a = str3;
    }

    public void a(int i2) {
        this.f9555b = i2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public void a(SubTagsStatus subTagsStatus) {
        PlatformMessageSender.a(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), subTagsStatus);
    }

    public void a(String str) {
        this.f9556c = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public boolean a() {
        return (TextUtils.isEmpty(this.f9542f) || TextUtils.isEmpty(this.f9543g) || TextUtils.isEmpty(this.f9554a)) ? false : true;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public Intent c() {
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f9542f);
        intent.putExtra(com.alipay.sdk.cons.b.f3222h, this.f9543g);
        intent.putExtra("strategy_package_name", this.f9541e.getPackageName());
        intent.putExtra("push_id", this.f9554a);
        intent.putExtra("strategy_type", g());
        intent.putExtra("strategy_child_type", this.f9555b);
        intent.putExtra("strategy_params", this.f9556c);
        return intent;
    }

    public void e(String str) {
        this.f9554a = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public int g() {
        return 4;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: h, reason: merged with bridge method [inline-methods] */
    public SubTagsStatus b() {
        String str;
        SubTagsStatus subTagsStatus = new SubTagsStatus();
        subTagsStatus.setCode("20001");
        if (TextUtils.isEmpty(this.f9542f)) {
            str = "appId not empty";
        } else {
            if (!TextUtils.isEmpty(this.f9543g)) {
                if (TextUtils.isEmpty(this.f9554a)) {
                    str = "pushId not empty";
                }
                return subTagsStatus;
            }
            str = "appKey not empty";
        }
        subTagsStatus.setMessage(str);
        return subTagsStatus;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: i, reason: merged with bridge method [inline-methods] */
    public SubTagsStatus e() {
        StringBuilder sb;
        String str;
        SubTagsStatus subTagsStatus = new SubTagsStatus();
        int i2 = this.f9555b;
        com.meizu.cloud.pushsdk.b.a.c cVarE = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? null : this.f9546j.e(this.f9542f, this.f9543g, this.f9554a) : this.f9546j.d(this.f9542f, this.f9543g, this.f9554a) : this.f9546j.b(this.f9542f, this.f9543g, this.f9554a, this.f9556c) : this.f9546j.a(this.f9542f, this.f9543g, this.f9554a, this.f9556c);
        if (cVarE.b()) {
            subTagsStatus = new SubTagsStatus((String) cVarE.a());
            sb = new StringBuilder();
            str = "network subTagsStatus ";
        } else {
            com.meizu.cloud.pushsdk.b.b.a aVarC = cVarE.c();
            if (aVarC.a() != null) {
                DebugLogger.e("Strategy", "status code=" + aVarC.b() + " data=" + aVarC.a());
            }
            subTagsStatus.setCode(String.valueOf(aVarC.b()));
            subTagsStatus.setMessage(aVarC.c());
            sb = new StringBuilder();
            str = "subTagsStatus ";
        }
        sb.append(str);
        sb.append(subTagsStatus);
        DebugLogger.e("Strategy", sb.toString());
        return subTagsStatus;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public SubTagsStatus f() {
        return null;
    }
}
