package com.psychiatrygarden.activity.courselist.roomDB.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import java.util.List;

@Dao
/* loaded from: classes5.dex */
public interface CourseCalalogueDao {
    @Query("delete from course_calaogue where userAndAppId = :userAndAppId and category_id = :category_id")
    void deleteCourseCalalogue(String userAndAppId, String category_id);

    @Query("select * from course_calaogue where userAndAppId = :userAndAppId and category_id = :category_id order by sort asc")
    List<CourseCalalogueBean.DataNewBean.DataBean> getCourseCalalogue(String userAndAppId, String category_id);

    @Insert(onConflict = 1)
    void insertCourseCalalogueData(List<CourseCalalogueBean.DataNewBean.DataBean> dataBeans);
}
