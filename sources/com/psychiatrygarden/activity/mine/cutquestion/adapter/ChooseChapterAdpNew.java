package com.psychiatrygarden.activity.mine.cutquestion.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class ChooseChapterAdpNew extends BaseNodeAdapter {

    public interface JumpToQList {
        void getExCo();

        void getPassData(String activity_id);

        void mChildSelectClumn(int groupPosition, int childPosition);

        void mGroupSelectClumn(int groupPosition);

        void mJumpToQList(String primary_id, String unit, String parent_title, String title);
    }

    public ChooseChapterAdpNew(JumpToQList mJumpToQList, boolean isShowCount, boolean childCanSelect) {
        addNodeProvider(new ChooseChapterTitleProviderNew(mJumpToQList, isShowCount));
        ChooseChapterChildProviderNew chooseChapterChildProviderNew = new ChooseChapterChildProviderNew(mJumpToQList, isShowCount);
        chooseChapterChildProviderNew.setChildCanSelect(childCanSelect);
        addNodeProvider(chooseChapterChildProviderNew);
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
