package com.google.android.exoplayer2.offline;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class DefaultDownloadIndex implements WritableDownloadIndex {
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_ID = "id";
    private static final int COLUMN_INDEX_BYTES_DOWNLOADED = 13;
    private static final int COLUMN_INDEX_CONTENT_LENGTH = 9;
    private static final int COLUMN_INDEX_CUSTOM_CACHE_KEY = 4;
    private static final int COLUMN_INDEX_DATA = 5;
    private static final int COLUMN_INDEX_FAILURE_REASON = 11;
    private static final int COLUMN_INDEX_ID = 0;
    private static final int COLUMN_INDEX_KEY_SET_ID = 14;
    private static final int COLUMN_INDEX_MIME_TYPE = 1;
    private static final int COLUMN_INDEX_PERCENT_DOWNLOADED = 12;
    private static final int COLUMN_INDEX_START_TIME_MS = 7;
    private static final int COLUMN_INDEX_STATE = 6;
    private static final int COLUMN_INDEX_STOP_REASON = 10;
    private static final int COLUMN_INDEX_STREAM_KEYS = 3;
    private static final int COLUMN_INDEX_UPDATE_TIME_MS = 8;
    private static final int COLUMN_INDEX_URI = 2;
    private static final String COLUMN_STOP_REASON = "stop_reason";
    private static final String TABLE_PREFIX = "ExoPlayerDownloads";
    private static final String TABLE_SCHEMA = "(id TEXT PRIMARY KEY NOT NULL,mime_type TEXT,uri TEXT NOT NULL,stream_keys TEXT NOT NULL,custom_cache_key TEXT,data BLOB NOT NULL,state INTEGER NOT NULL,start_time_ms INTEGER NOT NULL,update_time_ms INTEGER NOT NULL,content_length INTEGER NOT NULL,stop_reason INTEGER NOT NULL,failure_reason INTEGER NOT NULL,percent_downloaded REAL NOT NULL,bytes_downloaded INTEGER NOT NULL,key_set_id BLOB NOT NULL)";

    @VisibleForTesting
    static final int TABLE_VERSION = 3;
    private static final String TRUE = "1";
    private static final String WHERE_ID_EQUALS = "id = ?";
    private static final String WHERE_STATE_IS_DOWNLOADING = "state = 2";
    private final DatabaseProvider databaseProvider;
    private final Object initializationLock;

    @GuardedBy("initializationLock")
    private boolean initialized;
    private final String name;
    private final String tableName;
    private static final String WHERE_STATE_IS_TERMINAL = getStateQuery(3, 4);
    private static final String COLUMN_MIME_TYPE = "mime_type";
    private static final String COLUMN_URI = "uri";
    private static final String COLUMN_STREAM_KEYS = "stream_keys";
    private static final String COLUMN_CUSTOM_CACHE_KEY = "custom_cache_key";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_START_TIME_MS = "start_time_ms";
    private static final String COLUMN_UPDATE_TIME_MS = "update_time_ms";
    private static final String COLUMN_CONTENT_LENGTH = "content_length";
    private static final String COLUMN_FAILURE_REASON = "failure_reason";
    private static final String COLUMN_PERCENT_DOWNLOADED = "percent_downloaded";
    private static final String COLUMN_BYTES_DOWNLOADED = "bytes_downloaded";
    private static final String COLUMN_KEY_SET_ID = "key_set_id";
    private static final String[] COLUMNS = {"id", COLUMN_MIME_TYPE, COLUMN_URI, COLUMN_STREAM_KEYS, COLUMN_CUSTOM_CACHE_KEY, "data", COLUMN_STATE, COLUMN_START_TIME_MS, COLUMN_UPDATE_TIME_MS, COLUMN_CONTENT_LENGTH, "stop_reason", COLUMN_FAILURE_REASON, COLUMN_PERCENT_DOWNLOADED, COLUMN_BYTES_DOWNLOADED, COLUMN_KEY_SET_ID};

    public static final class DownloadCursorImpl implements DownloadCursor {
        private final Cursor cursor;

        @Override // com.google.android.exoplayer2.offline.DownloadCursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.cursor.close();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public int getCount() {
            return this.cursor.getCount();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public Download getDownload() {
            return DefaultDownloadIndex.getDownloadForCurrentRow(this.cursor);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public int getPosition() {
            return this.cursor.getPosition();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public /* synthetic */ boolean isAfterLast() {
            return b.a(this);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public /* synthetic */ boolean isBeforeFirst() {
            return b.b(this);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public boolean isClosed() {
            return this.cursor.isClosed();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public /* synthetic */ boolean isFirst() {
            return b.c(this);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public /* synthetic */ boolean isLast() {
            return b.d(this);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public /* synthetic */ boolean moveToFirst() {
            return b.e(this);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public /* synthetic */ boolean moveToLast() {
            return b.f(this);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public /* synthetic */ boolean moveToNext() {
            return b.g(this);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public boolean moveToPosition(int i2) {
            return this.cursor.moveToPosition(i2);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadCursor
        public /* synthetic */ boolean moveToPrevious() {
            return b.h(this);
        }

        private DownloadCursorImpl(Cursor cursor) {
            this.cursor = cursor;
        }
    }

    public DefaultDownloadIndex(DatabaseProvider databaseProvider) {
        this(databaseProvider, "");
    }

    private static List<StreamKey> decodeStreamKeys(@Nullable String str) {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        for (String str2 : Util.split(str, ",")) {
            String[] strArrSplit = Util.split(str2, "\\.");
            Assertions.checkState(strArrSplit.length == 3);
            arrayList.add(new StreamKey(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[2])));
        }
        return arrayList;
    }

    @VisibleForTesting
    public static String encodeStreamKeys(List<StreamKey> list) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < list.size(); i2++) {
            StreamKey streamKey = list.get(i2);
            sb.append(streamKey.periodIndex);
            sb.append('.');
            sb.append(streamKey.groupIndex);
            sb.append('.');
            sb.append(streamKey.streamIndex);
            sb.append(',');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private void ensureInitialized() throws DatabaseIOException {
        synchronized (this.initializationLock) {
            if (this.initialized) {
                return;
            }
            try {
                int version = VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 0, this.name);
                if (version != 3) {
                    SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
                    writableDatabase.beginTransactionNonExclusive();
                    try {
                        VersionTable.setVersion(writableDatabase, 0, this.name, 3);
                        List<Download> listLoadDownloadsFromVersion2 = version == 2 ? loadDownloadsFromVersion2(writableDatabase) : new ArrayList<>();
                        String strValueOf = String.valueOf(this.tableName);
                        writableDatabase.execSQL(strValueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(strValueOf) : new String("DROP TABLE IF EXISTS "));
                        String str = this.tableName;
                        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 415);
                        sb.append("CREATE TABLE ");
                        sb.append(str);
                        sb.append(" ");
                        sb.append(TABLE_SCHEMA);
                        writableDatabase.execSQL(sb.toString());
                        Iterator<Download> it = listLoadDownloadsFromVersion2.iterator();
                        while (it.hasNext()) {
                            putDownloadInternal(it.next(), writableDatabase);
                        }
                        writableDatabase.setTransactionSuccessful();
                        writableDatabase.endTransaction();
                    } catch (Throwable th) {
                        writableDatabase.endTransaction();
                        throw th;
                    }
                }
                this.initialized = true;
            } catch (SQLException e2) {
                throw new DatabaseIOException(e2);
            }
        }
    }

    private Cursor getCursor(String str, @Nullable String[] strArr) throws DatabaseIOException {
        try {
            return this.databaseProvider.getReadableDatabase().query(this.tableName, COLUMNS, str, strArr, null, null, "start_time_ms ASC");
        } catch (SQLiteException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Download getDownloadForCurrentRow(Cursor cursor) {
        byte[] blob = cursor.getBlob(14);
        DownloadRequest.Builder streamKeys = new DownloadRequest.Builder((String) Assertions.checkNotNull(cursor.getString(0)), Uri.parse((String) Assertions.checkNotNull(cursor.getString(2)))).setMimeType(cursor.getString(1)).setStreamKeys(decodeStreamKeys(cursor.getString(3)));
        if (blob.length <= 0) {
            blob = null;
        }
        DownloadRequest downloadRequestBuild = streamKeys.setKeySetId(blob).setCustomCacheKey(cursor.getString(4)).setData(cursor.getBlob(5)).build();
        DownloadProgress downloadProgress = new DownloadProgress();
        downloadProgress.bytesDownloaded = cursor.getLong(13);
        downloadProgress.percentDownloaded = cursor.getFloat(12);
        int i2 = cursor.getInt(6);
        return new Download(downloadRequestBuild, i2, cursor.getLong(7), cursor.getLong(8), cursor.getLong(9), cursor.getInt(10), i2 == 4 ? cursor.getInt(11) : 0, downloadProgress);
    }

    private static Download getDownloadForCurrentRowV2(Cursor cursor) {
        DownloadRequest downloadRequestBuild = new DownloadRequest.Builder((String) Assertions.checkNotNull(cursor.getString(0)), Uri.parse((String) Assertions.checkNotNull(cursor.getString(2)))).setMimeType(inferMimeType(cursor.getString(1))).setStreamKeys(decodeStreamKeys(cursor.getString(3))).setCustomCacheKey(cursor.getString(4)).setData(cursor.getBlob(5)).build();
        DownloadProgress downloadProgress = new DownloadProgress();
        downloadProgress.bytesDownloaded = cursor.getLong(13);
        downloadProgress.percentDownloaded = cursor.getFloat(12);
        int i2 = cursor.getInt(6);
        return new Download(downloadRequestBuild, i2, cursor.getLong(7), cursor.getLong(8), cursor.getLong(9), cursor.getInt(10), i2 == 4 ? cursor.getInt(11) : 0, downloadProgress);
    }

    private static String getStateQuery(int... iArr) {
        if (iArr.length == 0) {
            return "1";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(COLUMN_STATE);
        sb.append(" IN (");
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (i2 > 0) {
                sb.append(',');
            }
            sb.append(iArr[i2]);
        }
        sb.append(')');
        return sb.toString();
    }

    private static String inferMimeType(@Nullable String str) {
        return "dash".equals(str) ? MimeTypes.APPLICATION_MPD : "hls".equals(str) ? MimeTypes.APPLICATION_M3U8 : "ss".equals(str) ? MimeTypes.APPLICATION_SS : MimeTypes.VIDEO_UNKNOWN;
    }

    private List<Download> loadDownloadsFromVersion2(SQLiteDatabase sQLiteDatabase) {
        ArrayList arrayList = new ArrayList();
        if (!Util.tableExists(sQLiteDatabase, this.tableName)) {
            return arrayList;
        }
        Cursor cursorQuery = sQLiteDatabase.query(this.tableName, new String[]{"id", "title", COLUMN_URI, COLUMN_STREAM_KEYS, COLUMN_CUSTOM_CACHE_KEY, "data", COLUMN_STATE, COLUMN_START_TIME_MS, COLUMN_UPDATE_TIME_MS, COLUMN_CONTENT_LENGTH, "stop_reason", COLUMN_FAILURE_REASON, COLUMN_PERCENT_DOWNLOADED, COLUMN_BYTES_DOWNLOADED}, null, null, null, null, null);
        while (cursorQuery.moveToNext()) {
            try {
                arrayList.add(getDownloadForCurrentRowV2(cursorQuery));
            } finally {
            }
        }
        cursorQuery.close();
        return arrayList;
    }

    private void putDownloadInternal(Download download, SQLiteDatabase sQLiteDatabase) throws SQLException {
        byte[] bArr = download.request.keySetId;
        if (bArr == null) {
            bArr = Util.EMPTY_BYTE_ARRAY;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", download.request.id);
        contentValues.put(COLUMN_MIME_TYPE, download.request.mimeType);
        contentValues.put(COLUMN_URI, download.request.uri.toString());
        contentValues.put(COLUMN_STREAM_KEYS, encodeStreamKeys(download.request.streamKeys));
        contentValues.put(COLUMN_CUSTOM_CACHE_KEY, download.request.customCacheKey);
        contentValues.put("data", download.request.data);
        contentValues.put(COLUMN_STATE, Integer.valueOf(download.state));
        contentValues.put(COLUMN_START_TIME_MS, Long.valueOf(download.startTimeMs));
        contentValues.put(COLUMN_UPDATE_TIME_MS, Long.valueOf(download.updateTimeMs));
        contentValues.put(COLUMN_CONTENT_LENGTH, Long.valueOf(download.contentLength));
        contentValues.put("stop_reason", Integer.valueOf(download.stopReason));
        contentValues.put(COLUMN_FAILURE_REASON, Integer.valueOf(download.failureReason));
        contentValues.put(COLUMN_PERCENT_DOWNLOADED, Float.valueOf(download.getPercentDownloaded()));
        contentValues.put(COLUMN_BYTES_DOWNLOADED, Long.valueOf(download.getBytesDownloaded()));
        contentValues.put(COLUMN_KEY_SET_ID, bArr);
        sQLiteDatabase.replaceOrThrow(this.tableName, null, contentValues);
    }

    @Override // com.google.android.exoplayer2.offline.DownloadIndex
    @Nullable
    public Download getDownload(String str) throws DatabaseIOException {
        ensureInitialized();
        try {
            Cursor cursor = getCursor(WHERE_ID_EQUALS, new String[]{str});
            try {
                if (cursor.getCount() == 0) {
                    cursor.close();
                    return null;
                }
                cursor.moveToNext();
                Download downloadForCurrentRow = getDownloadForCurrentRow(cursor);
                cursor.close();
                return downloadForCurrentRow;
            } finally {
            }
        } catch (SQLiteException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @Override // com.google.android.exoplayer2.offline.DownloadIndex
    public DownloadCursor getDownloads(int... iArr) throws DatabaseIOException {
        ensureInitialized();
        return new DownloadCursorImpl(getCursor(getStateQuery(iArr), null));
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void putDownload(Download download) throws SQLException, DatabaseIOException {
        ensureInitialized();
        try {
            putDownloadInternal(download, this.databaseProvider.getWritableDatabase());
        } catch (SQLiteException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void removeDownload(String str) throws DatabaseIOException {
        ensureInitialized();
        try {
            this.databaseProvider.getWritableDatabase().delete(this.tableName, WHERE_ID_EQUALS, new String[]{str});
        } catch (SQLiteException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void setDownloadingStatesToQueued() throws DatabaseIOException {
        ensureInitialized();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_STATE, (Integer) 0);
            this.databaseProvider.getWritableDatabase().update(this.tableName, contentValues, WHERE_STATE_IS_DOWNLOADING, null);
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void setStatesToRemoving() throws DatabaseIOException {
        ensureInitialized();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_STATE, (Integer) 5);
            contentValues.put(COLUMN_FAILURE_REASON, (Integer) 0);
            this.databaseProvider.getWritableDatabase().update(this.tableName, contentValues, null, null);
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void setStopReason(int i2) throws DatabaseIOException {
        ensureInitialized();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("stop_reason", Integer.valueOf(i2));
            this.databaseProvider.getWritableDatabase().update(this.tableName, contentValues, WHERE_STATE_IS_TERMINAL, null);
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    public DefaultDownloadIndex(DatabaseProvider databaseProvider, String str) {
        this.name = str;
        this.databaseProvider = databaseProvider;
        String strValueOf = String.valueOf(str);
        this.tableName = strValueOf.length() != 0 ? TABLE_PREFIX.concat(strValueOf) : new String(TABLE_PREFIX);
        this.initializationLock = new Object();
    }

    @Override // com.google.android.exoplayer2.offline.WritableDownloadIndex
    public void setStopReason(String str, int i2) throws DatabaseIOException {
        ensureInitialized();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("stop_reason", Integer.valueOf(i2));
            SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
            String str2 = this.tableName;
            String str3 = WHERE_STATE_IS_TERMINAL;
            StringBuilder sb = new StringBuilder(String.valueOf(str3).length() + 11);
            sb.append(str3);
            sb.append(" AND ");
            sb.append(WHERE_ID_EQUALS);
            writableDatabase.update(str2, contentValues, sb.toString(), new String[]{str});
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }
}
