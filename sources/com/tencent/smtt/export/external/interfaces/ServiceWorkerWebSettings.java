package com.tencent.smtt.export.external.interfaces;

/* loaded from: classes6.dex */
public abstract class ServiceWorkerWebSettings {
    public abstract boolean getAllowContentAccess();

    public abstract boolean getAllowFileAccess();

    public abstract boolean getBlockNetworkLoads();

    public abstract int getCacheMode();

    public abstract void setAllowContentAccess(boolean z2);

    public abstract void setAllowFileAccess(boolean z2);

    public abstract void setBlockNetworkLoads(boolean z2);

    public abstract void setCacheMode(int i2);
}
