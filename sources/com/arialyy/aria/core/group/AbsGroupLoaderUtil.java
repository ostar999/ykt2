package com.arialyy.aria.core.group;

import com.arialyy.aria.core.inf.IUtil;
import com.arialyy.aria.core.listener.IEventListener;
import com.arialyy.aria.core.loader.LoaderStructure;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public abstract class AbsGroupLoaderUtil implements IUtil {
    private IEventListener mListener;
    protected AbsGroupLoader mLoader;
    private AbsTaskWrapper mTaskWrapper;
    protected String TAG = CommonUtil.getClassName((Class) getClass());
    private boolean isStop = false;
    private boolean isCancel = false;

    public abstract LoaderStructure buildLoaderStructure();

    @Override // com.arialyy.aria.core.inf.IUtil
    public void cancel() {
        this.isCancel = true;
        this.mLoader.cancel();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public long getCurrentLocation() {
        return this.mLoader.getCurrentProgress();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public long getFileSize() {
        return this.mTaskWrapper.getEntity().getFileSize();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public String getKey() {
        return this.mTaskWrapper.getKey();
    }

    public IEventListener getListener() {
        return this.mListener;
    }

    public abstract AbsGroupLoader getLoader();

    public AbsTaskWrapper getTaskWrapper() {
        return this.mTaskWrapper;
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public boolean isRunning() {
        return this.mLoader.isRunning();
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
            ALog.w(this.TAG, "启动组合任务失败，任务已停止或已取消");
            return;
        }
        this.mListener.onPre();
        buildLoaderStructure();
        new Thread(this.mLoader).start();
    }

    public void startSubTask(String str) {
        getLoader().startSubTask(str);
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public void stop() {
        this.isStop = true;
        this.mLoader.stop();
    }

    public void stopSubTask(String str) {
        getLoader().stopSubTask(str);
    }
}
