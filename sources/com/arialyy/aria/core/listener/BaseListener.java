package com.arialyy.aria.core.listener;

import android.os.Handler;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.task.AbsTask;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.exception.AriaException;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.ErrorHelp;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public abstract class BaseListener implements IEventListener {
    static final int RUN_SAVE_INTERVAL = 5000;
    private boolean isConvertSpeed;
    protected AbsEntity mEntity;
    private long mLastLen;
    long mLastSaveTime;
    private AbsTask mTask;
    protected AbsTaskWrapper mTaskWrapper;
    private long mUpdateInterval;
    protected SoftReference<Handler> outHandler;
    protected String TAG = getClass().getSimpleName();
    private boolean isFirst = true;

    private void handleComplete() {
        this.mEntity.setComplete(true);
        this.mEntity.setCompleteTime(System.currentTimeMillis());
        AbsEntity absEntity = this.mEntity;
        absEntity.setCurrentProgress(absEntity.getFileSize());
        this.mEntity.setPercent(100);
        handleSpeed(0L);
    }

    private void handleSpeed(long j2) {
        long j3 = this.mUpdateInterval;
        if (j3 != 1000) {
            j2 = (j2 * 1000) / j3;
        }
        if (this.isConvertSpeed) {
            AbsEntity absEntity = this.mEntity;
            StringBuilder sb = new StringBuilder();
            sb.append(CommonUtil.formatFileSize(j2 < 0 ? 0.0d : j2));
            sb.append("/s");
            absEntity.setConvertSpeed(sb.toString());
        }
        this.mEntity.setSpeed(j2 < 0 ? 0L : j2);
        int requestType = this.mTaskWrapper.getRequestType();
        if (requestType != 7 && requestType != 8) {
            AbsEntity absEntity2 = this.mEntity;
            absEntity2.setPercent((int) (absEntity2.getFileSize() <= 0 ? 0L : (this.mEntity.getCurrentProgress() * 100) / this.mEntity.getFileSize()));
        }
        if (this.mEntity.getFileSize() != 0) {
            if (j2 == 0) {
                this.mEntity.setTimeLeft(Integer.MAX_VALUE);
            } else {
                AbsEntity absEntity3 = this.mEntity;
                absEntity3.setTimeLeft((int) ((absEntity3.getFileSize() - this.mEntity.getCurrentProgress()) / j2));
            }
        }
    }

    public <TASK extends AbsTask> TASK getTask(Class<TASK> cls) {
        return (TASK) this.mTask;
    }

    public abstract void handleCancel();

    @Override // com.arialyy.aria.core.listener.IEventListener
    public void onCancel() {
        saveData(7, -1L);
        handleSpeed(0L);
        if (this.mTask.getSchedulerType() != 4) {
            ALog.d(this.TAG, "删除任务完成");
            sendInState2Target(5);
        }
    }

    @Override // com.arialyy.aria.core.listener.IEventListener
    public void onComplete() {
        saveData(1, this.mEntity.getFileSize());
        handleSpeed(0L);
        sendInState2Target(6);
    }

    @Override // com.arialyy.aria.core.listener.IEventListener
    public void onFail(boolean z2, AriaException ariaException) throws Throwable {
        AbsEntity absEntity = this.mEntity;
        absEntity.setFailNum(absEntity.getFailNum() + 1);
        saveData(0, this.mEntity.getCurrentProgress());
        handleSpeed(0L);
        this.mTask.setNeedRetry(z2);
        this.mTask.putExpand(AbsTask.ERROR_INFO_KEY, ariaException);
        sendInState2Target(4);
        if (ariaException != null) {
            String exceptionString = ALog.getExceptionString(ariaException);
            ALog.e(this.TAG, exceptionString);
            ErrorHelp.saveError(ariaException.getMessage(), exceptionString);
        }
    }

    @Override // com.arialyy.aria.core.listener.IEventListener
    public void onPre() {
        saveData(5, -1L);
        sendInState2Target(0);
    }

    @Override // com.arialyy.aria.core.listener.IEventListener
    public void onProgress(long j2) {
        this.mEntity.setCurrentProgress(j2);
        long j3 = j2 - this.mLastLen;
        if (this.isFirst) {
            this.isFirst = false;
            j3 = 0;
        }
        handleSpeed(j3);
        sendInState2Target(7);
        if (System.currentTimeMillis() - this.mLastSaveTime >= 5000) {
            saveData(4, j2);
            this.mLastSaveTime = System.currentTimeMillis();
        }
        this.mLastLen = j2;
    }

    @Override // com.arialyy.aria.core.listener.IEventListener
    public void onResume(long j2) {
        saveData(4, j2);
        sendInState2Target(8);
    }

    @Override // com.arialyy.aria.core.listener.IEventListener
    public void onStart(long j2) {
        saveData(4, j2);
        sendInState2Target(2);
    }

    @Override // com.arialyy.aria.core.listener.IEventListener
    public void onStop(long j2) {
        saveData(this.mTask.getSchedulerType() == 3 ? 3 : 2, j2);
        handleSpeed(0L);
        sendInState2Target(3);
    }

    public void saveData(int i2, long j2) {
        this.mEntity.setState(i2);
        if (i2 == 7) {
            handleCancel();
            return;
        }
        if (i2 == 2) {
            this.mEntity.setStopTime(System.currentTimeMillis());
        } else if (i2 == 1) {
            handleComplete();
        }
        if (j2 > 0) {
            this.mEntity.setCurrentProgress(j2);
        }
        this.mEntity.update();
    }

    public void sendInState2Target(int i2) {
        if (this.outHandler.get() != null) {
            this.outHandler.get().obtainMessage(i2, this.mTask).sendToTarget();
        }
    }

    @Override // com.arialyy.aria.core.listener.IEventListener
    public IEventListener setParams(AbsTask absTask, Handler handler) {
        this.outHandler = new SoftReference<>(handler);
        AbsTask absTask2 = (AbsTask) new WeakReference(absTask).get();
        this.mTask = absTask2;
        this.mEntity = absTask2.getTaskWrapper().getEntity();
        AbsTaskWrapper taskWrapper = this.mTask.getTaskWrapper();
        this.mTaskWrapper = taskWrapper;
        this.isConvertSpeed = taskWrapper.getConfig().isConvertSpeed();
        this.mUpdateInterval = this.mTaskWrapper.getConfig().getUpdateInterval();
        this.mLastLen = this.mEntity.getCurrentProgress();
        this.mLastSaveTime = System.currentTimeMillis();
        this.TAG = CommonUtil.getClassName((Class) getClass());
        return this;
    }
}
