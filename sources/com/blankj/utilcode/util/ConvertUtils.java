package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ConvertUtils {
    private static final int BUFFER_SIZE = 8192;
    private static final char[] HEX_DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        return UtilsBridge.bitmap2Bytes(bitmap);
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return UtilsBridge.bitmap2Drawable(bitmap);
    }

    public static byte[] bits2Bytes(String str) {
        int length = str.length() % 8;
        int length2 = str.length() / 8;
        if (length != 0) {
            while (length < 8) {
                str = "0" + str;
                length++;
            }
            length2++;
        }
        byte[] bArr = new byte[length2];
        for (int i2 = 0; i2 < length2; i2++) {
            for (int i3 = 0; i3 < 8; i3++) {
                byte b3 = (byte) (bArr[i2] << 1);
                bArr[i2] = b3;
                bArr[i2] = (byte) (b3 | (str.charAt((i2 * 8) + i3) - '0'));
            }
        }
        return bArr;
    }

    @SuppressLint({"DefaultLocale"})
    public static String byte2FitMemorySize(long j2) {
        return byte2FitMemorySize(j2, 3);
    }

    public static double byte2MemorySize(long j2, int i2) {
        if (j2 < 0) {
            return -1.0d;
        }
        return j2 / i2;
    }

    public static Bitmap bytes2Bitmap(byte[] bArr) {
        return UtilsBridge.bytes2Bitmap(bArr);
    }

    public static String bytes2Bits(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArr) {
            for (int i2 = 7; i2 >= 0; i2--) {
                sb.append(((b3 >> i2) & 1) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    public static char[] bytes2Chars(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return null;
        }
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            cArr[i2] = (char) (bArr[i2] & 255);
        }
        return cArr;
    }

    public static Drawable bytes2Drawable(byte[] bArr) {
        return UtilsBridge.bytes2Drawable(bArr);
    }

    public static String bytes2HexString(byte[] bArr) {
        return bytes2HexString(bArr, true);
    }

    public static InputStream bytes2InputStream(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        return new ByteArrayInputStream(bArr);
    }

    public static JSONArray bytes2JSONArray(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return new JSONArray(new String(bArr));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static JSONObject bytes2JSONObject(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return new JSONObject(new String(bArr));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0033 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object bytes2Object(byte[] r3) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r3 != 0) goto L4
            return r0
        L4:
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L1f
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L1f
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L1f
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L1f
            java.lang.Object r3 = r1.readObject()     // Catch: java.lang.Exception -> L1b java.lang.Throwable -> L2f
            r1.close()     // Catch: java.io.IOException -> L16
            goto L1a
        L16:
            r0 = move-exception
            r0.printStackTrace()
        L1a:
            return r3
        L1b:
            r3 = move-exception
            goto L21
        L1d:
            r3 = move-exception
            goto L31
        L1f:
            r3 = move-exception
            r1 = r0
        L21:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L2f
            if (r1 == 0) goto L2e
            r1.close()     // Catch: java.io.IOException -> L2a
            goto L2e
        L2a:
            r3 = move-exception
            r3.printStackTrace()
        L2e:
            return r0
        L2f:
            r3 = move-exception
            r0 = r1
        L31:
            if (r0 == 0) goto L3b
            r0.close()     // Catch: java.io.IOException -> L37
            goto L3b
        L37:
            r0 = move-exception
            r0.printStackTrace()
        L3b:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.ConvertUtils.bytes2Object(byte[]):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0030 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.OutputStream bytes2OutputStream(byte[] r2) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r2 == 0) goto L39
            int r1 = r2.length
            if (r1 > 0) goto L7
            goto L39
        L7:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L1a java.io.IOException -> L1c
            r1.<init>()     // Catch: java.lang.Throwable -> L1a java.io.IOException -> L1c
            r1.write(r2)     // Catch: java.io.IOException -> L18 java.lang.Throwable -> L2c
            r1.close()     // Catch: java.io.IOException -> L13
            goto L17
        L13:
            r2 = move-exception
            r2.printStackTrace()
        L17:
            return r1
        L18:
            r2 = move-exception
            goto L1e
        L1a:
            r2 = move-exception
            goto L2e
        L1c:
            r2 = move-exception
            r1 = r0
        L1e:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L2c
            if (r1 == 0) goto L2b
            r1.close()     // Catch: java.io.IOException -> L27
            goto L2b
        L27:
            r2 = move-exception
            r2.printStackTrace()
        L2b:
            return r0
        L2c:
            r2 = move-exception
            r0 = r1
        L2e:
            if (r0 == 0) goto L38
            r0.close()     // Catch: java.io.IOException -> L34
            goto L38
        L34:
            r0 = move-exception
            r0.printStackTrace()
        L38:
            throw r2
        L39:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.ConvertUtils.bytes2OutputStream(byte[]):java.io.OutputStream");
    }

    public static <T> T bytes2Parcelable(byte[] bArr, Parcelable.Creator<T> creator) {
        if (bArr == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, 0, bArr.length);
        parcelObtain.setDataPosition(0);
        T tCreateFromParcel = creator.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return tCreateFromParcel;
    }

    public static String bytes2String(byte[] bArr) {
        return bytes2String(bArr, "");
    }

    public static byte[] chars2Bytes(char[] cArr) {
        if (cArr == null || cArr.length <= 0) {
            return null;
        }
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = (byte) cArr[i2];
        }
        return bArr;
    }

    public static int dp2px(float f2) {
        return UtilsBridge.dp2px(f2);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        return UtilsBridge.drawable2Bitmap(drawable);
    }

    public static byte[] drawable2Bytes(Drawable drawable) {
        return UtilsBridge.drawable2Bytes(drawable);
    }

    private static String getSafeCharset(String str) {
        return (UtilsBridge.isSpace(str) || !Charset.isSupported(str)) ? "UTF-8" : str;
    }

    private static int hex2Dec(char c3) {
        if (c3 >= '0' && c3 <= '9') {
            return c3 - '0';
        }
        if (c3 < 'A' || c3 > 'F') {
            throw new IllegalArgumentException();
        }
        return (c3 - 'A') + 10;
    }

    public static byte[] hexString2Bytes(String str) {
        if (UtilsBridge.isSpace(str)) {
            return new byte[0];
        }
        int length = str.length();
        if (length % 2 != 0) {
            str = "0" + str;
            length++;
        }
        char[] charArray = str.toUpperCase().toCharArray();
        byte[] bArr = new byte[length >> 1];
        for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 >> 1] = (byte) ((hex2Dec(charArray[i2]) << 4) | hex2Dec(charArray[i2 + 1]));
        }
        return bArr;
    }

    public static int hexString2Int(String str) {
        return Integer.parseInt(str, 16);
    }

    public static ByteArrayOutputStream input2OutputStream(InputStream inputStream) throws IOException {
        try {
            if (inputStream == null) {
                return null;
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[8192];
                while (true) {
                    int i2 = inputStream.read(bArr, 0, 8192);
                    if (i2 != -1) {
                        byteArrayOutputStream.write(bArr, 0, i2);
                    } else {
                        try {
                            break;
                        } catch (IOException e2) {
                        }
                    }
                }
                return byteArrayOutputStream;
            } catch (IOException e3) {
                e3.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return null;
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e22) {
                e22.printStackTrace();
            }
        }
    }

    public static byte[] inputStream2Bytes(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        return input2OutputStream(inputStream).toByteArray();
    }

    public static List<String> inputStream2Lines(InputStream inputStream) {
        return inputStream2Lines(inputStream, "");
    }

    public static String inputStream2String(InputStream inputStream, String str) throws IOException {
        if (inputStream == null) {
            return "";
        }
        try {
            ByteArrayOutputStream byteArrayOutputStreamInput2OutputStream = input2OutputStream(inputStream);
            return byteArrayOutputStreamInput2OutputStream == null ? "" : byteArrayOutputStreamInput2OutputStream.toString(getSafeCharset(str));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String int2HexString(int i2) {
        return Integer.toHexString(i2);
    }

    public static byte[] jsonArray2Bytes(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        return jSONArray.toString().getBytes();
    }

    public static byte[] jsonObject2Bytes(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        return jSONObject.toString().getBytes();
    }

    public static long memorySize2Byte(long j2, int i2) {
        if (j2 < 0) {
            return -1L;
        }
        return j2 * i2;
    }

    public static String millis2FitTimeSpan(long j2, int i2) {
        return UtilsBridge.millis2FitTimeSpan(j2, i2);
    }

    public static long millis2TimeSpan(long j2, int i2) {
        return j2 / i2;
    }

    public static ByteArrayInputStream output2InputStream(OutputStream outputStream) {
        if (outputStream == null) {
            return null;
        }
        return new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
    }

    public static byte[] outputStream2Bytes(OutputStream outputStream) {
        if (outputStream == null) {
            return null;
        }
        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }

    public static String outputStream2String(OutputStream outputStream, String str) {
        if (outputStream == null) {
            return "";
        }
        try {
            return new String(outputStream2Bytes(outputStream), getSafeCharset(str));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static byte[] parcelable2Bytes(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        parcelable.writeToParcel(parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        return bArrMarshall;
    }

    public static int px2dp(float f2) {
        return UtilsBridge.px2dp(f2);
    }

    public static int px2sp(float f2) {
        return UtilsBridge.px2sp(f2);
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x0033: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]) (LINE:52), block:B:26:0x0033 */
    public static byte[] serializable2Bytes(Serializable serializable) throws Throwable {
        ObjectOutputStream objectOutputStream;
        ObjectOutputStream objectOutputStream2;
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream3 = null;
        try {
            if (serializable == null) {
                return null;
            }
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
            } catch (Exception e2) {
                e = e2;
                objectOutputStream2 = null;
            } catch (Throwable th) {
                th = th;
                if (objectOutputStream3 != null) {
                    try {
                        objectOutputStream3.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
            try {
                objectOutputStream2.writeObject(serializable);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    objectOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return byteArray;
            } catch (Exception e5) {
                e = e5;
                e.printStackTrace();
                if (objectOutputStream2 != null) {
                    try {
                        objectOutputStream2.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            objectOutputStream3 = objectOutputStream;
        }
    }

    public static int sp2px(float f2) {
        return UtilsBridge.sp2px(f2);
    }

    public static byte[] string2Bytes(String str) {
        return string2Bytes(str, "");
    }

    public static InputStream string2InputStream(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return new ByteArrayInputStream(str.getBytes(getSafeCharset(str2)));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static OutputStream string2OutputStream(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return bytes2OutputStream(str.getBytes(getSafeCharset(str2)));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static long timeSpan2Millis(long j2, int i2) {
        return j2 * i2;
    }

    public static Bitmap view2Bitmap(View view) {
        return UtilsBridge.view2Bitmap(view);
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i2) {
        return UtilsBridge.bitmap2Bytes(bitmap, compressFormat, i2);
    }

    @SuppressLint({"DefaultLocale"})
    public static String byte2FitMemorySize(long j2, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("precision shouldn't be less than zero!");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        }
        if (j2 < 1024) {
            return String.format("%." + i2 + "fB", Double.valueOf(j2));
        }
        if (j2 < 1048576) {
            return String.format("%." + i2 + "fKB", Double.valueOf(j2 / 1024.0d));
        }
        if (j2 < 1073741824) {
            return String.format("%." + i2 + "fMB", Double.valueOf(j2 / 1048576.0d));
        }
        return String.format("%." + i2 + "fGB", Double.valueOf(j2 / 1.073741824E9d));
    }

    public static String bytes2HexString(byte[] bArr, boolean z2) {
        if (bArr == null) {
            return "";
        }
        char[] cArr = z2 ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int length = bArr.length;
        if (length <= 0) {
            return "";
        }
        char[] cArr2 = new char[length << 1];
        int i2 = 0;
        for (byte b3 : bArr) {
            int i3 = i2 + 1;
            cArr2[i2] = cArr[(b3 >> 4) & 15];
            i2 = i3 + 1;
            cArr2[i3] = cArr[b3 & 15];
        }
        return new String(cArr2);
    }

    public static String bytes2String(byte[] bArr, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return new String(bArr, getSafeCharset(str));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return new String(bArr);
        }
    }

    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat compressFormat, int i2) {
        return UtilsBridge.drawable2Bytes(drawable, compressFormat, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x003f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> inputStream2Lines(java.io.InputStream r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            r0 = 0
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            r1.<init>()     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            java.lang.String r5 = getSafeCharset(r5)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            r3.<init>(r4, r5)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
        L14:
            java.lang.String r4 = r2.readLine()     // Catch: java.io.IOException -> L27 java.lang.Throwable -> L3b
            if (r4 == 0) goto L1e
            r1.add(r4)     // Catch: java.io.IOException -> L27 java.lang.Throwable -> L3b
            goto L14
        L1e:
            r2.close()     // Catch: java.io.IOException -> L22
            goto L26
        L22:
            r4 = move-exception
            r4.printStackTrace()
        L26:
            return r1
        L27:
            r4 = move-exception
            goto L2d
        L29:
            r4 = move-exception
            goto L3d
        L2b:
            r4 = move-exception
            r2 = r0
        L2d:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L3b
            if (r2 == 0) goto L3a
            r2.close()     // Catch: java.io.IOException -> L36
            goto L3a
        L36:
            r4 = move-exception
            r4.printStackTrace()
        L3a:
            return r0
        L3b:
            r4 = move-exception
            r0 = r2
        L3d:
            if (r0 == 0) goto L47
            r0.close()     // Catch: java.io.IOException -> L43
            goto L47
        L43:
            r5 = move-exception
            r5.printStackTrace()
        L47:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.ConvertUtils.inputStream2Lines(java.io.InputStream, java.lang.String):java.util.List");
    }

    public static byte[] string2Bytes(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(getSafeCharset(str2));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return str.getBytes();
        }
    }
}
