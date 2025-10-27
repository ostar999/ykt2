package com.tencent.rtmp.ui;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.tencent.liteav.k;
import com.tencent.liteav.renderer.TXCFocusIndicatorView;
import com.tencent.liteav.renderer.TXCGLSurfaceView;

/* loaded from: classes6.dex */
public class TXCloudVideoView extends FrameLayout implements View.OnTouchListener {
    private static final int FOCUS_AREA_SIZE_DP = 70;
    private static final String TAG = "TXCloudVideoView";
    private float mBottom;
    private k mCapture;
    private int mCaptureHeight;
    private int mCaptureWidth;
    private int mCurrentScale;
    protected TXDashBoard mDashBoard;
    private boolean mFocus;
    private int mFocusAreaSize;
    protected TXCFocusIndicatorView mFocusIndicatorView;
    protected Object mGLContext;
    protected TXCGLSurfaceView mGLSurfaceView;
    private float mLeft;
    private float mRight;
    private ScaleGestureDetector mScaleGestureDetector;
    private ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener;
    protected SurfaceView mSurfaceView;
    private float mTop;
    private a mTouchFocusRunnable;
    private String mUserId;
    protected TextureView mVideoView;
    private boolean mZoom;

    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private View f20763b;

        /* renamed from: c, reason: collision with root package name */
        private MotionEvent f20764c;

        private a() {
        }

        public void a(View view) {
            this.f20763b = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TXCloudVideoView.this.mCapture != null && TXCloudVideoView.this.mFocus) {
                TXCloudVideoView.this.mCapture.a(this.f20764c.getX() / this.f20763b.getWidth(), this.f20764c.getY() / this.f20763b.getHeight());
            }
            if (TXCloudVideoView.this.mFocus) {
                TXCloudVideoView.this.onTouchFocus((int) this.f20764c.getX(), (int) this.f20764c.getY());
            }
        }

        public void a(MotionEvent motionEvent) {
            this.f20764c = motionEvent;
        }
    }

    public TXCloudVideoView(Context context) {
        this(context, null);
    }

    private int clamp(int i2, int i3, int i4) {
        return i2 > i4 ? i4 : i2 < i3 ? i3 : i2;
    }

    private Rect getTouchRect(int i2, int i3, int i4, int i5, float f2) {
        TXCGLSurfaceView tXCGLSurfaceView;
        if (this.mFocusAreaSize == 0 && (tXCGLSurfaceView = this.mGLSurfaceView) != null) {
            this.mFocusAreaSize = (int) ((tXCGLSurfaceView.getResources().getDisplayMetrics().density * 70.0f) + 0.5f);
        }
        int iIntValue = Float.valueOf(this.mFocusAreaSize * f2).intValue();
        int i6 = iIntValue / 2;
        int iClamp = clamp(i2 - i6, 0, i4 - iIntValue);
        int iClamp2 = clamp(i3 - i6, 0, i5 - iIntValue);
        return new Rect(iClamp, iClamp2, iClamp + iIntValue, iIntValue + iClamp2);
    }

    public static int px2dip(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void updateDbMargin() {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.a((int) this.mLeft, (int) this.mTop, (int) this.mRight, (int) this.mBottom);
        }
    }

    public void addVideoView(TXCGLSurfaceView tXCGLSurfaceView) {
        TXCGLSurfaceView tXCGLSurfaceView2 = this.mGLSurfaceView;
        if (tXCGLSurfaceView2 != null) {
            removeView(tXCGLSurfaceView2);
        }
        this.mGLSurfaceView = tXCGLSurfaceView;
        addView(tXCGLSurfaceView);
        resetLogView();
    }

    public void appendEventInfo(String str) {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.a(str);
        }
    }

    public void clearLastFrame(boolean z2) {
        if (z2) {
            setVisibility(8);
        }
    }

    public void clearLog() {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.a();
        }
    }

    public void disableLog(boolean z2) {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.a(z2);
        }
    }

    public TXCGLSurfaceView getGLSurfaceView() {
        return this.mGLSurfaceView;
    }

    public TextureView getHWVideoView() {
        return this.mVideoView;
    }

    public Object getOpenGLContext() {
        return this.mGLContext;
    }

    public SurfaceView getSurfaceView() {
        return this.mSurfaceView;
    }

    public String getUserId() {
        return this.mUserId;
    }

    public TextureView getVideoView() {
        return this.mVideoView;
    }

    public void onDestroy() {
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        super.onLayout(z2, i2, i3, i4, i5);
        updateDbMargin();
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.setStatusTextSize((float) (px2dip(getContext(), getWidth()) / 30.0d));
            this.mDashBoard.setEventTextSize((float) (px2dip(getContext(), getWidth()) / 25.0d));
        }
        int i7 = this.mCaptureWidth;
        if (i7 == 0 || (i6 = this.mCaptureHeight) == 0) {
            return;
        }
        updateVideoViewSize(i7, i6);
    }

    public void onPause() {
    }

    public void onResume() {
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() == 1 && motionEvent.getAction() == 0) {
            this.mTouchFocusRunnable.a(view);
            this.mTouchFocusRunnable.a(motionEvent);
            postDelayed(this.mTouchFocusRunnable, 100L);
        } else if (motionEvent.getPointerCount() > 1 && motionEvent.getAction() == 2) {
            removeCallbacks(this.mTouchFocusRunnable);
            onTouchFocus(-1, -1);
            ScaleGestureDetector scaleGestureDetector = this.mScaleGestureDetector;
            if (scaleGestureDetector != null && this.mZoom) {
                scaleGestureDetector.onTouchEvent(motionEvent);
            }
        }
        if (this.mZoom && motionEvent.getAction() == 0) {
            performClick();
        }
        return this.mZoom;
    }

    public void onTouchFocus(int i2, int i3) {
        if (this.mGLSurfaceView == null) {
            return;
        }
        if (i2 < 0 || i3 < 0) {
            TXCFocusIndicatorView tXCFocusIndicatorView = this.mFocusIndicatorView;
            if (tXCFocusIndicatorView != null) {
                tXCFocusIndicatorView.setVisibility(8);
                return;
            }
            return;
        }
        TXCFocusIndicatorView tXCFocusIndicatorView2 = this.mFocusIndicatorView;
        if (tXCFocusIndicatorView2 == null) {
            TXCFocusIndicatorView tXCFocusIndicatorView3 = new TXCFocusIndicatorView(getContext());
            this.mFocusIndicatorView = tXCFocusIndicatorView3;
            tXCFocusIndicatorView3.setVisibility(0);
            addView(this.mFocusIndicatorView);
        } else if (indexOfChild(tXCFocusIndicatorView2) != getChildCount() - 1) {
            removeView(this.mFocusIndicatorView);
            addView(this.mFocusIndicatorView);
        }
        Rect touchRect = getTouchRect(i2, i3, this.mGLSurfaceView.getWidth(), this.mGLSurfaceView.getHeight(), 1.0f);
        TXCFocusIndicatorView tXCFocusIndicatorView4 = this.mFocusIndicatorView;
        int i4 = touchRect.left;
        tXCFocusIndicatorView4.show(i4, touchRect.top, touchRect.right - i4);
    }

    public void removeFocusIndicatorView() {
        TXCFocusIndicatorView tXCFocusIndicatorView = this.mFocusIndicatorView;
        if (tXCFocusIndicatorView != null) {
            removeView(tXCFocusIndicatorView);
            this.mFocusIndicatorView = null;
        }
    }

    public void removeVideoView() {
        TextureView textureView = this.mVideoView;
        if (textureView != null) {
            removeView(textureView);
            this.mVideoView = null;
        }
        TXCGLSurfaceView tXCGLSurfaceView = this.mGLSurfaceView;
        if (tXCGLSurfaceView != null) {
            removeView(tXCGLSurfaceView);
            this.mGLSurfaceView = null;
        }
        this.mSurfaceView = null;
    }

    public void resetLogView() {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            removeView(tXDashBoard);
            addView(this.mDashBoard);
        }
    }

    public void setDashBoardStatusInfo(CharSequence charSequence) {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.a(charSequence);
        }
    }

    public void setLogMargin(float f2, float f3, float f4, float f5) {
        this.mLeft = f2;
        this.mRight = f3;
        this.mTop = f4;
        this.mBottom = f5;
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.a((int) f2, (int) f4, (int) f3, (int) f5);
        }
    }

    public void setLogMarginRatio(final float f2, final float f3, final float f4, final float f5) {
        getWidth();
        getHeight();
        postDelayed(new Runnable() { // from class: com.tencent.rtmp.ui.TXCloudVideoView.1
            @Override // java.lang.Runnable
            public void run() {
                TXCloudVideoView.this.mLeft = r0.getWidth() * f2;
                TXCloudVideoView.this.mRight = r0.getWidth() * f3;
                TXCloudVideoView.this.mTop = r0.getHeight() * f4;
                TXCloudVideoView.this.mBottom = r0.getHeight() * f5;
                TXCloudVideoView tXCloudVideoView = TXCloudVideoView.this;
                TXDashBoard tXDashBoard = tXCloudVideoView.mDashBoard;
                if (tXDashBoard != null) {
                    tXDashBoard.a((int) tXCloudVideoView.mLeft, (int) TXCloudVideoView.this.mTop, (int) TXCloudVideoView.this.mRight, (int) TXCloudVideoView.this.mBottom);
                }
            }
        }, 100L);
    }

    public void setLogText(Bundle bundle, Bundle bundle2, int i2) {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.a(bundle, bundle2, i2);
        }
    }

    public void setMirror(boolean z2) {
    }

    public void setOpenGLContext(Object obj) {
        this.mGLContext = obj;
    }

    public void setRenderMode(int i2) {
    }

    public void setRenderRotation(int i2) {
    }

    public void setUserId(String str) {
        this.mUserId = str;
    }

    public void showLog(boolean z2) {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.setShowLevel(z2 ? 2 : 0);
        }
    }

    public void showVideoDebugLog(int i2) {
        TXDashBoard tXDashBoard = this.mDashBoard;
        if (tXDashBoard != null) {
            tXDashBoard.setShowLevel(i2);
        }
    }

    public void start(boolean z2, boolean z3, k kVar) {
        this.mFocus = z2;
        this.mZoom = z3;
        if (z2 || z3) {
            setOnTouchListener(this);
            this.mCapture = kVar;
        }
        TXCGLSurfaceView tXCGLSurfaceView = this.mGLSurfaceView;
        if (tXCGLSurfaceView != null) {
            tXCGLSurfaceView.setVisibility(0);
        }
    }

    public void stop(boolean z2) {
        TXCGLSurfaceView tXCGLSurfaceView;
        if (this.mFocus || this.mZoom) {
            setOnTouchListener(null);
        }
        this.mCapture = null;
        if (!z2 || (tXCGLSurfaceView = this.mGLSurfaceView) == null) {
            return;
        }
        tXCGLSurfaceView.setVisibility(8);
    }

    public void updateVideoViewSize(int i2, int i3) {
        int height;
        FrameLayout.LayoutParams layoutParams;
        View view = this.mGLSurfaceView;
        if (view == null && (view = this.mVideoView) == null) {
            return;
        }
        int width = getWidth();
        int height2 = getHeight();
        if (i3 == 0 || height2 == 0) {
            return;
        }
        this.mCaptureWidth = i2;
        this.mCaptureHeight = i3;
        float f2 = (i2 * 1.0f) / i3;
        float f3 = width;
        float f4 = height2;
        int width2 = 0;
        if (f2 > (1.0f * f3) / f4) {
            height2 = (int) (f3 / f2);
            height = (getHeight() - height2) / 2;
        } else {
            width = (int) (f4 * f2);
            width2 = (getWidth() - width) / 2;
            height = 0;
        }
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 != null) {
            layoutParams = (FrameLayout.LayoutParams) layoutParams2;
            if (layoutParams.width == width && layoutParams.height == height2) {
                return;
            }
            layoutParams.width = width;
            layoutParams.height = height2;
        } else {
            layoutParams = new FrameLayout.LayoutParams(width, height2);
        }
        layoutParams.leftMargin = width2;
        layoutParams.topMargin = height;
        view.setLayoutParams(layoutParams);
    }

    public TXCloudVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCaptureWidth = 0;
        this.mCaptureHeight = 0;
        this.mLeft = 0.0f;
        this.mRight = 0.0f;
        this.mTop = 0.0f;
        this.mBottom = 0.0f;
        this.mFocusAreaSize = 0;
        this.mUserId = "";
        this.mFocus = false;
        this.mZoom = false;
        this.mCurrentScale = 1;
        this.mScaleGestureDetector = null;
        this.mScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.tencent.rtmp.ui.TXCloudVideoView.2
            /* JADX WARN: Removed duplicated region for block: B:11:0x0039 A[PHI: r3
              0x0039: PHI (r3v13 float) = (r3v2 float), (r3v14 float) binds: [B:15:0x0050, B:10:0x0037] A[DONT_GENERATE, DONT_INLINE]] */
            /* JADX WARN: Removed duplicated region for block: B:19:0x0067  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x0077  */
            /* JADX WARN: Removed duplicated region for block: B:30:0x007b  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
            /* JADX WARN: Removed duplicated region for block: B:36:0x008f  */
            /* JADX WARN: Removed duplicated region for block: B:43:0x00ae  */
            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onScale(android.view.ScaleGestureDetector r6) {
                /*
                    r5 = this;
                    com.tencent.rtmp.ui.TXCloudVideoView r0 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    com.tencent.liteav.k r0 = com.tencent.rtmp.ui.TXCloudVideoView.access$400(r0)
                    r1 = 0
                    if (r0 == 0) goto L14
                    com.tencent.rtmp.ui.TXCloudVideoView r0 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    com.tencent.liteav.k r0 = com.tencent.rtmp.ui.TXCloudVideoView.access$400(r0)
                    int r0 = r0.e()
                    goto L15
                L14:
                    r0 = r1
                L15:
                    if (r0 <= 0) goto Lbd
                    float r6 = r6.getScaleFactor()
                    r2 = 1065353216(0x3f800000, float:1.0)
                    int r3 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                    r4 = 1045220557(0x3e4ccccd, float:0.2)
                    if (r3 <= 0) goto L3b
                    float r6 = (float) r0
                    float r4 = r4 / r6
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r6 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r6)
                    int r6 = r0 - r6
                    float r6 = (float) r6
                    float r4 = r4 * r6
                    float r6 = r4 + r2
                    r3 = 1066192077(0x3f8ccccd, float:1.1)
                    int r4 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
                    if (r4 > 0) goto L53
                L39:
                    r6 = r3
                    goto L53
                L3b:
                    int r3 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                    if (r3 >= 0) goto L53
                    float r6 = (float) r0
                    float r4 = r4 / r6
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r6 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r6)
                    float r6 = (float) r6
                    float r4 = r4 * r6
                    float r6 = r2 - r4
                    r3 = 1063675494(0x3f666666, float:0.9)
                    int r4 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
                    if (r4 < 0) goto L53
                    goto L39
                L53:
                    com.tencent.rtmp.ui.TXCloudVideoView r3 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r3 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r3)
                    float r3 = (float) r3
                    float r3 = r3 * r6
                    int r3 = java.lang.Math.round(r3)
                    com.tencent.rtmp.ui.TXCloudVideoView r4 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r4 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r4)
                    if (r3 != r4) goto L74
                    int r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                    if (r4 <= 0) goto L6e
                    int r3 = r3 + 1
                    goto L74
                L6e:
                    int r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                    if (r4 >= 0) goto L74
                    int r3 = r3 + (-1)
                L74:
                    if (r3 < r0) goto L77
                    goto L78
                L77:
                    r0 = r3
                L78:
                    r3 = 1
                    if (r0 > r3) goto L7c
                    r0 = r3
                L7c:
                    int r3 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                    if (r3 <= 0) goto L8f
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r6 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r6)
                    if (r0 >= r6) goto La1
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r0 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r6)
                    goto La1
                L8f:
                    int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                    if (r6 >= 0) goto La1
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r6 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r6)
                    if (r0 <= r6) goto La1
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r0 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r6)
                La1:
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    com.tencent.rtmp.ui.TXCloudVideoView.access$502(r6, r0)
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    com.tencent.liteav.k r6 = com.tencent.rtmp.ui.TXCloudVideoView.access$400(r6)
                    if (r6 == 0) goto Lbd
                    com.tencent.rtmp.ui.TXCloudVideoView r6 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    com.tencent.liteav.k r6 = com.tencent.rtmp.ui.TXCloudVideoView.access$400(r6)
                    com.tencent.rtmp.ui.TXCloudVideoView r0 = com.tencent.rtmp.ui.TXCloudVideoView.this
                    int r0 = com.tencent.rtmp.ui.TXCloudVideoView.access$500(r0)
                    r6.a(r0)
                Lbd:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.ui.TXCloudVideoView.AnonymousClass2.onScale(android.view.ScaleGestureDetector):boolean");
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }
        };
        this.mTouchFocusRunnable = new a();
        this.mDashBoard = new TXDashBoard(context);
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this.mScaleGestureListener);
    }

    public void addVideoView(TextureView textureView) {
        TextureView textureView2 = this.mVideoView;
        if (textureView2 != null) {
            removeView(textureView2);
        }
        this.mVideoView = textureView;
        addView(textureView);
        resetLogView();
    }

    public TXCloudVideoView(SurfaceView surfaceView) {
        this(surfaceView.getContext(), null);
        this.mSurfaceView = surfaceView;
    }
}
