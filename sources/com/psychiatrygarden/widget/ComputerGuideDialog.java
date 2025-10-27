package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.android.material.badge.BadgeDrawable;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class ComputerGuideDialog extends FullScreenPopupView {
    private boolean isPublicQuestion;
    private ProjectChoosedInterface itemChooseLisenter;
    private Context mContext;
    private int mStep;

    public interface ProjectChoosedInterface {
        void mItemLinsenter();
    }

    public ComputerGuideDialog(Context context, boolean isPublicQuestion, ProjectChoosedInterface itemChooseLisenter) {
        super(context);
        this.mStep = 1;
        this.mContext = context;
        this.itemChooseLisenter = itemChooseLisenter;
        this.isPublicQuestion = isPublicQuestion;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
        this.itemChooseLisenter.mItemLinsenter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
        this.itemChooseLisenter.mItemLinsenter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        dismiss();
        this.itemChooseLisenter.mItemLinsenter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(TextView textView, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, View view) {
        if (textView.getText().toString().equals("我知道啦")) {
            dismiss();
            this.itemChooseLisenter.mItemLinsenter();
            return;
        }
        if (this.isPublicQuestion) {
            this.mStep++;
        } else {
            int i2 = this.mStep;
            if (i2 == 2) {
                this.mStep = i2 + 2;
            } else {
                this.mStep = i2 + 1;
            }
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) linearLayout4.getLayoutParams();
        int i3 = this.mStep;
        if (i3 == 2) {
            linearLayout.setGravity(GravityCompat.START);
            linearLayout2.setGravity(BadgeDrawable.TOP_START);
            layoutParams2.leftMargin = ScreenUtil.getPxByDp(this.mContext, R2.anim.window_ios_out);
            layoutParams4.topMargin = ScreenUtil.getPxByDp(this.mContext, 65) + StatusBarUtil.getStatusBarHeight(this.mContext);
            linearLayout4.setBackgroundResource(R.mipmap.ic_computer_guide_step_two);
        } else if (i3 == 3) {
            linearLayout.setGravity(GravityCompat.START);
            linearLayout2.setGravity(81);
            layoutParams2.leftMargin = ScreenUtil.getPxByDp(this.mContext, 220);
            layoutParams3.bottomMargin = ScreenUtil.getPxByDp(this.mContext, 61);
            linearLayout4.setBackgroundResource(R.mipmap.ic_computer_guide_step_three);
        } else if (i3 == 4) {
            linearLayout.setGravity(17);
            linearLayout2.setGravity(BadgeDrawable.BOTTOM_END);
            layoutParams2.leftMargin = ScreenUtil.getPxByDp(this.mContext, 20);
            layoutParams4.rightMargin = ScreenUtil.getPxByDp(this.mContext, 31);
            linearLayout4.setBackgroundResource(R.mipmap.ic_computer_guide_step_four);
        } else if (i3 == 5) {
            textView.setText("我知道啦");
            linearLayout.setGravity(GravityCompat.START);
            linearLayout2.setGravity(49);
            layoutParams2.width = -1;
            layoutParams2.leftMargin = UIUtil.dip2px(this.mContext, 199.0d);
            layoutParams4.width = -1;
            layoutParams4.topMargin = ScreenUtil.getPxByDp(this.mContext, 13) + StatusBarUtil.getStatusBarHeight(this.mContext);
            layoutParams4.rightMargin = 0;
            linearLayout4.setBackgroundResource(R.mipmap.ic_computer_guide_step_five);
            linearLayout5.setVisibility(0);
            linearLayout2.setVisibility(8);
        }
        linearLayout.setLayoutParams(layoutParams);
        linearLayout2.setLayoutParams(layoutParams2);
        linearLayout3.setLayoutParams(layoutParams3);
        linearLayout4.setLayoutParams(layoutParams4);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_computer_guide_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.btn_skip);
        final TextView textView2 = (TextView) findViewById(R.id.btn_next);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.img_step);
        final LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.ly_content);
        final LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.ly_bottom);
        final LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.ly_view);
        final LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.ly_content_last);
        TextView textView3 = (TextView) findViewById(R.id.btn_skip_last);
        TextView textView4 = (TextView) findViewById(R.id.btn_next_last);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.o3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16751c.lambda$onCreate$0(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.p3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16780c.lambda$onCreate$1(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.q3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16813c.lambda$onCreate$2(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.r3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16844c.lambda$onCreate$3(textView2, linearLayout4, linearLayout2, linearLayout3, linearLayout, linearLayout5, view);
            }
        });
    }
}
