package com.umeng.socialize.b.b;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.umeng.socialize.utils.SLog;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: com.umeng.socialize.b.b.a$a, reason: collision with other inner class name */
    public static class C0392a implements Comparator<File> {
        private C0392a() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(File file, File file2) {
            if (file.lastModified() > file2.lastModified()) {
                return 1;
            }
            return file.lastModified() == file2.lastModified() ? 0 : -1;
        }
    }

    public static void a() {
        if ((Environment.getExternalStorageDirectory() == null || TextUtils.isEmpty(Environment.getExternalStorageDirectory().getPath())) ? false : true) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getPath());
            String str = File.separator;
            sb.append(str);
            sb.append(c.f23646e);
            sb.append(str);
            c.f23645d = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getDataDirectory().getPath());
            String str2 = File.separator;
            sb2.append(str2);
            sb2.append(c.f23646e);
            sb2.append(str2);
            c.f23645d = sb2.toString();
        }
        File file = new File(c.f23645d);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            a(c.f23645d);
        } catch (Exception e2) {
            SLog.error(e2);
        }
    }

    public static void b() {
        a();
    }

    private static int c() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (int) ((statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1048576.0d);
    }

    private static void a(String str) {
        File[] fileArrListFiles = new File(str).listFiles();
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            return;
        }
        int length = 0;
        for (File file : fileArrListFiles) {
            length = (int) (length + file.length());
        }
        if (length > 0 || 40 > c()) {
            Arrays.sort(fileArrListFiles, new C0392a());
            for (File file2 : fileArrListFiles) {
                file2.delete();
            }
        }
    }
}
