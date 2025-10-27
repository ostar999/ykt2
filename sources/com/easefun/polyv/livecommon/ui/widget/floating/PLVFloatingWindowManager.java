package com.easefun.polyv.livecommon.ui.widget.floating;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.floating.enums.PLVFloatingEnums;
import com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout;
import com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVAbsFloatingLayout;
import com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVAppFloatingLayout;
import com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVSystemFloatingLayout;

/* loaded from: classes3.dex */
public class PLVFloatingWindowManager implements IPLVFloatingLayout {
    private static final PLVFloatingWindowManager INSTANCE = new PLVFloatingWindowManager();

    @Nullable
    private PLVAbsFloatingLayout floatingLayout;

    public static class WindowBuilder {
        private final Param buildParam;

        public static class Param {
            private Activity activity;
            private PLVFloatingEnums.AutoEdgeType autoEdgeType;
            private boolean consumeTouchEventOnMove;
            private View contentView;
            private boolean enableDrag;
            private int height;
            private boolean isSystemWindow;
            private int left;
            private PLVFloatingEnums.ShowType showType;

            /* renamed from: top, reason: collision with root package name */
            private int f6552top;
            private int width;

            private Param() {
                this.isSystemWindow = true;
                this.showType = PLVFloatingEnums.ShowType.SHOW_ONLY_BACKGROUND;
                this.autoEdgeType = PLVFloatingEnums.AutoEdgeType.AUTO_MOVE_TO_RIGHT;
                this.enableDrag = true;
                this.consumeTouchEventOnMove = true;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public PLVAbsFloatingLayout create() {
                PLVAbsFloatingLayout pLVSystemFloatingLayout = this.isSystemWindow ? new PLVSystemFloatingLayout(this.activity) : new PLVAppFloatingLayout(this.activity);
                ViewGroup.LayoutParams layoutParams = pLVSystemFloatingLayout.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.LayoutParams(-1, -1);
                }
                layoutParams.width = this.width;
                layoutParams.height = this.height;
                pLVSystemFloatingLayout.setLayoutParams(layoutParams);
                pLVSystemFloatingLayout.setContentView(this.contentView);
                pLVSystemFloatingLayout.updateFloatSize(this.width, this.height);
                pLVSystemFloatingLayout.updateFloatLocation(this.left, this.f6552top);
                pLVSystemFloatingLayout.setShowType(this.showType);
                pLVSystemFloatingLayout.setAutoMoveToEdge(this.autoEdgeType);
                pLVSystemFloatingLayout.setEnableDrag(this.enableDrag);
                pLVSystemFloatingLayout.setConsumeTouchEventOnMove(this.consumeTouchEventOnMove);
                return pLVSystemFloatingLayout;
            }
        }

        public PLVFloatingWindowManager build() {
            PLVFloatingWindowManager.getInstance().floatingLayout = this.buildParam.create();
            return PLVFloatingWindowManager.getInstance();
        }

        public WindowBuilder setAutoMoveToEdge(PLVFloatingEnums.AutoEdgeType autoEdgeType) {
            this.buildParam.autoEdgeType = autoEdgeType;
            return this;
        }

        public WindowBuilder setConsumeTouchEventOnMove(boolean consumeTouchEventOnMove) {
            this.buildParam.consumeTouchEventOnMove = consumeTouchEventOnMove;
            return this;
        }

        public WindowBuilder setContentView(View view) {
            this.buildParam.contentView = view;
            return this;
        }

        public WindowBuilder setEnableDrag(boolean enableDrag) {
            this.buildParam.enableDrag = enableDrag;
            return this;
        }

        public WindowBuilder setFloatLocation(int left, int top2) {
            this.buildParam.left = left;
            this.buildParam.f6552top = top2;
            return this;
        }

        public WindowBuilder setIsSystemWindow(boolean isSystemWindow) {
            this.buildParam.isSystemWindow = isSystemWindow;
            return this;
        }

        public WindowBuilder setShowType(PLVFloatingEnums.ShowType showType) {
            this.buildParam.showType = showType;
            return this;
        }

        public WindowBuilder setSize(int width, int height) {
            this.buildParam.width = width;
            this.buildParam.height = height;
            return this;
        }

        private WindowBuilder(@NonNull Activity activity) {
            Param param = new Param();
            this.buildParam = param;
            param.activity = activity;
        }
    }

    private PLVFloatingWindowManager() {
    }

    public static PLVFloatingWindowManager getInstance() {
        return INSTANCE;
    }

    public WindowBuilder createNewWindow(Activity activity) {
        destroy();
        return new WindowBuilder(activity);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void destroy() {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.destroy();
            this.floatingLayout = null;
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    @Nullable
    public View getContentView() {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            return pLVAbsFloatingLayout.getContentView();
        }
        return null;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    @Nullable
    public Point getFloatLocation() {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            return pLVAbsFloatingLayout.getFloatLocation();
        }
        return null;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void hide() {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.hide();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public boolean isShowing() {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            return pLVAbsFloatingLayout.isShowing();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setAutoMoveToEdge(PLVFloatingEnums.AutoEdgeType autoEdgeType) {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.setAutoMoveToEdge(autoEdgeType);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setConsumeTouchEventOnMove(boolean consumeTouchEventOnMove) {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.setConsumeTouchEventOnMove(consumeTouchEventOnMove);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setContentView(View view) {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.setContentView(view);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setEnableDrag(boolean enableDrag) {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.setEnableDrag(enableDrag);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setShowType(PLVFloatingEnums.ShowType showType) {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.setShowType(showType);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void show(Activity activity) {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.show(activity);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void updateFloatLocation(int x2, int y2) {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.updateFloatLocation(x2, y2);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void updateFloatSize(int width, int height) {
        PLVAbsFloatingLayout pLVAbsFloatingLayout = this.floatingLayout;
        if (pLVAbsFloatingLayout != null) {
            pLVAbsFloatingLayout.updateFloatSize(width, height);
        }
    }
}
