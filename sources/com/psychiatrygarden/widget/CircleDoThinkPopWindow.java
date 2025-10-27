package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CircleDoThinkPopWindow extends CenterPopupView {
    private String argeeNum;
    private EditText fanduied;
    private Context mContext;
    private onClickIMl monClickIMl;
    private String opptionNum;
    private EditText zantonged;

    public interface onClickIMl {
        void onClickMethod(String argeeNum, String opptionNum);
    }

    public CircleDoThinkPopWindow(@NonNull Context context, String argeeNum, String opptionNum, onClickIMl monClickIMl) {
        super(context);
        this.argeeNum = argeeNum;
        this.opptionNum = opptionNum;
        this.monClickIMl = monClickIMl;
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        if (TextUtils.isEmpty(this.zantonged.getText().toString())) {
            ToastUtil.shortToast(this.mContext, "赞同数不能为空");
        } else if (TextUtils.isEmpty(this.fanduied.getText().toString())) {
            ToastUtil.shortToast(this.mContext, "反对数不能为空");
        } else {
            this.monClickIMl.onClickMethod(this.zantonged.getText().toString(), this.fanduied.getText().toString());
            dismiss();
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.activity_circle_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.zantonged = (EditText) findViewById(R.id.zantonged);
        this.fanduied = (EditText) findViewById(R.id.fanduied);
        TextView textView = (TextView) findViewById(R.id.buttom);
        this.zantonged.setText(this.argeeNum);
        this.fanduied.setText(this.opptionNum);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.n2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16725c.lambda$onCreate$0(view);
            }
        });
    }
}
