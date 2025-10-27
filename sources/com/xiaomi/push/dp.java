package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public class dp implements LoggerInterface {

    /* renamed from: a, reason: collision with other field name */
    private Context f332a;

    /* renamed from: b, reason: collision with root package name */
    private String f24742b;

    /* renamed from: c, reason: collision with root package name */
    private String f24743c = "";

    /* renamed from: a, reason: collision with other field name */
    private static final SimpleDateFormat f330a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");

    /* renamed from: a, reason: collision with root package name */
    private static al f24741a = new al(true);

    /* renamed from: a, reason: collision with other field name */
    public static String f329a = "/MiPushLog";

    /* renamed from: a, reason: collision with other field name */
    private static List<Pair<String, Throwable>> f331a = Collections.synchronizedList(new ArrayList());

    public dp(Context context) {
        this.f332a = context;
        if (context.getApplicationContext() != null) {
            this.f332a = context.getApplicationContext();
        }
        this.f24742b = this.f332a.getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x0131 -> B:104:0x0174). Please report as a decompilation issue!!! */
    /* renamed from: a, reason: collision with other method in class */
    public void m329a() throws Throwable {
        FileLock fileLockLock;
        RandomAccessFile randomAccessFile;
        File file;
        File externalFilesDir;
        BufferedWriter bufferedWriter = null;
        try {
            try {
                if (TextUtils.isEmpty(this.f24743c) && (externalFilesDir = this.f332a.getExternalFilesDir(null)) != null) {
                    this.f24743c = externalFilesDir.getAbsolutePath() + "";
                }
                file = new File(this.f24743c + f329a);
            } catch (Exception e2) {
                e = e2;
                fileLockLock = null;
                randomAccessFile = null;
            } catch (Throwable th) {
                th = th;
                fileLockLock = null;
                randomAccessFile = null;
            }
        } catch (IOException e3) {
            Log.e(this.f24742b, "", e3);
        }
        if ((!file.exists() || !file.isDirectory()) && !file.mkdirs()) {
            Log.w(this.f24742b, "Create mipushlog directory fail.");
            return;
        }
        File file2 = new File(file, "log.lock");
        if (!file2.exists() || file2.isDirectory()) {
            file2.createNewFile();
        }
        randomAccessFile = new RandomAccessFile(file2, InternalZipConstants.WRITE_MODE);
        try {
            fileLockLock = randomAccessFile.getChannel().lock();
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file, "log1.txt"), true)));
                    while (!f331a.isEmpty()) {
                        try {
                            Pair<String, Throwable> pairRemove = f331a.remove(0);
                            String str = (String) pairRemove.first;
                            if (pairRemove.second != null) {
                                str = (str + "\n") + Log.getStackTraceString((Throwable) pairRemove.second);
                            }
                            bufferedWriter2.write(str + "\n");
                        } catch (Exception e4) {
                            e = e4;
                            bufferedWriter = bufferedWriter2;
                            Log.e(this.f24742b, "", e);
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e5) {
                                    Log.e(this.f24742b, "", e5);
                                }
                            }
                            if (fileLockLock != null && fileLockLock.isValid()) {
                                try {
                                    fileLockLock.release();
                                } catch (IOException e6) {
                                    Log.e(this.f24742b, "", e6);
                                }
                            }
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedWriter = bufferedWriter2;
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e7) {
                                    Log.e(this.f24742b, "", e7);
                                }
                            }
                            if (fileLockLock != null && fileLockLock.isValid()) {
                                try {
                                    fileLockLock.release();
                                } catch (IOException e8) {
                                    Log.e(this.f24742b, "", e8);
                                }
                            }
                            if (randomAccessFile == null) {
                                throw th;
                            }
                            try {
                                randomAccessFile.close();
                                throw th;
                            } catch (IOException e9) {
                                Log.e(this.f24742b, "", e9);
                                throw th;
                            }
                        }
                    }
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                    File file3 = new File(file, "log1.txt");
                    if (file3.length() >= 1048576) {
                        File file4 = new File(file, "log0.txt");
                        if (file4.exists() && file4.isFile()) {
                            file4.delete();
                        }
                        file3.renameTo(file4);
                    }
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e10) {
                            Log.e(this.f24742b, "", e10);
                        }
                    }
                    randomAccessFile.close();
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e11) {
                e = e11;
            }
        } catch (Exception e12) {
            e = e12;
            fileLockLock = null;
        } catch (Throwable th4) {
            th = th4;
            fileLockLock = null;
        }
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void log(String str) {
        log(str, null);
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void log(String str, Throwable th) {
        f331a.add(new Pair<>(String.format("%1$s %2$s %3$s ", f330a.format(new Date()), this.f24742b, str), th));
        f24741a.a(new dq(this));
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void setTag(String str) {
        this.f24742b = str;
    }
}
