package net.polyv.danmaku.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import net.polyv.danmaku.controller.DrawHandler;
import net.polyv.danmaku.controller.DrawHelper;
import net.polyv.danmaku.danmaku.model.AlphaValue;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.DanmakuTimer;
import net.polyv.danmaku.danmaku.model.Duration;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.IDisplayer;
import net.polyv.danmaku.danmaku.model.SpecialDanmaku;
import net.polyv.danmaku.danmaku.model.android.DanmakuContext;
import net.polyv.danmaku.danmaku.model.android.Danmakus;
import net.polyv.danmaku.danmaku.parser.BaseDanmakuParser;
import net.polyv.danmaku.danmaku.util.DanmakuUtils;

/* loaded from: classes9.dex */
public class FakeDanmakuView extends DanmakuView implements DrawHandler.Callback {
    private long mBeginTimeMills;
    private Bitmap mBufferBitmap;
    private Canvas mBufferCanvas;
    private long mEndTimeMills;
    private long mExpectBeginMills;
    private long mFrameIntervalMills;
    private int mHeight;
    private boolean mIsRelease;
    private OnFrameAvailableListener mOnFrameAvailableListener;
    private DanmakuTimer mOuterTimer;
    private int mRetryCount;
    private float mScale;
    private DanmakuTimer mTimer;
    private int mWidth;

    public class CustomParser extends BaseDanmakuParser {
        private final long edTime;
        private final BaseDanmakuParser mBaseParser;
        private float mDispScaleX;
        private float mDispScaleY;
        private int mViewWidth;
        private final long stTime;

        public CustomParser(BaseDanmakuParser baseDanmakuParser, long j2, long j3) {
            this.mBaseParser = baseDanmakuParser;
            this.stTime = j2;
            this.edTime = j3;
        }

        @Override // net.polyv.danmaku.danmaku.parser.BaseDanmakuParser
        public float getViewportSizeFactor() {
            return (this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION * 1.1f) / ((this.mViewWidth * 3800) / 682.0f);
        }

        @Override // net.polyv.danmaku.danmaku.parser.BaseDanmakuParser
        public IDanmakus parse() {
            IDanmakus danmakus;
            final Danmakus danmakus2 = new Danmakus();
            try {
                danmakus = this.mBaseParser.getDanmakus().subnew(this.stTime, this.edTime);
            } catch (Exception unused) {
                danmakus = this.mBaseParser.getDanmakus();
            }
            if (danmakus == null) {
                return danmakus2;
            }
            danmakus.forEach(new IDanmakus.Consumer<BaseDanmaku, Object>() { // from class: net.polyv.danmaku.ui.widget.FakeDanmakuView.CustomParser.1
                @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    long time = baseDanmaku.getTime();
                    if (time < CustomParser.this.stTime) {
                        return 0;
                    }
                    if (time > CustomParser.this.edTime) {
                        return 1;
                    }
                    BaseDanmaku baseDanmakuCreateDanmaku = ((BaseDanmakuParser) CustomParser.this).mContext.mDanmakuFactory.createDanmaku(baseDanmaku.getType(), ((BaseDanmakuParser) CustomParser.this).mContext);
                    if (baseDanmakuCreateDanmaku != null) {
                        baseDanmakuCreateDanmaku.setTime(baseDanmaku.getTime());
                        DanmakuUtils.fillText(baseDanmakuCreateDanmaku, baseDanmaku.text);
                        baseDanmakuCreateDanmaku.textSize = baseDanmaku.textSize;
                        baseDanmakuCreateDanmaku.textColor = baseDanmaku.textColor;
                        baseDanmakuCreateDanmaku.textShadowColor = baseDanmaku.textShadowColor;
                        if (baseDanmaku instanceof SpecialDanmaku) {
                            SpecialDanmaku specialDanmaku = (SpecialDanmaku) baseDanmaku;
                            baseDanmakuCreateDanmaku.index = baseDanmaku.index;
                            baseDanmakuCreateDanmaku.duration = new Duration(specialDanmaku.getDuration());
                            baseDanmakuCreateDanmaku.rotationZ = specialDanmaku.rotateZ;
                            baseDanmakuCreateDanmaku.rotationY = specialDanmaku.rotationY;
                            ((SpecialDanmaku) baseDanmakuCreateDanmaku).isQuadraticEaseOut = specialDanmaku.isQuadraticEaseOut;
                            ((BaseDanmakuParser) CustomParser.this).mContext.mDanmakuFactory.fillTranslationData(baseDanmakuCreateDanmaku, specialDanmaku.beginX, specialDanmaku.beginY, specialDanmaku.endX, specialDanmaku.endY, specialDanmaku.translationDuration, specialDanmaku.translationStartDelay, CustomParser.this.mDispScaleX, CustomParser.this.mDispScaleY);
                            ((BaseDanmakuParser) CustomParser.this).mContext.mDanmakuFactory.fillAlphaData(baseDanmakuCreateDanmaku, specialDanmaku.beginAlpha, specialDanmaku.endAlpha, baseDanmakuCreateDanmaku.getDuration());
                            return 0;
                        }
                        baseDanmakuCreateDanmaku.setTimer(((BaseDanmakuParser) CustomParser.this).mTimer);
                        baseDanmakuCreateDanmaku.mFilterParam = baseDanmaku.mFilterParam;
                        baseDanmakuCreateDanmaku.filterResetFlag = baseDanmaku.filterResetFlag;
                        baseDanmakuCreateDanmaku.flags = ((BaseDanmakuParser) CustomParser.this).mContext.mGlobalFlagValues;
                        synchronized (danmakus2.obtainSynchronizer()) {
                            danmakus2.addItem(baseDanmakuCreateDanmaku);
                        }
                    }
                    return 0;
                }
            });
            return danmakus2;
        }

        @Override // net.polyv.danmaku.danmaku.parser.BaseDanmakuParser
        public BaseDanmakuParser setDisplayer(IDisplayer iDisplayer) {
            super.setDisplayer(iDisplayer);
            BaseDanmakuParser baseDanmakuParser = this.mBaseParser;
            if (baseDanmakuParser != null && baseDanmakuParser.getDisplayer() != null) {
                this.mDispScaleX = this.mDispWidth / this.mBaseParser.getDisplayer().getWidth();
                this.mDispScaleY = this.mDispHeight / this.mBaseParser.getDisplayer().getHeight();
                if (this.mViewWidth <= 1) {
                    this.mViewWidth = iDisplayer.getWidth();
                }
            }
            return this;
        }
    }

    public interface OnFrameAvailableListener {
        void onConfig(DanmakuContext danmakuContext);

        void onFailed(int i2, String str);

        void onFrameAvailable(long j2, Bitmap bitmap);

        void onFramesFinished(long j2);
    }

    public FakeDanmakuView(Context context) {
        super(context);
        this.mWidth = 0;
        this.mHeight = 0;
        this.mScale = 1.0f;
        this.mFrameIntervalMills = 16L;
        this.mRetryCount = 0;
        this.mExpectBeginMills = 0L;
    }

    @Override // net.polyv.danmaku.controller.DrawHandler.Callback
    public void danmakuShown(BaseDanmaku baseDanmaku) {
    }

    @Override // net.polyv.danmaku.ui.widget.DanmakuView, net.polyv.danmaku.controller.IDanmakuViewController
    public long drawDanmakus() {
        Canvas canvas;
        Bitmap bitmapCreateScaledBitmap;
        DanmakuTimer danmakuTimer;
        boolean z2;
        if (this.mIsRelease || (canvas = this.mBufferCanvas) == null || (bitmapCreateScaledBitmap = this.mBufferBitmap) == null || bitmapCreateScaledBitmap.isRecycled()) {
            return 0L;
        }
        bitmapCreateScaledBitmap.eraseColor(0);
        if (this.mClearFlag) {
            DrawHelper.clearCanvas(canvas);
            this.mClearFlag = false;
        } else if (this.handler != null) {
            this.handler.draw(canvas);
        }
        OnFrameAvailableListener onFrameAvailableListener = this.mOnFrameAvailableListener;
        if (onFrameAvailableListener != null) {
            long j2 = this.mOuterTimer.currMillisecond;
            try {
                try {
                    if (j2 >= this.mExpectBeginMills - this.mFrameIntervalMills) {
                        float f2 = this.mScale;
                        if (f2 == 1.0f) {
                            z2 = false;
                        } else {
                            z2 = true;
                            bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateScaledBitmap, (int) (this.mWidth * f2), (int) (this.mHeight * f2), true);
                        }
                        onFrameAvailableListener.onFrameAvailable(j2, bitmapCreateScaledBitmap);
                        if (z2) {
                            bitmapCreateScaledBitmap.recycle();
                        }
                    }
                } catch (Exception e2) {
                    release();
                    onFrameAvailableListener.onFailed(101, e2.getMessage());
                    if (j2 >= this.mEndTimeMills) {
                        release();
                        danmakuTimer = this.mTimer;
                        if (danmakuTimer != null) {
                        }
                    }
                }
                if (j2 >= this.mEndTimeMills) {
                    release();
                    danmakuTimer = this.mTimer;
                    if (danmakuTimer != null) {
                        danmakuTimer.update(this.mEndTimeMills);
                    }
                    onFrameAvailableListener.onFramesFinished(j2);
                }
            } catch (Throwable th) {
                if (j2 >= this.mEndTimeMills) {
                    release();
                    DanmakuTimer danmakuTimer2 = this.mTimer;
                    if (danmakuTimer2 != null) {
                        danmakuTimer2.update(this.mEndTimeMills);
                    }
                    onFrameAvailableListener.onFramesFinished(j2);
                }
                throw th;
            }
        }
        this.mRequestRender = false;
        return 2L;
    }

    @Override // net.polyv.danmaku.controller.DrawHandler.Callback
    public void drawingFinished() {
    }

    public void getFrameAtTime(final int i2) {
        int i3 = this.mRetryCount;
        this.mRetryCount = i3 + 1;
        if (i3 > 5) {
            release();
            OnFrameAvailableListener onFrameAvailableListener = this.mOnFrameAvailableListener;
            if (onFrameAvailableListener != null) {
                onFrameAvailableListener.onFailed(100, "not prepared");
                return;
            }
            return;
        }
        if (!isPrepared()) {
            DrawHandler drawHandler = this.handler;
            if (drawHandler == null) {
                return;
            }
            drawHandler.postDelayed(new Runnable() { // from class: net.polyv.danmaku.ui.widget.FakeDanmakuView.1
                @Override // java.lang.Runnable
                public void run() {
                    FakeDanmakuView.this.getFrameAtTime(i2);
                }
            }, 1000L);
            return;
        }
        this.mFrameIntervalMills = 1000 / i2;
        setCallback(this);
        long jMax = Math.max(0L, this.mExpectBeginMills - ((getConfig().mDanmakuFactory.MAX_DANMAKU_DURATION * 3) / 2));
        this.mOuterTimer = new DanmakuTimer(jMax);
        start(jMax);
    }

    @Override // net.polyv.danmaku.ui.widget.DanmakuView, net.polyv.danmaku.controller.IDanmakuViewController
    public int getViewHeight() {
        return this.mHeight;
    }

    @Override // net.polyv.danmaku.ui.widget.DanmakuView, net.polyv.danmaku.controller.IDanmakuViewController
    public int getViewWidth() {
        return this.mWidth;
    }

    public void initBufferCanvas(int i2, int i3) {
        this.mBufferBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        this.mBufferCanvas = new Canvas(this.mBufferBitmap);
    }

    @Override // net.polyv.danmaku.ui.widget.DanmakuView, android.view.View, net.polyv.danmaku.controller.IDanmakuView
    public boolean isShown() {
        return true;
    }

    @Override // net.polyv.danmaku.ui.widget.DanmakuView, net.polyv.danmaku.controller.IDanmakuViewController
    public boolean isViewReady() {
        return true;
    }

    @Override // net.polyv.danmaku.ui.widget.DanmakuView, android.view.View
    public void onDraw(Canvas canvas) {
    }

    @Override // net.polyv.danmaku.ui.widget.DanmakuView, net.polyv.danmaku.controller.IDanmakuView
    public void prepare(BaseDanmakuParser baseDanmakuParser, DanmakuContext danmakuContext) {
        CustomParser customParser = new CustomParser(baseDanmakuParser, this.mBeginTimeMills, this.mEndTimeMills);
        try {
            DanmakuContext danmakuContext2 = (DanmakuContext) danmakuContext.clone();
            danmakuContext2.resetContext();
            int i2 = AlphaValue.MAX;
            danmakuContext2.transparency = i2;
            danmakuContext2.setDanmakuTransparency(danmakuContext.transparency / i2);
            danmakuContext2.mGlobalFlagValues.FILTER_RESET_FLAG = danmakuContext.mGlobalFlagValues.FILTER_RESET_FLAG;
            danmakuContext2.setDanmakuSync(null);
            danmakuContext2.unregisterAllConfigChangedCallbacks();
            danmakuContext2.mGlobalFlagValues.updateAll();
            danmakuContext = danmakuContext2;
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
        }
        danmakuContext.updateMethod = (byte) 1;
        OnFrameAvailableListener onFrameAvailableListener = this.mOnFrameAvailableListener;
        if (onFrameAvailableListener != null) {
            onFrameAvailableListener.onConfig(danmakuContext);
        }
        super.prepare(customParser, danmakuContext);
        this.handler.setIdleSleep(false);
        this.handler.enableNonBlockMode(true);
    }

    @Override // net.polyv.danmaku.controller.DrawHandler.Callback
    public void prepared() {
    }

    @Override // net.polyv.danmaku.ui.widget.DanmakuView, net.polyv.danmaku.controller.IDanmakuView
    public void release() {
        this.mIsRelease = true;
        super.release();
        this.mBufferBitmap = null;
    }

    public void setOnFrameAvailableListener(OnFrameAvailableListener onFrameAvailableListener) {
        this.mOnFrameAvailableListener = onFrameAvailableListener;
    }

    public void setTimeRange(long j2, long j3) {
        this.mExpectBeginMills = j2;
        this.mBeginTimeMills = Math.max(0L, j2 - 30000);
        this.mEndTimeMills = j3;
    }

    @Override // net.polyv.danmaku.controller.DrawHandler.Callback
    public void updateTimer(DanmakuTimer danmakuTimer) {
        this.mTimer = danmakuTimer;
        danmakuTimer.update(this.mOuterTimer.currMillisecond);
        this.mOuterTimer.add(this.mFrameIntervalMills);
        danmakuTimer.add(this.mFrameIntervalMills);
    }

    public FakeDanmakuView(Context context, int i2, int i3, float f2) {
        super(context);
        this.mFrameIntervalMills = 16L;
        this.mRetryCount = 0;
        this.mExpectBeginMills = 0L;
        this.mWidth = i2;
        this.mHeight = i3;
        this.mScale = f2;
        initBufferCanvas(i2, i3);
    }
}
