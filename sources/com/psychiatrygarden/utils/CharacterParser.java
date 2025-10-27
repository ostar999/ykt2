package com.psychiatrygarden.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.UnsupportedEncodingException;

/* loaded from: classes6.dex */
public class CharacterParser {
    private String resource;
    private static int[] pyvalue = {-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242, -20230, -20051, -20036, -20032, -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751, -19746, -19741, -19739, -19728, -19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281, -19275, -19270, -19263, -19261, -19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006, -19003, -18996, -18977, -18961, -18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696, -18526, -18518, -18501, -18490, -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183, -18181, -18012, -17997, -17988, -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721, -17703, -17701, -17697, -17692, -17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983, -16970, -16942, -16915, -16733, -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433, -16429, -16427, -16423, -16419, -16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171, -16169, -16158, -16155, -15959, -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661, -15659, -15652, -15640, -15631, -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369, -15363, -15362, -15183, -15180, -15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119, -15117, -15110, -15109, -14941, -14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889, -14882, -14873, -14871, -14857, -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384, -14379, -14368, -14355, -14353, -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112, -14109, -14099, -14097, -14094, -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878, -13870, -13859, -13847, -13831, -13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359, -13356, -13343, -13340, -13329, -13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060, -12888, -12875, -12871, -12860, -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556, -12359, -12346, -12320, -12300, -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781, -11604, -11589, -11536, -11358, -11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024, -11020, -11019, -11018, -11014, -10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329, -10328, -10322, -10315, -10309, -10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};
    public static String[] pystr = {am.av, "ai", com.alipay.sdk.sys.a.f3324i, "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin", "bing", "bo", AliyunLogKey.KEY_BUCKET, AliyunLogKey.KEY_CARRIER, "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang", "chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", SocializeProtocolConstants.PROTOCOL_KEY_DE, "deng", AppIconSetting.DEFAULT_LARGE_ICON, "dian", "diao", "die", "ding", "diu", "dong", "dou", com.umeng.analytics.pro.d.W, "duan", "dui", "dun", "duo", AliyunLogKey.KEY_EVENT, SocializeProtocolConstants.PROTOCOL_KEY_EN, "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng", AppIconSetting.LARGE_ICON_URL, "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", AliyunLogKey.KEY_LOG_VERSION, "luan", "lue", "lun", "luo", "ma", "mai", "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan", "nue", "nuo", "o", "ou", com.alipay.sdk.cons.b.f3225k, "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha", "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};
    private static CharacterParser characterParser = new CharacterParser();

    private int getChsAscii(String chs) throws UnsupportedEncodingException {
        byte b3 = 0;
        try {
            byte[] bytes = chs.getBytes("gb2312");
            if (bytes == null || bytes.length > 2 || bytes.length <= 0) {
                throw new RuntimeException("illegal resource string");
            }
            byte b4 = bytes.length == 1 ? bytes[0] : (byte) 0;
            try {
                if (bytes.length == 2) {
                    return (((bytes[0] + 256) * 256) + (bytes[1] + 256)) - 65536;
                }
                return b4;
            } catch (Exception e2) {
                e = e2;
                b3 = b4;
                System.out.println("ERROR:ChineseSpelling.class-getChsAscii(String chs)" + e);
                return b3;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static CharacterParser getInstance() {
        return characterParser;
    }

    public static SpannableStringBuilder getOmitColored(String filterStr, String content, int type, Context context) {
        String strSubstring;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String lowerCase = filterStr.toLowerCase();
        String lowerCase2 = content.toLowerCase();
        if (!lowerCase2.contains(lowerCase)) {
            return spannableStringBuilder;
        }
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
        if (type != 0) {
            if (type == 1) {
                spannableStringBuilder2.append("[链接]");
            } else if (type == 2) {
                spannableStringBuilder2.append("[文件]");
            }
        }
        int length = content.length();
        int iIndexOf = lowerCase2.indexOf(lowerCase);
        String strSubstring2 = content.substring(iIndexOf);
        int iIndexOf2 = 0;
        int length2 = strSubstring2 != null ? strSubstring2.length() : 0;
        if (length <= 12) {
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(content);
            spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#0099ff")), iIndexOf, filterStr.length() + iIndexOf, 17);
            return spannableStringBuilder2.append((CharSequence) spannableStringBuilder3);
        }
        if (filterStr.length() + iIndexOf < 12) {
            SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder(content.substring(0, 12));
            spannableStringBuilder4.setSpan(new ForegroundColorSpan(Color.parseColor("#0099ff")), iIndexOf, filterStr.length() + iIndexOf, 17);
            spannableStringBuilder4.append((CharSequence) "...");
            return spannableStringBuilder2.append((CharSequence) spannableStringBuilder4);
        }
        if (length2 < 12) {
            int i2 = length - 12;
            String strSubstring3 = content.substring(i2, length);
            int iIndexOf3 = lowerCase2.substring(i2, length).indexOf(lowerCase);
            SpannableStringBuilder spannableStringBuilder5 = new SpannableStringBuilder("...");
            SpannableStringBuilder spannableStringBuilder6 = new SpannableStringBuilder(strSubstring3);
            spannableStringBuilder6.setSpan(new ForegroundColorSpan(Color.parseColor("#0099ff")), iIndexOf3, filterStr.length() + iIndexOf3, 17);
            spannableStringBuilder5.append((CharSequence) spannableStringBuilder6);
            return spannableStringBuilder2.append((CharSequence) spannableStringBuilder5);
        }
        if (iIndexOf >= 5) {
            int i3 = iIndexOf - 5;
            int i4 = iIndexOf + 7;
            strSubstring = content.substring(i3, i4);
            String strSubstring4 = lowerCase2.substring(i3, i4);
            if (lowerCase.length() > 7) {
                lowerCase = lowerCase.substring(0, 7);
            }
            iIndexOf2 = strSubstring4.indexOf(lowerCase);
        } else {
            int i5 = iIndexOf + 12;
            strSubstring = content.substring(iIndexOf, i5);
            lowerCase2.substring(iIndexOf, i5).length();
            lowerCase.length();
        }
        SpannableStringBuilder spannableStringBuilder7 = new SpannableStringBuilder("...");
        SpannableStringBuilder spannableStringBuilder8 = new SpannableStringBuilder(strSubstring);
        spannableStringBuilder8.setSpan(new ForegroundColorSpan(Color.parseColor("#0099ff")), iIndexOf2, getSmallerLength(strSubstring.length(), filterStr.length() + iIndexOf2), 17);
        spannableStringBuilder7.append((CharSequence) spannableStringBuilder8);
        spannableStringBuilder7.append((CharSequence) "...");
        return spannableStringBuilder2.append((CharSequence) spannableStringBuilder7);
    }

    private static int getSmallerLength(int stringLength, int endIndex) {
        return stringLength > endIndex + 1 ? endIndex : stringLength;
    }

    public static SpannableStringBuilder getSpannable(String target, int start, int end) {
        SpannableStringBuilder spannableStringBuilder = TextUtils.isEmpty(target) ? new SpannableStringBuilder("") : new SpannableStringBuilder(target);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#F24E3F")), start, end, 17);
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder getSpannableColorSize(String target, int start, int end, String color) {
        SpannableStringBuilder spannableStringBuilder = TextUtils.isEmpty(target) ? new SpannableStringBuilder("") : new SpannableStringBuilder(target);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, 17);
        spannableStringBuilder.setSpan(new StyleSpan(1), start, end, 33);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(18, true), start, end, 17);
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder getSpannableColorSizew2(String target, int start, int end, String color, int size) {
        if (TextUtils.isEmpty(target)) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(target);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, 17);
        spannableStringBuilder.setSpan(new StyleSpan(1), start, end, 33);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(size, true), start, end, 17);
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder getSpannableText(String target, int start, int end) {
        SpannableStringBuilder spannableStringBuilder = TextUtils.isEmpty(target) ? new SpannableStringBuilder("") : new SpannableStringBuilder(target);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#212121")), start, end, 17);
        return spannableStringBuilder;
    }

    public String convert(String str) throws UnsupportedEncodingException {
        int chsAscii = getChsAscii(str);
        if (chsAscii > 0 && chsAscii < 160) {
            return String.valueOf((char) chsAscii);
        }
        for (int length = pyvalue.length - 1; length >= 0; length--) {
            if (pyvalue[length] <= chsAscii) {
                return pystr[length];
            }
        }
        return null;
    }

    public String getSpelling(String chs) {
        if (TextUtils.isEmpty(chs)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < chs.length()) {
            int i3 = i2 + 1;
            String strSubstring = chs.substring(i2, i3);
            if (strSubstring.getBytes().length >= 2 && (strSubstring = convert(strSubstring)) == null) {
                strSubstring = "unknown";
            }
            sb.append(strSubstring);
            i2 = i3;
        }
        return sb.toString();
    }

    public static SpannableStringBuilder getSpannableText(String target, int start, int end, String color) {
        SpannableStringBuilder spannableStringBuilder;
        if (TextUtils.isEmpty(target)) {
            spannableStringBuilder = new SpannableStringBuilder("");
        } else {
            spannableStringBuilder = new SpannableStringBuilder(target);
        }
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, 17);
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder getSpannableColorSize(String target, int start, int end, int start1, int end1, String color) {
        SpannableStringBuilder spannableStringBuilder;
        if (TextUtils.isEmpty(target)) {
            spannableStringBuilder = new SpannableStringBuilder("");
        } else {
            spannableStringBuilder = new SpannableStringBuilder(target);
        }
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start1, end1, 17);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(18, true), start, end, 17);
        return spannableStringBuilder;
    }
}
