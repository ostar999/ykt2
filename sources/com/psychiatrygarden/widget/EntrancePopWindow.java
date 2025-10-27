package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.rank.RankEntranceActivity;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class EntrancePopWindow extends CenterPopupView {
    Activity activity;
    ImageView close;
    String flag;
    TextView hint1;
    TextView hint2;
    TextView hint3;
    TextView hint4;
    TextView newTitle;
    TextView oldTitle;
    TextView title;

    public EntrancePopWindow(@NonNull Context context, String flag) {
        super(context);
        this.flag = "1";
        this.activity = (Activity) context;
        this.flag = flag;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_entrance_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() throws Resources.NotFoundException {
        super.onCreate();
        this.title = (TextView) findViewById(R.id.title);
        this.newTitle = (TextView) findViewById(R.id.newTitle);
        this.close = (ImageView) findViewById(R.id.close);
        this.oldTitle = (TextView) findViewById(R.id.oldTitle);
        this.hint1 = (TextView) findViewById(R.id.hint1);
        this.hint2 = (TextView) findViewById(R.id.hint2);
        this.hint3 = (TextView) findViewById(R.id.hint3);
        this.hint4 = (TextView) findViewById(R.id.hint4);
        if ("2".equals(this.flag)) {
            this.title.setText("排名统计");
            this.newTitle.setText("经审核版");
            Drawable drawable = this.activity.getResources().getDrawable(R.drawable.smimg_rank);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.newTitle.setCompoundDrawables(drawable, null, null, null);
            this.oldTitle.setText("未审核版");
            Drawable drawable2 = this.activity.getResources().getDrawable(R.drawable.smimg_rank_no);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            this.oldTitle.setCompoundDrawables(drawable2, null, null, null);
            this.hint1.setText("提交成绩单真实截图，审核通过后进入");
            this.hint2.setText("（截图仅用于审核，不会公开）");
            this.hint3.setText("提交成绩信息即可进入");
            this.hint4.setText("成绩未经审核，仅供参考");
        }
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.EntrancePopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                EntrancePopWindow.this.dismiss();
            }
        });
        this.newTitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.EntrancePopWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (!"2".equals(EntrancePopWindow.this.flag)) {
                    Intent intent = new Intent(EntrancePopWindow.this.activity, (Class<?>) RankEntranceActivity.class);
                    intent.putExtra("moudle", Constants.VIA_REPORT_TYPE_WPA_STATE);
                    intent.putExtra("type", "new");
                    EntrancePopWindow.this.activity.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(ProjectApp.instance(), (Class<?>) RankEntranceActivity.class);
                intent2.putExtra("moudle", Constants.VIA_REPORT_TYPE_SET_AVATAR);
                intent2.setFlags(268435456);
                intent2.putExtra("type", "haveimg");
                ProjectApp.instance().startActivity(intent2);
            }
        });
        this.oldTitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.EntrancePopWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (!"2".equals(EntrancePopWindow.this.flag)) {
                    Intent intent = new Intent(EntrancePopWindow.this.activity, (Class<?>) RankEntranceActivity.class);
                    intent.putExtra("moudle", Constants.VIA_REPORT_TYPE_WPA_STATE);
                    intent.putExtra("type", "old");
                    EntrancePopWindow.this.activity.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(ProjectApp.instance(), (Class<?>) RankEntranceActivity.class);
                intent2.putExtra("moudle", Constants.VIA_REPORT_TYPE_SET_AVATAR);
                intent2.setFlags(268435456);
                intent2.putExtra("type", "noimg");
                ProjectApp.instance().startActivity(intent2);
            }
        });
    }
}
