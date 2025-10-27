package com.psychiatrygarden.activity.mine.cutquestion.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.mine.cutquestion.adapter.ChooseChapterAdpNew;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class ChooseChapterChildProviderNew extends BaseNodeProvider {
    private boolean childCanSelect = true;
    private boolean mIsShowCount;
    private ChooseChapterAdpNew.JumpToQList mJumpToQList;

    public ChooseChapterChildProviderNew(ChooseChapterAdpNew.JumpToQList mJumpToQList, boolean isShowCount) {
        this.mJumpToQList = mJumpToQList;
        this.mIsShowCount = isShowCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(QuestionBankNewBean.DataBean.ChildrenBean childrenBean, BaseViewHolder baseViewHolder, View view) {
        if (this.childCanSelect) {
            if (1 == childrenBean.getIsSelected()) {
                childrenBean.setIsSelected(0);
            } else {
                childrenBean.setIsSelected(1);
            }
            this.mJumpToQList.mChildSelectClumn(childrenBean.getGroupPosition(), BaseViewHolderUtilKt.getCustomerAdapterPosition(baseViewHolder) - 1);
            getAdapter2().notifyDataSetChanged();
        }
    }

    public void changeTextColor(TextView tv_Name, int colorDay, int colorNight) {
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            tv_Name.setTextColor(ContextCompat.getColor(this.context, colorDay));
        } else {
            tv_Name.setTextColor(ContextCompat.getColor(this.context, colorNight));
        }
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 2;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_choose_chapter_new;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
    }

    public void setChildCanSelect(boolean childCanSelect) {
        this.childCanSelect = childCanSelect;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull final BaseViewHolder helper, @NotNull BaseNode baseNode) {
        final QuestionBankNewBean.DataBean.ChildrenBean childrenBean = ((QuestionBankNewSecondBean) baseNode).getChildrenBean();
        TextView textView = (TextView) helper.getView(R.id.main_groups_tv_name);
        ImageView imageView = (ImageView) helper.getView(R.id.iv_arrow);
        ImageView imageView2 = (ImageView) helper.getView(R.id.img_down);
        ImageView imageView3 = (ImageView) helper.getView(R.id.iv_select);
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.ly_item);
        imageView.setVisibility(8);
        imageView2.setVisibility(4);
        TextView textView2 = (TextView) helper.getView(R.id.tv_count);
        if (this.mIsShowCount) {
            textView2.setVisibility(0);
            textView2.setText(childrenBean.getCount() + "题");
        } else {
            textView2.setVisibility(8);
        }
        boolean z2 = SkinManager.getCurrentSkinType(this.context) == 1;
        imageView3.setSelected(1 == childrenBean.getIsSelected());
        if (1 == childrenBean.getIsSelected()) {
            imageView3.setImageResource(z2 ? R.drawable.download_new_select_night : R.drawable.download_new_select_day);
        } else {
            imageView3.setImageResource(z2 ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select);
        }
        imageView3.setVisibility(this.childCanSelect ? 0 : 8);
        textView.setText(childrenBean.getTitle());
        changeTextColor(textView, R.color.question_color, R.color.question_color_night);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: l1.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27662c.lambda$convert$0(childrenBean, helper, view);
            }
        });
    }
}
