package com.ykb.ebook.page;

import android.content.AppCtxKt;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.FileExtensionsKt;
import com.ykb.ebook.util.FileUtils;
import com.ykb.ebook.util.MD5Utils;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000RN\u0010\f\u001aB\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u000f0\u000f \u000e* \u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u000f0\u000f\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/ykb/ebook/page/BookHelp;", "", "()V", "cacheEpubFolderName", "", "cacheFolderName", "cacheImageFolderName", "cachePath", "getCachePath", "()Ljava/lang/String;", "downloadDir", "Ljava/io/File;", "downloadImages", "Ljava/util/concurrent/ConcurrentHashMap$KeySetView;", "kotlin.jvm.PlatformType", "", "getImage", "folderName", "src", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BookHelp {

    @NotNull
    public static final BookHelp INSTANCE = new BookHelp();

    @NotNull
    private static final String cacheEpubFolderName = "epub";

    @NotNull
    private static final String cacheFolderName = "book_cache";

    @NotNull
    private static final String cacheImageFolderName = "images";

    @NotNull
    private static final String cachePath;

    @NotNull
    private static final File downloadDir;
    private static final ConcurrentHashMap.KeySetView<String, Boolean> downloadImages;

    static {
        File externalFiles = ContextExtensionsKt.getExternalFiles(AppCtxKt.getAppCtx());
        downloadDir = externalFiles;
        downloadImages = ConcurrentHashMap.newKeySet();
        cachePath = FileUtils.INSTANCE.getPath(externalFiles, cacheFolderName);
    }

    private BookHelp() {
    }

    @NotNull
    public final String getCachePath() {
        return cachePath;
    }

    @NotNull
    public final File getImage(@NotNull String folderName, @NotNull String src) {
        Intrinsics.checkNotNullParameter(folderName, "folderName");
        Intrinsics.checkNotNullParameter(src, "src");
        return FileExtensionsKt.getFile(downloadDir, cacheFolderName, folderName, cacheImageFolderName, MD5Utils.INSTANCE.md5Encode16(src) + ".jpg");
    }
}
