package org.eclipse.jetty.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.util.Utf8Appendable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class UrlEncoded extends MultiMap implements Cloneable {
    private static final Logger LOG = Log.getLogger((Class<?>) UrlEncoded.class);
    public static final String ENCODING = System.getProperty("org.eclipse.jetty.util.UrlEncoding.charset", "UTF-8");

    public UrlEncoded(UrlEncoded urlEncoded) {
        super((MultiMap) urlEncoded);
    }

    public static void decode88591To(InputStream inputStream, MultiMap multiMap, int i2, int i3) throws IOException {
        int i4;
        int i5;
        int i6;
        synchronized (multiMap) {
            StringBuffer stringBuffer = new StringBuffer();
            String string = null;
            int i7 = 0;
            while (true) {
                int i8 = inputStream.read();
                if (i8 >= 0) {
                    char c3 = (char) i8;
                    if (c3 == '%') {
                        int i9 = inputStream.read();
                        if (117 == i9) {
                            int i10 = inputStream.read();
                            if (i10 >= 0 && (i5 = inputStream.read()) >= 0 && (i6 = inputStream.read()) >= 0) {
                                stringBuffer.append(Character.toChars((TypeUtil.convertHexDigit(i9) << 12) + (TypeUtil.convertHexDigit(i10) << 8) + (TypeUtil.convertHexDigit(i5) << 4) + TypeUtil.convertHexDigit(i6)));
                            }
                        } else if (i9 >= 0 && (i4 = inputStream.read()) >= 0) {
                            stringBuffer.append((char) ((TypeUtil.convertHexDigit(i9) << 4) + TypeUtil.convertHexDigit(i4)));
                        }
                    } else if (c3 == '&') {
                        String string2 = stringBuffer.length() == 0 ? "" : stringBuffer.toString();
                        stringBuffer.setLength(0);
                        if (string != null) {
                            multiMap.add(string, string2);
                        } else if (string2 != null && string2.length() > 0) {
                            multiMap.add(string2, "");
                        }
                        if (i3 > 0 && multiMap.size() > i3) {
                            throw new IllegalStateException("Form too many keys");
                        }
                        string = null;
                    } else if (c3 == '+') {
                        stringBuffer.append(' ');
                    } else if (c3 == '=' && string == null) {
                        string = stringBuffer.toString();
                        stringBuffer.setLength(0);
                    } else {
                        stringBuffer.append(c3);
                    }
                    if (i2 >= 0 && (i7 = i7 + 1) > i2) {
                        throw new IllegalStateException("Form too large");
                    }
                } else if (string != null) {
                    Object string3 = stringBuffer.length() == 0 ? "" : stringBuffer.toString();
                    stringBuffer.setLength(0);
                    multiMap.add(string, string3);
                } else if (stringBuffer.length() > 0) {
                    multiMap.add(stringBuffer.toString(), "");
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:165:0x00c8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00c9 A[Catch: UnsupportedEncodingException -> 0x0103, LOOP:1: B:23:0x0051->B:58:0x00c9, LOOP_END, TryCatch #2 {UnsupportedEncodingException -> 0x0103, blocks: (B:10:0x0026, B:16:0x0034, B:17:0x003c, B:65:0x00f7, B:20:0x0045, B:21:0x004d, B:29:0x0061, B:34:0x006d, B:58:0x00c9, B:48:0x00a2, B:39:0x008a, B:44:0x0095, B:49:0x00ac, B:52:0x00b7, B:54:0x00c3, B:53:0x00be, B:59:0x00d5, B:61:0x00e3, B:63:0x00e9, B:64:0x00f4, B:70:0x0109, B:73:0x0110, B:75:0x0117), top: B:145:0x0026 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String decodeString(java.lang.String r16, int r17, int r18, java.lang.String r19) throws java.io.UnsupportedEncodingException {
        /*
            Method dump skipped, instructions count: 545
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.UrlEncoded.decodeString(java.lang.String, int, int, java.lang.String):java.lang.String");
    }

    public static void decodeTo(String str, MultiMap multiMap, String str2) {
        decodeTo(str, multiMap, str2, -1);
    }

    public static void decodeUtf16To(InputStream inputStream, MultiMap multiMap, int i2, int i3) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-16");
        StringWriter stringWriter = new StringWriter(8192);
        IO.copy(inputStreamReader, stringWriter, i2);
        decodeTo(stringWriter.getBuffer().toString(), multiMap, "UTF-16", i3);
    }

    public static void decodeUtf8To(byte[] bArr, int i2, int i3, MultiMap multiMap) {
        decodeUtf8To(bArr, i2, i3, multiMap, new Utf8StringBuilder());
    }

    public static String encodeString(String str) {
        return encodeString(str, ENCODING);
    }

    public Object clone() {
        return new UrlEncoded(this);
    }

    public void decode(String str) {
        decodeTo(str, this, ENCODING, -1);
    }

    public String encode() {
        return encode(ENCODING, false);
    }

    public UrlEncoded() {
        super(6);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void decodeTo(java.lang.String r9, org.eclipse.jetty.util.MultiMap r10, java.lang.String r11, int r12) {
        /*
            Method dump skipped, instructions count: 200
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.UrlEncoded.decodeTo(java.lang.String, org.eclipse.jetty.util.MultiMap, java.lang.String, int):void");
    }

    public static void decodeUtf8To(byte[] bArr, int i2, int i3, MultiMap multiMap, Utf8StringBuilder utf8StringBuilder) {
        Utf8Appendable.NotUtf8Exception e2;
        synchronized (multiMap) {
            int i4 = i3 + i2;
            String string = null;
            while (i2 < i4) {
                try {
                    byte b3 = bArr[i2];
                    char c3 = (char) (b3 & 255);
                    if (c3 == '%') {
                        if (i2 + 2 < i4) {
                            int i5 = i2 + 1;
                            byte b4 = bArr[i5];
                            if (117 != b4) {
                                i5++;
                                utf8StringBuilder.append((byte) ((TypeUtil.convertHexDigit(b4) << 4) + TypeUtil.convertHexDigit(bArr[i5])));
                            } else if (i5 + 4 < i4) {
                                try {
                                    StringBuilder stringBuilder = utf8StringBuilder.getStringBuilder();
                                    int i6 = i5 + 1;
                                    int iConvertHexDigit = TypeUtil.convertHexDigit(bArr[i6]) << 12;
                                    int i7 = i6 + 1;
                                    int iConvertHexDigit2 = iConvertHexDigit + (TypeUtil.convertHexDigit(bArr[i7]) << 8);
                                    int i8 = i7 + 1;
                                    int iConvertHexDigit3 = iConvertHexDigit2 + (TypeUtil.convertHexDigit(bArr[i8]) << 4);
                                    i5 = i8 + 1;
                                    stringBuilder.append(Character.toChars(iConvertHexDigit3 + TypeUtil.convertHexDigit(bArr[i5])));
                                } catch (Utf8Appendable.NotUtf8Exception e3) {
                                    int i9 = i5;
                                    e2 = e3;
                                    i2 = i9;
                                    Logger logger = LOG;
                                    logger.warn(e2.toString(), new Object[0]);
                                    logger.debug(e2);
                                    i2++;
                                }
                            } else {
                                utf8StringBuilder.getStringBuilder().append((char) 65533);
                            }
                            i2 = i5;
                        } else {
                            utf8StringBuilder.getStringBuilder().append((char) 65533);
                        }
                        i2 = i4;
                    } else if (c3 == '&') {
                        String string2 = utf8StringBuilder.length() == 0 ? "" : utf8StringBuilder.toString();
                        utf8StringBuilder.reset();
                        if (string != null) {
                            multiMap.add(string, string2);
                        } else if (string2 != null && string2.length() > 0) {
                            multiMap.add(string2, "");
                        }
                        string = null;
                    } else if (c3 == '+') {
                        utf8StringBuilder.append((byte) 32);
                    } else if (c3 != '=') {
                        try {
                            utf8StringBuilder.append(b3);
                        } catch (Utf8Appendable.NotUtf8Exception e4) {
                            e2 = e4;
                            Logger logger2 = LOG;
                            logger2.warn(e2.toString(), new Object[0]);
                            logger2.debug(e2);
                            i2++;
                        }
                    } else if (string != null) {
                        utf8StringBuilder.append(b3);
                    } else {
                        string = utf8StringBuilder.toString();
                        utf8StringBuilder.reset();
                    }
                    i2++;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (string != null) {
                String replacedString = utf8StringBuilder.length() == 0 ? "" : utf8StringBuilder.toReplacedString();
                utf8StringBuilder.reset();
                multiMap.add(string, replacedString);
            } else if (utf8StringBuilder.length() > 0) {
                multiMap.add(utf8StringBuilder.toReplacedString(), "");
            }
        }
    }

    public static String encodeString(String str, String str2) throws UnsupportedEncodingException {
        byte[] bytes;
        int i2;
        if (str2 == null) {
            str2 = ENCODING;
        }
        try {
            bytes = str.getBytes(str2);
        } catch (UnsupportedEncodingException unused) {
            bytes = str.getBytes();
        }
        byte[] bArr = new byte[bytes.length * 3];
        boolean z2 = true;
        int i3 = 0;
        for (byte b3 : bytes) {
            if (b3 == 32) {
                bArr[i3] = 43;
                i3++;
            } else if ((b3 < 97 || b3 > 122) && ((b3 < 65 || b3 > 90) && (b3 < 48 || b3 > 57))) {
                int i4 = i3 + 1;
                bArr[i3] = 37;
                byte b4 = (byte) ((b3 & 240) >> 4);
                if (b4 >= 10) {
                    i2 = i4 + 1;
                    bArr[i4] = (byte) ((b4 + 65) - 10);
                } else {
                    i2 = i4 + 1;
                    bArr[i4] = (byte) (b4 + TarConstants.LF_NORMAL);
                }
                byte b5 = (byte) (b3 & 15);
                if (b5 >= 10) {
                    i3 = i2 + 1;
                    bArr[i2] = (byte) ((b5 + 65) - 10);
                } else {
                    i3 = i2 + 1;
                    bArr[i2] = (byte) (b5 + TarConstants.LF_NORMAL);
                }
            } else {
                bArr[i3] = b3;
                i3++;
            }
            z2 = false;
        }
        if (z2) {
            return str;
        }
        try {
            return new String(bArr, 0, i3, str2);
        } catch (UnsupportedEncodingException unused2) {
            return new String(bArr, 0, i3);
        }
    }

    public void decode(String str, String str2) {
        decodeTo(str, this, str2, -1);
    }

    public String encode(String str) {
        return encode(str, false);
    }

    public UrlEncoded(String str) {
        super(6);
        decode(str, ENCODING);
    }

    public synchronized String encode(String str, boolean z2) {
        return encode(this, str, z2);
    }

    public static String encode(MultiMap multiMap, String str, boolean z2) {
        if (str == null) {
            str = ENCODING;
        }
        StringBuilder sb = new StringBuilder(128);
        Iterator it = multiMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String string = entry.getKey().toString();
            Object value = entry.getValue();
            int size = LazyList.size(value);
            if (size == 0) {
                sb.append(encodeString(string, str));
                if (z2) {
                    sb.append('=');
                }
            } else {
                for (int i2 = 0; i2 < size; i2++) {
                    if (i2 > 0) {
                        sb.append('&');
                    }
                    Object obj = LazyList.get(value, i2);
                    sb.append(encodeString(string, str));
                    if (obj != null) {
                        String string2 = obj.toString();
                        if (string2.length() > 0) {
                            sb.append('=');
                            sb.append(encodeString(string2, str));
                        } else if (z2) {
                            sb.append('=');
                        }
                    } else if (z2) {
                        sb.append('=');
                    }
                }
            }
            if (it.hasNext()) {
                sb.append('&');
            }
        }
        return sb.toString();
    }

    public UrlEncoded(String str, String str2) {
        super(6);
        decode(str, str2);
    }

    public static void decodeTo(InputStream inputStream, MultiMap multiMap, String str, int i2, int i3) throws IOException {
        int i4;
        int i5;
        int i6;
        if (str == null) {
            str = ENCODING;
        }
        if ("UTF-8".equalsIgnoreCase(str)) {
            decodeUtf8To(inputStream, multiMap, i2, i3);
            return;
        }
        if ("ISO-8859-1".equals(str)) {
            decode88591To(inputStream, multiMap, i2, i3);
            return;
        }
        if ("UTF-16".equalsIgnoreCase(str)) {
            decodeUtf16To(inputStream, multiMap, i2, i3);
            return;
        }
        synchronized (multiMap) {
            ByteArrayOutputStream2 byteArrayOutputStream2 = new ByteArrayOutputStream2();
            String string = null;
            int i7 = 0;
            while (true) {
                int i8 = inputStream.read();
                if (i8 > 0) {
                    char c3 = (char) i8;
                    if (c3 == '%') {
                        int i9 = inputStream.read();
                        if (117 == i9) {
                            int i10 = inputStream.read();
                            if (i10 >= 0 && (i5 = inputStream.read()) >= 0 && (i6 = inputStream.read()) >= 0) {
                                byteArrayOutputStream2.write(new String(Character.toChars((TypeUtil.convertHexDigit(i9) << 12) + (TypeUtil.convertHexDigit(i10) << 8) + (TypeUtil.convertHexDigit(i5) << 4) + TypeUtil.convertHexDigit(i6))).getBytes(str));
                            }
                        } else if (i9 >= 0 && (i4 = inputStream.read()) >= 0) {
                            byteArrayOutputStream2.write((TypeUtil.convertHexDigit(i9) << 4) + TypeUtil.convertHexDigit(i4));
                        }
                    } else if (c3 == '&') {
                        String string2 = byteArrayOutputStream2.size() == 0 ? "" : byteArrayOutputStream2.toString(str);
                        byteArrayOutputStream2.setCount(0);
                        if (string != null) {
                            multiMap.add(string, string2);
                        } else if (string2 != null && string2.length() > 0) {
                            multiMap.add(string2, "");
                        }
                        if (i3 > 0 && multiMap.size() > i3) {
                            throw new IllegalStateException("Form too many keys");
                        }
                        string = null;
                    } else if (c3 == '+') {
                        byteArrayOutputStream2.write(32);
                    } else if (c3 != '=' || string != null) {
                        byteArrayOutputStream2.write(i8);
                    } else {
                        string = byteArrayOutputStream2.size() == 0 ? "" : byteArrayOutputStream2.toString(str);
                        byteArrayOutputStream2.setCount(0);
                    }
                    i7++;
                    if (i2 >= 0 && i7 > i2) {
                        throw new IllegalStateException("Form too large");
                    }
                } else {
                    int size = byteArrayOutputStream2.size();
                    if (string != null) {
                        Object string3 = size == 0 ? "" : byteArrayOutputStream2.toString(str);
                        byteArrayOutputStream2.setCount(0);
                        multiMap.add(string, string3);
                    } else if (size > 0) {
                        multiMap.add(byteArrayOutputStream2.toString(str), "");
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x00e0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x000a A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void decodeUtf8To(java.io.InputStream r10, org.eclipse.jetty.util.MultiMap r11, int r12, int r13) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.UrlEncoded.decodeUtf8To(java.io.InputStream, org.eclipse.jetty.util.MultiMap, int, int):void");
    }
}
