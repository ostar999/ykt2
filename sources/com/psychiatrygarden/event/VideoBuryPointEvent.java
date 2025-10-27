package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class VideoBuryPointEvent {
    private String course_id;
    private String free_watch;
    private String is_permission;
    private String obj_id;
    private String obj_type;
    private boolean quitPlay;
    private String type;

    public VideoBuryPointEvent(String course_id, String type, String obj_type, String obj_id, String is_permission, String free_watch, boolean quitPlay) {
        this.course_id = course_id;
        this.type = type;
        this.obj_type = obj_type;
        this.obj_id = obj_id;
        this.is_permission = is_permission;
        this.free_watch = free_watch;
        this.quitPlay = quitPlay;
    }

    public String getCourse_id() {
        return this.course_id;
    }

    public String getFree_watch() {
        return this.free_watch;
    }

    public String getIs_permission() {
        return this.is_permission;
    }

    public String getObj_id() {
        return this.obj_id;
    }

    public String getObj_type() {
        return this.obj_type;
    }

    public String getType() {
        return this.type;
    }

    public boolean isQuitPlay() {
        return this.quitPlay;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setFree_watch(String free_watch) {
        this.free_watch = free_watch;
    }

    public void setIs_permission(String is_permission) {
        this.is_permission = is_permission;
    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }

    public void setObj_type(String obj_type) {
        this.obj_type = obj_type;
    }

    public void setQuitPlay(boolean quitPlay) {
        this.quitPlay = quitPlay;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VideoBuryPointEvent(String course_id, String type, String obj_type, String obj_id) {
        this.course_id = course_id;
        this.type = type;
        this.obj_type = obj_type;
        this.obj_id = obj_id;
    }
}
