package com.psychiatrygarden.activity.courselist.roomDB.dataBean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

@Entity(primaryKeys = {"cId", "vid", "chapter_id"}, tableName = "video_down")
/* loaded from: classes5.dex */
public class VideoDownBean extends BaseExpandNode implements Serializable, Comparable<VideoDownBean>, MultiItemEntity {

    @Ignore
    private String duration;

    @Ignore
    private boolean editMode;

    @Ignore
    public String hasPermission;

    @Ignore
    private boolean isExpanded;
    public String mFormat;
    public String mQuality;
    public long mSize;
    public int mStatus;
    public String mTitle;

    @Ignore
    private boolean node3;

    @Ignore
    private List<BaseNode> notes;

    @NonNull
    @Ignore
    public String parentNodeChapterId;
    public String parent_id;

    @Ignore
    private String seeDuration;

    @Ignore
    private int selected;
    public int sort;
    public String thumb;

    @Ignore
    public int videoType;

    @NonNull
    public String cId = "";

    @NonNull
    public String chapter_id = "";

    @NonNull
    public String vid = "";

    @NonNull
    public int obj_id = 0;
    public int mProgress = 0;
    public String mSavePath = null;

    @NonNull
    @Ignore
    public String chapter_title = "";
    public int isEncripted = 0;

    @Ignore
    public boolean isAllSelect = false;

    @Ignore
    public boolean isSelect = false;

    @Ignore
    public boolean isShowBtn = false;

    @Ignore
    public int type = 0;

    @Ignore
    private boolean showStatus = true;

    @Ignore
    public boolean show = true;

    @Ignore
    public boolean childViewExpand = true;

    public void addChildren(BaseNode baseNode) {
        if (baseNode == null) {
            return;
        }
        if (this.notes == null) {
            this.notes = new ArrayList();
        }
        this.notes.add(baseNode);
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_title() {
        return this.chapter_title;
    }

    @Override // com.chad.library.adapter.base.entity.node.BaseNode
    @Nullable
    public List<BaseNode> getChildNode() {
        return null;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getHasPermission() {
        return this.hasPermission;
    }

    public int getIsEncripted() {
        return this.isEncripted;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return 1;
    }

    public List<BaseNode> getNotes() {
        return this.notes;
    }

    public String getParentNodeChapterId() {
        return this.parentNodeChapterId;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public String getSeeDuration() {
        return this.seeDuration;
    }

    public int getSelected() {
        return this.selected;
    }

    public int getSort() {
        return this.sort;
    }

    public String getThumb() {
        return this.thumb;
    }

    public int getType() {
        return this.type;
    }

    public String getVid() {
        return this.vid;
    }

    public String getcId() {
        return this.cId;
    }

    public String getmFormat() {
        return this.mFormat;
    }

    public int getmProgress() {
        return this.mProgress;
    }

    public String getmQuality() {
        return this.mQuality;
    }

    public String getmSavePath() {
        return this.mSavePath;
    }

    public long getmSize() {
        return this.mSize;
    }

    public int getmStatus() {
        return this.mStatus;
    }

    public String getmTitle() {
        return this.mTitle;
    }

    public boolean isAllSelect() {
        return this.isAllSelect;
    }

    public boolean isEditMode() {
        return this.editMode;
    }

    public boolean isNode3() {
        return this.node3;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public boolean isShowBtn() {
        return this.isShowBtn;
    }

    public boolean isShowStatus() {
        return this.showStatus;
    }

    public void setAllSelect(boolean allSelect) {
        this.isAllSelect = allSelect;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setHasPermission(String hasPermission) {
        this.hasPermission = hasPermission;
    }

    public void setIsEncripted(int isEncripted) {
        this.isEncripted = isEncripted;
    }

    public void setNode3(boolean node3) {
        this.node3 = node3;
    }

    public void setParentNodeChapterId(String parentNodeChapterId) {
        this.parentNodeChapterId = parentNodeChapterId;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setSeeDuration(String seeDuration) {
        this.seeDuration = seeDuration;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public void setSelected(int select) {
        this.selected = select;
    }

    public void setShowBtn(boolean showBtn) {
        this.isShowBtn = showBtn;
    }

    public void setShowStatus(boolean showStatus) {
        this.showStatus = showStatus;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public void setmFormat(String mFormat) {
        this.mFormat = mFormat;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public void setmQuality(String mQuality) {
        this.mQuality = mQuality;
    }

    public void setmSavePath(String mSavePath) {
        this.mSavePath = mSavePath;
    }

    public void setmSize(long mSize) {
        this.mSize = mSize;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int sort(VideoDownBean o2) {
        if (this.sort > o2.getSort()) {
            return 1;
        }
        return this.sort < o2.getSort() ? -1 : 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull VideoDownBean o2) {
        return sort(o2);
    }
}
