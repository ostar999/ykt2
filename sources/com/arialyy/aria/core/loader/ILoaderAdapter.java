package com.arialyy.aria.core.loader;

import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.common.SubThreadConfig;
import com.arialyy.aria.core.task.IThreadTask;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;

/* loaded from: classes2.dex */
public interface ILoaderAdapter {
    IThreadTask createThreadTask(SubThreadConfig subThreadConfig);

    boolean handleNewTask(TaskRecord taskRecord, int i2);

    IRecordHandler recordHandler(AbsTaskWrapper absTaskWrapper);
}
