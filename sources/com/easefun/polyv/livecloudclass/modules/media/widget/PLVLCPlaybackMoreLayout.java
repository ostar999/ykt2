package com.easefun.polyv.livecloudclass.modules.media.widget;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.download.layout.PLVLCPlaybackCachePopupLayout;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheVideoViewModel;
import com.easefun.polyv.livecommon.module.utils.PLVViewInitUtils;
import com.easefun.polyv.livecommon.ui.widget.PLVOrientationSensibleLinearLayout;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCPlaybackMoreLayout {
    private View anchor;
    private ViewGroup containerLy;
    private TextView landscapeSpeedTv;
    private PLVOrientationSensibleLinearLayout llMoreVertical;
    private ViewGroup llSpeed;
    private OnSpeedSelectedListener onSpeedSelectedListener;
    private LinearLayout playbackCacheLl;
    private PLVLCPlaybackCachePopupLayout playbackCachePopupLayout;
    private PopupWindow popupWindow;
    private int portraitHeight;
    private TextView portraitSpeedTv;
    private View root;
    private RvSpeedAdapter rvAdapter;
    private RecyclerView rvSpeed;
    private PLVOrientationSensibleLinearLayout speedTipsContainerLy;
    private View speedTipsLy;
    private TextView speedTipsTv;
    private List<Float> speedVO = new ArrayList();
    private Handler handler = new Handler(Looper.getMainLooper());

    public interface OnSpeedSelectedListener {
        void onSpeedSelected(Float f2, int i2);
    }

    public class RvSpeedAdapter extends RecyclerView.Adapter<RvMoreViewHolder> {
        private int curSelectPos;
        private List<Float> speedVO;

        public class RvMoreViewHolder extends RecyclerView.ViewHolder {
            TextView tvSpeed;

            public RvMoreViewHolder(View view) {
                super(view);
                this.tvSpeed = (TextView) view.findViewById(R.id.tv_bitrate);
            }
        }

        private RvSpeedAdapter() {
            this.curSelectPos = -1;
        }

        public int getCurSelectPos() {
            return this.curSelectPos;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            List<Float> list = this.speedVO;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        public void updateSpeedListData(List<Float> list, int i2) {
            if (list == null) {
                list = new ArrayList<>();
            }
            this.speedVO = list;
            this.curSelectPos = i2;
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull final RvMoreViewHolder rvMoreViewHolder, int i2) {
            final String str = this.speedVO.get(i2) + "x";
            rvMoreViewHolder.tvSpeed.setText(str);
            rvMoreViewHolder.tvSpeed.setSelected(i2 == this.curSelectPos);
            rvMoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.RvSpeedAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (rvMoreViewHolder.getAdapterPosition() == RvSpeedAdapter.this.curSelectPos) {
                        return;
                    }
                    RvSpeedAdapter.this.curSelectPos = rvMoreViewHolder.getAdapterPosition();
                    RvSpeedAdapter.this.notifyDataSetChanged();
                    if (PLVLCPlaybackMoreLayout.this.onSpeedSelectedListener != null) {
                        PLVLCPlaybackMoreLayout.this.onSpeedSelectedListener.onSpeedSelected((Float) RvSpeedAdapter.this.speedVO.get(RvSpeedAdapter.this.curSelectPos), RvSpeedAdapter.this.curSelectPos);
                    }
                    PLVLCPlaybackMoreLayout.this.rvSpeed.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.RvSpeedAdapter.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVLCPlaybackMoreLayout.this.showSpeedTips(ScreenUtils.isPortrait(), str);
                        }
                    });
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public RvMoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
            return new RvMoreViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plvlc_live_controller_bitrate_item, viewGroup, false));
        }
    }

    public PLVLCPlaybackMoreLayout(View view) {
        this.anchor = view;
        this.speedVO.add(Float.valueOf(0.5f));
        this.speedVO.add(Float.valueOf(1.0f));
        this.speedVO.add(Float.valueOf(1.5f));
        this.speedVO.add(Float.valueOf(2.0f));
        if (this.popupWindow == null) {
            this.popupWindow = new PopupWindow(view.getContext());
            this.root = PLVViewInitUtils.initPopupWindow(view, R.layout.plvlc_playback_controller_more_layout, this.popupWindow, new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    PLVLCPlaybackMoreLayout.this.hide();
                }
            });
            initView();
        }
    }

    private void initView() {
        ViewGroup viewGroup = (ViewGroup) this.root.findViewById(R.id.container_ly);
        this.containerLy = viewGroup;
        viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCPlaybackMoreLayout.this.hide();
            }
        });
        PLVOrientationSensibleLinearLayout pLVOrientationSensibleLinearLayout = (PLVOrientationSensibleLinearLayout) this.root.findViewById(R.id.ll_more_vertical);
        this.llMoreVertical = pLVOrientationSensibleLinearLayout;
        pLVOrientationSensibleLinearLayout.setOnLandscape(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.3
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCPlaybackMoreLayout.this.popupWindow != null && PLVLCPlaybackMoreLayout.this.popupWindow.isShowing()) {
                    PLVLCPlaybackMoreLayout.this.popupWindow.update();
                }
                PLVLCPlaybackMoreLayout.this.onLandscape();
            }
        });
        this.llMoreVertical.setOnPortrait(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.4
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCPlaybackMoreLayout.this.portraitHeight == 0) {
                    PLVLCPlaybackMoreLayout.this.hide();
                    return;
                }
                if (PLVLCPlaybackMoreLayout.this.popupWindow != null && PLVLCPlaybackMoreLayout.this.popupWindow.isShowing()) {
                    PLVLCPlaybackMoreLayout.this.popupWindow.update();
                }
                PLVLCPlaybackMoreLayout.this.onPortrait();
            }
        });
        this.rvSpeed = (RecyclerView) this.root.findViewById(R.id.rv_more_speed);
        RvSpeedAdapter rvSpeedAdapter = new RvSpeedAdapter();
        this.rvAdapter = rvSpeedAdapter;
        this.rvSpeed.setAdapter(rvSpeedAdapter);
        this.rvSpeed.setLayoutManager(new LinearLayoutManager(this.root.getContext(), 0, false));
        this.llSpeed = (ViewGroup) this.root.findViewById(R.id.fl_speed);
        this.portraitSpeedTv = (TextView) this.root.findViewById(R.id.portrait_speed_tv);
        this.landscapeSpeedTv = (TextView) this.root.findViewById(R.id.landscape_speed_tv);
        this.playbackCacheLl = (LinearLayout) this.root.findViewById(R.id.plvlc_playback_cache_ll);
        this.playbackCachePopupLayout = new PLVLCPlaybackCachePopupLayout(this.root.getContext());
        this.playbackCacheLl.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCPlaybackMoreLayout.this.playbackCachePopupLayout.show();
                PLVLCPlaybackMoreLayout.this.hide();
            }
        });
        observePlaybackCacheEnable((LifecycleOwner) this.root.getContext());
    }

    private void observePlaybackCacheEnable(@NonNull LifecycleOwner lifecycleOwner) {
        ((PLVPlaybackCacheVideoViewModel) PLVDependManager.getInstance().get(PLVPlaybackCacheVideoViewModel.class)).getPlaybackCacheUpdateLiveData().observe(lifecycleOwner, new Observer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
                if (pLVPlaybackCacheVideoVO == null) {
                    return;
                }
                PLVLCPlaybackMoreLayout.this.playbackCacheLl.setVisibility(pLVPlaybackCacheVideoVO.isEnableDownload() != null && pLVPlaybackCacheVideoVO.isEnableDownload().booleanValue() ? 0 : 8);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLandscape() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.containerLy.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -1;
        layoutParams.gravity = 5;
        this.containerLy.setLayoutParams(layoutParams);
        this.containerLy.setBackgroundColor(Color.parseColor("#CC000000"));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.rvSpeed.getLayoutParams();
        layoutParams2.leftMargin = 0;
        this.rvSpeed.setLayoutParams(layoutParams2);
        this.portraitSpeedTv.setVisibility(8);
        if (this.llSpeed.getVisibility() == 0) {
            this.landscapeSpeedTv.setVisibility(0);
        } else {
            this.landscapeSpeedTv.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPortrait() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.containerLy.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = this.portraitHeight;
        layoutParams.gravity = 0;
        this.containerLy.setLayoutParams(layoutParams);
        this.containerLy.setBackgroundColor(Color.parseColor("#D8000000"));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.rvSpeed.getLayoutParams();
        layoutParams2.leftMargin = ConvertUtils.dp2px(24.0f);
        this.rvSpeed.setLayoutParams(layoutParams2);
        this.portraitSpeedTv.setVisibility(0);
        this.landscapeSpeedTv.setVisibility(8);
    }

    private void show(boolean z2) {
        show(z2, this.root);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSpeedTips(boolean z2, String str) {
        View view;
        if (this.speedTipsLy == null && (view = this.anchor) != null) {
            View viewInflate = LayoutInflater.from(view.getContext()).inflate(R.layout.plvlc_tips_view_speed, (ViewGroup) null, false);
            this.speedTipsLy = viewInflate;
            PLVOrientationSensibleLinearLayout pLVOrientationSensibleLinearLayout = (PLVOrientationSensibleLinearLayout) viewInflate.findViewById(R.id.speed_tips_container_ly);
            this.speedTipsContainerLy = pLVOrientationSensibleLinearLayout;
            pLVOrientationSensibleLinearLayout.setOnLandscape(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.7
                @Override // java.lang.Runnable
                public void run() {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) PLVLCPlaybackMoreLayout.this.speedTipsContainerLy.getLayoutParams();
                    layoutParams.height = -1;
                    PLVLCPlaybackMoreLayout.this.speedTipsContainerLy.setLayoutParams(layoutParams);
                }
            });
            this.speedTipsContainerLy.setOnPortrait(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.8
                @Override // java.lang.Runnable
                public void run() {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) PLVLCPlaybackMoreLayout.this.speedTipsContainerLy.getLayoutParams();
                    layoutParams.height = PLVLCPlaybackMoreLayout.this.portraitHeight;
                    PLVLCPlaybackMoreLayout.this.speedTipsContainerLy.setLayoutParams(layoutParams);
                }
            });
            this.speedTipsContainerLy.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    PLVLCPlaybackMoreLayout.this.hide();
                }
            });
            this.speedTipsTv = (TextView) this.speedTipsLy.findViewById(R.id.speed_tips_tv);
        }
        PLVOrientationSensibleLinearLayout pLVOrientationSensibleLinearLayout2 = this.speedTipsContainerLy;
        if (pLVOrientationSensibleLinearLayout2 != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) pLVOrientationSensibleLinearLayout2.getLayoutParams();
            layoutParams.height = z2 ? this.portraitHeight : -1;
            this.speedTipsContainerLy.setLayoutParams(layoutParams);
        }
        TextView textView = this.speedTipsTv;
        if (textView != null) {
            textView.setText(str);
        }
        show(z2, this.speedTipsLy);
        this.handler.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCPlaybackMoreLayout.10
            @Override // java.lang.Runnable
            public void run() {
                PLVLCPlaybackMoreLayout.this.hide();
            }
        }, 500L);
    }

    public void hide() {
        this.handler.removeCallbacksAndMessages(null);
        PopupWindow popupWindow = this.popupWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public void initSpeed(List<Float> list, int i2) {
        this.rvAdapter.updateSpeedListData(list, i2);
        showSpeed(true);
    }

    public void setOnSpeedSelectedListener(OnSpeedSelectedListener onSpeedSelectedListener) {
        this.onSpeedSelectedListener = onSpeedSelectedListener;
    }

    public void showSpeed(boolean z2) {
        if (!z2 || this.rvAdapter.getItemCount() <= 1) {
            this.llSpeed.setVisibility(8);
            if (ScreenUtils.isLandscape()) {
                this.landscapeSpeedTv.setVisibility(8);
                return;
            }
            return;
        }
        this.llSpeed.setVisibility(0);
        if (ScreenUtils.isLandscape()) {
            this.landscapeSpeedTv.setVisibility(0);
        }
    }

    public void showWhenLandscape() {
        onLandscape();
        show(false);
    }

    public void showWhenPortrait(int i2) {
        this.portraitHeight = i2;
        onPortrait();
        show(true);
    }

    public void updateViewWithPlayInfo(float f2) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.speedVO.size()) {
                i2 = 1;
                break;
            } else if (this.speedVO.get(i2).floatValue() == f2) {
                break;
            } else {
                i2++;
            }
        }
        initSpeed(this.speedVO, i2);
    }

    private void show(boolean z2, View view) {
        PopupWindow popupWindow = this.popupWindow;
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                hide();
            }
            this.popupWindow.setContentView(view);
            this.popupWindow.showAtLocation(this.anchor, 0, 0, 0);
        }
    }
}
