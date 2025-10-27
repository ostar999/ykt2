package com.ykb.ebook.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;
import com.ykb.ebook.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t\u001a\u0016\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003¨\u0006\u000e"}, d2 = {"setRefreshTileView", "", "colorMode", "", "refreshLayout", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "refreshHeader", "Lcom/scwang/smartrefresh/layout/header/ClassicsHeader;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "toastPop", "v", "Landroid/view/View;", "flag", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ViewUtilKt {
    public static final void setRefreshTileView(int i2, @NotNull SmartRefreshLayout refreshLayout, @NotNull ClassicsHeader refreshHeader, @Nullable Context context) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
        Intrinsics.checkNotNullParameter(refreshHeader, "refreshHeader");
        refreshLayout.setHeaderTriggerRate(0.0f);
        Intrinsics.checkNotNull(context);
        refreshHeader.setAccentColor(context.getResources().getColor(i2 != 2 ? R.color.color_C2C6CB : R.color.color_575F79));
        refreshHeader.setFinishDuration(0);
        refreshHeader.setTimeFormat(DateUtilKt.getDateFormat());
        ArrowDrawable arrowDrawable = new ArrowDrawable();
        arrowDrawable.setColor(i2 != 2 ? -4012341 : -12235676);
        refreshHeader.setArrowDrawable(context.getResources().getDrawable(R.mipmap.reflash_title_arrow));
        refreshHeader.setArrowDrawable(arrowDrawable);
        refreshLayout.setRefreshHeader(refreshHeader);
    }

    public static final void toastPop(@NotNull View v2, int i2) {
        Intrinsics.checkNotNullParameter(v2, "v");
        ImageView imageView = new ImageView(v2.getContext());
        if (i2 == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        final PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        popupWindow.setAnimationStyle(i2 == 0 ? R.style.popshowzan : R.style.popshowcai);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.ykb.ebook.util.i
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ViewUtilKt.toastPop$lambda$1$lambda$0();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        v2.getLocationOnScreen(new int[2]);
        v2.getLocalVisibleRect(new Rect());
        popupWindow.showAsDropDown(v2, v2.getWidth() / 4, ((-v2.getHeight()) * 2) - 20);
        new Handler().postDelayed(new Runnable() { // from class: com.ykb.ebook.util.j
            @Override // java.lang.Runnable
            public final void run() {
                ViewUtilKt.toastPop$lambda$2(popupWindow);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toastPop$lambda$1$lambda$0() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toastPop$lambda$2(PopupWindow popupWindowFiltrate) {
        Intrinsics.checkNotNullParameter(popupWindowFiltrate, "$popupWindowFiltrate");
        popupWindowFiltrate.dismiss();
    }
}
