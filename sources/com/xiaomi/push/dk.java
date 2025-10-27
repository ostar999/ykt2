package com.xiaomi.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
class dk {

    /* renamed from: a, reason: collision with root package name */
    private static String f24727a = "/MiPushLog";

    /* renamed from: a, reason: collision with other field name */
    private int f310a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f313a;

    /* renamed from: b, reason: collision with other field name */
    private String f314b;

    /* renamed from: c, reason: collision with root package name */
    private String f24729c;

    /* renamed from: a, reason: collision with other field name */
    @SuppressLint({"SimpleDateFormat"})
    private final SimpleDateFormat f311a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /* renamed from: b, reason: collision with root package name */
    private int f24728b = 2097152;

    /* renamed from: a, reason: collision with other field name */
    private ArrayList<File> f312a = new ArrayList<>();

    private void a(BufferedReader bufferedReader, BufferedWriter bufferedWriter, Pattern pattern) throws IOException {
        char[] cArr = new char[4096];
        int i2 = bufferedReader.read(cArr);
        boolean z2 = false;
        while (i2 != -1 && !z2) {
            String str = new String(cArr, 0, i2);
            Matcher matcher = pattern.matcher(str);
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i3 >= i2 || !matcher.find(i3)) {
                    break;
                }
                int iStart = matcher.start();
                String strSubstring = str.substring(iStart, this.f314b.length() + iStart);
                if (this.f313a) {
                    if (strSubstring.compareTo(this.f24729c) > 0) {
                        z2 = true;
                        i2 = iStart;
                        break;
                    }
                } else if (strSubstring.compareTo(this.f314b) >= 0) {
                    this.f313a = true;
                    i4 = iStart;
                }
                int iIndexOf = str.indexOf(10, iStart);
                if (iIndexOf == -1) {
                    iIndexOf = this.f314b.length();
                }
                i3 = iStart + iIndexOf;
            }
            if (this.f313a) {
                int i5 = i2 - i4;
                this.f310a += i5;
                bufferedWriter.write(cArr, i4, i5);
                if (z2 || this.f310a > this.f24728b) {
                    return;
                }
            }
            i2 = bufferedReader.read(cArr);
        }
    }

    private void a(File file) throws Throwable {
        BufferedReader bufferedReader;
        String str;
        Pattern patternCompile = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        BufferedReader bufferedReader2 = null;
        try {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                try {
                    bufferedWriter.write("model :" + Build.MODEL + "; os :" + Build.VERSION.INCREMENTAL + "; uid :" + com.xiaomi.push.service.bi.m731a() + "; lng :" + Locale.getDefault().toString() + "; sdk :38; andver :" + Build.VERSION.SDK_INT + "\n");
                    this.f310a = 0;
                    Iterator<File> it = this.f312a.iterator();
                    while (it.hasNext()) {
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(it.next())));
                        try {
                            a(bufferedReader, bufferedWriter, patternCompile);
                            bufferedReader.close();
                            bufferedReader2 = bufferedReader;
                        } catch (FileNotFoundException e2) {
                            e = e2;
                            bufferedReader2 = bufferedWriter;
                            str = "LOG: filter error = " + e.getMessage();
                            com.xiaomi.channel.commonutils.logger.b.c(str);
                            y.a(bufferedReader2);
                            y.a(bufferedReader);
                            return;
                        } catch (IOException e3) {
                            e = e3;
                            bufferedReader2 = bufferedWriter;
                            str = "LOG: filter error = " + e.getMessage();
                            com.xiaomi.channel.commonutils.logger.b.c(str);
                            y.a(bufferedReader2);
                            y.a(bufferedReader);
                            return;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader2 = bufferedWriter;
                            y.a(bufferedReader2);
                            y.a(bufferedReader);
                            throw th;
                        }
                    }
                    bufferedWriter.write(dc.a().c());
                    y.a(bufferedWriter);
                    y.a(bufferedReader2);
                } catch (FileNotFoundException e4) {
                    e = e4;
                    bufferedReader = bufferedReader2;
                } catch (IOException e5) {
                    e = e5;
                    bufferedReader = bufferedReader2;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                }
            } catch (FileNotFoundException e6) {
                e = e6;
                bufferedReader = null;
            } catch (IOException e7) {
                e = e7;
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public dk m324a(File file) {
        if (file.exists()) {
            this.f312a.add(file);
        }
        return this;
    }

    public dk a(Date date, Date date2) {
        String str;
        if (date.after(date2)) {
            this.f314b = this.f311a.format(date2);
            str = this.f311a.format(date);
        } else {
            this.f314b = this.f311a.format(date);
            str = this.f311a.format(date2);
        }
        this.f24729c = str;
        return this;
    }

    public File a(Context context, Date date, Date date2, File file) throws Throwable {
        File filesDir;
        if ("com.xiaomi.xmsf".equalsIgnoreCase(context.getPackageName())) {
            filesDir = context.getFilesDir();
            m324a(new File(filesDir, "xmsf.log.1"));
            m324a(new File(filesDir, "xmsf.log"));
        } else {
            File file2 = new File(context.getExternalFilesDir(null) + f24727a);
            m324a(new File(file2, "log0.txt"));
            m324a(new File(file2, "log1.txt"));
            filesDir = file2;
        }
        if (!filesDir.isDirectory()) {
            return null;
        }
        File file3 = new File(file, date.getTime() + "-" + date2.getTime() + ".zip");
        if (file3.exists()) {
            return null;
        }
        a(date, date2);
        long jCurrentTimeMillis = System.currentTimeMillis();
        File file4 = new File(file, "log.txt");
        a(file4);
        com.xiaomi.channel.commonutils.logger.b.c("LOG: filter cost = " + (System.currentTimeMillis() - jCurrentTimeMillis));
        if (file4.exists()) {
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            y.a(file3, file4);
            com.xiaomi.channel.commonutils.logger.b.c("LOG: zip cost = " + (System.currentTimeMillis() - jCurrentTimeMillis2));
            file4.delete();
            if (file3.exists()) {
                return file3;
            }
        }
        return null;
    }

    public void a(int i2) {
        if (i2 != 0) {
            this.f24728b = i2;
        }
    }
}
