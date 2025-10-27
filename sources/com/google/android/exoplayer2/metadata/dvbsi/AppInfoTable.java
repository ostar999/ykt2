package com.google.android.exoplayer2.metadata.dvbsi;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.a;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes3.dex */
public final class AppInfoTable implements Metadata.Entry {
    public static final int CONTROL_CODE_AUTOSTART = 1;
    public static final int CONTROL_CODE_PRESENT = 2;
    public static final Parcelable.Creator<AppInfoTable> CREATOR = new Parcelable.Creator<AppInfoTable>() { // from class: com.google.android.exoplayer2.metadata.dvbsi.AppInfoTable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppInfoTable createFromParcel(Parcel parcel) {
            return new AppInfoTable(parcel.readInt(), (String) Assertions.checkNotNull(parcel.readString()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppInfoTable[] newArray(int i2) {
            return new AppInfoTable[i2];
        }
    };
    public final int controlCode;
    public final String url;

    public AppInfoTable(int i2, String str) {
        this.controlCode = i2;
        this.url = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.exoplayer2.metadata.Metadata.Entry
    public /* synthetic */ byte[] getWrappedMetadataBytes() {
        return a.a(this);
    }

    @Override // com.google.android.exoplayer2.metadata.Metadata.Entry
    public /* synthetic */ Format getWrappedMetadataFormat() {
        return a.b(this);
    }

    @Override // com.google.android.exoplayer2.metadata.Metadata.Entry
    public /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        a.c(this, builder);
    }

    public String toString() {
        int i2 = this.controlCode;
        String str = this.url;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33);
        sb.append("Ait(controlCode=");
        sb.append(i2);
        sb.append(",url=");
        sb.append(str);
        sb.append(")");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.url);
        parcel.writeInt(this.controlCode);
    }
}
