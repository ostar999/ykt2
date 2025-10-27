package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.ai;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public abstract class ei extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    protected int f24755a;

    /* renamed from: a, reason: collision with other field name */
    protected Context f339a;

    public ei(Context context, int i2) {
        this.f24755a = i2;
        this.f339a = context;
    }

    public static void a(Context context, ig igVar) {
        ds dsVarM331a = dt.a().m331a();
        String strA = dsVarM331a == null ? "" : dsVarM331a.a();
        if (TextUtils.isEmpty(strA) || TextUtils.isEmpty(igVar.a())) {
            return;
        }
        a(context, igVar, strA);
    }

    private static void a(Context context, ig igVar, String str) {
        BufferedOutputStream bufferedOutputStream;
        RandomAccessFile randomAccessFile;
        byte[] bArrB = dx.b(str, jp.a(igVar));
        if (bArrB == null || bArrB.length == 0) {
            return;
        }
        synchronized (dy.f24749a) {
            FileLock fileLock = null;
            try {
                try {
                    File file = new File(context.getExternalFilesDir(null), "push_cdata.lock");
                    y.m774a(file);
                    randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
                    try {
                        FileLock fileLockLock = randomAccessFile.getChannel().lock();
                        try {
                            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(context.getExternalFilesDir(null), "push_cdata.data"), true));
                            try {
                                bufferedOutputStream.write(ac.a(bArrB.length));
                                bufferedOutputStream.write(bArrB);
                                bufferedOutputStream.flush();
                                if (fileLockLock != null && fileLockLock.isValid()) {
                                    try {
                                        fileLockLock.release();
                                    } catch (IOException unused) {
                                    }
                                }
                                y.a(bufferedOutputStream);
                            } catch (IOException e2) {
                                e = e2;
                                fileLock = fileLockLock;
                                try {
                                    e.printStackTrace();
                                    if (fileLock != null && fileLock.isValid()) {
                                        try {
                                            fileLock.release();
                                        } catch (IOException unused2) {
                                        }
                                    }
                                    y.a(bufferedOutputStream);
                                    y.a(randomAccessFile);
                                } catch (Throwable th) {
                                    th = th;
                                    if (fileLock != null && fileLock.isValid()) {
                                        try {
                                            fileLock.release();
                                        } catch (IOException unused3) {
                                        }
                                    }
                                    y.a(bufferedOutputStream);
                                    y.a(randomAccessFile);
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                fileLock = fileLockLock;
                                if (fileLock != null) {
                                    fileLock.release();
                                }
                                y.a(bufferedOutputStream);
                                y.a(randomAccessFile);
                                throw th;
                            }
                        } catch (IOException e3) {
                            e = e3;
                            bufferedOutputStream = null;
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedOutputStream = null;
                        }
                    } catch (IOException e4) {
                        e = e4;
                        bufferedOutputStream = null;
                    } catch (Throwable th4) {
                        th = th4;
                        bufferedOutputStream = null;
                    }
                } catch (IOException e5) {
                    e = e5;
                    bufferedOutputStream = null;
                    randomAccessFile = null;
                } catch (Throwable th5) {
                    th = th5;
                    bufferedOutputStream = null;
                    randomAccessFile = null;
                }
                y.a(randomAccessFile);
            } catch (Throwable th6) {
                throw th6;
            }
        }
    }

    public abstract hz a();

    /* renamed from: a */
    public abstract String mo336a();

    /* renamed from: a */
    public boolean mo337a() {
        return true;
    }

    public boolean b() {
        return ag.a(this.f339a, String.valueOf(mo185a()), this.f24755a);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (b()) {
            ds dsVarM331a = dt.a().m331a();
            String strA = dsVarM331a == null ? "" : dsVarM331a.a();
            if (!TextUtils.isEmpty(strA) && mo337a()) {
                String strMo336a = mo336a();
                if (TextUtils.isEmpty(strMo336a)) {
                    return;
                }
                ig igVar = new ig();
                igVar.a(strMo336a);
                igVar.a(System.currentTimeMillis());
                igVar.a(a());
                a(this.f339a, igVar, strA);
            }
        }
    }
}
