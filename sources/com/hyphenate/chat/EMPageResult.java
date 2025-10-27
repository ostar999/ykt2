package com.hyphenate.chat;

import java.util.List;

/* loaded from: classes4.dex */
public class EMPageResult<T> extends EMResult<List<T>> {
    private int pageCount;

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int i2) {
        this.pageCount = i2;
    }
}
