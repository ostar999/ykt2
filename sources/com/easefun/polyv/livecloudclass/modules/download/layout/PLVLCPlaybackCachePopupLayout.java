package com.easefun.polyv.livecloudclass.modules.download.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
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
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.download.PLVPlaybackCacheActivity;
import com.easefun.polyv.livecloudclass.modules.download.widget.PLVLCPlaybackCacheDownloadProgressButton;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity.PLVPlaybackCacheVideoVO;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.PLVPlaybackCacheVideoViewModel;
import com.easefun.polyv.livecommon.module.utils.PLVDebounceClicker;
import com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer;
import com.easefun.polyv.livecommon.ui.widget.menudrawer.Position;
import com.hjq.permissions.Permission;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PLVLCPlaybackCachePopupLayout extends FrameLayout implements View.OnClickListener {
    private PLVMenuDrawer menuDrawer;
    private PLVLCPlaybackCacheDownloadProgressButton playbackCacheDownloadStatusBtn;
    private LinearLayout playbackCacheGoDownloadListLl;
    private ImageView playbackCachePopupCloseIv;
    private TextView playbackCachePopupTitleTv;
    private View playbackCacheSeparateLineView;
    private TextView playbackCacheVideoSizeTv;
    private TextView playbackCacheVideoTitleTv;
    private final PLVPlaybackCacheVideoViewModel playbackCacheVideoViewModel;

    @Nullable
    private PLVPlaybackCacheVideoVO vo;

    public PLVLCPlaybackCachePopupLayout(@NonNull Context context) {
        super(context);
        this.playbackCacheVideoViewModel = (PLVPlaybackCacheVideoViewModel) PLVDependManager.getInstance().get(PLVPlaybackCacheVideoViewModel.class);
        initView();
    }

    private void findView() {
        this.playbackCachePopupTitleTv = (TextView) findViewById(R.id.plvlc_playback_cache_popup_title_tv);
        this.playbackCachePopupCloseIv = (ImageView) findViewById(R.id.plvlc_playback_cache_popup_close_iv);
        this.playbackCacheSeparateLineView = findViewById(R.id.plvlc_playback_cache_separate_line_view);
        this.playbackCacheVideoTitleTv = (TextView) findViewById(R.id.plvlc_playback_cache_video_title_tv);
        this.playbackCacheVideoSizeTv = (TextView) findViewById(R.id.plvlc_playback_cache_video_size_tv);
        this.playbackCacheDownloadStatusBtn = (PLVLCPlaybackCacheDownloadProgressButton) findViewById(R.id.plvlc_playback_cache_download_status_btn);
        this.playbackCacheGoDownloadListLl = (LinearLayout) findViewById(R.id.plvlc_playback_cache_go_download_list_ll);
        setOnClickListener(this);
        this.playbackCachePopupCloseIv.setOnClickListener(this);
        this.playbackCacheGoDownloadListLl.setOnClickListener(new PLVDebounceClicker.OnClickListener(this));
        this.playbackCacheDownloadStatusBtn.setOnClickListener(new PLVDebounceClicker.OnClickListener(this));
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_playback_cache_popup_layout, this);
        findView();
        observePlaybackCacheStatus();
    }

    private void observePlaybackCacheStatus() {
        this.playbackCacheVideoViewModel.getPlaybackCacheUpdateLiveData().observe((LifecycleOwner) getContext(), new Observer<PLVPlaybackCacheVideoVO>() { // from class: com.easefun.polyv.livecloudclass.modules.download.layout.PLVLCPlaybackCachePopupLayout.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
                if (pLVPlaybackCacheVideoVO == null) {
                    return;
                }
                PLVLCPlaybackCachePopupLayout.this.updateDownloadStatus(pLVPlaybackCacheVideoVO);
            }
        });
    }

    private void requirePermissionThenRun(final Runnable runnable) {
        PLVFastPermission.getInstance().start((Activity) getContext(), PLVSugarUtil.listOf(Permission.WRITE_EXTERNAL_STORAGE), new PLVOnPermissionCallback() { // from class: com.easefun.polyv.livecloudclass.modules.download.layout.PLVLCPlaybackCachePopupLayout.4
            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onAllGranted() {
                runnable.run();
            }

            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onPartialGranted(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDownloadStatus(@NonNull PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO) {
        this.vo = pLVPlaybackCacheVideoVO;
        this.playbackCacheVideoTitleTv.setText(pLVPlaybackCacheVideoVO.getTitle());
        this.playbackCacheVideoSizeTv.setText(PLVPlaybackCacheVideoVO.bytesToFitSizeString(pLVPlaybackCacheVideoVO.getTotalBytes()));
        this.playbackCacheDownloadStatusBtn.update(pLVPlaybackCacheVideoVO);
    }

    public void hide() {
        PLVMenuDrawer pLVMenuDrawer = this.menuDrawer;
        if (pLVMenuDrawer != null) {
            pLVMenuDrawer.closeMenu();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO;
        int id = view.getId();
        if (id == this.playbackCachePopupCloseIv.getId() || id == getId()) {
            hide();
            return;
        }
        if (id == this.playbackCacheGoDownloadListLl.getId()) {
            getContext().startActivity(new Intent(getContext(), (Class<?>) PLVPlaybackCacheActivity.class));
        } else if (id == this.playbackCacheDownloadStatusBtn.getId() && (pLVPlaybackCacheVideoVO = this.vo) != null && pLVPlaybackCacheVideoVO.getDownloadStatusEnum() == PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST) {
            requirePermissionThenRun(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.download.layout.PLVLCPlaybackCachePopupLayout.3
                @Override // java.lang.Runnable
                public void run() {
                    PLVLCPlaybackCachePopupLayout.this.playbackCacheVideoViewModel.startDownload(PLVLCPlaybackCachePopupLayout.this.vo);
                }
            });
        }
    }

    public void show() {
        PLVMenuDrawer pLVMenuDrawer = this.menuDrawer;
        if (pLVMenuDrawer != null) {
            pLVMenuDrawer.attachToContainer();
            this.menuDrawer.openMenu();
            return;
        }
        PLVMenuDrawer pLVMenuDrawerAttach = PLVMenuDrawer.attach((Activity) getContext(), PLVMenuDrawer.Type.OVERLAY, Position.BOTTOM, 2, (ViewGroup) ((Activity) getContext()).findViewById(R.id.plvlc_popup_container));
        this.menuDrawer = pLVMenuDrawerAttach;
        pLVMenuDrawerAttach.setMenuView(this);
        this.menuDrawer.setMenuSize(ScreenUtils.getScreenOrientatedHeight());
        this.menuDrawer.setTouchMode(1);
        this.menuDrawer.setDrawOverlay(false);
        this.menuDrawer.setDropShadowEnabled(false);
        this.menuDrawer.setOnDrawerStateChangeListener(new PLVMenuDrawer.OnDrawerStateChangeListener() { // from class: com.easefun.polyv.livecloudclass.modules.download.layout.PLVLCPlaybackCachePopupLayout.2
            @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.OnDrawerStateChangeListener
            public void onDrawerSlide(float f2, int i2) {
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.OnDrawerStateChangeListener
            public void onDrawerStateChange(int i2, int i3) {
                if (i3 == 0) {
                    PLVLCPlaybackCachePopupLayout.this.menuDrawer.detachToContainer();
                }
            }
        });
        this.menuDrawer.openMenu();
    }
}
