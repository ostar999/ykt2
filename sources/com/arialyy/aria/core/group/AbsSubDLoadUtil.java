package com.arialyy.aria.core.group;

import android.os.Handler;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.inf.IUtil;
import com.arialyy.aria.core.listener.IEventListener;
import com.arialyy.aria.core.loader.LoaderStructure;
import com.arialyy.aria.core.loader.SubLoader;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public abstract class AbsSubDLoadUtil implements IUtil, Runnable {
    protected SubLoader mDLoader;
    private Handler mSchedulers;
    private DTaskWrapper mWrapper;
    private boolean needGetInfo;
    private String parentKey;
    protected final String TAG = CommonUtil.getClassName((Class) getClass());
    private boolean isStop = false;
    private boolean isCancel = false;

    public AbsSubDLoadUtil(Handler handler, boolean z2, String str) {
        this.mSchedulers = handler;
        this.parentKey = str;
        this.needGetInfo = z2;
    }

    public abstract LoaderStructure buildLoaderStructure();

    @Override // com.arialyy.aria.core.inf.IUtil
    public void cancel() {
        if (this.isCancel) {
            ALog.w(this.TAG, "子任务已取消");
            return;
        }
        this.isCancel = true;
        if (this.mDLoader == null || !isRunning()) {
            this.mSchedulers.obtainMessage(5, this).sendToTarget();
        } else {
            this.mDLoader.cancel();
        }
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    @Deprecated
    public long getCurrentLocation() {
        return -1L;
    }

    public DownloadEntity getEntity() {
        return this.mWrapper.getEntity();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    @Deprecated
    public long getFileSize() {
        return -1L;
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public String getKey() {
        return this.mDLoader.getKey();
    }

    public abstract SubLoader getLoader();

    public String getParentKey() {
        return this.parentKey;
    }

    public TaskRecord getRecord() {
        return getLoader().getRecord();
    }

    public Handler getSchedulers() {
        return this.mSchedulers;
    }

    public DTaskWrapper getWrapper() {
        return this.mWrapper;
    }

    public boolean isNeedGetInfo() {
        return this.needGetInfo;
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public boolean isRunning() {
        SubLoader subLoader = this.mDLoader;
        return subLoader != null && subLoader.isRunning();
    }

    public void reStart() {
        SubLoader subLoader = this.mDLoader;
        if (subLoader != null) {
            subLoader.retryTask();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.isStop || this.isCancel) {
            return;
        }
        buildLoaderStructure();
        new Thread(this.mDLoader).start();
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public IUtil setParams(AbsTaskWrapper absTaskWrapper, IEventListener iEventListener) {
        this.mWrapper = (DTaskWrapper) absTaskWrapper;
        this.mDLoader = getLoader();
        return this;
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    @Deprecated
    public void start() {
        throw new AssertionError("请在线程池中使用");
    }

    @Override // com.arialyy.aria.core.inf.IUtil
    public void stop() {
        if (this.isStop) {
            ALog.w(this.TAG, "任务已停止");
            return;
        }
        this.isStop = true;
        if (this.mDLoader == null || !isRunning()) {
            this.mSchedulers.obtainMessage(3, this).sendToTarget();
        } else {
            this.mDLoader.stop();
        }
    }
}
