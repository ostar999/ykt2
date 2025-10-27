package com.google.android.exoplayer2.extractor.mp4;

import android.net.Uri;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.TrueHdSampleRechunker;
import com.google.android.exoplayer2.extractor.mp4.Atom;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Function;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class Mp4Extractor implements Extractor, SeekMap {
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() { // from class: com.google.android.exoplayer2.extractor.mp4.c
        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public final Extractor[] createExtractors() {
            return Mp4Extractor.lambda$static$0();
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
            return com.google.android.exoplayer2.extractor.c.a(this, uri, map);
        }
    };
    private static final int FILE_TYPE_HEIC = 2;
    private static final int FILE_TYPE_MP4 = 0;
    private static final int FILE_TYPE_QUICKTIME = 1;
    public static final int FLAG_READ_MOTION_PHOTO_METADATA = 2;
    public static final int FLAG_READ_SEF_DATA = 4;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 1;
    private static final long MAXIMUM_READ_AHEAD_BYTES_STREAM = 10485760;
    private static final long RELOAD_MINIMUM_SEEK_DISTANCE = 262144;
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_SAMPLE = 2;
    private static final int STATE_READING_SEF = 3;
    private long[][] accumulatedSampleSizes;

    @Nullable
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private final ArrayDeque<Atom.ContainerAtom> containerAtoms;
    private long durationUs;
    private ExtractorOutput extractorOutput;
    private int fileType;
    private int firstVideoTrackIndex;
    private final int flags;

    @Nullable
    private MotionPhotoMetadata motionPhotoMetadata;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private int parserState;
    private int sampleBytesRead;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleTrackIndex;
    private final ParsableByteArray scratch;
    private final SefReader sefReader;
    private final List<Metadata.Entry> slowMotionMetadataEntries;
    private Mp4Track[] tracks;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static final class Mp4Track {
        public int sampleIndex;
        public final TrackSampleTable sampleTable;
        public final Track track;
        public final TrackOutput trackOutput;

        @Nullable
        public final TrueHdSampleRechunker trueHdSampleRechunker;

        public Mp4Track(Track track, TrackSampleTable trackSampleTable, TrackOutput trackOutput) {
            this.track = track;
            this.sampleTable = trackSampleTable;
            this.trackOutput = trackOutput;
            this.trueHdSampleRechunker = MimeTypes.AUDIO_TRUEHD.equals(track.format.sampleMimeType) ? new TrueHdSampleRechunker() : null;
        }
    }

    public Mp4Extractor() {
        this(0);
    }

    private static int brandToFileType(int i2) {
        if (i2 != 1751476579) {
            return i2 != 1903435808 ? 0 : 1;
        }
        return 2;
    }

    private static long[][] calculateAccumulatedSampleSizes(Mp4Track[] mp4TrackArr) {
        long[][] jArr = new long[mp4TrackArr.length][];
        int[] iArr = new int[mp4TrackArr.length];
        long[] jArr2 = new long[mp4TrackArr.length];
        boolean[] zArr = new boolean[mp4TrackArr.length];
        for (int i2 = 0; i2 < mp4TrackArr.length; i2++) {
            jArr[i2] = new long[mp4TrackArr[i2].sampleTable.sampleCount];
            jArr2[i2] = mp4TrackArr[i2].sampleTable.timestampsUs[0];
        }
        long j2 = 0;
        int i3 = 0;
        while (i3 < mp4TrackArr.length) {
            long j3 = Long.MAX_VALUE;
            int i4 = -1;
            for (int i5 = 0; i5 < mp4TrackArr.length; i5++) {
                if (!zArr[i5]) {
                    long j4 = jArr2[i5];
                    if (j4 <= j3) {
                        i4 = i5;
                        j3 = j4;
                    }
                }
            }
            int i6 = iArr[i4];
            long[] jArr3 = jArr[i4];
            jArr3[i6] = j2;
            TrackSampleTable trackSampleTable = mp4TrackArr[i4].sampleTable;
            j2 += trackSampleTable.sizes[i6];
            int i7 = i6 + 1;
            iArr[i4] = i7;
            if (i7 < jArr3.length) {
                jArr2[i4] = trackSampleTable.timestampsUs[i7];
            } else {
                zArr[i4] = true;
                i3++;
            }
        }
        return jArr;
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private static int getSynchronizationSampleIndex(TrackSampleTable trackSampleTable, long j2) {
        int indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfEarlierOrEqualSynchronizationSample(j2);
        return indexOfEarlierOrEqualSynchronizationSample == -1 ? trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(j2) : indexOfEarlierOrEqualSynchronizationSample;
    }

    private int getTrackIndexOfNextReadSample(long j2) {
        int i2 = -1;
        int i3 = -1;
        long j3 = Long.MAX_VALUE;
        boolean z2 = true;
        long j4 = Long.MAX_VALUE;
        boolean z3 = true;
        long j5 = Long.MAX_VALUE;
        for (int i4 = 0; i4 < ((Mp4Track[]) Util.castNonNull(this.tracks)).length; i4++) {
            Mp4Track mp4Track = this.tracks[i4];
            int i5 = mp4Track.sampleIndex;
            TrackSampleTable trackSampleTable = mp4Track.sampleTable;
            if (i5 != trackSampleTable.sampleCount) {
                long j6 = trackSampleTable.offsets[i5];
                long j7 = ((long[][]) Util.castNonNull(this.accumulatedSampleSizes))[i4][i5];
                long j8 = j6 - j2;
                boolean z4 = j8 < 0 || j8 >= 262144;
                if ((!z4 && z3) || (z4 == z3 && j8 < j5)) {
                    z3 = z4;
                    j5 = j8;
                    i3 = i4;
                    j4 = j7;
                }
                if (j7 < j3) {
                    z2 = z4;
                    i2 = i4;
                    j3 = j7;
                }
            }
        }
        return (j3 == Long.MAX_VALUE || !z2 || j4 < j3 + MAXIMUM_READ_AHEAD_BYTES_STREAM) ? i3 : i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Track lambda$processMoovAtom$1(Track track) {
        return track;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new Mp4Extractor()};
    }

    private static long maybeAdjustSeekOffset(TrackSampleTable trackSampleTable, long j2, long j3) {
        int synchronizationSampleIndex = getSynchronizationSampleIndex(trackSampleTable, j2);
        return synchronizationSampleIndex == -1 ? j3 : Math.min(trackSampleTable.offsets[synchronizationSampleIndex], j3);
    }

    private void maybeSkipRemainingMetaAtomHeaderBytes(ExtractorInput extractorInput) throws IOException {
        this.scratch.reset(8);
        extractorInput.peekFully(this.scratch.getData(), 0, 8);
        AtomParsers.maybeSkipRemainingMetaAtomHeaderBytes(this.scratch);
        extractorInput.skipFully(this.scratch.getPosition());
        extractorInput.resetPeekPosition();
    }

    private void processAtomEnded(long j2) throws ParserException {
        while (!this.containerAtoms.isEmpty() && this.containerAtoms.peek().endPosition == j2) {
            Atom.ContainerAtom containerAtomPop = this.containerAtoms.pop();
            if (containerAtomPop.type == 1836019574) {
                processMoovAtom(containerAtomPop);
                this.containerAtoms.clear();
                this.parserState = 2;
            } else if (!this.containerAtoms.isEmpty()) {
                this.containerAtoms.peek().add(containerAtomPop);
            }
        }
        if (this.parserState != 2) {
            enterReadingAtomHeaderState();
        }
    }

    private void processEndOfStreamReadingAtomHeader() {
        if (this.fileType != 2 || (this.flags & 2) == 0) {
            return;
        }
        ExtractorOutput extractorOutput = (ExtractorOutput) Assertions.checkNotNull(this.extractorOutput);
        extractorOutput.track(0, 4).format(new Format.Builder().setMetadata(this.motionPhotoMetadata == null ? null : new Metadata(this.motionPhotoMetadata)).build());
        extractorOutput.endTracks();
        extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
    }

    private static int processFtypAtom(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        int iBrandToFileType = brandToFileType(parsableByteArray.readInt());
        if (iBrandToFileType != 0) {
            return iBrandToFileType;
        }
        parsableByteArray.skipBytes(4);
        while (parsableByteArray.bytesLeft() > 0) {
            int iBrandToFileType2 = brandToFileType(parsableByteArray.readInt());
            if (iBrandToFileType2 != 0) {
                return iBrandToFileType2;
            }
        }
        return 0;
    }

    private void processMoovAtom(Atom.ContainerAtom containerAtom) throws ParserException {
        Metadata metadata;
        Metadata metadata2;
        ArrayList arrayList;
        List<TrackSampleTable> list;
        int i2;
        int i3;
        ArrayList arrayList2 = new ArrayList();
        boolean z2 = this.fileType == 1;
        GaplessInfoHolder gaplessInfoHolder = new GaplessInfoHolder();
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_udta);
        if (leafAtomOfType != null) {
            Pair<Metadata, Metadata> udta = AtomParsers.parseUdta(leafAtomOfType);
            Metadata metadata3 = (Metadata) udta.first;
            Metadata metadata4 = (Metadata) udta.second;
            if (metadata3 != null) {
                gaplessInfoHolder.setFromMetadata(metadata3);
            }
            metadata = metadata4;
            metadata2 = metadata3;
        } else {
            metadata = null;
            metadata2 = null;
        }
        Atom.ContainerAtom containerAtomOfType = containerAtom.getContainerAtomOfType(Atom.TYPE_meta);
        Metadata mdtaFromMeta = containerAtomOfType != null ? AtomParsers.parseMdtaFromMeta(containerAtomOfType) : null;
        List<TrackSampleTable> traks = AtomParsers.parseTraks(containerAtom, gaplessInfoHolder, C.TIME_UNSET, null, (this.flags & 1) != 0, z2, new Function() { // from class: com.google.android.exoplayer2.extractor.mp4.d
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return Mp4Extractor.lambda$processMoovAtom$1((Track) obj);
            }
        });
        ExtractorOutput extractorOutput = (ExtractorOutput) Assertions.checkNotNull(this.extractorOutput);
        int size = traks.size();
        int i4 = 0;
        int i5 = -1;
        long j2 = C.TIME_UNSET;
        while (i4 < size) {
            TrackSampleTable trackSampleTable = traks.get(i4);
            if (trackSampleTable.sampleCount == 0) {
                list = traks;
                i2 = size;
                arrayList = arrayList2;
            } else {
                Track track = trackSampleTable.track;
                int i6 = i5;
                arrayList = arrayList2;
                long j3 = track.durationUs;
                if (j3 == C.TIME_UNSET) {
                    j3 = trackSampleTable.durationUs;
                }
                long jMax = Math.max(j2, j3);
                list = traks;
                i2 = size;
                Mp4Track mp4Track = new Mp4Track(track, trackSampleTable, extractorOutput.track(i4, track.type));
                int i7 = MimeTypes.AUDIO_TRUEHD.equals(track.format.sampleMimeType) ? trackSampleTable.maximumSize * 16 : trackSampleTable.maximumSize + 30;
                Format.Builder builderBuildUpon = track.format.buildUpon();
                builderBuildUpon.setMaxInputSize(i7);
                if (track.type == 2 && j3 > 0 && (i3 = trackSampleTable.sampleCount) > 1) {
                    builderBuildUpon.setFrameRate(i3 / (j3 / 1000000.0f));
                }
                MetadataUtil.setFormatGaplessInfo(track.type, gaplessInfoHolder, builderBuildUpon);
                int i8 = track.type;
                Metadata[] metadataArr = new Metadata[2];
                metadataArr[0] = metadata;
                metadataArr[1] = this.slowMotionMetadataEntries.isEmpty() ? null : new Metadata(this.slowMotionMetadataEntries);
                MetadataUtil.setFormatMetadata(i8, metadata2, mdtaFromMeta, builderBuildUpon, metadataArr);
                mp4Track.trackOutput.format(builderBuildUpon.build());
                int size2 = i6;
                if (track.type == 2 && size2 == -1) {
                    size2 = arrayList.size();
                }
                i5 = size2;
                arrayList.add(mp4Track);
                j2 = jMax;
            }
            i4++;
            arrayList2 = arrayList;
            traks = list;
            size = i2;
        }
        this.firstVideoTrackIndex = i5;
        this.durationUs = j2;
        Mp4Track[] mp4TrackArr = (Mp4Track[]) arrayList2.toArray(new Mp4Track[0]);
        this.tracks = mp4TrackArr;
        this.accumulatedSampleSizes = calculateAccumulatedSampleSizes(mp4TrackArr);
        extractorOutput.endTracks();
        extractorOutput.seekMap(this);
    }

    private void processUnparsedAtom(long j2) {
        if (this.atomType == 1836086884) {
            int i2 = this.atomHeaderBytesRead;
            this.motionPhotoMetadata = new MotionPhotoMetadata(0L, j2, C.TIME_UNSET, j2 + i2, this.atomSize - i2);
        }
    }

    private boolean readAtomHeader(ExtractorInput extractorInput) throws IOException {
        Atom.ContainerAtom containerAtomPeek;
        if (this.atomHeaderBytesRead == 0) {
            if (!extractorInput.readFully(this.atomHeader.getData(), 0, 8, true)) {
                processEndOfStreamReadingAtomHeader();
                return false;
            }
            this.atomHeaderBytesRead = 8;
            this.atomHeader.setPosition(0);
            this.atomSize = this.atomHeader.readUnsignedInt();
            this.atomType = this.atomHeader.readInt();
        }
        long j2 = this.atomSize;
        if (j2 == 1) {
            extractorInput.readFully(this.atomHeader.getData(), 8, 8);
            this.atomHeaderBytesRead += 8;
            this.atomSize = this.atomHeader.readUnsignedLongToLong();
        } else if (j2 == 0) {
            long length = extractorInput.getLength();
            if (length == -1 && (containerAtomPeek = this.containerAtoms.peek()) != null) {
                length = containerAtomPeek.endPosition;
            }
            if (length != -1) {
                this.atomSize = (length - extractorInput.getPosition()) + this.atomHeaderBytesRead;
            }
        }
        if (this.atomSize < this.atomHeaderBytesRead) {
            throw ParserException.createForUnsupportedContainerFeature("Atom size less than header length (unsupported).");
        }
        if (shouldParseContainerAtom(this.atomType)) {
            long position = extractorInput.getPosition();
            long j3 = this.atomSize;
            int i2 = this.atomHeaderBytesRead;
            long j4 = (position + j3) - i2;
            if (j3 != i2 && this.atomType == 1835365473) {
                maybeSkipRemainingMetaAtomHeaderBytes(extractorInput);
            }
            this.containerAtoms.push(new Atom.ContainerAtom(this.atomType, j4));
            if (this.atomSize == this.atomHeaderBytesRead) {
                processAtomEnded(j4);
            } else {
                enterReadingAtomHeaderState();
            }
        } else if (shouldParseLeafAtom(this.atomType)) {
            Assertions.checkState(this.atomHeaderBytesRead == 8);
            Assertions.checkState(this.atomSize <= 2147483647L);
            ParsableByteArray parsableByteArray = new ParsableByteArray((int) this.atomSize);
            System.arraycopy(this.atomHeader.getData(), 0, parsableByteArray.getData(), 0, 8);
            this.atomData = parsableByteArray;
            this.parserState = 1;
        } else {
            processUnparsedAtom(extractorInput.getPosition() - this.atomHeaderBytesRead);
            this.atomData = null;
            this.parserState = 1;
        }
        return true;
    }

    private boolean readAtomPayload(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z2;
        long j2 = this.atomSize - this.atomHeaderBytesRead;
        long position = extractorInput.getPosition() + j2;
        ParsableByteArray parsableByteArray = this.atomData;
        if (parsableByteArray != null) {
            extractorInput.readFully(parsableByteArray.getData(), this.atomHeaderBytesRead, (int) j2);
            if (this.atomType == 1718909296) {
                this.fileType = processFtypAtom(parsableByteArray);
            } else if (!this.containerAtoms.isEmpty()) {
                this.containerAtoms.peek().add(new Atom.LeafAtom(this.atomType, parsableByteArray));
            }
        } else {
            if (j2 >= 262144) {
                positionHolder.position = extractorInput.getPosition() + j2;
                z2 = true;
                processAtomEnded(position);
                return (z2 || this.parserState == 2) ? false : true;
            }
            extractorInput.skipFully((int) j2);
        }
        z2 = false;
        processAtomEnded(position);
        if (z2) {
        }
    }

    private int readSample(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        int i2;
        PositionHolder positionHolder2;
        long position = extractorInput.getPosition();
        if (this.sampleTrackIndex == -1) {
            int trackIndexOfNextReadSample = getTrackIndexOfNextReadSample(position);
            this.sampleTrackIndex = trackIndexOfNextReadSample;
            if (trackIndexOfNextReadSample == -1) {
                return -1;
            }
        }
        Mp4Track mp4Track = ((Mp4Track[]) Util.castNonNull(this.tracks))[this.sampleTrackIndex];
        TrackOutput trackOutput = mp4Track.trackOutput;
        int i3 = mp4Track.sampleIndex;
        TrackSampleTable trackSampleTable = mp4Track.sampleTable;
        long j2 = trackSampleTable.offsets[i3];
        int i4 = trackSampleTable.sizes[i3];
        TrueHdSampleRechunker trueHdSampleRechunker = mp4Track.trueHdSampleRechunker;
        long j3 = (j2 - position) + this.sampleBytesRead;
        if (j3 < 0) {
            i2 = 1;
            positionHolder2 = positionHolder;
        } else {
            if (j3 < 262144) {
                if (mp4Track.track.sampleTransformation == 1) {
                    j3 += 8;
                    i4 -= 8;
                }
                extractorInput.skipFully((int) j3);
                Track track = mp4Track.track;
                if (track.nalUnitLengthFieldLength == 0) {
                    if (MimeTypes.AUDIO_AC4.equals(track.format.sampleMimeType)) {
                        if (this.sampleBytesWritten == 0) {
                            Ac4Util.getAc4SampleHeader(i4, this.scratch);
                            trackOutput.sampleData(this.scratch, 7);
                            this.sampleBytesWritten += 7;
                        }
                        i4 += 7;
                    } else if (trueHdSampleRechunker != null) {
                        trueHdSampleRechunker.startSample(extractorInput);
                    }
                    while (true) {
                        int i5 = this.sampleBytesWritten;
                        if (i5 >= i4) {
                            break;
                        }
                        int iSampleData = trackOutput.sampleData((DataReader) extractorInput, i4 - i5, false);
                        this.sampleBytesRead += iSampleData;
                        this.sampleBytesWritten += iSampleData;
                        this.sampleCurrentNalBytesRemaining -= iSampleData;
                    }
                } else {
                    byte[] data = this.nalLength.getData();
                    data[0] = 0;
                    data[1] = 0;
                    data[2] = 0;
                    int i6 = mp4Track.track.nalUnitLengthFieldLength;
                    int i7 = 4 - i6;
                    while (this.sampleBytesWritten < i4) {
                        int i8 = this.sampleCurrentNalBytesRemaining;
                        if (i8 == 0) {
                            extractorInput.readFully(data, i7, i6);
                            this.sampleBytesRead += i6;
                            this.nalLength.setPosition(0);
                            int i9 = this.nalLength.readInt();
                            if (i9 < 0) {
                                throw ParserException.createForMalformedContainer("Invalid NAL length", null);
                            }
                            this.sampleCurrentNalBytesRemaining = i9;
                            this.nalStartCode.setPosition(0);
                            trackOutput.sampleData(this.nalStartCode, 4);
                            this.sampleBytesWritten += 4;
                            i4 += i7;
                        } else {
                            int iSampleData2 = trackOutput.sampleData((DataReader) extractorInput, i8, false);
                            this.sampleBytesRead += iSampleData2;
                            this.sampleBytesWritten += iSampleData2;
                            this.sampleCurrentNalBytesRemaining -= iSampleData2;
                        }
                    }
                }
                int i10 = i4;
                TrackSampleTable trackSampleTable2 = mp4Track.sampleTable;
                long j4 = trackSampleTable2.timestampsUs[i3];
                int i11 = trackSampleTable2.flags[i3];
                if (trueHdSampleRechunker != null) {
                    trueHdSampleRechunker.sampleMetadata(trackOutput, j4, i11, i10, 0, null);
                    if (i3 + 1 == mp4Track.sampleTable.sampleCount) {
                        trueHdSampleRechunker.outputPendingSampleMetadata(trackOutput, null);
                    }
                } else {
                    trackOutput.sampleMetadata(j4, i11, i10, 0, null);
                }
                mp4Track.sampleIndex++;
                this.sampleTrackIndex = -1;
                this.sampleBytesRead = 0;
                this.sampleBytesWritten = 0;
                this.sampleCurrentNalBytesRemaining = 0;
                return 0;
            }
            positionHolder2 = positionHolder;
            i2 = 1;
        }
        positionHolder2.position = j2;
        return i2;
    }

    private int readSefData(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        int i2 = this.sefReader.read(extractorInput, positionHolder, this.slowMotionMetadataEntries);
        if (i2 == 1 && positionHolder.position == 0) {
            enterReadingAtomHeaderState();
        }
        return i2;
    }

    private static boolean shouldParseContainerAtom(int i2) {
        return i2 == 1836019574 || i2 == 1953653099 || i2 == 1835297121 || i2 == 1835626086 || i2 == 1937007212 || i2 == 1701082227 || i2 == 1835365473;
    }

    private static boolean shouldParseLeafAtom(int i2) {
        return i2 == 1835296868 || i2 == 1836476516 || i2 == 1751411826 || i2 == 1937011556 || i2 == 1937011827 || i2 == 1937011571 || i2 == 1668576371 || i2 == 1701606260 || i2 == 1937011555 || i2 == 1937011578 || i2 == 1937013298 || i2 == 1937007471 || i2 == 1668232756 || i2 == 1953196132 || i2 == 1718909296 || i2 == 1969517665 || i2 == 1801812339 || i2 == 1768715124;
    }

    private void updateSampleIndex(Mp4Track mp4Track, long j2) {
        TrackSampleTable trackSampleTable = mp4Track.sampleTable;
        int indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfEarlierOrEqualSynchronizationSample(j2);
        if (indexOfEarlierOrEqualSynchronizationSample == -1) {
            indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(j2);
        }
        mp4Track.sampleIndex = indexOfEarlierOrEqualSynchronizationSample;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        long j3;
        long jMaybeAdjustSeekOffset;
        long j4;
        long j5;
        int indexOfLaterOrEqualSynchronizationSample;
        if (((Mp4Track[]) Assertions.checkNotNull(this.tracks)).length == 0) {
            return new SeekMap.SeekPoints(SeekPoint.START);
        }
        int i2 = this.firstVideoTrackIndex;
        if (i2 != -1) {
            TrackSampleTable trackSampleTable = this.tracks[i2].sampleTable;
            int synchronizationSampleIndex = getSynchronizationSampleIndex(trackSampleTable, j2);
            if (synchronizationSampleIndex == -1) {
                return new SeekMap.SeekPoints(SeekPoint.START);
            }
            long j6 = trackSampleTable.timestampsUs[synchronizationSampleIndex];
            j3 = trackSampleTable.offsets[synchronizationSampleIndex];
            if (j6 >= j2 || synchronizationSampleIndex >= trackSampleTable.sampleCount - 1 || (indexOfLaterOrEqualSynchronizationSample = trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(j2)) == -1 || indexOfLaterOrEqualSynchronizationSample == synchronizationSampleIndex) {
                j5 = -1;
                j4 = -9223372036854775807L;
            } else {
                j4 = trackSampleTable.timestampsUs[indexOfLaterOrEqualSynchronizationSample];
                j5 = trackSampleTable.offsets[indexOfLaterOrEqualSynchronizationSample];
            }
            jMaybeAdjustSeekOffset = j5;
            j2 = j6;
        } else {
            j3 = Long.MAX_VALUE;
            jMaybeAdjustSeekOffset = -1;
            j4 = -9223372036854775807L;
        }
        int i3 = 0;
        while (true) {
            Mp4Track[] mp4TrackArr = this.tracks;
            if (i3 >= mp4TrackArr.length) {
                break;
            }
            if (i3 != this.firstVideoTrackIndex) {
                TrackSampleTable trackSampleTable2 = mp4TrackArr[i3].sampleTable;
                long jMaybeAdjustSeekOffset2 = maybeAdjustSeekOffset(trackSampleTable2, j2, j3);
                if (j4 != C.TIME_UNSET) {
                    jMaybeAdjustSeekOffset = maybeAdjustSeekOffset(trackSampleTable2, j4, jMaybeAdjustSeekOffset);
                }
                j3 = jMaybeAdjustSeekOffset2;
            }
            i3++;
        }
        SeekPoint seekPoint = new SeekPoint(j2, j3);
        return j4 == C.TIME_UNSET ? new SeekMap.SeekPoints(seekPoint) : new SeekMap.SeekPoints(seekPoint, new SeekPoint(j4, jMaybeAdjustSeekOffset));
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        while (true) {
            int i2 = this.parserState;
            if (i2 != 0) {
                if (i2 != 1) {
                    if (i2 == 2) {
                        return readSample(extractorInput, positionHolder);
                    }
                    if (i2 == 3) {
                        return readSefData(extractorInput, positionHolder);
                    }
                    throw new IllegalStateException();
                }
                if (readAtomPayload(extractorInput, positionHolder)) {
                    return 1;
                }
            } else if (!readAtomHeader(extractorInput)) {
                return -1;
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j2, long j3) {
        this.containerAtoms.clear();
        this.atomHeaderBytesRead = 0;
        this.sampleTrackIndex = -1;
        this.sampleBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        if (j2 == 0) {
            if (this.parserState != 3) {
                enterReadingAtomHeaderState();
                return;
            } else {
                this.sefReader.reset();
                this.slowMotionMetadataEntries.clear();
                return;
            }
        }
        Mp4Track[] mp4TrackArr = this.tracks;
        if (mp4TrackArr != null) {
            for (Mp4Track mp4Track : mp4TrackArr) {
                updateSampleIndex(mp4Track, j3);
                TrueHdSampleRechunker trueHdSampleRechunker = mp4Track.trueHdSampleRechunker;
                if (trueHdSampleRechunker != null) {
                    trueHdSampleRechunker.reset();
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return Sniffer.sniffUnfragmented(extractorInput, (this.flags & 2) != 0);
    }

    public Mp4Extractor(int i2) {
        this.flags = i2;
        this.parserState = (i2 & 4) != 0 ? 3 : 0;
        this.sefReader = new SefReader();
        this.slowMotionMetadataEntries = new ArrayList();
        this.atomHeader = new ParsableByteArray(16);
        this.containerAtoms = new ArrayDeque<>();
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
        this.scratch = new ParsableByteArray();
        this.sampleTrackIndex = -1;
    }
}
