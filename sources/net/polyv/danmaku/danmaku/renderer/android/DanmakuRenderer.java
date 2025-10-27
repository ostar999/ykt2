package net.polyv.danmaku.danmaku.renderer.android;

import net.polyv.danmaku.controller.DanmakuFilters;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.DanmakuTimer;
import net.polyv.danmaku.danmaku.model.ICacheManager;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.IDisplayer;
import net.polyv.danmaku.danmaku.model.IDrawingCache;
import net.polyv.danmaku.danmaku.model.android.DanmakuContext;
import net.polyv.danmaku.danmaku.renderer.IRenderer;
import net.polyv.danmaku.danmaku.renderer.Renderer;
import net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer;

/* loaded from: classes9.dex */
public class DanmakuRenderer extends Renderer {
    private ICacheManager mCacheManager;
    private final DanmakuContext mContext;
    private final DanmakusRetainer mDanmakusRetainer;
    private IRenderer.OnDanmakuShownListener mOnDanmakuShownListener;
    private DanmakuTimer mStartTimer;
    private DanmakusRetainer.Verifier mVerifier;
    private final DanmakusRetainer.Verifier verifier = new DanmakusRetainer.Verifier() { // from class: net.polyv.danmaku.danmaku.renderer.android.DanmakuRenderer.1
        @Override // net.polyv.danmaku.danmaku.renderer.android.DanmakusRetainer.Verifier
        public boolean skipLayout(BaseDanmaku baseDanmaku, float f2, int i2, boolean z2) {
            if (baseDanmaku.priority != 0 || !DanmakuRenderer.this.mContext.mDanmakuFilters.filterSecondary(baseDanmaku, i2, 0, DanmakuRenderer.this.mStartTimer, z2, DanmakuRenderer.this.mContext)) {
                return false;
            }
            baseDanmaku.setVisibility(false);
            return true;
        }
    };
    private Consumer mConsumer = new Consumer();

    public class Consumer extends IDanmakus.DefaultConsumer<BaseDanmaku> {
        public IDisplayer disp;
        private BaseDanmaku lastItem;
        public IRenderer.RenderingState renderingState;
        public long startRenderTime;

        private Consumer() {
        }

        @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
        public void after() {
            this.renderingState.lastDanmaku = this.lastItem;
            super.after();
        }

        @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
        public int accept(BaseDanmaku baseDanmaku) {
            this.lastItem = baseDanmaku;
            if (baseDanmaku.isTimeOut()) {
                this.disp.recycle(baseDanmaku);
                return this.renderingState.isRunningDanmakus ? 2 : 0;
            }
            if (!this.renderingState.isRunningDanmakus && baseDanmaku.isOffset()) {
                return 0;
            }
            if (!baseDanmaku.hasPassedFilter()) {
                DanmakuFilters danmakuFilters = DanmakuRenderer.this.mContext.mDanmakuFilters;
                IRenderer.RenderingState renderingState = this.renderingState;
                danmakuFilters.filter(baseDanmaku, renderingState.indexInScreen, renderingState.totalSizeInScreen, renderingState.timer, false, DanmakuRenderer.this.mContext);
            }
            if (baseDanmaku.getActualTime() >= this.startRenderTime && (baseDanmaku.priority != 0 || !baseDanmaku.isFiltered())) {
                if (baseDanmaku.isLate()) {
                    IDrawingCache<?> drawingCache = baseDanmaku.getDrawingCache();
                    if (DanmakuRenderer.this.mCacheManager != null && (drawingCache == null || drawingCache.get() == null)) {
                        DanmakuRenderer.this.mCacheManager.addDanmaku(baseDanmaku);
                    }
                    return 1;
                }
                if (baseDanmaku.getType() == 1) {
                    this.renderingState.indexInScreen++;
                }
                if (!baseDanmaku.isMeasured()) {
                    baseDanmaku.measure(this.disp, false);
                }
                if (!baseDanmaku.isPrepared()) {
                    baseDanmaku.prepare(this.disp, false);
                }
                DanmakuRenderer.this.mDanmakusRetainer.fix(baseDanmaku, this.disp, DanmakuRenderer.this.mVerifier);
                if (!baseDanmaku.isShown() || (baseDanmaku.lines == null && baseDanmaku.getBottom() > this.disp.getHeight())) {
                    return 0;
                }
                int iDraw = baseDanmaku.draw(this.disp);
                if (iDraw == 1) {
                    this.renderingState.cacheHitCount++;
                } else if (iDraw == 2) {
                    this.renderingState.cacheMissCount++;
                    if (DanmakuRenderer.this.mCacheManager != null) {
                        DanmakuRenderer.this.mCacheManager.addDanmaku(baseDanmaku);
                    }
                }
                this.renderingState.addCount(baseDanmaku.getType(), 1);
                this.renderingState.addTotalCount(1);
                this.renderingState.appendToRunningDanmakus(baseDanmaku);
                if (DanmakuRenderer.this.mOnDanmakuShownListener != null && baseDanmaku.firstShownFlag != DanmakuRenderer.this.mContext.mGlobalFlagValues.FIRST_SHOWN_RESET_FLAG) {
                    baseDanmaku.firstShownFlag = DanmakuRenderer.this.mContext.mGlobalFlagValues.FIRST_SHOWN_RESET_FLAG;
                    DanmakuRenderer.this.mOnDanmakuShownListener.onDanmakuShown(baseDanmaku);
                }
            }
            return 0;
        }
    }

    public DanmakuRenderer(DanmakuContext danmakuContext) {
        this.mContext = danmakuContext;
        this.mDanmakusRetainer = new DanmakusRetainer(danmakuContext.isAlignBottom());
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void alignBottom(boolean z2) {
        DanmakusRetainer danmakusRetainer = this.mDanmakusRetainer;
        if (danmakusRetainer != null) {
            danmakusRetainer.alignBottom(z2);
        }
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void clear() {
        clearRetainer();
        this.mContext.mDanmakuFilters.clear();
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void clearRetainer() {
        this.mDanmakusRetainer.clear();
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void draw(IDisplayer iDisplayer, IDanmakus iDanmakus, long j2, IRenderer.RenderingState renderingState) {
        this.mStartTimer = renderingState.timer;
        Consumer consumer = this.mConsumer;
        consumer.disp = iDisplayer;
        consumer.renderingState = renderingState;
        consumer.startRenderTime = j2;
        iDanmakus.forEachSync(consumer);
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void release() {
        this.mDanmakusRetainer.release();
        this.mContext.mDanmakuFilters.clear();
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void removeOnDanmakuShownListener() {
        this.mOnDanmakuShownListener = null;
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void setCacheManager(ICacheManager iCacheManager) {
        this.mCacheManager = iCacheManager;
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void setOnDanmakuShownListener(IRenderer.OnDanmakuShownListener onDanmakuShownListener) {
        this.mOnDanmakuShownListener = onDanmakuShownListener;
    }

    @Override // net.polyv.danmaku.danmaku.renderer.IRenderer
    public void setVerifierEnabled(boolean z2) {
        this.mVerifier = z2 ? this.verifier : null;
    }
}
