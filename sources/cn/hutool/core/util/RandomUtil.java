package cn.hutool.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.text.CharSequenceUtil;
import com.yikaobang.yixue.R2;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/* loaded from: classes.dex */
public class RandomUtil {
    public static final String BASE_NUMBER = "0123456789";
    public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    public static final String BASE_CHAR_NUMBER_LOWER = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final String BASE_CHAR_NUMBER = BASE_CHAR.toUpperCase() + BASE_CHAR_NUMBER_LOWER;

    public static SecureRandom createSecureRandom(byte[] bArr) {
        return bArr == null ? new SecureRandom() : new SecureRandom(bArr);
    }

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    public static SecureRandom getSHA1PRNGRandom(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            if (bArr != null) {
                secureRandom.setSeed(bArr);
            }
            return secureRandom;
        } catch (NoSuchAlgorithmException e2) {
            throw new UtilException(e2);
        }
    }

    public static SecureRandom getSecureRandom() {
        return getSecureRandom(null);
    }

    public static SecureRandom getSecureRandomStrong() {
        try {
            return SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e2) {
            throw new UtilException(e2);
        }
    }

    public static BigDecimal randomBigDecimal() {
        return NumberUtil.toBigDecimal(Double.valueOf(getRandom().nextDouble()));
    }

    public static boolean randomBoolean() {
        return randomInt(2) == 0;
    }

    public static byte[] randomBytes(int i2) {
        byte[] bArr = new byte[i2];
        getRandom().nextBytes(bArr);
        return bArr;
    }

    public static char randomChar() {
        return randomChar(BASE_CHAR_NUMBER);
    }

    public static char randomChinese() {
        return (char) randomInt(R2.id.tv_rank_exam, 40959);
    }

    public static DateTime randomDate(Date date, DateField dateField, int i2, int i3) {
        if (date == null) {
            date = DateUtil.date();
        }
        return DateUtil.offset(date, dateField, randomInt(i2, i3));
    }

    public static DateTime randomDay(int i2, int i3) {
        return randomDate(DateUtil.date(), DateField.DAY_OF_YEAR, i2, i3);
    }

    public static double randomDouble(double d3, double d4) {
        return getRandom().nextDouble(d3, d4);
    }

    public static <T> T randomEle(List<T> list) {
        return (T) randomEle(list, list.size());
    }

    public static <T> List<T> randomEleList(List<T> list, int i2) {
        if (i2 >= list.size()) {
            return ListUtil.toList((Collection) list);
        }
        int[] iArrSub = PrimitiveArrayUtil.sub(randomInts(list.size()), 0, i2);
        ArrayList arrayList = new ArrayList();
        for (int i3 : iArrSub) {
            arrayList.add(list.get(i3));
        }
        return arrayList;
    }

    public static <T> Set<T> randomEleSet(Collection<T> collection, int i2) {
        ArrayList arrayListDistinct = CollUtil.distinct(collection);
        if (i2 > arrayListDistinct.size()) {
            throw new IllegalArgumentException("Count is larger than collection distinct size !");
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(i2);
        int size = arrayListDistinct.size();
        while (linkedHashSet.size() < i2) {
            linkedHashSet.add(randomEle(arrayListDistinct, size));
        }
        return linkedHashSet;
    }

    public static <T> List<T> randomEles(List<T> list, int i2) {
        ArrayList arrayList = new ArrayList(i2);
        int size = list.size();
        while (arrayList.size() < i2) {
            arrayList.add(randomEle(list, size));
        }
        return arrayList;
    }

    public static float randomFloat() {
        return getRandom().nextFloat();
    }

    public static int randomInt() {
        return getRandom().nextInt();
    }

    public static int[] randomInts(int i2) {
        int[] iArrRange = PrimitiveArrayUtil.range(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            PrimitiveArrayUtil.swap(iArrRange, i3, randomInt(i3, i2));
        }
        return iArrRange;
    }

    public static long randomLong() {
        return getRandom().nextLong();
    }

    public static char randomNumber() {
        return randomChar(BASE_NUMBER);
    }

    public static String randomNumbers(int i2) {
        return randomString(BASE_NUMBER, i2);
    }

    public static String randomString(int i2) {
        return randomString(BASE_CHAR_NUMBER, i2);
    }

    public static String randomStringUpper(int i2) {
        return randomString(BASE_CHAR_NUMBER, i2).toUpperCase();
    }

    public static String randomStringWithoutStr(int i2, String str) {
        return randomString(CharSequenceUtil.removeAll(BASE_CHAR_NUMBER, str.toLowerCase().toCharArray()), i2);
    }

    public static <T> WeightRandom<T> weightRandom(WeightRandom.WeightObj<T>[] weightObjArr) {
        return new WeightRandom<>(weightObjArr);
    }

    public static Random getRandom(boolean z2) {
        return z2 ? getSecureRandom() : getRandom();
    }

    public static SecureRandom getSecureRandom(byte[] bArr) {
        return createSecureRandom(bArr);
    }

    public static BigDecimal randomBigDecimal(BigDecimal bigDecimal) {
        return NumberUtil.toBigDecimal(Double.valueOf(getRandom().nextDouble(bigDecimal.doubleValue())));
    }

    public static char randomChar(String str) {
        return str.charAt(randomInt(str.length()));
    }

    public static double randomDouble(double d3, double d4, int i2, RoundingMode roundingMode) {
        return NumberUtil.round(randomDouble(d3, d4), i2, roundingMode).doubleValue();
    }

    public static <T> T randomEle(List<T> list, int i2) {
        if (list.size() < i2) {
            i2 = list.size();
        }
        return list.get(randomInt(i2));
    }

    public static float randomFloat(float f2) {
        return randomFloat(0.0f, f2);
    }

    public static int randomInt(int i2) {
        return getRandom().nextInt(i2);
    }

    public static long randomLong(long j2) {
        return getRandom().nextLong(j2);
    }

    public static String randomString(String str, int i2) {
        if (CharSequenceUtil.isEmpty(str)) {
            return "";
        }
        if (i2 < 1) {
            i2 = 1;
        }
        StringBuilder sb = new StringBuilder(i2);
        int length = str.length();
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(str.charAt(randomInt(length)));
        }
        return sb.toString();
    }

    public static <T> WeightRandom<T> weightRandom(Iterable<WeightRandom.WeightObj<T>> iterable) {
        return new WeightRandom<>(iterable);
    }

    public static BigDecimal randomBigDecimal(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        return NumberUtil.toBigDecimal(Double.valueOf(getRandom().nextDouble(bigDecimal.doubleValue(), bigDecimal2.doubleValue())));
    }

    public static double randomDouble() {
        return getRandom().nextDouble();
    }

    public static float randomFloat(float f2, float f3) {
        return f2 == f3 ? f2 : f2 + ((f3 - f2) * getRandom().nextFloat());
    }

    public static int randomInt(int i2, int i3) {
        return randomInt(i2, i3, true, false);
    }

    public static long randomLong(long j2, long j3) {
        return randomLong(j2, j3, true, false);
    }

    public static double randomDouble(int i2, RoundingMode roundingMode) {
        return NumberUtil.round(randomDouble(), i2, roundingMode).doubleValue();
    }

    public static int randomInt(int i2, int i3, boolean z2, boolean z3) {
        if (!z2) {
            i2++;
        }
        if (z3) {
            i3--;
        }
        return getRandom().nextInt(i2, i3);
    }

    public static long randomLong(long j2, long j3, boolean z2, boolean z3) {
        if (!z2) {
            j2++;
        }
        if (z3) {
            j3--;
        }
        return getRandom().nextLong(j2, j3);
    }

    public static double randomDouble(double d3) {
        return getRandom().nextDouble(d3);
    }

    public static <T> T randomEle(T[] tArr) {
        return (T) randomEle(tArr, tArr.length);
    }

    public static double randomDouble(double d3, int i2, RoundingMode roundingMode) {
        return NumberUtil.round(randomDouble(d3), i2, roundingMode).doubleValue();
    }

    public static <T> T randomEle(T[] tArr, int i2) {
        if (tArr.length < i2) {
            i2 = tArr.length;
        }
        return tArr[randomInt(i2)];
    }
}
