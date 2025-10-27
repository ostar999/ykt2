package com.arialyy.aria.core.common;

import com.arialyy.aria.core.wrapper.ITaskWrapper;

/* loaded from: classes2.dex */
public class CompleteInfo {
    public int code;
    public Object obj;
    public ITaskWrapper wrapper;

    public CompleteInfo() {
    }

    public CompleteInfo(int i2, ITaskWrapper iTaskWrapper) {
        this.code = i2;
        this.wrapper = iTaskWrapper;
    }
}
