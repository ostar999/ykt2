package com.tencent.liteav.basic.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/* loaded from: classes6.dex */
public class d {
    public static boolean a(long j2) {
        return true;
    }

    public static boolean a(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnectedOrConnecting()) ? false : true;
    }

    public static String b(String str) throws Throwable {
        File file = new File(str);
        StringBuilder sb = new StringBuilder("");
        BufferedReader bufferedReader = null;
        if (!file.isFile()) {
            return null;
        }
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (true) {
                    try {
                        String line = bufferedReader2.readLine();
                        if (line == null) {
                            bufferedReader2.close();
                            String string = sb.toString();
                            try {
                                bufferedReader2.close();
                                return string;
                            } catch (IOException e2) {
                                throw new RuntimeException("IOException occurred. ", e2);
                            }
                        }
                        sb.append(line);
                    } catch (IOException e3) {
                        e = e3;
                        bufferedReader = bufferedReader2;
                        throw new RuntimeException("IOException occurred. ", e);
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e4) {
                                throw new RuntimeException("IOException occurred. ", e4);
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            e = e5;
        }
    }

    public static long a(File file) {
        return a(file, 5);
    }

    public static long a(File file, int i2) {
        long length;
        long j2 = 0;
        if (i2 <= 0) {
            return 0L;
        }
        try {
            for (File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    length = a(file2, i2 - 1);
                } else {
                    length = file2.length();
                }
                j2 += length;
            }
        } catch (Exception e2) {
            TXCLog.i("FileUtil", "getFolderSize exception " + e2.toString());
        }
        return j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r2, java.lang.String r3, java.lang.String r4) throws java.lang.Throwable {
        /*
            android.content.res.AssetManager r2 = r2.getAssets()
            r0 = 0
            java.io.InputStream r2 = r2.open(r3)     // Catch: java.lang.Throwable -> L27 java.io.IOException -> L2a
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L23
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L23
            a(r2, r3)     // Catch: java.lang.Throwable -> L1b java.io.IOException -> L1d
            a(r2)
        L14:
            r3.flush()     // Catch: java.io.IOException -> L39
            r3.close()     // Catch: java.io.IOException -> L39
            goto L39
        L1b:
            r4 = move-exception
            goto L21
        L1d:
            r4 = move-exception
            goto L25
        L1f:
            r4 = move-exception
            r3 = r0
        L21:
            r0 = r2
            goto L3b
        L23:
            r4 = move-exception
            r3 = r0
        L25:
            r0 = r2
            goto L2c
        L27:
            r4 = move-exception
            r3 = r0
            goto L3b
        L2a:
            r4 = move-exception
            r3 = r0
        L2c:
            java.lang.String r2 = "FileUtil"
            java.lang.String r1 = "copy asset file failed."
            com.tencent.liteav.basic.log.TXCLog.e(r2, r1, r4)     // Catch: java.lang.Throwable -> L3a
            a(r0)
            if (r3 == 0) goto L39
            goto L14
        L39:
            return
        L3a:
            r4 = move-exception
        L3b:
            a(r0)
            if (r3 == 0) goto L46
            r3.flush()     // Catch: java.io.IOException -> L46
            r3.close()     // Catch: java.io.IOException -> L46
        L46:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.util.d.a(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static String b(Context context, String str) throws IOException {
        InputStream inputStreamOpen = null;
        try {
            try {
                inputStreamOpen = context.getAssets().open(str);
                byte[] bArr = new byte[inputStreamOpen.available()];
                if (inputStreamOpen.read(bArr) <= 0) {
                    try {
                        inputStreamOpen.close();
                    } catch (IOException unused) {
                    }
                    return "";
                }
                inputStreamOpen.close();
                String str2 = new String(bArr, "utf-8");
                try {
                    inputStreamOpen.close();
                } catch (IOException unused2) {
                }
                return str2;
            } catch (IOException e2) {
                TXCLog.e("FileUtil", "read asset file failed.", e2);
                if (inputStreamOpen != null) {
                    try {
                        inputStreamOpen.close();
                    } catch (IOException unused3) {
                    }
                }
                return "";
            }
        } catch (Throwable th) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException unused4) {
                }
            }
            throw th;
        }
    }

    public static void a(InputStream inputStream, String str) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                a(inputStream, fileOutputStream2);
                a(fileOutputStream2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                a(fileOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i2);
            }
        }
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.isFile();
    }

    public static void a(String str, byte[] bArr) throws Throwable {
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(new File(str));
                try {
                    try {
                        BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                        try {
                            bufferedOutputStream2.write(bArr);
                            bufferedOutputStream2.close();
                        } catch (Exception e2) {
                            e = e2;
                            bufferedOutputStream = bufferedOutputStream2;
                            TXCLog.e("FileUtil", "write file failed.", e);
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                            if (fileOutputStream == null) {
                                return;
                            }
                            fileOutputStream.close();
                        } catch (Throwable th) {
                            th = th;
                            bufferedOutputStream = bufferedOutputStream2;
                            if (bufferedOutputStream != null) {
                                try {
                                    bufferedOutputStream.close();
                                } catch (Exception unused) {
                                    throw th;
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
            fileOutputStream.close();
        } catch (Exception unused2) {
        }
    }

    public static boolean a(Context context, String str) throws IOException {
        try {
            for (String str2 : context.getAssets().list("")) {
                if (str2.equals(str.trim())) {
                    TXCLog.i("isAssetFileExists", str + " exist");
                    return true;
                }
            }
            TXCLog.i("isAssetFileExists", str + " not exist");
            return false;
        } catch (IOException unused) {
            TXCLog.i("isAssetFileExists", str + " not exist");
            return false;
        }
    }

    public static void a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
