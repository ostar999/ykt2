package com.ykb.ebook.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a7\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\bH\u0002¢\u0006\u0002\u0010\t\u001a\u0016\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0003¨\u0006\u0011"}, d2 = {"getDataColumn", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "selection", "selectionArgs", "", "(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getPathFromUri", "isDownloadsDocument", "", "isExternalStorageDocument", "isGooglePhotosUri", "isMediaDocument", "uriToFileApiQ", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nFilePathUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FilePathUtil.kt\ncom/ykb/ebook/util/FilePathUtilKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,184:1\n731#2,9:185\n731#2,9:196\n37#3,2:194\n37#3,2:205\n*S KotlinDebug\n*F\n+ 1 FilePathUtil.kt\ncom/ykb/ebook/util/FilePathUtilKt\n*L\n33#1:185,9\n47#1:196,9\n34#1:194,2\n48#1:205,2\n*E\n"})
/* loaded from: classes7.dex */
public final class FilePathUtilKt {
    private static final String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursorQuery == null || !cursorQuery.moveToFirst()) {
            }
            String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
            Intrinsics.checkNotNullExpressionValue(string, "cursor.getString(index)");
            cursorQuery.close();
            return string;
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x014d  */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String getPathFromUri(@org.jetbrains.annotations.NotNull android.content.Context r7, @org.jetbrains.annotations.NotNull android.net.Uri r8) {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.util.FilePathUtilKt.getPathFromUri(android.content.Context, android.net.Uri):java.lang.String");
    }

    private static final boolean isDownloadsDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.providers.downloads.documents", uri.getAuthority());
    }

    private static final boolean isExternalStorageDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.externalstorage.documents", uri.getAuthority());
    }

    private static final boolean isGooglePhotosUri(Uri uri) {
        return Intrinsics.areEqual("com.google.android.apps.photos.content", uri.getAuthority());
    }

    private static final boolean isMediaDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.providers.media.documents", uri.getAuthority());
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    @androidx.annotation.RequiresApi(api = 29)
    @android.annotation.SuppressLint({"Range"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static final java.lang.String uriToFileApiQ(android.content.Context r9, android.net.Uri r10) throws java.io.IOException {
        /*
            java.lang.String r0 = r10.getScheme()
            java.lang.String r1 = "file"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            java.lang.String r1 = ""
            r2 = 0
            if (r0 == 0) goto L1c
            java.io.File r9 = new java.io.File
            java.lang.String r10 = r10.getPath()
            if (r10 != 0) goto L18
            r10 = r1
        L18:
            r9.<init>(r10)
            goto L7c
        L1c:
            java.lang.String r0 = r10.getScheme()
            java.lang.String r3 = "content"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r3)
            if (r0 == 0) goto L7b
            android.content.ContentResolver r0 = r9.getContentResolver()
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r3 = r0
            r4 = r10
            android.database.Cursor r3 = r3.query(r4, r5, r6, r7, r8)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            boolean r4 = r3.moveToFirst()
            if (r4 == 0) goto L7b
            java.lang.String r4 = "_display_name"
            int r4 = r3.getColumnIndex(r4)
            java.lang.String r4 = r3.getString(r4)
            java.io.InputStream r10 = r0.openInputStream(r10)     // Catch: java.io.IOException -> L74
            java.io.File r0 = new java.io.File     // Catch: java.io.IOException -> L74
            java.io.File r9 = r9.getExternalCacheDir()     // Catch: java.io.IOException -> L74
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)     // Catch: java.io.IOException -> L74
            java.lang.String r9 = r9.getAbsolutePath()     // Catch: java.io.IOException -> L74
            r0.<init>(r9, r4)     // Catch: java.io.IOException -> L74
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L74
            r9.<init>(r0)     // Catch: java.io.IOException -> L74
            kotlin.jvm.internal.Intrinsics.checkNotNull(r10)     // Catch: java.io.IOException -> L74
            com.ykb.ebook.util.b.a(r10, r9)     // Catch: java.io.IOException -> L74
            r9.close()     // Catch: java.io.IOException -> L72
            r10.close()     // Catch: java.io.IOException -> L72
            r3.close()     // Catch: java.io.IOException -> L72
            goto L79
        L72:
            r9 = move-exception
            goto L76
        L74:
            r9 = move-exception
            r0 = r2
        L76:
            r9.printStackTrace()
        L79:
            r9 = r0
            goto L7c
        L7b:
            r9 = r2
        L7c:
            if (r9 == 0) goto L82
            java.lang.String r2 = r9.getAbsolutePath()
        L82:
            if (r2 != 0) goto L85
            goto L86
        L85:
            r1 = r2
        L86:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.util.FilePathUtilKt.uriToFileApiQ(android.content.Context, android.net.Uri):java.lang.String");
    }
}
