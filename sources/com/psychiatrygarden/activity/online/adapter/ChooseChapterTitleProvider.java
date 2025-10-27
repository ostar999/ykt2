package com.psychiatrygarden.activity.online.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.online.adapter.ChooseChapterAdp;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class ChooseChapterTitleProvider extends BaseNodeProvider {
    private boolean mIsShowCount;
    private ChooseChapterAdp.JumpToQList mJumpToQList;

    public ChooseChapterTitleProvider(ChooseChapterAdp.JumpToQList mJumpToQList, boolean isShowCount) {
        this.mJumpToQList = mJumpToQList;
        this.mIsShowCount = isShowCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(QuestionBankNewBean.DataBean dataBean, BaseViewHolder baseViewHolder, View view) {
        if (1 == dataBean.getIsSelected()) {
            dataBean.setIsSelected(0);
            for (int i2 = 0; i2 < dataBean.getChildren().size(); i2++) {
                QuestionBankNewBean.DataBean.ChildrenBean childrenBean = dataBean.getChildren().get(i2);
                childrenBean.setIsSelected(0);
                if (dataBean.getChildIds().contains(childrenBean.getChapter_id())) {
                    dataBean.childIds.remove(childrenBean.getChapter_id());
                    dataBean.setChildSelectCount(dataBean.childSelectCount - childrenBean.getCount());
                }
            }
        } else {
            dataBean.setIsSelected(1);
            for (int i3 = 0; i3 < dataBean.getChildren().size(); i3++) {
                QuestionBankNewBean.DataBean.ChildrenBean childrenBean2 = dataBean.getChildren().get(i3);
                childrenBean2.setIsSelected(1);
                if (!dataBean.getChildIds().contains(childrenBean2.getChapter_id())) {
                    dataBean.childIds.add(childrenBean2.getChapter_id());
                    dataBean.setChildSelectCount(dataBean.childSelectCount + childrenBean2.getCount());
                }
            }
        }
        getAdapter2().notifyDataSetChanged();
        this.mJumpToQList.mGroupSelectClumn(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r3v10, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
    public /* synthetic */ void lambda$convert$1(QuestionBankNewBean.DataBean dataBean, BaseViewHolder baseViewHolder, BaseNode baseNode, TextView textView, ImageView imageView, View view) {
        if ("0".equals(dataBean.getPass()) || UserConfig.getUserId().equals("")) {
            this.mJumpToQList.getPassData(dataBean.getActivity_id() + "");
            return;
        }
        if (dataBean.getChildren() == null || dataBean.getChildren().size() <= 0) {
            if (dataBean.getCount() <= 0) {
                ToastUtil.shortToast(this.context, "此学科下无题！");
                return;
            }
            this.mJumpToQList.mJumpToQList(dataBean.primary_id + "", "", dataBean.getTitle(), "");
            return;
        }
        this.mJumpToQList.getExCo();
        int bindingAdapterPosition = baseViewHolder.getBindingAdapterPosition() - getAdapter2().getHeaderLayoutCount();
        if (bindingAdapterPosition < 0) {
            return;
        }
        if (((QuestionBankNewFristBean) baseNode).getIsExpanded()) {
            getAdapter2().collapse(bindingAdapterPosition, false);
            changePData(textView, imageView, R.mipmap.ic_question_combine_collapse_day, R.mipmap.ic_question_combine_collapse_night);
        } else {
            getAdapter2().expandAndCollapseOther(bindingAdapterPosition);
            changePData(textView, imageView, R.mipmap.ic_choose_chapter_expand_day, R.mipmap.ic_choose_chapter_expand_night);
        }
    }

    public void changePData(TextView tv_Name, ImageView iv_arrow, int sunDraw, int blackDraw) {
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            iv_arrow.setImageResource(sunDraw);
            tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color));
        } else {
            iv_arrow.setImageResource(blackDraw);
            tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color_night));
        }
    }

    public String getFormatStr(String type) {
        if (type.contains("export_")) {
            type = type.split(StrPool.UNDERLINE)[1];
        }
        return "error".equals(type) ? "错%s题" : "collection".equals(type) ? "收藏%s题" : "note".equals(type) ? "%s条笔记" : "praise".equals(type) ? "%s条点赞" : ClientCookie.COMMENT_ATTR.equals(type) ? "%s条评论" : "%s";
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 1;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_choose_chapter;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull final BaseViewHolder helper, @NotNull final BaseNode data) {
        QuestionBankNewFristBean questionBankNewFristBean = (QuestionBankNewFristBean) data;
        final QuestionBankNewBean.DataBean dataBean = questionBankNewFristBean.getDataBean();
        final TextView textView = (TextView) helper.getView(R.id.main_groups_tv_name);
        final ImageView imageView = (ImageView) helper.getView(R.id.img_down);
        ImageView imageView2 = (ImageView) helper.getView(R.id.iv_arrow);
        ImageView imageView3 = (ImageView) helper.getView(R.id.iv_select);
        ImageView imageView4 = (ImageView) helper.getView(R.id.iv_select_half);
        TextView textView2 = (TextView) helper.getView(R.id.tv_count);
        if (this.mIsShowCount) {
            dataBean.setPass("1");
            textView2.setVisibility(0);
            textView2.setText(dataBean.getCount() + "题");
        } else {
            textView2.setVisibility(8);
        }
        textView.getPaint().setFakeBoldText(true);
        if (dataBean.getCount() <= 0) {
            imageView2.setVisibility(8);
            textView.setCompoundDrawables(null, null, null, null);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                textView.setTextColor(this.context.getResources().getColor(R.color.gray_font_new2));
            } else {
                textView.setTextColor(this.context.getResources().getColor(R.color.font_com_night));
            }
        }
        if (!"1".equals(dataBean.getPass())) {
            imageView2.setVisibility(0);
            imageView3.setVisibility(8);
            changePData(textView, imageView2, R.drawable.homepage_question_password, R.drawable.homepage_question_password_night);
        } else if (UserConfig.isLogin()) {
            imageView3.setVisibility(0);
            imageView2.setVisibility(8);
            if (dataBean.getIsSelected() == 1) {
                imageView3.setVisibility(0);
                imageView4.setVisibility(8);
                imageView3.setSelected(true);
            } else if (dataBean.getIsSelected() == 2) {
                imageView3.setVisibility(8);
                imageView4.setVisibility(0);
                imageView4.setImageResource(SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_choose_chapter_half_night : R.drawable.ic_choose_chapter_half);
            } else {
                imageView3.setVisibility(0);
                imageView4.setVisibility(8);
                imageView3.setSelected(false);
            }
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13095c.lambda$convert$0(dataBean, helper, view);
                }
            });
        } else {
            imageView3.setVisibility(8);
            imageView2.setVisibility(0);
            changePData(textView, imageView2, R.drawable.homepage_question_password, R.drawable.homepage_question_password_night);
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13098c.lambda$convert$1(dataBean, helper, data, textView, imageView, view);
            }
        });
        if (dataBean.getChildren() == null || dataBean.getChildren().size() <= 0) {
            imageView.setVisibility(8);
            changePData(textView, imageView, R.mipmap.arrow_question_right, R.mipmap.arrow_question_right_night);
        } else {
            imageView.setVisibility(0);
            if (questionBankNewFristBean.getIsExpanded()) {
                changePData(textView, imageView, R.mipmap.ic_question_combine_collapse_day, R.mipmap.ic_question_combine_collapse_night);
            } else {
                changePData(textView, imageView, R.mipmap.ic_choose_chapter_expand_day, R.mipmap.ic_choose_chapter_expand_night);
            }
        }
        textView.setText(String.format("%s", dataBean.getTitle()));
    }
}
