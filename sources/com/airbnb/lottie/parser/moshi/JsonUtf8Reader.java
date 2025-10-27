package com.airbnb.lottie.parser.moshi;

import androidx.annotation.Nullable;
import cn.hutool.core.util.URLUtil;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import java.io.EOFException;
import java.io.IOException;
import k.a;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import org.apache.http.message.BasicHeaderValueFormatter;

/* loaded from: classes2.dex */
final class JsonUtf8Reader extends JsonReader {
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_BUFFERED_NAME = 15;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_EOF = 18;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_LONG = 16;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_NUMBER = 17;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private final Buffer buffer;
    private int peeked = 0;
    private long peekedLong;
    private int peekedNumberLength;

    @Nullable
    private String peekedString;
    private final BufferedSource source;
    private static final ByteString SINGLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("'\\");
    private static final ByteString DOUBLE_QUOTE_OR_SLASH = ByteString.encodeUtf8(BasicHeaderValueFormatter.UNSAFE_CHARS);
    private static final ByteString UNQUOTED_STRING_TERMINALS = ByteString.encodeUtf8("{}[]:, \n\t\r\f/\\;#=");
    private static final ByteString LINEFEED_OR_CARRIAGE_RETURN = ByteString.encodeUtf8("\n\r");
    private static final ByteString CLOSING_BLOCK_COMMENT = ByteString.encodeUtf8(URLUtil.WAR_URL_SEPARATOR);

    public JsonUtf8Reader(BufferedSource bufferedSource) {
        if (bufferedSource == null) {
            throw new NullPointerException("source == null");
        }
        this.source = bufferedSource;
        this.buffer = bufferedSource.getBufferField();
        pushScope(6);
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private int doPeek() throws IOException {
        int[] iArr = this.scopes;
        int i2 = this.stackSize;
        int i3 = iArr[i2 - 1];
        if (i3 == 1) {
            iArr[i2 - 1] = 2;
        } else if (i3 == 2) {
            int iNextNonWhitespace = nextNonWhitespace(true);
            this.buffer.readByte();
            if (iNextNonWhitespace != 44) {
                if (iNextNonWhitespace != 59) {
                    if (iNextNonWhitespace != 93) {
                        throw syntaxError("Unterminated array");
                    }
                    this.peeked = 4;
                    return 4;
                }
                checkLenient();
            }
        } else {
            if (i3 == 3 || i3 == 5) {
                iArr[i2 - 1] = 4;
                if (i3 == 5) {
                    int iNextNonWhitespace2 = nextNonWhitespace(true);
                    this.buffer.readByte();
                    if (iNextNonWhitespace2 != 44) {
                        if (iNextNonWhitespace2 != 59) {
                            if (iNextNonWhitespace2 != 125) {
                                throw syntaxError("Unterminated object");
                            }
                            this.peeked = 2;
                            return 2;
                        }
                        checkLenient();
                    }
                }
                int iNextNonWhitespace3 = nextNonWhitespace(true);
                if (iNextNonWhitespace3 == 34) {
                    this.buffer.readByte();
                    this.peeked = 13;
                    return 13;
                }
                if (iNextNonWhitespace3 == 39) {
                    this.buffer.readByte();
                    checkLenient();
                    this.peeked = 12;
                    return 12;
                }
                if (iNextNonWhitespace3 != 125) {
                    checkLenient();
                    if (!isLiteral((char) iNextNonWhitespace3)) {
                        throw syntaxError("Expected name");
                    }
                    this.peeked = 14;
                    return 14;
                }
                if (i3 == 5) {
                    throw syntaxError("Expected name");
                }
                this.buffer.readByte();
                this.peeked = 2;
                return 2;
            }
            if (i3 == 4) {
                iArr[i2 - 1] = 5;
                int iNextNonWhitespace4 = nextNonWhitespace(true);
                this.buffer.readByte();
                if (iNextNonWhitespace4 != 58) {
                    if (iNextNonWhitespace4 != 61) {
                        throw syntaxError("Expected ':'");
                    }
                    checkLenient();
                    if (this.source.request(1L) && this.buffer.getByte(0L) == 62) {
                        this.buffer.readByte();
                    }
                }
            } else if (i3 == 6) {
                iArr[i2 - 1] = 7;
            } else if (i3 == 7) {
                if (nextNonWhitespace(false) == -1) {
                    this.peeked = 18;
                    return 18;
                }
                checkLenient();
            } else if (i3 == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
        }
        int iNextNonWhitespace5 = nextNonWhitespace(true);
        if (iNextNonWhitespace5 == 34) {
            this.buffer.readByte();
            this.peeked = 9;
            return 9;
        }
        if (iNextNonWhitespace5 == 39) {
            checkLenient();
            this.buffer.readByte();
            this.peeked = 8;
            return 8;
        }
        if (iNextNonWhitespace5 != 44 && iNextNonWhitespace5 != 59) {
            if (iNextNonWhitespace5 == 91) {
                this.buffer.readByte();
                this.peeked = 3;
                return 3;
            }
            if (iNextNonWhitespace5 != 93) {
                if (iNextNonWhitespace5 == 123) {
                    this.buffer.readByte();
                    this.peeked = 1;
                    return 1;
                }
                int iPeekKeyword = peekKeyword();
                if (iPeekKeyword != 0) {
                    return iPeekKeyword;
                }
                int iPeekNumber = peekNumber();
                if (iPeekNumber != 0) {
                    return iPeekNumber;
                }
                if (!isLiteral(this.buffer.getByte(0L))) {
                    throw syntaxError("Expected value");
                }
                checkLenient();
                this.peeked = 10;
                return 10;
            }
            if (i3 == 1) {
                this.buffer.readByte();
                this.peeked = 4;
                return 4;
            }
        }
        if (i3 != 1 && i3 != 2) {
            throw syntaxError("Unexpected value");
        }
        checkLenient();
        this.peeked = 7;
        return 7;
    }

    private int findName(String str, JsonReader.Options options) {
        int length = options.strings.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.equals(options.strings[i2])) {
                this.peeked = 0;
                this.pathNames[this.stackSize - 1] = str;
                return i2;
            }
        }
        return -1;
    }

    private int findString(String str, JsonReader.Options options) {
        int length = options.strings.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.equals(options.strings[i2])) {
                this.peeked = 0;
                int[] iArr = this.pathIndices;
                int i3 = this.stackSize - 1;
                iArr[i3] = iArr[i3] + 1;
                return i2;
            }
        }
        return -1;
    }

    private boolean isLiteral(int i2) throws IOException {
        if (i2 == 9 || i2 == 10 || i2 == 12 || i2 == 13 || i2 == 32) {
            return false;
        }
        if (i2 != 35) {
            if (i2 == 44) {
                return false;
            }
            if (i2 != 47 && i2 != 61) {
                if (i2 == 123 || i2 == 125 || i2 == 58) {
                    return false;
                }
                if (i2 != 59) {
                    switch (i2) {
                        case 91:
                        case 93:
                            return false;
                        case 92:
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        checkLenient();
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0025, code lost:
    
        r6.buffer.skip(r3 - 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
    
        if (r1 != 47) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0039, code lost:
    
        if (r6.source.request(2) != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003b, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003c, code lost:
    
        checkLenient();
        r3 = r6.buffer.getByte(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0049, code lost:
    
        if (r3 == 42) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004b, code lost:
    
        if (r3 == 47) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004d, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004e, code lost:
    
        r6.buffer.readByte();
        r6.buffer.readByte();
        skipToEndOfLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005c, code lost:
    
        r6.buffer.readByte();
        r6.buffer.readByte();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006a, code lost:
    
        if (skipToEndOfBlockComment() == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0073, code lost:
    
        throw syntaxError("Unterminated comment");
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0076, code lost:
    
        if (r1 != 35) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0078, code lost:
    
        checkLenient();
        skipToEndOfLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007f, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int nextNonWhitespace(boolean r7) throws java.io.IOException {
        /*
            r6 = this;
            r0 = 0
        L1:
            r1 = r0
        L2:
            okio.BufferedSource r2 = r6.source
            int r3 = r1 + 1
            long r4 = (long) r3
            boolean r2 = r2.request(r4)
            if (r2 == 0) goto L82
            okio.Buffer r2 = r6.buffer
            long r4 = (long) r1
            byte r1 = r2.getByte(r4)
            r2 = 10
            if (r1 == r2) goto L80
            r2 = 32
            if (r1 == r2) goto L80
            r2 = 13
            if (r1 == r2) goto L80
            r2 = 9
            if (r1 != r2) goto L25
            goto L80
        L25:
            okio.Buffer r2 = r6.buffer
            int r3 = r3 + (-1)
            long r3 = (long) r3
            r2.skip(r3)
            r2 = 47
            if (r1 != r2) goto L74
            okio.BufferedSource r3 = r6.source
            r4 = 2
            boolean r3 = r3.request(r4)
            if (r3 != 0) goto L3c
            return r1
        L3c:
            r6.checkLenient()
            okio.Buffer r3 = r6.buffer
            r4 = 1
            byte r3 = r3.getByte(r4)
            r4 = 42
            if (r3 == r4) goto L5c
            if (r3 == r2) goto L4e
            return r1
        L4e:
            okio.Buffer r1 = r6.buffer
            r1.readByte()
            okio.Buffer r1 = r6.buffer
            r1.readByte()
            r6.skipToEndOfLine()
            goto L1
        L5c:
            okio.Buffer r1 = r6.buffer
            r1.readByte()
            okio.Buffer r1 = r6.buffer
            r1.readByte()
            boolean r1 = r6.skipToEndOfBlockComment()
            if (r1 == 0) goto L6d
            goto L1
        L6d:
            java.lang.String r7 = "Unterminated comment"
            com.airbnb.lottie.parser.moshi.JsonEncodingException r7 = r6.syntaxError(r7)
            throw r7
        L74:
            r2 = 35
            if (r1 != r2) goto L7f
            r6.checkLenient()
            r6.skipToEndOfLine()
            goto L1
        L7f:
            return r1
        L80:
            r1 = r3
            goto L2
        L82:
            if (r7 != 0) goto L86
            r7 = -1
            return r7
        L86:
            java.io.EOFException r7 = new java.io.EOFException
            java.lang.String r0 = "End of input"
            r7.<init>(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.moshi.JsonUtf8Reader.nextNonWhitespace(boolean):int");
    }

    private String nextQuotedValue(ByteString byteString) throws IOException {
        StringBuilder sb = null;
        while (true) {
            long jIndexOfElement = this.source.indexOfElement(byteString);
            if (jIndexOfElement == -1) {
                throw syntaxError("Unterminated string");
            }
            if (this.buffer.getByte(jIndexOfElement) != 92) {
                if (sb == null) {
                    String utf8 = this.buffer.readUtf8(jIndexOfElement);
                    this.buffer.readByte();
                    return utf8;
                }
                sb.append(this.buffer.readUtf8(jIndexOfElement));
                this.buffer.readByte();
                return sb.toString();
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(this.buffer.readUtf8(jIndexOfElement));
            this.buffer.readByte();
            sb.append(readEscapeCharacter());
        }
    }

    private String nextUnquotedValue() throws IOException {
        long jIndexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        return jIndexOfElement != -1 ? this.buffer.readUtf8(jIndexOfElement) : this.buffer.readUtf8();
    }

    private int peekKeyword() throws IOException {
        String str;
        String str2;
        int i2;
        byte b3 = this.buffer.getByte(0L);
        if (b3 == 116 || b3 == 84) {
            str = a.f27523u;
            str2 = "TRUE";
            i2 = 5;
        } else if (b3 == 102 || b3 == 70) {
            str = a.f27524v;
            str2 = "FALSE";
            i2 = 6;
        } else {
            if (b3 != 110 && b3 != 78) {
                return 0;
            }
            str = "null";
            str2 = PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL;
            i2 = 7;
        }
        int length = str.length();
        int i3 = 1;
        while (i3 < length) {
            int i4 = i3 + 1;
            if (!this.source.request(i4)) {
                return 0;
            }
            byte b4 = this.buffer.getByte(i3);
            if (b4 != str.charAt(i3) && b4 != str2.charAt(i3)) {
                return 0;
            }
            i3 = i4;
        }
        if (this.source.request(length + 1) && isLiteral(this.buffer.getByte(length))) {
            return 0;
        }
        this.buffer.skip(length);
        this.peeked = i2;
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0081, code lost:
    
        if (isLiteral(r11) != false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0083, code lost:
    
        if (r6 != 2) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0085, code lost:
    
        if (r7 == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x008b, code lost:
    
        if (r8 != Long.MIN_VALUE) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x008d, code lost:
    
        if (r10 == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0091, code lost:
    
        if (r8 != 0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0093, code lost:
    
        if (r10 != false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0095, code lost:
    
        if (r10 == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0098, code lost:
    
        r8 = -r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0099, code lost:
    
        r16.peekedLong = r8;
        r16.buffer.skip(r5);
        r16.peeked = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00a5, code lost:
    
        return 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00a6, code lost:
    
        if (r6 == 2) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00a9, code lost:
    
        if (r6 == 4) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ac, code lost:
    
        if (r6 != 7) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00af, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00b0, code lost:
    
        r16.peekedNumberLength = r5;
        r16.peeked = 17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00b6, code lost:
    
        return 17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00b7, code lost:
    
        return 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int peekNumber() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.moshi.JsonUtf8Reader.peekNumber():int");
    }

    private char readEscapeCharacter() throws IOException {
        int i2;
        int i3;
        if (!this.source.request(1L)) {
            throw syntaxError("Unterminated escape sequence");
        }
        byte b3 = this.buffer.readByte();
        if (b3 == 10 || b3 == 34 || b3 == 39 || b3 == 47 || b3 == 92) {
            return (char) b3;
        }
        if (b3 == 98) {
            return '\b';
        }
        if (b3 == 102) {
            return '\f';
        }
        if (b3 == 110) {
            return '\n';
        }
        if (b3 == 114) {
            return '\r';
        }
        if (b3 == 116) {
            return '\t';
        }
        if (b3 != 117) {
            if (this.lenient) {
                return (char) b3;
            }
            throw syntaxError("Invalid escape sequence: \\" + ((char) b3));
        }
        if (!this.source.request(4L)) {
            throw new EOFException("Unterminated escape sequence at path " + getPath());
        }
        char c3 = 0;
        for (int i4 = 0; i4 < 4; i4++) {
            byte b4 = this.buffer.getByte(i4);
            char c4 = (char) (c3 << 4);
            if (b4 < 48 || b4 > 57) {
                if (b4 >= 97 && b4 <= 102) {
                    i2 = b4 - 97;
                } else {
                    if (b4 < 65 || b4 > 70) {
                        throw syntaxError("\\u" + this.buffer.readUtf8(4L));
                    }
                    i2 = b4 - 65;
                }
                i3 = i2 + 10;
            } else {
                i3 = b4 - 48;
            }
            c3 = (char) (c4 + i3);
        }
        this.buffer.skip(4L);
        return c3;
    }

    private void skipQuotedValue(ByteString byteString) throws IOException {
        while (true) {
            long jIndexOfElement = this.source.indexOfElement(byteString);
            if (jIndexOfElement == -1) {
                throw syntaxError("Unterminated string");
            }
            if (this.buffer.getByte(jIndexOfElement) != 92) {
                this.buffer.skip(jIndexOfElement + 1);
                return;
            } else {
                this.buffer.skip(jIndexOfElement + 1);
                readEscapeCharacter();
            }
        }
    }

    private boolean skipToEndOfBlockComment() throws IOException {
        long jIndexOf = this.source.indexOf(CLOSING_BLOCK_COMMENT);
        boolean z2 = jIndexOf != -1;
        Buffer buffer = this.buffer;
        buffer.skip(z2 ? jIndexOf + r1.size() : buffer.size());
        return z2;
    }

    private void skipToEndOfLine() throws IOException {
        long jIndexOfElement = this.source.indexOfElement(LINEFEED_OR_CARRIAGE_RETURN);
        Buffer buffer = this.buffer;
        buffer.skip(jIndexOfElement != -1 ? jIndexOfElement + 1 : buffer.size());
    }

    private void skipUnquotedValue() throws IOException {
        long jIndexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        Buffer buffer = this.buffer;
        if (jIndexOfElement == -1) {
            jIndexOfElement = buffer.size();
        }
        buffer.skip(jIndexOfElement);
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void beginArray() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 3) {
            pushScope(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
        } else {
            throw new JsonDataException("Expected BEGIN_ARRAY but was " + peek() + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void beginObject() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 1) {
            pushScope(3);
            this.peeked = 0;
            return;
        }
        throw new JsonDataException("Expected BEGIN_OBJECT but was " + peek() + " at path " + getPath());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.peeked = 0;
        this.scopes[0] = 8;
        this.stackSize = 1;
        this.buffer.clear();
        this.source.close();
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void endArray() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek != 4) {
            throw new JsonDataException("Expected END_ARRAY but was " + peek() + " at path " + getPath());
        }
        int i2 = this.stackSize - 1;
        this.stackSize = i2;
        int[] iArr = this.pathIndices;
        int i3 = i2 - 1;
        iArr[i3] = iArr[i3] + 1;
        this.peeked = 0;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void endObject() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek != 2) {
            throw new JsonDataException("Expected END_OBJECT but was " + peek() + " at path " + getPath());
        }
        int i2 = this.stackSize - 1;
        this.stackSize = i2;
        this.pathNames[i2] = null;
        int[] iArr = this.pathIndices;
        int i3 = i2 - 1;
        iArr[i3] = iArr[i3] + 1;
        this.peeked = 0;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public boolean hasNext() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        return (iDoPeek == 2 || iDoPeek == 4 || iDoPeek == 18) ? false : true;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public boolean nextBoolean() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return true;
        }
        if (iDoPeek == 6) {
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return false;
        }
        throw new JsonDataException("Expected a boolean but was " + peek() + " at path " + getPath());
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public double nextDouble() throws IOException, NumberFormatException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 16) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return this.peekedLong;
        }
        if (iDoPeek == 17) {
            this.peekedString = this.buffer.readUtf8(this.peekedNumberLength);
        } else if (iDoPeek == 9) {
            this.peekedString = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 8) {
            this.peekedString = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 10) {
            this.peekedString = nextUnquotedValue();
        } else if (iDoPeek != 11) {
            throw new JsonDataException("Expected a double but was " + peek() + " at path " + getPath());
        }
        this.peeked = 11;
        try {
            double d3 = Double.parseDouble(this.peekedString);
            if (this.lenient || !(Double.isNaN(d3) || Double.isInfinite(d3))) {
                this.peekedString = null;
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i3 = this.stackSize - 1;
                iArr2[i3] = iArr2[i3] + 1;
                return d3;
            }
            throw new JsonEncodingException("JSON forbids NaN and infinities: " + d3 + " at path " + getPath());
        } catch (NumberFormatException unused) {
            throw new JsonDataException("Expected a double but was " + this.peekedString + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public int nextInt() throws IOException, NumberFormatException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 16) {
            long j2 = this.peekedLong;
            int i2 = (int) j2;
            if (j2 == i2) {
                this.peeked = 0;
                int[] iArr = this.pathIndices;
                int i3 = this.stackSize - 1;
                iArr[i3] = iArr[i3] + 1;
                return i2;
            }
            throw new JsonDataException("Expected an int but was " + this.peekedLong + " at path " + getPath());
        }
        if (iDoPeek == 17) {
            this.peekedString = this.buffer.readUtf8(this.peekedNumberLength);
        } else if (iDoPeek == 9 || iDoPeek == 8) {
            String strNextQuotedValue = iDoPeek == 9 ? nextQuotedValue(DOUBLE_QUOTE_OR_SLASH) : nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
            this.peekedString = strNextQuotedValue;
            try {
                int i4 = Integer.parseInt(strNextQuotedValue);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i5 = this.stackSize - 1;
                iArr2[i5] = iArr2[i5] + 1;
                return i4;
            } catch (NumberFormatException unused) {
            }
        } else if (iDoPeek != 11) {
            throw new JsonDataException("Expected an int but was " + peek() + " at path " + getPath());
        }
        this.peeked = 11;
        try {
            double d3 = Double.parseDouble(this.peekedString);
            int i6 = (int) d3;
            if (i6 == d3) {
                this.peekedString = null;
                this.peeked = 0;
                int[] iArr3 = this.pathIndices;
                int i7 = this.stackSize - 1;
                iArr3[i7] = iArr3[i7] + 1;
                return i6;
            }
            throw new JsonDataException("Expected an int but was " + this.peekedString + " at path " + getPath());
        } catch (NumberFormatException unused2) {
            throw new JsonDataException("Expected an int but was " + this.peekedString + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public String nextName() throws IOException {
        String strNextQuotedValue;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 14) {
            strNextQuotedValue = nextUnquotedValue();
        } else if (iDoPeek == 13) {
            strNextQuotedValue = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 12) {
            strNextQuotedValue = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else {
            if (iDoPeek != 15) {
                throw new JsonDataException("Expected a name but was " + peek() + " at path " + getPath());
            }
            strNextQuotedValue = this.peekedString;
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = strNextQuotedValue;
        return strNextQuotedValue;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public String nextString() throws IOException {
        String utf8;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 10) {
            utf8 = nextUnquotedValue();
        } else if (iDoPeek == 9) {
            utf8 = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 8) {
            utf8 = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 11) {
            utf8 = this.peekedString;
            this.peekedString = null;
        } else if (iDoPeek == 16) {
            utf8 = Long.toString(this.peekedLong);
        } else {
            if (iDoPeek != 17) {
                throw new JsonDataException("Expected a string but was " + peek() + " at path " + getPath());
            }
            utf8 = this.buffer.readUtf8(this.peekedNumberLength);
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr[i2] = iArr[i2] + 1;
        return utf8;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public JsonReader.Token peek() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        switch (iDoPeek) {
            case 1:
                return JsonReader.Token.BEGIN_OBJECT;
            case 2:
                return JsonReader.Token.END_OBJECT;
            case 3:
                return JsonReader.Token.BEGIN_ARRAY;
            case 4:
                return JsonReader.Token.END_ARRAY;
            case 5:
            case 6:
                return JsonReader.Token.BOOLEAN;
            case 7:
                return JsonReader.Token.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonReader.Token.STRING;
            case 12:
            case 13:
            case 14:
            case 15:
                return JsonReader.Token.NAME;
            case 16:
            case 17:
                return JsonReader.Token.NUMBER;
            case 18:
                return JsonReader.Token.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public int selectName(JsonReader.Options options) throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek < 12 || iDoPeek > 15) {
            return -1;
        }
        if (iDoPeek == 15) {
            return findName(this.peekedString, options);
        }
        int iSelect = this.source.select(options.doubleQuoteSuffix);
        if (iSelect != -1) {
            this.peeked = 0;
            this.pathNames[this.stackSize - 1] = options.strings[iSelect];
            return iSelect;
        }
        String str = this.pathNames[this.stackSize - 1];
        String strNextName = nextName();
        int iFindName = findName(strNextName, options);
        if (iFindName == -1) {
            this.peeked = 15;
            this.peekedString = strNextName;
            this.pathNames[this.stackSize - 1] = str;
        }
        return iFindName;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void skipName() throws IOException {
        if (this.failOnUnknown) {
            throw new JsonDataException("Cannot skip unexpected " + peek() + " at " + getPath());
        }
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 14) {
            skipUnquotedValue();
        } else if (iDoPeek == 13) {
            skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 12) {
            skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (iDoPeek != 15) {
            throw new JsonDataException("Expected a name but was " + peek() + " at path " + getPath());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = "null";
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void skipValue() throws IOException {
        if (this.failOnUnknown) {
            throw new JsonDataException("Cannot skip unexpected " + peek() + " at " + getPath());
        }
        int i2 = 0;
        do {
            int iDoPeek = this.peeked;
            if (iDoPeek == 0) {
                iDoPeek = doPeek();
            }
            if (iDoPeek == 3) {
                pushScope(1);
            } else if (iDoPeek == 1) {
                pushScope(3);
            } else {
                if (iDoPeek == 4) {
                    i2--;
                    if (i2 < 0) {
                        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                    }
                    this.stackSize--;
                } else if (iDoPeek == 2) {
                    i2--;
                    if (i2 < 0) {
                        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                    }
                    this.stackSize--;
                } else if (iDoPeek == 14 || iDoPeek == 10) {
                    skipUnquotedValue();
                } else if (iDoPeek == 9 || iDoPeek == 13) {
                    skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
                } else if (iDoPeek == 8 || iDoPeek == 12) {
                    skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
                } else if (iDoPeek == 17) {
                    this.buffer.skip(this.peekedNumberLength);
                } else if (iDoPeek == 18) {
                    throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                }
                this.peeked = 0;
            }
            i2++;
            this.peeked = 0;
        } while (i2 != 0);
        int[] iArr = this.pathIndices;
        int i3 = this.stackSize;
        int i4 = i3 - 1;
        iArr[i4] = iArr[i4] + 1;
        this.pathNames[i3 - 1] = "null";
    }

    public String toString() {
        return "JsonReader(" + this.source + ")";
    }
}
