package com.ykb.ebook.util;

import android.content.AppCtxKt;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import cn.hutool.core.text.StrPool;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.psychiatrygarden.utils.MimeTypes;
import com.tencent.smtt.sdk.TbsReaderView;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ConvertUtils;
import com.ykb.ebook.extensions.StringExtensionsKt;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.ArraysKt__ArraysKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt___CollectionsJvmKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0010\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u0005]^_`aB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fJ\u0010\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0016\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fJ\u0016\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aJ\u0016\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fJ\u0016\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u000fJ'\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001a2\u0012\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\"\"\u00020\u000f¢\u0006\u0002\u0010#J\u000e\u0010\u001f\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u000fJ\u000e\u0010%\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u000fJ'\u0010&\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001a2\u0012\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\"\"\u00020\u000f¢\u0006\u0002\u0010#J\u000e\u0010&\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u000fJ\u001a\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u001a2\b\b\u0002\u0010*\u001a\u00020\rH\u0007J\u001a\u0010(\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010*\u001a\u00020\rH\u0007J\u0010\u0010+\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u001aH\u0002J\u000e\u0010,\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010-\u001a\u00020\u000fJ\u0016\u0010.\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020\u001a2\u0006\u0010/\u001a\u00020\u000fJ\u001a\u0010.\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000fH\u0007J\u000e\u00100\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u000fJ\u0010\u00102\u001a\u0002032\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u000e\u00104\u001a\u0002052\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u00106\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u000fJ\u0010\u00107\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u000e\u00108\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fJ'\u00109\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u001a2\u0012\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\"\"\u00020\u000f¢\u0006\u0002\u0010:J'\u00109\u001a\u00020\u000f2\u0006\u0010;\u001a\u00020\u000f2\u0012\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\"\"\u00020\u000f¢\u0006\u0002\u0010<J\u0006\u0010=\u001a\u00020\u000fJ\u000e\u0010>\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fJ7\u0010?\u001a\b\u0012\u0004\u0012\u00020\u001a0\"2\u0006\u0010@\u001a\u00020\u000f2\u0010\b\u0002\u0010A\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\"2\b\b\u0002\u0010B\u001a\u00020\u0004H\u0007¢\u0006\u0002\u0010CJ/\u0010D\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\"2\u0006\u0010@\u001a\u00020\u000f2\u0010\b\u0002\u0010E\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\"H\u0007¢\u0006\u0002\u0010FJ1\u0010G\u001a\b\u0012\u0004\u0012\u00020\u001a0\"2\u0006\u0010@\u001a\u00020\u000f2\n\b\u0002\u0010H\u001a\u0004\u0018\u00010I2\b\b\u0002\u0010B\u001a\u00020\u0004H\u0007¢\u0006\u0002\u0010JJ+\u0010G\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\"2\u0006\u0010@\u001a\u00020\u000f2\u000e\u0010E\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\"¢\u0006\u0002\u0010FJ%\u0010G\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\"2\u0006\u0010@\u001a\u00020\u000f2\b\u0010K\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010LJ\u000e\u0010M\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u001aJ\u000e\u0010M\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010N\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aJ\u0016\u0010N\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fJ\u0010\u0010O\u001a\u0004\u0018\u00010P2\u0006\u0010Q\u001a\u00020\u000fJ\u001a\u0010R\u001a\u00020\u000f2\u0006\u0010Q\u001a\u00020\u000f2\b\b\u0002\u0010S\u001a\u00020\u000fH\u0007J\u0016\u0010T\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aJ\u0016\u0010T\u001a\u00020\r2\u0006\u0010U\u001a\u00020\u000f2\u0006\u0010V\u001a\u00020\u000fJ\u000e\u0010W\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010X\u001a\u00020\r2\u0006\u0010Q\u001a\u00020\u000f2\u0006\u0010Y\u001a\u00020PJ\u0016\u0010Z\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u001a2\u0006\u0010Y\u001a\u00020[J\u0016\u0010Z\u001a\u00020\r2\u0006\u0010Q\u001a\u00020\u000f2\u0006\u0010Y\u001a\u00020[J\"\u0010\\\u001a\u00020\r2\u0006\u0010Q\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010S\u001a\u00020\u000fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lcom/ykb/ebook/util/FileUtils;", "", "()V", "BY_EXTENSION_ASC", "", "BY_EXTENSION_DESC", "BY_NAME_ASC", "BY_NAME_DESC", "BY_SIZE_ASC", "BY_SIZE_DESC", "BY_TIME_ASC", "BY_TIME_DESC", "appendText", "", "path", "", "content", "closeSilently", "", am.aF, "Ljava/io/Closeable;", "compareLastModified", "path1", "path2", "copy", "src", "Ljava/io/File;", ArchiveStreamFactory.TAR, "copyContent", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "createFileIfNotExist", "root", "subDirFiles", "", "(Ljava/io/File;[Ljava/lang/String;)Ljava/io/File;", TbsReaderView.KEY_FILE_PATH, "createFileWithReplace", "createFolderIfNotExist", "subDirs", RequestParameters.SUBRESOURCE_DELETE, "file", "deleteRootDir", "deleteResolveEBUSY", "exist", "getCachePath", "getDateTime", "format", "getExtension", "pathOrUrl", "getImageSize", "", "getLength", "", "getMimeType", "getName", "getNameExcludeExtension", "getPath", "(Ljava/io/File;[Ljava/lang/String;)Ljava/lang/String;", "rootPath", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getSdCardPath", "getSize", "listDirs", "startDirPath", "excludeDirs", "sortType", "(Ljava/lang/String;[Ljava/lang/String;I)[Ljava/io/File;", "listDirsAndFiles", "allowExtensions", "(Ljava/lang/String;[Ljava/lang/String;)[Ljava/io/File;", "listFiles", "filterPattern", "Ljava/util/regex/Pattern;", "(Ljava/lang/String;Ljava/util/regex/Pattern;I)[Ljava/io/File;", "allowExtension", "(Ljava/lang/String;Ljava/lang/String;)[Ljava/io/File;", "makeDirs", "move", "readBytes", "", "filepath", "readText", "charset", "rename", "oldPath", "newPath", "separator", "writeBytes", "data", "writeInputStream", "Ljava/io/InputStream;", "writeText", "SortByExtension", "SortByName", "SortBySize", "SortByTime", "SortType", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nFileUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FileUtils.kt\ncom/ykb/ebook/util/FileUtils\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 Strings.kt\nkotlin/text/StringsKt__StringsKt\n*L\n1#1,787:1\n13579#2,2:788\n13579#2,2:790\n13579#2,2:796\n37#3,2:792\n37#3,2:794\n107#4:798\n79#4,22:799\n*S KotlinDebug\n*F\n+ 1 FileUtils.kt\ncom/ykb/ebook/util/FileUtils\n*L\n77#1:788,2\n90#1:790,2\n382#1:796,2\n200#1:792,2\n273#1:794,2\n429#1:798\n429#1:799,22\n*E\n"})
/* loaded from: classes7.dex */
public final class FileUtils {
    public static final int BY_EXTENSION_ASC = 6;
    public static final int BY_EXTENSION_DESC = 7;
    public static final int BY_NAME_ASC = 0;
    public static final int BY_NAME_DESC = 1;
    public static final int BY_SIZE_ASC = 4;
    public static final int BY_SIZE_DESC = 5;
    public static final int BY_TIME_ASC = 2;
    public static final int BY_TIME_DESC = 3;

    @NotNull
    public static final FileUtils INSTANCE = new FileUtils();

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\b"}, d2 = {"Lcom/ykb/ebook/util/FileUtils$SortByExtension;", "Ljava/util/Comparator;", "Ljava/io/File;", "()V", "compare", "", "f1", "f2", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class SortByExtension implements Comparator<File> {
        @Override // java.util.Comparator
        public int compare(@Nullable File f12, @Nullable File f2) {
            if (f12 == null || f2 == null) {
                if (f12 == null) {
                    return -1;
                }
            } else {
                if (f12.isDirectory() && f2.isFile()) {
                    return -1;
                }
                if (!f12.isFile() || !f2.isDirectory()) {
                    String name = f12.getName();
                    Intrinsics.checkNotNullExpressionValue(name, "f1.name");
                    String name2 = f2.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "f2.name");
                    return StringsKt__StringsJVMKt.compareTo(name, name2, true);
                }
            }
            return 1;
        }
    }

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0007\b\u0016¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u00022\b\u0010\n\u001a\u0004\u0018\u00010\u0002H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/util/FileUtils$SortByName;", "Ljava/util/Comparator;", "Ljava/io/File;", "caseSensitive", "", "(Z)V", "()V", "compare", "", "f1", "f2", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class SortByName implements Comparator<File> {
        private boolean caseSensitive;

        public SortByName(boolean z2) {
            this.caseSensitive = z2;
        }

        @Override // java.util.Comparator
        public int compare(@Nullable File f12, @Nullable File f2) {
            if (f12 == null || f2 == null) {
                return f12 == null ? -1 : 1;
            }
            if (f12.isDirectory() && f2.isFile()) {
                return -1;
            }
            if (f12.isFile() && f2.isDirectory()) {
                return 1;
            }
            String s12 = f12.getName();
            String s2 = f2.getName();
            if (this.caseSensitive) {
                Intrinsics.checkNotNullExpressionValue(s12, "s1");
                Intrinsics.checkNotNullExpressionValue(s2, "s2");
                return StringExtensionsKt.cnCompare(s12, s2);
            }
            Intrinsics.checkNotNullExpressionValue(s12, "s1");
            Intrinsics.checkNotNullExpressionValue(s2, "s2");
            return StringsKt__StringsJVMKt.compareTo(s12, s2, true);
        }

        public SortByName() {
            this.caseSensitive = false;
        }
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\b"}, d2 = {"Lcom/ykb/ebook/util/FileUtils$SortBySize;", "Ljava/util/Comparator;", "Ljava/io/File;", "()V", "compare", "", "f1", "f2", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class SortBySize implements Comparator<File> {
        @Override // java.util.Comparator
        public int compare(@Nullable File f12, @Nullable File f2) {
            if (f12 == null || f2 == null) {
                if (f12 == null) {
                    return -1;
                }
            } else {
                if (f12.isDirectory() && f2.isFile()) {
                    return -1;
                }
                if ((!f12.isFile() || !f2.isDirectory()) && f12.length() < f2.length()) {
                    return -1;
                }
            }
            return 1;
        }
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\b"}, d2 = {"Lcom/ykb/ebook/util/FileUtils$SortByTime;", "Ljava/util/Comparator;", "Ljava/io/File;", "()V", "compare", "", "f1", "f2", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class SortByTime implements Comparator<File> {
        @Override // java.util.Comparator
        public int compare(@Nullable File f12, @Nullable File f2) {
            if (f12 == null || f2 == null) {
                if (f12 == null) {
                    return -1;
                }
            } else {
                if (f12.isDirectory() && f2.isFile()) {
                    return -1;
                }
                if ((!f12.isFile() || !f2.isDirectory()) && f12.lastModified() > f2.lastModified()) {
                    return -1;
                }
            }
            return 1;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Lcom/ykb/ebook/util/FileUtils$SortType;", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface SortType {
    }

    private FileUtils() {
    }

    public static /* synthetic */ boolean delete$default(FileUtils fileUtils, File file, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return fileUtils.delete(file, z2);
    }

    private final boolean deleteResolveEBUSY(File file) {
        File file2 = new File(file.getAbsolutePath() + System.currentTimeMillis());
        file.renameTo(file2);
        return file2.delete();
    }

    public static /* synthetic */ String getDateTime$default(FileUtils fileUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = "yyyy年MM月dd日HH:mm";
        }
        return fileUtils.getDateTime(str, str2);
    }

    public static /* synthetic */ File[] listDirs$default(FileUtils fileUtils, String str, String[] strArr, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            strArr = null;
        }
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return fileUtils.listDirs(str, strArr, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean listDirs$lambda$4(File file) {
        if (file == null) {
            return false;
        }
        return file.isDirectory();
    }

    public static /* synthetic */ File[] listDirsAndFiles$default(FileUtils fileUtils, String str, String[] strArr, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            strArr = null;
        }
        return fileUtils.listDirsAndFiles(str, strArr);
    }

    public static /* synthetic */ File[] listFiles$default(FileUtils fileUtils, String str, Pattern pattern, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            pattern = null;
        }
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return fileUtils.listFiles(str, pattern, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean listFiles$lambda$5(Pattern pattern, File file) {
        Matcher matcher;
        if (file == null || file.isDirectory()) {
            return false;
        }
        if (pattern == null || (matcher = pattern.matcher(file.getName())) == null) {
            return true;
        }
        return matcher.find();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean listFiles$lambda$6(String[] strArr, File file, String name) {
        String strContentDeepToString;
        FileUtils fileUtils = INSTANCE;
        Intrinsics.checkNotNullExpressionValue(name, "name");
        return (strArr != null && (strContentDeepToString = ArraysKt__ArraysKt.contentDeepToString(strArr)) != null && StringsKt__StringsKt.contains$default((CharSequence) strContentDeepToString, (CharSequence) fileUtils.getExtension(name), false, 2, (Object) null)) || strArr == null;
    }

    public static /* synthetic */ String readText$default(FileUtils fileUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = "utf-8";
        }
        return fileUtils.readText(str, str2);
    }

    public static /* synthetic */ boolean writeText$default(FileUtils fileUtils, String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str3 = "utf-8";
        }
        return fileUtils.writeText(str, str2, str3);
    }

    public final boolean appendText(@NotNull String path, @NotNull String content) throws Throwable {
        FileWriter fileWriter;
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(content, "content");
        File file = new File(path);
        Closeable closeable = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, true);
        } catch (IOException unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileWriter.write(content);
            closeSilently(fileWriter);
            return true;
        } catch (IOException unused2) {
            closeable = fileWriter;
            closeSilently(closeable);
            return false;
        } catch (Throwable th2) {
            th = th2;
            closeable = fileWriter;
            closeSilently(closeable);
            throw th;
        }
    }

    public final void closeSilently(@Nullable Closeable c3) throws IOException {
        if (c3 == null) {
            return;
        }
        try {
            c3.close();
        } catch (IOException unused) {
        }
    }

    public final int compareLastModified(@NotNull String path1, @NotNull String path2) {
        Intrinsics.checkNotNullParameter(path1, "path1");
        Intrinsics.checkNotNullParameter(path2, "path2");
        long jLastModified = new File(path1).lastModified();
        long jLastModified2 = new File(path2).lastModified();
        if (jLastModified > jLastModified2) {
            return 1;
        }
        return jLastModified < jLastModified2 ? -1 : 0;
    }

    public final boolean copy(@NotNull String src, @NotNull String tar) {
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(tar, "tar");
        File file = new File(src);
        return file.exists() && copy(file, new File(tar));
    }

    public final void copyContent(@NotNull Context context, @NotNull String content) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(content, "content");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("Simple text", content));
        ToastUtilsKt.toastOnUi$default(context, "复制成功", 0, 2, (Object) null);
    }

    @NotNull
    public final File createFileIfNotExist(@NotNull File root, @NotNull String... subDirFiles) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(subDirFiles, "subDirFiles");
        return createFileIfNotExist(getPath(root, (String[]) Arrays.copyOf(subDirFiles, subDirFiles.length)));
    }

    @NotNull
    public final File createFileWithReplace(@NotNull String filePath) throws IOException {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        } else {
            String parent = file.getParent();
            if (parent != null) {
                INSTANCE.createFolderIfNotExist(parent);
            }
            file.createNewFile();
        }
        return file;
    }

    @NotNull
    public final File createFolderIfNotExist(@NotNull File root, @NotNull String... subDirs) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(subDirs, "subDirs");
        return createFolderIfNotExist(getPath(root, (String[]) Arrays.copyOf(subDirs, subDirs.length)));
    }

    @JvmOverloads
    public final boolean delete(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return delete$default(this, file, false, 2, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0047  */
    @kotlin.jvm.JvmOverloads
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean delete(@org.jetbrains.annotations.NotNull java.io.File r6, boolean r7) {
        /*
            r5 = this;
            java.lang.String r0 = "file"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            boolean r0 = r6.isFile()
            if (r0 == 0) goto L10
            boolean r6 = r5.deleteResolveEBUSY(r6)
            goto L48
        L10:
            java.io.File[] r0 = r6.listFiles()
            r1 = 0
            if (r0 != 0) goto L18
            return r1
        L18:
            int r2 = r0.length
            r3 = 1
            if (r2 != 0) goto L1e
            r2 = r3
            goto L1f
        L1e:
            r2 = r1
        L1f:
            if (r2 == 0) goto L2a
            if (r7 == 0) goto L40
            boolean r0 = r5.deleteResolveEBUSY(r6)
            if (r0 == 0) goto L40
            goto L3f
        L2a:
            int r2 = r0.length
            r3 = r1
        L2c:
            if (r1 >= r2) goto L3f
            r3 = r0[r1]
            java.lang.String r4 = "f"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r5.delete(r3, r7)
            boolean r3 = r5.deleteResolveEBUSY(r3)
            int r1 = r1 + 1
            goto L2c
        L3f:
            r1 = r3
        L40:
            if (r7 == 0) goto L47
            boolean r6 = r5.deleteResolveEBUSY(r6)
            goto L48
        L47:
            r6 = r1
        L48:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.util.FileUtils.delete(java.io.File, boolean):boolean");
    }

    @JvmOverloads
    public final boolean delete(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return delete$default(this, path, false, 2, (Object) null);
    }

    public final boolean exist(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new File(path).exists();
    }

    @NotNull
    public final String getCachePath() {
        String absolutePath = ContextExtensionsKt.getExternalCache(AppCtxKt.getAppCtx()).getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "appCtx.externalCache.absolutePath");
        return absolutePath;
    }

    @JvmOverloads
    @NotNull
    public final String getDateTime(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return getDateTime$default(this, path, null, 2, null);
    }

    @JvmOverloads
    @NotNull
    public final String getDateTime(@NotNull String path, @NotNull String format) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(format, "format");
        return getDateTime(new File(path), format);
    }

    @NotNull
    public final String getExtension(@NotNull String pathOrUrl) {
        Intrinsics.checkNotNullParameter(pathOrUrl, "pathOrUrl");
        int iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default((CharSequence) pathOrUrl, '.', 0, false, 6, (Object) null);
        if (iLastIndexOf$default < 0) {
            return SocializeProtocolConstants.PROTOCOL_KEY_EXTEND;
        }
        String strSubstring = pathOrUrl.substring(iLastIndexOf$default + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    public final float getImageSize(@Nullable String path) {
        try {
            return (new File(path).length() / 1024.0f) / 1024.0f;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0.0f;
        }
    }

    public final long getLength(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            return file.length();
        }
        return 0L;
    }

    @NotNull
    public final String getMimeType(@NotNull String pathOrUrl) {
        Intrinsics.checkNotNullParameter(pathOrUrl, "pathOrUrl");
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getExtension(pathOrUrl));
        return mimeTypeFromExtension == null ? MimeTypes.ANY_TYPE : mimeTypeFromExtension;
    }

    @NotNull
    public final String getName(@Nullable String path) {
        if (path == null) {
            return "";
        }
        String separator = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        int iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default((CharSequence) path, separator, 0, false, 6, (Object) null);
        if (iLastIndexOf$default < 0) {
            return path;
        }
        String strSubstring = path.substring(iLastIndexOf$default + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    @NotNull
    public final String getNameExcludeExtension(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        try {
            String fileName = new File(path).getName();
            Intrinsics.checkNotNullExpressionValue(fileName, "fileName");
            int iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default((CharSequence) fileName, StrPool.DOT, 0, false, 6, (Object) null);
            if (iLastIndexOf$default != -1) {
                Intrinsics.checkNotNullExpressionValue(fileName, "fileName");
                fileName = fileName.substring(0, iLastIndexOf$default);
                Intrinsics.checkNotNullExpressionValue(fileName, "this as java.lang.String…ing(startIndex, endIndex)");
            }
            Intrinsics.checkNotNullExpressionValue(fileName, "{\n            var fileNa…       fileName\n        }");
            return fileName;
        } catch (Exception unused) {
            return "";
        }
    }

    @NotNull
    public final String getPath(@NotNull String rootPath, @NotNull String... subDirFiles) {
        Intrinsics.checkNotNullParameter(rootPath, "rootPath");
        Intrinsics.checkNotNullParameter(subDirFiles, "subDirFiles");
        StringBuilder sb = new StringBuilder(rootPath);
        for (String str : subDirFiles) {
            if (str.length() > 0) {
                String separator = File.separator;
                Intrinsics.checkNotNullExpressionValue(separator, "separator");
                if (!StringsKt__StringsKt.endsWith$default((CharSequence) sb, (CharSequence) separator, false, 2, (Object) null)) {
                    sb.append(separator);
                }
                sb.append(str);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "path.toString()");
        return string;
    }

    @NotNull
    public final String getSdCardPath() throws IOException {
        String sdCardDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            sdCardDirectory = new File(sdCardDirectory).getCanonicalPath();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        Intrinsics.checkNotNullExpressionValue(sdCardDirectory, "sdCardDirectory");
        return sdCardDirectory;
    }

    @NotNull
    public final String getSize(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return ConvertUtils.INSTANCE.formatFileSize(getLength(path));
    }

    @JvmOverloads
    @NotNull
    public final File[] listDirs(@NotNull String startDirPath) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        return listDirs$default(this, startDirPath, null, 0, 6, null);
    }

    @JvmOverloads
    @NotNull
    public final File[] listDirs(@NotNull String startDirPath, @Nullable String[] strArr) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        return listDirs$default(this, startDirPath, strArr, 0, 4, null);
    }

    @JvmOverloads
    @NotNull
    public final File[] listDirs(@NotNull String startDirPath, @Nullable String[] excludeDirs, int sortType) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        ArrayList arrayList = new ArrayList();
        File file = new File(startDirPath);
        if (!file.isDirectory()) {
            return new File[0];
        }
        File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.ykb.ebook.util.d
            @Override // java.io.FileFilter
            public final boolean accept(File file2) {
                return FileUtils.listDirs$lambda$4(file2);
            }
        });
        if (fileArrListFiles == null) {
            return new File[0];
        }
        if (excludeDirs == null) {
            excludeDirs = new String[0];
        }
        for (File file2 : fileArrListFiles) {
            File absoluteFile = file2.getAbsoluteFile();
            String strContentDeepToString = ArraysKt__ArraysKt.contentDeepToString(excludeDirs);
            String name = absoluteFile.getName();
            Intrinsics.checkNotNullExpressionValue(name, "file.name");
            if (!StringsKt__StringsKt.contains$default((CharSequence) strContentDeepToString, (CharSequence) name, false, 2, (Object) null)) {
                arrayList.add(absoluteFile);
            }
        }
        switch (sortType) {
            case 0:
                Collections.sort(arrayList, new SortByName());
                break;
            case 1:
                Collections.sort(arrayList, new SortByName());
                CollectionsKt___CollectionsJvmKt.reverse(arrayList);
                break;
            case 2:
                Collections.sort(arrayList, new SortByTime());
                break;
            case 3:
                Collections.sort(arrayList, new SortByTime());
                CollectionsKt___CollectionsJvmKt.reverse(arrayList);
                break;
            case 4:
                Collections.sort(arrayList, new SortBySize());
                break;
            case 5:
                Collections.sort(arrayList, new SortBySize());
                CollectionsKt___CollectionsJvmKt.reverse(arrayList);
                break;
            case 6:
                Collections.sort(arrayList, new SortByExtension());
                break;
            case 7:
                Collections.sort(arrayList, new SortByExtension());
                CollectionsKt___CollectionsJvmKt.reverse(arrayList);
                break;
        }
        return (File[]) arrayList.toArray(new File[0]);
    }

    @JvmOverloads
    @Nullable
    public final File[] listDirsAndFiles(@NotNull String startDirPath) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        return listDirsAndFiles$default(this, startDirPath, null, 2, null);
    }

    @JvmOverloads
    @Nullable
    public final File[] listDirsAndFiles(@NotNull String startDirPath, @Nullable String[] allowExtensions) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        File[] fileArrListFiles$default = allowExtensions == null ? listFiles$default(this, startDirPath, null, 0, 6, null) : listFiles(startDirPath, allowExtensions);
        File[] fileArrListDirs$default = listDirs$default(this, startDirPath, null, 0, 6, null);
        if (fileArrListFiles$default == null) {
            return null;
        }
        return (File[]) ArraysKt___ArraysJvmKt.plus((Object[]) fileArrListDirs$default, (Object[]) fileArrListFiles$default);
    }

    @JvmOverloads
    @NotNull
    public final File[] listFiles(@NotNull String startDirPath) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        return listFiles$default(this, startDirPath, null, 0, 6, null);
    }

    @JvmOverloads
    @NotNull
    public final File[] listFiles(@NotNull String startDirPath, @Nullable Pattern pattern) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        return listFiles$default(this, startDirPath, pattern, 0, 4, null);
    }

    @JvmOverloads
    @NotNull
    public final File[] listFiles(@NotNull String startDirPath, @Nullable final Pattern filterPattern, int sortType) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        ArrayList arrayList = new ArrayList();
        File file = new File(startDirPath);
        if (!file.isDirectory()) {
            return new File[0];
        }
        File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.ykb.ebook.util.c
            @Override // java.io.FileFilter
            public final boolean accept(File file2) {
                return FileUtils.listFiles$lambda$5(filterPattern, file2);
            }
        });
        if (fileArrListFiles == null) {
            return new File[0];
        }
        for (File file2 : fileArrListFiles) {
            arrayList.add(file2.getAbsoluteFile());
        }
        switch (sortType) {
            case 0:
                Collections.sort(arrayList, new SortByName());
                break;
            case 1:
                Collections.sort(arrayList, new SortByName());
                CollectionsKt___CollectionsJvmKt.reverse(arrayList);
                break;
            case 2:
                Collections.sort(arrayList, new SortByTime());
                break;
            case 3:
                Collections.sort(arrayList, new SortByTime());
                CollectionsKt___CollectionsJvmKt.reverse(arrayList);
                break;
            case 4:
                Collections.sort(arrayList, new SortBySize());
                break;
            case 5:
                Collections.sort(arrayList, new SortBySize());
                CollectionsKt___CollectionsJvmKt.reverse(arrayList);
                break;
            case 6:
                Collections.sort(arrayList, new SortByExtension());
                break;
            case 7:
                Collections.sort(arrayList, new SortByExtension());
                CollectionsKt___CollectionsJvmKt.reverse(arrayList);
                break;
        }
        return (File[]) arrayList.toArray(new File[0]);
    }

    public final boolean makeDirs(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return makeDirs(new File(path));
    }

    public final boolean move(@NotNull String src, @NotNull String tar) {
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(tar, "tar");
        return move(new File(src), new File(tar));
    }

    @Nullable
    public final byte[] readBytes(@NotNull String filepath) throws Throwable {
        FileInputStream fileInputStream;
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        Closeable closeable = null;
        try {
            fileInputStream = new FileInputStream(filepath);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = fileInputStream.read(bArr, 0, 1024);
                    if (i2 == -1) {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        closeSilently(fileInputStream);
                        return byteArray;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
            } catch (IOException unused) {
                closeSilently(fileInputStream);
                return null;
            } catch (Throwable th) {
                th = th;
                closeable = fileInputStream;
                closeSilently(closeable);
                throw th;
            }
        } catch (IOException unused2) {
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @JvmOverloads
    @NotNull
    public final String readText(@NotNull String filepath) {
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        return readText$default(this, filepath, null, 2, null);
    }

    @JvmOverloads
    @NotNull
    public final String readText(@NotNull String filepath, @NotNull String charset) throws Throwable {
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        Intrinsics.checkNotNullParameter(charset, "charset");
        try {
            byte[] bytes = readBytes(filepath);
            if (bytes == null) {
                return "";
            }
            Charset charsetForName = Charset.forName(charset);
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(charset)");
            String str = new String(bytes, charsetForName);
            int length = str.length() - 1;
            int i2 = 0;
            boolean z2 = false;
            while (i2 <= length) {
                boolean z3 = Intrinsics.compare((int) str.charAt(!z2 ? i2 : length), 32) <= 0;
                if (z2) {
                    if (!z3) {
                        break;
                    }
                    length--;
                } else if (z3) {
                    i2++;
                } else {
                    z2 = true;
                }
            }
            return str.subSequence(i2, length + 1).toString();
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public final boolean rename(@NotNull String oldPath, @NotNull String newPath) {
        Intrinsics.checkNotNullParameter(oldPath, "oldPath");
        Intrinsics.checkNotNullParameter(newPath, "newPath");
        return rename(new File(oldPath), new File(newPath));
    }

    @NotNull
    public final String separator(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        String separator = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        String strReplace$default = StringsKt__StringsJVMKt.replace$default(path, StrPool.BACKSLASH, separator, false, 4, (Object) null);
        if (StringsKt__StringsJVMKt.endsWith$default(strReplace$default, separator, false, 2, null)) {
            return strReplace$default;
        }
        return strReplace$default + separator;
    }

    public final boolean writeBytes(@NotNull String filepath, @NotNull byte[] data) throws Throwable {
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        Intrinsics.checkNotNullParameter(data, "data");
        File file = new File(filepath);
        Closeable closeable = null;
        try {
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            try {
                fileOutputStream.write(data);
                closeSilently(fileOutputStream);
                return true;
            } catch (IOException unused) {
                closeable = fileOutputStream;
                closeSilently(closeable);
                return false;
            } catch (Throwable th) {
                th = th;
                closeable = fileOutputStream;
                closeSilently(closeable);
                throw th;
            }
        } catch (IOException unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final boolean writeInputStream(@NotNull String filepath, @NotNull InputStream data) {
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        Intrinsics.checkNotNullParameter(data, "data");
        return writeInputStream(new File(filepath), data);
    }

    @JvmOverloads
    public final boolean writeText(@NotNull String filepath, @NotNull String content) {
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        Intrinsics.checkNotNullParameter(content, "content");
        return writeText$default(this, filepath, content, null, 4, null);
    }

    @JvmOverloads
    public final boolean writeText(@NotNull String filepath, @NotNull String content, @NotNull String charset) {
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(charset, "charset");
        try {
            Charset charsetForName = Charset.forName(charset);
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(charsetName)");
            byte[] bytes = content.getBytes(charsetForName);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            return writeBytes(filepath, bytes);
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    public static /* synthetic */ boolean delete$default(FileUtils fileUtils, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        return fileUtils.delete(str, z2);
    }

    public final boolean makeDirs(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return file.mkdirs();
    }

    public final boolean move(@NotNull File src, @NotNull File tar) {
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(tar, "tar");
        return rename(src, tar);
    }

    public final boolean rename(@NotNull File src, @NotNull File tar) {
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(tar, "tar");
        return src.renameTo(tar);
    }

    public final boolean copy(@NotNull File src, @NotNull File tar) {
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(tar, "tar");
        try {
            if (src.isFile()) {
                FileInputStream fileInputStream = new FileInputStream(src);
                FileOutputStream fileOutputStream = new FileOutputStream(tar);
                try {
                    try {
                        ByteStreamsKt.copyTo$default(fileInputStream, fileOutputStream, 0, 2, null);
                        fileOutputStream.flush();
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(fileOutputStream, null);
                        CloseableKt.closeFinally(fileInputStream, null);
                        return true;
                    } finally {
                    }
                } finally {
                }
            } else {
                if (!src.isDirectory()) {
                    return true;
                }
                tar.mkdirs();
                File[] fileArrListFiles = src.listFiles();
                if (fileArrListFiles == null) {
                    return true;
                }
                for (File file : fileArrListFiles) {
                    FileUtils fileUtils = INSTANCE;
                    File absoluteFile = file.getAbsoluteFile();
                    Intrinsics.checkNotNullExpressionValue(absoluteFile, "file.absoluteFile");
                    fileUtils.copy(absoluteFile, new File(tar.getAbsoluteFile(), file.getName()));
                }
                return true;
            }
        } catch (Exception unused) {
            return false;
        }
    }

    @NotNull
    public final synchronized File createFileIfNotExist(@NotNull String filePath) {
        File file;
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        file = new File(filePath);
        try {
            if (!file.exists()) {
                String parent = file.getParent();
                if (parent != null) {
                    INSTANCE.createFolderIfNotExist(parent);
                }
                file.createNewFile();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return file;
    }

    @NotNull
    public final File createFolderIfNotExist(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @NotNull
    public final String getDateTime(@NotNull File file, @NotNull String format) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(format, "format");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(file.lastModified());
        String str = new SimpleDateFormat(format, Locale.PRC).format(calendar.getTime());
        Intrinsics.checkNotNullExpressionValue(str, "SimpleDateFormat(format,…ale.PRC).format(cal.time)");
        return str;
    }

    public final boolean writeInputStream(@NotNull File file, @NotNull InputStream data) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(data, "data");
        try {
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    ByteStreamsKt.copyTo$default(data, fileOutputStream, 0, 2, null);
                    fileOutputStream.flush();
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileOutputStream, null);
                    CloseableKt.closeFinally(data, null);
                    return true;
                } finally {
                }
            } finally {
            }
        } catch (IOException unused) {
            return false;
        }
    }

    @NotNull
    public final String getPath(@NotNull File root, @NotNull String... subDirFiles) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(subDirFiles, "subDirFiles");
        StringBuilder sb = new StringBuilder(root.getAbsolutePath());
        for (String str : subDirFiles) {
            if (str.length() > 0) {
                sb.append(File.separator);
                sb.append(str);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "path.toString()");
        return string;
    }

    @JvmOverloads
    public final boolean delete(@NotNull String path, boolean deleteRootDir) {
        Intrinsics.checkNotNullParameter(path, "path");
        File file = new File(path);
        if (file.exists()) {
            return delete(file, deleteRootDir);
        }
        return false;
    }

    @Nullable
    public final File[] listFiles(@NotNull String startDirPath, @Nullable final String[] allowExtensions) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        return new File(startDirPath).listFiles(new FilenameFilter() { // from class: com.ykb.ebook.util.e
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return FileUtils.listFiles$lambda$6(allowExtensions, file, str);
            }
        });
    }

    @Nullable
    public final File[] listFiles(@NotNull String startDirPath, @Nullable String allowExtension) {
        Intrinsics.checkNotNullParameter(startDirPath, "startDirPath");
        if (allowExtension == null) {
            return listFiles(startDirPath, (String) null);
        }
        return listFiles(startDirPath, new String[]{allowExtension});
    }
}
