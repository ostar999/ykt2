package com.google.android.exoplayer2.offline;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;

/* loaded from: classes3.dex */
public final class StreamKey implements Comparable<StreamKey>, Parcelable {
    public static final Parcelable.Creator<StreamKey> CREATOR = new Parcelable.Creator<StreamKey>() { // from class: com.google.android.exoplayer2.offline.StreamKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamKey createFromParcel(Parcel parcel) {
            return new StreamKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamKey[] newArray(int i2) {
            return new StreamKey[i2];
        }
    };
    public final int groupIndex;
    public final int periodIndex;
    public final int streamIndex;

    @Deprecated
    public final int trackIndex;

    public StreamKey(int i2, int i3) {
        this(0, i2, i3);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || StreamKey.class != obj.getClass()) {
            return false;
        }
        StreamKey streamKey = (StreamKey) obj;
        return this.periodIndex == streamKey.periodIndex && this.groupIndex == streamKey.groupIndex && this.streamIndex == streamKey.streamIndex;
    }

    public int hashCode() {
        return (((this.periodIndex * 31) + this.groupIndex) * 31) + this.streamIndex;
    }

    public String toString() {
        int i2 = this.periodIndex;
        int i3 = this.groupIndex;
        int i4 = this.streamIndex;
        StringBuilder sb = new StringBuilder(35);
        sb.append(i2);
        sb.append(StrPool.DOT);
        sb.append(i3);
        sb.append(StrPool.DOT);
        sb.append(i4);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.periodIndex);
        parcel.writeInt(this.groupIndex);
        parcel.writeInt(this.streamIndex);
    }

    public StreamKey(int i2, int i3, int i4) {
        this.periodIndex = i2;
        this.groupIndex = i3;
        this.streamIndex = i4;
        this.trackIndex = i4;
    }

    @Override // java.lang.Comparable
    public int compareTo(StreamKey streamKey) {
        int i2 = this.periodIndex - streamKey.periodIndex;
        if (i2 != 0) {
            return i2;
        }
        int i3 = this.groupIndex - streamKey.groupIndex;
        return i3 == 0 ? this.streamIndex - streamKey.streamIndex : i3;
    }

    public StreamKey(Parcel parcel) {
        this.periodIndex = parcel.readInt();
        this.groupIndex = parcel.readInt();
        int i2 = parcel.readInt();
        this.streamIndex = i2;
        this.trackIndex = i2;
    }
}
