package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes6.dex */
public class ButtomContactPopwindow extends BottomPopupView {
    public Activity activity;
    public BaseQuickAdapter<OnlineServiceBean, BaseViewHolder> adapter;
    public List<OnlineServiceBean> css;
    public RecyclerView recyview;

    /* renamed from: com.psychiatrygarden.widget.ButtomContactPopwindow$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<OnlineServiceBean, BaseViewHolder> {
        public AnonymousClass1(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(OnlineServiceBean onlineServiceBean, View view) {
            ButtomContactPopwindow.this.dismiss();
            CommonUtil.onlineService(ButtomContactPopwindow.this.activity, onlineServiceBean);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, final OnlineServiceBean item) {
            TextView textView = (TextView) holder.getView(R.id.name);
            TextView textView2 = (TextView) holder.getView(R.id.dyail);
            textView.setText(item.getCs_name());
            textView2.setText(item.getCs_desc());
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.b0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16328c.lambda$convert$0(item, view);
                }
            });
        }
    }

    public ButtomContactPopwindow(@NonNull Context context, List<OnlineServiceBean> css) {
        super(context);
        this.css = css;
        this.activity = (Activity) context;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_buttom_contact;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyview);
        this.recyview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.layout_buttom_contact_item);
        this.adapter = anonymousClass1;
        this.recyview.setAdapter(anonymousClass1);
        this.adapter.setList(this.css);
    }
}
