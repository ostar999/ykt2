package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.flac.PictureFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class FlacMetadataReader {
    private static final int SEEK_POINT_SIZE = 18;
    private static final int STREAM_MARKER = 1716281667;
    private static final int SYNC_CODE = 16382;

    public static final class FlacStreamMetadataHolder {

        @Nullable
        public FlacStreamMetadata flacStreamMetadata;

        public FlacStreamMetadataHolder(@Nullable FlacStreamMetadata flacStreamMetadata) {
            this.flacStreamMetadata = flacStreamMetadata;
        }
    }

    private FlacMetadataReader() {
    }

    public static boolean checkAndPeekStreamMarker(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(4);
        extractorInput.peekFully(parsableByteArray.getData(), 0, 4);
        return parsableByteArray.readUnsignedInt() == 1716281667;
    }

    public static int getFrameStartMarker(ExtractorInput extractorInput) throws IOException {
        extractorInput.resetPeekPosition();
        ParsableByteArray parsableByteArray = new ParsableByteArray(2);
        extractorInput.peekFully(parsableByteArray.getData(), 0, 2);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        if ((unsignedShort >> 2) == 16382) {
            extractorInput.resetPeekPosition();
            return unsignedShort;
        }
        extractorInput.resetPeekPosition();
        throw ParserException.createForMalformedContainer("First frame does not start with sync code.", null);
    }

    @Nullable
    public static Metadata peekId3Metadata(ExtractorInput extractorInput, boolean z2) throws IOException {
        Metadata metadataPeekId3Data = new Id3Peeker().peekId3Data(extractorInput, z2 ? null : Id3Decoder.NO_FRAMES_PREDICATE);
        if (metadataPeekId3Data == null || metadataPeekId3Data.length() == 0) {
            return null;
        }
        return metadataPeekId3Data;
    }

    @Nullable
    public static Metadata readId3Metadata(ExtractorInput extractorInput, boolean z2) throws IOException {
        extractorInput.resetPeekPosition();
        long peekPosition = extractorInput.getPeekPosition();
        Metadata metadataPeekId3Metadata = peekId3Metadata(extractorInput, z2);
        extractorInput.skipFully((int) (extractorInput.getPeekPosition() - peekPosition));
        return metadataPeekId3Metadata;
    }

    public static boolean readMetadataBlock(ExtractorInput extractorInput, FlacStreamMetadataHolder flacStreamMetadataHolder) throws IOException {
        extractorInput.resetPeekPosition();
        ParsableBitArray parsableBitArray = new ParsableBitArray(new byte[4]);
        extractorInput.peekFully(parsableBitArray.data, 0, 4);
        boolean bit = parsableBitArray.readBit();
        int bits = parsableBitArray.readBits(7);
        int bits2 = parsableBitArray.readBits(24) + 4;
        if (bits == 0) {
            flacStreamMetadataHolder.flacStreamMetadata = readStreamInfoBlock(extractorInput);
        } else {
            FlacStreamMetadata flacStreamMetadata = flacStreamMetadataHolder.flacStreamMetadata;
            if (flacStreamMetadata == null) {
                throw new IllegalArgumentException();
            }
            if (bits == 3) {
                flacStreamMetadataHolder.flacStreamMetadata = flacStreamMetadata.copyWithSeekTable(readSeekTableMetadataBlock(extractorInput, bits2));
            } else if (bits == 4) {
                flacStreamMetadataHolder.flacStreamMetadata = flacStreamMetadata.copyWithVorbisComments(readVorbisCommentMetadataBlock(extractorInput, bits2));
            } else if (bits == 6) {
                flacStreamMetadataHolder.flacStreamMetadata = flacStreamMetadata.copyWithPictureFrames(Collections.singletonList(readPictureMetadataBlock(extractorInput, bits2)));
            } else {
                extractorInput.skipFully(bits2);
            }
        }
        return bit;
    }

    private static PictureFrame readPictureMetadataBlock(ExtractorInput extractorInput, int i2) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(i2);
        extractorInput.readFully(parsableByteArray.getData(), 0, i2);
        parsableByteArray.skipBytes(4);
        int i3 = parsableByteArray.readInt();
        String string = parsableByteArray.readString(parsableByteArray.readInt(), Charsets.US_ASCII);
        String string2 = parsableByteArray.readString(parsableByteArray.readInt());
        int i4 = parsableByteArray.readInt();
        int i5 = parsableByteArray.readInt();
        int i6 = parsableByteArray.readInt();
        int i7 = parsableByteArray.readInt();
        int i8 = parsableByteArray.readInt();
        byte[] bArr = new byte[i8];
        parsableByteArray.readBytes(bArr, 0, i8);
        return new PictureFrame(i3, string, string2, i4, i5, i6, i7, bArr);
    }

    public static FlacStreamMetadata.SeekTable readSeekTableMetadataBlock(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(1);
        int unsignedInt24 = parsableByteArray.readUnsignedInt24();
        long position = parsableByteArray.getPosition() + unsignedInt24;
        int i2 = unsignedInt24 / 18;
        long[] jArrCopyOf = new long[i2];
        long[] jArrCopyOf2 = new long[i2];
        int i3 = 0;
        while (true) {
            if (i3 >= i2) {
                break;
            }
            long j2 = parsableByteArray.readLong();
            if (j2 == -1) {
                jArrCopyOf = Arrays.copyOf(jArrCopyOf, i3);
                jArrCopyOf2 = Arrays.copyOf(jArrCopyOf2, i3);
                break;
            }
            jArrCopyOf[i3] = j2;
            jArrCopyOf2[i3] = parsableByteArray.readLong();
            parsableByteArray.skipBytes(2);
            i3++;
        }
        parsableByteArray.skipBytes((int) (position - parsableByteArray.getPosition()));
        return new FlacStreamMetadata.SeekTable(jArrCopyOf, jArrCopyOf2);
    }

    private static FlacStreamMetadata readStreamInfoBlock(ExtractorInput extractorInput) throws IOException {
        byte[] bArr = new byte[38];
        extractorInput.readFully(bArr, 0, 38);
        return new FlacStreamMetadata(bArr, 4);
    }

    public static void readStreamMarker(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(4);
        extractorInput.readFully(parsableByteArray.getData(), 0, 4);
        if (parsableByteArray.readUnsignedInt() != 1716281667) {
            throw ParserException.createForMalformedContainer("Failed to read FLAC stream marker.", null);
        }
    }

    private static List<String> readVorbisCommentMetadataBlock(ExtractorInput extractorInput, int i2) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(i2);
        extractorInput.readFully(parsableByteArray.getData(), 0, i2);
        parsableByteArray.skipBytes(4);
        return Arrays.asList(VorbisUtil.readVorbisCommentHeader(parsableByteArray, false, false).comments);
    }

    private static FlacStreamMetadata.SeekTable readSeekTableMetadataBlock(ExtractorInput extractorInput, int i2) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(i2);
        extractorInput.readFully(parsableByteArray.getData(), 0, i2);
        return readSeekTableMetadataBlock(parsableByteArray);
    }
}
