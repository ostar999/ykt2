package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class VorbisUtil {
    private static final String TAG = "VorbisUtil";

    public static final class CodeBook {
        public final int dimensions;
        public final int entries;
        public final boolean isOrdered;
        public final long[] lengthMap;
        public final int lookupType;

        public CodeBook(int i2, int i3, long[] jArr, int i4, boolean z2) {
            this.dimensions = i2;
            this.entries = i3;
            this.lengthMap = jArr;
            this.lookupType = i4;
            this.isOrdered = z2;
        }
    }

    public static final class CommentHeader {
        public final String[] comments;
        public final int length;
        public final String vendor;

        public CommentHeader(String str, String[] strArr, int i2) {
            this.vendor = str;
            this.comments = strArr;
            this.length = i2;
        }
    }

    public static final class Mode {
        public final boolean blockFlag;
        public final int mapping;
        public final int transformType;
        public final int windowType;

        public Mode(boolean z2, int i2, int i3, int i4) {
            this.blockFlag = z2;
            this.windowType = i2;
            this.transformType = i3;
            this.mapping = i4;
        }
    }

    public static final class VorbisIdHeader {
        public final int bitrateMaximum;
        public final int bitrateMinimum;
        public final int bitrateNominal;
        public final int blockSize0;
        public final int blockSize1;
        public final int channels;
        public final byte[] data;
        public final boolean framingFlag;
        public final int sampleRate;
        public final int version;

        public VorbisIdHeader(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z2, byte[] bArr) {
            this.version = i2;
            this.channels = i3;
            this.sampleRate = i4;
            this.bitrateMaximum = i5;
            this.bitrateNominal = i6;
            this.bitrateMinimum = i7;
            this.blockSize0 = i8;
            this.blockSize1 = i9;
            this.framingFlag = z2;
            this.data = bArr;
        }
    }

    private VorbisUtil() {
    }

    public static int iLog(int i2) {
        int i3 = 0;
        while (i2 > 0) {
            i3++;
            i2 >>>= 1;
        }
        return i3;
    }

    private static long mapType1QuantValues(long j2, long j3) {
        return (long) Math.floor(Math.pow(j2, 1.0d / j3));
    }

    private static CodeBook readBook(VorbisBitArray vorbisBitArray) throws ParserException {
        if (vorbisBitArray.readBits(24) != 5653314) {
            int position = vorbisBitArray.getPosition();
            StringBuilder sb = new StringBuilder(66);
            sb.append("expected code book to start with [0x56, 0x43, 0x42] at ");
            sb.append(position);
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }
        int bits = vorbisBitArray.readBits(16);
        int bits2 = vorbisBitArray.readBits(24);
        long[] jArr = new long[bits2];
        boolean bit = vorbisBitArray.readBit();
        long jMapType1QuantValues = 0;
        if (bit) {
            int bits3 = vorbisBitArray.readBits(5) + 1;
            int i2 = 0;
            while (i2 < bits2) {
                int bits4 = vorbisBitArray.readBits(iLog(bits2 - i2));
                for (int i3 = 0; i3 < bits4 && i2 < bits2; i3++) {
                    jArr[i2] = bits3;
                    i2++;
                }
                bits3++;
            }
        } else {
            boolean bit2 = vorbisBitArray.readBit();
            for (int i4 = 0; i4 < bits2; i4++) {
                if (!bit2) {
                    jArr[i4] = vorbisBitArray.readBits(5) + 1;
                } else if (vorbisBitArray.readBit()) {
                    jArr[i4] = vorbisBitArray.readBits(5) + 1;
                } else {
                    jArr[i4] = 0;
                }
            }
        }
        int bits5 = vorbisBitArray.readBits(4);
        if (bits5 > 2) {
            StringBuilder sb2 = new StringBuilder(53);
            sb2.append("lookup type greater than 2 not decodable: ");
            sb2.append(bits5);
            throw ParserException.createForMalformedContainer(sb2.toString(), null);
        }
        if (bits5 == 1 || bits5 == 2) {
            vorbisBitArray.skipBits(32);
            vorbisBitArray.skipBits(32);
            int bits6 = vorbisBitArray.readBits(4) + 1;
            vorbisBitArray.skipBits(1);
            if (bits5 != 1) {
                jMapType1QuantValues = bits2 * bits;
            } else if (bits != 0) {
                jMapType1QuantValues = mapType1QuantValues(bits2, bits);
            }
            vorbisBitArray.skipBits((int) (jMapType1QuantValues * bits6));
        }
        return new CodeBook(bits, bits2, jArr, bits5, bit);
    }

    private static void readFloors(VorbisBitArray vorbisBitArray) throws ParserException {
        int bits = vorbisBitArray.readBits(6) + 1;
        for (int i2 = 0; i2 < bits; i2++) {
            int bits2 = vorbisBitArray.readBits(16);
            if (bits2 == 0) {
                vorbisBitArray.skipBits(8);
                vorbisBitArray.skipBits(16);
                vorbisBitArray.skipBits(16);
                vorbisBitArray.skipBits(6);
                vorbisBitArray.skipBits(8);
                int bits3 = vorbisBitArray.readBits(4) + 1;
                for (int i3 = 0; i3 < bits3; i3++) {
                    vorbisBitArray.skipBits(8);
                }
            } else {
                if (bits2 != 1) {
                    StringBuilder sb = new StringBuilder(52);
                    sb.append("floor type greater than 1 not decodable: ");
                    sb.append(bits2);
                    throw ParserException.createForMalformedContainer(sb.toString(), null);
                }
                int bits4 = vorbisBitArray.readBits(5);
                int[] iArr = new int[bits4];
                int i4 = -1;
                for (int i5 = 0; i5 < bits4; i5++) {
                    int bits5 = vorbisBitArray.readBits(4);
                    iArr[i5] = bits5;
                    if (bits5 > i4) {
                        i4 = bits5;
                    }
                }
                int i6 = i4 + 1;
                int[] iArr2 = new int[i6];
                for (int i7 = 0; i7 < i6; i7++) {
                    iArr2[i7] = vorbisBitArray.readBits(3) + 1;
                    int bits6 = vorbisBitArray.readBits(2);
                    if (bits6 > 0) {
                        vorbisBitArray.skipBits(8);
                    }
                    for (int i8 = 0; i8 < (1 << bits6); i8++) {
                        vorbisBitArray.skipBits(8);
                    }
                }
                vorbisBitArray.skipBits(2);
                int bits7 = vorbisBitArray.readBits(4);
                int i9 = 0;
                int i10 = 0;
                for (int i11 = 0; i11 < bits4; i11++) {
                    i9 += iArr2[iArr[i11]];
                    while (i10 < i9) {
                        vorbisBitArray.skipBits(bits7);
                        i10++;
                    }
                }
            }
        }
    }

    private static void readMappings(int i2, VorbisBitArray vorbisBitArray) throws ParserException {
        int bits = vorbisBitArray.readBits(6) + 1;
        for (int i3 = 0; i3 < bits; i3++) {
            int bits2 = vorbisBitArray.readBits(16);
            if (bits2 != 0) {
                StringBuilder sb = new StringBuilder(52);
                sb.append("mapping type other than 0 not supported: ");
                sb.append(bits2);
                Log.e(TAG, sb.toString());
            } else {
                int bits3 = vorbisBitArray.readBit() ? vorbisBitArray.readBits(4) + 1 : 1;
                if (vorbisBitArray.readBit()) {
                    int bits4 = vorbisBitArray.readBits(8) + 1;
                    for (int i4 = 0; i4 < bits4; i4++) {
                        int i5 = i2 - 1;
                        vorbisBitArray.skipBits(iLog(i5));
                        vorbisBitArray.skipBits(iLog(i5));
                    }
                }
                if (vorbisBitArray.readBits(2) != 0) {
                    throw ParserException.createForMalformedContainer("to reserved bits must be zero after mapping coupling steps", null);
                }
                if (bits3 > 1) {
                    for (int i6 = 0; i6 < i2; i6++) {
                        vorbisBitArray.skipBits(4);
                    }
                }
                for (int i7 = 0; i7 < bits3; i7++) {
                    vorbisBitArray.skipBits(8);
                    vorbisBitArray.skipBits(8);
                    vorbisBitArray.skipBits(8);
                }
            }
        }
    }

    private static Mode[] readModes(VorbisBitArray vorbisBitArray) {
        int bits = vorbisBitArray.readBits(6) + 1;
        Mode[] modeArr = new Mode[bits];
        for (int i2 = 0; i2 < bits; i2++) {
            modeArr[i2] = new Mode(vorbisBitArray.readBit(), vorbisBitArray.readBits(16), vorbisBitArray.readBits(16), vorbisBitArray.readBits(8));
        }
        return modeArr;
    }

    private static void readResidues(VorbisBitArray vorbisBitArray) throws ParserException {
        int bits = vorbisBitArray.readBits(6) + 1;
        for (int i2 = 0; i2 < bits; i2++) {
            if (vorbisBitArray.readBits(16) > 2) {
                throw ParserException.createForMalformedContainer("residueType greater than 2 is not decodable", null);
            }
            vorbisBitArray.skipBits(24);
            vorbisBitArray.skipBits(24);
            vorbisBitArray.skipBits(24);
            int bits2 = vorbisBitArray.readBits(6) + 1;
            vorbisBitArray.skipBits(8);
            int[] iArr = new int[bits2];
            for (int i3 = 0; i3 < bits2; i3++) {
                iArr[i3] = ((vorbisBitArray.readBit() ? vorbisBitArray.readBits(5) : 0) * 8) + vorbisBitArray.readBits(3);
            }
            for (int i4 = 0; i4 < bits2; i4++) {
                for (int i5 = 0; i5 < 8; i5++) {
                    if ((iArr[i4] & (1 << i5)) != 0) {
                        vorbisBitArray.skipBits(8);
                    }
                }
            }
        }
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray parsableByteArray) throws ParserException {
        return readVorbisCommentHeader(parsableByteArray, true, true);
    }

    public static VorbisIdHeader readVorbisIdentificationHeader(ParsableByteArray parsableByteArray) throws ParserException {
        verifyVorbisHeaderCapturePattern(1, parsableByteArray, false);
        int littleEndianUnsignedIntToInt = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int littleEndianUnsignedIntToInt2 = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int littleEndianInt = parsableByteArray.readLittleEndianInt();
        if (littleEndianInt <= 0) {
            littleEndianInt = -1;
        }
        int littleEndianInt2 = parsableByteArray.readLittleEndianInt();
        if (littleEndianInt2 <= 0) {
            littleEndianInt2 = -1;
        }
        int littleEndianInt3 = parsableByteArray.readLittleEndianInt();
        if (littleEndianInt3 <= 0) {
            littleEndianInt3 = -1;
        }
        int unsignedByte2 = parsableByteArray.readUnsignedByte();
        return new VorbisIdHeader(littleEndianUnsignedIntToInt, unsignedByte, littleEndianUnsignedIntToInt2, littleEndianInt, littleEndianInt2, littleEndianInt3, (int) Math.pow(2.0d, unsignedByte2 & 15), (int) Math.pow(2.0d, (unsignedByte2 & 240) >> 4), (parsableByteArray.readUnsignedByte() & 1) > 0, Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit()));
    }

    public static Mode[] readVorbisModes(ParsableByteArray parsableByteArray, int i2) throws ParserException {
        verifyVorbisHeaderCapturePattern(5, parsableByteArray, false);
        int unsignedByte = parsableByteArray.readUnsignedByte() + 1;
        VorbisBitArray vorbisBitArray = new VorbisBitArray(parsableByteArray.getData());
        vorbisBitArray.skipBits(parsableByteArray.getPosition() * 8);
        for (int i3 = 0; i3 < unsignedByte; i3++) {
            readBook(vorbisBitArray);
        }
        int bits = vorbisBitArray.readBits(6) + 1;
        for (int i4 = 0; i4 < bits; i4++) {
            if (vorbisBitArray.readBits(16) != 0) {
                throw ParserException.createForMalformedContainer("placeholder of time domain transforms not zeroed out", null);
            }
        }
        readFloors(vorbisBitArray);
        readResidues(vorbisBitArray);
        readMappings(i2, vorbisBitArray);
        Mode[] modes = readModes(vorbisBitArray);
        if (vorbisBitArray.readBit()) {
            return modes;
        }
        throw ParserException.createForMalformedContainer("framing bit after modes not set as expected", null);
    }

    public static boolean verifyVorbisHeaderCapturePattern(int i2, ParsableByteArray parsableByteArray, boolean z2) throws ParserException {
        if (parsableByteArray.bytesLeft() < 7) {
            if (z2) {
                return false;
            }
            int iBytesLeft = parsableByteArray.bytesLeft();
            StringBuilder sb = new StringBuilder(29);
            sb.append("too short header: ");
            sb.append(iBytesLeft);
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }
        if (parsableByteArray.readUnsignedByte() != i2) {
            if (z2) {
                return false;
            }
            String strValueOf = String.valueOf(Integer.toHexString(i2));
            throw ParserException.createForMalformedContainer(strValueOf.length() != 0 ? "expected header type ".concat(strValueOf) : new String("expected header type "), null);
        }
        if (parsableByteArray.readUnsignedByte() == 118 && parsableByteArray.readUnsignedByte() == 111 && parsableByteArray.readUnsignedByte() == 114 && parsableByteArray.readUnsignedByte() == 98 && parsableByteArray.readUnsignedByte() == 105 && parsableByteArray.readUnsignedByte() == 115) {
            return true;
        }
        if (z2) {
            return false;
        }
        throw ParserException.createForMalformedContainer("expected characters 'vorbis'", null);
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray parsableByteArray, boolean z2, boolean z3) throws ParserException {
        if (z2) {
            verifyVorbisHeaderCapturePattern(3, parsableByteArray, false);
        }
        String string = parsableByteArray.readString((int) parsableByteArray.readLittleEndianUnsignedInt());
        int length = 11 + string.length();
        long littleEndianUnsignedInt = parsableByteArray.readLittleEndianUnsignedInt();
        String[] strArr = new String[(int) littleEndianUnsignedInt];
        int length2 = length + 4;
        for (int i2 = 0; i2 < littleEndianUnsignedInt; i2++) {
            String string2 = parsableByteArray.readString((int) parsableByteArray.readLittleEndianUnsignedInt());
            strArr[i2] = string2;
            length2 = length2 + 4 + string2.length();
        }
        if (z3 && (parsableByteArray.readUnsignedByte() & 1) == 0) {
            throw ParserException.createForMalformedContainer("framing bit expected to be set", null);
        }
        return new CommentHeader(string, strArr, length2 + 1);
    }
}
