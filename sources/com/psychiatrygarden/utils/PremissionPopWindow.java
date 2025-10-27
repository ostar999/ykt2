package com.psychiatrygarden.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.yikaobang.yixue.R;
import java.util.Locale;

/* loaded from: classes6.dex */
public class PremissionPopWindow extends CenterPopupView {
    public TextView cancle;
    private TextView content;
    public PressionIml mPressionIml;
    public TextView ok;
    int type;

    public interface PressionIml {
        void premissionIml(int code);
    }

    public PremissionPopWindow(@NonNull Context context, PressionIml mPressionIml, int type) {
        super(context);
        this.mPressionIml = mPressionIml;
        this.type = type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isPremission, true, getContext());
        this.mPressionIml.premissionIml(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
        this.mPressionIml.premissionIml(1);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isPremission, false, getContext());
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_premission_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.content = (TextView) findViewById(R.id.content);
        this.cancle = (TextView) findViewById(R.id.cancle);
        this.ok = (TextView) findViewById(R.id.ok);
        this.content.setText(this.type == 0 ? String.format(Locale.CHINA, "为了方便您在App内正常使用更新下载版本、下载视频、上传图片/头像及分享功能，请您允许%s使用存储（读取）内存权限和相机权限，使用第三方推送sdk，更好服务用户。", getContext().getString(R.string.app_name)) : String.format(Locale.CHINA, "为了方便您在App内正常使用分享功能，请您允许%s使用存储（读取）内存权限，使用第三方推送sdk，更好服务用户。", getContext().getString(R.string.app_name)));
        this.cancle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16250c.lambda$onCreate$0(view);
            }
        });
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16251c.lambda$onCreate$1(view);
            }
        });
    }
}
