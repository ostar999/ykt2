package org.wrtca.record.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes9.dex */
public class BaseMediaBitrateConfig implements Parcelable {
    public static final Parcelable.Creator<BaseMediaBitrateConfig> CREATOR = new Parcelable.Creator<BaseMediaBitrateConfig>() { // from class: org.wrtca.record.model.BaseMediaBitrateConfig.1
        @Override // android.os.Parcelable.Creator
        public BaseMediaBitrateConfig createFromParcel(Parcel parcel) {
            return new BaseMediaBitrateConfig(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public BaseMediaBitrateConfig[] newArray(int i2) {
            return new BaseMediaBitrateConfig[i2];
        }
    };
    public int bitrate;
    public int bufSize;
    public int crfSize;
    public int maxBitrate;
    public int mode;
    public String velocity;

    public static class MODE {
        public static final int AUTO_VBR = 3;
        public static final int CBR = 2;
        public static final int VBR = 1;
    }

    public static class Velocity {
        public static final String FAST = "fast";
        public static final String FASTER = "faster";
        public static final String MEDIUM = "medium";
        public static final String PLACEBO = "placebo";
        public static final String SLOW = "slow";
        public static final String SLOWER = "slower";
        public static final String SUPERFAST = "superfast";
        public static final String ULTRAFAST = "ultrafast";
        public static final String VERYFAST = "veryfast";
        public static final String VERYSLOW = "veryslow";
    }

    public BaseMediaBitrateConfig() {
        this.mode = -1;
        this.bitrate = -1;
        this.maxBitrate = -1;
        this.bufSize = -1;
        this.crfSize = -1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getBitrate() {
        return this.bitrate;
    }

    public int getBufSize() {
        return this.bufSize;
    }

    public int getCrfSize() {
        return this.crfSize;
    }

    public int getMaxBitrate() {
        return this.maxBitrate;
    }

    public int getMode() {
        return this.mode;
    }

    public String getVelocity() {
        return this.velocity;
    }

    public BaseMediaBitrateConfig setVelocity(String str) {
        this.velocity = str;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.mode);
        parcel.writeInt(this.bitrate);
        parcel.writeInt(this.maxBitrate);
        parcel.writeInt(this.bufSize);
        parcel.writeInt(this.crfSize);
        parcel.writeString(this.velocity);
    }

    public BaseMediaBitrateConfig(Parcel parcel) {
        this.mode = -1;
        this.bitrate = -1;
        this.maxBitrate = -1;
        this.bufSize = -1;
        this.crfSize = -1;
        this.mode = parcel.readInt();
        this.bitrate = parcel.readInt();
        this.maxBitrate = parcel.readInt();
        this.bufSize = parcel.readInt();
        this.crfSize = parcel.readInt();
        this.velocity = parcel.readString();
    }
}
