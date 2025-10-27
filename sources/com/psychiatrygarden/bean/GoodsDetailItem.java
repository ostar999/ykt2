package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import java.util.List;

/* loaded from: classes5.dex */
public class GoodsDetailItem implements MultiItemEntity {
    public static final int TYPE_COURSE_COMMENT = 6;
    public static final int TYPE_COURSE_DIRECTORY = 4;
    public static final int TYPE_COURSE_OR_GOODS_DETAIL = 3;
    public static final int TYPE_COURSE_STRUCTURE = 5;
    public static final int TYPE_GOODS_COMMENT = 7;
    public static final int TYPE_GOODS_COMMON_PROBLEM = 8;
    public static final int TYPE_TEACHER_DETAIL = 2;
    public static final int TYPE_TOP_DETAIL = 1;
    private CourseDetailBean courseData;
    private GoodsBean.DataBean data;
    private String extraData;
    private String extraText;
    private String goodsId;
    private List<String> indicatorStrList;
    private int showType;
    private String title;

    public GoodsDetailItem(String goodsId, String title, int showType, GoodsBean.DataBean data) {
        this.goodsId = goodsId;
        this.title = title;
        this.showType = showType;
        this.data = data;
    }

    public CourseDetailBean getCourseData() {
        return this.courseData;
    }

    public GoodsBean.DataBean getData() {
        return this.data;
    }

    public String getExtraData() {
        return this.extraData;
    }

    public String getExtraText() {
        return this.extraText;
    }

    public String getGoodsId() {
        return this.goodsId;
    }

    public List<String> getIndicatorStrList() {
        return this.indicatorStrList;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.showType;
    }

    public int getShowType() {
        return this.showType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setData(GoodsBean.DataBean data) {
        this.data = data;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setIndicatorStrList(List<String> indicatorStrList) {
        this.indicatorStrList = indicatorStrList;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GoodsDetailItem(String goodsId, String title, int showType, CourseDetailBean data) {
        this.goodsId = goodsId;
        this.title = title;
        this.showType = showType;
        this.courseData = data;
    }
}
