package cn.hutool.core.util;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.math.Calculator;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.google.common.primitives.Longs;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/* loaded from: classes.dex */
public class NumberUtil {
    private static final int DEFAULT_DIV_SCALE = 10;
    private static final long[] FACTORIALS = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};

    public static double add(float f2, float f3) {
        return add(Float.toString(f2), Float.toString(f3)).doubleValue();
    }

    public static Collection<Integer> appendRange(int i2, int i3, Collection<Integer> collection) {
        return appendRange(i2, i3, 1, collection);
    }

    public static int binaryToInt(String str) {
        return Integer.parseInt(str, 2);
    }

    public static long binaryToLong(String str) {
        return Long.parseLong(str, 2);
    }

    public static double calculate(String str) {
        return Calculator.conversion(str);
    }

    public static int ceilDiv(int i2, int i3) {
        return (int) Math.ceil(i2 / i3);
    }

    public static int compare(char c3, char c4) {
        return Character.compare(c3, c4);
    }

    public static int count(int i2, int i3) {
        int i4 = i2 % i3;
        int i5 = i2 / i3;
        return i4 == 0 ? i5 : i5 + 1;
    }

    public static String decimalFormat(String str, double d3) throws Throwable {
        Assert.isTrue(isValid(d3), "value is NaN or Infinite!", new Object[0]);
        return new DecimalFormat(str).format(d3);
    }

    public static String decimalFormatMoney(double d3) {
        return decimalFormat(",##0.00", d3);
    }

    public static double div(float f2, float f3) {
        return div(f2, f3, 10);
    }

    public static int divisor(int i2, int i3) {
        while (true) {
            int i4 = i2 % i3;
            if (i4 == 0) {
                return i3;
            }
            int i5 = i3;
            i3 = i4;
            i2 = i5;
        }
    }

    public static boolean equals(double d3, double d4) {
        return Double.doubleToLongBits(d3) == Double.doubleToLongBits(d4);
    }

    public static boolean equals(long j2, long j3) {
        return j2 == j3;
    }

    public static BigInteger factorial(BigInteger bigInteger) {
        BigInteger bigInteger2 = BigInteger.ZERO;
        return bigInteger.equals(bigInteger2) ? BigInteger.ONE : factorial(bigInteger, bigInteger2);
    }

    private static long factorialMultiplyAndCheck(long j2, long j3) {
        if (j2 <= Long.MAX_VALUE / j3) {
            return j2 * j3;
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("Overflow in multiplication: {} * {}", Long.valueOf(j2), Long.valueOf(j3)));
    }

    public static String formatPercent(double d3, int i2) {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(i2);
        return percentInstance.format(d3);
    }

    public static BigInteger fromUnsignedByteArray(byte[] bArr) {
        return new BigInteger(1, bArr);
    }

    public static Integer[] generateBySet(int i2, int i3, int i4) {
        if (i2 <= i3) {
            i3 = i2;
            i2 = i3;
        }
        int i5 = i2 - i3;
        if (i5 < i4) {
            throw new UtilException("Size is larger than range between begin and end!");
        }
        HashSet hashSet = new HashSet(i4, 1.0f);
        while (hashSet.size() < i4) {
            hashSet.add(Integer.valueOf(RandomUtil.randomInt(i5) + i3));
        }
        return (Integer[]) hashSet.toArray(new Integer[0]);
    }

    public static int[] generateRandomNumber(int i2, int i3, int i4) {
        return generateRandomNumber(i2, i3, i4, PrimitiveArrayUtil.range(i2, i3));
    }

    public static String getBinaryStr(Number number) {
        return number instanceof Long ? Long.toBinaryString(((Long) number).longValue()) : number instanceof Integer ? Integer.toBinaryString(((Integer) number).intValue()) : Long.toBinaryString(number.longValue());
    }

    public static boolean isBeside(long j2, long j3) {
        return Math.abs(j2 - j3) == 1;
    }

    public static boolean isDouble(String str) throws NumberFormatException {
        if (CharSequenceUtil.isBlank(str)) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return str.contains(StrPool.DOT);
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static boolean isEven(int i2) {
        return !isOdd(i2);
    }

    public static boolean isGreater(BigDecimal bigDecimal, BigDecimal bigDecimal2) throws IllegalArgumentException {
        Assert.notNull(bigDecimal);
        Assert.notNull(bigDecimal2);
        return bigDecimal.compareTo(bigDecimal2) > 0;
    }

    public static boolean isGreaterOrEqual(BigDecimal bigDecimal, BigDecimal bigDecimal2) throws IllegalArgumentException {
        Assert.notNull(bigDecimal);
        Assert.notNull(bigDecimal2);
        return bigDecimal.compareTo(bigDecimal2) >= 0;
    }

    public static boolean isIn(BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3) throws IllegalArgumentException {
        Assert.notNull(bigDecimal);
        Assert.notNull(bigDecimal2);
        Assert.notNull(bigDecimal3);
        return isGreaterOrEqual(bigDecimal, bigDecimal2) && isLessOrEqual(bigDecimal, bigDecimal3);
    }

    public static boolean isInteger(String str) throws NumberFormatException {
        if (CharSequenceUtil.isBlank(str)) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static boolean isLess(BigDecimal bigDecimal, BigDecimal bigDecimal2) throws IllegalArgumentException {
        Assert.notNull(bigDecimal);
        Assert.notNull(bigDecimal2);
        return bigDecimal.compareTo(bigDecimal2) < 0;
    }

    public static boolean isLessOrEqual(BigDecimal bigDecimal, BigDecimal bigDecimal2) throws IllegalArgumentException {
        Assert.notNull(bigDecimal);
        Assert.notNull(bigDecimal2);
        return bigDecimal.compareTo(bigDecimal2) <= 0;
    }

    public static boolean isLong(String str) throws NumberFormatException {
        if (CharSequenceUtil.isBlank(str)) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x00cc, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isNumber(java.lang.CharSequence r16) {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.util.NumberUtil.isNumber(java.lang.CharSequence):boolean");
    }

    public static boolean isOdd(int i2) {
        return (i2 & 1) == 1;
    }

    public static boolean isPowerOfTwo(long j2) {
        return j2 > 0 && (j2 & (j2 - 1)) == 0;
    }

    public static boolean isPrimes(int i2) throws Throwable {
        Assert.isTrue(i2 > 1, "The number must be > 1", new Object[0]);
        for (int i3 = 2; i3 <= Math.sqrt(i2); i3++) {
            if (i2 % i3 == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValid(double d3) {
        return !(Double.isNaN(d3) || Double.isInfinite(d3));
    }

    public static boolean isValidNumber(Number number) {
        if (number == null) {
            return false;
        }
        if (number instanceof Double) {
            Double d3 = (Double) number;
            return (d3.isInfinite() || d3.isNaN()) ? false : true;
        }
        if (!(number instanceof Float)) {
            return true;
        }
        Float f2 = (Float) number;
        return (f2.isInfinite() || f2.isNaN()) ? false : true;
    }

    private static int mathNode(int i2) {
        if (i2 == 0) {
            return 1;
        }
        return i2 * mathNode(i2 - 1);
    }

    private static int mathSubNode(int i2, int i3) {
        if (i2 == i3) {
            return 1;
        }
        return i2 * mathSubNode(i2 - 1, i3);
    }

    public static <T extends Comparable<? super T>> T max(T[] tArr) {
        return (T) ArrayUtil.max(tArr);
    }

    public static <T extends Comparable<? super T>> T min(T[] tArr) {
        return (T) ArrayUtil.min(tArr);
    }

    public static double mul(float f2, float f3) {
        return mul(Float.toString(f2), Float.toString(f3)).doubleValue();
    }

    public static int multiple(int i2, int i3) {
        return (i2 * i3) / divisor(i2, i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.math.BigInteger newBigInteger(java.lang.String r4) {
        /*
            java.lang.String r4 = cn.hutool.core.text.CharSequenceUtil.trimToNull(r4)
            if (r4 != 0) goto L8
            r4 = 0
            return r4
        L8:
            java.lang.String r0 = "-"
            boolean r0 = r4.startsWith(r0)
            java.lang.String r1 = "0x"
            boolean r1 = r4.startsWith(r1, r0)
            r2 = 16
            if (r1 != 0) goto L46
            java.lang.String r1 = "0X"
            boolean r1 = r4.startsWith(r1, r0)
            if (r1 == 0) goto L21
            goto L46
        L21:
            java.lang.String r1 = "#"
            boolean r1 = r4.startsWith(r1, r0)
            if (r1 == 0) goto L2c
            int r1 = r0 + 1
            goto L48
        L2c:
            java.lang.String r1 = "0"
            boolean r1 = r4.startsWith(r1, r0)
            if (r1 == 0) goto L42
            int r1 = r4.length()
            int r2 = r0 + 1
            if (r1 <= r2) goto L42
            r1 = 8
            r3 = r2
            r2 = r1
            r1 = r3
            goto L48
        L42:
            r2 = 10
            r1 = r0
            goto L48
        L46:
            int r1 = r0 + 2
        L48:
            if (r1 <= 0) goto L4e
            java.lang.String r4 = r4.substring(r1)
        L4e:
            java.math.BigInteger r1 = new java.math.BigInteger
            r1.<init>(r4, r2)
            if (r0 == 0) goto L59
            java.math.BigInteger r1 = r1.negate()
        L59:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.util.NumberUtil.newBigInteger(java.lang.String):java.math.BigInteger");
    }

    @Deprecated
    public static BigDecimal null2Zero(BigDecimal bigDecimal) {
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }

    public static int nullToZero(Integer num) {
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public static double parseDouble(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return 0.0d;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return parseNumber(str).doubleValue();
        }
    }

    public static float parseFloat(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            return parseNumber(str).floatValue();
        }
    }

    public static int parseInt(String str) throws NumberFormatException {
        if (CharSequenceUtil.isBlank(str)) {
            return 0;
        }
        if (CharSequenceUtil.startWithIgnoreCase(str, "0x")) {
            return Integer.parseInt(str.substring(2), 16);
        }
        if (CharSequenceUtil.containsIgnoreCase(str, "E")) {
            throw new NumberFormatException(CharSequenceUtil.format("Unsupported int format: [{}]", str));
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return parseNumber(str).intValue();
        }
    }

    public static long parseLong(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return 0L;
        }
        if (str.startsWith("0x")) {
            return Long.parseLong(str.substring(2), 16);
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return parseNumber(str).longValue();
        }
    }

    public static Number parseNumber(String str) throws NumberFormatException {
        if (CharSequenceUtil.startWithIgnoreCase(str, "0x")) {
            return Long.valueOf(Long.parseLong(str.substring(2), 16));
        }
        if (CharSequenceUtil.startWith((CharSequence) str, '+')) {
            str = CharSequenceUtil.subSuf(str, 1);
        }
        try {
            NumberFormat numberFormat = NumberFormat.getInstance();
            if (numberFormat instanceof DecimalFormat) {
                ((DecimalFormat) numberFormat).setParseBigDecimal(true);
            }
            return numberFormat.parse(str);
        } catch (ParseException e2) {
            NumberFormatException numberFormatException = new NumberFormatException(e2.getMessage());
            numberFormatException.initCause(e2);
            throw numberFormatException;
        }
    }

    public static int partValue(int i2, int i3) {
        return partValue(i2, i3, true);
    }

    public static BigDecimal pow(Number number, int i2) {
        return pow(toBigDecimal(number), i2);
    }

    public static int processMultiple(int i2, int i3) {
        return mathSubNode(i2, i3) / mathNode(i2 - i3);
    }

    public static int[] range(int i2) {
        return range(0, i2);
    }

    public static BigDecimal round(double d3, int i2) {
        return round(d3, i2, RoundingMode.HALF_UP);
    }

    public static BigDecimal roundDown(Number number, int i2) {
        return roundDown(toBigDecimal(number), i2);
    }

    public static BigDecimal roundHalfEven(Number number, int i2) {
        return roundHalfEven(toBigDecimal(number), i2);
    }

    public static String roundStr(double d3, int i2) {
        return round(d3, i2).toPlainString();
    }

    public static long sqrt(long j2) {
        long j3 = 0;
        for (long j4 = Longs.MAX_POWER_OF_TWO; j4 > 0; j4 >>= 2) {
            long j5 = j3 + j4;
            if (j2 >= j5) {
                j2 -= j5;
                j3 = (j3 >> 1) + j4;
            } else {
                j3 >>= 1;
            }
        }
        return j3;
    }

    public static double sub(float f2, float f3) {
        return sub(Float.toString(f2), Float.toString(f3)).doubleValue();
    }

    public static BigDecimal toBigDecimal(Number number) {
        return number == null ? BigDecimal.ZERO : number instanceof BigDecimal ? (BigDecimal) number : number instanceof Long ? new BigDecimal(((Long) number).longValue()) : number instanceof Integer ? new BigDecimal(((Integer) number).intValue()) : number instanceof BigInteger ? new BigDecimal((BigInteger) number) : toBigDecimal(number.toString());
    }

    public static BigInteger toBigInteger(Number number) {
        return number == null ? BigInteger.ZERO : number instanceof BigInteger ? (BigInteger) number : number instanceof Long ? BigInteger.valueOf(((Long) number).longValue()) : toBigInteger(Long.valueOf(number.longValue()));
    }

    public static byte[] toBytes(int i2) {
        return new byte[]{(byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2};
    }

    public static double toDouble(Number number) {
        return number instanceof Float ? Double.parseDouble(number.toString()) : number.doubleValue();
    }

    public static int toInt(byte[] bArr) {
        return (bArr[3] & 255) | ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8);
    }

    public static String toStr(Number number, String str) {
        return number == null ? str : toStr(number);
    }

    public static byte[] toUnsignedByteArray(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray[0] != 0) {
            return byteArray;
        }
        int length = byteArray.length - 1;
        byte[] bArr = new byte[length];
        System.arraycopy(byteArray, 1, bArr, 0, length);
        return bArr;
    }

    public static int zero2One(int i2) {
        if (i2 == 0) {
            return 1;
        }
        return i2;
    }

    public static double add(float f2, double d3) {
        return add(Float.toString(f2), Double.toString(d3)).doubleValue();
    }

    public static Collection<Integer> appendRange(int i2, int i3, int i4, Collection<Integer> collection) {
        int iAbs;
        if (i2 < i3) {
            iAbs = Math.abs(i4);
        } else {
            if (i2 <= i3) {
                collection.add(Integer.valueOf(i2));
                return collection;
            }
            iAbs = -Math.abs(i4);
        }
        while (true) {
            if (iAbs <= 0) {
                if (i2 < i3) {
                    break;
                }
                collection.add(Integer.valueOf(i2));
                i2 += iAbs;
            } else {
                if (i2 > i3) {
                    break;
                }
                collection.add(Integer.valueOf(i2));
                i2 += iAbs;
            }
        }
        return collection;
    }

    public static int compare(double d3, double d4) {
        return Double.compare(d3, d4);
    }

    public static double div(float f2, double d3) {
        return div(f2, d3, 10);
    }

    public static boolean equals(float f2, float f3) {
        return Float.floatToIntBits(f2) == Float.floatToIntBits(f3);
    }

    public static BigInteger fromUnsignedByteArray(byte[] bArr, int i2, int i3) {
        if (i2 != 0 || i3 != bArr.length) {
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i2, bArr2, 0, i3);
            bArr = bArr2;
        }
        return new BigInteger(1, bArr);
    }

    public static boolean isBeside(int i2, int i3) {
        return Math.abs(i2 - i3) == 1;
    }

    public static boolean isValid(float f2) {
        return !(Float.isNaN(f2) || Float.isInfinite(f2));
    }

    public static long max(long... jArr) {
        return PrimitiveArrayUtil.max(jArr);
    }

    public static long min(long... jArr) {
        return PrimitiveArrayUtil.min(jArr);
    }

    public static double mul(float f2, double d3) {
        return mul(Float.toString(f2), Double.toString(d3)).doubleValue();
    }

    public static long nullToZero(Long l2) {
        if (l2 == null) {
            return 0L;
        }
        return l2.longValue();
    }

    public static int partValue(int i2, int i3, boolean z2) {
        int i4 = i2 / i3;
        return (!z2 || i2 % i3 <= 0) ? i4 : i4 + 1;
    }

    public static BigDecimal pow(BigDecimal bigDecimal, int i2) {
        return bigDecimal.pow(i2);
    }

    public static int[] range(int i2, int i3) {
        return range(i2, i3, 1);
    }

    public static BigDecimal round(String str, int i2) {
        return round(str, i2, RoundingMode.HALF_UP);
    }

    public static BigDecimal roundDown(BigDecimal bigDecimal, int i2) {
        return round(bigDecimal, i2, RoundingMode.DOWN);
    }

    public static BigDecimal roundHalfEven(BigDecimal bigDecimal, int i2) {
        return round(bigDecimal, i2, RoundingMode.HALF_EVEN);
    }

    public static String roundStr(String str, int i2) {
        return round(str, i2).toPlainString();
    }

    public static double sub(float f2, double d3) {
        return sub(Float.toString(f2), Double.toString(d3)).doubleValue();
    }

    public static String toStr(Number number) {
        return toStr(number, true);
    }

    public static double add(double d3, float f2) {
        return add(Double.toString(d3), Float.toString(f2)).doubleValue();
    }

    public static int compare(int i2, int i3) {
        return Integer.compare(i2, i3);
    }

    public static String decimalFormat(String str, long j2) {
        return new DecimalFormat(str).format(j2);
    }

    public static double div(double d3, float f2) {
        return div(d3, f2, 10);
    }

    public static boolean equals(Number number, Number number2) {
        if ((number instanceof BigDecimal) && (number2 instanceof BigDecimal)) {
            return equals((BigDecimal) number, (BigDecimal) number2);
        }
        return Objects.equals(number, number2);
    }

    public static int[] generateRandomNumber(int i2, int i3, int i4, int[] iArr) throws Throwable {
        if (i2 <= i3) {
            i3 = i2;
            i2 = i3;
        }
        Assert.isTrue(i2 - i3 >= i4, "Size is larger than range between begin and end!", new Object[0]);
        Assert.isTrue(iArr.length >= i4, "Size is larger than seed size!", new Object[0]);
        int[] iArr2 = new int[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            int iRandomInt = RandomUtil.randomInt(iArr.length - i5);
            iArr2[i5] = iArr[iRandomInt];
            iArr[iRandomInt] = iArr[(iArr.length - 1) - i5];
        }
        return iArr2;
    }

    public static int max(int... iArr) {
        return PrimitiveArrayUtil.max(iArr);
    }

    public static int min(int... iArr) {
        return PrimitiveArrayUtil.min(iArr);
    }

    public static double mul(double d3, float f2) {
        return mul(Double.toString(d3), Float.toString(f2)).doubleValue();
    }

    public static double nullToZero(Double d3) {
        if (d3 == null) {
            return 0.0d;
        }
        return d3.doubleValue();
    }

    public static int[] range(int i2, int i3, int i4) {
        int iAbs;
        int i5 = 0;
        if (i2 < i3) {
            iAbs = Math.abs(i4);
        } else {
            if (i2 <= i3) {
                return new int[]{i2};
            }
            iAbs = -Math.abs(i4);
        }
        int[] iArr = new int[Math.abs((i3 - i2) / iAbs) + 1];
        while (true) {
            if (iAbs <= 0) {
                if (i2 < i3) {
                    break;
                }
                iArr[i5] = i2;
                i5++;
                i2 += iAbs;
            } else {
                if (i2 > i3) {
                    break;
                }
                iArr[i5] = i2;
                i5++;
                i2 += iAbs;
            }
        }
        return iArr;
    }

    public static BigDecimal round(BigDecimal bigDecimal, int i2) {
        return round(bigDecimal, i2, RoundingMode.HALF_UP);
    }

    public static String roundStr(double d3, int i2, RoundingMode roundingMode) {
        return round(d3, i2, roundingMode).toPlainString();
    }

    public static double sub(double d3, float f2) {
        return sub(Double.toString(d3), Float.toString(f2)).doubleValue();
    }

    public static String toStr(Number number, boolean z2) throws Throwable {
        Assert.notNull(number, "Number is null !", new Object[0]);
        if (number instanceof BigDecimal) {
            return toStr((BigDecimal) number, z2);
        }
        Assert.isTrue(isValidNumber(number), "Number is non-finite!", new Object[0]);
        String string = number.toString();
        if (!z2 || string.indexOf(46) <= 0 || string.indexOf(101) >= 0 || string.indexOf(69) >= 0) {
            return string;
        }
        while (string.endsWith("0")) {
            string = string.substring(0, string.length() - 1);
        }
        return string.endsWith(StrPool.DOT) ? string.substring(0, string.length() - 1) : string;
    }

    public static double add(double d3, double d4) {
        return add(Double.toString(d3), Double.toString(d4)).doubleValue();
    }

    public static int compare(long j2, long j3) {
        return Long.compare(j2, j3);
    }

    public static String decimalFormat(String str, Object obj) {
        return decimalFormat(str, obj, null);
    }

    public static double div(double d3, double d4) {
        return div(d3, d4, 10);
    }

    public static BigInteger factorial(BigInteger bigInteger, BigInteger bigInteger2) throws IllegalArgumentException {
        Assert.notNull(bigInteger, "Factorial start must be not null!", new Object[0]);
        Assert.notNull(bigInteger2, "Factorial end must be not null!", new Object[0]);
        BigInteger bigInteger3 = BigInteger.ZERO;
        if (bigInteger.compareTo(bigInteger3) >= 0 && bigInteger2.compareTo(bigInteger3) >= 0) {
            if (bigInteger.equals(bigInteger3)) {
                bigInteger = BigInteger.ONE;
            }
            BigInteger bigInteger4 = BigInteger.ONE;
            if (bigInteger2.compareTo(bigInteger4) < 0) {
                bigInteger2 = bigInteger4;
            }
            BigInteger bigIntegerAdd = bigInteger2.add(bigInteger4);
            BigInteger bigIntegerMultiply = bigInteger;
            while (bigInteger.compareTo(bigIntegerAdd) > 0) {
                bigInteger = bigInteger.subtract(BigInteger.ONE);
                bigIntegerMultiply = bigIntegerMultiply.multiply(bigInteger);
            }
            return bigIntegerMultiply;
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("Factorial start and end both must be > 0, but got start={}, end={}", bigInteger, bigInteger2));
    }

    public static short max(short... sArr) {
        return PrimitiveArrayUtil.max(sArr);
    }

    public static short min(short... sArr) {
        return PrimitiveArrayUtil.min(sArr);
    }

    public static double mul(double d3, double d4) {
        return mul(Double.toString(d3), Double.toString(d4)).doubleValue();
    }

    public static float nullToZero(Float f2) {
        if (f2 == null) {
            return 0.0f;
        }
        return f2.floatValue();
    }

    public static Double parseDouble(String str, Double d3) {
        if (CharSequenceUtil.isBlank(str)) {
            return d3;
        }
        try {
            return Double.valueOf(parseDouble(str));
        } catch (NumberFormatException unused) {
            return d3;
        }
    }

    public static Float parseFloat(String str, Float f2) {
        if (CharSequenceUtil.isBlank(str)) {
            return f2;
        }
        try {
            return Float.valueOf(parseFloat(str));
        } catch (NumberFormatException unused) {
            return f2;
        }
    }

    public static BigDecimal round(double d3, int i2, RoundingMode roundingMode) {
        return round(Double.toString(d3), i2, roundingMode);
    }

    public static String roundStr(String str, int i2, RoundingMode roundingMode) {
        return round(str, i2, roundingMode).toPlainString();
    }

    public static double sub(double d3, double d4) {
        return sub(Double.toString(d3), Double.toString(d4)).doubleValue();
    }

    public static double add(Double d3, Double d4) {
        return add((Number) d3, (Number) d4).doubleValue();
    }

    public static int compare(short s2, short s3) {
        return Short.compare(s2, s3);
    }

    public static String decimalFormat(String str, Object obj, RoundingMode roundingMode) throws Throwable {
        if (obj instanceof Number) {
            Assert.isTrue(isValidNumber((Number) obj), "value is NaN or Infinite!", new Object[0]);
        }
        DecimalFormat decimalFormat = new DecimalFormat(str);
        if (roundingMode != null) {
            decimalFormat.setRoundingMode(roundingMode);
        }
        return decimalFormat.format(obj);
    }

    public static double div(Double d3, Double d4) {
        return div(d3, d4, 10);
    }

    public static double max(double... dArr) {
        return PrimitiveArrayUtil.max(dArr);
    }

    public static double min(double... dArr) {
        return PrimitiveArrayUtil.min(dArr);
    }

    public static double mul(Double d3, Double d4) {
        return mul((Number) d3, (Number) d4).doubleValue();
    }

    public static short nullToZero(Short sh) {
        if (sh == null) {
            return (short) 0;
        }
        return sh.shortValue();
    }

    public static BigDecimal round(String str, int i2, RoundingMode roundingMode) throws IllegalArgumentException {
        Assert.notBlank(str);
        if (i2 < 0) {
            i2 = 0;
        }
        return round(toBigDecimal(str), i2, roundingMode);
    }

    public static double sub(Double d3, Double d4) {
        return sub((Number) d3, (Number) d4).doubleValue();
    }

    public static byte[] toUnsignedByteArray(int i2, BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == i2) {
            return byteArray;
        }
        int i3 = byteArray[0] == 0 ? 1 : 0;
        int length = byteArray.length - i3;
        if (length <= i2) {
            byte[] bArr = new byte[i2];
            System.arraycopy(byteArray, i3, bArr, i2 - length, length);
            return bArr;
        }
        throw new IllegalArgumentException("standard length exceeded for value");
    }

    public static BigDecimal add(Number number, Number number2) {
        return add(number, number2);
    }

    public static int compare(byte b3, byte b4) {
        return Byte.compare(b3, b4);
    }

    public static BigDecimal div(Number number, Number number2) {
        return div(number, number2, 10);
    }

    public static boolean equals(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        if (bigDecimal == bigDecimal2) {
            return true;
        }
        return (bigDecimal == null || bigDecimal2 == null || bigDecimal.compareTo(bigDecimal2) != 0) ? false : true;
    }

    public static float max(float... fArr) {
        return PrimitiveArrayUtil.max(fArr);
    }

    public static float min(float... fArr) {
        return PrimitiveArrayUtil.min(fArr);
    }

    public static BigDecimal mul(Number number, Number number2) {
        return mul(number, number2);
    }

    public static byte nullToZero(Byte b3) {
        if (b3 == null) {
            return (byte) 0;
        }
        return b3.byteValue();
    }

    public static Long parseLong(String str, Long l2) {
        if (CharSequenceUtil.isBlank(str)) {
            return l2;
        }
        try {
            return Long.valueOf(parseLong(str));
        } catch (NumberFormatException unused) {
            return l2;
        }
    }

    public static BigDecimal sub(Number number, Number number2) {
        return sub(number, number2);
    }

    public static BigDecimal add(Number... numberArr) {
        if (ArrayUtil.isEmpty((Object[]) numberArr)) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = toBigDecimal(numberArr[0]);
        for (int i2 = 1; i2 < numberArr.length; i2++) {
            Number number = numberArr[i2];
            if (number != null) {
                bigDecimal = bigDecimal.add(toBigDecimal(number));
            }
        }
        return bigDecimal;
    }

    public static BigDecimal div(String str, String str2) {
        return div(str, str2, 10);
    }

    public static boolean equals(char c3, char c4, boolean z2) {
        return CharUtil.equals(c3, c4, z2);
    }

    public static BigDecimal max(BigDecimal... bigDecimalArr) {
        return (BigDecimal) ArrayUtil.max(bigDecimalArr);
    }

    public static BigDecimal min(BigDecimal... bigDecimalArr) {
        return (BigDecimal) ArrayUtil.min(bigDecimalArr);
    }

    public static BigDecimal mul(Number... numberArr) {
        if (!ArrayUtil.isEmpty((Object[]) numberArr) && !ArrayUtil.hasNull(numberArr)) {
            BigDecimal bigDecimal = toBigDecimal(numberArr[0].toString());
            for (int i2 = 1; i2 < numberArr.length; i2++) {
                bigDecimal = bigDecimal.multiply(toBigDecimal(numberArr[i2].toString()));
            }
            return bigDecimal;
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal nullToZero(BigDecimal bigDecimal) {
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }

    public static BigDecimal round(BigDecimal bigDecimal, int i2, RoundingMode roundingMode) {
        if (bigDecimal == null) {
            bigDecimal = BigDecimal.ZERO;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_UP;
        }
        return bigDecimal.setScale(i2, roundingMode);
    }

    public static BigDecimal sub(Number... numberArr) {
        if (ArrayUtil.isEmpty((Object[]) numberArr)) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = toBigDecimal(numberArr[0]);
        for (int i2 = 1; i2 < numberArr.length; i2++) {
            Number number = numberArr[i2];
            if (number != null) {
                bigDecimal = bigDecimal.subtract(toBigDecimal(number));
            }
        }
        return bigDecimal;
    }

    public static BigInteger toBigInteger(String str) {
        return CharSequenceUtil.isBlank(str) ? BigInteger.ZERO : new BigInteger(str);
    }

    public static double div(float f2, float f3, int i2) {
        return div(f2, f3, i2, RoundingMode.HALF_UP);
    }

    public static BigInteger nullToZero(BigInteger bigInteger) {
        return bigInteger == null ? BigInteger.ZERO : bigInteger;
    }

    public static Integer parseInt(String str, Integer num) {
        if (CharSequenceUtil.isBlank(str)) {
            return num;
        }
        try {
            return Integer.valueOf(parseInt(str));
        } catch (NumberFormatException unused) {
            return num;
        }
    }

    public static double div(float f2, double d3, int i2) {
        return div(f2, d3, i2, RoundingMode.HALF_UP);
    }

    public static double div(double d3, float f2, int i2) {
        return div(d3, f2, i2, RoundingMode.HALF_UP);
    }

    public static double div(double d3, double d4, int i2) {
        return div(d3, d4, i2, RoundingMode.HALF_UP);
    }

    public static BigDecimal toBigDecimal(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(str);
        } catch (Exception unused) {
            return toBigDecimal(parseNumber(str));
        }
    }

    public static double div(Double d3, Double d4, int i2) {
        return div(d3, d4, i2, RoundingMode.HALF_UP);
    }

    public static Number parseNumber(String str, Number number) {
        if (CharSequenceUtil.isBlank(str)) {
            return number;
        }
        try {
            return parseNumber(str);
        } catch (NumberFormatException unused) {
            return number;
        }
    }

    public static BigDecimal div(Number number, Number number2, int i2) {
        return div(number, number2, i2, RoundingMode.HALF_UP);
    }

    public static String toStr(BigDecimal bigDecimal) {
        return toStr(bigDecimal, true);
    }

    public static BigDecimal add(String... strArr) {
        if (ArrayUtil.isEmpty((Object[]) strArr)) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = toBigDecimal(strArr[0]);
        for (int i2 = 1; i2 < strArr.length; i2++) {
            String str = strArr[i2];
            if (CharSequenceUtil.isNotBlank(str)) {
                bigDecimal = bigDecimal.add(toBigDecimal(str));
            }
        }
        return bigDecimal;
    }

    public static BigDecimal div(String str, String str2, int i2) {
        return div(str, str2, i2, RoundingMode.HALF_UP);
    }

    public static BigDecimal mul(String str, String str2) {
        return mul(toBigDecimal(str), toBigDecimal(str2));
    }

    public static BigDecimal sub(String... strArr) {
        if (ArrayUtil.isEmpty((Object[]) strArr)) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = toBigDecimal(strArr[0]);
        for (int i2 = 1; i2 < strArr.length; i2++) {
            String str = strArr[i2];
            if (CharSequenceUtil.isNotBlank(str)) {
                bigDecimal = bigDecimal.subtract(toBigDecimal(str));
            }
        }
        return bigDecimal;
    }

    public static String toStr(BigDecimal bigDecimal, boolean z2) throws IllegalArgumentException {
        Assert.notNull(bigDecimal, "BigDecimal is null !", new Object[0]);
        if (z2) {
            bigDecimal = bigDecimal.stripTrailingZeros();
        }
        return bigDecimal.toPlainString();
    }

    public static double div(float f2, float f3, int i2, RoundingMode roundingMode) {
        return div(Float.toString(f2), Float.toString(f3), i2, roundingMode).doubleValue();
    }

    public static long factorial(long j2, long j3) {
        if (j2 < 0 || j3 < 0) {
            throw new IllegalArgumentException(CharSequenceUtil.format("Factorial start and end both must be >= 0, but got start={}, end={}", Long.valueOf(j2), Long.valueOf(j3)));
        }
        if (0 == j2 || j2 == j3) {
            return 1L;
        }
        if (j2 < j3) {
            return 0L;
        }
        return factorialMultiplyAndCheck(j2, factorial(j2 - 1, j3));
    }

    public static BigDecimal mul(String... strArr) {
        if (!ArrayUtil.isEmpty((Object[]) strArr) && !ArrayUtil.hasNull(strArr)) {
            BigDecimal bigDecimal = toBigDecimal(strArr[0]);
            for (int i2 = 1; i2 < strArr.length; i2++) {
                bigDecimal = bigDecimal.multiply(toBigDecimal(strArr[i2]));
            }
            return bigDecimal;
        }
        return BigDecimal.ZERO;
    }

    public static double div(float f2, double d3, int i2, RoundingMode roundingMode) {
        return div(Float.toString(f2), Double.toString(d3), i2, roundingMode).doubleValue();
    }

    public static double div(double d3, float f2, int i2, RoundingMode roundingMode) {
        return div(Double.toString(d3), Float.toString(f2), i2, roundingMode).doubleValue();
    }

    public static long factorial(long j2) {
        if (j2 >= 0 && j2 <= 20) {
            return FACTORIALS[(int) j2];
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("Factorial must have n >= 0 and n <= 20 for n!, but got n = {}", Long.valueOf(j2)));
    }

    public static double div(double d3, double d4, int i2, RoundingMode roundingMode) {
        return div(Double.toString(d3), Double.toString(d4), i2, roundingMode).doubleValue();
    }

    public static double div(Double d3, Double d4, int i2, RoundingMode roundingMode) {
        return div((Number) d3, (Number) d4, i2, roundingMode).doubleValue();
    }

    public static BigDecimal div(Number number, Number number2, int i2, RoundingMode roundingMode) {
        if ((number instanceof BigDecimal) && (number2 instanceof BigDecimal)) {
            return div((BigDecimal) number, (BigDecimal) number2, i2, roundingMode);
        }
        return div(StrUtil.toStringOrNull(number), StrUtil.toStringOrNull(number2), i2, roundingMode);
    }

    public static BigDecimal mul(BigDecimal... bigDecimalArr) {
        if (!ArrayUtil.isEmpty((Object[]) bigDecimalArr) && !ArrayUtil.hasNull(bigDecimalArr)) {
            BigDecimal bigDecimalMultiply = bigDecimalArr[0];
            for (int i2 = 1; i2 < bigDecimalArr.length; i2++) {
                bigDecimalMultiply = bigDecimalMultiply.multiply(bigDecimalArr[i2]);
            }
            return bigDecimalMultiply;
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal add(BigDecimal... bigDecimalArr) {
        if (ArrayUtil.isEmpty((Object[]) bigDecimalArr)) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = toBigDecimal(bigDecimalArr[0]);
        for (int i2 = 1; i2 < bigDecimalArr.length; i2++) {
            BigDecimal bigDecimal2 = bigDecimalArr[i2];
            if (bigDecimal2 != null) {
                bigDecimal = bigDecimal.add(bigDecimal2);
            }
        }
        return bigDecimal;
    }

    public static BigDecimal sub(BigDecimal... bigDecimalArr) {
        if (ArrayUtil.isEmpty((Object[]) bigDecimalArr)) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = toBigDecimal(bigDecimalArr[0]);
        for (int i2 = 1; i2 < bigDecimalArr.length; i2++) {
            BigDecimal bigDecimal2 = bigDecimalArr[i2];
            if (bigDecimal2 != null) {
                bigDecimal = bigDecimal.subtract(bigDecimal2);
            }
        }
        return bigDecimal;
    }

    public static BigDecimal div(String str, String str2, int i2, RoundingMode roundingMode) {
        return div(toBigDecimal(str), toBigDecimal(str2), i2, roundingMode);
    }

    public static BigDecimal div(BigDecimal bigDecimal, BigDecimal bigDecimal2, int i2, RoundingMode roundingMode) throws IllegalArgumentException {
        Assert.notNull(bigDecimal2, "Divisor must be not null !", new Object[0]);
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        if (i2 < 0) {
            i2 = -i2;
        }
        return bigDecimal.divide(bigDecimal2, i2, roundingMode);
    }
}
