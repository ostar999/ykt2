package com.psychiatrygarden.adapter;

import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.VideoSummary;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class VideoSummaryAdapter extends BaseQuickAdapter<VideoSummary, BaseViewHolder> {
    private int normalColor;
    private int playColor;

    public VideoSummaryAdapter() {
        super(R.layout.item_excerpt);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, VideoSummary item) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.first_txt_color, R.attr.main_theme_color});
        if (this.normalColor == 0) {
            this.normalColor = typedArrayObtainStyledAttributes.getColor(0, getContext().getColor(R.color.first_txt_color));
        }
        if (this.playColor == 0) {
            this.playColor = typedArrayObtainStyledAttributes.getColor(1, getContext().getColor(R.color.main_theme_color));
        }
        holder.setGone(R.id.iv_status, !item.isCurrentPlay()).setText(R.id.tv_title, item.getTitle()).setTextColor(R.id.tv_duration, item.isCurrentPlay() ? this.playColor : this.normalColor).setTextColor(R.id.tv_title, item.isCurrentPlay() ? this.playColor : this.normalColor);
        Drawable background = holder.getView(R.id.iv_status).getBackground();
        if (item.isCurrentPlay()) {
            holder.getView(R.id.root).setBackground(getContext().getDrawable(SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.bg_video_play_excerpt_playing : R.drawable.bg_video_play_excerpt_playing_night));
            if (background instanceof AnimationDrawable) {
                ((AnimationDrawable) background).start();
            }
        } else {
            if (background instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) background;
                if (animationDrawable.isRunning()) {
                    animationDrawable.stop();
                }
            }
            holder.getView(R.id.root).setBackground(getContext().getDrawable(R.drawable.bg_video_play_excerpt_normal));
        }
        int realPoint = item.getRealPoint() / 1000;
        int i2 = realPoint / 3600;
        holder.setText(R.id.tv_duration, String.format(i2 <= 99 ? "%02d:%02d:%02d" : "%d:%02d:%02d", Integer.valueOf(i2), Integer.valueOf((realPoint % 3600) / 60), Integer.valueOf(realPoint % 60)));
        typedArrayObtainStyledAttributes.recycle();
    }
}
