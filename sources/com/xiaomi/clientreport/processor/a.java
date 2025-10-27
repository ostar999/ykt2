package com.xiaomi.clientreport.processor;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import androidx.core.app.NotificationCompat;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.push.ac;
import com.xiaomi.push.ay;
import com.xiaomi.push.be;
import com.xiaomi.push.i;
import com.xiaomi.push.y;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public class a implements IEventProcessor {

    /* renamed from: a, reason: collision with root package name */
    protected Context f24497a;

    /* renamed from: a, reason: collision with other field name */
    private HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> f100a;

    public a(Context context) {
        a(context);
    }

    public static String a(com.xiaomi.clientreport.data.a aVar) {
        return String.valueOf(aVar.production);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
    
        com.xiaomi.channel.commonutils.logger.b.d("eventData read from cache file failed because magicNumber error");
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005d, code lost:
    
        r9 = "eventData read from cache file failed cause lengthBuffer < 1 || lengthBuffer > 4K";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<java.lang.String> a(java.lang.String r9) throws java.lang.Throwable {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 4
            byte[] r2 = new byte[r1]
            byte[] r3 = new byte[r1]
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            r6.<init>(r9)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6c
        L15:
            int r9 = r5.read(r2)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            r4 = -1
            if (r9 != r4) goto L1d
            goto L60
        L1d:
            java.lang.String r6 = "eventData read from cache file failed because magicNumber error"
            if (r9 == r1) goto L25
        L21:
            com.xiaomi.channel.commonutils.logger.b.d(r6)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            goto L60
        L25:
            int r9 = com.xiaomi.push.ac.a(r2)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            r7 = -573785174(0xffffffffddccbbaa, float:-1.8440715E18)
            if (r9 == r7) goto L2f
            goto L21
        L2f:
            int r9 = r5.read(r3)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            if (r9 != r4) goto L36
            goto L60
        L36:
            if (r9 == r1) goto L3e
            java.lang.String r9 = "eventData read from cache file failed cause lengthBuffer error"
        L3a:
            com.xiaomi.channel.commonutils.logger.b.d(r9)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            goto L60
        L3e:
            int r9 = com.xiaomi.push.ac.a(r3)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            r4 = 1
            if (r9 < r4) goto L5d
            r4 = 4096(0x1000, float:5.74E-42)
            if (r9 <= r4) goto L4a
            goto L5d
        L4a:
            byte[] r4 = new byte[r9]     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            int r6 = r5.read(r4)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            if (r6 == r9) goto L55
            java.lang.String r9 = "eventData read from cache file failed cause buffer size not equal length"
            goto L3a
        L55:
            java.lang.String r9 = r8.bytesToString(r4)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            r0.add(r9)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            goto L15
        L5d:
            java.lang.String r9 = "eventData read from cache file failed cause lengthBuffer < 1 || lengthBuffer > 4K"
            goto L3a
        L60:
            com.xiaomi.push.y.a(r5)
            goto L73
        L64:
            r9 = move-exception
            r4 = r5
            goto L74
        L67:
            r9 = move-exception
            r4 = r5
            goto L6d
        L6a:
            r9 = move-exception
            goto L74
        L6c:
            r9 = move-exception
        L6d:
            com.xiaomi.channel.commonutils.logger.b.a(r9)     // Catch: java.lang.Throwable -> L6a
            com.xiaomi.push.y.a(r4)
        L73:
            return r0
        L74:
            com.xiaomi.push.y.a(r4)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.a.a(java.lang.String):java.util.List");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void a(com.xiaomi.clientreport.data.a[] aVarArr, String str) throws Throwable {
        if (aVarArr == null || aVarArr.length <= 0 || TextUtils.isEmpty(str)) {
            return;
        }
        File file = new File(str);
        BufferedOutputStream bufferedOutputStream = null;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream3 = new BufferedOutputStream(new FileOutputStream(file, true));
                try {
                    int length = aVarArr.length;
                    for (com.xiaomi.clientreport.data.a aVar : aVarArr) {
                        if (aVar != null) {
                            byte[] bArrStringToBytes = stringToBytes(aVar.toJsonString());
                            if (bArrStringToBytes == null || bArrStringToBytes.length < 1 || bArrStringToBytes.length > 4096) {
                                com.xiaomi.channel.commonutils.logger.b.d("event data throw a invalid item ");
                            } else {
                                bufferedOutputStream3.write(ac.a(-573785174));
                                bufferedOutputStream3.write(ac.a(bArrStringToBytes.length));
                                bufferedOutputStream3.write(bArrStringToBytes);
                                bufferedOutputStream3.flush();
                            }
                        }
                    }
                    y.a(bufferedOutputStream3);
                    bufferedOutputStream = length;
                } catch (Exception e2) {
                    e = e2;
                    bufferedOutputStream2 = bufferedOutputStream3;
                    com.xiaomi.channel.commonutils.logger.b.a("event data write to cache file failed cause exception", e);
                    y.a(bufferedOutputStream2);
                    bufferedOutputStream = bufferedOutputStream2;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream3;
                    y.a(bufferedOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private String b(com.xiaomi.clientreport.data.a aVar) {
        File externalFilesDir = this.f24497a.getExternalFilesDir(NotificationCompat.CATEGORY_EVENT);
        String strA = a(aVar);
        if (externalFilesDir == null) {
            return null;
        }
        String str = externalFilesDir.getAbsolutePath() + File.separator + strA;
        for (int i2 = 0; i2 < 100; i2++) {
            String str2 = str + i2;
            if (be.m229a(this.f24497a, str2)) {
                return str2;
            }
        }
        return null;
    }

    @Override // com.xiaomi.clientreport.processor.c
    public void a() throws Throwable {
        File file;
        RandomAccessFile randomAccessFile;
        Exception e2;
        String absolutePath;
        be.a(this.f24497a, NotificationCompat.CATEGORY_EVENT, "eventUploading");
        File[] fileArrM230a = be.m230a(this.f24497a, "eventUploading");
        if (fileArrM230a == null || fileArrM230a.length <= 0) {
            return;
        }
        FileLock fileLockLock = null;
        File file2 = null;
        RandomAccessFile randomAccessFile2 = null;
        for (File file3 : fileArrM230a) {
            if (file3 == null) {
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException e3) {
                        com.xiaomi.channel.commonutils.logger.b.a(e3);
                    }
                }
                y.a(randomAccessFile2);
                if (file2 != null) {
                    file2.delete();
                }
            } else {
                try {
                    absolutePath = file3.getAbsolutePath();
                    file = new File(absolutePath + ".lock");
                    try {
                        y.m774a(file);
                        randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
                    } catch (Exception e4) {
                        randomAccessFile = randomAccessFile2;
                        e2 = e4;
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Exception e5) {
                    file = file2;
                    randomAccessFile = randomAccessFile2;
                    e2 = e5;
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    try {
                        fileLockLock = randomAccessFile.getChannel().lock();
                        a(a(absolutePath));
                        file3.delete();
                        if (fileLockLock != null && fileLockLock.isValid()) {
                            try {
                                fileLockLock.release();
                            } catch (IOException e6) {
                                com.xiaomi.channel.commonutils.logger.b.a(e6);
                            }
                        }
                        y.a(randomAccessFile);
                    } catch (Exception e7) {
                        e2 = e7;
                        com.xiaomi.channel.commonutils.logger.b.a(e2);
                        if (fileLockLock != null && fileLockLock.isValid()) {
                            try {
                                fileLockLock.release();
                            } catch (IOException e8) {
                                com.xiaomi.channel.commonutils.logger.b.a(e8);
                            }
                        }
                        y.a(randomAccessFile);
                        if (file != null) {
                            file.delete();
                        }
                        randomAccessFile2 = randomAccessFile;
                        file2 = file;
                    }
                    file.delete();
                    randomAccessFile2 = randomAccessFile;
                    file2 = file;
                } catch (Throwable th3) {
                    th = th3;
                    randomAccessFile2 = randomAccessFile;
                    file2 = file;
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e9) {
                            com.xiaomi.channel.commonutils.logger.b.a(e9);
                        }
                    }
                    y.a(randomAccessFile2);
                    if (file2 == null) {
                        throw th;
                    }
                    file2.delete();
                    throw th;
                }
            }
        }
    }

    public void a(Context context) {
        this.f24497a = context;
    }

    @Override // com.xiaomi.clientreport.processor.d
    /* renamed from: a, reason: collision with other method in class */
    public void mo122a(com.xiaomi.clientreport.data.a aVar) {
        if ((aVar instanceof EventClientReport) && this.f100a != null) {
            EventClientReport eventClientReport = (EventClientReport) aVar;
            String strA = a((com.xiaomi.clientreport.data.a) eventClientReport);
            ArrayList<com.xiaomi.clientreport.data.a> arrayList = this.f100a.get(strA);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            arrayList.add(eventClientReport);
            this.f100a.put(strA, arrayList);
            com.xiaomi.channel.commonutils.logger.b.c("pre event out " + this.f100a.size() + " list " + arrayList.size());
        }
    }

    public void a(List<String> list) {
        be.a(this.f24497a, list);
    }

    public void a(com.xiaomi.clientreport.data.a[] aVarArr) throws Throwable {
        RandomAccessFile randomAccessFile;
        if (aVarArr == null || aVarArr.length <= 0) {
            return;
        }
        com.xiaomi.clientreport.data.a aVar = aVarArr[0];
        if (aVar == null) {
            return;
        }
        String strB = b(aVar);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        FileLock fileLockLock = null;
        try {
            File file = new File(strB + ".lock");
            y.m774a(file);
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
            y.a(randomAccessFile);
            throw th;
        }
        try {
            try {
                fileLockLock = randomAccessFile.getChannel().lock();
                for (com.xiaomi.clientreport.data.a aVar2 : aVarArr) {
                    if (aVar2 != null) {
                        a(aVarArr, strB);
                    }
                }
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException e4) {
                        e = e4;
                        com.xiaomi.channel.commonutils.logger.b.a(e);
                        y.a(randomAccessFile);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileLockLock != null && fileLockLock.isValid()) {
                    fileLockLock.release();
                }
                y.a(randomAccessFile);
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            com.xiaomi.channel.commonutils.logger.b.a(e);
            if (fileLockLock != null && fileLockLock.isValid()) {
                try {
                    fileLockLock.release();
                } catch (IOException e6) {
                    e = e6;
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    y.a(randomAccessFile);
                }
            }
            y.a(randomAccessFile);
        }
        y.a(randomAccessFile);
    }

    @Override // com.xiaomi.clientreport.processor.d
    public void b() throws Throwable {
        HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> map = this.f100a;
        if (map == null) {
            return;
        }
        if (map.size() > 0) {
            Iterator<String> it = this.f100a.keySet().iterator();
            while (it.hasNext()) {
                ArrayList<com.xiaomi.clientreport.data.a> arrayList = this.f100a.get(it.next());
                if (arrayList != null && arrayList.size() > 0) {
                    com.xiaomi.clientreport.data.a[] aVarArr = new com.xiaomi.clientreport.data.a[arrayList.size()];
                    com.xiaomi.channel.commonutils.logger.b.c("begin write eventJob " + arrayList.size());
                    arrayList.toArray(aVarArr);
                    a(aVarArr);
                }
            }
        }
        this.f100a.clear();
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public String bytesToString(byte[] bArr) {
        byte[] bArrA;
        if (bArr != null && bArr.length >= 1) {
            if (!com.xiaomi.clientreport.manager.a.a(this.f24497a).a().isEventEncrypted()) {
                return ay.a(bArr);
            }
            String strM227a = be.m227a(this.f24497a);
            if (!TextUtils.isEmpty(strM227a) && (bArrA = be.a(strM227a)) != null && bArrA.length > 0) {
                try {
                    return ay.a(Base64.decode(i.a(bArrA, bArr), 2));
                } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
            }
        }
        return null;
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public void setEventMap(HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> map) {
        this.f100a = map;
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public byte[] stringToBytes(String str) {
        byte[] bArrA;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!com.xiaomi.clientreport.manager.a.a(this.f24497a).a().isEventEncrypted()) {
            return ay.m210a(str);
        }
        String strM227a = be.m227a(this.f24497a);
        byte[] bArrM210a = ay.m210a(str);
        if (!TextUtils.isEmpty(strM227a) && bArrM210a != null && bArrM210a.length > 1 && (bArrA = be.a(strM227a)) != null) {
            try {
                if (bArrA.length > 1) {
                    return i.b(bArrA, Base64.encode(bArrM210a, 2));
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        return null;
    }
}
