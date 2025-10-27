package com.arialyy.aria.core.scheduler;

import com.arialyy.aria.core.common.AbsNormalEntity;
import com.arialyy.aria.core.task.ITask;

/* loaded from: classes2.dex */
public class AptSubTaskListener<TASK extends ITask, SUB_ENTITY extends AbsNormalEntity> implements SubTaskListener<TASK, SUB_ENTITY>, ISchedulerListener {
    @Override // com.arialyy.aria.core.scheduler.SubTaskListener
    public void onNoSupportBreakPoint(TASK task) {
    }

    @Override // com.arialyy.aria.core.scheduler.SubTaskListener
    public void onSubTaskCancel(TASK task, SUB_ENTITY sub_entity) {
    }

    @Override // com.arialyy.aria.core.scheduler.SubTaskListener
    public void onSubTaskComplete(TASK task, SUB_ENTITY sub_entity) {
    }

    @Deprecated
    public void onSubTaskFail(TASK task, SUB_ENTITY sub_entity) {
    }

    @Override // com.arialyy.aria.core.scheduler.SubTaskListener
    public void onSubTaskFail(TASK task, SUB_ENTITY sub_entity, Exception exc) {
    }

    @Override // com.arialyy.aria.core.scheduler.SubTaskListener
    public void onSubTaskPre(TASK task, SUB_ENTITY sub_entity) {
    }

    @Override // com.arialyy.aria.core.scheduler.SubTaskListener
    public void onSubTaskRunning(TASK task, SUB_ENTITY sub_entity) {
    }

    @Override // com.arialyy.aria.core.scheduler.SubTaskListener
    public void onSubTaskStart(TASK task, SUB_ENTITY sub_entity) {
    }

    @Override // com.arialyy.aria.core.scheduler.SubTaskListener
    public void onSubTaskStop(TASK task, SUB_ENTITY sub_entity) {
    }

    @Override // com.arialyy.aria.core.scheduler.ISchedulerListener
    public void setListener(Object obj) {
    }
}
