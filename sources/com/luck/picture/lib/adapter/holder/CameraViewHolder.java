package com.luck.picture.lib.adapter.holder;

import android.view.View;
import android.widget.TextView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.StyleUtils;

/* loaded from: classes4.dex */
public class CameraViewHolder extends BaseRecyclerMediaHolder {
    public CameraViewHolder(View view) {
        super(view);
        TextView textView = (TextView) view.findViewById(R.id.tvCamera);
        SelectMainStyle selectMainStyle = PictureSelectionConfig.selectorStyle.getSelectMainStyle();
        int adapterCameraBackgroundColor = selectMainStyle.getAdapterCameraBackgroundColor();
        if (StyleUtils.checkStyleValidity(adapterCameraBackgroundColor)) {
            textView.setBackgroundColor(adapterCameraBackgroundColor);
        }
        int adapterCameraDrawableTop = selectMainStyle.getAdapterCameraDrawableTop();
        if (StyleUtils.checkStyleValidity(adapterCameraDrawableTop)) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, adapterCameraDrawableTop, 0, 0);
        }
        String adapterCameraText = selectMainStyle.getAdapterCameraText();
        if (StyleUtils.checkTextValidity(adapterCameraText)) {
            textView.setText(adapterCameraText);
        } else if (PictureSelectionConfig.getInstance().chooseMode == SelectMimeType.ofAudio()) {
            textView.setText(view.getContext().getString(R.string.ps_tape));
        }
        int adapterCameraTextSize = selectMainStyle.getAdapterCameraTextSize();
        if (StyleUtils.checkSizeValidity(adapterCameraTextSize)) {
            textView.setTextSize(adapterCameraTextSize);
        }
        int adapterCameraTextColor = selectMainStyle.getAdapterCameraTextColor();
        if (StyleUtils.checkStyleValidity(adapterCameraTextColor)) {
            textView.setTextColor(adapterCameraTextColor);
        }
    }
}
