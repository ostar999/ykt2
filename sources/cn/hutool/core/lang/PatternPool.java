package cn.hutool.core.lang;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.map.WeakConcurrentMap;
import java.util.function.Function;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class PatternPool {
    public static final Pattern GENERAL = Pattern.compile(RegexPool.GENERAL);
    public static final Pattern NUMBERS = Pattern.compile(RegexPool.NUMBERS);
    public static final Pattern WORD = Pattern.compile(RegexPool.WORD);
    public static final Pattern CHINESE = Pattern.compile("[⺀-\u2eff⼀-\u2fdf㇀-\u31ef㐀-䶿一-鿿豈-\ufaff𠀀-𪛟𪜀-\u2b73f𫝀-\u2b81f𫠠-\u2ceaf丽-\u2fa1f]");
    public static final Pattern CHINESES = Pattern.compile("[⺀-\u2eff⼀-\u2fdf㇀-\u31ef㐀-䶿一-鿿豈-\ufaff𠀀-𪛟𪜀-\u2b73f𫝀-\u2b81f𫠠-\u2ceaf丽-\u2fa1f]+");
    public static final Pattern GROUP_VAR = Pattern.compile(RegexPool.GROUP_VAR);
    public static final Pattern IPV4 = Pattern.compile(RegexPool.IPV4);
    public static final Pattern IPV6 = Pattern.compile(RegexPool.IPV6);
    public static final Pattern MONEY = Pattern.compile(RegexPool.MONEY);
    public static final Pattern EMAIL = Pattern.compile(RegexPool.EMAIL, 2);
    public static final Pattern EMAIL_WITH_CHINESE = Pattern.compile(RegexPool.EMAIL_WITH_CHINESE, 2);
    public static final Pattern MOBILE = Pattern.compile(RegexPool.MOBILE);
    public static final Pattern MOBILE_HK = Pattern.compile(RegexPool.MOBILE_HK);
    public static final Pattern MOBILE_TW = Pattern.compile(RegexPool.MOBILE_TW);
    public static final Pattern MOBILE_MO = Pattern.compile(RegexPool.MOBILE_MO);
    public static final Pattern TEL = Pattern.compile(RegexPool.TEL);
    public static final Pattern TEL_400_800 = Pattern.compile(RegexPool.TEL_400_800);
    public static final Pattern CITIZEN_ID = Pattern.compile(RegexPool.CITIZEN_ID);
    public static final Pattern ZIP_CODE = Pattern.compile(RegexPool.ZIP_CODE);
    public static final Pattern BIRTHDAY = Pattern.compile(RegexPool.BIRTHDAY);
    public static final Pattern URL = Pattern.compile(RegexPool.URL);
    public static final Pattern URL_HTTP = Pattern.compile(RegexPool.URL_HTTP, 2);
    public static final Pattern GENERAL_WITH_CHINESE = Pattern.compile(RegexPool.GENERAL_WITH_CHINESE);
    public static final Pattern UUID = Pattern.compile(RegexPool.UUID, 2);
    public static final Pattern UUID_SIMPLE = Pattern.compile(RegexPool.UUID_SIMPLE);
    public static final Pattern MAC_ADDRESS = Pattern.compile(RegexPool.MAC_ADDRESS, 2);
    public static final Pattern HEX = Pattern.compile(RegexPool.HEX);
    public static final Pattern TIME = Pattern.compile(RegexPool.TIME);
    public static final Pattern PLATE_NUMBER = Pattern.compile(RegexPool.PLATE_NUMBER);
    public static final Pattern CREDIT_CODE = Pattern.compile(RegexPool.CREDIT_CODE);
    public static final Pattern CAR_VIN = Pattern.compile(RegexPool.CAR_VIN);
    public static final Pattern CAR_DRIVING_LICENCE = Pattern.compile(RegexPool.CAR_DRIVING_LICENCE);
    public static final Pattern CHINESE_NAME = Pattern.compile(RegexPool.CHINESE_NAME);
    private static final WeakConcurrentMap<RegexWithFlag, Pattern> POOL = new WeakConcurrentMap<>();

    public static class RegexWithFlag {
        private final int flag;
        private final String regex;

        public RegexWithFlag(String str, int i2) {
            this.regex = str;
            this.flag = i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RegexWithFlag regexWithFlag = (RegexWithFlag) obj;
            if (this.flag != regexWithFlag.flag) {
                return false;
            }
            String str = this.regex;
            return str == null ? regexWithFlag.regex == null : str.equals(regexWithFlag.regex);
        }

        public int hashCode() {
            int i2 = (this.flag + 31) * 31;
            String str = this.regex;
            return i2 + (str == null ? 0 : str.hashCode());
        }
    }

    public static void clear() {
        POOL.clear();
    }

    public static Pattern get(String str) {
        return get(str, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Pattern lambda$get$0(String str, int i2, RegexWithFlag regexWithFlag) {
        return Pattern.compile(str, i2);
    }

    public static Pattern remove(String str, int i2) {
        return POOL.remove(new RegexWithFlag(str, i2));
    }

    public static Pattern get(final String str, final int i2) {
        return POOL.computeIfAbsent((WeakConcurrentMap<RegexWithFlag, Pattern>) new RegexWithFlag(str, i2), (Function<? super WeakConcurrentMap<RegexWithFlag, Pattern>, ? extends Pattern>) new Function() { // from class: cn.hutool.core.lang.f0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PatternPool.lambda$get$0(str, i2, (PatternPool.RegexWithFlag) obj);
            }
        });
    }
}
