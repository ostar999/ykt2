package com.arialyy.aria.core.command;

import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;

/* loaded from: classes2.dex */
final class AddCmd<T extends AbsTaskWrapper> extends AbsNormalCmd<T> {
    public AddCmd(T t2, int i2) {
        super(t2, i2);
    }

    @Override // com.arialyy.aria.core.command.ICmd
    public void executeCmd() {
        if (this.canExeCmd) {
            if (getTask() != null) {
                ALog.w(this.TAG, "添加命令执行失败，【该任务已经存在】");
                return;
            }
            this.mTaskWrapper.getEntity().setState(3);
            createTask();
            sendWaitState();
        }
    }
}
