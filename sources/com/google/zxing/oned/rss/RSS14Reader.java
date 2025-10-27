package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class RSS14Reader extends AbstractRSSReader {
    private final List<Pair> possibleLeftPairs = new ArrayList();
    private final List<Pair> possibleRightPairs = new ArrayList();
    private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET = {1, 10, 34, 70, 126};
    private static final int[] INSIDE_ODD_TOTAL_SUBSET = {4, 20, 48, 81};
    private static final int[] OUTSIDE_GSUM = {0, 161, R2.attr.circle_24hot, 2015, R2.attr.orientation};
    private static final int[] INSIDE_GSUM = {0, R2.attr.app_title_color, 1036, R2.attr.expandedTitleMargin};
    private static final int[] OUTSIDE_ODD_WIDEST = {8, 6, 4, 3, 1};
    private static final int[] INSIDE_ODD_WIDEST = {2, 4, 6, 8};
    private static final int[][] FINDER_PATTERNS = {new int[]{3, 8, 2, 1}, new int[]{3, 5, 5, 1}, new int[]{3, 3, 7, 1}, new int[]{3, 1, 9, 1}, new int[]{2, 7, 4, 1}, new int[]{2, 5, 6, 1}, new int[]{2, 3, 8, 1}, new int[]{1, 5, 7, 1}, new int[]{1, 3, 9, 1}};

    private static void addOrTally(Collection<Pair> collection, Pair pair) {
        boolean z2;
        if (pair == null) {
            return;
        }
        Iterator<Pair> it = collection.iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            }
            Pair next = it.next();
            if (next.getValue() == pair.getValue()) {
                next.incrementCount();
                z2 = true;
                break;
            }
        }
        if (z2) {
            return;
        }
        collection.add(pair);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0051 A[PHI: r7 r8
      0x0051: PHI (r7v5 boolean) = (r7v2 boolean), (r7v10 boolean) binds: [B:32:0x004f, B:20:0x0037] A[DONT_GENERATE, DONT_INLINE]
      0x0051: PHI (r8v5 boolean) = (r8v2 boolean), (r8v14 boolean) binds: [B:32:0x004f, B:20:0x0037] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0054 A[PHI: r7 r8
      0x0054: PHI (r7v7 boolean) = (r7v2 boolean), (r7v10 boolean) binds: [B:32:0x004f, B:20:0x0037] A[DONT_GENERATE, DONT_INLINE]
      0x0054: PHI (r8v11 boolean) = (r8v2 boolean), (r8v14 boolean) binds: [B:32:0x004f, B:20:0x0037] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void adjustOddEvenCounts(boolean r10, int r11) throws com.google.zxing.NotFoundException {
        /*
            Method dump skipped, instructions count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.rss.RSS14Reader.adjustOddEvenCounts(boolean, int):void");
    }

    private static boolean checkChecksum(Pair pair, Pair pair2) {
        int checksumPortion = (pair.getChecksumPortion() + (pair2.getChecksumPortion() * 16)) % 79;
        int value = (pair.getFinderPattern().getValue() * 9) + pair2.getFinderPattern().getValue();
        if (value > 72) {
            value--;
        }
        if (value > 8) {
            value--;
        }
        return checksumPortion == value;
    }

    private static Result constructResult(Pair pair, Pair pair2) {
        String strValueOf = String.valueOf((pair.getValue() * 4537077) + pair2.getValue());
        StringBuilder sb = new StringBuilder(14);
        for (int length = 13 - strValueOf.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(strValueOf);
        int i2 = 0;
        for (int i3 = 0; i3 < 13; i3++) {
            int iCharAt = sb.charAt(i3) - '0';
            if ((i3 & 1) == 0) {
                iCharAt *= 3;
            }
            i2 += iCharAt;
        }
        int i4 = 10 - (i2 % 10);
        if (i4 == 10) {
            i4 = 0;
        }
        sb.append(i4);
        ResultPoint[] resultPoints = pair.getFinderPattern().getResultPoints();
        ResultPoint[] resultPoints2 = pair2.getFinderPattern().getResultPoints();
        return new Result(String.valueOf(sb.toString()), null, new ResultPoint[]{resultPoints[0], resultPoints[1], resultPoints2[0], resultPoints2[1]}, BarcodeFormat.RSS_14);
    }

    private DataCharacter decodeDataCharacter(BitArray bitArray, FinderPattern finderPattern, boolean z2) throws NotFoundException {
        int[] dataCharacterCounters = getDataCharacterCounters();
        dataCharacterCounters[0] = 0;
        dataCharacterCounters[1] = 0;
        dataCharacterCounters[2] = 0;
        dataCharacterCounters[3] = 0;
        dataCharacterCounters[4] = 0;
        dataCharacterCounters[5] = 0;
        dataCharacterCounters[6] = 0;
        dataCharacterCounters[7] = 0;
        if (z2) {
            OneDReader.recordPatternInReverse(bitArray, finderPattern.getStartEnd()[0], dataCharacterCounters);
        } else {
            OneDReader.recordPattern(bitArray, finderPattern.getStartEnd()[1] + 1, dataCharacterCounters);
            int i2 = 0;
            for (int length = dataCharacterCounters.length - 1; i2 < length; length--) {
                int i3 = dataCharacterCounters[i2];
                dataCharacterCounters[i2] = dataCharacterCounters[length];
                dataCharacterCounters[length] = i3;
                i2++;
            }
        }
        int i4 = z2 ? 16 : 15;
        float fCount = AbstractRSSReader.count(dataCharacterCounters) / i4;
        int[] oddCounts = getOddCounts();
        int[] evenCounts = getEvenCounts();
        float[] oddRoundingErrors = getOddRoundingErrors();
        float[] evenRoundingErrors = getEvenRoundingErrors();
        for (int i5 = 0; i5 < dataCharacterCounters.length; i5++) {
            float f2 = dataCharacterCounters[i5] / fCount;
            int i6 = (int) (0.5f + f2);
            if (i6 < 1) {
                i6 = 1;
            } else if (i6 > 8) {
                i6 = 8;
            }
            int i7 = i5 >> 1;
            if ((i5 & 1) == 0) {
                oddCounts[i7] = i6;
                oddRoundingErrors[i7] = f2 - i6;
            } else {
                evenCounts[i7] = i6;
                evenRoundingErrors[i7] = f2 - i6;
            }
        }
        adjustOddEvenCounts(z2, i4);
        int i8 = 0;
        int i9 = 0;
        for (int length2 = oddCounts.length - 1; length2 >= 0; length2--) {
            int i10 = oddCounts[length2];
            i8 = (i8 * 9) + i10;
            i9 += i10;
        }
        int i11 = 0;
        int i12 = 0;
        for (int length3 = evenCounts.length - 1; length3 >= 0; length3--) {
            int i13 = evenCounts[length3];
            i11 = (i11 * 9) + i13;
            i12 += i13;
        }
        int i14 = i8 + (i11 * 3);
        if (!z2) {
            if ((i12 & 1) != 0 || i12 > 10 || i12 < 4) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i15 = (10 - i12) / 2;
            int i16 = INSIDE_ODD_WIDEST[i15];
            return new DataCharacter((RSSUtils.getRSSvalue(evenCounts, 9 - i16, false) * INSIDE_ODD_TOTAL_SUBSET[i15]) + RSSUtils.getRSSvalue(oddCounts, i16, true) + INSIDE_GSUM[i15], i14);
        }
        if ((i9 & 1) != 0 || i9 > 12 || i9 < 4) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i17 = (12 - i9) / 2;
        int i18 = OUTSIDE_ODD_WIDEST[i17];
        return new DataCharacter((RSSUtils.getRSSvalue(oddCounts, i18, false) * OUTSIDE_EVEN_TOTAL_SUBSET[i17]) + RSSUtils.getRSSvalue(evenCounts, 9 - i18, true) + OUTSIDE_GSUM[i17], i14);
    }

    private Pair decodePair(BitArray bitArray, boolean z2, int i2, Map<DecodeHintType, ?> map) {
        try {
            FinderPattern foundFinderPattern = parseFoundFinderPattern(bitArray, i2, z2, findFinderPattern(bitArray, 0, z2));
            ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (resultPointCallback != null) {
                float size = (r2[0] + r2[1]) / 2.0f;
                if (z2) {
                    size = (bitArray.getSize() - 1) - size;
                }
                resultPointCallback.foundPossibleResultPoint(new ResultPoint(size, i2));
            }
            DataCharacter dataCharacterDecodeDataCharacter = decodeDataCharacter(bitArray, foundFinderPattern, true);
            DataCharacter dataCharacterDecodeDataCharacter2 = decodeDataCharacter(bitArray, foundFinderPattern, false);
            return new Pair((dataCharacterDecodeDataCharacter.getValue() * R2.attr.fontProviderCerts) + dataCharacterDecodeDataCharacter2.getValue(), dataCharacterDecodeDataCharacter.getChecksumPortion() + (dataCharacterDecodeDataCharacter2.getChecksumPortion() * 4), foundFinderPattern);
        } catch (NotFoundException unused) {
            return null;
        }
    }

    private int[] findFinderPattern(BitArray bitArray, int i2, boolean z2) throws NotFoundException {
        int[] decodeFinderCounters = getDecodeFinderCounters();
        decodeFinderCounters[0] = 0;
        decodeFinderCounters[1] = 0;
        decodeFinderCounters[2] = 0;
        decodeFinderCounters[3] = 0;
        int size = bitArray.getSize();
        boolean z3 = false;
        while (i2 < size) {
            z3 = !bitArray.get(i2);
            if (z2 == z3) {
                break;
            }
            i2++;
        }
        int i3 = i2;
        int i4 = 0;
        while (i2 < size) {
            if (bitArray.get(i2) ^ z3) {
                decodeFinderCounters[i4] = decodeFinderCounters[i4] + 1;
            } else {
                if (i4 != 3) {
                    i4++;
                } else {
                    if (AbstractRSSReader.isFinderPattern(decodeFinderCounters)) {
                        return new int[]{i3, i2};
                    }
                    i3 += decodeFinderCounters[0] + decodeFinderCounters[1];
                    decodeFinderCounters[0] = decodeFinderCounters[2];
                    decodeFinderCounters[1] = decodeFinderCounters[3];
                    decodeFinderCounters[2] = 0;
                    decodeFinderCounters[3] = 0;
                    i4--;
                }
                decodeFinderCounters[i4] = 1;
                z3 = !z3;
            }
            i2++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int i2, boolean z2, int[] iArr) throws NotFoundException {
        int size;
        int i3;
        boolean z3 = bitArray.get(iArr[0]);
        int i4 = iArr[0] - 1;
        while (i4 >= 0 && (bitArray.get(i4) ^ z3)) {
            i4--;
        }
        int i5 = i4 + 1;
        int i6 = iArr[0] - i5;
        int[] decodeFinderCounters = getDecodeFinderCounters();
        System.arraycopy(decodeFinderCounters, 0, decodeFinderCounters, 1, decodeFinderCounters.length - 1);
        decodeFinderCounters[0] = i6;
        int finderValue = AbstractRSSReader.parseFinderValue(decodeFinderCounters, FINDER_PATTERNS);
        int i7 = iArr[1];
        if (z2) {
            int size2 = (bitArray.getSize() - 1) - i5;
            size = (bitArray.getSize() - 1) - i7;
            i3 = size2;
        } else {
            size = i7;
            i3 = i5;
        }
        return new FinderPattern(finderValue, new int[]{i5, iArr[1]}, i3, size, i2);
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        addOrTally(this.possibleLeftPairs, decodePair(bitArray, false, i2, map));
        bitArray.reverse();
        addOrTally(this.possibleRightPairs, decodePair(bitArray, true, i2, map));
        bitArray.reverse();
        int size = this.possibleLeftPairs.size();
        for (int i3 = 0; i3 < size; i3++) {
            Pair pair = this.possibleLeftPairs.get(i3);
            if (pair.getCount() > 1) {
                int size2 = this.possibleRightPairs.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    Pair pair2 = this.possibleRightPairs.get(i4);
                    if (pair2.getCount() > 1 && checkChecksum(pair, pair2)) {
                        return constructResult(pair, pair2);
                    }
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public void reset() {
        this.possibleLeftPairs.clear();
        this.possibleRightPairs.clear();
    }
}
