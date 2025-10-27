package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.psychiatrygarden.activity.forum.bean.SearchOptionBean;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class CirclePartShadowPopWindow extends PartShadowPopupView {
    public BaseQuickAdapter<SearchOptionBean.SearchChildBean, BaseViewHolder> adapter;
    public List<SearchOptionBean.SearchChildBean> listData;
    public CirclePartClick mCirclePartClick;
    public RecyclerView recycle;
    public String value;

    /* renamed from: com.psychiatrygarden.widget.CirclePartShadowPopWindow$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<SearchOptionBean.SearchChildBean, BaseViewHolder> {
        public AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(SearchOptionBean.SearchChildBean searchChildBean, View view) {
            CirclePartShadowPopWindow.this.mCirclePartClick.putValue(searchChildBean.getValue(), searchChildBean.getLabel());
            CirclePartShadowPopWindow.this.dismiss();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder baseViewHolder, final SearchOptionBean.SearchChildBean partShadowBean) {
            TextView textView = (TextView) baseViewHolder.getView(R.id.name);
            textView.setText(partShadowBean.getLabel());
            if (CirclePartShadowPopWindow.this.value.equals(partShadowBean.getValue())) {
                textView.setSelected(true);
            } else {
                textView.setSelected(false);
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.t2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16920c.lambda$convert$0(partShadowBean, view);
                }
            });
        }
    }

    public interface CirclePartClick {
        void dismiss();

        void putValue(String value, String label);
    }

    public CirclePartShadowPopWindow(@NonNull Context context, List<SearchOptionBean.SearchChildBean> listData, String value, CirclePartClick mCirclePartClick) {
        super(context);
        new ArrayList();
        this.listData = listData;
        this.value = value;
        this.mCirclePartClick = mCirclePartClick;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
        this.mCirclePartClick.dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_circle_part;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        this.recycle = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.layout_partshadow_item, this.listData);
        this.adapter = anonymousClass1;
        this.recycle.setAdapter(anonymousClass1);
    }
}
