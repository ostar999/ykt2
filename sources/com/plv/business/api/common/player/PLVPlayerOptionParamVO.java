package com.plv.business.api.common.player;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVPlayerOptionParamVO {
    private final String name;
    private final int type;
    private final Object value;

    public PLVPlayerOptionParamVO(int i2, String str, Object obj) {
        this.type = i2;
        this.name = str;
        this.value = obj;
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        return "{name='" + this.name + CharPool.SINGLE_QUOTE + ", value='" + this.value + CharPool.SINGLE_QUOTE + '}';
    }
}
