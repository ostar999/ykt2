package com.psychiatrygarden.activity.vip.pop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class GoBuyVipPopwindow extends CenterPopupView {
    public GoBuyClickIml goBuyClickIml;
    public TextView goodnames;
    public String goodsContent;
    public TextView goodsDetail;
    public TextView goodsPickName;
    public String goodsTitle;
    public TextView gotoSource;
    public String label;
    public TextView labelid;
    public String tag;
    public ImageView vipclose;

    public interface GoBuyClickIml {
        void mGoBuyClick(int type);
    }

    public GoBuyVipPopwindow(@NonNull Context context, GoBuyClickIml goBuyClickIml, String goodsTitle, String goodsContent, String label, String tag) {
        super(context);
        this.goBuyClickIml = goBuyClickIml;
        this.tag = tag;
        this.goodsTitle = goodsTitle;
        this.goodsContent = goodsContent;
        this.label = label;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        this.goBuyClickIml.mGoBuyClick(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        this.goBuyClickIml.mGoBuyClick(1);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_go_buy_vip_pop;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        return XPopupUtils.getWindowWidth(getContext());
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.labelid = (TextView) findViewById(R.id.labelid);
        TextView textView = (TextView) findViewById(R.id.goodnames);
        this.goodnames = textView;
        textView.setText("" + this.goodsTitle);
        TextView textView2 = (TextView) findViewById(R.id.goodsDetail);
        this.goodsDetail = textView2;
        textView2.setText(this.goodsContent + "");
        TextView textView3 = (TextView) findViewById(R.id.goodsPickName);
        this.goodsPickName = textView3;
        textView3.setText("" + this.goodsTitle);
        this.gotoSource = (TextView) findViewById(R.id.gotoSource);
        this.vipclose = (ImageView) findViewById(R.id.vipclose);
        if (TextUtils.isEmpty(this.label)) {
            this.labelid.setVisibility(8);
        } else {
            this.labelid.setVisibility(0);
            this.labelid.setText(this.label);
        }
        if ("vip".equals(this.tag)) {
            this.gotoSource.setText("继续开通VIP");
        } else {
            this.gotoSource.setText("继续开通SVIP");
        }
        this.vipclose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14104c.lambda$onCreate$0(view);
            }
        });
        this.goodsPickName.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14105c.lambda$onCreate$1(view);
            }
        });
        this.gotoSource.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14106c.lambda$onCreate$2(view);
            }
        });
    }
}
