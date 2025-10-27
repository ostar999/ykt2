package com.arialyy.aria.core.scheduler;

import com.arialyy.aria.core.task.ITask;

/* loaded from: classes2.dex */
public interface NormalTaskListenerInterface<TASK extends ITask> extends TaskInternalListenerInterface {
    void onNoSupportBreakPoint(TASK task);

    void onPre(TASK task);

    void onTaskCancel(TASK task);

    void onTaskComplete(TASK task);

    void onTaskFail(TASK task, Exception exc);

    void onTaskPre(TASK task);

    void onTaskResume(TASK task);

    void onTaskRunning(TASK task);

    void onTaskStart(TASK task);

    void onTaskStop(TASK task);

    void onWait(TASK task);
}
