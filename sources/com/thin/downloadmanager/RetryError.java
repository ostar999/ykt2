package com.thin.downloadmanager;

/* loaded from: classes6.dex */
public class RetryError extends Exception {
    public RetryError() {
        super("Maximum retry exceeded");
    }

    public RetryError(Throwable th) {
        super(th);
    }
}
