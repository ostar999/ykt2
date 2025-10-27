package com.psychiatrygarden.activity.courselist.roomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean;
import com.psychiatrygarden.activity.courselist.bean.CourseVideoListBean;
import com.psychiatrygarden.activity.courselist.roomDB.dao.ChapterDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCalalogueDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseCoverDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseDirectoryDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.CourseVideoDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.VideoChildChapterDao;
import com.psychiatrygarden.activity.courselist.roomDB.dao.VideoDownDao;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;

@androidx.room.TypeConverters({TypeConverters.class})
@Database(entities = {CourseListChapterBean.DataBean.class, CourseCalalogueBean.DataNewBean.DataBean.class, CourseVideoListBean.DataBean.class, CourseCoverBean.class, CourseDirectoryBean.class, VideoDownBean.class}, exportSchema = false, version = 6)
/* loaded from: classes5.dex */
public abstract class DatabaseTask extends RoomDatabase {
    public abstract ChapterDao getChapterDao();

    public abstract VideoChildChapterDao getChildChapterDao();

    public abstract CourseCalalogueDao getCourseCalalogueDao();

    public abstract CourseCoverDao getCourseCoverDao();

    public abstract CourseDirectoryDao getCourseDirectoryDao();

    public abstract CourseVideoDao getCourseVideoDao();

    public abstract VideoDownDao getVideoDownDao();
}
