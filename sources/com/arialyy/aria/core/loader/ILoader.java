package com.arialyy.aria.core.loader;

/* loaded from: classes2.dex */
public interface ILoader extends Runnable {
    void cancel();

    long getCurrentProgress();

    String getKey();

    boolean isBreak();

    boolean isRunning();

    void stop();
}
