package com.arialyy.aria.core.command;

import com.arialyy.aria.core.download.AbsGroupTaskWrapper;

/* loaded from: classes2.dex */
final class DGSubStopCmd<T extends AbsGroupTaskWrapper> extends AbsGroupCmd<T> {
    public DGSubStopCmd(T t2) {
        super(t2);
    }

    @Override // com.arialyy.aria.core.command.ICmd
    public void executeCmd() {
        if (checkTask()) {
            this.tempTask.stopSubTask(this.childUrl);
        }
    }
}
