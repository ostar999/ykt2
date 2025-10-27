package com.easefun.polyv.livecloudclass.modules.pagemenu.product;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.vo.PLVCommodityUiState;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer;
import com.easefun.polyv.livecommon.ui.widget.menudrawer.Position;
import com.easefun.polyv.livecommon.ui.window.PLVBaseFragment;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVLCProductFragment extends PLVBaseFragment {
    private final PLVCommodityViewModel commodityViewModel = (PLVCommodityViewModel) PLVDependManager.getInstance().get(PLVCommodityViewModel.class);
    private final ProductLandscapeLayoutHelper landscapeLayoutHelper = new ProductLandscapeLayoutHelper();

    @Nullable
    private PLVOrientationManager.OnConfigurationChangedListener onConfigurationChangedListener;
    private PLVLCProductLayout pageMenuProductLayout;
    private View rootView;

    public static class ProductLandscapeLayoutHelper {

        @Nullable
        private PLVMenuDrawer menuDrawer;

        @Nullable
        private PLVSugarUtil.Consumer<View> onMenuHideCallback;

        private ProductLandscapeLayoutHelper() {
        }

        public void hide() {
            PLVMenuDrawer pLVMenuDrawer = this.menuDrawer;
            if (pLVMenuDrawer != null) {
                pLVMenuDrawer.closeMenu();
            }
        }

        public boolean isShowing() {
            PLVMenuDrawer pLVMenuDrawer = this.menuDrawer;
            return (pLVMenuDrawer == null || pLVMenuDrawer.getDrawerState() == 0) ? false : true;
        }

        public void setOnMenuHide(PLVSugarUtil.Consumer<View> consumer) {
            this.onMenuHideCallback = consumer;
        }

        public void show(ViewGroup viewGroup, View view) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            PLVMenuDrawer pLVMenuDrawer = this.menuDrawer;
            if (pLVMenuDrawer != null) {
                pLVMenuDrawer.setMenuView(view);
                this.menuDrawer.attachToContainer();
                this.menuDrawer.openMenu();
                return;
            }
            PLVMenuDrawer pLVMenuDrawerAttach = PLVMenuDrawer.attach((Activity) viewGroup.getContext(), PLVMenuDrawer.Type.OVERLAY, Position.END, 2, viewGroup);
            this.menuDrawer = pLVMenuDrawerAttach;
            pLVMenuDrawerAttach.setMenuView(view);
            this.menuDrawer.setMenuSize(ConvertUtils.dp2px(375.0f));
            this.menuDrawer.setTouchMode(1);
            this.menuDrawer.setDrawOverlay(false);
            this.menuDrawer.setDropShadowEnabled(false);
            this.menuDrawer.setOnDrawerStateChangeListener(new PLVMenuDrawer.OnDrawerStateChangeListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductFragment.ProductLandscapeLayoutHelper.1
                @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.OnDrawerStateChangeListener
                public void onDrawerSlide(float f2, int i2) {
                }

                @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.OnDrawerStateChangeListener
                public void onDrawerStateChange(int i2, int i3) {
                    if (i3 == 0) {
                        ProductLandscapeLayoutHelper.this.menuDrawer.detachToContainer();
                        View menuView = ProductLandscapeLayoutHelper.this.menuDrawer.getMenuView();
                        if (menuView.getParent() != null) {
                            ((ViewGroup) menuView.getParent()).removeView(menuView);
                        }
                        if (ProductLandscapeLayoutHelper.this.onMenuHideCallback != null) {
                            ProductLandscapeLayoutHelper.this.onMenuHideCallback.accept(menuView);
                        }
                    }
                }
            });
            this.menuDrawer.openMenu();
        }
    }

    private void findView() {
        this.pageMenuProductLayout = (PLVLCProductLayout) this.rootView.findViewById(R.id.plvlc_page_menu_product_layout);
    }

    private void initView() {
        findView();
        setOnLandscapeMenuHideListener();
        observeOnOrientationChanged();
        observeShowProductViewOnLandscape();
    }

    private void observeOnOrientationChanged() {
        if (this.onConfigurationChangedListener != null) {
            PLVOrientationManager.getInstance().removeOnConfigurationChangedListener(this.onConfigurationChangedListener);
        }
        PLVOrientationManager pLVOrientationManager = PLVOrientationManager.getInstance();
        PLVOrientationManager.OnConfigurationChangedListener onConfigurationChangedListener = new PLVOrientationManager.OnConfigurationChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductFragment.2
            @Override // com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager.OnConfigurationChangedListener
            public void onCall(Context context, boolean z2) {
                if (z2 || !PLVLCProductFragment.this.landscapeLayoutHelper.isShowing()) {
                    return;
                }
                PLVLCProductFragment.this.landscapeLayoutHelper.hide();
            }
        };
        this.onConfigurationChangedListener = onConfigurationChangedListener;
        pLVOrientationManager.addOnConfigurationChangedListener(onConfigurationChangedListener);
    }

    private void observeShowProductViewOnLandscape() {
        this.commodityViewModel.getCommodityUiStateLiveData().observe((LifecycleOwner) this.rootView.getContext(), new Observer<PLVCommodityUiState>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductFragment.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVCommodityUiState pLVCommodityUiState) {
                if (pLVCommodityUiState == null) {
                    return;
                }
                if (pLVCommodityUiState.hasProductView && pLVCommodityUiState.showProductViewOnLandscape && !PLVLCProductFragment.this.landscapeLayoutHelper.isShowing()) {
                    PLVLCProductFragment.this.landscapeLayoutHelper.show((ViewGroup) ((Activity) PLVLCProductFragment.this.rootView.getContext()).findViewById(R.id.plvlc_popup_container), PLVLCProductFragment.this.pageMenuProductLayout);
                } else if (PLVLCProductFragment.this.landscapeLayoutHelper.isShowing()) {
                    PLVLCProductFragment.this.landscapeLayoutHelper.hide();
                }
            }
        });
    }

    private void setOnLandscapeMenuHideListener() {
        this.landscapeLayoutHelper.setOnMenuHide(new PLVSugarUtil.Consumer<View>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductFragment.1
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(View view) {
                PLVLCProductFragment.this.commodityViewModel.onLandscapeProductLayoutHide();
                ((ViewGroup) PLVLCProductFragment.this.rootView).addView(view, -1, -1);
            }
        });
    }

    public void init(final IPLVLiveRoomDataManager iPLVLiveRoomDataManager) {
        runAfterOnActivityCreated(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductFragment.4
            @Override // java.lang.Runnable
            public void run() {
                PLVLCProductFragment.this.pageMenuProductLayout.init(iPLVLiveRoomDataManager);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        initView();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View view = this.rootView;
        if (view != null) {
            return view;
        }
        View viewInflate = layoutInflater.inflate(R.layout.plvlc_page_menu_product_fragment, (ViewGroup) null);
        this.rootView = viewInflate;
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (this.onConfigurationChangedListener != null) {
            PLVOrientationManager.getInstance().removeOnConfigurationChangedListener(this.onConfigurationChangedListener);
            this.onConfigurationChangedListener = null;
        }
    }
}
