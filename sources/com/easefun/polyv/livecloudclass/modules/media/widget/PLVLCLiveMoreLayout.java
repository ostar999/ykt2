package com.easefun.polyv.livecloudclass.modules.media.widget;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.businesssdk.model.video.PolyvDefinitionVO;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.utils.PLVViewInitUtils;
import com.easefun.polyv.livecommon.ui.widget.PLVOrientationSensibleLinearLayout;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCLiveMoreLayout implements View.OnClickListener {
    private View anchor;
    private FrameLayout containerLy;
    private boolean isAudioMode;
    private boolean isLowLatency;
    private PLVOrientationSensibleLinearLayout llMoreVertical;
    private OnBitrateSelectedListener onBitrateSelectedListener;
    private OnChangeLowLatencyListener onChangeLowLatencyListener;
    private OnLinesSelectedListener onLinesSelectedListener;
    private OnOnlyAudioSwitchListener onOnlyAudioSwitchListener;
    private LinearLayout plvlcLiveControlMoreBitrateLl;
    private RecyclerView plvlcLiveControlMoreBitrateRv;
    private TextView plvlcLiveControlMoreBitrateTv;
    private LinearLayout plvlcLiveControlMoreLatencyLl;
    private RecyclerView plvlcLiveControlMoreLatencyRv;
    private TextView plvlcLiveControlMoreLatencyTv;
    private LinearLayout plvlcLiveControlMoreLinesLl;
    private RecyclerView plvlcLiveControlMoreLinesRv;
    private TextView plvlcLiveControlMoreLinesTv;
    private LinearLayout plvlcLiveControlMoreModeLl;
    private LinearLayout plvlcLiveControlMoreModeSwitchLl;
    private TextView plvlcLiveControlMoreModeTv;
    private PopupWindow popupWindow;
    private int portraitHeight;
    private View root;
    private RvBitrateAdapter rvBitrateAdapter;
    private RvLatencyAdapter rvLatencyAdapter;
    private RvLinesAdapter rvLinesAdapter;
    private boolean shouldShowBitrateLayout;
    private boolean shouldShowLinesLayout;
    private TextView tvOnlyAudioSwitch;
    private TextView tvPlayVideoSwitch;

    public interface OnBitrateSelectedListener {
        void onBitrateSelected(PolyvDefinitionVO polyvDefinitionVO, int i2);
    }

    public interface OnChangeLowLatencyListener extends PLVSugarUtil.Consumer<Boolean> {
    }

    public interface OnLinesSelectedListener {
        void onLineSelected(int i2, int i3);
    }

    public interface OnOnlyAudioSwitchListener {
        boolean onOnlyAudioSelect(boolean z2);
    }

    public class RvBitrateAdapter extends RecyclerView.Adapter<RvMoreViewHolder> {
        private List<PolyvDefinitionVO> bitrateVO;
        private int curSelectPos;

        public class RvMoreViewHolder extends RecyclerView.ViewHolder {
            TextView tvBitrate;

            public RvMoreViewHolder(View view) {
                super(view);
                this.tvBitrate = (TextView) view.findViewById(R.id.tv_bitrate);
            }
        }

        private RvBitrateAdapter() {
            this.curSelectPos = -1;
        }

        public int getCurSelectPos() {
            return this.curSelectPos;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            List<PolyvDefinitionVO> list = this.bitrateVO;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        public void updateBitrateListData(List<PolyvDefinitionVO> list, int i2) {
            if (list == null) {
                list = new ArrayList<>();
            }
            this.bitrateVO = list;
            this.curSelectPos = i2;
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull final RvMoreViewHolder rvMoreViewHolder, int i2) {
            rvMoreViewHolder.tvBitrate.setText(this.bitrateVO.get(i2).getDefinition());
            rvMoreViewHolder.tvBitrate.setSelected(i2 == this.curSelectPos);
            rvMoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.RvBitrateAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (rvMoreViewHolder.getAdapterPosition() == RvBitrateAdapter.this.curSelectPos) {
                        return;
                    }
                    RvBitrateAdapter.this.curSelectPos = rvMoreViewHolder.getAdapterPosition();
                    RvBitrateAdapter.this.notifyDataSetChanged();
                    if (PLVLCLiveMoreLayout.this.onBitrateSelectedListener != null) {
                        PLVLCLiveMoreLayout.this.onBitrateSelectedListener.onBitrateSelected((PolyvDefinitionVO) RvBitrateAdapter.this.bitrateVO.get(RvBitrateAdapter.this.curSelectPos), RvBitrateAdapter.this.curSelectPos);
                    }
                    PLVLCLiveMoreLayout.this.plvlcLiveControlMoreBitrateTv.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.RvBitrateAdapter.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVLCLiveMoreLayout.this.hide();
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

    public static class RvLatencyAdapter extends RecyclerView.Adapter<RvLatencyViewHolder> {
        private LatencyType curSelectLatencyType;
        private final List<LatencyType> latencyTypeList;
        private OnChangeLowLatencyListener onChangeLowLatencyListener;

        public enum LatencyType {
            LOW_LATENCY("无延迟", true),
            NORMAL_LATENCY("正常延迟", false);

            private final String latencyName;
            private final boolean lowLatency;

            LatencyType(String str, boolean z2) {
                this.latencyName = str;
                this.lowLatency = z2;
            }

            public static LatencyType getLatencyType(boolean z2) {
                return z2 ? LOW_LATENCY : NORMAL_LATENCY;
            }

            public String getLatencyName() {
                return this.latencyName;
            }

            public boolean isLowLatency() {
                return this.lowLatency;
            }
        }

        public static class RvLatencyViewHolder extends RecyclerView.ViewHolder {
            private TextView latencyTextView;

            public RvLatencyViewHolder(View view) {
                super(view);
                findView();
            }

            private void findView() {
                this.latencyTextView = (TextView) this.itemView.findViewById(R.id.tv_bitrate);
            }

            public void bind(LatencyType latencyType) {
                this.latencyTextView.setText(latencyType.getLatencyName());
            }
        }

        private RvLatencyAdapter() {
            LatencyType latencyType = LatencyType.LOW_LATENCY;
            this.latencyTypeList = PLVSugarUtil.listOf(latencyType, LatencyType.NORMAL_LATENCY);
            this.curSelectLatencyType = latencyType;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.latencyTypeList.size();
        }

        public void setCurrentIsLowLatency(boolean z2) {
            if (this.curSelectLatencyType.isLowLatency() != z2) {
                this.curSelectLatencyType = LatencyType.getLatencyType(z2);
                notifyDataSetChanged();
            }
        }

        public void setOnChangeLowLatencyListener(OnChangeLowLatencyListener onChangeLowLatencyListener) {
            this.onChangeLowLatencyListener = onChangeLowLatencyListener;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull RvLatencyViewHolder rvLatencyViewHolder, int i2) {
            final LatencyType latencyType = this.latencyTypeList.get(i2);
            rvLatencyViewHolder.bind(latencyType);
            rvLatencyViewHolder.itemView.setSelected(latencyType == this.curSelectLatencyType);
            rvLatencyViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.RvLatencyAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (RvLatencyAdapter.this.onChangeLowLatencyListener != null) {
                        RvLatencyAdapter.this.onChangeLowLatencyListener.accept(Boolean.valueOf(latencyType.isLowLatency()));
                    }
                    RvLatencyAdapter.this.curSelectLatencyType = latencyType;
                    RvLatencyAdapter.this.notifyDataSetChanged();
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public RvLatencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
            return new RvLatencyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plvlc_live_controller_bitrate_item, viewGroup, false));
        }
    }

    public class RvLinesAdapter extends RecyclerView.Adapter<RvLinesViewHolder> {
        private int curSelectPos;
        private int lines;

        public class RvLinesViewHolder extends RecyclerView.ViewHolder {
            TextView tvLines;

            public RvLinesViewHolder(View view) {
                super(view);
                this.tvLines = (TextView) view.findViewById(R.id.tv_bitrate);
            }
        }

        private RvLinesAdapter() {
            this.curSelectPos = 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.lines;
        }

        public void updateLinesDatas(int i2, int i3) {
            this.lines = i2;
            this.curSelectPos = i3;
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull final RvLinesViewHolder rvLinesViewHolder, @SuppressLint({"RecyclerView"}) final int i2) {
            rvLinesViewHolder.tvLines.setText("线路" + (i2 + 1));
            rvLinesViewHolder.tvLines.setSelected(i2 == this.curSelectPos);
            rvLinesViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.RvLinesAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (rvLinesViewHolder.getAdapterPosition() == RvLinesAdapter.this.curSelectPos) {
                        return;
                    }
                    RvLinesAdapter.this.curSelectPos = rvLinesViewHolder.getAdapterPosition();
                    RvLinesAdapter.this.notifyDataSetChanged();
                    if (PLVLCLiveMoreLayout.this.onLinesSelectedListener != null) {
                        PLVLCLiveMoreLayout.this.onLinesSelectedListener.onLineSelected(RvLinesAdapter.this.lines, i2);
                    }
                    PLVLCLiveMoreLayout.this.plvlcLiveControlMoreLinesRv.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.RvLinesAdapter.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVLCLiveMoreLayout.this.hide();
                        }
                    });
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public RvLinesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
            return new RvLinesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plvlc_live_controller_bitrate_item, viewGroup, false));
        }
    }

    public PLVLCLiveMoreLayout(View view) {
        this.anchor = view;
        if (this.popupWindow == null) {
            this.popupWindow = new PopupWindow(view.getContext());
            this.root = PLVViewInitUtils.initPopupWindow(view, R.layout.plvlc_live_controller_more_layout, this.popupWindow, new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    PLVLCLiveMoreLayout.this.hide();
                }
            });
            initView();
        }
    }

    private void findView() {
        this.plvlcLiveControlMoreModeTv = (TextView) this.root.findViewById(R.id.plvlc_live_control_more_mode_tv);
        this.tvPlayVideoSwitch = (TextView) this.root.findViewById(R.id.tv_play_video_switch);
        this.tvOnlyAudioSwitch = (TextView) this.root.findViewById(R.id.tv_only_audio_switch);
        this.plvlcLiveControlMoreModeSwitchLl = (LinearLayout) this.root.findViewById(R.id.plvlc_live_control_more_mode_switch_ll);
        this.plvlcLiveControlMoreModeLl = (LinearLayout) this.root.findViewById(R.id.plvlc_live_control_more_mode_ll);
        this.plvlcLiveControlMoreBitrateTv = (TextView) this.root.findViewById(R.id.plvlc_live_control_more_bitrate_tv);
        this.plvlcLiveControlMoreBitrateRv = (RecyclerView) this.root.findViewById(R.id.plvlc_live_control_more_bitrate_rv);
        this.plvlcLiveControlMoreBitrateLl = (LinearLayout) this.root.findViewById(R.id.plvlc_live_control_more_bitrate_ll);
        this.plvlcLiveControlMoreLinesTv = (TextView) this.root.findViewById(R.id.plvlc_live_control_more_lines_tv);
        this.plvlcLiveControlMoreLinesRv = (RecyclerView) this.root.findViewById(R.id.plvlc_live_control_more_lines_rv);
        this.plvlcLiveControlMoreLinesLl = (LinearLayout) this.root.findViewById(R.id.plvlc_live_control_more_lines_ll);
        this.plvlcLiveControlMoreLatencyTv = (TextView) this.root.findViewById(R.id.plvlc_live_control_more_latency_tv);
        this.plvlcLiveControlMoreLatencyRv = (RecyclerView) this.root.findViewById(R.id.plvlc_live_control_more_latency_rv);
        this.plvlcLiveControlMoreLatencyLl = (LinearLayout) this.root.findViewById(R.id.plvlc_live_control_more_latency_ll);
        this.llMoreVertical = (PLVOrientationSensibleLinearLayout) this.root.findViewById(R.id.ll_more_vertical);
        this.containerLy = (FrameLayout) this.root.findViewById(R.id.container_ly);
    }

    private void initLatencyRv() {
        setEnableLowLatency(PLVLinkMicConfig.getInstance().isLowLatencyWatchEnabled());
        RvLatencyAdapter rvLatencyAdapter = new RvLatencyAdapter();
        this.rvLatencyAdapter = rvLatencyAdapter;
        rvLatencyAdapter.setOnChangeLowLatencyListener(new OnChangeLowLatencyListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.4
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(Boolean bool) {
                PLVLCLiveMoreLayout.this.hide();
                if (bool.booleanValue()) {
                    PLVLCLiveMoreLayout.this.onClickChangeToPlayVideo();
                }
                if (PLVLCLiveMoreLayout.this.isLowLatency != bool.booleanValue()) {
                    if (PLVLCLiveMoreLayout.this.onChangeLowLatencyListener != null) {
                        PLVLCLiveMoreLayout.this.onChangeLowLatencyListener.accept(bool);
                    }
                    PLVLCLiveMoreLayout.this.isLowLatency = bool.booleanValue();
                }
            }
        });
        RvLatencyAdapter rvLatencyAdapter2 = this.rvLatencyAdapter;
        boolean zIsLowLatencyWatchEnabled = PLVLinkMicConfig.getInstance().isLowLatencyWatchEnabled();
        this.isLowLatency = zIsLowLatencyWatchEnabled;
        rvLatencyAdapter2.setCurrentIsLowLatency(zIsLowLatencyWatchEnabled);
        this.plvlcLiveControlMoreLatencyRv.setLayoutManager(new LinearLayoutManager(this.root.getContext(), 0, false));
        this.plvlcLiveControlMoreLatencyRv.setAdapter(this.rvLatencyAdapter);
    }

    private void initView() {
        findView();
        this.containerLy.setOnClickListener(this);
        this.llMoreVertical.setOnLandscape(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.2
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCLiveMoreLayout.this.popupWindow != null && PLVLCLiveMoreLayout.this.popupWindow.isShowing()) {
                    PLVLCLiveMoreLayout.this.popupWindow.update();
                }
                PLVLCLiveMoreLayout.this.onLandscape();
            }
        });
        this.llMoreVertical.setOnPortrait(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveMoreLayout.3
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCLiveMoreLayout.this.portraitHeight == 0) {
                    PLVLCLiveMoreLayout.this.hide();
                    return;
                }
                if (PLVLCLiveMoreLayout.this.popupWindow != null && PLVLCLiveMoreLayout.this.popupWindow.isShowing()) {
                    PLVLCLiveMoreLayout.this.popupWindow.update();
                }
                PLVLCLiveMoreLayout.this.onPortrait();
            }
        });
        RvBitrateAdapter rvBitrateAdapter = new RvBitrateAdapter();
        this.rvBitrateAdapter = rvBitrateAdapter;
        this.plvlcLiveControlMoreBitrateRv.setAdapter(rvBitrateAdapter);
        this.plvlcLiveControlMoreBitrateRv.setLayoutManager(new LinearLayoutManager(this.root.getContext(), 0, false));
        RvLinesAdapter rvLinesAdapter = new RvLinesAdapter();
        this.rvLinesAdapter = rvLinesAdapter;
        this.plvlcLiveControlMoreLinesRv.setAdapter(rvLinesAdapter);
        this.plvlcLiveControlMoreLinesRv.setLayoutManager(new LinearLayoutManager(this.root.getContext(), 0, false));
        this.tvPlayVideoSwitch.setSelected(true);
        this.tvPlayVideoSwitch.setOnClickListener(this);
        this.tvOnlyAudioSwitch.setSelected(false);
        this.tvOnlyAudioSwitch.setOnClickListener(this);
        initLatencyRv();
    }

    private void onBitrateLayoutReactOrientation(boolean z2) {
        ViewGroup.LayoutParams layoutParams = this.plvlcLiveControlMoreBitrateTv.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.plvlcLiveControlMoreBitrateRv.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.plvlcLiveControlMoreBitrateLl.getLayoutParams();
        if (z2) {
            layoutParams.width = ConvertUtils.dp2px(60.0f);
            marginLayoutParams.leftMargin = ConvertUtils.dp2px(24.0f);
            marginLayoutParams2.topMargin = 0;
            this.plvlcLiveControlMoreBitrateLl.setOrientation(0);
        } else {
            layoutParams.width = -2;
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams2.topMargin = ConvertUtils.dp2px(16.0f);
            this.plvlcLiveControlMoreBitrateLl.setOrientation(1);
        }
        this.plvlcLiveControlMoreBitrateTv.setLayoutParams(layoutParams);
        this.plvlcLiveControlMoreBitrateRv.setLayoutParams(marginLayoutParams);
        this.plvlcLiveControlMoreBitrateLl.setLayoutParams(marginLayoutParams2);
    }

    private void onClickChangeToPlayAudio() {
        if (this.tvOnlyAudioSwitch.isSelected()) {
            return;
        }
        OnOnlyAudioSwitchListener onOnlyAudioSwitchListener = this.onOnlyAudioSwitchListener;
        if (!(onOnlyAudioSwitchListener != null ? onOnlyAudioSwitchListener.onOnlyAudioSelect(true) : false)) {
            hide();
            return;
        }
        showBitrate(false);
        this.tvOnlyAudioSwitch.setSelected(true);
        this.tvPlayVideoSwitch.setSelected(false);
        hide();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClickChangeToPlayVideo() {
        if (this.tvPlayVideoSwitch.isSelected()) {
            return;
        }
        OnOnlyAudioSwitchListener onOnlyAudioSwitchListener = this.onOnlyAudioSwitchListener;
        if (!(onOnlyAudioSwitchListener != null ? onOnlyAudioSwitchListener.onOnlyAudioSelect(false) : false)) {
            hide();
            return;
        }
        showBitrate(true);
        this.tvOnlyAudioSwitch.setSelected(false);
        this.tvPlayVideoSwitch.setSelected(true);
        hide();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLandscape() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.llMoreVertical.getLayoutParams();
        layoutParams.gravity = 48;
        this.llMoreVertical.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.containerLy.getLayoutParams();
        layoutParams2.width = -2;
        layoutParams2.height = -1;
        layoutParams2.gravity = 5;
        this.containerLy.setLayoutParams(layoutParams2);
        this.containerLy.setBackgroundColor(Color.parseColor("#CC000000"));
        onModeLayoutReactOrientation(false);
        onBitrateLayoutReactOrientation(false);
        onLinesLayoutReactOrientation(false);
        onLatencyLayoutReactOrientation(false);
    }

    private void onLatencyLayoutReactOrientation(boolean z2) {
        ViewGroup.LayoutParams layoutParams = this.plvlcLiveControlMoreLatencyTv.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.plvlcLiveControlMoreLatencyRv.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.plvlcLiveControlMoreLatencyLl.getLayoutParams();
        if (z2) {
            layoutParams.width = ConvertUtils.dp2px(60.0f);
            marginLayoutParams.leftMargin = ConvertUtils.dp2px(24.0f);
            marginLayoutParams2.topMargin = 0;
            this.plvlcLiveControlMoreLatencyLl.setOrientation(0);
        } else {
            layoutParams.width = -2;
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams2.topMargin = ConvertUtils.dp2px(16.0f);
            this.plvlcLiveControlMoreLatencyLl.setOrientation(1);
        }
        this.plvlcLiveControlMoreLatencyTv.setLayoutParams(layoutParams);
        this.plvlcLiveControlMoreLatencyRv.setLayoutParams(marginLayoutParams);
        this.plvlcLiveControlMoreLatencyLl.setLayoutParams(marginLayoutParams2);
    }

    private void onLinesLayoutReactOrientation(boolean z2) {
        ViewGroup.LayoutParams layoutParams = this.plvlcLiveControlMoreLinesTv.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.plvlcLiveControlMoreLinesRv.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.plvlcLiveControlMoreLinesLl.getLayoutParams();
        if (z2) {
            layoutParams.width = ConvertUtils.dp2px(60.0f);
            marginLayoutParams.leftMargin = ConvertUtils.dp2px(24.0f);
            marginLayoutParams2.topMargin = 0;
            this.plvlcLiveControlMoreLinesLl.setOrientation(0);
        } else {
            layoutParams.width = -2;
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams2.topMargin = ConvertUtils.dp2px(16.0f);
            this.plvlcLiveControlMoreLinesLl.setOrientation(1);
        }
        this.plvlcLiveControlMoreLinesTv.setLayoutParams(layoutParams);
        this.plvlcLiveControlMoreLinesRv.setLayoutParams(marginLayoutParams);
        this.plvlcLiveControlMoreLinesLl.setLayoutParams(marginLayoutParams2);
    }

    private void onModeLayoutReactOrientation(boolean z2) {
        ViewGroup.LayoutParams layoutParams = this.plvlcLiveControlMoreModeTv.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.plvlcLiveControlMoreModeSwitchLl.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.plvlcLiveControlMoreModeLl.getLayoutParams();
        if (z2) {
            layoutParams.width = ConvertUtils.dp2px(60.0f);
            marginLayoutParams.leftMargin = ConvertUtils.dp2px(24.0f);
            marginLayoutParams2.topMargin = 0;
            this.plvlcLiveControlMoreModeLl.setOrientation(0);
        } else {
            layoutParams.width = -2;
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams2.topMargin = ConvertUtils.dp2px(16.0f);
            this.plvlcLiveControlMoreModeLl.setOrientation(1);
        }
        this.plvlcLiveControlMoreModeTv.setLayoutParams(layoutParams);
        this.plvlcLiveControlMoreModeSwitchLl.setLayoutParams(marginLayoutParams);
        this.plvlcLiveControlMoreModeLl.setLayoutParams(marginLayoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPortrait() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.llMoreVertical.getLayoutParams();
        layoutParams.gravity = 17;
        this.llMoreVertical.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.containerLy.getLayoutParams();
        layoutParams2.width = -1;
        layoutParams2.height = this.portraitHeight;
        layoutParams2.gravity = 0;
        this.containerLy.setLayoutParams(layoutParams2);
        this.containerLy.setBackgroundColor(Color.parseColor("#D8000000"));
        onModeLayoutReactOrientation(true);
        onBitrateLayoutReactOrientation(true);
        onLinesLayoutReactOrientation(true);
        onLatencyLayoutReactOrientation(true);
    }

    private void show(boolean z2) {
        PopupWindow popupWindow = this.popupWindow;
        if (popupWindow != null) {
            popupWindow.showAtLocation(this.anchor, 0, 0, 0);
        }
        updateViewVisibility();
    }

    private void updateViewVisibility() {
        if (this.isLowLatency) {
            this.plvlcLiveControlMoreModeLl.setVisibility(8);
        } else {
            this.plvlcLiveControlMoreModeLl.setVisibility(0);
        }
        if (!this.shouldShowBitrateLayout || this.rvBitrateAdapter.getItemCount() <= 1 || this.isLowLatency) {
            this.plvlcLiveControlMoreBitrateLl.setVisibility(8);
        } else {
            this.plvlcLiveControlMoreBitrateLl.setVisibility(0);
        }
        if (!this.shouldShowLinesLayout || this.rvLinesAdapter.getItemCount() <= 1 || this.isLowLatency) {
            this.plvlcLiveControlMoreLinesLl.setVisibility(8);
        } else {
            this.plvlcLiveControlMoreLinesLl.setVisibility(0);
        }
    }

    public void hide() {
        PopupWindow popupWindow = this.popupWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public void initBitrate(List<PolyvDefinitionVO> list, int i2) {
        this.rvBitrateAdapter.updateBitrateListData(list, i2);
    }

    public void initLines(int i2, int i3) {
        this.rvLinesAdapter.updateLinesDatas(i2, i3);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == this.containerLy.getId()) {
            hide();
        } else if (view.getId() == this.tvPlayVideoSwitch.getId()) {
            onClickChangeToPlayVideo();
        } else if (view.getId() == this.tvOnlyAudioSwitch.getId()) {
            onClickChangeToPlayAudio();
        }
    }

    public void setEnableLowLatency(boolean z2) {
        this.plvlcLiveControlMoreLatencyLl.setVisibility(z2 ? 0 : 8);
    }

    public void setOnBitrateSelectedListener(OnBitrateSelectedListener onBitrateSelectedListener) {
        this.onBitrateSelectedListener = onBitrateSelectedListener;
    }

    public void setOnChangeLowLatencyListener(OnChangeLowLatencyListener onChangeLowLatencyListener) {
        this.onChangeLowLatencyListener = onChangeLowLatencyListener;
    }

    public void setOnLinesSelectedListener(OnLinesSelectedListener onLinesSelectedListener) {
        this.onLinesSelectedListener = onLinesSelectedListener;
    }

    public void setOnOnlyAudioSwitchListener(OnOnlyAudioSwitchListener onOnlyAudioSwitchListener) {
        this.onOnlyAudioSwitchListener = onOnlyAudioSwitchListener;
    }

    public void showBitrate(boolean z2) {
        this.shouldShowBitrateLayout = z2;
        updateViewVisibility();
    }

    public void showLines(boolean z2) {
        this.shouldShowLinesLayout = z2;
        updateViewVisibility();
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

    public void updateViewWithLatency(boolean z2) {
        this.isLowLatency = z2;
        this.rvLatencyAdapter.setCurrentIsLowLatency(z2);
        updateViewVisibility();
    }

    public void updateViewWithPlayInfo(int i2, Pair<List<PolyvDefinitionVO>, Integer> pair, int[] iArr) {
        updateViewWithPlayMode(i2);
        initBitrate((List) pair.first, ((Integer) pair.second).intValue());
        initLines(iArr[0], iArr[1]);
        showLines(true);
        showBitrate(!this.isAudioMode);
    }

    public void updateViewWithPlayMode(int i2) {
        boolean z2 = i2 == 1;
        this.isAudioMode = z2;
        if (z2) {
            showBitrate(false);
        }
        this.tvOnlyAudioSwitch.setSelected(this.isAudioMode);
        this.tvPlayVideoSwitch.setSelected(!this.isAudioMode);
    }

    public void updateWhenOnlyAudio(boolean z2) {
        this.tvPlayVideoSwitch.setVisibility(z2 ? 8 : 0);
        this.tvOnlyAudioSwitch.setSelected(z2);
    }
}
