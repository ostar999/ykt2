package com.arialyy.aria.core.command;

import com.arialyy.aria.core.download.AbsGroupTaskWrapper;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.queue.AbsTaskQueue;
import com.arialyy.aria.core.queue.DGroupTaskQueue;
import com.arialyy.aria.core.task.AbsGroupTask;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public abstract class AbsGroupCmd<T extends AbsGroupTaskWrapper> extends AbsCmd<T> {
    String childUrl;
    AbsGroupTask tempTask;

    public AbsGroupCmd(T t2) {
        this.mTaskWrapper = t2;
        this.TAG = CommonUtil.getClassName(this);
        if (t2 instanceof DGTaskWrapper) {
            this.mQueue = DGroupTaskQueue.getInstance();
            this.isDownloadCmd = true;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.arialyy.aria.core.common.AbsEntity] */
    public boolean checkTask() {
        AbsGroupTask absGroupTask = (AbsGroupTask) this.mQueue.getTask(((AbsGroupTaskWrapper) this.mTaskWrapper).getEntity().getKey());
        this.tempTask = absGroupTask;
        if (absGroupTask != null) {
            return true;
        }
        createTask();
        if (!this.tempTask.isComplete()) {
            return true;
        }
        ALog.i(this.TAG, "任务已完成");
        return false;
    }

    public AbsTask createTask() {
        AbsGroupTask absGroupTask = (AbsGroupTask) this.mQueue.createTask((AbsTaskQueue) this.mTaskWrapper);
        this.tempTask = absGroupTask;
        return absGroupTask;
    }
}
