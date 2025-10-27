package com.arialyy.aria.core.inf;

import android.text.TextUtils;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.inf.AbsTarget;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public abstract class AbsTarget<TARGET extends AbsTarget> {
    protected String TAG = CommonUtil.getClassName(this);
    private AbsEntity mEntity;
    private AbsTaskWrapper mTaskWrapper;

    public AbsEntity getEntity() {
        return this.mEntity;
    }

    public AbsTaskWrapper getTaskWrapper() {
        return this.mTaskWrapper;
    }

    public TARGET resetState() {
        getTaskWrapper().getEntity().setState(3);
        getTaskWrapper().setRefreshInfo(true);
        return this;
    }

    public TARGET setExtendField(String str) {
        if (TextUtils.isEmpty(str)) {
            return this;
        }
        if (TextUtils.isEmpty(this.mEntity.getStr()) || !this.mEntity.getStr().equals(str)) {
            this.mEntity.setStr(str);
        } else {
            ALog.e(this.TAG, "设置扩展字段失败，扩展字段为一致");
        }
        return this;
    }

    public void setTaskWrapper(AbsTaskWrapper absTaskWrapper) {
        this.mTaskWrapper = absTaskWrapper;
        this.mEntity = absTaskWrapper.getEntity();
    }
}
