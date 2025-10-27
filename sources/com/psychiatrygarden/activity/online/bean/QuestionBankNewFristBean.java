package com.psychiatrygarden.activity.online.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes5.dex */
public class QuestionBankNewFristBean extends BaseExpandNode {
    public List<BaseNode> childNode;
    private QuestionBankNewBean.DataBean dataBean;

    public QuestionBankNewFristBean(List<BaseNode> childNode, QuestionBankNewBean.DataBean dataBean) {
        this.childNode = childNode;
        this.dataBean = dataBean;
        setExpanded(false);
    }

    @Override // com.chad.library.adapter.base.entity.node.BaseNode
    @Nullable
    public List<BaseNode> getChildNode() {
        return this.childNode;
    }

    public QuestionBankNewBean.DataBean getDataBean() {
        return this.dataBean;
    }
}
