package com.arialyy.aria.core.group;

import com.arialyy.aria.core.inf.IUtil;

/* loaded from: classes2.dex */
interface ISubQueue<Fileer extends IUtil> {
    void addTask(Fileer fileer);

    void clear();

    Fileer getNextTask();

    void modifyMaxExecNum(int i2);

    void removeAllTask();

    void removeTask(Fileer fileer);

    void removeTaskFromExecQ(Fileer fileer);

    void startTask(Fileer fileer);

    void stopAllTask();

    void stopTask(Fileer fileer);
}
