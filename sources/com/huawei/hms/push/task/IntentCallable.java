package com.huawei.hms.push.task;

import android.content.Context;
import android.content.Intent;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.push.utils.PushBiUtil;
import com.huawei.hms.support.api.entity.push.PushNaming;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public class IntentCallable implements Callable<Void> {

    /* renamed from: a, reason: collision with root package name */
    public Context f8040a;

    /* renamed from: b, reason: collision with root package name */
    public Intent f8041b;

    /* renamed from: c, reason: collision with root package name */
    public String f8042c;

    public IntentCallable(Context context, Intent intent, String str) {
        this.f8040a = context;
        this.f8041b = intent;
        this.f8042c = str;
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        this.f8040a.sendBroadcast(this.f8041b);
        PushBiUtil.reportExit(this.f8040a, PushNaming.SET_NOTIFY_FLAG, this.f8042c, ErrorEnum.SUCCESS);
        return null;
    }
}
