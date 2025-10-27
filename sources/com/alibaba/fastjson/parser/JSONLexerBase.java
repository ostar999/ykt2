package com.alibaba.fastjson.parser;

import cn.hutool.core.text.CharPool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import java.io.Closeable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import k.a;

/* loaded from: classes2.dex */
public abstract class JSONLexerBase implements JSONLexer, Closeable {
    protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
    protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
    protected int bp;
    protected char ch;
    protected int eofPos;
    protected int features;
    protected boolean hasSpecial;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected int token;
    private static final ThreadLocal<char[]> SBUF_LOCAL = new ThreadLocal<>();
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    protected static final int[] digits = new int[103];
    protected Calendar calendar = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;
    protected Locale locale = JSON.defaultLocale;
    public int matchStat = 0;
    protected int nanos = 0;

    static {
        for (int i2 = 48; i2 <= 57; i2++) {
            digits[i2] = i2 - 48;
        }
        for (int i3 = 97; i3 <= 102; i3++) {
            digits[i3] = (i3 - 97) + 10;
        }
        for (int i4 = 65; i4 <= 70; i4++) {
            digits[i4] = (i4 - 65) + 10;
        }
    }

    public JSONLexerBase(int i2) {
        this.stringDefaultValue = null;
        this.features = i2;
        if ((i2 & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
        char[] cArr = SBUF_LOCAL.get();
        this.sbuf = cArr;
        if (cArr == null) {
            this.sbuf = new char[512];
        }
    }

    public static boolean isWhitespace(char c3) {
        return c3 <= ' ' && (c3 == ' ' || c3 == '\n' || c3 == '\r' || c3 == '\t' || c3 == '\f' || c3 == '\b');
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readString(char[] r12, int r13) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.readString(char[], int):java.lang.String");
    }

    private void scanStringSingleQuote() {
        char next;
        char next2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char next3 = next();
            if (next3 == '\'') {
                this.token = 4;
                next();
                return;
            }
            if (next3 != 26) {
                boolean z2 = true;
                if (next3 == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        int i2 = this.sp;
                        char[] cArr = this.sbuf;
                        if (i2 > cArr.length) {
                            char[] cArr2 = new char[i2 * 2];
                            System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                            this.sbuf = cArr2;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char next4 = next();
                    if (next4 == '\"') {
                        putChar('\"');
                    } else if (next4 != '\'') {
                        if (next4 != 'F') {
                            if (next4 == '\\') {
                                putChar('\\');
                            } else if (next4 == 'b') {
                                putChar('\b');
                            } else if (next4 != 'f') {
                                if (next4 == 'n') {
                                    putChar('\n');
                                } else if (next4 == 'r') {
                                    putChar('\r');
                                } else if (next4 != 'x') {
                                    switch (next4) {
                                        case '/':
                                            putChar('/');
                                            break;
                                        case '0':
                                            putChar((char) 0);
                                            break;
                                        case '1':
                                            putChar((char) 1);
                                            break;
                                        case '2':
                                            putChar((char) 2);
                                            break;
                                        case '3':
                                            putChar((char) 3);
                                            break;
                                        case '4':
                                            putChar((char) 4);
                                            break;
                                        case '5':
                                            putChar((char) 5);
                                            break;
                                        case '6':
                                            putChar((char) 6);
                                            break;
                                        case '7':
                                            putChar((char) 7);
                                            break;
                                        default:
                                            switch (next4) {
                                                case 't':
                                                    putChar('\t');
                                                    break;
                                                case 'u':
                                                    putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                                                    break;
                                                case 'v':
                                                    putChar((char) 11);
                                                    break;
                                                default:
                                                    this.ch = next4;
                                                    throw new JSONException("unclosed single-quote string");
                                            }
                                    }
                                } else {
                                    next = next();
                                    next2 = next();
                                    boolean z3 = (next >= '0' && next <= '9') || (next >= 'a' && next <= 'f') || (next >= 'A' && next <= 'F');
                                    if ((next2 < '0' || next2 > '9') && ((next2 < 'a' || next2 > 'f') && (next2 < 'A' || next2 > 'F'))) {
                                        z2 = false;
                                    }
                                    if (z3 && z2) {
                                        int[] iArr = digits;
                                        putChar((char) ((iArr[next] * 16) + iArr[next2]));
                                    }
                                }
                            }
                        }
                        putChar('\f');
                    } else {
                        putChar(CharPool.SINGLE_QUOTE);
                    }
                } else if (this.hasSpecial) {
                    int i3 = this.sp;
                    char[] cArr3 = this.sbuf;
                    if (i3 == cArr3.length) {
                        putChar(next3);
                    } else {
                        this.sp = i3 + 1;
                        cArr3[i3] = next3;
                    }
                } else {
                    this.sp++;
                }
            } else {
                if (isEOF()) {
                    throw new JSONException("unclosed single-quote string");
                }
                putChar(JSONLexer.EOI);
            }
        }
        throw new JSONException("invalid escape character \\x" + next + next2);
    }

    public abstract String addSymbol(int i2, int i3, int i4, SymbolTable symbolTable);

    public abstract void arrayCopy(int i2, char[] cArr, int i3, int i4);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract byte[] bytesValue();

    public abstract boolean charArrayCompare(char[] cArr);

    public abstract char charAt(int i2);

    @Override // com.alibaba.fastjson.parser.JSONLexer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        char[] cArr = this.sbuf;
        if (cArr.length <= 8192) {
            SBUF_LOCAL.set(cArr);
        }
        this.sbuf = null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void config(Feature feature, boolean z2) {
        int iConfig = Feature.config(this.features, feature, z2);
        this.features = iConfig;
        if ((iConfig & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
    }

    public abstract void copyTo(int i2, int i3, char[] cArr);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number decimalValue(boolean z2) {
        char cCharAt = charAt((this.np + this.sp) - 1);
        try {
            return cCharAt == 'F' ? Float.valueOf(Float.parseFloat(numberString())) : cCharAt == 'D' ? Double.valueOf(Double.parseDouble(numberString())) : z2 ? decimalValue() : Double.valueOf(doubleValue());
        } catch (NumberFormatException e2) {
            throw new JSONException(e2.getMessage() + ", " + info());
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract BigDecimal decimalValue();

    public double doubleValue() {
        return Double.parseDouble(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public float floatValue() throws NumberFormatException {
        char cCharAt;
        String strNumberString = numberString();
        float f2 = Float.parseFloat(strNumberString);
        if ((f2 != 0.0f && f2 != Float.POSITIVE_INFINITY) || (cCharAt = strNumberString.charAt(0)) <= '0' || cCharAt > '9') {
            return f2;
        }
        throw new JSONException("float overflow : " + strNumberString);
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final char getCurrent() {
        return this.ch;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int getFeatures() {
        return this.features;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Locale getLocale() {
        return this.locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public abstract int indexOf(char c3, int i2);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        return "";
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int intValue() {
        int i2;
        boolean z2;
        int i3 = 0;
        if (this.np == -1) {
            this.np = 0;
        }
        int i4 = this.np;
        int i5 = this.sp + i4;
        if (charAt(i4) == '-') {
            i4++;
            i2 = Integer.MIN_VALUE;
            z2 = true;
        } else {
            i2 = -2147483647;
            z2 = false;
        }
        if (i4 < i5) {
            i3 = -(charAt(i4) - '0');
            i4++;
        }
        while (i4 < i5) {
            int i6 = i4 + 1;
            char cCharAt = charAt(i4);
            if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B') {
                i4 = i6;
                break;
            }
            int i7 = cCharAt - '0';
            if (i3 < -214748364) {
                throw new NumberFormatException(numberString());
            }
            int i8 = i3 * 10;
            if (i8 < i2 + i7) {
                throw new NumberFormatException(numberString());
            }
            i3 = i8 - i7;
            i4 = i6;
        }
        if (!z2) {
            return -i3;
        }
        if (i4 > this.np + 1) {
            return i3;
        }
        throw new NumberFormatException(numberString());
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0088  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x0086 -> B:19:0x0051). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Number integerValue() throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.integerValue():java.lang.Number");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean isBlankInput() {
        int i2 = 0;
        while (true) {
            char cCharAt = charAt(i2);
            if (cCharAt == 26) {
                this.token = 20;
                return true;
            }
            if (!isWhitespace(cCharAt)) {
                return false;
            }
            i2++;
        }
    }

    public abstract boolean isEOF();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(Feature feature) {
        return isEnabled(feature.mask);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isRef() {
        return this.sp == 4 && charAt(this.np + 1) == '$' && charAt(this.np + 2) == 'r' && charAt(this.np + 3) == 'e' && charAt(this.np + 4) == 'f';
    }

    public void lexError(String str, Object... objArr) {
        this.token = 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0085  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x005c -> B:11:0x002e). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long longValue() throws java.lang.NumberFormatException {
        /*
            r13 = this;
            int r0 = r13.np
            r1 = -1
            r2 = 0
            if (r0 != r1) goto L8
            r13.np = r2
        L8:
            int r0 = r13.np
            int r1 = r13.sp
            int r1 = r1 + r0
            char r3 = r13.charAt(r0)
            r4 = 45
            r5 = 1
            if (r3 != r4) goto L1d
            int r0 = r0 + 1
            r2 = -9223372036854775808
            r3 = r2
            r2 = r5
            goto L22
        L1d:
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L22:
            if (r0 >= r1) goto L30
            int r6 = r0 + 1
            char r0 = r13.charAt(r0)
            int r0 = r0 + (-48)
            int r0 = -r0
            long r7 = (long) r0
        L2e:
            r0 = r6
            goto L32
        L30:
            r7 = 0
        L32:
            if (r0 >= r1) goto L73
            int r6 = r0 + 1
            char r0 = r13.charAt(r0)
            r9 = 76
            if (r0 == r9) goto L72
            r9 = 83
            if (r0 == r9) goto L72
            r9 = 66
            if (r0 != r9) goto L47
            goto L72
        L47:
            int r0 = r0 + (-48)
            r9 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r9 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r9 < 0) goto L68
            r9 = 10
            long r7 = r7 * r9
            long r9 = (long) r0
            long r11 = r3 + r9
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 < 0) goto L5e
            long r7 = r7 - r9
            goto L2e
        L5e:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L68:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L72:
            r0 = r6
        L73:
            if (r2 == 0) goto L85
            int r1 = r13.np
            int r1 = r1 + r5
            if (r0 <= r1) goto L7b
            return r7
        L7b:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L85:
            long r0 = -r7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.longValue():long");
    }

    public final boolean matchField(char[] cArr) {
        while (!charArrayCompare(cArr)) {
            if (!isWhitespace(this.ch)) {
                return false;
            }
            next();
        }
        int length = this.bp + cArr.length;
        this.bp = length;
        char cCharAt = charAt(length);
        this.ch = cCharAt;
        if (cCharAt == '{') {
            next();
            this.token = 12;
        } else if (cCharAt == '[') {
            next();
            this.token = 14;
        } else if (cCharAt == 'S' && charAt(this.bp + 1) == 'e' && charAt(this.bp + 2) == 't' && charAt(this.bp + 3) == '[') {
            int i2 = this.bp + 3;
            this.bp = i2;
            this.ch = charAt(i2);
            this.token = 21;
        } else {
            nextToken();
        }
        return true;
    }

    public boolean matchField2(char[] cArr) {
        throw new UnsupportedOperationException();
    }

    public final int matchStat() {
        return this.matchStat;
    }

    public Collection<String> newCollectionByType(Class<?> cls) {
        if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (cls.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (cls.isAssignableFrom(LinkedList.class)) {
            return new LinkedList();
        }
        try {
            return (Collection) cls.newInstance();
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract char next();

    public final void nextIdent() {
        while (isWhitespace(this.ch)) {
            next();
        }
        char c3 = this.ch;
        if (c3 == '_' || c3 == '$' || Character.isLetter(c3)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken() {
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            char c3 = this.ch;
            if (c3 == '/') {
                skipComment();
            } else {
                if (c3 == '\"') {
                    scanString();
                    return;
                }
                if (c3 == ',') {
                    next();
                    this.token = 16;
                    return;
                }
                if (c3 >= '0' && c3 <= '9') {
                    scanNumber();
                    return;
                }
                if (c3 != '-') {
                    switch (c3) {
                        case '\b':
                        case '\t':
                        case '\n':
                        case '\f':
                        case '\r':
                        case ' ':
                            next();
                            break;
                        case '\'':
                            if (!isEnabled(Feature.AllowSingleQuotes)) {
                                throw new JSONException("Feature.AllowSingleQuotes is false");
                            }
                            scanStringSingleQuote();
                            return;
                        case '(':
                            next();
                            this.token = 10;
                            return;
                        case ')':
                            next();
                            this.token = 11;
                            return;
                        case '+':
                            next();
                            scanNumber();
                            return;
                        case '.':
                            next();
                            this.token = 25;
                            return;
                        case ':':
                            next();
                            this.token = 17;
                            return;
                        case ';':
                            next();
                            this.token = 24;
                            return;
                        case 'N':
                        case 'S':
                        case 'T':
                        case 'u':
                            scanIdent();
                            return;
                        case '[':
                            next();
                            this.token = 14;
                            return;
                        case ']':
                            next();
                            this.token = 15;
                            return;
                        case 'f':
                            scanFalse();
                            return;
                        case 'n':
                            scanNullOrNew();
                            return;
                        case 't':
                            scanTrue();
                            return;
                        case 'x':
                            scanHex();
                            return;
                        case '{':
                            next();
                            this.token = 12;
                            return;
                        case '}':
                            next();
                            this.token = 13;
                            return;
                        default:
                            if (isEOF()) {
                                if (this.token == 20) {
                                    throw new JSONException("EOF error");
                                }
                                this.token = 20;
                                int i2 = this.bp;
                                this.pos = i2;
                                this.eofPos = i2;
                                return;
                            }
                            char c4 = this.ch;
                            if (c4 > 31 && c4 != 127) {
                                lexError("illegal.char", String.valueOf((int) c4));
                                next();
                                return;
                            } else {
                                next();
                                break;
                            }
                            break;
                    }
                } else {
                    scanNumber();
                    return;
                }
            }
        }
    }

    public final void nextTokenWithChar(char c3) {
        this.sp = 0;
        while (true) {
            char c4 = this.ch;
            if (c4 == c3) {
                next();
                nextToken();
                return;
            }
            if (c4 != ' ' && c4 != '\n' && c4 != '\r' && c4 != '\t' && c4 != '\f' && c4 != '\b') {
                throw new JSONException("not match " + c3 + " - " + this.ch + ", info : " + info());
            }
            next();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon() {
        nextTokenWithChar(':');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String numberString();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int pos() {
        return this.pos;
    }

    public final void putChar(char c3) {
        int i2 = this.sp;
        char[] cArr = this.sbuf;
        if (i2 == cArr.length) {
            char[] cArr2 = new char[cArr.length * 2];
            System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
            this.sbuf = cArr2;
        }
        char[] cArr3 = this.sbuf;
        int i3 = this.sp;
        this.sp = i3 + 1;
        cArr3[i3] = c3;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void resetStringPosition() {
        this.sp = 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00ab  */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean scanBoolean(char r10) {
        /*
            r9 = this;
            r0 = 0
            r9.matchStat = r0
            int r1 = r9.bp
            int r1 = r1 + r0
            char r1 = r9.charAt(r1)
            r2 = 116(0x74, float:1.63E-43)
            r3 = 3
            r4 = 5
            r5 = 101(0x65, float:1.42E-43)
            r6 = -1
            r7 = 2
            r8 = 1
            if (r1 != r2) goto L42
            int r1 = r9.bp
            int r1 = r1 + r8
            char r1 = r9.charAt(r1)
            r2 = 114(0x72, float:1.6E-43)
            if (r1 != r2) goto L3f
            int r1 = r9.bp
            int r1 = r1 + r8
            int r1 = r1 + r8
            char r1 = r9.charAt(r1)
            r2 = 117(0x75, float:1.64E-43)
            if (r1 != r2) goto L3f
            int r1 = r9.bp
            int r1 = r1 + r8
            int r1 = r1 + r7
            char r1 = r9.charAt(r1)
            if (r1 != r5) goto L3f
            int r0 = r9.bp
            int r0 = r0 + 4
            char r1 = r9.charAt(r0)
            goto L8b
        L3f:
            r9.matchStat = r6
            return r0
        L42:
            r2 = 102(0x66, float:1.43E-43)
            if (r1 != r2) goto L7f
            int r1 = r9.bp
            int r1 = r1 + r8
            char r1 = r9.charAt(r1)
            r2 = 97
            if (r1 != r2) goto L7c
            int r1 = r9.bp
            int r1 = r1 + r8
            int r1 = r1 + r8
            char r1 = r9.charAt(r1)
            r2 = 108(0x6c, float:1.51E-43)
            if (r1 != r2) goto L7c
            int r1 = r9.bp
            int r1 = r1 + r8
            int r1 = r1 + r7
            char r1 = r9.charAt(r1)
            r2 = 115(0x73, float:1.61E-43)
            if (r1 != r2) goto L7c
            int r1 = r9.bp
            int r1 = r1 + r8
            int r1 = r1 + r3
            char r1 = r9.charAt(r1)
            if (r1 != r5) goto L7c
            int r1 = r9.bp
            int r1 = r1 + r4
            char r1 = r9.charAt(r1)
            r4 = 6
            goto L9b
        L7c:
            r9.matchStat = r6
            return r0
        L7f:
            r2 = 49
            if (r1 != r2) goto L8d
            int r0 = r9.bp
            int r0 = r0 + r8
            char r1 = r9.charAt(r0)
            r4 = r7
        L8b:
            r0 = r8
            goto L9b
        L8d:
            r2 = 48
            if (r1 != r2) goto L9a
            int r1 = r9.bp
            int r1 = r1 + r8
            char r1 = r9.charAt(r1)
            r4 = r7
            goto L9b
        L9a:
            r4 = r8
        L9b:
            if (r1 != r10) goto Lab
            int r10 = r9.bp
            int r10 = r10 + r4
            r9.bp = r10
            char r10 = r9.charAt(r10)
            r9.ch = r10
            r9.matchStat = r3
            return r0
        Lab:
            boolean r1 = isWhitespace(r1)
            if (r1 == 0) goto Lbc
            int r1 = r9.bp
            int r2 = r4 + 1
            int r1 = r1 + r4
            char r1 = r9.charAt(r1)
            r4 = r2
            goto L9b
        Lbc:
            r9.matchStat = r6
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanBoolean(char):boolean");
    }

    public Date scanDate(char c3) {
        int i2;
        long j2;
        Date date;
        int i3;
        boolean z2 = false;
        this.matchStat = 0;
        char cCharAt = charAt(this.bp + 0);
        if (cCharAt == '\"') {
            int iIndexOf = indexOf('\"', this.bp + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int i4 = this.bp + 1;
            String strSubString = subString(i4, iIndexOf - i4);
            if (strSubString.indexOf(92) != -1) {
                while (true) {
                    int i5 = 0;
                    for (int i6 = iIndexOf - 1; i6 >= 0 && charAt(i6) == '\\'; i6--) {
                        i5++;
                    }
                    if (i5 % 2 == 0) {
                        break;
                    }
                    iIndexOf = indexOf('\"', iIndexOf + 1);
                }
                int i7 = this.bp;
                int i8 = iIndexOf - (i7 + 1);
                strSubString = readString(sub_chars(i7 + 1, i8), i8);
            }
            int i9 = this.bp;
            int i10 = (iIndexOf - (i9 + 1)) + 1 + 1;
            i2 = i10 + 1;
            cCharAt = charAt(i9 + i10);
            JSONScanner jSONScanner = new JSONScanner(strSubString);
            try {
                if (!jSONScanner.scanISO8601DateIfMatch(false)) {
                    this.matchStat = -1;
                    return null;
                }
                date = jSONScanner.getCalendar().getTime();
            } finally {
                jSONScanner.close();
            }
        } else {
            char c4 = '9';
            i2 = 2;
            if (cCharAt == '-' || (cCharAt >= '0' && cCharAt <= '9')) {
                if (cCharAt == '-') {
                    cCharAt = charAt(this.bp + 1);
                    z2 = true;
                } else {
                    i2 = 1;
                }
                if (cCharAt < '0' || cCharAt > '9') {
                    j2 = 0;
                } else {
                    j2 = cCharAt - '0';
                    while (true) {
                        i3 = i2 + 1;
                        cCharAt = charAt(this.bp + i2);
                        if (cCharAt < '0' || cCharAt > c4) {
                            break;
                        }
                        j2 = (j2 * 10) + (cCharAt - '0');
                        i2 = i3;
                        c4 = '9';
                    }
                    i2 = i3;
                }
                if (j2 < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (z2) {
                    j2 = -j2;
                }
                date = new Date(j2);
            } else {
                if (cCharAt != 'n' || charAt(this.bp + 1) != 'u' || charAt(this.bp + 1 + 1) != 'l' || charAt(this.bp + 1 + 2) != 'l') {
                    this.matchStat = -1;
                    return null;
                }
                i2 = 5;
                this.matchStat = 5;
                cCharAt = charAt(this.bp + 4);
                date = null;
            }
        }
        if (cCharAt == ',') {
            int i11 = this.bp + i2;
            this.bp = i11;
            this.ch = charAt(i11);
            this.matchStat = 3;
            this.token = 16;
            return date;
        }
        if (cCharAt != ']') {
            this.matchStat = -1;
            return null;
        }
        int i12 = i2 + 1;
        char cCharAt2 = charAt(this.bp + i2);
        if (cCharAt2 == ',') {
            this.token = 16;
            int i13 = this.bp + i12;
            this.bp = i13;
            this.ch = charAt(i13);
        } else if (cCharAt2 == ']') {
            this.token = 15;
            int i14 = this.bp + i12;
            this.bp = i14;
            this.ch = charAt(i14);
        } else if (cCharAt2 == '}') {
            this.token = 13;
            int i15 = this.bp + i12;
            this.bp = i15;
            this.ch = charAt(i15);
        } else {
            if (cCharAt2 != 26) {
                this.matchStat = -1;
                return null;
            }
            this.token = 20;
            this.bp += i12 - 1;
            this.ch = JSONLexer.EOI;
        }
        this.matchStat = 4;
        return date;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00af A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x00b1 -> B:50:0x009f). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.math.BigDecimal scanDecimal(char r19) {
        /*
            Method dump skipped, instructions count: 470
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanDecimal(char):java.math.BigDecimal");
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00ca A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x00cc -> B:53:0x00ba). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public double scanDouble(char r23) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanDouble(char):double");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Enum<?> scanEnum(Class<?> cls, SymbolTable symbolTable, char c3) {
        String strScanSymbolWithSeperator = scanSymbolWithSeperator(symbolTable, c3);
        if (strScanSymbolWithSeperator == null) {
            return null;
        }
        return Enum.valueOf(cls, strScanSymbolWithSeperator);
    }

    public long scanEnumSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i2 = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j2 = -3750763034362895579L;
        while (true) {
            int i3 = i2 + 1;
            char cCharAt = charAt(this.bp + i2);
            if (cCharAt == '\"') {
                int i4 = i3 + 1;
                char cCharAt2 = charAt(this.bp + i3);
                if (cCharAt2 == ',') {
                    int i5 = this.bp + i4;
                    this.bp = i5;
                    this.ch = charAt(i5);
                    this.matchStat = 3;
                    return j2;
                }
                if (cCharAt2 != '}') {
                    this.matchStat = -1;
                    return 0L;
                }
                int i6 = i4 + 1;
                char cCharAt3 = charAt(this.bp + i4);
                if (cCharAt3 == ',') {
                    this.token = 16;
                    int i7 = this.bp + i6;
                    this.bp = i7;
                    this.ch = charAt(i7);
                } else if (cCharAt3 == ']') {
                    this.token = 15;
                    int i8 = this.bp + i6;
                    this.bp = i8;
                    this.ch = charAt(i8);
                } else if (cCharAt3 == '}') {
                    this.token = 13;
                    int i9 = this.bp + i6;
                    this.bp = i9;
                    this.ch = charAt(i9);
                } else {
                    if (cCharAt3 != 26) {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.token = 20;
                    this.bp += i6 - 1;
                    this.ch = JSONLexer.EOI;
                }
                this.matchStat = 4;
                return j2;
            }
            j2 = (j2 ^ ((cCharAt < 'A' || cCharAt > 'Z') ? cCharAt : cCharAt + ' ')) * 1099511628211L;
            if (cCharAt == '\\') {
                this.matchStat = -1;
                return 0L;
            }
            i2 = i3;
        }
    }

    public final void scanFalse() {
        if (this.ch != 'f') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'a') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 's') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse false");
        }
        next();
        char c3 = this.ch;
        if (c3 != ' ' && c3 != ',' && c3 != '}' && c3 != ']' && c3 != '\n' && c3 != '\r' && c3 != '\t' && c3 != 26 && c3 != '\f' && c3 != '\b' && c3 != ':' && c3 != '/') {
            throw new JSONException("scan false error");
        }
        this.token = 7;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0073, code lost:
    
        r16 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.math.BigInteger scanFieldBigInteger(char[] r21) {
        /*
            Method dump skipped, instructions count: 438
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldBigInteger(char[]):java.math.BigInteger");
    }

    public boolean scanFieldBoolean(char[] cArr) {
        boolean z2;
        int i2;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char cCharAt = charAt(this.bp + length);
        if (cCharAt == 't') {
            int i4 = i3 + 1;
            if (charAt(this.bp + i3) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int i5 = i4 + 1;
            if (charAt(this.bp + i4) != 'u') {
                this.matchStat = -1;
                return false;
            }
            i2 = i5 + 1;
            if (charAt(this.bp + i5) != 'e') {
                this.matchStat = -1;
                return false;
            }
            z2 = true;
        } else {
            if (cCharAt != 'f') {
                this.matchStat = -1;
                return false;
            }
            int i6 = i3 + 1;
            if (charAt(this.bp + i3) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int i7 = i6 + 1;
            if (charAt(this.bp + i6) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int i8 = i7 + 1;
            if (charAt(this.bp + i7) != 's') {
                this.matchStat = -1;
                return false;
            }
            int i9 = i8 + 1;
            if (charAt(this.bp + i8) != 'e') {
                this.matchStat = -1;
                return false;
            }
            z2 = false;
            i2 = i9;
        }
        int i10 = i2 + 1;
        char cCharAt2 = charAt(this.bp + i2);
        if (cCharAt2 == ',') {
            int i11 = this.bp + i10;
            this.bp = i11;
            this.ch = charAt(i11);
            this.matchStat = 3;
            this.token = 16;
            return z2;
        }
        if (cCharAt2 != '}') {
            this.matchStat = -1;
            return false;
        }
        int i12 = i10 + 1;
        char cCharAt3 = charAt(this.bp + i10);
        if (cCharAt3 == ',') {
            this.token = 16;
            int i13 = this.bp + i12;
            this.bp = i13;
            this.ch = charAt(i13);
        } else if (cCharAt3 == ']') {
            this.token = 15;
            int i14 = this.bp + i12;
            this.bp = i14;
            this.ch = charAt(i14);
        } else if (cCharAt3 == '}') {
            this.token = 13;
            int i15 = this.bp + i12;
            this.bp = i15;
            this.ch = charAt(i15);
        } else {
            if (cCharAt3 != 26) {
                this.matchStat = -1;
                return false;
            }
            this.token = 20;
            this.bp += i12 - 1;
            this.ch = JSONLexer.EOI;
        }
        this.matchStat = 4;
        return z2;
    }

    public Date scanFieldDate(char[] cArr) {
        int i2;
        long j2;
        Date date;
        int i3;
        char cCharAt;
        boolean z2 = false;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i4 = length + 1;
        char cCharAt2 = charAt(this.bp + length);
        if (cCharAt2 == '\"') {
            int iIndexOf = indexOf('\"', this.bp + cArr.length + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int length2 = this.bp + cArr.length + 1;
            String strSubString = subString(length2, iIndexOf - length2);
            if (strSubString.indexOf(92) != -1) {
                while (true) {
                    int i5 = 0;
                    for (int i6 = iIndexOf - 1; i6 >= 0 && charAt(i6) == '\\'; i6--) {
                        i5++;
                    }
                    if (i5 % 2 == 0) {
                        break;
                    }
                    iIndexOf = indexOf('\"', iIndexOf + 1);
                }
                int i7 = this.bp;
                int length3 = iIndexOf - ((cArr.length + i7) + 1);
                strSubString = readString(sub_chars(i7 + cArr.length + 1, length3), length3);
            }
            int i8 = this.bp;
            int length4 = i4 + (iIndexOf - ((cArr.length + i8) + 1)) + 1;
            i2 = length4 + 1;
            cCharAt2 = charAt(i8 + length4);
            JSONScanner jSONScanner = new JSONScanner(strSubString);
            try {
                if (!jSONScanner.scanISO8601DateIfMatch(false)) {
                    this.matchStat = -1;
                    return null;
                }
                date = jSONScanner.getCalendar().getTime();
            } finally {
                jSONScanner.close();
            }
        } else {
            if (cCharAt2 != '-' && (cCharAt2 < '0' || cCharAt2 > '9')) {
                this.matchStat = -1;
                return null;
            }
            if (cCharAt2 == '-') {
                cCharAt2 = charAt(this.bp + i4);
                i4++;
                z2 = true;
            }
            if (cCharAt2 < '0' || cCharAt2 > '9') {
                i2 = i4;
                j2 = 0;
            } else {
                j2 = cCharAt2 - '0';
                while (true) {
                    i3 = i4 + 1;
                    cCharAt = charAt(this.bp + i4);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    j2 = (j2 * 10) + (cCharAt - '0');
                    i4 = i3;
                }
                cCharAt2 = cCharAt;
                i2 = i3;
            }
            if (j2 < 0) {
                this.matchStat = -1;
                return null;
            }
            if (z2) {
                j2 = -j2;
            }
            date = new Date(j2);
        }
        if (cCharAt2 == ',') {
            int i9 = this.bp + i2;
            this.bp = i9;
            this.ch = charAt(i9);
            this.matchStat = 3;
            return date;
        }
        if (cCharAt2 != '}') {
            this.matchStat = -1;
            return null;
        }
        int i10 = i2 + 1;
        char cCharAt3 = charAt(this.bp + i2);
        if (cCharAt3 == ',') {
            this.token = 16;
            int i11 = this.bp + i10;
            this.bp = i11;
            this.ch = charAt(i11);
        } else if (cCharAt3 == ']') {
            this.token = 15;
            int i12 = this.bp + i10;
            this.bp = i12;
            this.ch = charAt(i12);
        } else if (cCharAt3 == '}') {
            this.token = 13;
            int i13 = this.bp + i10;
            this.bp = i13;
            this.ch = charAt(i13);
        } else {
            if (cCharAt3 != 26) {
                this.matchStat = -1;
                return null;
            }
            this.token = 20;
            this.bp += i10 - 1;
            this.ch = JSONLexer.EOI;
        }
        this.matchStat = 4;
        return date;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00bc A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x00be -> B:52:0x00ac). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.math.BigDecimal scanFieldDecimal(char[] r19) {
        /*
            Method dump skipped, instructions count: 493
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldDecimal(char[]):java.math.BigDecimal");
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00da A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x00dc -> B:55:0x00c8). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final double scanFieldDouble(char[] r22) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldDouble(char[]):double");
    }

    public final float scanFieldFloat(char[] cArr) throws NumberFormatException {
        int i2;
        char cCharAt;
        boolean z2;
        long j2;
        int length;
        int i3;
        float f2;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int length2 = cArr.length;
        int i4 = length2 + 1;
        char cCharAt2 = charAt(this.bp + length2);
        boolean z3 = cCharAt2 == '\"';
        if (z3) {
            cCharAt2 = charAt(this.bp + i4);
            i4++;
        }
        boolean z4 = cCharAt2 == '-';
        if (z4) {
            cCharAt2 = charAt(this.bp + i4);
            i4++;
        }
        if (cCharAt2 >= '0') {
            char c3 = '9';
            if (cCharAt2 <= '9') {
                long j3 = cCharAt2 - '0';
                while (true) {
                    i2 = i4 + 1;
                    cCharAt = charAt(this.bp + i4);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    j3 = (j3 * 10) + (cCharAt - '0');
                    i4 = i2;
                }
                if (cCharAt == '.') {
                    int i5 = i2 + 1;
                    char cCharAt3 = charAt(this.bp + i2);
                    if (cCharAt3 < '0' || cCharAt3 > '9') {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    z2 = z3;
                    j3 = (j3 * 10) + (cCharAt3 - '0');
                    j2 = 10;
                    while (true) {
                        i2 = i5 + 1;
                        cCharAt = charAt(this.bp + i5);
                        if (cCharAt < '0' || cCharAt > c3) {
                            break;
                        }
                        j3 = (j3 * 10) + (cCharAt - '0');
                        j2 *= 10;
                        i5 = i2;
                        c3 = '9';
                    }
                } else {
                    z2 = z3;
                    j2 = 1;
                }
                boolean z5 = cCharAt == 'e' || cCharAt == 'E';
                if (z5) {
                    int i6 = i2 + 1;
                    cCharAt = charAt(this.bp + i2);
                    if (cCharAt == '+' || cCharAt == '-') {
                        int i7 = i6 + 1;
                        cCharAt = charAt(this.bp + i6);
                        i2 = i7;
                    } else {
                        i2 = i6;
                    }
                    while (cCharAt >= '0' && cCharAt <= '9') {
                        int i8 = i2 + 1;
                        cCharAt = charAt(this.bp + i2);
                        i2 = i8;
                    }
                }
                if (!z2) {
                    int i9 = this.bp;
                    length = cArr.length + i9;
                    i3 = ((i9 + i2) - length) - 1;
                } else {
                    if (cCharAt != '\"') {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    int i10 = i2 + 1;
                    cCharAt = charAt(this.bp + i2);
                    int i11 = this.bp;
                    length = cArr.length + i11 + 1;
                    i3 = ((i11 + i10) - length) - 2;
                    i2 = i10;
                }
                if (z5 || i3 >= 17) {
                    f2 = Float.parseFloat(subString(length, i3));
                } else {
                    f2 = (float) (j3 / j2);
                    if (z4) {
                        f2 = -f2;
                    }
                }
                if (cCharAt == ',') {
                    int i12 = this.bp + i2;
                    this.bp = i12;
                    this.ch = charAt(i12);
                    this.matchStat = 3;
                    this.token = 16;
                    return f2;
                }
                if (cCharAt != '}') {
                    this.matchStat = -1;
                    return 0.0f;
                }
                int i13 = i2 + 1;
                char cCharAt4 = charAt(this.bp + i2);
                if (cCharAt4 == ',') {
                    this.token = 16;
                    int i14 = this.bp + i13;
                    this.bp = i14;
                    this.ch = charAt(i14);
                } else if (cCharAt4 == ']') {
                    this.token = 15;
                    int i15 = this.bp + i13;
                    this.bp = i15;
                    this.ch = charAt(i15);
                } else if (cCharAt4 == '}') {
                    this.token = 13;
                    int i16 = this.bp + i13;
                    this.bp = i16;
                    this.ch = charAt(i16);
                } else {
                    if (cCharAt4 != 26) {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    this.bp += i13 - 1;
                    this.token = 20;
                    this.ch = JSONLexer.EOI;
                }
                this.matchStat = 4;
                return f2;
            }
        }
        boolean z6 = z3;
        if (cCharAt2 != 'n' || charAt(this.bp + i4) != 'u' || charAt(this.bp + i4 + 1) != 'l' || charAt(this.bp + i4 + 2) != 'l') {
            this.matchStat = -1;
            return 0.0f;
        }
        this.matchStat = 5;
        int i17 = i4 + 3;
        int i18 = i17 + 1;
        char cCharAt5 = charAt(this.bp + i17);
        if (z6 && cCharAt5 == '\"') {
            cCharAt5 = charAt(this.bp + i18);
            i18++;
        }
        while (cCharAt5 != ',') {
            if (cCharAt5 == '}') {
                int i19 = this.bp + i18;
                this.bp = i19;
                this.ch = charAt(i19);
                this.matchStat = 5;
                this.token = 13;
                return 0.0f;
            }
            if (!isWhitespace(cCharAt5)) {
                this.matchStat = -1;
                return 0.0f;
            }
            cCharAt5 = charAt(this.bp + i18);
            i18++;
        }
        int i20 = this.bp + i18;
        this.bp = i20;
        this.ch = charAt(i20);
        this.matchStat = 5;
        this.token = 16;
        return 0.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01bb, code lost:
    
        r1 = r4;
        r19.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01be, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a8, code lost:
    
        r19.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00aa, code lost:
    
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float[] scanFieldFloatArray(char[] r20) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 447
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldFloatArray(char[]):float[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b1, code lost:
    
        r21.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b3, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0135, code lost:
    
        r4 = r18 + 1;
        r1 = charAt(r21.bp + r18);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0140, code lost:
    
        if (r2 == r3.length) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0142, code lost:
    
        r5 = new float[r2];
        r6 = 0;
        java.lang.System.arraycopy(r3, 0, r5, 0, r2);
        r3 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x014a, code lost:
    
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x014c, code lost:
    
        if (r8 < r7.length) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x014e, code lost:
    
        r5 = new float[(r7.length * 3) / 2][];
        java.lang.System.arraycopy(r3, r6, r5, r6, r2);
        r7 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0159, code lost:
    
        r5 = r8 + 1;
        r7[r8] = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x015f, code lost:
    
        if (r1 != ',') goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0161, code lost:
    
        r3 = r4 + 1;
        r2 = charAt(r21.bp + r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x016f, code lost:
    
        if (r1 != ']') goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0171, code lost:
    
        r3 = r4 + 1;
        r2 = charAt(r21.bp + r4);
        r8 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x017c, code lost:
    
        r2 = r1;
        r3 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0191, code lost:
    
        r21.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0194, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float[][] scanFieldFloatArray2(char[] r22) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 529
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldFloatArray2(char[]):float[][]");
    }

    public int scanFieldInt(char[] cArr) {
        int i2;
        char cCharAt;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char cCharAt2 = charAt(this.bp + length);
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(this.bp + i3);
            i3++;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int i4 = cCharAt2 - '0';
        while (true) {
            i2 = i3 + 1;
            cCharAt = charAt(this.bp + i3);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            i4 = (i4 * 10) + (cCharAt - '0');
            i3 = i2;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return 0;
        }
        if ((i4 < 0 || i2 > cArr.length + 14) && !(i4 == Integer.MIN_VALUE && i2 == 17 && z2)) {
            this.matchStat = -1;
            return 0;
        }
        if (cCharAt == ',') {
            int i5 = this.bp + i2;
            this.bp = i5;
            this.ch = charAt(i5);
            this.matchStat = 3;
            this.token = 16;
            return z2 ? -i4 : i4;
        }
        if (cCharAt != '}') {
            this.matchStat = -1;
            return 0;
        }
        int i6 = i2 + 1;
        char cCharAt3 = charAt(this.bp + i2);
        if (cCharAt3 == ',') {
            this.token = 16;
            int i7 = this.bp + i6;
            this.bp = i7;
            this.ch = charAt(i7);
        } else if (cCharAt3 == ']') {
            this.token = 15;
            int i8 = this.bp + i6;
            this.bp = i8;
            this.ch = charAt(i8);
        } else if (cCharAt3 == '}') {
            this.token = 13;
            int i9 = this.bp + i6;
            this.bp = i9;
            this.ch = charAt(i9);
        } else {
            if (cCharAt3 != 26) {
                this.matchStat = -1;
                return 0;
            }
            this.token = 20;
            this.bp += i6 - 1;
            this.ch = JSONLexer.EOI;
        }
        this.matchStat = 4;
        return z2 ? -i4 : i4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x011f, code lost:
    
        r2 = r4;
        r17.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0122, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int[] scanFieldIntArray(char[] r18) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldIntArray(char[]):int[]");
    }

    public long scanFieldLong(char[] cArr) {
        boolean z2;
        int i2;
        char cCharAt;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char cCharAt2 = charAt(this.bp + length);
        if (cCharAt2 == '-') {
            cCharAt2 = charAt(this.bp + i3);
            i3++;
            z2 = true;
        } else {
            z2 = false;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            this.matchStat = -1;
            return 0L;
        }
        long j2 = cCharAt2 - '0';
        while (true) {
            i2 = i3 + 1;
            cCharAt = charAt(this.bp + i3);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j2 = (j2 * 10) + (cCharAt - '0');
            i3 = i2;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return 0L;
        }
        if (!(i2 - cArr.length < 21 && (j2 >= 0 || (j2 == Long.MIN_VALUE && z2)))) {
            this.matchStat = -1;
            return 0L;
        }
        if (cCharAt == ',') {
            int i4 = this.bp + i2;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 3;
            this.token = 16;
            return z2 ? -j2 : j2;
        }
        if (cCharAt != '}') {
            this.matchStat = -1;
            return 0L;
        }
        int i5 = i2 + 1;
        char cCharAt3 = charAt(this.bp + i2);
        if (cCharAt3 == ',') {
            this.token = 16;
            int i6 = this.bp + i5;
            this.bp = i6;
            this.ch = charAt(i6);
        } else if (cCharAt3 == ']') {
            this.token = 15;
            int i7 = this.bp + i5;
            this.bp = i7;
            this.ch = charAt(i7);
        } else if (cCharAt3 == '}') {
            this.token = 13;
            int i8 = this.bp + i5;
            this.bp = i8;
            this.ch = charAt(i8);
        } else {
            if (cCharAt3 != 26) {
                this.matchStat = -1;
                return 0L;
            }
            this.token = 20;
            this.bp += i5 - 1;
            this.ch = JSONLexer.EOI;
        }
        this.matchStat = 4;
        return z2 ? -j2 : j2;
    }

    public String scanFieldString(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return stringDefaultValue();
        }
        int length = cArr.length;
        int i2 = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int iIndexOf = indexOf('\"', this.bp + cArr.length + 1);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        int length2 = this.bp + cArr.length + 1;
        String strSubString = subString(length2, iIndexOf - length2);
        if (strSubString.indexOf(92) != -1) {
            while (true) {
                int i3 = 0;
                for (int i4 = iIndexOf - 1; i4 >= 0 && charAt(i4) == '\\'; i4--) {
                    i3++;
                }
                if (i3 % 2 == 0) {
                    break;
                }
                iIndexOf = indexOf('\"', iIndexOf + 1);
            }
            int i5 = this.bp;
            int length3 = iIndexOf - ((cArr.length + i5) + 1);
            strSubString = readString(sub_chars(i5 + cArr.length + 1, length3), length3);
        }
        int i6 = this.bp;
        int length4 = i2 + (iIndexOf - ((cArr.length + i6) + 1)) + 1;
        int i7 = length4 + 1;
        char cCharAt = charAt(i6 + length4);
        if (cCharAt == ',') {
            int i8 = this.bp + i7;
            this.bp = i8;
            this.ch = charAt(i8);
            this.matchStat = 3;
            return strSubString;
        }
        if (cCharAt != '}') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int i9 = i7 + 1;
        char cCharAt2 = charAt(this.bp + i7);
        if (cCharAt2 == ',') {
            this.token = 16;
            int i10 = this.bp + i9;
            this.bp = i10;
            this.ch = charAt(i10);
        } else if (cCharAt2 == ']') {
            this.token = 15;
            int i11 = this.bp + i9;
            this.bp = i11;
            this.ch = charAt(i11);
        } else if (cCharAt2 == '}') {
            this.token = 13;
            int i12 = this.bp + i9;
            this.bp = i12;
            this.ch = charAt(i12);
        } else {
            if (cCharAt2 != 26) {
                this.matchStat = -1;
                return stringDefaultValue();
            }
            this.token = 20;
            this.bp += i9 - 1;
            this.ch = JSONLexer.EOI;
        }
        this.matchStat = 4;
        return strSubString;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00e8, code lost:
    
        if (r12 != ']') goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ee, code lost:
    
        if (r13.size() != 0) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f0, code lost:
    
        r12 = charAt(r11.bp + r1);
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0170, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illega str");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r12, java.lang.Class<?> r13) {
        /*
            Method dump skipped, instructions count: 369
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    public long scanFieldSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i2 = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j2 = -3750763034362895579L;
        while (true) {
            int i3 = i2 + 1;
            char cCharAt = charAt(this.bp + i2);
            if (cCharAt == '\"') {
                int i4 = i3 + 1;
                char cCharAt2 = charAt(this.bp + i3);
                if (cCharAt2 == ',') {
                    int i5 = this.bp + i4;
                    this.bp = i5;
                    this.ch = charAt(i5);
                    this.matchStat = 3;
                    return j2;
                }
                if (cCharAt2 != '}') {
                    this.matchStat = -1;
                    return 0L;
                }
                int i6 = i4 + 1;
                char cCharAt3 = charAt(this.bp + i4);
                if (cCharAt3 == ',') {
                    this.token = 16;
                    int i7 = this.bp + i6;
                    this.bp = i7;
                    this.ch = charAt(i7);
                } else if (cCharAt3 == ']') {
                    this.token = 15;
                    int i8 = this.bp + i6;
                    this.bp = i8;
                    this.ch = charAt(i8);
                } else if (cCharAt3 == '}') {
                    this.token = 13;
                    int i9 = this.bp + i6;
                    this.bp = i9;
                    this.ch = charAt(i9);
                } else {
                    if (cCharAt3 != 26) {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.token = 20;
                    this.bp += i6 - 1;
                    this.ch = JSONLexer.EOI;
                }
                this.matchStat = 4;
                return j2;
            }
            j2 = (j2 ^ cCharAt) * 1099511628211L;
            if (cCharAt == '\\') {
                this.matchStat = -1;
                return 0L;
            }
            i2 = i3;
        }
    }

    public UUID scanFieldUUID(char[] cArr) {
        char cCharAt;
        int i2;
        UUID uuid;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i16 = length + 1;
        char cCharAt2 = charAt(this.bp + length);
        char c3 = 4;
        if (cCharAt2 != '\"') {
            if (cCharAt2 == 'n') {
                int i17 = i16 + 1;
                if (charAt(this.bp + i16) == 'u') {
                    int i18 = i17 + 1;
                    if (charAt(this.bp + i17) == 'l') {
                        int i19 = i18 + 1;
                        if (charAt(this.bp + i18) == 'l') {
                            cCharAt = charAt(this.bp + i19);
                            i2 = i19 + 1;
                            uuid = null;
                        }
                    }
                }
            }
            this.matchStat = -1;
            return null;
        }
        int iIndexOf = indexOf('\"', this.bp + cArr.length + 1);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        int length2 = this.bp + cArr.length + 1;
        int i20 = iIndexOf - length2;
        char c4 = 'F';
        char c5 = 'f';
        char c6 = 'A';
        char c7 = '0';
        if (i20 == 36) {
            int i21 = 0;
            long j2 = 0;
            while (i21 < 8) {
                char cCharAt3 = charAt(length2 + i21);
                if (cCharAt3 < '0' || cCharAt3 > '9') {
                    if (cCharAt3 >= 'a' && cCharAt3 <= 'f') {
                        i14 = cCharAt3 - 'a';
                    } else {
                        if (cCharAt3 < 'A' || cCharAt3 > c4) {
                            this.matchStat = -2;
                            return null;
                        }
                        i14 = cCharAt3 - 'A';
                    }
                    i15 = i14 + 10;
                } else {
                    i15 = cCharAt3 - '0';
                }
                j2 = (j2 << 4) | i15;
                i21++;
                iIndexOf = iIndexOf;
                c4 = 'F';
            }
            int i22 = iIndexOf;
            int i23 = 9;
            int i24 = 13;
            while (i23 < i24) {
                char cCharAt4 = charAt(length2 + i23);
                if (cCharAt4 < '0' || cCharAt4 > '9') {
                    if (cCharAt4 >= 'a' && cCharAt4 <= 'f') {
                        i12 = cCharAt4 - 'a';
                    } else {
                        if (cCharAt4 < c6 || cCharAt4 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i12 = cCharAt4 - 'A';
                    }
                    i13 = i12 + 10;
                } else {
                    i13 = cCharAt4 - '0';
                }
                j2 = (j2 << c3) | i13;
                i23++;
                i24 = 13;
                c6 = 'A';
                c3 = 4;
            }
            long j3 = j2;
            for (int i25 = 14; i25 < 18; i25++) {
                char cCharAt5 = charAt(length2 + i25);
                if (cCharAt5 < '0' || cCharAt5 > '9') {
                    if (cCharAt5 >= 'a' && cCharAt5 <= 'f') {
                        i10 = cCharAt5 - 'a';
                    } else {
                        if (cCharAt5 < 'A' || cCharAt5 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i10 = cCharAt5 - 'A';
                    }
                    i11 = i10 + 10;
                } else {
                    i11 = cCharAt5 - '0';
                }
                j3 = (j3 << 4) | i11;
            }
            long j4 = 0;
            for (int i26 = 19; i26 < 23; i26++) {
                char cCharAt6 = charAt(length2 + i26);
                if (cCharAt6 < '0' || cCharAt6 > '9') {
                    if (cCharAt6 >= 'a' && cCharAt6 <= 'f') {
                        i8 = cCharAt6 - 'a';
                    } else {
                        if (cCharAt6 < 'A' || cCharAt6 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i8 = cCharAt6 - 'A';
                    }
                    i9 = i8 + 10;
                } else {
                    i9 = cCharAt6 - '0';
                }
                j4 = (j4 << 4) | i9;
            }
            int i27 = 24;
            long j5 = j4;
            int i28 = 36;
            while (i27 < i28) {
                char cCharAt7 = charAt(length2 + i27);
                if (cCharAt7 < c7 || cCharAt7 > '9') {
                    if (cCharAt7 >= 'a' && cCharAt7 <= c5) {
                        i6 = cCharAt7 - 'a';
                    } else {
                        if (cCharAt7 < 'A' || cCharAt7 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i6 = cCharAt7 - 'A';
                    }
                    i7 = i6 + 10;
                } else {
                    i7 = cCharAt7 - '0';
                }
                j5 = (j5 << 4) | i7;
                i27++;
                i16 = i16;
                i28 = 36;
                c7 = '0';
                c5 = 'f';
            }
            uuid = new UUID(j3, j5);
            int i29 = this.bp;
            int length3 = i16 + (i22 - ((cArr.length + i29) + 1)) + 1;
            i2 = length3 + 1;
            cCharAt = charAt(i29 + length3);
        } else {
            if (i20 != 32) {
                this.matchStat = -1;
                return null;
            }
            long j6 = 0;
            for (int i30 = 0; i30 < 16; i30++) {
                char cCharAt8 = charAt(length2 + i30);
                if (cCharAt8 < '0' || cCharAt8 > '9') {
                    if (cCharAt8 >= 'a' && cCharAt8 <= 'f') {
                        i4 = cCharAt8 - 'a';
                    } else {
                        if (cCharAt8 < 'A' || cCharAt8 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i4 = cCharAt8 - 'A';
                    }
                    i5 = i4 + 10;
                } else {
                    i5 = cCharAt8 - '0';
                }
                j6 = (j6 << 4) | i5;
            }
            int i31 = 16;
            long j7 = 0;
            for (int i32 = 32; i31 < i32; i32 = 32) {
                char cCharAt9 = charAt(length2 + i31);
                if (cCharAt9 >= '0' && cCharAt9 <= '9') {
                    i3 = cCharAt9 - '0';
                } else if (cCharAt9 >= 'a' && cCharAt9 <= 'f') {
                    i3 = (cCharAt9 - 'a') + 10;
                } else {
                    if (cCharAt9 < 'A' || cCharAt9 > 'F') {
                        this.matchStat = -2;
                        return null;
                    }
                    i3 = (cCharAt9 - 'A') + 10;
                    j7 = (j7 << 4) | i3;
                    i31++;
                }
                j7 = (j7 << 4) | i3;
                i31++;
            }
            uuid = new UUID(j6, j7);
            int i33 = this.bp;
            int length4 = i16 + (iIndexOf - ((cArr.length + i33) + 1)) + 1;
            i2 = length4 + 1;
            cCharAt = charAt(i33 + length4);
        }
        if (cCharAt == ',') {
            int i34 = this.bp + i2;
            this.bp = i34;
            this.ch = charAt(i34);
            this.matchStat = 3;
            return uuid;
        }
        if (cCharAt != '}') {
            this.matchStat = -1;
            return null;
        }
        int i35 = i2 + 1;
        char cCharAt10 = charAt(this.bp + i2);
        if (cCharAt10 == ',') {
            this.token = 16;
            int i36 = this.bp + i35;
            this.bp = i36;
            this.ch = charAt(i36);
        } else if (cCharAt10 == ']') {
            this.token = 15;
            int i37 = this.bp + i35;
            this.bp = i37;
            this.ch = charAt(i37);
        } else if (cCharAt10 == '}') {
            this.token = 13;
            int i38 = this.bp + i35;
            this.bp = i38;
            this.ch = charAt(i38);
        } else {
            if (cCharAt10 != 26) {
                this.matchStat = -1;
                return null;
            }
            this.token = 20;
            this.bp += i35 - 1;
            this.ch = JSONLexer.EOI;
        }
        this.matchStat = 4;
        return uuid;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final float scanFloat(char c3) throws NumberFormatException {
        int i2;
        int i3;
        char cCharAt;
        long j2;
        int i4;
        int i5;
        float f2;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        boolean z2 = cCharAt2 == '\"';
        if (z2) {
            cCharAt2 = charAt(this.bp + 1);
            i2 = 2;
        } else {
            i2 = 1;
        }
        boolean z3 = cCharAt2 == '-';
        if (z3) {
            cCharAt2 = charAt(this.bp + i2);
            i2++;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            if (cCharAt2 != 'n' || charAt(this.bp + i2) != 'u' || charAt(this.bp + i2 + 1) != 'l' || charAt(this.bp + i2 + 2) != 'l') {
                this.matchStat = -1;
                return 0.0f;
            }
            this.matchStat = 5;
            int i6 = i2 + 3;
            int i7 = i6 + 1;
            char cCharAt3 = charAt(this.bp + i6);
            if (z2 && cCharAt3 == '\"') {
                cCharAt3 = charAt(this.bp + i7);
                i7++;
            }
            while (cCharAt3 != ',') {
                if (cCharAt3 == ']') {
                    int i8 = this.bp + i7;
                    this.bp = i8;
                    this.ch = charAt(i8);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0f;
                }
                if (!isWhitespace(cCharAt3)) {
                    this.matchStat = -1;
                    return 0.0f;
                }
                cCharAt3 = charAt(this.bp + i7);
                i7++;
            }
            int i9 = this.bp + i7;
            this.bp = i9;
            this.ch = charAt(i9);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        }
        long j3 = cCharAt2 - '0';
        while (true) {
            i3 = i2 + 1;
            cCharAt = charAt(this.bp + i2);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j3 = (j3 * 10) + (cCharAt - '0');
            i2 = i3;
        }
        if (cCharAt == '.') {
            int i10 = i3 + 1;
            char cCharAt4 = charAt(this.bp + i3);
            if (cCharAt4 < '0' || cCharAt4 > '9') {
                this.matchStat = -1;
                return 0.0f;
            }
            j3 = (j3 * 10) + (cCharAt4 - '0');
            j2 = 10;
            while (true) {
                i3 = i10 + 1;
                cCharAt = charAt(this.bp + i10);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                j3 = (j3 * 10) + (cCharAt - '0');
                j2 *= 10;
                i10 = i3;
            }
        } else {
            j2 = 1;
        }
        boolean z4 = cCharAt == 'e' || cCharAt == 'E';
        if (z4) {
            int i11 = i3 + 1;
            char cCharAt5 = charAt(this.bp + i3);
            if (cCharAt5 == '+' || cCharAt5 == '-') {
                int i12 = i11 + 1;
                cCharAt = charAt(this.bp + i11);
                i3 = i12;
            } else {
                i3 = i11;
                cCharAt = cCharAt5;
            }
            while (cCharAt >= '0' && cCharAt <= '9') {
                int i13 = i3 + 1;
                cCharAt = charAt(this.bp + i3);
                i3 = i13;
            }
        }
        if (!z2) {
            i4 = this.bp;
            i5 = ((i4 + i3) - i4) - 1;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return 0.0f;
            }
            int i14 = i3 + 1;
            cCharAt = charAt(this.bp + i3);
            int i15 = this.bp;
            i4 = i15 + 1;
            i5 = ((i15 + i14) - i4) - 2;
            i3 = i14;
        }
        if (z4 || i5 >= 17) {
            f2 = Float.parseFloat(subString(i4, i5));
        } else {
            f2 = (float) (j3 / j2);
            if (z3) {
                f2 = -f2;
            }
        }
        if (cCharAt != c3) {
            this.matchStat = -1;
            return f2;
        }
        int i16 = this.bp + i3;
        this.bp = i16;
        this.ch = charAt(i16);
        this.matchStat = 3;
        this.token = 16;
        return f2;
    }

    public final void scanHex() {
        char next;
        if (this.ch != 'x') {
            throw new JSONException("illegal state. " + this.ch);
        }
        next();
        if (this.ch != '\'') {
            throw new JSONException("illegal state. " + this.ch);
        }
        this.np = this.bp;
        next();
        if (this.ch == '\'') {
            next();
            this.token = 26;
            return;
        }
        while (true) {
            next = next();
            if ((next < '0' || next > '9') && (next < 'A' || next > 'F')) {
                break;
            } else {
                this.sp++;
            }
        }
        if (next == '\'') {
            this.sp++;
            next();
            this.token = 26;
        } else {
            throw new JSONException("illegal state. " + next);
        }
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String strStringVal = stringVal();
        if ("null".equalsIgnoreCase(strStringVal)) {
            this.token = 8;
            return;
        }
        if ("new".equals(strStringVal)) {
            this.token = 9;
            return;
        }
        if (a.f27523u.equals(strStringVal)) {
            this.token = 6;
            return;
        }
        if (a.f27524v.equals(strStringVal)) {
            this.token = 7;
            return;
        }
        if ("undefined".equals(strStringVal)) {
            this.token = 23;
            return;
        }
        if ("Set".equals(strStringVal)) {
            this.token = 21;
        } else if ("TreeSet".equals(strStringVal)) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int scanInt(char c3) {
        int i2;
        int i3;
        char cCharAt;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        boolean z2 = cCharAt2 == '\"';
        if (z2) {
            cCharAt2 = charAt(this.bp + 1);
            i2 = 2;
        } else {
            i2 = 1;
        }
        boolean z3 = cCharAt2 == '-';
        if (z3) {
            cCharAt2 = charAt(this.bp + i2);
            i2++;
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            int i4 = cCharAt2 - '0';
            while (true) {
                i3 = i2 + 1;
                cCharAt = charAt(this.bp + i2);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i4 = (i4 * 10) + (cCharAt - '0');
                i2 = i3;
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0;
            }
            if (i4 < 0) {
                this.matchStat = -1;
                return 0;
            }
            while (cCharAt != c3) {
                if (!isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return z3 ? -i4 : i4;
                }
                char cCharAt3 = charAt(this.bp + i3);
                i3++;
                cCharAt = cCharAt3;
            }
            int i5 = this.bp + i3;
            this.bp = i5;
            this.ch = charAt(i5);
            this.matchStat = 3;
            this.token = 16;
            return z3 ? -i4 : i4;
        }
        if (cCharAt2 != 'n' || charAt(this.bp + i2) != 'u' || charAt(this.bp + i2 + 1) != 'l' || charAt(this.bp + i2 + 2) != 'l') {
            this.matchStat = -1;
            return 0;
        }
        this.matchStat = 5;
        int i6 = i2 + 3;
        int i7 = i6 + 1;
        char cCharAt4 = charAt(this.bp + i6);
        if (z2 && cCharAt4 == '\"') {
            int i8 = i7 + 1;
            cCharAt4 = charAt(this.bp + i7);
            i7 = i8;
        }
        while (cCharAt4 != ',') {
            if (cCharAt4 == ']') {
                int i9 = this.bp + i7;
                this.bp = i9;
                this.ch = charAt(i9);
                this.matchStat = 5;
                this.token = 15;
                return 0;
            }
            if (!isWhitespace(cCharAt4)) {
                this.matchStat = -1;
                return 0;
            }
            int i10 = i7 + 1;
            cCharAt4 = charAt(this.bp + i7);
            i7 = i10;
        }
        int i11 = this.bp + i7;
        this.bp = i11;
        this.ch = charAt(i11);
        this.matchStat = 5;
        this.token = 16;
        return 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char c3) {
        int i2;
        int i3;
        char cCharAt;
        char c4;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        boolean z2 = cCharAt2 == '\"';
        if (z2) {
            cCharAt2 = charAt(this.bp + 1);
            i2 = 2;
        } else {
            i2 = 1;
        }
        boolean z3 = cCharAt2 == '-';
        if (z3) {
            cCharAt2 = charAt(this.bp + i2);
            i2++;
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            long j2 = cCharAt2 - '0';
            while (true) {
                i3 = i2 + 1;
                cCharAt = charAt(this.bp + i2);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                j2 = (j2 * 10) + (cCharAt - '0');
                i2 = i3;
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (!(j2 >= 0 || (j2 == Long.MIN_VALUE && z3))) {
                throw new NumberFormatException(subString(this.bp, i3 - 1));
            }
            if (!z2) {
                c4 = c3;
            } else {
                if (cCharAt != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                cCharAt = charAt(this.bp + i3);
                c4 = c3;
                i3++;
            }
            while (cCharAt != c4) {
                if (!isWhitespace(cCharAt)) {
                    this.matchStat = -1;
                    return j2;
                }
                cCharAt = charAt(this.bp + i3);
                i3++;
            }
            int i4 = this.bp + i3;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 3;
            this.token = 16;
            return z3 ? -j2 : j2;
        }
        if (cCharAt2 != 'n' || charAt(this.bp + i2) != 'u' || charAt(this.bp + i2 + 1) != 'l' || charAt(this.bp + i2 + 2) != 'l') {
            this.matchStat = -1;
            return 0L;
        }
        this.matchStat = 5;
        int i5 = i2 + 3;
        int i6 = i5 + 1;
        char cCharAt3 = charAt(this.bp + i5);
        if (z2 && cCharAt3 == '\"') {
            int i7 = i6 + 1;
            cCharAt3 = charAt(this.bp + i6);
            i6 = i7;
        }
        while (cCharAt3 != ',') {
            if (cCharAt3 == ']') {
                int i8 = this.bp + i6;
                this.bp = i8;
                this.ch = charAt(i8);
                this.matchStat = 5;
                this.token = 15;
                return 0L;
            }
            if (!isWhitespace(cCharAt3)) {
                this.matchStat = -1;
                return 0L;
            }
            int i9 = i6 + 1;
            cCharAt3 = charAt(this.bp + i6);
            i6 = i9;
        }
        int i10 = this.bp + i6;
        this.bp = i10;
        this.ch = charAt(i10);
        this.matchStat = 5;
        this.token = 16;
        return 0L;
    }

    public final void scanNullOrNew() {
        scanNullOrNew(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00cb  */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void scanNumber() {
        /*
            Method dump skipped, instructions count: 207
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanNumber():void");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanString() {
        char next;
        char next2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char next3 = next();
            if (next3 == '\"') {
                this.token = 4;
                this.ch = next();
                return;
            }
            if (next3 != 26) {
                boolean z2 = true;
                if (next3 == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        int i2 = this.sp;
                        char[] cArr = this.sbuf;
                        if (i2 >= cArr.length) {
                            int length = cArr.length * 2;
                            if (i2 <= length) {
                                i2 = length;
                            }
                            char[] cArr2 = new char[i2];
                            System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                            this.sbuf = cArr2;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char next4 = next();
                    if (next4 == '\"') {
                        putChar('\"');
                    } else if (next4 != '\'') {
                        if (next4 != 'F') {
                            if (next4 == '\\') {
                                putChar('\\');
                            } else if (next4 == 'b') {
                                putChar('\b');
                            } else if (next4 != 'f') {
                                if (next4 == 'n') {
                                    putChar('\n');
                                } else if (next4 == 'r') {
                                    putChar('\r');
                                } else if (next4 != 'x') {
                                    switch (next4) {
                                        case '/':
                                            putChar('/');
                                            break;
                                        case '0':
                                            putChar((char) 0);
                                            break;
                                        case '1':
                                            putChar((char) 1);
                                            break;
                                        case '2':
                                            putChar((char) 2);
                                            break;
                                        case '3':
                                            putChar((char) 3);
                                            break;
                                        case '4':
                                            putChar((char) 4);
                                            break;
                                        case '5':
                                            putChar((char) 5);
                                            break;
                                        case '6':
                                            putChar((char) 6);
                                            break;
                                        case '7':
                                            putChar((char) 7);
                                            break;
                                        default:
                                            switch (next4) {
                                                case 't':
                                                    putChar('\t');
                                                    break;
                                                case 'u':
                                                    putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                                                    break;
                                                case 'v':
                                                    putChar((char) 11);
                                                    break;
                                                default:
                                                    this.ch = next4;
                                                    throw new JSONException("unclosed string : " + next4);
                                            }
                                    }
                                } else {
                                    next = next();
                                    next2 = next();
                                    boolean z3 = (next >= '0' && next <= '9') || (next >= 'a' && next <= 'f') || (next >= 'A' && next <= 'F');
                                    if ((next2 < '0' || next2 > '9') && ((next2 < 'a' || next2 > 'f') && (next2 < 'A' || next2 > 'F'))) {
                                        z2 = false;
                                    }
                                    if (z3 && z2) {
                                        int[] iArr = digits;
                                        putChar((char) ((iArr[next] * 16) + iArr[next2]));
                                    }
                                }
                            }
                        }
                        putChar('\f');
                    } else {
                        putChar(CharPool.SINGLE_QUOTE);
                    }
                } else if (this.hasSpecial) {
                    int i3 = this.sp;
                    char[] cArr3 = this.sbuf;
                    if (i3 == cArr3.length) {
                        putChar(next3);
                    } else {
                        this.sp = i3 + 1;
                        cArr3[i3] = next3;
                    }
                } else {
                    this.sp++;
                }
            } else {
                if (isEOF()) {
                    throw new JSONException("unclosed string : " + next3);
                }
                putChar(JSONLexer.EOI);
            }
        }
        throw new JSONException("invalid escape character \\x" + next + next2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void scanStringArray(Collection<String> collection, char c3) {
        int i2;
        char cCharAt;
        int i3;
        char cCharAt2;
        this.matchStat = 0;
        char cCharAt3 = charAt(this.bp + 0);
        char c4 = 'u';
        char c5 = 'l';
        if (cCharAt3 == 'n' && charAt(this.bp + 1) == 'u' && charAt(this.bp + 1 + 1) == 'l' && charAt(this.bp + 1 + 2) == 'l' && charAt(this.bp + 1 + 3) == c3) {
            int i4 = this.bp + 5;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 5;
            return;
        }
        if (cCharAt3 != '[') {
            this.matchStat = -1;
            return;
        }
        char cCharAt4 = charAt(this.bp + 1);
        int i5 = 2;
        while (true) {
            if (cCharAt4 == 'n' && charAt(this.bp + i5) == c4 && charAt(this.bp + i5 + 1) == c5 && charAt(this.bp + i5 + 2) == c5) {
                int i6 = i5 + 3;
                i2 = i6 + 1;
                cCharAt = charAt(this.bp + i6);
                collection.add(null);
            } else {
                if (cCharAt4 == ']' && collection.size() == 0) {
                    i3 = i5 + 1;
                    cCharAt2 = charAt(this.bp + i5);
                    break;
                }
                if (cCharAt4 != '\"') {
                    this.matchStat = -1;
                    return;
                }
                int i7 = this.bp + i5;
                int iIndexOf = indexOf('\"', i7);
                if (iIndexOf == -1) {
                    throw new JSONException("unclosed str");
                }
                String strSubString = subString(this.bp + i5, iIndexOf - i7);
                if (strSubString.indexOf(92) != -1) {
                    while (true) {
                        int i8 = 0;
                        for (int i9 = iIndexOf - 1; i9 >= 0 && charAt(i9) == '\\'; i9--) {
                            i8++;
                        }
                        if (i8 % 2 == 0) {
                            break;
                        } else {
                            iIndexOf = indexOf('\"', iIndexOf + 1);
                        }
                    }
                    int i10 = iIndexOf - i7;
                    strSubString = readString(sub_chars(this.bp + i5, i10), i10);
                }
                int i11 = this.bp;
                int i12 = i5 + (iIndexOf - (i11 + i5)) + 1;
                i2 = i12 + 1;
                cCharAt = charAt(i11 + i12);
                collection.add(strSubString);
            }
            if (cCharAt == ',') {
                i5 = i2 + 1;
                cCharAt4 = charAt(this.bp + i2);
                c4 = 'u';
                c5 = 'l';
            } else if (cCharAt != ']') {
                this.matchStat = -1;
                return;
            } else {
                i3 = i2 + 1;
                cCharAt2 = charAt(this.bp + i2);
            }
        }
        if (cCharAt2 != c3) {
            this.matchStat = -1;
            return;
        }
        int i13 = this.bp + i3;
        this.bp = i13;
        this.ch = charAt(i13);
        this.matchStat = 3;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable) {
        skipWhitespace();
        char c3 = this.ch;
        if (c3 == '\"') {
            return scanSymbol(symbolTable, '\"');
        }
        if (c3 == '\'') {
            if (isEnabled(Feature.AllowSingleQuotes)) {
                return scanSymbol(symbolTable, CharPool.SINGLE_QUOTE);
            }
            throw new JSONException("syntax error");
        }
        if (c3 == '}') {
            next();
            this.token = 13;
            return null;
        }
        if (c3 == ',') {
            next();
            this.token = 16;
            return null;
        }
        if (c3 == 26) {
            this.token = 20;
            return null;
        }
        if (isEnabled(Feature.AllowUnQuotedFieldNames)) {
            return scanSymbolUnQuoted(symbolTable);
        }
        throw new JSONException("syntax error");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        if (this.token == 1 && this.pos == 0 && this.bp == 1) {
            this.bp = 0;
        }
        boolean[] zArr = IOUtils.firstIdentifierFlags;
        int i2 = this.ch;
        if (!(i2 >= zArr.length || zArr[i2])) {
            throw new JSONException("illegal identifier : " + this.ch + info());
        }
        boolean[] zArr2 = IOUtils.identifierFlags;
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            char next = next();
            if (next < zArr2.length && !zArr2[next]) {
                break;
            }
            i2 = (i2 * 31) + next;
            this.sp++;
        }
        this.ch = charAt(this.bp);
        this.token = 18;
        if (this.sp == 4 && i2 == 3392903 && charAt(this.np) == 'n' && charAt(this.np + 1) == 'u' && charAt(this.np + 2) == 'l' && charAt(this.np + 3) == 'l') {
            return null;
        }
        return symbolTable == null ? subString(this.np, this.sp) : addSymbol(this.np, this.sp, i2, symbolTable);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanSymbolWithSeperator(SymbolTable symbolTable, char c3) {
        int i2 = 0;
        this.matchStat = 0;
        char cCharAt = charAt(this.bp + 0);
        if (cCharAt == 'n') {
            if (charAt(this.bp + 1) != 'u' || charAt(this.bp + 1 + 1) != 'l' || charAt(this.bp + 1 + 2) != 'l') {
                this.matchStat = -1;
                return null;
            }
            if (charAt(this.bp + 4) != c3) {
                this.matchStat = -1;
                return null;
            }
            int i3 = this.bp + 5;
            this.bp = i3;
            this.ch = charAt(i3);
            this.matchStat = 3;
            return null;
        }
        if (cCharAt != '\"') {
            this.matchStat = -1;
            return null;
        }
        int i4 = 1;
        while (true) {
            int i5 = i4 + 1;
            char cCharAt2 = charAt(this.bp + i4);
            if (cCharAt2 == '\"') {
                int i6 = this.bp;
                int i7 = i6 + 0 + 1;
                String strAddSymbol = addSymbol(i7, ((i6 + i5) - i7) - 1, i2, symbolTable);
                int i8 = i5 + 1;
                char cCharAt3 = charAt(this.bp + i5);
                while (cCharAt3 != c3) {
                    if (!isWhitespace(cCharAt3)) {
                        this.matchStat = -1;
                        return strAddSymbol;
                    }
                    cCharAt3 = charAt(this.bp + i8);
                    i8++;
                }
                int i9 = this.bp + i8;
                this.bp = i9;
                this.ch = charAt(i9);
                this.matchStat = 3;
                return strAddSymbol;
            }
            i2 = (i2 * 31) + cCharAt2;
            if (cCharAt2 == '\\') {
                this.matchStat = -1;
                return null;
            }
            i4 = i5;
        }
    }

    public final void scanTrue() {
        if (this.ch != 't') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'r') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'u') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse true");
        }
        next();
        char c3 = this.ch;
        if (c3 != ' ' && c3 != ',' && c3 != '}' && c3 != ']' && c3 != '\n' && c3 != '\r' && c3 != '\t' && c3 != 26 && c3 != '\f' && c3 != '\b' && c3 != ':' && c3 != '/') {
            throw new JSONException("scan true error");
        }
        this.token = 6;
    }

    public final int scanType(String str) {
        this.matchStat = 0;
        char[] cArr = typeFieldName;
        if (!charArrayCompare(cArr)) {
            return -2;
        }
        int length = this.bp + cArr.length;
        int length2 = str.length();
        for (int i2 = 0; i2 < length2; i2++) {
            if (str.charAt(i2) != charAt(length + i2)) {
                return -1;
            }
        }
        int i3 = length + length2;
        if (charAt(i3) != '\"') {
            return -1;
        }
        int i4 = i3 + 1;
        char cCharAt = charAt(i4);
        this.ch = cCharAt;
        if (cCharAt == ',') {
            int i5 = i4 + 1;
            this.ch = charAt(i5);
            this.bp = i5;
            this.token = 16;
            return 3;
        }
        if (cCharAt == '}') {
            i4++;
            char cCharAt2 = charAt(i4);
            this.ch = cCharAt2;
            if (cCharAt2 == ',') {
                this.token = 16;
                i4++;
                this.ch = charAt(i4);
            } else if (cCharAt2 == ']') {
                this.token = 15;
                i4++;
                this.ch = charAt(i4);
            } else if (cCharAt2 == '}') {
                this.token = 13;
                i4++;
                this.ch = charAt(i4);
            } else {
                if (cCharAt2 != 26) {
                    return -1;
                }
                this.token = 20;
            }
            this.matchStat = 4;
        }
        this.bp = i4;
        return this.matchStat;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanTypeName(SymbolTable symbolTable) {
        return null;
    }

    public UUID scanUUID(char c3) {
        char cCharAt;
        int i2;
        UUID uuid;
        int i3;
        char c4;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp + 0);
        int i16 = 13;
        char c5 = 4;
        if (cCharAt2 == '\"') {
            int iIndexOf = indexOf('\"', this.bp + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int i17 = this.bp + 1;
            int i18 = iIndexOf - i17;
            char c6 = 'f';
            char c7 = '9';
            char c8 = 'A';
            char c9 = 'a';
            if (i18 == 36) {
                int i19 = 0;
                long j2 = 0;
                while (i19 < 8) {
                    char cCharAt3 = charAt(i17 + i19);
                    if (cCharAt3 < '0' || cCharAt3 > '9') {
                        if (cCharAt3 >= 'a' && cCharAt3 <= c6) {
                            i14 = cCharAt3 - 'a';
                        } else {
                            if (cCharAt3 < 'A' || cCharAt3 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i14 = cCharAt3 - 'A';
                        }
                        i15 = i14 + 10;
                    } else {
                        i15 = cCharAt3 - '0';
                    }
                    j2 = (j2 << 4) | i15;
                    i19++;
                    c6 = 'f';
                }
                int i20 = 9;
                while (i20 < i16) {
                    char cCharAt4 = charAt(i17 + i20);
                    if (cCharAt4 < '0' || cCharAt4 > '9') {
                        if (cCharAt4 >= 'a' && cCharAt4 <= 'f') {
                            i12 = cCharAt4 - 'a';
                        } else {
                            if (cCharAt4 < c8 || cCharAt4 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i12 = cCharAt4 - 'A';
                        }
                        i13 = i12 + 10;
                    } else {
                        i13 = cCharAt4 - '0';
                    }
                    j2 = (j2 << 4) | i13;
                    i20++;
                    i16 = 13;
                    c8 = 'A';
                }
                int i21 = 14;
                long j3 = j2;
                while (i21 < 18) {
                    char cCharAt5 = charAt(i17 + i21);
                    if (cCharAt5 < '0' || cCharAt5 > '9') {
                        if (cCharAt5 >= c9 && cCharAt5 <= 'f') {
                            i10 = cCharAt5 - 'a';
                        } else {
                            if (cCharAt5 < 'A' || cCharAt5 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i10 = cCharAt5 - 'A';
                        }
                        i11 = i10 + 10;
                    } else {
                        i11 = cCharAt5 - '0';
                    }
                    j3 = (j3 << c5) | i11;
                    i21++;
                    c9 = 'a';
                    c5 = 4;
                }
                long j4 = 0;
                for (int i22 = 19; i22 < 23; i22++) {
                    char cCharAt6 = charAt(i17 + i22);
                    if (cCharAt6 < '0' || cCharAt6 > '9') {
                        if (cCharAt6 >= 'a' && cCharAt6 <= 'f') {
                            i8 = cCharAt6 - 'a';
                        } else {
                            if (cCharAt6 < 'A' || cCharAt6 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i8 = cCharAt6 - 'A';
                        }
                        i9 = i8 + 10;
                    } else {
                        i9 = cCharAt6 - '0';
                    }
                    j4 = (j4 << 4) | i9;
                }
                int i23 = 24;
                long j5 = j4;
                int i24 = 36;
                while (i23 < i24) {
                    char cCharAt7 = charAt(i17 + i23);
                    if (cCharAt7 < '0' || cCharAt7 > c7) {
                        if (cCharAt7 >= 'a' && cCharAt7 <= 'f') {
                            i6 = cCharAt7 - 'a';
                        } else {
                            if (cCharAt7 < 'A' || cCharAt7 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i6 = cCharAt7 - 'A';
                        }
                        i7 = i6 + 10;
                    } else {
                        i7 = cCharAt7 - '0';
                    }
                    j5 = (j5 << 4) | i7;
                    i23++;
                    i24 = 36;
                    c7 = '9';
                }
                uuid = new UUID(j3, j5);
                int i25 = this.bp;
                int i26 = (iIndexOf - (i25 + 1)) + 1 + 1;
                i2 = i26 + 1;
                cCharAt = charAt(i25 + i26);
            } else {
                if (i18 != 32) {
                    this.matchStat = -1;
                    return null;
                }
                long j6 = 0;
                for (int i27 = 0; i27 < 16; i27++) {
                    char cCharAt8 = charAt(i17 + i27);
                    if (cCharAt8 < '0' || cCharAt8 > '9') {
                        if (cCharAt8 >= 'a' && cCharAt8 <= 'f') {
                            i4 = cCharAt8 - 'a';
                        } else {
                            if (cCharAt8 < 'A' || cCharAt8 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i4 = cCharAt8 - 'A';
                        }
                        i5 = i4 + 10;
                    } else {
                        i5 = cCharAt8 - '0';
                    }
                    j6 = (j6 << 4) | i5;
                }
                int i28 = 16;
                long j7 = 0;
                for (int i29 = 32; i28 < i29; i29 = 32) {
                    char cCharAt9 = charAt(i17 + i28);
                    if (cCharAt9 < '0' || cCharAt9 > '9') {
                        if (cCharAt9 >= 'a' && cCharAt9 <= 'f') {
                            i3 = (cCharAt9 - 'a') + 10;
                        } else {
                            if (cCharAt9 < 'A' || cCharAt9 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i3 = (cCharAt9 - 'A') + 10;
                        }
                        c4 = 4;
                    } else {
                        i3 = cCharAt9 - '0';
                        c4 = 4;
                    }
                    j7 = (j7 << c4) | i3;
                    i28++;
                }
                uuid = new UUID(j6, j7);
                int i30 = this.bp;
                int i31 = (iIndexOf - (i30 + 1)) + 1 + 1;
                i2 = i31 + 1;
                cCharAt = charAt(i30 + i31);
            }
        } else {
            if (cCharAt2 != 'n' || charAt(this.bp + 1) != 'u' || charAt(this.bp + 2) != 'l' || charAt(this.bp + 3) != 'l') {
                this.matchStat = -1;
                return null;
            }
            cCharAt = charAt(this.bp + 4);
            i2 = 5;
            uuid = null;
        }
        if (cCharAt == ',') {
            int i32 = this.bp + i2;
            this.bp = i32;
            this.ch = charAt(i32);
            this.matchStat = 3;
            return uuid;
        }
        if (cCharAt != ']') {
            this.matchStat = -1;
            return null;
        }
        int i33 = i2 + 1;
        char cCharAt10 = charAt(this.bp + i2);
        if (cCharAt10 == ',') {
            this.token = 16;
            int i34 = this.bp + i33;
            this.bp = i34;
            this.ch = charAt(i34);
        } else if (cCharAt10 == ']') {
            this.token = 15;
            int i35 = this.bp + i33;
            this.bp = i35;
            this.ch = charAt(i35);
        } else if (cCharAt10 == '}') {
            this.token = 13;
            int i36 = this.bp + i33;
            this.bp = i36;
            this.ch = charAt(i36);
        } else {
            if (cCharAt10 != 26) {
                this.matchStat = -1;
                return null;
            }
            this.token = 20;
            this.bp += i33 - 1;
            this.ch = JSONLexer.EOI;
        }
        this.matchStat = 4;
        return uuid;
    }

    public boolean seekArrayToItem(int i2) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long j2, boolean z2) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToFieldDeepScan(long j2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setToken(int i2) {
        this.token = i2;
    }

    public void skipArray() {
        throw new UnsupportedOperationException();
    }

    public void skipComment() {
        char c3;
        next();
        char c4 = this.ch;
        if (c4 == '/') {
            do {
                next();
                c3 = this.ch;
                if (c3 == '\n') {
                    next();
                    return;
                }
            } while (c3 != 26);
            return;
        }
        if (c4 != '*') {
            throw new JSONException("invalid comment");
        }
        next();
        while (true) {
            char c5 = this.ch;
            if (c5 == 26) {
                return;
            }
            if (c5 == '*') {
                next();
                if (this.ch == '/') {
                    next();
                    return;
                }
            } else {
                next();
            }
        }
    }

    public void skipObject() {
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void skipWhitespace() {
        while (true) {
            char c3 = this.ch;
            if (c3 > '/') {
                return;
            }
            if (c3 == ' ' || c3 == '\r' || c3 == '\n' || c3 == '\t' || c3 == '\f' || c3 == '\b') {
                next();
            } else if (c3 != '/') {
                return;
            } else {
                skipComment();
            }
        }
    }

    public final String stringDefaultValue() {
        return this.stringDefaultValue;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String stringVal();

    public abstract String subString(int i2, int i3);

    public abstract char[] sub_chars(int i2, int i3);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int token() {
        return this.token;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String tokenName() {
        return JSONToken.name(this.token);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(int i2) {
        return (i2 & this.features) != 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon(int i2) {
        nextTokenWithChar(':');
    }

    public final void scanNullOrNew(boolean z2) {
        if (this.ch != 'n') {
            throw new JSONException("error parse null or new");
        }
        next();
        char c3 = this.ch;
        if (c3 != 'u') {
            if (c3 != 'e') {
                throw new JSONException("error parse new");
            }
            next();
            if (this.ch != 'w') {
                throw new JSONException("error parse new");
            }
            next();
            char c4 = this.ch;
            if (c4 != ' ' && c4 != ',' && c4 != '}' && c4 != ']' && c4 != '\n' && c4 != '\r' && c4 != '\t' && c4 != 26 && c4 != '\f' && c4 != '\b') {
                throw new JSONException("scan new error");
            }
            this.token = 9;
            return;
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse null");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse null");
        }
        next();
        char c5 = this.ch;
        if (c5 != ' ' && c5 != ',' && c5 != '}' && c5 != ']' && c5 != '\n' && c5 != '\r' && c5 != '\t' && c5 != 26 && ((c5 != ':' || !z2) && c5 != '\f' && c5 != '\b')) {
            throw new JSONException("scan null error");
        }
        this.token = 8;
    }

    public int seekObjectToField(long[] jArr) {
        throw new UnsupportedOperationException();
    }

    public void skipObject(boolean z2) {
        throw new UnsupportedOperationException();
    }

    public final boolean isEnabled(int i2, int i3) {
        return ((this.features & i3) == 0 && (i2 & i3) == 0) ? false : true;
    }

    public int matchField(long j2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable, char c3) throws NumberFormatException {
        String strAddSymbol;
        this.np = this.bp;
        this.sp = 0;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            char next = next();
            if (next == c3) {
                this.token = 4;
                if (!z2) {
                    int i3 = this.np;
                    strAddSymbol = addSymbol(i3 == -1 ? 0 : i3 + 1, this.sp, i2, symbolTable);
                } else {
                    strAddSymbol = symbolTable.addSymbol(this.sbuf, 0, this.sp, i2);
                }
                this.sp = 0;
                next();
                return strAddSymbol;
            }
            if (next == 26) {
                throw new JSONException("unclosed.str");
            }
            if (next == '\\') {
                if (!z2) {
                    int i4 = this.sp;
                    char[] cArr = this.sbuf;
                    if (i4 >= cArr.length) {
                        int length = cArr.length * 2;
                        if (i4 <= length) {
                            i4 = length;
                        }
                        char[] cArr2 = new char[i4];
                        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                        this.sbuf = cArr2;
                    }
                    arrayCopy(this.np + 1, this.sbuf, 0, this.sp);
                    z2 = true;
                }
                char next2 = next();
                if (next2 == '\"') {
                    i2 = (i2 * 31) + 34;
                    putChar('\"');
                } else if (next2 != '\'') {
                    if (next2 != 'F') {
                        if (next2 == '\\') {
                            i2 = (i2 * 31) + 92;
                            putChar('\\');
                        } else if (next2 == 'b') {
                            i2 = (i2 * 31) + 8;
                            putChar('\b');
                        } else if (next2 != 'f') {
                            if (next2 == 'n') {
                                i2 = (i2 * 31) + 10;
                                putChar('\n');
                            } else if (next2 == 'r') {
                                i2 = (i2 * 31) + 13;
                                putChar('\r');
                            } else if (next2 != 'x') {
                                switch (next2) {
                                    case '/':
                                        i2 = (i2 * 31) + 47;
                                        putChar('/');
                                        break;
                                    case '0':
                                        i2 = (i2 * 31) + next2;
                                        putChar((char) 0);
                                        break;
                                    case '1':
                                        i2 = (i2 * 31) + next2;
                                        putChar((char) 1);
                                        break;
                                    case '2':
                                        i2 = (i2 * 31) + next2;
                                        putChar((char) 2);
                                        break;
                                    case '3':
                                        i2 = (i2 * 31) + next2;
                                        putChar((char) 3);
                                        break;
                                    case '4':
                                        i2 = (i2 * 31) + next2;
                                        putChar((char) 4);
                                        break;
                                    case '5':
                                        i2 = (i2 * 31) + next2;
                                        putChar((char) 5);
                                        break;
                                    case '6':
                                        i2 = (i2 * 31) + next2;
                                        putChar((char) 6);
                                        break;
                                    case '7':
                                        i2 = (i2 * 31) + next2;
                                        putChar((char) 7);
                                        break;
                                    default:
                                        switch (next2) {
                                            case 't':
                                                i2 = (i2 * 31) + 9;
                                                putChar('\t');
                                                break;
                                            case 'u':
                                                int i5 = Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16);
                                                i2 = (i2 * 31) + i5;
                                                putChar((char) i5);
                                                break;
                                            case 'v':
                                                i2 = (i2 * 31) + 11;
                                                putChar((char) 11);
                                                break;
                                            default:
                                                this.ch = next2;
                                                throw new JSONException("unclosed.str.lit");
                                        }
                                }
                            } else {
                                char next3 = next();
                                this.ch = next3;
                                char next4 = next();
                                this.ch = next4;
                                int[] iArr = digits;
                                char c4 = (char) ((iArr[next3] * 16) + iArr[next4]);
                                i2 = (i2 * 31) + c4;
                                putChar(c4);
                            }
                        }
                    }
                    i2 = (i2 * 31) + 12;
                    putChar('\f');
                } else {
                    i2 = (i2 * 31) + 39;
                    putChar(CharPool.SINGLE_QUOTE);
                }
            } else {
                i2 = (i2 * 31) + next;
                if (!z2) {
                    this.sp++;
                } else {
                    int i6 = this.sp;
                    char[] cArr3 = this.sbuf;
                    if (i6 == cArr3.length) {
                        putChar(next);
                    } else {
                        this.sp = i6 + 1;
                        cArr3[i6] = next;
                    }
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:108:0x007b A[SYNTHETIC] */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void nextToken(int r11) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.nextToken(int):void");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanString(char c3) {
        this.matchStat = 0;
        char cCharAt = charAt(this.bp + 0);
        if (cCharAt == 'n') {
            if (charAt(this.bp + 1) == 'u' && charAt(this.bp + 1 + 1) == 'l' && charAt(this.bp + 1 + 2) == 'l') {
                if (charAt(this.bp + 4) == c3) {
                    int i2 = this.bp + 5;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        int i3 = 1;
        while (cCharAt != '\"') {
            if (isWhitespace(cCharAt)) {
                cCharAt = charAt(this.bp + i3);
                i3++;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        int i4 = this.bp + i3;
        int iIndexOf = indexOf('\"', i4);
        if (iIndexOf != -1) {
            String strSubString = subString(this.bp + i3, iIndexOf - i4);
            if (strSubString.indexOf(92) != -1) {
                while (true) {
                    int i5 = 0;
                    for (int i6 = iIndexOf - 1; i6 >= 0 && charAt(i6) == '\\'; i6--) {
                        i5++;
                    }
                    if (i5 % 2 == 0) {
                        break;
                    }
                    iIndexOf = indexOf('\"', iIndexOf + 1);
                }
                int i7 = iIndexOf - i4;
                strSubString = readString(sub_chars(this.bp + 1, i7), i7);
            }
            int i8 = i3 + (iIndexOf - i4) + 1;
            int i9 = i8 + 1;
            char cCharAt2 = charAt(this.bp + i8);
            while (cCharAt2 != c3) {
                if (!isWhitespace(cCharAt2)) {
                    if (cCharAt2 == ']') {
                        int i10 = this.bp + i9;
                        this.bp = i10;
                        this.ch = charAt(i10);
                        this.matchStat = -1;
                    }
                    return strSubString;
                }
                cCharAt2 = charAt(this.bp + i9);
                i9++;
            }
            int i11 = this.bp + i9;
            this.bp = i11;
            this.ch = charAt(i11);
            this.matchStat = 3;
            this.token = 16;
            return strSubString;
        }
        throw new JSONException("unclosed str");
    }

    public String[] scanFieldStringArray(char[] cArr, int i2, SymbolTable symbolTable) {
        throw new UnsupportedOperationException();
    }
}
