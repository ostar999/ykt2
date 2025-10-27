package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.psychiatrygarden.bean.RankBeanData;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class RankUserItemView extends LinearLayout {
    private CircleImageView mImgHead;
    private TextView mTvRankNum;

    public RankUserItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_do_question_rank, this);
        this.mImgHead = (CircleImageView) findViewById(R.id.img_head);
        this.mTvRankNum = (TextView) findViewById(R.id.tv_rank);
    }

    public void setData(int pos, RankBeanData data) {
        if (pos == 1) {
            this.mImgHead.setBorderColor(getContext().getColor(R.color.main_theme_color));
            this.mImgHead.setBorderColor(getResources().getColor(R.color.main_theme_color, null));
            this.mTvRankNum.setBackgroundResource(R.mipmap.ic_rank_first);
        } else if (pos == 2) {
            this.mImgHead.setBorderColor(getContext().getColor(R.color.orange_color));
            this.mImgHead.setBorderColor(getResources().getColor(R.color.orange_color, null));
            this.mTvRankNum.setBackgroundResource(R.mipmap.ic_rank_second);
        } else if (pos != 3) {
            this.mImgHead.setBorderColor(getContext().getColor(R.color.forth_txt_color));
            this.mImgHead.setBorderColor(getResources().getColor(R.color.forth_txt_color, null));
            this.mTvRankNum.setBackgroundResource(R.mipmap.ic_rank_other);
        } else {
            this.mImgHead.setBorderColor(getContext().getColor(R.color.person_orange_color));
            this.mImgHead.setBorderColor(getResources().getColor(R.color.person_orange_color, null));
            this.mTvRankNum.setBackgroundResource(R.mipmap.ic_rank_third);
        }
        this.mTvRankNum.setText(data.getRanking());
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(data.getAvatar())).placeholder(R.mipmap.ic_avatar_def).apply((BaseRequestOptions<?>) new RequestOptions().error(R.mipmap.ic_avatar_def)).into(this.mImgHead);
    }

    public RankUserItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RankUserItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
