package com.arialyy.aria.core.config;

import com.arialyy.aria.core.event.EventMsgUtil;
import com.arialyy.aria.core.event.UMaxNumEvent;
import com.arialyy.aria.core.event.USpeedEvent;
import com.arialyy.aria.util.ALog;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class UploadConfig extends BaseTaskConfig implements Serializable {
    @Override // com.arialyy.aria.core.config.BaseConfig
    public int getType() {
        return 2;
    }

    @Override // com.arialyy.aria.core.config.BaseTaskConfig
    public UploadConfig setMaxSpeed(int i2) throws Throwable {
        super.setMaxSpeed(i2);
        EventMsgUtil.getDefault().post(new USpeedEvent(i2));
        return this;
    }

    @Override // com.arialyy.aria.core.config.BaseTaskConfig
    public UploadConfig setMaxTaskNum(int i2) throws Throwable {
        if (i2 <= 0) {
            ALog.e(this.TAG, "上传任务最大任务数不能小于0");
            return this;
        }
        super.setMaxTaskNum(i2);
        EventMsgUtil.getDefault().post(new UMaxNumEvent(i2));
        return this;
    }
}
