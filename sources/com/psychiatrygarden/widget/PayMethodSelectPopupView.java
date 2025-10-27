package com.psychiatrygarden.widget;

import android.app.Activity;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.activity.mine.order.OrderListFragment;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.utils.pay.PayMethodKeyKt;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PayMethodSelectPopupView extends BottomPopupView {
    private final Activity activity;
    private ClickBack clickBack;
    private final String endTime;
    private WeakHandler mHandler;
    private final String moneyCount;
    private String payMethod;

    public interface ClickBack {
        void back(String payMethod);
    }

    public PayMethodSelectPopupView(@NonNull Activity context, String moneyCount, String endTime) {
        super(context);
        this.payMethod = PayMethodKeyKt.ALi_PayMethod;
        this.moneyCount = moneyCount;
        this.activity = context;
        this.endTime = endTime;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(ImageView imageView, ImageView imageView2, View view) {
        imageView.setSelected(true);
        imageView2.setSelected(false);
        this.payMethod = PayMethodKeyKt.ALi_PayMethod;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(ImageView imageView, ImageView imageView2, View view) {
        imageView.setSelected(false);
        imageView2.setSelected(true);
        this.payMethod = "wechat";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(View view) {
        ClickBack clickBack = this.clickBack;
        if (clickBack != null) {
            clickBack.back(this.payMethod);
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(TextView textView, Message message) {
        if (message.what == 1) {
            textView.setText(OrderListFragment.getCountDownTimeStr((System.currentTimeMillis() / 1000) + "", this.endTime));
            this.mHandler.sendEmptyMessageDelayed(1, 1000L);
        }
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void doAfterDismiss() {
        super.doAfterDismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_pay_method_select;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tvSubmit);
        ImageView imageView = (ImageView) findViewById(R.id.imgClose);
        final ImageView imageView2 = (ImageView) findViewById(R.id.imgCheckoutZFB);
        final ImageView imageView3 = (ImageView) findViewById(R.id.imgCheckoutWX);
        TextView textView2 = (TextView) findViewById(R.id.tvMoneyCount);
        final TextView textView3 = (TextView) findViewById(R.id.tvTime);
        textView2.setText(TextUtils.isEmpty(this.moneyCount) ? "" : this.moneyCount.replace("¥", ""));
        imageView2.setSelected(true);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.pb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16790c.lambda$onCreate$0(imageView2, imageView3, view);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.qb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16822c.lambda$onCreate$1(imageView2, imageView3, view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.rb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16858c.lambda$onCreate$2(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.sb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16899c.lambda$onCreate$3(view);
            }
        });
        textView3.setText(OrderListFragment.getCountDownTimeStr((System.currentTimeMillis() / 1000) + "", this.endTime));
        WeakHandler weakHandler = new WeakHandler(this.activity, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.widget.tb
            @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
            public final void handlerMessage(Message message) {
                this.f16931a.lambda$onCreate$4(textView3, message);
            }
        });
        this.mHandler = weakHandler;
        weakHandler.sendEmptyMessageDelayed(1, 1000L);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void setClickBack(ClickBack clickBack) {
        this.clickBack = clickBack;
    }
}
