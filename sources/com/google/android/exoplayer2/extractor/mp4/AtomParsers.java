package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.mp4.Atom;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.AvcConfig;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.android.exoplayer2.video.DolbyVisionConfig;
import com.google.android.exoplayer2.video.HevcConfig;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
final class AtomParsers {
    private static final int MAX_GAPLESS_TRIM_SIZE_SAMPLES = 4;
    private static final String TAG = "AtomParsers";
    private static final int TYPE_clcp = 1668047728;
    private static final int TYPE_mdta = 1835299937;
    private static final int TYPE_meta = 1835365473;
    private static final int TYPE_nclc = 1852009571;
    private static final int TYPE_nclx = 1852009592;
    private static final int TYPE_sbtl = 1935832172;
    private static final int TYPE_soun = 1936684398;
    private static final int TYPE_subt = 1937072756;
    private static final int TYPE_text = 1952807028;
    private static final int TYPE_vide = 1986618469;
    private static final byte[] opusMagic = Util.getUtf8Bytes("OpusHead");

    public static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z2) throws ParserException {
            this.stsc = parsableByteArray;
            this.chunkOffsets = parsableByteArray2;
            this.chunkOffsetsAreLongs = z2;
            parsableByteArray2.setPosition(12);
            this.length = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.remainingSamplesPerChunkChanges = parsableByteArray.readUnsignedIntToInt();
            ExtractorUtil.checkContainerInput(parsableByteArray.readInt() == 1, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            int i2 = this.index + 1;
            this.index = i2;
            if (i2 == this.length) {
                return false;
            }
            this.offset = this.chunkOffsetsAreLongs ? this.chunkOffsets.readUnsignedLongToLong() : this.chunkOffsets.readUnsignedInt();
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                int i3 = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i3;
                this.nextSamplesPerChunkChangeIndex = i3 > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    public interface SampleSizeBox {
        int getFixedSampleSize();

        int getSampleCount();

        int readNextSampleSize();
    }

    public static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;

        @Nullable
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int i2) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[i2];
        }
    }

    public static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize;
        private final int sampleCount;

        public StszSampleSizeBox(Atom.LeafAtom leafAtom, Format format) {
            ParsableByteArray parsableByteArray = leafAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
                int pcmFrameSize = Util.getPcmFrameSize(format.pcmEncoding, format.channelCount);
                if (unsignedIntToInt == 0 || unsignedIntToInt % pcmFrameSize != 0) {
                    StringBuilder sb = new StringBuilder(88);
                    sb.append("Audio sample size mismatch. stsd sample size: ");
                    sb.append(pcmFrameSize);
                    sb.append(", stsz sample size: ");
                    sb.append(unsignedIntToInt);
                    Log.w(AtomParsers.TAG, sb.toString());
                    unsignedIntToInt = pcmFrameSize;
                }
            }
            this.fixedSampleSize = unsignedIntToInt == 0 ? -1 : unsignedIntToInt;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return this.fixedSampleSize;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i2 = this.fixedSampleSize;
            return i2 == -1 ? this.data.readUnsignedIntToInt() : i2;
        }
    }

    public static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize;
        private final int sampleCount;
        private int sampleIndex;

        public Stz2SampleSizeBox(Atom.LeafAtom leafAtom) {
            ParsableByteArray parsableByteArray = leafAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            this.fieldSize = parsableByteArray.readUnsignedIntToInt() & 255;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return -1;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i2 = this.fieldSize;
            if (i2 == 8) {
                return this.data.readUnsignedByte();
            }
            if (i2 == 16) {
                return this.data.readUnsignedShort();
            }
            int i3 = this.sampleIndex;
            this.sampleIndex = i3 + 1;
            if (i3 % 2 != 0) {
                return this.currentByte & 15;
            }
            int unsignedByte = this.data.readUnsignedByte();
            this.currentByte = unsignedByte;
            return (unsignedByte & 240) >> 4;
        }
    }

    public static final class TkhdData {
        private final long duration;
        private final int id;
        private final int rotationDegrees;

        public TkhdData(int i2, long j2, int i3) {
            this.id = i2;
            this.duration = j2;
            this.rotationDegrees = i3;
        }
    }

    private AtomParsers() {
    }

    private static ByteBuffer allocateHdrStaticInfo() {
        return ByteBuffer.allocate(25).order(ByteOrder.LITTLE_ENDIAN);
    }

    private static boolean canApplyEditWithGaplessInfo(long[] jArr, long j2, long j3, long j4) {
        int length = jArr.length - 1;
        return jArr[0] <= j3 && j3 < jArr[Util.constrainValue(4, 0, length)] && jArr[Util.constrainValue(jArr.length - 4, 0, length)] < j4 && j4 <= j2;
    }

    private static int findBoxPosition(ParsableByteArray parsableByteArray, int i2, int i3, int i4) throws ParserException {
        int position = parsableByteArray.getPosition();
        ExtractorUtil.checkContainerInput(position >= i3, null);
        while (position - i3 < i4) {
            parsableByteArray.setPosition(position);
            int i5 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i5 > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == i2) {
                return position;
            }
            position += i5;
        }
        return -1;
    }

    private static int getTrackTypeForHdlr(int i2) {
        if (i2 == TYPE_soun) {
            return 1;
        }
        if (i2 == TYPE_vide) {
            return 2;
        }
        if (i2 == TYPE_text || i2 == TYPE_sbtl || i2 == TYPE_subt || i2 == TYPE_clcp) {
            return 3;
        }
        return i2 == 1835365473 ? 5 : -1;
    }

    public static void maybeSkipRemainingMetaAtomHeaderBytes(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() != 1751411826) {
            position += 4;
        }
        parsableByteArray.setPosition(position);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0165  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void parseAudioSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray r22, int r23, int r24, int r25, int r26, java.lang.String r27, boolean r28, @androidx.annotation.Nullable com.google.android.exoplayer2.drm.DrmInitData r29, com.google.android.exoplayer2.extractor.mp4.AtomParsers.StsdData r30, int r31) throws com.google.android.exoplayer2.ParserException {
        /*
            Method dump skipped, instructions count: 840
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseAudioSampleEntry(com.google.android.exoplayer2.util.ParsableByteArray, int, int, int, int, java.lang.String, boolean, com.google.android.exoplayer2.drm.DrmInitData, com.google.android.exoplayer2.extractor.mp4.AtomParsers$StsdData, int):void");
    }

    @Nullable
    public static Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent(ParsableByteArray parsableByteArray, int i2, int i3) throws ParserException {
        int i4 = i2 + 8;
        int i5 = -1;
        int i6 = 0;
        String string = null;
        Integer numValueOf = null;
        while (i4 - i2 < i3) {
            parsableByteArray.setPosition(i4);
            int i7 = parsableByteArray.readInt();
            int i8 = parsableByteArray.readInt();
            if (i8 == 1718775137) {
                numValueOf = Integer.valueOf(parsableByteArray.readInt());
            } else if (i8 == 1935894637) {
                parsableByteArray.skipBytes(4);
                string = parsableByteArray.readString(4);
            } else if (i8 == 1935894633) {
                i5 = i4;
                i6 = i7;
            }
            i4 += i7;
        }
        if (!C.CENC_TYPE_cenc.equals(string) && !C.CENC_TYPE_cbc1.equals(string) && !C.CENC_TYPE_cens.equals(string) && !C.CENC_TYPE_cbcs.equals(string)) {
            return null;
        }
        ExtractorUtil.checkContainerInput(numValueOf != null, "frma atom is mandatory");
        ExtractorUtil.checkContainerInput(i5 != -1, "schi atom is mandatory");
        TrackEncryptionBox schiFromParent = parseSchiFromParent(parsableByteArray, i5, i6, string);
        ExtractorUtil.checkContainerInput(schiFromParent != null, "tenc atom is mandatory");
        return Pair.create(numValueOf, (TrackEncryptionBox) Util.castNonNull(schiFromParent));
    }

    @Nullable
    private static Pair<long[], long[]> parseEdts(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_elst);
        if (leafAtomOfType == null) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType.data;
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        long[] jArr = new long[unsignedIntToInt];
        long[] jArr2 = new long[unsignedIntToInt];
        for (int i2 = 0; i2 < unsignedIntToInt; i2++) {
            jArr[i2] = fullAtomVersion == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
            jArr2[i2] = fullAtomVersion == 1 ? parsableByteArray.readLong() : parsableByteArray.readInt();
            if (parsableByteArray.readShort() != 1) {
                throw new IllegalArgumentException("Unsupported media rate.");
            }
            parsableByteArray.skipBytes(2);
        }
        return Pair.create(jArr, jArr2);
    }

    private static Pair<String, byte[]> parseEsdsFromParent(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.setPosition(i2 + 8 + 4);
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int unsignedByte = parsableByteArray.readUnsignedByte();
        if ((unsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((unsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort());
        }
        if ((unsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if ("audio/mpeg".equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS_HD.equals(mimeTypeFromMp4ObjectType)) {
            return Pair.create(mimeTypeFromMp4ObjectType, null);
        }
        parsableByteArray.skipBytes(12);
        parsableByteArray.skipBytes(1);
        int expandableClassSize = parseExpandableClassSize(parsableByteArray);
        byte[] bArr = new byte[expandableClassSize];
        parsableByteArray.readBytes(bArr, 0, expandableClassSize);
        return Pair.create(mimeTypeFromMp4ObjectType, bArr);
    }

    private static int parseExpandableClassSize(ParsableByteArray parsableByteArray) {
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = unsignedByte & 127;
        while ((unsignedByte & 128) == 128) {
            unsignedByte = parsableByteArray.readUnsignedByte();
            i2 = (i2 << 7) | (unsignedByte & 127);
        }
        return i2;
    }

    private static int parseHdlr(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        return parsableByteArray.readInt();
    }

    @Nullable
    private static Metadata parseIlst(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.skipBytes(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i2) {
            Metadata.Entry ilstElement = MetadataUtil.parseIlstElement(parsableByteArray);
            if (ilstElement != null) {
                arrayList.add(ilstElement);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 8 : 16);
        long unsignedInt = parsableByteArray.readUnsignedInt();
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 4 : 8);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        StringBuilder sb = new StringBuilder(3);
        sb.append((char) (((unsignedShort >> 10) & 31) + 96));
        sb.append((char) (((unsignedShort >> 5) & 31) + 96));
        sb.append((char) ((unsignedShort & 31) + 96));
        return Pair.create(Long.valueOf(unsignedInt), sb.toString());
    }

    @Nullable
    public static Metadata parseMdtaFromMeta(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_hdlr);
        Atom.LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_keys);
        Atom.LeafAtom leafAtomOfType3 = containerAtom.getLeafAtomOfType(Atom.TYPE_ilst);
        if (leafAtomOfType == null || leafAtomOfType2 == null || leafAtomOfType3 == null || parseHdlr(leafAtomOfType.data) != TYPE_mdta) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType2.data;
        parsableByteArray.setPosition(12);
        int i2 = parsableByteArray.readInt();
        String[] strArr = new String[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            strArr[i3] = parsableByteArray.readString(i4 - 8);
        }
        ParsableByteArray parsableByteArray2 = leafAtomOfType3.data;
        parsableByteArray2.setPosition(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray2.bytesLeft() > 8) {
            int position = parsableByteArray2.getPosition();
            int i5 = parsableByteArray2.readInt();
            int i6 = parsableByteArray2.readInt() - 1;
            if (i6 < 0 || i6 >= i2) {
                StringBuilder sb = new StringBuilder(52);
                sb.append("Skipped metadata with unknown key index: ");
                sb.append(i6);
                Log.w(TAG, sb.toString());
            } else {
                MdtaMetadataEntry mdtaMetadataEntryFromIlst = MetadataUtil.parseMdtaMetadataEntryFromIlst(parsableByteArray2, position + i5, strArr[i6]);
                if (mdtaMetadataEntryFromIlst != null) {
                    arrayList.add(mdtaMetadataEntryFromIlst);
                }
            }
            parsableByteArray2.setPosition(position + i5);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static void parseMetaDataSampleEntry(ParsableByteArray parsableByteArray, int i2, int i3, int i4, StsdData stsdData) {
        parsableByteArray.setPosition(i3 + 8 + 8);
        if (i2 == 1835365492) {
            parsableByteArray.readNullTerminatedString();
            String nullTerminatedString = parsableByteArray.readNullTerminatedString();
            if (nullTerminatedString != null) {
                stsdData.format = new Format.Builder().setId(i4).setSampleMimeType(nullTerminatedString).build();
            }
        }
    }

    private static long parseMvhd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        parsableByteArray.skipBytes(Atom.parseFullAtomVersion(parsableByteArray.readInt()) != 0 ? 16 : 8);
        return parsableByteArray.readUnsignedInt();
    }

    private static float parsePaspFromParent(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.setPosition(i2 + 8);
        return parsableByteArray.readUnsignedIntToInt() / parsableByteArray.readUnsignedIntToInt();
    }

    @Nullable
    private static byte[] parseProjFromParent(ParsableByteArray parsableByteArray, int i2, int i3) {
        int i4 = i2 + 8;
        while (i4 - i2 < i3) {
            parsableByteArray.setPosition(i4);
            int i5 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1886547818) {
                return Arrays.copyOfRange(parsableByteArray.getData(), i4, i5 + i4);
            }
            i4 += i5;
        }
        return null;
    }

    @Nullable
    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parsableByteArray, int i2, int i3) throws ParserException {
        Pair<Integer, TrackEncryptionBox> commonEncryptionSinfFromParent;
        int position = parsableByteArray.getPosition();
        while (position - i2 < i3) {
            parsableByteArray.setPosition(position);
            int i4 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i4 > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == 1936289382 && (commonEncryptionSinfFromParent = parseCommonEncryptionSinfFromParent(parsableByteArray, position, i4)) != null) {
                return commonEncryptionSinfFromParent;
            }
            position += i4;
        }
        return null;
    }

    @Nullable
    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parsableByteArray, int i2, int i3, String str) {
        int i4;
        int i5;
        int i6 = i2 + 8;
        while (true) {
            byte[] bArr = null;
            if (i6 - i2 >= i3) {
                return null;
            }
            parsableByteArray.setPosition(i6);
            int i7 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1952804451) {
                int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                parsableByteArray.skipBytes(1);
                if (fullAtomVersion == 0) {
                    parsableByteArray.skipBytes(1);
                    i5 = 0;
                    i4 = 0;
                } else {
                    int unsignedByte = parsableByteArray.readUnsignedByte();
                    i4 = unsignedByte & 15;
                    i5 = (unsignedByte & 240) >> 4;
                }
                boolean z2 = parsableByteArray.readUnsignedByte() == 1;
                int unsignedByte2 = parsableByteArray.readUnsignedByte();
                byte[] bArr2 = new byte[16];
                parsableByteArray.readBytes(bArr2, 0, 16);
                if (z2 && unsignedByte2 == 0) {
                    int unsignedByte3 = parsableByteArray.readUnsignedByte();
                    bArr = new byte[unsignedByte3];
                    parsableByteArray.readBytes(bArr, 0, unsignedByte3);
                }
                return new TrackEncryptionBox(z2, str, unsignedByte2, bArr2, i5, i4, bArr);
            }
            i6 += i7;
        }
    }

    @Nullable
    private static Metadata parseSmta(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i2) {
            int position = parsableByteArray.getPosition();
            int i3 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1935766900) {
                if (i3 < 14) {
                    return null;
                }
                parsableByteArray.skipBytes(5);
                int unsignedByte = parsableByteArray.readUnsignedByte();
                if (unsignedByte != 12 && unsignedByte != 13) {
                    return null;
                }
                float f2 = unsignedByte == 12 ? 240.0f : 120.0f;
                parsableByteArray.skipBytes(1);
                return new Metadata(new SmtaMetadataEntry(f2, parsableByteArray.readUnsignedByte()));
            }
            parsableByteArray.setPosition(position + i3);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0443  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0446  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0449  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0451  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0460  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0430 A[EDGE_INSN: B:207:0x0430->B:169:0x0430 BREAK  A[LOOP:2: B:152:0x03d2->B:168:0x0428], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.extractor.mp4.TrackSampleTable parseStbl(com.google.android.exoplayer2.extractor.mp4.Track r37, com.google.android.exoplayer2.extractor.mp4.Atom.ContainerAtom r38, com.google.android.exoplayer2.extractor.GaplessInfoHolder r39) throws com.google.android.exoplayer2.ParserException {
        /*
            Method dump skipped, instructions count: 1306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseStbl(com.google.android.exoplayer2.extractor.mp4.Track, com.google.android.exoplayer2.extractor.mp4.Atom$ContainerAtom, com.google.android.exoplayer2.extractor.GaplessInfoHolder):com.google.android.exoplayer2.extractor.mp4.TrackSampleTable");
    }

    private static StsdData parseStsd(ParsableByteArray parsableByteArray, int i2, int i3, String str, @Nullable DrmInitData drmInitData, boolean z2) throws ParserException {
        int i4;
        parsableByteArray.setPosition(12);
        int i5 = parsableByteArray.readInt();
        StsdData stsdData = new StsdData(i5);
        for (int i6 = 0; i6 < i5; i6++) {
            int position = parsableByteArray.getPosition();
            int i7 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i7 > 0, "childAtomSize must be positive");
            int i8 = parsableByteArray.readInt();
            if (i8 == 1635148593 || i8 == 1635148595 || i8 == 1701733238 || i8 == 1831958048 || i8 == 1836070006 || i8 == 1752589105 || i8 == 1751479857 || i8 == 1932670515 || i8 == 1211250227 || i8 == 1987063864 || i8 == 1987063865 || i8 == 1635135537 || i8 == 1685479798 || i8 == 1685479729 || i8 == 1685481573 || i8 == 1685481521) {
                i4 = position;
                parseVideoSampleEntry(parsableByteArray, i8, i4, i7, i2, i3, drmInitData, stsdData, i6);
            } else if (i8 == 1836069985 || i8 == 1701733217 || i8 == 1633889587 || i8 == 1700998451 || i8 == 1633889588 || i8 == 1835823201 || i8 == 1685353315 || i8 == 1685353317 || i8 == 1685353320 || i8 == 1685353324 || i8 == 1685353336 || i8 == 1935764850 || i8 == 1935767394 || i8 == 1819304813 || i8 == 1936684916 || i8 == 1953984371 || i8 == 778924082 || i8 == 778924083 || i8 == 1835557169 || i8 == 1835560241 || i8 == 1634492771 || i8 == 1634492791 || i8 == 1970037111 || i8 == 1332770163 || i8 == 1716281667) {
                i4 = position;
                parseAudioSampleEntry(parsableByteArray, i8, position, i7, i2, str, z2, drmInitData, stsdData, i6);
            } else {
                if (i8 == 1414810956 || i8 == 1954034535 || i8 == 2004251764 || i8 == 1937010800 || i8 == 1664495672) {
                    parseTextSampleEntry(parsableByteArray, i8, position, i7, i2, str, stsdData);
                } else if (i8 == 1835365492) {
                    parseMetaDataSampleEntry(parsableByteArray, i8, position, i2, stsdData);
                } else if (i8 == 1667329389) {
                    stsdData.format = new Format.Builder().setId(i2).setSampleMimeType(MimeTypes.APPLICATION_CAMERA_MOTION).build();
                }
                i4 = position;
            }
            parsableByteArray.setPosition(i4 + i7);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parsableByteArray, int i2, int i3, int i4, int i5, String str, StsdData stsdData) {
        parsableByteArray.setPosition(i3 + 8 + 8);
        String str2 = MimeTypes.APPLICATION_TTML;
        ImmutableList immutableListOf = null;
        long j2 = Long.MAX_VALUE;
        if (i2 != 1414810956) {
            if (i2 == 1954034535) {
                int i6 = (i4 - 8) - 8;
                byte[] bArr = new byte[i6];
                parsableByteArray.readBytes(bArr, 0, i6);
                immutableListOf = ImmutableList.of(bArr);
                str2 = MimeTypes.APPLICATION_TX3G;
            } else if (i2 == 2004251764) {
                str2 = MimeTypes.APPLICATION_MP4VTT;
            } else if (i2 == 1937010800) {
                j2 = 0;
            } else {
                if (i2 != 1664495672) {
                    throw new IllegalStateException();
                }
                stsdData.requiredSampleTransformation = 1;
                str2 = MimeTypes.APPLICATION_MP4CEA608;
            }
        }
        stsdData.format = new Format.Builder().setId(i5).setSampleMimeType(str2).setLanguage(str).setSubsampleOffsetUs(j2).setInitializationData(immutableListOf).build();
    }

    private static TkhdData parseTkhd(ParsableByteArray parsableByteArray) {
        boolean z2;
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 8 : 16);
        int i2 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int position = parsableByteArray.getPosition();
        int i3 = fullAtomVersion == 0 ? 4 : 8;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= i3) {
                z2 = true;
                break;
            }
            if (parsableByteArray.getData()[position + i5] != -1) {
                z2 = false;
                break;
            }
            i5++;
        }
        long j2 = C.TIME_UNSET;
        if (z2) {
            parsableByteArray.skipBytes(i3);
        } else {
            long unsignedInt = fullAtomVersion == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            if (unsignedInt != 0) {
                j2 = unsignedInt;
            }
        }
        parsableByteArray.skipBytes(16);
        int i6 = parsableByteArray.readInt();
        int i7 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int i8 = parsableByteArray.readInt();
        int i9 = parsableByteArray.readInt();
        if (i6 == 0 && i7 == 65536 && i8 == -65536 && i9 == 0) {
            i4 = 90;
        } else if (i6 == 0 && i7 == -65536 && i8 == 65536 && i9 == 0) {
            i4 = 270;
        } else if (i6 == -65536 && i7 == 0 && i8 == 0 && i9 == -65536) {
            i4 = 180;
        }
        return new TkhdData(i2, j2, i4);
    }

    @Nullable
    private static Track parseTrak(Atom.ContainerAtom containerAtom, Atom.LeafAtom leafAtom, long j2, @Nullable DrmInitData drmInitData, boolean z2, boolean z3) throws ParserException {
        Atom.LeafAtom leafAtom2;
        long j3;
        long[] jArr;
        long[] jArr2;
        Atom.ContainerAtom containerAtomOfType;
        Pair<long[], long[]> edts;
        Atom.ContainerAtom containerAtom2 = (Atom.ContainerAtom) Assertions.checkNotNull(containerAtom.getContainerAtomOfType(Atom.TYPE_mdia));
        int trackTypeForHdlr = getTrackTypeForHdlr(parseHdlr(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(Atom.TYPE_hdlr))).data));
        if (trackTypeForHdlr == -1) {
            return null;
        }
        TkhdData tkhd = parseTkhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_tkhd))).data);
        long jScaleLargeTimestamp = C.TIME_UNSET;
        if (j2 == C.TIME_UNSET) {
            leafAtom2 = leafAtom;
            j3 = tkhd.duration;
        } else {
            leafAtom2 = leafAtom;
            j3 = j2;
        }
        long mvhd = parseMvhd(leafAtom2.data);
        if (j3 != C.TIME_UNSET) {
            jScaleLargeTimestamp = Util.scaleLargeTimestamp(j3, 1000000L, mvhd);
        }
        long j4 = jScaleLargeTimestamp;
        Atom.ContainerAtom containerAtom3 = (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom2.getContainerAtomOfType(Atom.TYPE_minf))).getContainerAtomOfType(Atom.TYPE_stbl));
        Pair<Long, String> mdhd = parseMdhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(Atom.TYPE_mdhd))).data);
        StsdData stsd = parseStsd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom3.getLeafAtomOfType(Atom.TYPE_stsd))).data, tkhd.id, tkhd.rotationDegrees, (String) mdhd.second, drmInitData, z3);
        if (z2 || (containerAtomOfType = containerAtom.getContainerAtomOfType(Atom.TYPE_edts)) == null || (edts = parseEdts(containerAtomOfType)) == null) {
            jArr = null;
            jArr2 = null;
        } else {
            long[] jArr3 = (long[]) edts.first;
            jArr2 = (long[]) edts.second;
            jArr = jArr3;
        }
        if (stsd.format == null) {
            return null;
        }
        return new Track(tkhd.id, trackTypeForHdlr, ((Long) mdhd.first).longValue(), mvhd, j4, stsd.format, stsd.requiredSampleTransformation, stsd.trackEncryptionBoxes, stsd.nalUnitLengthFieldLength, jArr, jArr2);
    }

    public static List<TrackSampleTable> parseTraks(Atom.ContainerAtom containerAtom, GaplessInfoHolder gaplessInfoHolder, long j2, @Nullable DrmInitData drmInitData, boolean z2, boolean z3, Function<Track, Track> function) throws ParserException {
        Track trackApply;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < containerAtom.containerChildren.size(); i2++) {
            Atom.ContainerAtom containerAtom2 = containerAtom.containerChildren.get(i2);
            if (containerAtom2.type == 1953653099 && (trackApply = function.apply(parseTrak(containerAtom2, (Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_mvhd)), j2, drmInitData, z2, z3))) != null) {
                arrayList.add(parseStbl(trackApply, (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom2.getContainerAtomOfType(Atom.TYPE_mdia))).getContainerAtomOfType(Atom.TYPE_minf))).getContainerAtomOfType(Atom.TYPE_stbl)), gaplessInfoHolder));
            }
        }
        return arrayList;
    }

    public static Pair<Metadata, Metadata> parseUdta(Atom.LeafAtom leafAtom) {
        ParsableByteArray parsableByteArray = leafAtom.data;
        parsableByteArray.setPosition(8);
        Metadata udtaMeta = null;
        Metadata smta = null;
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int i2 = parsableByteArray.readInt();
            int i3 = parsableByteArray.readInt();
            if (i3 == 1835365473) {
                parsableByteArray.setPosition(position);
                udtaMeta = parseUdtaMeta(parsableByteArray, position + i2);
            } else if (i3 == 1936553057) {
                parsableByteArray.setPosition(position);
                smta = parseSmta(parsableByteArray, position + i2);
            }
            parsableByteArray.setPosition(position + i2);
        }
        return Pair.create(udtaMeta, smta);
    }

    @Nullable
    private static Metadata parseUdtaMeta(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.skipBytes(8);
        maybeSkipRemainingMetaAtomHeaderBytes(parsableByteArray);
        while (parsableByteArray.getPosition() < i2) {
            int position = parsableByteArray.getPosition();
            int i3 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1768715124) {
                parsableByteArray.setPosition(position);
                return parseIlst(parsableByteArray, position + i3);
            }
            parsableByteArray.setPosition(position + i3);
        }
        return null;
    }

    private static void parseVideoSampleEntry(ParsableByteArray parsableByteArray, int i2, int i3, int i4, int i5, int i6, @Nullable DrmInitData drmInitData, StsdData stsdData, int i7) throws ParserException {
        DrmInitData drmInitData2;
        int i8;
        int i9;
        byte[] bArr;
        float f2;
        List<byte[]> list;
        String str;
        int i10 = i3;
        int i11 = i4;
        DrmInitData drmInitDataCopyWithSchemeType = drmInitData;
        StsdData stsdData2 = stsdData;
        parsableByteArray.setPosition(i10 + 8 + 8);
        parsableByteArray.skipBytes(16);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int unsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(50);
        int position = parsableByteArray.getPosition();
        int iIntValue = i2;
        if (iIntValue == 1701733238) {
            Pair<Integer, TrackEncryptionBox> sampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i10, i11);
            if (sampleEntryEncryptionData != null) {
                iIntValue = ((Integer) sampleEntryEncryptionData.first).intValue();
                drmInitDataCopyWithSchemeType = drmInitDataCopyWithSchemeType == null ? null : drmInitDataCopyWithSchemeType.copyWithSchemeType(((TrackEncryptionBox) sampleEntryEncryptionData.second).schemeType);
                stsdData2.trackEncryptionBoxes[i7] = (TrackEncryptionBox) sampleEntryEncryptionData.second;
            }
            parsableByteArray.setPosition(position);
        }
        String str2 = "video/3gpp";
        String str3 = iIntValue == 1831958048 ? MimeTypes.VIDEO_MPEG : iIntValue == 1211250227 ? "video/3gpp" : null;
        float paspFromParent = 1.0f;
        byte[] projFromParent = null;
        String str4 = null;
        List<byte[]> listOf = null;
        int i12 = -1;
        int iIsoColorPrimariesToColorSpace = -1;
        int i13 = -1;
        int iIsoTransferCharacteristicsToColorTransfer = -1;
        ByteBuffer byteBuffer = null;
        boolean z2 = false;
        while (true) {
            if (position - i10 >= i11) {
                drmInitData2 = drmInitDataCopyWithSchemeType;
                break;
            }
            parsableByteArray.setPosition(position);
            int position2 = parsableByteArray.getPosition();
            String str5 = str2;
            int i14 = parsableByteArray.readInt();
            if (i14 == 0) {
                drmInitData2 = drmInitDataCopyWithSchemeType;
                if (parsableByteArray.getPosition() - i10 == i11) {
                    break;
                }
            } else {
                drmInitData2 = drmInitDataCopyWithSchemeType;
            }
            ExtractorUtil.checkContainerInput(i14 > 0, "childAtomSize must be positive");
            int i15 = parsableByteArray.readInt();
            if (i15 == 1635148611) {
                ExtractorUtil.checkContainerInput(str3 == null, null);
                parsableByteArray.setPosition(position2 + 8);
                AvcConfig avcConfig = AvcConfig.parse(parsableByteArray);
                listOf = avcConfig.initializationData;
                stsdData2.nalUnitLengthFieldLength = avcConfig.nalUnitLengthFieldLength;
                if (!z2) {
                    paspFromParent = avcConfig.pixelWidthHeightRatio;
                }
                str4 = avcConfig.codecs;
                str = MimeTypes.VIDEO_H264;
            } else if (i15 == 1752589123) {
                ExtractorUtil.checkContainerInput(str3 == null, null);
                parsableByteArray.setPosition(position2 + 8);
                HevcConfig hevcConfig = HevcConfig.parse(parsableByteArray);
                listOf = hevcConfig.initializationData;
                stsdData2.nalUnitLengthFieldLength = hevcConfig.nalUnitLengthFieldLength;
                if (!z2) {
                    paspFromParent = hevcConfig.pixelWidthHeightRatio;
                }
                str4 = hevcConfig.codecs;
                str = MimeTypes.VIDEO_H265;
            } else {
                if (i15 == 1685480259 || i15 == 1685485123) {
                    i8 = unsignedShort2;
                    i9 = iIntValue;
                    bArr = projFromParent;
                    f2 = paspFromParent;
                    list = listOf;
                    DolbyVisionConfig dolbyVisionConfig = DolbyVisionConfig.parse(parsableByteArray);
                    if (dolbyVisionConfig != null) {
                        str4 = dolbyVisionConfig.codecs;
                        str3 = MimeTypes.VIDEO_DOLBY_VISION;
                    }
                } else if (i15 == 1987076931) {
                    ExtractorUtil.checkContainerInput(str3 == null, null);
                    str = iIntValue == 1987063864 ? MimeTypes.VIDEO_VP8 : MimeTypes.VIDEO_VP9;
                } else if (i15 == 1635135811) {
                    ExtractorUtil.checkContainerInput(str3 == null, null);
                    str = MimeTypes.VIDEO_AV1;
                } else if (i15 == 1668050025) {
                    ByteBuffer byteBufferAllocateHdrStaticInfo = byteBuffer == null ? allocateHdrStaticInfo() : byteBuffer;
                    byteBufferAllocateHdrStaticInfo.position(21);
                    byteBufferAllocateHdrStaticInfo.putShort(parsableByteArray.readShort());
                    byteBufferAllocateHdrStaticInfo.putShort(parsableByteArray.readShort());
                    byteBuffer = byteBufferAllocateHdrStaticInfo;
                    i8 = unsignedShort2;
                    i9 = iIntValue;
                    position += i14;
                    i10 = i3;
                    i11 = i4;
                    stsdData2 = stsdData;
                    str2 = str5;
                    drmInitDataCopyWithSchemeType = drmInitData2;
                    iIntValue = i9;
                    unsignedShort2 = i8;
                } else if (i15 == 1835295606) {
                    ByteBuffer byteBufferAllocateHdrStaticInfo2 = byteBuffer == null ? allocateHdrStaticInfo() : byteBuffer;
                    short s2 = parsableByteArray.readShort();
                    short s3 = parsableByteArray.readShort();
                    short s4 = parsableByteArray.readShort();
                    i9 = iIntValue;
                    short s5 = parsableByteArray.readShort();
                    short s6 = parsableByteArray.readShort();
                    List<byte[]> list2 = listOf;
                    short s7 = parsableByteArray.readShort();
                    byte[] bArr2 = projFromParent;
                    short s8 = parsableByteArray.readShort();
                    float f3 = paspFromParent;
                    short s9 = parsableByteArray.readShort();
                    long unsignedInt = parsableByteArray.readUnsignedInt();
                    long unsignedInt2 = parsableByteArray.readUnsignedInt();
                    i8 = unsignedShort2;
                    byteBufferAllocateHdrStaticInfo2.position(1);
                    byteBufferAllocateHdrStaticInfo2.putShort(s6);
                    byteBufferAllocateHdrStaticInfo2.putShort(s7);
                    byteBufferAllocateHdrStaticInfo2.putShort(s2);
                    byteBufferAllocateHdrStaticInfo2.putShort(s3);
                    byteBufferAllocateHdrStaticInfo2.putShort(s4);
                    byteBufferAllocateHdrStaticInfo2.putShort(s5);
                    byteBufferAllocateHdrStaticInfo2.putShort(s8);
                    byteBufferAllocateHdrStaticInfo2.putShort(s9);
                    byteBufferAllocateHdrStaticInfo2.putShort((short) (unsignedInt / com.heytap.mcssdk.constant.a.f7153q));
                    byteBufferAllocateHdrStaticInfo2.putShort((short) (unsignedInt2 / com.heytap.mcssdk.constant.a.f7153q));
                    byteBuffer = byteBufferAllocateHdrStaticInfo2;
                    listOf = list2;
                    projFromParent = bArr2;
                    paspFromParent = f3;
                    position += i14;
                    i10 = i3;
                    i11 = i4;
                    stsdData2 = stsdData;
                    str2 = str5;
                    drmInitDataCopyWithSchemeType = drmInitData2;
                    iIntValue = i9;
                    unsignedShort2 = i8;
                } else {
                    i8 = unsignedShort2;
                    i9 = iIntValue;
                    bArr = projFromParent;
                    f2 = paspFromParent;
                    list = listOf;
                    if (i15 == 1681012275) {
                        ExtractorUtil.checkContainerInput(str3 == null, null);
                        str3 = str5;
                    } else if (i15 == 1702061171) {
                        ExtractorUtil.checkContainerInput(str3 == null, null);
                        Pair<String, byte[]> esdsFromParent = parseEsdsFromParent(parsableByteArray, position2);
                        String str6 = (String) esdsFromParent.first;
                        byte[] bArr3 = (byte[]) esdsFromParent.second;
                        listOf = bArr3 != null ? ImmutableList.of(bArr3) : list;
                        str3 = str6;
                        projFromParent = bArr;
                        paspFromParent = f2;
                        position += i14;
                        i10 = i3;
                        i11 = i4;
                        stsdData2 = stsdData;
                        str2 = str5;
                        drmInitDataCopyWithSchemeType = drmInitData2;
                        iIntValue = i9;
                        unsignedShort2 = i8;
                    } else if (i15 == 1885434736) {
                        paspFromParent = parsePaspFromParent(parsableByteArray, position2);
                        listOf = list;
                        projFromParent = bArr;
                        z2 = true;
                        position += i14;
                        i10 = i3;
                        i11 = i4;
                        stsdData2 = stsdData;
                        str2 = str5;
                        drmInitDataCopyWithSchemeType = drmInitData2;
                        iIntValue = i9;
                        unsignedShort2 = i8;
                    } else if (i15 == 1937126244) {
                        projFromParent = parseProjFromParent(parsableByteArray, position2, i14);
                        listOf = list;
                        paspFromParent = f2;
                        position += i14;
                        i10 = i3;
                        i11 = i4;
                        stsdData2 = stsdData;
                        str2 = str5;
                        drmInitDataCopyWithSchemeType = drmInitData2;
                        iIntValue = i9;
                        unsignedShort2 = i8;
                    } else if (i15 == 1936995172) {
                        int unsignedByte = parsableByteArray.readUnsignedByte();
                        parsableByteArray.skipBytes(3);
                        if (unsignedByte == 0) {
                            int unsignedByte2 = parsableByteArray.readUnsignedByte();
                            if (unsignedByte2 == 0) {
                                i12 = 0;
                            } else if (unsignedByte2 == 1) {
                                i12 = 1;
                            } else if (unsignedByte2 == 2) {
                                i12 = 2;
                            } else if (unsignedByte2 == 3) {
                                i12 = 3;
                            }
                        }
                    } else if (i15 == 1668246642) {
                        int i16 = parsableByteArray.readInt();
                        if (i16 == TYPE_nclx || i16 == TYPE_nclc) {
                            int unsignedShort3 = parsableByteArray.readUnsignedShort();
                            int unsignedShort4 = parsableByteArray.readUnsignedShort();
                            parsableByteArray.skipBytes(2);
                            boolean z3 = i14 == 19 && (parsableByteArray.readUnsignedByte() & 128) != 0;
                            iIsoColorPrimariesToColorSpace = ColorInfo.isoColorPrimariesToColorSpace(unsignedShort3);
                            i13 = z3 ? 1 : 2;
                            iIsoTransferCharacteristicsToColorTransfer = ColorInfo.isoTransferCharacteristicsToColorTransfer(unsignedShort4);
                        } else {
                            String strValueOf = String.valueOf(Atom.getAtomTypeString(i16));
                            Log.w(TAG, strValueOf.length() != 0 ? "Unsupported color type: ".concat(strValueOf) : new String("Unsupported color type: "));
                        }
                    }
                }
                listOf = list;
                projFromParent = bArr;
                paspFromParent = f2;
                position += i14;
                i10 = i3;
                i11 = i4;
                stsdData2 = stsdData;
                str2 = str5;
                drmInitDataCopyWithSchemeType = drmInitData2;
                iIntValue = i9;
                unsignedShort2 = i8;
            }
            str3 = str;
            i8 = unsignedShort2;
            i9 = iIntValue;
            position += i14;
            i10 = i3;
            i11 = i4;
            stsdData2 = stsdData;
            str2 = str5;
            drmInitDataCopyWithSchemeType = drmInitData2;
            iIntValue = i9;
            unsignedShort2 = i8;
        }
        int i17 = unsignedShort2;
        byte[] bArr4 = projFromParent;
        float f4 = paspFromParent;
        List<byte[]> list3 = listOf;
        if (str3 == null) {
            return;
        }
        Format.Builder drmInitData3 = new Format.Builder().setId(i5).setSampleMimeType(str3).setCodecs(str4).setWidth(unsignedShort).setHeight(i17).setPixelWidthHeightRatio(f4).setRotationDegrees(i6).setProjectionData(bArr4).setStereoMode(i12).setInitializationData(list3).setDrmInitData(drmInitData2);
        int i18 = iIsoColorPrimariesToColorSpace;
        int i19 = i13;
        int i20 = iIsoTransferCharacteristicsToColorTransfer;
        if (i18 != -1 || i19 != -1 || i20 != -1 || byteBuffer != null) {
            drmInitData3.setColorInfo(new ColorInfo(i18, i19, i20, byteBuffer != null ? byteBuffer.array() : null));
        }
        stsdData.format = drmInitData3.build();
    }
}
