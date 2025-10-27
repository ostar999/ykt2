package com.easefun.polyv.livecommon.ui.window;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.KeyboardUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVInputWindow extends PLVBaseActivity {
    private static final int ALLOW_SHOW_INTERVAL = 1200;
    private static final String TAG = "PLVInputWindow";
    protected static InputListener inputListener;
    private static SpannableStringBuilder lastInputText;
    private static long lastStartTime;
    private EditText inputView;
    private boolean isShowKeyBoard;
    private List<View> popupButtonList = new ArrayList();
    private List<ViewGroup> popupLayoutList = new ArrayList();
    private View viewBg;
    private View willSelectPopupButton;
    private boolean willShowKeyBoard;
    private ViewGroup willShowPopupLayout;

    public interface InputListener {
        boolean onSendMsg(String message);
    }

    private void initView() {
        View viewFindViewById = findViewById(bgViewId());
        this.viewBg = viewFindViewById;
        viewFindViewById.setVisibility(8);
        EditText editText = (EditText) findViewById(inputViewId());
        this.inputView = editText;
        SpannableStringBuilder spannableStringBuilder = lastInputText;
        if (spannableStringBuilder != null) {
            editText.setText(spannableStringBuilder);
            EditText editText2 = this.inputView;
            editText2.setSelection(editText2.getText().length());
        }
        this.inputView.setOnTouchListener(new View.OnTouchListener() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputWindow.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v2, MotionEvent event) {
                PLVInputWindow.this.willShowKeyBoard = true;
                PLVInputWindow.this.hideAllPopupLayout();
                if (PLVInputWindow.this.isHideStatusBar()) {
                    PLVInputWindow.this.viewBg.setVisibility(0);
                }
                return false;
            }
        });
        this.inputView.addTextChangedListener(new TextWatcher() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputWindow.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                PLVCommonLog.d(PLVInputWindow.TAG, " beforeTextChanged:");
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
                PLVCommonLog.d(PLVInputWindow.TAG, " beforeTextChanged:" + s2.toString());
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                if (s2 == null || s2.length() <= 0) {
                    PLVCommonLog.d(PLVInputWindow.TAG, "onTextChanged: enabled false");
                } else {
                    PLVCommonLog.d(PLVInputWindow.TAG, "onTextChanged: enabled true");
                }
            }
        });
        this.inputView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputWindow.4
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView v2, int actionId, KeyEvent event) {
                if (actionId != 4) {
                    return false;
                }
                PLVInputWindow.this.postMsg();
                return true;
            }
        });
    }

    public static void setLastInputText(SpannableStringBuilder spannableStringBuilder) {
        lastInputText = spannableStringBuilder;
    }

    public static void show(Activity packageActivity, Class<? extends PLVInputWindow> cls, InputListener listener) {
        show(packageActivity, new Intent(packageActivity, cls), listener);
    }

    public void addPopupButton(View view) {
        this.popupButtonList.add(view);
    }

    public void addPopupLayout(ViewGroup popupLayout) {
        this.popupLayoutList.add(popupLayout);
    }

    public void backPressed() {
    }

    public abstract int bgViewId();

    public int computeUsableHeight(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return rect.bottom;
    }

    @Override // android.app.Activity
    public void finish() {
        if (this.isShowKeyBoard || popupLayoutIsVisible()) {
            hideSoftInputAndPopupLayout();
            return;
        }
        if (this.inputView != null) {
            lastInputText = new SpannableStringBuilder(this.inputView.getText());
        }
        inputListener = null;
        getWindow().setFlags(1024, 1024);
        super.finish();
        overridePendingTransition(0, 0);
    }

    public ViewGroup firstPopupLayout() {
        return null;
    }

    public View firstPopupView() {
        return null;
    }

    public abstract boolean firstShowInput();

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
        this.willShowPopupLayout = null;
        this.willSelectPopupButton = null;
    }

    public void hidePopupLayout(View view, ViewGroup popupLayout) {
        popupLayout.setVisibility(8);
        view.setSelected(false);
        this.willShowPopupLayout = null;
        this.willSelectPopupButton = null;
    }

    public void hideSoftInputAndPopupLayout() {
        EditText editText = this.inputView;
        if (editText != null) {
            this.willShowKeyBoard = false;
            KeyboardUtils.hideSoftInput(editText);
            this.viewBg.setVisibility(8);
            getWindow().setSoftInputMode(19);
        }
        hideAllPopupLayout();
    }

    public void hideStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(R2.color.material_dynamic_neutral_variant20);
    }

    public abstract int inputViewId();

    public boolean isHideStatusBar() {
        return false;
    }

    public abstract int layoutId();

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (!this.isShowKeyBoard && !popupLayoutIsVisible()) {
            backPressed();
        }
        super.onBackPressed();
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isHideStatusBar()) {
            hideStatusBar();
        }
        setContentView(layoutId());
        getWindow().setLayout(-1, -1);
        initView();
        final View childAt = ((FrameLayout) findViewById(R.id.content)).getChildAt(0);
        childAt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.ui.window.PLVInputWindow.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int iComputeUsableHeight = PLVInputWindow.this.computeUsableHeight(childAt);
                int iAbs = Math.abs(childAt.getRootView().getHeight() - iComputeUsableHeight);
                if (iAbs <= iComputeUsableHeight / 4) {
                    PLVInputWindow.this.isShowKeyBoard = false;
                    if (!PLVInputWindow.this.willShowKeyBoard) {
                        PLVInputWindow.this.viewBg.setVisibility(8);
                    }
                    if (PLVInputWindow.this.willShowPopupLayout != null) {
                        PLVInputWindow.this.willShowPopupLayout.setVisibility(0);
                    }
                    if (PLVInputWindow.this.willSelectPopupButton != null) {
                        PLVInputWindow.this.willSelectPopupButton.setSelected(true);
                        return;
                    }
                    return;
                }
                PLVInputWindow.this.isShowKeyBoard = true;
                if (PLVInputWindow.this.willShowKeyBoard && PLVInputWindow.this.isHideStatusBar()) {
                    PLVInputWindow.this.hideStatusBar();
                    PLVInputWindow.this.viewBg.setVisibility(0);
                }
                PLVInputWindow.this.willShowKeyBoard = false;
                if (PLVInputWindow.this.viewBg.getTag() == null || ((Integer) PLVInputWindow.this.viewBg.getTag()).intValue() != iAbs) {
                    ViewGroup.LayoutParams layoutParams = PLVInputWindow.this.viewBg.getLayoutParams();
                    layoutParams.height = iAbs;
                    PLVInputWindow.this.viewBg.setLayoutParams(layoutParams);
                    PLVInputWindow.this.viewBg.setTag(Integer.valueOf(iAbs));
                }
            }
        });
        if (firstShowInput()) {
            willShowInput();
        } else {
            showPopupLayout();
        }
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            requestClose();
        }
        return super.onTouchEvent(event);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            return;
        }
        hideSoftInputAndPopupLayout();
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

    public void postMsg() {
        String string = this.inputView.getText().toString();
        if (string.trim().length() == 0) {
            ToastUtils.showLong(com.easefun.polyv.livecommon.R.string.plv_chat_toast_send_text_empty);
            return;
        }
        InputListener inputListener2 = inputListener;
        if (inputListener2 != null ? inputListener2.onSendMsg(string) : true) {
            this.inputView.setText("");
            requestClose();
        }
    }

    public void requestClose() {
        hideSoftInputAndPopupLayout();
        this.isShowKeyBoard = false;
        finish();
    }

    public void showPopupLayout() {
        getWindow().setSoftInputMode(19);
        showPopupLayout(firstPopupView(), firstPopupLayout());
    }

    public void togglePopupLayout(View view, ViewGroup popupLayout) {
        if (view.isSelected()) {
            hidePopupLayout(view, popupLayout);
        } else {
            showPopupLayout(view, popupLayout);
        }
    }

    public void willShowInput() {
        this.willShowKeyBoard = true;
        getWindow().setSoftInputMode(20);
    }

    public static void show(Activity packageActivity, Intent intent, InputListener listener) {
        if (System.currentTimeMillis() - lastStartTime > 1200) {
            lastStartTime = System.currentTimeMillis();
            inputListener = listener;
            packageActivity.startActivity(intent);
            packageActivity.overridePendingTransition(0, 0);
        }
    }

    public void showPopupLayout(final View view, final ViewGroup popupLayout) {
        this.willShowKeyBoard = false;
        hideAllPopupLayout();
        KeyboardUtils.hideSoftInput(this.inputView);
        this.viewBg.setVisibility(8);
        if (this.isShowKeyBoard) {
            this.willShowPopupLayout = popupLayout;
            this.willSelectPopupButton = view;
        } else {
            popupLayout.setVisibility(0);
            view.setSelected(true);
        }
    }
}
