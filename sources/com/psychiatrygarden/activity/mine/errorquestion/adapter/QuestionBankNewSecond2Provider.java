package com.psychiatrygarden.activity.mine.errorquestion.adapter;

import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.C;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.NoQuestionByYearFilterDialogs;
import com.psychiatrygarden.widget.SpringProgressView;
import com.yikaobang.yixue.R;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class QuestionBankNewSecond2Provider extends BaseNodeProvider {
    private QuestionBankNew2Adapter.JumpToQList mJumpToQList;

    public QuestionBankNewSecond2Provider(QuestionBankNew2Adapter.JumpToQList mJumpToQList) {
        this.mJumpToQList = mJumpToQList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(QuestionBankNewBean.DataBean.ChildrenBean childrenBean, BaseViewHolder baseViewHolder, BaseNode baseNode, View view) {
        String str;
        if (childrenBean.getCount() <= 0) {
            if (!ProjectApp.isHaveFilter) {
                ToastUtil.shortToast(this.context, "此章节暂无试题！");
                return;
            }
            final NoQuestionByYearFilterDialogs noQuestionByYearFilterDialogs = new NoQuestionByYearFilterDialogs(this.context, childrenBean.getCategory());
            XPopup.setShadowBgColor(0);
            new XPopup.Builder(this.context).animationDuration(0).asCustom(noQuestionByYearFilterDialogs).show();
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.errorquestion.adapter.c
                @Override // java.lang.Runnable
                public final void run() {
                    noQuestionByYearFilterDialogs.dismiss();
                }
            }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            return;
        }
        if (childrenBean.getType().contains("export_")) {
            if (1 == childrenBean.getIsSelected()) {
                childrenBean.setIsSelected(0);
            } else {
                childrenBean.setIsSelected(1);
            }
            this.mJumpToQList.mChildSelectClumn(childrenBean.getGroupPosition(), BaseViewHolderUtilKt.getCustomerAdapterPosition(baseViewHolder) - 1);
            getAdapter2().notifyDataSetChanged();
            return;
        }
        boolean zEquals = "year".equals(childrenBean.getCategory());
        QuestionBankNew2Adapter.JumpToQList jumpToQList = this.mJumpToQList;
        String str2 = childrenBean.getPrimary_id() + "";
        if (zEquals) {
            str = childrenBean.getTitle() + "";
        } else {
            str = "";
        }
        jumpToQList.mJumpToQList(str2, str, childrenBean.getParent_title(), childrenBean.getTitle(), baseNode);
    }

    public String getFormatStr(String type) {
        if (type.contains("export_")) {
            type = type.split(StrPool.UNDERLINE)[1];
        }
        return "error".equals(type) ? "错%s题" : "collection".equals(type) ? "收藏%s题" : "note".equals(type) ? "%s条笔记" : "praise".equals(type) ? "%s条点赞" : ClientCookie.COMMENT_ATTR.equals(type) ? "%s条评论" : "%s";
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 2;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_question_child_new;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull final BaseViewHolder helper, final BaseNode baseNode) {
        int i2;
        final QuestionBankNewBean.DataBean.ChildrenBean childrenBean = ((QuestionBankNewSecondBean) baseNode).getChildrenBean();
        final RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.ly_title);
        TextView textView = (TextView) helper.getView(R.id.main_child_tv_num);
        final TextView textView2 = (TextView) helper.getView(R.id.main_child_tv_name);
        TextView textView3 = (TextView) helper.getView(R.id.cNumTv);
        SpringProgressView springProgressView = (SpringProgressView) helper.getView(R.id.spro_child);
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.lineview);
        ImageView imageView = (ImageView) helper.getView(R.id.iv_select);
        RelativeLayout relativeLayout2 = (RelativeLayout) helper.getView(R.id.relchildview);
        final ImageView imageView2 = (ImageView) helper.getView(R.id.img_continue);
        boolean z2 = SkinManager.getCurrentSkinType(this.context) == 1;
        if ("all".equals(childrenBean.getType())) {
            textView3.setVisibility(8);
            imageView.setVisibility(8);
            springProgressView.setMaxErrRightCount(childrenBean.getCount(), childrenBean.getError_count(), childrenBean.getRight_count());
            springProgressView.invalidate();
            textView.setText(String.format(Locale.CHINA, "%d/%d", Integer.valueOf(childrenBean.getError_count() + childrenBean.getRight_count()), Integer.valueOf(childrenBean.getCount())));
            if (childrenBean.getCount() <= 0) {
                linearLayout.setVisibility(8);
            } else {
                linearLayout.setVisibility(0);
            }
            i2 = 0;
        } else {
            if (childrenBean.getType().contains("export_")) {
                linearLayout.setVisibility(8);
                textView3.setVisibility(0);
                imageView.setVisibility(0);
                if (1 == childrenBean.getIsSelected()) {
                    imageView.setImageResource(z2 ? R.drawable.download_new_select_night : R.drawable.download_new_select_day);
                } else {
                    imageView.setImageResource(z2 ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select);
                }
            } else {
                linearLayout.setVisibility(8);
                imageView.setVisibility(8);
                textView3.setVisibility(0);
            }
            i2 = 0;
            textView3.setText(String.format(getFormatStr(childrenBean.getType()), childrenBean.getCount() + ""));
        }
        textView2.setText(childrenBean.getTitle());
        if (childrenBean.getCount() <= 0) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                textView2.setTextColor(this.context.getResources().getColor(R.color.gray_light));
            } else {
                textView2.setTextColor(this.context.getResources().getColor(R.color.font_com_night));
            }
        } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
            textView2.setTextColor(this.context.getResources().getColor(R.color.question_color));
        } else {
            textView2.setTextColor(this.context.getResources().getColor(R.color.question_color_night));
        }
        imageView2.setVisibility(childrenBean.isShowContinue() ? i2 : 8);
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.adapter.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12844c.lambda$convert$1(childrenBean, helper, baseNode, view);
            }
        });
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNewSecond2Provider.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int width = relativeLayout.getWidth();
                if (imageView2.getVisibility() == 0) {
                    textView2.setMaxWidth(width - ScreenUtil.getPxByDp(QuestionBankNewSecond2Provider.this.getContext(), 38));
                } else {
                    textView2.setMaxWidth(width);
                }
                relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
