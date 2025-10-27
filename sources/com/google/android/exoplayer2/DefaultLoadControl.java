package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
public class DefaultLoadControl implements LoadControl {
    public static final int DEFAULT_AUDIO_BUFFER_SIZE = 13107200;
    public static final int DEFAULT_BACK_BUFFER_DURATION_MS = 0;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 5000;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_MS = 2500;
    public static final int DEFAULT_CAMERA_MOTION_BUFFER_SIZE = 131072;
    public static final int DEFAULT_IMAGE_BUFFER_SIZE = 131072;
    public static final int DEFAULT_MAX_BUFFER_MS = 50000;
    public static final int DEFAULT_METADATA_BUFFER_SIZE = 131072;
    public static final int DEFAULT_MIN_BUFFER_MS = 50000;
    public static final int DEFAULT_MIN_BUFFER_SIZE = 13107200;
    public static final int DEFAULT_MUXED_BUFFER_SIZE = 144310272;
    public static final boolean DEFAULT_PRIORITIZE_TIME_OVER_SIZE_THRESHOLDS = false;
    public static final boolean DEFAULT_RETAIN_BACK_BUFFER_FROM_KEYFRAME = false;
    public static final int DEFAULT_TARGET_BUFFER_BYTES = -1;
    public static final int DEFAULT_TEXT_BUFFER_SIZE = 131072;
    public static final int DEFAULT_VIDEO_BUFFER_SIZE = 131072000;
    private final DefaultAllocator allocator;
    private final long backBufferDurationUs;
    private final long bufferForPlaybackAfterRebufferUs;
    private final long bufferForPlaybackUs;
    private boolean isLoading;
    private final long maxBufferUs;
    private final long minBufferUs;
    private final boolean prioritizeTimeOverSizeThresholds;
    private final boolean retainBackBufferFromKeyframe;
    private int targetBufferBytes;
    private final int targetBufferBytesOverwrite;

    public static final class Builder {

        @Nullable
        private DefaultAllocator allocator;
        private boolean buildCalled;
        private int minBufferMs = 50000;
        private int maxBufferMs = 50000;
        private int bufferForPlaybackMs = 2500;
        private int bufferForPlaybackAfterRebufferMs = 5000;
        private int targetBufferBytes = -1;
        private boolean prioritizeTimeOverSizeThresholds = false;
        private int backBufferDurationMs = 0;
        private boolean retainBackBufferFromKeyframe = false;

        public DefaultLoadControl build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            if (this.allocator == null) {
                this.allocator = new DefaultAllocator(true, 65536);
            }
            return new DefaultLoadControl(this.allocator, this.minBufferMs, this.maxBufferMs, this.bufferForPlaybackMs, this.bufferForPlaybackAfterRebufferMs, this.targetBufferBytes, this.prioritizeTimeOverSizeThresholds, this.backBufferDurationMs, this.retainBackBufferFromKeyframe);
        }

        @Deprecated
        public DefaultLoadControl createDefaultLoadControl() {
            return build();
        }

        public Builder setAllocator(DefaultAllocator defaultAllocator) {
            Assertions.checkState(!this.buildCalled);
            this.allocator = defaultAllocator;
            return this;
        }

        public Builder setBackBuffer(int i2, boolean z2) {
            Assertions.checkState(!this.buildCalled);
            DefaultLoadControl.assertGreaterOrEqual(i2, 0, "backBufferDurationMs", "0");
            this.backBufferDurationMs = i2;
            this.retainBackBufferFromKeyframe = z2;
            return this;
        }

        public Builder setBufferDurationsMs(int i2, int i3, int i4, int i5) {
            Assertions.checkState(!this.buildCalled);
            DefaultLoadControl.assertGreaterOrEqual(i4, 0, "bufferForPlaybackMs", "0");
            DefaultLoadControl.assertGreaterOrEqual(i5, 0, "bufferForPlaybackAfterRebufferMs", "0");
            DefaultLoadControl.assertGreaterOrEqual(i2, i4, "minBufferMs", "bufferForPlaybackMs");
            DefaultLoadControl.assertGreaterOrEqual(i2, i5, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
            DefaultLoadControl.assertGreaterOrEqual(i3, i2, "maxBufferMs", "minBufferMs");
            this.minBufferMs = i2;
            this.maxBufferMs = i3;
            this.bufferForPlaybackMs = i4;
            this.bufferForPlaybackAfterRebufferMs = i5;
            return this;
        }

        public Builder setPrioritizeTimeOverSizeThresholds(boolean z2) {
            Assertions.checkState(!this.buildCalled);
            this.prioritizeTimeOverSizeThresholds = z2;
            return this;
        }

        public Builder setTargetBufferBytes(int i2) {
            Assertions.checkState(!this.buildCalled);
            this.targetBufferBytes = i2;
            return this;
        }
    }

    public DefaultLoadControl() {
        this(new DefaultAllocator(true, 65536), 50000, 50000, 2500, 5000, -1, false, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void assertGreaterOrEqual(int i2, int i3, String str, String str2) {
        boolean z2 = i2 >= i3;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(" cannot be less than ");
        sb.append(str2);
        Assertions.checkArgument(z2, sb.toString());
    }

    private static int getDefaultBufferSize(int i2) {
        switch (i2) {
            case -2:
                return 0;
            case -1:
            default:
                throw new IllegalArgumentException();
            case 0:
                return DEFAULT_MUXED_BUFFER_SIZE;
            case 1:
                return 13107200;
            case 2:
                return DEFAULT_VIDEO_BUFFER_SIZE;
            case 3:
            case 4:
            case 5:
            case 6:
                return 131072;
        }
    }

    private void reset(boolean z2) {
        int i2 = this.targetBufferBytesOverwrite;
        if (i2 == -1) {
            i2 = 13107200;
        }
        this.targetBufferBytes = i2;
        this.isLoading = false;
        if (z2) {
            this.allocator.reset();
        }
    }

    public int calculateTargetBufferBytes(Renderer[] rendererArr, ExoTrackSelection[] exoTrackSelectionArr) {
        int defaultBufferSize = 0;
        for (int i2 = 0; i2 < rendererArr.length; i2++) {
            if (exoTrackSelectionArr[i2] != null) {
                defaultBufferSize += getDefaultBufferSize(rendererArr[i2].getTrackType());
            }
        }
        return Math.max(13107200, defaultBufferSize);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public Allocator getAllocator() {
        return this.allocator;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public long getBackBufferDurationUs() {
        return this.backBufferDurationUs;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onPrepared() {
        reset(false);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onReleased() {
        reset(true);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onStopped() {
        reset(true);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public void onTracksSelected(Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        int iCalculateTargetBufferBytes = this.targetBufferBytesOverwrite;
        if (iCalculateTargetBufferBytes == -1) {
            iCalculateTargetBufferBytes = calculateTargetBufferBytes(rendererArr, exoTrackSelectionArr);
        }
        this.targetBufferBytes = iCalculateTargetBufferBytes;
        this.allocator.setTargetBufferSize(iCalculateTargetBufferBytes);
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean retainBackBufferFromKeyframe() {
        return this.retainBackBufferFromKeyframe;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean shouldContinueLoading(long j2, long j3, float f2) {
        boolean z2 = true;
        boolean z3 = this.allocator.getTotalBytesAllocated() >= this.targetBufferBytes;
        long jMin = this.minBufferUs;
        if (f2 > 1.0f) {
            jMin = Math.min(Util.getMediaDurationForPlayoutDuration(jMin, f2), this.maxBufferUs);
        }
        if (j3 < Math.max(jMin, 500000L)) {
            if (!this.prioritizeTimeOverSizeThresholds && z3) {
                z2 = false;
            }
            this.isLoading = z2;
            if (!z2 && j3 < 500000) {
                Log.w("DefaultLoadControl", "Target buffer size reached with less than 500ms of buffered media data.");
            }
        } else if (j3 >= this.maxBufferUs || z3) {
            this.isLoading = false;
        }
        return this.isLoading;
    }

    @Override // com.google.android.exoplayer2.LoadControl
    public boolean shouldStartPlayback(long j2, float f2, boolean z2, long j3) {
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(j2, f2);
        long jMin = z2 ? this.bufferForPlaybackAfterRebufferUs : this.bufferForPlaybackUs;
        if (j3 != C.TIME_UNSET) {
            jMin = Math.min(j3 / 2, jMin);
        }
        return jMin <= 0 || playoutDurationForMediaDuration >= jMin || (!this.prioritizeTimeOverSizeThresholds && this.allocator.getTotalBytesAllocated() >= this.targetBufferBytes);
    }

    public DefaultLoadControl(DefaultAllocator defaultAllocator, int i2, int i3, int i4, int i5, int i6, boolean z2, int i7, boolean z3) {
        assertGreaterOrEqual(i4, 0, "bufferForPlaybackMs", "0");
        assertGreaterOrEqual(i5, 0, "bufferForPlaybackAfterRebufferMs", "0");
        assertGreaterOrEqual(i2, i4, "minBufferMs", "bufferForPlaybackMs");
        assertGreaterOrEqual(i2, i5, "minBufferMs", "bufferForPlaybackAfterRebufferMs");
        assertGreaterOrEqual(i3, i2, "maxBufferMs", "minBufferMs");
        assertGreaterOrEqual(i7, 0, "backBufferDurationMs", "0");
        this.allocator = defaultAllocator;
        this.minBufferUs = Util.msToUs(i2);
        this.maxBufferUs = Util.msToUs(i3);
        this.bufferForPlaybackUs = Util.msToUs(i4);
        this.bufferForPlaybackAfterRebufferUs = Util.msToUs(i5);
        this.targetBufferBytesOverwrite = i6;
        this.targetBufferBytes = i6 == -1 ? 13107200 : i6;
        this.prioritizeTimeOverSizeThresholds = z2;
        this.backBufferDurationUs = Util.msToUs(i7);
        this.retainBackBufferFromKeyframe = z3;
    }
}
