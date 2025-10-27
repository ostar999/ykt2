package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.bean.StatisticsData;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u000eH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/widget/QuestionStatisticsPop;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "data", "Lcom/psychiatrygarden/bean/StatisticsData;", "onConfirmListener", "Lcom/lxj/xpopup/interfaces/OnConfirmListener;", "(Landroid/content/Context;Lcom/psychiatrygarden/bean/StatisticsData;Lcom/lxj/xpopup/interfaces/OnConfirmListener;)V", "mProgressView", "Lcom/psychiatrygarden/widget/QuestionStatisticsProgressView;", "getImplLayoutId", "", "onCreate", "", "onShow", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class QuestionStatisticsPop extends CenterPopupView {

    @NotNull
    private final StatisticsData data;

    @Nullable
    private QuestionStatisticsProgressView mProgressView;

    @NotNull
    private final OnConfirmListener onConfirmListener;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuestionStatisticsPop(@NotNull Context context, @NotNull StatisticsData data, @NotNull OnConfirmListener onConfirmListener) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(onConfirmListener, "onConfirmListener");
        this.data = data;
        this.onConfirmListener = onConfirmListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(QuestionStatisticsPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(QuestionStatisticsPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onConfirmListener.onConfirm();
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onShow$lambda$0(QuestionStatisticsPop this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        QuestionStatisticsProgressView questionStatisticsProgressView = this$0.mProgressView;
        if (questionStatisticsProgressView != null) {
            questionStatisticsProgressView.setCurrentPercent(this$0.data.getRight(), this$0.data.getTotal(), this$0.data.getRightPercent());
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_answer_statistics;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tv_chapter_name);
        TextView textView2 = (TextView) findViewById(R.id.tv_child_chapter_name);
        TextView textView3 = (TextView) findViewById(R.id.tv_total);
        TextView textView4 = (TextView) findViewById(R.id.tv_answered);
        TextView textView5 = (TextView) findViewById(R.id.tv_right_num);
        TextView textView6 = (TextView) findViewById(R.id.tv_wrong_num);
        TextView textView7 = (TextView) findViewById(R.id.tv_describe);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            textView7.setTextColor(Color.parseColor("#CC7018"));
        }
        ImageView imageView = (ImageView) findViewById(R.id.iv_next_chapter);
        this.mProgressView = (QuestionStatisticsProgressView) findViewById(R.id.progress_view);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.hf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuestionStatisticsPop.onCreate$lambda$1(this.f16558c, view);
            }
        });
        QuestionStatisticsProgressView questionStatisticsProgressView = this.mProgressView;
        ViewGroup.LayoutParams layoutParams = questionStatisticsProgressView != null ? questionStatisticsProgressView.getLayoutParams() : null;
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
        int i2 = getResources().getDisplayMetrics().widthPixels;
        if (i2 < CommonUtil.dip2px(getContext(), 320.0f)) {
            layoutParams2.width = i2;
            layoutParams2.height = i2 / 2;
            QuestionStatisticsProgressView questionStatisticsProgressView2 = this.mProgressView;
            if (questionStatisticsProgressView2 != null) {
                questionStatisticsProgressView2.setLayoutParams(layoutParams2);
            }
        }
        if (TextUtils.isEmpty(this.data.getChapter())) {
            textView.setVisibility(4);
        } else {
            textView.setText(this.data.getChapter());
        }
        if (TextUtils.isEmpty(this.data.getChildChapter())) {
            textView2.setVisibility(4);
        } else {
            textView2.setText(this.data.getChildChapter());
        }
        textView3.setText(String.valueOf(this.data.getTotal()));
        textView4.setText(String.valueOf(this.data.getAnswered()));
        textView5.setText(String.valueOf(this.data.getRight()));
        textView6.setText(String.valueOf(this.data.getWrong()));
        if (TextUtils.isEmpty(this.data.getDescrible())) {
            textView7.setVisibility(8);
        } else {
            textView7.setText(this.data.getDescrible());
        }
        imageView.setVisibility(this.data.getShowNextChapter() ? 0 : 8);
        if (this.data.getShowNextChapter()) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.if
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuestionStatisticsPop.onCreate$lambda$2(this.f16591c, view);
                }
            });
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
        QuestionStatisticsProgressView questionStatisticsProgressView = this.mProgressView;
        if (questionStatisticsProgressView != null) {
            questionStatisticsProgressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.gf
                @Override // java.lang.Runnable
                public final void run() {
                    QuestionStatisticsPop.onShow$lambda$0(this.f16524c);
                }
            }, 300L);
        }
    }
}
