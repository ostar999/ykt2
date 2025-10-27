package com.plv.business.api.common.ppt;

/* loaded from: classes4.dex */
public interface IPLVPPTWarpper<T> {
    void bindPPTView(IPLVPPTView iPLVPPTView);

    void destory();

    void pause();

    void restart();

    void seekTo(int i2);

    void speedUp(int i2);

    @Deprecated
    void startPlay(String str, String str2);
}
