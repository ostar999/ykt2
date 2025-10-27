package com.psychiatrygarden.activity.courselist.roomDB.dataBean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.chad.library.adapter.base.entity.a;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

@Entity(tableName = "course_cover")
/* loaded from: classes5.dex */
public class CourseCoverBean extends BaseExpandNode implements Serializable, Comparable<CourseCoverBean>, SectionEntity {
    public String activity_id;

    @Ignore
    private boolean allComplete;
    public String cover;

    @Ignore
    private int downloadingCount;

    @Ignore
    private String headerText;

    @PrimaryKey
    public int id;

    @Ignore
    private boolean isExpanded;

    @Ignore
    private boolean isHeader;

    @Ignore
    private List<BaseNode> notes;

    @Ignore
    public int select;
    public int sort;

    @Ignore
    private int state;
    public String title;

    @Ignore
    public boolean isShowBtn = false;

    @Ignore
    public List<CourseDirectoryBean> courseDirectoryBeanList = new ArrayList();

    @Ignore
    private List<Integer> status = new ArrayList();

    @Ignore
    private long size = 1;

    @Ignore
    private long sizeprocess = 1;

    @Ignore
    private int type = 1;

    @Ignore
    private int count = 0;

    public void addChildren(BaseNode baseNode) {
        if (baseNode == null) {
            return;
        }
        if (this.notes == null) {
            this.notes = new ArrayList();
        }
        this.notes.add(baseNode);
    }

    public String getActivity_id() {
        return this.activity_id;
    }

    @Override // com.chad.library.adapter.base.entity.node.BaseNode
    @Nullable
    public List<BaseNode> getChildNode() {
        return this.notes;
    }

    public int getCount() {
        return this.count;
    }

    public List<CourseDirectoryBean> getCourseDirectoryBeanList() {
        return this.courseDirectoryBeanList;
    }

    public String getCover() {
        return this.cover;
    }

    public int getDownloadingCount() {
        return this.downloadingCount;
    }

    public String getHeaderText() {
        return this.headerText;
    }

    public int getId() {
        return this.id;
    }

    @Override // com.chad.library.adapter.base.entity.SectionEntity, com.chad.library.adapter.base.entity.MultiItemEntity
    public /* synthetic */ int getItemType() {
        return a.a(this);
    }

    public List<BaseNode> getNotes() {
        return this.notes;
    }

    public int getSelect() {
        return this.select;
    }

    public long getSize() {
        return this.size;
    }

    public long getSizeprocess() {
        return this.sizeprocess;
    }

    public int getSort() {
        return this.sort;
    }

    public int getState() {
        return this.state;
    }

    public List<Integer> getStatus() {
        return this.status;
    }

    public String getTitle() {
        return this.title;
    }

    public int getType() {
        return this.type;
    }

    public boolean isAllComplete() {
        return this.allComplete;
    }

    @Override // com.chad.library.adapter.base.entity.SectionEntity
    public boolean isHeader() {
        return this.isHeader;
    }

    public boolean isShowBtn() {
        return this.isShowBtn;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public void setAllComplete(boolean allComplete) {
        this.allComplete = allComplete;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCourseDirectoryBeanList(List<CourseDirectoryBean> courseDirectoryBeanList) {
        this.courseDirectoryBeanList = courseDirectoryBeanList;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setDownloadingCount(int downloadingCount) {
        this.downloadingCount = downloadingCount;
    }

    public void setHeader(boolean header) {
        this.isHeader = header;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public void setShowBtn(boolean showBtn) {
        this.isShowBtn = showBtn;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setSizeprocess(long sizeprocess) {
        this.sizeprocess = sizeprocess;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int sort(CourseCoverBean o2) {
        if (this.sort > o2.getSort()) {
            return 1;
        }
        return this.sort < o2.getSort() ? -1 : 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull CourseCoverBean o2) {
        return sort(o2);
    }
}
