package com.google.android.exoplayer2.upstream.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
final class CacheFileMetadataIndex {
    private static final int COLUMN_INDEX_LAST_TOUCH_TIMESTAMP = 2;
    private static final int COLUMN_INDEX_LENGTH = 1;
    private static final int COLUMN_INDEX_NAME = 0;
    private static final String COLUMN_LENGTH = "length";
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_PREFIX = "ExoPlayerCacheFileMetadata";
    private static final String TABLE_SCHEMA = "(name TEXT PRIMARY KEY NOT NULL,length INTEGER NOT NULL,last_touch_timestamp INTEGER NOT NULL)";
    private static final int TABLE_VERSION = 1;
    private static final String WHERE_NAME_EQUALS = "name = ?";
    private final DatabaseProvider databaseProvider;
    private String tableName;
    private static final String COLUMN_LAST_TOUCH_TIMESTAMP = "last_touch_timestamp";
    private static final String[] COLUMNS = {"name", "length", COLUMN_LAST_TOUCH_TIMESTAMP};

    public CacheFileMetadataIndex(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    @WorkerThread
    public static void delete(DatabaseProvider databaseProvider, long j2) throws DatabaseIOException {
        String hexString = Long.toHexString(j2);
        try {
            String tableName = getTableName(hexString);
            SQLiteDatabase writableDatabase = databaseProvider.getWritableDatabase();
            writableDatabase.beginTransactionNonExclusive();
            try {
                VersionTable.removeVersion(writableDatabase, 2, hexString);
                dropTable(writableDatabase, tableName);
                writableDatabase.setTransactionSuccessful();
            } finally {
                writableDatabase.endTransaction();
            }
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    private static void dropTable(SQLiteDatabase sQLiteDatabase, String str) throws SQLException {
        String strValueOf = String.valueOf(str);
        sQLiteDatabase.execSQL(strValueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(strValueOf) : new String("DROP TABLE IF EXISTS "));
    }

    private Cursor getCursor() {
        Assertions.checkNotNull(this.tableName);
        return this.databaseProvider.getReadableDatabase().query(this.tableName, COLUMNS, null, null, null, null, null);
    }

    private static String getTableName(String str) {
        String strValueOf = String.valueOf(str);
        return strValueOf.length() != 0 ? TABLE_PREFIX.concat(strValueOf) : new String(TABLE_PREFIX);
    }

    @WorkerThread
    public Map<String, CacheFileMetadata> getAll() throws DatabaseIOException {
        try {
            Cursor cursor = getCursor();
            try {
                HashMap map = new HashMap(cursor.getCount());
                while (cursor.moveToNext()) {
                    map.put((String) Assertions.checkNotNull(cursor.getString(0)), new CacheFileMetadata(cursor.getLong(1), cursor.getLong(2)));
                }
                cursor.close();
                return map;
            } finally {
            }
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @WorkerThread
    public void initialize(long j2) throws DatabaseIOException {
        try {
            String hexString = Long.toHexString(j2);
            this.tableName = getTableName(hexString);
            if (VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 2, hexString) != 1) {
                SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                try {
                    VersionTable.setVersion(writableDatabase, 2, hexString, 1);
                    dropTable(writableDatabase, this.tableName);
                    String str = this.tableName;
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 108);
                    sb.append("CREATE TABLE ");
                    sb.append(str);
                    sb.append(" ");
                    sb.append(TABLE_SCHEMA);
                    writableDatabase.execSQL(sb.toString());
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                } catch (Throwable th) {
                    writableDatabase.endTransaction();
                    throw th;
                }
            }
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @WorkerThread
    public void remove(String str) throws DatabaseIOException {
        Assertions.checkNotNull(this.tableName);
        try {
            this.databaseProvider.getWritableDatabase().delete(this.tableName, WHERE_NAME_EQUALS, new String[]{str});
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @WorkerThread
    public void removeAll(Set<String> set) throws DatabaseIOException {
        Assertions.checkNotNull(this.tableName);
        try {
            SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
            writableDatabase.beginTransactionNonExclusive();
            try {
                Iterator<String> it = set.iterator();
                while (it.hasNext()) {
                    writableDatabase.delete(this.tableName, WHERE_NAME_EQUALS, new String[]{it.next()});
                }
                writableDatabase.setTransactionSuccessful();
            } finally {
                writableDatabase.endTransaction();
            }
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }

    @WorkerThread
    public void set(String str, long j2, long j3) throws SQLException, DatabaseIOException {
        Assertions.checkNotNull(this.tableName);
        try {
            SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put("length", Long.valueOf(j2));
            contentValues.put(COLUMN_LAST_TOUCH_TIMESTAMP, Long.valueOf(j3));
            writableDatabase.replaceOrThrow(this.tableName, null, contentValues);
        } catch (SQLException e2) {
            throw new DatabaseIOException(e2);
        }
    }
}
