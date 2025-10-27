package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
public final class PrivateCommand extends SpliceCommand {
    public static final Parcelable.Creator<PrivateCommand> CREATOR = new Parcelable.Creator<PrivateCommand>() { // from class: com.google.android.exoplayer2.metadata.scte35.PrivateCommand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrivateCommand createFromParcel(Parcel parcel) {
            return new PrivateCommand(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrivateCommand[] newArray(int i2) {
            return new PrivateCommand[i2];
        }
    };
    public final byte[] commandBytes;
    public final long identifier;
    public final long ptsAdjustment;

    public static PrivateCommand parseFromSection(ParsableByteArray parsableByteArray, int i2, long j2) {
        long unsignedInt = parsableByteArray.readUnsignedInt();
        int i3 = i2 - 4;
        byte[] bArr = new byte[i3];
        parsableByteArray.readBytes(bArr, 0, i3);
        return new PrivateCommand(unsignedInt, bArr, j2);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.ptsAdjustment);
        parcel.writeLong(this.identifier);
        parcel.writeByteArray(this.commandBytes);
    }

    private PrivateCommand(long j2, byte[] bArr, long j3) {
        this.ptsAdjustment = j3;
        this.identifier = j2;
        this.commandBytes = bArr;
    }

    private PrivateCommand(Parcel parcel) {
        this.ptsAdjustment = parcel.readLong();
        this.identifier = parcel.readLong();
        this.commandBytes = (byte[]) Util.castNonNull(parcel.createByteArray());
    }
}
