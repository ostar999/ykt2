package com.arialyy.aria.core.download;

import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class AbsGroupTaskWrapper<ENTITY extends AbsEntity, SUB extends AbsTaskWrapper> extends AbsTaskWrapper<ENTITY> {
    private boolean ignoreTaskOccupy;

    public AbsGroupTaskWrapper(ENTITY entity) {
        super(entity);
        this.ignoreTaskOccupy = false;
    }

    public abstract List<SUB> getSubTaskWrapper();

    public boolean isIgnoreTaskOccupy() {
        return this.ignoreTaskOccupy;
    }

    public void setIgnoreTaskOccupy(boolean z2) {
        this.ignoreTaskOccupy = z2;
    }

    public abstract void setSubTaskWrapper(List<SUB> list);
}
