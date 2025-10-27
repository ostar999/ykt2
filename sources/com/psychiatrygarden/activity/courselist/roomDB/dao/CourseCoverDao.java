package com.psychiatrygarden.activity.courselist.roomDB.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import java.util.List;

@Dao
/* loaded from: classes5.dex */
public interface CourseCoverDao {
    @Query("delete from `course_cover` where id=:id")
    void deleSignleData(String id);

    @Query("delete from `course_cover` where id in (:ids)")
    void deleteAllData(String[] ids);

    @Query("select * from course_cover where id = :id")
    CourseCoverBean findCourse(int id);

    @Query("select * from `course_cover`")
    List<CourseCoverBean> getList();

    @Insert(onConflict = 1)
    void insertTopic(CourseCoverBean mTList);
}
