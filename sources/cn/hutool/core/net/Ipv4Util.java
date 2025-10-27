package cn.hutool.core.net;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
public class Ipv4Util {
    public static final int IP_MASK_MAX = 32;
    public static final String IP_MASK_SPLIT_MARK = "/";
    public static final String IP_SPLIT_MARK = "-";
    public static final String LOCAL_IP = "127.0.0.1";

    public static int countByIpRange(String str, String str2) {
        if (ipv4ToLong(str) > ipv4ToLong(str2)) {
            throw new IllegalArgumentException("to IP must be greater than from IP!");
        }
        int[] array = CharSequenceUtil.split((CharSequence) str, '.').stream().mapToInt(new ToIntFunction() { // from class: i0.b
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return Integer.parseInt((String) obj);
            }
        }).toArray();
        int[] array2 = CharSequenceUtil.split((CharSequence) str2, '.').stream().mapToInt(new ToIntFunction() { // from class: i0.b
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return Integer.parseInt((String) obj);
            }
        }).toArray();
        int iPow = 1;
        for (int length = array.length - 1; length >= 0; length--) {
            iPow = (int) (iPow + ((array2[length] - array[length]) * Math.pow(256.0d, (array.length - length) - 1)));
        }
        return iPow;
    }

    public static int countByMaskBit(int i2, boolean z2) {
        if (!z2 && (i2 <= 0 || i2 >= 32)) {
            return 0;
        }
        int iPow = (int) Math.pow(2.0d, 32 - i2);
        return z2 ? iPow : iPow - 2;
    }

    public static String formatIpBlock(String str, String str2) {
        return str + "/" + getMaskBitByMask(str2);
    }

    public static Long getBeginIpLong(String str, int i2) {
        return Long.valueOf(ipv4ToLong(getMaskByMaskBit(i2)) & ipv4ToLong(str));
    }

    public static String getBeginIpStr(String str, int i2) {
        return longToIpv4(getBeginIpLong(str, i2).longValue());
    }

    public static Long getEndIpLong(String str, int i2) {
        return Long.valueOf(getBeginIpLong(str, i2).longValue() + ((~ipv4ToLong(getMaskByMaskBit(i2))) & InternalZipConstants.ZIP_64_LIMIT));
    }

    public static String getEndIpStr(String str, int i2) {
        return longToIpv4(getEndIpLong(str, i2).longValue());
    }

    public static int getMaskBitByMask(String str) {
        Integer maskBit = MaskBit.getMaskBit(str);
        if (maskBit != null) {
            return maskBit.intValue();
        }
        throw new IllegalArgumentException("Invalid netmask " + str);
    }

    public static String getMaskByIpRange(String str, String str2) throws Throwable {
        Assert.isTrue(ipv4ToLong(str) < ipv4ToLong(str2), "to IP must be greater than from IP!", new Object[0]);
        String[] strArrSplitToArray = CharSequenceUtil.splitToArray((CharSequence) str, '.');
        String[] strArrSplitToArray2 = CharSequenceUtil.splitToArray((CharSequence) str2, '.');
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < strArrSplitToArray2.length; i2++) {
            sb.append((255 - Integer.parseInt(strArrSplitToArray2[i2])) + Integer.parseInt(strArrSplitToArray[i2]));
            sb.append('.');
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String getMaskByMaskBit(int i2) {
        return MaskBit.get(i2);
    }

    public static long ipv4ToLong(String str) {
        Matcher matcher = PatternPool.IPV4.matcher(str);
        if (matcher.matches()) {
            return matchAddress(matcher);
        }
        throw new IllegalArgumentException("Invalid IPv4 address!");
    }

    private static boolean isInner(long j2, long j3, long j4) {
        return j2 >= j3 && j2 <= j4;
    }

    public static boolean isInnerIP(String str) {
        long jIpv4ToLong = ipv4ToLong(str);
        return isInner(jIpv4ToLong, ipv4ToLong("10.0.0.0"), ipv4ToLong("10.255.255.255")) || isInner(jIpv4ToLong, ipv4ToLong("172.16.0.0"), ipv4ToLong("172.31.255.255")) || isInner(jIpv4ToLong, ipv4ToLong("192.168.0.0"), ipv4ToLong("192.168.255.255")) || "127.0.0.1".equals(str);
    }

    public static boolean isMaskBitValid(int i2) {
        return MaskBit.get(i2) != null;
    }

    public static boolean isMaskValid(String str) {
        return MaskBit.getMaskBit(str) != null;
    }

    public static List<String> list(String str, boolean z2) {
        if (str.contains("-")) {
            String[] strArrSplitToArray = CharSequenceUtil.splitToArray(str, "-");
            return list(strArrSplitToArray[0], strArrSplitToArray[1]);
        }
        if (!str.contains("/")) {
            return ListUtil.toList(str);
        }
        String[] strArrSplitToArray2 = CharSequenceUtil.splitToArray(str, "/");
        return list(strArrSplitToArray2[0], Integer.parseInt(strArrSplitToArray2[1]), z2);
    }

    public static String longToIpv4(long j2) {
        StringBuilder sbBuilder = StrUtil.builder();
        sbBuilder.append((j2 >> 24) & 255);
        sbBuilder.append('.');
        sbBuilder.append((j2 >> 16) & 255);
        sbBuilder.append('.');
        sbBuilder.append((j2 >> 8) & 255);
        sbBuilder.append('.');
        sbBuilder.append(j2 & 255);
        return sbBuilder.toString();
    }

    private static long matchAddress(Matcher matcher) {
        long j2 = 0;
        for (int i2 = 1; i2 <= 4; i2++) {
            j2 |= Long.parseLong(matcher.group(i2)) << ((4 - i2) * 8);
        }
        return j2;
    }

    public static boolean matches(String str, String str2) {
        if (!ReUtil.isMatch(PatternPool.IPV4, str2)) {
            return false;
        }
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        if (strArrSplit.length != strArrSplit2.length) {
            return false;
        }
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (!"*".equals(strArrSplit[i2]) && !strArrSplit[i2].equals(strArrSplit2[i2])) {
                return false;
            }
        }
        return true;
    }

    public static long ipv4ToLong(String str, long j2) {
        return Validator.isIpv4(str) ? ipv4ToLong(str) : j2;
    }

    public static List<String> list(String str, int i2, boolean z2) {
        if (i2 == 32) {
            ArrayList arrayList = new ArrayList();
            if (z2) {
                arrayList.add(str);
            }
            return arrayList;
        }
        String beginIpStr = getBeginIpStr(str, i2);
        String endIpStr = getEndIpStr(str, i2);
        if (z2) {
            return list(beginIpStr, endIpStr);
        }
        int iLastIndexOf = beginIpStr.lastIndexOf(46) + 1;
        StringBuilder sb = new StringBuilder();
        sb.append(CharSequenceUtil.subPre(beginIpStr, iLastIndexOf));
        String strSubSuf = CharSequenceUtil.subSuf(beginIpStr, iLastIndexOf);
        Objects.requireNonNull(strSubSuf);
        sb.append(Integer.parseInt(strSubSuf) + 1);
        String string = sb.toString();
        int iLastIndexOf2 = endIpStr.lastIndexOf(46) + 1;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(CharSequenceUtil.subPre(endIpStr, iLastIndexOf2));
        Objects.requireNonNull(CharSequenceUtil.subSuf(endIpStr, iLastIndexOf2));
        sb2.append(Integer.parseInt(r3) - 1);
        return list(string, sb2.toString());
    }

    public static List<String> list(String str, String str2) {
        int iCountByIpRange = countByIpRange(str, str2);
        int[] iArr = (int[]) Convert.convert(int[].class, (Object) CharSequenceUtil.splitToArray((CharSequence) str, '.'));
        int[] iArr2 = (int[]) Convert.convert(int[].class, (Object) CharSequenceUtil.splitToArray((CharSequence) str2, '.'));
        ArrayList arrayList = new ArrayList(iCountByIpRange);
        int i2 = 0;
        int i3 = iArr2[0];
        int i4 = iArr[0];
        int i5 = 1;
        boolean z2 = true;
        boolean z3 = true;
        boolean z4 = true;
        while (i4 <= i3) {
            int i6 = i4 == i3 ? i5 : i2;
            int i7 = i6 != 0 ? iArr2[i5] : 255;
            int i8 = z2 ? iArr[i5] : i2;
            while (i8 <= i7) {
                int i9 = (i6 == 0 || i8 != i7) ? i2 : i5;
                int i10 = i9 != 0 ? iArr2[2] : 255;
                int i11 = z3 ? iArr[2] : i2;
                while (i11 <= i10) {
                    int i12 = ((i9 == 0 || i11 != i10) ? i2 : i5) != 0 ? iArr2[3] : 255;
                    for (int i13 = z4 ? iArr[3] : i2; i13 <= i12; i13++) {
                        arrayList.add(i4 + StrPool.DOT + i8 + StrPool.DOT + i11 + StrPool.DOT + i13);
                    }
                    i11++;
                    i2 = 0;
                    i5 = 1;
                    z4 = false;
                }
                i8++;
                i2 = 0;
                i5 = 1;
                z3 = false;
            }
            i4++;
            i2 = 0;
            i5 = 1;
            z2 = false;
        }
        return arrayList;
    }
}
