package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.push.ai;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public class en extends ai.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f24766a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f340a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.push.service.ao f341a;

    public en(Context context) {
        this.f24766a = context;
        this.f340a = context.getSharedPreferences("mipush_extra", 0);
        this.f341a = com.xiaomi.push.service.ao.a(context);
    }

    private List<ig> a(File file) {
        RandomAccessFile randomAccessFile;
        FileInputStream fileInputStream;
        ds dsVarM331a = dt.a().m331a();
        String strA = dsVarM331a == null ? "" : dsVarM331a.a();
        FileLock fileLock = null;
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        byte[] bArr = new byte[4];
        synchronized (dy.f24749a) {
            try {
                File file2 = new File(this.f24766a.getExternalFilesDir(null), "push_cdata.lock");
                y.m774a(file2);
                randomAccessFile = new RandomAccessFile(file2, InternalZipConstants.WRITE_MODE);
                try {
                    FileLock fileLockLock = randomAccessFile.getChannel().lock();
                    try {
                        fileInputStream = new FileInputStream(file);
                        while (fileInputStream.read(bArr) == 4) {
                            try {
                                int iA = ac.a(bArr);
                                byte[] bArr2 = new byte[iA];
                                if (fileInputStream.read(bArr2) != iA) {
                                    break;
                                }
                                byte[] bArrA = dx.a(strA, bArr2);
                                if (bArrA != null && bArrA.length != 0) {
                                    ig igVar = new ig();
                                    jp.a(igVar, bArrA);
                                    arrayList.add(igVar);
                                }
                            } catch (Exception unused) {
                                fileLock = fileLockLock;
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException unused2) {
                                    }
                                }
                                y.a(fileInputStream);
                                y.a(randomAccessFile);
                                return arrayList;
                            } catch (Throwable th) {
                                th = th;
                                fileLock = fileLockLock;
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException unused3) {
                                    }
                                }
                                y.a(fileInputStream);
                                y.a(randomAccessFile);
                                throw th;
                            }
                        }
                        if (fileLockLock != null && fileLockLock.isValid()) {
                            try {
                                fileLockLock.release();
                            } catch (IOException unused4) {
                            }
                        }
                        y.a(fileInputStream);
                    } catch (Exception unused5) {
                        fileInputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = null;
                    }
                } catch (Exception unused6) {
                    fileInputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = null;
                }
            } catch (Exception unused7) {
                randomAccessFile = null;
                fileInputStream = null;
            } catch (Throwable th4) {
                th = th4;
                randomAccessFile = null;
                fileInputStream = null;
            }
            y.a(randomAccessFile);
        }
        return arrayList;
    }

    private void a() {
        SharedPreferences.Editor editorEdit = this.f340a.edit();
        editorEdit.putLong("last_upload_data_timestamp", System.currentTimeMillis() / 1000);
        editorEdit.commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m338a() {
        if (as.d(this.f24766a)) {
            return false;
        }
        if (!as.f(this.f24766a) || c()) {
            return (as.g(this.f24766a) && !b()) || as.h(this.f24766a);
        }
        return true;
    }

    private boolean b() {
        if (!this.f341a.a(ic.Upload3GSwitch.a(), true)) {
            return false;
        }
        return Math.abs((System.currentTimeMillis() / 1000) - this.f340a.getLong("last_upload_data_timestamp", -1L)) > ((long) Math.max(86400, this.f341a.a(ic.Upload3GFrequency.a(), 432000)));
    }

    private boolean c() {
        if (!this.f341a.a(ic.Upload4GSwitch.a(), true)) {
            return false;
        }
        return Math.abs((System.currentTimeMillis() / 1000) - this.f340a.getLong("last_upload_data_timestamp", -1L)) > ((long) Math.max(86400, this.f341a.a(ic.Upload4GFrequency.a(), 259200)));
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 1;
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        File file = new File(this.f24766a.getExternalFilesDir(null), "push_cdata.data");
        if (!as.c(this.f24766a)) {
            if (file.length() > 1863680) {
                file.delete();
                return;
            }
            return;
        }
        if (!m338a() && file.exists()) {
            List<ig> listA = a(file);
            if (!ad.a(listA)) {
                int size = listA.size();
                if (size > 4000) {
                    listA = listA.subList(size - 4000, size);
                }
                iy iyVar = new iy();
                iyVar.a(listA);
                byte[] bArrA = y.a(jp.a(iyVar));
                je jeVar = new je("-1", false);
                jeVar.c(in.DataCollection.f622a);
                jeVar.a(bArrA);
                ds dsVarM331a = dt.a().m331a();
                if (dsVarM331a != null) {
                    dsVarM331a.a(jeVar, hw.Notification, null);
                }
                a();
            }
            file.delete();
            this.f340a.edit().remove("ltapn").commit();
        }
    }
}
