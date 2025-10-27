package com.ykb.ebook.extensions;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.util.ColorResourcesKt;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\u0002\u001a\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u00012\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0082\u0010\u001a\u001b\u0010\n\u001a\u00020\u000b*\u00020\u00022\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\u0004\u001a&\u0010\u000e\u001a\u00020\u000b*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u001a\n\u0010\u0014\u001a\u00020\u000b*\u00020\u0002\u001a\u0012\u0010\u0014\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0006\u001a\n\u0010\u0015\u001a\u00020\u000b*\u00020\u0002\u001a$\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\u00022\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u001a\u0012\u0010\u0016\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u0012\u0010\u001d\u001a\u00020\u000b*\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0012\u001a\n\u0010 \u001a\u00020\u000b*\u00020!\u001a\n\u0010\"\u001a\u00020\u000b*\u00020!\u001a\u0014\u0010#\u001a\u00020\u000b*\u00020!2\b\u0010$\u001a\u0004\u0018\u00010%\u001a\n\u0010&\u001a\u00020\u000b*\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u000b*\u00020\u00022\u0006\u0010&\u001a\u00020\u0006\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006'"}, d2 = {PushConstants.INTENT_ACTIVITY_NAME, "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View;", "getActivity", "(Landroid/view/View;)Landroidx/appcompat/app/AppCompatActivity;", "doubleClick", "", "getCompatActivity", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "clickDelay", "", "clickAction", "Lkotlin/Function0;", "dismissWithUpdate", "Landroid/widget/PopupWindow;", "anchorView", "xOffset", "", "yOffset", "gone", "invisible", "screenshot", "Landroid/graphics/Bitmap;", "bitmap", "canvas", "Landroid/graphics/Canvas;", "canvasRecorder", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "setCornerRadius", "Landroid/widget/ImageView;", "radius", "setMainColor", "Landroid/widget/TextView;", "setSecondColor", "setTextIfNotEqual", "charSequence", "", "visible", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nViewExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ViewExtensions.kt\ncom/ykb/ebook/extensions/ViewExtensionsKt\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 3 CanvasRecorderExtensions.kt\ncom/ykb/ebook/extensions/CanvasRecorderExtensionsKt\n+ 4 Canvas.kt\nandroidx/core/graphics/CanvasKt\n*L\n1#1,201:1\n42#2:202\n42#2:203\n18#3,3:204\n21#3:210\n22#3:219\n24#3,3:223\n30#4,3:207\n47#4,8:211\n34#4,3:220\n*S KotlinDebug\n*F\n+ 1 ViewExtensions.kt\ncom/ykb/ebook/extensions/ViewExtensionsKt\n*L\n51#1:202\n60#1:203\n128#1:204,3\n128#1:210\n128#1:219\n128#1:223,3\n128#1:207,3\n129#1:211,8\n128#1:220,3\n*E\n"})
/* loaded from: classes7.dex */
public final class ViewExtensionsKt {
    public static final void clickDelay(@NotNull final View view, @NotNull final Function0<Unit> clickAction) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(clickAction, "clickAction");
        view.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.extensions.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ViewExtensionsKt.clickDelay$lambda$2(view, clickAction, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clickDelay$lambda$2(View this_clickDelay, Function0 clickAction, View view) {
        Intrinsics.checkNotNullParameter(this_clickDelay, "$this_clickDelay");
        Intrinsics.checkNotNullParameter(clickAction, "$clickAction");
        int iHashCode = this_clickDelay.hashCode();
        ViewClickDelay viewClickDelay = ViewClickDelay.INSTANCE;
        if (iHashCode != viewClickDelay.getHash()) {
            viewClickDelay.setHash(this_clickDelay.hashCode());
            viewClickDelay.setLastClickTime(System.currentTimeMillis());
            clickAction.invoke();
        } else if (System.currentTimeMillis() - viewClickDelay.getLastClickTime() > viewClickDelay.getSPACE_TIME()) {
            viewClickDelay.setLastClickTime(System.currentTimeMillis());
            clickAction.invoke();
        }
    }

    public static final void dismissWithUpdate(@NotNull PopupWindow popupWindow, @NotNull View anchorView, int i2, int i3) {
        Intrinsics.checkNotNullParameter(popupWindow, "<this>");
        Intrinsics.checkNotNullParameter(anchorView, "anchorView");
        popupWindow.update(anchorView, i2, i3, -2, -2);
        popupWindow.dismiss();
    }

    public static /* synthetic */ void dismissWithUpdate$default(PopupWindow popupWindow, View view, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = 0;
        }
        dismissWithUpdate(popupWindow, view, i2, i3);
    }

    public static final boolean doubleClick() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ViewClickDelay viewClickDelay = ViewClickDelay.INSTANCE;
        if (jCurrentTimeMillis - viewClickDelay.getLastClickTime() <= viewClickDelay.getSPACE_TIME()) {
            return false;
        }
        viewClickDelay.setLastClickTime(System.currentTimeMillis());
        return true;
    }

    @Nullable
    public static final AppCompatActivity getActivity(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return getCompatActivity(view.getContext());
    }

    private static final AppCompatActivity getCompatActivity(Context context) {
        while (!(context instanceof AppCompatActivity)) {
            if (context instanceof ContextThemeWrapper) {
                context = ((ContextThemeWrapper) context).getBaseContext();
            } else {
                if (!(context instanceof android.view.ContextThemeWrapper)) {
                    return null;
                }
                context = ((android.view.ContextThemeWrapper) context).getBaseContext();
            }
        }
        return (AppCompatActivity) context;
    }

    public static final void gone(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (view.getVisibility() != 8) {
            view.setVisibility(8);
        }
    }

    public static final void invisible(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (view.getVisibility() != 4) {
            view.setVisibility(4);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x004b, code lost:
    
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004f, code lost:
    
        throw r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void screenshot(@org.jetbrains.annotations.NotNull android.view.View r5, @org.jetbrains.annotations.NotNull com.ykb.ebook.page.canvans_recorder.CanvasRecorder r6) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "canvasRecorder"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            int r0 = r5.getWidth()
            if (r0 <= 0) goto L55
            int r0 = r5.getHeight()
            if (r0 <= 0) goto L55
            int r0 = r5.getWidth()
            int r1 = r5.getHeight()
            android.graphics.Canvas r0 = r6.beginRecording(r0, r1)     // Catch: java.lang.Throwable -> L50
            int r1 = r0.save()     // Catch: java.lang.Throwable -> L50
            int r2 = r5.getScrollX()     // Catch: java.lang.Throwable -> L4b
            float r2 = (float) r2     // Catch: java.lang.Throwable -> L4b
            float r2 = -r2
            int r3 = r5.getScrollY()     // Catch: java.lang.Throwable -> L4b
            float r3 = (float) r3     // Catch: java.lang.Throwable -> L4b
            float r3 = -r3
            int r4 = r0.save()     // Catch: java.lang.Throwable -> L4b
            r0.translate(r2, r3)     // Catch: java.lang.Throwable -> L4b
            r5.draw(r0)     // Catch: java.lang.Throwable -> L46
            r0.restoreToCount(r4)     // Catch: java.lang.Throwable -> L4b
            r0.restoreToCount(r1)     // Catch: java.lang.Throwable -> L50
            r6.endRecording()
            goto L55
        L46:
            r5 = move-exception
            r0.restoreToCount(r4)     // Catch: java.lang.Throwable -> L4b
            throw r5     // Catch: java.lang.Throwable -> L4b
        L4b:
            r5 = move-exception
            r0.restoreToCount(r1)     // Catch: java.lang.Throwable -> L50
            throw r5     // Catch: java.lang.Throwable -> L50
        L50:
            r5 = move-exception
            r6.endRecording()
            throw r5
        L55:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.extensions.ViewExtensionsKt.screenshot(android.view.View, com.ykb.ebook.page.canvans_recorder.CanvasRecorder):void");
    }

    public static /* synthetic */ Bitmap screenshot$default(View view, Bitmap bitmap, Canvas canvas, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            bitmap = null;
        }
        if ((i2 & 2) != 0) {
            canvas = null;
        }
        return screenshot(view, bitmap, canvas);
    }

    public static final void setCornerRadius(@NotNull final ImageView imageView, final int i2) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        imageView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.ykb.ebook.extensions.ViewExtensionsKt.setCornerRadius.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(@NotNull View view, @NotNull Outline outline) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), TypedValue.applyDimension(1, i2 * 1.0f, imageView.getContext().getResources().getDisplayMetrics()));
            }
        });
        imageView.setClipToOutline(true);
    }

    public static final void setMainColor(@NotNull TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
    }

    public static final void setSecondColor(@NotNull TextView textView) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
    }

    public static final void setTextIfNotEqual(@NotNull TextView textView, @Nullable CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        if (Intrinsics.areEqual(textView.getText(), charSequence)) {
            return;
        }
        textView.setText(charSequence);
    }

    public static final void visible(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (view.getVisibility() != 0) {
            view.setVisibility(0);
        }
    }

    public static final void gone(@NotNull View view, boolean z2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (z2) {
            gone(view);
        } else {
            view.setVisibility(0);
        }
    }

    public static final void visible(@NotNull View view, boolean z2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (z2 && view.getVisibility() != 0) {
            view.setVisibility(0);
        } else {
            if (z2 || view.getVisibility() != 0) {
                return;
            }
            view.setVisibility(4);
        }
    }

    @Nullable
    public static final Bitmap screenshot(@NotNull View view, @Nullable Bitmap bitmap, @Nullable Canvas canvas) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (view.getWidth() <= 0 || view.getHeight() <= 0) {
            return null;
        }
        if (bitmap != null && bitmap.getWidth() == view.getWidth() && bitmap.getHeight() == view.getHeight()) {
            bitmap.eraseColor(0);
        } else {
            if (bitmap != null) {
                bitmap.recycle();
            }
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        }
        if (canvas == null) {
            canvas = new Canvas();
        }
        canvas.setBitmap(bitmap);
        canvas.save();
        canvas.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(canvas);
        canvas.restore();
        canvas.setBitmap(null);
        bitmap.prepareToDraw();
        return bitmap;
    }
}
