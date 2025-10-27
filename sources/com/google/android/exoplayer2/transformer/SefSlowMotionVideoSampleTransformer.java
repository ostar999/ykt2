package com.google.android.exoplayer2.transformer;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
final class SefSlowMotionVideoSampleTransformer implements SampleTransformer {

    @VisibleForTesting
    static final int INPUT_FRAME_RATE = 30;
    private static final int NAL_START_CODE_LENGTH = NalUnitUtil.NAL_START_CODE.length;
    private static final int NAL_UNIT_TYPE_PREFIX = 14;
    private static final int TARGET_OUTPUT_FRAME_RATE = 30;
    private final float captureFrameRate;

    @Nullable
    private SegmentInfo currentSegmentInfo;
    private long frameTimeDeltaUs;
    private final int inputMaxLayer;

    @Nullable
    private SegmentInfo nextSegmentInfo;
    private final int normalSpeedMaxLayer;
    private final byte[] scratch = new byte[NAL_START_CODE_LENGTH];
    private final Iterator<SlowMotionData.Segment> segmentIterator;

    @Nullable
    private final SlowMotionData slowMotionData;

    public static final class MetadataInfo {
        public float captureFrameRate = -3.4028235E38f;
        public int inputMaxLayer = -1;
        public int normalSpeedMaxLayer = -1;

        @Nullable
        public SlowMotionData slowMotionData;
    }

    public static final class SegmentInfo {
        public final long endTimeUs;
        public final int maxLayer;
        public final int speedDivisor;
        public final long startTimeUs;

        public SegmentInfo(SlowMotionData.Segment segment, int i2, int i3) {
            this.startTimeUs = Util.msToUs(segment.startTimeMs);
            this.endTimeUs = Util.msToUs(segment.endTimeMs);
            int i4 = segment.speedDivisor;
            this.speedDivisor = i4;
            this.maxLayer = getSlowMotionMaxLayer(i4, i2, i3);
        }

        private static int getSlowMotionMaxLayer(int i2, int i3, int i4) {
            int i5 = i2;
            while (true) {
                if (i5 <= 0) {
                    break;
                }
                if ((i5 & 1) == 1) {
                    boolean z2 = (i5 >> 1) == 0;
                    StringBuilder sb = new StringBuilder(34);
                    sb.append("Invalid speed divisor: ");
                    sb.append(i2);
                    Assertions.checkState(z2, sb.toString());
                } else {
                    i4++;
                    i5 >>= 1;
                }
            }
            return Math.min(i4, i3);
        }
    }

    public SefSlowMotionVideoSampleTransformer(Format format) {
        MetadataInfo metadataInfo = getMetadataInfo(format.metadata);
        SlowMotionData slowMotionData = metadataInfo.slowMotionData;
        this.slowMotionData = slowMotionData;
        Iterator<SlowMotionData.Segment> it = (slowMotionData != null ? slowMotionData.segments : ImmutableList.of()).iterator();
        this.segmentIterator = it;
        this.captureFrameRate = metadataInfo.captureFrameRate;
        int i2 = metadataInfo.inputMaxLayer;
        this.inputMaxLayer = i2;
        int i3 = metadataInfo.normalSpeedMaxLayer;
        this.normalSpeedMaxLayer = i3;
        this.nextSegmentInfo = it.hasNext() ? new SegmentInfo(it.next(), i2, i3) : null;
        if (slowMotionData != null) {
            boolean zEquals = MimeTypes.VIDEO_H264.equals(format.sampleMimeType);
            String strValueOf = String.valueOf(format.sampleMimeType);
            Assertions.checkArgument(zEquals, strValueOf.length() != 0 ? "Unsupported MIME type for SEF slow motion video track: ".concat(strValueOf) : new String("Unsupported MIME type for SEF slow motion video track: "));
        }
    }

    private void enterNextSegment() {
        if (this.currentSegmentInfo != null) {
            leaveCurrentSegment();
        }
        this.currentSegmentInfo = this.nextSegmentInfo;
        this.nextSegmentInfo = this.segmentIterator.hasNext() ? new SegmentInfo(this.segmentIterator.next(), this.inputMaxLayer, this.normalSpeedMaxLayer) : null;
    }

    private static MetadataInfo getMetadataInfo(@Nullable Metadata metadata) {
        MetadataInfo metadataInfo = new MetadataInfo();
        if (metadata == null) {
            return metadataInfo;
        }
        for (int i2 = 0; i2 < metadata.length(); i2++) {
            Metadata.Entry entry = metadata.get(i2);
            if (entry instanceof SmtaMetadataEntry) {
                SmtaMetadataEntry smtaMetadataEntry = (SmtaMetadataEntry) entry;
                metadataInfo.captureFrameRate = smtaMetadataEntry.captureFrameRate;
                metadataInfo.inputMaxLayer = smtaMetadataEntry.svcTemporalLayerCount - 1;
            } else if (entry instanceof SlowMotionData) {
                metadataInfo.slowMotionData = (SlowMotionData) entry;
            }
        }
        if (metadataInfo.slowMotionData == null) {
            return metadataInfo;
        }
        Assertions.checkState(metadataInfo.inputMaxLayer != -1, "SVC temporal layer count not found.");
        Assertions.checkState(metadataInfo.captureFrameRate != -3.4028235E38f, "Capture frame rate not found.");
        float f2 = metadataInfo.captureFrameRate;
        boolean z2 = f2 % 1.0f == 0.0f && f2 % 30.0f == 0.0f;
        StringBuilder sb = new StringBuilder(43);
        sb.append("Invalid capture frame rate: ");
        sb.append(f2);
        Assertions.checkState(z2, sb.toString());
        int i3 = ((int) metadataInfo.captureFrameRate) / 30;
        int i4 = metadataInfo.inputMaxLayer;
        while (true) {
            if (i4 < 0) {
                break;
            }
            if ((i3 & 1) == 1) {
                boolean z3 = (i3 >> 1) == 0;
                float f3 = metadataInfo.captureFrameRate;
                StringBuilder sb2 = new StringBuilder(84);
                sb2.append("Could not compute normal speed max SVC layer for capture frame rate  ");
                sb2.append(f3);
                Assertions.checkState(z3, sb2.toString());
                metadataInfo.normalSpeedMaxLayer = i4;
            } else {
                i3 >>= 1;
                i4--;
            }
        }
        return metadataInfo;
    }

    @RequiresNonNull({"currentSegmentInfo"})
    private void leaveCurrentSegment() {
        long j2 = this.frameTimeDeltaUs;
        SegmentInfo segmentInfo = this.currentSegmentInfo;
        this.frameTimeDeltaUs = j2 + ((segmentInfo.endTimeUs - segmentInfo.startTimeUs) * (segmentInfo.speedDivisor - 1));
        this.currentSegmentInfo = null;
    }

    private boolean shouldKeepFrameForOutputValidity(int i2, long j2) {
        int i3;
        SegmentInfo segmentInfo = this.nextSegmentInfo;
        if (segmentInfo != null && i2 < (i3 = segmentInfo.maxLayer)) {
            long j3 = ((segmentInfo.startTimeUs - j2) * 30) / 1000000;
            float f2 = (-(1 << (this.inputMaxLayer - i3))) + 0.45f;
            for (int i4 = 1; i4 < this.nextSegmentInfo.maxLayer && j3 < (1 << (this.inputMaxLayer - i4)) + f2; i4++) {
                if (i2 <= i4) {
                    return true;
                }
            }
        }
        return false;
    }

    private void skipToNextNalUnit(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        while (true) {
            int iRemaining = byteBuffer.remaining();
            int i2 = NAL_START_CODE_LENGTH;
            if (iRemaining < i2) {
                throw new IllegalStateException("Could not find NAL unit start code.");
            }
            byteBuffer.get(this.scratch, 0, i2);
            if (Arrays.equals(this.scratch, NalUnitUtil.NAL_START_CODE)) {
                byteBuffer.position(iPosition);
                return;
            } else {
                iPosition++;
                byteBuffer.position(iPosition);
            }
        }
    }

    @VisibleForTesting
    public long getCurrentFrameOutputTimeUs(long j2) {
        long j3 = this.frameTimeDeltaUs + j2;
        SegmentInfo segmentInfo = this.currentSegmentInfo;
        if (segmentInfo != null) {
            j3 += (j2 - segmentInfo.startTimeUs) * (segmentInfo.speedDivisor - 1);
        }
        return Math.round((j3 * 30) / this.captureFrameRate);
    }

    @VisibleForTesting
    public boolean processCurrentFrame(int i2, long j2) {
        SegmentInfo segmentInfo;
        while (true) {
            segmentInfo = this.nextSegmentInfo;
            if (segmentInfo == null || j2 < segmentInfo.endTimeUs) {
                break;
            }
            enterNextSegment();
        }
        if (segmentInfo == null || j2 < segmentInfo.startTimeUs) {
            SegmentInfo segmentInfo2 = this.currentSegmentInfo;
            if (segmentInfo2 != null && j2 >= segmentInfo2.endTimeUs) {
                leaveCurrentSegment();
            }
        } else {
            enterNextSegment();
        }
        SegmentInfo segmentInfo3 = this.currentSegmentInfo;
        return i2 <= (segmentInfo3 != null ? segmentInfo3.maxLayer : this.normalSpeedMaxLayer) || shouldKeepFrameForOutputValidity(i2, j2);
    }

    @Override // com.google.android.exoplayer2.transformer.SampleTransformer
    public void transformSample(DecoderInputBuffer decoderInputBuffer) {
        if (this.slowMotionData == null) {
            return;
        }
        ByteBuffer byteBuffer = (ByteBuffer) Util.castNonNull(decoderInputBuffer.data);
        byteBuffer.position(byteBuffer.position() + NAL_START_CODE_LENGTH);
        boolean z2 = false;
        byteBuffer.get(this.scratch, 0, 4);
        byte[] bArr = this.scratch;
        int i2 = bArr[0] & Ascii.US;
        boolean z3 = ((bArr[1] & 255) >> 7) == 1;
        if (i2 == 14 && z3) {
            z2 = true;
        }
        Assertions.checkState(z2, "Missing SVC extension prefix NAL unit.");
        if (!processCurrentFrame((this.scratch[3] & 255) >> 5, decoderInputBuffer.timeUs)) {
            decoderInputBuffer.data = null;
        } else {
            decoderInputBuffer.timeUs = getCurrentFrameOutputTimeUs(decoderInputBuffer.timeUs);
            skipToNextNalUnit(byteBuffer);
        }
    }
}
