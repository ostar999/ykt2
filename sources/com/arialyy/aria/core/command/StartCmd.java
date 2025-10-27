package com.arialyy.aria.core.command;

import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.common.QueueMod;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.NetUtils;

/* loaded from: classes2.dex */
public final class StartCmd<T extends AbsTaskWrapper> extends AbsNormalCmd<T> {
    private boolean nowStart;

    public StartCmd(T t2, int i2) {
        super(t2, i2);
        this.nowStart = false;
    }

    private void findAllWaitTask() {
        new Thread(new ResumeThread(this.isDownloadCmd, String.format("state=%s", 3))).start();
    }

    @Override // com.arialyy.aria.core.command.ICmd
    public void executeCmd() {
        if (this.canExeCmd) {
            if (!NetUtils.isConnected(AriaConfig.getInstance().getAPP())) {
                ALog.e(this.TAG, "启动任务失败，网络未连接");
                return;
            }
            int maxTaskNum = this.mQueue.getMaxTaskNum();
            AriaConfig ariaConfig = AriaConfig.getInstance();
            String queueMod = this.isDownloadCmd ? ariaConfig.getDConfig().getQueueMod() : ariaConfig.getUConfig().getQueueMod();
            AbsTask task = getTask();
            if (task == null) {
                AbsTask absTaskCreateTask = createTask();
                if (queueMod.equals(QueueMod.NOW.getTag())) {
                    startTask();
                } else if (queueMod.equals(QueueMod.WAIT.getTag())) {
                    int state = absTaskCreateTask.getState();
                    if (this.mQueue.getCurrentExePoolNum() < maxTaskNum) {
                        if (state == 2 || state == 0 || state == -1 || state == 5 || state == 6 || state == 1) {
                            resumeTask();
                        } else if (state == 4) {
                            ALog.w(this.TAG, String.format("任务【%s】已经在运行", absTaskCreateTask.getTaskName()));
                        } else {
                            ALog.d(this.TAG, String.format("开始新任务, 任务状态：%s", Integer.valueOf(state)));
                            startTask();
                        }
                    } else if (this.nowStart) {
                        startTask();
                    } else {
                        sendWaitState(absTaskCreateTask);
                    }
                }
            } else if (this.mQueue.taskIsRunning(task.getKey())) {
                ALog.w(this.TAG, String.format("任务【%s】已经在运行", task.getTaskName()));
            } else if (queueMod.equals(QueueMod.NOW.getTag()) || this.mQueue.getCurrentExePoolNum() < maxTaskNum || this.nowStart) {
                resumeTask();
            } else {
                sendWaitState(task);
            }
            if (this.mQueue.getCurrentCachePoolNum() == 0) {
                findAllWaitTask();
            }
        }
    }

    public void setNowStart(boolean z2) {
        this.nowStart = z2;
    }
}
