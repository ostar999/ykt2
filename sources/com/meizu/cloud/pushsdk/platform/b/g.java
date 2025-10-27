package com.meizu.cloud.pushsdk.platform.b;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class g extends c<UnRegisterStatus> {
    public g(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, aVar, scheduledExecutorService);
    }

    public g(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f9548l = z2;
    }

    public g(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public void a(UnRegisterStatus unRegisterStatus) {
        PlatformMessageSender.a(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), unRegisterStatus);
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public boolean a() {
        return (TextUtils.isEmpty(this.f9542f) || TextUtils.isEmpty(this.f9543g)) ? false : true;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public Intent c() {
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f9542f);
        intent.putExtra(com.alipay.sdk.cons.b.f3222h, this.f9543g);
        intent.putExtra("strategy_package_name", this.f9541e.getPackageName());
        intent.putExtra("strategy_type", g());
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public int g() {
        return 32;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: h, reason: merged with bridge method [inline-methods] */
    public UnRegisterStatus b() {
        String str;
        UnRegisterStatus unRegisterStatus = new UnRegisterStatus();
        unRegisterStatus.setCode("20001");
        if (!TextUtils.isEmpty(this.f9542f)) {
            str = TextUtils.isEmpty(this.f9543g) ? "appKey not empty" : "appId not empty";
            return unRegisterStatus;
        }
        unRegisterStatus.setMessage(str);
        return unRegisterStatus;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: i, reason: merged with bridge method [inline-methods] */
    public UnRegisterStatus e() {
        UnRegisterStatus unRegisterStatus = new UnRegisterStatus();
        if (TextUtils.isEmpty(com.meizu.cloud.pushsdk.util.b.a(this.f9541e, this.f9544h))) {
            unRegisterStatus.setCode("200");
            unRegisterStatus.setMessage("already unRegister PushId,dont unRegister frequently");
            unRegisterStatus.setIsUnRegisterSuccess(true);
        } else {
            this.f9545i = o();
            com.meizu.cloud.pushsdk.b.a.c cVarB = this.f9546j.b(this.f9542f, this.f9543g, this.f9545i);
            if (cVarB.b()) {
                unRegisterStatus = new UnRegisterStatus((String) cVarB.a());
                DebugLogger.e("Strategy", "network unRegisterStatus " + unRegisterStatus);
                if ("200".equals(unRegisterStatus.getCode())) {
                    com.meizu.cloud.pushsdk.util.b.f(this.f9541e, "", this.f9544h);
                }
            } else {
                com.meizu.cloud.pushsdk.b.b.a aVarC = cVarB.c();
                if (aVarC.a() != null) {
                    DebugLogger.e("Strategy", "status code=" + aVarC.b() + " data=" + aVarC.a());
                }
                unRegisterStatus.setCode(String.valueOf(aVarC.b()));
                unRegisterStatus.setMessage(aVarC.c());
                DebugLogger.e("Strategy", "unRegisterStatus " + unRegisterStatus);
            }
        }
        return unRegisterStatus;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public UnRegisterStatus f() {
        return null;
    }
}
