package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Element;
import com.hp.hpl.sparta.ParseException;

/* loaded from: classes9.dex */
class PinyinRomanizationTranslator {
    public static String convertRomanizationSystem(String str, PinyinRomanizationType pinyinRomanizationType, PinyinRomanizationType pinyinRomanizationType2) {
        String strExtractPinyinString = TextHelper.extractPinyinString(str);
        String strExtractToneNumber = TextHelper.extractToneNumber(str);
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("//");
            stringBuffer.append(pinyinRomanizationType.getTagName());
            stringBuffer.append("[text()='");
            stringBuffer.append(strExtractPinyinString);
            stringBuffer.append("']");
            Element elementXpathSelectElement = PinyinRomanizationResource.getInstance().getPinyinMappingDoc().xpathSelectElement(stringBuffer.toString());
            if (elementXpathSelectElement == null) {
                return null;
            }
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("../");
            stringBuffer2.append(pinyinRomanizationType2.getTagName());
            stringBuffer2.append("/text()");
            String strXpathSelectString = elementXpathSelectElement.xpathSelectString(stringBuffer2.toString());
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append(strXpathSelectString);
            stringBuffer3.append(strExtractToneNumber);
            return stringBuffer3.toString();
        } catch (ParseException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
