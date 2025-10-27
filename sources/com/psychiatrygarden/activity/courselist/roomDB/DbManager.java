package com.psychiatrygarden.activity.courselist.roomDB;

import android.content.Context;
import android.database.SQLException;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.LogUtils;

/* loaded from: classes5.dex */
public class DbManager {
    private static volatile DbManager instance;
    private static final Migration migration4_5 = new Migration(4, 5) { // from class: com.psychiatrygarden.activity.courselist.roomDB.DbManager.1
        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase database) throws SQLException {
            database.execSQL("ALTER TABLE video_down  ADD COLUMN obj_id INTEGER   NOT NULL DEFAULT 0");
        }
    };
    private Context context;
    private DatabaseTask dataTask;
    private String currentUserId = "";
    private final Migration MIGRATION_5_6 = new Migration(5, 6) { // from class: com.psychiatrygarden.activity.courselist.roomDB.DbManager.2
        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase database) throws SQLException {
            database.execSQL("CREATE TABLE IF NOT EXISTS video_down_new (isExpanded INTEGER NOT NULL DEFAULT null,cId TEXT NOT NULL DEFAULT null, chapter_id TEXT NOT NULL DEFAULT null, parent_id TEXT DEFAULT NULL, vid TEXT NOT NULL DEFAULT null, obj_id INTEGER NOT NULL DEFAULT null, mQuality TEXT DEFAULT null, mProgress INTEGER NOT NULL DEFAULT null, mSavePath TEXT DEFAULT null, mTitle TEXT DEFAULT null, mStatus INTEGER NOT NULL DEFAULT null, mSize INTEGER NOT NULL DEFAULT null, mFormat TEXT DEFAULT null, isEncripted INTEGER NOT NULL DEFAULT null, thumb TEXT DEFAULT null, sort INTEGER NOT NULL  DEFAULT null, PRIMARY KEY (cId, vid, chapter_id))");
            database.execSQL("INSERT INTO video_down_new (isExpanded,cId, chapter_id, parent_id, vid, obj_id, mQuality, mProgress, mSavePath, mTitle, mStatus, mSize, mFormat, isEncripted, thumb, sort) SELECT isExpanded,cId, chapter_id, parent_id, vid, obj_id, mQuality, mProgress, mSavePath, mTitle, mStatus, mSize, mFormat, isEncripted, thumb, sort FROM video_down");
            database.execSQL("DROP TABLE `video_down`");
            database.execSQL("ALTER TABLE `video_down_new` RENAME TO `video_down`");
            LogUtils.d("migrate", "数据迁移成功");
        }
    };

    private DbManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public static DbManager getInstance(Context context) {
        if (instance == null) {
            synchronized (DbManager.class) {
                if (instance == null) {
                    instance = new DbManager(context);
                }
            }
        }
        return instance;
    }

    public void closeDb() {
        DatabaseTask databaseTask = this.dataTask;
        if (databaseTask != null && databaseTask.isOpen()) {
            this.dataTask.close();
            this.dataTask = null;
        }
        this.currentUserId = "";
    }

    public ChapterDao getChapterDao() {
        initDataBase();
        return this.dataTask.getChapterDao();
    }

    public CourseCalalogueDao getCourseCalalogueDao() {
        initDataBase();
        return this.dataTask.getCourseCalalogueDao();
    }

    public CourseCoverDao getCourseCoverDao() {
        initDataBase();
        return this.dataTask.getCourseCoverDao();
    }

    public CourseDirectoryDao getCourseDirectoryDao() {
        initDataBase();
        return this.dataTask.getCourseDirectoryDao();
    }

    public CourseVideoDao getCourseVideoDao() {
        initDataBase();
        return this.dataTask.getCourseVideoDao();
    }

    public synchronized VideoDownDao getVideoDownDao() {
        initDataBase();
        return this.dataTask.getVideoDownDao();
    }

    public void initDataBase() {
        if (!this.currentUserId.equals(UserConfig.getUserId())) {
            closeDb();
            this.dataTask = openDb(UserConfig.getUserId());
        } else if (this.dataTask == null) {
            this.dataTask = openDb(UserConfig.getUserId());
        }
    }

    public synchronized DatabaseTask openDb(String userId) {
        DatabaseTask databaseTask;
        this.currentUserId = userId;
        databaseTask = (DatabaseTask) Room.databaseBuilder(this.context, DatabaseTask.class, String.format("user_ykb_%s", userId)).addMigrations(migration4_5).addMigrations(this.MIGRATION_5_6).fallbackToDestructiveMigration().build();
        this.dataTask = databaseTask;
        return databaseTask;
    }
}
