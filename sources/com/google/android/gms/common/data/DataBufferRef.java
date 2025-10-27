package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
/* loaded from: classes3.dex */
public class DataBufferRef {

    @KeepForSdk
    protected final DataHolder mDataHolder;

    @KeepForSdk
    protected int mDataRow;
    private int zalp;

    @KeepForSdk
    public DataBufferRef(DataHolder dataHolder, int i2) {
        this.mDataHolder = (DataHolder) Preconditions.checkNotNull(dataHolder);
        zag(i2);
    }

    @KeepForSdk
    public void copyToBuffer(String str, CharArrayBuffer charArrayBuffer) {
        this.mDataHolder.zaa(str, this.mDataRow, this.zalp, charArrayBuffer);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DataBufferRef) {
            DataBufferRef dataBufferRef = (DataBufferRef) obj;
            if (Objects.equal(Integer.valueOf(dataBufferRef.mDataRow), Integer.valueOf(this.mDataRow)) && Objects.equal(Integer.valueOf(dataBufferRef.zalp), Integer.valueOf(this.zalp)) && dataBufferRef.mDataHolder == this.mDataHolder) {
                return true;
            }
        }
        return false;
    }

    @KeepForSdk
    public boolean getBoolean(String str) {
        return this.mDataHolder.getBoolean(str, this.mDataRow, this.zalp);
    }

    @KeepForSdk
    public byte[] getByteArray(String str) {
        return this.mDataHolder.getByteArray(str, this.mDataRow, this.zalp);
    }

    @KeepForSdk
    public int getDataRow() {
        return this.mDataRow;
    }

    @KeepForSdk
    public double getDouble(String str) {
        return this.mDataHolder.zab(str, this.mDataRow, this.zalp);
    }

    @KeepForSdk
    public float getFloat(String str) {
        return this.mDataHolder.zaa(str, this.mDataRow, this.zalp);
    }

    @KeepForSdk
    public int getInteger(String str) {
        return this.mDataHolder.getInteger(str, this.mDataRow, this.zalp);
    }

    @KeepForSdk
    public long getLong(String str) {
        return this.mDataHolder.getLong(str, this.mDataRow, this.zalp);
    }

    @KeepForSdk
    public String getString(String str) {
        return this.mDataHolder.getString(str, this.mDataRow, this.zalp);
    }

    @KeepForSdk
    public boolean hasColumn(String str) {
        return this.mDataHolder.hasColumn(str);
    }

    @KeepForSdk
    public boolean hasNull(String str) {
        return this.mDataHolder.hasNull(str, this.mDataRow, this.zalp);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.mDataRow), Integer.valueOf(this.zalp), this.mDataHolder);
    }

    @KeepForSdk
    public boolean isDataValid() {
        return !this.mDataHolder.isClosed();
    }

    @KeepForSdk
    public Uri parseUri(String str) {
        String string = this.mDataHolder.getString(str, this.mDataRow, this.zalp);
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    public final void zag(int i2) {
        Preconditions.checkState(i2 >= 0 && i2 < this.mDataHolder.getCount());
        this.mDataRow = i2;
        this.zalp = this.mDataHolder.getWindowIndex(i2);
    }
}
