package com.hyphenate.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;

/* loaded from: classes4.dex */
abstract class DocumentFile {
    static final String TAG = "DocumentFile";
    private final DocumentFile mParent;

    public static class DocumentsContractApi19 {
        private static final int FLAG_VIRTUAL_DOCUMENT = 512;
        private static final String TAG = "DocumentFile";

        private DocumentsContractApi19() {
        }

        public static boolean canRead(Context context, Uri uri) {
            return context.checkCallingOrSelfUriPermission(uri, 1) == 0 && !TextUtils.isEmpty(getRawType(context, uri));
        }

        public static boolean canWrite(Context context, Uri uri) {
            if (context.checkCallingOrSelfUriPermission(uri, 2) != 0) {
                return false;
            }
            String rawType = getRawType(context, uri);
            int iQueryForInt = queryForInt(context, uri, "flags", 0);
            if (TextUtils.isEmpty(rawType)) {
                return false;
            }
            if ((iQueryForInt & 4) != 0) {
                return true;
            }
            if (!"vnd.android.document/directory".equals(rawType) || (iQueryForInt & 8) == 0) {
                return (TextUtils.isEmpty(rawType) || (iQueryForInt & 2) == 0) ? false : true;
            }
            return true;
        }

        private static void closeQuietly(AutoCloseable autoCloseable) throws Exception {
            if (autoCloseable != null) {
                try {
                    autoCloseable.close();
                } catch (RuntimeException e2) {
                    throw e2;
                } catch (Exception unused) {
                }
            }
        }

        public static boolean exists(Context context, Uri uri) throws Exception {
            Cursor cursorQuery = null;
            try {
                cursorQuery = context.getContentResolver().query(uri, new String[]{"document_id"}, null, null, null);
                return cursorQuery.getCount() > 0;
            } catch (Exception e2) {
                Log.w(TAG, "Failed query: " + e2);
                return false;
            } finally {
                closeQuietly(cursorQuery);
            }
        }

        public static long getFlags(Context context, Uri uri) {
            return queryForLong(context, uri, "flags", 0L);
        }

        public static String getName(Context context, Uri uri) {
            return queryForString(context, uri, "_display_name", null);
        }

        private static String getRawType(Context context, Uri uri) {
            return queryForString(context, uri, "mime_type", null);
        }

        public static String getType(Context context, Uri uri) {
            String rawType = getRawType(context, uri);
            if ("vnd.android.document/directory".equals(rawType)) {
                return null;
            }
            return rawType;
        }

        public static boolean isDirectory(Context context, Uri uri) {
            return "vnd.android.document/directory".equals(getRawType(context, uri));
        }

        public static boolean isFile(Context context, Uri uri) {
            String rawType = getRawType(context, uri);
            return ("vnd.android.document/directory".equals(rawType) || TextUtils.isEmpty(rawType)) ? false : true;
        }

        public static boolean isVirtual(Context context, Uri uri) {
            return DocumentsContract.isDocumentUri(context, uri) && (getFlags(context, uri) & 512) != 0;
        }

        public static long lastModified(Context context, Uri uri) {
            return queryForLong(context, uri, "last_modified", 0L);
        }

        public static long length(Context context, Uri uri) {
            return queryForLong(context, uri, "_size", 0L);
        }

        private static int queryForInt(Context context, Uri uri, String str, int i2) {
            return (int) queryForLong(context, uri, str, i2);
        }

        private static long queryForLong(Context context, Uri uri, String str, long j2) throws Exception {
            Cursor cursorQuery = null;
            try {
                cursorQuery = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
                return (!cursorQuery.moveToFirst() || cursorQuery.isNull(0)) ? j2 : cursorQuery.getLong(0);
            } catch (Exception e2) {
                Log.w(TAG, "Failed query: " + e2);
                return j2;
            } finally {
                closeQuietly(cursorQuery);
            }
        }

        private static String queryForString(Context context, Uri uri, String str, String str2) throws Exception {
            Cursor cursorQuery = null;
            try {
                cursorQuery = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
                return (!cursorQuery.moveToFirst() || cursorQuery.isNull(0)) ? str2 : cursorQuery.getString(0);
            } catch (Exception e2) {
                Log.w(TAG, "Failed query: " + e2);
                return str2;
            } finally {
                closeQuietly(cursorQuery);
            }
        }
    }

    public static class RawDocumentFile extends DocumentFile {
        private File mFile;

        public RawDocumentFile(DocumentFile documentFile, File file) {
            super(documentFile);
            this.mFile = file;
        }

        private static boolean deleteContents(File file) {
            File[] fileArrListFiles = file.listFiles();
            boolean zDeleteContents = true;
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.isDirectory()) {
                        zDeleteContents &= deleteContents(file2);
                    }
                    if (!file2.delete()) {
                        Log.w(DocumentFile.TAG, "Failed to delete " + file2);
                        zDeleteContents = false;
                    }
                }
            }
            return zDeleteContents;
        }

        private static String getTypeForName(String str) {
            int iLastIndexOf = str.lastIndexOf(46);
            if (iLastIndexOf < 0) {
                return "application/octet-stream";
            }
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(iLastIndexOf + 1).toLowerCase());
            return mimeTypeFromExtension != null ? mimeTypeFromExtension : "application/octet-stream";
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean canRead() {
            return this.mFile.canRead();
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean canWrite() {
            return this.mFile.canWrite();
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean delete() {
            deleteContents(this.mFile);
            return this.mFile.delete();
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean exists() {
            return this.mFile.exists();
        }

        @Override // com.hyphenate.util.DocumentFile
        public String getName() {
            return this.mFile.getName();
        }

        @Override // com.hyphenate.util.DocumentFile
        public String getType() {
            if (this.mFile.isDirectory()) {
                return null;
            }
            return getTypeForName(this.mFile.getName());
        }

        @Override // com.hyphenate.util.DocumentFile
        public Uri getUri() {
            return Uri.fromFile(this.mFile);
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean isDirectory() {
            return this.mFile.isDirectory();
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean isFile() {
            return this.mFile.isFile();
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean isVirtual() {
            return false;
        }

        @Override // com.hyphenate.util.DocumentFile
        public long lastModified() {
            return this.mFile.lastModified();
        }

        @Override // com.hyphenate.util.DocumentFile
        public long length() {
            return this.mFile.length();
        }
    }

    public static class SingleDocumentFile extends DocumentFile {
        private Context mContext;
        private Uri mUri;

        public SingleDocumentFile(DocumentFile documentFile, Context context, Uri uri) {
            super(documentFile);
            this.mContext = context;
            this.mUri = uri;
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean canRead() {
            return DocumentsContractApi19.canRead(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean canWrite() {
            return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean delete() {
            try {
                return DocumentsContract.deleteDocument(this.mContext.getContentResolver(), this.mUri);
            } catch (Exception unused) {
                return false;
            }
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean exists() {
            return DocumentsContractApi19.exists(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public String getName() {
            return DocumentsContractApi19.getName(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public String getType() {
            return DocumentsContractApi19.getType(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public Uri getUri() {
            return this.mUri;
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean isDirectory() {
            return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean isFile() {
            return DocumentsContractApi19.isFile(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public boolean isVirtual() {
            return DocumentsContractApi19.isVirtual(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public long lastModified() {
            return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
        }

        @Override // com.hyphenate.util.DocumentFile
        public long length() {
            return DocumentsContractApi19.length(this.mContext, this.mUri);
        }
    }

    public DocumentFile(DocumentFile documentFile) {
        this.mParent = documentFile;
    }

    public static DocumentFile fromFile(File file) {
        if (file == null) {
            return null;
        }
        return new RawDocumentFile(null, file);
    }

    public static DocumentFile fromSingleUri(Context context, Uri uri) {
        return new SingleDocumentFile(null, context, uri);
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri);
    }

    public abstract boolean canRead();

    public abstract boolean canWrite();

    public abstract boolean delete();

    public abstract boolean exists();

    public abstract String getName();

    public DocumentFile getParentFile() {
        return this.mParent;
    }

    public abstract String getType();

    public abstract Uri getUri();

    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public abstract boolean isVirtual();

    public abstract long lastModified();

    public abstract long length();
}
