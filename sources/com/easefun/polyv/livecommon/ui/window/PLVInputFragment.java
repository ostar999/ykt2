package com.easefun.polyv.livecommon.ui.window;

import android.R;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.window.PLVEmptyFragment;
import com.plv.thirdpart.blankj.utilcode.util.KeyboardUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVInputFragment extends PLVBaseFragment {
    private ViewGroup curShowLayout;
    private View curShowView;
    private PLVEmptyFragment emptyFragment;
    private ViewGroup fragmentView;
    private ViewGroup inputLayout;
    private ViewGroup.LayoutParams inputLayoutParams;
    private ViewGroup inputLayoutParent;
    private EditText inputView;
    private boolean isShowKeyBoard;
    private OnceHideKeyBoardListener onceHideKeyBoardListener;
    private List<View> popupButtonList = new ArrayList();
    private List<ViewGroup> popupLayoutList = new ArrayList();
    private boolean willShowKeyBoard;
    private boolean willShowPopupLayout;

    public interface OnceHideKeyBoardListener {
        void call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int computeUsableHeight(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return rect.bottom;
    }

    private void initView() {
        this.inputLayout = (ViewGroup) findViewById(inputLayoutId());
        EditText editText = (EditText) findViewById(inputViewId());
        this.inputView = editText;
        editText.setOnTouchListener(new View.OnTouchListener() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputFragment.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v2, MotionEvent event) {
                PLVInputFragment.this.hideAllPopupLayout();
                PLVInputFragment.this.moveInputLayoutToOtherWindow(true);
                return false;
            }
        });
        this.inputView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputFragment.2
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView v2, int actionId, KeyEvent event) {
                if (actionId != 4) {
                    return false;
                }
                PLVInputFragment.this.postMsg();
                return true;
            }
        });
        PLVEmptyFragment pLVEmptyFragment = new PLVEmptyFragment();
        this.emptyFragment = pLVEmptyFragment;
        pLVEmptyFragment.setViewActionListener(new PLVEmptyFragment.ViewActionListener() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputFragment.3
            @Override // com.easefun.polyv.livecommon.ui.window.PLVEmptyFragment.ViewActionListener
            public void onViewCreated(View view) {
                PLVInputFragment.this.fragmentView = (ViewGroup) view;
                PLVInputFragment.this.fragmentView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputFragment.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        PLVInputFragment.this.hideSoftInputAndPopupLayout();
                    }
                });
            }
        });
        getFragmentManager().beginTransaction().add(attachContainerViewId(), this.emptyFragment, "PLVEmptyFragment").hide(this.emptyFragment).commitAllowingStateLoss();
        final View childAt = ((FrameLayout) getActivity().findViewById(R.id.content)).getChildAt(0);
        childAt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputFragment.4
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int iComputeUsableHeight = PLVInputFragment.this.computeUsableHeight(childAt);
                if (Math.abs(childAt.getRootView().getHeight() - iComputeUsableHeight) > iComputeUsableHeight / 4) {
                    PLVInputFragment.this.isShowKeyBoard = true;
                    PLVInputFragment.this.willShowKeyBoard = false;
                    return;
                }
                PLVInputFragment.this.isShowKeyBoard = false;
                if (PLVInputFragment.this.onceHideKeyBoardListener != null) {
                    PLVInputFragment.this.onceHideKeyBoardListener.call();
                    PLVInputFragment.this.onceHideKeyBoardListener = null;
                }
                if (PLVInputFragment.this.willShowPopupLayout) {
                    if (PLVInputFragment.this.curShowLayout != null) {
                        PLVInputFragment.this.curShowLayout.setVisibility(0);
                    }
                    if (PLVInputFragment.this.curShowView != null) {
                        PLVInputFragment.this.curShowView.setSelected(true);
                    }
                }
                PLVInputFragment.this.willShowPopupLayout = false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveInputLayoutToOtherWindow(boolean isShowInput) {
        if (!this.emptyFragment.isVisible() && !this.willShowKeyBoard) {
            this.willShowKeyBoard = true;
            if (this.inputLayout.getParent() instanceof ViewGroup) {
                this.inputLayoutParams = this.inputLayout.getLayoutParams();
                this.inputLayoutParent = (ViewGroup) this.inputLayout.getParent();
                ((ViewGroup) this.inputLayout.getParent()).removeView(this.inputLayout);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                layoutParams.gravity = 80;
                this.inputLayout.setLayoutParams(layoutParams);
                this.fragmentView.addView(this.inputLayout);
            }
            getFragmentManager().beginTransaction().show(this.emptyFragment).commitAllowingStateLoss();
            if (isShowInput) {
                KeyboardUtils.showSoftInput(this.inputView);
            }
        }
        if (isShowInput) {
            return;
        }
        this.willShowKeyBoard = false;
        KeyboardUtils.hideSoftInput(this.inputView);
    }

    private void moveInputLayoutToSrcWindow() {
        if (this.inputLayout.getParent().equals(this.fragmentView)) {
            this.willShowKeyBoard = false;
            this.fragmentView.removeView(this.inputLayout);
            this.inputLayout.setLayoutParams(this.inputLayoutParams);
            this.inputLayoutParent.addView(this.inputLayout);
            KeyboardUtils.hideSoftInput(this.inputView);
            getFragmentManager().beginTransaction().hide(this.emptyFragment).commitAllowingStateLoss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postMsg() {
        String string = this.inputView.getText().toString();
        if (string.trim().length() == 0) {
            ToastUtils.showLong(com.easefun.polyv.livecommon.R.string.plv_chat_toast_send_text_empty);
        } else if (onSendMsg(string)) {
            this.inputView.setText("");
            hideSoftInputAndPopupLayout();
        }
    }

    public void addPopupButton(View view) {
        this.popupButtonList.add(view);
    }

    public void addPopupLayout(ViewGroup popupLayout) {
        this.popupLayoutList.add(popupLayout);
    }

    public int attachContainerViewId() {
        return R.id.content;
    }

    public void hideAllPopupLayout() {
        if (popupLayoutIsVisible()) {
            Iterator<ViewGroup> it = this.popupLayoutList.iterator();
            while (it.hasNext()) {
                it.next().setVisibility(8);
            }
            Iterator<View> it2 = this.popupButtonList.iterator();
            while (it2.hasNext()) {
                it2.next().setSelected(false);
            }
        }
    }

    public void hidePopupLayout(View view, ViewGroup popupLayout) {
        popupLayout.setVisibility(8);
        view.setSelected(false);
    }

    public void hideSoftInputAndPopupLayout() {
        moveInputLayoutToSrcWindow();
        hideAllPopupLayout();
    }

    public abstract int inputLayoutId();

    public abstract int inputViewId();

    public boolean isShowKeyBoard(OnceHideKeyBoardListener listener) {
        this.onceHideKeyBoardListener = listener;
        boolean z2 = this.isShowKeyBoard;
        if (!z2) {
            this.onceHideKeyBoardListener = null;
        }
        return z2;
    }

    public boolean onBackPressed() {
        if (!popupLayoutIsVisible()) {
            return false;
        }
        hideAllPopupLayout();
        return true;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        hideSoftInputAndPopupLayout();
    }

    public abstract boolean onSendMsg(String message);

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public boolean popupLayoutIsVisible() {
        Iterator<ViewGroup> it = this.popupLayoutList.iterator();
        while (it.hasNext()) {
            if (it.next().getVisibility() == 0) {
                return true;
            }
        }
        return false;
    }

    public void showPopupLayout(final View view, final ViewGroup popupLayout) {
        this.willShowKeyBoard = false;
        hideAllPopupLayout();
        moveInputLayoutToOtherWindow(false);
        if (this.isShowKeyBoard) {
            this.willShowPopupLayout = true;
            this.curShowView = view;
            this.curShowLayout = popupLayout;
        } else {
            popupLayout.setVisibility(0);
            view.setSelected(true);
            this.inputView.requestFocus();
        }
    }

    public void togglePopupLayout(View view, ViewGroup popupLayout) {
        if (view.isSelected()) {
            hidePopupLayout(view, popupLayout);
        } else {
            showPopupLayout(view, popupLayout);
        }
    }
}
