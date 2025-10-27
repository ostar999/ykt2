package com.luck.picture.lib.adapter.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.StyleUtils;

/* loaded from: classes4.dex */
public class VideoViewHolder extends BaseRecyclerMediaHolder {
    private final TextView tvDuration;

    public VideoViewHolder(@NonNull View view, PictureSelectionConfig pictureSelectionConfig) {
        super(view, pictureSelectionConfig);
        TextView textView = (TextView) view.findViewById(R.id.tv_duration);
        this.tvDuration = textView;
        SelectMainStyle selectMainStyle = PictureSelectionConfig.selectorStyle.getSelectMainStyle();
        int adapterDurationDrawableLeft = selectMainStyle.getAdapterDurationDrawableLeft();
        if (StyleUtils.checkStyleValidity(adapterDurationDrawableLeft)) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(adapterDurationDrawableLeft, 0, 0, 0);
        }
        int adapterDurationTextSize = selectMainStyle.getAdapterDurationTextSize();
        if (StyleUtils.checkSizeValidity(adapterDurationTextSize)) {
            textView.setTextSize(adapterDurationTextSize);
        }
        int adapterDurationTextColor = selectMainStyle.getAdapterDurationTextColor();
        if (StyleUtils.checkStyleValidity(adapterDurationTextColor)) {
            textView.setTextColor(adapterDurationTextColor);
        }
        int adapterDurationBackgroundResources = selectMainStyle.getAdapterDurationBackgroundResources();
        if (StyleUtils.checkStyleValidity(adapterDurationBackgroundResources)) {
            textView.setBackgroundResource(adapterDurationBackgroundResources);
        }
        int[] adapterDurationGravity = selectMainStyle.getAdapterDurationGravity();
        if (StyleUtils.checkArrayValidity(adapterDurationGravity) && (textView.getLayoutParams() instanceof RelativeLayout.LayoutParams)) {
            ((RelativeLayout.LayoutParams) textView.getLayoutParams()).removeRule(12);
            for (int i2 : adapterDurationGravity) {
                ((RelativeLayout.LayoutParams) this.tvDuration.getLayoutParams()).addRule(i2);
            }
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder
    public void bindData(LocalMedia localMedia, int i2) {
        super.bindData(localMedia, i2);
        this.tvDuration.setText(DateUtils.formatDurationTime(localMedia.getDuration()));
    }
}
