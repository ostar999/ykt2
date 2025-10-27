package com.plv.business.sub.danmaku.entity;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVDanmakuEntity implements Serializable {
    private List<PLVDanmakuInfo> allDanmaku;

    public List<PLVDanmakuInfo> getAllDanmaku() {
        return this.allDanmaku;
    }

    public void setAllDanmaku(List<PLVDanmakuInfo> list) {
        this.allDanmaku = list;
    }
}
