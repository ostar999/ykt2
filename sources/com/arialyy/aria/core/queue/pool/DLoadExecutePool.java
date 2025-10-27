package com.arialyy.aria.core.queue.pool;

import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.util.ALog;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class DLoadExecutePool<TASK extends AbsTask> extends BaseExecutePool<TASK> {
    private final String TAG = "DownloadExecutePool";

    @Override // com.arialyy.aria.core.queue.pool.BaseExecutePool
    public int getMaxSize() {
        return AriaConfig.getInstance().getDConfig().getMaxTaskNum();
    }

    @Override // com.arialyy.aria.core.queue.pool.BaseExecutePool
    public boolean pollFirstTask() {
        TASK taskPollFirst = this.mExecuteQueue.pollFirst();
        if (taskPollFirst == null) {
            ALog.w("DownloadExecutePool", "移除任务失败，错误原因：任务为null");
            return false;
        }
        if (taskPollFirst.isHighestPriorityTask()) {
            return false;
        }
        taskPollFirst.stop();
        return true;
    }

    @Override // com.arialyy.aria.core.queue.pool.BaseExecutePool, com.arialyy.aria.core.queue.pool.IPool
    public boolean putTask(TASK task) {
        synchronized (DLoadExecutePool.class) {
            if (task == null) {
                ALog.e("DownloadExecutePool", "任务不能为空！！");
                return false;
            }
            if (this.mExecuteQueue.contains(task)) {
                if (!task.isRunning()) {
                    return true;
                }
                ALog.e("DownloadExecutePool", "任务【" + task.getTaskName() + "】进入执行队列失败，错误原因：已经在执行队列中");
                return false;
            }
            if (this.mExecuteQueue.size() < this.mSize) {
                return putNewTask(task);
            }
            Iterator<TASK> it = this.mExecuteQueue.iterator();
            while (it.hasNext()) {
                if (it.next().isHighestPriorityTask()) {
                    return false;
                }
            }
            if (!pollFirstTask()) {
                return false;
            }
            return putNewTask(task);
        }
    }
}
