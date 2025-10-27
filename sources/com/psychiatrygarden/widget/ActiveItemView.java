package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.activity.mine.active.ActiveListAct;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ActiveItemView extends LinearLayout {
    private RoundedImageView imgPic;
    private LinearLayout mLyItem;
    private TextView mTvTime;
    private TextView mTvTitle;

    public ActiveItemView(Context context) {
        this(context, null);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_active_item, this);
        this.imgPic = (RoundedImageView) findViewById(R.id.img_pic);
        this.mTvTitle = (TextView) findViewById(R.id.tv_title);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_item);
        this.mTvTime = (TextView) findViewById(R.id.tv_time);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(View view) {
        getContext().startActivity(ActiveListAct.newIntent(getContext()));
    }

    public void setData(PushBookData data, String channelId, String articleId, String commentCount, String noAccess) {
        if (this.mTvTitle != null) {
            GlideUtils.loadImage(getContext(), data.getThumbnail(), this.imgPic);
            this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16301c.lambda$setData$0(view);
                }
            });
        }
    }

    public ActiveItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActiveItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
