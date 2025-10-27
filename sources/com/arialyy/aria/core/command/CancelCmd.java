package com.arialyy.aria.core.command;

import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;

/* loaded from: classes2.dex */
public final class CancelCmd<T extends AbsTaskWrapper> extends AbsNormalCmd<T> {
    public boolean removeFile;

    public CancelCmd(T t2, int i2) {
        super(t2, i2);
        this.removeFile = false;
    }

    @Override // com.arialyy.aria.core.command.ICmd
    public void executeCmd() {
        if (this.canExeCmd) {
            AbsTask task = getTask();
            if (task == null) {
                task = createTask();
            }
            if (task != null) {
                this.mTaskWrapper.setRemoveFile(this.removeFile);
                removeTask();
            }
        }
    }
}
