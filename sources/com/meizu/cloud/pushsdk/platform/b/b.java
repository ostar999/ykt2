package com.meizu.cloud.pushsdk.platform.b;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class b extends c<RegisterStatus> {

    /* renamed from: a, reason: collision with root package name */
    protected Handler f9534a;

    /* renamed from: b, reason: collision with root package name */
    protected ScheduledExecutorService f9535b;

    /* renamed from: c, reason: collision with root package name */
    protected int f9536c;

    public b(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, aVar, scheduledExecutorService);
        this.f9535b = (ScheduledExecutorService) com.meizu.cloud.pushsdk.c.b.a.b.a();
        this.f9534a = new Handler(context.getMainLooper()) { // from class: com.meizu.cloud.pushsdk.platform.b.b.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 0) {
                    return;
                }
                b.this.m();
            }
        };
    }

    public b(Context context, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f9548l = z2;
    }

    public b(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.a.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f9536c = 0;
    }

    public void a(long j2) {
        this.f9535b.schedule(new Runnable() { // from class: com.meizu.cloud.pushsdk.platform.b.b.2
            @Override // java.lang.Runnable
            public void run() {
                b.this.o();
                b.this.f9534a.sendEmptyMessage(0);
            }
        }, j2, TimeUnit.SECONDS);
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public void a(RegisterStatus registerStatus) {
        PlatformMessageSender.a(this.f9541e, !TextUtils.isEmpty(this.f9544h) ? this.f9544h : this.f9541e.getPackageName(), registerStatus);
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    public boolean a() {
        DebugLogger.e("Strategy", "isBrandMeizu " + MzSystemUtils.isBrandMeizu(this.f9541e));
        return (TextUtils.isEmpty(this.f9542f) || TextUtils.isEmpty(this.f9543g)) ? false : true;
    }

    public boolean a(String str, int i2) {
        String strO = o();
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(strO) || (!str.startsWith(strO) && (TextUtils.isEmpty(com.meizu.cloud.pushsdk.platform.a.a(str)) || !com.meizu.cloud.pushsdk.platform.a.a(str).startsWith(strO))) || System.currentTimeMillis() / 1000 >= ((long) i2);
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
        return 2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: h, reason: merged with bridge method [inline-methods] */
    public RegisterStatus b() {
        String str;
        RegisterStatus registerStatus = new RegisterStatus();
        registerStatus.setCode("20001");
        if (!TextUtils.isEmpty(this.f9542f)) {
            str = TextUtils.isEmpty(this.f9543g) ? "appKey not empty" : "appId not empty";
            return registerStatus;
        }
        registerStatus.setMessage(str);
        return registerStatus;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: i, reason: merged with bridge method [inline-methods] */
    public RegisterStatus f() {
        return null;
    }

    @Override // com.meizu.cloud.pushsdk.platform.b.c
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public RegisterStatus e() {
        RegisterStatus registerStatus = new RegisterStatus();
        String strA = com.meizu.cloud.pushsdk.util.b.a(this.f9541e, this.f9544h);
        int iB = com.meizu.cloud.pushsdk.util.b.b(this.f9541e, this.f9544h);
        if (a(strA, iB)) {
            com.meizu.cloud.pushsdk.util.b.f(this.f9541e, "", this.f9544h);
            this.f9545i = o();
            if (!TextUtils.isEmpty(this.f9545i) || this.f9536c >= 3) {
                this.f9536c = 0;
                com.meizu.cloud.pushsdk.b.a.c cVarA = this.f9546j.a(this.f9542f, this.f9543g, this.f9545i);
                if (cVarA.b()) {
                    registerStatus = new RegisterStatus((String) cVarA.a());
                    DebugLogger.e("Strategy", "registerStatus " + registerStatus);
                    if (!TextUtils.isEmpty(registerStatus.getPushId())) {
                        com.meizu.cloud.pushsdk.util.b.f(this.f9541e, registerStatus.getPushId(), this.f9544h);
                        com.meizu.cloud.pushsdk.util.b.a(this.f9541e, (int) ((System.currentTimeMillis() / 1000) + registerStatus.getExpireTime()), this.f9544h);
                    }
                } else {
                    com.meizu.cloud.pushsdk.b.b.a aVarC = cVarA.c();
                    if (aVarC.a() != null) {
                        DebugLogger.e("Strategy", "status code=" + aVarC.b() + " data=" + aVarC.a());
                    }
                    registerStatus.setCode(String.valueOf(aVarC.b()));
                    registerStatus.setMessage(aVarC.c());
                    DebugLogger.e("Strategy", "registerStatus " + registerStatus);
                }
            } else {
                DebugLogger.i("Strategy", "after " + (this.f9536c * 10) + " seconds start register");
                a((long) (this.f9536c * 10));
                this.f9536c = this.f9536c + 1;
                registerStatus.setCode("20000");
                registerStatus.setMessage("deviceId is empty");
            }
        } else {
            registerStatus.setCode("200");
            registerStatus.setMessage("already register PushId,dont register frequently");
            registerStatus.setPushId(strA);
            registerStatus.setExpireTime((int) (iB - (System.currentTimeMillis() / 1000)));
        }
        return registerStatus;
    }
}
