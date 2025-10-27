package com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data;

/* loaded from: classes3.dex */
public class PLVPlayInfoVO {
    private transient int bufPercent;
    private transient boolean isPlaying;
    private transient boolean isSubVideoViewPlaying;
    private int position;
    private int totalTime;

    public static final class Builder {
        private int bufPercent;
        private boolean isPlaying;
        private boolean isSubVideoViewPlaying;
        private int position;
        private int totalTime;

        public Builder bufPercent(int val) {
            this.bufPercent = val;
            return this;
        }

        public PLVPlayInfoVO build() {
            return new PLVPlayInfoVO(this);
        }

        public Builder isPlaying(boolean val) {
            this.isPlaying = val;
            return this;
        }

        public Builder isSubViewPlaying(boolean subViewPlaying) {
            this.isSubVideoViewPlaying = subViewPlaying;
            return this;
        }

        public Builder position(int val) {
            this.position = val;
            return this;
        }

        public Builder totalTime(int val) {
            this.totalTime = val;
            return this;
        }
    }

    public int getBufPercent() {
        return this.bufPercent;
    }

    public int getPosition() {
        return this.position;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public boolean isSubVideoViewPlaying() {
        return this.isSubVideoViewPlaying;
    }

    private PLVPlayInfoVO(Builder builder) {
        this.position = builder.position;
        this.totalTime = builder.totalTime;
        this.bufPercent = builder.bufPercent;
        this.isPlaying = builder.isPlaying;
        this.isSubVideoViewPlaying = builder.isSubVideoViewPlaying;
    }
}
