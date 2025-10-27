package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class KnowledgeMaskView extends LinearLayout {
    private RoundedImageView mImgView;

    public KnowledgeMaskView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_knowledge_vip_lock, this);
        this.mImgView = (RoundedImageView) findViewById(R.id.img_view);
    }

    public void setData(int type) {
        if (type == 1) {
            this.mImgView.setImageResource(R.mipmap.ic_part_view);
        } else if (type == 2) {
            this.mImgView.setImageResource(R.mipmap.ic_point_view);
        } else {
            this.mImgView.setImageResource(R.mipmap.ic_frequency_view);
        }
    }

    public KnowledgeMaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KnowledgeMaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
