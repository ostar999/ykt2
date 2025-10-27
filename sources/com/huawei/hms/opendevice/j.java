package com.huawei.hms.opendevice;

import android.content.Context;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.aaid.entity.AAIDResult;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public class j implements Callable<AAIDResult> {

    /* renamed from: a, reason: collision with root package name */
    public Context f7914a;

    public j(Context context) {
        this.f7914a = context;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    public AAIDResult call() throws Exception {
        Context context = this.f7914a;
        if (context == null) {
            throw ErrorEnum.ERROR_ARGUMENTS_INVALID.toApiException();
        }
        String strC = o.c(context);
        AAIDResult aAIDResult = new AAIDResult();
        aAIDResult.setId(strC);
        return aAIDResult;
    }
}
