package com.unity3d.splash.services.core.configuration;

/* loaded from: classes6.dex */
public interface IInitializationNotificationCenter {
    void addListener(IInitializationListener iInitializationListener);

    void removeListener(IInitializationListener iInitializationListener);

    void triggerOnSdkInitializationFailed(String str, int i2);

    void triggerOnSdkInitialized();
}
