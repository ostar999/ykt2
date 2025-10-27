package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static volatile a f25560a;

    /* renamed from: a, reason: collision with other field name */
    private Context f975a;

    /* renamed from: a, reason: collision with other field name */
    private final Object f976a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private final String f977a = "mipush_region";

    /* renamed from: b, reason: collision with root package name */
    private final String f25561b = "mipush_region.lock";

    /* renamed from: c, reason: collision with root package name */
    private volatile String f25562c;

    public a(Context context) {
        this.f975a = context;
    }

    public static a a(Context context) {
        if (f25560a == null) {
            synchronized (a.class) {
                if (f25560a == null) {
                    f25560a = new a(context);
                }
            }
        }
        return f25560a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a, reason: collision with other method in class */
    private String m704a(Context context) {
        RandomAccessFile randomAccessFile;
        FileLock fileLockLock;
        FileLock fileLock = 0;
        fileLock = 0;
        if (!new File(context.getFilesDir(), "mipush_region").exists()) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Region no ready file to get data.");
            return null;
        }
        synchronized (this.f976a) {
            try {
                try {
                    File file = new File(context.getFilesDir(), "mipush_region.lock");
                    com.xiaomi.push.y.m774a(file);
                    randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
                } catch (Exception e2) {
                    e = e2;
                    fileLockLock = null;
                    randomAccessFile = null;
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile = null;
                }
                try {
                    fileLockLock = randomAccessFile.getChannel().lock();
                } catch (Exception e3) {
                    e = e3;
                    fileLockLock = null;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileLock != 0 && fileLock.isValid()) {
                        try {
                            fileLock.release();
                        } catch (IOException e4) {
                            com.xiaomi.channel.commonutils.logger.b.a(e4);
                        }
                    }
                    com.xiaomi.push.y.a(randomAccessFile);
                    throw th;
                }
                try {
                    String strB = b(context);
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e5) {
                            com.xiaomi.channel.commonutils.logger.b.a(e5);
                        }
                    }
                    com.xiaomi.push.y.a(randomAccessFile);
                    return strB;
                } catch (Exception e6) {
                    e = e6;
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e7) {
                            com.xiaomi.channel.commonutils.logger.b.a(e7);
                        }
                    }
                    com.xiaomi.push.y.a(randomAccessFile);
                    return null;
                }
            } catch (Throwable th3) {
                th = th3;
                fileLock = "mipush_region";
            }
        }
    }

    private void a(Context context, String str) {
        RandomAccessFile randomAccessFile;
        synchronized (this.f976a) {
            FileLock fileLockLock = null;
            try {
                try {
                    File file = new File(context.getFilesDir(), "mipush_region.lock");
                    com.xiaomi.push.y.m774a(file);
                    randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
                } catch (Exception e2) {
                    e = e2;
                    randomAccessFile = null;
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile = null;
                    if (fileLockLock != null) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e3) {
                            com.xiaomi.channel.commonutils.logger.b.a(e3);
                        }
                    }
                    com.xiaomi.push.y.a(randomAccessFile);
                    throw th;
                }
                try {
                    fileLockLock = randomAccessFile.getChannel().lock();
                    b(context, str);
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e4) {
                            com.xiaomi.channel.commonutils.logger.b.a(e4);
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e6) {
                            com.xiaomi.channel.commonutils.logger.b.a(e6);
                        }
                    }
                    com.xiaomi.push.y.a(randomAccessFile);
                }
                com.xiaomi.push.y.a(randomAccessFile);
            } catch (Throwable th2) {
                th = th2;
                if (fileLockLock != null && fileLockLock.isValid()) {
                    fileLockLock.release();
                }
                com.xiaomi.push.y.a(randomAccessFile);
                throw th;
            }
        }
    }

    private String b(Context context) throws Throwable {
        BufferedReader bufferedReader;
        Exception e2;
        FileInputStream fileInputStreamOpenFileInput;
        String str = "";
        FileInputStream fileInputStream = null;
        try {
            fileInputStreamOpenFileInput = context.openFileInput("mipush_region");
        } catch (Exception e3) {
            bufferedReader = null;
            e2 = e3;
            fileInputStreamOpenFileInput = null;
        } catch (Throwable th) {
            th = th;
            bufferedReader = null;
            com.xiaomi.push.y.a(fileInputStream);
            com.xiaomi.push.y.a(bufferedReader);
            throw th;
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStreamOpenFileInput));
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        str = str + line;
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileInputStreamOpenFileInput;
                        com.xiaomi.push.y.a(fileInputStream);
                        com.xiaomi.push.y.a(bufferedReader);
                        throw th;
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    com.xiaomi.push.y.a(fileInputStreamOpenFileInput);
                    com.xiaomi.push.y.a(bufferedReader);
                    return str;
                }
            }
        } catch (Exception e5) {
            bufferedReader = null;
            e2 = e5;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            fileInputStream = fileInputStreamOpenFileInput;
            com.xiaomi.push.y.a(fileInputStream);
            com.xiaomi.push.y.a(bufferedReader);
            throw th;
        }
        com.xiaomi.push.y.a(fileInputStreamOpenFileInput);
        com.xiaomi.push.y.a(bufferedReader);
        return str;
    }

    private void b(Context context, String str) {
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        try {
            try {
                fileOutputStreamOpenFileOutput = context.openFileOutput("mipush_region", 32768);
                fileOutputStreamOpenFileOutput.write(str.getBytes());
                fileOutputStreamOpenFileOutput.flush();
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        } finally {
            com.xiaomi.push.y.a(fileOutputStreamOpenFileOutput);
        }
    }

    public String a() {
        if (TextUtils.isEmpty(this.f25562c)) {
            this.f25562c = m704a(this.f975a);
        }
        return this.f25562c;
    }

    public void a(String str) {
        if (TextUtils.equals(str, this.f25562c)) {
            return;
        }
        this.f25562c = str;
        a(this.f975a, this.f25562c);
    }
}
