package cn.hutool.core.lang;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CreditCodeUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class Validator {
    public static final Pattern GENERAL = PatternPool.GENERAL;
    public static final Pattern NUMBERS = PatternPool.NUMBERS;
    public static final Pattern GROUP_VAR = PatternPool.GROUP_VAR;
    public static final Pattern IPV4 = PatternPool.IPV4;
    public static final Pattern IPV6 = PatternPool.IPV6;
    public static final Pattern MONEY = PatternPool.MONEY;
    public static final Pattern EMAIL = PatternPool.EMAIL;
    public static final Pattern EMAIL_WITH_CHINESE = PatternPool.EMAIL_WITH_CHINESE;
    public static final Pattern MOBILE = PatternPool.MOBILE;
    public static final Pattern CITIZEN_ID = PatternPool.CITIZEN_ID;
    public static final Pattern ZIP_CODE = PatternPool.ZIP_CODE;
    public static final Pattern BIRTHDAY = PatternPool.BIRTHDAY;
    public static final Pattern URL = PatternPool.URL;
    public static final Pattern URL_HTTP = PatternPool.URL_HTTP;
    public static final Pattern GENERAL_WITH_CHINESE = PatternPool.GENERAL_WITH_CHINESE;
    public static final Pattern UUID = PatternPool.UUID;
    public static final Pattern UUID_SIMPLE = PatternPool.UUID_SIMPLE;
    public static final Pattern PLATE_NUMBER = PatternPool.PLATE_NUMBER;
    public static final Pattern CAR_VIN = PatternPool.CAR_VIN;
    public static final Pattern CAR_DRIVING_LICENCE = PatternPool.CAR_DRIVING_LICENCE;

    public static void checkIndexLimit(int i2, int i3) {
        if (i2 > (i3 + 1) * 10) {
            throw new ValidateException("Index [{}] is too large for size: [{}]", Integer.valueOf(i2), Integer.valueOf(i3));
        }
    }

    public static boolean equal(Object obj, Object obj2) {
        return ObjectUtil.equal(obj, obj2);
    }

    public static boolean hasChinese(CharSequence charSequence) {
        return ReUtil.contains("[⺀-\u2eff⼀-\u2fdf㇀-\u31ef㐀-䶿一-鿿豈-\ufaff𠀀-𪛟𪜀-\u2b73f𫝀-\u2b81f𫠠-\u2ceaf丽-\u2fa1f]+", charSequence);
    }

    public static boolean hasNumber(CharSequence charSequence) {
        return ReUtil.contains(PatternPool.NUMBERS, charSequence);
    }

    public static boolean isBetween(Number number, Number number2, Number number3) throws IllegalArgumentException {
        Assert.notNull(number);
        Assert.notNull(number2);
        Assert.notNull(number3);
        double dDoubleValue = number.doubleValue();
        return dDoubleValue >= number2.doubleValue() && dDoubleValue <= number3.doubleValue();
    }

    public static boolean isBirthday(int i2, int i3, int i4) {
        int iThisYear = DateUtil.thisYear();
        if (i2 < 1900 || i2 > iThisYear || i3 < 1 || i3 > 12 || i4 < 1 || i4 > 31) {
            return false;
        }
        if (i4 == 31 && (i3 == 4 || i3 == 6 || i3 == 9 || i3 == 11)) {
            return false;
        }
        if (i3 == 2) {
            return i4 < 29 || (i4 == 29 && DateUtil.isLeapYear(i2));
        }
        return true;
    }

    public static boolean isCarDrivingLicence(CharSequence charSequence) {
        return isMatchRegex(CAR_DRIVING_LICENCE, charSequence);
    }

    public static boolean isCarVin(CharSequence charSequence) {
        return isMatchRegex(CAR_VIN, charSequence);
    }

    public static boolean isChinese(CharSequence charSequence) {
        return isMatchRegex(PatternPool.CHINESES, charSequence);
    }

    public static boolean isChineseName(CharSequence charSequence) {
        return isMatchRegex(PatternPool.CHINESE_NAME, charSequence);
    }

    public static boolean isCitizenId(CharSequence charSequence) {
        return IdcardUtil.isValidCard(String.valueOf(charSequence));
    }

    public static boolean isCreditCode(CharSequence charSequence) {
        return CreditCodeUtil.isCreditCode(charSequence);
    }

    public static boolean isEmail(CharSequence charSequence) {
        return isMatchRegex(EMAIL, charSequence);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || ((obj instanceof String) && CharSequenceUtil.isEmpty((String) obj));
    }

    public static boolean isFalse(boolean z2) {
        return !z2;
    }

    public static boolean isGeneral(CharSequence charSequence) {
        return isMatchRegex(GENERAL, charSequence);
    }

    public static boolean isGeneralWithChinese(CharSequence charSequence) {
        return isMatchRegex(GENERAL_WITH_CHINESE, charSequence);
    }

    public static boolean isHex(CharSequence charSequence) {
        return isMatchRegex(PatternPool.HEX, charSequence);
    }

    public static boolean isIpv4(CharSequence charSequence) {
        return isMatchRegex(IPV4, charSequence);
    }

    public static boolean isIpv6(CharSequence charSequence) {
        return isMatchRegex(IPV6, charSequence);
    }

    public static boolean isLetter(CharSequence charSequence) {
        return CharSequenceUtil.isAllCharMatch(charSequence, new Matcher() { // from class: cn.hutool.core.lang.r0
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return Character.isLetter(((Character) obj).charValue());
            }
        });
    }

    public static boolean isLowerCase(CharSequence charSequence) {
        return CharSequenceUtil.isAllCharMatch(charSequence, new Matcher() { // from class: cn.hutool.core.lang.p0
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return Character.isLowerCase(((Character) obj).charValue());
            }
        });
    }

    public static boolean isMac(CharSequence charSequence) {
        return isMatchRegex(PatternPool.MAC_ADDRESS, charSequence);
    }

    public static boolean isMatchRegex(Pattern pattern, CharSequence charSequence) {
        return ReUtil.isMatch(pattern, charSequence);
    }

    public static boolean isMobile(CharSequence charSequence) {
        return isMatchRegex(MOBILE, charSequence);
    }

    public static boolean isMoney(CharSequence charSequence) {
        return isMatchRegex(MONEY, charSequence);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNumber(CharSequence charSequence) {
        return NumberUtil.isNumber(charSequence);
    }

    public static boolean isPlateNumber(CharSequence charSequence) {
        return isMatchRegex(PLATE_NUMBER, charSequence);
    }

    public static boolean isTrue(boolean z2) {
        return z2;
    }

    public static boolean isUUID(CharSequence charSequence) {
        return isMatchRegex(UUID, charSequence) || isMatchRegex(UUID_SIMPLE, charSequence);
    }

    public static boolean isUpperCase(CharSequence charSequence) {
        return CharSequenceUtil.isAllCharMatch(charSequence, new Matcher() { // from class: cn.hutool.core.lang.q0
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return Character.isUpperCase(((Character) obj).charValue());
            }
        });
    }

    public static boolean isUrl(CharSequence charSequence) {
        if (CharSequenceUtil.isBlank(charSequence)) {
            return false;
        }
        try {
            new URL(CharSequenceUtil.str(charSequence));
            return true;
        } catch (MalformedURLException unused) {
            return false;
        }
    }

    public static boolean isWord(CharSequence charSequence) {
        return isMatchRegex(PatternPool.WORD, charSequence);
    }

    public static boolean isZipCode(CharSequence charSequence) {
        return isMatchRegex(ZIP_CODE, charSequence);
    }

    public static void validateBetween(Number number, Number number2, Number number3, String str) throws ValidateException {
        if (!isBetween(number, number2, number3)) {
            throw new ValidateException(str);
        }
    }

    public static <T extends CharSequence> T validateBirthday(T t2, String str) throws ValidateException {
        if (isBirthday(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateCarDrivingLicence(T t2, String str) throws ValidateException {
        if (isCarDrivingLicence(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateCarVin(T t2, String str) throws ValidateException {
        if (isCarVin(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateChinese(T t2, String str) throws ValidateException {
        if (isChinese(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateCitizenIdNumber(T t2, String str) throws ValidateException {
        if (isCitizenId(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateEmail(T t2, String str) throws ValidateException {
        if (isEmail(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T> T validateEmpty(T t2, String str) throws ValidateException {
        if (isNotEmpty(t2)) {
            throw new ValidateException(str);
        }
        return t2;
    }

    public static Object validateEqual(Object obj, Object obj2, String str) throws ValidateException {
        if (equal(obj, obj2)) {
            return obj;
        }
        throw new ValidateException(str);
    }

    public static boolean validateFalse(boolean z2, String str, Object... objArr) throws ValidateException {
        if (isTrue(z2)) {
            throw new ValidateException(str, objArr);
        }
        return false;
    }

    public static <T extends CharSequence> T validateGeneral(T t2, String str) throws ValidateException {
        if (isGeneral(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateGeneralWithChinese(T t2, String str) throws ValidateException {
        if (isGeneralWithChinese(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateHex(T t2, String str) throws ValidateException {
        if (isHex(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateIpv4(T t2, String str) throws ValidateException {
        if (isIpv4(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateIpv6(T t2, String str) throws ValidateException {
        if (isIpv6(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateLetter(T t2, String str) throws ValidateException {
        if (isLetter(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateLowerCase(T t2, String str) throws ValidateException {
        if (isLowerCase(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateMac(T t2, String str) throws ValidateException {
        if (isMac(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateMatchRegex(String str, T t2, String str2) throws ValidateException {
        if (isMatchRegex(str, t2)) {
            return t2;
        }
        throw new ValidateException(str2);
    }

    public static <T extends CharSequence> T validateMobile(T t2, String str) throws ValidateException {
        if (isMobile(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateMoney(T t2, String str) throws ValidateException {
        if (isMoney(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T> T validateNotEmpty(T t2, String str) throws ValidateException {
        if (isEmpty(t2)) {
            throw new ValidateException(str);
        }
        return t2;
    }

    public static void validateNotEmptyAndEqual(Object obj, Object obj2, String str) throws ValidateException {
        validateNotEmpty(obj, str);
        validateEqual(obj, obj2, str);
    }

    public static void validateNotEmptyAndNotEqual(Object obj, Object obj2, String str) throws ValidateException {
        validateNotEmpty(obj, str);
        validateNotEqual(obj, obj2, str);
    }

    public static void validateNotEqual(Object obj, Object obj2, String str) throws ValidateException {
        if (equal(obj, obj2)) {
            throw new ValidateException(str);
        }
    }

    public static <T> T validateNotNull(T t2, String str, Object... objArr) throws ValidateException {
        if (isNull(t2)) {
            throw new ValidateException(str, objArr);
        }
        return t2;
    }

    public static <T> T validateNull(T t2, String str, Object... objArr) throws ValidateException {
        if (isNotNull(t2)) {
            throw new ValidateException(str, objArr);
        }
        return null;
    }

    public static String validateNumber(String str, String str2) throws ValidateException {
        if (isNumber(str)) {
            return str;
        }
        throw new ValidateException(str2);
    }

    public static <T extends CharSequence> T validatePlateNumber(T t2, String str) throws ValidateException {
        if (isPlateNumber(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static boolean validateTrue(boolean z2, String str, Object... objArr) throws ValidateException {
        if (isFalse(z2)) {
            throw new ValidateException(str, objArr);
        }
        return true;
    }

    public static <T extends CharSequence> T validateUUID(T t2, String str) throws ValidateException {
        if (isUUID(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateUpperCase(T t2, String str) throws ValidateException {
        if (isUpperCase(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateUrl(T t2, String str) throws ValidateException {
        if (isUrl(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateWord(T t2, String str) throws ValidateException {
        if (isWord(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static <T extends CharSequence> T validateZipCode(T t2, String str) throws ValidateException {
        if (isZipCode(t2)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static boolean isEmail(CharSequence charSequence, boolean z2) {
        return z2 ? isMatchRegex(EMAIL_WITH_CHINESE, charSequence) : isEmail(charSequence);
    }

    public static boolean isGeneral(CharSequence charSequence, int i2, int i3) {
        if (i2 < 0) {
            i2 = 0;
        }
        String str = "^\\w{" + i2 + "," + i3 + "}$";
        if (i3 <= 0) {
            str = "^\\w{" + i2 + ",}$";
        }
        return isMatchRegex(str, charSequence);
    }

    public static boolean isMatchRegex(String str, CharSequence charSequence) {
        return ReUtil.isMatch(str, charSequence);
    }

    public static boolean isBirthday(CharSequence charSequence) {
        java.util.regex.Matcher matcher = BIRTHDAY.matcher(charSequence);
        if (matcher.find()) {
            return isBirthday(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(5)));
        }
        return false;
    }

    public static <T extends CharSequence> T validateGeneral(T t2, int i2, int i3, String str) throws ValidateException {
        if (isGeneral(t2, i2, i3)) {
            return t2;
        }
        throw new ValidateException(str);
    }

    public static boolean isGeneral(CharSequence charSequence, int i2) {
        return isGeneral(charSequence, i2, 0);
    }

    public static <T extends CharSequence> T validateGeneral(T t2, int i2, String str) throws ValidateException {
        return (T) validateGeneral(t2, i2, 0, str);
    }
}
