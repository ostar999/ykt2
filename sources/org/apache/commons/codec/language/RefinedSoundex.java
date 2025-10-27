package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes9.dex */
public class RefinedSoundex implements StringEncoder {
    private final char[] soundexMapping;
    public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";
    private static final char[] US_ENGLISH_MAPPING = US_ENGLISH_MAPPING_STRING.toCharArray();
    public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();

    public RefinedSoundex() {
        this.soundexMapping = US_ENGLISH_MAPPING;
    }

    public int difference(String str, String str2) throws EncoderException {
        return SoundexUtils.difference(this, str, str2);
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return soundex((String) obj);
        }
        throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
    }

    public char getMappingCode(char c3) {
        if (Character.isLetter(c3)) {
            return this.soundexMapping[Character.toUpperCase(c3) - 'A'];
        }
        return (char) 0;
    }

    public String soundex(String str) {
        if (str == null) {
            return null;
        }
        String strClean = SoundexUtils.clean(str);
        if (strClean.length() == 0) {
            return strClean;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strClean.charAt(0));
        char c3 = '*';
        for (int i2 = 0; i2 < strClean.length(); i2++) {
            char mappingCode = getMappingCode(strClean.charAt(i2));
            if (mappingCode != c3) {
                if (mappingCode != 0) {
                    stringBuffer.append(mappingCode);
                }
                c3 = mappingCode;
            }
        }
        return stringBuffer.toString();
    }

    public RefinedSoundex(char[] cArr) {
        char[] cArr2 = new char[cArr.length];
        this.soundexMapping = cArr2;
        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return soundex(str);
    }

    public RefinedSoundex(String str) {
        this.soundexMapping = str.toCharArray();
    }
}
