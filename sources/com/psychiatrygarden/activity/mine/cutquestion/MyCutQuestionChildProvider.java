package com.psychiatrygarden.activity.mine.cutquestion;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAdp;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class MyCutQuestionChildProvider extends BaseNodeProvider {
    private MyCutQuestionAdp.JumpToQList mJumpToQList;

    public MyCutQuestionChildProvider(MyCutQuestionAdp.JumpToQList mJumpToQList) {
        this.mJumpToQList = mJumpToQList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(QuestionBankNewBean.DataBean.ChildrenBean childrenBean, View view) {
        this.mJumpToQList.mJumpToQList(childrenBean.getPrimary_id() + "", "", childrenBean.getParent_title(), childrenBean.getTitle());
    }

    public void changeTextColor(TextView tv_Name, ImageView mImgDown, int colorDay, int colorNight) {
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            mImgDown.setImageResource(R.mipmap.arrow_question_right);
            tv_Name.setTextColor(ContextCompat.getColor(this.context, colorDay));
        } else {
            mImgDown.setImageResource(R.mipmap.arrow_question_right_night);
            tv_Name.setTextColor(ContextCompat.getColor(this.context, colorNight));
        }
        mImgDown.setVisibility(8);
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 2;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_choose_chapter;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode baseNode) {
        final QuestionBankNewBean.DataBean.ChildrenBean childrenBean = ((QuestionBankNewSecondBean) baseNode).getChildrenBean();
        TextView textView = (TextView) helper.getView(R.id.main_groups_tv_name);
        ImageView imageView = (ImageView) helper.getView(R.id.iv_arrow);
        ImageView imageView2 = (ImageView) helper.getView(R.id.img_down);
        ImageView imageView3 = (ImageView) helper.getView(R.id.iv_select);
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.ly_item);
        TextView textView2 = (TextView) helper.getView(R.id.tv_count);
        imageView.setVisibility(8);
        imageView2.setVisibility(8);
        textView2.setVisibility(0);
        textView2.setText(childrenBean.getCount() + "题");
        imageView3.setSelected(1 == childrenBean.getIsSelected());
        textView.setText(childrenBean.getTitle());
        changeTextColor(textView, imageView3, R.color.question_color, R.color.question_color_night);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12814c.lambda$convert$0(childrenBean, view);
            }
        });
    }
}
