package com.huawei.hms.support.api.entity.push;

import cn.hutool.core.text.CharPool;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.annotation.Packed;

/* loaded from: classes4.dex */
public class EnableNotifyReq implements IMessageEntity {

    @Packed
    public boolean enable;

    @Packed
    public String packageName;

    public String getPackageName() {
        return this.packageName;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z2) {
        this.enable = z2;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public String toString() {
        return "EnableNotifyReq{packageName='" + this.packageName + CharPool.SINGLE_QUOTE + ", enable=" + this.enable + '}';
    }
}
