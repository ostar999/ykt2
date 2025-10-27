package com.xiaomi.push;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public final class w {

    /* renamed from: a, reason: collision with root package name */
    private static final Set<String> f25729a = Collections.synchronizedSet(new HashSet());

    /* renamed from: a, reason: collision with other field name */
    private Context f1099a;

    /* renamed from: a, reason: collision with other field name */
    private RandomAccessFile f1100a;

    /* renamed from: a, reason: collision with other field name */
    private String f1101a;

    /* renamed from: a, reason: collision with other field name */
    private FileLock f1102a;

    private w(Context context) {
        this.f1099a = context;
    }

    public static w a(Context context, File file) throws IOException {
        com.xiaomi.channel.commonutils.logger.b.c("Locking: " + file.getAbsolutePath());
        String str = file.getAbsolutePath() + ".LOCK";
        File file2 = new File(str);
        if (!file2.exists()) {
            file2.getParentFile().mkdirs();
            file2.createNewFile();
        }
        Set<String> set = f25729a;
        if (!set.add(str)) {
            throw new IOException("abtain lock failure");
        }
        w wVar = new w(context);
        wVar.f1101a = str;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file2, InternalZipConstants.WRITE_MODE);
            wVar.f1100a = randomAccessFile;
            wVar.f1102a = randomAccessFile.getChannel().lock();
            com.xiaomi.channel.commonutils.logger.b.c("Locked: " + str + " :" + wVar.f1102a);
            if (wVar.f1102a == null) {
                RandomAccessFile randomAccessFile2 = wVar.f1100a;
                if (randomAccessFile2 != null) {
                    y.a(randomAccessFile2);
                }
                set.remove(wVar.f1101a);
            }
            return wVar;
        } catch (Throwable th) {
            if (wVar.f1102a == null) {
                RandomAccessFile randomAccessFile3 = wVar.f1100a;
                if (randomAccessFile3 != null) {
                    y.a(randomAccessFile3);
                }
                f25729a.remove(wVar.f1101a);
            }
            throw th;
        }
    }

    public void a() throws IOException {
        com.xiaomi.channel.commonutils.logger.b.c("unLock: " + this.f1102a);
        FileLock fileLock = this.f1102a;
        if (fileLock != null && fileLock.isValid()) {
            try {
                this.f1102a.release();
            } catch (IOException unused) {
            }
            this.f1102a = null;
        }
        RandomAccessFile randomAccessFile = this.f1100a;
        if (randomAccessFile != null) {
            y.a(randomAccessFile);
        }
        f25729a.remove(this.f1101a);
    }
}
