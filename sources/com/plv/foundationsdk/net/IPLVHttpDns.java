package com.plv.foundationsdk.net;

import java.util.ArrayList;
import javax.annotation.Nullable;

/* loaded from: classes4.dex */
public interface IPLVHttpDns {
    @Nullable
    String getIp(String str);

    @Deprecated
    boolean isEnableIPV6();

    @Deprecated
    boolean isEnabled();

    @Deprecated
    void setEnable(boolean z2);

    @Deprecated
    void setEnableIPV6(boolean z2);

    void setPreResolveHosts(ArrayList<String> arrayList);
}
