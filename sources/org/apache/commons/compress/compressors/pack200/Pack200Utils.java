package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;

/* loaded from: classes9.dex */
public class Pack200Utils {
    private Pack200Utils() {
    }

    public static void normalize(File file) throws IOException {
        normalize(file, file, null);
    }

    public static void normalize(File file, Map<String, String> map) throws IOException {
        normalize(file, file, map);
    }

    public static void normalize(File file, File file2) throws IOException {
        normalize(file, file2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.OutputStream, java.util.jar.JarOutputStream] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.util.jar.Pack200$Packer] */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.util.jar.Pack200$Unpacker] */
    public static void normalize(File file, File file2, Map<String, String> map) throws IOException {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("pack.segment.limit", "-1");
        File fileCreateTempFile = File.createTempFile("commons-compress", "pack200normalize");
        fileCreateTempFile.deleteOnExit();
        try {
            ?? fileOutputStream = new FileOutputStream(fileCreateTempFile);
            JarFile jarFile = null;
            try {
                ?? NewPacker = Pack200.newPacker();
                NewPacker.properties().putAll(map);
                JarFile jarFile2 = new JarFile(file);
                try {
                    NewPacker.pack(jarFile2, fileOutputStream);
                    fileOutputStream.close();
                    try {
                        ?? NewUnpacker = Pack200.newUnpacker();
                        fileOutputStream = new JarOutputStream(new FileOutputStream(file2));
                        NewUnpacker.unpack(fileCreateTempFile, fileOutputStream);
                        fileOutputStream.close();
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = 0;
                        if (jarFile != null) {
                            jarFile.close();
                        }
                        if (fileOutputStream != 0) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    jarFile = jarFile2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } finally {
            fileCreateTempFile.delete();
        }
    }
}
