package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.bean.VideoSummary;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes6.dex */
public class SummaryPointPop extends BottomPopupView {
    private int currentPosition;
    private final OnNodeSelectListener mOnNodeSelectListener;
    private final List<VideoSummary> mSummaryList;

    public interface OnNodeSelectListener {
        void onNodeSelect(int position);
    }

    public SummaryPointPop(@NonNull Context context, List<VideoSummary> summaries, int currentPosition, OnNodeSelectListener listener) {
        super(context);
        this.mOnNodeSelectListener = listener;
        this.mSummaryList = summaries;
        this.currentPosition = currentPosition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
        OnNodeSelectListener onNodeSelectListener = this.mOnNodeSelectListener;
        if (onNodeSelectListener != null) {
            onNodeSelectListener.onNodeSelect(i2);
            List data = baseQuickAdapter.getData();
            int i3 = 0;
            while (i3 < data.size()) {
                ((VideoSummary) data.get(i3)).setCurrentPlay(i3 == i2);
                i3++;
            }
            this.currentPosition = i2;
            baseQuickAdapter2.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
        OnNodeSelectListener onNodeSelectListener = this.mOnNodeSelectListener;
        if (onNodeSelectListener != null) {
            onNodeSelectListener.onNodeSelect(i2);
            List data = baseQuickAdapter.getData();
            int i3 = 0;
            while (i3 < data.size()) {
                ((VideoSummary) data.get(i3)).setCurrentPlay(i3 == i2);
                i3++;
            }
            this.currentPosition = i2;
            baseQuickAdapter2.notifyDataSetChanged();
        }
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_video_knowledge_point;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.hi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16565c.lambda$onCreate$0(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNodes);
        final BaseQuickAdapter<VideoSummary, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VideoSummary, BaseViewHolder>(R.layout.item_knowledge_point) { // from class: com.psychiatrygarden.widget.SummaryPointPop.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder, VideoSummary item) {
                TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.main_theme_color, R.attr.first_txt_color});
                int realPoint = item.getRealPoint();
                int i2 = realPoint / 3600;
                holder.setText(R.id.tv_time, String.format(i2 <= 99 ? "%02d:%02d:%02d" : "%d:%02d:%02d", Integer.valueOf(i2), Integer.valueOf((realPoint % 3600) / 60), Integer.valueOf(realPoint % 60))).setText(R.id.tv_point_title, item.getTitle()).setGone(R.id.tv_operate, true).setTextColor(R.id.tv_time, item.isCurrentPlay() ? typedArrayObtainStyledAttributes.getColor(0, Color.parseColor("#F95843")) : typedArrayObtainStyledAttributes.getColor(1, Color.parseColor("#333333"))).setTextColor(R.id.tv_point_title, item.isCurrentPlay() ? typedArrayObtainStyledAttributes.getColor(0, Color.parseColor("#F95843")) : typedArrayObtainStyledAttributes.getColor(1, Color.parseColor("#333333")));
                ImageView imageView = (ImageView) holder.getView(R.id.iv_status);
                Drawable background = imageView.getBackground();
                if (item.isCurrentPlay()) {
                    imageView.setBackground(getContext().getDrawable(R.drawable.anim_excerpt_play_day));
                    Drawable background2 = imageView.getBackground();
                    if (background2 instanceof AnimationDrawable) {
                        AnimationDrawable animationDrawable = (AnimationDrawable) background2;
                        if (!animationDrawable.isRunning()) {
                            animationDrawable.start();
                        }
                    }
                } else {
                    if (background instanceof AnimationDrawable) {
                        AnimationDrawable animationDrawable2 = (AnimationDrawable) background;
                        if (animationDrawable2.isRunning()) {
                            animationDrawable2.stop();
                        }
                    }
                    imageView.setBackground(null);
                    imageView.setImageResource(R.drawable.ic_point_play_landscape);
                }
                typedArrayObtainStyledAttributes.recycle();
            }
        };
        baseQuickAdapter.addChildClickViewIds(R.id.iv_status);
        baseQuickAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.widget.ii
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f16597c.lambda$onCreate$1(baseQuickAdapter, baseQuickAdapter2, view, i2);
            }
        });
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.ji
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f16627c.lambda$onCreate$2(baseQuickAdapter, baseQuickAdapter2, view, i2);
            }
        });
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setList(this.mSummaryList);
    }
}
