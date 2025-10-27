package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* loaded from: classes3.dex */
final class Sniffer {
    public static final int BRAND_HEIC = 1751476579;
    public static final int BRAND_QUICKTIME = 1903435808;
    private static final int[] COMPATIBLE_BRANDS = {1769172845, 1769172786, 1769172787, 1769172788, 1769172789, 1769172790, 1769172793, Atom.TYPE_avc1, Atom.TYPE_hvc1, Atom.TYPE_hev1, Atom.TYPE_av01, 1836069937, 1836069938, 862401121, 862401122, 862417462, 862417718, 862414134, 862414646, 1295275552, 1295270176, 1714714144, 1801741417, 1295275600, BRAND_QUICKTIME, 1297305174, 1684175153, 1769172332, 1885955686};
    private static final int SEARCH_LENGTH = 4096;

    private Sniffer() {
    }

    private static boolean isCompatibleBrand(int i2, boolean z2) {
        if ((i2 >>> 8) == 3368816) {
            return true;
        }
        if (i2 == 1751476579 && z2) {
            return true;
        }
        for (int i3 : COMPATIBLE_BRANDS) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public static boolean sniffFragmented(ExtractorInput extractorInput) throws IOException {
        return sniffInternal(extractorInput, true, false);
    }

    private static boolean sniffInternal(ExtractorInput extractorInput, boolean z2, boolean z3) throws IOException {
        boolean z4;
        boolean z5;
        boolean z6;
        int i2;
        boolean z7;
        boolean z8;
        long length = extractorInput.getLength();
        long j2 = -1;
        int i3 = (length > (-1L) ? 1 : (length == (-1L) ? 0 : -1));
        long j3 = 4096;
        if (i3 != 0 && length <= 4096) {
            j3 = length;
        }
        int i4 = (int) j3;
        ParsableByteArray parsableByteArray = new ParsableByteArray(64);
        boolean z9 = false;
        int i5 = 0;
        boolean z10 = false;
        while (i5 < i4) {
            parsableByteArray.reset(8);
            if (!extractorInput.peekFully(parsableByteArray.getData(), z9 ? 1 : 0, 8, true)) {
                break;
            }
            long unsignedInt = parsableByteArray.readUnsignedInt();
            int i6 = parsableByteArray.readInt();
            if (unsignedInt == 1) {
                extractorInput.peekFully(parsableByteArray.getData(), 8, 8);
                parsableByteArray.setLimit(16);
                i2 = 16;
                unsignedInt = parsableByteArray.readLong();
            } else {
                if (unsignedInt == 0) {
                    long length2 = extractorInput.getLength();
                    if (length2 != j2) {
                        unsignedInt = (length2 - extractorInput.getPeekPosition()) + 8;
                    }
                }
                i2 = 8;
            }
            long j4 = i2;
            if (unsignedInt < j4) {
                return z9;
            }
            i5 += i2;
            if (i6 == 1836019574) {
                i4 += (int) unsignedInt;
                if (i3 != 0 && i4 > length) {
                    i4 = (int) length;
                }
            } else {
                if (i6 == 1836019558 || i6 == 1836475768) {
                    z4 = z9 ? 1 : 0;
                    z5 = true;
                    z6 = true;
                    break;
                }
                int i7 = i3;
                if ((i5 + unsignedInt) - j4 >= i4) {
                    z4 = false;
                    z5 = true;
                    break;
                }
                int i8 = (int) (unsignedInt - j4);
                i5 += i8;
                if (i6 != 1718909296) {
                    z7 = false;
                    z10 = z10;
                    if (i8 != 0) {
                        extractorInput.advancePeekPosition(i8);
                        z10 = z10;
                    }
                } else {
                    if (i8 < 8) {
                        return false;
                    }
                    parsableByteArray.reset(i8);
                    extractorInput.peekFully(parsableByteArray.getData(), 0, i8);
                    int i9 = i8 / 4;
                    int i10 = 0;
                    while (true) {
                        if (i10 >= i9) {
                            z8 = z10;
                            break;
                        }
                        if (i10 == 1) {
                            parsableByteArray.skipBytes(4);
                        } else if (isCompatibleBrand(parsableByteArray.readInt(), z3)) {
                            z8 = true;
                            break;
                        }
                        i10++;
                    }
                    if (!z8) {
                        return false;
                    }
                    z7 = false;
                    z10 = z8;
                }
                z9 = z7;
                i3 = i7;
            }
            j2 = -1;
            z10 = z10;
        }
        z4 = z9 ? 1 : 0;
        z5 = true;
        z6 = z4;
        return (z10 && z2 == z6) ? z5 : z4;
    }

    public static boolean sniffUnfragmented(ExtractorInput extractorInput) throws IOException {
        return sniffInternal(extractorInput, false, false);
    }

    public static boolean sniffUnfragmented(ExtractorInput extractorInput, boolean z2) throws IOException {
        return sniffInternal(extractorInput, false, z2);
    }
}
