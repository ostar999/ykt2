package com.google.android.exoplayer2.source.ads;

import androidx.annotation.CheckResult;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaPeriodId;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
public final class ServerSideInsertedAdsUtil {
    private ServerSideInsertedAdsUtil() {
    }

    @CheckResult
    public static AdPlaybackState addAdGroupToAdPlaybackState(AdPlaybackState adPlaybackState, long j2, long j3, long j4) {
        long mediaPeriodPositionUsForContent = getMediaPeriodPositionUsForContent(j2, -1, adPlaybackState);
        int i2 = adPlaybackState.removedAdGroupCount;
        while (i2 < adPlaybackState.adGroupCount && adPlaybackState.getAdGroup(i2).timeUs != Long.MIN_VALUE && adPlaybackState.getAdGroup(i2).timeUs <= mediaPeriodPositionUsForContent) {
            i2++;
        }
        long j5 = j3 - j2;
        AdPlaybackState adPlaybackStateWithContentResumeOffsetUs = adPlaybackState.withNewAdGroup(i2, mediaPeriodPositionUsForContent).withIsServerSideInserted(i2, true).withAdCount(i2, 1).withAdDurationsUs(i2, j5).withContentResumeOffsetUs(i2, j4);
        long j6 = (-j5) + j4;
        for (int i3 = i2 + 1; i3 < adPlaybackStateWithContentResumeOffsetUs.adGroupCount; i3++) {
            long j7 = adPlaybackStateWithContentResumeOffsetUs.getAdGroup(i3).timeUs;
            if (j7 != Long.MIN_VALUE) {
                adPlaybackStateWithContentResumeOffsetUs = adPlaybackStateWithContentResumeOffsetUs.withAdGroupTimeUs(i3, j7 + j6);
            }
        }
        return adPlaybackStateWithContentResumeOffsetUs;
    }

    public static int getAdCountInGroup(AdPlaybackState adPlaybackState, int i2) {
        int i3 = adPlaybackState.getAdGroup(i2).count;
        if (i3 == -1) {
            return 0;
        }
        return i3;
    }

    public static long getMediaPeriodPositionUs(long j2, MediaPeriodId mediaPeriodId, AdPlaybackState adPlaybackState) {
        return mediaPeriodId.isAd() ? getMediaPeriodPositionUsForAd(j2, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState) : getMediaPeriodPositionUsForContent(j2, mediaPeriodId.nextAdGroupIndex, adPlaybackState);
    }

    public static long getMediaPeriodPositionUsForAd(long j2, int i2, int i3, AdPlaybackState adPlaybackState) {
        int i4;
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i2);
        long j3 = j2 - adGroup.timeUs;
        int i5 = adPlaybackState.removedAdGroupCount;
        while (true) {
            i4 = 0;
            if (i5 >= i2) {
                break;
            }
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(i5);
            while (i4 < getAdCountInGroup(adPlaybackState, i5)) {
                j3 -= adGroup2.durationsUs[i4];
                i4++;
            }
            j3 += adGroup2.contentResumeOffsetUs;
            i5++;
        }
        if (i3 < getAdCountInGroup(adPlaybackState, i2)) {
            while (i4 < i3) {
                j3 -= adGroup.durationsUs[i4];
                i4++;
            }
        }
        return j3;
    }

    public static long getMediaPeriodPositionUsForContent(long j2, int i2, AdPlaybackState adPlaybackState) {
        if (i2 == -1) {
            i2 = adPlaybackState.adGroupCount;
        }
        long j3 = 0;
        for (int i3 = adPlaybackState.removedAdGroupCount; i3 < i2; i3++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i3);
            long j4 = adGroup.timeUs;
            if (j4 == Long.MIN_VALUE || j4 > j2 - j3) {
                break;
            }
            for (int i4 = 0; i4 < getAdCountInGroup(adPlaybackState, i3); i4++) {
                j3 += adGroup.durationsUs[i4];
            }
            long j5 = adGroup.contentResumeOffsetUs;
            j3 -= j5;
            long j6 = adGroup.timeUs;
            long j7 = j2 - j3;
            if (j5 + j6 > j7) {
                return Math.max(j6, j7);
            }
        }
        return j2 - j3;
    }

    public static long getStreamDurationUs(Player player, AdPlaybackState adPlaybackState) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C.TIME_UNSET;
        }
        long j2 = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), new Timeline.Period()).durationUs;
        return j2 == C.TIME_UNSET ? C.TIME_UNSET : getStreamPositionUsForContent(j2, -1, adPlaybackState);
    }

    public static long getStreamPositionUs(Player player, AdPlaybackState adPlaybackState) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C.TIME_UNSET;
        }
        Timeline.Period period = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), new Timeline.Period());
        if (!Util.areEqual(period.getAdsId(), adPlaybackState.adsId)) {
            return C.TIME_UNSET;
        }
        if (!player.isPlayingAd()) {
            return getStreamPositionUsForContent(Util.msToUs(player.getCurrentPosition()) - period.getPositionInWindowUs(), -1, adPlaybackState);
        }
        return getStreamPositionUsForAd(Util.msToUs(player.getCurrentPosition()), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), adPlaybackState);
    }

    public static long getStreamPositionUsForAd(long j2, int i2, int i3, AdPlaybackState adPlaybackState) {
        int i4;
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i2);
        long j3 = j2 + adGroup.timeUs;
        int i5 = adPlaybackState.removedAdGroupCount;
        while (true) {
            i4 = 0;
            if (i5 >= i2) {
                break;
            }
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(i5);
            while (i4 < getAdCountInGroup(adPlaybackState, i5)) {
                j3 += adGroup2.durationsUs[i4];
                i4++;
            }
            j3 -= adGroup2.contentResumeOffsetUs;
            i5++;
        }
        if (i3 < getAdCountInGroup(adPlaybackState, i2)) {
            while (i4 < i3) {
                j3 += adGroup.durationsUs[i4];
                i4++;
            }
        }
        return j3;
    }

    public static long getStreamPositionUsForContent(long j2, int i2, AdPlaybackState adPlaybackState) {
        if (i2 == -1) {
            i2 = adPlaybackState.adGroupCount;
        }
        long j3 = 0;
        for (int i3 = adPlaybackState.removedAdGroupCount; i3 < i2; i3++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i3);
            long j4 = adGroup.timeUs;
            if (j4 == Long.MIN_VALUE || j4 > j2) {
                break;
            }
            long j5 = j4 + j3;
            for (int i4 = 0; i4 < getAdCountInGroup(adPlaybackState, i3); i4++) {
                j3 += adGroup.durationsUs[i4];
            }
            long j6 = adGroup.contentResumeOffsetUs;
            j3 -= j6;
            if (adGroup.timeUs + j6 > j2) {
                return Math.max(j5, j2 + j3);
            }
        }
        return j2 + j3;
    }

    public static long getStreamPositionUs(long j2, MediaPeriodId mediaPeriodId, AdPlaybackState adPlaybackState) {
        if (mediaPeriodId.isAd()) {
            return getStreamPositionUsForAd(j2, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState);
        }
        return getStreamPositionUsForContent(j2, mediaPeriodId.nextAdGroupIndex, adPlaybackState);
    }
}
