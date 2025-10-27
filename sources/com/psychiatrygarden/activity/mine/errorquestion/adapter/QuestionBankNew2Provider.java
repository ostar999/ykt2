package com.psychiatrygarden.activity.mine.errorquestion.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.SpringProgressView;
import com.yikaobang.yixue.R;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class QuestionBankNew2Provider extends BaseNodeProvider {
    private QuestionBankNew2Adapter.JumpToQList mJumpToQList;

    public QuestionBankNew2Provider(QuestionBankNew2Adapter.JumpToQList mJumpToQList) {
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
        this.mJumpToQList.mGroupSelectClumn(BaseViewHolderUtilKt.getCustomerLayoutPosition(baseViewHolder));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r8v8, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
    /* JADX WARN: Type inference failed for: r8v9, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
    public /* synthetic */ void lambda$convert$1(QuestionBankNewBean.DataBean dataBean, BaseViewHolder baseViewHolder, BaseNode baseNode, View view) {
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
        int customerBindAdapterPosition = BaseViewHolderUtilKt.getCustomerBindAdapterPosition(baseViewHolder) - getAdapter2().getHeaderLayoutCount();
        if (customerBindAdapterPosition < 0) {
            return;
        }
        if (((QuestionBankNewFristBean) baseNode).getIsExpanded()) {
            getAdapter2().collapse(customerBindAdapterPosition, false);
        } else {
            getAdapter2().expandAndCollapseOther(customerBindAdapterPosition);
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

    public void changePDataExport(ImageView iv_arrow_export, boolean isExpand) {
        if (isExpand) {
            iv_arrow_export.setImageResource(R.drawable.icon_top_arrow_main_theme_color);
        } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
            iv_arrow_export.setImageResource(R.drawable.icon_bottom_arrow_day);
        } else {
            iv_arrow_export.setImageResource(R.drawable.icon_bottom_arrow_night);
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
        return R.layout.item_question_groups_new;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull final BaseViewHolder helper, @NotNull final BaseNode data) {
        int i2;
        final ImageView imageView;
        int i3;
        QuestionBankNewFristBean questionBankNewFristBean = (QuestionBankNewFristBean) data;
        final QuestionBankNewBean.DataBean dataBean = questionBankNewFristBean.getDataBean();
        final TextView textView = (TextView) helper.getView(R.id.main_groups_tv_name);
        final RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.ly_title);
        TextView textView2 = (TextView) helper.getView(R.id.gnumTv);
        TextView textView3 = (TextView) helper.getView(R.id.main_groups_tv_num);
        ImageView imageView2 = (ImageView) helper.getView(R.id.iv_arrow);
        ImageView imageView3 = (ImageView) helper.getView(R.id.iv_arrow_export);
        textView3.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this.context) == 0 ? "#555555" : "#575F79"));
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.llay_progress_view);
        ImageView imageView4 = (ImageView) helper.getView(R.id.iv_select);
        SpringProgressView springProgressView = (SpringProgressView) helper.getView(R.id.spring_progress_view);
        ImageView imageView5 = (ImageView) helper.getView(R.id.img_continue);
        textView.getPaint().setFakeBoldText(true);
        if (dataBean.getCount() <= 0) {
            imageView2.setVisibility(8);
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
            imageView2.setVisibility(8);
            if ("all".equals(dataBean.getType())) {
                linearLayout.setVisibility(0);
                textView2.setVisibility(8);
                imageView4.setVisibility(8);
                textView3.setText(String.format(Locale.CHINA, "%d/%d", Integer.valueOf(dataBean.getRight_count() + dataBean.getError_count()), Integer.valueOf(dataBean.getCount())));
                springProgressView.setMaxErrRightCount(dataBean.getCount(), dataBean.getError_count(), dataBean.getRight_count());
                springProgressView.invalidate();
                if (TextUtils.isEmpty(UserConfig.getUserId())) {
                    imageView2.setVisibility(0);
                    changePData(textView, imageView2, R.drawable.homepage_question_password, R.drawable.homepage_question_password_night);
                }
            } else {
                if (dataBean.getType().contains("export_")) {
                    linearLayout.setVisibility(8);
                    textView2.setVisibility(0);
                    imageView4.setVisibility(0);
                    imageView3.setVisibility(0);
                    if (dataBean.getChildren() == null || dataBean.getChildren().isEmpty()) {
                        i3 = 8;
                        imageView3.setVisibility(8);
                    } else {
                        i3 = 8;
                    }
                    imageView2.setVisibility(i3);
                    changePDataExport(imageView3, questionBankNewFristBean.getIsExpanded());
                    boolean z2 = SkinManager.getCurrentSkinType(this.context) == 1;
                    if (dataBean.getIsSelected() == 0) {
                        imageView4.setImageResource(z2 ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select);
                    } else if (dataBean.getIsSelected() == 1) {
                        imageView4.setImageResource(z2 ? R.drawable.download_new_select_night : R.drawable.download_new_select_day);
                    } else if (dataBean.getIsSelected() == 2) {
                        imageView4.setImageResource(z2 ? R.drawable.download_part_select_night : R.drawable.download_part_select);
                    }
                    imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.adapter.a
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f12836c.lambda$convert$0(dataBean, helper, view);
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
            imageView2.setVisibility(0);
            textView2.setVisibility(i2);
            imageView4.setVisibility(i2);
            changePData(textView, imageView2, R.drawable.homepage_question_password, R.drawable.homepage_question_password_night);
        }
        BaseViewHolderUtilKt.getCustomerItemView(helper).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.adapter.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12839c.lambda$convert$1(dataBean, helper, data, view);
            }
        });
        if (dataBean.getChildren() == null || dataBean.getChildren().size() <= 0) {
            imageView = imageView5;
            if (UserConfig.isLogin() && dataBean.isShowContinue()) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        } else if (questionBankNewFristBean.getIsExpanded()) {
            imageView = imageView5;
            imageView.setVisibility(8);
        } else {
            imageView = imageView5;
            if (UserConfig.isLogin() && dataBean.isShowContinue()) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
        textView.setText(String.format("%s", dataBean.getTitle()));
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Provider.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int width = relativeLayout.getWidth();
                if (imageView.getVisibility() == 0) {
                    textView.setMaxWidth(width - ScreenUtil.getPxByDp(QuestionBankNew2Provider.this.getContext(), 38));
                } else {
                    textView.setMaxWidth(width);
                }
                relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
