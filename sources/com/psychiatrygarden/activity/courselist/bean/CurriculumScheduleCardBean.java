package com.psychiatrygarden.activity.courselist.bean;

import android.text.TextUtils;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.activity.courselist.bean.CurriculumLiveBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes5.dex */
public class CurriculumScheduleCardBean implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private List<DataDTO> data;

    @SerializedName("message")
    private String message;

    @SerializedName("server_time")
    private String serverTime;

    public static class DataDTO extends BaseExpandNode implements Serializable {

        @SerializedName("duration")
        private String duration;
        private boolean editMode;

        @SerializedName("is_free_watch")
        private String isFreeWatch;

        @SerializedName("notes")
        private List<BaseNode> notes;

        @SerializedName(DatabaseManager.SIZE)
        private String size;

        @SerializedName("title")
        private String title;

        @SerializedName("id")
        private String id = "0";

        @SerializedName("vid")
        private String vid = "";

        @SerializedName("children")
        private List<ChildrenDTO> children = new ArrayList();
        public int selected = 0;
        public int status = 0;
        public String live_status = "";
        public CurriculumLiveBean.DataDTO live = new CurriculumLiveBean.DataDTO();

        public static class ChildrenDTO extends BaseExpandNode implements Serializable {
            private String chapterTitle;

            @SerializedName("id")
            private String id;

            @SerializedName("is_free_watch")
            private String isFreeWatch;
            public int lastPlayback;
            private Object live;

            @SerializedName("notes")
            private List<BaseNode> notes;
            private int status;

            @SerializedName("title")
            private String title;

            @SerializedName("vid")
            private String vid = "";

            @SerializedName("duration")
            private String duration = "0";

            @SerializedName(DatabaseManager.SIZE)
            private String size = "0";

            @SerializedName("children")
            private List<ChildrenDTO> children = new ArrayList();
            private int selected = 0;
            private String pid = "";
            private int sort = 0;
            private String process = "0";
            private String see = "0";
            private String current_see = "0";
            private String video_type = "";
            private String teacher = "";

            public String getChapterTitle() {
                return this.chapterTitle;
            }

            @Override // com.chad.library.adapter.base.entity.node.BaseNode
            @Nullable
            public List<BaseNode> getChildNode() {
                return null;
            }

            public List<ChildrenDTO> getChildren() {
                return this.children;
            }

            public String getCurrent_see() {
                return this.current_see;
            }

            public String getDuration() {
                return this.duration;
            }

            public String getId() {
                return this.id;
            }

            public String getIsFreeWatch() {
                return this.isFreeWatch;
            }

            public int getLastPlayback() {
                return this.lastPlayback;
            }

            public CurriculumLiveBean.DataDTO getLive() {
                try {
                    if (this.live instanceof ArrayList) {
                        return new CurriculumLiveBean.DataDTO();
                    }
                    String json = new Gson().toJson(this.live);
                    if (TextUtils.isEmpty(json)) {
                        return new CurriculumLiveBean.DataDTO();
                    }
                    CurriculumLiveBean.DataDTO dataDTO = (CurriculumLiveBean.DataDTO) new Gson().fromJson(json, CurriculumLiveBean.DataDTO.class);
                    return dataDTO != null ? dataDTO : new CurriculumLiveBean.DataDTO();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return new CurriculumLiveBean.DataDTO();
                }
            }

            public List<BaseNode> getNotes() {
                return this.notes;
            }

            public String getPid() {
                return this.pid;
            }

            public String getProcess() {
                return this.process;
            }

            public String getSee() {
                return this.see;
            }

            public int getSelected() {
                return this.selected;
            }

            public String getSize() {
                return this.size;
            }

            public int getSort() {
                return this.sort;
            }

            public int getStatus() {
                return this.status;
            }

            public String getTeacher() {
                return this.teacher;
            }

            public String getTitle() {
                return this.title;
            }

            public String getVid() {
                return this.vid;
            }

            public String getVideo_type() {
                return this.video_type;
            }

            public void setChapterTitle(String chapterTitle) {
                this.chapterTitle = chapterTitle;
            }

            public void setChildren(List<ChildrenDTO> children) {
                this.children = children;
            }

            public void setCurrent_see(String current_see) {
                this.current_see = current_see;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setIsFreeWatch(String isFreeWatch) {
                this.isFreeWatch = isFreeWatch;
            }

            public void setLastPlayback(int lastPlayback) {
                this.lastPlayback = lastPlayback;
            }

            public void setNotes(List<BaseNode> notes) {
                this.notes = notes;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public void setProcess(String process) {
                this.process = process;
            }

            public void setSee(String see) {
                this.see = see;
            }

            public void setSelected(int selected) {
                this.selected = selected;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setTeacher(String teacher) {
                this.teacher = teacher;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public void setVideo_type(String video_type) {
                this.video_type = video_type;
            }
        }

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

        public List<ChildrenDTO> getChildren() {
            return this.children;
        }

        public String getDuration() {
            return this.duration;
        }

        public String getId() {
            return this.id;
        }

        public String getIsFreeWatch() {
            return this.isFreeWatch;
        }

        public CurriculumLiveBean.DataDTO getLive() {
            return this.live;
        }

        public String getLive_status() {
            return this.live_status;
        }

        public List<BaseNode> getNotes() {
            return this.notes;
        }

        public int getSelected() {
            return this.selected;
        }

        public String getSize() {
            return this.size;
        }

        public int getStatus() {
            return this.status;
        }

        public String getTitle() {
            return this.title;
        }

        public String getVid() {
            return this.vid;
        }

        public boolean isEditMode() {
            return this.editMode;
        }

        public void setChildren(List<ChildrenDTO> children) {
            this.children = children;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setEditMode(boolean editMode) {
            this.editMode = editMode;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIsFreeWatch(String isFreeWatch) {
            this.isFreeWatch = isFreeWatch;
        }

        public void setLive(CurriculumLiveBean.DataDTO live) {
            this.live = live;
        }

        public void setLive_status(String live_status) {
            this.live_status = live_status;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataDTO> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServerTime() {
        return this.serverTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
