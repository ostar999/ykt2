package com.easefun.polyv.mediasdk.example.widget.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.mediasdk.example.widget.media.IRenderView;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.ISurfaceTextureHolder;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class SurfaceRenderView extends SurfaceView implements IRenderView {
    private IRenderView.IMeasureCallback iMeasureCallback;
    private MeasureHelper mMeasureHelper;
    private SurfaceCallback mSurfaceCallback;

    public static final class InternalSurfaceHolder implements IRenderView.ISurfaceHolder {
        private SurfaceHolder mSurfaceHolder;
        private SurfaceRenderView mSurfaceView;

        public InternalSurfaceHolder(@NonNull SurfaceRenderView surfaceRenderView, @Nullable SurfaceHolder surfaceHolder) {
            this.mSurfaceView = surfaceRenderView;
            this.mSurfaceHolder = surfaceHolder;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        public void bindToMediaPlayer(IMediaPlayer iMediaPlayer) {
            if (iMediaPlayer != null) {
                if (iMediaPlayer instanceof ISurfaceTextureHolder) {
                    ((ISurfaceTextureHolder) iMediaPlayer).setSurfaceTexture(null);
                }
                iMediaPlayer.setDisplay(this.mSurfaceHolder);
            }
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @NonNull
        public IRenderView getRenderView() {
            return this.mSurfaceView;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        public Surface getSurface() {
            return null;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public SurfaceHolder getSurfaceHolder() {
            return this.mSurfaceHolder;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public SurfaceTexture getSurfaceTexture() {
            return null;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public Surface openSurface() {
            SurfaceHolder surfaceHolder = this.mSurfaceHolder;
            if (surfaceHolder == null) {
                return null;
            }
            return surfaceHolder.getSurface();
        }
    }

    public static final class SurfaceCallback implements SurfaceHolder.Callback {
        private int mFormat;
        private int mHeight;
        private boolean mIsFormatChanged;
        private Map<IRenderView.IRenderCallback, Object> mRenderCallbackMap = new ConcurrentHashMap();
        private SurfaceHolder mSurfaceHolder;
        private WeakReference<SurfaceRenderView> mWeakSurfaceView;
        private int mWidth;

        public SurfaceCallback(@NonNull SurfaceRenderView surfaceRenderView) {
            this.mWeakSurfaceView = new WeakReference<>(surfaceRenderView);
        }

        public void addRenderCallback(@NonNull IRenderView.IRenderCallback iRenderCallback) {
            InternalSurfaceHolder internalSurfaceHolder;
            this.mRenderCallbackMap.put(iRenderCallback, iRenderCallback);
            if (this.mSurfaceHolder != null) {
                internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
                iRenderCallback.onSurfaceCreated(internalSurfaceHolder, this.mWidth, this.mHeight);
            } else {
                internalSurfaceHolder = null;
            }
            if (this.mIsFormatChanged) {
                if (internalSurfaceHolder == null) {
                    internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
                }
                iRenderCallback.onSurfaceChanged(internalSurfaceHolder, this.mFormat, this.mWidth, this.mHeight);
            }
        }

        public void removeRenderCallback(@NonNull IRenderView.IRenderCallback iRenderCallback) {
            this.mRenderCallbackMap.remove(iRenderCallback);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
            this.mSurfaceHolder = surfaceHolder;
            this.mIsFormatChanged = true;
            this.mFormat = i2;
            this.mWidth = i3;
            this.mHeight = i4;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            Iterator<IRenderView.IRenderCallback> it = this.mRenderCallbackMap.keySet().iterator();
            while (it.hasNext()) {
                it.next().onSurfaceChanged(internalSurfaceHolder, i2, i3, i4);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            this.mSurfaceHolder = surfaceHolder;
            this.mIsFormatChanged = false;
            this.mFormat = 0;
            this.mWidth = 0;
            this.mHeight = 0;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            Iterator<IRenderView.IRenderCallback> it = this.mRenderCallbackMap.keySet().iterator();
            while (it.hasNext()) {
                it.next().onSurfaceCreated(internalSurfaceHolder, 0, 0);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            this.mSurfaceHolder = null;
            this.mIsFormatChanged = false;
            this.mFormat = 0;
            this.mWidth = 0;
            this.mHeight = 0;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            Iterator<IRenderView.IRenderCallback> it = this.mRenderCallbackMap.keySet().iterator();
            while (it.hasNext()) {
                it.next().onSurfaceDestroyed(internalSurfaceHolder);
            }
        }
    }

    public SurfaceRenderView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        this.mMeasureHelper = new MeasureHelper(this);
        this.mSurfaceCallback = new SurfaceCallback(this);
        getHolder().addCallback(this.mSurfaceCallback);
        getHolder().setType(0);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void addRenderCallback(IRenderView.IRenderCallback iRenderCallback) {
        this.mSurfaceCallback.addRenderCallback(iRenderCallback);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public View getView() {
        return this;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(SurfaceRenderView.class.getName());
    }

    @Override // android.view.View
    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(SurfaceRenderView.class.getName());
    }

    @Override // android.view.SurfaceView, android.view.View
    public void onMeasure(int i2, int i3) {
        this.mMeasureHelper.doMeasure(i2, i3);
        int measuredWidth = this.mMeasureHelper.getMeasuredWidth();
        int measuredHeight = this.mMeasureHelper.getMeasuredHeight();
        setMeasuredDimension(measuredWidth, measuredHeight);
        IRenderView.IMeasureCallback iMeasureCallback = this.iMeasureCallback;
        if (iMeasureCallback != null) {
            iMeasureCallback.onMeasure(measuredWidth, measuredHeight);
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void removeRenderCallback(IRenderView.IRenderCallback iRenderCallback) {
        this.mSurfaceCallback.removeRenderCallback(iRenderCallback);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void setAspectRatio(int i2) {
        this.mMeasureHelper.setAspectRatio(i2);
        requestLayout();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void setMeasureCallback(@NonNull IRenderView.IMeasureCallback iMeasureCallback) {
        this.iMeasureCallback = iMeasureCallback;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void setVideoRotation(int i2) {
        Log.e("", "SurfaceView doesn't support rotation (" + i2 + ")!\n");
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void setVideoSampleAspectRatio(int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            return;
        }
        this.mMeasureHelper.setVideoSampleAspectRatio(i2, i3);
        requestLayout();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void setVideoSize(int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            return;
        }
        this.mMeasureHelper.setVideoSize(i2, i3);
        getHolder().setFixedSize(i2, i3);
        requestLayout();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public boolean shouldWaitForResize() {
        return true;
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        initView(context);
    }

    @TargetApi(21)
    public SurfaceRenderView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        initView(context);
    }
}
