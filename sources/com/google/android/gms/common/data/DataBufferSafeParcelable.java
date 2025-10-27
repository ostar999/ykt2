package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
/* loaded from: classes3.dex */
public class DataBufferSafeParcelable<T extends SafeParcelable> extends AbstractDataBuffer<T> {
    private static final String[] zalr = {"data"};
    private final Parcelable.Creator<T> zals;

    @KeepForSdk
    public DataBufferSafeParcelable(DataHolder dataHolder, Parcelable.Creator<T> creator) {
        super(dataHolder);
        this.zals = creator;
    }

    @KeepForSdk
    public static <T extends SafeParcelable> void addValue(DataHolder.Builder builder, T t2) {
        Parcel parcelObtain = Parcel.obtain();
        t2.writeToParcel(parcelObtain, 0);
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", parcelObtain.marshall());
        builder.withRow(contentValues);
        parcelObtain.recycle();
    }

    @KeepForSdk
    public static DataHolder.Builder buildDataHolder() {
        return DataHolder.builder(zalr);
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    @KeepForSdk
    public T get(int i2) {
        DataHolder dataHolder = this.mDataHolder;
        byte[] byteArray = dataHolder.getByteArray("data", i2, dataHolder.getWindowIndex(i2));
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(byteArray, 0, byteArray.length);
        parcelObtain.setDataPosition(0);
        T tCreateFromParcel = this.zals.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return tCreateFromParcel;
    }
}
