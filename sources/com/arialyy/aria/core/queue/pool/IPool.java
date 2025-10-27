package com.arialyy.aria.core.queue.pool;

import com.arialyy.aria.core.task.AbsTask;

/* loaded from: classes2.dex */
interface IPool<T extends AbsTask> {
    T getTask(String str);

    T pollTask();

    boolean putTask(T t2);

    boolean removeTask(T t2);

    boolean removeTask(String str);

    int size();

    boolean taskExits(String str);
}
