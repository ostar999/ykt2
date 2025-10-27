package com.psychiatrygarden.activity.courselist.roomDB.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import java.util.List;

@Dao
/* loaded from: classes5.dex */
public interface CourseDirectoryDao {
    @Query("delete from course_directory where id=:id")
    void deleSignleData(String id);

    @Query("delete from course_directory where id in (:ids)")
    void deleteAllData(String[] ids);

    @Query("select * from course_directory where id = :chapterId")
    CourseDirectoryBean findCourseDirectory(int chapterId);

    @Query("select * from course_directory where id = :chapterId and pid=:courseId")
    CourseDirectoryBean findCourseDirectory(int chapterId, String courseId);

    @Query("select * from course_directory where id =:id")
    List<CourseDirectoryBean> findDirectoryByVideoId(int id);

    @Query("select * from course_directory where pid = :courseId")
    List<CourseDirectoryBean> getCourseDirectoryByCourseId(String courseId);

    @Query("select * from course_directory")
    List<CourseDirectoryBean> getList();

    @Insert(onConflict = 1)
    void insertTopicList(List<CourseDirectoryBean> mTList);
}
