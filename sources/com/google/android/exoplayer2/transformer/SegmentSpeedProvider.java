package com.google.android.exoplayer2.transformer;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes3.dex */
class SegmentSpeedProvider implements SpeedProvider {
    private static final int INPUT_FRAME_RATE = 30;
    private final float baseSpeedMultiplier;
    private final ImmutableSortedMap<Long, Float> speedsByStartTimeUs;

    public SegmentSpeedProvider(Format format) {
        float captureFrameRate = getCaptureFrameRate(format);
        float f2 = captureFrameRate == -3.4028235E38f ? 1.0f : captureFrameRate / 30.0f;
        this.baseSpeedMultiplier = f2;
        this.speedsByStartTimeUs = buildSpeedByStartTimeUsMap(format, f2);
    }

    private static ImmutableSortedMap<Long, Float> buildSpeedByStartTimeUsMap(Format format, float f2) {
        ImmutableList<SlowMotionData.Segment> immutableListExtractSlowMotionSegments = extractSlowMotionSegments(format);
        if (immutableListExtractSlowMotionSegments.isEmpty()) {
            return ImmutableSortedMap.of();
        }
        TreeMap treeMap = new TreeMap();
        for (int i2 = 0; i2 < immutableListExtractSlowMotionSegments.size(); i2++) {
            treeMap.put(Long.valueOf(Util.msToUs(immutableListExtractSlowMotionSegments.get(i2).startTimeMs)), Float.valueOf(f2 / r3.speedDivisor));
        }
        for (int i3 = 0; i3 < immutableListExtractSlowMotionSegments.size(); i3++) {
            SlowMotionData.Segment segment = immutableListExtractSlowMotionSegments.get(i3);
            if (!treeMap.containsKey(Long.valueOf(Util.msToUs(segment.endTimeMs)))) {
                treeMap.put(Long.valueOf(Util.msToUs(segment.endTimeMs)), Float.valueOf(f2));
            }
        }
        return ImmutableSortedMap.copyOf((Map) treeMap);
    }

    private static ImmutableList<SlowMotionData.Segment> extractSlowMotionSegments(Format format) {
        ArrayList arrayList = new ArrayList();
        Metadata metadata = format.metadata;
        if (metadata != null) {
            for (int i2 = 0; i2 < metadata.length(); i2++) {
                Metadata.Entry entry = metadata.get(i2);
                if (entry instanceof SlowMotionData) {
                    arrayList.addAll(((SlowMotionData) entry).segments);
                }
            }
        }
        return ImmutableList.sortedCopyOf(SlowMotionData.Segment.BY_START_THEN_END_THEN_DIVISOR, arrayList);
    }

    private static float getCaptureFrameRate(Format format) {
        Metadata metadata = format.metadata;
        if (metadata == null) {
            return -3.4028235E38f;
        }
        for (int i2 = 0; i2 < metadata.length(); i2++) {
            Metadata.Entry entry = metadata.get(i2);
            if (entry instanceof SmtaMetadataEntry) {
                return ((SmtaMetadataEntry) entry).captureFrameRate;
            }
        }
        return -3.4028235E38f;
    }

    @Override // com.google.android.exoplayer2.transformer.SpeedProvider
    public float getSpeed(long j2) {
        Assertions.checkArgument(j2 >= 0);
        Map.Entry<Long, Float> entryFloorEntry = this.speedsByStartTimeUs.floorEntry(Long.valueOf(j2));
        return entryFloorEntry != null ? entryFloorEntry.getValue().floatValue() : this.baseSpeedMultiplier;
    }
}
