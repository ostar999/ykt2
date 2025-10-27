package com.arialyy.aria.core.task;

/* loaded from: classes2.dex */
public interface IThreadTaskAdapter {
    void attach(IThreadTaskObserver iThreadTaskObserver);

    void call(IThreadTask iThreadTask) throws Exception;

    void setMaxSpeed(int i2);
}
