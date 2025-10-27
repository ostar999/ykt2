package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ComCenterPop extends CenterPopupView {
    private String contentstr;
    private ComPopInterLisnter mComPopInterLisnter;
    private Context mContext;
    private TextView ok;
    private String titlestr;

    public interface ComPopInterLisnter {
        void mComPopInterLisnter();
    }

    public ComCenterPop(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        this.mComPopInterLisnter.mComPopInterLisnter();
        dismiss();
    }

    public String getContentstr() {
        return this.contentstr;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_comcenterpop;
    }

    public String getTitlestr() {
        return this.titlestr;
    }

    public ComPopInterLisnter getmComPopInterLisnter() {
        return this.mComPopInterLisnter;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.content);
        this.ok = (TextView) findViewById(R.id.ok);
        textView.setText(this.contentstr);
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.i3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16576c.lambda$onCreate$0(view);
            }
        });
    }

    public void setContentstr(String contentstr) {
        this.contentstr = contentstr;
    }

    public void setTitlestr(String titlestr) {
        this.titlestr = titlestr;
    }

    public void setmComPopInterLisnter(ComPopInterLisnter mComPopInterLisnter) {
        this.mComPopInterLisnter = mComPopInterLisnter;
    }

    public ComCenterPop(@NonNull Context context, Context mContext, String titlestr, String contentstr) {
        super(context);
        this.mContext = mContext;
        this.titlestr = titlestr;
        this.contentstr = contentstr;
    }
}
