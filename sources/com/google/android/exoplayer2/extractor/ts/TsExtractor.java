package com.google.android.exoplayer2.extractor.ts;

import android.net.Uri;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class TsExtractor implements Extractor {
    private static final long AC3_FORMAT_IDENTIFIER = 1094921523;
    private static final long AC4_FORMAT_IDENTIFIER = 1094921524;
    private static final int BUFFER_SIZE = 9400;
    public static final int DEFAULT_TIMESTAMP_SEARCH_BYTES = 112800;
    private static final long E_AC3_FORMAT_IDENTIFIER = 1161904947;
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() { // from class: com.google.android.exoplayer2.extractor.ts.e
        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public final Extractor[] createExtractors() {
            return TsExtractor.lambda$static$0();
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
            return com.google.android.exoplayer2.extractor.c.a(this, uri, map);
        }
    };
    private static final long HEVC_FORMAT_IDENTIFIER = 1212503619;
    private static final int MAX_PID_PLUS_ONE = 8192;
    public static final int MODE_HLS = 2;
    public static final int MODE_MULTI_PMT = 0;
    public static final int MODE_SINGLE_PMT = 1;
    private static final int SNIFF_TS_PACKET_COUNT = 5;
    public static final int TS_PACKET_SIZE = 188;
    private static final int TS_PAT_PID = 0;
    public static final int TS_STREAM_TYPE_AAC_ADTS = 15;
    public static final int TS_STREAM_TYPE_AAC_LATM = 17;
    public static final int TS_STREAM_TYPE_AC3 = 129;
    public static final int TS_STREAM_TYPE_AC4 = 172;
    public static final int TS_STREAM_TYPE_AIT = 257;
    public static final int TS_STREAM_TYPE_DC2_H262 = 128;
    public static final int TS_STREAM_TYPE_DTS = 138;
    public static final int TS_STREAM_TYPE_DVBSUBS = 89;
    public static final int TS_STREAM_TYPE_E_AC3 = 135;
    public static final int TS_STREAM_TYPE_H262 = 2;
    public static final int TS_STREAM_TYPE_H263 = 16;
    public static final int TS_STREAM_TYPE_H264 = 27;
    public static final int TS_STREAM_TYPE_H265 = 36;
    public static final int TS_STREAM_TYPE_HDMV_DTS = 130;
    public static final int TS_STREAM_TYPE_ID3 = 21;
    public static final int TS_STREAM_TYPE_MPA = 3;
    public static final int TS_STREAM_TYPE_MPA_LSF = 4;
    public static final int TS_STREAM_TYPE_SPLICE_INFO = 134;
    public static final int TS_SYNC_BYTE = 71;
    private int bytesSinceLastSync;
    private final SparseIntArray continuityCounters;
    private final TsDurationReader durationReader;
    private boolean hasOutputSeekMap;

    @Nullable
    private TsPayloadReader id3Reader;
    private final int mode;
    private ExtractorOutput output;
    private final TsPayloadReader.Factory payloadReaderFactory;
    private int pcrPid;
    private boolean pendingSeekToStart;
    private int remainingPmts;
    private final List<TimestampAdjuster> timestampAdjusters;
    private final int timestampSearchBytes;
    private final SparseBooleanArray trackIds;
    private final SparseBooleanArray trackPids;
    private boolean tracksEnded;
    private TsBinarySearchSeeker tsBinarySearchSeeker;
    private final ParsableByteArray tsPacketBuffer;
    private final SparseArray<TsPayloadReader> tsPayloadReaders;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public class PatReader implements SectionPayloadReader {
        private final ParsableBitArray patScratch = new ParsableBitArray(new byte[4]);

        public PatReader() {
        }

        @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
        public void consume(ParsableByteArray parsableByteArray) {
            if (parsableByteArray.readUnsignedByte() == 0 && (parsableByteArray.readUnsignedByte() & 128) != 0) {
                parsableByteArray.skipBytes(6);
                int iBytesLeft = parsableByteArray.bytesLeft() / 4;
                for (int i2 = 0; i2 < iBytesLeft; i2++) {
                    parsableByteArray.readBytes(this.patScratch, 4);
                    int bits = this.patScratch.readBits(16);
                    this.patScratch.skipBits(3);
                    if (bits == 0) {
                        this.patScratch.skipBits(13);
                    } else {
                        int bits2 = this.patScratch.readBits(13);
                        if (TsExtractor.this.tsPayloadReaders.get(bits2) == null) {
                            TsExtractor.this.tsPayloadReaders.put(bits2, new SectionReader(TsExtractor.this.new PmtReader(bits2)));
                            TsExtractor.access$108(TsExtractor.this);
                        }
                    }
                }
                if (TsExtractor.this.mode != 2) {
                    TsExtractor.this.tsPayloadReaders.remove(0);
                }
            }
        }

        @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        }
    }

    public class PmtReader implements SectionPayloadReader {
        private static final int TS_PMT_DESC_AC3 = 106;
        private static final int TS_PMT_DESC_AIT = 111;
        private static final int TS_PMT_DESC_DTS = 123;
        private static final int TS_PMT_DESC_DVBSUBS = 89;
        private static final int TS_PMT_DESC_DVB_EXT = 127;
        private static final int TS_PMT_DESC_DVB_EXT_AC4 = 21;
        private static final int TS_PMT_DESC_EAC3 = 122;
        private static final int TS_PMT_DESC_ISO639_LANG = 10;
        private static final int TS_PMT_DESC_REGISTRATION = 5;
        private final int pid;
        private final ParsableBitArray pmtScratch = new ParsableBitArray(new byte[5]);
        private final SparseArray<TsPayloadReader> trackIdToReaderScratch = new SparseArray<>();
        private final SparseIntArray trackIdToPidScratch = new SparseIntArray();

        public PmtReader(int i2) {
            this.pid = i2;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0043  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0055  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x005b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private com.google.android.exoplayer2.extractor.ts.TsPayloadReader.EsInfo readEsInfo(com.google.android.exoplayer2.util.ParsableByteArray r13, int r14) {
            /*
                r12 = this;
                int r0 = r13.getPosition()
                int r14 = r14 + r0
                r1 = -1
                r2 = 0
                r3 = r2
            L8:
                int r4 = r13.getPosition()
                if (r4 >= r14) goto Lbd
                int r4 = r13.readUnsignedByte()
                int r5 = r13.readUnsignedByte()
                int r6 = r13.getPosition()
                int r6 = r6 + r5
                if (r6 <= r14) goto L1f
                goto Lbd
            L1f:
                r5 = 5
                r7 = 172(0xac, float:2.41E-43)
                r8 = 135(0x87, float:1.89E-43)
                r9 = 129(0x81, float:1.81E-43)
                if (r4 != r5) goto L51
                long r4 = r13.readUnsignedInt()
                r10 = 1094921523(0x41432d33, double:5.409631094E-315)
                int r10 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
                if (r10 != 0) goto L34
                goto L55
            L34:
                r9 = 1161904947(0x45414333, double:5.74057318E-315)
                int r9 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
                if (r9 != 0) goto L3c
                goto L5b
            L3c:
                r8 = 1094921524(0x41432d34, double:5.4096311E-315)
                int r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r8 != 0) goto L46
            L43:
                r1 = r7
                goto Lb3
            L46:
                r7 = 1212503619(0x48455643, double:5.990563836E-315)
                int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
                if (r4 != 0) goto Lb3
                r1 = 36
                goto Lb3
            L51:
                r5 = 106(0x6a, float:1.49E-43)
                if (r4 != r5) goto L57
            L55:
                r1 = r9
                goto Lb3
            L57:
                r5 = 122(0x7a, float:1.71E-43)
                if (r4 != r5) goto L5d
            L5b:
                r1 = r8
                goto Lb3
            L5d:
                r5 = 127(0x7f, float:1.78E-43)
                if (r4 != r5) goto L6a
                int r4 = r13.readUnsignedByte()
                r5 = 21
                if (r4 != r5) goto Lb3
                goto L43
            L6a:
                r5 = 123(0x7b, float:1.72E-43)
                if (r4 != r5) goto L71
                r1 = 138(0x8a, float:1.93E-43)
                goto Lb3
            L71:
                r5 = 10
                r7 = 3
                if (r4 != r5) goto L7f
                java.lang.String r2 = r13.readString(r7)
                java.lang.String r2 = r2.trim()
                goto Lb3
            L7f:
                r5 = 89
                if (r4 != r5) goto Lad
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
            L88:
                int r3 = r13.getPosition()
                if (r3 >= r6) goto Laa
                java.lang.String r3 = r13.readString(r7)
                java.lang.String r3 = r3.trim()
                int r4 = r13.readUnsignedByte()
                r8 = 4
                byte[] r9 = new byte[r8]
                r10 = 0
                r13.readBytes(r9, r10, r8)
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$DvbSubtitleInfo r8 = new com.google.android.exoplayer2.extractor.ts.TsPayloadReader$DvbSubtitleInfo
                r8.<init>(r3, r4, r9)
                r1.add(r8)
                goto L88
            Laa:
                r3 = r1
                r1 = r5
                goto Lb3
            Lad:
                r5 = 111(0x6f, float:1.56E-43)
                if (r4 != r5) goto Lb3
                r1 = 257(0x101, float:3.6E-43)
            Lb3:
                int r4 = r13.getPosition()
                int r6 = r6 - r4
                r13.skipBytes(r6)
                goto L8
            Lbd:
                r13.setPosition(r14)
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$EsInfo r4 = new com.google.android.exoplayer2.extractor.ts.TsPayloadReader$EsInfo
                byte[] r13 = r13.getData()
                byte[] r13 = java.util.Arrays.copyOfRange(r13, r0, r14)
                r4.<init>(r1, r2, r3, r13)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.TsExtractor.PmtReader.readEsInfo(com.google.android.exoplayer2.util.ParsableByteArray, int):com.google.android.exoplayer2.extractor.ts.TsPayloadReader$EsInfo");
        }

        @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
        public void consume(ParsableByteArray parsableByteArray) {
            TimestampAdjuster timestampAdjuster;
            if (parsableByteArray.readUnsignedByte() != 2) {
                return;
            }
            if (TsExtractor.this.mode == 1 || TsExtractor.this.mode == 2 || TsExtractor.this.remainingPmts == 1) {
                timestampAdjuster = (TimestampAdjuster) TsExtractor.this.timestampAdjusters.get(0);
            } else {
                timestampAdjuster = new TimestampAdjuster(((TimestampAdjuster) TsExtractor.this.timestampAdjusters.get(0)).getFirstSampleTimestampUs());
                TsExtractor.this.timestampAdjusters.add(timestampAdjuster);
            }
            if ((parsableByteArray.readUnsignedByte() & 128) == 0) {
                return;
            }
            parsableByteArray.skipBytes(1);
            int unsignedShort = parsableByteArray.readUnsignedShort();
            int i2 = 3;
            parsableByteArray.skipBytes(3);
            parsableByteArray.readBytes(this.pmtScratch, 2);
            this.pmtScratch.skipBits(3);
            int i3 = 13;
            TsExtractor.this.pcrPid = this.pmtScratch.readBits(13);
            parsableByteArray.readBytes(this.pmtScratch, 2);
            int i4 = 4;
            this.pmtScratch.skipBits(4);
            parsableByteArray.skipBytes(this.pmtScratch.readBits(12));
            if (TsExtractor.this.mode == 2 && TsExtractor.this.id3Reader == null) {
                TsPayloadReader.EsInfo esInfo = new TsPayloadReader.EsInfo(21, null, null, Util.EMPTY_BYTE_ARRAY);
                TsExtractor tsExtractor = TsExtractor.this;
                tsExtractor.id3Reader = tsExtractor.payloadReaderFactory.createPayloadReader(21, esInfo);
                if (TsExtractor.this.id3Reader != null) {
                    TsExtractor.this.id3Reader.init(timestampAdjuster, TsExtractor.this.output, new TsPayloadReader.TrackIdGenerator(unsignedShort, 21, 8192));
                }
            }
            this.trackIdToReaderScratch.clear();
            this.trackIdToPidScratch.clear();
            int iBytesLeft = parsableByteArray.bytesLeft();
            while (iBytesLeft > 0) {
                parsableByteArray.readBytes(this.pmtScratch, 5);
                int bits = this.pmtScratch.readBits(8);
                this.pmtScratch.skipBits(i2);
                int bits2 = this.pmtScratch.readBits(i3);
                this.pmtScratch.skipBits(i4);
                int bits3 = this.pmtScratch.readBits(12);
                TsPayloadReader.EsInfo esInfo2 = readEsInfo(parsableByteArray, bits3);
                if (bits == 6 || bits == 5) {
                    bits = esInfo2.streamType;
                }
                iBytesLeft -= bits3 + 5;
                int i5 = TsExtractor.this.mode == 2 ? bits : bits2;
                if (!TsExtractor.this.trackIds.get(i5)) {
                    TsPayloadReader tsPayloadReaderCreatePayloadReader = (TsExtractor.this.mode == 2 && bits == 21) ? TsExtractor.this.id3Reader : TsExtractor.this.payloadReaderFactory.createPayloadReader(bits, esInfo2);
                    if (TsExtractor.this.mode != 2 || bits2 < this.trackIdToPidScratch.get(i5, 8192)) {
                        this.trackIdToPidScratch.put(i5, bits2);
                        this.trackIdToReaderScratch.put(i5, tsPayloadReaderCreatePayloadReader);
                    }
                }
                i2 = 3;
                i4 = 4;
                i3 = 13;
            }
            int size = this.trackIdToPidScratch.size();
            for (int i6 = 0; i6 < size; i6++) {
                int iKeyAt = this.trackIdToPidScratch.keyAt(i6);
                int iValueAt = this.trackIdToPidScratch.valueAt(i6);
                TsExtractor.this.trackIds.put(iKeyAt, true);
                TsExtractor.this.trackPids.put(iValueAt, true);
                TsPayloadReader tsPayloadReaderValueAt = this.trackIdToReaderScratch.valueAt(i6);
                if (tsPayloadReaderValueAt != null) {
                    if (tsPayloadReaderValueAt != TsExtractor.this.id3Reader) {
                        tsPayloadReaderValueAt.init(timestampAdjuster, TsExtractor.this.output, new TsPayloadReader.TrackIdGenerator(unsignedShort, iKeyAt, 8192));
                    }
                    TsExtractor.this.tsPayloadReaders.put(iValueAt, tsPayloadReaderValueAt);
                }
            }
            if (TsExtractor.this.mode == 2) {
                if (TsExtractor.this.tracksEnded) {
                    return;
                }
                TsExtractor.this.output.endTracks();
                TsExtractor.this.remainingPmts = 0;
                TsExtractor.this.tracksEnded = true;
                return;
            }
            TsExtractor.this.tsPayloadReaders.remove(this.pid);
            TsExtractor tsExtractor2 = TsExtractor.this;
            tsExtractor2.remainingPmts = tsExtractor2.mode == 1 ? 0 : TsExtractor.this.remainingPmts - 1;
            if (TsExtractor.this.remainingPmts == 0) {
                TsExtractor.this.output.endTracks();
                TsExtractor.this.tracksEnded = true;
            }
        }

        @Override // com.google.android.exoplayer2.extractor.ts.SectionPayloadReader
        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        }
    }

    public TsExtractor() {
        this(0);
    }

    public static /* synthetic */ int access$108(TsExtractor tsExtractor) {
        int i2 = tsExtractor.remainingPmts;
        tsExtractor.remainingPmts = i2 + 1;
        return i2;
    }

    private boolean fillBufferWithAtLeastOnePacket(ExtractorInput extractorInput) throws IOException {
        byte[] data = this.tsPacketBuffer.getData();
        if (9400 - this.tsPacketBuffer.getPosition() < 188) {
            int iBytesLeft = this.tsPacketBuffer.bytesLeft();
            if (iBytesLeft > 0) {
                System.arraycopy(data, this.tsPacketBuffer.getPosition(), data, 0, iBytesLeft);
            }
            this.tsPacketBuffer.reset(data, iBytesLeft);
        }
        while (this.tsPacketBuffer.bytesLeft() < 188) {
            int iLimit = this.tsPacketBuffer.limit();
            int i2 = extractorInput.read(data, iLimit, 9400 - iLimit);
            if (i2 == -1) {
                return false;
            }
            this.tsPacketBuffer.setLimit(iLimit + i2);
        }
        return true;
    }

    private int findEndOfFirstTsPacketInBuffer() throws ParserException {
        int position = this.tsPacketBuffer.getPosition();
        int iLimit = this.tsPacketBuffer.limit();
        int iFindSyncBytePosition = TsUtil.findSyncBytePosition(this.tsPacketBuffer.getData(), position, iLimit);
        this.tsPacketBuffer.setPosition(iFindSyncBytePosition);
        int i2 = iFindSyncBytePosition + 188;
        if (i2 > iLimit) {
            int i3 = this.bytesSinceLastSync + (iFindSyncBytePosition - position);
            this.bytesSinceLastSync = i3;
            if (this.mode == 2 && i3 > 376) {
                throw ParserException.createForMalformedContainer("Cannot find sync byte. Most likely not a Transport Stream.", null);
            }
        } else {
            this.bytesSinceLastSync = 0;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new TsExtractor()};
    }

    private void maybeOutputSeekMap(long j2) {
        if (this.hasOutputSeekMap) {
            return;
        }
        this.hasOutputSeekMap = true;
        if (this.durationReader.getDurationUs() == C.TIME_UNSET) {
            this.output.seekMap(new SeekMap.Unseekable(this.durationReader.getDurationUs()));
            return;
        }
        TsBinarySearchSeeker tsBinarySearchSeeker = new TsBinarySearchSeeker(this.durationReader.getPcrTimestampAdjuster(), this.durationReader.getDurationUs(), j2, this.pcrPid, this.timestampSearchBytes);
        this.tsBinarySearchSeeker = tsBinarySearchSeeker;
        this.output.seekMap(tsBinarySearchSeeker.getSeekMap());
    }

    private void resetPayloadReaders() {
        this.trackIds.clear();
        this.tsPayloadReaders.clear();
        SparseArray<TsPayloadReader> sparseArrayCreateInitialPayloadReaders = this.payloadReaderFactory.createInitialPayloadReaders();
        int size = sparseArrayCreateInitialPayloadReaders.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.tsPayloadReaders.put(sparseArrayCreateInitialPayloadReaders.keyAt(i2), sparseArrayCreateInitialPayloadReaders.valueAt(i2));
        }
        this.tsPayloadReaders.put(0, new SectionReader(new PatReader()));
        this.id3Reader = null;
    }

    private boolean shouldConsumePacketPayload(int i2) {
        return this.mode == 2 || this.tracksEnded || !this.trackPids.get(i2, false);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.output = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        long length = extractorInput.getLength();
        if (this.tracksEnded) {
            if (((length == -1 || this.mode == 2) ? false : true) && !this.durationReader.isDurationReadFinished()) {
                return this.durationReader.readDuration(extractorInput, positionHolder, this.pcrPid);
            }
            maybeOutputSeekMap(length);
            if (this.pendingSeekToStart) {
                this.pendingSeekToStart = false;
                seek(0L, 0L);
                if (extractorInput.getPosition() != 0) {
                    positionHolder.position = 0L;
                    return 1;
                }
            }
            TsBinarySearchSeeker tsBinarySearchSeeker = this.tsBinarySearchSeeker;
            if (tsBinarySearchSeeker != null && tsBinarySearchSeeker.isSeeking()) {
                return this.tsBinarySearchSeeker.handlePendingSeek(extractorInput, positionHolder);
            }
        }
        if (!fillBufferWithAtLeastOnePacket(extractorInput)) {
            return -1;
        }
        int iFindEndOfFirstTsPacketInBuffer = findEndOfFirstTsPacketInBuffer();
        int iLimit = this.tsPacketBuffer.limit();
        if (iFindEndOfFirstTsPacketInBuffer > iLimit) {
            return 0;
        }
        int i2 = this.tsPacketBuffer.readInt();
        if ((8388608 & i2) != 0) {
            this.tsPacketBuffer.setPosition(iFindEndOfFirstTsPacketInBuffer);
            return 0;
        }
        int i3 = ((4194304 & i2) != 0 ? 1 : 0) | 0;
        int i4 = (2096896 & i2) >> 8;
        boolean z2 = (i2 & 32) != 0;
        TsPayloadReader tsPayloadReader = (i2 & 16) != 0 ? this.tsPayloadReaders.get(i4) : null;
        if (tsPayloadReader == null) {
            this.tsPacketBuffer.setPosition(iFindEndOfFirstTsPacketInBuffer);
            return 0;
        }
        if (this.mode != 2) {
            int i5 = i2 & 15;
            int i6 = this.continuityCounters.get(i4, i5 - 1);
            this.continuityCounters.put(i4, i5);
            if (i6 == i5) {
                this.tsPacketBuffer.setPosition(iFindEndOfFirstTsPacketInBuffer);
                return 0;
            }
            if (i5 != ((i6 + 1) & 15)) {
                tsPayloadReader.seek();
            }
        }
        if (z2) {
            int unsignedByte = this.tsPacketBuffer.readUnsignedByte();
            i3 |= (this.tsPacketBuffer.readUnsignedByte() & 64) != 0 ? 2 : 0;
            this.tsPacketBuffer.skipBytes(unsignedByte - 1);
        }
        boolean z3 = this.tracksEnded;
        if (shouldConsumePacketPayload(i4)) {
            this.tsPacketBuffer.setLimit(iFindEndOfFirstTsPacketInBuffer);
            tsPayloadReader.consume(this.tsPacketBuffer, i3);
            this.tsPacketBuffer.setLimit(iLimit);
        }
        if (this.mode != 2 && !z3 && this.tracksEnded && length != -1) {
            this.pendingSeekToStart = true;
        }
        this.tsPacketBuffer.setPosition(iFindEndOfFirstTsPacketInBuffer);
        return 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j2, long j3) {
        TsBinarySearchSeeker tsBinarySearchSeeker;
        Assertions.checkState(this.mode != 2);
        int size = this.timestampAdjusters.size();
        for (int i2 = 0; i2 < size; i2++) {
            TimestampAdjuster timestampAdjuster = this.timestampAdjusters.get(i2);
            boolean z2 = timestampAdjuster.getTimestampOffsetUs() == C.TIME_UNSET;
            if (!z2) {
                long firstSampleTimestampUs = timestampAdjuster.getFirstSampleTimestampUs();
                z2 = (firstSampleTimestampUs == C.TIME_UNSET || firstSampleTimestampUs == 0 || firstSampleTimestampUs == j3) ? false : true;
            }
            if (z2) {
                timestampAdjuster.reset(j3);
            }
        }
        if (j3 != 0 && (tsBinarySearchSeeker = this.tsBinarySearchSeeker) != null) {
            tsBinarySearchSeeker.setSeekTargetUs(j3);
        }
        this.tsPacketBuffer.reset(0);
        this.continuityCounters.clear();
        for (int i3 = 0; i3 < this.tsPayloadReaders.size(); i3++) {
            this.tsPayloadReaders.valueAt(i3).seek();
        }
        this.bytesSinceLastSync = 0;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        boolean z2;
        byte[] data = this.tsPacketBuffer.getData();
        extractorInput.peekFully(data, 0, R2.attr.chipMinHeight);
        for (int i2 = 0; i2 < 188; i2++) {
            int i3 = 0;
            while (true) {
                if (i3 >= 5) {
                    z2 = true;
                    break;
                }
                if (data[(i3 * 188) + i2] != 71) {
                    z2 = false;
                    break;
                }
                i3++;
            }
            if (z2) {
                extractorInput.skipFully(i2);
                return true;
            }
        }
        return false;
    }

    public TsExtractor(int i2) {
        this(1, i2, DEFAULT_TIMESTAMP_SEARCH_BYTES);
    }

    public TsExtractor(int i2, int i3, int i4) {
        this(i2, new TimestampAdjuster(0L), new DefaultTsPayloadReaderFactory(i3), i4);
    }

    public TsExtractor(int i2, TimestampAdjuster timestampAdjuster, TsPayloadReader.Factory factory) {
        this(i2, timestampAdjuster, factory, DEFAULT_TIMESTAMP_SEARCH_BYTES);
    }

    public TsExtractor(int i2, TimestampAdjuster timestampAdjuster, TsPayloadReader.Factory factory, int i3) {
        this.payloadReaderFactory = (TsPayloadReader.Factory) Assertions.checkNotNull(factory);
        this.timestampSearchBytes = i3;
        this.mode = i2;
        if (i2 != 1 && i2 != 2) {
            ArrayList arrayList = new ArrayList();
            this.timestampAdjusters = arrayList;
            arrayList.add(timestampAdjuster);
        } else {
            this.timestampAdjusters = Collections.singletonList(timestampAdjuster);
        }
        this.tsPacketBuffer = new ParsableByteArray(new byte[9400], 0);
        this.trackIds = new SparseBooleanArray();
        this.trackPids = new SparseBooleanArray();
        this.tsPayloadReaders = new SparseArray<>();
        this.continuityCounters = new SparseIntArray();
        this.durationReader = new TsDurationReader(i3);
        this.output = ExtractorOutput.PLACEHOLDER;
        this.pcrPid = -1;
        resetPayloadReaders();
    }
}
