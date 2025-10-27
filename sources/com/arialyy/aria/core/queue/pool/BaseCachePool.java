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
public class BaseCachePool<TASK extends AbsTask> implements IPool<TASK> {
    private static final Object LOCK = new Object();
    private static final int MAX_NUM = Integer.MAX_VALUE;
    private final String TAG = CommonUtil.getClassName(this);
    private Deque<TASK> mCacheQueue = new LinkedBlockingDeque(Integer.MAX_VALUE);

    public void clear() {
        this.mCacheQueue.clear();
    }

    public List<TASK> getAllTask() {
        return new ArrayList(this.mCacheQueue);
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public TASK getTask(String str) {
        synchronized (LOCK) {
            if (TextUtils.isEmpty(str)) {
                ALog.e(this.TAG, "key 为null");
                return null;
            }
            for (TASK task : this.mCacheQueue) {
                if (task.getKey().equals(str)) {
                    return task;
                }
            }
            return null;
        }
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public TASK pollTask() {
        TASK taskPollFirst;
        synchronized (LOCK) {
            taskPollFirst = this.mCacheQueue.pollFirst();
        }
        return taskPollFirst;
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public boolean putTask(TASK task) {
        synchronized (LOCK) {
            if (task == null) {
                ALog.e(this.TAG, "任务不能为空！！");
                return false;
            }
            if (this.mCacheQueue.contains(task)) {
                ALog.w(this.TAG, "任务【" + task.getTaskName() + "】进入缓存队列失败，原因：已经在缓存队列中");
                return false;
            }
            boolean zOffer = this.mCacheQueue.offer(task);
            String str = this.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("任务【");
            sb.append(task.getTaskName());
            sb.append("】进入缓存队列");
            sb.append(zOffer ? ResultCode.MSG_SUCCESS : ResultCode.MSG_FAILED);
            ALog.d(str, sb.toString());
            return zOffer;
        }
    }

    public boolean putTaskToFirst(TASK task) {
        return this.mCacheQueue.offerFirst(task);
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public boolean removeTask(TASK task) {
        synchronized (LOCK) {
            if (task == null) {
                ALog.e(this.TAG, "任务不能为空");
                return false;
            }
            return this.mCacheQueue.remove(task);
        }
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public int size() {
        return this.mCacheQueue.size();
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public boolean taskExits(String str) {
        return getTask(str) != null;
    }

    @Override // com.arialyy.aria.core.queue.pool.IPool
    public boolean removeTask(String str) {
        synchronized (LOCK) {
            if (TextUtils.isEmpty(str)) {
                ALog.e(this.TAG, "请传入有效的下载链接");
                return false;
            }
            return this.mCacheQueue.remove(getTask(str));
        }
    }
}
