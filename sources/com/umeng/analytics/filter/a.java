package com.umeng.analytics.filter;

import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;

/* loaded from: classes6.dex */
public class a extends EventList {

    /* renamed from: a, reason: collision with root package name */
    private d f22391a;

    /* renamed from: b, reason: collision with root package name */
    private Object f22392b;

    public a(String str, String str2) {
        super(str, str2);
        this.f22392b = new Object();
    }

    @Override // com.umeng.analytics.filter.EventList
    public void eventListChange() {
        if (TextUtils.isEmpty(this.mEventList)) {
            return;
        }
        synchronized (this.f22392b) {
            this.f22391a = null;
            this.f22391a = new d(false, this.mEventList);
        }
    }

    @Override // com.umeng.analytics.filter.EventList
    public boolean matchHit(String str) {
        boolean zA;
        if (TextUtils.isEmpty(this.mEventList)) {
            return false;
        }
        synchronized (this.f22392b) {
            if (this.f22391a == null) {
                this.f22391a = new d(false, this.mEventList);
            }
            zA = this.f22391a.a(str);
        }
        return zA;
    }

    @Override // com.umeng.analytics.filter.EventList
    public void setMD5ClearFlag(boolean z2) {
        AnalyticsConfig.CLEAR_EKV_BL = z2;
    }
}
