package org.eclipse.jetty.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class MultiPartInputStream {
    private static final Logger LOG = Log.getLogger((Class<?>) MultiPartInputStream.class);
    public static final MultipartConfigElement __DEFAULT_MULTIPART_CONFIG = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
    protected MultipartConfigElement _config;
    protected String _contentType;
    protected File _contextTmpDir;
    protected boolean _deleteOnExit;
    protected InputStream _in;
    protected MultiMap<String> _parts;
    protected File _tmpDir;

    public static class Base64InputStream extends InputStream {
        byte[] _buffer;
        ReadLineInputStream _in;
        String _line;
        int _pos;

        public Base64InputStream(ReadLineInputStream readLineInputStream) {
            this._in = readLineInputStream;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            byte[] bArr = this._buffer;
            if (bArr == null || this._pos >= bArr.length) {
                String line = this._in.readLine();
                this._line = line;
                if (line == null) {
                    return -1;
                }
                if (line.startsWith("--")) {
                    this._buffer = (this._line + "\r\n").getBytes();
                } else if (this._line.length() == 0) {
                    this._buffer = "\r\n".getBytes();
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(((this._line.length() * 4) / 3) + 2);
                    B64Code.decode(this._line, byteArrayOutputStream);
                    byteArrayOutputStream.write(13);
                    byteArrayOutputStream.write(10);
                    this._buffer = byteArrayOutputStream.toByteArray();
                }
                this._pos = 0;
            }
            byte[] bArr2 = this._buffer;
            int i2 = this._pos;
            this._pos = i2 + 1;
            return bArr2[i2];
        }
    }

    public MultiPartInputStream(InputStream inputStream, String str, MultipartConfigElement multipartConfigElement, File file) {
        this._in = new ReadLineInputStream(inputStream);
        this._contentType = str;
        this._config = multipartConfigElement;
        this._contextTmpDir = file;
        if (file == null) {
            this._contextTmpDir = new File(System.getProperty("java.io.tmpdir"));
        }
        if (this._config == null) {
            this._config = new MultipartConfigElement(this._contextTmpDir.getAbsolutePath());
        }
    }

    private String filenameValue(String str) {
        String strTrim = str.substring(str.indexOf(61) + 1).trim();
        if (!strTrim.matches(".??[a-z,A-Z]\\:\\\\[^\\\\].*")) {
            return QuotedStringTokenizer.unquoteOnly(strTrim, true);
        }
        char cCharAt = strTrim.charAt(0);
        if (cCharAt == '\"' || cCharAt == '\'') {
            strTrim = strTrim.substring(1);
        }
        char cCharAt2 = strTrim.charAt(strTrim.length() - 1);
        return (cCharAt2 == '\"' || cCharAt2 == '\'') ? strTrim.substring(0, strTrim.length() - 1) : strTrim;
    }

    private String value(String str, boolean z2) {
        return QuotedStringTokenizer.unquoteOnly(str.substring(str.indexOf(61) + 1).trim());
    }

    public void deleteParts() throws MultiException {
        Collection<Part> parsedParts = getParsedParts();
        MultiException multiException = new MultiException();
        Iterator<Part> it = parsedParts.iterator();
        while (it.hasNext()) {
            try {
                ((MultiPart) it.next()).cleanUp();
            } catch (Exception e2) {
                multiException.add(e2);
            }
        }
        this._parts.clear();
        multiException.ifExceptionThrowMulti();
    }

    public Collection<Part> getParsedParts() {
        MultiMap<String> multiMap = this._parts;
        if (multiMap == null) {
            return Collections.emptyList();
        }
        Collection<Object> collectionValues = multiMap.values();
        ArrayList arrayList = new ArrayList();
        Iterator<Object> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.addAll(LazyList.getList(it.next(), false));
        }
        return arrayList;
    }

    public Part getPart(String str) throws ServletException, IOException {
        parse();
        return (Part) this._parts.getValue(str, 0);
    }

    public Collection<Part> getParts() throws ServletException, IOException {
        parse();
        Collection<Object> collectionValues = this._parts.values();
        ArrayList arrayList = new ArrayList();
        Iterator<Object> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.addAll(LazyList.getList(it.next(), false));
        }
        return arrayList;
    }

    public boolean isDeleteOnExit() {
        return this._deleteOnExit;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x020c, code lost:
    
        if (r14 != r5[r13]) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x020e, code lost:
    
        r13 = r13 + 1;
        r4 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0212, code lost:
    
        if (r6 == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0214, code lost:
    
        r2.write(13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0219, code lost:
    
        if (r7 == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x021b, code lost:
    
        r2.write(10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x021e, code lost:
    
        if (r13 <= 0) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0220, code lost:
    
        r2.write(r5, 0, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0224, code lost:
    
        r2.write(r14);
        r13 = -1;
        r4 = -2;
        r6 = false;
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x022b, code lost:
    
        r12 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x022d, code lost:
    
        if (r14 != r12) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x022f, code lost:
    
        r3.mark(1);
        r12 = r3.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0237, code lost:
    
        if (r12 == 10) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0239, code lost:
    
        r3.reset();
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x023c, code lost:
    
        r12 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x023d, code lost:
    
        if (r13 <= 0) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0242, code lost:
    
        if (r13 < (r5.length - 2)) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0245, code lost:
    
        r21 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0248, code lost:
    
        r21 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x024d, code lost:
    
        if (r13 != (r5.length - 1)) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x024f, code lost:
    
        if (r6 == false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0251, code lost:
    
        r2.write(13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0256, code lost:
    
        if (r7 == false) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0258, code lost:
    
        r2.write(10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x025b, code lost:
    
        r2.write(r5, 0, r13);
        r6 = false;
        r7 = false;
        r13 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0262, code lost:
    
        if (r13 > 0) goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0265, code lost:
    
        if (r14 != (-1)) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0268, code lost:
    
        if (r6 == false) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x026a, code lost:
    
        r2.write(13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x026f, code lost:
    
        if (r7 == false) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0271, code lost:
    
        r2.write(10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0276, code lost:
    
        if (r14 != 13) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0278, code lost:
    
        r6 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x027b, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x027c, code lost:
    
        if (r14 == 10) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x027e, code lost:
    
        if (r12 != 10) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0281, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0283, code lost:
    
        r7 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0285, code lost:
    
        if (r12 != 10) goto L211;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0287, code lost:
    
        r12 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0288, code lost:
    
        r4 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x028c, code lost:
    
        if (r13 != r5.length) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x028e, code lost:
    
        r0 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0290, code lost:
    
        r2.close();
        r2 = r20;
        r4 = r21;
        r3 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x029b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x029c, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x029f, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02a7, code lost:
    
        throw new java.io.IOException("Missing content-disposition");
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x011a, code lost:
    
        if (r13 == null) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x011c, code lost:
    
        r12 = new org.eclipse.jetty.util.QuotedStringTokenizer(r13, r3, r7, r4);
        r16 = r7;
        r7 = null;
        r13 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0129, code lost:
    
        if (r12.hasMoreTokens() == false) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x012b, code lost:
    
        r4 = r12.nextToken().trim();
        r20 = r2;
        r2 = r4.toLowerCase(java.util.Locale.ENGLISH);
        r22 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0143, code lost:
    
        if (r4.startsWith("form-data") == false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0145, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x014e, code lost:
    
        if (r2.startsWith("name=") == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0150, code lost:
    
        r13 = value(r4, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x015d, code lost:
    
        if (r2.startsWith("filename=") == false) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x015f, code lost:
    
        r7 = filenameValue(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0164, code lost:
    
        r2 = r20;
        r3 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x016a, code lost:
    
        r20 = r2;
        r22 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x016e, code lost:
    
        if (r16 != false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0171, code lost:
    
        if (r13 != null) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0173, code lost:
    
        r2 = r20;
        r3 = r22;
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0178, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x017a, code lost:
    
        r2 = new org.eclipse.jetty.util.MultiPartInputStream.MultiPart(r25, r13, r7);
        r2.setHeaders(r6);
        r2.setContentType(r14);
        r25._parts.add(r13, r2);
        r2.open();
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0193, code lost:
    
        if ("base64".equalsIgnoreCase(r15) == false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0195, code lost:
    
        r3 = new org.eclipse.jetty.util.MultiPartInputStream.Base64InputStream((org.eclipse.jetty.util.ReadLineInputStream) r25._in);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01a5, code lost:
    
        if ("quoted-printable".equalsIgnoreCase(r15) == false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01a7, code lost:
    
        r3 = new org.eclipse.jetty.util.MultiPartInputStream.AnonymousClass1(r25, r25._in);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01af, code lost:
    
        r3 = r25._in;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01b1, code lost:
    
        r4 = -2;
        r12 = -2;
        r6 = false;
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01b5, code lost:
    
        r13 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01b6, code lost:
    
        if (r12 == r4) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01b8, code lost:
    
        r14 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01ba, code lost:
    
        r14 = r3.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01c1, code lost:
    
        if (r14 == (-1)) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01c3, code lost:
    
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01d1, code lost:
    
        if (r25._config.getMaxRequestSize() <= 0) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01db, code lost:
    
        if (r10 > r25._config.getMaxRequestSize()) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01fb, code lost:
    
        throw new java.lang.IllegalStateException("Request exceeds maxRequestSize (" + r25._config.getMaxRequestSize() + ")");
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01fc, code lost:
    
        r12 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01fe, code lost:
    
        if (r14 == 13) goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0200, code lost:
    
        if (r14 != 10) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0202, code lost:
    
        r12 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0205, code lost:
    
        if (r13 < 0) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0208, code lost:
    
        if (r13 >= r5.length) goto L102;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parse() throws javax.servlet.ServletException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 849
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.MultiPartInputStream.parse():void");
    }

    public void setDeleteOnExit(boolean z2) {
        this._deleteOnExit = z2;
    }

    public class MultiPart implements Part {
        protected ByteArrayOutputStream2 _bout;
        protected String _contentType;
        protected File _file;
        protected String _filename;
        protected MultiMap<String> _headers;
        protected String _name;
        protected OutputStream _out;
        protected long _size = 0;
        protected boolean _temporary = true;

        public MultiPart(String str, String str2) throws IOException {
            this._name = str;
            this._filename = str2;
        }

        public void cleanUp() throws IOException {
            File file;
            if (this._temporary && (file = this._file) != null && file.exists()) {
                this._file.delete();
            }
        }

        public void close() throws IOException {
            this._out.close();
        }

        public void createFile() throws IOException {
            OutputStream outputStream;
            File fileCreateTempFile = File.createTempFile("MultiPart", "", MultiPartInputStream.this._tmpDir);
            this._file = fileCreateTempFile;
            if (MultiPartInputStream.this._deleteOnExit) {
                fileCreateTempFile.deleteOnExit();
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this._file));
            if (this._size > 0 && (outputStream = this._out) != null) {
                outputStream.flush();
                this._bout.writeTo(bufferedOutputStream);
                this._out.close();
                this._bout = null;
            }
            this._out = bufferedOutputStream;
        }

        public void delete() throws IOException {
            File file = this._file;
            if (file == null || !file.exists()) {
                return;
            }
            this._file.delete();
        }

        public byte[] getBytes() {
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bout;
            if (byteArrayOutputStream2 != null) {
                return byteArrayOutputStream2.toByteArray();
            }
            return null;
        }

        public String getContentDispositionFilename() {
            return this._filename;
        }

        public String getContentType() {
            return this._contentType;
        }

        public File getFile() {
            return this._file;
        }

        public String getHeader(String str) {
            if (str == null) {
                return null;
            }
            return (String) this._headers.getValue(str.toLowerCase(Locale.ENGLISH), 0);
        }

        public Collection<String> getHeaderNames() {
            return this._headers.keySet();
        }

        public Collection<String> getHeaders(String str) {
            return this._headers.getValues(str);
        }

        public InputStream getInputStream() throws IOException {
            return this._file != null ? new BufferedInputStream(new FileInputStream(this._file)) : new ByteArrayInputStream(this._bout.getBuf(), 0, this._bout.size());
        }

        public String getName() {
            return this._name;
        }

        public long getSize() {
            return this._size;
        }

        public void open() throws IOException {
            String str = this._filename;
            if (str != null && str.trim().length() > 0) {
                createFile();
                return;
            }
            ByteArrayOutputStream2 byteArrayOutputStream2 = new ByteArrayOutputStream2();
            this._bout = byteArrayOutputStream2;
            this._out = byteArrayOutputStream2;
        }

        public void setContentType(String str) {
            this._contentType = str;
        }

        public void setHeaders(MultiMap<String> multiMap) {
            this._headers = multiMap;
        }

        public void write(int i2) throws IOException {
            if (MultiPartInputStream.this._config.getMaxFileSize() > 0 && this._size + 1 > MultiPartInputStream.this._config.getMaxFileSize()) {
                throw new IllegalStateException("Multipart Mime part " + this._name + " exceeds max filesize");
            }
            if (MultiPartInputStream.this._config.getFileSizeThreshold() > 0 && this._size + 1 > MultiPartInputStream.this._config.getFileSizeThreshold() && this._file == null) {
                createFile();
            }
            this._out.write(i2);
            this._size++;
        }

        public void write(byte[] bArr, int i2, int i3) throws IOException {
            if (MultiPartInputStream.this._config.getMaxFileSize() > 0 && this._size + i3 > MultiPartInputStream.this._config.getMaxFileSize()) {
                throw new IllegalStateException("Multipart Mime part " + this._name + " exceeds max filesize");
            }
            if (MultiPartInputStream.this._config.getFileSizeThreshold() > 0 && this._size + i3 > MultiPartInputStream.this._config.getFileSizeThreshold() && this._file == null) {
                createFile();
            }
            this._out.write(bArr, i2, i3);
            this._size += i3;
        }

        public void write(String str) throws Throwable {
            BufferedOutputStream bufferedOutputStream;
            if (this._file == null) {
                this._temporary = false;
                this._file = new File(MultiPartInputStream.this._tmpDir, str);
                try {
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this._file));
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = null;
                }
                try {
                    this._bout.writeTo(bufferedOutputStream);
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    this._bout = null;
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    this._bout = null;
                    throw th;
                }
            }
            this._temporary = false;
            File file = new File(MultiPartInputStream.this._tmpDir, str);
            if (this._file.renameTo(file)) {
                this._file = file;
            }
        }
    }
}
