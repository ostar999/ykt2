package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.activity.WebViewActivity;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.psychiatrygarden.widget.glideUtil.progress.GlideRequest;
import com.uuzuche.lib_zxing.DisplayUtil;
import com.yikaobang.yixue.R;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class NoticePopWindow extends CenterPopupView {
    public ImageView appimg;
    public ImageView close;
    public String imgUrl;
    public NoticeClickIml mNoticeClickIml;
    public String noticeText;
    public String push_url;
    public TextView tips;

    public interface NoticeClickIml {
        void mNoticeClick();
    }

    public NoticePopWindow(@NonNull Context context, String imgUrl, String noticeText, String push_url, NoticeClickIml mNoticeClickIml) {
        super(context);
        this.imgUrl = imgUrl;
        this.noticeText = noticeText;
        this.push_url = push_url;
        this.mNoticeClickIml = mNoticeClickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        if (this.push_url.equals("")) {
            return;
        }
        Intent intent = new Intent(getContext(), (Class<?>) WebViewActivity.class);
        intent.putExtra("title", "活动规则");
        intent.putExtra("web_url", this.push_url);
        intent.setFlags(268435456);
        getContext().startActivity(intent);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
        NoticeClickIml noticeClickIml = this.mNoticeClickIml;
        if (noticeClickIml != null) {
            noticeClickIml.mNoticeClick();
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_notice_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.close = (ImageView) findViewById(R.id.close);
        this.appimg = (ImageView) findViewById(R.id.appimg);
        this.tips = (TextView) findViewById(R.id.tips);
        final int iDip2px = DisplayUtil.dip2px(getContext(), 300.0f);
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(this.imgUrl)).placeholder(R.drawable.app_icon).into((GlideRequest<Drawable>) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.widget.NoticePopWindow.1
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                NoticePopWindow.this.appimg.getLayoutParams().height = (int) (iDip2px * (resource.getIntrinsicHeight() / resource.getIntrinsicWidth()));
                NoticePopWindow.this.appimg.getLayoutParams().width = iDip2px;
                NoticePopWindow.this.appimg.setImageDrawable(resource);
            }
        });
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.nb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16734c.lambda$onCreate$0(view);
            }
        });
        try {
            if (TextUtils.isEmpty(this.noticeText)) {
                this.tips.setVisibility(8);
            } else {
                this.tips.setVisibility(0);
            }
            Pattern.compile("[0-9]").matcher(this.noticeText);
            this.tips.setText(new SpannableStringBuilder(this.noticeText));
            this.appimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ob
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16761c.lambda$onCreate$1(view);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        NoticeClickIml noticeClickIml = this.mNoticeClickIml;
        if (noticeClickIml != null) {
            noticeClickIml.mNoticeClick();
        }
    }
}
