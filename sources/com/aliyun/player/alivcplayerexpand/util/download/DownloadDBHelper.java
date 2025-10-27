package com.aliyun.player.alivcplayerexpand.util.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;

/* loaded from: classes2.dex */
public class DownloadDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "download.db";
    private static final String TABLE_NAME = "download_cache";
    private static DownloadDBHelper helper;

    public interface DownloadState {
        public static final int STATE_COMPLETE = 2;
        public static final int STATE_DOWNLOADING = 0;
        public static final int STATE_PAUSE = 1;
    }

    private DownloadDBHelper(Context context, int i2) {
        this(context, DB_NAME, null, i2);
    }

    public static DownloadDBHelper getDownloadHelper(Context context, int i2) {
        if (helper == null) {
            synchronized (DownloadDBHelper.class) {
                if (helper == null) {
                    helper = new DownloadDBHelper(context, i2);
                }
            }
        }
        return helper;
    }

    private boolean hasAdded(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        Cursor cursorRawQuery = getWritableDatabase().rawQuery("select * from download_cache where vid = ? and quality = ? and size = ?", new String[]{aliyunDownloadMediaInfo.getVid(), aliyunDownloadMediaInfo.getQuality(), String.valueOf(aliyunDownloadMediaInfo.getSize())});
        boolean zMoveToNext = cursorRawQuery.moveToNext();
        cursorRawQuery.close();
        return zMoveToNext;
    }

    public int delete(String str, String[] strArr) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        int iDelete = writableDatabase.delete(TABLE_NAME, str, strArr);
        writableDatabase.close();
        return iDelete;
    }

    public long insert(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, int i2) {
        if (hasAdded(aliyunDownloadMediaInfo)) {
            return -1L;
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("vid", aliyunDownloadMediaInfo.getVid());
        contentValues.put("title", aliyunDownloadMediaInfo.getTitle());
        contentValues.put(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, aliyunDownloadMediaInfo.getCoverUrl());
        contentValues.put(DatabaseManager.SIZE, Long.valueOf(aliyunDownloadMediaInfo.getSize()));
        contentValues.put("progress", Integer.valueOf(aliyunDownloadMediaInfo.getProgress()));
        contentValues.put(DatabaseManager.QUALITY, aliyunDownloadMediaInfo.getQuality());
        contentValues.put("state", Integer.valueOf(i2));
        long jInsert = writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
        return jInsert;
    }

    public long insertOnly(String str, ContentValues contentValues) {
        getWritableDatabase();
        return 0L;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL("create table if not exists download_cache (_id integer primary key autoincrement, vid varchar(50), title varchar(50), cover varchar(255),  size long, progress double, quality varchar(10), state integer,  completeTime long)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }

    public Cursor query(String[] strArr, String str, String[] strArr2, String str2, String str3, String str4) {
        return getWritableDatabase().query(TABLE_NAME, strArr, str, strArr2, str2, str3, str4);
    }

    public int update(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, String str, String[] strArr, int i2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("vid", aliyunDownloadMediaInfo.getVid());
        contentValues.put("progress", Integer.valueOf(aliyunDownloadMediaInfo.getProgress()));
        contentValues.put("state", Integer.valueOf(i2));
        int iUpdate = writableDatabase.update(TABLE_NAME, contentValues, str, strArr);
        writableDatabase.close();
        return iUpdate;
    }

    private DownloadDBHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i2) {
        super(context, DB_NAME, cursorFactory, i2);
    }

    public Cursor query(String[] strArr, String str, String[] strArr2) {
        return getWritableDatabase().query(TABLE_NAME, strArr, str, strArr2, null, null, null);
    }
}
