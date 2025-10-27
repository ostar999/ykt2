package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DataStatisticsInfoDialog extends BottomPopupView {
    private boolean isRanking;
    private String mType;

    public DataStatisticsInfoDialog(@NonNull Context context, boolean isRank, String type) {
        super(context);
        this.isRanking = isRank;
        this.mType = type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return this.isRanking ? R.layout.view_ranking_info_dialog : R.layout.view_statistics_info_dialog;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0068  */
    @Override // com.lxj.xpopup.core.BasePopupView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCreate() {
        /*
            r11 = this;
            super.onCreate()
            r0 = 2131365157(0x7f0a0d25, float:1.8350171E38)
            android.view.View r0 = r11.findViewById(r0)
            android.widget.LinearLayout r0 = (android.widget.LinearLayout) r0
            r1 = 2131365307(0x7f0a0dbb, float:1.8350476E38)
            android.view.View r1 = r11.findViewById(r1)
            android.widget.LinearLayout r1 = (android.widget.LinearLayout) r1
            com.psychiatrygarden.utils.AnimUtil.fromBottomToTopAnim(r1)
            r2 = 2131363704(0x7f0a0778, float:1.8347224E38)
            android.view.View r2 = r11.findViewById(r2)
            android.widget.ImageView r2 = (android.widget.ImageView) r2
            r3 = 2131366805(0x7f0a1395, float:1.8353514E38)
            android.view.View r3 = r11.findViewById(r3)
            androidx.core.widget.NestedScrollView r3 = (androidx.core.widget.NestedScrollView) r3
            r3 = 2131365279(0x7f0a0d9f, float:1.8350419E38)
            android.view.View r3 = r11.findViewById(r3)
            android.widget.LinearLayout r3 = (android.widget.LinearLayout) r3
            r4 = 2131368676(0x7f0a1ae4, float:1.8357309E38)
            android.view.View r4 = r11.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r5 = 2131368675(0x7f0a1ae3, float:1.8357307E38)
            android.view.View r5 = r11.findViewById(r5)
            android.widget.TextView r5 = (android.widget.TextView) r5
            android.content.Context r6 = r11.getContext()
            int r6 = com.psychiatrygarden.utils.SkinManager.getCurrentSkinType(r6)
            r7 = 1
            if (r6 != r7) goto L54
            r6 = 2131233053(0x7f08091d, float:1.8082233E38)
            goto L57
        L54:
            r6 = 2131232552(0x7f080728, float:1.8081216E38)
        L57:
            r2.setImageResource(r6)
            java.lang.String r6 = r11.mType
            r6.hashCode()
            int r8 = r6.hashCode()
            r9 = 0
            r10 = -1
            switch(r8) {
                case -1165870106: goto L89;
                case -980226692: goto L80;
                case 3446944: goto L75;
                case 950398559: goto L6a;
                default: goto L68;
            }
        L68:
            r7 = r10
            goto L94
        L6a:
            java.lang.String r7 = "comment"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L73
            goto L68
        L73:
            r7 = 3
            goto L94
        L75:
            java.lang.String r7 = "post"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L7e
            goto L68
        L7e:
            r7 = 2
            goto L94
        L80:
            java.lang.String r8 = "praise"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L94
            goto L68
        L89:
            java.lang.String r7 = "question"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L93
            goto L68
        L93:
            r7 = r9
        L94:
            switch(r7) {
                case 0: goto Lbe;
                case 1: goto Lb8;
                case 2: goto La8;
                case 3: goto L98;
                default: goto L97;
            }
        L97:
            goto Lcd
        L98:
            r3.setVisibility(r9)
            java.lang.String r3 = "评论次数"
            r4.setText(r3)
            java.lang.String r3 = "进统计本项目的评论次数，已删除或被平台清理的的评论不计算次数；"
            r5.setText(r3)
            goto Lcd
        La8:
            r3.setVisibility(r9)
            java.lang.String r3 = "发帖次数"
            r4.setText(r3)
            java.lang.String r3 = "进统计本项目的发帖次数，已删除或被平台清理的的帖子不计算次数；"
            r5.setText(r3)
            goto Lcd
        Lb8:
            r4 = 8
            r3.setVisibility(r4)
            goto Lcd
        Lbe:
            r3.setVisibility(r9)
            java.lang.String r3 = "刷题次数"
            r4.setText(r3)
            java.lang.String r3 = "基于刷题次数统计，试题每次提交答案即为一次，同一个试题多次提交答案，次数累计计算；每1小时统计一次；"
            r5.setText(r3)
        Lcd:
            com.psychiatrygarden.widget.s5 r3 = new com.psychiatrygarden.widget.s5
            r3.<init>()
            r2.setOnClickListener(r3)
            android.content.Context r2 = r11.getContext()
            int r2 = cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil.getScreenHeight(r2)
            android.content.Context r3 = r11.getContext()
            r4 = 4636455816377925632(0x4058000000000000, double:96.0)
            int r3 = cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil.dip2px(r3, r4)
            int r2 = r2 - r3
            android.view.ViewTreeObserver r3 = r0.getViewTreeObserver()
            com.psychiatrygarden.widget.DataStatisticsInfoDialog$1 r4 = new com.psychiatrygarden.widget.DataStatisticsInfoDialog$1
            r4.<init>()
            r3.addOnGlobalLayoutListener(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.DataStatisticsInfoDialog.onCreate():void");
    }
}
