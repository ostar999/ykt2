package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DailyTaskPop extends CenterPopupView {
    private ShareListener mShareListener;
    private String type;

    public interface ShareListener {
        void mShareCloseListener();

        void mShareDataListener(String type);
    }

    public DailyTaskPop(@NonNull Context context, String type) {
        super(context);
        this.type = type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        this.mShareListener.mShareDataListener(this.type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        this.mShareListener.mShareCloseListener();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_daily_task_pop;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        return ScreenUtil.getScreenWidth(ProjectApp.instance().getTopActivity()) - DpOrPxUtils.dip2px(getContext(), 80.0f);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.close);
        TextView textView = (TextView) findViewById(R.id.tvSubmit);
        if ("1".equals(this.type)) {
            textView.setText("开启会员 定制个人专属规划");
        } else {
            textView.setText("制定新规划");
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.n5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16728c.lambda$onCreate$0(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.o5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16753c.lambda$onCreate$1(view);
            }
        });
    }

    public void setShareListener(ShareListener mShareListener) {
        this.mShareListener = mShareListener;
    }
}
