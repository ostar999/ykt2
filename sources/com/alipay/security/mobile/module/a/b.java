package com.alipay.security.mobile.module.a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public final class b {
    public static String a(String str, String str2) throws Throwable {
        File file;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            try {
                file = new File(str, str2);
            } catch (IOException unused) {
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable unused2) {
        }
        if (!file.exists()) {
            return null;
        }
        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        while (true) {
            try {
                String line = bufferedReader2.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            } catch (IOException unused3) {
                bufferedReader = bufferedReader2;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                return sb.toString();
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable unused4) {
                    }
                }
                throw th;
            }
        }
        bufferedReader2.close();
        return sb.toString();
    }
}
