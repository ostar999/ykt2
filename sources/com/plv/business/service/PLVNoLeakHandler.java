package com.plv.business.service;

import android.content.Context;
import android.os.Handler;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class PLVNoLeakHandler extends Handler {
    private WeakReference<Context> mActivity;

    public PLVNoLeakHandler(Context context) {
        this.mActivity = new WeakReference<>(context);
    }
}
