package com.easefun.polyv.mediasdk.example.widget.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.mediasdk.example.widget.media.IRenderView;
import com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView;
import com.easefun.polyv.mediasdk.gifmaker.GifMaker;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.ISurfaceTextureHolder;
import com.easefun.polyv.mediasdk.player.ISurfaceTextureHost;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TargetApi(14)
/* loaded from: classes3.dex */
public class TextureRenderView extends TextureView implements IRenderView {
    private static final int STAGE_CANCEL = 3;
    private static final int STAGE_DEFAULT = 0;
    private static final int STAGE_START = 1;
    private static final int STAGE_STOP = 2;
    private static final String TAG = "TextureRenderView";
    private static final int pw = 420;
    private Bitmap bitmap;
    private List<Bitmap> bitmaps;
    private int clipStage;
    private int count;
    private GifMaker gifMaker;
    private Handler handler;
    private IRenderView.IMeasureCallback iMeasureCallback;
    private MeasureHelper mMeasureHelper;
    private IMediaPlayer mMediaPlayer;
    private SurfaceCallback mSurfaceCallback;
    private int sr;
    private IjkVideoView.OnSurfaceUpdateListener surfaceUpdateListener;
    private int vh;
    private int vw;

    public static final class InternalSurfaceHolder implements IRenderView.ISurfaceHolder {
        private Surface mSurface;
        private SurfaceTexture mSurfaceTexture;
        private ISurfaceTextureHost mSurfaceTextureHost;
        private TextureRenderView mTextureView;

        public InternalSurfaceHolder(@NonNull TextureRenderView textureRenderView, @Nullable SurfaceTexture surfaceTexture, @NonNull ISurfaceTextureHost iSurfaceTextureHost) {
            this.mTextureView = textureRenderView;
            this.mSurfaceTexture = surfaceTexture;
            this.mSurfaceTextureHost = iSurfaceTextureHost;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @TargetApi(16)
        public void bindToMediaPlayer(IMediaPlayer iMediaPlayer) {
            if (iMediaPlayer == null) {
                return;
            }
            if (!(iMediaPlayer instanceof ISurfaceTextureHolder)) {
                iMediaPlayer.setSurface(openSurface());
                return;
            }
            ISurfaceTextureHolder iSurfaceTextureHolder = (ISurfaceTextureHolder) iMediaPlayer;
            this.mTextureView.mSurfaceCallback.setOwnSurfaceTexture(false);
            SurfaceTexture surfaceTexture = iSurfaceTextureHolder.getSurfaceTexture();
            if (surfaceTexture != null) {
                this.mTextureView.setSurfaceTexture(surfaceTexture);
            } else {
                iSurfaceTextureHolder.setSurfaceTexture(this.mSurfaceTexture);
                iSurfaceTextureHolder.setSurfaceTextureHost(this.mTextureView.mSurfaceCallback);
            }
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @NonNull
        public IRenderView getRenderView() {
            return this.mTextureView;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        public Surface getSurface() {
            Surface surface = this.mSurface;
            if (surface != null) {
                return surface;
            }
            Surface surface2 = new Surface(this.mSurfaceTexture);
            this.mSurface = surface2;
            return surface2;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public SurfaceHolder getSurfaceHolder() {
            return null;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public SurfaceTexture getSurfaceTexture() {
            return this.mSurfaceTexture;
        }

        @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.ISurfaceHolder
        @Nullable
        public Surface openSurface() {
            if (this.mSurfaceTexture == null) {
                return null;
            }
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
            }
            Surface surface2 = new Surface(this.mSurfaceTexture);
            this.mSurface = surface2;
            return surface2;
        }
    }

    public final class SurfaceCallback implements TextureView.SurfaceTextureListener, ISurfaceTextureHost {
        private int mHeight;
        private boolean mIsFormatChanged;
        private SurfaceTexture mSurfaceTexture;
        private WeakReference<TextureRenderView> mWeakRenderView;
        private int mWidth;
        private boolean mOwnSurfaceTexture = true;
        private boolean mWillDetachFromWindow = false;
        private boolean mDidDetachFromWindow = false;
        private Map<IRenderView.IRenderCallback, Object> mRenderCallbackMap = new ConcurrentHashMap();

        public SurfaceCallback(@NonNull TextureRenderView textureRenderView) {
            this.mWeakRenderView = new WeakReference<>(textureRenderView);
        }

        public void addRenderCallback(@NonNull IRenderView.IRenderCallback iRenderCallback) {
            InternalSurfaceHolder internalSurfaceHolder;
            this.mRenderCallbackMap.put(iRenderCallback, iRenderCallback);
            if (this.mSurfaceTexture != null) {
                internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get(), this.mSurfaceTexture, this);
                iRenderCallback.onSurfaceCreated(internalSurfaceHolder, this.mWidth, this.mHeight);
            } else {
                internalSurfaceHolder = null;
            }
            if (this.mIsFormatChanged) {
                if (internalSurfaceHolder == null) {
                    internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get(), this.mSurfaceTexture, this);
                }
                iRenderCallback.onSurfaceChanged(internalSurfaceHolder, 0, this.mWidth, this.mHeight);
            }
        }

        public void didDetachFromWindow() {
            Log.d(TextureRenderView.TAG, "didDetachFromWindow()");
            this.mDidDetachFromWindow = true;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
            this.mSurfaceTexture = surfaceTexture;
            this.mIsFormatChanged = false;
            this.mWidth = 0;
            this.mHeight = 0;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get(), surfaceTexture, this);
            Iterator<IRenderView.IRenderCallback> it = this.mRenderCallbackMap.keySet().iterator();
            while (it.hasNext()) {
                it.next().onSurfaceCreated(internalSurfaceHolder, 0, 0);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            this.mSurfaceTexture = surfaceTexture;
            this.mIsFormatChanged = false;
            this.mWidth = 0;
            this.mHeight = 0;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get(), surfaceTexture, this);
            Iterator<IRenderView.IRenderCallback> it = this.mRenderCallbackMap.keySet().iterator();
            while (it.hasNext()) {
                it.next().onSurfaceDestroyed(internalSurfaceHolder);
            }
            Log.d(TextureRenderView.TAG, "onSurfaceTextureDestroyed: destroy: " + this.mOwnSurfaceTexture);
            return this.mOwnSurfaceTexture;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
            this.mSurfaceTexture = surfaceTexture;
            this.mIsFormatChanged = true;
            this.mWidth = i2;
            this.mHeight = i3;
            InternalSurfaceHolder internalSurfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get(), surfaceTexture, this);
            Iterator<IRenderView.IRenderCallback> it = this.mRenderCallbackMap.keySet().iterator();
            while (it.hasNext()) {
                it.next().onSurfaceChanged(internalSurfaceHolder, 0, i2, i3);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            if (TextureRenderView.this.surfaceUpdateListener != null) {
                TextureRenderView.this.surfaceUpdateListener.onUpdate();
            }
        }

        @Override // com.easefun.polyv.mediasdk.player.ISurfaceTextureHost
        public void releaseSurfaceTexture(SurfaceTexture surfaceTexture) {
        }

        public void removeRenderCallback(@NonNull IRenderView.IRenderCallback iRenderCallback) {
            this.mRenderCallbackMap.remove(iRenderCallback);
        }

        public void setOwnSurfaceTexture(boolean z2) {
            this.mOwnSurfaceTexture = z2;
        }

        public void willDetachFromWindow() {
            Log.d(TextureRenderView.TAG, "willDetachFromWindow()");
            this.mWillDetachFromWindow = true;
        }
    }

    public TextureRenderView(Context context) {
        super(context);
        this.clipStage = 0;
        this.bitmaps = new ArrayList();
        this.gifMaker = new GifMaker();
        this.handler = new Handler() { // from class: com.easefun.polyv.mediasdk.example.widget.media.TextureRenderView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 3) {
                    TextureRenderView.this.cancelClip();
                }
            }
        };
        initView(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clear() throws IOException {
        this.count = 0;
        this.handler.removeMessages(3);
        GifMaker gifMaker = this.gifMaker;
        if (gifMaker != null) {
            gifMaker.cancel();
        }
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmap.recycle();
            this.bitmap = null;
        }
        for (Bitmap bitmap2 : this.bitmaps) {
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                bitmap2.recycle();
            }
        }
        this.bitmaps.clear();
        this.mMediaPlayer = null;
        System.gc();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float getVideoOutputFramesPerSecond() {
        /*
            r2 = this;
            com.easefun.polyv.mediasdk.player.IMediaPlayer r0 = r2.mMediaPlayer
            boolean r1 = r0 instanceof com.easefun.polyv.mediasdk.player.IjkMediaPlayer
            if (r1 == 0) goto L9
            com.easefun.polyv.mediasdk.player.IjkMediaPlayer r0 = (com.easefun.polyv.mediasdk.player.IjkMediaPlayer) r0
            goto L1b
        L9:
            boolean r1 = r0 instanceof com.easefun.polyv.mediasdk.player.MediaPlayerProxy
            if (r1 == 0) goto L1a
            com.easefun.polyv.mediasdk.player.MediaPlayerProxy r0 = (com.easefun.polyv.mediasdk.player.MediaPlayerProxy) r0
            com.easefun.polyv.mediasdk.player.IMediaPlayer r0 = r0.getInternalMediaPlayer()
            boolean r1 = r0 instanceof com.easefun.polyv.mediasdk.player.IjkMediaPlayer
            if (r1 == 0) goto L1a
            com.easefun.polyv.mediasdk.player.IjkMediaPlayer r0 = (com.easefun.polyv.mediasdk.player.IjkMediaPlayer) r0
            goto L1b
        L1a:
            r0 = 0
        L1b:
            if (r0 == 0) goto L22
            float r0 = r0.getVideoOutputFramesPerSecond()
            return r0
        L22:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.mediasdk.example.widget.media.TextureRenderView.getVideoOutputFramesPerSecond():float");
    }

    private void initView(Context context) {
        this.mMeasureHelper = new MeasureHelper(this);
        SurfaceCallback surfaceCallback = new SurfaceCallback(this);
        this.mSurfaceCallback = surfaceCallback;
        setSurfaceTextureListener(surfaceCallback);
    }

    private void makeGif(final GifMaker.OnGifListener onGifListener) {
        new Thread(new Runnable() { // from class: com.easefun.polyv.mediasdk.example.widget.media.TextureRenderView.2
            @Override // java.lang.Runnable
            public void run() throws IOException {
                if (TextureRenderView.this.clipStage == 3) {
                    GifMaker.OnGifListener onGifListener2 = onGifListener;
                    if (onGifListener2 != null) {
                        onGifListener2.onError(new Exception("Has been cancelled"));
                    }
                    TextureRenderView.this.clear();
                    return;
                }
                TextureRenderView.this.gifMaker.setOnGifListener(new GifMaker.OnGifListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.TextureRenderView.2.1
                    @Override // com.easefun.polyv.mediasdk.gifmaker.GifMaker.OnGifListener
                    public void onError(Throwable th) throws IOException {
                        GifMaker.OnGifListener onGifListener3 = onGifListener;
                        if (onGifListener3 != null) {
                            onGifListener3.onError(th);
                        }
                        TextureRenderView.this.clear();
                    }

                    @Override // com.easefun.polyv.mediasdk.gifmaker.GifMaker.OnGifListener
                    public void onFinish(byte[] bArr, int i2, int i3, int i4) throws IOException {
                        GifMaker.OnGifListener onGifListener3 = onGifListener;
                        if (onGifListener3 != null) {
                            onGifListener3.onFinish(bArr, i2, i3, i4);
                        }
                        TextureRenderView.this.clear();
                    }

                    @Override // com.easefun.polyv.mediasdk.gifmaker.GifMaker.OnGifListener
                    public void onMake(int i2, int i3, int i4) {
                        GifMaker.OnGifListener onGifListener3 = onGifListener;
                        if (onGifListener3 != null) {
                            onGifListener3.onMake(i2, i3, i4);
                        }
                    }
                });
                if (TextureRenderView.this.bitmaps.size() != 0) {
                    TextureRenderView.this.gifMaker.makeGif(TextureRenderView.this.bitmaps, TextureRenderView.this.getScaleX(), TextureRenderView.this.getScaleY(), TextureRenderView.this.getRotation());
                    return;
                }
                GifMaker.OnGifListener onGifListener3 = onGifListener;
                if (onGifListener3 != null) {
                    onGifListener3.onError(new Exception("Convert image number is 0"));
                }
                TextureRenderView.this.clear();
            }
        }).start();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void addRenderCallback(IRenderView.IRenderCallback iRenderCallback) {
        this.mSurfaceCallback.addRenderCallback(iRenderCallback);
    }

    public void cancelClip() {
        this.clipStage = 3;
        clear();
    }

    public IRenderView.ISurfaceHolder getSurfaceHolder() {
        return new InternalSurfaceHolder(this, this.mSurfaceCallback.mSurfaceTexture, this.mSurfaceCallback);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public View getView() {
        return this;
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        this.mSurfaceCallback.willDetachFromWindow();
        super.onDetachedFromWindow();
        this.mSurfaceCallback.didDetachFromWindow();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(TextureRenderView.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(TextureRenderView.class.getName());
    }

    @Override // android.view.View
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

    public void setMirror(boolean z2) {
        if (getRotation() == 90.0f || getRotation() == 270.0f) {
            setScaleY(z2 ? -1.0f : 1.0f);
        } else {
            setScaleX(z2 ? -1.0f : 1.0f);
        }
    }

    public void setOnSurfaceUpdateListener(IjkVideoView.OnSurfaceUpdateListener onSurfaceUpdateListener) {
        this.surfaceUpdateListener = onSurfaceUpdateListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public void setVideoRotation(int i2) {
        this.mMeasureHelper.setVideoRotation(i2);
        setRotation(i2);
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
        requestLayout();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView
    public boolean shouldWaitForResize() {
        return false;
    }

    public void startClip(int i2, int i3, int i4, int i5, IMediaPlayer iMediaPlayer) {
        cancelClip();
        this.vw = i3;
        this.vh = i4;
        this.sr = i5;
        this.mMediaPlayer = iMediaPlayer;
        this.clipStage = 1;
        int iMin = Math.min(8, i2);
        this.handler.removeMessages(3);
        this.handler.sendEmptyMessageDelayed(3, iMin * 1000);
    }

    public void stopClip(GifMaker.OnGifListener onGifListener) {
        this.clipStage = 2;
        this.handler.removeMessages(3);
        makeGif(onGifListener);
    }

    public TextureRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.clipStage = 0;
        this.bitmaps = new ArrayList();
        this.gifMaker = new GifMaker();
        this.handler = new Handler() { // from class: com.easefun.polyv.mediasdk.example.widget.media.TextureRenderView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 3) {
                    TextureRenderView.this.cancelClip();
                }
            }
        };
        initView(context);
    }

    public TextureRenderView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.clipStage = 0;
        this.bitmaps = new ArrayList();
        this.gifMaker = new GifMaker();
        this.handler = new Handler() { // from class: com.easefun.polyv.mediasdk.example.widget.media.TextureRenderView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 3) {
                    TextureRenderView.this.cancelClip();
                }
            }
        };
        initView(context);
    }

    @TargetApi(21)
    public TextureRenderView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.clipStage = 0;
        this.bitmaps = new ArrayList();
        this.gifMaker = new GifMaker();
        this.handler = new Handler() { // from class: com.easefun.polyv.mediasdk.example.widget.media.TextureRenderView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 3) {
                    TextureRenderView.this.cancelClip();
                }
            }
        };
        initView(context);
    }
}
