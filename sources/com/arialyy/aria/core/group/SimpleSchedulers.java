package com.arialyy.aria.core.group;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.config.Configuration;
import com.arialyy.aria.core.inf.IThreadStateManager;
import com.arialyy.aria.core.loader.IRecordHandler;
import com.arialyy.aria.core.manager.ThreadTaskManager;
import com.arialyy.aria.exception.ExceptionFactory;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.NetUtils;
import java.io.File;

/* loaded from: classes2.dex */
final class SimpleSchedulers implements Handler.Callback {
    private final String TAG = CommonUtil.getClassName(this);
    private GroupRunState mGState;
    private String mKey;
    private SimpleSubQueue mQueue;

    private SimpleSchedulers(GroupRunState groupRunState, String str) {
        this.mQueue = groupRunState.queue;
        this.mGState = groupRunState;
        this.mKey = str;
    }

    private synchronized void handleComplete(AbsSubDLoadUtil absSubDLoadUtil) {
        ALog.d(this.TAG, String.format("子任务【%s】完成", absSubDLoadUtil.getEntity().getFileName()));
        Log.d(this.TAG, String.format("handleComplete, size = %s, completeNum = %s, failNum = %s, stopNum = %s", Integer.valueOf(this.mGState.getSubSize()), Integer.valueOf(this.mGState.getCompleteNum()), Integer.valueOf(this.mGState.getFailNum()), Integer.valueOf(this.mGState.getSubSize())));
        TaskRecord record = absSubDLoadUtil.getRecord();
        if (record != null && record.isBlock) {
            new File(String.format(IRecordHandler.SUB_PATH, record.filePath, 0)).renameTo(new File(record.filePath));
        }
        ThreadTaskManager.getInstance().removeTaskThread(absSubDLoadUtil.getKey());
        this.mGState.listener.onSubComplete(absSubDLoadUtil.getEntity());
        this.mQueue.removeTaskFromExecQ(absSubDLoadUtil);
        this.mGState.updateCompleteNum();
        if (this.mGState.getCompleteNum() + this.mGState.getFailNum() + this.mGState.getStopNum() != this.mGState.getSubSize()) {
            startNext();
            return;
        }
        if (this.mGState.getStopNum() == 0 && this.mGState.getFailNum() == 0) {
            this.mGState.listener.onComplete();
        } else if (this.mGState.getStopNum() != 0 || Configuration.getInstance().dGroupCfg.isSubFailAsStop()) {
            GroupRunState groupRunState = this.mGState;
            groupRunState.listener.onStop(groupRunState.getProgress());
        } else {
            GroupRunState groupRunState2 = this.mGState;
            groupRunState2.listener.onFail(false, ExceptionFactory.getException(5, String.format("任务组【%s】下载失败", groupRunState2.getGroupHash()), null));
        }
        this.mGState.isRunning.set(false);
    }

    private synchronized void handleFail(AbsSubDLoadUtil absSubDLoadUtil, boolean z2) {
        Log.d(this.TAG, String.format("handleFail, size = %s, completeNum = %s, failNum = %s, stopNum = %s", Integer.valueOf(this.mGState.getSubSize()), Integer.valueOf(this.mGState.getCompleteNum()), Integer.valueOf(this.mGState.getFailNum()), Integer.valueOf(this.mGState.getSubSize())));
        Configuration configuration = Configuration.getInstance();
        int subReTryNum = configuration.dGroupCfg.getSubReTryNum();
        boolean zIsNotNetRetry = configuration.appCfg.isNotNetRetry();
        if (z2 && ((NetUtils.isConnected(AriaConfig.getInstance().getAPP()) || zIsNotNetRetry) && absSubDLoadUtil.getLoader() != null && absSubDLoadUtil.getEntity().getFailNum() <= subReTryNum)) {
            SimpleSubRetryQueue.getInstance().offer(absSubDLoadUtil);
            return;
        }
        this.mQueue.removeTaskFromExecQ(absSubDLoadUtil);
        this.mGState.listener.onSubFail(absSubDLoadUtil.getEntity(), ExceptionFactory.getException(5, String.format("任务组子任务【%s】下载失败，下载地址【%s】", absSubDLoadUtil.getEntity().getFileName(), absSubDLoadUtil.getEntity().getUrl()), null));
        this.mGState.countFailNum(absSubDLoadUtil.getKey());
        if (this.mGState.getFailNum() != this.mGState.getSubSize() && this.mGState.getStopNum() + this.mGState.getFailNum() + this.mGState.getCompleteNum() != this.mGState.getSubSize()) {
            startNext();
            return;
        }
        this.mGState.isRunning.set(false);
        if (this.mGState.getCompleteNum() <= 0 || !Configuration.getInstance().dGroupCfg.isSubFailAsStop()) {
            GroupRunState groupRunState = this.mGState;
            groupRunState.listener.onFail(false, ExceptionFactory.getException(5, String.format("任务组【%s】下载失败", groupRunState.getGroupHash()), null));
        } else {
            ALog.e(this.TAG, String.format("任务组【%s】停止", this.mGState.getGroupHash()));
            GroupRunState groupRunState2 = this.mGState;
            groupRunState2.listener.onStop(groupRunState2.getProgress());
        }
    }

    private synchronized void handleStop(AbsSubDLoadUtil absSubDLoadUtil, long j2) {
        Log.d(this.TAG, String.format("handleStop, size = %s, completeNum = %s, failNum = %s, stopNum = %s", Integer.valueOf(this.mGState.getSubSize()), Integer.valueOf(this.mGState.getCompleteNum()), Integer.valueOf(this.mGState.getFailNum()), Integer.valueOf(this.mGState.getSubSize())));
        this.mGState.listener.onSubStop(absSubDLoadUtil.getEntity(), j2);
        this.mGState.countStopNum(absSubDLoadUtil.getKey());
        if (this.mGState.getStopNum() != this.mGState.getSubSize() && this.mGState.getStopNum() + this.mGState.getCompleteNum() + this.mGState.getFailNum() + this.mQueue.getCacheSize() != this.mGState.getSubSize()) {
            startNext();
            return;
        }
        this.mGState.isRunning.set(false);
        GroupRunState groupRunState = this.mGState;
        groupRunState.listener.onStop(groupRunState.getProgress());
    }

    public static SimpleSchedulers newInstance(GroupRunState groupRunState, String str) {
        return new SimpleSchedulers(groupRunState, str);
    }

    private void startNext() {
        if (this.mQueue.isStopAll()) {
            return;
        }
        AbsSubDLoadUtil nextTask = this.mQueue.getNextTask();
        if (nextTask == null) {
            ALog.i(this.TAG, "没有下一子任务");
        } else {
            ALog.d(this.TAG, String.format("启动任务：%s", nextTask.getEntity().getFileName()));
            this.mQueue.startTask(nextTask);
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        Bundle data = message.getData();
        if (data == null) {
            ALog.w(this.TAG, "组合任务子任务调度数据为空");
            return true;
        }
        String string = data.getString(IThreadStateManager.DATA_THREAD_NAME);
        AbsSubDLoadUtil loaderUtil = this.mQueue.getLoaderUtil(string);
        if (loaderUtil == null) {
            ALog.e(this.TAG, String.format("子任务loader不存在，state：%s，key：%s", Integer.valueOf(message.what), string));
            return true;
        }
        long j2 = data.getLong(IThreadStateManager.DATA_THREAD_LOCATION, loaderUtil.getLoader().getWrapper().getEntity().getCurrentProgress());
        int i2 = message.what;
        if (i2 == 1) {
            handleStop(loaderUtil, j2);
            ThreadTaskManager.getInstance().removeSingleTaskThread(this.mKey, string);
        } else if (i2 == 2) {
            handleFail(loaderUtil, data.getBoolean(IThreadStateManager.DATA_RETRY, false));
            ThreadTaskManager.getInstance().removeSingleTaskThread(this.mKey, string);
        } else if (i2 == 4) {
            handleComplete(loaderUtil);
            ThreadTaskManager.getInstance().removeSingleTaskThread(this.mKey, string);
        } else if (i2 == 5) {
            this.mGState.listener.onSubRunning(loaderUtil.getEntity(), ((Long) message.obj).longValue());
        } else if (i2 == 7) {
            this.mGState.listener.onSubPre(loaderUtil.getEntity());
            this.mGState.updateCount(loaderUtil.getKey());
        } else if (i2 == 8) {
            this.mGState.listener.onSubStart(loaderUtil.getEntity());
        }
        return true;
    }
}
