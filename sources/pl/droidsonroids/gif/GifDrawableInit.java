package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import okhttp3.internal.ws.WebSocketProtocol;
import pl.droidsonroids.gif.GifDrawableInit;
import pl.droidsonroids.gif.InputSource;
import pl.droidsonroids.gif.annotations.Beta;

/* loaded from: classes9.dex */
public abstract class GifDrawableInit<T extends GifDrawableInit<T>> {
    private ScheduledThreadPoolExecutor mExecutor;
    private InputSource mInputSource;
    private GifDrawable mOldDrawable;
    private boolean mIsRenderingTriggeredOnDraw = true;
    private final GifOptions mOptions = new GifOptions();

    public GifDrawable build() throws IOException {
        InputSource inputSource = this.mInputSource;
        if (inputSource != null) {
            return inputSource.createGifDrawable(this.mOldDrawable, this.mExecutor, this.mIsRenderingTriggeredOnDraw, this.mOptions);
        }
        throw new NullPointerException("Source is not set");
    }

    public T from(InputStream inputStream) {
        this.mInputSource = new InputSource.InputStreamSource(inputStream);
        return (T) self();
    }

    public ScheduledThreadPoolExecutor getExecutor() {
        return this.mExecutor;
    }

    public InputSource getInputSource() {
        return this.mInputSource;
    }

    public GifDrawable getOldDrawable() {
        return this.mOldDrawable;
    }

    public GifOptions getOptions() {
        return this.mOptions;
    }

    public boolean isRenderingTriggeredOnDraw() {
        return this.mIsRenderingTriggeredOnDraw;
    }

    @Beta
    public T options(@Nullable GifOptions gifOptions) {
        this.mOptions.setFrom(gifOptions);
        return (T) self();
    }

    public T renderingTriggeredOnDraw(boolean z2) {
        this.mIsRenderingTriggeredOnDraw = z2;
        return (T) self();
    }

    public T sampleSize(@IntRange(from = 1, to = WebSocketProtocol.PAYLOAD_SHORT_MAX) int i2) {
        this.mOptions.setInSampleSize(i2);
        return (T) self();
    }

    public abstract T self();

    public T setRenderingTriggeredOnDraw(boolean z2) {
        return (T) renderingTriggeredOnDraw(z2);
    }

    public T taskExecutor(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        this.mExecutor = scheduledThreadPoolExecutor;
        return (T) self();
    }

    public T threadPoolSize(int i2) {
        this.mExecutor = new ScheduledThreadPoolExecutor(i2);
        return (T) self();
    }

    public T with(GifDrawable gifDrawable) {
        this.mOldDrawable = gifDrawable;
        return (T) self();
    }

    public T from(AssetFileDescriptor assetFileDescriptor) {
        this.mInputSource = new InputSource.AssetFileDescriptorSource(assetFileDescriptor);
        return (T) self();
    }

    public T from(FileDescriptor fileDescriptor) {
        this.mInputSource = new InputSource.FileDescriptorSource(fileDescriptor);
        return (T) self();
    }

    public T from(AssetManager assetManager, String str) {
        this.mInputSource = new InputSource.AssetSource(assetManager, str);
        return (T) self();
    }

    public T from(ContentResolver contentResolver, Uri uri) {
        this.mInputSource = new InputSource.UriSource(contentResolver, uri);
        return (T) self();
    }

    public T from(File file) {
        this.mInputSource = new InputSource.FileSource(file);
        return (T) self();
    }

    public T from(String str) {
        this.mInputSource = new InputSource.FileSource(str);
        return (T) self();
    }

    public T from(byte[] bArr) {
        this.mInputSource = new InputSource.ByteArraySource(bArr);
        return (T) self();
    }

    public T from(ByteBuffer byteBuffer) {
        this.mInputSource = new InputSource.DirectByteBufferSource(byteBuffer);
        return (T) self();
    }

    public T from(Resources resources, int i2) {
        this.mInputSource = new InputSource.ResourcesSource(resources, i2);
        return (T) self();
    }
}
