package com.google.android.gms.common.server.response;

import android.util.Log;
import androidx.databinding.ObservableArrayList;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import okio.internal._BufferKt;

@ShowFirstParty
@KeepForSdk
/* loaded from: classes3.dex */
public class FastParser<T extends FastJsonResponse> {
    private static final char[] zaqu = {'u', 'l', 'l'};
    private static final char[] zaqv = {'r', 'u', 'e'};
    private static final char[] zaqw = {'r', 'u', 'e', '\"'};
    private static final char[] zaqx = {'a', 'l', 's', 'e'};
    private static final char[] zaqy = {'a', 'l', 's', 'e', '\"'};
    private static final char[] zaqz = {'\n'};
    private static final zaa<Integer> zarb = new zab();
    private static final zaa<Long> zarc = new com.google.android.gms.common.server.response.zaa();
    private static final zaa<Float> zard = new zad();
    private static final zaa<Double> zare = new zac();
    private static final zaa<Boolean> zarf = new zaf();
    private static final zaa<String> zarg = new zae();
    private static final zaa<BigInteger> zarh = new zah();
    private static final zaa<BigDecimal> zari = new zag();
    private final char[] zaqp = new char[1];
    private final char[] zaqq = new char[32];
    private final char[] zaqr = new char[1024];
    private final StringBuilder zaqs = new StringBuilder(32);
    private final StringBuilder zaqt = new StringBuilder(1024);
    private final Stack<Integer> zara = new Stack<>();

    @ShowFirstParty
    @KeepForSdk
    public static class ParseException extends Exception {
        public ParseException(String str) {
            super(str);
        }

        public ParseException(String str, Throwable th) {
            super(str, th);
        }

        public ParseException(Throwable th) {
            super(th);
        }
    }

    public interface zaa<O> {
        O zah(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0297 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0279 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean zaa(java.io.BufferedReader r17, com.google.android.gms.common.server.response.FastJsonResponse r18) throws com.google.android.gms.common.server.response.FastParser.ParseException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 702
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FastParser.zaa(java.io.BufferedReader, com.google.android.gms.common.server.response.FastJsonResponse):boolean");
    }

    private final String zab(BufferedReader bufferedReader) throws ParseException, IOException {
        bufferedReader.mark(1024);
        char cZaj = zaj(bufferedReader);
        if (cZaj == '\"') {
            if (bufferedReader.read(this.zaqp) == -1) {
                throw new ParseException("Unexpected EOF while parsing string");
            }
            char c3 = this.zaqp[0];
            boolean z2 = false;
            do {
                if (c3 != '\"' || z2) {
                    z2 = c3 == '\\' ? !z2 : false;
                    if (bufferedReader.read(this.zaqp) == -1) {
                        throw new ParseException("Unexpected EOF while parsing string");
                    }
                    c3 = this.zaqp[0];
                }
            } while (!Character.isISOControl(c3));
            throw new ParseException("Unexpected control character while reading string");
        }
        if (cZaj == ',') {
            throw new ParseException("Missing value");
        }
        int i2 = 1;
        if (cZaj == '[') {
            this.zara.push(5);
            bufferedReader.mark(32);
            if (zaj(bufferedReader) == ']') {
                zak(5);
            } else {
                bufferedReader.reset();
                boolean z3 = false;
                boolean z4 = false;
                while (i2 > 0) {
                    char cZaj2 = zaj(bufferedReader);
                    if (cZaj2 == 0) {
                        throw new ParseException("Unexpected EOF while parsing array");
                    }
                    if (Character.isISOControl(cZaj2)) {
                        throw new ParseException("Unexpected control character while reading array");
                    }
                    if (cZaj2 == '\"' && !z3) {
                        z4 = !z4;
                    }
                    if (cZaj2 == '[' && !z4) {
                        i2++;
                    }
                    if (cZaj2 == ']' && !z4) {
                        i2--;
                    }
                    z3 = (cZaj2 == '\\' && z4) ? !z3 : false;
                }
                zak(5);
            }
        } else if (cZaj != '{') {
            bufferedReader.reset();
            zaa(bufferedReader, this.zaqr);
        } else {
            this.zara.push(1);
            bufferedReader.mark(32);
            char cZaj3 = zaj(bufferedReader);
            if (cZaj3 == '}') {
                zak(1);
            } else {
                if (cZaj3 != '\"') {
                    StringBuilder sb = new StringBuilder(18);
                    sb.append("Unexpected token ");
                    sb.append(cZaj3);
                    throw new ParseException(sb.toString());
                }
                bufferedReader.reset();
                zaa(bufferedReader);
                while (zab(bufferedReader) != null) {
                }
                zak(1);
            }
        }
        char cZaj4 = zaj(bufferedReader);
        if (cZaj4 == ',') {
            zak(2);
            return zaa(bufferedReader);
        }
        if (cZaj4 == '}') {
            zak(2);
            return null;
        }
        StringBuilder sb2 = new StringBuilder(18);
        sb2.append("Unexpected token ");
        sb2.append(cZaj4);
        throw new ParseException(sb2.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zac(BufferedReader bufferedReader) throws ParseException, IOException {
        return zaa(bufferedReader, this.zaqq, this.zaqs, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int zad(BufferedReader bufferedReader) throws ParseException, IOException {
        int i2;
        int i3;
        int iZaa = zaa(bufferedReader, this.zaqr);
        int i4 = 0;
        if (iZaa == 0) {
            return 0;
        }
        char[] cArr = this.zaqr;
        if (iZaa <= 0) {
            throw new ParseException("No number to parse");
        }
        if (cArr[0] == '-') {
            i2 = Integer.MIN_VALUE;
            i3 = 1;
        } else {
            i2 = -2147483647;
            i3 = 0;
        }
        int i5 = i3;
        if (i3 < iZaa) {
            int i6 = i3 + 1;
            int iDigit = Character.digit(cArr[i3], 10);
            if (iDigit < 0) {
                throw new ParseException("Unexpected non-digit character");
            }
            int i7 = -iDigit;
            i3 = i6;
            i4 = i7;
        }
        while (i3 < iZaa) {
            int i8 = i3 + 1;
            int iDigit2 = Character.digit(cArr[i3], 10);
            if (iDigit2 < 0) {
                throw new ParseException("Unexpected non-digit character");
            }
            if (i4 < -214748364) {
                throw new ParseException("Number too large");
            }
            int i9 = i4 * 10;
            if (i9 < i2 + iDigit2) {
                throw new ParseException("Number too large");
            }
            i4 = i9 - iDigit2;
            i3 = i8;
        }
        if (i5 == 0) {
            return -i4;
        }
        if (i3 > 1) {
            return i4;
        }
        throw new ParseException("No digits to parse");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long zae(BufferedReader bufferedReader) throws ParseException, IOException {
        long j2;
        int iZaa = zaa(bufferedReader, this.zaqr);
        long j3 = 0;
        if (iZaa == 0) {
            return 0L;
        }
        char[] cArr = this.zaqr;
        if (iZaa <= 0) {
            throw new ParseException("No number to parse");
        }
        int i2 = 0;
        if (cArr[0] == '-') {
            j2 = Long.MIN_VALUE;
            i2 = 1;
        } else {
            j2 = -9223372036854775807L;
        }
        int i3 = i2;
        int i4 = 10;
        if (i2 < iZaa) {
            int i5 = i2 + 1;
            int iDigit = Character.digit(cArr[i2], 10);
            if (iDigit < 0) {
                throw new ParseException("Unexpected non-digit character");
            }
            i2 = i5;
            j3 = -iDigit;
        }
        while (i2 < iZaa) {
            int i6 = i2 + 1;
            int iDigit2 = Character.digit(cArr[i2], i4);
            if (iDigit2 < 0) {
                throw new ParseException("Unexpected non-digit character");
            }
            if (j3 < _BufferKt.OVERFLOW_ZONE) {
                throw new ParseException("Number too large");
            }
            long j4 = j3 * 10;
            long j5 = iDigit2;
            if (j4 < j2 + j5) {
                throw new ParseException("Number too large");
            }
            j3 = j4 - j5;
            i2 = i6;
            i4 = 10;
        }
        if (i3 == 0) {
            return -j3;
        }
        if (i2 > 1) {
            return j3;
        }
        throw new ParseException("No digits to parse");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BigInteger zaf(BufferedReader bufferedReader) throws ParseException, IOException {
        int iZaa = zaa(bufferedReader, this.zaqr);
        if (iZaa == 0) {
            return null;
        }
        return new BigInteger(new String(this.zaqr, 0, iZaa));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float zag(BufferedReader bufferedReader) throws ParseException, IOException {
        int iZaa = zaa(bufferedReader, this.zaqr);
        if (iZaa == 0) {
            return 0.0f;
        }
        return Float.parseFloat(new String(this.zaqr, 0, iZaa));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final double zah(BufferedReader bufferedReader) throws ParseException, IOException {
        int iZaa = zaa(bufferedReader, this.zaqr);
        if (iZaa == 0) {
            return 0.0d;
        }
        return Double.parseDouble(new String(this.zaqr, 0, iZaa));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BigDecimal zai(BufferedReader bufferedReader) throws ParseException, IOException {
        int iZaa = zaa(bufferedReader, this.zaqr);
        if (iZaa == 0) {
            return null;
        }
        return new BigDecimal(new String(this.zaqr, 0, iZaa));
    }

    private final char zaj(BufferedReader bufferedReader) throws ParseException, IOException {
        if (bufferedReader.read(this.zaqp) == -1) {
            return (char) 0;
        }
        while (Character.isWhitespace(this.zaqp[0])) {
            if (bufferedReader.read(this.zaqp) == -1) {
                return (char) 0;
            }
        }
        return this.zaqp[0];
    }

    private final void zak(int i2) throws ParseException {
        if (this.zara.isEmpty()) {
            StringBuilder sb = new StringBuilder(46);
            sb.append("Expected state ");
            sb.append(i2);
            sb.append(" but had empty stack");
            throw new ParseException(sb.toString());
        }
        int iIntValue = this.zara.pop().intValue();
        if (iIntValue == i2) {
            return;
        }
        StringBuilder sb2 = new StringBuilder(46);
        sb2.append("Expected state ");
        sb2.append(i2);
        sb2.append(" but had ");
        sb2.append(iIntValue);
        throw new ParseException(sb2.toString());
    }

    @KeepForSdk
    public void parse(InputStream inputStream, T t2) throws ParseException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        try {
            try {
                this.zara.push(0);
                char cZaj = zaj(bufferedReader);
                if (cZaj == 0) {
                    throw new ParseException("No data to parse");
                }
                if (cZaj == '[') {
                    this.zara.push(5);
                    Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = t2.getFieldMappings();
                    if (fieldMappings.size() != 1) {
                        throw new ParseException("Object array response class must have a single Field");
                    }
                    FastJsonResponse.Field<?, ?> value = fieldMappings.entrySet().iterator().next().getValue();
                    t2.addConcreteTypeArrayInternal(value, value.zaqj, zaa(bufferedReader, value));
                } else {
                    if (cZaj != '{') {
                        StringBuilder sb = new StringBuilder(19);
                        sb.append("Unexpected token: ");
                        sb.append(cZaj);
                        throw new ParseException(sb.toString());
                    }
                    this.zara.push(1);
                    zaa(bufferedReader, t2);
                }
                zak(0);
            } catch (IOException e2) {
                throw new ParseException(e2);
            }
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException unused) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0030 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String zab(java.io.BufferedReader r9, char[] r10, java.lang.StringBuilder r11, char[] r12) throws com.google.android.gms.common.server.response.FastParser.ParseException, java.io.IOException {
        /*
            r0 = 0
            r11.setLength(r0)
            int r1 = r10.length
            r9.mark(r1)
            r1 = r0
            r2 = r1
        La:
            int r3 = r9.read(r10)
            r4 = -1
            if (r3 == r4) goto L6d
            r4 = r0
        L12:
            if (r4 >= r3) goto L65
            char r5 = r10[r4]
            boolean r6 = java.lang.Character.isISOControl(r5)
            r7 = 1
            if (r6 == 0) goto L38
            if (r12 == 0) goto L2c
            r6 = r0
        L20:
            int r8 = r12.length
            if (r6 >= r8) goto L2c
            char r8 = r12[r6]
            if (r8 != r5) goto L29
            r6 = r7
            goto L2d
        L29:
            int r6 = r6 + 1
            goto L20
        L2c:
            r6 = r0
        L2d:
            if (r6 == 0) goto L30
            goto L38
        L30:
            com.google.android.gms.common.server.response.FastParser$ParseException r9 = new com.google.android.gms.common.server.response.FastParser$ParseException
            java.lang.String r10 = "Unexpected control character while reading string"
            r9.<init>(r10)
            throw r9
        L38:
            r6 = 34
            if (r5 != r6) goto L59
            if (r1 != 0) goto L59
            r11.append(r10, r0, r4)
            r9.reset()
            int r4 = r4 + r7
            long r0 = (long) r4
            r9.skip(r0)
            if (r2 == 0) goto L54
            java.lang.String r9 = r11.toString()
            java.lang.String r9 = com.google.android.gms.common.util.JsonUtils.unescapeString(r9)
            return r9
        L54:
            java.lang.String r9 = r11.toString()
            return r9
        L59:
            r6 = 92
            if (r5 != r6) goto L61
            r1 = r1 ^ 1
            r2 = r7
            goto L62
        L61:
            r1 = r0
        L62:
            int r4 = r4 + 1
            goto L12
        L65:
            r11.append(r10, r0, r3)
            int r3 = r10.length
            r9.mark(r3)
            goto La
        L6d:
            com.google.android.gms.common.server.response.FastParser$ParseException r9 = new com.google.android.gms.common.server.response.FastParser$ParseException
            java.lang.String r10 = "Unexpected EOF while parsing string"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FastParser.zab(java.io.BufferedReader, char[], java.lang.StringBuilder, char[]):java.lang.String");
    }

    private final void zab(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i2 = 0;
        while (i2 < cArr.length) {
            int i3 = bufferedReader.read(this.zaqq, 0, cArr.length - i2);
            if (i3 == -1) {
                throw new ParseException("Unexpected EOF");
            }
            for (int i4 = 0; i4 < i3; i4++) {
                if (cArr[i4 + i2] != this.zaqq[i4]) {
                    throw new ParseException("Unexpected character");
                }
            }
            i2 += i3;
        }
    }

    private final String zaa(BufferedReader bufferedReader) throws ParseException, IOException {
        this.zara.push(2);
        char cZaj = zaj(bufferedReader);
        if (cZaj == '\"') {
            this.zara.push(3);
            String strZab = zab(bufferedReader, this.zaqq, this.zaqs, null);
            zak(3);
            if (zaj(bufferedReader) == ':') {
                return strZab;
            }
            throw new ParseException("Expected key/value separator");
        }
        if (cZaj == ']') {
            zak(2);
            zak(1);
            zak(5);
            return null;
        }
        if (cZaj == '}') {
            zak(2);
            return null;
        }
        StringBuilder sb = new StringBuilder(19);
        sb.append("Unexpected token: ");
        sb.append(cZaj);
        throw new ParseException(sb.toString());
    }

    private final <O> ArrayList<O> zaa(BufferedReader bufferedReader, zaa<O> zaaVar) throws ParseException, IOException {
        char cZaj = zaj(bufferedReader);
        if (cZaj == 'n') {
            zab(bufferedReader, zaqu);
            return null;
        }
        if (cZaj == '[') {
            this.zara.push(5);
            ArrayList<O> arrayList = new ArrayList<>();
            while (true) {
                bufferedReader.mark(1024);
                char cZaj2 = zaj(bufferedReader);
                if (cZaj2 == 0) {
                    throw new ParseException("Unexpected EOF");
                }
                if (cZaj2 != ',') {
                    if (cZaj2 != ']') {
                        bufferedReader.reset();
                        arrayList.add(zaaVar.zah(this, bufferedReader));
                    } else {
                        zak(5);
                        return arrayList;
                    }
                }
            }
        } else {
            throw new ParseException("Expected start of array");
        }
    }

    private final String zaa(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, char[] cArr2) throws ParseException, IOException {
        char cZaj = zaj(bufferedReader);
        if (cZaj == '\"') {
            return zab(bufferedReader, cArr, sb, cArr2);
        }
        if (cZaj == 'n') {
            zab(bufferedReader, zaqu);
            return null;
        }
        throw new ParseException("Expected string");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zaa(BufferedReader bufferedReader, boolean z2) throws ParseException, IOException {
        while (true) {
            char cZaj = zaj(bufferedReader);
            if (cZaj != '\"') {
                if (cZaj == 'f') {
                    zab(bufferedReader, z2 ? zaqy : zaqx);
                    return false;
                }
                if (cZaj == 'n') {
                    zab(bufferedReader, zaqu);
                    return false;
                }
                if (cZaj == 't') {
                    zab(bufferedReader, z2 ? zaqw : zaqv);
                    return true;
                }
                StringBuilder sb = new StringBuilder(19);
                sb.append("Unexpected token: ");
                sb.append(cZaj);
                throw new ParseException(sb.toString());
            }
            if (z2) {
                throw new ParseException("No boolean value found in string");
            }
            z2 = true;
        }
    }

    private final <T extends FastJsonResponse> ArrayList<T> zaa(BufferedReader bufferedReader, FastJsonResponse.Field<?, ?> field) throws ParseException, IOException {
        ObservableArrayList observableArrayList = (ArrayList<T>) new ArrayList();
        char cZaj = zaj(bufferedReader);
        if (cZaj == ']') {
            zak(5);
            return observableArrayList;
        }
        if (cZaj == 'n') {
            zab(bufferedReader, zaqu);
            zak(5);
            return null;
        }
        if (cZaj == '{') {
            this.zara.push(1);
            while (true) {
                try {
                    FastJsonResponse fastJsonResponseZacn = field.zacn();
                    if (!zaa(bufferedReader, fastJsonResponseZacn)) {
                        return observableArrayList;
                    }
                    observableArrayList.add(fastJsonResponseZacn);
                    char cZaj2 = zaj(bufferedReader);
                    if (cZaj2 != ',') {
                        if (cZaj2 == ']') {
                            zak(5);
                            return observableArrayList;
                        }
                        StringBuilder sb = new StringBuilder(19);
                        sb.append("Unexpected token: ");
                        sb.append(cZaj2);
                        throw new ParseException(sb.toString());
                    }
                    if (zaj(bufferedReader) == '{') {
                        this.zara.push(1);
                    } else {
                        throw new ParseException("Expected start of next object in array");
                    }
                } catch (IllegalAccessException e2) {
                    throw new ParseException("Error instantiating inner object", e2);
                } catch (InstantiationException e3) {
                    throw new ParseException("Error instantiating inner object", e3);
                }
            }
        } else {
            StringBuilder sb2 = new StringBuilder(19);
            sb2.append("Unexpected token: ");
            sb2.append(cZaj);
            throw new ParseException(sb2.toString());
        }
    }

    private final int zaa(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i2;
        char cZaj = zaj(bufferedReader);
        if (cZaj == 0) {
            throw new ParseException("Unexpected EOF");
        }
        if (cZaj == ',') {
            throw new ParseException("Missing value");
        }
        if (cZaj == 'n') {
            zab(bufferedReader, zaqu);
            return 0;
        }
        bufferedReader.mark(1024);
        if (cZaj == '\"') {
            i2 = 0;
            boolean z2 = false;
            while (i2 < cArr.length && bufferedReader.read(cArr, i2, 1) != -1) {
                char c3 = cArr[i2];
                if (Character.isISOControl(c3)) {
                    throw new ParseException("Unexpected control character while reading string");
                }
                if (c3 == '\"' && !z2) {
                    bufferedReader.reset();
                    bufferedReader.skip(i2 + 1);
                    return i2;
                }
                z2 = c3 == '\\' ? !z2 : false;
                i2++;
            }
        } else {
            cArr[0] = cZaj;
            i2 = 1;
            while (i2 < cArr.length && bufferedReader.read(cArr, i2, 1) != -1) {
                char c4 = cArr[i2];
                if (c4 == '}' || c4 == ',' || Character.isWhitespace(c4) || cArr[i2] == ']') {
                    bufferedReader.reset();
                    bufferedReader.skip(i2 - 1);
                    cArr[i2] = 0;
                    return i2;
                }
                i2++;
            }
        }
        if (i2 == cArr.length) {
            throw new ParseException("Absurdly long value");
        }
        throw new ParseException("Unexpected EOF");
    }
}
