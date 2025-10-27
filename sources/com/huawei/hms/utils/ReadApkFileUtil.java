package com.huawei.hms.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Base64;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.support.log.HMSLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

/* loaded from: classes4.dex */
public class ReadApkFileUtil {
    public static final String EMUI10_PK = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx4nUogUyMCmzHhaEb420yvpw9zBs+ETzE9Qm77bGxl1Iml9JEkBkNTsUWOstLgUBajNhV+BAMVBHKMEdzoQbL5kIHkTgUVM65yewd+5+BhrcB9OQ3LHp+0BN6aLKZh71T4WvsvHFhfhQpShuGWkRkSaVGLFTHxX70kpWLzeZ3RtqiEUNIufPR2SFCH6EmecJ+HdkmBOh603IblCpGxwSWse0fDI98wZBEmV88RFaiYEgyiezLlWvXzqIj6I/xuyd5nGAegjH2y3cmoDE6CubecoB1jf4KdgACXgdiQ4Oc63MfLGTor3l6RCqeUk4APAMtyhK83jc72W1sdXMd/sj2wIDAQAB";
    public static final String EMUI11_PK = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAqq2eRTMYr2JHLtvuZzfgPrgU8oatD4Rar9fOD7E00es2VhtB3vTyaT2BvYPUPA/nbkHRPak3EZX77CfWj9tzLgSHJE8XLk9C+2ESkdrxCDA6z7I8X+cBDnA05OlCJeZFjnUbjYB8SP8M3BttdrvqtVPxTkEJhchC7UXnMLaJ3kQ3ZPjN7ubjYzO4rv7EtEpqr2bX+qjnSLIZZuUXraxqfdBuhGDIYq62dNsqiyrhX1mfvA3+43N4ZIs3BdfSYII8BNFmFxf+gyf1aoq386R2kAjHcrfOOhjAbZh+R1OAGLWPCqi3E9nB8EsZkeoTW/oIP6pJvgL3bnxq+1viT2dmZyipMgcx/3N6FJqkd67j/sPMtPlHJuq8/s0silzs13jAw1WBV6tWHFkLGpkWGs8jp50wQtndtY8cCPl2XPGmdPN72agH+zsHuKqr/HOB2TuzzaO8rKlGIDQlzZcCSHB28nnvOyBVN9xzLkbYiLnHfd6bTwzNPeqjWrTnPwKyH3BPAgMBAAE=";
    public static final String KEY_SIGNATURE = "Signature:";
    public static final String KEY_SIGNATURE2 = "Signature2:";
    public static final String KEY_SIGNATURE3 = "Signature3:";

    /* renamed from: a, reason: collision with root package name */
    public static final String f8156a = "ReadApkFileUtil";

    /* renamed from: b, reason: collision with root package name */
    public static String f8157b;

    /* renamed from: c, reason: collision with root package name */
    public static String f8158c;

    /* renamed from: d, reason: collision with root package name */
    public static String f8159d;

    /* renamed from: e, reason: collision with root package name */
    public static String f8160e;

    /* renamed from: f, reason: collision with root package name */
    public static String f8161f;

    public static byte[] a(ZipFile zipFile) {
        return a(zipFile, "META-INF/MANIFEST.MF");
    }

    @TargetApi(19)
    public static void b(byte[] bArr) throws Throwable {
        Throwable th;
        ByteArrayInputStream byteArrayInputStream;
        BufferedReader bufferedReader;
        if (bArr == null) {
            HMSLog.e(f8156a, "manifest is null！");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader2 = null;
        f8157b = null;
        f8158c = null;
        f8159d = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8));
            } catch (Exception unused) {
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                String strA = a(bufferedReader);
                while (strA != null) {
                    if (strA.length() != 0) {
                        if (strA.startsWith("ApkHash:")) {
                            f8160e = a(strA.substring(strA.indexOf(":") + 1));
                        }
                        if (strA.startsWith(KEY_SIGNATURE)) {
                            f8157b = a(strA.substring(strA.indexOf(":") + 1));
                            strA = a(bufferedReader);
                        } else if (strA.startsWith(KEY_SIGNATURE2)) {
                            f8158c = a(strA.substring(strA.indexOf(":") + 1));
                            strA = a(bufferedReader);
                        } else if (strA.startsWith(KEY_SIGNATURE3)) {
                            f8159d = a(strA.substring(strA.indexOf(":") + 1));
                            strA = a(bufferedReader);
                        } else {
                            stringBuffer.append(strA);
                            stringBuffer.append("\r\n");
                        }
                    }
                    strA = a(bufferedReader);
                }
                f8161f = stringBuffer.toString();
            } catch (Exception unused2) {
                bufferedReader2 = bufferedReader;
                try {
                    HMSLog.e(f8156a, "loadApkCert Exception!");
                    bufferedReader = bufferedReader2;
                    IOUtils.closeQuietly((InputStream) byteArrayInputStream);
                    IOUtils.closeQuietly((Reader) bufferedReader);
                } catch (Throwable th3) {
                    th = th3;
                    IOUtils.closeQuietly((InputStream) byteArrayInputStream);
                    IOUtils.closeQuietly((Reader) bufferedReader2);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                bufferedReader2 = bufferedReader;
                IOUtils.closeQuietly((InputStream) byteArrayInputStream);
                IOUtils.closeQuietly((Reader) bufferedReader2);
                throw th;
            }
        } catch (Exception unused3) {
            byteArrayInputStream = null;
        } catch (Throwable th5) {
            th = th5;
            byteArrayInputStream = null;
        }
        IOUtils.closeQuietly((InputStream) byteArrayInputStream);
        IOUtils.closeQuietly((Reader) bufferedReader);
    }

    public static String bytesToString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] cArr2 = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = i2 * 2;
            cArr2[i4] = cArr[i3 >>> 4];
            cArr2[i4 + 1] = cArr[i3 & 15];
        }
        return String.valueOf(cArr2);
    }

    public static boolean c() {
        try {
        } catch (Exception e2) {
            HMSLog.i(f8156a, "verifyMDMSignatureV3 MDM verify Exception!:" + e2.getMessage());
        }
        if (a(Base64.decode(EMUI11_PK, 0), a(f8161f, "SHA-384"), b(f8159d), "SHA384withRSA")) {
            HMSLog.i(f8156a, "verifyMDMSignatureV3 verify successful!");
            return true;
        }
        HMSLog.i(f8156a, "verifyMDMSignatureV3 verify failure!");
        return false;
    }

    public static boolean checkSignature() {
        if (f8159d != null) {
            return c();
        }
        if (f8158c != null) {
            return b();
        }
        if (f8157b != null) {
            return a();
        }
        return false;
    }

    public static String getHmsPath(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo("com.huawei.hwid", 128).sourceDir;
        } catch (PackageManager.NameNotFoundException unused) {
            HMSLog.e(f8156a, "HMS is not found!");
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @TargetApi(19)
    public static boolean isCertFound(String str) throws Throwable {
        boolean z2 = false;
        ZipFile zipFile = null;
        ZipFile zipFile2 = null;
        ZipFile zipFile3 = null;
        try {
            try {
                ZipFile zipFile4 = new ZipFile(str);
                try {
                    boolean z3 = zipFile4.getEntry("META-INF/HUAWEI.CER") != null;
                    if (z3) {
                        b(a(zipFile4, "META-INF/HUAWEI.CER"));
                    }
                    try {
                        zipFile4.close();
                    } catch (IOException e2) {
                        String str2 = f8156a;
                        StringBuilder sb = new StringBuilder();
                        sb.append("zipFile.close Exception!");
                        sb.append(e2.getMessage());
                        HMSLog.e(str2, sb.toString());
                        zipFile2 = sb;
                    }
                    z2 = z3;
                    zipFile = zipFile2;
                } catch (Exception e3) {
                    e = e3;
                    zipFile3 = zipFile4;
                    HMSLog.e(f8156a, "isCertFound Exception!" + e.getMessage());
                    zipFile = zipFile3;
                    if (zipFile3 != null) {
                        try {
                            zipFile3.close();
                            zipFile = zipFile3;
                        } catch (IOException e4) {
                            String str3 = f8156a;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("zipFile.close Exception!");
                            sb2.append(e4.getMessage());
                            HMSLog.e(str3, sb2.toString());
                            zipFile = sb2;
                        }
                    }
                    return z2;
                } catch (Throwable th) {
                    th = th;
                    zipFile = zipFile4;
                    if (zipFile != null) {
                        try {
                            zipFile.close();
                        } catch (IOException e5) {
                            HMSLog.e(f8156a, "zipFile.close Exception!" + e5.getMessage());
                        }
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
            }
            return z2;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.util.zip.ZipFile] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v6 */
    public static boolean verifyApkHash(String str) throws Throwable {
        ?? r12 = 0;
        ZipFile zipFile = null;
        try {
            try {
                ZipFile zipFile2 = new ZipFile(str);
                try {
                    byte[] bArrA = a(zipFile2);
                    ArrayList<String> arrayListA = a(bArrA);
                    if (arrayListA != null) {
                        bArrA = a(arrayListA);
                    }
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    messageDigest.update(bArrA);
                    String strBytesToString = bytesToString(messageDigest.digest());
                    String str2 = f8160e;
                    if (str2 != null) {
                        if (str2.equals(strBytesToString)) {
                            try {
                                zipFile2.close();
                                return true;
                            } catch (Exception e2) {
                                HMSLog.i(f8156a, "close stream Exception!" + e2.getMessage());
                                return true;
                            }
                        }
                    }
                    try {
                        zipFile2.close();
                        return false;
                    } catch (Exception e3) {
                        HMSLog.i(f8156a, "close stream Exception!" + e3.getMessage());
                        return false;
                    }
                } catch (Exception e4) {
                    e = e4;
                    zipFile = zipFile2;
                    HMSLog.i(f8156a, "verifyApkHash Exception!" + e.getMessage());
                    if (zipFile == null) {
                        return false;
                    }
                    try {
                        zipFile.close();
                        return false;
                    } catch (Exception e5) {
                        r12 = f8156a;
                        HMSLog.i(r12, "close stream Exception!" + e5.getMessage());
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    r12 = zipFile2;
                    if (r12 != 0) {
                        try {
                            r12.close();
                        } catch (Exception e6) {
                            HMSLog.i(f8156a, "close stream Exception!" + e6.getMessage());
                        }
                    }
                    throw th;
                }
            } catch (Exception e7) {
                e = e7;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0099: MOVE (r0 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:45:0x0098 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.io.BufferedOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.util.zip.ZipFile] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.util.zip.ZipEntry] */
    /* JADX WARN: Type inference failed for: r8v11, types: [java.io.BufferedInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8, types: [java.io.InputStream] */
    public static byte[] a(ZipFile zipFile, String str) throws Throwable {
        Throwable th;
        OutputStream outputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th2;
        Exception e2;
        Object obj;
        ?? bufferedOutputStream;
        byte[] bArr;
        ?? entry = zipFile.getEntry(str);
        OutputStream outputStream2 = null;
        try {
            if (entry == 0) {
                return null;
            }
            try {
                zipFile = zipFile.getInputStream(entry);
                if (zipFile == 0) {
                    IOUtils.closeQuietly((InputStream) zipFile);
                    IOUtils.closeQuietly((InputStream) null);
                    IOUtils.closeQuietly((OutputStream) null);
                    IOUtils.closeQuietly((OutputStream) null);
                    return null;
                }
                try {
                    entry = new BufferedInputStream(zipFile);
                    try {
                        bArr = new byte[4096];
                        byteArrayOutputStream = new ByteArrayOutputStream();
                    } catch (Exception e3) {
                        e2 = e3;
                        byteArrayOutputStream = null;
                        zipFile = zipFile;
                        entry = entry;
                        bufferedOutputStream = byteArrayOutputStream;
                        HMSLog.i(f8156a, "getManifestBytes Exception!" + e2.getMessage());
                        IOUtils.closeQuietly((InputStream) zipFile);
                        IOUtils.closeQuietly((InputStream) entry);
                        IOUtils.closeQuietly((OutputStream) byteArrayOutputStream);
                        IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                        return null;
                    } catch (Throwable th3) {
                        th = th3;
                        byteArrayOutputStream = null;
                    }
                    try {
                        bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
                    } catch (Exception e4) {
                        e2 = e4;
                        bufferedOutputStream = 0;
                    } catch (Throwable th4) {
                        th = th4;
                        IOUtils.closeQuietly((InputStream) zipFile);
                        IOUtils.closeQuietly((InputStream) entry);
                        IOUtils.closeQuietly((OutputStream) byteArrayOutputStream);
                        IOUtils.closeQuietly(outputStream2);
                        throw th;
                    }
                } catch (Exception e5) {
                    e2 = e5;
                    obj = zipFile;
                    entry = 0;
                    byteArrayOutputStream = null;
                    zipFile = obj;
                    bufferedOutputStream = byteArrayOutputStream;
                    HMSLog.i(f8156a, "getManifestBytes Exception!" + e2.getMessage());
                    IOUtils.closeQuietly((InputStream) zipFile);
                    IOUtils.closeQuietly((InputStream) entry);
                    IOUtils.closeQuietly((OutputStream) byteArrayOutputStream);
                    IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                    return null;
                } catch (Throwable th5) {
                    th2 = th5;
                    th = th2;
                    entry = 0;
                    byteArrayOutputStream = null;
                    IOUtils.closeQuietly((InputStream) zipFile);
                    IOUtils.closeQuietly((InputStream) entry);
                    IOUtils.closeQuietly((OutputStream) byteArrayOutputStream);
                    IOUtils.closeQuietly(outputStream2);
                    throw th;
                }
                try {
                    for (int i2 = entry.read(bArr, 0, 4096); i2 > 0; i2 = entry.read(bArr, 0, 4096)) {
                        bufferedOutputStream.write(bArr, 0, i2);
                    }
                    bufferedOutputStream.flush();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    IOUtils.closeQuietly((InputStream) zipFile);
                    IOUtils.closeQuietly((InputStream) entry);
                    IOUtils.closeQuietly((OutputStream) byteArrayOutputStream);
                    IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                    return byteArray;
                } catch (Exception e6) {
                    e2 = e6;
                    HMSLog.i(f8156a, "getManifestBytes Exception!" + e2.getMessage());
                    IOUtils.closeQuietly((InputStream) zipFile);
                    IOUtils.closeQuietly((InputStream) entry);
                    IOUtils.closeQuietly((OutputStream) byteArrayOutputStream);
                    IOUtils.closeQuietly((OutputStream) bufferedOutputStream);
                    return null;
                }
            } catch (Exception e7) {
                e2 = e7;
                obj = null;
            } catch (Throwable th6) {
                th2 = th6;
                zipFile = 0;
            }
        } catch (Throwable th7) {
            th = th7;
            outputStream2 = outputStream;
        }
    }

    @TargetApi(19)
    public static ArrayList<String> a(byte[] bArr) throws IOException {
        if (bArr == null) {
            HMSLog.e(f8156a, "manifest is null！");
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8));
                try {
                    if (a(bufferedReader, arrayList)) {
                        bufferedReader.close();
                        byteArrayInputStream.close();
                        return arrayList;
                    }
                    bufferedReader.close();
                    byteArrayInputStream.close();
                    return null;
                } finally {
                }
            } finally {
            }
        } catch (IOException unused) {
            HMSLog.e(f8156a, "getManifestLinesArrary IOException!");
            return null;
        }
    }

    public static boolean b() {
        try {
        } catch (Exception e2) {
            HMSLog.i(f8156a, "verifyMDMSignatureV2 MDM verify Exception!:" + e2.getMessage());
        }
        if (a(Base64.decode(EMUI10_PK, 0), a(f8161f, "SHA-256"), b(f8158c), "SHA256withRSA")) {
            HMSLog.i(f8156a, "verifyMDMSignatureV2 verify successful!");
            return true;
        }
        HMSLog.i(f8156a, "verifyMDMSignatureV2 verify failure!");
        return false;
    }

    public static byte[] b(String str) {
        int i2;
        if (TextUtils.isEmpty(str)) {
            return new byte[0];
        }
        int length = str.length();
        if (length % 2 == 0) {
            i2 = length / 2;
        } else {
            i2 = (length / 2) + 1;
        }
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < length; i3 += 2) {
            int i4 = i3 + 1;
            if (i4 < length) {
                bArr[i3 / 2] = (byte) ((Character.digit(str.charAt(i3), 16) << 4) + Character.digit(str.charAt(i4), 16));
            } else {
                bArr[i3 / 2] = (byte) (Character.digit(str.charAt(i3), 16) << 4);
            }
        }
        return bArr;
    }

    @TargetApi(19)
    public static byte[] a(ArrayList<String> arrayList) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8));
        try {
            try {
                Collections.sort(arrayList);
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    String str = arrayList.get(i2);
                    bufferedWriter.write(str, 0, str.length());
                    bufferedWriter.write("\r\n", 0, 2);
                }
                bufferedWriter.flush();
            } catch (Exception e2) {
                HMSLog.i(f8156a, "getManifestBytesbySorted Exception!" + e2.getMessage());
            }
            IOUtils.closeQuietly((OutputStream) byteArrayOutputStream);
            IOUtils.closeQuietly((Writer) bufferedWriter);
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            IOUtils.closeQuietly((OutputStream) byteArrayOutputStream);
            IOUtils.closeQuietly((Writer) bufferedWriter);
            throw th;
        }
    }

    public static boolean a(BufferedReader bufferedReader, ArrayList<String> arrayList) throws IOException {
        String strA = a(bufferedReader);
        boolean z2 = false;
        while (strA != null) {
            if (strA.equals("Name: META-INF/HUAWEI.CER")) {
                String strA2 = a(bufferedReader);
                while (true) {
                    if (strA2 == null) {
                        break;
                    }
                    if (strA2.startsWith("Name:")) {
                        strA = strA2;
                        break;
                    }
                    strA2 = a(bufferedReader);
                }
                z2 = true;
            }
            if (strA.length() != 0) {
                arrayList.add(strA);
            }
            strA = a(bufferedReader);
        }
        return z2;
    }

    public static String a(BufferedReader bufferedReader) throws IOException {
        int i2;
        if (bufferedReader == null || (i2 = bufferedReader.read()) == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder(10);
        while (i2 != -1) {
            char c3 = (char) i2;
            if (c3 == '\n') {
                break;
            }
            if (sb.length() < 4096) {
                sb.append(c3);
                i2 = bufferedReader.read();
            } else {
                throw new IOException("cert line is too long!");
            }
        }
        String string = sb.toString();
        return (string.isEmpty() || !string.endsWith(StrPool.CR)) ? string : string.substring(0, string.length() - 1);
    }

    public static boolean a() {
        try {
            if (a(b("30820122300d06092a864886f70d01010105000382010f003082010a0282010100a3d269348ac59923f65e8111c337605e29a1d1bc54fa96c1445050dd14d8d63b10f9f0230bb87ef348183660bedcabfdec045e235ed96935799fcdb4af5c97717ff3b0954eaf1b723225b3a00f81cbd67ce6dc5a4c07f7741ad3bf1913a480c6e267ab1740f409edd2dc33c8b718a8e30e56d9a93f321723c1d0c9ea62115f996812ceef186954595e39a19b74245542c407f7dddb1d12e6eedcfc0bd7cd945ef7255ad0fc9e796258e0fb5e52a23013d15033a32b4071b65f3f924ae5c5761e22327b4d2ae60f4158a5eb15565ba079de29b81540f5fbb3be101a95357f367fc661d797074ff3826950029c52223e4594673a24a334cae62d63b838ba3df9770203010001"), a(f8161f, "SHA-256"), b(f8157b), "SHA256withRSA")) {
                HMSLog.i(f8156a, "verifyMDMSignatureV1 verify successful!");
                return true;
            }
            HMSLog.i(f8156a, "verifyMDMSignatureV1 verify failure!");
            return false;
        } catch (Exception e2) {
            HMSLog.i(f8156a, "verifyMDMSignatureV1 MDM verify Exception!:" + e2.getMessage());
            return false;
        }
    }

    public static boolean a(byte[] bArr, byte[] bArr2, byte[] bArr3, String str) throws Exception {
        Signature signature = Signature.getInstance(str);
        signature.initVerify(KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr)));
        signature.update(bArr2);
        return signature.verify(bArr3);
    }

    @TargetApi(19)
    public static byte[] a(String str, String str2) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(str2);
        messageDigest.update(str.getBytes(StandardCharsets.UTF_8.name()));
        return messageDigest.digest();
    }

    public static String a(String str) {
        return str != null ? Pattern.compile("\\s*|\t|\r|\n").matcher(str).replaceAll("") : "";
    }
}
