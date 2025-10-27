package com.easefun.polyv.livecommon.module.utils;

import android.graphics.Rect;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.easefun.polyv.businesssdk.api.common.player.PolyvBaseVideoView;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;

/* loaded from: classes3.dex */
public class PLVVideoSizeUtils {
    public static int fitVideoRatio(PolyvBaseVideoView baseVideoView) {
        if (baseVideoView == null) {
            return -1;
        }
        int[] videoWH = getVideoWH(baseVideoView);
        if (videoWH[0] >= videoWH[1]) {
            baseVideoView.setAspectRatio(0);
            return 0;
        }
        baseVideoView.setAspectRatio(1);
        return 1;
    }

    public static void fitVideoRatioAndRect(PolyvBaseVideoView baseVideoView, ViewParent viewParent, Rect rect) {
        fitVideoRect(fitVideoRatio(baseVideoView) == 1, viewParent, rect);
    }

    public static void fitVideoRect(boolean isFill, ViewParent viewParent, Rect rect) {
        if (viewParent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) viewParent;
            if (viewGroup.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
                if (isFill || rect == null) {
                    marginLayoutParams.height = -1;
                    marginLayoutParams.width = -1;
                    marginLayoutParams.topMargin = 0;
                    marginLayoutParams.leftMargin = 0;
                } else {
                    int i2 = rect.bottom;
                    int i3 = rect.top;
                    if (i2 > i3) {
                        marginLayoutParams.height = i2 - i3;
                    }
                    int i4 = rect.right;
                    int i5 = rect.left;
                    if (i4 > i5) {
                        marginLayoutParams.width = i4 - i5;
                    }
                    marginLayoutParams.topMargin = i3;
                    marginLayoutParams.leftMargin = i5;
                }
                viewGroup.requestLayout();
                viewGroup.invalidate();
            }
        }
    }

    public static int getVideoDisplayRectHeight(PolyvBaseVideoView baseVideoView, ViewParent viewParent, Rect rect) {
        int i2;
        float f2;
        int[] videoWH = getVideoWH(baseVideoView);
        int i3 = videoWH[0];
        if (i3 == 0 || (i2 = videoWH[1]) == 0) {
            return 0;
        }
        if (i3 < i2) {
            return ((ViewGroup) viewParent).getHeight();
        }
        int i4 = rect.bottom - rect.top;
        int width = ((ViewGroup) viewParent).getWidth();
        if (i4 == 0 || width == 0) {
            return 0;
        }
        int i5 = videoWH[0];
        if (i5 >= width) {
            float f3 = (i5 * 1.0f) / width;
            int i6 = videoWH[1];
            if (f3 <= (i6 * 1.0f) / i4) {
                return i4;
            }
            f2 = (i6 * 1.0f) / f3;
        } else {
            float f4 = (width * 1.0f) / i5;
            int i7 = videoWH[1];
            if (f4 > (i4 * 1.0f) / i7) {
                return i4;
            }
            f2 = i7 * 1.0f * f4;
        }
        return (int) f2;
    }

    public static int[] getVideoWH(PolyvBaseVideoView baseVideoView) {
        IjkMediaPlayer ijkMediaPlayer;
        return (baseVideoView == null || (ijkMediaPlayer = baseVideoView.getIjkMediaPlayer()) == null) ? new int[]{0, 0} : new int[]{ijkMediaPlayer.getVideoWidth(), ijkMediaPlayer.getVideoHeight()};
    }
}
