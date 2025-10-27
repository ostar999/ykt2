package com.aliyun.player.alivcplayerexpand.util.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DatabaseManager {
    private static final int COMPLETED_STATE = 5;
    public static final String COVERURL = "coverurl";
    public static final String CREATE_TABLE_SQL = "create table if not exists player_download_info (id integer primary key autoincrement,vid text,quality text,title text,coverurl text,duration text,size text,progress integer,status integer,path text,trackindex integer,tvid text,tvname text,watched integer,tvcoverurl text,vidtype integer,format text)";
    public static final String CREATE_TABLE_SQL_WATCH_HISTORY = "create table if not exists player_watch_history_info (id integer primary key autoincrement,vid text,title text,coverurl text,duration text,size text,watchduration integer,tvid text,description text,status text,firstframeurl text,tags text,tvname text,dot text,sort text,isvip text,downloading text,updatetime text,downloaded text,watchpercent integer)";
    public static final String DB_NAME = "Player_Download.db";
    public static final String DESCRIPTION = "description";
    public static final String DOT = "dot";
    public static final String DOWNLOADED = "downloaded";
    public static final String DOWNLOADING = "downloading";
    private static final int DOWNLOADING_STATE = 3;
    public static final String DURATION = "duration";
    public static final String FIRSTFRAMEURL = "firstframeurl";
    public static final String FORMAT = "format";
    private static final int HAS_WATCHED = 1;
    public static final String HISTORY_DB_NAME = "Player_Watch_History.db";
    public static final String ID = "id";
    public static final String ISVIP = "isvip";
    private static final int NOT_WATCH = 0;
    public static final String PATH = "path";
    private static final int PREPARED_STATE = 1;
    public static final String PROGRESS = "progress";
    public static final String QUALITY = "quality";
    private static final String SELECT_ALL_BY_TVID = "select * from player_download_info where tvid=?";
    private static final String SELECT_ALL_SQL = "select * from player_download_info";
    private static final String SELECT_WATCHED_SQL = "select * from player_download_info where watched=?";
    private static final String SELECT_WITH_STATUS_SQL = "select * from player_download_info where status=?";
    public static final String SIZE = "size";
    public static final String SORT = "sort";
    public static final String STATUS = "status";
    private static final int STOP_STATE = 4;
    private static final String TABLE_NAME = "player_download_info";
    public static final String TAGS = "tags";
    public static final String TITLE = "title";
    public static final String TRACKINDEX = "trackindex";
    public static final String TVCOVERURL = "tvcoverurl";
    public static final String TVID = "tvid";
    public static final String TVNAME = "tvname";
    public static final String UPDATETIME = "updatetime";
    public static final String VID = "vid";
    public static final String VIDTYPE = "vidtype";
    private static final int WAIT_STATE = 2;
    public static final String WATCHDURATION = "watchduration";
    public static final String WATCHED = "watched";
    public static final String WATCHPERCENT = "watchpercent";
    public static final String WATCH_HISTORY_TABLE_NAME = "player_watch_history_info";
    private static DatabaseManager mInstance;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase mSqliteDatabase;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (mInstance == null) {
            synchronized (DatabaseManager.class) {
                if (mInstance == null) {
                    mInstance = new DatabaseManager();
                }
            }
        }
        return mInstance;
    }

    private List<AliyunDownloadMediaInfo> selectAllCursorToDownloadMediaInfo(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        }
        while (cursor.moveToNext()) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfo = new AliyunDownloadMediaInfo();
            aliyunDownloadMediaInfo.setVid(cursor.getString(cursor.getColumnIndex("vid")));
            aliyunDownloadMediaInfo.setQuality(cursor.getString(cursor.getColumnIndex(QUALITY)));
            aliyunDownloadMediaInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            aliyunDownloadMediaInfo.setCoverUrl(cursor.getString(cursor.getColumnIndex(COVERURL)));
            aliyunDownloadMediaInfo.setDuration(Long.valueOf(cursor.getString(cursor.getColumnIndex("duration"))).longValue());
            aliyunDownloadMediaInfo.setSize(Long.valueOf(cursor.getString(cursor.getColumnIndex(SIZE))).longValue());
            aliyunDownloadMediaInfo.setProgress(cursor.getInt(cursor.getColumnIndex("progress")));
            aliyunDownloadMediaInfo.setSavePath(cursor.getString(cursor.getColumnIndex("path")));
            int i2 = cursor.getInt(cursor.getColumnIndex("status"));
            aliyunDownloadMediaInfo.setFormat(cursor.getString(cursor.getColumnIndex("format")));
            aliyunDownloadMediaInfo.setQualityIndex(cursor.getInt(cursor.getColumnIndex(TRACKINDEX)));
            aliyunDownloadMediaInfo.setTvId(cursor.getString(cursor.getColumnIndex(TVID)));
            aliyunDownloadMediaInfo.setTvName(cursor.getString(cursor.getColumnIndex(TVNAME)));
            aliyunDownloadMediaInfo.setTvCoverUrl(cursor.getString(cursor.getColumnIndex(TVCOVERURL)));
            aliyunDownloadMediaInfo.setWatched(cursor.getInt(cursor.getColumnIndex(WATCHED)));
            aliyunDownloadMediaInfo.setVidType(cursor.getInt(cursor.getColumnIndex(VIDTYPE)));
            switch (i2) {
                case 0:
                    aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Idle);
                    break;
                case 1:
                    aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Prepare);
                    break;
                case 2:
                    aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Wait);
                    break;
                case 3:
                    aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Start);
                    break;
                case 4:
                    aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Stop);
                    break;
                case 5:
                    aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Complete);
                    break;
                case 6:
                    aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Error);
                    break;
                default:
                    aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Idle);
                    break;
            }
            arrayList.add(aliyunDownloadMediaInfo);
        }
        return arrayList;
    }

    private int updateByVid(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("progress", Integer.valueOf(aliyunDownloadMediaInfo.getProgress()));
        contentValues.put("status", Integer.valueOf(aliyunDownloadMediaInfo.getStatus().ordinal()));
        contentValues.put("path", aliyunDownloadMediaInfo.getSavePath());
        contentValues.put(TRACKINDEX, Integer.valueOf(aliyunDownloadMediaInfo.getQualityIndex()));
        contentValues.put("format", aliyunDownloadMediaInfo.getFormat());
        contentValues.put(QUALITY, aliyunDownloadMediaInfo.getQuality());
        contentValues.put(TVNAME, aliyunDownloadMediaInfo.getTvName());
        contentValues.put(WATCHED, Integer.valueOf(aliyunDownloadMediaInfo.getWatched()));
        return this.mSqliteDatabase.update(TABLE_NAME, contentValues, " vid=?", new String[]{aliyunDownloadMediaInfo.getVid()});
    }

    private int updateByVidAndQuality(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("progress", Integer.valueOf(aliyunDownloadMediaInfo.getProgress()));
        contentValues.put("status", Integer.valueOf(aliyunDownloadMediaInfo.getStatus().ordinal()));
        contentValues.put("path", aliyunDownloadMediaInfo.getSavePath());
        contentValues.put(TRACKINDEX, Integer.valueOf(aliyunDownloadMediaInfo.getQualityIndex()));
        contentValues.put("format", aliyunDownloadMediaInfo.getFormat());
        contentValues.put(TVNAME, aliyunDownloadMediaInfo.getTvName());
        contentValues.put(WATCHED, Integer.valueOf(aliyunDownloadMediaInfo.getWatched()));
        return this.mSqliteDatabase.update(TABLE_NAME, contentValues, " vid=? and quality=?", new String[]{aliyunDownloadMediaInfo.getVid(), aliyunDownloadMediaInfo.getQuality()});
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        DatabaseHelper databaseHelper = this.databaseHelper;
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    public void createDataBase(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context.getApplicationContext());
        if (this.mSqliteDatabase == null) {
            synchronized (DatabaseManager.class) {
                if (this.mSqliteDatabase == null) {
                    this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
                }
            }
        }
    }

    public int delete(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        return this.mSqliteDatabase.delete(TABLE_NAME, "vid=? and quality=?", new String[]{aliyunDownloadMediaInfo.getVid(), aliyunDownloadMediaInfo.getQuality()});
    }

    public void deleteAll() {
        if (!this.mSqliteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        this.mSqliteDatabase.delete(TABLE_NAME, "", new String[0]);
    }

    public void deleteItem(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (!this.mSqliteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        this.mSqliteDatabase.delete(TABLE_NAME, "vid=?,quality=?", new String[]{aliyunDownloadMediaInfo.getVid(), aliyunDownloadMediaInfo.getQuality()});
    }

    public long insert(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("vid", aliyunDownloadMediaInfo.getVid());
        contentValues.put(QUALITY, aliyunDownloadMediaInfo.getQuality());
        contentValues.put("title", aliyunDownloadMediaInfo.getTitle());
        contentValues.put("format", aliyunDownloadMediaInfo.getFormat());
        contentValues.put(COVERURL, aliyunDownloadMediaInfo.getCoverUrl());
        contentValues.put("duration", Long.valueOf(aliyunDownloadMediaInfo.getDuration()));
        contentValues.put(SIZE, Long.valueOf(aliyunDownloadMediaInfo.getSize()));
        contentValues.put("progress", Integer.valueOf(aliyunDownloadMediaInfo.getProgress()));
        contentValues.put("status", Integer.valueOf(aliyunDownloadMediaInfo.getStatus().ordinal()));
        contentValues.put("path", aliyunDownloadMediaInfo.getSavePath());
        contentValues.put(TRACKINDEX, Integer.valueOf(aliyunDownloadMediaInfo.getQualityIndex()));
        contentValues.put(TVID, aliyunDownloadMediaInfo.getTvId());
        contentValues.put(TVNAME, aliyunDownloadMediaInfo.getTvName());
        contentValues.put(TVCOVERURL, aliyunDownloadMediaInfo.getTvCoverUrl());
        contentValues.put(WATCHED, Integer.valueOf(aliyunDownloadMediaInfo.getWatched()));
        contentValues.put(VIDTYPE, Integer.valueOf(aliyunDownloadMediaInfo.getVidType()));
        return this.mSqliteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public List<AliyunDownloadMediaInfo> selectAll() {
        if (!this.mSqliteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_ALL_SQL, new String[0]);
        List<AliyunDownloadMediaInfo> listSelectAllCursorToDownloadMediaInfo = selectAllCursorToDownloadMediaInfo(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllCursorToDownloadMediaInfo;
    }

    public List<AliyunDownloadMediaInfo> selectAllByTvId(String str) {
        if (!this.mSqliteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_ALL_BY_TVID, new String[]{str});
        List<AliyunDownloadMediaInfo> listSelectAllCursorToDownloadMediaInfo = selectAllCursorToDownloadMediaInfo(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllCursorToDownloadMediaInfo;
    }

    public List<AliyunDownloadMediaInfo> selectCompletedList() {
        ArrayList arrayList = new ArrayList();
        if (this.databaseHelper == null) {
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_WITH_STATUS_SQL, new String[]{"5"});
        List<AliyunDownloadMediaInfo> listSelectAllCursorToDownloadMediaInfo = selectAllCursorToDownloadMediaInfo(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllCursorToDownloadMediaInfo;
    }

    public List<AliyunDownloadMediaInfo> selectDownloadingList() {
        ArrayList arrayList = new ArrayList();
        if (this.databaseHelper == null) {
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_WITH_STATUS_SQL, new String[]{"3"});
        List<AliyunDownloadMediaInfo> listSelectAllCursorToDownloadMediaInfo = selectAllCursorToDownloadMediaInfo(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllCursorToDownloadMediaInfo;
    }

    public int selectItemExist(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        Cursor cursorQuery = this.mSqliteDatabase.query(TABLE_NAME, new String[]{"id"}, "vid=? and quality=?", new String[]{aliyunDownloadMediaInfo.getVid(), aliyunDownloadMediaInfo.getQuality()}, null, null, null);
        int count = cursorQuery.getCount();
        cursorQuery.close();
        return count;
    }

    public List<AliyunDownloadMediaInfo> selectPreparedList() {
        ArrayList arrayList = new ArrayList();
        if (this.databaseHelper == null) {
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_WITH_STATUS_SQL, new String[]{"1"});
        List<AliyunDownloadMediaInfo> listSelectAllCursorToDownloadMediaInfo = selectAllCursorToDownloadMediaInfo(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllCursorToDownloadMediaInfo;
    }

    public List<AliyunDownloadMediaInfo> selectStopedList() {
        ArrayList arrayList = new ArrayList();
        if (this.databaseHelper == null) {
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_WITH_STATUS_SQL, new String[]{"4"});
        List<AliyunDownloadMediaInfo> listSelectAllCursorToDownloadMediaInfo = selectAllCursorToDownloadMediaInfo(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllCursorToDownloadMediaInfo;
    }

    public List<AliyunDownloadMediaInfo> selectWaitList() {
        ArrayList arrayList = new ArrayList();
        if (this.databaseHelper == null) {
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_WITH_STATUS_SQL, new String[]{"2"});
        List<AliyunDownloadMediaInfo> listSelectAllCursorToDownloadMediaInfo = selectAllCursorToDownloadMediaInfo(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllCursorToDownloadMediaInfo;
    }

    public List<AliyunDownloadMediaInfo> selectWatchedList() {
        ArrayList arrayList = new ArrayList();
        if (this.databaseHelper == null) {
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_WATCHED_SQL, new String[]{"1"});
        List<AliyunDownloadMediaInfo> listSelectAllCursorToDownloadMediaInfo = selectAllCursorToDownloadMediaInfo(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllCursorToDownloadMediaInfo;
    }

    public int update(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        return TextUtils.isEmpty(aliyunDownloadMediaInfo.getTvId()) ? updateByVidAndQuality(aliyunDownloadMediaInfo) : updateByVid(aliyunDownloadMediaInfo);
    }

    public void createDataBase(Context context, String str) {
        this.databaseHelper = DatabaseHelper.getInstance(context, str);
        if (this.mSqliteDatabase == null) {
            synchronized (DatabaseManager.class) {
                if (this.mSqliteDatabase == null) {
                    this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
                }
            }
        }
    }
}
