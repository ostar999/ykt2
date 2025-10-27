package com.arialyy.aria.core.scheduler;

import com.arialyy.aria.core.task.ITask;

/* loaded from: classes2.dex */
public class AptNormalTaskListener<TASK extends ITask> implements NormalTaskListenerInterface<TASK>, ISchedulerListener {
    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onNoSupportBreakPoint(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onPre(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskCancel(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskComplete(TASK task) {
    }

    public void onTaskFail(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskFail(TASK task, Exception exc) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskPre(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskResume(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskRunning(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskStart(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskStop(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onWait(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.ISchedulerListener
    public void setListener(Object obj) {
    }
}
