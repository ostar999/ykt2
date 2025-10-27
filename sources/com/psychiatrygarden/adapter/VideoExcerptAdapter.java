package com.psychiatrygarden.adapter;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ExcerptBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class VideoExcerptAdapter extends BaseQuickAdapter<ExcerptBean, BaseViewHolder> {
    public VideoExcerptAdapter() {
        super(R.layout.item_excerpt);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder viewHolder, ExcerptBean excerptBean) {
        Drawable background = ((ImageView) viewHolder.getView(R.id.iv_status)).getBackground();
        if (background instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) background;
            boolean zIsRunning = animationDrawable.isRunning();
            if (excerptBean.isPlaying()) {
                if (!zIsRunning) {
                    animationDrawable.start();
                }
            } else if (zIsRunning) {
                animationDrawable.stop();
            }
        }
        Drawable drawable = getContext().getDrawable(excerptBean.isPlaying() ? R.drawable.bg_video_play_excerpt_play : R.drawable.bg_video_play_excerpt_normal);
        if (excerptBean.isPlaying()) {
            if (drawable instanceof GradientDrawable) {
                if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                    ((GradientDrawable) drawable).setColor(Color.parseColor("#FFFBFB"));
                } else {
                    ((GradientDrawable) drawable).setColor(Color.parseColor("#32232E"));
                }
            }
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                viewHolder.setTextColor(R.id.tv_duration, Color.parseColor("#F95843")).setTextColor(R.id.tv_title, Color.parseColor("#F95843"));
            } else {
                viewHolder.setTextColor(R.id.tv_duration, Color.parseColor("#B2575C")).setTextColor(R.id.tv_title, Color.parseColor("#B2575C"));
            }
        } else if (SkinManager.getCurrentSkinType(getContext()) == 0) {
            viewHolder.setTextColor(R.id.tv_duration, Color.parseColor("#909499")).setTextColor(R.id.tv_title, Color.parseColor("#141516"));
        } else {
            viewHolder.setTextColor(R.id.tv_duration, Color.parseColor("#575F79")).setTextColor(R.id.tv_title, Color.parseColor("#7380A9"));
        }
        viewHolder.setText(R.id.tv_duration, String.valueOf(excerptBean.getTime())).setText(R.id.tv_title, excerptBean.getTitle());
    }
}
