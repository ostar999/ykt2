package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.bean.ReportReason;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ReportReasonPop extends BottomPopupView {
    private BaseQuickAdapter<ReportReason, BaseViewHolder> mAdapter;
    private final OnReasonSelectListener mOnReasonSelectListener;
    private final List<ReportReason> mReportReasons;

    public interface OnReasonSelectListener {
        void onSelect(String reasonId, String title);
    }

    public ReportReasonPop(@NonNull Context context, List<ReportReason> reasonList, OnReasonSelectListener listener) {
        super(context);
        this.mOnReasonSelectListener = listener;
        this.mReportReasons = reasonList;
    }

    private void handleItemClick(int i2) {
        OnReasonSelectListener onReasonSelectListener;
        OnReasonSelectListener onReasonSelectListener2;
        int i3 = 0;
        for (int i4 = 0; i4 < this.mReportReasons.size(); i4++) {
            if (i4 == i2) {
                this.mReportReasons.get(i4).setSelect(!this.mReportReasons.get(i4).isSelect());
                if (this.mReportReasons.get(i4).isSelect() && (onReasonSelectListener2 = this.mOnReasonSelectListener) != null) {
                    onReasonSelectListener2.onSelect(this.mReportReasons.get(i4).getId(), this.mReportReasons.get(i4).getTitle());
                }
            } else {
                this.mReportReasons.get(i4).setSelect(false);
            }
            i3 += this.mReportReasons.get(i4).isSelect() ? 1 : 0;
        }
        if (i3 == 0 && (onReasonSelectListener = this.mOnReasonSelectListener) != null) {
            onReasonSelectListener.onSelect(null, null);
        }
        this.mAdapter.notifyDataSetChanged();
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        handleItemClick(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (view.getId() == R.id.iv_select) {
            handleItemClick(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void beforeDismiss() {
        boolean z2;
        OnReasonSelectListener onReasonSelectListener;
        BaseQuickAdapter<ReportReason, BaseViewHolder> baseQuickAdapter = this.mAdapter;
        if (baseQuickAdapter != null) {
            Iterator<ReportReason> it = baseQuickAdapter.getData().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                }
                ReportReason next = it.next();
                if (next.isSelect()) {
                    OnReasonSelectListener onReasonSelectListener2 = this.mOnReasonSelectListener;
                    if (onReasonSelectListener2 != null) {
                        onReasonSelectListener2.onSelect(next.getId(), next.getTitle());
                    }
                    z2 = true;
                }
            }
            if (!z2 && (onReasonSelectListener = this.mOnReasonSelectListener) != null) {
                onReasonSelectListener.onSelect(null, null);
            }
        }
        super.beforeDismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_report_reason;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvReasons);
        BaseQuickAdapter<ReportReason, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ReportReason, BaseViewHolder>(R.layout.item_report_reason, this.mReportReasons) { // from class: com.psychiatrygarden.widget.ReportReasonPop.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder, ReportReason item) {
                boolean z2 = SkinManager.getCurrentSkinType(getContext()) == 1;
                int i2 = z2 ? R.drawable.ic_report_select_night : R.drawable.ic_report_select_day;
                int i3 = z2 ? R.drawable.ic_report_unselect_night : R.drawable.ic_report_unselect_day;
                BaseViewHolder text = holder.setText(R.id.tv_title, item.getTitle());
                if (!item.isSelect()) {
                    i2 = i3;
                }
                text.setImageResource(R.id.iv_select, i2);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.dg
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f16410c.lambda$onCreate$0(baseQuickAdapter2, view, i2);
            }
        });
        this.mAdapter.addChildClickViewIds(R.id.iv_select);
        this.mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.widget.eg
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f16443c.lambda$onCreate$1(baseQuickAdapter2, view, i2);
            }
        });
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.fg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16491c.lambda$onCreate$2(view);
            }
        });
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            ((ImageView) findViewById(R.id.iv_close)).setImageResource(R.drawable.ic_down_arrow_night);
        }
    }
}
