package com.psychiatrygarden.activity.courselist.roomDB.dataBean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

@Entity(tableName = "course_directory")
/* loaded from: classes5.dex */
public class CourseDirectoryBean extends BaseExpandNode implements Serializable, Comparable<CourseDirectoryBean>, MultiItemEntity {

    @Ignore
    private boolean editMode;

    @PrimaryKey
    public int id;

    @Ignore
    private boolean isExpanded;

    @Ignore
    private List<BaseNode> notes;
    public String pid;

    @Ignore
    public int select;

    @Ignore
    private int selected;
    public int sort;
    public String title;

    @Ignore
    public boolean isShowBtn = false;

    @Ignore
    public List<VideoDownBean> videoDownBeans = new ArrayList();

    public void addChildren(BaseNode baseNode) {
        if (baseNode == null) {
            return;
        }
        if (this.notes == null) {
            this.notes = new ArrayList();
        }
        this.notes.add(baseNode);
    }

    @Override // com.chad.library.adapter.base.entity.node.BaseNode
    @Nullable
    public List<BaseNode> getChildNode() {
        return this.notes;
    }

    public int getId() {
        return this.id;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return 0;
    }

    public List<BaseNode> getNotes() {
        return this.notes;
    }

    public String getPid() {
        return this.pid;
    }

    public int getSelect() {
        return this.select;
    }

    public int getSelected() {
        return this.selected;
    }

    public int getSort() {
        return this.sort;
    }

    public String getTitle() {
        return this.title;
    }

    public List<VideoDownBean> getVideoDownBeans() {
        return this.videoDownBeans;
    }

    public boolean isEditMode() {
        return this.editMode;
    }

    public boolean isShowBtn() {
        return this.isShowBtn;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public void setSelected(int selected) {
        this.selected = selected;
        this.select = selected;
    }

    public void setShowBtn(boolean showBtn) {
        this.isShowBtn = showBtn;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoDownBeans(List<VideoDownBean> videoDownBeans) {
        this.videoDownBeans = videoDownBeans;
    }

    public int sort(CourseDirectoryBean o2) {
        if (this.sort > o2.getSort()) {
            return 1;
        }
        return this.sort < o2.getSort() ? -1 : 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull CourseDirectoryBean o2) {
        return sort(o2);
    }
}
