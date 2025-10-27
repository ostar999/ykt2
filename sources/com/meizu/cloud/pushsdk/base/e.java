package com.meizu.cloud.pushsdk.base;

import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/* loaded from: classes4.dex */
class e {

    /* renamed from: d, reason: collision with root package name */
    private BufferedWriter f9259d;

    /* renamed from: a, reason: collision with root package name */
    private String f9256a = "EncryptionWriter";

    /* renamed from: b, reason: collision with root package name */
    private SimpleDateFormat f9257b = new SimpleDateFormat("yyyy-MM-dd");

    /* renamed from: e, reason: collision with root package name */
    private int f9260e = 7;

    /* renamed from: f, reason: collision with root package name */
    private String f9261f = ".log.txt";

    /* renamed from: c, reason: collision with root package name */
    private d f9258c = new d("lo");

    public class a implements Comparator<File> {
        public a() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(File file, File file2) {
            long jLastModified = file.lastModified() - file2.lastModified();
            if (jLastModified > 0) {
                return -1;
            }
            return jLastModified == 0 ? 0 : 1;
        }
    }

    public void a() throws IOException {
        BufferedWriter bufferedWriter = this.f9259d;
        if (bufferedWriter != null) {
            bufferedWriter.flush();
            this.f9259d.close();
            this.f9259d = null;
        }
    }

    public void a(File file) {
        File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.meizu.cloud.pushsdk.base.e.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return file2.getName().endsWith(e.this.f9261f);
            }
        });
        if (fileArrListFiles != null || fileArrListFiles.length > this.f9260e) {
            Arrays.sort(fileArrListFiles, new a());
            for (int i2 = this.f9260e; i2 < fileArrListFiles.length; i2++) {
                fileArrListFiles[i2].delete();
            }
        }
    }

    public void a(String str) throws IOException {
        File file = new File(str);
        if (!file.exists() && !file.mkdirs()) {
            throw new IOException("create " + str + " dir failed!!!");
        }
        String str2 = this.f9257b.format(new Date());
        File file2 = new File(str, str2 + this.f9261f);
        if (!file2.exists()) {
            if (file2.createNewFile()) {
                a(file);
            } else {
                Log.e(this.f9256a, "create new file " + str2 + " failed !!!");
            }
        }
        this.f9259d = new BufferedWriter(new FileWriter(file2, true));
    }

    public void a(String str, String str2, String str3) throws IOException {
        if (this.f9259d != null) {
            StringBuffer stringBuffer = new StringBuffer(str);
            stringBuffer.append(str2);
            stringBuffer.append(" ");
            stringBuffer.append(str3);
            this.f9259d.write(this.f9258c.a(stringBuffer.toString().getBytes()));
            this.f9259d.write("\r\n");
        }
    }
}
