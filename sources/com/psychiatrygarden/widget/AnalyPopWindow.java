package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.lxj.xpopup.core.AttachPopupView;
import com.yikaobang.yixue.R;
import java.util.Arrays;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes6.dex */
public class AnalyPopWindow extends AttachPopupView {
    ImageView buttomimg;
    private com.lxj.xpopup.interfaces.OnSelectListener onSelectListener;
    private String[] strings;
    ImageView topimg;

    public AnalyPopWindow(@NonNull Context context, String[] strings, int[] ints, com.lxj.xpopup.interfaces.OnSelectListener onSelectListener) {
        super(context);
        this.strings = strings;
        this.onSelectListener = onSelectListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPopupContent$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        String str = ServletHandler.__DEFAULT_SERVLET;
        if (i2 != 0) {
            if (i2 == 1) {
                str = "hot";
            } else if (i2 == 2) {
                str = CrashHianalyticsData.TIME;
            } else if (i2 == 3) {
                str = "my_agree";
            }
        }
        this.onSelectListener.onSelect(i2, str);
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPopupContent$1() {
        if (this.isShowUp) {
            this.topimg.setVisibility(8);
            this.buttomimg.setVisibility(0);
        } else {
            this.topimg.setVisibility(0);
            this.buttomimg.setVisibility(8);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_analypop;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        this.topimg = (ImageView) findViewById(R.id.topimg);
        this.buttomimg = (ImageView) findViewById(R.id.buttomimg);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.attachPopupContainer.setElevation(0.0f);
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_analypop_item, Arrays.asList(this.strings)) { // from class: com.psychiatrygarden.widget.AnalyPopWindow.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                TextView textView = (TextView) helper.getView(R.id.tv_text);
                View view = helper.getView(R.id.viewline);
                if (helper.getAdapterPosition() == AnalyPopWindow.this.strings.length - 1) {
                    view.setVisibility(8);
                } else {
                    view.setVisibility(0);
                }
                textView.setText(item);
            }
        };
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.g
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f16496c.lambda$initPopupContent$0(baseQuickAdapter2, view, i2);
            }
        });
        post(new Runnable() { // from class: com.psychiatrygarden.widget.h
            @Override // java.lang.Runnable
            public final void run() {
                this.f16532c.lambda$initPopupContent$1();
            }
        });
    }
}
