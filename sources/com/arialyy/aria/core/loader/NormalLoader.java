package com.arialyy.aria.core.loader;

import android.os.Handler;
import android.os.Looper;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.AbsNormalEntity;
import com.arialyy.aria.core.common.CompleteInfo;
import com.arialyy.aria.core.event.EventMsgUtil;
import com.arialyy.aria.core.inf.IThreadStateManager;
import com.arialyy.aria.core.listener.IDLoadListener;
import com.arialyy.aria.core.listener.IEventListener;
import com.arialyy.aria.core.loader.IInfoTask;
import com.arialyy.aria.core.manager.ThreadTaskManager;
import com.arialyy.aria.core.task.IThreadTask;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.exception.AriaException;
import com.arialyy.aria.util.FileUtil;
import java.io.File;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class NormalLoader<T extends AbsTaskWrapper> extends AbsNormalLoader<T> {
    protected boolean isComplete;
    private Looper looper;
    private int startThreadNum;

    public NormalLoader(T t2, IEventListener iEventListener) throws SecurityException {
        super(t2, iEventListener);
        this.isComplete = false;
        this.mTempFile = new File(getEntity().getFilePath());
        EventMsgUtil.getDefault().register(this);
        setUpdateInterval(t2.getConfig().getUpdateInterval());
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    public void addComponent(IRecordHandler iRecordHandler) {
        this.mRecordHandler = iRecordHandler;
        if (iRecordHandler.checkTaskCompleted()) {
            this.mRecord.deleteData();
            this.isComplete = true;
            getListener().onComplete();
        }
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public long getCurrentProgress() {
        return isRunning() ? this.mStateManager.getCurrentProgress() : getEntity().getCurrentProgress();
    }

    public AbsNormalEntity getEntity() {
        return (AbsNormalEntity) this.mTaskWrapper.getEntity();
    }

    @Override // com.arialyy.aria.core.loader.AbsNormalLoader
    public long getFileSize() {
        return getEntity().getFileSize();
    }

    public Looper getLooper() {
        return this.looper;
    }

    @Override // com.arialyy.aria.core.loader.AbsNormalLoader
    public void handleTask(Looper looper) {
        if (isBreak() || this.isComplete) {
            return;
        }
        this.looper = looper;
        this.mInfoTask.run();
    }

    @Override // com.arialyy.aria.core.loader.AbsNormalLoader
    public void onDestroy() {
        super.onDestroy();
        EventMsgUtil.getDefault().unRegister(this);
    }

    public void setMaxSpeed(int i2) {
        int i3;
        for (IThreadTask iThreadTask : getTaskList()) {
            if (iThreadTask != null && (i3 = this.startThreadNum) > 0) {
                iThreadTask.setMaxSpeed(i2 / i3);
            }
        }
    }

    public void startThreadTask() {
        if (isBreak()) {
            return;
        }
        if (getListener() instanceof IDLoadListener) {
            ((IDLoadListener) getListener()).onPostPre(getEntity().getFileSize());
        }
        File file = new File(getEntity().getFilePath());
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            FileUtil.createDir(file.getPath());
        }
        TaskRecord record = this.mRecordHandler.getRecord(getFileSize());
        this.mRecord = record;
        this.mStateManager.setLooper(record, this.looper);
        getTaskList().addAll(this.mTTBuilder.buildThreadTask(this.mRecord, new Handler(this.looper, this.mStateManager.getHandlerCallback())));
        this.startThreadNum = this.mTTBuilder.getCreatedThreadNum();
        this.mStateManager.updateCurrentProgress(getEntity().getCurrentProgress());
        if (this.mStateManager.getCurrentProgress() > 0) {
            getListener().onResume(this.mStateManager.getCurrentProgress());
        } else {
            getListener().onStart(this.mStateManager.getCurrentProgress());
        }
        Iterator<IThreadTask> it = getTaskList().iterator();
        while (it.hasNext()) {
            ThreadTaskManager.getInstance().startThread(this.mTaskWrapper.getKey(), it.next());
        }
        startTimer();
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    public void addComponent(IInfoTask iInfoTask) {
        this.mInfoTask = iInfoTask;
        iInfoTask.setCallback(new IInfoTask.Callback() { // from class: com.arialyy.aria.core.loader.NormalLoader.1
            @Override // com.arialyy.aria.core.loader.IInfoTask.Callback
            public void onFail(AbsEntity absEntity, AriaException ariaException, boolean z2) {
                NormalLoader.this.getListener().onFail(z2, ariaException);
            }

            @Override // com.arialyy.aria.core.loader.IInfoTask.Callback
            public void onSucceed(String str, CompleteInfo completeInfo) {
                NormalLoader.this.startThreadTask();
            }
        });
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    public void addComponent(IThreadStateManager iThreadStateManager) {
        this.mStateManager = iThreadStateManager;
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    public void addComponent(IThreadTaskBuilder iThreadTaskBuilder) {
        this.mTTBuilder = iThreadTaskBuilder;
    }
}
