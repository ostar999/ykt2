package com.psychiatrygarden.activity.courselist.roomDB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean;
import java.util.List;

@Dao
/* loaded from: classes5.dex */
public interface ChapterDao {
    @Query("delete from course_chapter")
    void deleteData();

    @Query("select * from course_chapter where vidteaching_id = :vidteaching_id and type = :type")
    LiveData<List<CourseListChapterBean.DataBean>> getChapterList(String vidteaching_id, String type);

    @Insert(onConflict = 1)
    void insertTopicList(List<CourseListChapterBean.DataBean> mTList);
}
