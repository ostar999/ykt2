package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.bean.RankBeanData;
import com.psychiatrygarden.ranking.ActiveRankingAct;
import com.psychiatrygarden.ranking.CommentActionRankingAct;
import com.yikaobang.yixue.R;
import java.util.List;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes6.dex */
public class RankItemView extends LinearLayout {
    private TextView mBtnMore;
    private LinearLayout mLyItem;
    private TextView mTvRankNum;
    private TextView mTvTypeName;

    public RankItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_ranking_item, this);
        this.mBtnMore = (TextView) findViewById(R.id.btn_more);
        this.mTvRankNum = (TextView) findViewById(R.id.tv_rank);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_add_item);
        this.mTvTypeName = (TextView) findViewById(R.id.tv_type_name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(int i2, View view) {
        if (i2 == 1) {
            CommentActionRankingAct.newIntent(getContext(), "question", 0);
            return;
        }
        if (i2 == 2) {
            CommentActionRankingAct.newIntent(getContext(), "post", 0);
            return;
        }
        if (i2 == 3) {
            CommentActionRankingAct.newIntent(getContext(), ClientCookie.COMMENT_ATTR, 0);
            return;
        }
        if (i2 == 4) {
            CommentActionRankingAct.newIntent(getContext(), "praise", 0);
        } else if (i2 == 5) {
            ActiveRankingAct.newIntent(getContext(), "accumulate");
        } else if (i2 == 6) {
            ActiveRankingAct.newIntent(getContext(), "continuous");
        }
    }

    public void setData(String title, List<RankBeanData> datas, final int type) {
        this.mTvRankNum.setText(title + "榜排行TOP" + datas.size());
        this.mLyItem.removeAllViews();
        for (int i2 = 0; i2 < datas.size(); i2++) {
            RankItemUserView rankItemUserView = new RankItemUserView(getContext());
            RankBeanData rankBeanData = datas.get(i2);
            boolean z2 = true;
            if (i2 != datas.size() - 1) {
                z2 = false;
            }
            rankItemUserView.setData(rankBeanData, z2, type);
            this.mLyItem.addView(rankItemUserView);
        }
        switch (type) {
            case 1:
                this.mTvTypeName.setText("刷题量");
                break;
            case 2:
                this.mTvTypeName.setText("发帖量");
                break;
            case 3:
                this.mTvTypeName.setText("评论数");
                break;
            case 4:
                this.mTvTypeName.setText("获赞数");
                break;
            case 5:
                this.mTvTypeName.setText("累计活跃");
                break;
            case 6:
                this.mTvTypeName.setText("连续活跃");
                break;
        }
        this.mBtnMore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.sf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16905c.lambda$setData$0(type, view);
            }
        });
    }

    public RankItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RankItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
