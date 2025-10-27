package com.psychiatrygarden.activity.courselist.bean;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseListChapterBean implements Parcelable {
    public static final Parcelable.Creator<CourseListChapterBean> CREATOR = new Parcelable.Creator<CourseListChapterBean>() { // from class: com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CourseListChapterBean createFromParcel(Parcel in) {
            return new CourseListChapterBean(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CourseListChapterBean[] newArray(int size) {
            return new CourseListChapterBean[size];
        }
    };
    public int code;
    public List<DataBean> data = new ArrayList();
    public String message;
    public int server_time;

    @Entity(primaryKeys = {"userAndAppId", "chapter_id", "vidteaching_id", "type"}, tableName = "course_chapter")
    public static class DataBean implements Serializable, Comparable<DataBean> {

        @NonNull
        public int chapter_id;
        public int parent_id;
        public int sort;
        public String title;
        public int total;

        @NonNull
        public String type;

        @NonNull
        public int vidteaching_id;
        public int watched;

        @NonNull
        public String userAndAppId = UserConfig.getUserId() + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance());
        public String have = "0";
        public String isdown = "0";
        public ArrayList<ChildrenBean> children = new ArrayList<>();

        public static class ChildrenBean implements Serializable, Comparable<ChildrenBean> {
            public int chapter_id;
            public String have;

            @Ignore
            public String isdown = "0";
            public int parent_id;
            public int sort;
            public String title;
            public int total;
            public int vidteaching_id;
            public int watched;

            public int getChapter_id() {
                return this.chapter_id;
            }

            public String getHave() {
                return this.have;
            }

            public String getIsdown() {
                return this.isdown;
            }

            public int getParent_id() {
                return this.parent_id;
            }

            public int getSort() {
                return this.sort;
            }

            public String getTitle() {
                return this.title;
            }

            public int getTotal() {
                return this.total;
            }

            public int getVidteaching_id() {
                return this.vidteaching_id;
            }

            public int getWatched() {
                return this.watched;
            }

            public void setChapter_id(int chapter_id) {
                this.chapter_id = chapter_id;
            }

            public void setHave(String have) {
                this.have = have;
            }

            public void setIsdown(String isdown) {
                this.isdown = isdown;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setVidteaching_id(int vidteaching_id) {
                this.vidteaching_id = vidteaching_id;
            }

            public void setWatched(int watched) {
                this.watched = watched;
            }

            public int sort(ChildrenBean o2) {
                if (this.sort > o2.getSort()) {
                    return 1;
                }
                return this.sort < o2.getSort() ? -1 : 0;
            }

            @Override // java.lang.Comparable
            public int compareTo(@NonNull ChildrenBean o2) {
                return sort(o2);
            }
        }

        public int getChapter_id() {
            return this.chapter_id;
        }

        public ArrayList<ChildrenBean> getChildren() {
            return this.children;
        }

        public String getHave() {
            return this.have;
        }

        public String getIsdown() {
            return this.isdown;
        }

        public int getParent_id() {
            return this.parent_id;
        }

        public int getSort() {
            return this.sort;
        }

        public String getTitle() {
            return this.title;
        }

        public int getTotal() {
            return this.total;
        }

        @NonNull
        public String getType() {
            return this.type;
        }

        @NonNull
        public String getUserAndAppId() {
            return this.userAndAppId;
        }

        public int getVidteaching_id() {
            return this.vidteaching_id;
        }

        public int getWatched() {
            return this.watched;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setChildren(ArrayList<ChildrenBean> children) {
            this.children = children;
        }

        public void setHave(String have) {
            this.have = have;
        }

        public void setIsdown(String isdown) {
            this.isdown = isdown;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setType(@NonNull String type) {
            this.type = type;
        }

        public void setUserAndAppId(@NonNull String userAndAppId) {
            this.userAndAppId = userAndAppId;
        }

        public void setVidteaching_id(int vidteaching_id) {
            this.vidteaching_id = vidteaching_id;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }

        public int sort(DataBean o2) {
            if (this.sort > o2.getSort()) {
                return 1;
            }
            return this.sort < o2.getSort() ? -1 : 0;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull DataBean o2) {
            return sort(o2);
        }
    }

    public CourseListChapterBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.server_time = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeInt(this.server_time);
    }
}
