package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ErrorExportPopuWindow extends BottomPopupView {
    private boolean exportEnable;
    private String mType;
    public ClickIml sClickIml;

    public interface ClickIml {
        void mClickIml(String isExport, Switch errorSetting);
    }

    public ErrorExportPopuWindow(@NonNull Context context, String type, ClickIml sClickIml) {
        super(context);
        this.sClickIml = sClickIml;
        this.mType = type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(Switch r2, View view) {
        dismiss();
        this.sClickIml.mClickIml("export", r2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(Switch r2, View view) {
        dismiss();
        this.sClickIml.mClickIml("compose_test", r2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(Switch r12, CompoundButton compoundButton, boolean z2) {
        this.sClickIml.mClickIml(z2 ? "1" : "2", r12);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_error_export_popu_window;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.btn_cancel);
        TextView textView2 = (TextView) findViewById(R.id.btn_export);
        TextView textView3 = (TextView) findViewById(R.id.btn_compose_test);
        final Switch r3 = (Switch) findViewById(R.id.sw_question_remove);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_remove);
        View viewFindViewById = findViewById(R.id.compose_line);
        if (this.exportEnable) {
            textView2.setEnabled(true);
            textView2.setTextColor(SkinManager.getThemeColor(getContext(), R.attr.first_text_color));
        } else {
            textView2.setEnabled(false);
            textView2.setTextColor(SkinManager.getThemeColor(getContext(), R.attr.forth_txt_color));
        }
        if (this.mType.equals("error")) {
            relativeLayout.setVisibility(0);
            if (TextUtils.isEmpty(UserConfig.getInstance().getUser().getError_set())) {
                UserConfig.getInstance().getUser().setError_set("2");
                UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
            }
            r3.setChecked(UserConfig.getInstance().getUser().getError_set().equals("1"));
        } else {
            relativeLayout.setVisibility(8);
            if (this.mType.equals("note")) {
                textView3.setVisibility(8);
                viewFindViewById.setVisibility(8);
            } else {
                textView3.setVisibility(0);
                viewFindViewById.setVisibility(0);
            }
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.t8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16927c.lambda$onCreate$0(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.u8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16950c.lambda$onCreate$1(r3, view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.v8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16990c.lambda$onCreate$2(r3, view);
            }
        });
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.widget.w8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f17041c.lambda$onCreate$3(r3, compoundButton, z2);
            }
        });
    }

    public ErrorExportPopuWindow(@NonNull Context context, String type, boolean exportEnable, ClickIml sClickIml) {
        super(context);
        this.sClickIml = sClickIml;
        this.mType = type;
        this.exportEnable = exportEnable;
    }
}
