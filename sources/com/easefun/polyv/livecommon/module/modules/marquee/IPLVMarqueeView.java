package com.easefun.polyv.livecommon.module.modules.marquee;

import com.easefun.polyv.livecommon.module.modules.marquee.model.PLVMarqueeModel;

/* loaded from: classes3.dex */
public interface IPLVMarqueeView {
    void pause();

    void setPLVMarqueeModel(PLVMarqueeModel marqueeVO);

    void start();

    void stop();
}
