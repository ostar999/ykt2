package cn.hutool.core.compiler;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ZipUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.tools.JavaFileObject;

/* loaded from: classes.dex */
public class JavaFileObjectUtil {
    private static List<JavaFileObject> getJavaFileObjectByZipOrJarFile(File file) {
        final ArrayList arrayList = new ArrayList();
        final ZipFile zipFile = ZipUtil.toZipFile(file, null);
        ZipUtil.read(zipFile, (Consumer<ZipEntry>) new Consumer() { // from class: cn.hutool.core.compiler.c
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                JavaFileObjectUtil.lambda$getJavaFileObjectByZipOrJarFile$0(arrayList, zipFile, (ZipEntry) obj);
            }
        });
        return arrayList;
    }

    public static List<JavaFileObject> getJavaFileObjects(File file) {
        ArrayList arrayList = new ArrayList();
        String name = file.getName();
        if (isJavaFile(name)) {
            arrayList.add(new JavaSourceFileObject(file.toURI()));
        } else if (isJarOrZipFile(name)) {
            arrayList.addAll(getJavaFileObjectByZipOrJarFile(file));
        }
        return arrayList;
    }

    public static boolean isJarOrZipFile(String str) {
        return FileNameUtil.isType(str, "jar", "zip");
    }

    public static boolean isJavaFile(String str) {
        return FileNameUtil.isType(str, "java");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getJavaFileObjectByZipOrJarFile$0(List list, ZipFile zipFile, ZipEntry zipEntry) {
        String name = zipEntry.getName();
        if (isJavaFile(name)) {
            list.add(new JavaSourceFileObject(name, ZipUtil.getStream(zipFile, zipEntry)));
        }
    }
}
