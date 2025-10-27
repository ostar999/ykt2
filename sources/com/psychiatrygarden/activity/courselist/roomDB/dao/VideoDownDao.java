package com.psychiatrygarden.activity.courselist.roomDB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import java.util.List;

@Dao
/* loaded from: classes5.dex */
public interface VideoDownDao {
    @Query("delete from video_down where vid in (:vids)")
    void deleteAllData(String[] vids);

    @Query("delete  from video_down where vid=:vid")
    void deleteData(String vid);

    @Query("delete from video_down where vid in (:vids) and cId=:cid")
    void deleteData(String[] vids, String cid);

    @Query("delete from video_down where chapter_id= :chapterId and vid is null or vid =''")
    void deleteEmptyVideo(String chapterId);

    @Query("delete from video_down where vid is null or vid='' and chapter_id=:chapterId")
    void deleteVideoParent(String chapterId);

    @Query("select * from video_down where vid is null or vid ='' and chapter_id = :chapterId")
    VideoDownBean getChildVideoParent(String chapterId);

    @Query("select distinct cId from `video_down` where cId not like 'course%'")
    List<String> getCids();

    @Query("select * from `video_down` where cId=:cId")
    LiveData<List<VideoDownBean>> getDownLoadInfo(String cId);

    @Query("select * from `video_down` where mStatus !=5 and cId =:courseId")
    List<VideoDownBean> getNotCompleteVideoList(String courseId);

    @Query("select * from `video_down` where vid = :vid")
    VideoDownBean getVideoDownBean(String vid);

    @Query("select * from `video_down` where vid = :vid and cId=:cId and chapter_id=:chapter_id and parent_id=:parent_id")
    VideoDownBean getVideoDownBean(String vid, String cId, String chapter_id, String parent_id);

    @Query("select * from video_down where chapter_id =:chapterId and cId = :courseId")
    List<VideoDownBean> getVideoDownListByChapterId(String chapterId, String courseId);

    @Query("select * from `video_down`")
    List<VideoDownBean> getVideoDownLoadAllList();

    @Query("select * from `video_down` where cId=:cId and parent_id=:parent_id")
    List<VideoDownBean> getVideoDownLoadCourseInfo(String cId, String parent_id);

    @Query("select * from `video_down` where cId=:cId")
    List<VideoDownBean> getVideoDownLoadInfo(String cId);

    @Query("select * from `video_down` where cId=:cId and chapter_id=:chapter_id and parent_id=:parent_id")
    List<VideoDownBean> getVideoDownLoadInfo(String cId, String chapter_id, String parent_id);

    @Query("select * from `video_down` where mStatus=:status and cId =:courseId")
    List<VideoDownBean> getVideoDownLoadInfoByStatus(int status, String courseId);

    @Insert(onConflict = 1)
    void insertChildVideos(List<VideoDownBean> list);

    @Insert(onConflict = 1)
    void insertChildVideos(VideoDownBean... videoDownBeans);

    @Insert(onConflict = 1)
    void insertTopicList(VideoDownBean... videoDownBeans);

    @Query("update `video_down` set mQuality=:mQuality,mProgress=:mProgress,mSavePath=:mSavePath,mStatus=:mStatus,mSize=:mSize,mFormat=:mFormat,isEncripted=:isEncripted where vid=:vid")
    void updataModel(String mQuality, String mProgress, String mSavePath, int mStatus, int mSize, String mFormat, int isEncripted, String vid);

    @Query("update `video_down` set mStatus=:mStatus where vid in (:courseId)")
    void updataModelStatus(int mStatus, String[] courseId);

    @Query("update video_down set cId =:courseId where vid=:vid")
    void updateCourseId(String vid, String courseId);

    @Query("update `video_down` set mProgress=:mProgress,mSavePath=:mSavePath,mStatus=:mStatus where vid=:vid")
    void updateModel(String mProgress, String mSavePath, int mStatus, String vid);

    @Query("update `video_down` set mSize=:size,mProgress = :progress ,mStatus = :status where vid = :vid")
    void updateSizeAndProgress(String vid, long size, int progress, int status);

    @Query("update `video_down` set mStatus=:mStatus where vid = :vid")
    void updateVideoStatus(int mStatus, String vid);
}
