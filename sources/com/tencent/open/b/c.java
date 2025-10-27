package com.tencent.open.b;

import android.os.Bundle;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.Serializable;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class c implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    public final HashMap<String, String> f20536a;

    public c(Bundle bundle) {
        this.f20536a = new HashMap<>();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                this.f20536a.put(str, bundle.getString(str));
            }
        }
    }

    public String toString() {
        return "BaseData{time=" + this.f20536a.get(CrashHianalyticsData.TIME) + ", name=" + this.f20536a.get("interface_name") + '}';
    }

    public c(HashMap<String, String> map) {
        this.f20536a = new HashMap<>(map);
    }
}
