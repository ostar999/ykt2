package org.apache.commons.codec.language;

import cn.hutool.core.text.StrPool;
import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes9.dex */
public class ColognePhonetic implements StringEncoder {
    private static final char[][] PREPROCESS_MAP = {new char[]{196, 'A'}, new char[]{220, 'U'}, new char[]{214, 'O'}, new char[]{223, 'S'}};

    public class CologneInputBuffer extends CologneBuffer {
        public CologneInputBuffer(char[] cArr) {
            super(cArr);
        }

        public void addLeft(char c3) {
            this.length++;
            this.data[getNextPos()] = c3;
        }

        @Override // org.apache.commons.codec.language.ColognePhonetic.CologneBuffer
        public char[] copyData(int i2, int i3) {
            char[] cArr = new char[i3];
            char[] cArr2 = this.data;
            System.arraycopy(cArr2, (cArr2.length - this.length) + i2, cArr, 0, i3);
            return cArr;
        }

        public char getNextChar() {
            return this.data[getNextPos()];
        }

        public int getNextPos() {
            return this.data.length - this.length;
        }

        public char removeNext() {
            this.length--;
            return getNextChar();
        }
    }

    public class CologneOutputBuffer extends CologneBuffer {
        public CologneOutputBuffer(int i2) {
            super(i2);
        }

        public void addRight(char c3) {
            char[] cArr = this.data;
            int i2 = this.length;
            cArr[i2] = c3;
            this.length = i2 + 1;
        }

        @Override // org.apache.commons.codec.language.ColognePhonetic.CologneBuffer
        public char[] copyData(int i2, int i3) {
            char[] cArr = new char[i3];
            System.arraycopy(this.data, i2, cArr, 0, i3);
            return cArr;
        }
    }

    private static boolean arrayContains(char[] cArr, char c3) {
        for (char c4 : cArr) {
            if (c4 == c3) {
                return true;
            }
        }
        return false;
    }

    private String preprocess(String str) {
        char[] charArray = str.toUpperCase(Locale.GERMAN).toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (charArray[i2] > 'Z') {
                char[][] cArr = PREPROCESS_MAP;
                int length = cArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        char[] cArr2 = cArr[i3];
                        if (charArray[i2] == cArr2[0]) {
                            charArray[i2] = cArr2[1];
                            break;
                        }
                        i3++;
                    }
                }
            }
        }
        return new String(charArray);
    }

    public String colognePhonetic(String str) {
        char c3;
        if (str == null) {
            return null;
        }
        String strPreprocess = preprocess(str);
        CologneOutputBuffer cologneOutputBuffer = new CologneOutputBuffer(strPreprocess.length() * 2);
        CologneInputBuffer cologneInputBuffer = new CologneInputBuffer(strPreprocess.toCharArray());
        int length = cologneInputBuffer.length();
        char c4 = '/';
        char c5 = '-';
        while (length > 0) {
            char cRemoveNext = cologneInputBuffer.removeNext();
            int length2 = cologneInputBuffer.length();
            char nextChar = length2 > 0 ? cologneInputBuffer.getNextChar() : '-';
            if (arrayContains(new char[]{'A', 'E', 'I', 'J', 'O', 'U', 'Y'}, cRemoveNext)) {
                c3 = '0';
            } else if (cRemoveNext == 'H' || cRemoveNext < 'A' || cRemoveNext > 'Z') {
                if (c4 == '/') {
                    length = length2;
                } else {
                    c3 = '-';
                }
            } else if (cRemoveNext == 'B' || (cRemoveNext == 'P' && nextChar != 'H')) {
                c3 = '1';
            } else if ((cRemoveNext == 'D' || cRemoveNext == 'T') && !arrayContains(new char[]{'S', 'C', 'Z'}, nextChar)) {
                c3 = '2';
            } else if (arrayContains(new char[]{'W', 'F', 'P', 'V'}, cRemoveNext)) {
                c3 = '3';
            } else {
                if (arrayContains(new char[]{'G', 'K', 'Q'}, cRemoveNext)) {
                    c3 = '4';
                } else {
                    if (cRemoveNext == 'X' && !arrayContains(new char[]{'C', 'K', 'Q'}, c5)) {
                        cologneInputBuffer.addLeft('S');
                        length2++;
                    } else if (cRemoveNext == 'S' || cRemoveNext == 'Z') {
                        c3 = '8';
                    } else {
                        if (cRemoveNext == 'C') {
                            if (c4 != '/' ? arrayContains(new char[]{'S', 'Z'}, c5) || !arrayContains(new char[]{'A', 'H', 'O', 'U', 'K', 'Q', 'X'}, nextChar) : !arrayContains(new char[]{'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X'}, nextChar)) {
                            }
                        } else if (!arrayContains(new char[]{'T', 'D', 'X'}, cRemoveNext)) {
                            c3 = cRemoveNext == 'R' ? '7' : cRemoveNext == 'L' ? '5' : (cRemoveNext == 'M' || cRemoveNext == 'N') ? '6' : cRemoveNext;
                        }
                        c3 = '8';
                    }
                    c3 = '4';
                }
                length = length2;
            }
            if (c3 != '-' && ((c4 != c3 && (c3 != '0' || c4 == '/')) || c3 < '0' || c3 > '8')) {
                cologneOutputBuffer.addRight(c3);
            }
            c4 = c3;
            c5 = cRemoveNext;
            length = length2;
        }
        return cologneOutputBuffer.toString();
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + obj.getClass().getName() + StrPool.DOT);
    }

    public boolean isEncodeEqual(String str, String str2) {
        return colognePhonetic(str).equals(colognePhonetic(str2));
    }

    public abstract class CologneBuffer {
        protected final char[] data;
        protected int length;

        public CologneBuffer(char[] cArr) {
            this.length = 0;
            this.data = cArr;
            this.length = cArr.length;
        }

        public abstract char[] copyData(int i2, int i3);

        public int length() {
            return this.length;
        }

        public String toString() {
            return new String(copyData(0, this.length));
        }

        public CologneBuffer(int i2) {
            this.length = 0;
            this.data = new char[i2];
            this.length = 0;
        }
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return colognePhonetic(str);
    }
}
