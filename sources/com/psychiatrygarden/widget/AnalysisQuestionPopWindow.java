package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.AttachPopupView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class AnalysisQuestionPopWindow extends AttachPopupView {
    private ImageView iv_quetion_img;
    private QuestionDetailBean tagQuestionDetailBean;

    public AnalysisQuestionPopWindow(@NonNull Context context) {
        super(context);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_analysis_question;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getPopupWidth() {
        return (ScreenUtil.getScreenWidth((Activity) getContext()) * 2) / 3;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        TextView textView = (TextView) findViewById(R.id.tv_question_title);
        this.iv_quetion_img = (ImageView) findViewById(R.id.iv_quetion_img);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_question_option);
        QuestionDetailBean questionDetailBean = this.tagQuestionDetailBean;
        if (questionDetailBean != null) {
            textView.setText(questionDetailBean.getTitle());
            if (TextUtils.isEmpty(this.tagQuestionDetailBean.getTitle_img())) {
                this.iv_quetion_img.setVisibility(8);
            } else {
                this.iv_quetion_img.setVisibility(0);
                Glide.with(ProjectApp.instance()).load((Object) GlideUtils.generateUrl(this.tagQuestionDetailBean.getTitle_img())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.imgplacehodel_image).placeholder(R.drawable.imgplacehodel_image)).listener(new RequestListener<Drawable>() { // from class: com.psychiatrygarden.widget.AnalysisQuestionPopWindow.2
                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Drawable> target, boolean isFirstResource) {
                        AnalysisQuestionPopWindow.this.iv_quetion_img.setImageResource(R.drawable.imgplacehodel_image);
                        return true;
                    }

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        AnalysisQuestionPopWindow.this.iv_quetion_img.setImageDrawable(resource);
                        return true;
                    }
                }).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.widget.AnalysisQuestionPopWindow.1
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                        onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
                    }

                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource != null) {
                            float intrinsicWidth = resource.getIntrinsicWidth();
                            float intrinsicHeight = resource.getIntrinsicHeight();
                            float width = AnalysisQuestionPopWindow.this.iv_quetion_img.getWidth();
                            if (width == 0.0f) {
                                width = AnalysisQuestionPopWindow.this.iv_quetion_img.getResources().getDisplayMetrics().widthPixels;
                            }
                            int i2 = (int) ((intrinsicHeight / intrinsicWidth) * width);
                            ViewGroup.LayoutParams layoutParams = AnalysisQuestionPopWindow.this.iv_quetion_img.getLayoutParams();
                            if (i2 >= 4096) {
                                layoutParams.height = R2.color.N_stretchTextColor;
                                AnalysisQuestionPopWindow.this.iv_quetion_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                layoutParams.height = -1;
                            }
                            AnalysisQuestionPopWindow.this.iv_quetion_img.setLayoutParams(layoutParams);
                        }
                    }
                });
            }
            if (this.tagQuestionDetailBean.getOption() == null || this.tagQuestionDetailBean.getOption().size() <= 0) {
                return;
            }
            linearLayout.removeAllViews();
            for (int i2 = 0; i2 < this.tagQuestionDetailBean.getOption().size(); i2++) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                if (!TextUtils.isEmpty(this.tagQuestionDetailBean.getOption().get(i2).getTitle())) {
                    TextView textView2 = new TextView(getContext());
                    layoutParams.setMargins(0, 0, 0, ScreenUtil.getPxByDp(getContext(), 10));
                    textView2.setLayoutParams(layoutParams);
                    textView2.setLineSpacing(0.0f, 1.3f);
                    textView2.setTextSize(13.0f);
                    if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                        textView2.setTextColor(Color.parseColor("#303030"));
                    } else {
                        textView2.setTextColor(Color.parseColor("#7380A9"));
                    }
                    textView2.setText(this.tagQuestionDetailBean.getOption().get(i2).getKey() + ". " + this.tagQuestionDetailBean.getOption().get(i2).getTitle());
                    linearLayout.addView(textView2);
                }
                if (!TextUtils.isEmpty(this.tagQuestionDetailBean.getOption().get(i2).getImg())) {
                    ImageView imageView = new ImageView(getContext());
                    layoutParams.setMargins(0, 0, 0, ScreenUtil.getPxByDp(getContext(), 10));
                    Glide.with(getContext()).load((Object) GlideUtils.generateUrl(this.tagQuestionDetailBean.getOption().get(i2).getImg())).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
                    imageView.setLayoutParams(layoutParams);
                    linearLayout.addView(imageView);
                }
            }
        }
    }

    public AnalysisQuestionPopWindow(@NonNull Context context, QuestionDetailBean tagQuestionDetailBean) {
        super(context);
        this.tagQuestionDetailBean = tagQuestionDetailBean;
    }
}
