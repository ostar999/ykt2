package com.easefun.polyv.livecloudclass.modules.pagemenu.question;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.window.PLVBaseFragment;
import com.google.android.exoplayer2.ExoPlayer;
import com.plv.livescenes.feature.pagemenu.PLVQAWebView;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVLCQAFragment extends PLVBaseFragment {
    private static final String REPLACEMENT = "\\\\u0027";
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private ViewGroup parentLy;
    private PopupWindow popupWindow;
    private PLVQAWebView qaWebView;
    private String socketMsg;

    private void initView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.parent_ly);
        this.parentLy = viewGroup;
        viewGroup.setBackgroundColor(Color.parseColor("#141518"));
        this.qaWebView = new PLVQAWebView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.bottomMargin = ConvertUtils.dp2px(8.0f);
        this.qaWebView.setLayoutParams(layoutParams);
        this.parentLy.addView(this.qaWebView);
        PopupWindow popupWindow = new PopupWindow(getActivity().getLayoutInflater().inflate(com.easefun.polyv.livecloudclass.R.layout.plvlc_empty_popup, this.parentLy, false), 1, 1, false);
        this.popupWindow = popupWindow;
        popupWindow.setOutsideTouchable(true);
        this.popupWindow.setBackgroundDrawable(new ColorDrawable());
        this.popupWindow.setInputMethodMode(1);
        this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.question.PLVLCQAFragment.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                PLVLCQAFragment.this.qaWebView.clearFocus();
                PLVLCQAFragment.this.qaWebView.setFocusableInTouchMode(false);
                ((InputMethodManager) PLVLCQAFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(PLVLCQAFragment.this.getActivity().getWindow().getDecorView().getWindowToken(), 0);
            }
        });
        this.qaWebView.setOnTouchListener(new View.OnTouchListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.question.PLVLCQAFragment.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    PLVLCQAFragment.this.popupWindow.showAtLocation(PLVLCQAFragment.this.qaWebView, GravityCompat.END, 0, 0);
                    view.setFocusable(true);
                    view.setFocusableInTouchMode(true);
                    view.requestFocus();
                }
                return false;
            }
        });
        this.qaWebView.loadWeb();
        this.handler.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.question.PLVLCQAFragment.3
            @Override // java.lang.Runnable
            public void run() {
                PLVLCQAFragment.this.qaWebView.callInit(PLVLCQAFragment.this.socketMsg);
            }
        }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        observeDataChangedWithSocket();
    }

    private void observeDataChangedWithSocket() {
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.question.PLVLCQAFragment.4
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                if (PLVEventConstant.QuestionAndAnswer.EVENT_LAUNCH_A.equals(str2)) {
                    PLVLCQAFragment.this.qaWebView.callLaunchA(str3.replaceAll("'", PLVLCQAFragment.REPLACEMENT));
                } else if (PLVEventConstant.QuestionAndAnswer.EVENT_DELETE_QA_ANSWER.equals(str2)) {
                    PLVLCQAFragment.this.qaWebView.callDeleteQAAnswer(str3.replaceAll("'", PLVLCQAFragment.REPLACEMENT));
                } else if (PLVEventConstant.QuestionAndAnswer.EVENT_LAUNCH_Q.equals(str2)) {
                    PLVLCQAFragment.this.qaWebView.callLaunchQ(str3.replaceAll("'", PLVLCQAFragment.REPLACEMENT));
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener);
    }

    public void init(String str) {
        this.socketMsg = str;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(com.easefun.polyv.livecloudclass.R.layout.plv_horizontal_linear_layout, this.parentLy, false);
        initView();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        PLVQAWebView pLVQAWebView = this.qaWebView;
        if (pLVQAWebView != null) {
            if (pLVQAWebView.getParent() != null) {
                ((ViewGroup) this.qaWebView.getParent()).removeView(this.qaWebView);
            }
            this.qaWebView.removeAllViews();
            this.qaWebView.destroy();
            this.qaWebView = null;
        }
    }
}
