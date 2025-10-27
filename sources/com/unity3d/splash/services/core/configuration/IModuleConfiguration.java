package com.unity3d.splash.services.core.configuration;

/* loaded from: classes6.dex */
public interface IModuleConfiguration {
    Class[] getWebAppApiClassList();

    boolean initCompleteState(Configuration configuration);

    boolean initErrorState(Configuration configuration, String str, String str2);

    boolean initModuleState(Configuration configuration);

    boolean resetState(Configuration configuration);
}
