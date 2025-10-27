package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.psychiatrygarden.activity.online.bean.SearchHeaderBean;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SearchPartShadowPopWindow extends PartShadowPopupView {
    public BaseQuickAdapter<SearchHeaderBean.DataDTO.QsBankDTO, BaseViewHolder> adapter;
    public RecyclerView labelrecy;
    public List<SearchHeaderBean.DataDTO.QsBankDTO> listData;
    public ShadowPartClick mCirclePartClick;
    public BaseQuickAdapter<SearchHeaderBean.DataDTO.QsBankDTO, BaseViewHolder> mLeftadapter;
    public String name;
    public RecyclerView recycle;
    public int type;
    public String value;

    public interface ShadowPartClick {
        void dismiss();

        void putValue(String value, String label);
    }

    public SearchPartShadowPopWindow(@NonNull Context context, List<SearchHeaderBean.DataDTO.QsBankDTO> listData, String value, ShadowPartClick mCirclePartClick, int type, String name) {
        super(context);
        new ArrayList();
        this.listData = listData;
        this.value = value;
        this.mCirclePartClick = mCirclePartClick;
        this.type = type;
        this.name = name;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
        this.mCirclePartClick.dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_search_part;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.labelrecy = (RecyclerView) findViewById(R.id.labelrecy);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        this.recycle = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.labelrecy.setLayoutManager(new LinearLayoutManager(getContext()));
        BaseQuickAdapter<SearchHeaderBean.DataDTO.QsBankDTO, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SearchHeaderBean.DataDTO.QsBankDTO, BaseViewHolder>(R.layout.layout_search_part_item) { // from class: com.psychiatrygarden.widget.SearchPartShadowPopWindow.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, final SearchHeaderBean.DataDTO.QsBankDTO partShadowBean) {
                ((TextView) baseViewHolder.getView(R.id.label)).setText(partShadowBean.getLabel());
                ImageView imageView = (ImageView) baseViewHolder.getView(R.id.duigou);
                RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.relview);
                if (SearchPartShadowPopWindow.this.value.equals(partShadowBean.getValue())) {
                    imageView.setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                }
                relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.SearchPartShadowPopWindow.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        SearchPartShadowPopWindow.this.mCirclePartClick.putValue(partShadowBean.getValue(), SearchPartShadowPopWindow.this.labelrecy.getVisibility() == 0 ? SearchPartShadowPopWindow.this.name : partShadowBean.getLabel());
                        SearchPartShadowPopWindow.this.dismiss();
                    }
                });
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycle.setAdapter(baseQuickAdapter);
        BaseQuickAdapter<SearchHeaderBean.DataDTO.QsBankDTO, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<SearchHeaderBean.DataDTO.QsBankDTO, BaseViewHolder>(R.layout.layout_search_part_parent) { // from class: com.psychiatrygarden.widget.SearchPartShadowPopWindow.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, final SearchHeaderBean.DataDTO.QsBankDTO partShadowBean) {
                TextView textView = (TextView) helper.getView(R.id.title);
                TextView textView2 = (TextView) helper.getView(R.id.viewimg);
                RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.forumrel);
                textView.setText(partShadowBean.getLabel());
                if (SearchPartShadowPopWindow.this.name.equals(partShadowBean.getLabel())) {
                    relativeLayout.setSelected(true);
                    textView.setSelected(true);
                    textView2.setVisibility(0);
                } else {
                    relativeLayout.setSelected(false);
                    textView.setSelected(false);
                    textView2.setVisibility(4);
                }
                relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.SearchPartShadowPopWindow.2.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        SearchPartShadowPopWindow.this.name = partShadowBean.getLabel();
                        SearchPartShadowPopWindow.this.adapter.setList(partShadowBean.getChildren());
                        notifyDataSetChanged();
                    }
                });
            }
        };
        this.mLeftadapter = baseQuickAdapter2;
        this.labelrecy.setAdapter(baseQuickAdapter2);
        int i2 = this.type;
        if (i2 == 0) {
            this.labelrecy.setVisibility(8);
            this.recycle.setVisibility(0);
            this.adapter.setList(this.listData);
        } else if (i2 == 1) {
            this.labelrecy.setVisibility(0);
            this.recycle.setVisibility(0);
            this.mLeftadapter.setList(this.listData);
            for (int i3 = 0; i3 < this.listData.size(); i3++) {
                if (this.name.equals(this.listData.get(i3).getLabel())) {
                    this.adapter.setList(this.listData.get(i3).getChildren());
                    return;
                }
            }
            this.adapter.setList(this.listData.get(0).getChildren());
        }
    }
}
