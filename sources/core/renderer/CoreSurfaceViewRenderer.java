package core.renderer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import c.h;
import c.l;
import core.interfaces.DataReceiver;
import core.interfaces.ScreenShot;
import j.d;
import java.nio.ByteBuffer;
import org.wrtca.api.EglBase;
import org.wrtca.api.EglRenderer;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.SurfaceViewRenderer;

/* loaded from: classes8.dex */
public class CoreSurfaceViewRenderer extends SurfaceViewRenderer implements RendererCommon.RendererEvents {
    private static final String TAG = "CoreSurfaceViewRenderer";
    public View.OnLayoutChangeListener mLayoutChangeListener;
    public Handler mMainHandler;
    public PositionChanged mPositionChangedListener;
    public int mVideoHeight;
    public int mVideoWidth;
    public boolean needFullScreen;

    public interface PositionChanged {
        void onPositionChanged(int i2);
    }

    public class SetScaleTypeRunnable implements Runnable {
        public RendererCommon.ScalingType renderscacletype;

        public SetScaleTypeRunnable(RendererCommon.ScalingType scalingType) {
            this.renderscacletype = scalingType;
        }

        @Override // java.lang.Runnable
        public void run() {
            CoreSurfaceViewRenderer coreSurfaceViewRenderer = CoreSurfaceViewRenderer.this;
            RendererCommon.ScalingType scalingType = this.renderscacletype;
            coreSurfaceViewRenderer.setScalingType(scalingType, scalingType);
        }
    }

    public CoreSurfaceViewRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMainHandler = null;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: core.renderer.CoreSurfaceViewRenderer.1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                h.a(CoreSurfaceViewRenderer.TAG, "onLayoutChange new width  " + (i4 - i2) + " from old width: " + (i8 - i6));
                h.a(CoreSurfaceViewRenderer.TAG, "onLayoutChange new height  " + (i5 - i3) + " from old height: " + (i9 - i7));
                CoreSurfaceViewRenderer.this.resetSurface();
            }
        };
        this.mLayoutChangeListener = onLayoutChangeListener;
        addOnLayoutChangeListener(onLayoutChangeListener);
    }

    public int getScaleType() {
        return this.mScaleType;
    }

    public void init() {
        super.init(d.d().o(), this, null);
    }

    public boolean isNeedFullScreen() {
        return this.needFullScreen;
    }

    @Override // org.wrtca.api.SurfaceViewRenderer, org.wrtca.api.RendererCommon.RendererEvents
    public void onFirstFrameRendered() {
        h.a(TAG, "onFirstFrameRendered in CoreSurfaceViewRenderer");
        if (this.mPeerConnectionCallBack != null) {
            h.a(TAG, "mPeerConnectionCallBack onFirstFrameRender");
            this.mPeerConnectionCallBack.onFirstFrameRender(this.mStreamInfo, this);
        }
        if (this.mFrameRendered != null) {
            h.a(TAG, "CoreSurfaceViewRendereronFirstFrameRendered" + this.mStreamInfo + "view: " + this);
            this.mFrameRendered.onFirstFrameRender(this.mStreamInfo, this);
        }
    }

    @Override // org.wrtca.api.SurfaceViewRenderer, org.wrtca.api.RendererCommon.RendererEvents
    public void onFrameResolutionChanged(final int i2, final int i3, int i4) {
        h.a(TAG, " onFrameResolutionChanged " + i2 + "*" + i3 + " rotation " + i4);
        Handler handler = this.mMainHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: core.renderer.CoreSurfaceViewRenderer.4
                @Override // java.lang.Runnable
                public void run() {
                    CoreSurfaceViewRenderer coreSurfaceViewRenderer = CoreSurfaceViewRenderer.this;
                    coreSurfaceViewRenderer.mVideoWidth = i2;
                    coreSurfaceViewRenderer.mVideoHeight = i3;
                    coreSurfaceViewRenderer.resetSurface();
                }
            });
        }
    }

    @Override // org.wrtca.api.SurfaceViewRenderer, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        super.onLayout(z2, i2, i3, i4, i5);
        if (getParent() != null && (getParent() instanceof ViewGroup) && this.mScaleType == 0) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup.getWidth() <= 0 || viewGroup.getHeight() <= 0) {
                return;
            }
            if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                i6 = ((ViewGroup.MarginLayoutParams) getLayoutParams()).leftMargin + ((ViewGroup.MarginLayoutParams) getLayoutParams()).rightMargin;
                i7 = ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin + ((ViewGroup.MarginLayoutParams) getLayoutParams()).bottomMargin;
            } else {
                i6 = 0;
                i7 = 0;
            }
            int height = (viewGroup.getHeight() - (viewGroup.getPaddingBottom() + viewGroup.getPaddingTop())) - i7;
            int width = (viewGroup.getWidth() - (viewGroup.getPaddingLeft() + viewGroup.getPaddingRight())) - i6;
            int i9 = i4 - i2;
            if (viewGroup.getWidth() != i9 || height <= getMeasuredHeight()) {
                if (viewGroup.getHeight() != i5 - i3 || width <= getMeasuredWidth() || i2 == (i8 = ((width - i9) / 2) + ((ViewGroup.MarginLayoutParams) getLayoutParams()).leftMargin)) {
                    return;
                }
                layout(i8, 0, i9 + i8, i5);
                return;
            }
            int i10 = i5 - i3;
            int i11 = ((height - i10) / 2) + ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin;
            if (i3 != i11) {
                layout(0, i11, i4, i10 + i11);
            }
        }
    }

    @Override // org.wrtca.api.SurfaceViewRenderer, android.view.SurfaceView, android.view.View
    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        super.onMeasure(i2, i3);
        h.a(TAG, this + " onMeasure mScaleType: " + this.mScaleType);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        h.a(TAG, "mVideoWidth: " + this.mVideoWidth + " mVideoHeight: " + this.mVideoHeight);
        h.a(TAG, "widthSpec.getSize: " + size + " heightSpec.getSize: " + size2);
        if ((getLayoutParams() instanceof ViewGroup.MarginLayoutParams) && this.mScaleType == 0) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            if (marginLayoutParams.topMargin != 0 || marginLayoutParams.bottomMargin != 0 || marginLayoutParams.leftMargin != 0 || marginLayoutParams.rightMargin != 0) {
                h.a(TAG, "call setLayoutParams() clear margins");
                marginLayoutParams.topMargin = 0;
                marginLayoutParams.bottomMargin = 0;
                marginLayoutParams.leftMargin = 0;
                marginLayoutParams.rightMargin = 0;
                setLayoutParams(marginLayoutParams);
                return;
            }
            if (size <= 0 || size2 <= 0 || (i4 = this.mVideoHeight) <= 0 || (i5 = this.mVideoWidth) <= 0) {
                return;
            }
            float fMin = Math.min(size / i5, size2 / i4);
            int i6 = (int) (this.mVideoWidth * fMin);
            int i7 = (int) (fMin * this.mVideoHeight);
            h.a(TAG, "Recalculate margins in onMeasure topBottomMargin: " + ((size2 - i7) / 2) + " leftRightMargin: " + ((size - i6) / 2));
            h.a(TAG, "adjust measureWidth: " + i6 + " measureHeight: " + i7);
            setMeasuredDimension(i6, i7);
        }
    }

    @Override // org.wrtca.api.SurfaceViewRenderer
    public void release() {
        super.release();
        if (this.mMainHandler != null) {
            this.mMainHandler = null;
        }
        removeOnLayoutChangeListener(this.mLayoutChangeListener);
        this.mLayoutChangeListener = null;
        setPositionChangedListener(null);
    }

    public void resetSurface() {
        int i2;
        int width;
        int paddingRight;
        int height;
        int paddingBottom;
        h.a(TAG, this + " resetSurface mScaleType: " + this.mScaleType);
        if (this.mVideoWidth == 0 || this.mVideoHeight == 0 || (i2 = this.mScaleType) == -1 || i2 == 1 || i2 == 2 || !(getParent() instanceof ViewGroup)) {
            return;
        }
        if (this.needFullScreen) {
            width = l.b(getContext()) - getPaddingLeft();
            paddingRight = getPaddingRight();
        } else {
            width = ((ViewGroup) getParent()).getWidth() - getPaddingLeft();
            paddingRight = getPaddingRight();
        }
        int i3 = width - paddingRight;
        if (this.needFullScreen) {
            height = l.a(getContext()) - getPaddingTop();
            paddingBottom = getPaddingBottom();
        } else {
            height = ((ViewGroup) getParent()).getHeight() - getPaddingTop();
            paddingBottom = getPaddingBottom();
        }
        int i4 = height - paddingBottom;
        h.a(TAG, " winWidth " + i3 + " winHeight " + i4);
        h.a(TAG, " mVideoWidth " + this.mVideoWidth + " mVideoHeight " + this.mVideoHeight);
        float fMin = Math.min(((float) i3) / ((float) this.mVideoWidth), ((float) i4) / ((float) this.mVideoHeight));
        int i5 = (int) (((float) this.mVideoWidth) * fMin);
        int i6 = (int) (fMin * ((float) this.mVideoHeight));
        int i7 = (i4 - i6) / 2;
        int i8 = (i3 - i5) / 2;
        h.a(TAG, " drawn width " + i5 + " drawn height " + i6 + " topBottomMargin " + i7 + " leftRightMargin " + i8);
        if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            h.a(TAG, "using new margins to call setLayoutParams and setMeasuredDimension");
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            marginLayoutParams.topMargin = i7;
            marginLayoutParams.bottomMargin = i7;
            marginLayoutParams.leftMargin = i8;
            marginLayoutParams.rightMargin = i8;
            setLayoutParams(marginLayoutParams);
            setMeasuredDimension(i5, i6);
        }
    }

    public void setNeedFullScreen(boolean z2) {
        this.needFullScreen = z2;
    }

    public void setPositionChangedListener(PositionChanged positionChanged) {
        this.mPositionChangedListener = positionChanged;
    }

    public void setScaleType(int i2) {
        if (i2 >= 0) {
            RendererCommon.ScalingType scalingType = RendererCommon.ScalingType.SCALE_ASPECT_FILL;
            if (i2 == 0) {
                scalingType = RendererCommon.ScalingType.SCALE_ASPECT_FIT;
            }
            this.mScaleType = i2;
            h.a(TAG, " renderview setScaleType" + i2);
            post(new SetScaleTypeRunnable(scalingType));
        }
    }

    public void setScreenShotBack(ScreenShot screenShot) {
        if (screenShot != null) {
            addFrameListener(new EglRenderer.FrameListener(screenShot) { // from class: core.renderer.CoreSurfaceViewRenderer.3
                public ScreenShot mScreenShot;
                public final /* synthetic */ ScreenShot val$callBack;

                {
                    this.val$callBack = screenShot;
                    this.mScreenShot = screenShot;
                }

                @Override // org.wrtca.api.EglRenderer.FrameListener
                public void onFrame(ByteBuffer byteBuffer, int i2, int i3) {
                    this.mScreenShot.onReceiveRGBAData(byteBuffer, i2, i3);
                }
            }, 1.0f);
        }
    }

    @Override // org.wrtca.api.SurfaceViewRenderer
    public void init(EglBase.Context context, RendererCommon.RendererEvents rendererEvents, DataReceiver dataReceiver) {
        h.a(TAG, "init egl surface view ");
        super.init(context, this, dataReceiver);
        RendererCommon.ScalingType scalingType = RendererCommon.ScalingType.SCALE_ASPECT_FIT;
        super.setScalingType(scalingType, scalingType);
    }

    public CoreSurfaceViewRenderer(Context context) {
        super(context);
        this.mMainHandler = null;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: core.renderer.CoreSurfaceViewRenderer.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                h.a(CoreSurfaceViewRenderer.TAG, "onLayoutChange new width  " + (i4 - i2) + " from old width: " + (i8 - i6));
                h.a(CoreSurfaceViewRenderer.TAG, "onLayoutChange new height  " + (i5 - i3) + " from old height: " + (i9 - i7));
                CoreSurfaceViewRenderer.this.resetSurface();
            }
        };
        this.mLayoutChangeListener = onLayoutChangeListener;
        addOnLayoutChangeListener(onLayoutChangeListener);
    }
}
