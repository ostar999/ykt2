package net.polyv.danmaku.controller;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.yikaobang.yixue.R2;
import net.polyv.danmaku.controller.IDrawTask;
import net.polyv.danmaku.danmaku.model.AbsDisplayer;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.DanmakuTimer;
import net.polyv.danmaku.danmaku.model.ICacheManager;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.IDrawingCache;
import net.polyv.danmaku.danmaku.model.android.DanmakuContext;
import net.polyv.danmaku.danmaku.model.android.DanmakuFactory;
import net.polyv.danmaku.danmaku.model.android.Danmakus;
import net.polyv.danmaku.danmaku.model.android.DrawingCache;
import net.polyv.danmaku.danmaku.model.android.DrawingCachePoolManager;
import net.polyv.danmaku.danmaku.model.objectpool.Pool;
import net.polyv.danmaku.danmaku.model.objectpool.Pools;
import net.polyv.danmaku.danmaku.parser.BaseDanmakuParser;
import net.polyv.danmaku.danmaku.renderer.IRenderer;
import net.polyv.danmaku.danmaku.util.DanmakuUtils;
import net.polyv.danmaku.danmaku.util.SystemClock;
import tv.polyv.jni.NativeBitmapFactory;

/* loaded from: classes9.dex */
public class CacheManagingDrawTask extends DrawTask {
    private static final int MAX_CACHE_SCREEN_SIZE = 3;
    private CacheManager mCacheManager;
    private DanmakuTimer mCacheTimer;
    private final Object mDrawingNotify;
    private int mMaxCacheSize;
    private int mRemaininCacheCount;

    public class CacheManager implements ICacheManager {
        public static final byte RESULT_FAILED = 1;
        public static final byte RESULT_FAILED_OVERSIZE = 2;
        public static final byte RESULT_SUCCESS = 0;
        private static final String TAG = "CacheManager";
        Pool<DrawingCache> mCachePool;
        DrawingCachePoolManager mCachePoolManager;
        Danmakus mCaches = new Danmakus();
        private boolean mEndFlag;
        private CacheHandler mHandler;
        private int mMaxSize;
        private int mRealSize;
        private int mScreenSize;
        public HandlerThread mThread;

        public class CacheHandler extends Handler {
            public static final int ADD_DANMAKU = 2;
            public static final int BUILD_CACHES = 3;
            public static final int CLEAR_ALL_CACHES = 7;
            public static final int CLEAR_OUTSIDE_CACHES = 8;
            public static final int CLEAR_OUTSIDE_CACHES_AND_RESET = 9;
            public static final int CLEAR_TIMEOUT_CACHES = 4;
            public static final int DISABLE_CANCEL_FLAG = 18;
            public static final int DISPATCH_ACTIONS = 16;
            private static final int PREPARE = 1;
            public static final int QUIT = 6;
            public static final int REBUILD_CACHE = 17;
            public static final int SEEK = 5;
            private boolean mCancelFlag;
            private boolean mIsPlayerPause;
            private boolean mPause;
            private boolean mSeekedFlag;

            public CacheHandler(Looper looper) {
                super(looper);
            }

            private final void addDanmakuAndBuildCache(BaseDanmaku baseDanmaku) {
                if (baseDanmaku.isTimeOut()) {
                    return;
                }
                if (baseDanmaku.getActualTime() <= CacheManagingDrawTask.this.mCacheTimer.currMillisecond + CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION || baseDanmaku.isLive) {
                    if (baseDanmaku.priority == 0 && baseDanmaku.isFiltered()) {
                        return;
                    }
                    IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
                    if (drawingCache == null || drawingCache.get() == null) {
                        buildCache(baseDanmaku, true);
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public byte buildCache(BaseDanmaku baseDanmaku, boolean z2) {
                if (!baseDanmaku.isMeasured()) {
                    baseDanmaku.measure(CacheManagingDrawTask.this.mDisp, true);
                }
                DrawingCache drawingCache = null;
                try {
                    CacheManager cacheManager = CacheManager.this;
                    BaseDanmaku baseDanmakuFindReusableCache = cacheManager.findReusableCache(baseDanmaku, true, CacheManagingDrawTask.this.mContext.cachingPolicy.maxTimesOfStrictReusableFinds);
                    DrawingCache drawingCache2 = baseDanmakuFindReusableCache != null ? (DrawingCache) baseDanmakuFindReusableCache.cache : null;
                    try {
                        if (drawingCache2 != null) {
                            drawingCache2.increaseReference();
                            baseDanmaku.cache = drawingCache2;
                            CacheManagingDrawTask.this.mCacheManager.push(baseDanmaku, 0, z2);
                            return (byte) 0;
                        }
                        CacheManager cacheManager2 = CacheManager.this;
                        BaseDanmaku baseDanmakuFindReusableCache2 = cacheManager2.findReusableCache(baseDanmaku, false, CacheManagingDrawTask.this.mContext.cachingPolicy.maxTimesOfReusableFinds);
                        if (baseDanmakuFindReusableCache2 != null) {
                            drawingCache2 = (DrawingCache) baseDanmakuFindReusableCache2.cache;
                        }
                        if (drawingCache2 != null) {
                            baseDanmakuFindReusableCache2.cache = null;
                            CacheManagingDrawTask cacheManagingDrawTask = CacheManagingDrawTask.this;
                            baseDanmaku.cache = DanmakuUtils.buildDanmakuDrawingCache(baseDanmaku, cacheManagingDrawTask.mDisp, drawingCache2, cacheManagingDrawTask.mContext.cachingPolicy.bitsPerPixelOfCache);
                            CacheManagingDrawTask.this.mCacheManager.push(baseDanmaku, 0, z2);
                            return (byte) 0;
                        }
                        int cacheSize = DanmakuUtils.getCacheSize((int) baseDanmaku.paintWidth, (int) baseDanmaku.paintHeight, CacheManagingDrawTask.this.mContext.cachingPolicy.bitsPerPixelOfCache / 8);
                        if (cacheSize * 2 > CacheManagingDrawTask.this.mMaxCacheSize) {
                            return (byte) 1;
                        }
                        if (!z2 && CacheManager.this.mRealSize + cacheSize > CacheManager.this.mMaxSize) {
                            CacheManagingDrawTask.this.mCacheManager.clearTimeOutAndFilteredCaches(cacheSize, false);
                            return (byte) 1;
                        }
                        DrawingCache drawingCache3 = (DrawingCache) CacheManager.this.mCachePool.acquire();
                        CacheManagingDrawTask cacheManagingDrawTask2 = CacheManagingDrawTask.this;
                        DrawingCache drawingCacheBuildDanmakuDrawingCache = DanmakuUtils.buildDanmakuDrawingCache(baseDanmaku, cacheManagingDrawTask2.mDisp, drawingCache3, cacheManagingDrawTask2.mContext.cachingPolicy.bitsPerPixelOfCache);
                        baseDanmaku.cache = drawingCacheBuildDanmakuDrawingCache;
                        boolean zPush = CacheManagingDrawTask.this.mCacheManager.push(baseDanmaku, CacheManager.this.sizeOf(baseDanmaku), z2);
                        if (!zPush) {
                            releaseDanmakuCache(baseDanmaku, drawingCacheBuildDanmakuDrawingCache);
                        }
                        return !zPush ? (byte) 1 : (byte) 0;
                    } catch (Exception unused) {
                        drawingCache = drawingCache2;
                        releaseDanmakuCache(baseDanmaku, drawingCache);
                        return (byte) 1;
                    } catch (OutOfMemoryError unused2) {
                        drawingCache = drawingCache2;
                        releaseDanmakuCache(baseDanmaku, drawingCache);
                        return (byte) 1;
                    }
                } catch (Exception unused3) {
                } catch (OutOfMemoryError unused4) {
                }
            }

            private long dispatchAction() {
                long j2 = CacheManagingDrawTask.this.mCacheTimer.currMillisecond;
                CacheManager cacheManager = CacheManager.this;
                CacheManagingDrawTask cacheManagingDrawTask = CacheManagingDrawTask.this;
                long j3 = cacheManagingDrawTask.mTimer.currMillisecond;
                DanmakuContext danmakuContext = cacheManagingDrawTask.mContext;
                if (j2 <= j3 - danmakuContext.mDanmakuFactory.MAX_DANMAKU_DURATION) {
                    if (danmakuContext.cachingPolicy.periodOfRecycle != -1) {
                        cacheManager.evictAllNotInScreen();
                    }
                    CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                    sendEmptyMessage(3);
                    return 0L;
                }
                float poolPercent = cacheManager.getPoolPercent();
                BaseDanmaku baseDanmakuFirst = CacheManager.this.mCaches.first();
                long actualTime = baseDanmakuFirst != null ? baseDanmakuFirst.getActualTime() - CacheManagingDrawTask.this.mTimer.currMillisecond : 0L;
                CacheManagingDrawTask cacheManagingDrawTask2 = CacheManagingDrawTask.this;
                long j4 = cacheManagingDrawTask2.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION;
                long j5 = 2 * j4;
                if (poolPercent < 0.6f && actualTime > j4) {
                    cacheManagingDrawTask2.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                    removeMessages(3);
                    sendEmptyMessage(3);
                    return 0L;
                }
                if (poolPercent > 0.4f && actualTime < (-j5)) {
                    removeMessages(4);
                    sendEmptyMessage(4);
                    return 0L;
                }
                if (poolPercent >= 0.9f) {
                    return 0L;
                }
                long j6 = cacheManagingDrawTask2.mCacheTimer.currMillisecond - CacheManagingDrawTask.this.mTimer.currMillisecond;
                if (baseDanmakuFirst != null && baseDanmakuFirst.isTimeOut()) {
                    CacheManagingDrawTask cacheManagingDrawTask3 = CacheManagingDrawTask.this;
                    if (j6 < (-cacheManagingDrawTask3.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION)) {
                        cacheManagingDrawTask3.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                        sendEmptyMessage(8);
                        sendEmptyMessage(3);
                        return 0L;
                    }
                }
                if (j6 > j5) {
                    return 0L;
                }
                removeMessages(3);
                sendEmptyMessage(3);
                return 0L;
            }

            private void preMeasure() {
                IDanmakus iDanmakusSubnew;
                try {
                    CacheManagingDrawTask cacheManagingDrawTask = CacheManagingDrawTask.this;
                    long j2 = cacheManagingDrawTask.mTimer.currMillisecond;
                    long j3 = cacheManagingDrawTask.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION;
                    iDanmakusSubnew = cacheManagingDrawTask.danmakuList.subnew(j2 - j3, (2 * j3) + j2);
                } catch (Exception unused) {
                    iDanmakusSubnew = null;
                }
                if (iDanmakusSubnew == null || iDanmakusSubnew.isEmpty()) {
                    return;
                }
                iDanmakusSubnew.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: net.polyv.danmaku.controller.CacheManagingDrawTask.CacheManager.CacheHandler.1
                    @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        if (CacheHandler.this.mPause || CacheHandler.this.mCancelFlag) {
                            return 1;
                        }
                        if (!baseDanmaku.hasPassedFilter()) {
                            DanmakuContext danmakuContext = CacheManagingDrawTask.this.mContext;
                            danmakuContext.mDanmakuFilters.filter(baseDanmaku, 0, 0, null, true, danmakuContext);
                        }
                        if (baseDanmaku.isFiltered()) {
                            return 0;
                        }
                        if (!baseDanmaku.isMeasured()) {
                            baseDanmaku.measure(CacheManagingDrawTask.this.mDisp, true);
                        }
                        if (!baseDanmaku.isPrepared()) {
                            baseDanmaku.prepare(CacheManagingDrawTask.this.mDisp, true);
                        }
                        return 0;
                    }
                });
            }

            private long prepareCaches(final boolean z2) {
                preMeasure();
                final long j2 = CacheManagingDrawTask.this.mCacheTimer.currMillisecond - 30;
                long j3 = j2 + (CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION * r0.mScreenSize);
                if (j3 < CacheManagingDrawTask.this.mTimer.currMillisecond) {
                    return 0L;
                }
                final long jUptimeMillis = SystemClock.uptimeMillis();
                int i2 = 0;
                IDanmakus iDanmakusSubnew = null;
                boolean z3 = false;
                do {
                    try {
                        iDanmakusSubnew = CacheManagingDrawTask.this.danmakuList.subnew(j2, j3);
                    } catch (Exception unused) {
                        SystemClock.sleep(10L);
                        z3 = true;
                    }
                    i2++;
                    if (i2 >= 3 || iDanmakusSubnew != null) {
                        break;
                    }
                } while (z3);
                if (iDanmakusSubnew == null) {
                    CacheManagingDrawTask.this.mCacheTimer.update(j3);
                    return 0L;
                }
                BaseDanmaku baseDanmakuFirst = iDanmakusSubnew.first();
                final BaseDanmaku baseDanmakuLast = iDanmakusSubnew.last();
                if (baseDanmakuFirst == null || baseDanmakuLast == null) {
                    CacheManagingDrawTask.this.mCacheTimer.update(j3);
                    return 0L;
                }
                long actualTime = baseDanmakuFirst.getActualTime();
                CacheManagingDrawTask cacheManagingDrawTask = CacheManagingDrawTask.this;
                long j4 = actualTime - cacheManagingDrawTask.mTimer.currMillisecond;
                final long jMin = z2 ? 0L : Math.min(100L, j4 < 0 ? 30L : ((j4 * 10) / cacheManagingDrawTask.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) + 30);
                final int size = iDanmakusSubnew.size();
                iDanmakusSubnew.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: net.polyv.danmaku.controller.CacheManagingDrawTask.CacheManager.CacheHandler.2
                    int orderInScreen = 0;
                    int currScreenIndex = 0;

                    @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        if (CacheHandler.this.mPause || CacheHandler.this.mCancelFlag || baseDanmakuLast.getActualTime() < CacheManagingDrawTask.this.mTimer.currMillisecond) {
                            return 1;
                        }
                        IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
                        if (drawingCache != null && drawingCache.get() != null) {
                            return 0;
                        }
                        if (!z2 && (baseDanmaku.isTimeOut() || !baseDanmaku.isOutside())) {
                            return 0;
                        }
                        if (!baseDanmaku.hasPassedFilter()) {
                            DanmakuContext danmakuContext = CacheManagingDrawTask.this.mContext;
                            danmakuContext.mDanmakuFilters.filter(baseDanmaku, this.orderInScreen, size, null, true, danmakuContext);
                        }
                        if (baseDanmaku.priority == 0 && baseDanmaku.isFiltered()) {
                            return 0;
                        }
                        if (baseDanmaku.getType() == 1) {
                            int actualTime2 = (int) ((baseDanmaku.getActualTime() - j2) / CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
                            if (this.currScreenIndex == actualTime2) {
                                this.orderInScreen++;
                            } else {
                                this.orderInScreen = 0;
                                this.currScreenIndex = actualTime2;
                            }
                        }
                        if (!z2 && !CacheHandler.this.mIsPlayerPause) {
                            try {
                                synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                                    CacheManagingDrawTask.this.mDrawingNotify.wait(jMin);
                                }
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                                return 1;
                            }
                        }
                        CacheHandler.this.buildCache(baseDanmaku, false);
                        if (!z2) {
                            long jUptimeMillis2 = SystemClock.uptimeMillis() - jUptimeMillis;
                            DanmakuFactory danmakuFactory = CacheManagingDrawTask.this.mContext.mDanmakuFactory;
                            if (jUptimeMillis2 >= r11.mScreenSize * 3800) {
                                return 1;
                            }
                        }
                        return 0;
                    }
                });
                long jUptimeMillis2 = SystemClock.uptimeMillis() - jUptimeMillis;
                CacheManagingDrawTask.this.mCacheTimer.update(j3);
                return jUptimeMillis2;
            }

            private void releaseDanmakuCache(BaseDanmaku baseDanmaku, DrawingCache drawingCache) {
                if (drawingCache == null) {
                    drawingCache = (DrawingCache) baseDanmaku.cache;
                }
                baseDanmaku.cache = null;
                if (drawingCache == null) {
                    return;
                }
                drawingCache.destroy();
                CacheManager.this.mCachePool.release(drawingCache);
            }

            public void begin() {
                sendEmptyMessage(1);
                sendEmptyMessageDelayed(4, CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
            }

            public boolean createCache(BaseDanmaku baseDanmaku) {
                DrawingCache drawingCacheBuildDanmakuDrawingCache;
                if (!baseDanmaku.isMeasured()) {
                    baseDanmaku.measure(CacheManagingDrawTask.this.mDisp, true);
                }
                try {
                    drawingCacheBuildDanmakuDrawingCache = (DrawingCache) CacheManager.this.mCachePool.acquire();
                } catch (Exception unused) {
                    drawingCacheBuildDanmakuDrawingCache = null;
                } catch (OutOfMemoryError unused2) {
                    drawingCacheBuildDanmakuDrawingCache = null;
                }
                try {
                    CacheManagingDrawTask cacheManagingDrawTask = CacheManagingDrawTask.this;
                    drawingCacheBuildDanmakuDrawingCache = DanmakuUtils.buildDanmakuDrawingCache(baseDanmaku, cacheManagingDrawTask.mDisp, drawingCacheBuildDanmakuDrawingCache, cacheManagingDrawTask.mContext.cachingPolicy.bitsPerPixelOfCache);
                    baseDanmaku.cache = drawingCacheBuildDanmakuDrawingCache;
                    return true;
                } catch (Exception unused3) {
                    if (drawingCacheBuildDanmakuDrawingCache != null) {
                        CacheManager.this.mCachePool.release(drawingCacheBuildDanmakuDrawingCache);
                    }
                    baseDanmaku.cache = null;
                    return false;
                } catch (OutOfMemoryError unused4) {
                    if (drawingCacheBuildDanmakuDrawingCache != null) {
                        CacheManager.this.mCachePool.release(drawingCacheBuildDanmakuDrawingCache);
                    }
                    baseDanmaku.cache = null;
                    return false;
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:41:0x0127  */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void handleMessage(android.os.Message r9) {
                /*
                    Method dump skipped, instructions count: 460
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: net.polyv.danmaku.controller.CacheManagingDrawTask.CacheManager.CacheHandler.handleMessage(android.os.Message):void");
            }

            public boolean isPause() {
                return this.mPause;
            }

            public void onPlayStateChanged(boolean z2) {
                this.mIsPlayerPause = !z2;
            }

            public void pause() {
                this.mPause = true;
                sendEmptyMessage(6);
            }

            public void requestBuildCacheAndDraw(long j2) {
                removeMessages(3);
                this.mSeekedFlag = true;
                sendEmptyMessage(18);
                CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond + j2);
                sendEmptyMessage(3);
            }

            public void requestCancelCaching() {
                this.mCancelFlag = true;
            }

            public void resume() {
                sendEmptyMessage(18);
                this.mPause = false;
                removeMessages(16);
                sendEmptyMessage(16);
                sendEmptyMessageDelayed(4, CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
            }
        }

        public CacheManager(int i2, int i3) {
            DrawingCachePoolManager drawingCachePoolManager = new DrawingCachePoolManager();
            this.mCachePoolManager = drawingCachePoolManager;
            this.mCachePool = Pools.finitePool(drawingCachePoolManager, 800);
            this.mEndFlag = false;
            this.mRealSize = 0;
            this.mMaxSize = i2;
            this.mScreenSize = i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long clearCache(BaseDanmaku baseDanmaku) {
            IDrawingCache<?> iDrawingCache = baseDanmaku.cache;
            if (iDrawingCache == null) {
                return 0L;
            }
            if (iDrawingCache.hasReferences()) {
                iDrawingCache.decreaseReference();
                baseDanmaku.cache = null;
                return 0L;
            }
            long jSizeOf = sizeOf(baseDanmaku);
            iDrawingCache.destroy();
            baseDanmaku.cache = null;
            return jSizeOf;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCachePool() {
            while (true) {
                DrawingCache drawingCache = (DrawingCache) this.mCachePool.acquire();
                if (drawingCache == null) {
                    return;
                } else {
                    drawingCache.destroy();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimeOutAndFilteredCaches(final int i2, final boolean z2) {
            this.mCaches.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: net.polyv.danmaku.controller.CacheManagingDrawTask.CacheManager.5
                @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (CacheManager.this.mEndFlag || CacheManager.this.mRealSize + i2 <= CacheManager.this.mMaxSize) {
                        return 1;
                    }
                    if (!baseDanmaku.isTimeOut() && !baseDanmaku.isFiltered()) {
                        return z2 ? 1 : 0;
                    }
                    CacheManager.this.entryRemoved(false, baseDanmaku, null);
                    return 2;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimeOutCaches() {
            this.mCaches.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: net.polyv.danmaku.controller.CacheManagingDrawTask.CacheManager.3
                @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (!baseDanmaku.isTimeOut()) {
                        return 1;
                    }
                    IDrawingCache<?> iDrawingCache = baseDanmaku.cache;
                    if (CacheManagingDrawTask.this.mContext.cachingPolicy.periodOfRecycle == -1 && iDrawingCache != null && !iDrawingCache.hasReferences() && iDrawingCache.size() / CacheManagingDrawTask.this.mMaxCacheSize < CacheManagingDrawTask.this.mContext.cachingPolicy.forceRecyleThreshold) {
                        return 0;
                    }
                    if (!CacheManager.this.mEndFlag) {
                        synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                            try {
                                try {
                                    CacheManagingDrawTask.this.mDrawingNotify.wait(30L);
                                } catch (InterruptedException e2) {
                                    e2.printStackTrace();
                                    return 1;
                                }
                            } finally {
                            }
                        }
                    }
                    CacheManager.this.entryRemoved(false, baseDanmaku, null);
                    return 2;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void evictAll() {
            Danmakus danmakus = this.mCaches;
            if (danmakus != null) {
                danmakus.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: net.polyv.danmaku.controller.CacheManagingDrawTask.CacheManager.1
                    @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        CacheManager.this.entryRemoved(true, baseDanmaku, null);
                        return 0;
                    }
                });
                this.mCaches.clear();
            }
            this.mRealSize = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void evictAllNotInScreen() {
            Danmakus danmakus = this.mCaches;
            if (danmakus != null) {
                danmakus.forEach(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: net.polyv.danmaku.controller.CacheManagingDrawTask.CacheManager.2
                    @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                    public int accept(BaseDanmaku baseDanmaku) {
                        if (!baseDanmaku.isOutside()) {
                            return 0;
                        }
                        CacheManager.this.entryRemoved(true, baseDanmaku, null);
                        return 2;
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public BaseDanmaku findReusableCache(final BaseDanmaku baseDanmaku, final boolean z2, final int i2) {
            final int slopPixel = (!z2 ? CacheManagingDrawTask.this.mDisp.getSlopPixel() * 2 : 0) + CacheManagingDrawTask.this.mContext.cachingPolicy.reusableOffsetPixel;
            IDanmakus.Consumer<BaseDanmaku, BaseDanmaku> consumer = new IDanmakus.Consumer<BaseDanmaku, BaseDanmaku>() { // from class: net.polyv.danmaku.controller.CacheManagingDrawTask.CacheManager.4
                int count = 0;
                BaseDanmaku mResult;

                @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku2) {
                    int i3 = this.count;
                    this.count = i3 + 1;
                    if (i3 >= i2) {
                        return 1;
                    }
                    IDrawingCache<?> drawingCache = baseDanmaku2.getDrawingCache();
                    if (drawingCache != null && drawingCache.get() != null) {
                        float f2 = baseDanmaku2.paintWidth;
                        BaseDanmaku baseDanmaku3 = baseDanmaku;
                        if (f2 == baseDanmaku3.paintWidth && baseDanmaku2.paintHeight == baseDanmaku3.paintHeight && baseDanmaku2.underlineColor == baseDanmaku3.underlineColor && baseDanmaku2.borderColor == baseDanmaku3.borderColor && baseDanmaku2.textColor == baseDanmaku3.textColor && baseDanmaku2.text.equals(baseDanmaku3.text) && baseDanmaku2.tag == baseDanmaku.tag) {
                            this.mResult = baseDanmaku2;
                            return 1;
                        }
                        if (z2) {
                            return 0;
                        }
                        if (!baseDanmaku2.isTimeOut()) {
                            return 1;
                        }
                        if (drawingCache.hasReferences()) {
                            return 0;
                        }
                        float fWidth = drawingCache.width() - baseDanmaku.paintWidth;
                        float fHeight = drawingCache.height() - baseDanmaku.paintHeight;
                        if (fWidth >= 0.0f) {
                            int i4 = slopPixel;
                            if (fWidth <= i4 && fHeight >= 0.0f && fHeight <= i4) {
                                this.mResult = baseDanmaku2;
                                return 1;
                            }
                        }
                    }
                    return 0;
                }

                @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                public BaseDanmaku result() {
                    return this.mResult;
                }
            };
            this.mCaches.forEach(consumer);
            return consumer.result();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean push(BaseDanmaku baseDanmaku, int i2, boolean z2) {
            if (i2 > 0) {
                clearTimeOutAndFilteredCaches(i2, z2);
            }
            this.mCaches.addItem(baseDanmaku);
            this.mRealSize += i2;
            return true;
        }

        @Override // net.polyv.danmaku.danmaku.model.ICacheManager
        public void addDanmaku(BaseDanmaku baseDanmaku) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                if (!baseDanmaku.isLive || !baseDanmaku.forceBuildCacheInSameThread) {
                    cacheHandler.obtainMessage(2, baseDanmaku).sendToTarget();
                } else {
                    if (baseDanmaku.isTimeOut()) {
                        return;
                    }
                    this.mHandler.createCache(baseDanmaku);
                }
            }
        }

        public void begin() {
            this.mEndFlag = false;
            if (this.mThread == null) {
                HandlerThread handlerThread = new HandlerThread("DFM Cache-Building Thread");
                this.mThread = handlerThread;
                handlerThread.start();
            }
            if (this.mHandler == null) {
                this.mHandler = new CacheHandler(this.mThread.getLooper());
            }
            this.mHandler.begin();
        }

        public void end() throws InterruptedException {
            this.mEndFlag = true;
            synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                CacheManagingDrawTask.this.mDrawingNotify.notifyAll();
            }
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                cacheHandler.removeCallbacksAndMessages(null);
                this.mHandler.pause();
                this.mHandler = null;
            }
            HandlerThread handlerThread = this.mThread;
            if (handlerThread != null) {
                try {
                    handlerThread.join();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                this.mThread.quit();
                this.mThread = null;
            }
        }

        public void entryRemoved(boolean z2, BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
            if (drawingCache != null) {
                long jClearCache = clearCache(baseDanmaku);
                if (baseDanmaku.isTimeOut()) {
                    CacheManagingDrawTask.this.mContext.getDisplayer().getCacheStuffer().releaseResource(baseDanmaku);
                }
                if (jClearCache <= 0) {
                    return;
                }
                this.mRealSize = (int) (this.mRealSize - jClearCache);
                this.mCachePool.release((DrawingCache) drawingCache);
            }
        }

        public long getFirstCacheTime() {
            BaseDanmaku baseDanmakuFirst;
            Danmakus danmakus = this.mCaches;
            if (danmakus == null || danmakus.size() <= 0 || (baseDanmakuFirst = this.mCaches.first()) == null) {
                return 0L;
            }
            return baseDanmakuFirst.getActualTime();
        }

        public float getPoolPercent() {
            int i2 = this.mMaxSize;
            if (i2 == 0) {
                return 0.0f;
            }
            return this.mRealSize / i2;
        }

        public void invalidateDanmaku(BaseDanmaku baseDanmaku, boolean z2) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                cacheHandler.requestCancelCaching();
                this.mHandler.obtainMessage(17, baseDanmaku).sendToTarget();
                this.mHandler.sendEmptyMessage(18);
                requestBuild(0L);
            }
        }

        public boolean isPoolFull() {
            return this.mRealSize + R2.color.m3_ref_palette_dynamic_tertiary10 >= this.mMaxSize;
        }

        public void onPlayStateChanged(int i2) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                cacheHandler.onPlayStateChanged(i2 == 1);
            }
        }

        public void post(Runnable runnable) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.post(runnable);
        }

        public void requestBuild(long j2) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                cacheHandler.requestBuildCacheAndDraw(j2);
            }
        }

        public void requestClearAll() {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.removeMessages(3);
            this.mHandler.removeMessages(18);
            this.mHandler.requestCancelCaching();
            this.mHandler.removeMessages(7);
            this.mHandler.sendEmptyMessage(7);
        }

        public void requestClearTimeout() {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.removeMessages(4);
            this.mHandler.sendEmptyMessage(4);
        }

        public void requestClearUnused() {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.removeMessages(9);
            this.mHandler.sendEmptyMessage(9);
        }

        public void resume() {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler != null) {
                cacheHandler.resume();
            } else {
                begin();
            }
        }

        public void seek(long j2) {
            CacheHandler cacheHandler = this.mHandler;
            if (cacheHandler == null) {
                return;
            }
            cacheHandler.requestCancelCaching();
            this.mHandler.removeMessages(3);
            this.mHandler.obtainMessage(5, Long.valueOf(j2)).sendToTarget();
        }

        public int sizeOf(BaseDanmaku baseDanmaku) {
            IDrawingCache<?> iDrawingCache = baseDanmaku.cache;
            if (iDrawingCache == null || iDrawingCache.hasReferences()) {
                return 0;
            }
            return baseDanmaku.cache.size();
        }
    }

    public CacheManagingDrawTask(DanmakuTimer danmakuTimer, DanmakuContext danmakuContext, IDrawTask.TaskListener taskListener) throws NoSuchFieldException, SecurityException {
        super(danmakuTimer, danmakuContext, taskListener);
        this.mMaxCacheSize = 2;
        this.mDrawingNotify = new Object();
        NativeBitmapFactory.loadLibs();
        int iMax = (int) Math.max(4194304.0f, Runtime.getRuntime().maxMemory() * danmakuContext.cachingPolicy.maxCachePoolSizeFactorPercentage);
        this.mMaxCacheSize = iMax;
        CacheManager cacheManager = new CacheManager(iMax, 3);
        this.mCacheManager = cacheManager;
        this.mRenderer.setCacheManager(cacheManager);
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void addDanmaku(BaseDanmaku baseDanmaku) {
        super.addDanmaku(baseDanmaku);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager == null) {
            return;
        }
        cacheManager.addDanmaku(baseDanmaku);
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public IRenderer.RenderingState draw(AbsDisplayer absDisplayer) {
        CacheManager cacheManager;
        IRenderer.RenderingState renderingStateDraw = super.draw(absDisplayer);
        synchronized (this.mDrawingNotify) {
            this.mDrawingNotify.notify();
        }
        if (renderingStateDraw != null && (cacheManager = this.mCacheManager) != null && renderingStateDraw.totalDanmakuCount - renderingStateDraw.lastTotalDanmakuCount < -20) {
            cacheManager.requestClearTimeout();
            this.mCacheManager.requestBuild(-this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
        }
        return renderingStateDraw;
    }

    @Override // net.polyv.danmaku.controller.DrawTask
    public void initTimer(DanmakuTimer danmakuTimer) {
        this.mTimer = danmakuTimer;
        DanmakuTimer danmakuTimer2 = new DanmakuTimer();
        this.mCacheTimer = danmakuTimer2;
        danmakuTimer2.update(danmakuTimer.currMillisecond);
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void invalidateDanmaku(BaseDanmaku baseDanmaku, boolean z2) {
        super.invalidateDanmaku(baseDanmaku, z2);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager == null) {
            return;
        }
        cacheManager.invalidateDanmaku(baseDanmaku, z2);
    }

    @Override // net.polyv.danmaku.controller.DrawTask
    public boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuContext.DanmakuConfigTag danmakuConfigTag, Object... objArr) {
        CacheManager cacheManager;
        Object obj;
        CacheManager cacheManager2;
        if (!super.handleOnDanmakuConfigChanged(danmakuContext, danmakuConfigTag, objArr)) {
            if (DanmakuContext.DanmakuConfigTag.SCROLL_SPEED_FACTOR.equals(danmakuConfigTag)) {
                this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
                requestClear();
            } else if (danmakuConfigTag.isVisibilityRelatedTag()) {
                if (objArr != null && objArr.length > 0 && (obj = objArr[0]) != null && ((!(obj instanceof Boolean) || ((Boolean) obj).booleanValue()) && (cacheManager2 = this.mCacheManager) != null)) {
                    cacheManager2.requestBuild(0L);
                }
                requestClear();
            } else if (DanmakuContext.DanmakuConfigTag.TRANSPARENCY.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.SCALE_TEXTSIZE.equals(danmakuConfigTag) || DanmakuContext.DanmakuConfigTag.DANMAKU_STYLE.equals(danmakuConfigTag)) {
                if (DanmakuContext.DanmakuConfigTag.SCALE_TEXTSIZE.equals(danmakuConfigTag)) {
                    this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
                }
                CacheManager cacheManager3 = this.mCacheManager;
                if (cacheManager3 != null) {
                    cacheManager3.requestClearAll();
                    this.mCacheManager.requestBuild(-this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
                }
            } else {
                CacheManager cacheManager4 = this.mCacheManager;
                if (cacheManager4 != null) {
                    cacheManager4.requestClearUnused();
                    this.mCacheManager.requestBuild(0L);
                }
            }
        }
        if (this.mTaskListener == null || (cacheManager = this.mCacheManager) == null) {
            return true;
        }
        cacheManager.post(new Runnable() { // from class: net.polyv.danmaku.controller.CacheManagingDrawTask.1
            @Override // java.lang.Runnable
            public void run() {
                CacheManagingDrawTask.this.mTaskListener.onDanmakuConfigChanged();
            }
        });
        return true;
    }

    @Override // net.polyv.danmaku.controller.DrawTask
    public void onDanmakuRemoved(BaseDanmaku baseDanmaku) {
        super.onDanmakuRemoved(baseDanmaku);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            int i2 = this.mRemaininCacheCount + 1;
            this.mRemaininCacheCount = i2;
            if (i2 > 5) {
                cacheManager.requestClearTimeout();
                this.mRemaininCacheCount = 0;
                return;
            }
            return;
        }
        IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
        if (drawingCache != null) {
            if (drawingCache.hasReferences()) {
                drawingCache.decreaseReference();
            } else {
                drawingCache.destroy();
            }
            baseDanmaku.cache = null;
        }
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void onPlayStateChanged(int i2) {
        super.onPlayStateChanged(i2);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            cacheManager.onPlayStateChanged(i2);
        }
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void prepare() {
        BaseDanmakuParser baseDanmakuParser = this.mParser;
        if (baseDanmakuParser == null) {
            return;
        }
        loadDanmakus(baseDanmakuParser);
        this.mCacheManager.begin();
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void quit() throws InterruptedException {
        super.quit();
        reset();
        this.mRenderer.setCacheManager(null);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            cacheManager.end();
            this.mCacheManager = null;
        }
        NativeBitmapFactory.releaseLibs();
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void removeAllDanmakus(boolean z2) {
        super.removeAllDanmakus(z2);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            cacheManager.requestClearAll();
        }
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void requestSync(long j2, long j3, long j4) {
        super.requestSync(j2, j3, j4);
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            cacheManager.seek(j3);
        }
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void seek(long j2) throws NoSuchFieldException, SecurityException {
        super.seek(j2);
        if (this.mCacheManager == null) {
            start();
        }
        this.mCacheManager.seek(j2);
    }

    @Override // net.polyv.danmaku.controller.DrawTask, net.polyv.danmaku.controller.IDrawTask
    public void start() throws NoSuchFieldException, SecurityException {
        super.start();
        NativeBitmapFactory.loadLibs();
        CacheManager cacheManager = this.mCacheManager;
        if (cacheManager != null) {
            cacheManager.resume();
            return;
        }
        CacheManager cacheManager2 = new CacheManager(this.mMaxCacheSize, 3);
        this.mCacheManager = cacheManager2;
        cacheManager2.begin();
        this.mRenderer.setCacheManager(this.mCacheManager);
    }
}
