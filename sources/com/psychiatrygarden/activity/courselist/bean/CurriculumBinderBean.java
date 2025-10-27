package com.psychiatrygarden.activity.courselist.bean;

import android.os.Binder;

/* loaded from: classes5.dex */
public class CurriculumBinderBean extends Binder {
    public Object data;

    public CurriculumBinderBean(Object data) {
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }
}
