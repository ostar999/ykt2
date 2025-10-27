package com.psychiatrygarden.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/* loaded from: classes6.dex */
public class ObservableScrollView extends NestedScrollView {
    private final Handler mHandler;
    private OnScrollStatusListener onScrollStatusListener;
    private int scrollDistance;
    private boolean upScroll;

    public interface OnScrollStatusListener {
        void onScrollStop(boolean upScroll, boolean scroll2Bottom, int scrollDistance);

        void onScrolling();

        void scrollToBottom();
    }

    public ObservableScrollView(@NonNull Context context) {
        super(context);
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.widget.ObservableScrollView.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what != 1 || ObservableScrollView.this.onScrollStatusListener == null) {
                    return;
                }
                ObservableScrollView.this.onScrollStatusListener.onScrollStop(ObservableScrollView.this.upScroll, ((Boolean) msg.obj).booleanValue(), ObservableScrollView.this.scrollDistance);
            }
        };
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    public void onScrollChanged(int x2, int scrollY, int oldX, int oldScrollY) {
        super.onScrollChanged(x2, scrollY, oldX, oldScrollY);
        if (scrollY > oldScrollY) {
            this.upScroll = false;
        } else if (scrollY < oldScrollY) {
            this.upScroll = true;
        }
        OnScrollStatusListener onScrollStatusListener = this.onScrollStatusListener;
        if (onScrollStatusListener != null) {
            onScrollStatusListener.onScrolling();
            this.mHandler.removeCallbacksAndMessages(null);
            View childAt = getChildAt(0);
            Message messageObtainMessage = this.mHandler.obtainMessage();
            if (getHeight() + getScrollY() == childAt.getHeight()) {
                this.onScrollStatusListener.scrollToBottom();
            }
            messageObtainMessage.obj = Boolean.valueOf(getHeight() + getScrollY() == childAt.getHeight());
            messageObtainMessage.what = 1;
            this.mHandler.sendMessageDelayed(messageObtainMessage, 200L);
        }
        this.scrollDistance = Math.abs(scrollY - oldScrollY);
    }

    public void setOnScrollStatusListener(OnScrollStatusListener onScrollStatusListener) {
        this.onScrollStatusListener = onScrollStatusListener;
    }

    public ObservableScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.widget.ObservableScrollView.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what != 1 || ObservableScrollView.this.onScrollStatusListener == null) {
                    return;
                }
                ObservableScrollView.this.onScrollStatusListener.onScrollStop(ObservableScrollView.this.upScroll, ((Boolean) msg.obj).booleanValue(), ObservableScrollView.this.scrollDistance);
            }
        };
    }

    public ObservableScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.widget.ObservableScrollView.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what != 1 || ObservableScrollView.this.onScrollStatusListener == null) {
                    return;
                }
                ObservableScrollView.this.onScrollStatusListener.onScrollStop(ObservableScrollView.this.upScroll, ((Boolean) msg.obj).booleanValue(), ObservableScrollView.this.scrollDistance);
            }
        };
    }
}
