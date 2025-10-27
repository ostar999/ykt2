package com.arialyy.aria.core.inf;

/* loaded from: classes2.dex */
public interface IReceiver {
    void destroy();

    String getKey();

    String getTargetName();

    ReceiverType getType();

    boolean isFragment();

    void register();

    void unRegister();
}
