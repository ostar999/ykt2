package com.opensource.svgaplayer.utils;

import androidx.exifinterface.media.ExifInterface;
import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lcom/opensource/svgaplayer/utils/Pools;", "", "()V", "Pool", "SimplePool", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class Pools {

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u000f\u0010\u0003\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lcom/opensource/svgaplayer/utils/Pools$Pool;", ExifInterface.GPS_DIRECTION_TRUE, "", "acquire", "()Ljava/lang/Object;", "release", "", "instance", "(Ljava/lang/Object;)Z", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public interface Pool<T> {
        @Nullable
        T acquire();

        boolean release(T instance);
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000f\u0010\u000b\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010\fJ\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0010J\u0015\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0010R\u0018\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/opensource/svgaplayer/utils/Pools$SimplePool;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/opensource/svgaplayer/utils/Pools$Pool;", "maxPoolSize", "", "(I)V", "mPool", "", "", "[Ljava/lang/Object;", "mPoolSize", "acquire", "()Ljava/lang/Object;", "isInPool", "", "instance", "(Ljava/lang/Object;)Z", "release", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public static class SimplePool<T> implements Pool<T> {
        private final Object[] mPool;
        private int mPoolSize;

        public SimplePool(int i2) {
            if (!(i2 > 0)) {
                throw new IllegalArgumentException("The max pool size must be > 0".toString());
            }
            this.mPool = new Object[i2];
        }

        private final boolean isInPool(T instance) {
            int i2 = this.mPoolSize;
            for (int i3 = 0; i3 < i2; i3++) {
                if (this.mPool[i3] == instance) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.opensource.svgaplayer.utils.Pools.Pool
        @Nullable
        public T acquire() {
            int i2 = this.mPoolSize;
            if (i2 <= 0) {
                return null;
            }
            int i3 = i2 - 1;
            Object[] objArr = this.mPool;
            T t2 = (T) objArr[i3];
            objArr[i3] = null;
            this.mPoolSize = i2 - 1;
            return t2;
        }

        @Override // com.opensource.svgaplayer.utils.Pools.Pool
        public boolean release(T instance) {
            if (!(!isInPool(instance))) {
                throw new IllegalStateException("Already in the pool!".toString());
            }
            int i2 = this.mPoolSize;
            Object[] objArr = this.mPool;
            if (i2 >= objArr.length) {
                return false;
            }
            objArr[i2] = instance;
            this.mPoolSize = i2 + 1;
            return true;
        }
    }

    private Pools() {
    }
}
