package com.psychiatrygarden.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ScoreTrendChangeDialog extends CenterPopupView {
    private String imgUrl;
    private String mDesc;
    private Handler mHandler;
    private int mScore;
    private TextView mTvScore;
    private int yesterdayScore;

    public ScoreTrendChangeDialog(@NonNull Context context, int yesterdayScore, int score, String url, String desc) {
        super(context);
        this.yesterdayScore = 0;
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.widget.ScoreTrendChangeDialog.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (ScoreTrendChangeDialog.this.mTvScore != null) {
                    if (ScoreTrendChangeDialog.this.yesterdayScore < ScoreTrendChangeDialog.this.mScore) {
                        ScoreTrendChangeDialog.access$108(ScoreTrendChangeDialog.this);
                        if (ScoreTrendChangeDialog.this.yesterdayScore > ScoreTrendChangeDialog.this.mScore) {
                            ScoreTrendChangeDialog.this.mTvScore.setText(ScoreTrendChangeDialog.this.mScore + "");
                            ScoreTrendChangeDialog.this.mHandler.removeCallbacksAndMessages(null);
                            return;
                        }
                        ScoreTrendChangeDialog.this.mTvScore.setText(ScoreTrendChangeDialog.this.yesterdayScore + "");
                        ScoreTrendChangeDialog.this.mHandler.sendEmptyMessageDelayed(1, 20L);
                        return;
                    }
                    ScoreTrendChangeDialog.access$110(ScoreTrendChangeDialog.this);
                    if (ScoreTrendChangeDialog.this.yesterdayScore < ScoreTrendChangeDialog.this.mScore) {
                        ScoreTrendChangeDialog.this.mTvScore.setText(ScoreTrendChangeDialog.this.mScore + "");
                        ScoreTrendChangeDialog.this.mHandler.removeCallbacksAndMessages(null);
                        return;
                    }
                    ScoreTrendChangeDialog.this.mTvScore.setText(ScoreTrendChangeDialog.this.yesterdayScore + "");
                    ScoreTrendChangeDialog.this.mHandler.sendEmptyMessageDelayed(1, 20L);
                }
            }
        };
        this.yesterdayScore = yesterdayScore;
        this.mScore = score;
        this.imgUrl = url;
        this.mDesc = desc;
    }

    public static /* synthetic */ int access$108(ScoreTrendChangeDialog scoreTrendChangeDialog) {
        int i2 = scoreTrendChangeDialog.yesterdayScore;
        scoreTrendChangeDialog.yesterdayScore = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$110(ScoreTrendChangeDialog scoreTrendChangeDialog) {
        int i2 = scoreTrendChangeDialog.yesterdayScore;
        scoreTrendChangeDialog.yesterdayScore = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.view_score_trend_change_dialog;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        return CommonUtil.getScreenWidth(getContext());
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.img_icon);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        TextView textView = (TextView) findViewById(R.id.tv_desc);
        TextView textView2 = (TextView) findViewById(R.id.btn_sure);
        textView.setText(this.mDesc);
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(this.imgUrl)).placeholder(R.mipmap.ic_order_default).into(imageView);
        TextView textView3 = this.mTvScore;
        if (textView3 != null) {
            textView3.setText(this.yesterdayScore + "");
        }
        this.mHandler.sendEmptyMessage(1);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.lg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16687c.lambda$onCreate$0(view);
            }
        });
    }
}
