package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.psychiatrygarden.adapter.TopicDragAdapter;
import com.psychiatrygarden.bean.TopicListBean;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class CircleTopicHeaderPopWindow extends PartShadowPopupView {
    public TopicDragAdapter adapter;
    public List<TopicListBean.DataDTO> data;
    public CirclePartClick mCirclePartClick;
    public RecyclerView recycle;
    public String value;

    public interface CirclePartClick {
        void dismiss(List<TopicListBean.DataDTO> data);

        void putValue(String value, String label);
    }

    public CircleTopicHeaderPopWindow(@NonNull Context context, List<TopicListBean.DataDTO> data, String value, CirclePartClick mCirclePartClick) {
        super(context);
        new ArrayList();
        this.data = data;
        this.value = value;
        this.mCirclePartClick = mCirclePartClick;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
        this.mCirclePartClick.dismiss(this.adapter.getData());
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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        TopicDragAdapter topicDragAdapter = new TopicDragAdapter(R.layout.layout_partshadow_item, this.data, this.value);
        this.adapter = topicDragAdapter;
        this.recycle.setAdapter(topicDragAdapter);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.CircleTopicHeaderPopWindow.1
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                CircleTopicHeaderPopWindow circleTopicHeaderPopWindow = CircleTopicHeaderPopWindow.this;
                circleTopicHeaderPopWindow.mCirclePartClick.putValue(circleTopicHeaderPopWindow.data.get(position).getId(), CircleTopicHeaderPopWindow.this.data.get(position).getName());
                CircleTopicHeaderPopWindow.this.dismiss();
            }
        });
        this.adapter.setOnItemLongClickListener(new OnItemLongClickListener() { // from class: com.psychiatrygarden.widget.CircleTopicHeaderPopWindow.2
            @Override // com.chad.library.adapter.base.listener.OnItemLongClickListener
            public boolean onItemLongClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                if ("全部".equals(CircleTopicHeaderPopWindow.this.data.get(position).getName()) || CircleTopicHeaderPopWindow.this.data.get(position).getIs_default() == 1) {
                    adapter.getDraggableModule().setDragEnabled(false);
                    adapter.getDraggableModule().setSwipeEnabled(false);
                } else {
                    adapter.getDraggableModule().setDragEnabled(true);
                    adapter.getDraggableModule().setSwipeEnabled(true);
                }
                return false;
            }
        });
    }
}
