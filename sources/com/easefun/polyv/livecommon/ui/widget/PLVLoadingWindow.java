package com.easefun.polyv.livecommon.ui.widget;

import android.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/* loaded from: classes3.dex */
public class PLVLoadingWindow {
    private final AppCompatActivity activity;
    private final View anchor;
    private ProgressBar loadingPb;
    private final PopupWindow window;

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.PLVLoadingWindow$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$Event;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$Event = iArr;
            try {
                iArr[Lifecycle.Event.ON_DESTROY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_PAUSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public PLVLoadingWindow(AppCompatActivity activity) {
        this.activity = activity;
        this.anchor = activity.findViewById(R.id.content);
        PopupWindow popupWindow = new PopupWindow(activity);
        this.window = popupWindow;
        View viewInflate = LayoutInflater.from(activity).inflate(com.easefun.polyv.livecommon.R.layout.plv_loading_window_layout, (ViewGroup) null);
        popupWindow.setContentView(viewInflate);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setWidth(-1);
        popupWindow.setHeight(-1);
        activity.getLifecycle().addObserver(new GenericLifecycleObserver() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVLoadingWindow.1
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                int i2 = AnonymousClass2.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()];
                if (i2 == 1 || i2 == 2) {
                    PLVLoadingWindow.this.window.dismiss();
                }
            }
        });
        initView(viewInflate);
    }

    private void initView(View root) {
        this.loadingPb = (ProgressBar) root.findViewById(com.easefun.polyv.livecommon.R.id.loading_pb);
    }

    public void hide() {
        this.window.dismiss();
    }

    public void show() {
        this.window.showAtLocation(this.anchor, 17, 0, 0);
    }
}
