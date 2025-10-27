package me.dkzwm.widget.srl.extra;

import android.view.View;
import androidx.annotation.NonNull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes9.dex */
public interface IRefreshView<T extends IIndicator> {
    public static final byte STYLE_DEFAULT = 0;
    public static final byte STYLE_FOLLOW_CENTER = 5;
    public static final byte STYLE_FOLLOW_PIN = 4;
    public static final byte STYLE_FOLLOW_SCALE = 3;
    public static final byte STYLE_PIN = 2;
    public static final byte STYLE_SCALE = 1;
    public static final byte TYPE_FOOTER = 1;
    public static final byte TYPE_HEADER = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshViewStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshViewType {
    }

    int getCustomHeight();

    int getStyle();

    int getType();

    @NonNull
    View getView();

    void onFingerUp(SmoothRefreshLayout smoothRefreshLayout, T t2);

    void onPureScrollPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2);

    void onRefreshBegin(SmoothRefreshLayout smoothRefreshLayout, T t2);

    void onRefreshComplete(SmoothRefreshLayout smoothRefreshLayout, boolean z2);

    void onRefreshPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2);

    void onRefreshPrepare(SmoothRefreshLayout smoothRefreshLayout);

    void onReset(SmoothRefreshLayout smoothRefreshLayout);
}
