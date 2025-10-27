package com.aliyun.player.alivcplayerexpand.view.thumbnail;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.aliyun.player.alivcplayerexpand.R;

/* loaded from: classes2.dex */
public class ThumbnailView extends LinearLayout {
    private TextView mPositionTextView;
    private ImageView mThumbnailImageView;

    public ThumbnailView(Context context) {
        super(context);
        init();
    }

    private void findAllViews() {
        this.mPositionTextView = (TextView) findViewById(R.id.tv_position);
        this.mThumbnailImageView = (ImageView) findViewById(R.id.iv_thumbnail);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_view_thumbnail, (ViewGroup) this, true);
        findAllViews();
    }

    public ImageView getThumbnailImageView() {
        return this.mThumbnailImageView;
    }

    public void hideThumbnailView() {
        setVisibility(8);
    }

    public void setThumbnailPicture(Bitmap bitmap) {
        this.mThumbnailImageView.setImageBitmap(bitmap);
    }

    public void setTime(String str) {
        this.mPositionTextView.setText(str);
    }

    public void showThumbnailView() {
        setVisibility(0);
    }

    public ThumbnailView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ThumbnailView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
