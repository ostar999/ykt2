package com.arialyy.aria.core.group;

import android.os.Handler;
import android.os.Looper;
import com.arialyy.aria.core.config.Configuration;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.inf.IThreadStateManager;
import com.arialyy.aria.core.listener.IDGroupListener;
import com.arialyy.aria.core.listener.IEventListener;
import com.arialyy.aria.core.loader.IInfoTask;
import com.arialyy.aria.core.loader.ILoader;
import com.arialyy.aria.core.loader.ILoaderVisitor;
import com.arialyy.aria.core.loader.IRecordHandler;
import com.arialyy.aria.core.loader.IThreadTaskBuilder;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.exception.AriaException;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.mobile.auth.gatewayauth.ResultCode;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class AbsGroupLoader implements ILoaderVisitor, ILoader {
    private DGTaskWrapper mGTWrapper;
    protected IInfoTask mInfoTask;
    private IDGroupListener mListener;
    private Handler mScheduler;
    private GroupRunState mState;
    private ScheduledThreadPoolExecutor mTimer;
    protected final String TAG = CommonUtil.getClassName((Class) getClass());
    private long mCurrentLocation = 0;
    private boolean isStop = false;
    private boolean isCancel = false;
    private SimpleSubQueue mSubQueue = SimpleSubQueue.newInstance();
    private Map<String, AbsSubDLoadUtil> mExeLoader = new WeakHashMap();
    private Map<String, DTaskWrapper> mCache = new WeakHashMap();
    private long mUpdateInterval = Configuration.getInstance().downloadCfg.getUpdateInterval();

    public AbsGroupLoader(AbsTaskWrapper absTaskWrapper, IEventListener iEventListener) {
        this.mListener = (IDGroupListener) iEventListener;
        this.mGTWrapper = (DGTaskWrapper) absTaskWrapper;
    }

    private void checkComponent() {
        if (this.mInfoTask == null) {
            throw new NullPointerException("文件信息组件为空");
        }
    }

    private long checkFileExists(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.length();
        }
        File file2 = new File(String.format(IRecordHandler.SUB_PATH, str, 0));
        if (file2.exists()) {
            return file2.length();
        }
        return -1L;
    }

    private boolean checkSubTask(String str, String str2) {
        DTaskWrapper dTaskWrapper = this.mCache.get(str);
        if (dTaskWrapper == null) {
            ALog.w(this.TAG, "任务组中没有该任务【" + str + "】，" + str2 + ResultCode.MSG_FAILED);
            return false;
        }
        if (dTaskWrapper.getState() != 1) {
            return true;
        }
        ALog.w(this.TAG, "任务【" + str + "】已完成，" + str2 + ResultCode.MSG_FAILED);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void closeTimer() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mTimer;
        if (scheduledThreadPoolExecutor != null && !scheduledThreadPoolExecutor.isShutdown()) {
            this.mTimer.shutdown();
        }
    }

    private AbsSubDLoadUtil getDownloader(String str, boolean z2) {
        AbsSubDLoadUtil absSubDLoadUtil = this.mExeLoader.get(str);
        return absSubDLoadUtil == null ? createSubLoader(this.mCache.get(str), z2) : absSubDLoadUtil;
    }

    private void initState(Looper looper) {
        this.mState = new GroupRunState(getWrapper().getKey(), this.mListener, this.mSubQueue);
        for (DTaskWrapper dTaskWrapper : this.mGTWrapper.getSubTaskWrapper()) {
            long jCheckFileExists = checkFileExists(dTaskWrapper.getEntity().getFilePath());
            if (dTaskWrapper.getEntity().getState() == 1 && jCheckFileExists > 0 && jCheckFileExists == dTaskWrapper.getEntity().getFileSize()) {
                this.mCurrentLocation += dTaskWrapper.getEntity().getFileSize();
            } else {
                if (jCheckFileExists <= 0) {
                    dTaskWrapper.getEntity().setCurrentProgress(0L);
                }
                dTaskWrapper.getEntity().setState(6);
                this.mCache.put(dTaskWrapper.getKey(), dTaskWrapper);
                this.mCurrentLocation += dTaskWrapper.getEntity().getCurrentProgress();
            }
        }
        if (getWrapper().getSubTaskWrapper().size() != this.mState.getCompleteNum()) {
            getWrapper().setState(6);
        }
        this.mState.updateProgress(this.mCurrentLocation);
        this.mScheduler = new Handler(looper, SimpleSchedulers.newInstance(this.mState, this.mGTWrapper.getKey()));
    }

    private void startRunningFlow() {
        closeTimer();
        Looper.prepare();
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == Looper.getMainLooper()) {
            throw new IllegalThreadStateException("不能在主线程程序中调用Loader");
        }
        initState(looperMyLooper);
        getState().setSubSize(getWrapper().getSubTaskWrapper().size());
        if (getState().getCompleteNum() != 0 && getState().getCompleteNum() == getState().getSubSize()) {
            this.mListener.onComplete();
            return;
        }
        startTimer();
        handlerTask(looperMyLooper);
        Looper.loop();
    }

    private synchronized void startTimer() {
        this.mState.isRunning.set(true);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        this.mTimer = scheduledThreadPoolExecutor;
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new Runnable() { // from class: com.arialyy.aria.core.group.AbsGroupLoader.1
            @Override // java.lang.Runnable
            public void run() {
                if (!AbsGroupLoader.this.mState.isRunning.get()) {
                    AbsGroupLoader.this.closeTimer();
                    return;
                }
                long fileSize = 0;
                if (AbsGroupLoader.this.mCurrentLocation >= 0) {
                    for (DTaskWrapper dTaskWrapper : AbsGroupLoader.this.mGTWrapper.getSubTaskWrapper()) {
                        fileSize += dTaskWrapper.getState() == 1 ? dTaskWrapper.getEntity().getFileSize() : dTaskWrapper.getEntity().getCurrentProgress();
                    }
                    AbsGroupLoader.this.mCurrentLocation = fileSize;
                    AbsGroupLoader.this.mState.updateProgress(AbsGroupLoader.this.mCurrentLocation);
                    AbsGroupLoader.this.mListener.onProgress(fileSize);
                }
            }
        }, 0L, this.mUpdateInterval, TimeUnit.MILLISECONDS);
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    @Deprecated
    public void addComponent(IThreadStateManager iThreadStateManager) {
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    @Deprecated
    public void addComponent(IRecordHandler iRecordHandler) {
    }

    @Override // com.arialyy.aria.core.loader.ILoaderVisitor
    @Deprecated
    public void addComponent(IThreadTaskBuilder iThreadTaskBuilder) {
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public void cancel() {
        this.isCancel = true;
        closeTimer();
        this.mSubQueue.removeAllTask();
        this.mListener.onCancel();
    }

    public abstract AbsSubDLoadUtil createSubLoader(DTaskWrapper dTaskWrapper, boolean z2);

    public void fail(AriaException ariaException, boolean z2) {
        closeTimer();
        getListener().onFail(z2, ariaException);
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public long getCurrentProgress() {
        return this.mCurrentLocation;
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public String getKey() {
        return this.mGTWrapper.getKey();
    }

    public IDGroupListener getListener() {
        return this.mListener;
    }

    public Handler getScheduler() {
        return this.mScheduler;
    }

    public GroupRunState getState() {
        return this.mState;
    }

    public DGTaskWrapper getWrapper() {
        return this.mGTWrapper;
    }

    public abstract void handlerTask(Looper looper);

    @Override // com.arialyy.aria.core.loader.ILoader
    public boolean isBreak() {
        if (!this.isCancel && !this.isStop) {
            return false;
        }
        ALog.d(this.TAG, String.format("任务【%s】已停止或取消了", this.mGTWrapper.getKey()));
        return true;
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public boolean isRunning() {
        GroupRunState groupRunState = this.mState;
        return groupRunState != null && groupRunState.isRunning.get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onPostStart() {
        if (isBreak()) {
            return;
        }
        getListener().onPostPre(((DownloadGroupEntity) getWrapper().getEntity()).getFileSize());
        if (((DownloadGroupEntity) getWrapper().getEntity()).getFileSize() > 0) {
            getListener().onResume(((DownloadGroupEntity) getWrapper().getEntity()).getCurrentProgress());
        } else {
            getListener().onStart(((DownloadGroupEntity) getWrapper().getEntity()).getCurrentProgress());
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        checkComponent();
        if (this.isStop || this.isCancel) {
            closeTimer();
        } else {
            startRunningFlow();
        }
    }

    public void startSubLoader(AbsSubDLoadUtil absSubDLoadUtil) {
        this.mExeLoader.put(absSubDLoadUtil.getKey(), absSubDLoadUtil);
        this.mSubQueue.startTask(absSubDLoadUtil);
    }

    public void startSubTask(String str) {
        if (checkSubTask(str, "开始")) {
            if (!this.mState.isRunning.get()) {
                startTimer();
            }
            AbsSubDLoadUtil downloader = getDownloader(str, false);
            if (downloader == null || downloader.isRunning()) {
                return;
            }
            this.mSubQueue.startTask(downloader);
        }
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public void stop() {
        this.isStop = true;
        this.mSubQueue.stopAllTask();
        closeTimer();
    }

    public void stopSubTask(String str) {
        AbsSubDLoadUtil downloader;
        if (checkSubTask(str, "停止") && (downloader = getDownloader(str, false)) != null && downloader.isRunning()) {
            this.mSubQueue.stopTask(downloader);
        }
    }
}
