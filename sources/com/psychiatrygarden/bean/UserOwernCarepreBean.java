package com.psychiatrygarden.bean;

import androidx.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class UserOwernCarepreBean implements Comparable<UserOwernCarepreBean>, Serializable {
    public String obj_id;
    public int postion;

    public String getObj_id() {
        return this.obj_id;
    }

    public int getPostion() {
        return this.postion;
    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public int sort(UserOwernCarepreBean o2) {
        if (this.postion > o2.getPostion()) {
            return 1;
        }
        return this.postion < o2.getPostion() ? -1 : 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull UserOwernCarepreBean o2) {
        return sort(o2);
    }
}
