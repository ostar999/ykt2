package com.plv.livescenes.hiclass;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/* loaded from: classes4.dex */
public class PLVHiClassManagerFactory {
    public static IPLVHiClassManager createHiClassManager(@NonNull LiveData<PLVHiClassDataBean> liveData, String str) {
        return new PLVHiClassManager(liveData, str);
    }
}
