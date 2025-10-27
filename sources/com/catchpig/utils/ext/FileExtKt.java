package com.catchpig.utils.ext;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0003¨\u0006\u0005"}, d2 = {"deleteFile", "", "file", "Ljava/io/File;", "deleteAll", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nFileExt.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FileExt.kt\ncom/catchpig/utils/ext/FileExtKt\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,19:1\n13579#2,2:20\n*S KotlinDebug\n*F\n+ 1 FileExt.kt\ncom/catchpig/utils/ext/FileExtKt\n*L\n13#1:20,2\n*E\n"})
/* loaded from: classes.dex */
public final class FileExtKt {
    public static final void deleteAll(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        deleteFile(file);
    }

    private static final void deleteFile(File file) {
        File[] fileArrListFiles;
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
            return;
        }
        for (File it : fileArrListFiles) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            deleteFile(it);
        }
    }
}
