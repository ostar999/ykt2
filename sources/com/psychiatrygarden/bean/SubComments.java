package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.CommentBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class SubComments {
    private List<CommentBean.DataBean.HotBean.ReplyBean> list;

    public SubComments(List<CommentBean.DataBean.HotBean.ReplyBean> cmts) {
        ArrayList arrayList = new ArrayList();
        this.list = arrayList;
        if (cmts == null) {
            this.list = new ArrayList();
            return;
        }
        if (arrayList.size() > 0) {
            this.list.clear();
        }
        this.list = cmts;
    }

    public CommentBean.DataBean.HotBean.ReplyBean get(int index) {
        return this.list.get(index);
    }

    public String getFloorNum() {
        return this.list.get(r0.size() - 1).getFloor_num();
    }

    public Iterator<CommentBean.DataBean.HotBean.ReplyBean> iterator() {
        List<CommentBean.DataBean.HotBean.ReplyBean> list = this.list;
        if (list == null) {
            return null;
        }
        return list.iterator();
    }

    public int size() {
        List<CommentBean.DataBean.HotBean.ReplyBean> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
