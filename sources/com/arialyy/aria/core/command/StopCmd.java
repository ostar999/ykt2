package com.arialyy.aria.core.command;

import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;

/* loaded from: classes2.dex */
final class StopCmd<T extends AbsTaskWrapper> extends AbsNormalCmd<T> {
    public StopCmd(T t2, int i2) {
        super(t2, i2);
    }

    @Override // com.arialyy.aria.core.command.ICmd
    public void executeCmd() {
        if (this.canExeCmd) {
            if (getTask() != null) {
                stopTask();
            } else if (this.mTaskWrapper.getEntity().getState() == 4) {
                stopTask();
            } else {
                ALog.w(this.TAG, "停止命令执行失败，【调度器中没有该任务】");
            }
        }
    }
}
