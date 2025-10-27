package com.psychiatrygarden.utils.weblong;

import com.alipay.sdk.cons.b;
import com.alipay.sdk.sys.a;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.d;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.UnsupportedEncodingException;

/* loaded from: classes6.dex */
public class StringUtils {
    private static int[] pyValue = {-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242, -20230, -20051, -20036, -20032, -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751, -19746, -19741, -19739, -19728, -19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281, -19275, -19270, -19263, -19261, -19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006, -19003, -18996, -18977, -18961, -18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696, -18526, -18518, -18501, -18490, -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183, -18181, -18012, -17997, -17988, -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721, -17703, -17701, -17697, -17692, -17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983, -16970, -16942, -16915, -16733, -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433, -16429, -16427, -16423, -16419, -16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171, -16169, -16158, -16155, -15959, -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661, -15659, -15652, -15640, -15631, -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369, -15363, -15362, -15183, -15180, -15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119, -15117, -15110, -15109, -14941, -14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889, -14882, -14873, -14871, -14857, -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384, -14379, -14368, -14355, -14353, -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112, -14109, -14099, -14097, -14094, -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878, -13870, -13859, -13847, -13831, -13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359, -13356, -13343, -13340, -13329, -13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060, -12888, -12875, -12871, -12860, -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556, -12359, -12346, -12320, -12300, -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781, -11604, -11589, -11536, -11358, -11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024, -11020, -11019, -11018, -11014, -10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329, -10328, -10322, -10315, -10309, -10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};
    private static String[] pyStr = {am.av, "ai", a.f3324i, "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin", "bing", "bo", AliyunLogKey.KEY_BUCKET, AliyunLogKey.KEY_CARRIER, "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang", "chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", SocializeProtocolConstants.PROTOCOL_KEY_DE, "deng", AppIconSetting.DEFAULT_LARGE_ICON, "dian", "diao", "die", "ding", "diu", "dong", "dou", d.W, "duan", "dui", "dun", "duo", AliyunLogKey.KEY_EVENT, SocializeProtocolConstants.PROTOCOL_KEY_EN, "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng", AppIconSetting.LARGE_ICON_URL, "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", AliyunLogKey.KEY_LOG_VERSION, "luan", "lue", "lun", "luo", "ma", "mai", "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan", "nue", "nuo", "o", "ou", b.f3225k, "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha", "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};

    private StringUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    public static String cn2PY(String s2) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < s2.length()) {
            int i3 = i2 + 1;
            String strOneCn2PY = oneCn2PY(s2.substring(i2, i3));
            if (strOneCn2PY == null) {
                strOneCn2PY = "?";
            }
            sb.append(strOneCn2PY);
            i2 = i3;
        }
        return sb.toString();
    }

    public static String getPYFirstLetter(String s2) throws UnsupportedEncodingException {
        if (isSpace(s2)) {
            return "";
        }
        String strOneCn2PY = oneCn2PY(s2.substring(0, 1));
        if (strOneCn2PY == null) {
            return null;
        }
        return strOneCn2PY.substring(0, 1);
    }

    public static boolean isEmpty(CharSequence s2) {
        return s2 == null || s2.length() == 0;
    }

    public static boolean isSpace(String s2) {
        return s2 == null || s2.trim().length() == 0;
    }

    public static String join(String[] var0, String var1) {
        int length;
        if (var0 == null) {
            return null;
        }
        int length2 = var0.length;
        int length3 = (var1 == null || var1.equals("")) ? 0 : var1.length();
        if (length2 == 0) {
            length = 0;
        } else {
            String str = var0[0];
            length = ((str == null ? 16 : str.length()) + length3) * length2;
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i2 = 0; i2 < length2; i2++) {
            if (i2 > 0) {
                sb.append(var1);
            }
            String str2 = var0[i2];
            if (str2 != null) {
                sb.append(str2);
            }
        }
        return sb.toString();
    }

    public static int length(CharSequence s2) {
        if (s2 == null) {
            return 0;
        }
        return s2.length();
    }

    public static String lowerFirstLetter(String s2) {
        if (isEmpty(s2) || !Character.isUpperCase(s2.charAt(0))) {
            return s2;
        }
        return String.valueOf((char) (s2.charAt(0) + ' ')) + s2.substring(1);
    }

    public static String null2Length0(String s2) {
        return s2 == null ? "" : s2;
    }

    private static int oneCn2ASCII(String s2) throws UnsupportedEncodingException {
        if (s2.length() != 1) {
            return -1;
        }
        try {
            byte[] bytes = s2.getBytes(com.google.zxing.common.StringUtils.GB2312);
            if (bytes.length == 1) {
                return bytes[0];
            }
            if (bytes.length != 2) {
                throw new IllegalArgumentException("Illegal resource string");
            }
            return (((bytes[0] + 256) * 256) + (bytes[1] + 256)) - 65536;
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    private static String oneCn2PY(String s2) throws UnsupportedEncodingException {
        int iOneCn2ASCII = oneCn2ASCII(s2);
        if (iOneCn2ASCII == -1) {
            return null;
        }
        if (iOneCn2ASCII >= 0 && iOneCn2ASCII <= 127) {
            return String.valueOf((char) iOneCn2ASCII);
        }
        for (int length = pyValue.length - 1; length >= 0; length--) {
            if (pyValue[length] <= iOneCn2ASCII) {
                return pyStr[length];
            }
        }
        return null;
    }

    public static String reverse(String s2) {
        int length = length(s2);
        if (length <= 1) {
            return s2;
        }
        int i2 = length >> 1;
        char[] charArray = s2.toCharArray();
        for (int i3 = 0; i3 < i2; i3++) {
            char c3 = charArray[i3];
            int i4 = (length - i3) - 1;
            charArray[i3] = charArray[i4];
            charArray[i4] = c3;
        }
        return new String(charArray);
    }

    public static String toDBC(String s2) {
        if (isEmpty(s2)) {
            return s2;
        }
        char[] charArray = s2.toCharArray();
        int length = charArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            char c3 = charArray[i2];
            if (c3 == 12288) {
                charArray[i2] = ' ';
            } else if (65281 > c3 || c3 > 65374) {
                charArray[i2] = c3;
            } else {
                charArray[i2] = (char) (c3 - 65248);
            }
        }
        return new String(charArray);
    }

    public static String toSBC(String s2) {
        if (isEmpty(s2)) {
            return s2;
        }
        char[] charArray = s2.toCharArray();
        int length = charArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            char c3 = charArray[i2];
            if (c3 == ' ') {
                charArray[i2] = 12288;
            } else if ('!' > c3 || c3 > '~') {
                charArray[i2] = c3;
            } else {
                charArray[i2] = (char) (c3 + 65248);
            }
        }
        return new String(charArray);
    }

    public static String upperFirstLetter(String s2) {
        if (isEmpty(s2) || !Character.isLowerCase(s2.charAt(0))) {
            return s2;
        }
        return String.valueOf((char) (s2.charAt(0) - ' ')) + s2.substring(1);
    }
}
