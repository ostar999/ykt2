package com.luck.picture.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.luck.picture.lib.adapter.PictureImageGridAdapter;
import com.luck.picture.lib.animators.AlphaInAnimationAdapter;
import com.luck.picture.lib.animators.SlideInBottomAnimationAdapter;
import com.luck.picture.lib.basic.FragmentInjectManager;
import com.luck.picture.lib.basic.IPictureSelectorEvent;
import com.luck.picture.lib.basic.PictureCommonFragment;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.dialog.AlbumListPopWindow;
import com.luck.picture.lib.dialog.AudioPlayDialog;
import com.luck.picture.lib.engine.ExtendLoaderEngine;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnAlbumItemClickListener;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.interfaces.OnPreviewInterceptListener;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.luck.picture.lib.interfaces.OnRecyclerViewPreloadMoreListener;
import com.luck.picture.lib.interfaces.OnRecyclerViewScrollListener;
import com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.loader.LocalMediaLoader;
import com.luck.picture.lib.loader.LocalMediaPageLoader;
import com.luck.picture.lib.magical.BuildRecycleItemViewParams;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.permissions.PermissionResultCallback;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.AnimUtils;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.DoubleUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ToastUtils;
import com.luck.picture.lib.utils.ValueOf;
import com.luck.picture.lib.widget.BottomNavBar;
import com.luck.picture.lib.widget.CompleteSelectView;
import com.luck.picture.lib.widget.RecyclerPreloadView;
import com.luck.picture.lib.widget.TitleBar;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PictureSelectorFragment extends PictureCommonFragment implements OnRecyclerViewPreloadMoreListener, IPictureSelectorEvent {
    private static final int SELECT_ANIM_DURATION = 135;
    public static final String TAG = "PictureSelectorFragment";
    private AlbumListPopWindow albumListPopWindow;
    private int allFolderSize;
    private BottomNavBar bottomNarBar;
    private CompleteSelectView completeSelectView;
    private boolean isCameraMemoryRecycling;
    private boolean isDisplayCamera;
    private PictureImageGridAdapter mAdapter;
    private RecyclerPreloadView mRecycler;
    private int openCameraNumber;
    private TitleBar titleBar;
    private TextView tvCurrentDataTime;
    private TextView tvDataEmpty;
    private long intervalClickTime = 0;
    private int currentPosition = -1;

    private void addAlbumPopWindowAction() {
        this.albumListPopWindow.setOnIBridgeAlbumWidget(new OnAlbumItemClickListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.7
            @Override // com.luck.picture.lib.interfaces.OnAlbumItemClickListener
            public void onItemClick(int i2, LocalMediaFolder localMediaFolder) {
                PictureSelectorFragment pictureSelectorFragment = PictureSelectorFragment.this;
                pictureSelectorFragment.isDisplayCamera = ((PictureCommonFragment) pictureSelectorFragment).config.isDisplayCamera && localMediaFolder.getBucketId() == -1;
                PictureSelectorFragment.this.mAdapter.setDisplayCamera(PictureSelectorFragment.this.isDisplayCamera);
                PictureSelectorFragment.this.titleBar.setTitle(localMediaFolder.getFolderName());
                LocalMediaFolder currentLocalMediaFolder = SelectedManager.getCurrentLocalMediaFolder();
                long bucketId = currentLocalMediaFolder.getBucketId();
                if (((PictureCommonFragment) PictureSelectorFragment.this).config.isPageStrategy) {
                    if (localMediaFolder.getBucketId() != bucketId) {
                        currentLocalMediaFolder.setData(PictureSelectorFragment.this.mAdapter.getData());
                        currentLocalMediaFolder.setCurrentDataPage(((PictureCommonFragment) PictureSelectorFragment.this).mPage);
                        currentLocalMediaFolder.setHasMore(PictureSelectorFragment.this.mRecycler.isEnabledLoadMore());
                        if (localMediaFolder.getData().size() > 0) {
                            PictureSelectorFragment.this.setAdapterData(localMediaFolder.getData());
                            ((PictureCommonFragment) PictureSelectorFragment.this).mPage = localMediaFolder.getCurrentDataPage();
                            PictureSelectorFragment.this.mRecycler.setEnabledLoadMore(localMediaFolder.isHasMore());
                            PictureSelectorFragment.this.mRecycler.smoothScrollToPosition(0);
                        } else {
                            ((PictureCommonFragment) PictureSelectorFragment.this).mPage = 1;
                            ExtendLoaderEngine extendLoaderEngine = PictureSelectionConfig.loaderDataEngine;
                            if (extendLoaderEngine != null) {
                                extendLoaderEngine.loadFirstPageMediaData(PictureSelectorFragment.this.getContext(), localMediaFolder.getBucketId(), ((PictureCommonFragment) PictureSelectorFragment.this).mPage, ((PictureCommonFragment) PictureSelectorFragment.this).config.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.7.1
                                    @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                                    public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                                        PictureSelectorFragment.this.handleSwitchAlbum(arrayList, z2);
                                    }
                                });
                            } else {
                                ((PictureCommonFragment) PictureSelectorFragment.this).mLoader.loadPageMediaData(localMediaFolder.getBucketId(), ((PictureCommonFragment) PictureSelectorFragment.this).mPage, ((PictureCommonFragment) PictureSelectorFragment.this).config.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.7.2
                                    @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                                    public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                                        PictureSelectorFragment.this.handleSwitchAlbum(arrayList, z2);
                                    }
                                });
                            }
                        }
                    }
                } else if (localMediaFolder.getBucketId() != bucketId) {
                    PictureSelectorFragment.this.setAdapterData(localMediaFolder.getData());
                    PictureSelectorFragment.this.mRecycler.smoothScrollToPosition(0);
                }
                SelectedManager.setCurrentLocalMediaFolder(localMediaFolder);
                PictureSelectorFragment.this.albumListPopWindow.dismiss();
            }
        });
    }

    private void addRecyclerAction() {
        this.mAdapter.setOnItemClickListener(new PictureImageGridAdapter.OnItemClickListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.16
            @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnItemClickListener
            public void onItemClick(View view, int i2, LocalMedia localMedia) {
                if (((PictureCommonFragment) PictureSelectorFragment.this).config.selectionMode == 1 && ((PictureCommonFragment) PictureSelectorFragment.this).config.isDirectReturnSingle) {
                    SelectedManager.getSelectedResult().clear();
                    SelectedManager.getSelectedResult().add(localMedia);
                    PictureSelectorFragment.this.dispatchTransformResult();
                } else {
                    if (DoubleUtils.isFastDoubleClick()) {
                        return;
                    }
                    if (!PictureMimeType.isHasAudio(localMedia.getMimeType())) {
                        PictureSelectorFragment.this.onStartPreview(i2, false);
                        return;
                    }
                    OnPreviewInterceptListener onPreviewInterceptListener = PictureSelectionConfig.onPreviewInterceptListener;
                    if (onPreviewInterceptListener != null) {
                        onPreviewInterceptListener.onPreviewAudio(PictureSelectorFragment.this.getContext(), localMedia);
                    } else {
                        AudioPlayDialog.showPlayAudioDialog(PictureSelectorFragment.this.getActivity(), localMedia.getPath());
                    }
                }
            }

            @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnItemClickListener
            public int onSelected(View view, int i2, LocalMedia localMedia) {
                int iConfirmSelect = PictureSelectorFragment.this.confirmSelect(localMedia, view.isSelected());
                if (iConfirmSelect == 0) {
                    view.startAnimation(AnimationUtils.loadAnimation(PictureSelectorFragment.this.getContext(), R.anim.ps_anim_modal_in));
                }
                return iConfirmSelect;
            }

            @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnItemClickListener
            public void openCameraClick() {
                PictureSelectorFragment.this.openSelectedCamera();
            }
        });
        this.mRecycler.setOnRecyclerViewScrollStateListener(new OnRecyclerViewScrollStateListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.17
            @Override // com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener
            public void onScrollFast() {
                ImageEngine imageEngine = PictureSelectionConfig.imageEngine;
                if (imageEngine != null) {
                    imageEngine.pauseRequests(PictureSelectorFragment.this.getContext());
                }
            }

            @Override // com.luck.picture.lib.interfaces.OnRecyclerViewScrollStateListener
            public void onScrollSlow() {
                ImageEngine imageEngine = PictureSelectionConfig.imageEngine;
                if (imageEngine != null) {
                    imageEngine.resumeRequests(PictureSelectorFragment.this.getContext());
                }
            }
        });
        this.mRecycler.setOnRecyclerViewScrollListener(new OnRecyclerViewScrollListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.18
            @Override // com.luck.picture.lib.interfaces.OnRecyclerViewScrollListener
            public void onScrollStateChanged(int i2) {
                if (i2 == 1) {
                    PictureSelectorFragment.this.showCurrentMediaCreateTimeUI();
                } else if (i2 == 0) {
                    PictureSelectorFragment.this.hideCurrentMediaCreateTimeUI();
                }
            }

            @Override // com.luck.picture.lib.interfaces.OnRecyclerViewScrollListener
            public void onScrolled(int i2, int i3) {
                PictureSelectorFragment.this.setCurrentMediaCreateTimeText();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beginLoadData() {
        if (this.config.isOnlySandboxDir) {
            loadOnlyInAppDirectoryAllMediaData();
        } else {
            loadAllAlbumData();
        }
    }

    private boolean checkNotifyStrategy(boolean z2) {
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (!pictureSelectionConfig.isMaxSelectEnabledMask || pictureSelectionConfig.selectionMode != 2) {
            return false;
        }
        if (pictureSelectionConfig.isWithVideoImage) {
            if (SelectedManager.getCount() != this.config.maxSelectNum && (z2 || SelectedManager.getCount() != this.config.maxSelectNum - 1)) {
                return false;
            }
        } else if (SelectedManager.getCount() != 0 && (!z2 || SelectedManager.getCount() != 1)) {
            if (PictureMimeType.isHasVideo(SelectedManager.getTopResultMimeType())) {
                PictureSelectionConfig pictureSelectionConfig2 = this.config;
                int i2 = pictureSelectionConfig2.maxVideoSelectNum;
                if (i2 <= 0) {
                    i2 = pictureSelectionConfig2.maxSelectNum;
                }
                if (SelectedManager.getCount() != i2 && (z2 || SelectedManager.getCount() != i2 - 1)) {
                    return false;
                }
            } else if (SelectedManager.getCount() != this.config.maxSelectNum && (z2 || SelectedManager.getCount() != this.config.maxSelectNum - 1)) {
                return false;
            }
        }
        return true;
    }

    private int getPageLimit(long j2) {
        if (j2 != -1) {
            return this.config.pageSize;
        }
        int i2 = this.openCameraNumber;
        int i3 = i2 > 0 ? this.config.pageSize - i2 : this.config.pageSize;
        this.openCameraNumber = 0;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAllAlbumData(List<LocalMediaFolder> list) {
        LocalMediaFolder currentLocalMediaFolder;
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (list.size() <= 0) {
            showDataNull();
            return;
        }
        if (SelectedManager.getCurrentLocalMediaFolder() != null) {
            currentLocalMediaFolder = SelectedManager.getCurrentLocalMediaFolder();
        } else {
            currentLocalMediaFolder = list.get(0);
            SelectedManager.setCurrentLocalMediaFolder(currentLocalMediaFolder);
        }
        this.titleBar.setTitle(currentLocalMediaFolder.getFolderName());
        this.albumListPopWindow.bindAlbumData(list);
        if (this.config.isPageStrategy) {
            loadFirstPageMediaData(currentLocalMediaFolder.getBucketId());
        } else {
            setAdapterData(currentLocalMediaFolder.getData());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFirstPageMedia(ArrayList<LocalMedia> arrayList, boolean z2) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.mRecycler.setEnabledLoadMore(z2);
        if (this.mRecycler.isEnabledLoadMore() && arrayList.size() == 0) {
            onRecyclerViewPreloadMore();
        } else {
            setAdapterData(arrayList);
        }
        recoveryRecyclerPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInAppDirAllMedia(LocalMediaFolder localMediaFolder) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        String str = this.config.sandboxDir;
        boolean z2 = localMediaFolder != null;
        this.titleBar.setTitle(z2 ? localMediaFolder.getFolderName() : new File(str).getName());
        if (!z2) {
            showDataNull();
            return;
        }
        SelectedManager.setCurrentLocalMediaFolder(localMediaFolder);
        setAdapterData(localMediaFolder.getData());
        recoveryRecyclerPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMoreMediaData(List<LocalMedia> list, boolean z2) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.mRecycler.setEnabledLoadMore(z2);
        if (this.mRecycler.isEnabledLoadMore()) {
            if (list.size() > 0) {
                int size = this.mAdapter.getData().size();
                this.mAdapter.getData().addAll(list);
                PictureImageGridAdapter pictureImageGridAdapter = this.mAdapter;
                pictureImageGridAdapter.notifyItemRangeChanged(size, pictureImageGridAdapter.getItemCount());
            } else {
                onRecyclerViewPreloadMore();
            }
            if (list.size() < 10) {
                RecyclerPreloadView recyclerPreloadView = this.mRecycler;
                recyclerPreloadView.onScrolled(recyclerPreloadView.getScrollX(), this.mRecycler.getScrollY());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSwitchAlbum(ArrayList<LocalMedia> arrayList, boolean z2) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        this.mRecycler.setEnabledLoadMore(z2);
        if (arrayList.size() == 0) {
            this.mAdapter.getData().clear();
        }
        setAdapterData(arrayList);
        this.mRecycler.onScrolled(0, 0);
        this.mRecycler.smoothScrollToPosition(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideCurrentMediaCreateTimeUI() {
        if (!this.config.isDisplayTimeAxis || this.mAdapter.getData().size() <= 0) {
            return;
        }
        this.tvCurrentDataTime.animate().setDuration(250L).alpha(0.0f).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideDataNull() {
        if (this.tvDataEmpty.getVisibility() == 0) {
            this.tvDataEmpty.setVisibility(8);
        }
    }

    private void initAlbumListPopWindow() {
        AlbumListPopWindow albumListPopWindowBuildPopWindow = AlbumListPopWindow.buildPopWindow(getContext());
        this.albumListPopWindow = albumListPopWindowBuildPopWindow;
        albumListPopWindowBuildPopWindow.setOnPopupWindowStatusListener(new AlbumListPopWindow.OnPopupWindowStatusListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.4
            @Override // com.luck.picture.lib.dialog.AlbumListPopWindow.OnPopupWindowStatusListener
            public void onDismissPopupWindow() {
                if (((PictureCommonFragment) PictureSelectorFragment.this).config.isOnlySandboxDir) {
                    return;
                }
                AnimUtils.rotateArrow(PictureSelectorFragment.this.titleBar.getImageArrow(), false);
            }

            @Override // com.luck.picture.lib.dialog.AlbumListPopWindow.OnPopupWindowStatusListener
            public void onShowPopupWindow() {
                if (((PictureCommonFragment) PictureSelectorFragment.this).config.isOnlySandboxDir) {
                    return;
                }
                AnimUtils.rotateArrow(PictureSelectorFragment.this.titleBar.getImageArrow(), true);
            }
        });
        addAlbumPopWindowAction();
    }

    private void initBottomNavBar() {
        this.bottomNarBar.setBottomNavBarStyle();
        this.bottomNarBar.setOnBottomNavBarListener(new BottomNavBar.OnBottomNavBarListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.8
            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onCheckOriginalChange() {
                PictureSelectorFragment.this.sendSelectedOriginalChangeEvent();
            }

            @Override // com.luck.picture.lib.widget.BottomNavBar.OnBottomNavBarListener
            public void onPreview() {
                PictureSelectorFragment.this.onStartPreview(0, true);
            }
        });
        this.bottomNarBar.setSelectedChange();
    }

    private void initComplete() {
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (pictureSelectionConfig.selectionMode == 1 && pictureSelectionConfig.isDirectReturnSingle) {
            PictureSelectionConfig.selectorStyle.getTitleBarStyle().setHideCancelButton(false);
            this.titleBar.getTitleCancelView().setVisibility(0);
            this.completeSelectView.setVisibility(8);
            return;
        }
        this.completeSelectView.setCompleteSelectViewStyle();
        this.completeSelectView.setSelectedChange(false);
        if (PictureSelectionConfig.selectorStyle.getSelectMainStyle().isCompleteSelectRelativeTop()) {
            if (this.completeSelectView.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams();
                int i2 = R.id.title_bar;
                layoutParams.topToTop = i2;
                ((ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams()).bottomToBottom = i2;
                if (this.config.isPreviewFullScreenMode) {
                    ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.completeSelectView.getLayoutParams())).topMargin = DensityUtil.getStatusBarHeight(getContext());
                }
            } else if ((this.completeSelectView.getLayoutParams() instanceof RelativeLayout.LayoutParams) && this.config.isPreviewFullScreenMode) {
                ((RelativeLayout.LayoutParams) this.completeSelectView.getLayoutParams()).topMargin = DensityUtil.getStatusBarHeight(getContext());
            }
        }
        this.completeSelectView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PictureSelectorFragment.this.dispatchTransformResult();
            }
        });
    }

    private void initRecycler(View view) {
        this.mRecycler = (RecyclerPreloadView) view.findViewById(R.id.recycler);
        SelectMainStyle selectMainStyle = PictureSelectionConfig.selectorStyle.getSelectMainStyle();
        int mainListBackgroundColor = selectMainStyle.getMainListBackgroundColor();
        if (StyleUtils.checkStyleValidity(mainListBackgroundColor)) {
            this.mRecycler.setBackgroundColor(mainListBackgroundColor);
        } else {
            this.mRecycler.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ps_color_black));
        }
        int i2 = this.config.imageSpanCount;
        if (i2 <= 0) {
            i2 = 4;
        }
        if (this.mRecycler.getItemDecorationCount() == 0) {
            if (StyleUtils.checkSizeValidity(selectMainStyle.getAdapterItemSpacingSize())) {
                this.mRecycler.addItemDecoration(new GridSpacingItemDecoration(i2, selectMainStyle.getAdapterItemSpacingSize(), selectMainStyle.isAdapterItemIncludeEdge()));
            } else {
                this.mRecycler.addItemDecoration(new GridSpacingItemDecoration(i2, DensityUtil.dip2px(view.getContext(), 1.0f), selectMainStyle.isAdapterItemIncludeEdge()));
            }
        }
        this.mRecycler.setLayoutManager(new GridLayoutManager(getContext(), i2));
        RecyclerView.ItemAnimator itemAnimator = this.mRecycler.getItemAnimator();
        if (itemAnimator != null) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
            this.mRecycler.setItemAnimator(null);
        }
        if (this.config.isPageStrategy) {
            this.mRecycler.setReachBottomRow(2);
            this.mRecycler.setOnRecyclerViewPreloadListener(this);
        } else {
            this.mRecycler.setHasFixedSize(true);
        }
        PictureImageGridAdapter pictureImageGridAdapter = new PictureImageGridAdapter(getContext(), this.config);
        this.mAdapter = pictureImageGridAdapter;
        pictureImageGridAdapter.setDisplayCamera(this.isDisplayCamera);
        int i3 = this.config.animationMode;
        if (i3 == 1) {
            this.mRecycler.setAdapter(new AlphaInAnimationAdapter(this.mAdapter));
        } else if (i3 != 2) {
            this.mRecycler.setAdapter(this.mAdapter);
        } else {
            this.mRecycler.setAdapter(new SlideInBottomAnimationAdapter(this.mAdapter));
        }
        addRecyclerAction();
    }

    private void initTitleBar() {
        if (PictureSelectionConfig.selectorStyle.getTitleBarStyle().isHideTitleBar()) {
            this.titleBar.setVisibility(8);
        }
        this.titleBar.setTitleBarStyle();
        this.titleBar.setOnTitleBarListener(new TitleBar.OnTitleBarListener() { // from class: com.luck.picture.lib.PictureSelectorFragment.3
            @Override // com.luck.picture.lib.widget.TitleBar.OnTitleBarListener
            public void onBackPressed() {
                if (PictureSelectorFragment.this.albumListPopWindow.isShowing()) {
                    PictureSelectorFragment.this.albumListPopWindow.dismiss();
                } else {
                    PictureSelectorFragment.this.onKeyBackFragment();
                }
            }

            @Override // com.luck.picture.lib.widget.TitleBar.OnTitleBarListener
            public void onShowAlbumPopWindow(View view) {
                PictureSelectorFragment.this.albumListPopWindow.showAsDropDown(view);
            }

            @Override // com.luck.picture.lib.widget.TitleBar.OnTitleBarListener
            public void onTitleDoubleClick() {
                if (((PictureCommonFragment) PictureSelectorFragment.this).config.isAutomaticTitleRecyclerTop) {
                    if (SystemClock.uptimeMillis() - PictureSelectorFragment.this.intervalClickTime < 500 && PictureSelectorFragment.this.mAdapter.getItemCount() > 0) {
                        PictureSelectorFragment.this.mRecycler.scrollToPosition(0);
                    } else {
                        PictureSelectorFragment.this.intervalClickTime = SystemClock.uptimeMillis();
                    }
                }
            }
        });
    }

    private boolean isAddSameImp(int i2) {
        int i3;
        return i2 != 0 && (i3 = this.allFolderSize) > 0 && i3 < i2;
    }

    private void mergeFolder(LocalMedia localMedia) {
        LocalMediaFolder folder;
        LocalMediaFolder localMediaFolder;
        if (this.albumListPopWindow.getFolderCount() == 0) {
            folder = new LocalMediaFolder();
            folder.setFolderName(getString(this.config.chooseMode == SelectMimeType.ofAudio() ? R.string.ps_all_audio : R.string.ps_camera_roll));
            folder.setFirstImagePath("");
            folder.setBucketId(-1L);
            this.albumListPopWindow.getAlbumList().add(0, folder);
        } else {
            folder = this.albumListPopWindow.getFolder(0);
        }
        folder.setFirstImagePath(localMedia.getPath());
        folder.setFirstMimeType(localMedia.getMimeType());
        folder.setData(this.mAdapter.getData());
        folder.setBucketId(-1L);
        folder.setFolderTotalNum(isAddSameImp(folder.getFolderTotalNum()) ? folder.getFolderTotalNum() : folder.getFolderTotalNum() + 1);
        if (SelectedManager.getCurrentLocalMediaFolder() == null) {
            SelectedManager.setCurrentLocalMediaFolder(folder);
        }
        List<LocalMediaFolder> albumList = this.albumListPopWindow.getAlbumList();
        int i2 = 0;
        while (true) {
            if (i2 >= albumList.size()) {
                localMediaFolder = null;
                break;
            }
            localMediaFolder = albumList.get(i2);
            if (TextUtils.equals(localMediaFolder.getFolderName(), localMedia.getParentFolderName())) {
                break;
            } else {
                i2++;
            }
        }
        if (localMediaFolder == null) {
            localMediaFolder = new LocalMediaFolder();
            localMediaFolder.setFolderName(localMedia.getParentFolderName());
            localMediaFolder.setBucketId(localMedia.getBucketId());
            if (!TextUtils.isEmpty(this.config.outPutCameraDir) || !TextUtils.isEmpty(this.config.outPutAudioDir)) {
                localMediaFolder.getData().add(0, localMedia);
            }
            albumList.add(localMediaFolder);
        } else {
            if ((!this.config.isPageStrategy && !isAddSameImp(folder.getFolderTotalNum())) || !TextUtils.isEmpty(this.config.outPutCameraDir) || !TextUtils.isEmpty(this.config.outPutAudioDir)) {
                localMediaFolder.getData().add(0, localMedia);
            }
            if (localMediaFolder.getBucketId() == -1 || localMediaFolder.getBucketId() == 0) {
                localMediaFolder.setBucketId(localMedia.getBucketId());
            }
        }
        localMediaFolder.setFolderTotalNum(isAddSameImp(folder.getFolderTotalNum()) ? localMediaFolder.getFolderTotalNum() : localMediaFolder.getFolderTotalNum() + 1);
        localMediaFolder.setFirstImagePath(this.config.cameraPath);
        localMediaFolder.setFirstMimeType(localMedia.getMimeType());
        this.albumListPopWindow.bindAlbumData(albumList);
    }

    public static PictureSelectorFragment newInstance() {
        PictureSelectorFragment pictureSelectorFragment = new PictureSelectorFragment();
        pictureSelectorFragment.setArguments(new Bundle());
        return pictureSelectorFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStartPreview(int i2, boolean z2) {
        ArrayList<LocalMedia> data;
        int folderTotalNum;
        long bucketId;
        FragmentActivity activity = getActivity();
        String str = PictureSelectorPreviewFragment.TAG;
        if (ActivityCompatHelper.checkFragmentNonExits(activity, str)) {
            if (z2) {
                data = new ArrayList<>(SelectedManager.getSelectedResult());
                folderTotalNum = data.size();
                bucketId = 0;
            } else {
                data = this.mAdapter.getData();
                folderTotalNum = SelectedManager.getCurrentLocalMediaFolder().getFolderTotalNum();
                bucketId = SelectedManager.getCurrentLocalMediaFolder().getBucketId();
            }
            ArrayList<LocalMedia> arrayList = data;
            int i3 = folderTotalNum;
            long j2 = bucketId;
            if (!z2) {
                PictureSelectionConfig pictureSelectionConfig = this.config;
                if (pictureSelectionConfig.isPreviewZoomEffect) {
                    BuildRecycleItemViewParams.generateViewParams(this.mRecycler, pictureSelectionConfig.isPreviewFullScreenMode ? 0 : DensityUtil.getStatusBarHeight(getContext()));
                }
            }
            OnPreviewInterceptListener onPreviewInterceptListener = PictureSelectionConfig.onPreviewInterceptListener;
            if (onPreviewInterceptListener != null) {
                onPreviewInterceptListener.onPreview(getContext(), i2, i3, this.mPage, j2, this.titleBar.getTitleText(), this.mAdapter.isDisplayCamera(), arrayList, z2);
            } else if (ActivityCompatHelper.checkFragmentNonExits(getActivity(), str)) {
                PictureSelectorPreviewFragment pictureSelectorPreviewFragmentNewInstance = PictureSelectorPreviewFragment.newInstance();
                pictureSelectorPreviewFragmentNewInstance.setInternalPreviewData(z2, this.titleBar.getTitleText(), this.mAdapter.isDisplayCamera(), i2, i3, this.mPage, j2, arrayList);
                FragmentInjectManager.injectFragment(getActivity(), str, pictureSelectorPreviewFragmentNewInstance);
            }
        }
    }

    private void recoveryRecyclerPosition() {
        if (this.currentPosition > 0) {
            this.mRecycler.post(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorFragment.15
                @Override // java.lang.Runnable
                public void run() {
                    PictureSelectorFragment.this.mRecycler.scrollToPosition(PictureSelectorFragment.this.currentPosition);
                    PictureSelectorFragment.this.mRecycler.setLastVisiblePosition(PictureSelectorFragment.this.currentPosition);
                }
            });
        }
    }

    private void requestLoadData() {
        this.mAdapter.setDisplayCamera(this.isDisplayCamera);
        if (PermissionChecker.isCheckReadStorage(getContext())) {
            beginLoadData();
            return;
        }
        OnPermissionsInterceptListener onPermissionsInterceptListener = PictureSelectionConfig.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null) {
            onPermissionsInterceptListener.requestPermission(this, PermissionConfig.READ_WRITE_EXTERNAL_STORAGE, new OnCallbackListener<Boolean>() { // from class: com.luck.picture.lib.PictureSelectorFragment.5
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(Boolean bool) {
                    if (bool.booleanValue()) {
                        PictureSelectorFragment.this.beginLoadData();
                    } else {
                        PictureSelectorFragment.this.handlePermissionDenied(PermissionConfig.READ_WRITE_EXTERNAL_STORAGE);
                    }
                }
            });
        } else {
            PermissionChecker.getInstance().requestPermissions(this, PermissionConfig.READ_WRITE_EXTERNAL_STORAGE, new PermissionResultCallback() { // from class: com.luck.picture.lib.PictureSelectorFragment.6
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureSelectorFragment.this.handlePermissionDenied(PermissionConfig.READ_WRITE_EXTERNAL_STORAGE);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureSelectorFragment.this.beginLoadData();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NotifyDataSetChanged"})
    public void setAdapterData(final ArrayList<LocalMedia> arrayList) {
        requireView().postDelayed(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorFragment.19
            @Override // java.lang.Runnable
            public void run() {
                PictureSelectorFragment.this.setEnterAnimationDuration(0L);
                PictureSelectorFragment.this.sendChangeSubSelectPositionEvent(false);
                PictureSelectorFragment.this.mAdapter.setDataAndDataSetChanged(arrayList);
                if (PictureSelectorFragment.this.mAdapter.isDataEmpty()) {
                    PictureSelectorFragment.this.showDataNull();
                } else {
                    PictureSelectorFragment.this.hideDataNull();
                }
            }
        }, getEnterAnimationDuration());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentMediaCreateTimeText() {
        int firstVisiblePosition;
        if (!this.config.isDisplayTimeAxis || (firstVisiblePosition = this.mRecycler.getFirstVisiblePosition()) == -1) {
            return;
        }
        ArrayList<LocalMedia> data = this.mAdapter.getData();
        if (data.size() <= firstVisiblePosition || data.get(firstVisiblePosition).getDateAddedTime() <= 0) {
            return;
        }
        this.tvCurrentDataTime.setText(DateUtils.getDataFormat(getContext(), data.get(firstVisiblePosition).getDateAddedTime()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCurrentMediaCreateTimeUI() {
        if (this.config.isDisplayTimeAxis && this.mAdapter.getData().size() > 0 && this.tvCurrentDataTime.getAlpha() == 0.0f) {
            this.tvCurrentDataTime.animate().setDuration(150L).alphaBy(1.0f).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDataNull() {
        if (this.tvDataEmpty.getVisibility() == 8) {
            this.tvDataEmpty.setVisibility(0);
        }
        this.tvDataEmpty.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ps_ic_no_data, 0, 0);
        this.tvDataEmpty.setText(getString(this.config.chooseMode == SelectMimeType.ofAudio() ? R.string.ps_audio_empty : R.string.ps_empty));
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void dispatchCameraMediaResult(LocalMedia localMedia) {
        if (this.isCameraMemoryRecycling) {
            this.isCameraMemoryRecycling = false;
            SelectedManager.getSelectedResult().add(localMedia);
            this.mAdapter.notifyItemPositionChanged(this.config.isDisplayCamera ? 1 : 0);
            if (this.config.isDirectReturnSingle) {
                dispatchTransformResult();
                return;
            }
            return;
        }
        if (!isAddSameImp(this.albumListPopWindow.getFirstAlbumImageCount())) {
            this.mAdapter.getData().add(0, localMedia);
            this.openCameraNumber++;
        }
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (pictureSelectionConfig.selectionMode == 1 && pictureSelectionConfig.isDirectReturnSingle) {
            SelectedManager.getSelectedResult().clear();
            SelectedManager.getSelectedResult().add(localMedia);
            dispatchTransformResult();
        } else {
            confirmSelect(localMedia, false);
        }
        this.mAdapter.notifyItemInserted(this.config.isDisplayCamera ? 1 : 0);
        PictureImageGridAdapter pictureImageGridAdapter = this.mAdapter;
        pictureImageGridAdapter.notifyItemRangeChanged(this.config.isDisplayCamera ? 1 : 0, pictureImageGridAdapter.getData().size());
        if (!this.config.isOnlySandboxDir) {
            mergeFolder(localMedia);
        } else if (SelectedManager.getCurrentLocalMediaFolder() == null) {
            LocalMediaFolder localMediaFolder = new LocalMediaFolder();
            localMediaFolder.setBucketId(ValueOf.toLong(Integer.valueOf(localMedia.getParentFolderName().hashCode())));
            localMediaFolder.setFolderName(localMedia.getParentFolderName());
            localMediaFolder.setFirstMimeType(localMedia.getMimeType());
            localMediaFolder.setFirstImagePath(localMedia.getPath());
            localMediaFolder.setFolderTotalNum(this.mAdapter.getData().size());
            localMediaFolder.setCurrentDataPage(this.mPage);
            localMediaFolder.setHasMore(false);
            this.mRecycler.setEnabledLoadMore(false);
            SelectedManager.setCurrentLocalMediaFolder(localMediaFolder);
        }
        this.allFolderSize = 0;
        if (this.mAdapter.getData().size() > 0 || this.config.isDirectReturnSingle) {
            hideDataNull();
        } else {
            showDataNull();
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment
    public String getFragmentTag() {
        return TAG;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int getResourceId() {
        int layoutResource = InjectResourceSource.getLayoutResource(getContext(), 1);
        return layoutResource != 0 ? layoutResource : R.layout.ps_fragment_selector;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void handlePermissionSettingResult() {
        OnPermissionsInterceptListener onPermissionsInterceptListener = PictureSelectionConfig.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null ? onPermissionsInterceptListener.hasPermissions(this) : PermissionChecker.isCheckReadStorage(getContext())) {
            beginLoadData();
        } else {
            ToastUtils.showToast(getContext(), getString(R.string.ps_jurisdiction));
            onKeyBackFragment();
        }
    }

    public void initLoader() {
        if (this.config.isPageStrategy) {
            this.mLoader = new LocalMediaPageLoader(getContext(), this.config);
        } else {
            this.mLoader = new LocalMediaLoader(getContext(), this.config);
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorEvent
    public void loadAllAlbumData() {
        ExtendLoaderEngine extendLoaderEngine = PictureSelectionConfig.loaderDataEngine;
        if (extendLoaderEngine != null) {
            extendLoaderEngine.loadAllAlbumData(getContext(), new OnQueryAllAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorFragment.9
                @Override // com.luck.picture.lib.interfaces.OnQueryAllAlbumListener
                public void onComplete(List<LocalMediaFolder> list) {
                    PictureSelectorFragment.this.handleAllAlbumData(list);
                }
            });
        } else {
            this.mLoader.loadAllMedia(new OnQueryAllAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorFragment.10
                @Override // com.luck.picture.lib.interfaces.OnQueryAllAlbumListener
                public void onComplete(List<LocalMediaFolder> list) {
                    PictureSelectorFragment.this.handleAllAlbumData(list);
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorEvent
    public void loadFirstPageMediaData(long j2) {
        this.mRecycler.setEnabledLoadMore(true);
        ExtendLoaderEngine extendLoaderEngine = PictureSelectionConfig.loaderDataEngine;
        if (extendLoaderEngine == null) {
            this.mLoader.loadFirstPageMedia(j2, this.mPage * this.config.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.12
                @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                    PictureSelectorFragment.this.handleFirstPageMedia(arrayList, z2);
                }
            });
            return;
        }
        Context context = getContext();
        int i2 = this.mPage;
        extendLoaderEngine.loadFirstPageMediaData(context, j2, i2, i2 * this.config.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.11
            @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
            public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                PictureSelectorFragment.this.handleFirstPageMedia(arrayList, z2);
            }
        });
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorEvent
    public void loadMoreMediaData() {
        if (this.mRecycler.isEnabledLoadMore()) {
            this.mPage++;
            LocalMediaFolder currentLocalMediaFolder = SelectedManager.getCurrentLocalMediaFolder();
            long bucketId = currentLocalMediaFolder != null ? currentLocalMediaFolder.getBucketId() : 0L;
            ExtendLoaderEngine extendLoaderEngine = PictureSelectionConfig.loaderDataEngine;
            if (extendLoaderEngine != null) {
                extendLoaderEngine.loadMoreMediaData(getContext(), bucketId, this.mPage, getPageLimit(bucketId), this.config.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.20
                    @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                    public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                        PictureSelectorFragment.this.handleMoreMediaData(arrayList, z2);
                    }
                });
            } else {
                this.mLoader.loadPageMediaData(bucketId, this.mPage, getPageLimit(bucketId), this.config.pageSize, new OnQueryDataResultListener<LocalMedia>() { // from class: com.luck.picture.lib.PictureSelectorFragment.21
                    @Override // com.luck.picture.lib.interfaces.OnQueryDataResultListener
                    public void onComplete(ArrayList<LocalMedia> arrayList, boolean z2) {
                        PictureSelectorFragment.this.handleMoreMediaData(arrayList, z2);
                    }
                });
            }
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorEvent
    public void loadOnlyInAppDirectoryAllMediaData() {
        ExtendLoaderEngine extendLoaderEngine = PictureSelectionConfig.loaderDataEngine;
        if (extendLoaderEngine != null) {
            extendLoaderEngine.loadOnlyInAppDirAllMediaData(getContext(), new OnQueryAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorFragment.13
                @Override // com.luck.picture.lib.interfaces.OnQueryAlbumListener
                public void onComplete(LocalMediaFolder localMediaFolder) {
                    PictureSelectorFragment.this.handleInAppDirAllMedia(localMediaFolder);
                }
            });
        } else {
            this.mLoader.loadOnlyInAppDirAllMedia(new OnQueryAlbumListener<LocalMediaFolder>() { // from class: com.luck.picture.lib.PictureSelectorFragment.14
                @Override // com.luck.picture.lib.interfaces.OnQueryAlbumListener
                public void onComplete(LocalMediaFolder localMediaFolder) {
                    PictureSelectorFragment.this.handleInAppDirAllMedia(localMediaFolder);
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onCheckOriginalChange() {
        this.bottomNarBar.setOriginalCheck();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onFixedSelectedChange(LocalMedia localMedia) {
        this.mAdapter.notifyItemPositionChanged(localMedia.position);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onFragmentResume() {
        setRootViewKeyListener(requireView());
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onKeyBackFragment() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (this.config.isActivityResultBack) {
            getActivity().setResult(0);
            onSelectFinish(0, null);
        } else {
            OnResultCallbackListener<LocalMedia> onResultCallbackListener = PictureSelectionConfig.onResultCallListener;
            if (onResultCallbackListener != null) {
                onResultCallbackListener.onCancel();
            }
        }
        onExitPictureSelector();
    }

    @Override // com.luck.picture.lib.interfaces.OnRecyclerViewPreloadMoreListener
    public void onRecyclerViewPreloadMore() {
        loadMoreMediaData();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(PictureConfig.EXTRA_ALL_FOLDER_SIZE, this.allFolderSize);
        bundle.putInt(PictureConfig.EXTRA_CURRENT_PAGE, this.mPage);
        bundle.putInt(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, this.mRecycler.getLastVisiblePosition());
        bundle.putBoolean(PictureConfig.EXTRA_DISPLAY_CAMERA, this.mAdapter.isDisplayCamera());
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    @SuppressLint({"NotifyDataSetChanged"})
    public void onSelectedChange(boolean z2, LocalMedia localMedia) {
        this.bottomNarBar.setSelectedChange();
        this.completeSelectView.setSelectedChange(false);
        if (checkNotifyStrategy(z2)) {
            this.mAdapter.notifyItemPositionChanged(localMedia.position);
            this.mRecycler.postDelayed(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorFragment.1
                @Override // java.lang.Runnable
                public void run() {
                    PictureSelectorFragment.this.mAdapter.notifyDataSetChanged();
                }
            }, 135L);
        } else {
            this.mAdapter.notifyItemPositionChanged(localMedia.position);
        }
        if (z2) {
            return;
        }
        sendChangeSubSelectPositionEvent(true);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        reStartSavedInstance(bundle);
        this.isCameraMemoryRecycling = bundle != null;
        this.tvDataEmpty = (TextView) view.findViewById(R.id.tv_data_empty);
        this.completeSelectView = (CompleteSelectView) view.findViewById(R.id.ps_complete_select);
        this.titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        this.bottomNarBar = (BottomNavBar) view.findViewById(R.id.bottom_nar_bar);
        this.tvCurrentDataTime = (TextView) view.findViewById(R.id.tv_current_data_time);
        initLoader();
        initAlbumListPopWindow();
        initTitleBar();
        initComplete();
        initRecycler(view);
        initBottomNavBar();
        requestLoadData();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void reStartSavedInstance(Bundle bundle) {
        super.reStartSavedInstance(bundle);
        if (bundle == null) {
            this.isDisplayCamera = this.config.isDisplayCamera;
            return;
        }
        this.allFolderSize = bundle.getInt(PictureConfig.EXTRA_ALL_FOLDER_SIZE);
        this.mPage = bundle.getInt(PictureConfig.EXTRA_CURRENT_PAGE, this.mPage);
        this.currentPosition = bundle.getInt(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, this.currentPosition);
        this.isDisplayCamera = bundle.getBoolean(PictureConfig.EXTRA_DISPLAY_CAMERA, this.config.isDisplayCamera);
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendChangeSubSelectPositionEvent(boolean z2) {
        if (PictureSelectionConfig.selectorStyle.getSelectMainStyle().isSelectNumberStyle()) {
            int i2 = 0;
            while (i2 < SelectedManager.getCount()) {
                LocalMedia localMedia = SelectedManager.getSelectedResult().get(i2);
                i2++;
                localMedia.setNum(i2);
                if (z2) {
                    this.mAdapter.notifyItemPositionChanged(localMedia.position);
                }
            }
        }
    }
}
