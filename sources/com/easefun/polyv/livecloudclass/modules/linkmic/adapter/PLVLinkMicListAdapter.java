package com.easefun.polyv.livecloudclass.modules.linkmic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowMode;
import com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.ui.widget.PLVLSNetworkQualityWidget;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.widget.roundview.PLVRoundRectLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.eclipse.jetty.util.URIUtil;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class PLVLinkMicListAdapter extends RecyclerView.Adapter<LinkMicItemViewHolder> {
    private static final String DEFAULT_LIVE_STREAM_COVER_IMAGE = "https://s1.videocc.net/default-img/channel/default-splash.png";
    public static final int HORIZONTAL_VISIBLE_COUNT = 3;
    private static final String PAYLOAD_UPDATE_COVER_IMAGE = "updateCoverImage";
    private static final String PAYLOAD_UPDATE_CUP = "updateCup";
    private static final String PAYLOAD_UPDATE_NET_QUALITY = "updateNetQuality";
    private static final String PAYLOAD_UPDATE_VIDEO_MUTE = "updateVideoMute";
    private static final String PAYLOAD_UPDATE_VOLUME = "updateVolume";
    private static final String TAG = "PLVLinkMicListAdapter";
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    @NotNull
    private OnPLVLinkMicAdapterCallback adapterCallback;
    private List<PLVLinkMicItemDataBean> dataList;
    private String invisibleItemLinkMicId;
    private boolean isAudio;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    private SurfaceView localRenderView;
    private String mediaInLinkMicListLinkMicId;
    private String myLinkMicId;
    private int netQuality;

    @Nullable
    private OnTeacherSwitchViewBindListener onTeacherSwitchViewBindListener;
    private PLVPlayerLogoView plvPlayerLogoView;
    private final RecyclerView rv;

    @Nullable
    private PLVSwitchViewAnchorLayout switchViewHasMedia;

    @Nullable
    private LinkMicItemViewHolder teacherViewHolder;
    private Map<String, Bitmap> linkMicIdSnapshotBitmapMap = new HashMap();
    private String firstScreenLinkMicId = "";
    private boolean showRoundRect = false;
    private boolean shouldHideAllRenderView = false;
    private PLVLinkMicListShowMode listShowMode = PLVLinkMicListShowMode.SHOW_ALL;
    private boolean hasNotifyTeacherViewHolderBind = false;
    private String coverImage = DEFAULT_LIVE_STREAM_COVER_IMAGE;
    private boolean isOnlyAudio = false;
    private volatile boolean isPausing = false;

    /* renamed from: com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecommon$module$modules$linkmic$model$PLVLinkMicListShowMode;

        static {
            int[] iArr = new int[PLVLinkMicListShowMode.values().length];
            $SwitchMap$com$easefun$polyv$livecommon$module$modules$linkmic$model$PLVLinkMicListShowMode = iArr;
            try {
                iArr[PLVLinkMicListShowMode.SHOW_ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$module$modules$linkmic$model$PLVLinkMicListShowMode[PLVLinkMicListShowMode.SHOW_TEACHER_AND_GUEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$module$modules$linkmic$model$PLVLinkMicListShowMode[PLVLinkMicListShowMode.SHOW_FIRST_SCREEN_AND_SELF.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$module$modules$linkmic$model$PLVLinkMicListShowMode[PLVLinkMicListShowMode.SHOW_FIRST_SCREEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class LinkMicItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView coverImageView;
        private FrameLayout flRenderViewContainer;
        private boolean isRenderViewSetup;
        private boolean isViewRecycled;
        private ImageView ivMicState;
        private ImageView linkMicRenderViewPausePlaceholder;
        private TextView liveLinkmicFloatingPlayingPlaceholderTv;
        private LinearLayout llCupLayout;
        private PLVPlayerLogoView plvPlayerLogoView;
        private PLVLSNetworkQualityWidget qualityWidget;

        @Nullable
        private SurfaceView renderView;
        private PLVRoundRectLayout roundRectLayout;
        private PLVSwitchViewAnchorLayout switchViewAnchorLayout;
        private TextView tvCupNumView;
        private TextView tvNick;

        public LinkMicItemViewHolder(View view) {
            super(view);
            this.isViewRecycled = false;
            this.isRenderViewSetup = false;
            this.flRenderViewContainer = (FrameLayout) view.findViewById(R.id.plvlc_link_mic_fl_render_view_container);
            this.linkMicRenderViewPausePlaceholder = (ImageView) view.findViewById(R.id.plvlc_link_mic_render_view_pause_placeholder);
            this.tvNick = (TextView) view.findViewById(R.id.plvlc_link_mic_tv_nick);
            this.tvCupNumView = (TextView) view.findViewById(R.id.plvlc_link_mic_tv_cup_num_view);
            this.llCupLayout = (LinearLayout) view.findViewById(R.id.plvlc_link_mic_ll_cup_layout);
            this.ivMicState = (ImageView) view.findViewById(R.id.plvlc_link_mic_iv_mic_state);
            this.roundRectLayout = (PLVRoundRectLayout) view.findViewById(R.id.plvlc_linkmic_item_round_rect_layout);
            this.switchViewAnchorLayout = (PLVSwitchViewAnchorLayout) view.findViewById(R.id.plvlc_linkmic_switch_anchor_item);
            this.qualityWidget = (PLVLSNetworkQualityWidget) view.findViewById(R.id.plvlc_link_mic_net_quality_view);
            this.coverImageView = (ImageView) view.findViewById(R.id.plvlc_link_mic_iv_cover_image);
            this.plvPlayerLogoView = (PLVPlayerLogoView) view.findViewById(R.id.plvlc_link_mic_logo_view);
            this.liveLinkmicFloatingPlayingPlaceholderTv = (TextView) view.findViewById(R.id.plvlc_live_linkmic_floating_playing_placeholder_tv);
            this.qualityWidget.shouldShowNoNetworkHint(false);
            this.qualityWidget.setNetQualityRes(R.drawable.plv_network_signal_watcher_good, R.drawable.plv_network_signal_watcher_middle, R.drawable.plv_network_signal_watcher_poor);
            observeFloatingPlayer();
        }

        private void observeFloatingPlayer() {
            PLVFloatingPlayerManager.getInstance().getFloatingViewShowState().observe((LifecycleOwner) this.itemView.getContext(), new Observer<Boolean>() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.LinkMicItemViewHolder.1
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable Boolean bool) {
                    LinkMicItemViewHolder.this.liveLinkmicFloatingPlayingPlaceholderTv.setVisibility(bool != null && bool.booleanValue() ? 0 : 8);
                }
            });
        }
    }

    public interface OnPLVLinkMicAdapterCallback {
        SurfaceView createLinkMicRenderView();

        void muteAllAudioVideo(boolean z2);

        void muteAudioVideo(String str, boolean z2);

        void onClickItemListener(int i2, @Nullable PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout, PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout2);

        void releaseRenderView(SurfaceView surfaceView);

        void setupRenderView(SurfaceView surfaceView, String str);
    }

    public interface OnTeacherSwitchViewBindListener {
        void onTeacherSwitchViewBind(PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout);
    }

    public PLVLinkMicListAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, @NotNull OnPLVLinkMicAdapterCallback onPLVLinkMicAdapterCallback) {
        this.rv = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
        this.adapterCallback = onPLVLinkMicAdapterCallback;
    }

    private void bindCoverImage(LinkMicItemViewHolder linkMicItemViewHolder, boolean z2, boolean z3) {
        if (!z2 || !z3) {
            linkMicItemViewHolder.coverImageView.setVisibility(4);
        } else {
            linkMicItemViewHolder.coverImageView.setVisibility(0);
            PLVImageLoader.getInstance().loadImage(this.coverImage, linkMicItemViewHolder.coverImageView);
        }
    }

    private void bindLogoView(LinkMicItemViewHolder linkMicItemViewHolder, boolean z2) {
        if (z2 && linkMicItemViewHolder != null) {
            linkMicItemViewHolder.plvPlayerLogoView.addLogo(this.plvPlayerLogoView.getParamZero());
            linkMicItemViewHolder.plvPlayerLogoView.setVisibility(0);
        } else {
            if (z2 || linkMicItemViewHolder == null) {
                return;
            }
            linkMicItemViewHolder.plvPlayerLogoView.setVisibility(8);
        }
    }

    private void bindVideoMute(@NonNull LinkMicItemViewHolder linkMicItemViewHolder, boolean z2, String str) {
        if ((linkMicItemViewHolder.flRenderViewContainer.getVisibility() != 0) == z2) {
            return;
        }
        if (z2) {
            linkMicItemViewHolder.flRenderViewContainer.setVisibility(4);
            if (linkMicItemViewHolder.renderView != null) {
                linkMicItemViewHolder.flRenderViewContainer.removeView(linkMicItemViewHolder.renderView);
                return;
            }
            return;
        }
        linkMicItemViewHolder.flRenderViewContainer.setVisibility(0);
        if (linkMicItemViewHolder.renderView != null) {
            this.adapterCallback.releaseRenderView(linkMicItemViewHolder.renderView);
        }
        linkMicItemViewHolder.renderView = this.adapterCallback.createLinkMicRenderView();
        this.adapterCallback.setupRenderView(linkMicItemViewHolder.renderView, str);
        if (linkMicItemViewHolder.renderView == null || linkMicItemViewHolder.renderView.getParent() != null) {
            return;
        }
        linkMicItemViewHolder.flRenderViewContainer.addView(linkMicItemViewHolder.renderView, 0, getRenderViewLayoutParam());
    }

    private FrameLayout.LayoutParams getRenderViewLayoutParam() {
        return new FrameLayout.LayoutParams(-1, -1, 17);
    }

    private boolean intBetween(int i2, int i3, int i4) {
        return i2 > i3 && i2 <= i4;
    }

    private boolean resolveListShowMode(LinkMicItemViewHolder linkMicItemViewHolder, int i2) {
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.dataList.get(i2);
        String linkMicId = pLVLinkMicItemDataBean.getLinkMicId();
        pLVLinkMicItemDataBean.getNick();
        pLVLinkMicItemDataBean.getCupNum();
        boolean zIsMuteVideo = pLVLinkMicItemDataBean.isMuteVideo();
        pLVLinkMicItemDataBean.isMuteAudio();
        pLVLinkMicItemDataBean.getCurVolume();
        boolean zIsTeacher = pLVLinkMicItemDataBean.isTeacher();
        boolean zIsGuest = pLVLinkMicItemDataBean.isGuest();
        boolean zEquals = this.firstScreenLinkMicId.equals(linkMicId);
        boolean zEquals2 = this.myLinkMicId.equals(linkMicId);
        int i3 = AnonymousClass4.$SwitchMap$com$easefun$polyv$livecommon$module$modules$linkmic$model$PLVLinkMicListShowMode[this.listShowMode.ordinal()];
        if (i3 == 1) {
            trySetupRenderView(linkMicItemViewHolder, linkMicId);
            return zIsMuteVideo;
        }
        if (i3 != 2) {
            if (i3 != 3) {
                if (i3 != 4) {
                    return zIsMuteVideo;
                }
                if (zEquals) {
                    trySetupRenderView(linkMicItemViewHolder, linkMicId);
                    return zIsMuteVideo;
                }
                linkMicItemViewHolder.flRenderViewContainer.setVisibility(8);
            } else {
                if (zEquals || zEquals2) {
                    trySetupRenderView(linkMicItemViewHolder, linkMicId);
                    return zIsMuteVideo;
                }
                linkMicItemViewHolder.flRenderViewContainer.setVisibility(8);
            }
        } else {
            if (zIsTeacher || zIsGuest) {
                trySetupRenderView(linkMicItemViewHolder, linkMicId);
                return zIsMuteVideo;
            }
            linkMicItemViewHolder.flRenderViewContainer.setVisibility(8);
        }
        return true;
    }

    private void setMicrophoneVolumeIcon(int i2, boolean z2, @NonNull LinkMicItemViewHolder linkMicItemViewHolder) {
        if (z2) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_iv_mic_close);
            return;
        }
        if (intBetween(i2, 0, 5) || i2 == 0) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_iv_mic_open);
            return;
        }
        if (intBetween(i2, 5, 15)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_10);
            return;
        }
        if (intBetween(i2, 15, 25)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_20);
            return;
        }
        if (intBetween(i2, 25, 35)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_30);
            return;
        }
        if (intBetween(i2, 35, 45)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_40);
            return;
        }
        if (intBetween(i2, 45, 55)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_50);
            return;
        }
        if (intBetween(i2, 55, 65)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_60);
            return;
        }
        if (intBetween(i2, 65, 75)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_70);
            return;
        }
        if (intBetween(i2, 75, 85)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_80);
        } else if (intBetween(i2, 85, 95)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_90);
        } else if (intBetween(i2, 95, 100)) {
            linkMicItemViewHolder.ivMicState.setImageResource(R.drawable.plvlc_linkmic_mic_volume_100);
        }
    }

    private void trySetupRenderView(LinkMicItemViewHolder linkMicItemViewHolder, String str) {
        if (linkMicItemViewHolder.renderView == null || linkMicItemViewHolder.isRenderViewSetup) {
            return;
        }
        this.adapterCallback.setupRenderView(linkMicItemViewHolder.renderView, str);
        linkMicItemViewHolder.isRenderViewSetup = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNetQualityLayout(Context context, FrameLayout.LayoutParams layoutParams, int i2, int i3) {
        float f2 = i2;
        layoutParams.width = PLVScreenUtils.dip2px(context, f2);
        layoutParams.height = PLVScreenUtils.dip2px(context, f2);
        layoutParams.gravity = BadgeDrawable.TOP_END;
        float f3 = i3;
        layoutParams.rightMargin = PLVScreenUtils.dip2px(context, f3);
        layoutParams.topMargin = PLVScreenUtils.dip2px(context, f3);
    }

    public PLVSwitchViewAnchorLayout getFirstScreenSwitchView() {
        LinkMicItemViewHolder linkMicItemViewHolder;
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if (this.dataList.get(i2).getLinkMicId().equals(this.firstScreenLinkMicId) && (linkMicItemViewHolder = (LinkMicItemViewHolder) this.rv.findViewHolderForAdapterPosition(i2)) != null) {
                return linkMicItemViewHolder.switchViewAnchorLayout;
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<PLVLinkMicItemDataBean> list = this.dataList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        return this.dataList.get(i2).getLinkMicId().hashCode();
    }

    public int getItemWidth() {
        return Math.min(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight()) / 3;
    }

    public int getMediaViewIndexInLinkMicList() {
        if (this.switchViewHasMedia == null) {
            return -1;
        }
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            LinkMicItemViewHolder linkMicItemViewHolder = (LinkMicItemViewHolder) this.rv.findViewHolderForAdapterPosition(i2);
            if (linkMicItemViewHolder != null && linkMicItemViewHolder.switchViewAnchorLayout == this.switchViewHasMedia) {
                return i2;
            }
        }
        return -1;
    }

    public PLVSwitchViewAnchorLayout getSwitchView(int i2) {
        LinkMicItemViewHolder linkMicItemViewHolder = (LinkMicItemViewHolder) this.rv.findViewHolderForAdapterPosition(i2);
        if (linkMicItemViewHolder != null) {
            return linkMicItemViewHolder.switchViewAnchorLayout;
        }
        return null;
    }

    @Nullable
    public PLVSwitchViewAnchorLayout getSwitchViewHasMedia() {
        return this.switchViewHasMedia;
    }

    public void hideAllRenderView() {
        this.shouldHideAllRenderView = true;
        notifyDataSetChanged();
    }

    public boolean isPausing() {
        return this.isPausing;
    }

    public void pauseAllRenderView() {
        this.isPausing = true;
        this.adapterCallback.muteAllAudioVideo(true);
        notifyDataSetChanged();
    }

    public void releaseView() {
        this.switchViewHasMedia = null;
        this.teacherViewHolder = null;
        this.hasNotifyTeacherViewHolderBind = false;
    }

    public void resumeAllRenderView() {
        this.isPausing = false;
        this.adapterCallback.muteAllAudioVideo(false);
        notifyDataSetChanged();
    }

    public void setCoverImage(String str) {
        if (TextUtils.isEmpty(str)) {
            str = DEFAULT_LIVE_STREAM_COVER_IMAGE;
        } else if (str.startsWith("//")) {
            str = URIUtil.HTTPS_COLON + str;
        }
        this.coverImage = str;
    }

    public void setDataList(List<PLVLinkMicItemDataBean> list) {
        this.dataList = list;
    }

    public void setFirstScreenLinkMicId(String str) {
        this.firstScreenLinkMicId = str;
    }

    public void setHasNotifyTeacherViewHolderBind(boolean z2) {
        this.hasNotifyTeacherViewHolderBind = z2;
    }

    public void setInvisibleItemLinkMicId(String str) {
        this.invisibleItemLinkMicId = str;
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public void setListShowMode(PLVLinkMicListShowMode pLVLinkMicListShowMode) {
        PLVCommonLog.d(TAG, "PLVLinkMicListAdapter.setListShowMode");
        this.listShowMode = pLVLinkMicListShowMode;
        updateAllItem();
    }

    public void setMediaInLinkMicListLinkMicId(String str) {
        this.mediaInLinkMicListLinkMicId = str;
    }

    public void setMyLinkMicId(String str) {
        this.myLinkMicId = str;
    }

    public void setOnlyAudio(boolean z2) {
        this.isOnlyAudio = z2;
    }

    public void setPlvPlayerLogoView(PLVPlayerLogoView pLVPlayerLogoView) {
        this.plvPlayerLogoView = pLVPlayerLogoView;
    }

    public void setShowRoundRect(boolean z2) {
        this.showRoundRect = z2;
        notifyDataSetChanged();
    }

    public void setSwitchViewHasMedia(@Nullable PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout) {
        this.switchViewHasMedia = pLVSwitchViewAnchorLayout;
    }

    public void setTeacherViewHolderBindListener(@Nullable OnTeacherSwitchViewBindListener onTeacherSwitchViewBindListener) {
        this.onTeacherSwitchViewBindListener = onTeacherSwitchViewBindListener;
    }

    public void showAllRenderView() {
        this.shouldHideAllRenderView = false;
        notifyDataSetChanged();
    }

    public void updateAllItem() {
        PLVCommonLog.d(TAG, "PLVLinkMicListAdapter.updateAllItem");
        notifyDataSetChanged();
    }

    public void updateCup(int i2) {
        notifyItemChanged(i2, PAYLOAD_UPDATE_CUP);
    }

    public void updateInvisibleItem(String str) {
        this.invisibleItemLinkMicId = str;
        notifyDataSetChanged();
    }

    public void updateNetQuality(int i2) {
        this.netQuality = i2;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 >= this.dataList.size()) {
                break;
            }
            if (this.dataList.get(i4).getLinkMicId().equals(this.myLinkMicId)) {
                i3 = i4;
                break;
            }
            i4++;
        }
        notifyItemChanged(i3, PAYLOAD_UPDATE_NET_QUALITY);
    }

    public void updateTeacherCoverImage() {
        if (this.dataList == null) {
            return;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.dataList.size()) {
                break;
            }
            if (this.dataList.get(i3).isTeacher()) {
                i2 = i3;
                break;
            }
            i3++;
        }
        notifyItemChanged(i2, PAYLOAD_UPDATE_COVER_IMAGE);
    }

    public void updateUserMuteVideo(final int i2) {
        this.rv.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                PLVLinkMicListAdapter.this.notifyItemChanged(i2, PLVLinkMicListAdapter.PAYLOAD_UPDATE_VIDEO_MUTE);
            }
        });
    }

    public void updateVolumeChanged() {
        LinearLayoutManager linearLayoutManager = this.linearLayoutManager;
        if (linearLayoutManager == null) {
            return;
        }
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int iFindLastVisibleItemPosition = this.linearLayoutManager.findLastVisibleItemPosition();
        int i2 = (iFindLastVisibleItemPosition - iFindFirstVisibleItemPosition) + 1;
        if (iFindFirstVisibleItemPosition != -1 && iFindLastVisibleItemPosition != -1 && i2 > 0) {
            notifyItemRangeChanged(iFindFirstVisibleItemPosition, i2, PAYLOAD_UPDATE_VOLUME);
        }
        if (TextUtils.isEmpty(this.invisibleItemLinkMicId)) {
            return;
        }
        for (int i3 = 0; i3 < this.dataList.size(); i3++) {
            if (this.dataList.get(i3).getLinkMicId().equals(this.invisibleItemLinkMicId)) {
                notifyItemChanged(i3, PAYLOAD_UPDATE_VOLUME);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i2, @NonNull List list) {
        onBindViewHolder((LinkMicItemViewHolder) viewHolder, i2, (List<Object>) list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public LinkMicItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        final View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plvlc_linkmic_scroll_item, viewGroup, false);
        viewInflate.getLayoutParams().width = getItemWidth();
        viewInflate.requestLayout();
        SurfaceView surfaceViewCreateLinkMicRenderView = this.adapterCallback.createLinkMicRenderView();
        final LinkMicItemViewHolder linkMicItemViewHolder = new LinkMicItemViewHolder(viewInflate);
        if (surfaceViewCreateLinkMicRenderView != null) {
            linkMicItemViewHolder.renderView = surfaceViewCreateLinkMicRenderView;
            linkMicItemViewHolder.flRenderViewContainer.addView(surfaceViewCreateLinkMicRenderView, 0, getRenderViewLayoutParam());
        } else {
            PLVCommonLog.e(TAG, "create render view return null");
        }
        linkMicItemViewHolder.flRenderViewContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.2
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(final View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                int i11 = i5 - i3;
                final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(linkMicItemViewHolder.qualityWidget.getLayoutParams());
                if (i11 == PLVLinkMicListAdapter.this.getItemWidth()) {
                    view.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (view.getWidth() != PLVLinkMicListAdapter.this.getItemWidth()) {
                                return;
                            }
                            linkMicItemViewHolder.tvNick.setVisibility(0);
                            linkMicItemViewHolder.ivMicState.setVisibility(0);
                            if (viewInflate.getContext() != null) {
                                if (PLVScreenUtils.isPortrait(viewInflate.getContext())) {
                                    AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                    PLVLinkMicListAdapter.this.updateNetQualityLayout(viewInflate.getContext(), layoutParams, 12, 4);
                                } else {
                                    AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                                    PLVLinkMicListAdapter.this.updateNetQualityLayout(viewInflate.getContext(), layoutParams, 16, 4);
                                }
                                linkMicItemViewHolder.qualityWidget.setLayoutParams(layoutParams);
                            }
                        }
                    });
                    return;
                }
                linkMicItemViewHolder.tvNick.setVisibility(8);
                linkMicItemViewHolder.ivMicState.setVisibility(8);
                view.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (viewInflate.getContext() != null) {
                            if (PLVScreenUtils.isPortrait(viewInflate.getContext())) {
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                PLVLinkMicListAdapter.this.updateNetQualityLayout(viewInflate.getContext(), layoutParams, 20, 12);
                            } else {
                                AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                                PLVLinkMicListAdapter.this.updateNetQualityLayout(viewInflate.getContext(), layoutParams, 24, 16);
                            }
                            linkMicItemViewHolder.qualityWidget.setLayoutParams(layoutParams);
                        }
                    }
                });
            }
        });
        return linkMicItemViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(@NonNull LinkMicItemViewHolder linkMicItemViewHolder) {
        super.onViewRecycled((PLVLinkMicListAdapter) linkMicItemViewHolder);
        if (linkMicItemViewHolder.renderView != null && !linkMicItemViewHolder.switchViewAnchorLayout.isViewSwitched() && linkMicItemViewHolder.renderView != this.localRenderView) {
            linkMicItemViewHolder.isViewRecycled = true;
            linkMicItemViewHolder.flRenderViewContainer.removeView(linkMicItemViewHolder.renderView);
            this.adapterCallback.releaseRenderView(linkMicItemViewHolder.renderView);
            linkMicItemViewHolder.renderView = null;
        }
        PLVCommonLog.d(TAG, "onViewRecycled pos=" + linkMicItemViewHolder.getAdapterPosition() + " holder=" + linkMicItemViewHolder.toString());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final LinkMicItemViewHolder linkMicItemViewHolder, int i2) {
        if (linkMicItemViewHolder.isViewRecycled) {
            linkMicItemViewHolder.isViewRecycled = false;
            linkMicItemViewHolder.renderView = this.adapterCallback.createLinkMicRenderView();
            if (linkMicItemViewHolder.renderView != null) {
                linkMicItemViewHolder.flRenderViewContainer.addView(linkMicItemViewHolder.renderView, 0, getRenderViewLayoutParam());
                linkMicItemViewHolder.isRenderViewSetup = false;
            } else {
                PLVCommonLog.e(TAG, String.format(Locale.US, "create render view return null at position:%d", Integer.valueOf(i2)));
            }
        }
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.dataList.get(i2);
        String linkMicId = pLVLinkMicItemDataBean.getLinkMicId();
        String nick = pLVLinkMicItemDataBean.getNick();
        pLVLinkMicItemDataBean.getCupNum();
        pLVLinkMicItemDataBean.isMuteVideo();
        boolean zIsMuteAudio = pLVLinkMicItemDataBean.isMuteAudio();
        int curVolume = pLVLinkMicItemDataBean.getCurVolume();
        String actor = pLVLinkMicItemDataBean.getActor();
        boolean zIsTeacher = pLVLinkMicItemDataBean.isTeacher();
        pLVLinkMicItemDataBean.isGuest();
        boolean zEquals = this.firstScreenLinkMicId.equals(linkMicId);
        this.myLinkMicId.equals(linkMicId);
        if (this.isPausing) {
            linkMicItemViewHolder.linkMicRenderViewPausePlaceholder.setVisibility(0);
            linkMicItemViewHolder.linkMicRenderViewPausePlaceholder.bringToFront();
        } else {
            linkMicItemViewHolder.linkMicRenderViewPausePlaceholder.setVisibility(8);
        }
        if (zIsTeacher) {
            this.teacherViewHolder = linkMicItemViewHolder;
            OnTeacherSwitchViewBindListener onTeacherSwitchViewBindListener = this.onTeacherSwitchViewBindListener;
            if (onTeacherSwitchViewBindListener != null && !this.hasNotifyTeacherViewHolderBind) {
                this.hasNotifyTeacherViewHolderBind = true;
                onTeacherSwitchViewBindListener.onTeacherSwitchViewBind(linkMicItemViewHolder.switchViewAnchorLayout);
            }
            bindCoverImage(linkMicItemViewHolder, this.isOnlyAudio, zIsTeacher);
        }
        bindLogoView(linkMicItemViewHolder, zEquals);
        ViewGroup.LayoutParams layoutParams = linkMicItemViewHolder.itemView.getLayoutParams();
        String str = this.invisibleItemLinkMicId;
        if (str != null && str.equals(linkMicId)) {
            if (layoutParams != null) {
                layoutParams.width = 0;
                layoutParams.height = 0;
            }
            linkMicItemViewHolder.itemView.setVisibility(8);
        } else {
            if (layoutParams != null) {
                layoutParams.width = getItemWidth();
                layoutParams.height = -2;
            }
            linkMicItemViewHolder.itemView.setVisibility(0);
        }
        linkMicItemViewHolder.switchViewAnchorLayout.setTag(R.id.tag_link_mic_id, linkMicId);
        linkMicItemViewHolder.plvPlayerLogoView.addLogo(this.plvPlayerLogoView.getParamZero());
        if (this.showRoundRect) {
            linkMicItemViewHolder.roundRectLayout.setCornerRadius(PLVScreenUtils.dip2px(8.0f));
        } else {
            linkMicItemViewHolder.roundRectLayout.setCornerRadius(0);
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(actor)) {
            sb.append(actor);
            sb.append("-");
        }
        sb.append(nick);
        if (this.myLinkMicId.equals(linkMicId)) {
            sb.append("（我）");
        }
        linkMicItemViewHolder.tvNick.setText(sb.toString());
        boolean zResolveListShowMode = resolveListShowMode(linkMicItemViewHolder, i2);
        setMicrophoneVolumeIcon(curVolume, zIsMuteAudio, linkMicItemViewHolder);
        bindVideoMute(linkMicItemViewHolder, zResolveListShowMode, linkMicId);
        if (this.myLinkMicId.equals(linkMicId)) {
            this.localRenderView = linkMicItemViewHolder.renderView;
        }
        linkMicItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PLVLinkMicListAdapter.this.switchViewHasMedia != null) {
                    PLVCommonLog.d(PLVLinkMicListAdapter.TAG, "onClick and media in link mic list");
                    try {
                        PLVLinkMicListAdapter.this.rv.getChildViewHolder((View) PLVLinkMicListAdapter.this.switchViewHasMedia.getParent().getParent()).getAdapterPosition();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                int adapterPosition = linkMicItemViewHolder.getAdapterPosition();
                boolean z2 = adapterPosition != PLVLinkMicListAdapter.this.getMediaViewIndexInLinkMicList();
                PLVLinkMicListAdapter.this.adapterCallback.onClickItemListener(adapterPosition, PLVLinkMicListAdapter.this.switchViewHasMedia, linkMicItemViewHolder.switchViewAnchorLayout);
                if (z2) {
                    PLVLinkMicListAdapter.this.switchViewHasMedia = linkMicItemViewHolder.switchViewAnchorLayout;
                } else {
                    PLVLinkMicListAdapter.this.switchViewHasMedia = null;
                }
                PLVLinkMicListAdapter.this.updateAllItem();
            }
        });
        if (linkMicItemViewHolder.renderView != null && !linkMicItemViewHolder.switchViewAnchorLayout.isViewSwitched()) {
            if (this.shouldHideAllRenderView) {
                linkMicItemViewHolder.renderView.setVisibility(4);
            } else {
                linkMicItemViewHolder.renderView.setVisibility(0);
            }
        }
        if (linkMicId.equals(this.myLinkMicId)) {
            linkMicItemViewHolder.qualityWidget.setVisibility(0);
        } else {
            linkMicItemViewHolder.qualityWidget.setVisibility(8);
        }
    }

    public void onBindViewHolder(@NonNull LinkMicItemViewHolder linkMicItemViewHolder, int i2, @NonNull List<Object> list) {
        String str;
        if (list.isEmpty()) {
            super.onBindViewHolder((PLVLinkMicListAdapter) linkMicItemViewHolder, i2, list);
            return;
        }
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.dataList.get(i2);
        String linkMicId = pLVLinkMicItemDataBean.getLinkMicId();
        pLVLinkMicItemDataBean.getNick();
        pLVLinkMicItemDataBean.getActor();
        int cupNum = pLVLinkMicItemDataBean.getCupNum();
        pLVLinkMicItemDataBean.isMuteVideo();
        boolean zIsMuteAudio = pLVLinkMicItemDataBean.isMuteAudio();
        int curVolume = pLVLinkMicItemDataBean.getCurVolume();
        boolean zIsTeacher = pLVLinkMicItemDataBean.isTeacher();
        pLVLinkMicItemDataBean.isGuest();
        this.firstScreenLinkMicId.equals(linkMicId);
        this.myLinkMicId.equals(linkMicId);
        boolean zResolveListShowMode = resolveListShowMode(linkMicItemViewHolder, i2);
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            String string = it.next().toString();
            string.hashCode();
            switch (string) {
                case "updateCup":
                    if (cupNum != 0) {
                        linkMicItemViewHolder.llCupLayout.setVisibility(0);
                        TextView textView = linkMicItemViewHolder.tvCupNumView;
                        if (cupNum > 99) {
                            str = "99+";
                        } else {
                            str = cupNum + "";
                        }
                        textView.setText(str);
                        break;
                    } else {
                        linkMicItemViewHolder.llCupLayout.setVisibility(8);
                        break;
                    }
                case "updateVideoMute":
                    bindVideoMute(linkMicItemViewHolder, zResolveListShowMode, linkMicId);
                    break;
                case "updateNetQuality":
                    if (!linkMicId.equals(this.myLinkMicId)) {
                        break;
                    } else {
                        linkMicItemViewHolder.qualityWidget.setNetQuality(this.netQuality);
                        break;
                    }
                case "updateVolume":
                    setMicrophoneVolumeIcon(curVolume, zIsMuteAudio, linkMicItemViewHolder);
                    break;
                case "updateCoverImage":
                    bindCoverImage(linkMicItemViewHolder, this.isOnlyAudio, zIsTeacher);
                    break;
            }
        }
    }
}
