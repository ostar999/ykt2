package com.arialyy.aria.core.loader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.CompleteInfo;
import com.arialyy.aria.core.inf.IThreadStateManager;
import com.arialyy.aria.core.loader.IInfoTask;
import com.arialyy.aria.core.manager.ThreadTaskManager;
import com.arialyy.aria.core.task.IThreadTask;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.exception.AriaException;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class SubLoader implements ILoader, ILoaderVisitor {
    private IInfoTask infoTask;
    protected IThreadStateManager mStateManager;
    private String parentKey;
    private TaskRecord record;
    private IRecordHandler recordHandler;
    private Handler schedulers;
    private IThreadTaskBuilder ttBuild;
    private AbsTaskWrapper wrapper;
    private String TAG = CommonUtil.getClassName(this);
    private boolean needGetInfo = true;
    private boolean isCancel = false;
    private boolean isStop = false;
    private List<IThreadTask> mTask = new ArrayList();

    public SubLoader(AbsTaskWrapper absTaskWrapper, Handler handler) {
        this.wrapper = absTaskWrapper;
        this.schedulers = handler;
    }

    private void checkComponent() {
        if (this.recordHandler == null) {
            throw new NullPointerException("任务记录组件为空");
        }
        if (this.infoTask == null) {
            throw new NullPointerException("文件信息组件为空");
        }
        if (this.ttBuild == null) {
            throw new NullPointerException("线程任务组件为空");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlerTask() {
        if (isBreak()) {
            return;
        }
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null) {
            Looper.prepare();
            looperMyLooper = Looper.myLooper();
        }
        TaskRecord record = this.recordHandler.getRecord(this.wrapper.getEntity().getFileSize());
        this.record = record;
        if (record == null) {
            ALog.d(this.TAG, "子任务记录为空");
            sendFailState(false);
            return;
        }
        if (record.threadRecords != null && !TextUtils.isEmpty(record.filePath) && new File(this.record.filePath).exists() && !this.record.threadRecords.isEmpty() && this.record.threadRecords.get(0).isComplete) {
            ALog.d(this.TAG, "子任务已完成，key：" + this.wrapper.getKey());
            sendNormalState(4);
            return;
        }
        List<IThreadTask> listBuildThreadTask = this.ttBuild.buildThreadTask(this.record, new Handler(looperMyLooper, this.mStateManager.getHandlerCallback()));
        this.mStateManager.setLooper(this.record, looperMyLooper);
        if (listBuildThreadTask == null || listBuildThreadTask.isEmpty()) {
            ALog.e(this.TAG, "创建子任务的线程任务失败，key：" + this.wrapper.getKey());
            sendFailState(false);
            return;
        }
        if (TextUtils.isEmpty(this.parentKey)) {
            ALog.e(this.TAG, "parentKey为空");
            sendFailState(false);
            return;
        }
        sendNormalState(7);
        this.mTask.addAll(listBuildThreadTask);
        try {
            Iterator<IThreadTask> it = this.mTask.iterator();
            while (it.hasNext()) {
                ThreadTaskManager.getInstance().startThread(this.parentKey, it.next());
            }
            sendNormalState(8);
            this.mStateManager.updateCurrentProgress(getWrapper().getEntity().getCurrentProgress());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Looper.loop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFailState(boolean z2) {
        Message messageObtainMessage = this.schedulers.obtainMessage();
        Bundle data = messageObtainMessage.getData();
        if (data == null) {
            data = new Bundle();
        }
        data.putString(IThreadStateManager.DATA_THREAD_NAME, getKey());
        data.putBoolean(IThreadStateManager.DATA_RETRY, z2);
        messageObtainMessage.what = 2;
        messageObtainMessage.setData(data);
        messageObtainMessage.sendToTarget();
    }

    private void sendNormalState(int i2) {
        Message messageObtainMessage = this.schedulers.obtainMessage();
        Bundle data = messageObtainMessage.getData();
        if (data == null) {
            data = new Bundle();
        }
        data.putString(IThreadStateManager.DATA_THREAD_NAME, getKey());
        messageObtainMessage.what = i2;
        messageObtainMessage.setData(data);
        messageObtainMessage.sendToTarget();
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    public void addComponent(IRecordHandler iRecordHandler) {
        this.recordHandler = iRecordHandler;
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public void cancel() {
        if (this.isCancel) {
            ALog.w(this.TAG, "子任务已取消");
            return;
        }
        this.isCancel = true;
        Iterator<IThreadTask> it = this.mTask.iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public long getCurrentProgress() {
        return isRunning() ? this.mStateManager.getCurrentProgress() : getWrapper().getEntity().getCurrentProgress();
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public String getKey() {
        return CommonUtil.getThreadName(this.wrapper.getKey(), 0);
    }

    public TaskRecord getRecord() {
        return this.record;
    }

    public AbsTaskWrapper getWrapper() {
        return this.wrapper;
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public boolean isBreak() {
        if (!this.isCancel && !this.isStop) {
            return false;
        }
        ALog.d(this.TAG, "isCancel = " + this.isCancel + ", isStop = " + this.isStop);
        ALog.d(this.TAG, String.format("任务【%s】已停止或取消了", this.wrapper.getKey()));
        return true;
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public boolean isRunning() {
        if (this.mTask.isEmpty()) {
            return false;
        }
        Iterator<IThreadTask> it = this.mTask.iterator();
        while (it.hasNext()) {
            if (!it.next().isBreak()) {
                return true;
            }
        }
        return false;
    }

    public void retryTask() {
        try {
            if (this.mTask.isEmpty()) {
                ALog.e(this.TAG, "子任务的线程任务为空");
                return;
            }
            Iterator<IThreadTask> it = this.mTask.iterator();
            while (it.hasNext()) {
                it.next().call();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        checkComponent();
        if (isBreak()) {
            return;
        }
        if (this.needGetInfo) {
            this.infoTask.run();
        } else {
            handlerTask();
        }
    }

    public void setNeedGetInfo(boolean z2) {
        this.needGetInfo = z2;
    }

    public void setParentKey(String str) {
        this.parentKey = str;
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public void stop() {
        if (this.isStop) {
            ALog.w(this.TAG, "子任务已停止");
            return;
        }
        this.isStop = true;
        Iterator<IThreadTask> it = this.mTask.iterator();
        while (it.hasNext()) {
            it.next().stop();
        }
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    public void addComponent(IInfoTask iInfoTask) {
        this.infoTask = iInfoTask;
        iInfoTask.setCallback(new IInfoTask.Callback() { // from class: com.arialyy.aria.core.loader.SubLoader.1
            @Override // com.arialyy.aria.core.loader.IInfoTask.Callback
            public void onFail(AbsEntity absEntity, AriaException ariaException, boolean z2) {
                SubLoader.this.sendFailState(z2);
            }

            @Override // com.arialyy.aria.core.loader.IInfoTask.Callback
            public void onSucceed(String str, CompleteInfo completeInfo) {
                SubLoader.this.handlerTask();
            }
        });
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    public void addComponent(IThreadStateManager iThreadStateManager) {
        this.mStateManager = iThreadStateManager;
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    public void addComponent(IThreadTaskBuilder iThreadTaskBuilder) {
        this.ttBuild = iThreadTaskBuilder;
    }
}
