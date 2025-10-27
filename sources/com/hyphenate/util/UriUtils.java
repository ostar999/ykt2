package com.hyphenate.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;

/* loaded from: classes4.dex */
public class UriUtils {
    private static final String TAG = "UriUtils";

    /* JADX WARN: Removed duplicated region for block: B:40:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0098 A[Catch: IOException -> 0x0094, TRY_LEAVE, TryCatch #3 {IOException -> 0x0094, blocks: (B:45:0x0090, B:49:0x0098), top: B:55:0x0090 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0090 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String copyFileProviderUri(android.content.Context r6, android.net.Uri r7) throws java.lang.Throwable {
        /*
            java.lang.String r0 = getFileNameByUri(r6, r7)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r2 = ""
            if (r1 == 0) goto Ld
            return r2
        Ld:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.hyphenate.util.PathUtil r3 = com.hyphenate.util.PathUtil.getInstance()
            java.io.File r3 = r3.getFilePath()
            r1.append(r3)
            java.lang.String r3 = java.io.File.separator
            r1.append(r3)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r1 = r1.exists()
            if (r1 == 0) goto L35
            return r0
        L35:
            r1 = 0
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L67
            java.io.InputStream r6 = r6.openInputStream(r7)     // Catch: java.lang.Throwable -> L64 java.io.IOException -> L67
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5f
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5f
            r1 = 2048(0x800, float:2.87E-42)
            byte[] r1 = new byte[r1]     // Catch: java.io.IOException -> L5a java.lang.Throwable -> L8c
        L47:
            int r3 = r6.read(r1)     // Catch: java.io.IOException -> L5a java.lang.Throwable -> L8c
            r4 = -1
            if (r3 == r4) goto L53
            r4 = 0
            r7.write(r1, r4, r3)     // Catch: java.io.IOException -> L5a java.lang.Throwable -> L8c
            goto L47
        L53:
            r6.close()     // Catch: java.io.IOException -> L74
            r7.close()     // Catch: java.io.IOException -> L74
            goto L7f
        L5a:
            r1 = move-exception
            goto L6b
        L5c:
            r0 = move-exception
            r7 = r1
            goto L8d
        L5f:
            r7 = move-exception
            r5 = r1
            r1 = r7
            r7 = r5
            goto L6b
        L64:
            r0 = move-exception
            r7 = r1
            goto L8e
        L67:
            r6 = move-exception
            r7 = r1
            r1 = r6
            r6 = r7
        L6b:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L8c
            if (r6 == 0) goto L76
            r6.close()     // Catch: java.io.IOException -> L74
            goto L76
        L74:
            r6 = move-exception
            goto L7c
        L76:
            if (r7 == 0) goto L7f
            r7.close()     // Catch: java.io.IOException -> L74
            goto L7f
        L7c:
            r6.printStackTrace()
        L7f:
            java.io.File r6 = new java.io.File
            r6.<init>(r0)
            boolean r6 = r6.exists()
            if (r6 == 0) goto L8b
            r2 = r0
        L8b:
            return r2
        L8c:
            r0 = move-exception
        L8d:
            r1 = r6
        L8e:
            if (r1 == 0) goto L96
            r1.close()     // Catch: java.io.IOException -> L94
            goto L96
        L94:
            r6 = move-exception
            goto L9c
        L96:
            if (r7 == 0) goto L9f
            r7.close()     // Catch: java.io.IOException -> L94
            goto L9f
        L9c:
            r6.printStackTrace()
        L9f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.util.UriUtils.copyFileProviderUri(android.content.Context, android.net.Uri):java.lang.String");
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) throws Throwable {
        Cursor cursor = null;
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        cursorQuery.close();
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static DocumentFile getDocumentFile(Context context, Uri uri) {
        String str;
        String str2;
        if (uri == null) {
            str = TAG;
            str2 = "uri is null";
        } else {
            DocumentFile documentFileFromSingleUri = DocumentFile.fromSingleUri(context, uri);
            if (documentFileFromSingleUri != null) {
                return documentFileFromSingleUri;
            }
            str = TAG;
            str2 = "DocumentFile is null";
        }
        EMLog.e(str, str2);
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0040, code lost:
    
        if (r8.name.equalsIgnoreCase(r3) == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0042, code lost:
    
        r4 = androidx.core.content.FileProvider.class.getDeclaredMethod("getPathStrategy", android.content.Context.class, java.lang.String.class);
        r4.setAccessible(true);
        r4 = r4.invoke(null, r11, r12.getAuthority());
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0065, code lost:
    
        if (r4 == null) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0067, code lost:
    
        r5 = java.lang.Class.forName(androidx.core.content.FileProvider.class.getName() + "$PathStrategy").getDeclaredMethod("getFileForUri", android.net.Uri.class);
        r5.setAccessible(true);
        r4 = r5.invoke(r4, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0099, code lost:
    
        if ((r4 instanceof java.io.File) == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a1, code lost:
    
        return ((java.io.File) r4).getAbsolutePath();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a2, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a3, code lost:
    
        r4.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0017, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0017, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0017, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0017, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getFPUriToPath(android.content.Context r11, android.net.Uri r12) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            java.lang.Class<androidx.core.content.FileProvider> r0 = androidx.core.content.FileProvider.class
            r1 = 0
            android.content.pm.PackageManager r2 = r11.getPackageManager()     // Catch: java.lang.Exception -> Lb2
            r3 = 8
            java.util.List r2 = r2.getInstalledPackages(r3)     // Catch: java.lang.Exception -> Lb2
            if (r2 == 0) goto Lb6
            java.lang.String r3 = r0.getName()     // Catch: java.lang.Exception -> Lb2
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Exception -> Lb2
        L17:
            boolean r4 = r2.hasNext()     // Catch: java.lang.Exception -> Lb2
            if (r4 == 0) goto Lb6
            java.lang.Object r4 = r2.next()     // Catch: java.lang.Exception -> Lb2
            android.content.pm.PackageInfo r4 = (android.content.pm.PackageInfo) r4     // Catch: java.lang.Exception -> Lb2
            android.content.pm.ProviderInfo[] r4 = r4.providers     // Catch: java.lang.Exception -> Lb2
            if (r4 == 0) goto L17
            int r5 = r4.length     // Catch: java.lang.Exception -> Lb2
            r6 = 0
            r7 = r6
        L2a:
            if (r7 >= r5) goto L17
            r8 = r4[r7]     // Catch: java.lang.Exception -> Lb2
            java.lang.String r9 = r12.getAuthority()     // Catch: java.lang.Exception -> Lb2
            java.lang.String r10 = r8.authority     // Catch: java.lang.Exception -> Lb2
            boolean r9 = r9.equals(r10)     // Catch: java.lang.Exception -> Lb2
            if (r9 == 0) goto Lae
            java.lang.String r4 = r8.name     // Catch: java.lang.Exception -> Lb2
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch: java.lang.Exception -> Lb2
            if (r4 == 0) goto L17
            java.lang.String r4 = "getPathStrategy"
            r5 = 2
            java.lang.Class[] r7 = new java.lang.Class[r5]     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.Class<android.content.Context> r8 = android.content.Context.class
            r7[r6] = r8     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r9 = 1
            r7[r9] = r8     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.reflect.Method r4 = r0.getDeclaredMethod(r4, r7)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            r4.setAccessible(r9)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            r5[r6] = r11     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.String r7 = r12.getAuthority()     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            r5[r9] = r7     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.Object r4 = r4.invoke(r1, r5)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            if (r4 == 0) goto L17
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            r5.<init>()     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.String r7 = r0.getName()     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            r5.append(r7)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.String r7 = "$PathStrategy"
            r5.append(r7)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.String r5 = r5.toString()     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.String r7 = "getFileForUri"
            java.lang.Class[] r8 = new java.lang.Class[r9]     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.Class<android.net.Uri> r10 = android.net.Uri.class
            r8[r6] = r10     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.reflect.Method r5 = r5.getDeclaredMethod(r7, r8)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            r5.setAccessible(r9)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.Object[] r7 = new java.lang.Object[r9]     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            r7[r6] = r12     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.Object r4 = r5.invoke(r4, r7)     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            boolean r5 = r4 instanceof java.io.File     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            if (r5 == 0) goto L17
            java.io.File r4 = (java.io.File) r4     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            java.lang.String r11 = r4.getAbsolutePath()     // Catch: java.lang.ClassNotFoundException -> La2 java.lang.IllegalAccessException -> La8 java.lang.reflect.InvocationTargetException -> Laa java.lang.NoSuchMethodException -> Lac java.lang.Exception -> Lb2
            return r11
        La2:
            r4 = move-exception
        La3:
            r4.printStackTrace()     // Catch: java.lang.Exception -> Lb2
            goto L17
        La8:
            r4 = move-exception
            goto La3
        Laa:
            r4 = move-exception
            goto La3
        Lac:
            r4 = move-exception
            goto La3
        Lae:
            int r7 = r7 + 1
            goto L2a
        Lb2:
            r11 = move-exception
            r11.printStackTrace()
        Lb6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.util.UriUtils.getFPUriToPath(android.content.Context, android.net.Uri):java.lang.String");
    }

    public static long getFileLength(Context context, Uri uri) throws Throwable {
        if (DocumentFile.isDocumentUri(context, uri)) {
            return DocumentFile.fromSingleUri(context, uri).length();
        }
        long length = 0;
        if (context.getContentResolver().getType(uri) == null) {
            String filePath = getFilePath(context, uri);
            if (!TextUtils.isEmpty(filePath)) {
                length = new File(filePath).length();
            }
        } else {
            Cursor cursorQuery = context.getContentResolver().query(uri, null, null, null, null);
            if (cursorQuery != null) {
                int columnIndex = cursorQuery.getColumnIndex("_size");
                cursorQuery.moveToFirst();
                length = cursorQuery.getLong(columnIndex);
                cursorQuery.close();
            }
        }
        EMLog.d(TAG, "getFileLength fileSize: " + length);
        return length;
    }

    @Deprecated
    public static String getFileMimeType(Context context, Uri uri) throws Throwable {
        if (uri == null) {
            return null;
        }
        if (!VersionUtils.isTargetQ(context)) {
            String filePath = getFilePath(context, uri);
            if (TextUtils.isEmpty(filePath)) {
                return null;
            }
            return FileUtils.getMIMEType(new File(filePath));
        }
        if (uriStartWithFile(uri)) {
            return FileUtils.getMIMEType(new File(uri.getPath()));
        }
        if (uriStartWithContent(uri)) {
            DocumentFile documentFile = getDocumentFile(context, uri);
            if (documentFile == null) {
                return null;
            }
            return documentFile.getType();
        }
        if (uri.toString().startsWith("/") && new File(uri.toString()).exists()) {
            return FileUtils.getMIMEType(new File(uri.toString()));
        }
        return null;
    }

    public static String getFileNameByUri(Context context, Uri uri) throws Throwable {
        String name;
        if (context == null || uri == null) {
            return "";
        }
        if (context.getContentResolver().getType(uri) == null) {
            String filePath = getFilePath(context, uri);
            name = TextUtils.isEmpty(filePath) ? getName(uri.toString()) : new File(filePath).getName();
        } else {
            Cursor cursorQuery = context.getContentResolver().query(uri, null, null, null, null);
            if (cursorQuery != null) {
                int columnIndex = cursorQuery.getColumnIndex("_display_name");
                cursorQuery.moveToFirst();
                String string = cursorQuery.getString(columnIndex);
                cursorQuery.close();
                name = string;
            } else {
                name = null;
            }
        }
        EMLog.d(TAG, "getFileNameByUri filename: " + name);
        return name;
    }

    public static String getFilePath(Context context, Uri uri) throws Throwable {
        String dataColumn;
        String dataColumn2;
        String dataColumn3;
        String dataColumn4;
        if (uri == null) {
            return "";
        }
        if (!VersionUtils.isTargetQ(context)) {
            int i2 = Build.VERSION.SDK_INT;
            Uri uri2 = null;
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                    if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                        return Environment.getExternalStorageDirectory() + "/" + strArrSplit[1];
                    }
                } else {
                    if (isDownloadsDocument(uri)) {
                        String documentId = DocumentsContract.getDocumentId(uri);
                        if (documentId.startsWith("raw:")) {
                            return documentId.replaceFirst("raw:", "");
                        }
                        String[] strArr = {"content://downloads/public_downloads", "content://downloads/my_downloads", "content://downloads/all_downloads"};
                        if (i2 >= 26) {
                            try {
                                dataColumn3 = getDataColumn(context, uri, null, null);
                            } catch (Exception unused) {
                            }
                            return !TextUtils.isEmpty(dataColumn3) ? dataColumn3 : "";
                        }
                        for (int i3 = 0; i3 < 3; i3++) {
                            try {
                                dataColumn4 = getDataColumn(context, ContentUris.withAppendedId(Uri.parse(strArr[i3]), Long.valueOf(documentId).longValue()), null, null);
                            } catch (Exception unused2) {
                            }
                            if (!TextUtils.isEmpty(dataColumn4)) {
                                return dataColumn4;
                            }
                        }
                        return "";
                    }
                    if (isMediaDocument(uri)) {
                        String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                        String str = strArrSplit2[0];
                        if ("image".equals(str)) {
                            uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(str)) {
                            uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(str)) {
                            uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        try {
                            dataColumn2 = getDataColumn(context, uri2, "_id=?", new String[]{strArrSplit2[1]});
                        } catch (Exception unused3) {
                        }
                        return !TextUtils.isEmpty(dataColumn2) ? dataColumn2 : "";
                    }
                }
            } else {
                if (isFileProvider(context, uri)) {
                    return getFPUriToPath(context, uri);
                }
                if (isOtherFileProvider(context, uri)) {
                    return copyFileProviderUri(context, uri);
                }
                if (uriStartWithContent(uri)) {
                    try {
                        dataColumn = getDataColumn(context, uri, null, null);
                    } catch (Exception unused4) {
                    }
                    return !TextUtils.isEmpty(dataColumn) ? dataColumn : "";
                }
            }
        }
        return isFileProvider(context, uri) ? getFPUriToPath(context, uri) : isOtherFileProvider(context, uri) ? copyFileProviderUri(context, uri) : uriStartWithFile(uri) ? uri.getPath() : uri.toString().startsWith("/") ? uri.toString() : "";
    }

    public static String getFilePath(Context context, String str) {
        return TextUtils.isEmpty(str) ? str : getFilePath(context, Uri.parse(str));
    }

    @Deprecated
    public static String getFilenameByDocument(Context context, Uri uri) {
        DocumentFile documentFile = getDocumentFile(context, uri);
        return documentFile == null ? "" : documentFile.getName();
    }

    @Deprecated
    public static Uri getLocalUriFromString(String str) {
        File file;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("content")) {
            return Uri.parse(str);
        }
        if (str.startsWith("file") && str.length() > 7) {
            file = new File(Uri.parse(str).getPath());
        } else {
            if (!str.startsWith("/")) {
                return null;
            }
            file = new File(str);
        }
        return Uri.fromFile(file);
    }

    public static String getMimeType(Context context, Uri uri) throws Throwable {
        String type = context.getContentResolver().getType(uri);
        if (TextUtils.isEmpty(type)) {
            String filePath = getFilePath(context, uri);
            if (!TextUtils.isEmpty(filePath)) {
                type = getMimeType(new File(filePath));
            }
        }
        EMLog.d(TAG, "getMimeType mimeType: " + type);
        return type;
    }

    public static String getMimeType(File file) {
        return FileUtils.getMIMEType(file);
    }

    public static String getMimeType(String str) {
        return (str.endsWith(".3gp") || str.endsWith(".amr")) ? "audio/3gp" : (str.endsWith(".jpe") || str.endsWith(".jpeg") || str.endsWith(".jpg")) ? "image/jpeg" : str.endsWith(".amr") ? "audio/amr" : str.endsWith(".mp4") ? "video/mp4" : str.endsWith(PictureMimeType.MP3) ? "audio/mpeg" : "application/octet-stream";
    }

    private static String getName(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(str.lastIndexOf(47) + 1);
    }

    public static String getUriString(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getVideoOrAudioDuration(android.content.Context r7, android.net.Uri r8) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "duration"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            android.content.ContentResolver r1 = r7.getContentResolver()
            r4 = 0
            r5 = 0
            r6 = 0
            r2 = r8
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)
            r2 = 0
            if (r1 == 0) goto L28
            boolean r4 = r1.moveToFirst()
            if (r4 == 0) goto L28
            int r0 = r1.getColumnIndex(r0)
            long r4 = r1.getLong(r0)
            r1.close()
            goto L29
        L28:
            r4 = r2
        L29:
            int r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r0 > 0) goto L6b
            r0 = 0
            android.media.MediaMetadataRetriever r1 = new android.media.MediaMetadataRetriever     // Catch: java.lang.Throwable -> L52 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L5b
            r1.<init>()     // Catch: java.lang.Throwable -> L52 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L5b
            r1.setDataSource(r7, r8)     // Catch: java.lang.Throwable -> L49 java.lang.SecurityException -> L4c java.lang.IllegalArgumentException -> L4f
            r7 = 9
            java.lang.String r7 = r1.extractMetadata(r7)     // Catch: java.lang.Throwable -> L49 java.lang.SecurityException -> L4c java.lang.IllegalArgumentException -> L4f
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Throwable -> L49 java.lang.SecurityException -> L4c java.lang.IllegalArgumentException -> L4f
            int r7 = r7.intValue()     // Catch: java.lang.Throwable -> L49 java.lang.SecurityException -> L4c java.lang.IllegalArgumentException -> L4f
            long r4 = (long) r7
            r1.release()
            goto L6b
        L49:
            r7 = move-exception
            r0 = r1
            goto L65
        L4c:
            r7 = move-exception
            r0 = r1
            goto L55
        L4f:
            r7 = move-exception
            r0 = r1
            goto L5c
        L52:
            r7 = move-exception
            goto L65
        L54:
            r7 = move-exception
        L55:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L52
            if (r0 == 0) goto L6b
            goto L61
        L5b:
            r7 = move-exception
        L5c:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L52
            if (r0 == 0) goto L6b
        L61:
            r0.release()
            goto L6b
        L65:
            if (r0 == 0) goto L6a
            r0.release()
        L6a:
            throw r7
        L6b:
            int r7 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r7 > 0) goto L70
            goto L71
        L70:
            r2 = r4
        L71:
            java.lang.String r7 = com.hyphenate.util.UriUtils.TAG
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r0 = "duration:"
            r8.append(r0)
            r8.append(r2)
            java.lang.String r8 = r8.toString()
            com.hyphenate.util.EMLog.d(r7, r8)
            int r7 = (int) r2
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.util.UriUtils.getVideoOrAudioDuration(android.content.Context, android.net.Uri):int");
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isFileExistByUri(Context context, Uri uri) throws Throwable {
        if (uri == null) {
            return false;
        }
        if (DocumentFile.isDocumentUri(context, uri)) {
            return DocumentFile.fromSingleUri(context, uri).exists();
        }
        String filePath = getFilePath(context, uri);
        if (!TextUtils.isEmpty(filePath)) {
            return new File(filePath).exists();
        }
        if (!uriStartWithFile(uri)) {
            if (!uriStartWithContent(uri)) {
                return uri.toString().startsWith("/") && new File(uri.toString()).exists();
            }
            DocumentFile documentFileFromSingleUri = DocumentFile.fromSingleUri(context, uri);
            return documentFileFromSingleUri != null && documentFileFromSingleUri.exists();
        }
        String path = uri.getPath();
        boolean zExists = new File(path).exists();
        long length = new File(path).length();
        EMLog.d(TAG, "file uri exist = " + zExists + " file length = " + length);
        return zExists;
    }

    public static boolean isFileProvider(Context context, Uri uri) {
        return (context.getApplicationInfo().packageName + ".fileProvider").equalsIgnoreCase(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isOtherFileProvider(Context context, Uri uri) {
        String scheme = uri.getScheme();
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(scheme) || TextUtils.isEmpty(authority)) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.getApplicationInfo().packageName);
        sb.append(".fileProvider");
        return !sb.toString().equalsIgnoreCase(uri.getAuthority()) && "content".equalsIgnoreCase(uri.getScheme()) && authority.contains(".fileProvider".toLowerCase());
    }

    public static boolean uriStartWithContent(Uri uri) {
        return "content".equalsIgnoreCase(uri.getScheme());
    }

    public static boolean uriStartWithFile(Uri uri) {
        return "file".equalsIgnoreCase(uri.getScheme()) && uri.toString().length() > 7;
    }
}
