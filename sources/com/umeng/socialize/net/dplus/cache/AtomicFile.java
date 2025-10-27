package com.umeng.socialize.net.dplus.cache;

import android.util.Log;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SyncFailedException;

/* loaded from: classes6.dex */
public class AtomicFile {

    /* renamed from: a, reason: collision with root package name */
    private final File f23760a;

    /* renamed from: b, reason: collision with root package name */
    private final File f23761b;

    public AtomicFile(File file) {
        this.f23760a = file;
        this.f23761b = new File(file.getPath() + ".bak");
    }

    private static void a(File file, File file2) throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        long jCurrentTimeMillis = System.currentTimeMillis();
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = null;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int i2 = fileInputStream.read(bArr);
                if (i2 <= 0) {
                    fileInputStream.close();
                    fileOutputStream.close();
                    Log.d("AtomicFile", "comsum time:" + (System.currentTimeMillis() - jCurrentTimeMillis));
                    return;
                }
                fileOutputStream.write(bArr, 0, i2);
                Log.d("AtomicFile", i2 + "");
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    public void delete() {
        this.f23760a.delete();
        this.f23761b.delete();
    }

    public void failWrite(FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream != null) {
            a(fileOutputStream);
            try {
                fileOutputStream.close();
                this.f23760a.delete();
                this.f23761b.renameTo(this.f23760a);
            } catch (IOException e2) {
                SLog.error(UmengText.CACHE.CACHEFILE, e2);
            }
        }
    }

    public void finishWrite(FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream != null) {
            a(fileOutputStream);
            try {
                fileOutputStream.close();
                this.f23761b.delete();
            } catch (IOException e2) {
                SLog.error(UmengText.CACHE.CACHEFILE, e2);
            }
        }
    }

    public File getBaseFile() {
        return this.f23760a;
    }

    public FileInputStream openRead() throws FileNotFoundException {
        if (this.f23761b.exists()) {
            this.f23760a.delete();
            this.f23761b.renameTo(this.f23760a);
        }
        return new FileInputStream(this.f23760a);
    }

    public byte[] readFully() throws IOException {
        FileInputStream fileInputStreamOpenRead = openRead();
        try {
            byte[] bArr = new byte[fileInputStreamOpenRead.available()];
            int i2 = 0;
            while (true) {
                int i3 = fileInputStreamOpenRead.read(bArr, i2, bArr.length - i2);
                if (i3 <= 0) {
                    return bArr;
                }
                i2 += i3;
                int iAvailable = fileInputStreamOpenRead.available();
                if (iAvailable > bArr.length - i2) {
                    byte[] bArr2 = new byte[iAvailable + i2];
                    System.arraycopy(bArr, 0, bArr2, 0, i2);
                    bArr = bArr2;
                }
            }
        } finally {
            fileInputStreamOpenRead.close();
        }
    }

    public FileOutputStream startWrite(boolean z2) throws Throwable {
        if (this.f23760a.exists()) {
            if (this.f23761b.exists()) {
                this.f23760a.delete();
            } else if (this.f23760a.renameTo(this.f23761b)) {
                a(this.f23761b, this.f23760a);
            } else {
                Log.w("AtomicFile", "Couldn't rename file " + this.f23760a + " to backup file " + this.f23761b);
            }
        }
        try {
            return new FileOutputStream(this.f23760a, z2);
        } catch (FileNotFoundException e2) {
            if (!this.f23760a.getParentFile().mkdirs()) {
                SLog.error(UmengText.CACHE.CACHEFILE, e2);
            }
            try {
                return new FileOutputStream(this.f23760a, z2);
            } catch (FileNotFoundException unused) {
                SLog.error(UmengText.CACHE.CACHEFILE, e2);
                return null;
            }
        }
    }

    public static boolean a(FileOutputStream fileOutputStream) throws SyncFailedException {
        if (fileOutputStream == null) {
            return true;
        }
        try {
            fileOutputStream.getFD().sync();
            return true;
        } catch (IOException e2) {
            SLog.error(UmengText.CACHE.CACHEFILE, e2);
            return false;
        }
    }
}
