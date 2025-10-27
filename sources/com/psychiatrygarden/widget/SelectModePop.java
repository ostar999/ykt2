package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class SelectModePop extends BottomPopupView {
    private TextView ceshimoshi;
    private TextView lianximoshi;
    private Context mContext;
    private LinearLayout moukuai;
    private View view_dismiss;

    public SelectModePop(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popu_filtrate;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.moukuai = (LinearLayout) findViewById(R.id.moukuai);
        this.lianximoshi = (TextView) findViewById(R.id.lianximoshi);
        this.ceshimoshi = (TextView) findViewById(R.id.ceshimoshi);
        this.view_dismiss = findViewById(R.id.view_dismiss);
        this.moukuai.setVisibility(0);
        this.view_dismiss.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.SelectModePop.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                SelectModePop.this.dismiss();
            }
        });
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.isceshitidan, false, this.mContext)) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.ceshimoshi.setTextColor(this.mContext.getResources().getColor(R.color.app_theme_red));
                this.lianximoshi.setTextColor(this.mContext.getResources().getColor(R.color.black));
            } else {
                this.ceshimoshi.setTextColor(this.mContext.getResources().getColor(R.color.question_color_night));
                this.lianximoshi.setTextColor(this.mContext.getResources().getColor(R.color.jiucuo_night));
            }
        } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.lianximoshi.setTextColor(this.mContext.getResources().getColor(R.color.app_theme_red));
            this.ceshimoshi.setTextColor(this.mContext.getResources().getColor(R.color.black));
        } else {
            this.lianximoshi.setTextColor(this.mContext.getResources().getColor(R.color.question_color_night));
            this.ceshimoshi.setTextColor(this.mContext.getResources().getColor(R.color.jiucuo_night));
        }
        this.lianximoshi.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.SelectModePop.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.isceshitidan, false, SelectModePop.this.mContext);
                SelectModePop.this.dismiss();
            }
        });
        this.ceshimoshi.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.SelectModePop.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.isceshitidan, true, SelectModePop.this.mContext);
                SelectModePop.this.dismiss();
            }
        });
    }
}
