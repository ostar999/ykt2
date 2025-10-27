package com.aliyun.player.alivcplayerexpand.util.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.aliyun.player.alivcplayerexpand.bean.DotBean;
import com.aliyun.player.alivcplayerexpand.bean.LongVideoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class LongVideoDatabaseManager {
    private static final String SELECT_ALL_WATCH_HISTORY = "select * from player_watch_history_info";
    private static final String SELECT_ALL_WATCH_HISTORY_LIMIT = "select * from player_watch_history_info order by updatetime desc limit 20";
    private static final String SELECT_WATCH_HISTORY_WITH_VID = "select * from player_watch_history_info where vid = ? ";
    private static LongVideoDatabaseManager mInstance;
    private DatabaseHistoryHelper databaseHelper;
    private Gson mGson = new Gson();
    private SQLiteDatabase mSqliteDatabase;

    private LongVideoDatabaseManager() {
    }

    public static LongVideoDatabaseManager getInstance() {
        if (mInstance == null) {
            synchronized (DatabaseManager.class) {
                if (mInstance == null) {
                    mInstance = new LongVideoDatabaseManager();
                }
            }
        }
        return mInstance;
    }

    private void insertWatchHistory(LongVideoBean longVideoBean) {
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        this.mSqliteDatabase.insert(DatabaseManager.WATCH_HISTORY_TABLE_NAME, null, longVideoBeanToContentValues(longVideoBean));
    }

    private ContentValues longVideoBeanToContentValues(LongVideoBean longVideoBean) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", longVideoBean.getTitle());
        contentValues.put("vid", longVideoBean.getVideoId());
        contentValues.put("description", longVideoBean.getDescription());
        contentValues.put("duration", longVideoBean.getDuration());
        contentValues.put(DatabaseManager.COVERURL, longVideoBean.getCoverUrl());
        contentValues.put("status", longVideoBean.getStatus());
        contentValues.put(DatabaseManager.FIRSTFRAMEURL, longVideoBean.getFirstFrameUrl());
        contentValues.put(DatabaseManager.SIZE, longVideoBean.getSize());
        contentValues.put("tags", longVideoBean.getTags());
        contentValues.put(DatabaseManager.TVID, longVideoBean.getTvId());
        contentValues.put(DatabaseManager.TVNAME, longVideoBean.getTvName());
        contentValues.put("dot", this.mGson.toJson(longVideoBean.getDot()));
        contentValues.put("sort", longVideoBean.getSort());
        contentValues.put(DatabaseManager.ISVIP, Boolean.valueOf(longVideoBean.getIsVip()));
        contentValues.put(DatabaseManager.DOWNLOADING, Boolean.valueOf(longVideoBean.isDownloading()));
        contentValues.put(DatabaseManager.DOWNLOADED, Boolean.valueOf(longVideoBean.isDownloaded()));
        contentValues.put(DatabaseManager.WATCHPERCENT, Integer.valueOf(longVideoBean.getWatchPercent()));
        contentValues.put(DatabaseManager.WATCHDURATION, longVideoBean.getWatchDuration());
        contentValues.put(DatabaseManager.UPDATETIME, Long.valueOf(System.currentTimeMillis()));
        return contentValues;
    }

    private List<LongVideoBean> selectAllWatchHitoryCusrorToLongVideoBean(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        }
        while (cursor.moveToNext()) {
            LongVideoBean longVideoBean = new LongVideoBean();
            longVideoBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            longVideoBean.setVideoId(cursor.getString(cursor.getColumnIndex("vid")));
            longVideoBean.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            longVideoBean.setCoverUrl(cursor.getString(cursor.getColumnIndex(DatabaseManager.COVERURL)));
            longVideoBean.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
            longVideoBean.setSize(cursor.getString(cursor.getColumnIndex(DatabaseManager.SIZE)));
            longVideoBean.setTvId(cursor.getString(cursor.getColumnIndex(DatabaseManager.TVID)));
            longVideoBean.setFirstFrameUrl(cursor.getString(cursor.getColumnIndex(DatabaseManager.FIRSTFRAMEURL)));
            longVideoBean.setTags(cursor.getString(cursor.getColumnIndex("tags")));
            longVideoBean.setTvName(cursor.getString(cursor.getColumnIndex(DatabaseManager.TVNAME)));
            longVideoBean.setDot((List) this.mGson.fromJson(cursor.getString(cursor.getColumnIndex("dot")), new TypeToken<List<DotBean>>() { // from class: com.aliyun.player.alivcplayerexpand.util.database.LongVideoDatabaseManager.1
            }.getType()));
            longVideoBean.setSort(cursor.getString(cursor.getColumnIndex("sort")));
            String string = cursor.getString(cursor.getColumnIndex(DatabaseManager.ISVIP));
            boolean z2 = false;
            if (!TextUtils.isEmpty(string) && !"0".equals(string)) {
                z2 = true;
            }
            longVideoBean.setIsVip(z2);
            longVideoBean.setDownloading(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseManager.DOWNLOADING))));
            longVideoBean.setDownloading(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseManager.DOWNLOADED))));
            longVideoBean.setWatchDuration(cursor.getString(cursor.getColumnIndex(DatabaseManager.WATCHDURATION)));
            longVideoBean.setWatchPercent(cursor.getInt(cursor.getColumnIndex(DatabaseManager.WATCHPERCENT)));
            arrayList.add(longVideoBean);
        }
        return arrayList;
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        DatabaseHistoryHelper databaseHistoryHelper = this.databaseHelper;
        if (databaseHistoryHelper != null) {
            databaseHistoryHelper.close();
        }
    }

    public void createDataBase(Context context) {
        this.databaseHelper = DatabaseHistoryHelper.getInstance(context);
        if (this.mSqliteDatabase == null) {
            synchronized (DatabaseManager.class) {
                if (this.mSqliteDatabase == null) {
                    this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
                }
            }
        }
    }

    public List<LongVideoBean> selectAllWatchHistory() {
        ArrayList arrayList = new ArrayList();
        if (this.databaseHelper == null) {
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_ALL_WATCH_HISTORY_LIMIT, new String[0]);
        List<LongVideoBean> listSelectAllWatchHitoryCusrorToLongVideoBean = selectAllWatchHitoryCusrorToLongVideoBean(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllWatchHitoryCusrorToLongVideoBean;
    }

    public List<LongVideoBean> selectWatchHistoryByVid(LongVideoBean longVideoBean) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase sQLiteDatabase = this.mSqliteDatabase;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.mSqliteDatabase = this.databaseHelper.getWritableDatabase();
        }
        if (longVideoBean == null || longVideoBean.getVideoId() == null) {
            return arrayList;
        }
        Cursor cursorRawQuery = this.mSqliteDatabase.rawQuery(SELECT_WATCH_HISTORY_WITH_VID, new String[]{longVideoBean.getVideoId()});
        List<LongVideoBean> listSelectAllWatchHitoryCusrorToLongVideoBean = selectAllWatchHitoryCusrorToLongVideoBean(cursorRawQuery);
        cursorRawQuery.close();
        return listSelectAllWatchHitoryCusrorToLongVideoBean;
    }

    public void updateWatchHistory(LongVideoBean longVideoBean) {
        List<LongVideoBean> listSelectWatchHistoryByVid = selectWatchHistoryByVid(longVideoBean);
        ContentValues contentValuesLongVideoBeanToContentValues = longVideoBeanToContentValues(longVideoBean);
        if (listSelectWatchHistoryByVid == null || listSelectWatchHistoryByVid.size() <= 0) {
            insertWatchHistory(longVideoBean);
        } else {
            this.mSqliteDatabase.update(DatabaseManager.WATCH_HISTORY_TABLE_NAME, contentValuesLongVideoBeanToContentValues, "vid = ?", new String[]{longVideoBean.getVideoId()});
        }
    }
}
