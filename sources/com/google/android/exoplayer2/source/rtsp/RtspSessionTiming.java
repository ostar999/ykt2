package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
final class RtspSessionTiming {
    private static final long LIVE_START_TIME = 0;
    private static final String START_TIMING_NTP_FORMAT = "npt=%.3f-";
    public final long startTimeMs;
    public final long stopTimeMs;
    public static final RtspSessionTiming DEFAULT = new RtspSessionTiming(0, C.TIME_UNSET);
    private static final Pattern NPT_RANGE_PATTERN = Pattern.compile("npt=([.\\d]+|now)\\s?-\\s?([.\\d]+)?");

    private RtspSessionTiming(long j2, long j3) {
        this.startTimeMs = j2;
        this.stopTimeMs = j3;
    }

    public static String getOffsetStartTimeTiming(long j2) {
        return Util.formatInvariant(START_TIMING_NTP_FORMAT, Double.valueOf(j2 / 1000.0d));
    }

    public static RtspSessionTiming parseTiming(String str) throws ParserException {
        long j2;
        Matcher matcher = NPT_RANGE_PATTERN.matcher(str);
        Assertions.checkArgument(matcher.matches());
        String str2 = (String) Assertions.checkNotNull(matcher.group(1));
        long j3 = str2.equals("now") ? 0L : (long) (Float.parseFloat(str2) * 1000.0f);
        String strGroup = matcher.group(2);
        if (strGroup != null) {
            try {
                j2 = (long) (Float.parseFloat(strGroup) * 1000.0f);
                Assertions.checkArgument(j2 > j3);
            } catch (NumberFormatException e2) {
                throw ParserException.createForMalformedManifest(strGroup, e2);
            }
        } else {
            j2 = C.TIME_UNSET;
        }
        return new RtspSessionTiming(j3, j2);
    }

    public long getDurationMs() {
        return this.stopTimeMs - this.startTimeMs;
    }

    public boolean isLive() {
        return this.stopTimeMs == C.TIME_UNSET;
    }
}
