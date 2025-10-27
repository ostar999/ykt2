package com.easefun.polyv.livecloudclass.modules.media.danmu;

import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender;
import com.easefun.polyv.livecommon.ui.widget.PLVOrientationSensibleLinearLayout;
import com.plv.thirdpart.blankj.utilcode.util.KeyboardUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;

/* loaded from: classes3.dex */
public class PLVLCLandscapeMessageSendPanel implements IPLVLCLandscapeMessageSender {
    private AppCompatActivity activity;
    private View anchor;
    private EditText etSendMessage;
    private PLVOrientationSensibleLinearLayout llSendMessage;
    private IPLVLCLandscapeMessageSender.OnSendMessageListener sendMessageListener;
    private TextView tvSendMessage;
    private int usableHeightPrevious;
    private PopupWindow window;

    public PLVLCLandscapeMessageSendPanel(AppCompatActivity appCompatActivity, View view) {
        this.anchor = view;
        this.activity = appCompatActivity;
        this.window = new PopupWindow(this.activity);
        View viewInflate = LayoutInflater.from(this.activity).inflate(R.layout.plvlc_player_message_send_layout, (ViewGroup) null);
        this.window.setContentView(viewInflate);
        this.window.setOutsideTouchable(false);
        this.window.setFocusable(true);
        this.window.setBackgroundDrawable(null);
        int iMax = Math.max(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
        int iMin = Math.min(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
        this.window.setWidth(iMax);
        this.window.setHeight(iMin);
        initView(viewInflate);
        final View childAt = ((FrameLayout) this.activity.findViewById(android.R.id.content)).getChildAt(0);
        childAt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams;
                int iComputeUsableHeight = PLVLCLandscapeMessageSendPanel.this.computeUsableHeight(childAt);
                if (PLVLCLandscapeMessageSendPanel.this.usableHeightPrevious == iComputeUsableHeight || (layoutParams = PLVLCLandscapeMessageSendPanel.this.llSendMessage.getLayoutParams()) == null) {
                    return;
                }
                int height = childAt.getRootView().getHeight();
                int iAbs = Math.abs(height - iComputeUsableHeight);
                if (iAbs > iComputeUsableHeight / 4) {
                    layoutParams.height = height - iAbs;
                } else {
                    layoutParams.height = height;
                }
                childAt.requestLayout();
                PLVLCLandscapeMessageSendPanel.this.usableHeightPrevious = iComputeUsableHeight;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int computeUsableHeight(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return rect.bottom;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hide() {
        KeyboardUtils.hideSoftInput(this.etSendMessage);
        this.window.dismiss();
    }

    private void initView(View view) {
        this.llSendMessage = (PLVOrientationSensibleLinearLayout) view.findViewById(R.id.ll_send_message);
        this.tvSendMessage = (TextView) view.findViewById(R.id.tv_send_message);
        this.etSendMessage = (EditText) view.findViewById(R.id.et_send_message);
        this.llSendMessage.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PLVLCLandscapeMessageSendPanel.this.onClick(view2);
            }
        });
        this.tvSendMessage.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PLVLCLandscapeMessageSendPanel.this.onClick(view2);
            }
        });
        this.etSendMessage.addTextChangedListener(new TextWatcher() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel.5
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PLVLCLandscapeMessageSendPanel.this.tvSendMessage.setEnabled(!TextUtils.isEmpty(PLVLCLandscapeMessageSendPanel.this.etSendMessage.getText()));
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        });
        this.etSendMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel.6
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                if (i2 != 4) {
                    return false;
                }
                PLVLCLandscapeMessageSendPanel.this.sendMessage();
                return true;
            }
        });
        this.llSendMessage.setOnPortrait(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel.7
            @Override // java.lang.Runnable
            public void run() {
                PLVLCLandscapeMessageSendPanel.this.hide();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_send_message) {
            hide();
        } else if (id == R.id.tv_send_message) {
            sendMessage();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage() {
        String string = this.etSendMessage.getText().toString();
        if (string.trim().length() == 0) {
            ToastUtils.showLong(R.string.plv_chat_toast_send_text_empty);
            return;
        }
        this.etSendMessage.setText("");
        IPLVLCLandscapeMessageSender.OnSendMessageListener onSendMessageListener = this.sendMessageListener;
        if (onSendMessageListener != null) {
            onSendMessageListener.onSend(string);
        }
        KeyboardUtils.hideSoftInput(this.etSendMessage);
        hide();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender
    public void dismiss() {
        PopupWindow popupWindow = this.window;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender
    public void hideMessageSender() {
        hide();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender
    public void openMessageSender() {
        this.window.showAtLocation(this.anchor, 17, 0, 0);
        this.llSendMessage.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCLandscapeMessageSendPanel.2
            @Override // java.lang.Runnable
            public void run() {
                KeyboardUtils.showSoftInput(PLVLCLandscapeMessageSendPanel.this.etSendMessage);
            }
        });
    }

    @Override // com.easefun.polyv.livecloudclass.modules.media.danmu.IPLVLCLandscapeMessageSender
    public void setOnSendMessageListener(IPLVLCLandscapeMessageSender.OnSendMessageListener onSendMessageListener) {
        this.sendMessageListener = onSendMessageListener;
    }
}
