package com.xiaomi.push;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes6.dex */
public class el extends ei {
    public el(Context context, int i2) {
        super(context, i2);
    }

    private double a(double d3) {
        int i2 = 1;
        while (true) {
            double d4 = i2;
            if (d4 >= d3) {
                return d4;
            }
            i2 <<= 1;
        }
    }

    private long a(File file) {
        StatFs statFs = new StatFs(file.getPath());
        return statFs.getBlockSize() * statFs.getBlockCount();
    }

    private String b() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        String[] strArrSplit;
        String str = "0";
        if (new File("/proc/meminfo").exists()) {
            BufferedReader bufferedReader2 = null;
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
                } catch (Exception unused) {
                } catch (Throwable th2) {
                    bufferedReader = null;
                    th = th2;
                }
                try {
                    String line = bufferedReader.readLine();
                    if (!TextUtils.isEmpty(line) && (strArrSplit = line.split("\\s+")) != null && strArrSplit.length >= 2) {
                        double dDoubleValue = (Double.valueOf(strArrSplit[1]).doubleValue() / 1024.0d) / 1024.0d;
                        if (dDoubleValue > 0.5d) {
                            dDoubleValue = Math.ceil(dDoubleValue);
                        }
                        str = dDoubleValue + "";
                    }
                    bufferedReader.close();
                } catch (Exception unused2) {
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    return str + "GB";
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused4) {
            }
        }
        return str + "GB";
    }

    private String c() {
        return a(((a(Environment.getDataDirectory()) / 1024.0d) / 1024.0d) / 1024.0d) + "GB";
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 23;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.Storage;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        return "ram:" + b() + ",rom:" + c();
    }
}
