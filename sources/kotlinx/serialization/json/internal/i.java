package kotlinx.serialization.json.internal;

/* loaded from: classes8.dex */
public final /* synthetic */ class i {
    public static /* synthetic */ String a(long j2, int i2) {
        if (j2 == 0) {
            return "0";
        }
        if (j2 > 0) {
            return Long.toString(j2, i2);
        }
        if (i2 < 2 || i2 > 36) {
            i2 = 10;
        }
        int i3 = 64;
        char[] cArr = new char[64];
        int i4 = i2 - 1;
        if ((i2 & i4) == 0) {
            int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(i2);
            do {
                i3--;
                cArr[i3] = Character.forDigit(((int) j2) & i4, i2);
                j2 >>>= iNumberOfTrailingZeros;
            } while (j2 != 0);
        } else {
            long jA = (i2 & 1) == 0 ? (j2 >>> 1) / (i2 >>> 1) : kotlin.f.a(j2, i2);
            long j3 = i2;
            cArr[63] = Character.forDigit((int) (j2 - (jA * j3)), i2);
            i3 = 63;
            while (jA > 0) {
                i3--;
                cArr[i3] = Character.forDigit((int) (jA % j3), i2);
                jA /= j3;
            }
        }
        return new String(cArr, i3, 64 - i3);
    }
}
