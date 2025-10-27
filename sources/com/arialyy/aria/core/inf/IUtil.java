package com.arialyy.aria.core.inf;

import com.arialyy.aria.core.listener.IEventListener;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;

/* loaded from: classes2.dex */
public interface IUtil {
    void cancel();

    long getCurrentLocation();

    long getFileSize();

    String getKey();

    boolean isRunning();

    IUtil setParams(AbsTaskWrapper absTaskWrapper, IEventListener iEventListener);

    void start();

    void stop();
}
