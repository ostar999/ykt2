package org.jsoup.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/* loaded from: classes9.dex */
public class DataUtil {
    private static final int bufferSize = 131072;
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
    static final String defaultCharset = "UTF-8";

    private DataUtil() {
    }

    public static String getCharsetFromContentType(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = charsetPattern.matcher(str);
        if (matcher.find()) {
            String strReplace = matcher.group(1).trim().replace("charset=", "");
            if (strReplace.length() == 0) {
                return null;
            }
            try {
                if (Charset.isSupported(strReplace)) {
                    return strReplace;
                }
                String upperCase = strReplace.toUpperCase(Locale.ENGLISH);
                if (Charset.isSupported(upperCase)) {
                    return upperCase;
                }
            } catch (IllegalCharsetNameException unused) {
            }
        }
        return null;
    }

    public static Document load(File file, String str, String str2) throws IOException {
        return parseByteData(readFileToByteBuffer(file), str, str2, Parser.htmlParser());
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.jsoup.nodes.Document parseByteData(java.nio.ByteBuffer r8, java.lang.String r9, java.lang.String r10, org.jsoup.parser.Parser r11) {
        /*
            java.lang.String r0 = "UTF-8"
            r1 = 0
            if (r9 != 0) goto L7d
            java.nio.charset.Charset r2 = java.nio.charset.Charset.forName(r0)
            java.nio.CharBuffer r2 = r2.decode(r8)
            java.lang.String r2 = r2.toString()
            org.jsoup.nodes.Document r3 = r11.parseInput(r2, r10)
            java.lang.String r4 = "meta[http-equiv=content-type], meta[charset]"
            org.jsoup.select.Elements r4 = r3.select(r4)
            org.jsoup.nodes.Element r4 = r4.first()
            if (r4 == 0) goto L8f
            java.lang.String r5 = "http-equiv"
            boolean r5 = r4.hasAttr(r5)
            java.lang.String r6 = "charset"
            if (r5 == 0) goto L4f
            java.lang.String r5 = "content"
            java.lang.String r5 = r4.attr(r5)
            java.lang.String r5 = getCharsetFromContentType(r5)
            if (r5 != 0) goto L53
            boolean r7 = r4.hasAttr(r6)
            if (r7 == 0) goto L53
            java.lang.String r7 = r4.attr(r6)     // Catch: java.nio.charset.IllegalCharsetNameException -> L4d
            boolean r7 = java.nio.charset.Charset.isSupported(r7)     // Catch: java.nio.charset.IllegalCharsetNameException -> L4d
            if (r7 == 0) goto L53
            java.lang.String r4 = r4.attr(r6)     // Catch: java.nio.charset.IllegalCharsetNameException -> L4d
            r5 = r4
            goto L53
        L4d:
            r5 = r1
            goto L53
        L4f:
            java.lang.String r5 = r4.attr(r6)
        L53:
            if (r5 == 0) goto L8f
            int r4 = r5.length()
            if (r4 == 0) goto L8f
            boolean r4 = r5.equals(r0)
            if (r4 != 0) goto L8f
            java.lang.String r9 = r5.trim()
            java.lang.String r2 = "[\"']"
            java.lang.String r3 = ""
            java.lang.String r9 = r9.replaceAll(r2, r3)
            r8.rewind()
            java.nio.charset.Charset r2 = java.nio.charset.Charset.forName(r9)
            java.nio.CharBuffer r2 = r2.decode(r8)
            java.lang.String r2 = r2.toString()
            goto L8e
        L7d:
            java.lang.String r2 = "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML"
            org.jsoup.helper.Validate.notEmpty(r9, r2)
            java.nio.charset.Charset r2 = java.nio.charset.Charset.forName(r9)
            java.nio.CharBuffer r2 = r2.decode(r8)
            java.lang.String r2 = r2.toString()
        L8e:
            r3 = r1
        L8f:
            int r4 = r2.length()
            if (r4 <= 0) goto Lb4
            r4 = 0
            char r4 = r2.charAt(r4)
            r5 = 65279(0xfeff, float:9.1475E-41)
            if (r4 != r5) goto Lb4
            r8.rewind()
            java.nio.charset.Charset r9 = java.nio.charset.Charset.forName(r0)
            java.nio.CharBuffer r8 = r9.decode(r8)
            java.lang.String r8 = r8.toString()
            r9 = 1
            java.lang.String r2 = r8.substring(r9)
            goto Lb6
        Lb4:
            r0 = r9
            r1 = r3
        Lb6:
            if (r1 != 0) goto Lc3
            org.jsoup.nodes.Document r1 = r11.parseInput(r2, r10)
            org.jsoup.nodes.Document$OutputSettings r8 = r1.outputSettings()
            r8.charset(r0)
        Lc3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.DataUtil.parseByteData(java.nio.ByteBuffer, java.lang.String, java.lang.String, org.jsoup.parser.Parser):org.jsoup.nodes.Document");
    }

    public static ByteBuffer readFileToByteBuffer(File file) throws Throwable {
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(bArr);
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
            randomAccessFile.close();
            return byteBufferWrap;
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile2 = randomAccessFile;
            if (randomAccessFile2 != null) {
                randomAccessFile2.close();
            }
            throw th;
        }
    }

    public static ByteBuffer readToByteBuffer(InputStream inputStream, int i2) throws IOException {
        Validate.isTrue(i2 >= 0, "maxSize must be 0 (unlimited) or larger");
        boolean z2 = i2 > 0;
        byte[] bArr = new byte[131072];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(131072);
        while (true) {
            int i3 = inputStream.read(bArr);
            if (i3 == -1) {
                break;
            }
            if (z2) {
                if (i3 > i2) {
                    byteArrayOutputStream.write(bArr, 0, i2);
                    break;
                }
                i2 -= i3;
            }
            byteArrayOutputStream.write(bArr, 0, i3);
        }
        return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
    }

    public static Document load(InputStream inputStream, String str, String str2) throws IOException {
        return parseByteData(readToByteBuffer(inputStream), str, str2, Parser.htmlParser());
    }

    public static Document load(InputStream inputStream, String str, String str2, Parser parser) throws IOException {
        return parseByteData(readToByteBuffer(inputStream), str, str2, parser);
    }

    public static ByteBuffer readToByteBuffer(InputStream inputStream) throws IOException {
        return readToByteBuffer(inputStream, 0);
    }
}
