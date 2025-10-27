package com.psychiatrygarden.activity.online.bean;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes5.dex */
public class QuestionBankNewSecondBean extends BaseNode {
    private QuestionBankNewBean.DataBean.ChildrenBean childrenBean;

    public QuestionBankNewSecondBean(QuestionBankNewBean.DataBean.ChildrenBean childrenBean) {
        this.childrenBean = childrenBean;
    }

    @Override // com.chad.library.adapter.base.entity.node.BaseNode
    @Nullable
    public List<BaseNode> getChildNode() {
        return null;
    }

    public QuestionBankNewBean.DataBean.ChildrenBean getChildrenBean() {
        return this.childrenBean;
    }
}
