package master.flame.danmaku.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.LinkedList;
import java.util.Locale;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.DrawHelper;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.controller.IDanmakuViewController;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.util.SystemClock;

/* loaded from: classes8.dex */
public class DanmakuView extends View implements IDanmakuView, IDanmakuViewController {
    private static final int MAX_RECORD_SIZE = 50;
    private static final int ONE_SECOND = 1000;
    public static final String TAG = "DanmakuView";
    protected volatile DrawHandler handler;
    private boolean isSurfaceCreated;
    private DrawHandler.Callback mCallback;
    protected boolean mClearFlag;
    private boolean mDanmakuVisible;
    private boolean mDrawFinished;
    private Object mDrawMonitor;
    private LinkedList<Long> mDrawTimes;
    protected int mDrawingThreadType;
    private boolean mEnableDanmakuDrwaingCache;
    private HandlerThread mHandlerThread;
    private View.OnClickListener mOnClickListener;
    private IDanmakuView.OnDanmakuClickListener mOnDanmakuClickListener;
    protected boolean mRequestRender;
    private Runnable mResumeRunnable;
    private int mResumeTryCount;
    private boolean mShowFps;
    private DanmakuTouchHelper mTouchHelper;
    private long mUiThreadId;
    private float mXOff;
    private float mYOff;

    public DanmakuView(Context context) {
        super(context);
        this.mEnableDanmakuDrwaingCache = true;
        this.mDanmakuVisible = true;
        this.mDrawingThreadType = 0;
        this.mDrawMonitor = new Object();
        this.mDrawFinished = false;
        this.mRequestRender = false;
        this.mResumeTryCount = 0;
        this.mResumeRunnable = new Runnable() { // from class: master.flame.danmaku.ui.widget.DanmakuView.1
            @Override // java.lang.Runnable
            public void run() {
                DrawHandler drawHandler = DanmakuView.this.handler;
                if (drawHandler == null) {
                    return;
                }
                DanmakuView.access$008(DanmakuView.this);
                if (DanmakuView.this.mResumeTryCount > 4 || DanmakuView.super.isShown()) {
                    drawHandler.resume();
                } else {
                    drawHandler.postDelayed(this, DanmakuView.this.mResumeTryCount * 100);
                }
            }
        };
        init();
    }

    public static /* synthetic */ int access$008(DanmakuView danmakuView) {
        int i2 = danmakuView.mResumeTryCount;
        danmakuView.mResumeTryCount = i2 + 1;
        return i2;
    }

    private float fps() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        this.mDrawTimes.addLast(Long.valueOf(jUptimeMillis));
        Long lPeekFirst = this.mDrawTimes.peekFirst();
        if (lPeekFirst == null) {
            return 0.0f;
        }
        float fLongValue = jUptimeMillis - lPeekFirst.longValue();
        if (this.mDrawTimes.size() > 50) {
            this.mDrawTimes.removeFirst();
        }
        if (fLongValue > 0.0f) {
            return (this.mDrawTimes.size() * 1000) / fLongValue;
        }
        return 0.0f;
    }

    private void init() {
        this.mUiThreadId = Thread.currentThread().getId();
        setBackgroundColor(0);
        setDrawingCacheBackgroundColor(0);
        DrawHelper.useDrawColorToClearCanvas(true, false);
        this.mTouchHelper = DanmakuTouchHelper.instance(this);
    }

    private void lockCanvasAndClear() {
        this.mClearFlag = true;
        lockCanvas();
    }

    @SuppressLint({"NewApi"})
    private void postInvalidateCompat() {
        this.mRequestRender = true;
        postInvalidateOnAnimation();
    }

    private void prepare() {
        if (this.handler == null) {
            this.handler = new DrawHandler(getLooper(this.mDrawingThreadType), this, this.mDanmakuVisible);
        }
    }

    private synchronized void stopDraw() {
        if (this.handler == null) {
            return;
        }
        DrawHandler drawHandler = this.handler;
        this.handler = null;
        unlockCanvasAndPost();
        if (drawHandler != null) {
            drawHandler.quit();
        }
        HandlerThread handlerThread = this.mHandlerThread;
        this.mHandlerThread = null;
        if (handlerThread != null) {
            try {
                handlerThread.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            handlerThread.quit();
        }
    }

    private void unlockCanvasAndPost() {
        synchronized (this.mDrawMonitor) {
            this.mDrawFinished = true;
            this.mDrawMonitor.notifyAll();
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void addDanmaku(BaseDanmaku baseDanmaku) {
        if (this.handler != null) {
            this.handler.addDanmaku(baseDanmaku);
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public void clear() {
        if (isViewReady()) {
            if (this.mDanmakuVisible && Thread.currentThread().getId() != this.mUiThreadId) {
                lockCanvasAndClear();
            } else {
                this.mClearFlag = true;
                postInvalidateCompat();
            }
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void clearDanmakusOnScreen() {
        if (this.handler != null) {
            this.handler.clearDanmakusOnScreen();
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public long drawDanmakus() {
        if (!this.isSurfaceCreated) {
            return 0L;
        }
        if (!isShown()) {
            return -1L;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        lockCanvas();
        return SystemClock.uptimeMillis() - jUptimeMillis;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void enableDanmakuDrawingCache(boolean z2) {
        this.mEnableDanmakuDrwaingCache = z2;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void forceRender() {
        this.mRequestRender = true;
        this.handler.forceRender();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public DanmakuContext getConfig() {
        if (this.handler == null) {
            return null;
        }
        return this.handler.getConfig();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public long getCurrentTime() {
        if (this.handler != null) {
            return this.handler.getCurrentTime();
        }
        return 0L;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public IDanmakus getCurrentVisibleDanmakus() {
        if (this.handler != null) {
            return this.handler.getCurrentVisibleDanmakus();
        }
        return null;
    }

    public synchronized Looper getLooper(int i2) {
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mHandlerThread = null;
        }
        if (i2 == 1) {
            return Looper.getMainLooper();
        }
        int i3 = i2 != 2 ? i2 != 3 ? 0 : 19 : -8;
        HandlerThread handlerThread2 = new HandlerThread("DFM Handler Thread #" + i3, i3);
        this.mHandlerThread = handlerThread2;
        handlerThread2.start();
        return this.mHandlerThread.getLooper();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public IDanmakuView.OnDanmakuClickListener getOnDanmakuClickListener() {
        return this.mOnDanmakuClickListener;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public View getView() {
        return this;
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public int getViewHeight() {
        return super.getHeight();
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public int getViewWidth() {
        return super.getWidth();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public float getXOff() {
        return this.mXOff;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public float getYOff() {
        return this.mYOff;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void hide() {
        this.mDanmakuVisible = false;
        if (this.handler == null) {
            return;
        }
        this.handler.hideDanmakus(false);
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public long hideAndPauseDrawTask() {
        this.mDanmakuVisible = false;
        if (this.handler == null) {
            return 0L;
        }
        return this.handler.hideDanmakus(true);
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void invalidateDanmaku(BaseDanmaku baseDanmaku, boolean z2) {
        if (this.handler != null) {
            this.handler.invalidateDanmaku(baseDanmaku, z2);
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView, master.flame.danmaku.controller.IDanmakuViewController
    public boolean isDanmakuDrawingCacheEnabled() {
        return this.mEnableDanmakuDrwaingCache;
    }

    @Override // android.view.View, master.flame.danmaku.controller.IDanmakuView, master.flame.danmaku.controller.IDanmakuViewController
    @SuppressLint({"NewApi"})
    public boolean isHardwareAccelerated() {
        return super.isHardwareAccelerated();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public boolean isPaused() {
        if (this.handler != null) {
            return this.handler.isStop();
        }
        return false;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public boolean isPrepared() {
        return this.handler != null && this.handler.isPrepared();
    }

    @Override // android.view.View, master.flame.danmaku.controller.IDanmakuView
    public boolean isShown() {
        return this.mDanmakuVisible && super.isShown();
    }

    @Override // master.flame.danmaku.controller.IDanmakuViewController
    public boolean isViewReady() {
        return this.isSurfaceCreated;
    }

    public void lockCanvas() {
        if (this.mDanmakuVisible) {
            postInvalidateCompat();
            synchronized (this.mDrawMonitor) {
                while (!this.mDrawFinished && this.handler != null) {
                    try {
                        this.mDrawMonitor.wait(200L);
                    } catch (InterruptedException unused) {
                        if (!this.mDanmakuVisible || this.handler == null || this.handler.isStop()) {
                            break;
                        } else {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                this.mDrawFinished = false;
            }
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (!this.mDanmakuVisible && !this.mRequestRender) {
            super.onDraw(canvas);
            return;
        }
        if (this.mClearFlag) {
            DrawHelper.clearCanvas(canvas);
            this.mClearFlag = false;
        } else if (this.handler != null) {
            IRenderer.RenderingState renderingStateDraw = this.handler.draw(canvas);
            if (this.mShowFps) {
                if (this.mDrawTimes == null) {
                    this.mDrawTimes = new LinkedList<>();
                }
                DrawHelper.drawFPS(canvas, String.format(Locale.getDefault(), "fps %.2f,time:%d s,cache:%d,miss:%d", Float.valueOf(fps()), Long.valueOf(getCurrentTime() / 1000), Long.valueOf(renderingStateDraw.cacheHitCount), Long.valueOf(renderingStateDraw.cacheMissCount)));
            }
        }
        this.mRequestRender = false;
        unlockCanvasAndPost();
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.handler != null) {
            this.handler.notifyDispSizeChanged(i4 - i2, i5 - i3);
        }
        this.isSurfaceCreated = true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent = this.mTouchHelper.onTouchEvent(motionEvent);
        return !zOnTouchEvent ? super.onTouchEvent(motionEvent) : zOnTouchEvent;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void pause() {
        if (this.handler != null) {
            this.handler.removeCallbacks(this.mResumeRunnable);
            this.handler.pause();
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void release() {
        stop();
        LinkedList<Long> linkedList = this.mDrawTimes;
        if (linkedList != null) {
            linkedList.clear();
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void removeAllDanmakus(boolean z2) {
        if (this.handler != null) {
            this.handler.removeAllDanmakus(z2);
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void removeAllLiveDanmakus() {
        if (this.handler != null) {
            this.handler.removeAllLiveDanmakus();
        }
    }

    public void restart() {
        stop();
        start();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void resume() {
        if (this.handler != null && this.handler.isPrepared()) {
            this.mResumeTryCount = 0;
            this.handler.post(this.mResumeRunnable);
        } else if (this.handler == null) {
            restart();
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void seekTo(Long l2) {
        if (this.handler != null) {
            this.handler.seekTo(l2);
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void setCallback(DrawHandler.Callback callback) {
        this.mCallback = callback;
        if (this.handler != null) {
            this.handler.setCallback(callback);
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void setDrawingThreadType(int i2) {
        this.mDrawingThreadType = i2;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void setOnDanmakuClickListener(IDanmakuView.OnDanmakuClickListener onDanmakuClickListener) {
        this.mOnDanmakuClickListener = onDanmakuClickListener;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void show() {
        showAndResumeDrawTask(null);
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void showAndResumeDrawTask(Long l2) {
        this.mDanmakuVisible = true;
        this.mClearFlag = false;
        if (this.handler == null) {
            return;
        }
        this.handler.showDanmakus(l2);
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void showFPS(boolean z2) {
        this.mShowFps = z2;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void start() {
        start(0L);
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void stop() {
        stopDraw();
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void toggle() {
        if (this.isSurfaceCreated) {
            if (this.handler == null) {
                start();
            } else if (this.handler.isStop()) {
                resume();
            } else {
                pause();
            }
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void setOnDanmakuClickListener(IDanmakuView.OnDanmakuClickListener onDanmakuClickListener, float f2, float f3) {
        this.mOnDanmakuClickListener = onDanmakuClickListener;
        this.mXOff = f2;
        this.mYOff = f3;
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void start(long j2) {
        DrawHandler drawHandler = this.handler;
        if (drawHandler == null) {
            prepare();
            drawHandler = this.handler;
        } else {
            drawHandler.removeCallbacksAndMessages(null);
        }
        if (drawHandler != null) {
            drawHandler.obtainMessage(1, Long.valueOf(j2)).sendToTarget();
        }
    }

    @Override // master.flame.danmaku.controller.IDanmakuView
    public void prepare(BaseDanmakuParser baseDanmakuParser, DanmakuContext danmakuContext) {
        prepare();
        this.handler.setConfig(danmakuContext);
        this.handler.setParser(baseDanmakuParser);
        this.handler.setCallback(this.mCallback);
        this.handler.prepare();
    }

    public DanmakuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnableDanmakuDrwaingCache = true;
        this.mDanmakuVisible = true;
        this.mDrawingThreadType = 0;
        this.mDrawMonitor = new Object();
        this.mDrawFinished = false;
        this.mRequestRender = false;
        this.mResumeTryCount = 0;
        this.mResumeRunnable = new Runnable() { // from class: master.flame.danmaku.ui.widget.DanmakuView.1
            @Override // java.lang.Runnable
            public void run() {
                DrawHandler drawHandler = DanmakuView.this.handler;
                if (drawHandler == null) {
                    return;
                }
                DanmakuView.access$008(DanmakuView.this);
                if (DanmakuView.this.mResumeTryCount > 4 || DanmakuView.super.isShown()) {
                    drawHandler.resume();
                } else {
                    drawHandler.postDelayed(this, DanmakuView.this.mResumeTryCount * 100);
                }
            }
        };
        init();
    }

    public DanmakuView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mEnableDanmakuDrwaingCache = true;
        this.mDanmakuVisible = true;
        this.mDrawingThreadType = 0;
        this.mDrawMonitor = new Object();
        this.mDrawFinished = false;
        this.mRequestRender = false;
        this.mResumeTryCount = 0;
        this.mResumeRunnable = new Runnable() { // from class: master.flame.danmaku.ui.widget.DanmakuView.1
            @Override // java.lang.Runnable
            public void run() {
                DrawHandler drawHandler = DanmakuView.this.handler;
                if (drawHandler == null) {
                    return;
                }
                DanmakuView.access$008(DanmakuView.this);
                if (DanmakuView.this.mResumeTryCount > 4 || DanmakuView.super.isShown()) {
                    drawHandler.resume();
                } else {
                    drawHandler.postDelayed(this, DanmakuView.this.mResumeTryCount * 100);
                }
            }
        };
        init();
    }
}
