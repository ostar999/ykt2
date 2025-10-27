package com.psychiatrygarden.activity.courselist.bean;

import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseEventBean implements Serializable {
    public String evestr;
    public List<CourseCalalogueBean.DataNewBean.DataBean> videoDownBeans;

    public CourseEventBean(List<CourseCalalogueBean.DataNewBean.DataBean> videoDownBeans, String evestr) {
        this.videoDownBeans = videoDownBeans;
        this.evestr = evestr;
    }
}
