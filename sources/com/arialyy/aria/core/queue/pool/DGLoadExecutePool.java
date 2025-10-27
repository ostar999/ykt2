package com.arialyy.aria.core.queue.pool;

import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.util.CommonUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class DGLoadExecutePool<TASK extends AbsTask> extends DLoadExecutePool<TASK> {
    private final String TAG = CommonUtil.getClassName(this);

    @Override // com.arialyy.aria.core.queue.pool.DLoadExecutePool, com.arialyy.aria.core.queue.pool.BaseExecutePool
    public int getMaxSize() {
        return AriaConfig.getInstance().getDGConfig().getMaxTaskNum();
    }
}
