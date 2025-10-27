package org.wrtca.record.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes9.dex */
public final class MediaRecorderConfig implements Parcelable {
    public static final Parcelable.Creator<MediaRecorderConfig> CREATOR = new Parcelable.Creator<MediaRecorderConfig>() { // from class: org.wrtca.record.model.MediaRecorderConfig.1
        @Override // android.os.Parcelable.Creator
        public MediaRecorderConfig createFromParcel(Parcel parcel) {
            return new MediaRecorderConfig(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public MediaRecorderConfig[] newArray(int i2) {
            return new MediaRecorderConfig[i2];
        }
    };
    private final boolean FULL_SCREEN;
    private final boolean GO_HOME;
    private final int MAX_FRAME_RATE;
    private final int MIN_FRAME_RATE;
    private final int RECORD_TIME_MAX;
    private final int RECORD_TIME_MIN;
    private final int SMALL_VIDEO_HEIGHT;
    private final int SMALL_VIDEO_WIDTH;
    private final int VIDEO_BITRATE;
    private final int captureThumbnailsTime;

    public static class Buidler {
        private int VIDEO_BITRATE;
        private int RECORD_TIME_MAX = 6000;
        private int SMALL_VIDEO_HEIGHT = 480;
        private int SMALL_VIDEO_WIDTH = 360;
        private int MAX_FRAME_RATE = 20;
        private int MIN_FRAME_RATE = 8;
        private int captureThumbnailsTime = 1;
        private boolean GO_HOME = false;
        public int RECORD_TIME_MIN = 1500;
        private boolean FULL_SCREEN = false;

        public MediaRecorderConfig build() {
            return new MediaRecorderConfig(this);
        }

        public Buidler captureThumbnailsTime(int i2) {
            this.captureThumbnailsTime = i2;
            return this;
        }

        public Buidler fullScreen(boolean z2) {
            this.FULL_SCREEN = z2;
            return this;
        }

        public Buidler goHome(boolean z2) {
            this.GO_HOME = z2;
            return this;
        }

        public Buidler maxFrameRate(int i2) {
            this.MAX_FRAME_RATE = i2;
            return this;
        }

        public Buidler minFrameRate(int i2) {
            this.MIN_FRAME_RATE = i2;
            return this;
        }

        public Buidler recordTimeMax(int i2) {
            this.RECORD_TIME_MAX = i2;
            return this;
        }

        public Buidler recordTimeMin(int i2) {
            this.RECORD_TIME_MIN = i2;
            return this;
        }

        public Buidler smallVideoHeight(int i2) {
            this.SMALL_VIDEO_HEIGHT = i2;
            return this;
        }

        public Buidler smallVideoWidth(int i2) {
            this.SMALL_VIDEO_WIDTH = i2;
            return this;
        }

        public Buidler videoBitrate(int i2) {
            this.VIDEO_BITRATE = i2;
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

    public boolean getFullScreen() {
        return this.FULL_SCREEN;
    }

    public int getMaxFrameRate() {
        return this.MAX_FRAME_RATE;
    }

    public int getMinFrameRate() {
        return this.MIN_FRAME_RATE;
    }

    public int getRecordTimeMax() {
        return this.RECORD_TIME_MAX;
    }

    public int getRecordTimeMin() {
        return this.RECORD_TIME_MIN;
    }

    public int getSmallVideoHeight() {
        return this.SMALL_VIDEO_HEIGHT;
    }

    public int getSmallVideoWidth() {
        return this.SMALL_VIDEO_WIDTH;
    }

    public int getVideoBitrate() {
        return this.VIDEO_BITRATE;
    }

    public boolean isGO_HOME() {
        return this.GO_HOME;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByte(this.FULL_SCREEN ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.RECORD_TIME_MAX);
        parcel.writeInt(this.RECORD_TIME_MIN);
        parcel.writeInt(this.SMALL_VIDEO_HEIGHT);
        parcel.writeInt(this.SMALL_VIDEO_WIDTH);
        parcel.writeInt(this.MAX_FRAME_RATE);
        parcel.writeInt(this.MIN_FRAME_RATE);
        parcel.writeInt(this.VIDEO_BITRATE);
        parcel.writeInt(this.captureThumbnailsTime);
        parcel.writeByte(this.GO_HOME ? (byte) 1 : (byte) 0);
    }

    private MediaRecorderConfig(Buidler buidler) {
        this.FULL_SCREEN = buidler.FULL_SCREEN;
        this.RECORD_TIME_MAX = buidler.RECORD_TIME_MAX;
        this.RECORD_TIME_MIN = buidler.RECORD_TIME_MIN;
        this.MAX_FRAME_RATE = buidler.MAX_FRAME_RATE;
        this.captureThumbnailsTime = buidler.captureThumbnailsTime;
        this.MIN_FRAME_RATE = buidler.MIN_FRAME_RATE;
        this.SMALL_VIDEO_HEIGHT = buidler.SMALL_VIDEO_HEIGHT;
        this.SMALL_VIDEO_WIDTH = buidler.SMALL_VIDEO_WIDTH;
        this.VIDEO_BITRATE = buidler.VIDEO_BITRATE;
        this.GO_HOME = buidler.GO_HOME;
    }

    public MediaRecorderConfig(Parcel parcel) {
        this.FULL_SCREEN = parcel.readByte() != 0;
        this.RECORD_TIME_MAX = parcel.readInt();
        this.RECORD_TIME_MIN = parcel.readInt();
        this.SMALL_VIDEO_HEIGHT = parcel.readInt();
        this.SMALL_VIDEO_WIDTH = parcel.readInt();
        this.MAX_FRAME_RATE = parcel.readInt();
        this.MIN_FRAME_RATE = parcel.readInt();
        this.VIDEO_BITRATE = parcel.readInt();
        this.captureThumbnailsTime = parcel.readInt();
        this.GO_HOME = parcel.readByte() != 0;
    }
}
