package com.psychiatrygarden.utils;

import android.view.View;
import com.psychiatrygarden.bean.CommentBean;

/* loaded from: classes6.dex */
public interface CommentListenter {
    void commListOppose(String moudle_type, String is_Oppose, String id, String obj_id);

    void commListPraise(String moudle_type, String is_praise, View v2, String id, String obj_id);

    void commListPraiseFaile();

    void commListReply(CommentBean.DataBean.HotBean.ReplyBean cmt, View v2);

    void commListSupport(String moudle_type, String is_Support, String id, String obj_id);

    void commentListenerData(CommentBean.DataBean.HotBean.ReplyBean commListBean, View v2);
}
