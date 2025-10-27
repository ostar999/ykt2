package com.ykb.ebook.extensions;

import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.ykb.ebook.util.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0002*\u00020\u0002\u001a#\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\"\u00020\n¢\u0006\u0002\u0010\u000b\u001a#\u0010\f\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\"\u00020\n¢\u0006\u0002\u0010\r\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0001¨\u0006\u0011"}, d2 = {"checkWrite", "", "Ljava/io/File;", "createFileIfNotExist", "createFileReplace", "createFolderIfNotExist", "createFolderReplace", "exists", "subDirFiles", "", "", "(Ljava/io/File;[Ljava/lang/String;)Z", "getFile", "(Ljava/io/File;[Ljava/lang/String;)Ljava/io/File;", "outputStream", "Ljava/io/FileOutputStream;", RequestParameters.SUBRESOURCE_APPEND, "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nFileExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FileExtensions.kt\ncom/ykb/ebook/extensions/FileExtensionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,70:1\n1#2:71\n*E\n"})
/* loaded from: classes7.dex */
public final class FileExtensionsKt {
    public static final boolean checkWrite(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        try {
            File fileCreateFileIfNotExist = FileUtils.INSTANCE.createFileIfNotExist(file, String.valueOf(System.currentTimeMillis()));
            FileOutputStream fileOutputStreamOutputStream$default = outputStream$default(fileCreateFileIfNotExist, false, 1, null);
            try {
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStreamOutputStream$default, null);
                fileCreateFileIfNotExist.delete();
                return true;
            } finally {
            }
        } catch (Exception unused) {
            return false;
        }
    }

    @NotNull
    public static final File createFileIfNotExist(@NotNull File file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                createFileIfNotExist(parentFile);
            }
            file.createNewFile();
        }
        return file;
    }

    @NotNull
    public static final File createFileReplace(@NotNull File file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        } else {
            String parent = file.getParent();
            if (parent != null) {
                new File(parent).mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }

    @NotNull
    public static final File createFolderIfNotExist(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @NotNull
    public static final File createFolderReplace(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (file.exists()) {
            FileUtils.INSTANCE.delete(file, true);
        }
        file.mkdirs();
        return file;
    }

    public static final boolean exists(@NotNull File file, @NotNull String... subDirFiles) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(subDirFiles, "subDirFiles");
        return getFile(file, (String[]) Arrays.copyOf(subDirFiles, subDirFiles.length)).exists();
    }

    @NotNull
    public static final File getFile(@NotNull File file, @NotNull String... subDirFiles) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(subDirFiles, "subDirFiles");
        return new File(FileUtils.INSTANCE.getPath(file, (String[]) Arrays.copyOf(subDirFiles, subDirFiles.length)));
    }

    @NotNull
    public static final FileOutputStream outputStream(@NotNull File file, boolean z2) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return new FileOutputStream(file, z2);
    }

    public static /* synthetic */ FileOutputStream outputStream$default(File file, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        return outputStream(file, z2);
    }
}
