package com.psychiatrygarden.activity.courselist.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.config.UserConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseCalalogueBean implements Serializable {
    private String code;
    private DataNewBean data;
    private String message;
    private String server_time;

    public static class DataNewBean implements Serializable {
        private List<DataBean> chapterList = new ArrayList();
        private PerMissionBean permission;

        @Entity(primaryKeys = {"userAndAppId", "category_id", "id"}, tableName = "course_calaogue")
        public static class DataBean implements Serializable, Cloneable {
            private String activity_id;

            @NonNull
            private String category_id;
            private String count;
            private String expire_str;

            @NonNull
            private String id;
            private int sort;
            private String student_type;
            private String title;
            private String verify_goods_id;
            private String watch_permission;

            @NonNull
            public String userAndAppId = UserConfig.getUserId();

            @Ignore
            private int isSelected = 0;
            private ArrayList<CourseListBean> courseList = new ArrayList<>();

            public static class CourseListBean implements Serializable, Cloneable {
                private String category_id;
                private String chapter_id;
                private String duration;
                private long free_watch_time;
                private String free_watch_vid;
                private String id;
                private String is_see;
                private String lecturer;
                public String mFormat;
                public String mQuality;
                public int mStatus;
                private int progress;
                private String thumb;
                private String title;
                private String vid;
                private String size = "0";
                private int isSelected = 0;
                public String mSavePath = "";

                @SerializedName("see_duration")
                private double durationTemp = 0.0d;
                public int isEncripted = 0;

                @NonNull
                public Object clone() throws CloneNotSupportedException {
                    return (CourseListBean) super.clone();
                }

                public String getCategory_id() {
                    return this.category_id;
                }

                public String getChapter_id() {
                    return this.chapter_id;
                }

                public String getDuration() {
                    return this.duration;
                }

                public double getDurationTemp() {
                    return this.durationTemp;
                }

                public long getFree_watch_time() {
                    return this.free_watch_time;
                }

                public String getFree_watch_vid() {
                    return this.free_watch_vid;
                }

                public String getId() {
                    return this.id;
                }

                public int getIsEncripted() {
                    return this.isEncripted;
                }

                public int getIsSelected() {
                    return this.isSelected;
                }

                public String getIs_see() {
                    return this.is_see;
                }

                public String getLecturer() {
                    return this.lecturer;
                }

                public int getProgress() {
                    return this.progress;
                }

                public String getSize() {
                    return this.size;
                }

                public String getThumb() {
                    return this.thumb;
                }

                public String getTitle() {
                    return this.title;
                }

                public String getVid() {
                    return this.vid;
                }

                public String getmFormat() {
                    return this.mFormat;
                }

                public String getmQuality() {
                    return this.mQuality;
                }

                public String getmSavePath() {
                    return this.mSavePath;
                }

                public int getmStatus() {
                    return this.mStatus;
                }

                public void setCategory_id(String category_id) {
                    this.category_id = category_id;
                }

                public void setChapter_id(String chapter_id) {
                    this.chapter_id = chapter_id;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public void setDurationTemp(double durationTemp) {
                    this.durationTemp = durationTemp;
                }

                public void setFree_watch_time(long free_watch_time) {
                    this.free_watch_time = free_watch_time;
                }

                public void setFree_watch_vid(String free_watch_vid) {
                    this.free_watch_vid = free_watch_vid;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setIsEncripted(int isEncripted) {
                    this.isEncripted = isEncripted;
                }

                public void setIsSelected(int isSelected) {
                    this.isSelected = isSelected;
                }

                public void setIs_see(String is_see) {
                    this.is_see = is_see;
                }

                public void setLecturer(String lecturer) {
                    this.lecturer = lecturer;
                }

                public void setProgress(int progress) {
                    this.progress = progress;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setVid(String vid) {
                    this.vid = vid;
                }

                public void setmFormat(String mFormat) {
                    this.mFormat = mFormat;
                }

                public void setmQuality(String mQuality) {
                    this.mQuality = mQuality;
                }

                public void setmSavePath(String mSavePath) {
                    this.mSavePath = mSavePath;
                }

                public void setmStatus(int mStatus) {
                    this.mStatus = mStatus;
                }
            }

            @NonNull
            public Object clone() throws CloneNotSupportedException {
                DataBean dataBean = (DataBean) super.clone();
                dataBean.courseList = (ArrayList) this.courseList.clone();
                return dataBean;
            }

            public String getActivity_id() {
                return this.activity_id;
            }

            public String getCategory_id() {
                return this.category_id;
            }

            public String getCount() {
                return this.count;
            }

            public ArrayList<CourseListBean> getCourseList() {
                return this.courseList;
            }

            public String getExpire_str() {
                return this.expire_str;
            }

            @NonNull
            public String getId() {
                return this.id;
            }

            public int getIsSelected() {
                return this.isSelected;
            }

            public int getSort() {
                return this.sort;
            }

            public String getStudent_type() {
                return this.student_type;
            }

            public String getTitle() {
                return this.title;
            }

            @NonNull
            public String getUserAndAppId() {
                return this.userAndAppId;
            }

            public String getVerify_goods_id() {
                return this.verify_goods_id;
            }

            public String getWatch_permission() {
                return this.watch_permission;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public void setCourseList(ArrayList<CourseListBean> courseList) {
                this.courseList = courseList;
            }

            public void setExpire_str(String expire_str) {
                this.expire_str = expire_str;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setIsSelected(int isSelected) {
                this.isSelected = isSelected;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public void setStudent_type(String student_type) {
                this.student_type = student_type;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUserAndAppId(@NonNull String userAndAppId) {
                this.userAndAppId = userAndAppId;
            }

            public void setVerify_goods_id(String verify_goods_id) {
                this.verify_goods_id = verify_goods_id;
            }

            public void setWatch_permission(String watch_permission) {
                this.watch_permission = watch_permission;
            }
        }

        public static class PerMissionBean implements Serializable {
            public String activity_id;
            public String expire_str;
            public String permission;
            public String verify_goods_id;

            public String getActivity_id() {
                return this.activity_id;
            }

            public String getExpire_str() {
                return this.expire_str;
            }

            public String getPermission() {
                return this.permission;
            }

            public String getVerify_goods_id() {
                return this.verify_goods_id;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public void setExpire_str(String expire_str) {
                this.expire_str = expire_str;
            }

            public void setPermission(String permission) {
                this.permission = permission;
            }

            public void setVerify_goods_id(String verify_goods_id) {
                this.verify_goods_id = verify_goods_id;
            }
        }

        public List<DataBean> getChapterList() {
            return this.chapterList;
        }

        public PerMissionBean getPermission() {
            return this.permission;
        }

        public void setChapterList(List<DataBean> chapterList) {
            this.chapterList = chapterList;
        }

        public void setPermission(PerMissionBean permission) {
            this.permission = permission;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataNewBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataNewBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
