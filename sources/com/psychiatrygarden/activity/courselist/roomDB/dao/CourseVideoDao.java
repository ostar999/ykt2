package com.psychiatrygarden.activity.courselist.roomDB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.psychiatrygarden.activity.courselist.bean.CourseVideoListBean;
import java.util.List;

@Dao
/* loaded from: classes5.dex */
public interface CourseVideoDao {
    @Query("delete from course_video")
    void deleteData();

    @Query("delete from course_video where vid=:vid ")
    void deleteVideo(String vid);

    @Delete
    void deleteVideo(CourseVideoListBean.DataBean... dataBean);

    @Query("select * from course_video where vidteaching_id = :vidteaching_id and type = :type and userAndAppId=:userAndAppId and chapter_id=:chapter_id order by sort,id")
    LiveData<List<CourseVideoListBean.DataBean>> getChapterVideoList(String vidteaching_id, String type, String userAndAppId, String chapter_id);

    @Insert(onConflict = 1)
    void insertTopicList(List<CourseVideoListBean.DataBean> mTList);

    @Update
    void updateVideoStatus(CourseVideoListBean.DataBean dataBean);
}
