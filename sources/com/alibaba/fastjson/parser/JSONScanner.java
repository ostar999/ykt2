package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

/* loaded from: classes2.dex */
public final class JSONScanner extends JSONLexerBase {
    private final int len;
    private final String text;

    public JSONScanner(String str) {
        this(str, JSON.DEFAULT_PARSER_FEATURE);
    }

    public static boolean charArrayCompare(String str, int i2, char[] cArr) {
        int length = cArr.length;
        if (length + i2 > str.length()) {
            return false;
        }
        for (int i3 = 0; i3 < length; i3++) {
            if (cArr[i3] != str.charAt(i2 + i3)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkDate(char c3, char c4, char c5, char c6, char c7, char c8, int i2, int i3) {
        if (c3 >= '0' && c3 <= '9' && c4 >= '0' && c4 <= '9' && c5 >= '0' && c5 <= '9' && c6 >= '0' && c6 <= '9') {
            if (c7 == '0') {
                if (c8 < '1' || c8 > '9') {
                    return false;
                }
            } else if (c7 != '1' || (c8 != '0' && c8 != '1' && c8 != '2')) {
                return false;
            }
            if (i2 == 48) {
                return i3 >= 49 && i3 <= 57;
            }
            if (i2 != 49 && i2 != 50) {
                return i2 == 51 && (i3 == 48 || i3 == 49);
            }
            if (i3 >= 48 && i3 <= 57) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTime(char c3, char c4, char c5, char c6, char c7, char c8) {
        if (c3 == '0') {
            if (c4 < '0' || c4 > '9') {
                return false;
            }
        } else {
            if (c3 != '1') {
                if (c3 == '2' && c4 >= '0' && c4 <= '4') {
                }
                return false;
            }
            if (c4 < '0' || c4 > '9') {
                return false;
            }
        }
        if (c5 < '0' || c5 > '5') {
            if (c5 != '6' || c6 != '0') {
                return false;
            }
        } else if (c6 < '0' || c6 > '9') {
            return false;
        }
        return (c7 < '0' || c7 > '5') ? c7 == '6' && c8 == '0' : c8 >= '0' && c8 <= '9';
    }

    private void setCalendar(char c3, char c4, char c5, char c6, char c7, char c8, char c9, char c10) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar = calendar;
        calendar.set(1, ((c3 - '0') * 1000) + ((c4 - '0') * 100) + ((c5 - '0') * 10) + (c6 - '0'));
        this.calendar.set(2, (((c7 - '0') * 10) + (c8 - '0')) - 1);
        this.calendar.set(5, ((c9 - '0') * 10) + (c10 - '0'));
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int i2, int i3, int i4, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, i2, i3, i4);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void arrayCopy(int i2, char[] cArr, int i3, int i4) {
        this.text.getChars(i2, i4 + i2, cArr, i3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        if (this.token != 26) {
            return !this.hasSpecial ? IOUtils.decodeBase64(this.text, this.np + 1, this.sp) : IOUtils.decodeBase64(new String(this.sbuf, 0, this.sp));
        }
        int i2 = this.np + 1;
        int i3 = this.sp;
        if (i3 % 2 != 0) {
            throw new JSONException("illegal state. " + i3);
        }
        int i4 = i3 / 2;
        byte[] bArr = new byte[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = (i5 * 2) + i2;
            char cCharAt = this.text.charAt(i6);
            char cCharAt2 = this.text.charAt(i6 + 1);
            char c3 = '0';
            int i7 = cCharAt - (cCharAt <= '9' ? '0' : '7');
            if (cCharAt2 > '9') {
                c3 = '7';
            }
            bArr[i5] = (byte) ((i7 << 4) | (cCharAt2 - c3));
        }
        return bArr;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int i2) {
        return i2 >= this.len ? JSONLexer.EOI : this.text.charAt(i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void copyTo(int i2, int i3, char[] cArr) {
        this.text.getChars(i2, i3 + i2, cArr, 0);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        char cCharAt = charAt((this.np + this.sp) - 1);
        int i2 = this.sp;
        if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B' || cCharAt == 'F' || cCharAt == 'D') {
            i2--;
        }
        int i3 = this.np;
        char[] cArr = this.sbuf;
        if (i2 < cArr.length) {
            this.text.getChars(i3, i3 + i2, cArr, 0);
            return new BigDecimal(this.sbuf, 0, i2);
        }
        char[] cArr2 = new char[i2];
        this.text.getChars(i3, i2 + i3, cArr2, 0);
        return new BigDecimal(cArr2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char c3, int i2) {
        return this.text.indexOf(c3, i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        StringBuilder sb = new StringBuilder();
        int i2 = 1;
        int i3 = 1;
        int i4 = 0;
        while (i4 < this.bp) {
            if (this.text.charAt(i4) == '\n') {
                i2++;
                i3 = 1;
            }
            i4++;
            i3++;
        }
        sb.append("pos ");
        sb.append(this.bp);
        sb.append(", line ");
        sb.append(i2);
        sb.append(", column ");
        sb.append(i3);
        if (this.text.length() < 65535) {
            sb.append(this.text);
        } else {
            sb.append(this.text.substring(0, 65535));
        }
        return sb.toString();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        int i2 = this.bp;
        int i3 = this.len;
        if (i2 != i3) {
            return this.ch == 26 && i2 + 1 >= i3;
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean matchField2(char[] cArr) {
        while (JSONLexerBase.isWhitespace(this.ch)) {
            next();
        }
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = this.bp + cArr.length;
        int i2 = length + 1;
        char cCharAt = this.text.charAt(length);
        while (JSONLexerBase.isWhitespace(cCharAt)) {
            cCharAt = this.text.charAt(i2);
            i2++;
        }
        if (cCharAt != ':') {
            this.matchStat = -2;
            return false;
        }
        this.bp = i2;
        this.ch = charAt(i2);
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int i2 = this.bp + 1;
        this.bp = i2;
        char cCharAt = i2 >= this.len ? JSONLexer.EOI : this.text.charAt(i2);
        this.ch = cCharAt;
        return cCharAt;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        char cCharAt = charAt((this.np + this.sp) - 1);
        int i2 = this.sp;
        if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B' || cCharAt == 'F' || cCharAt == 'D') {
            i2--;
        }
        return subString(this.np, i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanDate(char c3) {
        char cCharAt;
        long j2;
        Date date;
        int i2;
        boolean z2 = false;
        this.matchStat = 0;
        int i3 = this.bp;
        char c4 = this.ch;
        int i4 = i3 + 1;
        char cCharAt2 = charAt(i3);
        if (cCharAt2 == '\"') {
            int iIndexOf = indexOf('\"', i4);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            this.bp = i4;
            if (!scanISO8601DateIfMatch(false, iIndexOf - i4)) {
                this.bp = i3;
                this.ch = c4;
                this.matchStat = -1;
                return null;
            }
            date = this.calendar.getTime();
            cCharAt = charAt(iIndexOf + 1);
            this.bp = i3;
            while (cCharAt != ',' && cCharAt != ']') {
                if (!JSONLexerBase.isWhitespace(cCharAt)) {
                    this.bp = i3;
                    this.ch = c4;
                    this.matchStat = -1;
                    return null;
                }
                iIndexOf++;
                cCharAt = charAt(iIndexOf + 1);
            }
            this.bp = iIndexOf + 1;
            this.ch = cCharAt;
        } else {
            char c5 = '9';
            if (cCharAt2 != '-' && (cCharAt2 < '0' || cCharAt2 > '9')) {
                if (cCharAt2 == 'n') {
                    int i5 = i4 + 1;
                    if (charAt(i4) == 'u') {
                        int i6 = i5 + 1;
                        if (charAt(i5) == 'l') {
                            int i7 = i6 + 1;
                            if (charAt(i6) == 'l') {
                                cCharAt = charAt(i7);
                                this.bp = i7;
                                date = null;
                            }
                        }
                    }
                }
                this.bp = i3;
                this.ch = c4;
                this.matchStat = -1;
                return null;
            }
            if (cCharAt2 == '-') {
                cCharAt2 = charAt(i4);
                i4++;
                z2 = true;
            }
            if (cCharAt2 < '0' || cCharAt2 > '9') {
                cCharAt = cCharAt2;
                j2 = 0;
            } else {
                j2 = cCharAt2 - '0';
                while (true) {
                    i2 = i4 + 1;
                    cCharAt = charAt(i4);
                    if (cCharAt < '0' || cCharAt > c5) {
                        break;
                    }
                    j2 = (j2 * 10) + (cCharAt - '0');
                    i4 = i2;
                    c5 = '9';
                }
                if (cCharAt == ',' || cCharAt == ']') {
                    this.bp = i2 - 1;
                }
            }
            if (j2 < 0) {
                this.bp = i3;
                this.ch = c4;
                this.matchStat = -1;
                return null;
            }
            if (z2) {
                j2 = -j2;
            }
            date = new Date(j2);
        }
        if (cCharAt == ',') {
            int i8 = this.bp + 1;
            this.bp = i8;
            this.ch = charAt(i8);
            this.matchStat = 3;
            return date;
        }
        int i9 = this.bp + 1;
        this.bp = i9;
        char cCharAt3 = charAt(i9);
        if (cCharAt3 == ',') {
            this.token = 16;
            int i10 = this.bp + 1;
            this.bp = i10;
            this.ch = charAt(i10);
        } else if (cCharAt3 == ']') {
            this.token = 15;
            int i11 = this.bp + 1;
            this.bp = i11;
            this.ch = charAt(i11);
        } else if (cCharAt3 == '}') {
            this.token = 13;
            int i12 = this.bp + 1;
            this.bp = i12;
            this.ch = charAt(i12);
        } else {
            if (cCharAt3 != 26) {
                this.bp = i3;
                this.ch = c4;
                this.matchStat = -1;
                return null;
            }
            this.ch = JSONLexer.EOI;
            this.token = 20;
        }
        this.matchStat = 4;
        return date;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00c0  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x00c4 -> B:52:0x00b4). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public double scanDouble(char r22) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 391
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanDouble(char):double");
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x00f3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0104  */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean scanFieldBoolean(char[] r11) {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldBoolean(char[]):boolean");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanFieldDate(char[] cArr) {
        char cCharAt;
        long j2;
        char cCharAt2;
        Date date;
        int i2;
        boolean z2 = false;
        this.matchStat = 0;
        int i3 = this.bp;
        char c3 = this.ch;
        if (!charArrayCompare(this.text, i3, cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = this.bp + cArr.length;
        int i4 = length + 1;
        char cCharAt3 = charAt(length);
        if (cCharAt3 == '\"') {
            int iIndexOf = indexOf('\"', i4);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            this.bp = i4;
            if (!scanISO8601DateIfMatch(false, iIndexOf - i4)) {
                this.bp = i3;
                this.matchStat = -1;
                return null;
            }
            date = this.calendar.getTime();
            cCharAt2 = charAt(iIndexOf + 1);
            this.bp = i3;
            while (cCharAt2 != ',' && cCharAt2 != '}') {
                if (!JSONLexerBase.isWhitespace(cCharAt2)) {
                    this.matchStat = -1;
                    return null;
                }
                iIndexOf++;
                cCharAt2 = charAt(iIndexOf + 1);
            }
            this.bp = iIndexOf + 1;
            this.ch = cCharAt2;
        } else {
            char c4 = '9';
            char c5 = '0';
            if (cCharAt3 != '-' && (cCharAt3 < '0' || cCharAt3 > '9')) {
                this.matchStat = -1;
                return null;
            }
            if (cCharAt3 == '-') {
                cCharAt3 = charAt(i4);
                i4++;
                z2 = true;
            }
            if (cCharAt3 < '0' || cCharAt3 > '9') {
                cCharAt = cCharAt3;
                j2 = 0;
            } else {
                j2 = cCharAt3 - '0';
                while (true) {
                    i2 = i4 + 1;
                    cCharAt = charAt(i4);
                    if (cCharAt < c5 || cCharAt > c4) {
                        break;
                    }
                    j2 = (j2 * 10) + (cCharAt - '0');
                    i4 = i2;
                    c4 = '9';
                    c5 = '0';
                }
                if (cCharAt == ',' || cCharAt == '}') {
                    this.bp = i2 - 1;
                }
            }
            if (j2 < 0) {
                this.matchStat = -1;
                return null;
            }
            if (z2) {
                j2 = -j2;
            }
            cCharAt2 = cCharAt;
            date = new Date(j2);
        }
        if (cCharAt2 == ',') {
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
            this.matchStat = 3;
            this.token = 16;
            return date;
        }
        int i6 = this.bp + 1;
        this.bp = i6;
        char cCharAt4 = charAt(i6);
        if (cCharAt4 == ',') {
            this.token = 16;
            int i7 = this.bp + 1;
            this.bp = i7;
            this.ch = charAt(i7);
        } else if (cCharAt4 == ']') {
            this.token = 15;
            int i8 = this.bp + 1;
            this.bp = i8;
            this.ch = charAt(i8);
        } else if (cCharAt4 == '}') {
            this.token = 13;
            int i9 = this.bp + 1;
            this.bp = i9;
            this.ch = charAt(i9);
        } else {
            if (cCharAt4 != 26) {
                this.bp = i3;
                this.ch = c3;
                this.matchStat = -1;
                return null;
            }
            this.token = 20;
        }
        this.matchStat = 4;
        return date;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
    
        if (r15 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0067, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0069, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006a, code lost:
    
        if (r3 >= 0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006c, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x006e, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x006f, code lost:
    
        if (r6 == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0071, code lost:
    
        if (r15 == '\"') goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0073, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0075, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0076, code lost:
    
        r15 = r11 + 1;
        r4 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x007c, code lost:
    
        r11 = r15;
        r15 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0082, code lost:
    
        if (r15 == ',') goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0084, code lost:
    
        if (r15 != '}') goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008b, code lost:
    
        if (com.alibaba.fastjson.parser.JSONLexerBase.isWhitespace(r15) == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x008d, code lost:
    
        r15 = r11 + 1;
        r4 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0094, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0096, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0097, code lost:
    
        r11 = r11 - 1;
        r14.bp = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x009c, code lost:
    
        if (r15 != ',') goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009e, code lost:
    
        r11 = r11 + 1;
        r14.bp = r11;
        r14.ch = charAt(r11);
        r14.matchStat = 3;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ac, code lost:
    
        if (r7 == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00af, code lost:
    
        return -r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b0, code lost:
    
        if (r15 != '}') goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b2, code lost:
    
        r11 = r11 + 1;
        r14.bp = r11;
        r15 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b9, code lost:
    
        if (r15 != ',') goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00bb, code lost:
    
        r14.token = 16;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00cb, code lost:
    
        if (r15 != ']') goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00cd, code lost:
    
        r14.token = 15;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00dd, code lost:
    
        if (r15 != '}') goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00df, code lost:
    
        r14.token = 13;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00f1, code lost:
    
        if (r15 != 26) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00f3, code lost:
    
        r14.token = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00f7, code lost:
    
        r14.matchStat = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00ff, code lost:
    
        if (com.alibaba.fastjson.parser.JSONLexerBase.isWhitespace(r15) == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0101, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
        r15 = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x010b, code lost:
    
        r14.bp = r1;
        r14.ch = r2;
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0111, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0112, code lost:
    
        if (r7 == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0115, code lost:
    
        return -r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x007c, code lost:
    
        r11 = r15;
        r15 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:?, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:?, code lost:
    
        return r3;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int scanFieldInt(char[] r15) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldInt(char[]):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldLong(char[] cArr) {
        boolean z2;
        int i2;
        char cCharAt;
        this.matchStat = 0;
        int i3 = this.bp;
        char c3 = this.ch;
        if (!charArrayCompare(this.text, i3, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i4 = length + 1;
        char cCharAt2 = charAt(length);
        boolean z3 = cCharAt2 == '\"';
        if (z3) {
            cCharAt2 = charAt(i4);
            i4++;
        }
        if (cCharAt2 == '-') {
            z2 = true;
            cCharAt2 = charAt(i4);
            i4++;
        } else {
            z2 = false;
        }
        if (cCharAt2 >= '0') {
            char c4 = '9';
            if (cCharAt2 <= '9') {
                long j2 = cCharAt2 - '0';
                while (true) {
                    i2 = i4 + 1;
                    cCharAt = charAt(i4);
                    if (cCharAt < '0' || cCharAt > c4) {
                        break;
                    }
                    j2 = (j2 * 10) + (cCharAt - '0');
                    i4 = i2;
                    c4 = '9';
                }
                if (cCharAt == '.') {
                    this.matchStat = -1;
                    return 0L;
                }
                if (z3) {
                    if (cCharAt != '\"') {
                        this.matchStat = -1;
                        return 0L;
                    }
                    int i5 = i2 + 1;
                    char cCharAt3 = charAt(i2);
                    i2 = i5;
                    cCharAt = cCharAt3;
                }
                if (cCharAt == ',' || cCharAt == '}') {
                    this.bp = i2 - 1;
                }
                if (!(j2 >= 0 || (j2 == Long.MIN_VALUE && z2))) {
                    this.bp = i3;
                    this.ch = c3;
                    this.matchStat = -1;
                    return 0L;
                }
                while (cCharAt != ',') {
                    if (cCharAt == '}') {
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        char cCharAt4 = charAt(i6);
                        while (true) {
                            if (cCharAt4 == ',') {
                                this.token = 16;
                                int i7 = this.bp + 1;
                                this.bp = i7;
                                this.ch = charAt(i7);
                                break;
                            }
                            if (cCharAt4 == ']') {
                                this.token = 15;
                                int i8 = this.bp + 1;
                                this.bp = i8;
                                this.ch = charAt(i8);
                                break;
                            }
                            if (cCharAt4 == '}') {
                                this.token = 13;
                                int i9 = this.bp + 1;
                                this.bp = i9;
                                this.ch = charAt(i9);
                                break;
                            }
                            if (cCharAt4 == 26) {
                                this.token = 20;
                                break;
                            }
                            if (!JSONLexerBase.isWhitespace(cCharAt4)) {
                                this.bp = i3;
                                this.ch = c3;
                                this.matchStat = -1;
                                return 0L;
                            }
                            int i10 = this.bp + 1;
                            this.bp = i10;
                            cCharAt4 = charAt(i10);
                        }
                        this.matchStat = 4;
                        return z2 ? -j2 : j2;
                    }
                    if (!JSONLexerBase.isWhitespace(cCharAt)) {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.bp = i2;
                    int i11 = i2 + 1;
                    char cCharAt5 = charAt(i2);
                    i2 = i11;
                    cCharAt = cCharAt5;
                }
                int i12 = this.bp + 1;
                this.bp = i12;
                this.ch = charAt(i12);
                this.matchStat = 3;
                this.token = 16;
                return z2 ? -j2 : j2;
            }
        }
        this.bp = i3;
        this.ch = c3;
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldString(char[] cArr) {
        this.matchStat = 0;
        int i2 = this.bp;
        char c3 = this.ch;
        while (!charArrayCompare(this.text, this.bp, cArr)) {
            if (!JSONLexerBase.isWhitespace(this.ch)) {
                this.matchStat = -2;
                return stringDefaultValue();
            }
            next();
        }
        int length = this.bp + cArr.length;
        int i3 = length + 1;
        if (charAt(length) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int iIndexOf = indexOf('\"', i3);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        String strSubString = subString(i3, iIndexOf - i3);
        if (strSubString.indexOf(92) != -1) {
            while (true) {
                int i4 = 0;
                for (int i5 = iIndexOf - 1; i5 >= 0 && charAt(i5) == '\\'; i5--) {
                    i4++;
                }
                if (i4 % 2 == 0) {
                    break;
                }
                iIndexOf = indexOf('\"', iIndexOf + 1);
            }
            int i6 = this.bp;
            int length2 = iIndexOf - ((cArr.length + i6) + 1);
            strSubString = JSONLexerBase.readString(sub_chars(i6 + cArr.length + 1, length2), length2);
        }
        char cCharAt = charAt(iIndexOf + 1);
        while (cCharAt != ',' && cCharAt != '}') {
            if (!JSONLexerBase.isWhitespace(cCharAt)) {
                this.matchStat = -1;
                return stringDefaultValue();
            }
            iIndexOf++;
            cCharAt = charAt(iIndexOf + 1);
        }
        int i7 = iIndexOf + 1;
        this.bp = i7;
        this.ch = cCharAt;
        if (cCharAt == ',') {
            int i8 = i7 + 1;
            this.bp = i8;
            this.ch = charAt(i8);
            this.matchStat = 3;
            return strSubString;
        }
        int i9 = i7 + 1;
        this.bp = i9;
        char cCharAt2 = charAt(i9);
        if (cCharAt2 == ',') {
            this.token = 16;
            int i10 = this.bp + 1;
            this.bp = i10;
            this.ch = charAt(i10);
        } else if (cCharAt2 == ']') {
            this.token = 15;
            int i11 = this.bp + 1;
            this.bp = i11;
            this.ch = charAt(i11);
        } else if (cCharAt2 == '}') {
            this.token = 13;
            int i12 = this.bp + 1;
            this.bp = i12;
            this.ch = charAt(i12);
        } else {
            if (cCharAt2 != 26) {
                this.bp = i2;
                this.ch = c3;
                this.matchStat = -1;
                return stringDefaultValue();
            }
            this.token = 20;
        }
        this.matchStat = 4;
        return strSubString;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00db, code lost:
    
        if (r9 != ']') goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e1, code lost:
    
        if (r3.size() != 0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e3, code lost:
    
        r2 = r1 + 1;
        r1 = charAt(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ed, code lost:
    
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f0, code lost:
    
        return null;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r18, java.lang.Class<?> r19) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i2 = length + 1;
        if (charAt(length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j2 = -3750763034362895579L;
        while (true) {
            int i3 = i2 + 1;
            char cCharAt = charAt(i2);
            if (cCharAt == '\"') {
                this.bp = i3;
                char cCharAt2 = charAt(i3);
                this.ch = cCharAt2;
                while (cCharAt2 != ',') {
                    if (cCharAt2 == '}') {
                        next();
                        skipWhitespace();
                        char current = getCurrent();
                        if (current == ',') {
                            this.token = 16;
                            int i4 = this.bp + 1;
                            this.bp = i4;
                            this.ch = charAt(i4);
                        } else if (current == ']') {
                            this.token = 15;
                            int i5 = this.bp + 1;
                            this.bp = i5;
                            this.ch = charAt(i5);
                        } else if (current == '}') {
                            this.token = 13;
                            int i6 = this.bp + 1;
                            this.bp = i6;
                            this.ch = charAt(i6);
                        } else {
                            if (current != 26) {
                                this.matchStat = -1;
                                return 0L;
                            }
                            this.token = 20;
                        }
                        this.matchStat = 4;
                        return j2;
                    }
                    if (!JSONLexerBase.isWhitespace(cCharAt2)) {
                        this.matchStat = -1;
                        return 0L;
                    }
                    int i7 = this.bp + 1;
                    this.bp = i7;
                    cCharAt2 = charAt(i7);
                }
                int i8 = this.bp + 1;
                this.bp = i8;
                this.ch = charAt(i8);
                this.matchStat = 3;
                return j2;
            }
            if (i3 > this.len) {
                this.matchStat = -1;
                return 0L;
            }
            j2 = (j2 ^ cCharAt) * 1099511628211L;
            i2 = i3;
        }
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0080, code lost:
    
        if (r3 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0082, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0084, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0085, code lost:
    
        if (r7 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0087, code lost:
    
        if (r3 == '\"') goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0089, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008b, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x008c, code lost:
    
        r3 = charAt(r13);
        r13 = r13 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0093, code lost:
    
        if (r4 >= 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0095, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0097, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x009a, code lost:
    
        if (r3 != r17) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009c, code lost:
    
        r16.bp = r13;
        r16.ch = charAt(r13);
        r16.matchStat = 3;
        r16.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a9, code lost:
    
        if (r8 == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ac, code lost:
    
        return -r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b1, code lost:
    
        if (com.alibaba.fastjson.parser.JSONLexerBase.isWhitespace(r3) == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b3, code lost:
    
        r3 = charAt(r13);
        r13 = r13 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00bb, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00bd, code lost:
    
        if (r8 == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00c0, code lost:
    
        return -r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:?, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:?, code lost:
    
        return r4;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int scanInt(char r17) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanInt(char):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char c3) {
        int i2;
        char cCharAt;
        boolean z2 = false;
        this.matchStat = 0;
        int i3 = this.bp;
        int i4 = i3 + 1;
        char cCharAt2 = charAt(i3);
        boolean z3 = cCharAt2 == '\"';
        if (z3) {
            int i5 = i4 + 1;
            char cCharAt3 = charAt(i4);
            i4 = i5;
            cCharAt2 = cCharAt3;
        }
        boolean z4 = cCharAt2 == '-';
        if (z4) {
            int i6 = i4 + 1;
            char cCharAt4 = charAt(i4);
            i4 = i6;
            cCharAt2 = cCharAt4;
        }
        char c4 = '0';
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            long j2 = cCharAt2 - '0';
            while (true) {
                i2 = i4 + 1;
                cCharAt = charAt(i4);
                if (cCharAt < c4 || cCharAt > '9') {
                    break;
                }
                j2 = (j2 * 10) + (cCharAt - '0');
                i4 = i2;
                c4 = '0';
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (z3) {
                if (cCharAt != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                cCharAt = charAt(i2);
                i2++;
            }
            if (j2 >= 0 || (j2 == Long.MIN_VALUE && z4)) {
                z2 = true;
            }
            if (!z2) {
                this.matchStat = -1;
                return 0L;
            }
            while (cCharAt != c3) {
                if (!JSONLexerBase.isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return j2;
                }
                cCharAt = charAt(i2);
                i2++;
            }
            this.bp = i2;
            this.ch = charAt(i2);
            this.matchStat = 3;
            this.token = 16;
            return z4 ? -j2 : j2;
        }
        if (cCharAt2 == 'n') {
            int i7 = i4 + 1;
            if (charAt(i4) == 'u') {
                int i8 = i7 + 1;
                if (charAt(i7) == 'l') {
                    int i9 = i8 + 1;
                    if (charAt(i8) == 'l') {
                        this.matchStat = 5;
                        int i10 = i9 + 1;
                        char cCharAt5 = charAt(i9);
                        if (z3 && cCharAt5 == '\"') {
                            int i11 = i10 + 1;
                            char cCharAt6 = charAt(i10);
                            i10 = i11;
                            cCharAt5 = cCharAt6;
                        }
                        while (cCharAt5 != ',') {
                            if (cCharAt5 == ']') {
                                this.bp = i10;
                                this.ch = charAt(i10);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0L;
                            }
                            if (!JSONLexerBase.isWhitespace(cCharAt5)) {
                                this.matchStat = -1;
                                return 0L;
                            }
                            int i12 = i10 + 1;
                            char cCharAt7 = charAt(i10);
                            i10 = i12;
                            cCharAt5 = cCharAt7;
                        }
                        this.bp = i10;
                        this.ch = charAt(i10);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0L;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String scanTypeName(SymbolTable symbolTable) {
        int iIndexOf;
        if (!this.text.startsWith("\"@type\":\"", this.bp) || (iIndexOf = this.text.indexOf(34, this.bp + 9)) == -1) {
            return null;
        }
        int i2 = this.bp + 9;
        this.bp = i2;
        int iCharAt = 0;
        while (i2 < iIndexOf) {
            iCharAt = (iCharAt * 31) + this.text.charAt(i2);
            i2++;
        }
        int i3 = this.bp;
        String strAddSymbol = addSymbol(i3, iIndexOf - i3, iCharAt, symbolTable);
        char cCharAt = this.text.charAt(iIndexOf + 1);
        if (cCharAt != ',' && cCharAt != ']') {
            return null;
        }
        int i4 = iIndexOf + 2;
        this.bp = i4;
        this.ch = this.text.charAt(i4);
        return strAddSymbol;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0084, code lost:
    
        if (r3 == false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x008c, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illegal json.");
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean seekArrayToItem(int r11) {
        /*
            r10 = this;
            if (r11 < 0) goto Lb4
            int r0 = r10.token
            r1 = 20
            r2 = 0
            if (r0 != r1) goto La
            return r2
        La:
            r1 = 14
            if (r0 != r1) goto Lae
            r0 = r2
        Lf:
            r3 = 1
            if (r0 >= r11) goto Laa
            r10.skipWhitespace()
            char r4 = r10.ch
            r5 = 34
            r6 = 16
            java.lang.String r7 = "illegal json."
            r8 = 93
            r9 = 44
            if (r4 == r5) goto L8d
            r5 = 39
            if (r4 != r5) goto L28
            goto L8d
        L28:
            r5 = 123(0x7b, float:1.72E-43)
            if (r4 != r5) goto L37
            r10.next()
            r3 = 12
            r10.token = r3
            r10.skipObject(r2)
            goto L43
        L37:
            r5 = 91
            if (r4 != r5) goto L53
            r10.next()
            r10.token = r1
            r10.skipArray(r2)
        L43:
            int r3 = r10.token
            if (r3 != r6) goto L48
            goto L97
        L48:
            r11 = 15
            if (r3 != r11) goto L4d
            return r2
        L4d:
            java.lang.UnsupportedOperationException r11 = new java.lang.UnsupportedOperationException
            r11.<init>()
            throw r11
        L53:
            int r4 = r10.bp
            int r4 = r4 + r3
        L56:
            java.lang.String r5 = r10.text
            int r5 = r5.length()
            if (r4 >= r5) goto L83
            java.lang.String r5 = r10.text
            char r5 = r5.charAt(r4)
            if (r5 != r9) goto L71
            int r4 = r4 + 1
            r10.bp = r4
            char r4 = r10.charAt(r4)
            r10.ch = r4
            goto L84
        L71:
            if (r5 != r8) goto L80
            int r4 = r4 + r3
            r10.bp = r4
            char r11 = r10.charAt(r4)
            r10.ch = r11
            r10.nextToken()
            return r2
        L80:
            int r4 = r4 + 1
            goto L56
        L83:
            r3 = r2
        L84:
            if (r3 == 0) goto L87
            goto L97
        L87:
            com.alibaba.fastjson.JSONException r11 = new com.alibaba.fastjson.JSONException
            r11.<init>(r7)
            throw r11
        L8d:
            r10.skipString()
            char r3 = r10.ch
            if (r3 != r9) goto L9b
            r10.next()
        L97:
            int r0 = r0 + 1
            goto Lf
        L9b:
            if (r3 != r8) goto La4
            r10.next()
            r10.nextToken(r6)
            return r2
        La4:
            com.alibaba.fastjson.JSONException r11 = new com.alibaba.fastjson.JSONException
            r11.<init>(r7)
            throw r11
        Laa:
            r10.nextToken()
            return r3
        Lae:
            java.lang.UnsupportedOperationException r11 = new java.lang.UnsupportedOperationException
            r11.<init>()
            throw r11
        Lb4:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "index must > 0, but "
            r1.append(r2)
            r1.append(r11)
            java.lang.String r11 = r1.toString()
            r0.<init>(r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.seekArrayToItem(int):boolean");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int seekObjectToField(long j2, boolean z2) {
        char c3;
        int i2 = this.token;
        int i3 = -1;
        if (i2 == 20) {
            return -1;
        }
        if (i2 != 13) {
            int i4 = 15;
            if (i2 != 15) {
                int i5 = 16;
                if (i2 != 12 && i2 != 16) {
                    throw new UnsupportedOperationException(JSONToken.name(this.token));
                }
                while (true) {
                    char c4 = this.ch;
                    if (c4 == '}') {
                        next();
                        nextToken();
                        return i3;
                    }
                    if (c4 == 26) {
                        return i3;
                    }
                    if (c4 != '\"') {
                        skipWhitespace();
                    }
                    if (this.ch != '\"') {
                        throw new UnsupportedOperationException();
                    }
                    int i6 = this.bp + 1;
                    long j3 = -3750763034362895579L;
                    while (true) {
                        if (i6 >= this.text.length()) {
                            break;
                        }
                        char cCharAt = this.text.charAt(i6);
                        if (cCharAt == '\\') {
                            i6++;
                            if (i6 == this.text.length()) {
                                throw new JSONException("unclosed str, " + info());
                            }
                            cCharAt = this.text.charAt(i6);
                        }
                        if (cCharAt == '\"') {
                            int i7 = i6 + 1;
                            this.bp = i7;
                            this.ch = i7 >= this.text.length() ? (char) 26 : this.text.charAt(this.bp);
                        } else {
                            j3 = (j3 ^ cCharAt) * 1099511628211L;
                            i6++;
                        }
                    }
                    if (j3 == j2) {
                        if (this.ch != ':') {
                            skipWhitespace();
                        }
                        if (this.ch != ':') {
                            return 3;
                        }
                        int i8 = this.bp + 1;
                        this.bp = i8;
                        char cCharAt2 = i8 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i8);
                        this.ch = cCharAt2;
                        if (cCharAt2 == ',') {
                            int i9 = this.bp + 1;
                            this.bp = i9;
                            this.ch = i9 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i9);
                            this.token = i5;
                            return 3;
                        }
                        if (cCharAt2 == ']') {
                            int i10 = this.bp + 1;
                            this.bp = i10;
                            this.ch = i10 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i10);
                            this.token = i4;
                            return 3;
                        }
                        if (cCharAt2 == '}') {
                            int i11 = this.bp + 1;
                            this.bp = i11;
                            this.ch = i11 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i11);
                            this.token = 13;
                            return 3;
                        }
                        if (cCharAt2 < '0' || cCharAt2 > '9') {
                            nextToken(2);
                            return 3;
                        }
                        this.sp = 0;
                        this.pos = this.bp;
                        scanNumber();
                        return 3;
                    }
                    if (this.ch != ':') {
                        skipWhitespace();
                    }
                    if (this.ch != ':') {
                        throw new JSONException("illegal json, " + info());
                    }
                    int i12 = this.bp + 1;
                    this.bp = i12;
                    char cCharAt3 = i12 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i12);
                    this.ch = cCharAt3;
                    if (cCharAt3 != '\"' && cCharAt3 != '\'' && cCharAt3 != '{' && cCharAt3 != '[' && cCharAt3 != '0' && cCharAt3 != '1' && cCharAt3 != '2' && cCharAt3 != '3' && cCharAt3 != '4' && cCharAt3 != '5' && cCharAt3 != '6' && cCharAt3 != '7' && cCharAt3 != '8' && cCharAt3 != '9' && cCharAt3 != '+' && cCharAt3 != '-') {
                        skipWhitespace();
                    }
                    char c5 = this.ch;
                    if (c5 == '-' || c5 == '+' || (c5 >= '0' && c5 <= '9')) {
                        next();
                        while (true) {
                            c3 = this.ch;
                            if (c3 < '0' || c3 > '9') {
                                break;
                            }
                            next();
                        }
                        if (c3 == '.') {
                            next();
                            while (true) {
                                char c6 = this.ch;
                                if (c6 < '0' || c6 > '9') {
                                    break;
                                }
                                next();
                            }
                        }
                        char c7 = this.ch;
                        if (c7 == 'E' || c7 == 'e') {
                            next();
                            char c8 = this.ch;
                            if (c8 == '-' || c8 == '+') {
                                next();
                            }
                            while (true) {
                                char c9 = this.ch;
                                if (c9 < '0' || c9 > '9') {
                                    break;
                                }
                                next();
                            }
                        }
                        if (this.ch != ',') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c5 == '\"') {
                        skipString();
                        char c10 = this.ch;
                        if (c10 != ',' && c10 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c5 == 't') {
                        next();
                        if (this.ch == 'r') {
                            next();
                            if (this.ch == 'u') {
                                next();
                                if (this.ch == 'e') {
                                    next();
                                }
                            }
                        }
                        char c11 = this.ch;
                        if (c11 != ',' && c11 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c5 == 'n') {
                        next();
                        if (this.ch == 'u') {
                            next();
                            if (this.ch == 'l') {
                                next();
                                if (this.ch == 'l') {
                                    next();
                                }
                            }
                        }
                        char c12 = this.ch;
                        if (c12 != ',' && c12 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c5 == 'f') {
                        next();
                        if (this.ch == 'a') {
                            next();
                            if (this.ch == 'l') {
                                next();
                                if (this.ch == 's') {
                                    next();
                                    if (this.ch == 'e') {
                                        next();
                                    }
                                }
                            }
                        }
                        char c13 = this.ch;
                        if (c13 != ',' && c13 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c5 == '{') {
                        int i13 = this.bp + 1;
                        this.bp = i13;
                        this.ch = i13 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i13);
                        if (z2) {
                            this.token = 12;
                            return 1;
                        }
                        skipObject(false);
                        if (this.token == 13) {
                            return -1;
                        }
                    } else {
                        if (c5 != '[') {
                            throw new UnsupportedOperationException();
                        }
                        next();
                        if (z2) {
                            this.token = 14;
                            return 2;
                        }
                        skipArray(false);
                        if (this.token == 13) {
                            return -1;
                        }
                    }
                    i3 = -1;
                    i4 = 15;
                    i5 = 16;
                }
            }
        }
        nextToken();
        return -1;
    }

    public void setTime(char c3, char c4, char c5, char c6, char c7, char c8) {
        this.calendar.set(11, ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(12, ((c5 - '0') * 10) + (c6 - '0'));
        this.calendar.set(13, ((c7 - '0') * 10) + (c8 - '0'));
    }

    public void setTimeZone(char c3, char c4, char c5) {
        setTimeZone(c3, c4, c5, '0', '0');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipArray() {
        skipArray(false);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject() {
        skipObject(false);
    }

    public final void skipString() {
        if (this.ch != '\"') {
            throw new UnsupportedOperationException();
        }
        int i2 = this.bp;
        while (true) {
            i2++;
            if (i2 >= this.text.length()) {
                throw new JSONException("unclosed str");
            }
            char cCharAt = this.text.charAt(i2);
            if (cCharAt == '\\') {
                if (i2 < this.len - 1) {
                    i2++;
                }
            } else if (cCharAt == '\"') {
                String str = this.text;
                int i3 = i2 + 1;
                this.bp = i3;
                this.ch = str.charAt(i3);
                return;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        return !this.hasSpecial ? subString(this.np + 1, this.sp) : new String(this.sbuf, 0, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int i2, int i3) {
        if (!ASMUtils.IS_ANDROID) {
            return this.text.substring(i2, i3 + i2);
        }
        char[] cArr = this.sbuf;
        if (i3 < cArr.length) {
            this.text.getChars(i2, i2 + i3, cArr, 0);
            return new String(this.sbuf, 0, i3);
        }
        char[] cArr2 = new char[i3];
        this.text.getChars(i2, i3 + i2, cArr2, 0);
        return new String(cArr2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char[] sub_chars(int i2, int i3) {
        if (ASMUtils.IS_ANDROID) {
            char[] cArr = this.sbuf;
            if (i3 < cArr.length) {
                this.text.getChars(i2, i3 + i2, cArr, 0);
                return this.sbuf;
            }
        }
        char[] cArr2 = new char[i3];
        this.text.getChars(i2, i3 + i2, cArr2, 0);
        return cArr2;
    }

    public JSONScanner(String str, int i2) {
        super(i2);
        this.text = str;
        this.len = str.length();
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    public boolean scanISO8601DateIfMatch(boolean z2) {
        return scanISO8601DateIfMatch(z2, this.len - this.bp);
    }

    public void setTimeZone(char c3, char c4, char c5, char c6, char c7) {
        int i2 = ((((c4 - '0') * 10) + (c5 - '0')) * 3600 * 1000) + ((((c6 - '0') * 10) + (c7 - '0')) * 60 * 1000);
        if (c3 == '-') {
            i2 = -i2;
        }
        if (this.calendar.getTimeZone().getRawOffset() != i2) {
            this.calendar.setTimeZone(new SimpleTimeZone(i2, Integer.toString(i2)));
        }
    }

    public final void skipArray(boolean z2) {
        int i2 = this.bp;
        boolean z3 = false;
        int i3 = 0;
        while (i2 < this.text.length()) {
            char cCharAt = this.text.charAt(i2);
            if (cCharAt == '\\') {
                if (i2 >= this.len - 1) {
                    this.ch = cCharAt;
                    this.bp = i2;
                    throw new JSONException("illegal str, " + info());
                }
                i2++;
            } else if (cCharAt == '\"') {
                z3 = !z3;
            } else if (cCharAt != '[') {
                char cCharAt2 = JSONLexer.EOI;
                if (cCharAt == '{' && z2) {
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    if (i4 < this.text.length()) {
                        cCharAt2 = this.text.charAt(i4);
                    }
                    this.ch = cCharAt2;
                    skipObject(z2);
                } else if (cCharAt == ']' && !z3 && i3 - 1 == -1) {
                    int i5 = i2 + 1;
                    this.bp = i5;
                    if (i5 == this.text.length()) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    } else {
                        this.ch = this.text.charAt(this.bp);
                        nextToken(16);
                        return;
                    }
                }
            } else if (!z3) {
                i3++;
            }
            i2++;
        }
        if (i2 != this.text.length()) {
            return;
        }
        throw new JSONException("illegal str, " + info());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject(boolean z2) {
        int i2 = this.bp;
        boolean z3 = false;
        int i3 = 0;
        while (i2 < this.text.length()) {
            char cCharAt = this.text.charAt(i2);
            if (cCharAt == '\\') {
                if (i2 >= this.len - 1) {
                    this.ch = cCharAt;
                    this.bp = i2;
                    throw new JSONException("illegal str, " + info());
                }
                i2++;
            } else if (cCharAt == '\"') {
                z3 = !z3;
            } else if (cCharAt != '{') {
                if (cCharAt == '}' && !z3 && i3 - 1 == -1) {
                    int i4 = i2 + 1;
                    this.bp = i4;
                    int length = this.text.length();
                    char cCharAt2 = JSONLexer.EOI;
                    if (i4 == length) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    }
                    char cCharAt3 = this.text.charAt(this.bp);
                    this.ch = cCharAt3;
                    if (cCharAt3 == ',') {
                        this.token = 16;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        if (i5 < this.text.length()) {
                            cCharAt2 = this.text.charAt(i5);
                        }
                        this.ch = cCharAt2;
                        return;
                    }
                    if (cCharAt3 == '}') {
                        this.token = 13;
                        next();
                        return;
                    } else if (cCharAt3 != ']') {
                        nextToken(16);
                        return;
                    } else {
                        this.token = 15;
                        next();
                        return;
                    }
                }
            } else if (!z3) {
                i3++;
            }
            i2++;
        }
        for (int i6 = 0; i6 < this.bp; i6++) {
            if (i6 < this.text.length() && this.text.charAt(i6) == ' ') {
                i2++;
            }
        }
        if (i2 != this.text.length()) {
            return;
        }
        throw new JSONException("illegal str, " + info());
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x0211 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0213  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean scanISO8601DateIfMatch(boolean r34, int r35) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 1805
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanISO8601DateIfMatch(boolean, int):boolean");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] cArr) {
        return charArrayCompare(this.text, this.bp, cArr);
    }

    public JSONScanner(char[] cArr, int i2) {
        this(cArr, i2, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] cArr, int i2, int i3) {
        this(new String(cArr, 0, i2), i3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String[] scanFieldStringArray(char[] cArr, int i2, SymbolTable symbolTable) throws NumberFormatException {
        int i3;
        char cCharAt;
        int i4 = this.bp;
        char c3 = this.ch;
        while (JSONLexerBase.isWhitespace(this.ch)) {
            next();
        }
        if (cArr != null) {
            this.matchStat = 0;
            if (!charArrayCompare(cArr)) {
                this.matchStat = -2;
                return null;
            }
            int length = this.bp + cArr.length;
            int i5 = length + 1;
            char cCharAt2 = this.text.charAt(length);
            while (JSONLexerBase.isWhitespace(cCharAt2)) {
                cCharAt2 = this.text.charAt(i5);
                i5++;
            }
            if (cCharAt2 == ':') {
                i3 = i5 + 1;
                cCharAt = this.text.charAt(i5);
                while (JSONLexerBase.isWhitespace(cCharAt)) {
                    cCharAt = this.text.charAt(i3);
                    i3++;
                }
            } else {
                this.matchStat = -1;
                return null;
            }
        } else {
            i3 = this.bp + 1;
            cCharAt = this.ch;
        }
        if (cCharAt == '[') {
            this.bp = i3;
            this.ch = this.text.charAt(i3);
            String[] strArr = i2 >= 0 ? new String[i2] : new String[4];
            int i6 = 0;
            while (true) {
                if (JSONLexerBase.isWhitespace(this.ch)) {
                    next();
                } else {
                    if (this.ch != '\"') {
                        this.bp = i4;
                        this.ch = c3;
                        this.matchStat = -1;
                        return null;
                    }
                    String strScanSymbol = scanSymbol(symbolTable, '\"');
                    if (i6 == strArr.length) {
                        String[] strArr2 = new String[strArr.length + (strArr.length >> 1) + 1];
                        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                        strArr = strArr2;
                    }
                    int i7 = i6 + 1;
                    strArr[i6] = strScanSymbol;
                    while (JSONLexerBase.isWhitespace(this.ch)) {
                        next();
                    }
                    if (this.ch == ',') {
                        next();
                        i6 = i7;
                    } else {
                        if (strArr.length != i7) {
                            String[] strArr3 = new String[i7];
                            System.arraycopy(strArr, 0, strArr3, 0, i7);
                            strArr = strArr3;
                        }
                        while (JSONLexerBase.isWhitespace(this.ch)) {
                            next();
                        }
                        if (this.ch == ']') {
                            next();
                            return strArr;
                        }
                        this.bp = i4;
                        this.ch = c3;
                        this.matchStat = -1;
                        return null;
                    }
                }
            }
        } else {
            if (cCharAt == 'n' && this.text.startsWith("ull", this.bp + 1)) {
                int i8 = this.bp + 4;
                this.bp = i8;
                this.ch = this.text.charAt(i8);
                return null;
            }
            this.matchStat = -1;
            return null;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int seekObjectToField(long[] jArr) {
        char c3;
        int i2 = this.token;
        if (i2 != 12 && i2 != 16) {
            throw new UnsupportedOperationException();
        }
        while (true) {
            char c4 = this.ch;
            if (c4 == '}') {
                next();
                nextToken();
                this.matchStat = -1;
                return -1;
            }
            char cCharAt = JSONLexer.EOI;
            if (c4 == 26) {
                this.matchStat = -1;
                return -1;
            }
            if (c4 != '\"') {
                skipWhitespace();
            }
            if (this.ch == '\"') {
                int i3 = this.bp + 1;
                long j2 = -3750763034362895579L;
                while (true) {
                    if (i3 >= this.text.length()) {
                        break;
                    }
                    char cCharAt2 = this.text.charAt(i3);
                    if (cCharAt2 == '\\') {
                        i3++;
                        if (i3 != this.text.length()) {
                            cCharAt2 = this.text.charAt(i3);
                        } else {
                            throw new JSONException("unclosed str, " + info());
                        }
                    }
                    if (cCharAt2 == '\"') {
                        int i4 = i3 + 1;
                        this.bp = i4;
                        this.ch = i4 >= this.text.length() ? (char) 26 : this.text.charAt(this.bp);
                    } else {
                        j2 = (j2 ^ cCharAt2) * 1099511628211L;
                        i3++;
                    }
                }
                int i5 = 0;
                while (true) {
                    if (i5 >= jArr.length) {
                        i5 = -1;
                        break;
                    }
                    if (j2 == jArr[i5]) {
                        break;
                    }
                    i5++;
                }
                if (i5 != -1) {
                    if (this.ch != ':') {
                        skipWhitespace();
                    }
                    if (this.ch == ':') {
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        char cCharAt3 = i6 >= this.text.length() ? (char) 26 : this.text.charAt(i6);
                        this.ch = cCharAt3;
                        if (cCharAt3 == ',') {
                            int i7 = this.bp + 1;
                            this.bp = i7;
                            if (i7 < this.text.length()) {
                                cCharAt = this.text.charAt(i7);
                            }
                            this.ch = cCharAt;
                            this.token = 16;
                        } else if (cCharAt3 == ']') {
                            int i8 = this.bp + 1;
                            this.bp = i8;
                            if (i8 < this.text.length()) {
                                cCharAt = this.text.charAt(i8);
                            }
                            this.ch = cCharAt;
                            this.token = 15;
                        } else if (cCharAt3 == '}') {
                            int i9 = this.bp + 1;
                            this.bp = i9;
                            if (i9 < this.text.length()) {
                                cCharAt = this.text.charAt(i9);
                            }
                            this.ch = cCharAt;
                            this.token = 13;
                        } else if (cCharAt3 >= '0' && cCharAt3 <= '9') {
                            this.sp = 0;
                            this.pos = this.bp;
                            scanNumber();
                        } else {
                            nextToken(2);
                        }
                    }
                    this.matchStat = 3;
                    return i5;
                }
                if (this.ch != ':') {
                    skipWhitespace();
                }
                if (this.ch == ':') {
                    int i10 = this.bp + 1;
                    this.bp = i10;
                    char cCharAt4 = i10 >= this.text.length() ? (char) 26 : this.text.charAt(i10);
                    this.ch = cCharAt4;
                    if (cCharAt4 != '\"' && cCharAt4 != '\'' && cCharAt4 != '{' && cCharAt4 != '[' && cCharAt4 != '0' && cCharAt4 != '1' && cCharAt4 != '2' && cCharAt4 != '3' && cCharAt4 != '4' && cCharAt4 != '5' && cCharAt4 != '6' && cCharAt4 != '7' && cCharAt4 != '8' && cCharAt4 != '9' && cCharAt4 != '+' && cCharAt4 != '-') {
                        skipWhitespace();
                    }
                    char c5 = this.ch;
                    if (c5 == '-' || c5 == '+' || (c5 >= '0' && c5 <= '9')) {
                        next();
                        while (true) {
                            c3 = this.ch;
                            if (c3 < '0' || c3 > '9') {
                                break;
                            }
                            next();
                        }
                        if (c3 == '.') {
                            next();
                            while (true) {
                                char c6 = this.ch;
                                if (c6 < '0' || c6 > '9') {
                                    break;
                                }
                                next();
                            }
                        }
                        char c7 = this.ch;
                        if (c7 == 'E' || c7 == 'e') {
                            next();
                            char c8 = this.ch;
                            if (c8 == '-' || c8 == '+') {
                                next();
                            }
                            while (true) {
                                char c9 = this.ch;
                                if (c9 < '0' || c9 > '9') {
                                    break;
                                }
                                next();
                            }
                        }
                        if (this.ch != ',') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c5 == '\"') {
                        skipString();
                        char c10 = this.ch;
                        if (c10 != ',' && c10 != '}') {
                            skipWhitespace();
                        }
                        if (this.ch == ',') {
                            next();
                        }
                    } else if (c5 == '{') {
                        int i11 = this.bp + 1;
                        this.bp = i11;
                        if (i11 < this.text.length()) {
                            cCharAt = this.text.charAt(i11);
                        }
                        this.ch = cCharAt;
                        skipObject(false);
                    } else if (c5 == '[') {
                        next();
                        skipArray(false);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else {
                    throw new JSONException("illegal json, " + info());
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }
}
