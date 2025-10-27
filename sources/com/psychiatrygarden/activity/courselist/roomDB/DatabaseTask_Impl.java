package com.psychiatrygarden.activity.courselist.roomDB;

import android.database.SQLException;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao_Impl;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao_Impl;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao_Impl;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao_Impl;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao_Impl;
import com.psychiatrygarden.activity.courselist.roomDB.dao.VideoChildChapterDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.VideoChildChapterDao_Impl;
import com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao_Impl;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes5.dex */
public final class DatabaseTask_Impl extends DatabaseTask {
    private volatile ChapterDao _chapterDao;
    private volatile CourseCalalogueDao _courseCalalogueDao;
    private volatile CourseCoverDao _courseCoverDao;
    private volatile CourseDirectoryDao _courseDirectoryDao;
    private volatile CourseVideoDao _courseVideoDao;
    private volatile VideoChildChapterDao _videoChildChapterDao;
    private volatile VideoDownDao _videoDownDao;

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() throws SQLException {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `course_chapter`");
            writableDatabase.execSQL("DELETE FROM `course_calaogue`");
            writableDatabase.execSQL("DELETE FROM `course_video`");
            writableDatabase.execSQL("DELETE FROM `course_cover`");
            writableDatabase.execSQL("DELETE FROM `course_directory`");
            writableDatabase.execSQL("DELETE FROM `video_down`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, "course_chapter", "course_calaogue", "course_video", "course_cover", "course_directory", "video_down");
    }

    @Override // androidx.room.RoomDatabase
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) { // from class: com.psychiatrygarden.activity.courselist.roomDB.DatabaseTask_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) throws SQLException {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `course_chapter` (`userAndAppId` TEXT NOT NULL, `chapter_id` INTEGER NOT NULL, `vidteaching_id` INTEGER NOT NULL, `parent_id` INTEGER NOT NULL, `title` TEXT, `have` TEXT, `total` INTEGER NOT NULL, `watched` INTEGER NOT NULL, `isdown` TEXT, `children` TEXT, `type` TEXT NOT NULL, `sort` INTEGER NOT NULL, PRIMARY KEY(`userAndAppId`, `chapter_id`, `vidteaching_id`, `type`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `course_calaogue` (`userAndAppId` TEXT NOT NULL, `id` TEXT NOT NULL, `title` TEXT, `category_id` TEXT NOT NULL, `student_type` TEXT, `count` TEXT, `sort` INTEGER NOT NULL, `watch_permission` TEXT, `expire_str` TEXT, `verify_goods_id` TEXT, `activity_id` TEXT, `courseList` TEXT, PRIMARY KEY(`userAndAppId`, `category_id`, `id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `course_video` (`userAndAppId` TEXT NOT NULL, `id` INTEGER NOT NULL, `thumb` TEXT, `vid` TEXT NOT NULL, `duration` TEXT, `lecturer` TEXT, `sort` INTEGER NOT NULL, `description` TEXT, `chapter_id` TEXT NOT NULL, `vidteaching_id` TEXT, `app_id` TEXT, `watched` TEXT, `title` TEXT, `duration_text` TEXT, `comment_count` TEXT, `count` TEXT, `stutas` INTEGER NOT NULL, `progress` INTEGER NOT NULL, `type` TEXT NOT NULL, `collection` TEXT, `note` TEXT, `is_see` TEXT, `sign_time` TEXT, `free_watch_time` TEXT, `watch_permission` TEXT, `expire_str` TEXT, `activity_id` TEXT, PRIMARY KEY(`userAndAppId`, `vid`, `chapter_id`, `type`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `course_cover` (`id` INTEGER NOT NULL, `cover` TEXT, `title` TEXT, `activity_id` TEXT, `sort` INTEGER NOT NULL, `isExpanded` INTEGER NOT NULL, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `course_directory` (`id` INTEGER NOT NULL, `title` TEXT, `pid` TEXT, `sort` INTEGER NOT NULL, `isExpanded` INTEGER NOT NULL, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `video_down` (`cId` TEXT NOT NULL, `chapter_id` TEXT NOT NULL, `parent_id` TEXT, `vid` TEXT NOT NULL, `obj_id` INTEGER NOT NULL, `mQuality` TEXT, `mProgress` INTEGER NOT NULL, `mSavePath` TEXT, `mTitle` TEXT, `mStatus` INTEGER NOT NULL, `mSize` INTEGER NOT NULL, `mFormat` TEXT, `isEncripted` INTEGER NOT NULL, `thumb` TEXT, `sort` INTEGER NOT NULL, `isExpanded` INTEGER NOT NULL, PRIMARY KEY(`cId`, `vid`, `chapter_id`))");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"d6baf3c071325921a5ca3a421a935bf5\")");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) throws SQLException {
                _db.execSQL("DROP TABLE IF EXISTS `course_chapter`");
                _db.execSQL("DROP TABLE IF EXISTS `course_calaogue`");
                _db.execSQL("DROP TABLE IF EXISTS `course_video`");
                _db.execSQL("DROP TABLE IF EXISTS `course_cover`");
                _db.execSQL("DROP TABLE IF EXISTS `course_directory`");
                _db.execSQL("DROP TABLE IF EXISTS `video_down`");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onCreate(SupportSQLiteDatabase _db) {
                if (((RoomDatabase) DatabaseTask_Impl.this).mCallbacks != null) {
                    int size = ((RoomDatabase) DatabaseTask_Impl.this).mCallbacks.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        ((RoomDatabase.Callback) ((RoomDatabase) DatabaseTask_Impl.this).mCallbacks.get(i2)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                ((RoomDatabase) DatabaseTask_Impl.this).mDatabase = _db;
                DatabaseTask_Impl.this.internalInitInvalidationTracker(_db);
                if (((RoomDatabase) DatabaseTask_Impl.this).mCallbacks != null) {
                    int size = ((RoomDatabase) DatabaseTask_Impl.this).mCallbacks.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        ((RoomDatabase.Callback) ((RoomDatabase) DatabaseTask_Impl.this).mCallbacks.get(i2)).onOpen(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void validateMigration(SupportSQLiteDatabase _db) {
                HashMap map = new HashMap(12);
                map.put("userAndAppId", new TableInfo.Column("userAndAppId", "TEXT", true, 1));
                map.put("chapter_id", new TableInfo.Column("chapter_id", "INTEGER", true, 2));
                map.put("vidteaching_id", new TableInfo.Column("vidteaching_id", "INTEGER", true, 3));
                map.put("parent_id", new TableInfo.Column("parent_id", "INTEGER", true, 0));
                map.put("title", new TableInfo.Column("title", "TEXT", false, 0));
                map.put("have", new TableInfo.Column("have", "TEXT", false, 0));
                map.put("total", new TableInfo.Column("total", "INTEGER", true, 0));
                map.put(DatabaseManager.WATCHED, new TableInfo.Column(DatabaseManager.WATCHED, "INTEGER", true, 0));
                map.put("isdown", new TableInfo.Column("isdown", "TEXT", false, 0));
                map.put("children", new TableInfo.Column("children", "TEXT", false, 0));
                map.put("type", new TableInfo.Column("type", "TEXT", true, 4));
                map.put("sort", new TableInfo.Column("sort", "INTEGER", true, 0));
                TableInfo tableInfo = new TableInfo("course_chapter", map, new HashSet(0), new HashSet(0));
                TableInfo tableInfo2 = TableInfo.read(_db, "course_chapter");
                if (!tableInfo.equals(tableInfo2)) {
                    throw new IllegalStateException("Migration didn't properly handle course_chapter(com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean.DataBean).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
                }
                HashMap map2 = new HashMap(12);
                map2.put("userAndAppId", new TableInfo.Column("userAndAppId", "TEXT", true, 1));
                map2.put("id", new TableInfo.Column("id", "TEXT", true, 3));
                map2.put("title", new TableInfo.Column("title", "TEXT", false, 0));
                map2.put("category_id", new TableInfo.Column("category_id", "TEXT", true, 2));
                map2.put("student_type", new TableInfo.Column("student_type", "TEXT", false, 0));
                map2.put("count", new TableInfo.Column("count", "TEXT", false, 0));
                map2.put("sort", new TableInfo.Column("sort", "INTEGER", true, 0));
                map2.put("watch_permission", new TableInfo.Column("watch_permission", "TEXT", false, 0));
                map2.put("expire_str", new TableInfo.Column("expire_str", "TEXT", false, 0));
                map2.put("verify_goods_id", new TableInfo.Column("verify_goods_id", "TEXT", false, 0));
                map2.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, new TableInfo.Column(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "TEXT", false, 0));
                map2.put("courseList", new TableInfo.Column("courseList", "TEXT", false, 0));
                TableInfo tableInfo3 = new TableInfo("course_calaogue", map2, new HashSet(0), new HashSet(0));
                TableInfo tableInfo4 = TableInfo.read(_db, "course_calaogue");
                if (!tableInfo3.equals(tableInfo4)) {
                    throw new IllegalStateException("Migration didn't properly handle course_calaogue(com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean.DataNewBean.DataBean).\n Expected:\n" + tableInfo3 + "\n Found:\n" + tableInfo4);
                }
                HashMap map3 = new HashMap(27);
                map3.put("userAndAppId", new TableInfo.Column("userAndAppId", "TEXT", true, 1));
                map3.put("id", new TableInfo.Column("id", "INTEGER", true, 0));
                map3.put("thumb", new TableInfo.Column("thumb", "TEXT", false, 0));
                map3.put("vid", new TableInfo.Column("vid", "TEXT", true, 2));
                map3.put("duration", new TableInfo.Column("duration", "TEXT", false, 0));
                map3.put("lecturer", new TableInfo.Column("lecturer", "TEXT", false, 0));
                map3.put("sort", new TableInfo.Column("sort", "INTEGER", true, 0));
                map3.put("description", new TableInfo.Column("description", "TEXT", false, 0));
                map3.put("chapter_id", new TableInfo.Column("chapter_id", "TEXT", true, 3));
                map3.put("vidteaching_id", new TableInfo.Column("vidteaching_id", "TEXT", false, 0));
                map3.put("app_id", new TableInfo.Column("app_id", "TEXT", false, 0));
                map3.put(DatabaseManager.WATCHED, new TableInfo.Column(DatabaseManager.WATCHED, "TEXT", false, 0));
                map3.put("title", new TableInfo.Column("title", "TEXT", false, 0));
                map3.put("duration_text", new TableInfo.Column("duration_text", "TEXT", false, 0));
                map3.put("comment_count", new TableInfo.Column("comment_count", "TEXT", false, 0));
                map3.put("count", new TableInfo.Column("count", "TEXT", false, 0));
                map3.put("stutas", new TableInfo.Column("stutas", "INTEGER", true, 0));
                map3.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0));
                map3.put("type", new TableInfo.Column("type", "TEXT", true, 4));
                map3.put("collection", new TableInfo.Column("collection", "TEXT", false, 0));
                map3.put("note", new TableInfo.Column("note", "TEXT", false, 0));
                map3.put("is_see", new TableInfo.Column("is_see", "TEXT", false, 0));
                map3.put("sign_time", new TableInfo.Column("sign_time", "TEXT", false, 0));
                map3.put("free_watch_time", new TableInfo.Column("free_watch_time", "TEXT", false, 0));
                map3.put("watch_permission", new TableInfo.Column("watch_permission", "TEXT", false, 0));
                map3.put("expire_str", new TableInfo.Column("expire_str", "TEXT", false, 0));
                map3.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, new TableInfo.Column(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "TEXT", false, 0));
                TableInfo tableInfo5 = new TableInfo("course_video", map3, new HashSet(0), new HashSet(0));
                TableInfo tableInfo6 = TableInfo.read(_db, "course_video");
                if (!tableInfo5.equals(tableInfo6)) {
                    throw new IllegalStateException("Migration didn't properly handle course_video(com.psychiatrygarden.activity.courselist.bean.CourseVideoListBean.DataBean).\n Expected:\n" + tableInfo5 + "\n Found:\n" + tableInfo6);
                }
                HashMap map4 = new HashMap(6);
                map4.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
                map4.put(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, new TableInfo.Column(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "TEXT", false, 0));
                map4.put("title", new TableInfo.Column("title", "TEXT", false, 0));
                map4.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, new TableInfo.Column(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "TEXT", false, 0));
                map4.put("sort", new TableInfo.Column("sort", "INTEGER", true, 0));
                map4.put("isExpanded", new TableInfo.Column("isExpanded", "INTEGER", true, 0));
                TableInfo tableInfo7 = new TableInfo("course_cover", map4, new HashSet(0), new HashSet(0));
                TableInfo tableInfo8 = TableInfo.read(_db, "course_cover");
                if (!tableInfo7.equals(tableInfo8)) {
                    throw new IllegalStateException("Migration didn't properly handle course_cover(com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean).\n Expected:\n" + tableInfo7 + "\n Found:\n" + tableInfo8);
                }
                HashMap map5 = new HashMap(5);
                map5.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
                map5.put("title", new TableInfo.Column("title", "TEXT", false, 0));
                map5.put("pid", new TableInfo.Column("pid", "TEXT", false, 0));
                map5.put("sort", new TableInfo.Column("sort", "INTEGER", true, 0));
                map5.put("isExpanded", new TableInfo.Column("isExpanded", "INTEGER", true, 0));
                TableInfo tableInfo9 = new TableInfo("course_directory", map5, new HashSet(0), new HashSet(0));
                TableInfo tableInfo10 = TableInfo.read(_db, "course_directory");
                if (!tableInfo9.equals(tableInfo10)) {
                    throw new IllegalStateException("Migration didn't properly handle course_directory(com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean).\n Expected:\n" + tableInfo9 + "\n Found:\n" + tableInfo10);
                }
                HashMap map6 = new HashMap(16);
                map6.put("cId", new TableInfo.Column("cId", "TEXT", true, 1));
                map6.put("chapter_id", new TableInfo.Column("chapter_id", "TEXT", true, 3));
                map6.put("parent_id", new TableInfo.Column("parent_id", "TEXT", false, 0));
                map6.put("vid", new TableInfo.Column("vid", "TEXT", true, 2));
                map6.put("obj_id", new TableInfo.Column("obj_id", "INTEGER", true, 0));
                map6.put("mQuality", new TableInfo.Column("mQuality", "TEXT", false, 0));
                map6.put("mProgress", new TableInfo.Column("mProgress", "INTEGER", true, 0));
                map6.put("mSavePath", new TableInfo.Column("mSavePath", "TEXT", false, 0));
                map6.put("mTitle", new TableInfo.Column("mTitle", "TEXT", false, 0));
                map6.put("mStatus", new TableInfo.Column("mStatus", "INTEGER", true, 0));
                map6.put("mSize", new TableInfo.Column("mSize", "INTEGER", true, 0));
                map6.put("mFormat", new TableInfo.Column("mFormat", "TEXT", false, 0));
                map6.put("isEncripted", new TableInfo.Column("isEncripted", "INTEGER", true, 0));
                map6.put("thumb", new TableInfo.Column("thumb", "TEXT", false, 0));
                map6.put("sort", new TableInfo.Column("sort", "INTEGER", true, 0));
                map6.put("isExpanded", new TableInfo.Column("isExpanded", "INTEGER", true, 0));
                TableInfo tableInfo11 = new TableInfo("video_down", map6, new HashSet(0), new HashSet(0));
                TableInfo tableInfo12 = TableInfo.read(_db, "video_down");
                if (tableInfo11.equals(tableInfo12)) {
                    return;
                }
                throw new IllegalStateException("Migration didn't properly handle video_down(com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean).\n Expected:\n" + tableInfo11 + "\n Found:\n" + tableInfo12);
            }
        }, "d6baf3c071325921a5ca3a421a935bf5", "5459176c37a10cf6b07865cadd2137e0")).build());
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.DatabaseTask
    public ChapterDao getChapterDao() {
        ChapterDao chapterDao;
        if (this._chapterDao != null) {
            return this._chapterDao;
        }
        synchronized (this) {
            if (this._chapterDao == null) {
                this._chapterDao = new ChapterDao_Impl(this);
            }
            chapterDao = this._chapterDao;
        }
        return chapterDao;
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.DatabaseTask
    public VideoChildChapterDao getChildChapterDao() {
        VideoChildChapterDao videoChildChapterDao;
        if (this._videoChildChapterDao != null) {
            return this._videoChildChapterDao;
        }
        synchronized (this) {
            if (this._videoChildChapterDao == null) {
                this._videoChildChapterDao = new VideoChildChapterDao_Impl(this);
            }
            videoChildChapterDao = this._videoChildChapterDao;
        }
        return videoChildChapterDao;
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.DatabaseTask
    public CourseCalalogueDao getCourseCalalogueDao() {
        CourseCalalogueDao courseCalalogueDao;
        if (this._courseCalalogueDao != null) {
            return this._courseCalalogueDao;
        }
        synchronized (this) {
            if (this._courseCalalogueDao == null) {
                this._courseCalalogueDao = new CourseCalalogueDao_Impl(this);
            }
            courseCalalogueDao = this._courseCalalogueDao;
        }
        return courseCalalogueDao;
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.DatabaseTask
    public CourseCoverDao getCourseCoverDao() {
        CourseCoverDao courseCoverDao;
        if (this._courseCoverDao != null) {
            return this._courseCoverDao;
        }
        synchronized (this) {
            if (this._courseCoverDao == null) {
                this._courseCoverDao = new CourseCoverDao_Impl(this);
            }
            courseCoverDao = this._courseCoverDao;
        }
        return courseCoverDao;
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.DatabaseTask
    public CourseDirectoryDao getCourseDirectoryDao() {
        CourseDirectoryDao courseDirectoryDao;
        if (this._courseDirectoryDao != null) {
            return this._courseDirectoryDao;
        }
        synchronized (this) {
            if (this._courseDirectoryDao == null) {
                this._courseDirectoryDao = new CourseDirectoryDao_Impl(this);
            }
            courseDirectoryDao = this._courseDirectoryDao;
        }
        return courseDirectoryDao;
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.DatabaseTask
    public CourseVideoDao getCourseVideoDao() {
        CourseVideoDao courseVideoDao;
        if (this._courseVideoDao != null) {
            return this._courseVideoDao;
        }
        synchronized (this) {
            if (this._courseVideoDao == null) {
                this._courseVideoDao = new CourseVideoDao_Impl(this);
            }
            courseVideoDao = this._courseVideoDao;
        }
        return courseVideoDao;
    }

    @Override // com.psychiatrygarden.activity.courselist.roomDB.DatabaseTask
    public VideoDownDao getVideoDownDao() {
        VideoDownDao videoDownDao;
        if (this._videoDownDao != null) {
            return this._videoDownDao;
        }
        synchronized (this) {
            if (this._videoDownDao == null) {
                this._videoDownDao = new VideoDownDao_Impl(this);
            }
            videoDownDao = this._videoDownDao;
        }
        return videoDownDao;
    }
}
