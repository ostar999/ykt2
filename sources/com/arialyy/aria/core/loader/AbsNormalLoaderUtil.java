package com.arialyy.aria.core.loader;

import com.arialyy.aria.core.inf.IUtil;
import com.arialyy.aria.core.listener.IEventListener;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.exception.AriaException;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public abstract class AbsNormalLoaderUtil implements IUtil {
    private IEventListener mListener;
    protected AbsNormalLoader mLoader;
    private AbsTaskWrapper mTaskWrapper;
    protected String TAG = CommonUtil.getClassName((Class) getClass());
    private boolean isStop = false;
    private boolean isCancel = false;

    public abstract LoaderStructure BuildLoaderStructure();

    @Override // com.arialyy.aria.core.inf.IUtil
    public void cancel() {
        this.isCancel = true;
        this.mLoader.cancel();
        onCancel();
    }

    public void fail(AriaException ariaException, boolean z2) {
        if (this.isStop || this.isCancel) {
            return;
        }
        this.mListener.onFail(z2, ariaException);
        this.mLoader.onDestroy();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public long getCurrentLocation() {
        return this.mLoader.getCurrentProgress();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public long getFileSize() {
        return this.mLoader.getFileSize();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public String getKey() {
        return this.mTaskWrapper.getKey();
    }

    public IEventListener getListener() {
        return this.mListener;
    }

    public abstract AbsNormalLoader getLoader();

    public AbsTaskWrapper getTaskWrapper() {
        return this.mTaskWrapper;
    }

    public boolean isCancel() {
        return this.isCancel;
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public boolean isRunning() {
        return this.mLoader.isRunning();
    }

    public boolean isStop() {
        return this.isStop;
    }

    public void onCancel() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public IUtil setParams(AbsTaskWrapper absTaskWrapper, IEventListener iEventListener) {
        this.mTaskWrapper = absTaskWrapper;
        this.mListener = iEventListener;
        this.mLoader = getLoader();
        return this;
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public void start() {
        if (this.isStop || this.isCancel) {
            ALog.w(this.TAG, "启动任务失败，任务已停止或已取消");
            return;
        }
        this.mListener.onPre();
        BuildLoaderStructure();
        new Thread(this.mLoader).start();
        onStart();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public void stop() {
        this.isStop = true;
        this.mLoader.stop();
        onStop();
    }
}
