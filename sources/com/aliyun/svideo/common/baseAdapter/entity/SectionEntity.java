package com.aliyun.svideo.common.baseAdapter.entity;

import java.io.Serializable;

/* loaded from: classes2.dex */
public abstract class SectionEntity<T> implements Serializable {
    public String header;
    public boolean isHeader;

    /* renamed from: t, reason: collision with root package name */
    public T f3577t;

    public SectionEntity(boolean z2, String str) {
        this.isHeader = z2;
        this.header = str;
        this.f3577t = null;
    }

    public void setT(T t2) {
        this.f3577t = t2;
    }

    public SectionEntity(T t2) {
        this.isHeader = false;
        this.header = null;
        this.f3577t = t2;
    }
}
