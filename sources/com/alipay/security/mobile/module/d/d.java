package com.alipay.security.mobile.module.d;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    private static String f3431a = "";

    /* renamed from: b, reason: collision with root package name */
    private static String f3432b = "";

    /* renamed from: c, reason: collision with root package name */
    private static String f3433c = "";

    public static synchronized void a(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        a(arrayList);
    }

    public static synchronized void a(String str, String str2, String str3) {
        f3431a = str;
        f3432b = str2;
        f3433c = str3;
    }

    public static synchronized void a(Throwable th) {
        String string;
        ArrayList arrayList = new ArrayList();
        if (th != null) {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            string = stringWriter.toString();
        } else {
            string = "";
        }
        arrayList.add(string);
        a(arrayList);
    }

    private static synchronized void a(List<String> list) {
        if (!com.alipay.security.mobile.module.a.a.a(f3432b) && !com.alipay.security.mobile.module.a.a.a(f3433c)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(f3433c);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                stringBuffer.append(", " + it.next());
            }
            stringBuffer.append("\n");
            try {
                File file = new File(f3431a);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(f3431a, f3432b);
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileWriter fileWriter = ((long) stringBuffer.length()) + file2.length() <= 51200 ? new FileWriter(file2, true) : new FileWriter(file2);
                fileWriter.write(stringBuffer.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception unused) {
            }
        }
    }
}
