package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.DtsUtil;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.flac.PictureFrame;
import com.google.android.exoplayer2.metadata.flac.VorbisComment;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class FlacStreamMetadata {
    public static final int NOT_IN_LOOKUP_TABLE = -1;
    private static final String SEPARATOR = "=";
    private static final String TAG = "FlacStreamMetadata";
    public final int bitsPerSample;
    public final int bitsPerSampleLookupKey;
    public final int channels;
    public final int maxBlockSizeSamples;
    public final int maxFrameSize;

    @Nullable
    private final Metadata metadata;
    public final int minBlockSizeSamples;
    public final int minFrameSize;
    public final int sampleRate;
    public final int sampleRateLookupKey;

    @Nullable
    public final SeekTable seekTable;
    public final long totalSamples;

    public static class SeekTable {
        public final long[] pointOffsets;
        public final long[] pointSampleNumbers;

        public SeekTable(long[] jArr, long[] jArr2) {
            this.pointSampleNumbers = jArr;
            this.pointOffsets = jArr2;
        }
    }

    public FlacStreamMetadata(byte[] bArr, int i2) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr);
        parsableBitArray.setPosition(i2 * 8);
        this.minBlockSizeSamples = parsableBitArray.readBits(16);
        this.maxBlockSizeSamples = parsableBitArray.readBits(16);
        this.minFrameSize = parsableBitArray.readBits(24);
        this.maxFrameSize = parsableBitArray.readBits(24);
        int bits = parsableBitArray.readBits(20);
        this.sampleRate = bits;
        this.sampleRateLookupKey = getSampleRateLookupKey(bits);
        this.channels = parsableBitArray.readBits(3) + 1;
        int bits2 = parsableBitArray.readBits(5) + 1;
        this.bitsPerSample = bits2;
        this.bitsPerSampleLookupKey = getBitsPerSampleLookupKey(bits2);
        this.totalSamples = parsableBitArray.readBitsToLong(36);
        this.seekTable = null;
        this.metadata = null;
    }

    @Nullable
    private static Metadata buildMetadata(List<String> list, List<PictureFrame> list2) {
        if (list.isEmpty() && list2.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = list.get(i2);
            String[] strArrSplitAtFirst = Util.splitAtFirst(str, "=");
            if (strArrSplitAtFirst.length != 2) {
                String strValueOf = String.valueOf(str);
                Log.w(TAG, strValueOf.length() != 0 ? "Failed to parse Vorbis comment: ".concat(strValueOf) : new String("Failed to parse Vorbis comment: "));
            } else {
                arrayList.add(new VorbisComment(strArrSplitAtFirst[0], strArrSplitAtFirst[1]));
            }
        }
        arrayList.addAll(list2);
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static int getBitsPerSampleLookupKey(int i2) {
        if (i2 == 8) {
            return 1;
        }
        if (i2 == 12) {
            return 2;
        }
        if (i2 == 16) {
            return 4;
        }
        if (i2 != 20) {
            return i2 != 24 ? -1 : 6;
        }
        return 5;
    }

    private static int getSampleRateLookupKey(int i2) {
        switch (i2) {
            case 8000:
                return 4;
            case 16000:
                return 5;
            case R2.layout.item_problem /* 22050 */:
                return 6;
            case R2.string.load_cost /* 24000 */:
                return 7;
            case 32000:
                return 8;
            case 44100:
                return 9;
            case 48000:
                return 10;
            case 88200:
                return 1;
            case 96000:
                return 11;
            case 176400:
                return 2;
            case DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND /* 192000 */:
                return 3;
            default:
                return -1;
        }
    }

    public FlacStreamMetadata copyWithPictureFrames(List<PictureFrame> list) {
        return new FlacStreamMetadata(this.minBlockSizeSamples, this.maxBlockSizeSamples, this.minFrameSize, this.maxFrameSize, this.sampleRate, this.channels, this.bitsPerSample, this.totalSamples, this.seekTable, getMetadataCopyWithAppendedEntriesFrom(buildMetadata(Collections.emptyList(), list)));
    }

    public FlacStreamMetadata copyWithSeekTable(@Nullable SeekTable seekTable) {
        return new FlacStreamMetadata(this.minBlockSizeSamples, this.maxBlockSizeSamples, this.minFrameSize, this.maxFrameSize, this.sampleRate, this.channels, this.bitsPerSample, this.totalSamples, seekTable, this.metadata);
    }

    public FlacStreamMetadata copyWithVorbisComments(List<String> list) {
        return new FlacStreamMetadata(this.minBlockSizeSamples, this.maxBlockSizeSamples, this.minFrameSize, this.maxFrameSize, this.sampleRate, this.channels, this.bitsPerSample, this.totalSamples, this.seekTable, getMetadataCopyWithAppendedEntriesFrom(buildMetadata(list, Collections.emptyList())));
    }

    public long getApproxBytesPerFrame() {
        long j2;
        long j3;
        int i2 = this.maxFrameSize;
        if (i2 > 0) {
            j2 = (i2 + this.minFrameSize) / 2;
            j3 = 1;
        } else {
            int i3 = this.minBlockSizeSamples;
            j2 = ((((i3 != this.maxBlockSizeSamples || i3 <= 0) ? 4096L : i3) * this.channels) * this.bitsPerSample) / 8;
            j3 = 64;
        }
        return j2 + j3;
    }

    public int getDecodedBitrate() {
        return this.bitsPerSample * this.sampleRate * this.channels;
    }

    public long getDurationUs() {
        long j2 = this.totalSamples;
        return j2 == 0 ? C.TIME_UNSET : (j2 * 1000000) / this.sampleRate;
    }

    public Format getFormat(byte[] bArr, @Nullable Metadata metadata) {
        bArr[4] = -128;
        int i2 = this.maxFrameSize;
        if (i2 <= 0) {
            i2 = -1;
        }
        return new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_FLAC).setMaxInputSize(i2).setChannelCount(this.channels).setSampleRate(this.sampleRate).setInitializationData(Collections.singletonList(bArr)).setMetadata(getMetadataCopyWithAppendedEntriesFrom(metadata)).build();
    }

    public int getMaxDecodedFrameSize() {
        return this.maxBlockSizeSamples * this.channels * (this.bitsPerSample / 8);
    }

    @Nullable
    public Metadata getMetadataCopyWithAppendedEntriesFrom(@Nullable Metadata metadata) {
        Metadata metadata2 = this.metadata;
        return metadata2 == null ? metadata : metadata2.copyWithAppendedEntriesFrom(metadata);
    }

    public long getSampleNumber(long j2) {
        return Util.constrainValue((j2 * this.sampleRate) / 1000000, 0L, this.totalSamples - 1);
    }

    public FlacStreamMetadata(int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j2, ArrayList<String> arrayList, ArrayList<PictureFrame> arrayList2) {
        this(i2, i3, i4, i5, i6, i7, i8, j2, (SeekTable) null, buildMetadata(arrayList, arrayList2));
    }

    private FlacStreamMetadata(int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j2, @Nullable SeekTable seekTable, @Nullable Metadata metadata) {
        this.minBlockSizeSamples = i2;
        this.maxBlockSizeSamples = i3;
        this.minFrameSize = i4;
        this.maxFrameSize = i5;
        this.sampleRate = i6;
        this.sampleRateLookupKey = getSampleRateLookupKey(i6);
        this.channels = i7;
        this.bitsPerSample = i8;
        this.bitsPerSampleLookupKey = getBitsPerSampleLookupKey(i8);
        this.totalSamples = j2;
        this.seekTable = seekTable;
        this.metadata = metadata;
    }
}
