package com.psychiatrygarden.activity.mine.cutquestion;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class MyCutQuestionAdp extends BaseNodeAdapter {

    public interface JumpToQList {
        void getExCo();

        void getPassData(String activity_id);

        void mChildSelectClumn(int groupPosition, int childPosition);

        void mGroupSelectClumn(int groupPosition);

        void mJumpToQList(String primary_id, String unit, String parent_title, String title);
    }

    public MyCutQuestionAdp(JumpToQList mJumpToQList) {
        addNodeProvider(new MyCutQuestionTitleProvider(mJumpToQList));
        addNodeProvider(new MyCutQuestionChildProvider(mJumpToQList));
    }

    @Override // com.chad.library.adapter.base.BaseProviderMultiAdapter
    public int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode baseNode = data.get(position);
        if (baseNode instanceof QuestionBankNewFristBean) {
            return 1;
        }
        return baseNode instanceof QuestionBankNewSecondBean ? 2 : -1;
    }
}
