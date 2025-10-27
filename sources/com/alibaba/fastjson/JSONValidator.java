package com.alibaba.fastjson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* loaded from: classes2.dex */
public abstract class JSONValidator implements Cloneable {
    protected char ch;
    protected boolean eof;
    protected Type type;
    protected int pos = -1;
    protected int count = 0;
    protected boolean supportMultiValue = true;

    public static class ReaderValidator extends JSONValidator {
        private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
        private char[] buf;

        /* renamed from: r, reason: collision with root package name */
        final Reader f2626r;
        private int end = -1;
        private int readCount = 0;

        public ReaderValidator(Reader reader) throws IOException {
            this.f2626r = reader;
            ThreadLocal<char[]> threadLocal = bufLocal;
            char[] cArr = threadLocal.get();
            this.buf = cArr;
            if (cArr != null) {
                threadLocal.set(null);
            } else {
                this.buf = new char[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.f2626r.close();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void error() {
            throw new JSONException("error, readCount " + this.readCount + ", valueCount : " + this.count + ", pos " + this.pos);
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void next() throws IOException {
            int i2 = this.pos;
            if (i2 < this.end) {
                char[] cArr = this.buf;
                int i3 = i2 + 1;
                this.pos = i3;
                this.ch = cArr[i3];
                return;
            }
            if (this.eof) {
                return;
            }
            try {
                Reader reader = this.f2626r;
                char[] cArr2 = this.buf;
                int i4 = reader.read(cArr2, 0, cArr2.length);
                this.readCount++;
                if (i4 > 0) {
                    this.ch = this.buf[0];
                    this.pos = 0;
                    this.end = i4 - 1;
                } else {
                    if (i4 == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        return;
                    }
                    this.pos = 0;
                    this.end = 0;
                    this.buf = null;
                    this.ch = (char) 0;
                    this.eof = true;
                    throw new JSONException("read error");
                }
            } catch (IOException unused) {
                throw new JSONException("read error");
            }
        }
    }

    public enum Type {
        Object,
        Array,
        Value
    }

    public static class UTF16Validator extends JSONValidator {
        private final String str;

        public UTF16Validator(String str) {
            this.str = str;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void next() {
            int i2 = this.pos + 1;
            this.pos = i2;
            if (i2 < this.str.length()) {
                this.ch = this.str.charAt(this.pos);
            } else {
                this.ch = (char) 0;
                this.eof = true;
            }
        }
    }

    public static class UTF8InputStreamValidator extends JSONValidator {
        private static final ThreadLocal<byte[]> bufLocal = new ThreadLocal<>();
        private byte[] buf;
        private final InputStream is;
        private int end = -1;
        private int readCount = 0;

        public UTF8InputStreamValidator(InputStream inputStream) throws IOException {
            this.is = inputStream;
            ThreadLocal<byte[]> threadLocal = bufLocal;
            byte[] bArr = threadLocal.get();
            this.buf = bArr;
            if (bArr != null) {
                threadLocal.set(null);
            } else {
                this.buf = new byte[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.is.close();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void error() {
            throw new JSONException("error, readCount " + this.readCount + ", valueCount : " + this.count + ", pos " + this.pos);
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void next() throws IOException {
            int i2 = this.pos;
            if (i2 < this.end) {
                byte[] bArr = this.buf;
                int i3 = i2 + 1;
                this.pos = i3;
                this.ch = (char) bArr[i3];
                return;
            }
            if (this.eof) {
                return;
            }
            try {
                InputStream inputStream = this.is;
                byte[] bArr2 = this.buf;
                int i4 = inputStream.read(bArr2, 0, bArr2.length);
                this.readCount++;
                if (i4 > 0) {
                    this.ch = (char) this.buf[0];
                    this.pos = 0;
                    this.end = i4 - 1;
                } else {
                    if (i4 == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        return;
                    }
                    this.pos = 0;
                    this.end = 0;
                    this.buf = null;
                    this.ch = (char) 0;
                    this.eof = true;
                    throw new JSONException("read error");
                }
            } catch (IOException unused) {
                throw new JSONException("read error");
            }
        }
    }

    public static class UTF8Validator extends JSONValidator {
        private final byte[] bytes;

        public UTF8Validator(byte[] bArr) {
            this.bytes = bArr;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void next() {
            int i2 = this.pos + 1;
            this.pos = i2;
            byte[] bArr = this.bytes;
            if (i2 < bArr.length) {
                this.ch = (char) bArr[i2];
            } else {
                this.ch = (char) 0;
                this.eof = true;
            }
        }
    }

    public static JSONValidator from(String str) {
        return new UTF16Validator(str);
    }

    public static JSONValidator fromUtf8(byte[] bArr) {
        return new UTF8Validator(bArr);
    }

    public static final boolean isWhiteSpace(char c3) {
        return c3 == ' ' || c3 == '\t' || c3 == '\r' || c3 == '\n' || c3 == '\f' || c3 == '\b';
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void any() {
        /*
            Method dump skipped, instructions count: 558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONValidator.any():void");
    }

    public void close() throws IOException {
    }

    public void error() {
        throw new JSONException("error : " + this.pos);
    }

    public void fieldName() {
        next();
        while (true) {
            char c3 = this.ch;
            if (c3 == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                    next();
                } else {
                    next();
                }
            } else {
                if (c3 == '\"') {
                    next();
                    return;
                }
                next();
            }
        }
    }

    public Type getType() {
        return this.type;
    }

    public abstract void next();

    public void skipWhiteSpace() {
        while (isWhiteSpace(this.ch)) {
            next();
        }
    }

    public boolean validate() {
        do {
            any();
            this.count++;
            if (!this.supportMultiValue || this.eof) {
                break;
            }
            skipWhiteSpace();
        } while (!this.eof);
        return true;
    }

    public static JSONValidator from(Reader reader) {
        return new ReaderValidator(reader);
    }

    public static JSONValidator fromUtf8(InputStream inputStream) {
        return new UTF8InputStreamValidator(inputStream);
    }
}
