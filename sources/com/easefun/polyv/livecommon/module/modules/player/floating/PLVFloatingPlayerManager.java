package com.easefun.polyv.livecommon.module.modules.player.floating;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.utils.PLVViewSwitcher;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.widget.floating.PLVFloatingWindowManager;
import com.easefun.polyv.livecommon.ui.widget.floating.enums.PLVFloatingEnums;
import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes3.dex */
public class PLVFloatingPlayerManager {
    private static final PLVFloatingPlayerManager INSTANCE = new PLVFloatingPlayerManager();

    @Nullable
    private PLVSwitchViewAnchorLayout contentOriginAnchorLayout;

    @Nullable
    private PLVFloatingPlayerView floatingView;
    private final MutableLiveData<Boolean> floatingViewShowState;
    private int height;

    @Nullable
    private String identifyTag;
    private int left;

    @Nullable
    private OnCloseFloatingWindowListener onCloseFloatingWindowListener;
    private final Queue<Runnable> onClosedTaskQueue = new ArrayDeque();

    @Nullable
    private OnCreateFloatingViewListener onCreateFloatingViewListener;

    @Nullable
    private OnGoBackListener onGoBackListener;

    @Nullable
    private Intent savedLastIntent;

    @Nullable
    private Intent savedShowingFloatWindowIntent;
    private PLVFloatingEnums.ShowType showType;

    /* renamed from: top, reason: collision with root package name */
    private int f6550top;

    @Nullable
    private PLVViewSwitcher viewSwitcher;
    private int width;

    public interface OnCloseFloatingWindowListener {
        void onClosedFloatingWindow(@Nullable String tag);
    }

    public interface OnCreateFloatingViewListener {
        @NonNull
        PLVFloatingPlayerView onCreateFloatingView(@NonNull Context context);
    }

    public interface OnGoBackListener {
        void onGoBack(@Nullable Intent savedIntent);
    }

    private PLVFloatingPlayerManager() {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        this.floatingViewShowState = mutableLiveData;
        this.identifyTag = null;
        this.savedLastIntent = null;
        this.savedShowingFloatWindowIntent = null;
        this.contentOriginAnchorLayout = null;
        this.floatingView = null;
        this.viewSwitcher = null;
        this.left = 0;
        this.f6550top = 0;
        this.width = 0;
        this.height = 0;
        this.showType = PLVFloatingEnums.ShowType.SHOW_ALWAYS;
        this.onCreateFloatingViewListener = null;
        this.onGoBackListener = null;
        this.onCloseFloatingWindowListener = null;
        mutableLiveData.postValue(Boolean.FALSE);
    }

    private void closeFloatingWindow() {
        PLVFloatingWindowManager.getInstance().hide();
        PLVFloatingWindowManager.getInstance().destroy();
    }

    @NonNull
    private PLVFloatingPlayerView createFloatingView(@NonNull Context context) {
        OnCreateFloatingViewListener onCreateFloatingViewListener = this.onCreateFloatingViewListener;
        return onCreateFloatingViewListener != null ? onCreateFloatingViewListener.onCreateFloatingView(context) : new PLVFloatingPlayerView(context).setOnClickGoBackListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (PLVFloatingPlayerManager.this.onGoBackListener != null) {
                    PLVFloatingPlayerManager.this.onGoBackListener.onGoBack(PLVFloatingPlayerManager.this.savedShowingFloatWindowIntent);
                }
            }
        }).setOnClickCloseListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (PLVFloatingPlayerManager.this.onCloseFloatingWindowListener != null) {
                    PLVFloatingPlayerManager.this.onCloseFloatingWindowListener.onClosedFloatingWindow(PLVFloatingPlayerManager.this.identifyTag);
                }
            }
        });
    }

    public static PLVFloatingPlayerManager getInstance() {
        return INSTANCE;
    }

    private void runAllClosePendingTask() {
        while (!this.onClosedTaskQueue.isEmpty()) {
            Runnable runnablePoll = this.onClosedTaskQueue.poll();
            if (runnablePoll != null) {
                runnablePoll.run();
            }
        }
    }

    private void showFloatingWindow() {
        if (this.floatingView == null) {
            return;
        }
        PLVFloatingWindowManager.getInstance().createNewWindow((Activity) this.floatingView.getContext()).setIsSystemWindow(true).setContentView(this.floatingView).setSize(this.width, this.height).setFloatLocation(this.left, this.f6550top).setShowType(this.showType).setAutoMoveToEdge(PLVFloatingEnums.AutoEdgeType.NO_AUTO_MOVE).build().show((Activity) this.floatingView.getContext());
    }

    public PLVFloatingPlayerManager bindContentLayout(@Nullable PLVSwitchViewAnchorLayout anchorLayout) {
        this.contentOriginAnchorLayout = anchorLayout;
        return this;
    }

    public void clear() {
        hide();
        this.contentOriginAnchorLayout = null;
        this.floatingView = null;
        this.viewSwitcher = null;
        this.onCreateFloatingViewListener = null;
        this.onGoBackListener = null;
        this.onCloseFloatingWindowListener = null;
    }

    public LiveData<Boolean> getFloatingViewShowState() {
        return this.floatingViewShowState;
    }

    public void hide() {
        if (this.viewSwitcher == null || this.floatingView == null) {
            return;
        }
        closeFloatingWindow();
        PLVSwitchViewAnchorLayout placeholderParentAnchorLayout = this.floatingView.getPlaceholderParentAnchorLayout();
        if (placeholderParentAnchorLayout != null) {
            this.viewSwitcher.registerSwitchView(placeholderParentAnchorLayout, this.floatingView.getAnchorLayout());
        }
        this.viewSwitcher.switchView();
        this.viewSwitcher = null;
        this.floatingView = null;
        runAllClosePendingTask();
        this.floatingViewShowState.postValue(Boolean.FALSE);
    }

    public boolean isFloatingWindowShowing() {
        return this.viewSwitcher != null;
    }

    public PLVFloatingPlayerManager runOnFloatingWindowClosed(Runnable runnable) {
        if (isFloatingWindowShowing()) {
            this.onClosedTaskQueue.add(runnable);
            return this;
        }
        runnable.run();
        return this;
    }

    public PLVFloatingPlayerManager saveIntent(@Nullable Intent intent) {
        this.savedLastIntent = intent;
        return this;
    }

    public PLVFloatingPlayerManager setFloatingPosition(int left, int top2) {
        this.left = left;
        this.f6550top = top2;
        return this;
    }

    public PLVFloatingPlayerManager setFloatingSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public PLVFloatingPlayerManager setOnCloseFloatingWindowListener(@Nullable OnCloseFloatingWindowListener onCloseFloatingWindowListener) {
        this.onCloseFloatingWindowListener = onCloseFloatingWindowListener;
        return this;
    }

    public PLVFloatingPlayerManager setOnCreateFloatingViewListener(@Nullable OnCreateFloatingViewListener onCreateFloatingViewListener) {
        this.onCreateFloatingViewListener = onCreateFloatingViewListener;
        return this;
    }

    public PLVFloatingPlayerManager setOnGoBackListener(@Nullable OnGoBackListener onGoBackListener) {
        this.onGoBackListener = onGoBackListener;
        return this;
    }

    public PLVFloatingPlayerManager setTag(@Nullable String tag) {
        this.identifyTag = tag;
        return this;
    }

    public void show() {
        if (this.contentOriginAnchorLayout == null || isFloatingWindowShowing()) {
            return;
        }
        this.savedShowingFloatWindowIntent = this.savedLastIntent;
        this.floatingView = createFloatingView(this.contentOriginAnchorLayout.getContext());
        PLVViewSwitcher pLVViewSwitcher = new PLVViewSwitcher();
        this.viewSwitcher = pLVViewSwitcher;
        pLVViewSwitcher.registerSwitchView(this.contentOriginAnchorLayout, this.floatingView.getAnchorLayout());
        this.viewSwitcher.switchView();
        showFloatingWindow();
        this.floatingViewShowState.postValue(Boolean.TRUE);
    }

    public PLVFloatingPlayerManager updateShowType(PLVFloatingEnums.ShowType showType) {
        this.showType = showType;
        PLVFloatingWindowManager.getInstance().setShowType(showType);
        return this;
    }
}
