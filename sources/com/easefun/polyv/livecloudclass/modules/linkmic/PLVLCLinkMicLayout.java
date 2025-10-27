package com.easefun.polyv.livecloudclass.modules.linkmic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar;
import com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout;
import com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter;
import com.easefun.polyv.livecloudclass.modules.linkmic.widget.PLVLinkMicRvLandscapeItemDecoration;
import com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowMode;
import com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter;
import com.easefun.polyv.livecommon.module.utils.PLVForegroundService;
import com.easefun.polyv.livecommon.module.utils.PLVNotchUtils;
import com.easefun.polyv.livecommon.module.utils.PLVViewSwitcher;
import com.easefun.polyv.livecommon.ui.widget.PLVPlayerLogoView;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.hjq.permissions.Permission;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCLinkMicLayout extends FrameLayout implements IPLVLinkMicContract.IPLVLinkMicView, IPLVLCLinkMicLayout {
    private static final int DP_LAND_LINK_MIC_LIST_MARGIN_LEFT = 8;
    private static final int DP_LAND_LINK_MIC_LIST_MARGIN_RIGHT = 34;
    private static final int DP_LAND_SPEAKING_USER_VIEW_MARGIN_RIGHT_TO_LINK_MIC_LIST = 24;
    private static final int ERROR_PERMISSION_DENIED = 1060501;
    private static final String TAG = "PLVLCLinkMicLayout";
    private static final int TRY_SCROLL_VIEW_STATE_INVISIBLE_BY_LACK = 0;
    private static final int TRY_SCROLL_VIEW_STATE_INVISIBLE_BY_SCROLLED = 2;
    private static final int TRY_SCROLL_VIEW_STATE_VISIBLE = 1;
    private boolean curIsLandscape;
    private int curTryScrollViewState;
    private FrameLayout flMediaLinkMicRoot;
    private boolean isMediaShowInLinkMicList;
    private boolean isShowLandscapeLayout;
    private PLVLinkMicRvLandscapeItemDecoration landscapeItemDecoration;
    private int landscapeWidth;
    private IPLVLCLinkMicControlBar linkMicControlBar;
    private PLVLinkMicListAdapter linkMicListAdapter;
    private IPLVLinkMicContract.IPLVLinkMicPresenter linkMicPresenter;
    private PLVLiveChannelType liveChannelType;
    private LinearLayout llSpeakingUsers;
    private LinearLayout llTryScrollTip;

    @Nullable
    private String mediaInLinkMicListLinkMicId;
    private IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener;
    private RecyclerView.OnScrollListener onScrollTryScrollTipListener;
    private final List<Runnable> onUserJoinPendingTask;
    private RecyclerView rvLinkMicList;
    private PLVViewSwitcher teacherLocationViewSwitcher;
    private TextView tvSpeakingUsersText;

    /* renamed from: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout$12, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass12 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$livescenes$config$PLVLiveChannelType;

        static {
            int[] iArr = new int[PLVLiveChannelType.values().length];
            $SwitchMap$com$plv$livescenes$config$PLVLiveChannelType = iArr;
            try {
                iArr[PLVLiveChannelType.PPT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$livescenes$config$PLVLiveChannelType[PLVLiveChannelType.ALONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TryScrollViewStateType {
    }

    public PLVLCLinkMicLayout(@NonNull Context context) {
        this(context, null);
    }

    private void changeShowOrHideLandscapeLayoutState(boolean z2) {
        this.onPLVLinkMicLayoutListener.onShowLandscapeRTCLayout(z2);
        if (this.curIsLandscape) {
            showOrHideLandscapeLayout(z2);
        }
        this.isShowLandscapeLayout = z2;
    }

    private int getRvScrolledXOffset() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.rvLinkMicList.getLayoutManager();
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        View viewFindViewByPosition = linearLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
        return (iFindFirstVisibleItemPosition * viewFindViewByPosition.getWidth()) - viewFindViewByPosition.getLeft();
    }

    private void initLinkMicControlBar(IPLVLCLinkMicControlBar iPLVLCLinkMicControlBar) {
        if (iPLVLCLinkMicControlBar == null) {
            PLVCommonLog.exception(new Throwable("linkMicController == null"));
        } else {
            this.linkMicControlBar = iPLVLCLinkMicControlBar;
            iPLVLCLinkMicControlBar.setOnPLCLinkMicControlBarListener(new IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.2
                @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener
                public void onClickCameraFrontOfBack(boolean z2) {
                    PLVLCLinkMicLayout.this.linkMicPresenter.switchCamera();
                }

                @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener
                public void onClickCameraOpenOrClose(boolean z2) {
                    PLVLCLinkMicLayout.this.linkMicPresenter.muteVideo(z2);
                }

                @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener
                public void onClickMicroPhoneOpenOrClose(boolean z2) {
                    PLVLCLinkMicLayout.this.linkMicPresenter.muteAudio(z2);
                }

                @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener
                public void onClickRingOffLinkMic() {
                    if (PLVLCLinkMicLayout.this.linkMicPresenter.isJoinLinkMic()) {
                        PLVLCLinkMicLayout.this.linkMicPresenter.leaveLinkMic();
                        return;
                    }
                    PLVLCLinkMicLayout.this.linkMicPresenter.cancelRequestJoinLinkMic();
                    if (PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener != null) {
                        PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener.onCancelRequestJoinLinkMic();
                    }
                }

                @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener
                public void onClickRingUpLinkMic() {
                    PLVLCLinkMicLayout.this.linkMicPresenter.requestJoinLinkMic();
                    if (PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener != null) {
                        PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener.onRequestJoinLinkMic();
                    }
                }
            });
        }
    }

    private void initShouldShowLandscapeRTCLayout() {
        int i2 = AnonymousClass12.$SwitchMap$com$plv$livescenes$config$PLVLiveChannelType[this.liveChannelType.ordinal()];
        if (i2 == 1) {
            changeShowOrHideLandscapeLayoutState(true);
        } else {
            if (i2 != 2) {
                return;
            }
            changeShowOrHideLandscapeLayoutState(false);
        }
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_linkmic_media_layout, (ViewGroup) this, true);
        this.flMediaLinkMicRoot = (FrameLayout) findViewById(R.id.plvlc_linkmic_fl_media_linkmic_root);
        this.rvLinkMicList = (RecyclerView) findViewById(R.id.plvlc_link_mic_rv_linkmic_list);
        this.llTryScrollTip = (LinearLayout) findViewById(R.id.plvlc_link_mic_ll_try_scroll_tip);
        this.llSpeakingUsers = (LinearLayout) findViewById(R.id.plvlc_linkmic_ll_speaking_users);
        this.tvSpeakingUsersText = (TextView) findViewById(R.id.plvlc_linkmic_tv_speaking_users_text);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 0, false);
        this.rvLinkMicList.setLayoutManager(linearLayoutManager);
        this.rvLinkMicList.addItemDecoration(this.landscapeItemDecoration);
        this.rvLinkMicList.getItemAnimator().setAddDuration(0L);
        this.rvLinkMicList.getItemAnimator().setChangeDuration(0L);
        this.rvLinkMicList.getItemAnimator().setMoveDuration(0L);
        this.rvLinkMicList.getItemAnimator().setRemoveDuration(0L);
        RecyclerView.ItemAnimator itemAnimator = this.rvLinkMicList.getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        this.linkMicListAdapter = new PLVLinkMicListAdapter(this.rvLinkMicList, linearLayoutManager, new PLVLinkMicListAdapter.OnPLVLinkMicAdapterCallback() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.1
            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.OnPLVLinkMicAdapterCallback
            public SurfaceView createLinkMicRenderView() {
                return PLVLCLinkMicLayout.this.linkMicPresenter.createRenderView(Utils.getApp());
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.OnPLVLinkMicAdapterCallback
            public void muteAllAudioVideo(boolean z2) {
                PLVLCLinkMicLayout.this.linkMicPresenter.muteAllAudio(z2);
                PLVLCLinkMicLayout.this.linkMicPresenter.muteAllVideo(z2);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.OnPLVLinkMicAdapterCallback
            public void muteAudioVideo(String str, boolean z2) {
                PLVLCLinkMicLayout.this.linkMicPresenter.muteAudio(str, z2);
                PLVLCLinkMicLayout.this.linkMicPresenter.muteVideo(str, z2);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.OnPLVLinkMicAdapterCallback
            public void onClickItemListener(int i2, @Nullable PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout, PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout2) {
                if (PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener != null) {
                    if (pLVSwitchViewAnchorLayout == null || pLVSwitchViewAnchorLayout == pLVSwitchViewAnchorLayout2) {
                        PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener.onClickSwitchWithMediaOnce(pLVSwitchViewAnchorLayout2);
                    } else {
                        PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener.onClickSwitchWithMediaTwice(pLVSwitchViewAnchorLayout, pLVSwitchViewAnchorLayout2);
                    }
                }
                if (pLVSwitchViewAnchorLayout == pLVSwitchViewAnchorLayout2) {
                    PLVLCLinkMicLayout.this.isMediaShowInLinkMicList = false;
                    PLVLCLinkMicLayout.this.setMediaInLinkMicListLinkMicId(null);
                } else {
                    PLVLCLinkMicLayout.this.isMediaShowInLinkMicList = true;
                    PLVLCLinkMicLayout.this.setMediaInLinkMicListLinkMicId(String.valueOf(pLVSwitchViewAnchorLayout2.getTag(R.id.tag_link_mic_id)));
                }
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.OnPLVLinkMicAdapterCallback
            public void releaseRenderView(SurfaceView surfaceView) {
                PLVLCLinkMicLayout.this.linkMicPresenter.releaseRenderView(surfaceView);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.OnPLVLinkMicAdapterCallback
            public void setupRenderView(SurfaceView surfaceView, String str) {
                PLVLCLinkMicLayout.this.linkMicPresenter.setupRenderView(surfaceView, str);
            }
        });
        this.curIsLandscape = PLVScreenUtils.isLandscape(getContext());
    }

    private void observeOnAudioState(final IPLVLiveRoomDataManager iPLVLiveRoomDataManager) {
        iPLVLiveRoomDataManager.getIsOnlyAudioEnabled().observe((LifecycleOwner) getContext(), new Observer<Boolean>() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Boolean bool) {
                if (bool == null) {
                    bool = Boolean.FALSE;
                }
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(Permission.RECORD_AUDIO);
                if (!bool.booleanValue()) {
                    arrayList.add(Permission.CAMERA);
                }
                PLVLCLinkMicLayout.this.linkMicPresenter.resetRequestPermissionList(arrayList);
                if (PLVLCLinkMicLayout.this.linkMicListAdapter != null) {
                    PLVLCLinkMicLayout.this.linkMicListAdapter.setOnlyAudio(bool.booleanValue());
                    PLVLCLinkMicLayout.this.linkMicListAdapter.updateTeacherCoverImage();
                }
            }
        });
        iPLVLiveRoomDataManager.getClassDetailVO().observe((LifecycleOwner) getContext(), new Observer<PLVStatefulData<PolyvLiveClassDetailVO>>() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PolyvLiveClassDetailVO> pLVStatefulData) {
                PolyvLiveClassDetailVO data;
                iPLVLiveRoomDataManager.getClassDetailVO().removeObserver(this);
                if (pLVStatefulData == null || !pLVStatefulData.isSuccess() || (data = pLVStatefulData.getData()) == null || data.getData() == null) {
                    return;
                }
                String splashImg = data.getData().getSplashImg();
                if (PLVLCLinkMicLayout.this.linkMicListAdapter != null) {
                    PLVLCLinkMicLayout.this.linkMicListAdapter.setCoverImage(splashImg);
                    PLVLCLinkMicLayout.this.linkMicListAdapter.updateTeacherCoverImage();
                }
            }
        });
    }

    @SuppressLint({"RtlHardcoded"})
    private void onLandscape() {
        updatePushResolution(true);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
        layoutParams.topToBottom = -1;
        layoutParams.topToTop = 0;
        layoutParams.bottomToBottom = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams).width = -1;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = 0;
        layoutParams.rightToRight = 0;
        setLayoutParams(layoutParams);
        showOrHideLandscapeLayout(this.isShowLandscapeLayout);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.rvLinkMicList.getLayoutParams();
        layoutParams2.gravity = 3;
        layoutParams2.leftMargin = PLVScreenUtils.dip2px(8.0f);
        this.rvLinkMicList.setLayoutParams(layoutParams2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 1, false);
        this.rvLinkMicList.setLayoutManager(linearLayoutManager);
        this.linkMicListAdapter.setLinearLayoutManager(linearLayoutManager);
        this.landscapeItemDecoration.setLandscape();
        this.linkMicListAdapter.setShowRoundRect(true);
        this.llTryScrollTip.setVisibility(8);
        if (isJoinChannel()) {
            this.llSpeakingUsers.setVisibility(0);
        } else {
            this.llSpeakingUsers.setVisibility(8);
        }
    }

    @SuppressLint({"RtlHardcoded"})
    private void onPortrait() {
        updatePushResolution(false);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = -1;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = -2;
        layoutParams.topToBottom = R.id.plvlc_video_viewstub;
        layoutParams.topToTop = -1;
        layoutParams.bottomToBottom = -1;
        setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.flMediaLinkMicRoot.getLayoutParams();
        layoutParams2.width = -1;
        layoutParams2.height = -2;
        layoutParams2.gravity = 3;
        this.flMediaLinkMicRoot.setLayoutParams(layoutParams2);
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.rvLinkMicList.getLayoutParams();
        layoutParams3.leftMargin = 0;
        this.rvLinkMicList.setLayoutParams(layoutParams3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 0, false);
        this.rvLinkMicList.setLayoutManager(linearLayoutManager);
        this.linkMicListAdapter.setLinearLayoutManager(linearLayoutManager);
        this.landscapeItemDecoration.setPortrait();
        this.linkMicListAdapter.setShowRoundRect(false);
        this.llSpeakingUsers.setVisibility(8);
        if (this.linkMicPresenter.isTeacherOpenLinkMic()) {
            this.linkMicControlBar.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMediaInLinkMicListLinkMicId(String str) {
        this.mediaInLinkMicListLinkMicId = str;
        PLVLinkMicListAdapter pLVLinkMicListAdapter = this.linkMicListAdapter;
        if (pLVLinkMicListAdapter != null) {
            pLVLinkMicListAdapter.setMediaInLinkMicListLinkMicId(str);
        }
    }

    private void showOrHideLandscapeLayout(boolean z2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.flMediaLinkMicRoot.getLayoutParams();
        layoutParams.width = z2 ? this.landscapeWidth : 0;
        layoutParams.height = -1;
        layoutParams.gravity = 5;
        this.flMediaLinkMicRoot.setLayoutParams(layoutParams);
    }

    private void tryShowOrHideLandscapeRTCLayout(boolean z2) {
        if (z2) {
            if (this.liveChannelType != PLVLiveChannelType.ALONE || this.linkMicPresenter.getRTCListSize() <= 1) {
                return;
            }
            changeShowOrHideLandscapeLayoutState(true);
            return;
        }
        if (this.liveChannelType != PLVLiveChannelType.ALONE || this.linkMicPresenter.getRTCListSize() > 1) {
            return;
        }
        changeShowOrHideLandscapeLayoutState(false);
    }

    private void updatePushResolution(boolean z2) {
        IPLVLinkMicContract.IPLVLinkMicPresenter iPLVLinkMicPresenter = this.linkMicPresenter;
        if (iPLVLinkMicPresenter != null) {
            iPLVLinkMicPresenter.setPushPictureResolutionType(z2 ? 2 : 1);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void destroy() {
        this.linkMicPresenter.destroy();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public int getLandscapeWidth() {
        return this.landscapeWidth;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public int getMediaViewIndexInLinkMicList() {
        return this.linkMicListAdapter.getMediaViewIndexInLinkMicList();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void hideAll() {
        PLVCommonLog.d(TAG, "hide");
        hideLinkMicList();
        this.linkMicControlBar.hide();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void hideControlBar() {
        PLVCommonLog.d(TAG, "hide");
        IPLVLCLinkMicControlBar iPLVLCLinkMicControlBar = this.linkMicControlBar;
        if (iPLVLCLinkMicControlBar == null || !this.curIsLandscape) {
            return;
        }
        iPLVLCLinkMicControlBar.hide();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void hideLinkMicList() {
        PLVCommonLog.d(TAG, "hideOnlyLinkMicList");
        this.linkMicListAdapter.hideAllRenderView();
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.3
            @Override // java.lang.Runnable
            public void run() {
                PLVLCLinkMicLayout.this.setVisibility(8);
            }
        });
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void init(IPLVLiveRoomDataManager iPLVLiveRoomDataManager, IPLVLCLinkMicControlBar iPLVLCLinkMicControlBar) {
        this.liveChannelType = iPLVLiveRoomDataManager.getConfig().getChannelType();
        this.linkMicPresenter = new PLVLinkMicPresenter(iPLVLiveRoomDataManager, this);
        initLinkMicControlBar(iPLVLCLinkMicControlBar);
        updatePushResolution(this.curIsLandscape);
        observeOnAudioState(iPLVLiveRoomDataManager);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public boolean isJoinChannel() {
        return this.linkMicPresenter.isJoinChannel();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView, com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public boolean isMediaShowInLinkMicList() {
        return this.isMediaShowInLinkMicList;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public boolean isPausing() {
        return this.linkMicListAdapter.isPausing();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void notifySwitchedPptToMainScreenOnJoinChannel() {
        if (this.linkMicListAdapter.getFirstScreenSwitchView() != null) {
            onSwitchPPTViewLocation(false);
        } else {
            this.onUserJoinPendingTask.add(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.4
                @Override // java.lang.Runnable
                public void run() {
                    PLVLCLinkMicLayout.this.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVLCLinkMicLayout.this.onSwitchPPTViewLocation(false);
                        }
                    });
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onAdjustTeacherLocation(final String str, int i2, boolean z2, final Runnable runnable) {
        if (z2) {
            this.linkMicListAdapter.setTeacherViewHolderBindListener(new PLVLinkMicListAdapter.OnTeacherSwitchViewBindListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.9
                @Override // com.easefun.polyv.livecloudclass.modules.linkmic.adapter.PLVLinkMicListAdapter.OnTeacherSwitchViewBindListener
                public void onTeacherSwitchViewBind(PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout) {
                    PLVLCLinkMicLayout.this.linkMicListAdapter.setInvisibleItemLinkMicId(str);
                    if (PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener != null) {
                        PLVLCLinkMicLayout.this.teacherLocationViewSwitcher = new PLVViewSwitcher();
                        PLVLCLinkMicLayout.this.onPLVLinkMicLayoutListener.onChangeTeacherLocation(PLVLCLinkMicLayout.this.teacherLocationViewSwitcher, pLVSwitchViewAnchorLayout);
                        runnable.run();
                    }
                    PLVLCLinkMicLayout.this.rvLinkMicList.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.9.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVLCLinkMicLayout.this.linkMicListAdapter.updateInvisibleItem(str);
                        }
                    });
                }
            });
        } else {
            this.linkMicListAdapter.updateInvisibleItem(str);
            runnable.run();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onChangeListShowMode(PLVLinkMicListShowMode pLVLinkMicListShowMode) {
        this.linkMicListAdapter.setListShowMode(pLVLinkMicListShowMode);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        int i2 = configuration.orientation;
        if (i2 == 2) {
            PLVCommonLog.d(TAG, "onConfigurationChanged->landscape");
            if (!this.curIsLandscape) {
                onLandscape();
            }
            this.curIsLandscape = true;
            return;
        }
        if (i2 == 1) {
            PLVCommonLog.d(TAG, "onConfigurationChanged->portrait");
            if (this.curIsLandscape) {
                onPortrait();
            }
            this.curIsLandscape = false;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onJoinChannelTimeout() {
        ToastUtils.showShort("加入频道超时，请重试");
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onJoinLinkMic() {
        PLVCommonLog.d(TAG, "onJoinLinkMic");
        ToastUtils.showShort("上麦成功");
        ((PLVLCFloatingWindow) PLVDependManager.getInstance().get(PLVLCFloatingWindow.class)).showByUser(false);
        resume();
        this.linkMicControlBar.setIsAudio(this.linkMicPresenter.getIsAudioLinkMic());
        this.linkMicControlBar.setJoinLinkMicSuccess();
        IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener = this.onPLVLinkMicLayoutListener;
        if (onPLVLinkMicLayoutListener != null) {
            onPLVLinkMicLayoutListener.onJoinLinkMic();
        }
        tryShowOrHideLandscapeRTCLayout(true);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onJoinRtcChannel() {
        this.curTryScrollViewState = 0;
        this.flMediaLinkMicRoot.setKeepScreenOn(true);
        this.flMediaLinkMicRoot.setVisibility(0);
        this.linkMicListAdapter.updateAllItem();
        PLVForegroundService.startForegroundService(((Activity) getContext()).getClass(), "云课堂", R.drawable.ic_launcher);
        IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener = this.onPLVLinkMicLayoutListener;
        if (onPLVLinkMicLayoutListener != null) {
            onPLVLinkMicLayoutListener.onJoinRtcChannel();
        }
        this.isMediaShowInLinkMicList = false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onLeaveLinkMic() {
        this.linkMicControlBar.setLeaveLinkMic();
        IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener = this.onPLVLinkMicLayoutListener;
        if (onPLVLinkMicLayoutListener != null) {
            onPLVLinkMicLayoutListener.onLeaveLinkMic();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onLeaveRtcChannel() {
        IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener;
        if (this.linkMicPresenter.isAloneChannelTypeSupportRTC()) {
            PLVViewSwitcher pLVViewSwitcher = this.teacherLocationViewSwitcher;
            if (pLVViewSwitcher == null || !pLVViewSwitcher.isViewSwitched()) {
                PLVCommonLog.exception(new Exception("teacherLocationViewSwitcher should not be null"));
            } else {
                this.teacherLocationViewSwitcher.switchView();
            }
        } else if (this.isMediaShowInLinkMicList && this.linkMicListAdapter.getSwitchViewHasMedia() != null && (onPLVLinkMicLayoutListener = this.onPLVLinkMicLayoutListener) != null) {
            onPLVLinkMicLayoutListener.onClickSwitchWithMediaOnce(this.linkMicListAdapter.getSwitchViewHasMedia());
        }
        this.linkMicListAdapter.updateAllItem();
        this.linkMicListAdapter.releaseView();
        this.rvLinkMicList.removeAllViews();
        tryShowOrHideLandscapeRTCLayout(false);
        this.flMediaLinkMicRoot.setVisibility(8);
        this.flMediaLinkMicRoot.setKeepScreenOn(false);
        this.llTryScrollTip.setVisibility(8);
        this.isMediaShowInLinkMicList = false;
        this.teacherLocationViewSwitcher = null;
        setMediaInLinkMicListLinkMicId(null);
        PLVForegroundService.stopForegroundService();
        IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener2 = this.onPLVLinkMicLayoutListener;
        if (onPLVLinkMicLayoutListener2 != null) {
            onPLVLinkMicLayoutListener2.onLeaveRtcChannel();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onLinkMicError(int i2, Throwable th) {
        PLVCommonLog.exception(th);
        if (i2 == 1060501) {
            new AlertDialog.Builder(getContext()).setTitle(R.string.plv_common_dialog_tip).setMessage(R.string.plv_linkmic_error_tip_permission_denied).setPositiveButton(R.string.plv_common_dialog_confirm, new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i3) {
                    PLVFastPermission.getInstance().jump2Settings(PLVLCLinkMicLayout.this.getContext());
                }
            }).setNegativeButton(R.string.plv_common_dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i3) {
                    Toast.makeText(PLVLCLinkMicLayout.this.getContext(), R.string.plv_linkmic_error_tip_permission_cancel, 0).show();
                }
            }).setCancelable(false).show();
            return;
        }
        ToastUtils.showShort(getResources().getString(R.string.plv_linkmic_toast_error) + i2);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onLocalUserMicVolumeChanged() {
        this.linkMicListAdapter.updateVolumeChanged();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onNetQuality(int i2) {
        this.linkMicListAdapter.updateNetQuality(i2);
        IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener = this.onPLVLinkMicLayoutListener;
        if (onPLVLinkMicLayoutListener != null) {
            onPLVLinkMicLayoutListener.onNetworkQuality(i2);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onNotInLinkMicList() {
        ToastUtils.showShort("连麦重连超时，请重新上麦");
        this.linkMicPresenter.leaveLinkMic();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onPrepareLinkMicList(String str, PLVLinkMicListShowMode pLVLinkMicListShowMode, List<PLVLinkMicItemDataBean> list) {
        PLVCommonLog.d(TAG, "PLVLinkMicLayout.onBeforeJoinChannel");
        this.linkMicListAdapter.setDataList(list);
        this.linkMicListAdapter.setListShowMode(pLVLinkMicListShowMode);
        this.linkMicListAdapter.setMyLinkMicId(str);
        this.rvLinkMicList.setAdapter(this.linkMicListAdapter);
        this.landscapeWidth = this.linkMicListAdapter.getItemWidth() + PLVScreenUtils.dip2px(8.0f) + (PLVNotchUtils.hasNotchInScreen((Activity) getContext()) ? PLVScreenUtils.dip2px(34.0f) : 0);
        if (PLVScreenUtils.isPortrait(getContext())) {
            onPortrait();
        } else {
            onLandscape();
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.llSpeakingUsers.getLayoutParams();
        marginLayoutParams.rightMargin = this.landscapeWidth + PLVScreenUtils.dip2px(24.0f);
        this.llSpeakingUsers.setLayoutParams(marginLayoutParams);
        this.linkMicControlBar.setIsAudio(this.linkMicPresenter.getIsAudioLinkMic());
        initShouldShowLandscapeRTCLayout();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onRTCPrepared() {
        this.onPLVLinkMicLayoutListener.onRTCPrepared();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onRemoteUserVolumeChanged(List<PLVLinkMicItemDataBean> list) {
        boolean z2;
        this.linkMicListAdapter.updateVolumeChanged();
        if (PLVScreenUtils.isLandscape(getContext())) {
            Iterator<PLVLinkMicItemDataBean> it = list.iterator();
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean = null;
            while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                }
                PLVLinkMicItemDataBean next = it.next();
                if (!next.getLinkMicId().equals(this.linkMicPresenter.getLinkMicId()) && next.getCurVolume() != 0) {
                    if (pLVLinkMicItemDataBean != null) {
                        z2 = true;
                        break;
                    }
                    pLVLinkMicItemDataBean = next;
                }
            }
            if (pLVLinkMicItemDataBean == null) {
                this.llSpeakingUsers.setVisibility(8);
                return;
            }
            String nick = pLVLinkMicItemDataBean.getNick();
            StringBuilder sb = new StringBuilder();
            if (nick.length() > 8) {
                sb.append((CharSequence) nick, 0, 8);
            } else {
                sb.append(nick);
            }
            if (z2) {
                sb.append("...等");
            }
            this.tvSpeakingUsersText.setText(sb.toString());
            this.llSpeakingUsers.setVisibility(0);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onSwitchFirstScreen(String str) {
        this.linkMicListAdapter.updateAllItem();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onSwitchPPTViewLocation(boolean z2) {
        PLVSwitchViewAnchorLayout firstScreenSwitchView = this.linkMicListAdapter.getFirstScreenSwitchView();
        if (firstScreenSwitchView == null) {
            return;
        }
        if (!this.isMediaShowInLinkMicList || this.linkMicListAdapter.getSwitchViewHasMedia() == null) {
            if (z2) {
                return;
            }
            IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener = this.onPLVLinkMicLayoutListener;
            if (onPLVLinkMicLayoutListener != null) {
                onPLVLinkMicLayoutListener.onClickSwitchWithMediaOnce(firstScreenSwitchView);
                this.linkMicListAdapter.setSwitchViewHasMedia(firstScreenSwitchView);
            }
        } else if (this.linkMicListAdapter.getSwitchViewHasMedia() != firstScreenSwitchView) {
            IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener2 = this.onPLVLinkMicLayoutListener;
            if (onPLVLinkMicLayoutListener2 != null) {
                onPLVLinkMicLayoutListener2.onClickSwitchWithMediaTwice(this.linkMicListAdapter.getSwitchViewHasMedia(), firstScreenSwitchView);
                this.linkMicListAdapter.setSwitchViewHasMedia(firstScreenSwitchView);
            }
        } else {
            if (!z2) {
                return;
            }
            IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener3 = this.onPLVLinkMicLayoutListener;
            if (onPLVLinkMicLayoutListener3 != null) {
                onPLVLinkMicLayoutListener3.onClickSwitchWithMediaOnce(this.linkMicListAdapter.getSwitchViewHasMedia());
                this.linkMicListAdapter.setSwitchViewHasMedia(null);
            }
        }
        boolean z3 = !z2;
        this.isMediaShowInLinkMicList = z3;
        setMediaInLinkMicListLinkMicId(z3 ? String.valueOf(firstScreenSwitchView.getTag(R.id.tag_link_mic_id)) : null);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onTeacherAllowJoin() {
        PLVCommonLog.d(TAG, "onTeacherAllowJoin");
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onTeacherCloseLinkMic() {
        this.linkMicControlBar.setIsTeacherOpenLinkMic(false);
        IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener = this.onPLVLinkMicLayoutListener;
        if (onPLVLinkMicLayoutListener != null) {
            onPLVLinkMicLayoutListener.onChannelLinkMicOpenStatusChanged(false);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onTeacherOpenLinkMic() {
        this.linkMicControlBar.setIsTeacherOpenLinkMic(true);
        IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener = this.onPLVLinkMicLayoutListener;
        if (onPLVLinkMicLayoutListener != null) {
            onPLVLinkMicLayoutListener.onChannelLinkMicOpenStatusChanged(true);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onUserMuteAudio(String str, boolean z2, int i2) {
        this.linkMicListAdapter.updateVolumeChanged();
        if (str.equals(this.linkMicPresenter.getLinkMicId())) {
            this.linkMicControlBar.setMicrophoneOpenOrClose(!z2);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onUserMuteVideo(String str, boolean z2, int i2) {
        this.linkMicListAdapter.updateUserMuteVideo(i2);
        if (str.equals(this.linkMicPresenter.getLinkMicId())) {
            this.linkMicControlBar.setCameraOpenOrClose(!z2);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onUsersJoin(List<String> list) {
        this.linkMicListAdapter.updateAllItem();
        if (this.curTryScrollViewState == 0 && this.linkMicListAdapter.getItemCount() > 3 && getRvScrolledXOffset() == 0) {
            if (this.onScrollTryScrollTipListener == null) {
                this.onScrollTryScrollTipListener = new RecyclerView.OnScrollListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.8
                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
                        if (i2 > 0) {
                            PLVLCLinkMicLayout.this.curTryScrollViewState = 2;
                            PLVLCLinkMicLayout.this.llTryScrollTip.setVisibility(8);
                            PLVLCLinkMicLayout.this.rvLinkMicList.removeOnScrollListener(this);
                        }
                    }
                };
            }
            this.curTryScrollViewState = 1;
            if (!this.curIsLandscape) {
                this.llTryScrollTip.setVisibility(0);
            }
            this.rvLinkMicList.addOnScrollListener(this.onScrollTryScrollTipListener);
        }
        tryShowOrHideLandscapeRTCLayout(true);
        Iterator<Runnable> it = this.onUserJoinPendingTask.iterator();
        while (it.hasNext()) {
            it.next().run();
            it.remove();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onUsersLeave(List<String> list) {
        String mainTeacherLinkMicId;
        PLVViewSwitcher pLVViewSwitcher;
        if (this.isMediaShowInLinkMicList && getMediaViewIndexInLinkMicList() != -1) {
            Iterator<String> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                String str = this.mediaInLinkMicListLinkMicId;
                if (str != null && str.equals(next)) {
                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.rvLinkMicList.findViewHolderForAdapterPosition(getMediaViewIndexInLinkMicList());
                    if (viewHolderFindViewHolderForAdapterPosition != null) {
                        viewHolderFindViewHolderForAdapterPosition.itemView.performClick();
                    }
                }
            }
        }
        if (this.liveChannelType == PLVLiveChannelType.ALONE && (mainTeacherLinkMicId = this.linkMicPresenter.getMainTeacherLinkMicId()) != null && list.contains(mainTeacherLinkMicId) && (pLVViewSwitcher = this.teacherLocationViewSwitcher) != null && pLVViewSwitcher.isViewSwitched()) {
            this.teacherLocationViewSwitcher.switchView();
            this.linkMicListAdapter.setHasNotifyTeacherViewHolderBind(false);
        }
        this.linkMicListAdapter.updateAllItem();
        if (this.curTryScrollViewState == 1 && this.linkMicListAdapter.getItemCount() <= 3) {
            this.curTryScrollViewState = 0;
            this.llTryScrollTip.setVisibility(8);
            RecyclerView.OnScrollListener onScrollListener = this.onScrollTryScrollTipListener;
            if (onScrollListener != null) {
                this.rvLinkMicList.removeOnScrollListener(onScrollListener);
            }
        }
        tryShowOrHideLandscapeRTCLayout(false);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void pause() {
        this.linkMicListAdapter.pauseAllRenderView();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void performClickInLinkMicListItem(final int i2) {
        this.rvLinkMicList.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicLayout.5
            @Override // java.lang.Runnable
            public void run() {
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = PLVLCLinkMicLayout.this.rvLinkMicList.findViewHolderForAdapterPosition(i2);
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    viewHolderFindViewHolderForAdapterPosition.itemView.performClick();
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void resume() {
        this.linkMicListAdapter.resumeAllRenderView();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void setIsAudio(boolean z2) {
        this.linkMicPresenter.setIsAudioLinkMic(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void setIsTeacherOpenLinkMic(boolean z2) {
        this.linkMicControlBar.setIsTeacherOpenLinkMic(z2);
        this.linkMicPresenter.setIsTeacherOpenLinkMic(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void setLiveEnd() {
        this.linkMicPresenter.setLiveEnd();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void setLiveStart() {
        this.linkMicPresenter.setLiveStart();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void setLogoView(PLVPlayerLogoView pLVPlayerLogoView) {
        this.linkMicListAdapter.setPlvPlayerLogoView(pLVPlayerLogoView);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void setOnPLVLinkMicLayoutListener(IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener onPLVLinkMicLayoutListener) {
        this.onPLVLinkMicLayoutListener = onPLVLinkMicLayoutListener;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void setWatchLowLatency(boolean z2) {
        if (PLVLinkMicConfig.getInstance().isLowLatencyPureRtcWatch()) {
            this.linkMicPresenter.setWatchRtc(z2);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void showAll() {
        PLVCommonLog.d(TAG, "show");
        showLinkMicList();
        showControlBar();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void showControlBar() {
        IPLVLCLinkMicControlBar iPLVLCLinkMicControlBar = this.linkMicControlBar;
        if (iPLVLCLinkMicControlBar != null) {
            iPLVLCLinkMicControlBar.show();
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public void showLinkMicList() {
        setVisibility(0);
        this.linkMicListAdapter.showAllRenderView();
        this.linkMicListAdapter.updateAllItem();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void updateAllLinkMicList() {
        this.linkMicListAdapter.updateAllItem();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void updateFirstScreenChanged(String str, int i2, int i3) {
        this.linkMicListAdapter.setFirstScreenLinkMicId(str);
        if (i2 > 0) {
            this.linkMicListAdapter.updateUserMuteVideo(i2);
        }
        if (i3 > 0) {
            this.linkMicListAdapter.updateUserMuteVideo(i3);
        }
    }

    public PLVLCLinkMicLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCLinkMicLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.landscapeItemDecoration = new PLVLinkMicRvLandscapeItemDecoration();
        this.onUserJoinPendingTask = new LinkedList();
        this.isMediaShowInLinkMicList = false;
        this.curTryScrollViewState = 0;
        this.isShowLandscapeLayout = false;
        this.curIsLandscape = false;
        this.landscapeWidth = 0;
        initView();
    }
}
