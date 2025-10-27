package androidx.datastore.core;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\f\u001a\u00020\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n¨\u0006\u000e"}, d2 = {"Landroidx/datastore/core/DataAndHash;", ExifInterface.GPS_DIRECTION_TRUE, "", "value", "hashCode", "", "(Ljava/lang/Object;I)V", "getHashCode", "()I", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "checkHashCode", "", "datastore-core"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes.dex */
final class DataAndHash<T> {
    private final int hashCode;
    private final T value;

    public DataAndHash(T t2, int i2) {
        this.value = t2;
        this.hashCode = i2;
    }

    public final void checkHashCode() {
        T t2 = this.value;
        if (!((t2 != null ? t2.hashCode() : 0) == this.hashCode)) {
            throw new IllegalStateException("Data in DataStore was mutated but DataStore is only compatible with Immutable types.".toString());
        }
    }

    public final int getHashCode() {
        return this.hashCode;
    }

    public final T getValue() {
        return this.value;
    }
}
