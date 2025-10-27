package com.beizi.fusion.g;

import java.io.File;

/* loaded from: classes2.dex */
public class al {

    /* renamed from: a, reason: collision with root package name */
    private static String f5137a = "extra";

    public static void a(File file) {
        try {
            if (!file.isDirectory()) {
                if (file.exists()) {
                    file.delete();
                    return;
                }
                return;
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                for (File file2 : fileArrListFiles) {
                    a(file2);
                }
            }
            file.delete();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
