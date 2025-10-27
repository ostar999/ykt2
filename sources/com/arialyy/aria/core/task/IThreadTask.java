package com.arialyy.aria.core.task;

import java.util.concurrent.Callable;

/* loaded from: classes2.dex */
public interface IThreadTask extends Callable<IThreadTask> {
    void breakTask();

    void cancel();

    boolean checkBlock();

    void destroy();

    int getThreadId();

    String getThreadName();

    boolean isBreak();

    boolean isDestroy();

    boolean isLive();

    boolean isThreadComplete();

    void setMaxSpeed(int i2);

    void stop();
}
