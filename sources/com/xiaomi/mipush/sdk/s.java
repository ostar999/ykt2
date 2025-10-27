package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class s {

    /* renamed from: a, reason: collision with root package name */
    private static volatile s f24578a;

    /* renamed from: a, reason: collision with other field name */
    private static final Object f163a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private Context f164a;

    private s(Context context) {
        this.f164a = context;
    }

    public static s a(Context context) {
        if (f24578a == null) {
            synchronized (s.class) {
                if (f24578a == null) {
                    f24578a = new s(context);
                }
            }
        }
        return f24578a;
    }

    private File a(String str) {
        File file = new File(this.f164a.getFilesDir() + "/crash");
        if (!file.exists()) {
            file.mkdirs();
            return null;
        }
        File[] fileArrListFiles = file.listFiles();
        for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
            if (fileArrListFiles[i2].isFile() && fileArrListFiles[i2].getName().startsWith(str)) {
                return fileArrListFiles[i2];
            }
        }
        return null;
    }

    public String a(File file) {
        if (file == null) {
            return "";
        }
        String[] strArrSplit = file.getName().split(":");
        return strArrSplit.length != 2 ? "" : strArrSplit[0];
    }

    public ArrayList<File> a() {
        ArrayList<File> arrayList = new ArrayList<>();
        File file = new File(this.f164a.getFilesDir() + "/crash");
        if (!file.exists()) {
            file.mkdirs();
            return arrayList;
        }
        File[] fileArrListFiles = file.listFiles();
        for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
            String[] strArrSplit = fileArrListFiles[i2].getName().split(":");
            if (strArrSplit.length >= 2 && Integer.parseInt(strArrSplit[1]) >= 1 && fileArrListFiles[i2].isFile()) {
                arrayList.add(fileArrListFiles[i2]);
            }
        }
        return arrayList;
    }

    public void a(String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (f163a) {
            File fileA = a(str2);
            if (fileA != null) {
                String[] strArrSplit = fileA.getName().split(":");
                if (strArrSplit.length < 2) {
                    return;
                }
                fileA.renameTo(new File(this.f164a.getFilesDir() + "/crash/" + str2 + ":" + String.valueOf(Integer.parseInt(strArrSplit[1]) + 1)));
            } else {
                FileOutputStream fileOutputStream = null;
                try {
                    try {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(new File(this.f164a.getFilesDir() + "/crash/" + str2 + ":1"));
                        try {
                            fileOutputStream2.write(str.getBytes());
                            fileOutputStream2.flush();
                            com.xiaomi.push.y.a(fileOutputStream2);
                        } catch (Exception e2) {
                            e = e2;
                            fileOutputStream = fileOutputStream2;
                            com.xiaomi.channel.commonutils.logger.b.a(e);
                            com.xiaomi.push.y.a(fileOutputStream);
                        } catch (Throwable th) {
                            th = th;
                            fileOutputStream = fileOutputStream2;
                            com.xiaomi.push.y.a(fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }
    }
}
