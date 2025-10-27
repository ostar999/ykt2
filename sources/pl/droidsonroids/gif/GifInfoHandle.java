package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.system.ErrnoException;
import android.system.Os;
import android.view.Surface;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import kotlin.text.Typography;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes9.dex */
final class GifInfoHandle {
    private volatile long gifInfoPtr;

    static {
        LibraryLoader.loadLibrary();
    }

    public GifInfoHandle() {
    }

    private static native void bindSurface(long j2, Surface surface, long[] jArr);

    public static native int createTempNativeFileDescriptor() throws GifIOException;

    public static native int extractNativeFileDescriptor(FileDescriptor fileDescriptor, boolean z2) throws GifIOException;

    private static native void free(long j2);

    private static native long getAllocationByteCount(long j2);

    private static native String getComment(long j2);

    private static native int getCurrentFrameIndex(long j2);

    private static native int getCurrentLoop(long j2);

    private static native int getCurrentPosition(long j2);

    private static native int getDuration(long j2);

    private static native int getFrameDuration(long j2, int i2);

    private static native int getHeight(long j2);

    private static native int getLoopCount(long j2);

    private static native long getMetadataByteCount(long j2);

    private static native int getNativeErrorCode(long j2);

    @RequiresApi(21)
    private static int getNativeFileDescriptor(FileDescriptor fileDescriptor, boolean z2) throws GifIOException, ErrnoException {
        try {
            int iCreateTempNativeFileDescriptor = createTempNativeFileDescriptor();
            Os.dup2(fileDescriptor, iCreateTempNativeFileDescriptor);
            return iCreateTempNativeFileDescriptor;
        } finally {
            if (z2) {
                Os.close(fileDescriptor);
            }
        }
    }

    private static native int getNumberOfFrames(long j2);

    private static native long[] getSavedState(long j2);

    private static native long getSourceLength(long j2);

    private static native int getWidth(long j2);

    private static native void glTexImage2D(long j2, int i2, int i3);

    private static native void glTexSubImage2D(long j2, int i2, int i3);

    private static native void initTexImageDescriptor(long j2);

    private static native boolean isAnimationCompleted(long j2);

    private static native boolean isOpaque(long j2);

    public static native long openByteArray(byte[] bArr) throws GifIOException;

    public static native long openDirectByteBuffer(ByteBuffer byteBuffer) throws GifIOException;

    public static native long openFile(String str) throws GifIOException;

    private static long openFileDescriptor(FileDescriptor fileDescriptor, long j2, boolean z2) throws GifIOException {
        int nativeFileDescriptor;
        if (Build.VERSION.SDK_INT > 27) {
            try {
                nativeFileDescriptor = getNativeFileDescriptor(fileDescriptor, z2);
            } catch (Exception e2) {
                throw new GifIOException(GifError.OPEN_FAILED.errorCode, e2.getMessage());
            }
        } else {
            nativeFileDescriptor = extractNativeFileDescriptor(fileDescriptor, z2);
        }
        return openNativeFileDescriptor(nativeFileDescriptor, j2);
    }

    public static native long openNativeFileDescriptor(int i2, long j2) throws GifIOException;

    public static native long openStream(InputStream inputStream) throws GifIOException;

    public static GifInfoHandle openUri(ContentResolver contentResolver, Uri uri) throws IOException {
        if ("file".equals(uri.getScheme())) {
            return new GifInfoHandle(uri.getPath());
        }
        AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
        if (assetFileDescriptorOpenAssetFileDescriptor != null) {
            return new GifInfoHandle(assetFileDescriptorOpenAssetFileDescriptor);
        }
        throw new IOException("Could not open AssetFileDescriptor for " + uri);
    }

    private static native void postUnbindSurface(long j2);

    private static native long renderFrame(long j2, Bitmap bitmap);

    private static native boolean reset(long j2);

    private static native long restoreRemainder(long j2);

    private static native int restoreSavedState(long j2, long[] jArr, Bitmap bitmap);

    private static native void saveRemainder(long j2);

    private static native void seekToFrame(long j2, int i2, Bitmap bitmap);

    private static native void seekToFrameGL(long j2, int i2);

    private static native void seekToTime(long j2, int i2, Bitmap bitmap);

    private static native void setLoopCount(long j2, char c3);

    private static native void setOptions(long j2, char c3, boolean z2);

    private static native void setSpeedFactor(long j2, float f2);

    private static native void startDecoderThread(long j2);

    private static native void stopDecoderThread(long j2);

    private void throwIfFrameIndexOutOfBounds(@IntRange(from = 0) int i2) {
        int numberOfFrames = getNumberOfFrames(this.gifInfoPtr);
        if (i2 < 0 || i2 >= numberOfFrames) {
            throw new IndexOutOfBoundsException("Frame index is not in range <0;" + numberOfFrames + Typography.greater);
        }
    }

    public void bindSurface(Surface surface, long[] jArr) {
        bindSurface(this.gifInfoPtr, surface, jArr);
    }

    public void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }

    public synchronized long getAllocationByteCount() {
        return getAllocationByteCount(this.gifInfoPtr);
    }

    public synchronized String getComment() {
        return getComment(this.gifInfoPtr);
    }

    public synchronized int getCurrentFrameIndex() {
        return getCurrentFrameIndex(this.gifInfoPtr);
    }

    public synchronized int getCurrentLoop() {
        return getCurrentLoop(this.gifInfoPtr);
    }

    public synchronized int getCurrentPosition() {
        return getCurrentPosition(this.gifInfoPtr);
    }

    public synchronized int getDuration() {
        return getDuration(this.gifInfoPtr);
    }

    public synchronized int getFrameDuration(@IntRange(from = 0) int i2) {
        throwIfFrameIndexOutOfBounds(i2);
        return getFrameDuration(this.gifInfoPtr, i2);
    }

    public synchronized int getHeight() {
        return getHeight(this.gifInfoPtr);
    }

    public synchronized int getLoopCount() {
        return getLoopCount(this.gifInfoPtr);
    }

    public synchronized long getMetadataByteCount() {
        return getMetadataByteCount(this.gifInfoPtr);
    }

    public synchronized int getNativeErrorCode() {
        return getNativeErrorCode(this.gifInfoPtr);
    }

    public synchronized int getNumberOfFrames() {
        return getNumberOfFrames(this.gifInfoPtr);
    }

    public synchronized long[] getSavedState() {
        return getSavedState(this.gifInfoPtr);
    }

    public synchronized long getSourceLength() {
        return getSourceLength(this.gifInfoPtr);
    }

    public synchronized int getWidth() {
        return getWidth(this.gifInfoPtr);
    }

    public void glTexImage2D(int i2, int i3) {
        glTexImage2D(this.gifInfoPtr, i2, i3);
    }

    public void glTexSubImage2D(int i2, int i3) {
        glTexSubImage2D(this.gifInfoPtr, i2, i3);
    }

    public void initTexImageDescriptor() {
        initTexImageDescriptor(this.gifInfoPtr);
    }

    public synchronized boolean isAnimationCompleted() {
        return isAnimationCompleted(this.gifInfoPtr);
    }

    public synchronized boolean isOpaque() {
        return isOpaque(this.gifInfoPtr);
    }

    public synchronized boolean isRecycled() {
        return this.gifInfoPtr == 0;
    }

    public synchronized void postUnbindSurface() {
        postUnbindSurface(this.gifInfoPtr);
    }

    public synchronized void recycle() {
        free(this.gifInfoPtr);
        this.gifInfoPtr = 0L;
    }

    public synchronized long renderFrame(Bitmap bitmap) {
        return renderFrame(this.gifInfoPtr, bitmap);
    }

    public synchronized boolean reset() {
        return reset(this.gifInfoPtr);
    }

    public synchronized long restoreRemainder() {
        return restoreRemainder(this.gifInfoPtr);
    }

    public synchronized int restoreSavedState(long[] jArr, Bitmap bitmap) {
        return restoreSavedState(this.gifInfoPtr, jArr, bitmap);
    }

    public synchronized void saveRemainder() {
        saveRemainder(this.gifInfoPtr);
    }

    public synchronized void seekToFrame(@IntRange(from = 0, to = 2147483647L) int i2, Bitmap bitmap) {
        seekToFrame(this.gifInfoPtr, i2, bitmap);
    }

    public void seekToFrameGL(@IntRange(from = 0) int i2) {
        throwIfFrameIndexOutOfBounds(i2);
        seekToFrameGL(this.gifInfoPtr, i2);
    }

    public synchronized void seekToTime(@IntRange(from = 0, to = 2147483647L) int i2, Bitmap bitmap) {
        seekToTime(this.gifInfoPtr, i2, bitmap);
    }

    public void setLoopCount(@IntRange(from = 0, to = WebSocketProtocol.PAYLOAD_SHORT_MAX) int i2) {
        if (i2 < 0 || i2 > 65535) {
            throw new IllegalArgumentException("Loop count of range <0, 65535>");
        }
        synchronized (this) {
            setLoopCount(this.gifInfoPtr, (char) i2);
        }
    }

    public void setOptions(char c3, boolean z2) {
        setOptions(this.gifInfoPtr, c3, z2);
    }

    public void setSpeedFactor(@FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        if (f2 <= 0.0f || Float.isNaN(f2)) {
            throw new IllegalArgumentException("Speed factor is not positive");
        }
        if (f2 < 4.656613E-10f) {
            f2 = 4.656613E-10f;
        }
        synchronized (this) {
            setSpeedFactor(this.gifInfoPtr, f2);
        }
    }

    public void startDecoderThread() {
        startDecoderThread(this.gifInfoPtr);
    }

    public void stopDecoderThread() {
        stopDecoderThread(this.gifInfoPtr);
    }

    public GifInfoHandle(FileDescriptor fileDescriptor) throws GifIOException {
        this.gifInfoPtr = openFileDescriptor(fileDescriptor, 0L, true);
    }

    public GifInfoHandle(byte[] bArr) throws GifIOException {
        this.gifInfoPtr = openByteArray(bArr);
    }

    public GifInfoHandle(ByteBuffer byteBuffer) throws GifIOException {
        this.gifInfoPtr = openDirectByteBuffer(byteBuffer);
    }

    public GifInfoHandle(String str) throws GifIOException {
        this.gifInfoPtr = openFile(str);
    }

    public GifInfoHandle(InputStream inputStream) throws GifIOException {
        if (inputStream.markSupported()) {
            this.gifInfoPtr = openStream(inputStream);
            return;
        }
        throw new IllegalArgumentException("InputStream does not support marking");
    }

    public GifInfoHandle(AssetFileDescriptor assetFileDescriptor) throws IOException {
        try {
            this.gifInfoPtr = openFileDescriptor(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), false);
        } finally {
            try {
                assetFileDescriptor.close();
            } catch (IOException unused) {
            }
        }
    }
}
