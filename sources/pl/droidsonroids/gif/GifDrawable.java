package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.widget.MediaController;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.ws.WebSocketProtocol;
import pl.droidsonroids.gif.transforms.CornerRadiusTransform;
import pl.droidsonroids.gif.transforms.Transform;

/* loaded from: classes9.dex */
public class GifDrawable extends Drawable implements Animatable, MediaController.MediaPlayerControl {
    final Bitmap mBuffer;
    private final Rect mDstRect;
    final ScheduledThreadPoolExecutor mExecutor;
    final InvalidationHandler mInvalidationHandler;
    final boolean mIsRenderingTriggeredOnDraw;
    volatile boolean mIsRunning;
    final ConcurrentLinkedQueue<AnimationListener> mListeners;
    final GifInfoHandle mNativeInfoHandle;
    long mNextFrameRenderTime;
    protected final Paint mPaint;
    private final RenderTask mRenderTask;
    ScheduledFuture<?> mRenderTaskSchedule;
    private int mScaledHeight;
    private int mScaledWidth;
    private final Rect mSrcRect;
    private ColorStateList mTint;
    private PorterDuffColorFilter mTintFilter;
    private PorterDuff.Mode mTintMode;
    private Transform mTransform;

    public GifDrawable(@NonNull Resources resources, @DrawableRes @RawRes int i2) throws Resources.NotFoundException, IOException {
        this(resources.openRawResourceFd(i2));
        float densityScale = GifViewUtils.getDensityScale(resources, i2);
        this.mScaledHeight = (int) (this.mNativeInfoHandle.getHeight() * densityScale);
        this.mScaledWidth = (int) (this.mNativeInfoHandle.getWidth() * densityScale);
    }

    private void cancelPendingRenderTask() {
        ScheduledFuture<?> scheduledFuture = this.mRenderTaskSchedule;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.mInvalidationHandler.removeMessages(-1);
    }

    @Nullable
    public static GifDrawable createFromResource(@NonNull Resources resources, @DrawableRes @RawRes int i2) {
        try {
            return new GifDrawable(resources, i2);
        } catch (IOException unused) {
            return null;
        }
    }

    private void scheduleNextRender() {
        if (this.mIsRenderingTriggeredOnDraw && this.mIsRunning) {
            long j2 = this.mNextFrameRenderTime;
            if (j2 != Long.MIN_VALUE) {
                long jMax = Math.max(0L, j2 - SystemClock.uptimeMillis());
                this.mNextFrameRenderTime = Long.MIN_VALUE;
                this.mExecutor.remove(this.mRenderTask);
                this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, jMax, TimeUnit.MILLISECONDS);
            }
        }
    }

    private void shutdown() {
        this.mIsRunning = false;
        this.mInvalidationHandler.removeMessages(-1);
        this.mNativeInfoHandle.recycle();
    }

    private PorterDuffColorFilter updateTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public void addAnimationListener(@NonNull AnimationListener animationListener) {
        this.mListeners.add(animationListener);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return getNumberOfFrames() > 1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return getNumberOfFrames() > 1;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        boolean z2;
        if (this.mTintFilter == null || this.mPaint.getColorFilter() != null) {
            z2 = false;
        } else {
            this.mPaint.setColorFilter(this.mTintFilter);
            z2 = true;
        }
        Transform transform = this.mTransform;
        if (transform == null) {
            canvas.drawBitmap(this.mBuffer, this.mSrcRect, this.mDstRect, this.mPaint);
        } else {
            transform.onDraw(canvas, this.mPaint, this.mBuffer);
        }
        if (z2) {
            this.mPaint.setColorFilter(null);
        }
    }

    public long getAllocationByteCount() {
        return this.mNativeInfoHandle.getAllocationByteCount() + this.mBuffer.getAllocationByteCount();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        return 100;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Nullable
    public String getComment() {
        return this.mNativeInfoHandle.getComment();
    }

    @FloatRange(from = 0.0d)
    public float getCornerRadius() {
        Transform transform = this.mTransform;
        if (transform instanceof CornerRadiusTransform) {
            return ((CornerRadiusTransform) transform).getCornerRadius();
        }
        return 0.0f;
    }

    public Bitmap getCurrentFrame() {
        Bitmap bitmap = this.mBuffer;
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), this.mBuffer.isMutable());
        bitmapCopy.setHasAlpha(this.mBuffer.hasAlpha());
        return bitmapCopy;
    }

    public int getCurrentFrameIndex() {
        return this.mNativeInfoHandle.getCurrentFrameIndex();
    }

    public int getCurrentLoop() {
        int currentLoop = this.mNativeInfoHandle.getCurrentLoop();
        return (currentLoop == 0 || currentLoop < this.mNativeInfoHandle.getLoopCount()) ? currentLoop : currentLoop - 1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        return this.mNativeInfoHandle.getCurrentPosition();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        return this.mNativeInfoHandle.getDuration();
    }

    @NonNull
    public GifError getError() {
        return GifError.fromCode(this.mNativeInfoHandle.getNativeErrorCode());
    }

    public int getFrameByteCount() {
        return this.mBuffer.getRowBytes() * this.mBuffer.getHeight();
    }

    public int getFrameDuration(@IntRange(from = 0) int i2) {
        return this.mNativeInfoHandle.getFrameDuration(i2);
    }

    public long getInputSourceByteCount() {
        return this.mNativeInfoHandle.getSourceLength();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mScaledHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mScaledWidth;
    }

    public int getLoopCount() {
        return this.mNativeInfoHandle.getLoopCount();
    }

    public long getMetadataAllocationByteCount() {
        return this.mNativeInfoHandle.getMetadataByteCount();
    }

    public int getNumberOfFrames() {
        return this.mNativeInfoHandle.getNumberOfFrames();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return (!this.mNativeInfoHandle.isOpaque() || this.mPaint.getAlpha() < 255) ? -2 : -1;
    }

    @NonNull
    public final Paint getPaint() {
        return this.mPaint;
    }

    public int getPixel(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        if (i2 >= this.mNativeInfoHandle.getWidth()) {
            throw new IllegalArgumentException("x must be < width");
        }
        if (i3 < this.mNativeInfoHandle.getHeight()) {
            return this.mBuffer.getPixel(i2, i3);
        }
        throw new IllegalArgumentException("y must be < height");
    }

    public void getPixels(@NonNull int[] iArr) {
        this.mBuffer.getPixels(iArr, 0, this.mNativeInfoHandle.getWidth(), 0, 0, this.mNativeInfoHandle.getWidth(), this.mNativeInfoHandle.getHeight());
    }

    @Nullable
    public Transform getTransform() {
        return this.mTransform;
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
        scheduleNextRender();
    }

    public boolean isAnimationCompleted() {
        return this.mNativeInfoHandle.isAnimationCompleted();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return this.mIsRunning;
    }

    public boolean isRecycled() {
        return this.mNativeInfoHandle.isRecycled();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mIsRunning;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList;
        return super.isStateful() || ((colorStateList = this.mTint) != null && colorStateList.isStateful());
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        this.mDstRect.set(rect);
        Transform transform = this.mTransform;
        if (transform != null) {
            transform.onBoundsChange(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.mTint;
        if (colorStateList == null || (mode = this.mTintMode) == null) {
            return false;
        }
        this.mTintFilter = updateTintFilter(colorStateList, mode);
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        stop();
    }

    public void recycle() {
        shutdown();
        this.mBuffer.recycle();
    }

    public boolean removeAnimationListener(AnimationListener animationListener) {
        return this.mListeners.remove(animationListener);
    }

    public void reset() {
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.1
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                if (GifDrawable.this.mNativeInfoHandle.reset()) {
                    GifDrawable.this.start();
                }
            }
        });
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(@IntRange(from = 0, to = 2147483647L) final int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.2
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                GifDrawable gifDrawable = GifDrawable.this;
                gifDrawable.mNativeInfoHandle.seekToTime(i2, gifDrawable.mBuffer);
                this.mGifDrawable.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
            }
        });
    }

    public void seekToBlocking(@IntRange(from = 0, to = 2147483647L) int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToTime(i2, this.mBuffer);
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
    }

    public void seekToFrame(@IntRange(from = 0, to = 2147483647L) final int i2) {
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("Frame index is not positive");
        }
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.3
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                GifDrawable gifDrawable = GifDrawable.this;
                gifDrawable.mNativeInfoHandle.seekToFrame(i2, gifDrawable.mBuffer);
                GifDrawable.this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
            }
        });
    }

    public Bitmap seekToFrameAndGet(@IntRange(from = 0, to = 2147483647L) int i2) {
        Bitmap currentFrame;
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("Frame index is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToFrame(i2, this.mBuffer);
            currentFrame = getCurrentFrame();
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        return currentFrame;
    }

    public Bitmap seekToPositionAndGet(@IntRange(from = 0, to = 2147483647L) int i2) {
        Bitmap currentFrame;
        if (i2 < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToTime(i2, this.mBuffer);
            currentFrame = getCurrentFrame();
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        return currentFrame;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(@IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK) int i2) {
        this.mPaint.setAlpha(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setCornerRadius(@FloatRange(from = 0.0d) float f2) {
        CornerRadiusTransform cornerRadiusTransform = new CornerRadiusTransform(f2);
        this.mTransform = cornerRadiusTransform;
        cornerRadiusTransform.onBoundsChange(this.mDstRect);
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z2) {
        this.mPaint.setDither(z2);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z2) {
        this.mPaint.setFilterBitmap(z2);
        invalidateSelf();
    }

    public void setLoopCount(@IntRange(from = 0, to = WebSocketProtocol.PAYLOAD_SHORT_MAX) int i2) {
        this.mNativeInfoHandle.setLoopCount(i2);
    }

    public void setSpeed(@FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        this.mNativeInfoHandle.setSpeedFactor(f2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList;
        this.mTintFilter = updateTintFilter(colorStateList, this.mTintMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(@Nullable PorterDuff.Mode mode) {
        this.mTintMode = mode;
        this.mTintFilter = updateTintFilter(this.mTint, mode);
        invalidateSelf();
    }

    public void setTransform(@Nullable Transform transform) {
        this.mTransform = transform;
        if (transform != null) {
            transform.onBoundsChange(this.mDstRect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z2, boolean z3) {
        boolean visible = super.setVisible(z2, z3);
        if (!this.mIsRenderingTriggeredOnDraw) {
            if (z2) {
                if (z3) {
                    reset();
                }
                if (visible) {
                    start();
                }
            } else if (visible) {
                stop();
            }
        }
        return visible;
    }

    @Override // android.graphics.drawable.Animatable, android.widget.MediaController.MediaPlayerControl
    public void start() {
        synchronized (this) {
            if (this.mIsRunning) {
                return;
            }
            this.mIsRunning = true;
            startAnimation(this.mNativeInfoHandle.restoreRemainder());
        }
    }

    public void startAnimation(long j2) {
        if (this.mIsRenderingTriggeredOnDraw) {
            this.mNextFrameRenderTime = 0L;
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        } else {
            cancelPendingRenderTask();
            this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, Math.max(j2, 0L), TimeUnit.MILLISECONDS);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        synchronized (this) {
            if (this.mIsRunning) {
                this.mIsRunning = false;
                cancelPendingRenderTask();
                this.mNativeInfoHandle.saveRemainder();
            }
        }
    }

    @NonNull
    public String toString() {
        return String.format(Locale.ENGLISH, "GIF: size: %dx%d, frames: %d, error: %d", Integer.valueOf(this.mNativeInfoHandle.getWidth()), Integer.valueOf(this.mNativeInfoHandle.getHeight()), Integer.valueOf(this.mNativeInfoHandle.getNumberOfFrames()), Integer.valueOf(this.mNativeInfoHandle.getNativeErrorCode()));
    }

    public GifDrawable(@NonNull AssetManager assetManager, @NonNull String str) throws IOException {
        this(assetManager.openFd(str));
    }

    public GifDrawable(@NonNull String str) throws IOException {
        this(new GifInfoHandle(str), null, null, true);
    }

    public GifDrawable(@NonNull File file) throws IOException {
        this(file.getPath());
    }

    public GifDrawable(@NonNull InputStream inputStream) throws IOException {
        this(new GifInfoHandle(inputStream), null, null, true);
    }

    public GifDrawable(@NonNull AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(new GifInfoHandle(assetFileDescriptor), null, null, true);
    }

    public GifDrawable(@NonNull FileDescriptor fileDescriptor) throws IOException {
        this(new GifInfoHandle(fileDescriptor), null, null, true);
    }

    public GifDrawable(@NonNull byte[] bArr) throws IOException {
        this(new GifInfoHandle(bArr), null, null, true);
    }

    public GifDrawable(@NonNull ByteBuffer byteBuffer) throws IOException {
        this(new GifInfoHandle(byteBuffer), null, null, true);
    }

    public GifDrawable(@Nullable ContentResolver contentResolver, @NonNull Uri uri) throws IOException {
        this(GifInfoHandle.openUri(contentResolver, uri), null, null, true);
    }

    public GifDrawable(@NonNull InputSource inputSource, @Nullable GifDrawable gifDrawable, @Nullable ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z2, @NonNull GifOptions gifOptions) throws IOException {
        this(inputSource.createHandleWith(gifOptions), gifDrawable, scheduledThreadPoolExecutor, z2);
    }

    public GifDrawable(GifInfoHandle gifInfoHandle, GifDrawable gifDrawable, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z2) {
        this.mIsRunning = true;
        this.mNextFrameRenderTime = Long.MIN_VALUE;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mListeners = new ConcurrentLinkedQueue<>();
        RenderTask renderTask = new RenderTask(this);
        this.mRenderTask = renderTask;
        this.mIsRenderingTriggeredOnDraw = z2;
        this.mExecutor = scheduledThreadPoolExecutor == null ? GifRenderingExecutor.getInstance() : scheduledThreadPoolExecutor;
        this.mNativeInfoHandle = gifInfoHandle;
        Bitmap bitmap = null;
        if (gifDrawable != null) {
            synchronized (gifDrawable.mNativeInfoHandle) {
                if (!gifDrawable.mNativeInfoHandle.isRecycled() && gifDrawable.mNativeInfoHandle.getHeight() >= gifInfoHandle.getHeight() && gifDrawable.mNativeInfoHandle.getWidth() >= gifInfoHandle.getWidth()) {
                    gifDrawable.shutdown();
                    Bitmap bitmap2 = gifDrawable.mBuffer;
                    bitmap2.eraseColor(0);
                    bitmap = bitmap2;
                }
            }
        }
        if (bitmap == null) {
            this.mBuffer = Bitmap.createBitmap(gifInfoHandle.getWidth(), gifInfoHandle.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            this.mBuffer = bitmap;
        }
        this.mBuffer.setHasAlpha(!gifInfoHandle.isOpaque());
        this.mSrcRect = new Rect(0, 0, gifInfoHandle.getWidth(), gifInfoHandle.getHeight());
        this.mInvalidationHandler = new InvalidationHandler(this);
        renderTask.doWork();
        this.mScaledWidth = gifInfoHandle.getWidth();
        this.mScaledHeight = gifInfoHandle.getHeight();
    }
}
