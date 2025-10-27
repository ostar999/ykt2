package com.arialyy.aria.core.queue.pool;

import android.text.TextUtils;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.mobile.auth.gatewayauth.ResultCode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/* loaded from: classes2.dex */
public class BaseExecutePool<TASK extends AbsTask> implements IPool<TASK> {
    private static final Object LOCK = new Object();
    private final String TAG = CommonUtil.getClassName(this);
    int mSize = getMaxSize();
    Deque<TASK> mExecuteQueue = new LinkedBlockingDeque(this.mSize);

    public List<TASK> getAllTask() {
        return new ArrayList(this.mExecuteQueue);
    }

    public int getMaxSize() {
        return 2;
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public TASK getTask(String str) {
        synchronized (LOCK) {
            if (TextUtils.isEmpty(str)) {
                ALog.e(this.TAG, "key为null");
                return null;
            }
            for (TASK task : this.mExecuteQueue) {
                if (task.getKey().equals(str)) {
                    return task;
                }
            }
            return null;
        }
    }

    public boolean pollFirstTask() {
        synchronized (LOCK) {
            TASK taskPollFirst = this.mExecuteQueue.pollFirst();
            if (taskPollFirst == null) {
                ALog.w(this.TAG, "移除任务失败，原因：任务为null");
                return false;
            }
            taskPollFirst.stop();
            return true;
        }
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public TASK pollTask() {
        TASK taskPoll;
        synchronized (LOCK) {
            taskPoll = this.mExecuteQueue.poll();
        }
        return taskPoll;
    }

    public boolean putNewTask(TASK task) {
        boolean zOffer;
        synchronized (LOCK) {
            zOffer = this.mExecuteQueue.offer(task);
            String str = this.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("任务【");
            sb.append(task.getTaskName());
            sb.append("】进入执行队列");
            sb.append(zOffer ? ResultCode.MSG_SUCCESS : ResultCode.MSG_FAILED);
            ALog.d(str, sb.toString());
        }
        return zOffer;
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public boolean putTask(TASK task) {
        synchronized (LOCK) {
            if (task == null) {
                ALog.e(this.TAG, "任务不能为空！！");
                return false;
            }
            if (this.mExecuteQueue.contains(task)) {
                ALog.e(this.TAG, "任务【" + task.getTaskName() + "】进入执行队列失败，原因：已经在执行队列中");
                return false;
            }
            if (this.mExecuteQueue.size() < this.mSize) {
                return putNewTask(task);
            }
            if (!pollFirstTask()) {
                return false;
            }
            return putNewTask(task);
        }
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public boolean removeTask(TASK task) {
        synchronized (LOCK) {
            if (task == null) {
                ALog.e(this.TAG, "任务不能为空");
                return false;
            }
            return removeTask(task.getKey());
        }
    }

    public void setMaxNum(int i2) {
        synchronized (LOCK) {
            LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque(i2);
            while (true) {
                TASK taskPoll = this.mExecuteQueue.poll();
                if (taskPoll != null) {
                    linkedBlockingDeque.offer(taskPoll);
                } else {
                    this.mExecuteQueue = linkedBlockingDeque;
                    this.mSize = i2;
                }
            }
        }
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public int size() {
        return this.mExecuteQueue.size();
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public boolean taskExits(String str) {
        return getTask(str) != null;
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public boolean removeTask(String str) {
        synchronized (LOCK) {
            if (TextUtils.isEmpty(str)) {
                ALog.e(this.TAG, "key 为null");
                return false;
            }
            return this.mExecuteQueue.remove(getTask(str));
        }
    }
}
