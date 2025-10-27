package cn.hutool.core.util;

import androidx.exifinterface.media.ExifInterface;
import androidx.room.RoomMasterTable;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R2;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class IdcardUtil {
    private static final int CHINA_ID_MAX_LENGTH = 18;
    private static final int CHINA_ID_MIN_LENGTH = 15;
    private static final Map<String, String> CITY_CODES;
    private static final int[] POWER = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final Map<Character, Integer> TW_FIRST_CODE;

    public static class Idcard implements Serializable {
        private static final long serialVersionUID = 1;
        private final int age;
        private final DateTime birthDate;
        private final String cityCode;
        private final Integer gender;
        private final String provinceCode;

        public Idcard(String str) {
            this.provinceCode = IdcardUtil.getProvinceCodeByIdCard(str);
            this.cityCode = IdcardUtil.getCityCodeByIdCard(str);
            this.birthDate = IdcardUtil.getBirthDate(str);
            this.gender = Integer.valueOf(IdcardUtil.getGenderByIdCard(str));
            this.age = IdcardUtil.getAgeByIdCard(str);
        }

        public int getAge() {
            return this.age;
        }

        public DateTime getBirthDate() {
            return this.birthDate;
        }

        public String getCityCode() {
            return this.cityCode;
        }

        public Integer getGender() {
            return this.gender;
        }

        public String getProvince() {
            return (String) IdcardUtil.CITY_CODES.get(this.provinceCode);
        }

        public String getProvinceCode() {
            return this.provinceCode;
        }

        public String toString() {
            return "Idcard{provinceCode='" + this.provinceCode + CharPool.SINGLE_QUOTE + ", cityCode='" + this.cityCode + CharPool.SINGLE_QUOTE + ", birthDate=" + this.birthDate + ", gender=" + this.gender + ", age=" + this.age + '}';
        }
    }

    static {
        HashMap map = new HashMap();
        CITY_CODES = map;
        HashMap map2 = new HashMap();
        TW_FIRST_CODE = map2;
        map.put(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE, "北京");
        map.put(Constants.VIA_REPORT_TYPE_SET_AVATAR, "天津");
        map.put(Constants.VIA_REPORT_TYPE_JOININ_GROUP, "河北");
        map.put(Constants.VIA_REPORT_TYPE_MAKE_FRIEND, "山西");
        map.put(Constants.VIA_REPORT_TYPE_WPA_STATE, "内蒙古");
        map.put("21", "辽宁");
        map.put(Constants.VIA_REPORT_TYPE_DATALINE, "吉林");
        map.put(Constants.VIA_REPORT_TYPE_SHARE_TO_TROOPBAR, "黑龙江");
        map.put("31", "上海");
        map.put("32", "江苏");
        map.put("33", "浙江");
        map.put("34", "安徽");
        map.put("35", "福建");
        map.put("36", "江西");
        map.put("37", "山东");
        map.put("41", "河南");
        map.put(RoomMasterTable.DEFAULT_ID, "湖北");
        map.put("43", "湖南");
        map.put("44", "广东");
        map.put("45", "广西");
        map.put("46", "海南");
        map.put("50", "重庆");
        map.put("51", "四川");
        map.put("52", "贵州");
        map.put("53", "云南");
        map.put("54", "西藏");
        map.put("61", "陕西");
        map.put("62", "甘肃");
        map.put("63", "青海");
        map.put("64", "宁夏");
        map.put("65", "新疆");
        map.put("71", "台湾");
        map.put("81", "香港");
        map.put("82", "澳门");
        map.put("83", "台湾");
        map.put("91", "国外");
        map2.put('A', 10);
        map2.put('B', 11);
        map2.put('C', 12);
        map2.put('D', 13);
        map2.put('E', 14);
        map2.put('F', 15);
        map2.put('G', 16);
        map2.put('H', 17);
        map2.put('J', 18);
        map2.put('K', 19);
        map2.put('L', 20);
        map2.put('M', 21);
        map2.put('N', 22);
        map2.put('P', 23);
        map2.put('Q', 24);
        map2.put('R', 25);
        map2.put('S', 26);
        map2.put('T', 27);
        map2.put('U', 28);
        map2.put('V', 29);
        map2.put('X', 30);
        map2.put('Y', 31);
        map2.put('W', 32);
        map2.put('Z', 33);
        map2.put('I', 34);
        map2.put('O', 35);
    }

    public static String convert15To18(String str) {
        if (str.length() != 15 || !ReUtil.isMatch(PatternPool.NUMBERS, str)) {
            return null;
        }
        int iYear = DateUtil.year(DateUtil.parse(str.substring(6, 12), "yyMMdd"));
        if (iYear > 2000) {
            iYear -= 100;
        }
        StringBuilder sbBuilder = StrUtil.builder();
        sbBuilder.append((CharSequence) str, 0, 6);
        sbBuilder.append(iYear);
        sbBuilder.append(str.substring(8));
        sbBuilder.append(getCheckCode18(sbBuilder.toString()));
        return sbBuilder.toString();
    }

    public static String convert18To15(String str) {
        if (!CharSequenceUtil.isNotBlank(str) || !isValidCard18(str)) {
            return str;
        }
        return str.substring(0, 6) + str.substring(8, str.length() - 1);
    }

    public static int getAgeByIdCard(String str) {
        return getAgeByIdCard(str, DateUtil.date());
    }

    public static String getBirth(String str) throws IllegalArgumentException {
        Assert.notBlank(str, "id card must be not blank!", new Object[0]);
        int length = str.length();
        if (length < 15) {
            return null;
        }
        if (length == 15) {
            str = convert15To18(str);
        }
        Objects.requireNonNull(str);
        return str.substring(6, 14);
    }

    public static String getBirthByIdCard(String str) {
        return getBirth(str);
    }

    public static DateTime getBirthDate(String str) {
        String birthByIdCard = getBirthByIdCard(str);
        if (birthByIdCard == null) {
            return null;
        }
        return DateUtil.parse(birthByIdCard, DatePattern.PURE_DATE_FORMAT);
    }

    private static char getCheckCode18(String str) {
        return getCheckCode18(getPowerSum(str.toCharArray()));
    }

    public static String getCityCodeByIdCard(String str) {
        int length = str.length();
        if (length == 15 || length == 18) {
            return str.substring(0, 4);
        }
        return null;
    }

    public static Short getDayByIdCard(String str) {
        int length = str.length();
        if (length < 15) {
            return null;
        }
        if (length == 15) {
            str = convert15To18(str);
        }
        Objects.requireNonNull(str);
        return Short.valueOf(str.substring(12, 14));
    }

    public static String getDistrictCodeByIdCard(String str) {
        int length = str.length();
        if (length == 15 || length == 18) {
            return str.substring(0, 6);
        }
        return null;
    }

    public static int getGenderByIdCard(String str) throws IllegalArgumentException {
        Assert.notBlank(str);
        int length = str.length();
        if (length != 15 && length != 18) {
            throw new IllegalArgumentException("ID Card length must be 15 or 18");
        }
        if (length == 15) {
            str = convert15To18(str);
        }
        Objects.requireNonNull(str);
        return str.charAt(16) % 2 != 0 ? 1 : 0;
    }

    public static Idcard getIdcardInfo(String str) {
        return new Idcard(str);
    }

    public static Short getMonthByIdCard(String str) {
        int length = str.length();
        if (length < 15) {
            return null;
        }
        if (length == 15) {
            str = convert15To18(str);
        }
        Objects.requireNonNull(str);
        return Short.valueOf(str.substring(10, 12));
    }

    private static int getPowerSum(char[] cArr) {
        if (POWER.length != cArr.length) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < cArr.length; i3++) {
            i2 += Integer.parseInt(String.valueOf(cArr[i3])) * POWER[i3];
        }
        return i2;
    }

    public static String getProvinceByIdCard(String str) {
        String provinceCodeByIdCard = getProvinceCodeByIdCard(str);
        if (CharSequenceUtil.isNotBlank(provinceCodeByIdCard)) {
            return CITY_CODES.get(provinceCodeByIdCard);
        }
        return null;
    }

    public static String getProvinceCodeByIdCard(String str) {
        int length = str.length();
        if (length == 15 || length == 18) {
            return str.substring(0, 2);
        }
        return null;
    }

    public static Short getYearByIdCard(String str) {
        int length = str.length();
        if (length < 15) {
            return null;
        }
        if (length == 15) {
            str = convert15To18(str);
        }
        Objects.requireNonNull(str);
        return Short.valueOf(str.substring(6, 10));
    }

    public static String hide(String str, int i2, int i3) {
        return CharSequenceUtil.hide(str, i2, i3);
    }

    public static boolean isValidCard(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return false;
        }
        int length = str.length();
        if (length == 10) {
            String[] strArrIsValidCard10 = isValidCard10(str);
            return strArrIsValidCard10 != null && k.a.f27523u.equals(strArrIsValidCard10[2]);
        }
        if (length == 15) {
            return isValidCard15(str);
        }
        if (length != 18) {
            return false;
        }
        return isValidCard18(str);
    }

    public static String[] isValidCard10(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return null;
        }
        String[] strArr = new String[3];
        String strReplaceAll = str.replaceAll("[()]", "");
        if (strReplaceAll.length() != 8 && strReplaceAll.length() != 9 && str.length() != 10) {
            return null;
        }
        boolean zMatches = str.matches("^[a-zA-Z][0-9]{9}$");
        String str2 = k.a.f27524v;
        if (zMatches) {
            strArr[0] = "台湾";
            char cCharAt = str.charAt(1);
            if ('1' == cCharAt) {
                strArr[1] = "M";
            } else {
                if ('2' != cCharAt) {
                    strArr[1] = "N";
                    strArr[2] = k.a.f27524v;
                    return strArr;
                }
                strArr[1] = "F";
            }
            if (isValidTWCard(str)) {
                str2 = k.a.f27523u;
            }
            strArr[2] = str2;
        } else if (str.matches("^[157][0-9]{6}\\(?[0-9A-Z]\\)?$")) {
            strArr[0] = "澳门";
            strArr[1] = "N";
            strArr[2] = k.a.f27523u;
        } else {
            if (!str.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) {
                return null;
            }
            strArr[0] = "香港";
            strArr[1] = "N";
            if (isValidHKCard(str)) {
                str2 = k.a.f27523u;
            }
            strArr[2] = str2;
        }
        return strArr;
    }

    public static boolean isValidCard15(String str) {
        if (str == null || 15 != str.length() || !ReUtil.isMatch(PatternPool.NUMBERS, str)) {
            return false;
        }
        if (CITY_CODES.get(str.substring(0, 2)) == null) {
            return false;
        }
        return Validator.isBirthday(Constants.VIA_ACT_TYPE_NINETEEN + str.substring(6, 12));
    }

    public static boolean isValidCard18(String str) {
        return isValidCard18(str, true);
    }

    public static boolean isValidHKCard(String str) {
        int upperCase;
        String strReplaceAll = str.replaceAll("[()]", "");
        if (strReplaceAll.length() == 9) {
            upperCase = ((Character.toUpperCase(strReplaceAll.charAt(0)) - '7') * 9) + ((Character.toUpperCase(strReplaceAll.charAt(1)) - '7') * 8);
            strReplaceAll = strReplaceAll.substring(1, 9);
        } else {
            upperCase = ((Character.toUpperCase(strReplaceAll.charAt(0)) - '7') * 8) + R2.attr.bl_checkable_drawable;
        }
        int i2 = 7;
        String strSubstring = strReplaceAll.substring(1, 7);
        String strSubstring2 = strReplaceAll.substring(7, 8);
        for (char c3 : strSubstring.toCharArray()) {
            upperCase += Integer.parseInt(String.valueOf(c3)) * i2;
            i2--;
        }
        return (ExifInterface.GPS_MEASUREMENT_IN_PROGRESS.equalsIgnoreCase(strSubstring2) ? upperCase + 10 : upperCase + Integer.parseInt(strSubstring2)) % 11 == 0;
    }

    public static boolean isValidTWCard(String str) {
        Integer num;
        if (str == null || str.length() != 10 || (num = TW_FIRST_CODE.get(Character.valueOf(str.charAt(0)))) == null) {
            return false;
        }
        int iIntValue = (num.intValue() / 10) + ((num.intValue() % 10) * 9);
        int i2 = 8;
        for (char c3 : str.substring(1, 9).toCharArray()) {
            iIntValue += Integer.parseInt(String.valueOf(c3)) * i2;
            i2--;
        }
        int i3 = iIntValue % 10;
        return (i3 == 0 ? 0 : 10 - i3) == Integer.parseInt(str.substring(9, 10));
    }

    public static int getAgeByIdCard(String str, Date date) {
        return DateUtil.age(DateUtil.parse(getBirthByIdCard(str), DatePattern.PURE_DATE_PATTERN), date);
    }

    public static boolean isValidCard18(String str, boolean z2) {
        if (str == null || 18 != str.length()) {
            return false;
        }
        if (CITY_CODES.get(str.substring(0, 2)) == null || !Validator.isBirthday(str.substring(6, 14))) {
            return false;
        }
        String strSubstring = str.substring(0, 17);
        if (ReUtil.isMatch(PatternPool.NUMBERS, strSubstring)) {
            return CharUtil.equals(getCheckCode18(strSubstring), str.charAt(17), z2);
        }
        return false;
    }

    private static char getCheckCode18(int i2) {
        switch (i2 % 11) {
            case 0:
                return '1';
            case 1:
                return '0';
            case 2:
                return 'X';
            case 3:
                return '9';
            case 4:
                return '8';
            case 5:
                return '7';
            case 6:
                return '6';
            case 7:
                return '5';
            case 8:
                return '4';
            case 9:
                return '3';
            case 10:
                return '2';
            default:
                return ' ';
        }
    }
}
