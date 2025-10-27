package org.jsoup.parser;

import cn.hutool.core.text.CharPool;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;

/* loaded from: classes9.dex */
public class TokenQueue {
    private static final char ESC = '\\';
    private int pos = 0;
    private String queue;

    public TokenQueue(String str) {
        Validate.notNull(str);
        this.queue = str;
    }

    private int remainingLength() {
        return this.queue.length() - this.pos;
    }

    public static String unescape(String str) {
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i2 = 0;
        char c3 = 0;
        while (i2 < length) {
            char c4 = charArray[i2];
            if (c4 != '\\') {
                sb.append(c4);
            } else if (c3 != 0 && c3 == '\\') {
                sb.append(c4);
            }
            i2++;
            c3 = c4;
        }
        return sb.toString();
    }

    public void addFirst(Character ch) {
        addFirst(ch.toString());
    }

    public void advance() {
        if (isEmpty()) {
            return;
        }
        this.pos++;
    }

    public String chompBalanced(char c3, char c4) {
        char cCharValue = 0;
        int i2 = -1;
        int i3 = -1;
        int i4 = 0;
        while (!isEmpty()) {
            Character chValueOf = Character.valueOf(consume());
            if (cCharValue == 0 || cCharValue != '\\') {
                if (chValueOf.equals(Character.valueOf(c3))) {
                    i4++;
                    if (i2 == -1) {
                        i2 = this.pos;
                    }
                } else if (chValueOf.equals(Character.valueOf(c4))) {
                    i4--;
                }
            }
            if (i4 > 0 && cCharValue != 0) {
                i3 = this.pos;
            }
            cCharValue = chValueOf.charValue();
            if (i4 <= 0) {
                break;
            }
        }
        return i3 >= 0 ? this.queue.substring(i2, i3) : "";
    }

    public String chompTo(String str) {
        String strConsumeTo = consumeTo(str);
        matchChomp(str);
        return strConsumeTo;
    }

    public String chompToIgnoreCase(String str) {
        String strConsumeToIgnoreCase = consumeToIgnoreCase(str);
        matchChomp(str);
        return strConsumeToIgnoreCase;
    }

    public char consume() {
        String str = this.queue;
        int i2 = this.pos;
        this.pos = i2 + 1;
        return str.charAt(i2);
    }

    public String consumeAttributeKey() {
        int i2 = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny(CharPool.DASHED, '_', ':'))) {
            this.pos++;
        }
        return this.queue.substring(i2, this.pos);
    }

    public String consumeCssIdentifier() {
        int i2 = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny(CharPool.DASHED, '_'))) {
            this.pos++;
        }
        return this.queue.substring(i2, this.pos);
    }

    public String consumeElementSelector() {
        int i2 = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny('|', '_', CharPool.DASHED))) {
            this.pos++;
        }
        return this.queue.substring(i2, this.pos);
    }

    public String consumeTagName() {
        int i2 = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny(':', '_', CharPool.DASHED))) {
            this.pos++;
        }
        return this.queue.substring(i2, this.pos);
    }

    public String consumeTo(String str) {
        int iIndexOf = this.queue.indexOf(str, this.pos);
        if (iIndexOf == -1) {
            return remainder();
        }
        String strSubstring = this.queue.substring(this.pos, iIndexOf);
        this.pos += strSubstring.length();
        return strSubstring;
    }

    public String consumeToAny(String... strArr) {
        int i2 = this.pos;
        while (!isEmpty() && !matchesAny(strArr)) {
            this.pos++;
        }
        return this.queue.substring(i2, this.pos);
    }

    public String consumeToIgnoreCase(String str) {
        int i2 = this.pos;
        String strSubstring = str.substring(0, 1);
        boolean zEquals = strSubstring.toLowerCase().equals(strSubstring.toUpperCase());
        while (!isEmpty() && !matches(str)) {
            if (zEquals) {
                int iIndexOf = this.queue.indexOf(strSubstring, this.pos);
                int i3 = this.pos;
                int i4 = iIndexOf - i3;
                if (i4 == 0) {
                    this.pos = i3 + 1;
                } else if (i4 < 0) {
                    this.pos = this.queue.length();
                } else {
                    this.pos = i3 + i4;
                }
            } else {
                this.pos++;
            }
        }
        return this.queue.substring(i2, this.pos);
    }

    public boolean consumeWhitespace() {
        boolean z2 = false;
        while (matchesWhitespace()) {
            this.pos++;
            z2 = true;
        }
        return z2;
    }

    public String consumeWord() {
        int i2 = this.pos;
        while (matchesWord()) {
            this.pos++;
        }
        return this.queue.substring(i2, this.pos);
    }

    public boolean isEmpty() {
        return remainingLength() == 0;
    }

    public boolean matchChomp(String str) {
        if (!matches(str)) {
            return false;
        }
        this.pos += str.length();
        return true;
    }

    public boolean matches(String str) {
        return this.queue.regionMatches(true, this.pos, str, 0, str.length());
    }

    public boolean matchesAny(String... strArr) {
        for (String str : strArr) {
            if (matches(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesCS(String str) {
        return this.queue.startsWith(str, this.pos);
    }

    public boolean matchesStartTag() {
        return remainingLength() >= 2 && this.queue.charAt(this.pos) == '<' && Character.isLetter(this.queue.charAt(this.pos + 1));
    }

    public boolean matchesWhitespace() {
        return !isEmpty() && StringUtil.isWhitespace(this.queue.charAt(this.pos));
    }

    public boolean matchesWord() {
        return !isEmpty() && Character.isLetterOrDigit(this.queue.charAt(this.pos));
    }

    public char peek() {
        if (isEmpty()) {
            return (char) 0;
        }
        return this.queue.charAt(this.pos);
    }

    public String remainder() {
        String str = this.queue;
        String strSubstring = str.substring(this.pos, str.length());
        this.pos = this.queue.length();
        return strSubstring;
    }

    public String toString() {
        return this.queue.substring(this.pos);
    }

    public void addFirst(String str) {
        this.queue = str + this.queue.substring(this.pos);
        this.pos = 0;
    }

    public void consume(String str) {
        if (!matches(str)) {
            throw new IllegalStateException("Queue did not match expected sequence");
        }
        int length = str.length();
        if (length > remainingLength()) {
            throw new IllegalStateException("Queue not long enough to consume sequence");
        }
        this.pos += length;
    }

    public boolean matchesAny(char... cArr) {
        if (isEmpty()) {
            return false;
        }
        for (char c3 : cArr) {
            if (this.queue.charAt(this.pos) == c3) {
                return true;
            }
        }
        return false;
    }
}
