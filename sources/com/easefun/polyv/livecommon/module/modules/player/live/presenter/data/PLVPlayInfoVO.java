package com.easefun.polyv.livecommon.module.modules.player.live.presenter.data;

/* loaded from: classes3.dex */
public class PLVPlayInfoVO {
    private boolean isPlaying;
    private boolean isSubVideoViewPlaying;

    public static final class Builder {
        private boolean isPlaying = false;
        private boolean isSubVideoViewPlaying = false;

        public PLVPlayInfoVO build() {
            return new PLVPlayInfoVO(this);
        }

        public Builder isPlaying(boolean val) {
            this.isPlaying = val;
            return this;
        }

        public Builder isSubVideoViewPlaying(boolean val) {
            this.isSubVideoViewPlaying = val;
            return this;
        }
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public boolean isSubVideoViewPlaying() {
        return this.isSubVideoViewPlaying;
    }

    private PLVPlayInfoVO(Builder builder) {
        this.isPlaying = builder.isPlaying;
        this.isSubVideoViewPlaying = builder.isSubVideoViewPlaying;
    }
}
