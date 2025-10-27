package com.umeng.analytics.filter;

import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;

/* loaded from: classes6.dex */
public class b extends EventList {

    /* renamed from: a, reason: collision with root package name */
    private d f22393a;

    /* renamed from: b, reason: collision with root package name */
    private Object f22394b;

    public b(String str, String str2) {
        super(str, str2);
        this.f22394b = new Object();
    }

    @Override // com.umeng.analytics.filter.EventList
    public void eventListChange() {
        if (TextUtils.isEmpty(this.mEventList)) {
            return;
        }
        synchronized (this.f22394b) {
            this.f22393a = null;
            this.f22393a = new d(true, this.mEventList);
        }
    }

    @Override // com.umeng.analytics.filter.EventList
    public boolean matchHit(String str) {
        boolean zA;
        if (TextUtils.isEmpty(this.mEventList)) {
            return true;
        }
        synchronized (this.f22394b) {
            if (this.f22393a == null) {
                this.f22393a = new d(true, this.mEventList);
            }
            zA = this.f22393a.a(str);
        }
        return zA;
    }

    @Override // com.umeng.analytics.filter.EventList
    public void setMD5ClearFlag(boolean z2) {
        AnalyticsConfig.CLEAR_EKV_WL = z2;
    }
}
