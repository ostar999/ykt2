package com.arialyy.aria.core.queue;

import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.event.DGMaxNumEvent;
import com.arialyy.aria.core.event.Event;
import com.arialyy.aria.core.event.EventMsgUtil;
import com.arialyy.aria.core.scheduler.TaskSchedulers;
import com.arialyy.aria.core.task.DownloadGroupTask;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public class DGroupTaskQueue extends AbsTaskQueue<DownloadGroupTask, DGTaskWrapper> {
    private static volatile DGroupTaskQueue INSTANCE;
    private final String TAG = CommonUtil.getClassName(this);

    private DGroupTaskQueue() {
    }

    public static DGroupTaskQueue getInstance() {
        if (INSTANCE == null) {
            synchronized (DGroupTaskQueue.class) {
                INSTANCE = new DGroupTaskQueue();
                EventMsgUtil.getDefault().register(INSTANCE);
            }
        }
        return INSTANCE;
    }

    @Override // com.arialyy.aria.core.queue.ITaskQueue
    public int getMaxTaskNum() {
        return AriaConfig.getInstance().getDGConfig().getMaxTaskNum();
    }

    @Override // com.arialyy.aria.core.queue.AbsTaskQueue
    public int getOldMaxNum() {
        return AriaConfig.getInstance().getDGConfig().oldMaxTaskNum;
    }

    @Override // com.arialyy.aria.core.queue.AbsTaskQueue
    public int getQueueType() {
        return 2;
    }

    @Event
    public void maxTaskNum(DGMaxNumEvent dGMaxNumEvent) {
        setMaxTaskNum(dGMaxNumEvent.maxNum);
    }

    @Override // com.arialyy.aria.core.queue.AbsTaskQueue, com.arialyy.aria.core.queue.ITaskQueue
    public DownloadGroupTask createTask(DGTaskWrapper dGTaskWrapper) {
        super.createTask((DGroupTaskQueue) dGTaskWrapper);
        if (!this.mCachePool.taskExits(dGTaskWrapper.getKey()) && !this.mExecutePool.taskExits(dGTaskWrapper.getKey())) {
            DownloadGroupTask downloadGroupTask = (DownloadGroupTask) TaskFactory.getInstance().createTask(dGTaskWrapper, TaskSchedulers.getInstance());
            addTask(downloadGroupTask);
            return downloadGroupTask;
        }
        ALog.w(this.TAG, "任务已存在");
        return null;
    }
}
