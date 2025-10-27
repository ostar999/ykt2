package com.plv.business.sub.danmaku.auxiliary;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.caverock.androidsvg.SVGParser;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes4.dex */
public class DanmakuXmlSerializer implements XmlSerializer {
    private static final int WRITE_BUFFER_SIZE = 500;
    private int auto;
    private int depth;
    private String encoding;
    private boolean pending;
    private boolean unicode;
    private BufferedWriter writer;
    private String[] elementStack = new String[12];
    private int[] nspCounts = new int[4];
    private String[] nspStack = new String[8];
    private boolean[] indent = new boolean[4];

    public static class StringUtils {
        public static boolean isEmpty(CharSequence charSequence) {
            return charSequence == null || charSequence.length() == 0;
        }
    }

    private final void check(boolean z2) throws IOException {
        if (!this.pending) {
            return;
        }
        int i2 = this.depth + 1;
        this.depth = i2;
        this.pending = false;
        boolean[] zArr = this.indent;
        if (zArr.length <= i2) {
            boolean[] zArr2 = new boolean[i2 + 4];
            System.arraycopy(zArr, 0, zArr2, 0, i2);
            this.indent = zArr2;
        }
        boolean[] zArr3 = this.indent;
        int i3 = this.depth;
        zArr3[i3] = zArr3[i3 - 1];
        int i4 = this.nspCounts[i3 - 1];
        while (true) {
            int[] iArr = this.nspCounts;
            int i5 = this.depth;
            if (i4 >= iArr[i5]) {
                if (iArr.length <= i5 + 1) {
                    int[] iArr2 = new int[i5 + 8];
                    System.arraycopy(iArr, 0, iArr2, 0, i5 + 1);
                    this.nspCounts = iArr2;
                }
                int[] iArr3 = this.nspCounts;
                int i6 = this.depth;
                iArr3[i6 + 1] = iArr3[i6];
                this.writer.write(z2 ? " />" : ">");
                return;
            }
            this.writer.write(32);
            this.writer.write("xmlns");
            int i7 = i4 * 2;
            if (!StringUtils.isEmpty(this.nspStack[i7])) {
                this.writer.write(58);
                this.writer.write(this.nspStack[i7]);
            } else if (StringUtils.isEmpty(getNamespace()) && !StringUtils.isEmpty(this.nspStack[i7 + 1])) {
                throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
            }
            this.writer.write("=\"");
            writeEscaped(this.nspStack[i7 + 1], 34);
            this.writer.write(34);
            i4++;
        }
    }

    private static void reportInvalidCharacter(char c3) {
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void writeEscaped(java.lang.String r8, int r9) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 0
            r1 = r0
        L2:
            int r2 = r8.length()
            if (r1 >= r2) goto Ld2
            char r2 = r8.charAt(r1)
            r3 = 9
            r4 = 1
            if (r2 == r3) goto Lc8
            r3 = 10
            if (r2 == r3) goto Lc0
            r3 = 13
            if (r2 == r3) goto Lb8
            r3 = 38
            if (r2 == r3) goto Lb0
            r3 = 60
            if (r2 == r3) goto La8
            r3 = 62
            if (r2 == r3) goto La0
            if (r2 != r9) goto L37
            java.io.BufferedWriter r3 = r7.writer
            r5 = 34
            if (r2 != r5) goto L30
            java.lang.String r2 = "&quot;"
            goto L32
        L30:
            java.lang.String r2 = "&apos;"
        L32:
            r3.write(r2)
            goto Lcf
        L37:
            r3 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r3) goto L5a
            r3 = 56319(0xdbff, float:7.892E-41)
            if (r2 > r3) goto L5a
            int r3 = r1 + 1
            int r5 = r8.length()
            if (r3 >= r5) goto L57
            java.io.BufferedWriter r2 = r7.writer
            int r5 = r1 + 2
            java.lang.String r1 = r8.substring(r1, r5)
            r2.write(r1)
            r1 = r3
            goto Lcf
        L57:
            reportInvalidCharacter(r2)
        L5a:
            r3 = 32
            if (r2 < r3) goto L63
            r3 = 55295(0xd7ff, float:7.7485E-41)
            if (r2 <= r3) goto L6d
        L63:
            r3 = 57344(0xe000, float:8.0356E-41)
            if (r2 < r3) goto L6f
            r3 = 65533(0xfffd, float:9.1831E-41)
            if (r2 > r3) goto L6f
        L6d:
            r3 = r4
            goto L70
        L6f:
            r3 = r0
        L70:
            if (r3 != 0) goto L75
            reportInvalidCharacter(r2)
        L75:
            boolean r3 = r7.unicode
            if (r3 != 0) goto L9a
            r3 = 127(0x7f, float:1.78E-43)
            if (r2 >= r3) goto L7e
            goto L9a
        L7e:
            java.io.BufferedWriter r3 = r7.writer
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "&#"
            r5.append(r6)
            r5.append(r2)
            java.lang.String r2 = ";"
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            r3.write(r2)
            goto Lcf
        L9a:
            java.io.BufferedWriter r3 = r7.writer
            r3.write(r2)
            goto Lcf
        La0:
            java.io.BufferedWriter r2 = r7.writer
            java.lang.String r3 = "&gt;"
            r2.write(r3)
            goto Lcf
        La8:
            java.io.BufferedWriter r2 = r7.writer
            java.lang.String r3 = "&lt;"
            r2.write(r3)
            goto Lcf
        Lb0:
            java.io.BufferedWriter r2 = r7.writer
            java.lang.String r3 = "&amp;"
            r2.write(r3)
            goto Lcf
        Lb8:
            java.io.BufferedWriter r2 = r7.writer
            java.lang.String r3 = "\\r"
            r2.write(r3)
            goto Lcf
        Lc0:
            java.io.BufferedWriter r2 = r7.writer
            java.lang.String r3 = "/n"
            r2.write(r3)
            goto Lcf
        Lc8:
            java.io.BufferedWriter r2 = r7.writer
            java.lang.String r3 = "\\t"
            r2.write(r3)
        Lcf:
            int r1 = r1 + r4
            goto L2
        Ld2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.business.sub.danmaku.auxiliary.DanmakuXmlSerializer.writeEscaped(java.lang.String, int):void");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String str, String str2, String str3) throws IOException {
        if (!this.pending) {
            throw new IllegalStateException("illegal position for attribute");
        }
        if (str == null) {
            str = "";
        }
        String prefix = StringUtils.isEmpty(str) ? "" : getPrefix(str, false, true);
        this.writer.write(32);
        if (!StringUtils.isEmpty(prefix)) {
            this.writer.write(prefix);
            this.writer.write(58);
        }
        this.writer.write(str2);
        this.writer.write(61);
        int i2 = str3.indexOf(34) != -1 ? 39 : 34;
        this.writer.write(i2);
        writeEscaped(str3, i2);
        this.writer.write(i2);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) throws IOException {
        check(false);
        char[] charArray = str.replace("]]>", "]]]]><![CDATA[>").toCharArray();
        int length = charArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            char c3 = charArray[i2];
            if (!((c3 >= ' ' && c3 <= 55295) || c3 == '\t' || c3 == '\n' || c3 == '\r' || (c3 >= 57344 && c3 <= 65533))) {
                reportInvalidCharacter(c3);
            }
        }
        this.writer.write("<![CDATA[");
        this.writer.write(charArray, 0, charArray.length);
        this.writer.write("]]>");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) throws IOException {
        check(false);
        this.writer.write("<!--");
        this.writer.write(str);
        this.writer.write("-->");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) throws IOException {
        this.writer.write("<!DOCTYPE");
        this.writer.write(str);
        this.writer.write(">");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IOException {
        while (true) {
            if (this.depth <= 0) {
                flush();
                return;
            } else {
                String[] strArr = this.elementStack;
                endTag(strArr[(r0 * 3) - 3], strArr[(r0 * 3) - 1]);
            }
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String str, String str2) throws IOException {
        if (!this.pending) {
            this.depth--;
        }
        if ((str == null && this.elementStack[this.depth * 3] != null) || ((str != null && !str.equals(this.elementStack[this.depth * 3])) || !this.elementStack[(this.depth * 3) + 2].equals(str2))) {
            throw new IllegalArgumentException("</{" + str + "}" + str2 + "> does not match start");
        }
        if (this.pending) {
            check(true);
            this.depth--;
        } else {
            if (this.indent[this.depth + 1]) {
                this.writer.write("\r\n");
                for (int i2 = 0; i2 < this.depth; i2++) {
                    this.writer.write("  ");
                }
            }
            this.writer.write("</");
            String str3 = this.elementStack[(this.depth * 3) + 1];
            if (!StringUtils.isEmpty(str3)) {
                this.writer.write(str3);
                this.writer.write(58);
            }
            this.writer.write(str2);
            this.writer.write(62);
        }
        int[] iArr = this.nspCounts;
        int i3 = this.depth;
        iArr[i3 + 1] = iArr[i3];
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) throws IOException {
        check(false);
        this.writer.write(38);
        this.writer.write(str);
        this.writer.write(59);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        check(false);
        this.writer.flush();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        return this.pending ? this.depth + 1 : this.depth;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        return "http://xmlpull.org/v1/doc/features.html#indent-output".equals(str) && this.indent[this.depth];
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        if (getDepth() == 0) {
            return null;
        }
        return this.elementStack[(getDepth() * 3) - 1];
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        if (getDepth() == 0) {
            return null;
        }
        return this.elementStack[(getDepth() * 3) - 3];
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z2) {
        try {
            return getPrefix(str, false, z2);
        } catch (IOException e2) {
            PLVCommonLog.exception(new RuntimeException(e2.toString()));
            return null;
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        PLVCommonLog.exception(new RuntimeException("Unsupported property"));
        return null;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) throws IOException {
        text(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) throws IOException {
        check(false);
        this.writer.write("<?");
        this.writer.write(str);
        this.writer.write("?>");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z2) {
        if ("http://xmlpull.org/v1/doc/features.html#indent-output".equals(str)) {
            this.indent[this.depth] = z2;
        } else {
            PLVCommonLog.exception(new RuntimeException("Unsupported Feature"));
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) {
        if (writer instanceof BufferedWriter) {
            this.writer = (BufferedWriter) writer;
        } else {
            this.writer = new BufferedWriter(writer, 500);
        }
        int[] iArr = this.nspCounts;
        iArr[0] = 2;
        iArr[1] = 2;
        String[] strArr = this.nspStack;
        strArr[0] = "";
        strArr[1] = "";
        strArr[2] = AliyunVodHttpCommon.Format.FORMAT_XML;
        strArr[3] = "http://www.w3.org/XML/1998/namespace";
        this.pending = false;
        this.auto = 0;
        this.depth = 0;
        this.unicode = false;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) throws IOException {
        check(false);
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        if (str.equals(getPrefix(str2, true, false))) {
            return;
        }
        int[] iArr = this.nspCounts;
        int i2 = this.depth + 1;
        int i3 = iArr[i2];
        iArr[i2] = i3 + 1;
        int i4 = i3 << 1;
        String[] strArr = this.nspStack;
        int i5 = i4 + 1;
        if (strArr.length < i5) {
            String[] strArr2 = new String[strArr.length + 16];
            System.arraycopy(strArr, 0, strArr2, 0, i4);
            this.nspStack = strArr2;
        }
        String[] strArr3 = this.nspStack;
        strArr3[i4] = str;
        strArr3[i5] = str2;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) {
        PLVCommonLog.exception(new RuntimeException("Unsupported Property:" + obj));
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IOException {
        this.writer.write("<?xml version='1.0' ");
        if (str != null) {
            this.encoding = str;
            if (str.toLowerCase(Locale.US).startsWith("utf")) {
                this.unicode = true;
            }
        }
        if (this.encoding != null) {
            this.writer.write("encoding='");
            this.writer.write(this.encoding);
            this.writer.write("' ");
        }
        if (bool != null) {
            this.writer.write("standalone='");
            this.writer.write(bool.booleanValue() ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
            this.writer.write("' ");
        }
        this.writer.write("?>");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String str, String str2) throws IOException {
        check(false);
        if (this.indent[this.depth]) {
            this.writer.write("\r\n");
            for (int i2 = 0; i2 < this.depth; i2++) {
                this.writer.write("  ");
            }
        }
        int i3 = this.depth * 3;
        String[] strArr = this.elementStack;
        if (strArr.length < i3 + 3) {
            String[] strArr2 = new String[strArr.length + 12];
            System.arraycopy(strArr, 0, strArr2, 0, i3);
            this.elementStack = strArr2;
        }
        String prefix = str == null ? "" : getPrefix(str, true, true);
        if (str != null && StringUtils.isEmpty(str)) {
            for (int i4 = this.nspCounts[this.depth]; i4 < this.nspCounts[this.depth + 1]; i4++) {
                int i5 = i4 * 2;
                if (StringUtils.isEmpty(this.nspStack[i5]) && !StringUtils.isEmpty(this.nspStack[i5 + 1])) {
                    throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
                }
            }
        }
        String[] strArr3 = this.elementStack;
        int i6 = i3 + 1;
        strArr3[i3] = str;
        strArr3[i6] = prefix;
        strArr3[i6 + 1] = str2;
        this.writer.write(60);
        if (!StringUtils.isEmpty(prefix)) {
            this.writer.write(prefix);
            this.writer.write(58);
        }
        this.writer.write(str2);
        this.pending = true;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String str) throws IOException {
        check(false);
        this.indent[this.depth] = false;
        writeEscaped(str, -1);
        return this;
    }

    private final String getPrefix(String str, boolean z2, boolean z3) throws IOException {
        String string;
        int i2 = this.nspCounts[this.depth + 1] * 2;
        while (true) {
            i2 -= 2;
            String str2 = null;
            if (i2 < 0) {
                if (!z3) {
                    return null;
                }
                if (StringUtils.isEmpty(str)) {
                    string = "";
                } else {
                    do {
                        StringBuilder sb = new StringBuilder();
                        sb.append("n");
                        int i3 = this.auto;
                        this.auto = i3 + 1;
                        sb.append(i3);
                        string = sb.toString();
                        int i4 = (this.nspCounts[this.depth + 1] * 2) - 2;
                        while (true) {
                            if (i4 < 0) {
                                break;
                            }
                            if (string.equals(this.nspStack[i4])) {
                                string = null;
                                break;
                            }
                            i4 -= 2;
                        }
                    } while (string == null);
                }
                boolean z4 = this.pending;
                this.pending = false;
                setPrefix(string, str);
                this.pending = z4;
                return string;
            }
            if (this.nspStack[i2 + 1].equals(str) && (z2 || !StringUtils.isEmpty(this.nspStack[i2]))) {
                String str3 = this.nspStack[i2];
                int i5 = i2 + 2;
                while (true) {
                    if (i5 >= this.nspCounts[this.depth + 1] * 2) {
                        str2 = str3;
                        break;
                    }
                    if (this.nspStack[i5].equals(str3)) {
                        break;
                    }
                    i5++;
                }
                if (str2 != null) {
                    return str2;
                }
            }
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] cArr, int i2, int i3) throws IOException {
        text(new String(cArr, i2, i3));
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IOException {
        if (outputStream != null) {
            setOutput(str == null ? new OutputStreamWriter(outputStream) : new OutputStreamWriter(outputStream, str));
            this.encoding = str;
            if (str == null || !str.toLowerCase(Locale.US).startsWith("utf")) {
                return;
            }
            this.unicode = true;
            return;
        }
        throw new IllegalArgumentException("os == null");
    }
}
