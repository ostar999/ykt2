package com.google.android.exoplayer2.video;

import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import java.util.Arrays;

/* loaded from: classes3.dex */
final class FixedFrameRateEstimator {
    public static final int CONSECUTIVE_MATCHING_FRAME_DURATIONS_FOR_SYNC = 15;

    @VisibleForTesting
    static final long MAX_MATCHING_FRAME_DIFFERENCE_NS = 1000000;
    private boolean candidateMatcherActive;
    private int framesWithoutSyncCount;
    private boolean switchToCandidateMatcherWhenSynced;
    private Matcher currentMatcher = new Matcher();
    private Matcher candidateMatcher = new Matcher();
    private long lastFramePresentationTimeNs = C.TIME_UNSET;

    public static final class Matcher {
        private long firstFrameDurationNs;
        private long firstFramePresentationTimeNs;
        private long frameCount;
        private long lastFramePresentationTimeNs;
        private long matchingFrameCount;
        private long matchingFrameDurationSumNs;
        private int recentFrameOutlierCount;
        private final boolean[] recentFrameOutlierFlags = new boolean[15];

        private static int getRecentFrameOutlierIndex(long j2) {
            return (int) (j2 % 15);
        }

        public long getFrameDurationNs() {
            long j2 = this.matchingFrameCount;
            if (j2 == 0) {
                return 0L;
            }
            return this.matchingFrameDurationSumNs / j2;
        }

        public long getMatchingFrameDurationSumNs() {
            return this.matchingFrameDurationSumNs;
        }

        public boolean isLastFrameOutlier() {
            long j2 = this.frameCount;
            if (j2 == 0) {
                return false;
            }
            return this.recentFrameOutlierFlags[getRecentFrameOutlierIndex(j2 - 1)];
        }

        public boolean isSynced() {
            return this.frameCount > 15 && this.recentFrameOutlierCount == 0;
        }

        public void onNextFrame(long j2) {
            long j3 = this.frameCount;
            if (j3 == 0) {
                this.firstFramePresentationTimeNs = j2;
            } else if (j3 == 1) {
                long j4 = j2 - this.firstFramePresentationTimeNs;
                this.firstFrameDurationNs = j4;
                this.matchingFrameDurationSumNs = j4;
                this.matchingFrameCount = 1L;
            } else {
                long j5 = j2 - this.lastFramePresentationTimeNs;
                int recentFrameOutlierIndex = getRecentFrameOutlierIndex(j3);
                if (Math.abs(j5 - this.firstFrameDurationNs) <= 1000000) {
                    this.matchingFrameCount++;
                    this.matchingFrameDurationSumNs += j5;
                    boolean[] zArr = this.recentFrameOutlierFlags;
                    if (zArr[recentFrameOutlierIndex]) {
                        zArr[recentFrameOutlierIndex] = false;
                        this.recentFrameOutlierCount--;
                    }
                } else {
                    boolean[] zArr2 = this.recentFrameOutlierFlags;
                    if (!zArr2[recentFrameOutlierIndex]) {
                        zArr2[recentFrameOutlierIndex] = true;
                        this.recentFrameOutlierCount++;
                    }
                }
            }
            this.frameCount++;
            this.lastFramePresentationTimeNs = j2;
        }

        public void reset() {
            this.frameCount = 0L;
            this.matchingFrameCount = 0L;
            this.matchingFrameDurationSumNs = 0L;
            this.recentFrameOutlierCount = 0;
            Arrays.fill(this.recentFrameOutlierFlags, false);
        }
    }

    public long getFrameDurationNs() {
        return isSynced() ? this.currentMatcher.getFrameDurationNs() : C.TIME_UNSET;
    }

    public float getFrameRate() {
        if (isSynced()) {
            return (float) (1.0E9d / this.currentMatcher.getFrameDurationNs());
        }
        return -1.0f;
    }

    public int getFramesWithoutSyncCount() {
        return this.framesWithoutSyncCount;
    }

    public long getMatchingFrameDurationSumNs() {
        return isSynced() ? this.currentMatcher.getMatchingFrameDurationSumNs() : C.TIME_UNSET;
    }

    public boolean isSynced() {
        return this.currentMatcher.isSynced();
    }

    public void onNextFrame(long j2) {
        this.currentMatcher.onNextFrame(j2);
        if (this.currentMatcher.isSynced() && !this.switchToCandidateMatcherWhenSynced) {
            this.candidateMatcherActive = false;
        } else if (this.lastFramePresentationTimeNs != C.TIME_UNSET) {
            if (!this.candidateMatcherActive || this.candidateMatcher.isLastFrameOutlier()) {
                this.candidateMatcher.reset();
                this.candidateMatcher.onNextFrame(this.lastFramePresentationTimeNs);
            }
            this.candidateMatcherActive = true;
            this.candidateMatcher.onNextFrame(j2);
        }
        if (this.candidateMatcherActive && this.candidateMatcher.isSynced()) {
            Matcher matcher = this.currentMatcher;
            this.currentMatcher = this.candidateMatcher;
            this.candidateMatcher = matcher;
            this.candidateMatcherActive = false;
            this.switchToCandidateMatcherWhenSynced = false;
        }
        this.lastFramePresentationTimeNs = j2;
        this.framesWithoutSyncCount = this.currentMatcher.isSynced() ? 0 : this.framesWithoutSyncCount + 1;
    }

    public void reset() {
        this.currentMatcher.reset();
        this.candidateMatcher.reset();
        this.candidateMatcherActive = false;
        this.lastFramePresentationTimeNs = C.TIME_UNSET;
        this.framesWithoutSyncCount = 0;
    }
}
