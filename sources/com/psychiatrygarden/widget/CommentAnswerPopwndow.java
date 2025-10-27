package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CommentAnswerPopwndow extends CenterPopupView {
    public CommentAnswerPopwndow(@NonNull Context context) {
        super(context);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_comment_answer_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            ((TextView) findViewById(R.id.tv_high_hot)).setTextColor(Color.parseColor("#c49231"));
            ((TextView) findViewById(R.id.tv_latest_add_praise)).setTextColor(Color.parseColor("#7380a9"));
            ((TextView) findViewById(R.id.tv_most_like)).setTextColor(Color.parseColor("#bf463b"));
            ((TextView) findViewById(R.id.tv_history_acc_count)).setTextColor(Color.parseColor("#7380a9"));
            ((TextView) findViewById(R.id.tv_latest_publish)).setTextColor(Color.parseColor("#c49231"));
            ((TextView) findViewById(R.id.tv_sort_by_pub_time)).setTextColor(Color.parseColor("#7380a9"));
            ((TextView) findViewById(R.id.tv_most_reply)).setTextColor(Color.parseColor("#c49231"));
            ((TextView) findViewById(R.id.tv_sort_total_reply)).setTextColor(Color.parseColor("#7380a9"));
        }
    }
}
