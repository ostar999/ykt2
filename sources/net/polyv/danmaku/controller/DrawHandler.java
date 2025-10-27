package net.polyv.danmaku.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import com.google.android.exoplayer2.ExoPlayer;
import java.util.LinkedList;
import net.polyv.danmaku.controller.IDrawTask;
import net.polyv.danmaku.danmaku.model.AbsDanmakuSync;
import net.polyv.danmaku.danmaku.model.AbsDisplayer;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.DanmakuTimer;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.IDisplayer;
import net.polyv.danmaku.danmaku.model.android.DanmakuContext;
import net.polyv.danmaku.danmaku.parser.BaseDanmakuParser;
import net.polyv.danmaku.danmaku.renderer.IRenderer;
import net.polyv.danmaku.danmaku.util.SystemClock;
import tv.polyv.jni.DeviceUtils;

/* loaded from: classes9.dex */
public class DrawHandler extends Handler {
    private static final int CLEAR_DANMAKUS_ON_SCREEN = 13;
    private static final int FORCE_RENDER = 14;
    private static final int HIDE_DANMAKUS = 9;
    private static final long INDEFINITE_TIME = 10000000;
    private static final int MAX_RECORD_SIZE = 500;
    private static final int NOTIFY_DISP_SIZE_CHANGED = 10;
    private static final int NOTIFY_RENDERING = 11;
    private static final int PAUSE = 7;
    public static final int PREPARE = 5;
    private static final int QUIT = 6;
    public static final int RESUME = 3;
    public static final int SEEK_POS = 4;
    private static final int SHOW_DANMAKUS = 8;
    public static final int START = 1;
    public static final int UPDATE = 2;
    private static final int UPDATE_WHEN_PAUSED = 12;
    public IDrawTask drawTask;
    private Callback mCallback;
    private DanmakuContext mContext;
    private long mCordonTime;
    private long mCordonTime2;
    private IDanmakuViewController mDanmakuView;
    private boolean mDanmakusVisible;
    private long mDesireSeekingTime;
    private AbsDisplayer mDisp;
    private LinkedList<Long> mDrawTimes;
    private FrameCallback mFrameCallback;
    private long mFrameUpdateRate;
    private boolean mIdleSleep;
    private boolean mInSeekingAction;
    private boolean mInSyncAction;
    private boolean mInWaitingState;
    private long mLastDeltaTime;
    private boolean mNonBlockModeEnable;
    private BaseDanmakuParser mParser;
    private boolean mReady;
    private long mRemainingTime;
    private final IRenderer.RenderingState mRenderingState;
    private UpdateThread mThread;
    private long mThresholdTime;
    private long mTimeBase;
    private boolean mUpdateInSeparateThread;
    private long pausedPosition;
    private boolean quitFlag;
    private DanmakuTimer timer;

    public interface Callback {
        void danmakuShown(BaseDanmaku baseDanmaku);

        void drawingFinished();

        void prepared();

        void updateTimer(DanmakuTimer danmakuTimer);
    }

    @TargetApi(16)
    public class FrameCallback implements Choreographer.FrameCallback {
        private FrameCallback() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j2) {
            DrawHandler.this.sendEmptyMessage(2);
        }
    }

    public DrawHandler(Looper looper, IDanmakuViewController iDanmakuViewController, boolean z2) {
        super(looper);
        this.pausedPosition = 0L;
        this.quitFlag = true;
        this.timer = new DanmakuTimer();
        this.mDanmakusVisible = true;
        this.mRenderingState = new IRenderer.RenderingState();
        this.mDrawTimes = new LinkedList<>();
        this.mCordonTime = 30L;
        this.mCordonTime2 = 60L;
        this.mFrameUpdateRate = 16L;
        this.mIdleSleep = true ^ DeviceUtils.isProblemBoxDevice();
        bindView(iDanmakuViewController);
        if (z2) {
            showDanmakus(null);
        } else {
            hideDanmakus(false);
        }
        this.mDanmakusVisible = z2;
    }

    private void bindView(IDanmakuViewController iDanmakuViewController) {
        this.mDanmakuView = iDanmakuViewController;
    }

    private IDrawTask createDrawTask(boolean z2, DanmakuTimer danmakuTimer, Context context, int i2, int i3, boolean z3, IDrawTask.TaskListener taskListener) {
        AbsDisplayer displayer = this.mContext.getDisplayer();
        this.mDisp = displayer;
        displayer.setSize(i2, i3);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDisp.setDensities(displayMetrics.density, displayMetrics.densityDpi, displayMetrics.scaledDensity);
        this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
        this.mDisp.setHardwareAccelerated(z3);
        IDrawTask cacheManagingDrawTask = z2 ? new CacheManagingDrawTask(danmakuTimer, this.mContext, taskListener) : new DrawTask(danmakuTimer, this.mContext, taskListener);
        cacheManagingDrawTask.setParser(this.mParser);
        cacheManagingDrawTask.prepare();
        obtainMessage(10, Boolean.FALSE).sendToTarget();
        return cacheManagingDrawTask;
    }

    private synchronized long getAverageRenderingTime() {
        int size = this.mDrawTimes.size();
        if (size <= 0) {
            return 0L;
        }
        Long lPeekFirst = this.mDrawTimes.peekFirst();
        Long lPeekLast = this.mDrawTimes.peekLast();
        if (lPeekFirst != null && lPeekLast != null) {
            return (lPeekLast.longValue() - lPeekFirst.longValue()) / size;
        }
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRenderingConfigs() {
        long jMax = Math.max(33L, (long) (16 * 2.5f));
        this.mCordonTime = jMax;
        this.mCordonTime2 = (long) (jMax * 2.5f);
        long frameUpdateRate = getConfig().getFrameUpdateRate();
        this.mFrameUpdateRate = frameUpdateRate;
        this.mThresholdTime = frameUpdateRate + 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRendering() {
        if (this.mInWaitingState) {
            IDrawTask iDrawTask = this.drawTask;
            if (iDrawTask != null) {
                iDrawTask.requestClear();
            }
            if (this.mUpdateInSeparateThread) {
                synchronized (this) {
                    this.mDrawTimes.clear();
                }
                synchronized (this.drawTask) {
                    this.drawTask.notifyAll();
                }
            } else {
                this.mDrawTimes.clear();
                removeMessages(2);
                sendEmptyMessage(2);
            }
            this.mInWaitingState = false;
        }
    }

    private void prepare(final Runnable runnable) {
        if (this.drawTask == null) {
            this.drawTask = createDrawTask(this.mDanmakuView.isDanmakuDrawingCacheEnabled(), this.timer, this.mDanmakuView.getContext(), this.mDanmakuView.getViewWidth(), this.mDanmakuView.getViewHeight(), this.mDanmakuView.isHardwareAccelerated(), new IDrawTask.TaskListener() { // from class: net.polyv.danmaku.controller.DrawHandler.3
                @Override // net.polyv.danmaku.controller.IDrawTask.TaskListener
                public void onDanmakuAdd(BaseDanmaku baseDanmaku) {
                    if (baseDanmaku.isTimeOut()) {
                        return;
                    }
                    long actualTime = baseDanmaku.getActualTime() - DrawHandler.this.getCurrentTime();
                    if (actualTime < DrawHandler.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION && (DrawHandler.this.mInWaitingState || DrawHandler.this.mRenderingState.nothingRendered)) {
                        DrawHandler.this.notifyRendering();
                    } else {
                        if (actualTime <= 0 || actualTime > DrawHandler.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) {
                            return;
                        }
                        DrawHandler.this.sendEmptyMessageDelayed(11, actualTime);
                    }
                }

                @Override // net.polyv.danmaku.controller.IDrawTask.TaskListener
                public void onDanmakuConfigChanged() {
                    DrawHandler.this.redrawIfNeeded();
                }

                @Override // net.polyv.danmaku.controller.IDrawTask.TaskListener
                public void onDanmakuShown(BaseDanmaku baseDanmaku) {
                    if (DrawHandler.this.mCallback != null) {
                        DrawHandler.this.mCallback.danmakuShown(baseDanmaku);
                    }
                }

                @Override // net.polyv.danmaku.controller.IDrawTask.TaskListener
                public void onDanmakusDrawingFinished() {
                    if (DrawHandler.this.mCallback != null) {
                        DrawHandler.this.mCallback.drawingFinished();
                    }
                }

                @Override // net.polyv.danmaku.controller.IDrawTask.TaskListener
                public void ready() {
                    DrawHandler.this.initRenderingConfigs();
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    private synchronized void quitUpdateThread() {
        UpdateThread updateThread = this.mThread;
        this.mThread = null;
        if (updateThread != null) {
            synchronized (this.drawTask) {
                this.drawTask.notifyAll();
            }
            updateThread.quit();
            try {
                updateThread.join(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    private synchronized void recordRenderingTime() {
        this.mDrawTimes.addLast(Long.valueOf(SystemClock.uptimeMillis()));
        if (this.mDrawTimes.size() > 500) {
            this.mDrawTimes.removeFirst();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redrawIfNeeded() {
        if (this.quitFlag && this.mDanmakusVisible) {
            removeMessages(12);
            sendEmptyMessageDelayed(12, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long syncTimer(long j2) {
        long jLastInterval = 0;
        if (!this.mInSeekingAction && !this.mInSyncAction) {
            this.mInSyncAction = true;
            long j3 = j2 - this.mTimeBase;
            if (this.mNonBlockModeEnable) {
                Callback callback = this.mCallback;
                if (callback != null) {
                    callback.updateTimer(this.timer);
                    jLastInterval = this.timer.lastInterval();
                }
            } else if (!this.mDanmakusVisible || this.mRenderingState.nothingRendered || this.mInWaitingState) {
                this.timer.update(j3);
                this.mRemainingTime = 0L;
                Callback callback2 = this.mCallback;
                if (callback2 != null) {
                    callback2.updateTimer(this.timer);
                }
            } else {
                long j4 = j3 - this.timer.currMillisecond;
                long jMax = Math.max(this.mFrameUpdateRate, getAverageRenderingTime());
                if (j4 <= ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                    long j5 = this.mRenderingState.consumingTime;
                    long j6 = this.mCordonTime;
                    if (j5 <= j6 && jMax <= j6) {
                        long j7 = this.mFrameUpdateRate;
                        long jMin = Math.min(this.mCordonTime, Math.max(j7, jMax + (j4 / j7)));
                        long j8 = this.mLastDeltaTime;
                        long j9 = jMin - j8;
                        if (j9 > 3 && j9 < 8 && j8 >= this.mFrameUpdateRate && j8 <= this.mCordonTime) {
                            jMin = j8;
                        }
                        long j10 = j4 - jMin;
                        this.mLastDeltaTime = jMin;
                        j4 = jMin;
                        jLastInterval = j10;
                    }
                }
                this.mRemainingTime = jLastInterval;
                this.timer.add(j4);
                Callback callback3 = this.mCallback;
                if (callback3 != null) {
                    callback3.updateTimer(this.timer);
                }
                jLastInterval = j4;
            }
            this.mInSyncAction = false;
        }
        return jLastInterval;
    }

    private void syncTimerIfNeeded() {
        if (this.mInWaitingState) {
            syncTimer(SystemClock.uptimeMillis());
        }
    }

    @TargetApi(16)
    private void updateInChoreographer() {
        if (this.quitFlag) {
            return;
        }
        Choreographer.getInstance().postFrameCallback(this.mFrameCallback);
        if (syncTimer(SystemClock.uptimeMillis()) < 0) {
            removeMessages(2);
            return;
        }
        long jDrawDanmakus = this.mDanmakuView.drawDanmakus();
        removeMessages(2);
        if (jDrawDanmakus > this.mCordonTime2) {
            this.timer.add(jDrawDanmakus);
            this.mDrawTimes.clear();
        }
        if (!this.mDanmakusVisible) {
            waitRendering(INDEFINITE_TIME);
            return;
        }
        IRenderer.RenderingState renderingState = this.mRenderingState;
        if (renderingState.nothingRendered && this.mIdleSleep) {
            long j2 = renderingState.endTime - this.timer.currMillisecond;
            if (j2 > 500) {
                waitRendering(j2 - 10);
            }
        }
    }

    private void updateInCurrentThread() {
        if (this.quitFlag) {
            return;
        }
        long jSyncTimer = syncTimer(SystemClock.uptimeMillis());
        if (jSyncTimer < 0 && !this.mNonBlockModeEnable) {
            removeMessages(2);
            sendEmptyMessageDelayed(2, 60 - jSyncTimer);
            return;
        }
        long jDrawDanmakus = this.mDanmakuView.drawDanmakus();
        removeMessages(2);
        if (jDrawDanmakus > this.mCordonTime2) {
            this.timer.add(jDrawDanmakus);
            this.mDrawTimes.clear();
        }
        if (!this.mDanmakusVisible) {
            waitRendering(INDEFINITE_TIME);
            return;
        }
        IRenderer.RenderingState renderingState = this.mRenderingState;
        if (renderingState.nothingRendered && this.mIdleSleep) {
            long j2 = renderingState.endTime - this.timer.currMillisecond;
            if (j2 > 500) {
                waitRendering(j2 - 10);
                return;
            }
        }
        long j3 = this.mFrameUpdateRate;
        if (jDrawDanmakus < j3) {
            sendEmptyMessageDelayed(2, j3 - jDrawDanmakus);
        } else {
            sendEmptyMessage(2);
        }
    }

    private void updateInNewThread() {
        if (this.mThread != null) {
            return;
        }
        UpdateThread updateThread = new UpdateThread("DFM Update") { // from class: net.polyv.danmaku.controller.DrawHandler.2
            @Override // net.polyv.danmaku.controller.UpdateThread, java.lang.Thread, java.lang.Runnable
            public void run() {
                long jUptimeMillis = SystemClock.uptimeMillis();
                while (!isQuited() && !DrawHandler.this.quitFlag) {
                    long jUptimeMillis2 = SystemClock.uptimeMillis();
                    if (DrawHandler.this.mFrameUpdateRate - (SystemClock.uptimeMillis() - jUptimeMillis) <= 1 || DrawHandler.this.mNonBlockModeEnable) {
                        long jSyncTimer = DrawHandler.this.syncTimer(jUptimeMillis2);
                        if (jSyncTimer >= 0 || DrawHandler.this.mNonBlockModeEnable) {
                            long jDrawDanmakus = DrawHandler.this.mDanmakuView.drawDanmakus();
                            if (jDrawDanmakus > DrawHandler.this.mCordonTime2) {
                                DrawHandler.this.timer.add(jDrawDanmakus);
                                DrawHandler.this.mDrawTimes.clear();
                            }
                            if (!DrawHandler.this.mDanmakusVisible) {
                                DrawHandler.this.waitRendering(DrawHandler.INDEFINITE_TIME);
                            } else if (DrawHandler.this.mRenderingState.nothingRendered && DrawHandler.this.mIdleSleep) {
                                long j2 = DrawHandler.this.mRenderingState.endTime - DrawHandler.this.timer.currMillisecond;
                                if (j2 > 500) {
                                    DrawHandler.this.notifyRendering();
                                    DrawHandler.this.waitRendering(j2 - 10);
                                }
                            }
                        } else {
                            SystemClock.sleep(60 - jSyncTimer);
                        }
                        jUptimeMillis = jUptimeMillis2;
                    } else {
                        SystemClock.sleep(1L);
                    }
                }
            }
        };
        this.mThread = updateThread;
        updateThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitRendering(long j2) {
        if (isStop() || !isPrepared() || this.mInSeekingAction) {
            return;
        }
        this.mRenderingState.sysTime = SystemClock.uptimeMillis();
        this.mInWaitingState = true;
        if (!this.mUpdateInSeparateThread) {
            if (j2 == INDEFINITE_TIME) {
                removeMessages(11);
                removeMessages(2);
                return;
            } else {
                removeMessages(11);
                removeMessages(2);
                sendEmptyMessageDelayed(11, j2);
                return;
            }
        }
        if (this.mThread == null) {
            return;
        }
        try {
            synchronized (this.drawTask) {
                if (j2 == INDEFINITE_TIME) {
                    this.drawTask.wait();
                } else {
                    this.drawTask.wait(j2);
                }
                sendEmptyMessage(11);
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public void addDanmaku(BaseDanmaku baseDanmaku) {
        if (this.drawTask != null) {
            baseDanmaku.flags = this.mContext.mGlobalFlagValues;
            baseDanmaku.setTimer(this.timer);
            this.drawTask.addDanmaku(baseDanmaku);
            obtainMessage(11).sendToTarget();
        }
    }

    public void clearDanmakusOnScreen() {
        obtainMessage(13).sendToTarget();
    }

    public IRenderer.RenderingState draw(Canvas canvas) {
        AbsDanmakuSync absDanmakuSync;
        boolean zIsSyncPlayingState;
        if (this.drawTask == null) {
            return this.mRenderingState;
        }
        if (!this.mInWaitingState && (absDanmakuSync = this.mContext.danmakuSync) != null && ((zIsSyncPlayingState = absDanmakuSync.isSyncPlayingState()) || !this.quitFlag)) {
            int syncState = absDanmakuSync.getSyncState();
            if (syncState == 2) {
                long j2 = this.timer.currMillisecond;
                long uptimeMillis = absDanmakuSync.getUptimeMillis();
                long j3 = uptimeMillis - j2;
                if (Math.abs(j3) > absDanmakuSync.getThresholdTimeMills()) {
                    if (zIsSyncPlayingState && this.quitFlag) {
                        resume();
                    }
                    this.drawTask.requestSync(j2, uptimeMillis, j3);
                    this.timer.update(uptimeMillis);
                    this.mTimeBase -= j3;
                    this.mRemainingTime = 0L;
                }
            } else if (syncState == 1 && zIsSyncPlayingState && !this.quitFlag) {
                pause();
            }
        }
        this.mDisp.setExtraData(canvas);
        this.mRenderingState.set(this.drawTask.draw(this.mDisp));
        recordRenderingTime();
        return this.mRenderingState;
    }

    public void enableNonBlockMode(boolean z2) {
        this.mNonBlockModeEnable = z2;
    }

    public void forceRender() {
        removeMessages(14);
        obtainMessage(14).sendToTarget();
    }

    public DanmakuContext getConfig() {
        return this.mContext;
    }

    public long getCurrentTime() {
        long jUptimeMillis;
        long j2;
        if (!this.mReady) {
            return 0L;
        }
        if (this.mInSeekingAction) {
            return this.mDesireSeekingTime;
        }
        if (this.quitFlag || !this.mInWaitingState) {
            jUptimeMillis = this.timer.currMillisecond;
            j2 = this.mRemainingTime;
        } else {
            jUptimeMillis = SystemClock.uptimeMillis();
            j2 = this.mTimeBase;
        }
        return jUptimeMillis - j2;
    }

    public IDanmakus getCurrentVisibleDanmakus() {
        IDrawTask iDrawTask = this.drawTask;
        if (iDrawTask != null) {
            return iDrawTask.getVisibleDanmakusOnTime(getCurrentTime());
        }
        return null;
    }

    public IDisplayer getDisplayer() {
        return this.mDisp;
    }

    public boolean getVisibility() {
        return this.mDanmakusVisible;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01bf  */
    @Override // android.os.Handler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleMessage(android.os.Message r12) {
        /*
            Method dump skipped, instructions count: 532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.polyv.danmaku.controller.DrawHandler.handleMessage(android.os.Message):void");
    }

    public long hideDanmakus(boolean z2) {
        if (!this.mDanmakusVisible) {
            return this.timer.currMillisecond;
        }
        this.mDanmakusVisible = false;
        removeMessages(8);
        removeMessages(9);
        obtainMessage(9, Boolean.valueOf(z2)).sendToTarget();
        return this.timer.currMillisecond;
    }

    public void invalidateDanmaku(BaseDanmaku baseDanmaku, boolean z2) {
        IDrawTask iDrawTask = this.drawTask;
        if (iDrawTask != null && baseDanmaku != null) {
            iDrawTask.invalidateDanmaku(baseDanmaku, z2);
        }
        redrawIfNeeded();
    }

    public boolean isPrepared() {
        return this.mReady;
    }

    public boolean isStop() {
        return this.quitFlag;
    }

    public void notifyDispSizeChanged(int i2, int i3) {
        AbsDisplayer absDisplayer = this.mDisp;
        if (absDisplayer == null) {
            return;
        }
        if (absDisplayer.getWidth() == i2 && this.mDisp.getHeight() == i3) {
            return;
        }
        this.mDisp.setSize(i2, i3);
        obtainMessage(10, Boolean.TRUE).sendToTarget();
    }

    public void pause() {
        removeMessages(3);
        syncTimerIfNeeded();
        sendEmptyMessage(7);
    }

    public void quit() {
        this.quitFlag = true;
        sendEmptyMessage(6);
    }

    public void removeAllDanmakus(boolean z2) {
        IDrawTask iDrawTask = this.drawTask;
        if (iDrawTask != null) {
            iDrawTask.removeAllDanmakus(z2);
        }
    }

    public void removeAllLiveDanmakus() {
        IDrawTask iDrawTask = this.drawTask;
        if (iDrawTask != null) {
            iDrawTask.removeAllLiveDanmakus();
        }
    }

    public void resume() {
        removeMessages(7);
        sendEmptyMessage(3);
    }

    public void seekTo(Long l2) {
        this.mInSeekingAction = true;
        this.mDesireSeekingTime = l2.longValue();
        removeMessages(2);
        removeMessages(3);
        removeMessages(4);
        obtainMessage(4, l2).sendToTarget();
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setConfig(DanmakuContext danmakuContext) {
        this.mContext = danmakuContext;
    }

    public void setIdleSleep(boolean z2) {
        this.mIdleSleep = z2;
    }

    public void setParser(BaseDanmakuParser baseDanmakuParser) {
        this.mParser = baseDanmakuParser;
        DanmakuTimer timer = baseDanmakuParser.getTimer();
        if (timer != null) {
            this.timer = timer;
        }
    }

    public void showDanmakus(Long l2) {
        if (this.mDanmakusVisible) {
            return;
        }
        this.mDanmakusVisible = true;
        removeMessages(8);
        removeMessages(9);
        obtainMessage(8, l2).sendToTarget();
    }

    public void prepare() {
        this.mReady = false;
        if (this.mContext.updateMethod == 0) {
            this.mFrameCallback = new FrameCallback();
        }
        this.mUpdateInSeparateThread = this.mContext.updateMethod == 1;
        sendEmptyMessage(5);
    }
}
