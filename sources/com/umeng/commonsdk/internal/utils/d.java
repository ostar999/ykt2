package com.umeng.commonsdk.internal.utils;

import android.os.Build;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class d {

    public enum a {
        check_su_binary(new String[]{"/system/xbin/which", "su"});


        /* renamed from: b, reason: collision with root package name */
        String[] f23237b;

        a(String[] strArr) {
            this.f23237b = strArr;
        }
    }

    public ArrayList a(a aVar) throws IOException {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT > 28) {
            return arrayList;
        }
        try {
            Process processExec = Runtime.getRuntime().exec(aVar.f23237b);
            new BufferedWriter(new OutputStreamWriter(processExec.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    arrayList.add(line);
                } catch (Exception unused) {
                }
            }
            return arrayList;
        } catch (Exception unused2) {
            return null;
        }
    }
}
