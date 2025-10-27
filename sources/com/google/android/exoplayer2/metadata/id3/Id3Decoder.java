package com.google.android.exoplayer2.metadata.id3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes3.dex */
public final class Id3Decoder extends SimpleMetadataDecoder {
    private static final int FRAME_FLAG_V3_HAS_GROUP_IDENTIFIER = 32;
    private static final int FRAME_FLAG_V3_IS_COMPRESSED = 128;
    private static final int FRAME_FLAG_V3_IS_ENCRYPTED = 64;
    private static final int FRAME_FLAG_V4_HAS_DATA_LENGTH = 1;
    private static final int FRAME_FLAG_V4_HAS_GROUP_IDENTIFIER = 64;
    private static final int FRAME_FLAG_V4_IS_COMPRESSED = 8;
    private static final int FRAME_FLAG_V4_IS_ENCRYPTED = 4;
    private static final int FRAME_FLAG_V4_IS_UNSYNCHRONIZED = 2;
    public static final int ID3_HEADER_LENGTH = 10;
    public static final int ID3_TAG = 4801587;
    private static final int ID3_TEXT_ENCODING_ISO_8859_1 = 0;
    private static final int ID3_TEXT_ENCODING_UTF_16 = 1;
    private static final int ID3_TEXT_ENCODING_UTF_16BE = 2;
    private static final int ID3_TEXT_ENCODING_UTF_8 = 3;
    public static final FramePredicate NO_FRAMES_PREDICATE = new FramePredicate() { // from class: com.google.android.exoplayer2.metadata.id3.a
        @Override // com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate
        public final boolean evaluate(int i2, int i3, int i4, int i5, int i6) {
            return Id3Decoder.lambda$static$0(i2, i3, i4, i5, i6);
        }
    };
    private static final String TAG = "Id3Decoder";

    @Nullable
    private final FramePredicate framePredicate;

    public interface FramePredicate {
        boolean evaluate(int i2, int i3, int i4, int i5, int i6);
    }

    public static final class Id3Header {
        private final int framesSize;
        private final boolean isUnsynchronized;
        private final int majorVersion;

        public Id3Header(int i2, boolean z2, int i3) {
            this.majorVersion = i2;
            this.isUnsynchronized = z2;
            this.framesSize = i3;
        }
    }

    public Id3Decoder() {
        this(null);
    }

    private static byte[] copyOfRangeIfValid(byte[] bArr, int i2, int i3) {
        return i3 <= i2 ? Util.EMPTY_BYTE_ARRAY : Arrays.copyOfRange(bArr, i2, i3);
    }

    private static ApicFrame decodeApicFrame(ParsableByteArray parsableByteArray, int i2, int i3) throws UnsupportedEncodingException {
        int iIndexOfZeroByte;
        String strConcat;
        int unsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i4 = i2 - 1;
        byte[] bArr = new byte[i4];
        parsableByteArray.readBytes(bArr, 0, i4);
        if (i3 == 2) {
            String strValueOf = String.valueOf(Ascii.toLowerCase(new String(bArr, 0, 3, "ISO-8859-1")));
            strConcat = strValueOf.length() != 0 ? "image/".concat(strValueOf) : new String("image/");
            if ("image/jpg".equals(strConcat)) {
                strConcat = "image/jpeg";
            }
            iIndexOfZeroByte = 2;
        } else {
            iIndexOfZeroByte = indexOfZeroByte(bArr, 0);
            String lowerCase = Ascii.toLowerCase(new String(bArr, 0, iIndexOfZeroByte, "ISO-8859-1"));
            if (lowerCase.indexOf(47) != -1) {
                strConcat = lowerCase;
            } else if (lowerCase.length() != 0) {
                strConcat = "image/".concat(lowerCase);
            } else {
                lowerCase = new String("image/");
                strConcat = lowerCase;
            }
        }
        int i5 = bArr[iIndexOfZeroByte + 1] & 255;
        int i6 = iIndexOfZeroByte + 2;
        int iIndexOfEos = indexOfEos(bArr, i6, unsignedByte);
        return new ApicFrame(strConcat, new String(bArr, i6, iIndexOfEos - i6, charsetName), i5, copyOfRangeIfValid(bArr, iIndexOfEos + delimiterLength(unsignedByte), i4));
    }

    private static BinaryFrame decodeBinaryFrame(ParsableByteArray parsableByteArray, int i2, String str) {
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        return new BinaryFrame(str, bArr);
    }

    private static ChapterFrame decodeChapterFrame(ParsableByteArray parsableByteArray, int i2, int i3, boolean z2, int i4, @Nullable FramePredicate framePredicate) throws UnsupportedEncodingException {
        int position = parsableByteArray.getPosition();
        int iIndexOfZeroByte = indexOfZeroByte(parsableByteArray.getData(), position);
        String str = new String(parsableByteArray.getData(), position, iIndexOfZeroByte - position, "ISO-8859-1");
        parsableByteArray.setPosition(iIndexOfZeroByte + 1);
        int i5 = parsableByteArray.readInt();
        int i6 = parsableByteArray.readInt();
        long unsignedInt = parsableByteArray.readUnsignedInt();
        long j2 = unsignedInt == InternalZipConstants.ZIP_64_LIMIT ? -1L : unsignedInt;
        long unsignedInt2 = parsableByteArray.readUnsignedInt();
        long j3 = unsignedInt2 == InternalZipConstants.ZIP_64_LIMIT ? -1L : unsignedInt2;
        ArrayList arrayList = new ArrayList();
        int i7 = position + i2;
        while (parsableByteArray.getPosition() < i7) {
            Id3Frame id3FrameDecodeFrame = decodeFrame(i3, parsableByteArray, z2, i4, framePredicate);
            if (id3FrameDecodeFrame != null) {
                arrayList.add(id3FrameDecodeFrame);
            }
        }
        return new ChapterFrame(str, i5, i6, j2, j3, (Id3Frame[]) arrayList.toArray(new Id3Frame[0]));
    }

    private static ChapterTocFrame decodeChapterTOCFrame(ParsableByteArray parsableByteArray, int i2, int i3, boolean z2, int i4, @Nullable FramePredicate framePredicate) throws UnsupportedEncodingException {
        int position = parsableByteArray.getPosition();
        int iIndexOfZeroByte = indexOfZeroByte(parsableByteArray.getData(), position);
        String str = new String(parsableByteArray.getData(), position, iIndexOfZeroByte - position, "ISO-8859-1");
        parsableByteArray.setPosition(iIndexOfZeroByte + 1);
        int unsignedByte = parsableByteArray.readUnsignedByte();
        boolean z3 = (unsignedByte & 2) != 0;
        boolean z4 = (unsignedByte & 1) != 0;
        int unsignedByte2 = parsableByteArray.readUnsignedByte();
        String[] strArr = new String[unsignedByte2];
        for (int i5 = 0; i5 < unsignedByte2; i5++) {
            int position2 = parsableByteArray.getPosition();
            int iIndexOfZeroByte2 = indexOfZeroByte(parsableByteArray.getData(), position2);
            strArr[i5] = new String(parsableByteArray.getData(), position2, iIndexOfZeroByte2 - position2, "ISO-8859-1");
            parsableByteArray.setPosition(iIndexOfZeroByte2 + 1);
        }
        ArrayList arrayList = new ArrayList();
        int i6 = position + i2;
        while (parsableByteArray.getPosition() < i6) {
            Id3Frame id3FrameDecodeFrame = decodeFrame(i3, parsableByteArray, z2, i4, framePredicate);
            if (id3FrameDecodeFrame != null) {
                arrayList.add(id3FrameDecodeFrame);
            }
        }
        return new ChapterTocFrame(str, z3, z4, strArr, (Id3Frame[]) arrayList.toArray(new Id3Frame[0]));
    }

    @Nullable
    private static CommentFrame decodeCommentFrame(ParsableByteArray parsableByteArray, int i2) throws UnsupportedEncodingException {
        if (i2 < 4) {
            return null;
        }
        int unsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        byte[] bArr = new byte[3];
        parsableByteArray.readBytes(bArr, 0, 3);
        String str = new String(bArr, 0, 3);
        int i3 = i2 - 4;
        byte[] bArr2 = new byte[i3];
        parsableByteArray.readBytes(bArr2, 0, i3);
        int iIndexOfEos = indexOfEos(bArr2, 0, unsignedByte);
        String str2 = new String(bArr2, 0, iIndexOfEos, charsetName);
        int iDelimiterLength = iIndexOfEos + delimiterLength(unsignedByte);
        return new CommentFrame(str, str2, decodeStringIfValid(bArr2, iDelimiterLength, indexOfEos(bArr2, iDelimiterLength, unsignedByte), charsetName));
    }

    /* JADX WARN: Removed duplicated region for block: B:140:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01ea A[Catch: all -> 0x012e, UnsupportedEncodingException -> 0x0222, TryCatch #0 {UnsupportedEncodingException -> 0x0222, blocks: (B:91:0x011c, B:159:0x01f4, B:93:0x0124, B:102:0x013d, B:104:0x0145, B:112:0x015f, B:121:0x0177, B:132:0x0192, B:139:0x01a4, B:145:0x01b3, B:150:0x01cb, B:156:0x01e5, B:157:0x01ea), top: B:169:0x0112, outer: #1 }] */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.metadata.id3.Id3Frame decodeFrame(int r19, com.google.android.exoplayer2.util.ParsableByteArray r20, boolean r21, int r22, @androidx.annotation.Nullable com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate r23) {
        /*
            Method dump skipped, instructions count: 568
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.decodeFrame(int, com.google.android.exoplayer2.util.ParsableByteArray, boolean, int, com.google.android.exoplayer2.metadata.id3.Id3Decoder$FramePredicate):com.google.android.exoplayer2.metadata.id3.Id3Frame");
    }

    private static GeobFrame decodeGeobFrame(ParsableByteArray parsableByteArray, int i2) throws UnsupportedEncodingException {
        int unsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i3 = i2 - 1;
        byte[] bArr = new byte[i3];
        parsableByteArray.readBytes(bArr, 0, i3);
        int iIndexOfZeroByte = indexOfZeroByte(bArr, 0);
        String str = new String(bArr, 0, iIndexOfZeroByte, "ISO-8859-1");
        int i4 = iIndexOfZeroByte + 1;
        int iIndexOfEos = indexOfEos(bArr, i4, unsignedByte);
        String strDecodeStringIfValid = decodeStringIfValid(bArr, i4, iIndexOfEos, charsetName);
        int iDelimiterLength = iIndexOfEos + delimiterLength(unsignedByte);
        int iIndexOfEos2 = indexOfEos(bArr, iDelimiterLength, unsignedByte);
        return new GeobFrame(str, strDecodeStringIfValid, decodeStringIfValid(bArr, iDelimiterLength, iIndexOfEos2, charsetName), copyOfRangeIfValid(bArr, iIndexOfEos2 + delimiterLength(unsignedByte), i3));
    }

    @Nullable
    private static Id3Header decodeHeader(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() < 10) {
            Log.w(TAG, "Data too short to be an ID3 tag");
            return null;
        }
        int unsignedInt24 = parsableByteArray.readUnsignedInt24();
        boolean z2 = false;
        if (unsignedInt24 != 4801587) {
            String strValueOf = String.valueOf(String.format("%06X", Integer.valueOf(unsignedInt24)));
            Log.w(TAG, strValueOf.length() != 0 ? "Unexpected first three bytes of ID3 tag header: 0x".concat(strValueOf) : new String("Unexpected first three bytes of ID3 tag header: 0x"));
            return null;
        }
        int unsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.skipBytes(1);
        int unsignedByte2 = parsableByteArray.readUnsignedByte();
        int synchSafeInt = parsableByteArray.readSynchSafeInt();
        if (unsignedByte == 2) {
            if ((unsignedByte2 & 64) != 0) {
                Log.w(TAG, "Skipped ID3 tag with majorVersion=2 and undefined compression scheme");
                return null;
            }
        } else if (unsignedByte == 3) {
            if ((unsignedByte2 & 64) != 0) {
                int i2 = parsableByteArray.readInt();
                parsableByteArray.skipBytes(i2);
                synchSafeInt -= i2 + 4;
            }
        } else {
            if (unsignedByte != 4) {
                StringBuilder sb = new StringBuilder(57);
                sb.append("Skipped ID3 tag with unsupported majorVersion=");
                sb.append(unsignedByte);
                Log.w(TAG, sb.toString());
                return null;
            }
            if ((unsignedByte2 & 64) != 0) {
                int synchSafeInt2 = parsableByteArray.readSynchSafeInt();
                parsableByteArray.skipBytes(synchSafeInt2 - 4);
                synchSafeInt -= synchSafeInt2;
            }
            if ((unsignedByte2 & 16) != 0) {
                synchSafeInt -= 10;
            }
        }
        if (unsignedByte < 4 && (unsignedByte2 & 128) != 0) {
            z2 = true;
        }
        return new Id3Header(unsignedByte, z2, synchSafeInt);
    }

    private static MlltFrame decodeMlltFrame(ParsableByteArray parsableByteArray, int i2) {
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int unsignedInt24 = parsableByteArray.readUnsignedInt24();
        int unsignedInt242 = parsableByteArray.readUnsignedInt24();
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int unsignedByte2 = parsableByteArray.readUnsignedByte();
        ParsableBitArray parsableBitArray = new ParsableBitArray();
        parsableBitArray.reset(parsableByteArray);
        int i3 = ((i2 - 10) * 8) / (unsignedByte + unsignedByte2);
        int[] iArr = new int[i3];
        int[] iArr2 = new int[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            int bits = parsableBitArray.readBits(unsignedByte);
            int bits2 = parsableBitArray.readBits(unsignedByte2);
            iArr[i4] = bits;
            iArr2[i4] = bits2;
        }
        return new MlltFrame(unsignedShort, unsignedInt24, unsignedInt242, iArr, iArr2);
    }

    private static PrivFrame decodePrivFrame(ParsableByteArray parsableByteArray, int i2) throws UnsupportedEncodingException {
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        int iIndexOfZeroByte = indexOfZeroByte(bArr, 0);
        return new PrivFrame(new String(bArr, 0, iIndexOfZeroByte, "ISO-8859-1"), copyOfRangeIfValid(bArr, iIndexOfZeroByte + 1, i2));
    }

    private static String decodeStringIfValid(byte[] bArr, int i2, int i3, String str) throws UnsupportedEncodingException {
        return (i3 <= i2 || i3 > bArr.length) ? "" : new String(bArr, i2, i3 - i2, str);
    }

    @Nullable
    private static TextInformationFrame decodeTextInformationFrame(ParsableByteArray parsableByteArray, int i2, String str) throws UnsupportedEncodingException {
        if (i2 < 1) {
            return null;
        }
        int unsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i3 = i2 - 1;
        byte[] bArr = new byte[i3];
        parsableByteArray.readBytes(bArr, 0, i3);
        return new TextInformationFrame(str, null, new String(bArr, 0, indexOfEos(bArr, 0, unsignedByte), charsetName));
    }

    @Nullable
    private static TextInformationFrame decodeTxxxFrame(ParsableByteArray parsableByteArray, int i2) throws UnsupportedEncodingException {
        if (i2 < 1) {
            return null;
        }
        int unsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i3 = i2 - 1;
        byte[] bArr = new byte[i3];
        parsableByteArray.readBytes(bArr, 0, i3);
        int iIndexOfEos = indexOfEos(bArr, 0, unsignedByte);
        String str = new String(bArr, 0, iIndexOfEos, charsetName);
        int iDelimiterLength = iIndexOfEos + delimiterLength(unsignedByte);
        return new TextInformationFrame("TXXX", str, decodeStringIfValid(bArr, iDelimiterLength, indexOfEos(bArr, iDelimiterLength, unsignedByte), charsetName));
    }

    private static UrlLinkFrame decodeUrlLinkFrame(ParsableByteArray parsableByteArray, int i2, String str) throws UnsupportedEncodingException {
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        return new UrlLinkFrame(str, null, new String(bArr, 0, indexOfZeroByte(bArr, 0), "ISO-8859-1"));
    }

    @Nullable
    private static UrlLinkFrame decodeWxxxFrame(ParsableByteArray parsableByteArray, int i2) throws UnsupportedEncodingException {
        if (i2 < 1) {
            return null;
        }
        int unsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i3 = i2 - 1;
        byte[] bArr = new byte[i3];
        parsableByteArray.readBytes(bArr, 0, i3);
        int iIndexOfEos = indexOfEos(bArr, 0, unsignedByte);
        String str = new String(bArr, 0, iIndexOfEos, charsetName);
        int iDelimiterLength = iIndexOfEos + delimiterLength(unsignedByte);
        return new UrlLinkFrame("WXXX", str, decodeStringIfValid(bArr, iDelimiterLength, indexOfZeroByte(bArr, iDelimiterLength), "ISO-8859-1"));
    }

    private static int delimiterLength(int i2) {
        return (i2 == 0 || i2 == 3) ? 1 : 2;
    }

    private static String getCharsetName(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? "ISO-8859-1" : "UTF-8" : "UTF-16BE" : "UTF-16";
    }

    private static String getFrameId(int i2, int i3, int i4, int i5, int i6) {
        return i2 == 2 ? String.format(Locale.US, "%c%c%c", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)) : String.format(Locale.US, "%c%c%c%c", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6));
    }

    private static int indexOfEos(byte[] bArr, int i2, int i3) {
        int iIndexOfZeroByte = indexOfZeroByte(bArr, i2);
        if (i3 == 0 || i3 == 3) {
            return iIndexOfZeroByte;
        }
        while (iIndexOfZeroByte < bArr.length - 1) {
            if ((iIndexOfZeroByte - i2) % 2 == 0 && bArr[iIndexOfZeroByte + 1] == 0) {
                return iIndexOfZeroByte;
            }
            iIndexOfZeroByte = indexOfZeroByte(bArr, iIndexOfZeroByte + 1);
        }
        return bArr.length;
    }

    private static int indexOfZeroByte(byte[] bArr, int i2) {
        while (i2 < bArr.length) {
            if (bArr[i2] == 0) {
                return i2;
            }
            i2++;
        }
        return bArr.length;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$0(int i2, int i3, int i4, int i5, int i6) {
        return false;
    }

    private static int removeUnsynchronization(ParsableByteArray parsableByteArray, int i2) {
        byte[] data = parsableByteArray.getData();
        int position = parsableByteArray.getPosition();
        int i3 = position;
        while (true) {
            int i4 = i3 + 1;
            if (i4 >= position + i2) {
                return i2;
            }
            if ((data[i3] & 255) == 255 && data[i4] == 0) {
                System.arraycopy(data, i3 + 2, data, i4, (i2 - (i3 - position)) - 2);
                i2--;
            }
            i3 = i4;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0079 A[PHI: r3
      0x0079: PHI (r3v16 int) = (r3v5 int), (r3v19 int) binds: [B:40:0x0086, B:31:0x0076] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean validateFrames(com.google.android.exoplayer2.util.ParsableByteArray r18, int r19, int r20, boolean r21) {
        /*
            r1 = r18
            r0 = r19
            int r2 = r18.getPosition()
        L8:
            int r3 = r18.bytesLeft()     // Catch: java.lang.Throwable -> Laf
            r4 = 1
            r5 = r20
            if (r3 < r5) goto Lab
            r3 = 3
            r6 = 0
            if (r0 < r3) goto L22
            int r7 = r18.readInt()     // Catch: java.lang.Throwable -> Laf
            long r8 = r18.readUnsignedInt()     // Catch: java.lang.Throwable -> Laf
            int r10 = r18.readUnsignedShort()     // Catch: java.lang.Throwable -> Laf
            goto L2c
        L22:
            int r7 = r18.readUnsignedInt24()     // Catch: java.lang.Throwable -> Laf
            int r8 = r18.readUnsignedInt24()     // Catch: java.lang.Throwable -> Laf
            long r8 = (long) r8
            r10 = r6
        L2c:
            r11 = 0
            if (r7 != 0) goto L3a
            int r7 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r7 != 0) goto L3a
            if (r10 != 0) goto L3a
            r1.setPosition(r2)
            return r4
        L3a:
            r7 = 4
            if (r0 != r7) goto L6b
            if (r21 != 0) goto L6b
            r13 = 8421504(0x808080, double:4.160776E-317)
            long r13 = r13 & r8
            int r11 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r11 == 0) goto L4b
            r1.setPosition(r2)
            return r6
        L4b:
            r11 = 255(0xff, double:1.26E-321)
            long r13 = r8 & r11
            r15 = 8
            long r15 = r8 >> r15
            long r15 = r15 & r11
            r17 = 7
            long r15 = r15 << r17
            long r13 = r13 | r15
            r15 = 16
            long r15 = r8 >> r15
            long r15 = r15 & r11
            r17 = 14
            long r15 = r15 << r17
            long r13 = r13 | r15
            r15 = 24
            long r8 = r8 >> r15
            long r8 = r8 & r11
            r11 = 21
            long r8 = r8 << r11
            long r8 = r8 | r13
        L6b:
            if (r0 != r7) goto L7b
            r3 = r10 & 64
            if (r3 == 0) goto L73
            r3 = r4
            goto L74
        L73:
            r3 = r6
        L74:
            r7 = r10 & 1
            if (r7 == 0) goto L79
            goto L8b
        L79:
            r4 = r6
            goto L8b
        L7b:
            if (r0 != r3) goto L89
            r3 = r10 & 32
            if (r3 == 0) goto L83
            r3 = r4
            goto L84
        L83:
            r3 = r6
        L84:
            r7 = r10 & 128(0x80, float:1.8E-43)
            if (r7 == 0) goto L79
            goto L8b
        L89:
            r3 = r6
            r4 = r3
        L8b:
            if (r4 == 0) goto L8f
            int r3 = r3 + 4
        L8f:
            long r3 = (long) r3
            int r3 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r3 >= 0) goto L98
            r1.setPosition(r2)
            return r6
        L98:
            int r3 = r18.bytesLeft()     // Catch: java.lang.Throwable -> Laf
            long r3 = (long) r3
            int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r3 >= 0) goto La5
            r1.setPosition(r2)
            return r6
        La5:
            int r3 = (int) r8
            r1.skipBytes(r3)     // Catch: java.lang.Throwable -> Laf
            goto L8
        Lab:
            r1.setPosition(r2)
            return r4
        Laf:
            r0 = move-exception
            r1.setPosition(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.validateFrames(com.google.android.exoplayer2.util.ParsableByteArray, int, int, boolean):boolean");
    }

    @Override // com.google.android.exoplayer2.metadata.SimpleMetadataDecoder
    @Nullable
    public Metadata decode(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer) {
        return decode(byteBuffer.array(), byteBuffer.limit());
    }

    public Id3Decoder(@Nullable FramePredicate framePredicate) {
        this.framePredicate = framePredicate;
    }

    @Nullable
    public Metadata decode(byte[] bArr, int i2) {
        ArrayList arrayList = new ArrayList();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i2);
        Id3Header id3HeaderDecodeHeader = decodeHeader(parsableByteArray);
        if (id3HeaderDecodeHeader == null) {
            return null;
        }
        int position = parsableByteArray.getPosition();
        int i3 = id3HeaderDecodeHeader.majorVersion == 2 ? 6 : 10;
        int iRemoveUnsynchronization = id3HeaderDecodeHeader.framesSize;
        if (id3HeaderDecodeHeader.isUnsynchronized) {
            iRemoveUnsynchronization = removeUnsynchronization(parsableByteArray, id3HeaderDecodeHeader.framesSize);
        }
        parsableByteArray.setLimit(position + iRemoveUnsynchronization);
        boolean z2 = false;
        if (!validateFrames(parsableByteArray, id3HeaderDecodeHeader.majorVersion, i3, false)) {
            if (id3HeaderDecodeHeader.majorVersion != 4 || !validateFrames(parsableByteArray, 4, i3, true)) {
                int i4 = id3HeaderDecodeHeader.majorVersion;
                StringBuilder sb = new StringBuilder(56);
                sb.append("Failed to validate ID3 tag with majorVersion=");
                sb.append(i4);
                Log.w(TAG, sb.toString());
                return null;
            }
            z2 = true;
        }
        while (parsableByteArray.bytesLeft() >= i3) {
            Id3Frame id3FrameDecodeFrame = decodeFrame(id3HeaderDecodeHeader.majorVersion, parsableByteArray, z2, i3, this.framePredicate);
            if (id3FrameDecodeFrame != null) {
                arrayList.add(id3FrameDecodeFrame);
            }
        }
        return new Metadata(arrayList);
    }
}
