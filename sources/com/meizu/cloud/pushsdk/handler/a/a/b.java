package com.meizu.cloud.pushsdk.handler.a.a;

import android.os.Environment;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private File f9441a;

    public b(String str) {
        this.f9441a = new File(str);
    }

    private void a(File file, ZipOutputStream zipOutputStream, String str) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str.trim().length() == 0 ? "" : File.separator);
        sb.append(file.getName());
        String string = sb.toString();
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                a(file2, zipOutputStream, string);
            }
            return;
        }
        DebugLogger.i("ZipTask", "current file " + string + "/" + file.getName() + " size is " + (file.length() / 1024) + "KB");
        if (file.length() >= 10485760) {
            return;
        }
        byte[] bArr = new byte[1048576];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file), 1048576);
        zipOutputStream.putNextEntry(new ZipEntry(string));
        while (true) {
            int i2 = bufferedInputStream.read(bArr);
            if (i2 == -1) {
                bufferedInputStream.close();
                zipOutputStream.flush();
                zipOutputStream.closeEntry();
                return;
            }
            zipOutputStream.write(bArr, 0, i2);
        }
    }

    private void a(Collection<File> collection, File file) throws Exception {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file), 1048576));
        Iterator<File> it = collection.iterator();
        while (it.hasNext()) {
            a(it.next(), zipOutputStream, "");
        }
        zipOutputStream.close();
    }

    public boolean a(List<String> list) throws Exception {
        if (!this.f9441a.exists()) {
            this.f9441a.getParentFile().mkdirs();
        }
        ArrayList arrayList = new ArrayList();
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            File file = new File(absolutePath + it.next());
            if (file.exists()) {
                arrayList.add(file);
            }
        }
        a(arrayList, this.f9441a);
        return true;
    }
}
