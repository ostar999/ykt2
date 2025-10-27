package com.google.android.exoplayer2.source.mediaparser;

import android.annotation.SuppressLint;
import android.media.DrmInitData;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaParser;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.easefun.polyv.mediasdk.player.misc.IMediaFormat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiresApi(30)
@SuppressLint({"Override"})
/* loaded from: classes3.dex */
public final class OutputConsumerAdapterV30 implements MediaParser.OutputConsumer {
    private static final String MEDIA_FORMAT_KEY_CHUNK_INDEX_DURATIONS = "chunk-index-long-us-durations";
    private static final String MEDIA_FORMAT_KEY_CHUNK_INDEX_OFFSETS = "chunk-index-long-offsets";
    private static final String MEDIA_FORMAT_KEY_CHUNK_INDEX_SIZES = "chunk-index-int-sizes";
    private static final String MEDIA_FORMAT_KEY_CHUNK_INDEX_TIMES = "chunk-index-long-us-times";
    private static final String MEDIA_FORMAT_KEY_TRACK_TYPE = "track-type-string";
    private static final String TAG = "OutputConsumerAdapterV30";

    @Nullable
    private String containerMimeType;

    @Nullable
    private MediaParser.SeekMap dummySeekMap;
    private final boolean expectDummySeekMap;
    private ExtractorOutput extractorOutput;

    @Nullable
    private ChunkIndex lastChunkIndex;
    private final ArrayList<TrackOutput.CryptoData> lastOutputCryptoDatas;
    private final ArrayList<MediaCodec.CryptoInfo> lastReceivedCryptoInfos;

    @Nullable
    private MediaParser.SeekMap lastSeekMap;
    private List<Format> muxedCaptionFormats;
    private int primaryTrackIndex;

    @Nullable
    private final Format primaryTrackManifestFormat;
    private final int primaryTrackType;
    private long sampleTimestampUpperLimitFilterUs;
    private final DataReaderAdapter scratchDataReaderAdapter;
    private boolean seekingDisabled;

    @Nullable
    private TimestampAdjuster timestampAdjuster;
    private final ArrayList<Format> trackFormats;
    private final ArrayList<TrackOutput> trackOutputs;
    private boolean tracksEnded;
    private boolean tracksFoundCalled;
    private static final Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> SEEK_POINT_PAIR_START = Pair.create(MediaParser.SeekPoint.START, MediaParser.SeekPoint.START);
    private static final Pattern REGEX_CRYPTO_INFO_PATTERN = Pattern.compile("pattern \\(encrypt: (\\d+), skip: (\\d+)\\)");

    public static final class DataReaderAdapter implements DataReader {

        @Nullable
        public MediaParser.InputReader input;

        private DataReaderAdapter() {
        }

        @Override // com.google.android.exoplayer2.upstream.DataReader
        public int read(byte[] bArr, int i2, int i3) throws IOException {
            return ((MediaParser.InputReader) Util.castNonNull(this.input)).read(bArr, i2, i3);
        }
    }

    public static final class SeekMapAdapter implements SeekMap {
        private final MediaParser.SeekMap adaptedSeekMap;

        public SeekMapAdapter(MediaParser.SeekMap seekMap) {
            this.adaptedSeekMap = seekMap;
        }

        private static SeekPoint asExoPlayerSeekPoint(MediaParser.SeekPoint seekPoint) {
            return new SeekPoint(seekPoint.timeMicros, seekPoint.position);
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            long durationMicros = this.adaptedSeekMap.getDurationMicros();
            return durationMicros != -2147483648L ? durationMicros : C.TIME_UNSET;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long j2) {
            Pair seekPoints = this.adaptedSeekMap.getSeekPoints(j2);
            Object obj = seekPoints.first;
            return obj == seekPoints.second ? new SeekMap.SeekPoints(asExoPlayerSeekPoint((MediaParser.SeekPoint) obj)) : new SeekMap.SeekPoints(asExoPlayerSeekPoint((MediaParser.SeekPoint) obj), asExoPlayerSeekPoint((MediaParser.SeekPoint) seekPoints.second));
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return this.adaptedSeekMap.isSeekable();
        }
    }

    public OutputConsumerAdapterV30() {
        this(null, -2, false);
    }

    private void ensureSpaceForTrackIndex(int i2) {
        for (int size = this.trackOutputs.size(); size <= i2; size++) {
            this.trackOutputs.add(null);
            this.trackFormats.add(null);
            this.lastReceivedCryptoInfos.add(null);
            this.lastOutputCryptoDatas.add(null);
        }
    }

    private static byte[] getArray(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.get(bArr);
        return bArr;
    }

    @Nullable
    private static ColorInfo getColorInfo(MediaFormat mediaFormat) {
        ByteBuffer byteBuffer = mediaFormat.getByteBuffer("hdr-static-info");
        byte[] array = byteBuffer != null ? getArray(byteBuffer) : null;
        int integer = mediaFormat.getInteger("color-transfer", -1);
        int integer2 = mediaFormat.getInteger("color-range", -1);
        int integer3 = mediaFormat.getInteger("color-standard", -1);
        if (array == null && integer == -1 && integer2 == -1 && integer3 == -1) {
            return null;
        }
        return new ColorInfo(integer3, integer2, integer, array);
    }

    private static int getFlag(MediaFormat mediaFormat, String str, int i2) {
        if (mediaFormat.getInteger(str, 0) != 0) {
            return i2;
        }
        return 0;
    }

    private static List<byte[]> getInitializationData(MediaFormat mediaFormat) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            StringBuilder sb = new StringBuilder(15);
            sb.append("csd-");
            sb.append(i2);
            ByteBuffer byteBuffer = mediaFormat.getByteBuffer(sb.toString());
            if (byteBuffer == null) {
                return arrayList;
            }
            arrayList.add(getArray(byteBuffer));
            i2 = i3;
        }
    }

    private static String getMimeType(String str) {
        str.hashCode();
        switch (str) {
            case "android.media.mediaparser.Mp4Parser":
            case "android.media.mediaparser.FragmentedMp4Parser":
                return "video/mp4";
            case "android.media.mediaparser.OggParser":
                return "audio/ogg";
            case "android.media.mediaparser.TsParser":
                return MimeTypes.VIDEO_MP2T;
            case "android.media.mediaparser.AdtsParser":
                return MimeTypes.AUDIO_AAC;
            case "android.media.mediaparser.WavParser":
                return MimeTypes.AUDIO_RAW;
            case "android.media.mediaparser.PsParser":
                return MimeTypes.VIDEO_PS;
            case "android.media.mediaparser.Ac3Parser":
                return MimeTypes.AUDIO_AC3;
            case "android.media.mediaparser.AmrParser":
                return "audio/amr";
            case "android.media.mediaparser.FlacParser":
                return MimeTypes.AUDIO_FLAC;
            case "android.media.mediaparser.MatroskaParser":
                return "video/webm";
            case "android.media.mediaparser.Ac4Parser":
                return MimeTypes.AUDIO_AC4;
            case "android.media.mediaparser.Mp3Parser":
                return "audio/mpeg";
            case "android.media.mediaparser.FlvParser":
                return MimeTypes.VIDEO_FLV;
            default:
                throw new IllegalArgumentException(str.length() != 0 ? "Illegal parser name: ".concat(str) : new String("Illegal parser name: "));
        }
    }

    private static int getSelectionFlags(MediaFormat mediaFormat) {
        return getFlag(mediaFormat, "is-forced-subtitle", 2) | getFlag(mediaFormat, "is-autoselect", 4) | 0 | getFlag(mediaFormat, "is-default", 1);
    }

    private void maybeEndTracks() {
        if (!this.tracksFoundCalled || this.tracksEnded) {
            return;
        }
        int size = this.trackOutputs.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.trackOutputs.get(i2) == null) {
                return;
            }
        }
        this.extractorOutput.endTracks();
        this.tracksEnded = true;
    }

    private boolean maybeObtainChunkIndex(MediaFormat mediaFormat) {
        ByteBuffer byteBuffer = mediaFormat.getByteBuffer(MEDIA_FORMAT_KEY_CHUNK_INDEX_SIZES);
        if (byteBuffer == null) {
            return false;
        }
        IntBuffer intBufferAsIntBuffer = byteBuffer.asIntBuffer();
        LongBuffer longBufferAsLongBuffer = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer(MEDIA_FORMAT_KEY_CHUNK_INDEX_OFFSETS))).asLongBuffer();
        LongBuffer longBufferAsLongBuffer2 = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer(MEDIA_FORMAT_KEY_CHUNK_INDEX_DURATIONS))).asLongBuffer();
        LongBuffer longBufferAsLongBuffer3 = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer(MEDIA_FORMAT_KEY_CHUNK_INDEX_TIMES))).asLongBuffer();
        int[] iArr = new int[intBufferAsIntBuffer.remaining()];
        long[] jArr = new long[longBufferAsLongBuffer.remaining()];
        long[] jArr2 = new long[longBufferAsLongBuffer2.remaining()];
        long[] jArr3 = new long[longBufferAsLongBuffer3.remaining()];
        intBufferAsIntBuffer.get(iArr);
        longBufferAsLongBuffer.get(jArr);
        longBufferAsLongBuffer2.get(jArr2);
        longBufferAsLongBuffer3.get(jArr3);
        ChunkIndex chunkIndex = new ChunkIndex(iArr, jArr, jArr2, jArr3);
        this.lastChunkIndex = chunkIndex;
        this.extractorOutput.seekMap(chunkIndex);
        return true;
    }

    @Nullable
    private TrackOutput.CryptoData toExoPlayerCryptoData(int i2, @Nullable MediaCodec.CryptoInfo cryptoInfo) throws NumberFormatException {
        int i3;
        int i4;
        if (cryptoInfo == null) {
            return null;
        }
        if (this.lastReceivedCryptoInfos.get(i2) == cryptoInfo) {
            return (TrackOutput.CryptoData) Assertions.checkNotNull(this.lastOutputCryptoDatas.get(i2));
        }
        try {
            Matcher matcher = REGEX_CRYPTO_INFO_PATTERN.matcher(cryptoInfo.toString());
            matcher.find();
            i3 = Integer.parseInt((String) Util.castNonNull(matcher.group(1)));
            i4 = Integer.parseInt((String) Util.castNonNull(matcher.group(2)));
        } catch (RuntimeException e2) {
            String strValueOf = String.valueOf(cryptoInfo);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 43);
            sb.append("Unexpected error while parsing CryptoInfo: ");
            sb.append(strValueOf);
            Log.e(TAG, sb.toString(), e2);
            i3 = 0;
            i4 = 0;
        }
        TrackOutput.CryptoData cryptoData = new TrackOutput.CryptoData(cryptoInfo.mode, cryptoInfo.key, i3, i4);
        this.lastReceivedCryptoInfos.set(i2, cryptoInfo);
        this.lastOutputCryptoDatas.set(i2, cryptoData);
        return cryptoData;
    }

    @Nullable
    private static DrmInitData toExoPlayerDrmInitData(@Nullable String str, @Nullable android.media.DrmInitData drmInitData) {
        if (drmInitData == null) {
            return null;
        }
        int schemeInitDataCount = drmInitData.getSchemeInitDataCount();
        DrmInitData.SchemeData[] schemeDataArr = new DrmInitData.SchemeData[schemeInitDataCount];
        for (int i2 = 0; i2 < schemeInitDataCount; i2++) {
            DrmInitData.SchemeInitData schemeInitDataAt = drmInitData.getSchemeInitDataAt(i2);
            schemeDataArr[i2] = new DrmInitData.SchemeData(schemeInitDataAt.uuid, schemeInitDataAt.mimeType, schemeInitDataAt.data);
        }
        return new com.google.android.exoplayer2.drm.DrmInitData(str, schemeDataArr);
    }

    private Format toExoPlayerFormat(MediaParser.TrackData trackData) {
        MediaFormat mediaFormat = trackData.mediaFormat;
        String string = mediaFormat.getString(IMediaFormat.KEY_MIME);
        int integer = mediaFormat.getInteger("caption-service-number", -1);
        int i2 = 0;
        Format.Builder accessibilityChannel = new Format.Builder().setDrmInitData(toExoPlayerDrmInitData(mediaFormat.getString("crypto-mode-fourcc"), trackData.drmInitData)).setContainerMimeType(this.containerMimeType).setPeakBitrate(mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE, -1)).setChannelCount(mediaFormat.getInteger("channel-count", -1)).setColorInfo(getColorInfo(mediaFormat)).setSampleMimeType(string).setCodecs(mediaFormat.getString("codecs-string")).setFrameRate(mediaFormat.getFloat("frame-rate", -1.0f)).setWidth(mediaFormat.getInteger("width", -1)).setHeight(mediaFormat.getInteger("height", -1)).setInitializationData(getInitializationData(mediaFormat)).setLanguage(mediaFormat.getString("language")).setMaxInputSize(mediaFormat.getInteger("max-input-size", -1)).setPcmEncoding(mediaFormat.getInteger("exo-pcm-encoding", -1)).setRotationDegrees(mediaFormat.getInteger("rotation-degrees", 0)).setSampleRate(mediaFormat.getInteger("sample-rate", -1)).setSelectionFlags(getSelectionFlags(mediaFormat)).setEncoderDelay(mediaFormat.getInteger("encoder-delay", 0)).setEncoderPadding(mediaFormat.getInteger("encoder-padding", 0)).setPixelWidthHeightRatio(mediaFormat.getFloat("pixel-width-height-ratio-float", 1.0f)).setSubsampleOffsetUs(mediaFormat.getLong("subsample-offset-us-long", Long.MAX_VALUE)).setAccessibilityChannel(integer);
        while (true) {
            if (i2 >= this.muxedCaptionFormats.size()) {
                break;
            }
            Format format = this.muxedCaptionFormats.get(i2);
            if (Util.areEqual(format.sampleMimeType, string) && format.accessibilityChannel == integer) {
                accessibilityChannel.setLanguage(format.language).setRoleFlags(format.roleFlags).setSelectionFlags(format.selectionFlags).setLabel(format.label).setMetadata(format.metadata);
                break;
            }
            i2++;
        }
        return accessibilityChannel.build();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int toTrackTypeConstant(@androidx.annotation.Nullable java.lang.String r5) {
        /*
            r0 = -1
            if (r5 != 0) goto L4
            return r0
        L4:
            int r1 = r5.hashCode()
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r1) {
                case -450004177: goto L3c;
                case -284840886: goto L31;
                case 3556653: goto L26;
                case 93166550: goto L1b;
                case 112202875: goto L10;
                default: goto Le;
            }
        Le:
            r1 = r0
            goto L46
        L10:
            java.lang.String r1 = "video"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L19
            goto Le
        L19:
            r1 = 4
            goto L46
        L1b:
            java.lang.String r1 = "audio"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L24
            goto Le
        L24:
            r1 = r2
            goto L46
        L26:
            java.lang.String r1 = "text"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L2f
            goto Le
        L2f:
            r1 = r3
            goto L46
        L31:
            java.lang.String r1 = "unknown"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L3a
            goto Le
        L3a:
            r1 = r4
            goto L46
        L3c:
            java.lang.String r1 = "metadata"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L45
            goto Le
        L45:
            r1 = 0
        L46:
            switch(r1) {
                case 0: goto L52;
                case 1: goto L51;
                case 2: goto L50;
                case 3: goto L4f;
                case 4: goto L4e;
                default: goto L49;
            }
        L49:
            int r5 = com.google.android.exoplayer2.util.MimeTypes.getTrackType(r5)
            return r5
        L4e:
            return r3
        L4f:
            return r4
        L50:
            return r2
        L51:
            return r0
        L52:
            r5 = 5
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30.toTrackTypeConstant(java.lang.String):int");
    }

    public void disableSeeking() {
        this.seekingDisabled = true;
    }

    @Nullable
    public ChunkIndex getChunkIndex() {
        return this.lastChunkIndex;
    }

    @Nullable
    public MediaParser.SeekMap getDummySeekMap() {
        return this.dummySeekMap;
    }

    @Nullable
    public Format[] getSampleFormats() {
        if (!this.tracksFoundCalled) {
            return null;
        }
        Format[] formatArr = new Format[this.trackFormats.size()];
        for (int i2 = 0; i2 < this.trackFormats.size(); i2++) {
            formatArr[i2] = (Format) Assertions.checkNotNull(this.trackFormats.get(i2));
        }
        return formatArr;
    }

    public Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> getSeekPoints(long j2) {
        MediaParser.SeekMap seekMap = this.lastSeekMap;
        return seekMap != null ? seekMap.getSeekPoints(j2) : SEEK_POINT_PAIR_START;
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSampleCompleted(int i2, long j2, int i3, int i4, int i5, @Nullable MediaCodec.CryptoInfo cryptoInfo) {
        long j3 = this.sampleTimestampUpperLimitFilterUs;
        if (j3 == C.TIME_UNSET || j2 < j3) {
            TimestampAdjuster timestampAdjuster = this.timestampAdjuster;
            if (timestampAdjuster != null) {
                j2 = timestampAdjuster.adjustSampleTimestamp(j2);
            }
            ((TrackOutput) Assertions.checkNotNull(this.trackOutputs.get(i2))).sampleMetadata(j2, i3, i4, i5, toExoPlayerCryptoData(i2, cryptoInfo));
        }
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSampleDataFound(int i2, MediaParser.InputReader inputReader) throws IOException {
        ensureSpaceForTrackIndex(i2);
        this.scratchDataReaderAdapter.input = inputReader;
        TrackOutput trackOutputTrack = this.trackOutputs.get(i2);
        if (trackOutputTrack == null) {
            trackOutputTrack = this.extractorOutput.track(i2, -1);
            this.trackOutputs.set(i2, trackOutputTrack);
        }
        trackOutputTrack.sampleData((DataReader) this.scratchDataReaderAdapter, (int) inputReader.getLength(), true);
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSeekMapFound(MediaParser.SeekMap seekMap) {
        SeekMap seekMapAdapter;
        if (this.expectDummySeekMap && this.dummySeekMap == null) {
            this.dummySeekMap = seekMap;
            return;
        }
        this.lastSeekMap = seekMap;
        long durationMicros = seekMap.getDurationMicros();
        ExtractorOutput extractorOutput = this.extractorOutput;
        if (this.seekingDisabled) {
            if (durationMicros == -2147483648L) {
                durationMicros = C.TIME_UNSET;
            }
            seekMapAdapter = new SeekMap.Unseekable(durationMicros);
        } else {
            seekMapAdapter = new SeekMapAdapter(seekMap);
        }
        extractorOutput.seekMap(seekMapAdapter);
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onTrackCountFound(int i2) {
        this.tracksFoundCalled = true;
        maybeEndTracks();
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onTrackDataFound(int i2, MediaParser.TrackData trackData) {
        if (maybeObtainChunkIndex(trackData.mediaFormat)) {
            return;
        }
        ensureSpaceForTrackIndex(i2);
        TrackOutput trackOutput = this.trackOutputs.get(i2);
        if (trackOutput == null) {
            String string = trackData.mediaFormat.getString(MEDIA_FORMAT_KEY_TRACK_TYPE);
            int trackTypeConstant = toTrackTypeConstant(string != null ? string : trackData.mediaFormat.getString(IMediaFormat.KEY_MIME));
            if (trackTypeConstant == this.primaryTrackType) {
                this.primaryTrackIndex = i2;
            }
            TrackOutput trackOutputTrack = this.extractorOutput.track(i2, trackTypeConstant);
            this.trackOutputs.set(i2, trackOutputTrack);
            if (string != null) {
                return;
            } else {
                trackOutput = trackOutputTrack;
            }
        }
        Format exoPlayerFormat = toExoPlayerFormat(trackData);
        Format format = this.primaryTrackManifestFormat;
        trackOutput.format((format == null || i2 != this.primaryTrackIndex) ? exoPlayerFormat : exoPlayerFormat.withManifestFormatInfo(format));
        this.trackFormats.set(i2, exoPlayerFormat);
        maybeEndTracks();
    }

    public void setExtractorOutput(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
    }

    public void setMuxedCaptionFormats(List<Format> list) {
        this.muxedCaptionFormats = list;
    }

    public void setSampleTimestampUpperLimitFilterUs(long j2) {
        this.sampleTimestampUpperLimitFilterUs = j2;
    }

    public void setSelectedParserName(String str) {
        this.containerMimeType = getMimeType(str);
    }

    public void setTimestampAdjuster(TimestampAdjuster timestampAdjuster) {
        this.timestampAdjuster = timestampAdjuster;
    }

    public OutputConsumerAdapterV30(@Nullable Format format, int i2, boolean z2) {
        this.expectDummySeekMap = z2;
        this.primaryTrackManifestFormat = format;
        this.primaryTrackType = i2;
        this.trackOutputs = new ArrayList<>();
        this.trackFormats = new ArrayList<>();
        this.lastReceivedCryptoInfos = new ArrayList<>();
        this.lastOutputCryptoDatas = new ArrayList<>();
        this.scratchDataReaderAdapter = new DataReaderAdapter();
        this.extractorOutput = new DummyExtractorOutput();
        this.sampleTimestampUpperLimitFilterUs = C.TIME_UNSET;
        this.muxedCaptionFormats = ImmutableList.of();
    }
}
