package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import androidx.core.internal.view.SupportMenu;
import com.yikaobang.yixue.R2;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/* loaded from: classes3.dex */
public class SafeParcelWriter {
    private SafeParcelWriter() {
    }

    public static int beginObjectHeader(Parcel parcel) {
        return zza(parcel, R2.id.tv_video_definition);
    }

    public static void finishObjectHeader(Parcel parcel, int i2) {
        zzb(parcel, i2);
    }

    public static void writeBigDecimal(Parcel parcel, int i2, BigDecimal bigDecimal, boolean z2) {
        if (bigDecimal == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeByteArray(bigDecimal.unscaledValue().toByteArray());
            parcel.writeInt(bigDecimal.scale());
            zzb(parcel, iZza);
        }
    }

    public static void writeBigDecimalArray(Parcel parcel, int i2, BigDecimal[] bigDecimalArr, boolean z2) {
        if (bigDecimalArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int length = bigDecimalArr.length;
        parcel.writeInt(length);
        for (int i3 = 0; i3 < length; i3++) {
            parcel.writeByteArray(bigDecimalArr[i3].unscaledValue().toByteArray());
            parcel.writeInt(bigDecimalArr[i3].scale());
        }
        zzb(parcel, iZza);
    }

    public static void writeBigInteger(Parcel parcel, int i2, BigInteger bigInteger, boolean z2) {
        if (bigInteger == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeByteArray(bigInteger.toByteArray());
            zzb(parcel, iZza);
        }
    }

    public static void writeBigIntegerArray(Parcel parcel, int i2, BigInteger[] bigIntegerArr, boolean z2) {
        if (bigIntegerArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        parcel.writeInt(bigIntegerArr.length);
        for (BigInteger bigInteger : bigIntegerArr) {
            parcel.writeByteArray(bigInteger.toByteArray());
        }
        zzb(parcel, iZza);
    }

    public static void writeBoolean(Parcel parcel, int i2, boolean z2) {
        zzb(parcel, i2, 4);
        parcel.writeInt(z2 ? 1 : 0);
    }

    public static void writeBooleanArray(Parcel parcel, int i2, boolean[] zArr, boolean z2) {
        if (zArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeBooleanArray(zArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeBooleanList(Parcel parcel, int i2, List<Boolean> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(list.get(i3).booleanValue() ? 1 : 0);
        }
        zzb(parcel, iZza);
    }

    public static void writeBooleanObject(Parcel parcel, int i2, Boolean bool, boolean z2) {
        if (bool != null) {
            zzb(parcel, i2, 4);
            parcel.writeInt(bool.booleanValue() ? 1 : 0);
        } else if (z2) {
            zzb(parcel, i2, 0);
        }
    }

    public static void writeBundle(Parcel parcel, int i2, Bundle bundle, boolean z2) {
        if (bundle == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeBundle(bundle);
            zzb(parcel, iZza);
        }
    }

    public static void writeByte(Parcel parcel, int i2, byte b3) {
        zzb(parcel, i2, 4);
        parcel.writeInt(b3);
    }

    public static void writeByteArray(Parcel parcel, int i2, byte[] bArr, boolean z2) {
        if (bArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeByteArray(bArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeByteArrayArray(Parcel parcel, int i2, byte[][] bArr, boolean z2) {
        if (bArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        parcel.writeInt(bArr.length);
        for (byte[] bArr2 : bArr) {
            parcel.writeByteArray(bArr2);
        }
        zzb(parcel, iZza);
    }

    public static void writeByteArraySparseArray(Parcel parcel, int i2, SparseArray<byte[]> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeByteArray(sparseArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static void writeChar(Parcel parcel, int i2, char c3) {
        zzb(parcel, i2, 4);
        parcel.writeInt(c3);
    }

    public static void writeCharArray(Parcel parcel, int i2, char[] cArr, boolean z2) {
        if (cArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeCharArray(cArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeDouble(Parcel parcel, int i2, double d3) {
        zzb(parcel, i2, 8);
        parcel.writeDouble(d3);
    }

    public static void writeDoubleArray(Parcel parcel, int i2, double[] dArr, boolean z2) {
        if (dArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeDoubleArray(dArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeDoubleList(Parcel parcel, int i2, List<Double> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeDouble(list.get(i3).doubleValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeDoubleObject(Parcel parcel, int i2, Double d3, boolean z2) {
        if (d3 != null) {
            zzb(parcel, i2, 8);
            parcel.writeDouble(d3.doubleValue());
        } else if (z2) {
            zzb(parcel, i2, 0);
        }
    }

    public static void writeDoubleSparseArray(Parcel parcel, int i2, SparseArray<Double> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeDouble(sparseArray.valueAt(i3).doubleValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeFloat(Parcel parcel, int i2, float f2) {
        zzb(parcel, i2, 4);
        parcel.writeFloat(f2);
    }

    public static void writeFloatArray(Parcel parcel, int i2, float[] fArr, boolean z2) {
        if (fArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeFloatArray(fArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeFloatList(Parcel parcel, int i2, List<Float> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeFloat(list.get(i3).floatValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeFloatObject(Parcel parcel, int i2, Float f2, boolean z2) {
        if (f2 != null) {
            zzb(parcel, i2, 4);
            parcel.writeFloat(f2.floatValue());
        } else if (z2) {
            zzb(parcel, i2, 0);
        }
    }

    public static void writeFloatSparseArray(Parcel parcel, int i2, SparseArray<Float> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeFloat(sparseArray.valueAt(i3).floatValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeIBinder(Parcel parcel, int i2, IBinder iBinder, boolean z2) {
        if (iBinder == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeStrongBinder(iBinder);
            zzb(parcel, iZza);
        }
    }

    public static void writeIBinderArray(Parcel parcel, int i2, IBinder[] iBinderArr, boolean z2) {
        if (iBinderArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeBinderArray(iBinderArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeIBinderList(Parcel parcel, int i2, List<IBinder> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeBinderList(list);
            zzb(parcel, iZza);
        }
    }

    public static void writeIBinderSparseArray(Parcel parcel, int i2, SparseArray<IBinder> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeStrongBinder(sparseArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static void writeInt(Parcel parcel, int i2, int i3) {
        zzb(parcel, i2, 4);
        parcel.writeInt(i3);
    }

    public static void writeIntArray(Parcel parcel, int i2, int[] iArr, boolean z2) {
        if (iArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeIntArray(iArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeIntegerList(Parcel parcel, int i2, List<Integer> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(list.get(i3).intValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeIntegerObject(Parcel parcel, int i2, Integer num, boolean z2) {
        if (num != null) {
            zzb(parcel, i2, 4);
            parcel.writeInt(num.intValue());
        } else if (z2) {
            zzb(parcel, i2, 0);
        }
    }

    public static void writeList(Parcel parcel, int i2, List list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeList(list);
            zzb(parcel, iZza);
        }
    }

    public static void writeLong(Parcel parcel, int i2, long j2) {
        zzb(parcel, i2, 8);
        parcel.writeLong(j2);
    }

    public static void writeLongArray(Parcel parcel, int i2, long[] jArr, boolean z2) {
        if (jArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeLongArray(jArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeLongList(Parcel parcel, int i2, List<Long> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeLong(list.get(i3).longValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeLongObject(Parcel parcel, int i2, Long l2, boolean z2) {
        if (l2 != null) {
            zzb(parcel, i2, 8);
            parcel.writeLong(l2.longValue());
        } else if (z2) {
            zzb(parcel, i2, 0);
        }
    }

    public static void writeParcel(Parcel parcel, int i2, Parcel parcel2, boolean z2) {
        if (parcel2 == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            zzb(parcel, iZza);
        }
    }

    public static void writeParcelArray(Parcel parcel, int i2, Parcel[] parcelArr, boolean z2) {
        if (parcelArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        parcel.writeInt(parcelArr.length);
        for (Parcel parcel2 : parcelArr) {
            if (parcel2 != null) {
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            } else {
                parcel.writeInt(0);
            }
        }
        zzb(parcel, iZza);
    }

    public static void writeParcelList(Parcel parcel, int i2, List<Parcel> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            Parcel parcel2 = list.get(i3);
            if (parcel2 != null) {
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            } else {
                parcel.writeInt(0);
            }
        }
        zzb(parcel, iZza);
    }

    public static void writeParcelSparseArray(Parcel parcel, int i2, SparseArray<Parcel> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            Parcel parcelValueAt = sparseArray.valueAt(i3);
            if (parcelValueAt != null) {
                parcel.writeInt(parcelValueAt.dataSize());
                parcel.appendFrom(parcelValueAt, 0, parcelValueAt.dataSize());
            } else {
                parcel.writeInt(0);
            }
        }
        zzb(parcel, iZza);
    }

    public static void writeParcelable(Parcel parcel, int i2, Parcelable parcelable, int i3, boolean z2) {
        if (parcelable == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcelable.writeToParcel(parcel, i3);
            zzb(parcel, iZza);
        }
    }

    public static void writeShort(Parcel parcel, int i2, short s2) {
        zzb(parcel, i2, 4);
        parcel.writeInt(s2);
    }

    public static void writeSparseBooleanArray(Parcel parcel, int i2, SparseBooleanArray sparseBooleanArray, boolean z2) {
        if (sparseBooleanArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeSparseBooleanArray(sparseBooleanArray);
            zzb(parcel, iZza);
        }
    }

    public static void writeSparseIntArray(Parcel parcel, int i2, SparseIntArray sparseIntArray, boolean z2) {
        if (sparseIntArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseIntArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseIntArray.keyAt(i3));
            parcel.writeInt(sparseIntArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static void writeSparseLongArray(Parcel parcel, int i2, SparseLongArray sparseLongArray, boolean z2) {
        if (sparseLongArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseLongArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseLongArray.keyAt(i3));
            parcel.writeLong(sparseLongArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static void writeString(Parcel parcel, int i2, String str, boolean z2) {
        if (str == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeString(str);
            zzb(parcel, iZza);
        }
    }

    public static void writeStringArray(Parcel parcel, int i2, String[] strArr, boolean z2) {
        if (strArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeStringArray(strArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeStringList(Parcel parcel, int i2, List<String> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeStringList(list);
            zzb(parcel, iZza);
        }
    }

    public static void writeStringSparseArray(Parcel parcel, int i2, SparseArray<String> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeString(sparseArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static <T extends Parcelable> void writeTypedArray(Parcel parcel, int i2, T[] tArr, int i3, boolean z2) {
        if (tArr == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        parcel.writeInt(tArr.length);
        for (T t2 : tArr) {
            if (t2 == null) {
                parcel.writeInt(0);
            } else {
                zza(parcel, t2, i3);
            }
        }
        zzb(parcel, iZza);
    }

    public static <T extends Parcelable> void writeTypedList(Parcel parcel, int i2, List<T> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            T t2 = list.get(i3);
            if (t2 == null) {
                parcel.writeInt(0);
            } else {
                zza(parcel, t2, 0);
            }
        }
        zzb(parcel, iZza);
    }

    public static <T extends Parcelable> void writeTypedSparseArray(Parcel parcel, int i2, SparseArray<T> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzb(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            T tValueAt = sparseArray.valueAt(i3);
            if (tValueAt == null) {
                parcel.writeInt(0);
            } else {
                zza(parcel, tValueAt, 0);
            }
        }
        zzb(parcel, iZza);
    }

    private static int zza(Parcel parcel, int i2) {
        parcel.writeInt(i2 | SupportMenu.CATEGORY_MASK);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzb(Parcel parcel, int i2, int i3) {
        if (i3 < 65535) {
            parcel.writeInt(i2 | (i3 << 16));
        } else {
            parcel.writeInt(i2 | SupportMenu.CATEGORY_MASK);
            parcel.writeInt(i3);
        }
    }

    private static <T extends Parcelable> void zza(Parcel parcel, T t2, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(1);
        int iDataPosition2 = parcel.dataPosition();
        t2.writeToParcel(parcel, i2);
        int iDataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition3 - iDataPosition2);
        parcel.setDataPosition(iDataPosition3);
    }

    private static void zzb(Parcel parcel, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.setDataPosition(i2 - 4);
        parcel.writeInt(iDataPosition - i2);
        parcel.setDataPosition(iDataPosition);
    }
}
