package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Element;
import com.hp.hpl.sparta.ParseException;

/* loaded from: classes9.dex */
class GwoyeuRomatzyhTranslator {
    private static String[] tones = {"_I", "_II", "_III", "_IV", "_V"};

    public static String convertHanyuPinyinToGwoyeuRomatzyh(String str) {
        String strExtractPinyinString = TextHelper.extractPinyinString(str);
        String strExtractToneNumber = TextHelper.extractToneNumber(str);
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("//");
            stringBuffer.append(PinyinRomanizationType.HANYU_PINYIN.getTagName());
            stringBuffer.append("[text()='");
            stringBuffer.append(strExtractPinyinString);
            stringBuffer.append("']");
            Element elementXpathSelectElement = GwoyeuRomatzyhResource.getInstance().getPinyinToGwoyeuMappingDoc().xpathSelectElement(stringBuffer.toString());
            if (elementXpathSelectElement == null) {
                return null;
            }
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("../");
            stringBuffer2.append(PinyinRomanizationType.GWOYEU_ROMATZYH.getTagName());
            stringBuffer2.append(tones[Integer.parseInt(strExtractToneNumber) - 1]);
            stringBuffer2.append("/text()");
            return elementXpathSelectElement.xpathSelectString(stringBuffer2.toString());
        } catch (ParseException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
