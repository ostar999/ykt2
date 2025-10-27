package org.wrtca.record.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes9.dex */
public final class LocalMediaConfig implements Parcelable {
    public static final Parcelable.Creator<LocalMediaConfig> CREATOR = new Parcelable.Creator<LocalMediaConfig>() { // from class: org.wrtca.record.model.LocalMediaConfig.1
        @Override // android.os.Parcelable.Creator
        public LocalMediaConfig createFromParcel(Parcel parcel) {
            return new LocalMediaConfig(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LocalMediaConfig[] newArray(int i2) {
            return new LocalMediaConfig[i2];
        }
    };
    private final int FRAME_RATE;
    private final boolean GO_HOME;
    private final int captureThumbnailsTime;
    private final BaseMediaBitrateConfig compressConfig;
    private final float scale;
    private final String videoAddress;

    public static class Buidler {
        private int FRAME_RATE;
        private BaseMediaBitrateConfig compressConfig;
        private float scale;
        private String videoPath;
        private int captureThumbnailsTime = 1;
        private boolean GO_HOME = false;

        public LocalMediaConfig build() {
            return new LocalMediaConfig(this);
        }

        public Buidler captureThumbnailsTime(int i2) {
            this.captureThumbnailsTime = i2;
            return this;
        }

        public Buidler doH264Compress(BaseMediaBitrateConfig baseMediaBitrateConfig) {
            this.compressConfig = baseMediaBitrateConfig;
            return this;
        }

        public Buidler goHome(boolean z2) {
            this.GO_HOME = z2;
            return this;
        }

        public Buidler setFramerate(int i2) {
            this.FRAME_RATE = i2;
            return this;
        }

        public Buidler setScale(float f2) {
            if (f2 <= 1.0f) {
                this.scale = 1.0f;
            } else {
                this.scale = f2;
            }
            return this;
        }

        public Buidler setVideoPath(String str) {
            this.videoPath = str;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCaptureThumbnailsTime() {
        return this.captureThumbnailsTime;
    }

    public BaseMediaBitrateConfig getCompressConfig() {
        return this.compressConfig;
    }

    public int getFrameRate() {
        return this.FRAME_RATE;
    }

    public float getScale() {
        return this.scale;
    }

    public String getVideoPath() {
        return this.videoAddress;
    }

    public boolean isGO_HOME() {
        return this.GO_HOME;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.FRAME_RATE);
        parcel.writeInt(this.captureThumbnailsTime);
        parcel.writeByte(this.GO_HOME ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.compressConfig, i2);
        parcel.writeString(this.videoAddress);
        parcel.writeFloat(this.scale);
    }

    private LocalMediaConfig(Buidler buidler) {
        this.captureThumbnailsTime = buidler.captureThumbnailsTime;
        this.FRAME_RATE = buidler.FRAME_RATE;
        this.compressConfig = buidler.compressConfig;
        this.videoAddress = buidler.videoPath;
        this.scale = buidler.scale;
        this.GO_HOME = buidler.GO_HOME;
    }

    public LocalMediaConfig(Parcel parcel) {
        this.FRAME_RATE = parcel.readInt();
        this.captureThumbnailsTime = parcel.readInt();
        this.GO_HOME = parcel.readByte() != 0;
        this.compressConfig = (BaseMediaBitrateConfig) parcel.readParcelable(BaseMediaBitrateConfig.class.getClassLoader());
        this.videoAddress = parcel.readString();
        this.scale = parcel.readFloat();
    }
}
