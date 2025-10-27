package cn.hutool.core.codec;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.tencent.connect.common.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes.dex */
public class Morse {
    private static final Map<Integer, String> ALPHABETS = new HashMap();
    private static final Map<String, Integer> DICTIONARIES = new HashMap();
    private final char dah;
    private final char dit;
    private final char split;

    static {
        registerMorse('A', HiAnalyticsConstant.KeyAndValue.NUMBER_01);
        registerMorse('B', Constants.DEFAULT_UIN);
        registerMorse('C', "1010");
        registerMorse('D', "100");
        registerMorse('E', "0");
        registerMorse('F', "0010");
        registerMorse('G', "110");
        registerMorse('H', "0000");
        registerMorse('I', TarConstants.VERSION_POSIX);
        registerMorse('J', "0111");
        registerMorse('K', "101");
        registerMorse('L', "0100");
        registerMorse('M', Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
        registerMorse('N', Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        registerMorse('O', "111");
        registerMorse('P', "0110");
        registerMorse('Q', "1101");
        registerMorse('R', "010");
        registerMorse('S', "000");
        registerMorse('T', "1");
        registerMorse('U', "001");
        registerMorse('V', "0001");
        registerMorse('W', "011");
        registerMorse('X', "1001");
        registerMorse('Y', "1011");
        registerMorse('Z', "1100");
        registerMorse('0', "11111");
        registerMorse('1', "01111");
        registerMorse('2', "00111");
        registerMorse('3', "00011");
        registerMorse('4', "00001");
        registerMorse('5', "00000");
        registerMorse('6', "10000");
        registerMorse('7', "11000");
        registerMorse('8', "11100");
        registerMorse('9', "11110");
        registerMorse('.', "010101");
        registerMorse(',', "110011");
        registerMorse('?', "001100");
        registerMorse(Character.valueOf(CharPool.SINGLE_QUOTE), "011110");
        registerMorse('!', "101011");
        registerMorse('/', "10010");
        registerMorse('(', "10110");
        registerMorse(')', "101101");
        registerMorse('&', "01000");
        registerMorse(':', "111000");
        registerMorse(';', "101010");
        registerMorse('=', "10001");
        registerMorse('+', "01010");
        registerMorse(Character.valueOf(CharPool.DASHED), "100001");
        registerMorse('_', "001101");
        registerMorse('\"', "010010");
        registerMorse(Character.valueOf(Typography.dollar), "0001001");
        registerMorse('@', "011010");
    }

    public Morse() {
        this('.', CharPool.DASHED, '/');
    }

    private static void registerMorse(Character ch, String str) {
        ALPHABETS.put(Integer.valueOf(ch.charValue()), str);
        DICTIONARIES.put(str, Integer.valueOf(ch.charValue()));
    }

    public String decode(String str) throws IllegalArgumentException {
        Assert.notNull(str, "Morse should not be null.", new Object[0]);
        char c3 = this.dit;
        char c4 = this.dah;
        char c5 = this.split;
        if (!CharSequenceUtil.containsOnly(str, c3, c4, c5)) {
            throw new IllegalArgumentException("Incorrect morse.");
        }
        List<String> listSplit = CharSequenceUtil.split((CharSequence) str, c5);
        StringBuilder sb = new StringBuilder();
        for (String str2 : listSplit) {
            if (!CharSequenceUtil.isEmpty(str2)) {
                String strReplace = str2.replace(c3, '0').replace(c4, '1');
                Integer numValueOf = DICTIONARIES.get(strReplace);
                if (numValueOf == null) {
                    numValueOf = Integer.valueOf(strReplace, 2);
                }
                sb.appendCodePoint(numValueOf.intValue());
            }
        }
        return sb.toString();
    }

    public String encode(String str) throws IllegalArgumentException {
        Assert.notNull(str, "Text should not be null.", new Object[0]);
        String upperCase = str.toUpperCase();
        StringBuilder sb = new StringBuilder();
        int iCodePointCount = upperCase.codePointCount(0, upperCase.length());
        for (int i2 = 0; i2 < iCodePointCount; i2++) {
            int iCodePointAt = upperCase.codePointAt(i2);
            String binaryString = ALPHABETS.get(Integer.valueOf(iCodePointAt));
            if (binaryString == null) {
                binaryString = Integer.toBinaryString(iCodePointAt);
            }
            sb.append(binaryString.replace('0', this.dit).replace('1', this.dah));
            sb.append(this.split);
        }
        return sb.toString();
    }

    public Morse(char c3, char c4, char c5) {
        this.dit = c3;
        this.dah = c4;
        this.split = c5;
    }
}
