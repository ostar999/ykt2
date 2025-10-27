package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ComputerMockPopTipsDialog extends CenterPopupView {
    private SpannableStringBuilder content;
    public TextView ok;
    public ClickIml sClickIml;
    private String title;

    public interface ClickIml {
        void mClickIml();
    }

    public ComputerMockPopTipsDialog(@NonNull Context context, ClickIml sClickIml, SpannableStringBuilder content, String title) {
        super(context);
        this.sClickIml = sClickIml;
        this.content = content;
        this.title = title;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
        this.sClickIml.mClickIml();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_computer_pop_tips;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.isEmpty(this.content)) {
            ((TextView) findViewById(R.id.tv_content)).setText(this.content);
        }
        if (!TextUtils.isEmpty(this.title)) {
            ((TextView) findViewById(R.id.tv_top_hint)).setText(this.title);
        }
        this.ok = (TextView) findViewById(R.id.ok);
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        if (this.content.length() > 50) {
            nestedScrollView.getLayoutParams().height = ScreenUtil.getPxByDp(getContext(), 128);
        }
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.t3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16922c.lambda$onCreate$0(view);
            }
        });
    }
}
