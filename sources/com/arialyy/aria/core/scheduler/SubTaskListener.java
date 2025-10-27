package com.arialyy.aria.core.scheduler;

import com.arialyy.aria.core.common.AbsNormalEntity;
import com.arialyy.aria.core.task.ITask;

/* loaded from: classes2.dex */
public interface SubTaskListener<TASK extends ITask, SUB_ENTITY extends AbsNormalEntity> extends TaskInternalListenerInterface {
    void onNoSupportBreakPoint(TASK task);

    void onSubTaskCancel(TASK task, SUB_ENTITY sub_entity);

    void onSubTaskComplete(TASK task, SUB_ENTITY sub_entity);

    void onSubTaskFail(TASK task, SUB_ENTITY sub_entity, Exception exc);

    void onSubTaskPre(TASK task, SUB_ENTITY sub_entity);

    void onSubTaskRunning(TASK task, SUB_ENTITY sub_entity);

    void onSubTaskStart(TASK task, SUB_ENTITY sub_entity);

    void onSubTaskStop(TASK task, SUB_ENTITY sub_entity);
}
