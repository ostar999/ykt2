package com.arialyy.aria.core.queue;

import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.event.Event;
import com.arialyy.aria.core.event.EventMsgUtil;
import com.arialyy.aria.core.event.UMaxNumEvent;
import com.arialyy.aria.core.scheduler.TaskSchedulers;
import com.arialyy.aria.core.task.UploadTask;
import com.arialyy.aria.core.upload.UTaskWrapper;
import com.arialyy.aria.util.ALog;

/* loaded from: classes2.dex */
public class UTaskQueue extends AbsTaskQueue<UploadTask, UTaskWrapper> {
    private static volatile UTaskQueue INSTANCE = null;
    private static final String TAG = "UploadTaskQueue";

    private UTaskQueue() {
    }

    public static UTaskQueue getInstance() {
        if (INSTANCE == null) {
            synchronized (UTaskQueue.class) {
                INSTANCE = new UTaskQueue();
                EventMsgUtil.getDefault().register(INSTANCE);
            }
        }
        return INSTANCE;
    }

    @Override // com.arialyy.aria.core.queue.ITaskQueue
    public int getMaxTaskNum() {
        return AriaConfig.getInstance().getUConfig().getMaxTaskNum();
    }

    @Override // com.arialyy.aria.core.queue.AbsTaskQueue
    public int getOldMaxNum() {
        return AriaConfig.getInstance().getUConfig().oldMaxTaskNum;
    }

    @Override // com.arialyy.aria.core.queue.AbsTaskQueue
    public int getQueueType() {
        return 3;
    }

    @Event
    public void maxTaskNum(UMaxNumEvent uMaxNumEvent) {
        setMaxTaskNum(uMaxNumEvent.maxNum);
    }

    @Override // com.arialyy.aria.core.queue.AbsTaskQueue, com.arialyy.aria.core.queue.ITaskQueue
    public UploadTask createTask(UTaskWrapper uTaskWrapper) {
        super.createTask((UTaskQueue) uTaskWrapper);
        if (!this.mCachePool.taskExits(uTaskWrapper.getKey()) && !this.mExecutePool.taskExits(uTaskWrapper.getKey())) {
            UploadTask uploadTask = (UploadTask) TaskFactory.getInstance().createTask(uTaskWrapper, TaskSchedulers.getInstance());
            addTask(uploadTask);
            return uploadTask;
        }
        ALog.w(TAG, "任务已存在");
        return null;
    }
}
