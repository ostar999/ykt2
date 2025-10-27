package com.psychiatrygarden.activity.comment.bean;

import android.os.Binder;

/* loaded from: classes5.dex */
public class DiscussHeaderBean extends Binder {
    public Object discussbean;

    public DiscussHeaderBean(Object discussbean) {
        this.discussbean = discussbean;
    }

    public Object getDiscussbean() {
        return this.discussbean;
    }
}
