package com.ykb.ebook.util;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J;\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u00062\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\fH\u0002¢\u0006\u0002\u0010\rJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0004H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/ykb/ebook/util/RealPathUtil;", "", "()V", "filePathUri", "Landroid/net/Uri;", "getDataColumn", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "uri", "selection", "selectionArgs", "", "(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getPath", "isDownloadsDocument", "", "isExternalStorageDocument", "isGooglePhotosUri", "isMediaDocument", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nRealPathUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RealPathUtil.kt\ncom/ykb/ebook/util/RealPathUtil\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,167:1\n37#2,2:168\n37#2,2:170\n*S KotlinDebug\n*F\n+ 1 RealPathUtil.kt\ncom/ykb/ebook/util/RealPathUtil\n*L\n35#1:168,2\n50#1:170,2\n*E\n"})
/* loaded from: classes7.dex */
public final class RealPathUtil {

    @NotNull
    public static final RealPathUtil INSTANCE = new RealPathUtil();

    @Nullable
    private static Uri filePathUri;

    private RealPathUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00a9  */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.net.Uri, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r10v4, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r10v5, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.lang.String getDataColumn(android.content.Context r9, android.net.Uri r10, java.lang.String r11, java.lang.String[] r12) throws java.lang.Throwable {
        /*
            r8 = this;
            java.lang.String r0 = "_data"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r7 = 0
            android.content.ContentResolver r1 = r9.getContentResolver()     // Catch: java.lang.Throwable -> L33 java.lang.IllegalArgumentException -> L36
            kotlin.jvm.internal.Intrinsics.checkNotNull(r10)     // Catch: java.lang.Throwable -> L33 java.lang.IllegalArgumentException -> L36
            r6 = 0
            r2 = r10
            r4 = r11
            r5 = r12
            android.database.Cursor r10 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L33 java.lang.IllegalArgumentException -> L36
            if (r10 == 0) goto L2c
            boolean r11 = r10.moveToFirst()     // Catch: java.lang.IllegalArgumentException -> L2a java.lang.Throwable -> La5
            if (r11 == 0) goto L2c
            int r11 = r10.getColumnIndexOrThrow(r0)     // Catch: java.lang.IllegalArgumentException -> L2a java.lang.Throwable -> La5
            java.lang.String r9 = r10.getString(r11)     // Catch: java.lang.IllegalArgumentException -> L2a java.lang.Throwable -> La5
            r10.close()
            return r9
        L2a:
            r11 = move-exception
            goto L38
        L2c:
            if (r10 == 0) goto La4
        L2e:
            r10.close()
            goto La4
        L33:
            r9 = move-exception
            goto La7
        L36:
            r11 = move-exception
            r10 = r7
        L38:
            r11.printStackTrace()     // Catch: java.lang.Throwable -> La5
            java.io.File r11 = new java.io.File     // Catch: java.lang.Throwable -> La5
            java.io.File r12 = r9.getCacheDir()     // Catch: java.lang.Throwable -> La5
            java.lang.String r0 = "tmp"
            r11.<init>(r12, r0)     // Catch: java.lang.Throwable -> La5
            java.lang.String r11 = r11.getAbsolutePath()     // Catch: java.lang.Throwable -> La5
            android.content.ContentResolver r9 = r9.getContentResolver()     // Catch: java.io.IOException -> L9d java.lang.Throwable -> La5
            android.net.Uri r12 = com.ykb.ebook.util.RealPathUtil.filePathUri     // Catch: java.io.IOException -> L9d java.lang.Throwable -> La5
            kotlin.jvm.internal.Intrinsics.checkNotNull(r12)     // Catch: java.io.IOException -> L9d java.lang.Throwable -> La5
            java.lang.String r0 = "r"
            android.os.ParcelFileDescriptor r9 = r9.openFileDescriptor(r12, r0)     // Catch: java.io.IOException -> L9d java.lang.Throwable -> La5
            if (r9 == 0) goto L97
            java.io.FileDescriptor r12 = r9.getFileDescriptor()     // Catch: java.lang.Throwable -> L90
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L90
            r0.<init>(r12)     // Catch: java.lang.Throwable -> L90
            java.io.FileOutputStream r12 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L89
            r12.<init>(r11)     // Catch: java.lang.Throwable -> L89
            r1 = 0
            r2 = 2
            kotlin.io.ByteStreamsKt.copyTo$default(r0, r12, r1, r2, r7)     // Catch: java.lang.Throwable -> L82
            kotlin.io.CloseableKt.closeFinally(r12, r7)     // Catch: java.lang.Throwable -> L89
            kotlin.io.CloseableKt.closeFinally(r0, r7)     // Catch: java.lang.Throwable -> L90
            java.io.File r12 = new java.io.File     // Catch: java.lang.Throwable -> L90
            r12.<init>(r11)     // Catch: java.lang.Throwable -> L90
            java.lang.String r11 = r12.getAbsolutePath()     // Catch: java.lang.Throwable -> L90
            kotlin.io.CloseableKt.closeFinally(r9, r7)     // Catch: java.io.IOException -> L9d java.lang.Throwable -> La5
            r7 = r11
            goto L97
        L82:
            r11 = move-exception
            throw r11     // Catch: java.lang.Throwable -> L84
        L84:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r12, r11)     // Catch: java.lang.Throwable -> L89
            throw r1     // Catch: java.lang.Throwable -> L89
        L89:
            r11 = move-exception
            throw r11     // Catch: java.lang.Throwable -> L8b
        L8b:
            r12 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r11)     // Catch: java.lang.Throwable -> L90
            throw r12     // Catch: java.lang.Throwable -> L90
        L90:
            r11 = move-exception
            throw r11     // Catch: java.lang.Throwable -> L92
        L92:
            r12 = move-exception
            kotlin.io.CloseableKt.closeFinally(r9, r11)     // Catch: java.io.IOException -> L9d java.lang.Throwable -> La5
            throw r12     // Catch: java.io.IOException -> L9d java.lang.Throwable -> La5
        L97:
            if (r10 == 0) goto L9c
            r10.close()
        L9c:
            return r7
        L9d:
            r9 = move-exception
            r9.printStackTrace()     // Catch: java.lang.Throwable -> La5
            if (r10 == 0) goto La4
            goto L2e
        La4:
            return r7
        La5:
            r9 = move-exception
            r7 = r10
        La7:
            if (r7 == 0) goto Lac
            r7.close()
        Lac:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.util.RealPathUtil.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    private final boolean isDownloadsDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.providers.downloads.documents", uri.getAuthority());
    }

    private final boolean isExternalStorageDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.externalstorage.documents", uri.getAuthority());
    }

    private final boolean isGooglePhotosUri(Uri uri) {
        return Intrinsics.areEqual("com.google.android.apps.photos.content", uri.getAuthority());
    }

    private final boolean isMediaDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.providers.media.documents", uri.getAuthority());
    }

    @Nullable
    public final String getPath(@NotNull Context context, @NotNull Uri uri) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        filePathUri = uri;
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                Intrinsics.checkNotNullExpressionValue(docId, "docId");
                String[] strArr = (String[]) StringsKt__StringsKt.split$default((CharSequence) docId, new String[]{":"}, false, 0, 6, (Object) null).toArray(new String[0]);
                if (StringsKt__StringsJVMKt.equals("primary", strArr[0], true)) {
                    return Environment.getExternalStorageDirectory().toString() + '/' + strArr[1];
                }
            } else {
                if (isDownloadsDocument(uri)) {
                    String documentId = DocumentsContract.getDocumentId(uri);
                    Uri uri3 = Uri.parse("content://downloads/public_downloads");
                    Long lValueOf = Long.valueOf(documentId);
                    Intrinsics.checkNotNullExpressionValue(lValueOf, "valueOf(id)");
                    Uri uriWithAppendedId = ContentUris.withAppendedId(uri3, lValueOf.longValue());
                    Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(\n        …eOf(id)\n                )");
                    return getDataColumn(context, uriWithAppendedId, null, null);
                }
                if (isMediaDocument(uri)) {
                    String docId2 = DocumentsContract.getDocumentId(uri);
                    Intrinsics.checkNotNullExpressionValue(docId2, "docId");
                    String[] strArr2 = (String[]) StringsKt__StringsKt.split$default((CharSequence) docId2, new String[]{":"}, false, 0, 6, (Object) null).toArray(new String[0]);
                    String str = strArr2[0];
                    int iHashCode = str.hashCode();
                    if (iHashCode != 93166550) {
                        if (iHashCode != 100313435) {
                            if (iHashCode == 112202875 && str.equals("video")) {
                                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            }
                        } else if (str.equals("image")) {
                            uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        }
                    } else if (str.equals("audio")) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{strArr2[1]});
                }
            }
        } else {
            if (StringsKt__StringsJVMKt.equals("content", uri.getScheme(), true)) {
                return isGooglePhotosUri(uri) ? uri.getLastPathSegment() : getDataColumn(context, uri, null, null);
            }
            if (StringsKt__StringsJVMKt.equals("file", uri.getScheme(), true)) {
                return uri.getPath();
            }
        }
        return uri.getPath();
    }
}
