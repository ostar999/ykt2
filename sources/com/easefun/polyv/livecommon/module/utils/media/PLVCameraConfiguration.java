package com.easefun.polyv.livecommon.module.utils.media;

/* loaded from: classes3.dex */
public final class PLVCameraConfiguration {
    public static final int DEFAULT_FPS = 15;
    public static final int DEFAULT_HEIGHT = 1280;
    public static final int DEFAULT_WIDTH = 720;
    public final Facing facing;
    public final FocusMode focusMode;
    public final int fps;
    public final int height;
    public final Orientation orientation;
    public final int width;
    public static final Facing DEFAULT_FACING = Facing.FRONT;
    public static final Orientation DEFAULT_ORIENTATION = Orientation.PORTRAIT;
    public static final FocusMode DEFAULT_FOCUSMODE = FocusMode.AUTO;

    public static class Builder {
        private int height = 1280;
        private int width = 720;
        private int fps = 15;
        private Facing facing = PLVCameraConfiguration.DEFAULT_FACING;
        private Orientation orientation = PLVCameraConfiguration.DEFAULT_ORIENTATION;
        private FocusMode focusMode = PLVCameraConfiguration.DEFAULT_FOCUSMODE;

        public PLVCameraConfiguration build() {
            return new PLVCameraConfiguration(this);
        }

        public Builder setFacing(Facing facing) {
            this.facing = facing;
            return this;
        }

        public Builder setFocusMode(FocusMode focusMode) {
            this.focusMode = focusMode;
            return this;
        }

        public Builder setFps(int fps) {
            this.fps = fps;
            return this;
        }

        public Builder setOrientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder setPreview(int height, int width) {
            this.height = height;
            this.width = width;
            return this;
        }
    }

    public enum Facing {
        FRONT,
        BACK
    }

    public enum FocusMode {
        AUTO,
        TOUCH
    }

    public enum Orientation {
        LANDSCAPE,
        PORTRAIT
    }

    public static PLVCameraConfiguration createDefault() {
        return new Builder().build();
    }

    private PLVCameraConfiguration(final Builder builder) {
        this.height = builder.height;
        this.width = builder.width;
        this.facing = builder.facing;
        this.fps = builder.fps;
        this.orientation = builder.orientation;
        this.focusMode = builder.focusMode;
    }
}
