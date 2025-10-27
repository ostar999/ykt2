package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes3.dex */
public final class TimeSignalCommand extends SpliceCommand {
    public static final Parcelable.Creator<TimeSignalCommand> CREATOR = new Parcelable.Creator<TimeSignalCommand>() { // from class: com.google.android.exoplayer2.metadata.scte35.TimeSignalCommand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeSignalCommand createFromParcel(Parcel parcel) {
            return new TimeSignalCommand(parcel.readLong(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeSignalCommand[] newArray(int i2) {
            return new TimeSignalCommand[i2];
        }
    };
    public final long playbackPositionUs;
    public final long ptsTime;

    public static TimeSignalCommand parseFromSection(ParsableByteArray parsableByteArray, long j2, TimestampAdjuster timestampAdjuster) {
        long spliceTime = parseSpliceTime(parsableByteArray, j2);
        return new TimeSignalCommand(spliceTime, timestampAdjuster.adjustTsTimestamp(spliceTime));
    }

    public static long parseSpliceTime(ParsableByteArray parsableByteArray, long j2) {
        long unsignedByte = parsableByteArray.readUnsignedByte();
        return (128 & unsignedByte) != 0 ? TarConstants.MAXSIZE & ((((unsignedByte & 1) << 32) | parsableByteArray.readUnsignedInt()) + j2) : C.TIME_UNSET;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.ptsTime);
        parcel.writeLong(this.playbackPositionUs);
    }

    private TimeSignalCommand(long j2, long j3) {
        this.ptsTime = j2;
        this.playbackPositionUs = j3;
    }
}
