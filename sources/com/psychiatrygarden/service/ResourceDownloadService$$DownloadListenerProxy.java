package com.psychiatrygarden.service;

import com.arialyy.aria.core.scheduler.AptNormalTaskListener;
import com.arialyy.aria.core.task.DownloadTask;

/* loaded from: classes6.dex */
public final class ResourceDownloadService$$DownloadListenerProxy extends AptNormalTaskListener<DownloadTask> {
    private ResourceDownloadService obj;

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.ISchedulerListener
    public void setListener(final Object obj) {
        this.obj = (ResourceDownloadService) obj;
    }

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onPre(final DownloadTask task) {
        this.obj.onTaskPre(task);
    }

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskCancel(final DownloadTask task) {
        this.obj.onTaskCancel(task);
    }

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskComplete(final DownloadTask task) {
        this.obj.onTaskComplete(task);
    }

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskFail(final DownloadTask task, Exception e2) {
        this.obj.onTaskFail(task);
    }

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskRunning(final DownloadTask task) {
        this.obj.onTaskRunning(task);
    }

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskStart(final DownloadTask task) {
        this.obj.onTaskStart(task);
    }

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onTaskStop(final DownloadTask task) {
        this.obj.onTaskStop(task);
    }

    @Override // com.arialyy.aria.core.scheduler.AptNormalTaskListener, com.arialyy.aria.core.scheduler.NormalTaskListenerInterface
    public void onWait(final DownloadTask task) {
        this.obj.onTaskWait(task);
    }
}
