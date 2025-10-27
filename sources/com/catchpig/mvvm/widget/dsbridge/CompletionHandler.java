package com.catchpig.mvvm.widget.dsbridge;

/* loaded from: classes2.dex */
public interface CompletionHandler<T> {
    void complete();

    void complete(T t2);

    void setProgressData(T t2);
}
