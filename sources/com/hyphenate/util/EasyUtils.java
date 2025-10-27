package com.hyphenate.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.text.StrPool;
import cn.hutool.crypto.KeyUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public class EasyUtils {
    public static final String TAG = "EasyUtils";
    private static Hashtable<String, String> resourceTable = new Hashtable<>();

    public static String convertByteArrayToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArr) {
            sb.append(String.format("0x%02X", Byte.valueOf(b3)));
        }
        return sb.toString();
    }

    public static X509Certificate[] convertToCerts(List<String> list) {
        ByteArrayInputStream byteArrayInputStream;
        Exception e2;
        ArrayList arrayList = new ArrayList();
        ByteArrayInputStream byteArrayInputStream2 = null;
        for (int i2 = 0; i2 < list.size(); i2++) {
            try {
                byteArrayInputStream = new ByteArrayInputStream(list.get(i2).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e3) {
                byteArrayInputStream = byteArrayInputStream2;
                e2 = e3;
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    arrayList.add((X509Certificate) CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509).generateCertificate(byteArrayInputStream));
                } catch (Exception e4) {
                    e2 = e4;
                    e2.printStackTrace();
                    EMLog.e(TAG, e2.getMessage());
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (IOException e5) {
                            e = e5;
                            e.printStackTrace();
                            byteArrayInputStream2 = byteArrayInputStream;
                        }
                    }
                    byteArrayInputStream2 = byteArrayInputStream;
                }
                try {
                    byteArrayInputStream.close();
                } catch (IOException e6) {
                    e = e6;
                    e.printStackTrace();
                    byteArrayInputStream2 = byteArrayInputStream;
                }
                byteArrayInputStream2 = byteArrayInputStream;
            } catch (Throwable th2) {
                th = th2;
                byteArrayInputStream2 = byteArrayInputStream;
                if (byteArrayInputStream2 != null) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
        }
        X509Certificate[] x509CertificateArr = new X509Certificate[arrayList.size()];
        arrayList.toArray(x509CertificateArr);
        return x509CertificateArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x003e A[EXC_TOP_SPLITTER, PHI: r0 r4
      0x003e: PHI (r0v1 boolean) = (r0v0 boolean), (r0v3 boolean) binds: [B:37:0x0055, B:24:0x003c] A[DONT_GENERATE, DONT_INLINE]
      0x003e: PHI (r4v5 java.io.FileOutputStream) = (r4v4 java.io.FileOutputStream), (r4v7 java.io.FileOutputStream) binds: [B:37:0x0055, B:24:0x003c] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean copyFile(java.lang.String r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L4f
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L4f
            boolean r2 = r2.exists()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L4f
            if (r2 == 0) goto L36
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L4f
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L4f
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L33
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L33
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L34
        L1b:
            int r1 = r2.read(r5)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L34
            r3 = -1
            if (r1 == r3) goto L26
            r4.write(r5, r0, r1)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L34
            goto L1b
        L26:
            r4.flush()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L34
            r5 = 1
            r0 = r5
            r1 = r2
            goto L37
        L2d:
            r5 = move-exception
            goto L31
        L2f:
            r5 = move-exception
            r4 = r1
        L31:
            r1 = r2
            goto L44
        L33:
            r4 = r1
        L34:
            r1 = r2
            goto L50
        L36:
            r4 = r1
        L37:
            if (r1 == 0) goto L3c
            r1.close()     // Catch: java.lang.Exception -> L3c
        L3c:
            if (r4 == 0) goto L58
        L3e:
            r4.close()     // Catch: java.lang.Exception -> L58
            goto L58
        L42:
            r5 = move-exception
            r4 = r1
        L44:
            if (r1 == 0) goto L49
            r1.close()     // Catch: java.lang.Exception -> L49
        L49:
            if (r4 == 0) goto L4e
            r4.close()     // Catch: java.lang.Exception -> L4e
        L4e:
            throw r5
        L4f:
            r4 = r1
        L50:
            if (r1 == 0) goto L55
            r1.close()     // Catch: java.lang.Exception -> L55
        L55:
            if (r4 == 0) goto L58
            goto L3e
        L58:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.util.EasyUtils.copyFile(java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00b4 A[Catch: Exception -> 0x00e0, TRY_LEAVE, TryCatch #5 {Exception -> 0x00e0, blocks: (B:3:0x0001, B:5:0x0019, B:7:0x0023, B:9:0x0045, B:34:0x00a2, B:40:0x00ae, B:42:0x00b4, B:8:0x0033), top: B:59:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00db A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean copyFolder(java.lang.String r13, java.lang.String r14) throws java.lang.Throwable {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch: java.lang.Exception -> Le0
            r1.<init>(r14)     // Catch: java.lang.Exception -> Le0
            r1.mkdirs()     // Catch: java.lang.Exception -> Le0
            java.io.File r1 = new java.io.File     // Catch: java.lang.Exception -> Le0
            r1.<init>(r13)     // Catch: java.lang.Exception -> Le0
            java.lang.String[] r1 = r1.list()     // Catch: java.lang.Exception -> Le0
            int r2 = r1.length     // Catch: java.lang.Exception -> Le0
            r3 = 1
            r4 = 0
            r6 = r0
            r5 = r4
        L17:
            if (r6 >= r2) goto Ldf
            r7 = r1[r6]     // Catch: java.lang.Exception -> Le0
            java.lang.String r8 = java.io.File.separator     // Catch: java.lang.Exception -> Le0
            boolean r9 = r13.endsWith(r8)     // Catch: java.lang.Exception -> Le0
            if (r9 == 0) goto L33
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Le0
            r8.<init>()     // Catch: java.lang.Exception -> Le0
            r8.append(r13)     // Catch: java.lang.Exception -> Le0
            r8.append(r7)     // Catch: java.lang.Exception -> Le0
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Exception -> Le0
            goto L45
        L33:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Le0
            r9.<init>()     // Catch: java.lang.Exception -> Le0
            r9.append(r13)     // Catch: java.lang.Exception -> Le0
            r9.append(r8)     // Catch: java.lang.Exception -> Le0
            r9.append(r7)     // Catch: java.lang.Exception -> Le0
            java.lang.String r8 = r9.toString()     // Catch: java.lang.Exception -> Le0
        L45:
            java.io.File r9 = new java.io.File     // Catch: java.lang.Exception -> Le0
            r9.<init>(r8)     // Catch: java.lang.Exception -> Le0
            boolean r8 = r9.isFile()     // Catch: java.lang.Exception -> Le0
            java.lang.String r10 = "/"
            if (r8 == 0) goto Lae
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L97 java.lang.Exception -> La3
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L97 java.lang.Exception -> La3
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            r11.<init>()     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            r11.append(r14)     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            r11.append(r10)     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            java.lang.String r12 = r9.getName()     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            r11.append(r12)     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            java.lang.String r11 = r11.toString()     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            r4.<init>(r11)     // Catch: java.lang.Throwable -> L92 java.lang.Exception -> L95
            r5 = 5120(0x1400, float:7.175E-42)
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
        L76:
            int r11 = r8.read(r5)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
            r12 = -1
            if (r11 == r12) goto L81
            r4.write(r5, r0, r11)     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
            goto L76
        L81:
            r4.flush()     // Catch: java.lang.Throwable -> L8d java.lang.Exception -> L90
            r8.close()     // Catch: java.lang.Exception -> L87
        L87:
            r4.close()     // Catch: java.lang.Exception -> L8a
        L8a:
            r5 = r4
            r4 = r8
            goto Lae
        L8d:
            r13 = move-exception
            r5 = r4
            goto L93
        L90:
            r5 = r4
            goto L95
        L92:
            r13 = move-exception
        L93:
            r4 = r8
            goto L98
        L95:
            r4 = r8
            goto La3
        L97:
            r13 = move-exception
        L98:
            if (r4 == 0) goto L9d
            r4.close()     // Catch: java.lang.Exception -> L9d
        L9d:
            if (r5 == 0) goto La2
            r5.close()     // Catch: java.lang.Exception -> La2
        La2:
            throw r13     // Catch: java.lang.Exception -> Le0
        La3:
            if (r4 == 0) goto La8
            r4.close()     // Catch: java.lang.Exception -> La8
        La8:
            if (r5 == 0) goto Lad
            r5.close()     // Catch: java.lang.Exception -> Lad
        Lad:
            r3 = r0
        Lae:
            boolean r8 = r9.isDirectory()     // Catch: java.lang.Exception -> Le0
            if (r8 == 0) goto Ldb
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Le0
            r8.<init>()     // Catch: java.lang.Exception -> Le0
            r8.append(r13)     // Catch: java.lang.Exception -> Le0
            r8.append(r10)     // Catch: java.lang.Exception -> Le0
            r8.append(r7)     // Catch: java.lang.Exception -> Le0
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Exception -> Le0
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Le0
            r9.<init>()     // Catch: java.lang.Exception -> Le0
            r9.append(r14)     // Catch: java.lang.Exception -> Le0
            r9.append(r10)     // Catch: java.lang.Exception -> Le0
            r9.append(r7)     // Catch: java.lang.Exception -> Le0
            java.lang.String r7 = r9.toString()     // Catch: java.lang.Exception -> Le0
            copyFolder(r8, r7)     // Catch: java.lang.Exception -> Le0
        Ldb:
            int r6 = r6 + 1
            goto L17
        Ldf:
            r0 = r3
        Le0:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.util.EasyUtils.copyFolder(java.lang.String, java.lang.String):boolean");
    }

    public static String getAppResourceString(Context context, String str) {
        String str2 = resourceTable.get(str);
        if (str2 != null) {
            return str2;
        }
        String string = context.getString(context.getResources().getIdentifier(str, TypedValues.Custom.S_STRING, context.getPackageName()));
        if (string != null) {
            resourceTable.put(str, string);
        }
        return string;
    }

    public static String getMediaRequestUid(String str, String str2) {
        return str + StrPool.UNDERLINE + str2;
    }

    public static List<String> getRunningApps(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ArrayList arrayList = new ArrayList();
        try {
            runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (runningAppProcesses == null) {
            return arrayList;
        }
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (it.hasNext()) {
            String strSubstring = it.next().processName;
            if (strSubstring.contains(":")) {
                strSubstring = strSubstring.substring(0, strSubstring.indexOf(":"));
            }
            if (!arrayList.contains(strSubstring)) {
                arrayList.add(strSubstring);
            }
        }
        return arrayList;
    }

    public static X509TrustManager getSystemDefaultTrustManager() throws NoSuchAlgorithmException, KeyStoreException {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
                if (trustManager instanceof X509TrustManager) {
                    return (X509TrustManager) trustManager;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
    }

    public static String getTopActivityName(Context context) throws SecurityException {
        try {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(1);
            if (runningTasks != null && runningTasks.size() >= 1) {
                return runningTasks.get(0).topActivity.getClassName();
            }
            return "";
        } catch (SecurityException e2) {
            EMLog.d(TAG, "Apk doesn't hold GET_TASKS permission");
            e2.printStackTrace();
            return "";
        }
    }

    public static boolean isAppRunningForeground(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isSingleActivity(Context context) throws SecurityException {
        List<ActivityManager.RunningTaskInfo> runningTasks;
        try {
            runningTasks = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(1);
        } catch (SecurityException e2) {
            e2.printStackTrace();
            runningTasks = null;
        }
        return runningTasks != null && runningTasks.size() >= 1 && runningTasks.get(0).numRunning == 1;
    }

    public static String useridFromJid(String str) {
        return (str.contains(StrPool.UNDERLINE) && str.contains("@easemob.com")) ? str.substring(str.indexOf(StrPool.UNDERLINE) + 1, str.indexOf("@")) : str.contains(StrPool.UNDERLINE) ? str.substring(str.indexOf(StrPool.UNDERLINE) + 1) : str;
    }

    public static boolean writeToZipFile(byte[] bArr, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        GZIPOutputStream gZIPOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(str);
            try {
                try {
                    GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(new BufferedOutputStream(fileOutputStream));
                    try {
                        gZIPOutputStream2.write(bArr);
                        try {
                            gZIPOutputStream2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                        if (!EMLog.debugMode) {
                            return true;
                        }
                        File file = new File(str);
                        EMLog.d("zip", "data size:" + bArr.length + " zip file size:" + file.length() + "zip file ratio%: " + Double.valueOf(new DecimalFormat("#.##").format((file.length() / bArr.length) * 100.0d)).doubleValue());
                        return true;
                    } catch (Exception e4) {
                        e = e4;
                        gZIPOutputStream = gZIPOutputStream2;
                        e.printStackTrace();
                        if (gZIPOutputStream != null) {
                            try {
                                gZIPOutputStream.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (fileOutputStream == null) {
                            return false;
                        }
                        try {
                            fileOutputStream.close();
                            return false;
                        } catch (IOException e6) {
                            e6.printStackTrace();
                            return false;
                        }
                    } catch (Throwable th) {
                        th = th;
                        gZIPOutputStream = gZIPOutputStream2;
                        if (gZIPOutputStream != null) {
                            try {
                                gZIPOutputStream.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                        }
                        if (fileOutputStream == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream.close();
                            throw th;
                        } catch (IOException e8) {
                            e8.printStackTrace();
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e9) {
                e = e9;
            }
        } catch (Exception e10) {
            e = e10;
            fileOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
    }
}
