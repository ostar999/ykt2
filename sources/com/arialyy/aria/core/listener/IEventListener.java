package com.arialyy.aria.core.listener;

import android.os.Handler;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.exception.AriaException;

/* loaded from: classes2.dex */
public interface IEventListener {
    void onCancel();

    void onComplete();

    void onFail(boolean z2, AriaException ariaException);

    void onPre();

    void onProgress(long j2);

    void onResume(long j2);

    void onStart(long j2);

    void onStop(long j2);

    IEventListener setParams(AbsTask absTask, Handler handler);
}
