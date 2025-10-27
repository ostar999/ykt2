package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;

/* loaded from: classes5.dex */
public class SearchSchoolOrMajorBean implements MultiItemEntity {
    public static final int TYPE_MAJOR = 2;
    public static final int TYPE_SCHOOL = 1;
    private FollowSchoolBean.DataBean info;
    private int type = 1;

    public FollowSchoolBean.DataBean getInfo() {
        return this.info;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }

    public int getType() {
        return this.type;
    }

    public void setInfo(FollowSchoolBean.DataBean info) {
        this.info = info;
    }

    public void setType(int type) {
        this.type = type;
    }
}
