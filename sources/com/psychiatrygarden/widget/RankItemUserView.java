package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
public class RankItemUserView extends LinearLayout {
    private CircleImageView mImgAvatar;
    private ImageView mImgVip;
    private View mLine;
    private TextView mTvNickName;
    private TextView mTvQuestionNum;
    private TextView mTvSort;

    public RankItemUserView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rank_item_user_view, this);
        this.mTvQuestionNum = (TextView) findViewById(R.id.tv_question_number);
        this.mTvSort = (TextView) findViewById(R.id.tv_sort);
        this.mImgAvatar = (CircleImageView) findViewById(R.id.img_head);
        this.mTvNickName = (TextView) findViewById(R.id.tv_nickname);
        this.mLine = findViewById(R.id.line);
        this.mImgVip = (ImageView) findViewById(R.id.img_vip);
    }

    public void setData(RankBeanData data, boolean isEnd, int type) {
        this.mTvSort.setText(data.getRanking());
        this.mTvNickName.setText(data.getNickname());
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(data.getAvatar())).placeholder(R.mipmap.ic_avatar_def).apply((BaseRequestOptions<?>) new RequestOptions().error(R.mipmap.ic_avatar_def)).into(this.mImgAvatar);
        if (data.getIs_svip().equals("1")) {
            this.mImgVip.setVisibility(0);
            this.mImgVip.setImageResource(R.drawable.svip100_icon);
        } else if (data.getIs_vip().equals("1")) {
            this.mImgVip.setVisibility(0);
            this.mImgVip.setImageResource(R.drawable.vip_iconimg2);
        } else {
            this.mImgVip.setVisibility(8);
        }
        String ranking = data.getRanking();
        ranking.hashCode();
        switch (ranking) {
            case "1":
                this.mTvSort.setTextColor(Color.parseColor("#FFFFFF"));
                this.mImgAvatar.setBorderColor(getResources().getColor(R.color.main_theme_color, null));
                this.mTvSort.setBackgroundResource(R.mipmap.ic_rank_first);
                break;
            case "2":
                this.mTvSort.setTextColor(Color.parseColor("#FFFFFF"));
                this.mImgAvatar.setBorderColor(getResources().getColor(R.color.orange_color, null));
                this.mTvSort.setBackgroundResource(R.mipmap.ic_rank_second);
                break;
            case "3":
                this.mTvSort.setTextColor(Color.parseColor("#FFFFFF"));
                this.mImgAvatar.setBorderColor(getResources().getColor(R.color.person_orange_color, null));
                this.mTvSort.setBackgroundResource(R.mipmap.ic_rank_third);
                break;
            default:
                this.mTvSort.setTextColor(Color.parseColor("#C2C6CB"));
                this.mImgAvatar.setBorderColor(getResources().getColor(R.color.transparent, null));
                this.mTvSort.setBackground(null);
                break;
        }
        if (type == 1) {
            this.mTvQuestionNum.setText(data.getNum() + "道");
        } else if (type == 2) {
            this.mTvQuestionNum.setText(data.getNum() + "条");
        } else if (type == 3) {
            this.mTvQuestionNum.setText(data.getNum() + "条");
        } else if (type == 4) {
            this.mTvQuestionNum.setText(data.getNum() + "条");
        } else if (type == 5) {
            this.mTvQuestionNum.setText(data.getNum() + "天");
        } else if (type == 6) {
            this.mTvQuestionNum.setText(data.getNum() + "天");
        }
        this.mLine.setVisibility(isEnd ? 8 : 0);
    }

    public RankItemUserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RankItemUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
