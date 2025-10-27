package pl.droidsonroids.gif;

import android.graphics.Bitmap;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;

/* loaded from: classes9.dex */
public class GifDecoder {
    private final GifInfoHandle mGifInfoHandle;

    public GifDecoder(@NonNull InputSource inputSource) throws IOException {
        this(inputSource, null);
    }

    private void checkBuffer(Bitmap bitmap) {
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("Bitmap is recycled");
        }
        if (bitmap.getWidth() < this.mGifInfoHandle.getWidth() || bitmap.getHeight() < this.mGifInfoHandle.getHeight()) {
            throw new IllegalArgumentException("Bitmap ia too small, size must be greater than or equal to GIF size");
        }
        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888) {
            return;
        }
        throw new IllegalArgumentException("Only Config.ARGB_8888 is supported. Current bitmap config: " + bitmap.getConfig());
    }

    public long getAllocationByteCount() {
        return this.mGifInfoHandle.getAllocationByteCount();
    }

    public String getComment() {
        return this.mGifInfoHandle.getComment();
    }

    public int getDuration() {
        return this.mGifInfoHandle.getDuration();
    }

    public int getFrameDuration(@IntRange(from = 0) int i2) {
        return this.mGifInfoHandle.getFrameDuration(i2);
    }

    public int getHeight() {
        return this.mGifInfoHandle.getHeight();
    }

    public int getLoopCount() {
        return this.mGifInfoHandle.getLoopCount();
    }

    public int getNumberOfFrames() {
        return this.mGifInfoHandle.getNumberOfFrames();
    }

    public long getSourceLength() {
        return this.mGifInfoHandle.getSourceLength();
    }

    public int getWidth() {
        return this.mGifInfoHandle.getWidth();
    }

    public boolean isAnimated() {
        return this.mGifInfoHandle.getNumberOfFrames() > 1 && getDuration() > 0;
    }

    public void recycle() {
        this.mGifInfoHandle.recycle();
    }

    public void seekToFrame(@IntRange(from = 0, to = 2147483647L) int i2, @NonNull Bitmap bitmap) {
        checkBuffer(bitmap);
        this.mGifInfoHandle.seekToFrame(i2, bitmap);
    }

    public void seekToTime(@IntRange(from = 0, to = 2147483647L) int i2, @NonNull Bitmap bitmap) {
        checkBuffer(bitmap);
        this.mGifInfoHandle.seekToTime(i2, bitmap);
    }

    public GifDecoder(@NonNull InputSource inputSource, @Nullable GifOptions gifOptions) throws IOException {
        GifInfoHandle gifInfoHandleOpen = inputSource.open();
        this.mGifInfoHandle = gifInfoHandleOpen;
        if (gifOptions != null) {
            gifInfoHandleOpen.setOptions(gifOptions.inSampleSize, gifOptions.inIsOpaque);
        }
    }
}
