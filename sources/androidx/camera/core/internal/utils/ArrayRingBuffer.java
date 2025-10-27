package androidx.camera.core.internal.utils;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.internal.utils.RingBuffer;
import java.util.ArrayDeque;

/* loaded from: classes.dex */
public class ArrayRingBuffer<T> implements RingBuffer<T> {
    private static final String TAG = "ZslRingBuffer";

    @GuardedBy("mLock")
    private final ArrayDeque<T> mBuffer;
    private final Object mLock;

    @Nullable
    final RingBuffer.OnRemoveCallback<T> mOnRemoveCallback;
    private final int mRingBufferCapacity;

    public ArrayRingBuffer(int i2) {
        this(i2, null);
    }

    @Override // androidx.camera.core.internal.utils.RingBuffer
    @NonNull
    public T dequeue() {
        T tRemoveLast;
        synchronized (this.mLock) {
            tRemoveLast = this.mBuffer.removeLast();
        }
        return tRemoveLast;
    }

    @Override // androidx.camera.core.internal.utils.RingBuffer
    public void enqueue(@NonNull T t2) {
        T tDequeue;
        synchronized (this.mLock) {
            tDequeue = this.mBuffer.size() >= this.mRingBufferCapacity ? dequeue() : null;
            this.mBuffer.addFirst(t2);
        }
        RingBuffer.OnRemoveCallback<T> onRemoveCallback = this.mOnRemoveCallback;
        if (onRemoveCallback == null || tDequeue == null) {
            return;
        }
        onRemoveCallback.onRemove(tDequeue);
    }

    @Override // androidx.camera.core.internal.utils.RingBuffer
    public int getMaxCapacity() {
        return this.mRingBufferCapacity;
    }

    @Override // androidx.camera.core.internal.utils.RingBuffer
    public boolean isEmpty() {
        boolean zIsEmpty;
        synchronized (this.mLock) {
            zIsEmpty = this.mBuffer.isEmpty();
        }
        return zIsEmpty;
    }

    public ArrayRingBuffer(int i2, @Nullable RingBuffer.OnRemoveCallback<T> onRemoveCallback) {
        this.mLock = new Object();
        this.mRingBufferCapacity = i2;
        this.mBuffer = new ArrayDeque<>(i2);
        this.mOnRemoveCallback = onRemoveCallback;
    }
}
