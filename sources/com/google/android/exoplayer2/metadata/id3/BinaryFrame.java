package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class BinaryFrame extends Id3Frame {
    public static final Parcelable.Creator<BinaryFrame> CREATOR = new Parcelable.Creator<BinaryFrame>() { // from class: com.google.android.exoplayer2.metadata.id3.BinaryFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BinaryFrame createFromParcel(Parcel parcel) {
            return new BinaryFrame(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BinaryFrame[] newArray(int i2) {
            return new BinaryFrame[i2];
        }
    };
    public final byte[] data;

    public BinaryFrame(String str, byte[] bArr) {
        super(str);
        this.data = bArr;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || BinaryFrame.class != obj.getClass()) {
            return false;
        }
        BinaryFrame binaryFrame = (BinaryFrame) obj;
        return this.id.equals(binaryFrame.id) && Arrays.equals(this.data, binaryFrame.data);
    }

    public int hashCode() {
        return ((R2.attr.bl_checkable_gradient_endColor + this.id.hashCode()) * 31) + Arrays.hashCode(this.data);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.id);
        parcel.writeByteArray(this.data);
    }

    public BinaryFrame(Parcel parcel) {
        super((String) Util.castNonNull(parcel.readString()));
        this.data = (byte[]) Util.castNonNull(parcel.createByteArray());
    }
}
