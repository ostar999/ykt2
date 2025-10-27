package com.psychiatrygarden.activity.online.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.SpringProgressView;
import com.yikaobang.yixue.R;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class QuestionBankNewProvider extends BaseNodeProvider {
    private QuestionBankNewAdapter.JumpToQList mJumpToQList;

    public QuestionBankNewProvider(QuestionBankNewAdapter.JumpToQList mJumpToQList) {
        this.mJumpToQList = mJumpToQList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(QuestionBankNewBean.DataBean dataBean, BaseViewHolder baseViewHolder, View view) {
        if (1 == dataBean.getIsSelected()) {
            dataBean.setIsSelected(0);
            for (int i2 = 0; i2 < dataBean.getChildren().size(); i2++) {
                dataBean.getChildren().get(i2).setIsSelected(0);
            }
        } else {
            dataBean.setIsSelected(1);
            for (int i3 = 0; i3 < dataBean.getChildren().size(); i3++) {
                dataBean.getChildren().get(i3).setIsSelected(1);
            }
        }
        getAdapter2().notifyDataSetChanged();
        this.mJumpToQList.mGroupSelectClumn(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r8v10, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
    /* JADX WARN: Type inference failed for: r8v8, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
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
            this.mJumpToQList.mJumpToQList(dataBean.primary_id + "", "", dataBean.getTitle(), "", baseNode);
            return;
        }
        this.mJumpToQList.getExCo();
        int bindingAdapterPosition = baseViewHolder.getBindingAdapterPosition() - getAdapter2().getHeaderLayoutCount();
        if (bindingAdapterPosition < 0) {
            return;
        }
        if (((QuestionBankNewFristBean) baseNode).getIsExpanded()) {
            getAdapter2().collapse(bindingAdapterPosition, false);
            changePData(textView, imageView, R.mipmap.arrow_question_expand, R.mipmap.arrow_question_expand_night);
        } else {
            getAdapter2().expandAndCollapseOther(bindingAdapterPosition);
            changePData(textView, imageView, R.mipmap.arrow_question_collapse, R.mipmap.arrow_question_collapse_night);
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
        return R.layout.item_question_groups;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull final BaseViewHolder helper, @NotNull final BaseNode data) {
        int i2;
        ImageView imageView;
        int i3;
        QuestionBankNewFristBean questionBankNewFristBean = (QuestionBankNewFristBean) data;
        final QuestionBankNewBean.DataBean dataBean = questionBankNewFristBean.getDataBean();
        final TextView textView = (TextView) helper.getView(R.id.main_groups_tv_name);
        TextView textView2 = (TextView) helper.getView(R.id.gnumTv);
        TextView textView3 = (TextView) helper.getView(R.id.main_groups_tv_num);
        final ImageView imageView2 = (ImageView) helper.getView(R.id.iv_question_arrow);
        ImageView imageView3 = (ImageView) helper.getView(R.id.iv_arrow);
        textView3.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this.context) == 0 ? "#555555" : "#575F79"));
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.llay_progress_view);
        ImageView imageView4 = (ImageView) helper.getView(R.id.iv_select);
        SpringProgressView springProgressView = (SpringProgressView) helper.getView(R.id.spring_progress_view);
        ImageView imageView5 = (ImageView) helper.getView(R.id.img_continue);
        textView.getPaint().setFakeBoldText(true);
        if (dataBean.getCount() <= 0) {
            imageView3.setVisibility(8);
            linearLayout.setVisibility(8);
            textView2.setVisibility(8);
            imageView4.setVisibility(8);
            textView.setCompoundDrawables(null, null, null, null);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                textView.setTextColor(this.context.getResources().getColor(R.color.gray_font_new2));
            } else {
                textView.setTextColor(this.context.getResources().getColor(R.color.font_com_night));
            }
        }
        if ("1".equals(dataBean.getPass())) {
            imageView3.setVisibility(8);
            if ("all".equals(dataBean.getType())) {
                linearLayout.setVisibility(0);
                textView2.setVisibility(8);
                imageView4.setVisibility(8);
                textView3.setText(String.format(Locale.CHINA, "%d/%d", Integer.valueOf(dataBean.getRight_count() + dataBean.getError_count()), Integer.valueOf(dataBean.getCount())));
                springProgressView.setMaxErrRightCount(dataBean.getCount(), dataBean.getError_count(), dataBean.getRight_count());
                springProgressView.invalidate();
                if (TextUtils.isEmpty(UserConfig.getUserId())) {
                    imageView3.setVisibility(0);
                    changePData(textView, imageView3, R.drawable.homepage_question_password, R.drawable.homepage_question_password_night);
                }
            } else {
                if (dataBean.getType().contains("export_")) {
                    linearLayout.setVisibility(8);
                    textView2.setVisibility(0);
                    imageView4.setVisibility(0);
                    imageView4.setSelected(1 == dataBean.getIsSelected());
                    imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.g
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13111c.lambda$convert$0(dataBean, helper, view);
                        }
                    });
                } else {
                    linearLayout.setVisibility(8);
                    textView2.setVisibility(0);
                    imageView4.setVisibility(8);
                }
                textView2.setText(String.format(getFormatStr(dataBean.getType()), dataBean.getCount() + ""));
            }
        } else {
            if ("all".equals(dataBean.getType())) {
                linearLayout.setVisibility(0);
                textView3.setText(String.format(Locale.CHINA, "%d/%d", Integer.valueOf(dataBean.getRight_count() + dataBean.getError_count()), Integer.valueOf(dataBean.getCount())));
                springProgressView.setMaxErrRightCount(dataBean.getCount(), dataBean.getError_count(), dataBean.getRight_count());
                i2 = 8;
            } else {
                i2 = 8;
                linearLayout.setVisibility(8);
            }
            imageView3.setVisibility(0);
            textView2.setVisibility(i2);
            imageView4.setVisibility(i2);
            changePData(textView, imageView3, R.drawable.homepage_question_password, R.drawable.homepage_question_password_night);
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13114c.lambda$convert$1(dataBean, helper, data, textView, imageView2, view);
            }
        });
        if (dataBean.getChildren() == null || dataBean.getChildren().size() <= 0) {
            imageView = imageView5;
            if (UserConfig.isLogin() && dataBean.isShowContinue()) {
                imageView.setVisibility(0);
                i3 = 8;
            } else {
                i3 = 8;
                imageView.setVisibility(8);
            }
            imageView2.setVisibility(i3);
            changePData(textView, imageView2, R.mipmap.arrow_question_right, R.mipmap.arrow_question_right_night);
        } else {
            imageView2.setVisibility(0);
            if (questionBankNewFristBean.getIsExpanded()) {
                imageView = imageView5;
                imageView.setVisibility(8);
                changePData(textView, imageView2, R.mipmap.arrow_question_expand, R.mipmap.arrow_question_expand_night);
            } else {
                imageView = imageView5;
                if (UserConfig.isLogin() && dataBean.isShowContinue()) {
                    imageView.setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                }
                changePData(textView, imageView2, R.mipmap.arrow_question_collapse, R.mipmap.arrow_question_collapse_night);
            }
        }
        textView.setText(String.format("%s", dataBean.getTitle()));
        if (imageView.getVisibility() == 0) {
            textView.setMaxWidth(UIUtil.getScreenWidth(this.context) - ScreenUtil.getPxByDp(this.context, 165));
        }
    }
}
