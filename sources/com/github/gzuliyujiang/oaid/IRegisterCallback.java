package com.github.gzuliyujiang.oaid;

/* loaded from: classes3.dex */
public interface IRegisterCallback {
    @Deprecated
    void onComplete();

    void onComplete(String str, Exception exc);
}
