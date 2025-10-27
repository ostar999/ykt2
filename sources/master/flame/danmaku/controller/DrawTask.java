package master.flame.danmaku.controller;

import master.flame.danmaku.controller.IDrawTask;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.renderer.android.DanmakuRenderer;
import master.flame.danmaku.danmaku.util.SystemClock;

/* loaded from: classes6.dex */
public class DrawTask implements IDrawTask {
    protected boolean clearRetainerFlag;
    protected IDanmakus danmakuList;
    protected final DanmakuContext mContext;
    protected final AbsDisplayer mDisp;
    private boolean mIsHidden;
    private long mLastBeginMills;
    private BaseDanmaku mLastDanmaku;
    private long mLastEndMills;
    protected BaseDanmakuParser mParser;
    protected int mPlayState;
    protected boolean mReadyState;
    final IRenderer mRenderer;
    private boolean mRequestRender;
    private IDanmakus mRunningDanmakus;
    IDrawTask.TaskListener mTaskListener;
    DanmakuTimer mTimer;
    private IDanmakus danmakus = new Danmakus(4);
    private long mStartRenderTime = 0;
    private final IRenderer.RenderingState mRenderingState = new IRenderer.RenderingState();
    private Danmakus mLiveDanmakus = new Danmakus(4);
    private DanmakuContext.ConfigChangedCallback mConfigChangedCallback = new DanmakuContext.ConfigChangedCallback() { // from class: master.flame.danmaku.controller.DrawTask.1
        @Override // master.flame.danmaku.danmaku.model.android.DanmakuContext.ConfigChangedCallback
        public boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuContext.DanmakuConfigTag danmakuConfigTag, Object... objArr) {
            return DrawTask.this.onDanmakuConfigChanged(danmakuContext, danmakuConfigTag, objArr);
        }
    };

    public DrawTask(DanmakuTimer danmakuTimer, DanmakuContext danmakuContext, IDrawTask.TaskListener taskListener) {
        if (danmakuContext == null) {
            throw new IllegalArgumentException("context is null");
        }
        this.mContext = danmakuContext;
        this.mDisp = danmakuContext.getDisplayer();
        this.mTaskListener = taskListener;
        DanmakuRenderer danmakuRenderer = new DanmakuRenderer(danmakuContext);
        this.mRenderer = danmakuRenderer;
        danmakuRenderer.setOnDanmakuShownListener(new IRenderer.OnDanmakuShownListener() { // from class: master.flame.danmaku.controller.DrawTask.2
            @Override // master.flame.danmaku.danmaku.renderer.IRenderer.OnDanmakuShownListener
            public void onDanmakuShown(BaseDanmaku baseDanmaku) {
                IDrawTask.TaskListener taskListener2 = DrawTask.this.mTaskListener;
                if (taskListener2 != null) {
                    taskListener2.onDanmakuShown(baseDanmaku);
                }
            }
        });
        danmakuRenderer.setVerifierEnabled(danmakuContext.isPreventOverlappingEnabled() || danmakuContext.isMaxLinesLimited());
        initTimer(danmakuTimer);
        Boolean boolValueOf = Boolean.valueOf(danmakuContext.isDuplicateMergingEnabled());
        if (boolValueOf != null) {
            if (boolValueOf.booleanValue()) {
                danmakuContext.mDanmakuFilters.registerFilter("1017_Filter");
            } else {
                danmakuContext.mDanmakuFilters.unregisterFilter("1017_Filter");
            }
        }
    }

    private void beginTracing(IRenderer.RenderingState renderingState, IDanmakus iDanmakus, IDanmakus iDanmakus2) {
        renderingState.reset();
        renderingState.timer.update(SystemClock.uptimeMillis());
        renderingState.indexInScreen = 0;
        renderingState.totalSizeInScreen = (iDanmakus != null ? iDanmakus.size() : 0) + (iDanmakus2 != null ? iDanmakus2.size() : 0);
    }

    private void endTracing(IRenderer.RenderingState renderingState) {
        boolean z2 = renderingState.totalDanmakuCount == 0;
        renderingState.nothingRendered = z2;
        if (z2) {
            renderingState.beginTime = -1L;
        }
        BaseDanmaku baseDanmaku = renderingState.lastDanmaku;
        renderingState.lastDanmaku = null;
        renderingState.endTime = baseDanmaku != null ? baseDanmaku.getActualTime() : -1L;
        renderingState.consumingTime = renderingState.timer.update(SystemClock.uptimeMillis());
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public synchronized void addDanmaku(BaseDanmaku baseDanmaku) {
        boolean zAddItem;
        boolean zAddItem2;
        IDrawTask.TaskListener taskListener;
        if (this.danmakuList == null) {
            return;
        }
        if (baseDanmaku.isLive) {
            this.mLiveDanmakus.addItem(baseDanmaku);
            removeUnusedLiveDanmakusIn(10);
        }
        baseDanmaku.index = this.danmakuList.size();
        if (this.mLastBeginMills > baseDanmaku.getActualTime() || baseDanmaku.getActualTime() > this.mLastEndMills) {
            zAddItem = !baseDanmaku.isLive;
        } else {
            synchronized (this.danmakus) {
                zAddItem = this.danmakus.addItem(baseDanmaku);
            }
        }
        synchronized (this.danmakuList) {
            zAddItem2 = this.danmakuList.addItem(baseDanmaku);
        }
        if (!zAddItem || !zAddItem2) {
            this.mLastEndMills = 0L;
            this.mLastBeginMills = 0L;
        }
        if (zAddItem2 && (taskListener = this.mTaskListener) != null) {
            taskListener.onDanmakuAdd(baseDanmaku);
        }
        BaseDanmaku baseDanmaku2 = this.mLastDanmaku;
        if (baseDanmaku2 == null || (baseDanmaku2 != null && baseDanmaku.getActualTime() > this.mLastDanmaku.getActualTime())) {
            this.mLastDanmaku = baseDanmaku;
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void clearDanmakusOnScreen(long j2) {
        reset();
        this.mContext.mGlobalFlagValues.updateVisibleFlag();
        this.mContext.mGlobalFlagValues.updateFirstShownFlag();
        this.mStartRenderTime = j2;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public synchronized IRenderer.RenderingState draw(AbsDisplayer absDisplayer) {
        return drawDanmakus(absDisplayer, this.mTimer);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState drawDanmakus(master.flame.danmaku.danmaku.model.AbsDisplayer r22, master.flame.danmaku.danmaku.model.DanmakuTimer r23) {
        /*
            r21 = this;
            r0 = r21
            boolean r1 = r0.clearRetainerFlag
            r2 = 0
            if (r1 == 0) goto Le
            master.flame.danmaku.danmaku.renderer.IRenderer r1 = r0.mRenderer
            r1.clearRetainer()
            r0.clearRetainerFlag = r2
        Le:
            master.flame.danmaku.danmaku.model.IDanmakus r1 = r0.danmakuList
            r3 = 0
            if (r1 == 0) goto Lc6
            java.lang.Object r1 = r22.getExtraData()
            android.graphics.Canvas r1 = (android.graphics.Canvas) r1
            master.flame.danmaku.controller.DrawHelper.clearCanvas(r1)
            boolean r1 = r0.mIsHidden
            if (r1 == 0) goto L27
            boolean r1 = r0.mRequestRender
            if (r1 != 0) goto L27
            master.flame.danmaku.danmaku.renderer.IRenderer$RenderingState r1 = r0.mRenderingState
            return r1
        L27:
            r0.mRequestRender = r2
            master.flame.danmaku.danmaku.renderer.IRenderer$RenderingState r1 = r0.mRenderingState
            r4 = r23
            long r4 = r4.currMillisecond
            master.flame.danmaku.danmaku.model.android.DanmakuContext r6 = r0.mContext
            master.flame.danmaku.danmaku.model.android.DanmakuFactory r6 = r6.mDanmakuFactory
            long r6 = r6.MAX_DANMAKU_DURATION
            long r8 = r4 - r6
            r10 = 100
            long r8 = r8 - r10
            long r6 = r6 + r4
            master.flame.danmaku.danmaku.model.IDanmakus r10 = r0.danmakus
            long r11 = r0.mLastBeginMills
            int r13 = (r11 > r8 ? 1 : (r11 == r8 ? 0 : -1))
            if (r13 > 0) goto L4c
            long r13 = r0.mLastEndMills
            int r4 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r4 <= 0) goto L4a
            goto L4c
        L4a:
            r6 = r10
            goto L5d
        L4c:
            master.flame.danmaku.danmaku.model.IDanmakus r4 = r0.danmakuList
            master.flame.danmaku.danmaku.model.IDanmakus r4 = r4.sub(r8, r6)
            if (r4 == 0) goto L56
            r0.danmakus = r4
        L56:
            r0.mLastBeginMills = r8
            r0.mLastEndMills = r6
            r13 = r6
            r11 = r8
            r6 = r4
        L5d:
            master.flame.danmaku.danmaku.model.IDanmakus r4 = r0.mRunningDanmakus
            r0.beginTracing(r1, r4, r6)
            r5 = 1
            if (r4 == 0) goto L7c
            boolean r7 = r4.isEmpty()
            if (r7 != 0) goto L7c
            master.flame.danmaku.danmaku.renderer.IRenderer$RenderingState r7 = r0.mRenderingState
            r7.isRunningDanmakus = r5
            master.flame.danmaku.danmaku.renderer.IRenderer r15 = r0.mRenderer
            r18 = 0
            r16 = r22
            r17 = r4
            r20 = r7
            r15.draw(r16, r17, r18, r20)
        L7c:
            master.flame.danmaku.danmaku.renderer.IRenderer$RenderingState r4 = r0.mRenderingState
            r4.isRunningDanmakus = r2
            if (r6 == 0) goto Lbf
            boolean r2 = r6.isEmpty()
            if (r2 != 0) goto Lbf
            master.flame.danmaku.danmaku.renderer.IRenderer r4 = r0.mRenderer
            master.flame.danmaku.danmaku.model.AbsDisplayer r5 = r0.mDisp
            long r7 = r0.mStartRenderTime
            r9 = r1
            r4.draw(r5, r6, r7, r9)
            r0.endTracing(r1)
            boolean r2 = r1.nothingRendered
            if (r2 == 0) goto Lbe
            master.flame.danmaku.danmaku.model.BaseDanmaku r2 = r0.mLastDanmaku
            if (r2 == 0) goto Lac
            boolean r2 = r2.isTimeOut()
            if (r2 == 0) goto Lac
            r0.mLastDanmaku = r3
            master.flame.danmaku.controller.IDrawTask$TaskListener r2 = r0.mTaskListener
            if (r2 == 0) goto Lac
            r2.onDanmakusDrawingFinished()
        Lac:
            long r2 = r1.beginTime
            r4 = -1
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto Lb6
            r1.beginTime = r11
        Lb6:
            long r2 = r1.endTime
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto Lbe
            r1.endTime = r13
        Lbe:
            return r1
        Lbf:
            r1.nothingRendered = r5
            r1.beginTime = r11
            r1.endTime = r13
            return r1
        Lc6:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: master.flame.danmaku.controller.DrawTask.drawDanmakus(master.flame.danmaku.danmaku.model.AbsDisplayer, master.flame.danmaku.danmaku.model.DanmakuTimer):master.flame.danmaku.danmaku.renderer.IRenderer$RenderingState");
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public IDanmakus getVisibleDanmakusOnTime(long j2) {
        IDanmakus iDanmakusSubnew;
        long j3 = this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION;
        long j4 = (j2 - j3) - 100;
        long j5 = j2 + j3;
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (i2 >= 3) {
                iDanmakusSubnew = null;
                break;
            }
            try {
                iDanmakusSubnew = this.danmakuList.subnew(j4, j5);
                break;
            } catch (Exception unused) {
                i2 = i3;
            }
        }
        final Danmakus danmakus = new Danmakus();
        if (iDanmakusSubnew != null && !iDanmakusSubnew.isEmpty()) {
            iDanmakusSubnew.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.DrawTask.5
                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (!baseDanmaku.isShown() || baseDanmaku.isOutside()) {
                        return 0;
                    }
                    danmakus.addItem(baseDanmaku);
                    return 0;
                }
            });
        }
        return danmakus;
    }

    public boolean handleOnDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuContext.DanmakuConfigTag danmakuConfigTag, Object[] objArr) {
        Boolean bool;
        if (danmakuConfigTag == null || DanmakuContext.DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN.equals(danmakuConfigTag)) {
            return true;
        }
        if (DanmakuContext.DanmakuConfigTag.DUPLICATE_MERGING_ENABLED.equals(danmakuConfigTag)) {
            Boolean bool2 = (Boolean) objArr[0];
            if (bool2 != null) {
                if (bool2.booleanValue()) {
                    this.mContext.mDanmakuFilters.registerFilter("1017_Filter");
                    return true;
                }
                this.mContext.mDanmakuFilters.unregisterFilter("1017_Filter");
                return true;
            }
        } else if (DanmakuContext.DanmakuConfigTag.SCALE_TEXTSIZE.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.SCROLL_SPEED_FACTOR.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.DANMAKU_MARGIN.equals(danmakuConfigTag)) {
            requestClearRetainer();
        } else {
            if (DanmakuContext.DanmakuConfigTag.MAXIMUN_LINES.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.OVERLAPPING_ENABLE.equals(danmakuConfigTag)) {
                IRenderer iRenderer = this.mRenderer;
                if (iRenderer == null) {
                    return true;
                }
                iRenderer.setVerifierEnabled(this.mContext.isPreventOverlappingEnabled() || this.mContext.isMaxLinesLimited());
                return true;
            }
            if (DanmakuContext.DanmakuConfigTag.ALIGN_BOTTOM.equals(danmakuConfigTag) && (bool = (Boolean) objArr[0]) != null) {
                IRenderer iRenderer2 = this.mRenderer;
                if (iRenderer2 == null) {
                    return true;
                }
                iRenderer2.alignBottom(bool.booleanValue());
                return true;
            }
        }
        return false;
    }

    public void initTimer(DanmakuTimer danmakuTimer) {
        this.mTimer = danmakuTimer;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void invalidateDanmaku(BaseDanmaku baseDanmaku, boolean z2) {
        this.mContext.getDisplayer().getCacheStuffer().clearCache(baseDanmaku);
        int i2 = baseDanmaku.requestFlags | 2;
        baseDanmaku.requestFlags = i2;
        if (z2) {
            baseDanmaku.paintWidth = -1.0f;
            baseDanmaku.paintHeight = -1.0f;
            baseDanmaku.requestFlags = i2 | 1;
            baseDanmaku.measureResetFlag++;
        }
    }

    public void loadDanmakus(BaseDanmakuParser baseDanmakuParser) {
        this.danmakuList = baseDanmakuParser.setConfig(this.mContext).setDisplayer(this.mDisp).setTimer(this.mTimer).setListener(new BaseDanmakuParser.Listener() { // from class: master.flame.danmaku.controller.DrawTask.6
            @Override // master.flame.danmaku.danmaku.parser.BaseDanmakuParser.Listener
            public void onDanmakuAdd(BaseDanmaku baseDanmaku) {
                IDrawTask.TaskListener taskListener = DrawTask.this.mTaskListener;
                if (taskListener != null) {
                    taskListener.onDanmakuAdd(baseDanmaku);
                }
            }
        }).getDanmakus();
        this.mContext.mGlobalFlagValues.resetAll();
        IDanmakus iDanmakus = this.danmakuList;
        if (iDanmakus != null) {
            this.mLastDanmaku = iDanmakus.last();
        }
    }

    public boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuContext.DanmakuConfigTag danmakuConfigTag, Object... objArr) {
        boolean zHandleOnDanmakuConfigChanged = handleOnDanmakuConfigChanged(danmakuContext, danmakuConfigTag, objArr);
        IDrawTask.TaskListener taskListener = this.mTaskListener;
        if (taskListener != null) {
            taskListener.onDanmakuConfigChanged();
        }
        return zHandleOnDanmakuConfigChanged;
    }

    public void onDanmakuRemoved(BaseDanmaku baseDanmaku) {
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void onPlayStateChanged(int i2) {
        this.mPlayState = i2;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void prepare() {
        BaseDanmakuParser baseDanmakuParser = this.mParser;
        if (baseDanmakuParser == null) {
            return;
        }
        loadDanmakus(baseDanmakuParser);
        this.mLastEndMills = 0L;
        this.mLastBeginMills = 0L;
        IDrawTask.TaskListener taskListener = this.mTaskListener;
        if (taskListener != null) {
            taskListener.ready();
            this.mReadyState = true;
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void quit() {
        this.mContext.unregisterAllConfigChangedCallbacks();
        IRenderer iRenderer = this.mRenderer;
        if (iRenderer != null) {
            iRenderer.release();
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public synchronized void removeAllDanmakus(boolean z2) {
        IDanmakus iDanmakus = this.danmakuList;
        if (iDanmakus != null && !iDanmakus.isEmpty()) {
            synchronized (this.danmakuList) {
                if (z2) {
                    this.danmakuList.clear();
                } else {
                    long j2 = this.mTimer.currMillisecond;
                    long j3 = this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION;
                    IDanmakus iDanmakusSubnew = this.danmakuList.subnew((j2 - j3) - 100, j2 + j3);
                    if (iDanmakusSubnew != null) {
                        this.danmakus = iDanmakusSubnew;
                    }
                    this.danmakuList.clear();
                }
            }
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public synchronized void removeAllLiveDanmakus() {
        IDanmakus iDanmakus = this.danmakus;
        if (iDanmakus != null && !iDanmakus.isEmpty()) {
            synchronized (this.danmakus) {
                this.danmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.DrawTask.3
                    @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        if (!baseDanmaku.isLive) {
                            return 0;
                        }
                        DrawTask.this.onDanmakuRemoved(baseDanmaku);
                        return 2;
                    }
                });
            }
        }
    }

    public synchronized void removeUnusedLiveDanmakusIn(final int i2) {
        IDanmakus iDanmakus = this.danmakuList;
        if (iDanmakus != null && !iDanmakus.isEmpty() && !this.mLiveDanmakus.isEmpty()) {
            this.mLiveDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.DrawTask.4
                long startTime = SystemClock.uptimeMillis();

                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    boolean zIsTimeOut = baseDanmaku.isTimeOut();
                    if (SystemClock.uptimeMillis() - this.startTime > i2 || !zIsTimeOut) {
                        return 1;
                    }
                    DrawTask.this.danmakuList.removeItem(baseDanmaku);
                    DrawTask.this.onDanmakuRemoved(baseDanmaku);
                    return 2;
                }
            });
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestClear() {
        this.mLastEndMills = 0L;
        this.mLastBeginMills = 0L;
        this.mIsHidden = false;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestClearRetainer() {
        this.clearRetainerFlag = true;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestHide() {
        this.mIsHidden = true;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestRender() {
        this.mRequestRender = true;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void requestSync(long j2, long j3, final long j4) {
        IDanmakus iDanmakusObtainRunningDanmakus = this.mRenderingState.obtainRunningDanmakus();
        this.mRunningDanmakus = iDanmakusObtainRunningDanmakus;
        iDanmakusObtainRunningDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.controller.DrawTask.7
            @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
            public int accept(BaseDanmaku baseDanmaku) {
                if (baseDanmaku.isOutside()) {
                    return 2;
                }
                baseDanmaku.setTimeOffset(j4 + baseDanmaku.timeOffset);
                return baseDanmaku.timeOffset == 0 ? 2 : 0;
            }
        });
        this.mStartRenderTime = j3;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void reset() {
        if (this.danmakus != null) {
            this.danmakus = new Danmakus();
        }
        IRenderer iRenderer = this.mRenderer;
        if (iRenderer != null) {
            iRenderer.clear();
        }
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void seek(long j2) {
        BaseDanmaku baseDanmakuLast;
        reset();
        this.mContext.mGlobalFlagValues.updateVisibleFlag();
        this.mContext.mGlobalFlagValues.updateFirstShownFlag();
        this.mContext.mGlobalFlagValues.updateSyncOffsetTimeFlag();
        this.mContext.mGlobalFlagValues.updatePrepareFlag();
        this.mRunningDanmakus = new Danmakus(4);
        if (j2 < 1000) {
            j2 = 0;
        }
        this.mStartRenderTime = j2;
        this.mRenderingState.reset();
        this.mRenderingState.endTime = this.mStartRenderTime;
        this.mLastEndMills = 0L;
        this.mLastBeginMills = 0L;
        IDanmakus iDanmakus = this.danmakuList;
        if (iDanmakus == null || (baseDanmakuLast = iDanmakus.last()) == null || baseDanmakuLast.isTimeOut()) {
            return;
        }
        this.mLastDanmaku = baseDanmakuLast;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void setParser(BaseDanmakuParser baseDanmakuParser) {
        this.mParser = baseDanmakuParser;
        this.mReadyState = false;
    }

    @Override // master.flame.danmaku.controller.IDrawTask
    public void start() {
        this.mContext.registerConfigChangedCallback(this.mConfigChangedCallback);
    }
}
