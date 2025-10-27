package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.adapter.TopicDragAdapter;
import com.psychiatrygarden.bean.TopicListBean;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class SelectTopicPopWindow extends BottomPopupView {
    public TopicDragAdapter Tadapter;
    public ImageView closeimg;
    public List<TopicListBean.DataDTO> data;
    public SelecTopicIml mSelecTopicIml;
    public RecyclerView recyclerView;
    public String value;

    public interface SelecTopicIml {
        void dismissOnData(List<TopicListBean.DataDTO> dataList);

        void selecTopicData(List<TopicListBean.DataDTO> dataList, int p2);
    }

    public SelectTopicPopWindow(@NonNull Context context, List<TopicListBean.DataDTO> data, String value, SelecTopicIml mSelecTopicIml) {
        super(context);
        new ArrayList();
        this.data = data;
        this.value = value;
        this.mSelecTopicIml = mSelecTopicIml;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
        this.mSelecTopicIml.dismissOnData(this.Tadapter.getData());
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_select_topic_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.closeimg = (ImageView) findViewById(R.id.closeimg);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        TopicDragAdapter topicDragAdapter = new TopicDragAdapter(R.layout.layout_select_topic_item, this.data, this.value);
        this.Tadapter = topicDragAdapter;
        this.recyclerView.setAdapter(topicDragAdapter);
        this.closeimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.SelectTopicPopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                SelectTopicPopWindow.this.dismiss();
            }
        });
        this.Tadapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.SelectTopicPopWindow.2
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                List<TopicListBean.DataDTO> list = SelectTopicPopWindow.this.data;
                if (list == null || list.size() <= 0) {
                    return;
                }
                SelectTopicPopWindow.this.dismiss();
                SelectTopicPopWindow selectTopicPopWindow = SelectTopicPopWindow.this;
                selectTopicPopWindow.mSelecTopicIml.selecTopicData(selectTopicPopWindow.Tadapter.getData(), position);
            }
        });
    }
}
