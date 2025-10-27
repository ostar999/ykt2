package com.psychiatrygarden.aliPlayer.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class TryWatchUnlockView extends LinearLayout {
    private TryWatchActionListener mTryWatchActionListener;

    public interface TryWatchActionListener {
        void replay();

        void unlockCourse();
    }

    public TryWatchUnlockView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_try_watch_unlock, this);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15286c.lambda$init$0(view);
            }
        });
        findViewById(R.id.tv_unlock).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15287c.lambda$init$1(view);
            }
        });
        findViewById(R.id.ll_reply).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15288c.lambda$init$2(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        TryWatchActionListener tryWatchActionListener = this.mTryWatchActionListener;
        if (tryWatchActionListener != null) {
            tryWatchActionListener.unlockCourse();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        TryWatchActionListener tryWatchActionListener = this.mTryWatchActionListener;
        if (tryWatchActionListener != null) {
            tryWatchActionListener.replay();
        }
    }

    public void setTryWatchActionListener(TryWatchActionListener l2) {
        this.mTryWatchActionListener = l2;
    }
}
