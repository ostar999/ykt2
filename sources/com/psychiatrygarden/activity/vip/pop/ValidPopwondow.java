package com.psychiatrygarden.activity.vip.pop;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ValidPopwondow extends CenterPopupView {
    public TextView cententv;
    public TextView close;
    private View line;
    private View line2;
    private LinearLayout mLyBg;
    public TextView ok;
    public TextView tiptitles;

    public ValidPopwondow(@NonNull Context context) {
        super(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_clear_data_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.mLyBg = (LinearLayout) findViewById(R.id.ly_bg);
        this.tiptitles = (TextView) findViewById(R.id.tiptitles);
        this.line = findViewById(R.id.line);
        this.line2 = findViewById(R.id.line2);
        this.tiptitles.setText("温馨提示");
        TextView textView = (TextView) findViewById(R.id.cententv);
        this.cententv = textView;
        textView.setText("VIP折算金额已超出所选SVIP会员产品价格，无法抵扣~");
        TextView textView2 = (TextView) findViewById(R.id.close);
        this.close = textView2;
        textView2.setText("取消");
        TextView textView3 = (TextView) findViewById(R.id.ok);
        this.ok = textView3;
        textView3.setText("重新选择");
        this.ok.setTextColor(Color.parseColor("#E25D49"));
        this.mLyBg.setBackgroundResource(R.drawable.shape_round_write);
        this.tiptitles.setTextColor(Color.parseColor("#303030"));
        this.cententv.setTextColor(Color.parseColor("#606060"));
        this.close.setTextColor(Color.parseColor("#606060"));
        this.line.setBackgroundColor(Color.parseColor("#DD594A"));
        this.line2.setBackgroundColor(Color.parseColor("#DD594A"));
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14119c.lambda$onCreate$0(view);
            }
        });
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14120c.lambda$onCreate$1(view);
            }
        });
    }
}
