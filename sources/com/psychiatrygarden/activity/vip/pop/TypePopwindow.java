package com.psychiatrygarden.activity.vip.pop;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class TypePopwindow extends CenterPopupView {
    private TypeChooseClickIml mListener;

    public interface TypeChooseClickIml {
        void chooseType(String value);
    }

    public TypePopwindow(@NonNull Context context, TypeChooseClickIml listener) {
        super(context);
        this.mListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        this.mListener.chooseType("专硕");
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        this.mListener.chooseType("学硕");
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_pop_type_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tv_zhuan);
        TextView textView2 = (TextView) findViewById(R.id.tv_xue);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14117c.lambda$onCreate$0(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14118c.lambda$onCreate$1(view);
            }
        });
    }
}
