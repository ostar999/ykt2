package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.DrawerPopupView;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.VideoSummary;
import com.psychiatrygarden.event.RefreshVideoSummaryQuestionDoEvent;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes6.dex */
public class SummaryPointLandScapePop extends DrawerPopupView {
    BaseQuickAdapter<VideoSummary, BaseViewHolder> mAdapter;
    private final OnNodeSelectListener mOnNodeSelectListener;
    private final List<VideoSummary> mSummaryList;
    private final String title;

    /* renamed from: com.psychiatrygarden.widget.SummaryPointLandScapePop$1, reason: invalid class name */
    public class AnonymousClass1 extends CommAdapter<VideoSummary> {
        public AnonymousClass1(List mData, Context mcontext, int layoutId) {
            super(mData, mcontext, layoutId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(int i2, View view) {
            if (view.getId() == R.id.tv_operate) {
                if (SummaryPointLandScapePop.this.mOnNodeSelectListener != null) {
                    SummaryPointLandScapePop.this.mOnNodeSelectListener.doQuestion((VideoSummary) SummaryPointLandScapePop.this.mSummaryList.get(i2));
                }
            } else if (SummaryPointLandScapePop.this.mOnNodeSelectListener != null) {
                SummaryPointLandScapePop.this.mOnNodeSelectListener.onNodeSelect(i2);
                int i3 = 0;
                while (i3 < SummaryPointLandScapePop.this.mSummaryList.size()) {
                    ((VideoSummary) SummaryPointLandScapePop.this.mSummaryList.get(i3)).setCurrentPlay(i3 == i2);
                    i3++;
                }
                notifyDataSetChanged();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(int i2, View view) {
            if (SummaryPointLandScapePop.this.mOnNodeSelectListener != null) {
                SummaryPointLandScapePop.this.mOnNodeSelectListener.onNodeSelect(i2);
                int i3 = 0;
                while (i3 < SummaryPointLandScapePop.this.mSummaryList.size()) {
                    ((VideoSummary) SummaryPointLandScapePop.this.mSummaryList.get(i3)).setCurrentPlay(i3 == i2);
                    i3++;
                }
                notifyDataSetChanged();
            }
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, VideoSummary item, final int position) {
            CheckedTextView checkedTextView = (CheckedTextView) vHolder.getView(R.id.tv_operate);
            checkedTextView.setVisibility(!TextUtils.isEmpty(item.getQuestionId()) ? 0 : 8);
            checkedTextView.setText("1".equals(item.getIsDo()) ? "回顾" : "练习");
            checkedTextView.setChecked(!"1".equals(item.getIsDo()));
            int realPoint = item.getRealPoint() / 1000;
            int i2 = realPoint / 3600;
            int i3 = (realPoint % 3600) / 60;
            int i4 = realPoint % 60;
            vHolder.getConvertView().setBackground(new ColorDrawable(item.isCurrentPlay() ? Color.parseColor("#222222") : 0));
            vHolder.setText(R.id.tv_time, String.format(i2 <= 99 ? "%02d:%02d:%02d" : "%d:%02d:%02d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4))).setText(R.id.tv_point_title, item.getTitle()).setTextColor(R.id.tv_time, item.isCurrentPlay() ? -1 : Color.parseColor("#7B7E83")).setTextColor(R.id.tv_point_title, item.isCurrentPlay() ? -1 : Color.parseColor("#7B7E83"));
            ImageView imageView = (ImageView) vHolder.getView(R.id.iv_status);
            Drawable background = imageView.getBackground();
            if (item.isCurrentPlay()) {
                imageView.setBackground(SummaryPointLandScapePop.this.getContext().getDrawable(R.drawable.anim_play_knowledge_landscape));
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
                imageView.setBackground(SummaryPointLandScapePop.this.getContext().getDrawable(R.drawable.ic_point_play_landscape));
            }
            vHolder.getView(R.id.tv_operate).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.fi
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16493c.lambda$convert$0(position, view);
                }
            });
            vHolder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.gi
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16527c.lambda$convert$1(position, view);
                }
            });
        }
    }

    public interface OnNodeSelectListener {
        void doQuestion(VideoSummary item);

        void onNodeSelect(int position);

        void redoAllQuestion(List<String> questionIds, AliPlayerVideoPlayActivity.RedoQuestionSuccessListener l2);
    }

    public SummaryPointLandScapePop(@NonNull Context context, List<VideoSummary> summaries, String title, OnNodeSelectListener listener) {
        super(context);
        this.mOnNodeSelectListener = listener;
        this.mSummaryList = summaries;
        this.title = title;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(CommAdapter commAdapter) {
        Iterator<VideoSummary> it = this.mSummaryList.iterator();
        while (it.hasNext()) {
            it.next().setIsDo("0");
        }
        commAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(List list, AliPlayerVideoPlayActivity.RedoQuestionSuccessListener redoQuestionSuccessListener, View view) {
        OnNodeSelectListener onNodeSelectListener = this.mOnNodeSelectListener;
        if (onNodeSelectListener != null) {
            onNodeSelectListener.redoAllQuestion(list, redoQuestionSuccessListener);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_layout_landscape_knowledge_point;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ci
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16382c.lambda$onCreate$0(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.tv_video_title);
        textView.setText(this.title);
        if (TextUtils.isEmpty(this.title)) {
            textView.setVisibility(8);
        }
        View viewFindViewById = findViewById(R.id.ll_redo);
        final ArrayList arrayList = new ArrayList();
        for (VideoSummary videoSummary : this.mSummaryList) {
            if (!TextUtils.isEmpty(videoSummary.getQuestionId())) {
                arrayList.add(videoSummary.getQuestionId());
            }
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.mSummaryList, getContext(), R.layout.item_knowledge_point_landscape);
        listView.setAdapter((ListAdapter) anonymousClass1);
        final AliPlayerVideoPlayActivity.RedoQuestionSuccessListener redoQuestionSuccessListener = new AliPlayerVideoPlayActivity.RedoQuestionSuccessListener() { // from class: com.psychiatrygarden.widget.di
            @Override // com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.RedoQuestionSuccessListener
            public final void onSuccess() {
                this.f16412a.lambda$onCreate$1(anonymousClass1);
            }
        };
        if (arrayList.size() > 0) {
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ei
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16445c.lambda$onCreate$2(arrayList, redoQuestionSuccessListener, view);
                }
            });
        }
        viewFindViewById.setVisibility(arrayList.isEmpty() ? 8 : 0);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(RefreshVideoSummaryQuestionDoEvent event) {
        BaseQuickAdapter<VideoSummary, BaseViewHolder> baseQuickAdapter = this.mAdapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
        EventBus.getDefault().register(this);
    }
}
