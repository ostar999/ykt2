package com.ykb.ebook.popup;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.d;
import com.ykb.ebook.common_interface.ActivityAction;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.common_interface.ClickAction;
import com.ykb.ebook.common_interface.HandlerAction;
import com.ykb.ebook.common_interface.KeyboardAction;
import com.ykb.ebook.common_interface.ResourcesAction;
import com.ykb.ebook.popup.BasicPopupWindow;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u001b\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u0007:\f23456789:;<=B\r\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\rH\u0016J\u0012\u0010\u0015\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0011H\u0016J\b\u0010\u0016\u001a\u00020\u0013H\u0016J%\u0010\u0017\u001a\u0004\u0018\u0001H\u0018\"\n\b\u0000\u0010\u0018*\u0004\u0018\u00010\u00192\b\b\u0001\u0010\u001a\u001a\u00020\u001bH\u0016¢\u0006\u0002\u0010\u001cJ\b\u0010\u001d\u001a\u00020\tH\u0016J\b\u0010\u001e\u001a\u00020\u0013H\u0016J\u0012\u0010\u001f\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\rH\u0016J\u0012\u0010 \u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020#H\u0002J\u0012\u0010$\u001a\u00020\u00132\b\b\u0001\u0010%\u001a\u00020#H\u0016J\u001a\u0010&\u001a\u00020\u00132\u0010\u0010'\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\fH\u0002J\u001a\u0010(\u001a\u00020\u00132\u0010\u0010'\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\fH\u0002J*\u0010)\u001a\u00020\u00132\b\u0010*\u001a\u0004\u0018\u00010\u00192\u0006\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020\u001b2\u0006\u0010-\u001a\u00020\u001bH\u0016J*\u0010.\u001a\u00020\u00132\b\u0010/\u001a\u0004\u0018\u00010\u00192\u0006\u0010-\u001a\u00020\u001b2\u0006\u00100\u001a\u00020\u001b2\u0006\u00101\u001a\u00020\u001bH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow;", "Landroid/widget/PopupWindow;", "Lcom/ykb/ebook/common_interface/ActivityAction;", "Lcom/ykb/ebook/common_interface/HandlerAction;", "Lcom/ykb/ebook/common_interface/ClickAction;", "Lcom/ykb/ebook/common_interface/AnimAction;", "Lcom/ykb/ebook/common_interface/KeyboardAction;", "Landroid/widget/PopupWindow$OnDismissListener;", d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "dismissListeners", "", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnDismissListener;", "popupBackground", "Lcom/ykb/ebook/popup/BasicPopupWindow$PopupBackground;", "showListeners", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;", "addOnDismissListener", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "addOnShowListener", "dismiss", "findViewById", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Landroid/view/View;", "id", "", "(I)Landroid/view/View;", "getContext", "onDismiss", "removeOnDismissListener", "removeOnShowListener", "setActivityAlpha", "alpha", "", "setBackgroundDimAmount", "dimAmount", "setOnDismissListeners", "listeners", "setOnShowListeners", "showAsDropDown", "anchor", "xOff", "yOff", "gravity", "showAtLocation", "parent", "x", "y", "Builder", "DismissListenerWrapper", "OnClickListener", "OnCreateListener", "OnDismissListener", "OnShowListener", "PopupBackground", "PopupWindowLifecycle", "ShowPostAtTimeWrapper", "ShowPostDelayedWrapper", "ShowPostWrapper", "ViewClickWrapper", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public class BasicPopupWindow extends PopupWindow implements ActivityAction, HandlerAction, ClickAction, AnimAction, KeyboardAction, PopupWindow.OnDismissListener {

    @NotNull
    private final Context context;

    @Nullable
    private List<OnDismissListener> dismissListeners;

    @Nullable
    private PopupBackground popupBackground;

    @Nullable
    private List<OnShowListener> showListeners;

    @Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\r\n\u0002\b\u0018\b\u0016\u0018\u0000 p*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00002\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u0001pB\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010*\u001a\u00028\u00002\u0006\u0010+\u001a\u00020\u0016H\u0016¢\u0006\u0002\u0010,J\u0015\u0010-\u001a\u00028\u00002\u0006\u0010+\u001a\u00020#H\u0016¢\u0006\u0002\u0010.J\b\u0010/\u001a\u00020!H\u0016J\u0010\u00100\u001a\u00020!2\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\b\u00101\u001a\u000202H\u0016J%\u00103\u001a\u0004\u0018\u0001H4\"\n\b\u0001\u00104*\u0004\u0018\u00010\u00102\b\b\u0001\u00105\u001a\u00020\nH\u0016¢\u0006\u0002\u00106J\n\u00107\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u00108\u001a\u00020\u0007H\u0016J\n\u00109\u001a\u0004\u0018\u00010!H\u0016J\b\u0010:\u001a\u00020\u001cH\u0016J\b\u0010;\u001a\u00020\u001cH\u0016J\u0010\u0010<\u001a\u0002022\u0006\u0010=\u001a\u00020>H\u0016J\u0018\u0010?\u001a\u0002022\u0006\u0010=\u001a\u00020>2\u0006\u0010@\u001a\u00020AH\u0016J\u0018\u0010B\u001a\u0002022\u0006\u0010=\u001a\u00020>2\u0006\u0010C\u001a\u00020AH\u0016J\u0017\u0010D\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\nH\u0016¢\u0006\u0002\u0010EJ!\u0010F\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\n2\b\u0010G\u001a\u0004\u0018\u00010HH\u0016¢\u0006\u0002\u0010IJ!\u0010F\u001a\u00028\u00002\b\b\u0001\u0010J\u001a\u00020\n2\b\b\u0001\u0010K\u001a\u00020\nH\u0016¢\u0006\u0002\u0010LJ\u0017\u0010M\u001a\u00028\u00002\b\b\u0001\u0010N\u001a\u00020\fH\u0016¢\u0006\u0002\u0010OJ\u0017\u0010P\u001a\u00028\u00002\b\u0010Q\u001a\u0004\u0018\u00010\u0010H\u0016¢\u0006\u0002\u0010RJ\u0017\u0010P\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\nH\u0016¢\u0006\u0002\u0010EJ\u0015\u0010S\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u001cH\u0016¢\u0006\u0002\u0010TJ\u0015\u0010U\u001a\u00028\u00002\u0006\u0010\u001d\u001a\u00020\nH\u0016¢\u0006\u0002\u0010EJ\u0015\u0010V\u001a\u00028\u00002\u0006\u0010\u001e\u001a\u00020\nH\u0016¢\u0006\u0002\u0010EJ!\u0010W\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\n2\b\u0010X\u001a\u0004\u0018\u00010YH\u0016¢\u0006\u0002\u0010ZJ!\u0010W\u001a\u00028\u00002\b\b\u0001\u0010J\u001a\u00020\n2\b\b\u0001\u0010[\u001a\u00020\nH\u0016¢\u0006\u0002\u0010LJ!\u0010\\\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\n2\b\u0010G\u001a\u0004\u0018\u00010HH\u0016¢\u0006\u0002\u0010IJ!\u0010\\\u001a\u00028\u00002\b\b\u0001\u0010J\u001a\u00020\n2\b\b\u0001\u0010K\u001a\u00020\nH\u0016¢\u0006\u0002\u0010LJ'\u0010]\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\n2\u000e\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010^J\u0015\u0010_\u001a\u00028\u00002\u0006\u0010+\u001a\u00020\u0013H\u0016¢\u0006\u0002\u0010`J\u0015\u0010a\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020\u001cH\u0016¢\u0006\u0002\u0010TJ!\u0010b\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\n2\b\u0010X\u001a\u0004\u0018\u00010YH\u0016¢\u0006\u0002\u0010ZJ!\u0010b\u001a\u00028\u00002\b\b\u0001\u0010J\u001a\u00020\n2\b\b\u0001\u0010[\u001a\u00020\nH\u0016¢\u0006\u0002\u0010LJ!\u0010c\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\n2\b\b\u0001\u0010d\u001a\u00020\nH\u0016¢\u0006\u0002\u0010LJ\u0015\u0010e\u001a\u00028\u00002\u0006\u0010&\u001a\u00020\u001cH\u0016¢\u0006\u0002\u0010TJ\u001f\u0010f\u001a\u00028\u00002\b\b\u0001\u00105\u001a\u00020\n2\u0006\u0010g\u001a\u00020\nH\u0016¢\u0006\u0002\u0010LJ\u0015\u0010h\u001a\u00028\u00002\u0006\u0010'\u001a\u00020\nH\u0016¢\u0006\u0002\u0010EJ\u0015\u0010i\u001a\u00028\u00002\u0006\u0010j\u001a\u00020\nH\u0016¢\u0006\u0002\u0010EJ\u0015\u0010k\u001a\u00028\u00002\u0006\u0010j\u001a\u00020\nH\u0016¢\u0006\u0002\u0010EJ\u0012\u0010l\u001a\u0002022\b\u0010m\u001a\u0004\u0018\u00010\u0010H\u0016J\u0012\u0010n\u001a\u0002022\b\u0010o\u001a\u0004\u0018\u00010\u0010H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010#0\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u001a\u001a\u0004\b$\u0010\u0018R\u000e\u0010&\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006q"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "B", "Lcom/ykb/ebook/common_interface/ActivityAction;", "Lcom/ykb/ebook/common_interface/ResourcesAction;", "Lcom/ykb/ebook/common_interface/ClickAction;", "Lcom/ykb/ebook/common_interface/KeyboardAction;", d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "animStyle", "", "backgroundDimAmount", "", "clickArray", "Landroid/util/SparseArray;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnClickListener;", "Landroid/view/View;", "contentView", "createListener", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnCreateListener;", "dismissListeners", "", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnDismissListener;", "getDismissListeners", "()Ljava/util/List;", "dismissListeners$delegate", "Lkotlin/Lazy;", "focusable", "", "gravity", "height", "outsideTouchable", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "showListeners", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;", "getShowListeners", "showListeners$delegate", "touchable", "width", "xOffset", "yOffset", "addOnDismissListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "(Lcom/ykb/ebook/popup/BasicPopupWindow$OnDismissListener;)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "addOnShowListener", "(Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "create", "createPopupWindow", "dismiss", "", "findViewById", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "id", "(I)Landroid/view/View;", "getContentView", "getContext", "getPopupWindow", "isCreated", "isShowing", "post", "runnable", "Ljava/lang/Runnable;", "postAtTime", "uptimeMillis", "", "postDelayed", "delayMillis", "setAnimStyle", "(I)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "setBackground", "drawable", "Landroid/graphics/drawable/Drawable;", "(ILandroid/graphics/drawable/Drawable;)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "viewId", "drawableId", "(II)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "setBackgroundDimAmount", "dimAmount", "(F)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "setContentView", "view", "(Landroid/view/View;)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "setFocusable", "(Z)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "setGravity", "setHeight", "setHint", "text", "", "(ILjava/lang/CharSequence;)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "stringId", "setImageDrawable", "setOnClickListener", "(ILcom/ykb/ebook/popup/BasicPopupWindow$OnClickListener;)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "setOnCreateListener", "(Lcom/ykb/ebook/popup/BasicPopupWindow$OnCreateListener;)Lcom/ykb/ebook/popup/BasicPopupWindow$Builder;", "setOutsideTouchable", "setText", "setTextColor", "color", "setTouchable", "setVisibility", "visibility", "setWidth", "setXOffset", "offset", "setYOffset", "showAsDropDown", "anchor", "showAtLocation", "parent", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static class Builder<B extends Builder<B>> implements ActivityAction, ResourcesAction, ClickAction, KeyboardAction {
        private static final int DEFAULT_ANCHORED_GRAVITY = 8388659;
        private int animStyle;
        private float backgroundDimAmount;

        @Nullable
        private SparseArray<OnClickListener<View>> clickArray;

        @Nullable
        private View contentView;

        @NotNull
        private final Context context;

        @Nullable
        private OnCreateListener createListener;

        /* renamed from: dismissListeners$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy dismissListeners;
        private boolean focusable;
        private int gravity;
        private int height;
        private boolean outsideTouchable;

        @Nullable
        private BasicPopupWindow popupWindow;

        /* renamed from: showListeners$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy showListeners;
        private boolean touchable;
        private int width;
        private int xOffset;
        private int yOffset;

        public Builder(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.context = context;
            this.animStyle = -1;
            this.width = -2;
            this.height = -2;
            this.gravity = 8388659;
            this.touchable = true;
            this.focusable = true;
            this.showListeners = LazyKt__LazyJVMKt.lazy(new Function0<ArrayList<OnShowListener>>() { // from class: com.ykb.ebook.popup.BasicPopupWindow$Builder$showListeners$2
                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final ArrayList<BasicPopupWindow.OnShowListener> invoke() {
                    return new ArrayList<>();
                }
            });
            this.dismissListeners = LazyKt__LazyJVMKt.lazy(new Function0<ArrayList<OnDismissListener>>() { // from class: com.ykb.ebook.popup.BasicPopupWindow$Builder$dismissListeners$2
                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final ArrayList<BasicPopupWindow.OnDismissListener> invoke() {
                    return new ArrayList<>();
                }
            });
        }

        private final List<OnDismissListener> getDismissListeners() {
            return (List) this.dismissListeners.getValue();
        }

        private final List<OnShowListener> getShowListeners() {
            return (List) this.showListeners.getValue();
        }

        @NotNull
        public B addOnDismissListener(@NotNull OnDismissListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            getDismissListeners().add(listener);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B addOnShowListener(@NotNull OnShowListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            getShowListeners().add(listener);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public BasicPopupWindow create() {
            if (this.contentView == null) {
                throw new IllegalArgumentException("are you ok?");
            }
            if (isShowing()) {
                dismiss();
            }
            if (this.gravity == 8388659) {
                this.gravity = 17;
            }
            int anim_left = -1;
            if (this.animStyle == -1) {
                int i2 = this.gravity;
                if (i2 == 3) {
                    anim_left = AnimAction.INSTANCE.getANIM_LEFT();
                } else if (i2 == 5) {
                    anim_left = AnimAction.INSTANCE.getANIM_RIGHT();
                } else if (i2 == 48) {
                    anim_left = AnimAction.INSTANCE.getANIM_TOP();
                } else if (i2 == 80) {
                    anim_left = AnimAction.INSTANCE.getANIM_BOTTOM();
                }
                this.animStyle = anim_left;
            }
            BasicPopupWindow basicPopupWindowCreatePopupWindow = createPopupWindow(this.context);
            this.popupWindow = basicPopupWindowCreatePopupWindow;
            Intrinsics.checkNotNull(basicPopupWindowCreatePopupWindow);
            basicPopupWindowCreatePopupWindow.setContentView(this.contentView);
            basicPopupWindowCreatePopupWindow.setWidth(this.width);
            basicPopupWindowCreatePopupWindow.setHeight(this.height);
            basicPopupWindowCreatePopupWindow.setAnimationStyle(this.animStyle);
            basicPopupWindowCreatePopupWindow.setFocusable(this.focusable);
            basicPopupWindowCreatePopupWindow.setTouchable(this.touchable);
            basicPopupWindowCreatePopupWindow.setOutsideTouchable(this.outsideTouchable);
            basicPopupWindowCreatePopupWindow.setBackgroundDrawable(new ColorDrawable(0));
            basicPopupWindowCreatePopupWindow.setOnShowListeners(getShowListeners());
            basicPopupWindowCreatePopupWindow.setOnDismissListeners(getDismissListeners());
            basicPopupWindowCreatePopupWindow.setBackgroundDimAmount(this.backgroundDimAmount);
            SparseArray<OnClickListener<View>> sparseArray = this.clickArray;
            if (sparseArray != null) {
                for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                    View view = this.contentView;
                    Intrinsics.checkNotNull(view);
                    View viewFindViewById = view.findViewById(sparseArray.keyAt(i3));
                    if (viewFindViewById != null) {
                        viewFindViewById.setOnClickListener(new ViewClickWrapper(basicPopupWindowCreatePopupWindow, sparseArray.valueAt(i3)));
                    }
                }
            }
            Activity activity = getActivity();
            if (activity != null) {
                PopupWindowLifecycle.INSTANCE.with(activity, basicPopupWindowCreatePopupWindow);
            }
            OnCreateListener onCreateListener = this.createListener;
            if (onCreateListener != null) {
                onCreateListener.onCreate(basicPopupWindowCreatePopupWindow);
            }
            BasicPopupWindow basicPopupWindow = this.popupWindow;
            Intrinsics.checkNotNull(basicPopupWindow);
            return basicPopupWindow;
        }

        @NotNull
        public BasicPopupWindow createPopupWindow(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return new BasicPopupWindow(context);
        }

        @Override // com.ykb.ebook.common_interface.KeyboardAction
        public void dialogHideKeyboard(@Nullable View view) {
            KeyboardAction.DefaultImpls.dialogHideKeyboard(this, view);
        }

        @Override // com.ykb.ebook.common_interface.KeyboardAction
        public void dialogShowKeyboard(@Nullable View view) {
            KeyboardAction.DefaultImpls.dialogShowKeyboard(this, view);
        }

        @Override // com.ykb.ebook.common_interface.KeyboardAction
        public void dialogToggleSoftInput(@Nullable View view) {
            KeyboardAction.DefaultImpls.dialogToggleSoftInput(this, view);
        }

        public void dismiss() {
            BasicPopupWindow basicPopupWindow;
            Activity activity = getActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed() || (basicPopupWindow = this.popupWindow) == null) {
                return;
            }
            basicPopupWindow.dismiss();
        }

        @Override // com.ykb.ebook.common_interface.ClickAction
        @Nullable
        public <V extends View> V findViewById(@IdRes int id) {
            View view = this.contentView;
            if (view == null) {
                throw new IllegalStateException("are you ok?");
            }
            Intrinsics.checkNotNull(view);
            return (V) view.findViewById(id);
        }

        @Override // com.ykb.ebook.common_interface.ActivityAction
        @Nullable
        public Activity getActivity() {
            return ActivityAction.DefaultImpls.getActivity(this);
        }

        @Override // com.ykb.ebook.common_interface.ResourcesAction
        @ColorInt
        public int getColor(@ColorRes int i2) {
            return ResourcesAction.DefaultImpls.getColor(this, i2);
        }

        @Nullable
        public View getContentView() {
            return this.contentView;
        }

        @Override // com.ykb.ebook.common_interface.ActivityAction, com.ykb.ebook.common_interface.ResourcesAction
        @NotNull
        public Context getContext() {
            return this.context;
        }

        @Override // com.ykb.ebook.common_interface.ResourcesAction
        @Nullable
        public Drawable getDrawable(@DrawableRes int i2) {
            return ResourcesAction.DefaultImpls.getDrawable(this, i2);
        }

        @Nullable
        public BasicPopupWindow getPopupWindow() {
            return this.popupWindow;
        }

        @Override // com.ykb.ebook.common_interface.ResourcesAction
        @NotNull
        public Resources getResources() {
            return ResourcesAction.DefaultImpls.getResources(this);
        }

        @Override // com.ykb.ebook.common_interface.ResourcesAction
        @Nullable
        public String getString(@StringRes int i2) {
            return ResourcesAction.DefaultImpls.getString(this, i2);
        }

        @Override // com.ykb.ebook.common_interface.ResourcesAction
        public <S> S getSystemService(@NotNull Class<S> cls) {
            return (S) ResourcesAction.DefaultImpls.getSystemService(this, cls);
        }

        public boolean isCreated() {
            return this.popupWindow != null;
        }

        public boolean isShowing() {
            if (isCreated()) {
                BasicPopupWindow basicPopupWindow = this.popupWindow;
                Intrinsics.checkNotNull(basicPopupWindow);
                if (basicPopupWindow.isShowing()) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            ClickAction.DefaultImpls.onClick(this, view);
        }

        public void post(@NotNull Runnable runnable) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            if (!isShowing()) {
                addOnShowListener(new ShowPostWrapper(runnable));
                return;
            }
            BasicPopupWindow basicPopupWindow = this.popupWindow;
            Intrinsics.checkNotNull(basicPopupWindow);
            basicPopupWindow.post(runnable);
        }

        public void postAtTime(@NotNull Runnable runnable, long uptimeMillis) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            if (!isShowing()) {
                addOnShowListener(new ShowPostAtTimeWrapper(runnable, uptimeMillis));
                return;
            }
            BasicPopupWindow basicPopupWindow = this.popupWindow;
            Intrinsics.checkNotNull(basicPopupWindow);
            basicPopupWindow.postAtTime(runnable, uptimeMillis);
        }

        public void postDelayed(@NotNull Runnable runnable, long delayMillis) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            if (!isShowing()) {
                addOnShowListener(new ShowPostDelayedWrapper(runnable, delayMillis));
                return;
            }
            BasicPopupWindow basicPopupWindow = this.popupWindow;
            Intrinsics.checkNotNull(basicPopupWindow);
            basicPopupWindow.postDelayed(runnable, delayMillis);
        }

        @NotNull
        public B setAnimStyle(@StyleRes int id) {
            BasicPopupWindow basicPopupWindow;
            this.animStyle = id;
            if (isCreated() && (basicPopupWindow = this.popupWindow) != null) {
                basicPopupWindow.setAnimationStyle(id);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setBackground(@IdRes int viewId, @DrawableRes int drawableId) {
            return (B) setBackground(viewId, ContextCompat.getDrawable(this.context, drawableId));
        }

        @NotNull
        public B setBackgroundDimAmount(@FloatRange(from = 0.0d, to = 1.0d) float dimAmount) {
            this.backgroundDimAmount = dimAmount;
            if (isCreated()) {
                BasicPopupWindow basicPopupWindow = this.popupWindow;
                Intrinsics.checkNotNull(basicPopupWindow);
                basicPopupWindow.setBackgroundDimAmount(dimAmount);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setContentView(@LayoutRes int id) {
            return (B) setContentView(LayoutInflater.from(this.context).inflate(id, (ViewGroup) new FrameLayout(this.context), false));
        }

        @NotNull
        public B setFocusable(boolean focusable) {
            this.focusable = focusable;
            if (isCreated()) {
                BasicPopupWindow basicPopupWindow = this.popupWindow;
                Intrinsics.checkNotNull(basicPopupWindow);
                basicPopupWindow.setFocusable(focusable);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setGravity(int gravity) {
            this.gravity = Gravity.getAbsoluteGravity(gravity, getResources().getConfiguration().getLayoutDirection());
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setHeight(int height) {
            this.height = height;
            if (isCreated()) {
                BasicPopupWindow basicPopupWindow = this.popupWindow;
                Intrinsics.checkNotNull(basicPopupWindow);
                basicPopupWindow.setHeight(height);
                Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
                return this;
            }
            View view = this.contentView;
            ViewGroup.LayoutParams layoutParams = view != null ? view.getLayoutParams() : null;
            if (layoutParams != null) {
                layoutParams.height = height;
                View view2 = this.contentView;
                if (view2 != null) {
                    view2.setLayoutParams(layoutParams);
                }
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setHint(@IdRes int viewId, @StringRes int stringId) {
            return (B) setHint(viewId, getString(stringId));
        }

        @NotNull
        public B setImageDrawable(@IdRes int viewId, @DrawableRes int drawableId) {
            return (B) setBackground(viewId, ContextCompat.getDrawable(this.context, drawableId));
        }

        @Override // com.ykb.ebook.common_interface.ClickAction
        public void setOnClickListener(@Nullable View.OnClickListener onClickListener, @IdRes @NotNull int... iArr) {
            ClickAction.DefaultImpls.setOnClickListener(this, onClickListener, iArr);
        }

        @NotNull
        public B setOnCreateListener(@NotNull OnCreateListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.createListener = listener;
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setOutsideTouchable(boolean outsideTouchable) {
            this.outsideTouchable = outsideTouchable;
            if (isCreated()) {
                BasicPopupWindow basicPopupWindow = this.popupWindow;
                Intrinsics.checkNotNull(basicPopupWindow);
                basicPopupWindow.setOutsideTouchable(outsideTouchable);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setText(@IdRes int viewId, @StringRes int stringId) {
            return (B) setText(viewId, getString(stringId));
        }

        @NotNull
        public B setTextColor(@IdRes int id, @ColorInt int color) {
            TextView textView = (TextView) findViewById(id);
            if (textView != null) {
                textView.setTextColor(color);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setTouchable(boolean touchable) {
            this.touchable = touchable;
            if (isCreated()) {
                BasicPopupWindow basicPopupWindow = this.popupWindow;
                Intrinsics.checkNotNull(basicPopupWindow);
                basicPopupWindow.setTouchable(touchable);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setVisibility(@IdRes int id, int visibility) {
            View viewFindViewById = findViewById(id);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(visibility);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setWidth(int width) {
            this.width = width;
            if (isCreated()) {
                BasicPopupWindow basicPopupWindow = this.popupWindow;
                Intrinsics.checkNotNull(basicPopupWindow);
                basicPopupWindow.setWidth(width);
                Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
                return this;
            }
            View view = this.contentView;
            ViewGroup.LayoutParams layoutParams = view != null ? view.getLayoutParams() : null;
            if (layoutParams != null) {
                layoutParams.width = width;
                View view2 = this.contentView;
                if (view2 != null) {
                    view2.setLayoutParams(layoutParams);
                }
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setXOffset(int offset) {
            this.xOffset = offset;
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setYOffset(int offset) {
            this.yOffset = offset;
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        public void showAsDropDown(@Nullable View anchor) {
            Activity activity = getActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (!isCreated()) {
                create();
            }
            BasicPopupWindow basicPopupWindow = this.popupWindow;
            if (basicPopupWindow != null) {
                basicPopupWindow.showAsDropDown(anchor, this.xOffset, this.yOffset, this.gravity);
            }
        }

        public void showAtLocation(@Nullable View parent) {
            Activity activity = getActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (!isCreated()) {
                create();
            }
            BasicPopupWindow basicPopupWindow = this.popupWindow;
            if (basicPopupWindow != null) {
                basicPopupWindow.showAtLocation(parent, this.gravity, this.xOffset, this.yOffset);
            }
        }

        @Override // com.ykb.ebook.common_interface.ActivityAction
        public void startActivity(@NotNull Intent intent) {
            ActivityAction.DefaultImpls.startActivity(this, intent);
        }

        @Override // com.ykb.ebook.common_interface.ResourcesAction
        @Nullable
        public String getString(@StringRes int i2, @NotNull Object... objArr) {
            return ResourcesAction.DefaultImpls.getString(this, i2, objArr);
        }

        @NotNull
        public B setBackground(@IdRes int id, @Nullable Drawable drawable) {
            View viewFindViewById = findViewById(id);
            if (viewFindViewById != null) {
                viewFindViewById.setBackground(drawable);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setHint(@IdRes int id, @Nullable CharSequence text) {
            TextView textView = (TextView) findViewById(id);
            if (textView != null) {
                textView.setHint(text);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @NotNull
        public B setImageDrawable(@IdRes int id, @Nullable Drawable drawable) {
            ImageView imageView = (ImageView) findViewById(id);
            if (imageView != null) {
                imageView.setImageDrawable(drawable);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @Override // com.ykb.ebook.common_interface.ClickAction
        public void setOnClickListener(@Nullable View.OnClickListener onClickListener, @NotNull View... viewArr) {
            ClickAction.DefaultImpls.setOnClickListener(this, onClickListener, viewArr);
        }

        @NotNull
        public B setText(@IdRes int id, @Nullable CharSequence text) {
            TextView textView = (TextView) findViewById(id);
            if (textView != null) {
                textView.setText(text);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }

        @Override // com.ykb.ebook.common_interface.ActivityAction
        public void startActivity(@NotNull Class<? extends Activity> cls) {
            ActivityAction.DefaultImpls.startActivity(this, cls);
        }

        @NotNull
        public B setContentView(@Nullable View view) {
            int i2;
            if (view != null) {
                this.contentView = view;
                if (isCreated()) {
                    BasicPopupWindow basicPopupWindow = this.popupWindow;
                    Intrinsics.checkNotNull(basicPopupWindow);
                    basicPopupWindow.setContentView(view);
                    Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
                    return this;
                }
                View view2 = this.contentView;
                Intrinsics.checkNotNull(view2);
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams != null && this.width == -2 && this.height == -2) {
                    setWidth(layoutParams.width);
                    setHeight(layoutParams.height);
                }
                if (this.gravity == 8388659) {
                    if (layoutParams instanceof FrameLayout.LayoutParams) {
                        int i3 = ((FrameLayout.LayoutParams) layoutParams).gravity;
                        if (i3 != -1) {
                            setGravity(i3);
                        }
                    } else if ((layoutParams instanceof LinearLayout.LayoutParams) && (i2 = ((LinearLayout.LayoutParams) layoutParams).gravity) != 0) {
                        setGravity(i2);
                    }
                    if (this.gravity == 0) {
                        setGravity(17);
                    }
                }
                Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
                return this;
            }
            throw new IllegalArgumentException("are you ok?");
        }

        @Override // com.ykb.ebook.common_interface.ClickAction
        public void setOnClickListener(@IdRes @NotNull int... iArr) {
            ClickAction.DefaultImpls.setOnClickListener(this, iArr);
        }

        @Override // com.ykb.ebook.common_interface.ClickAction
        public void setOnClickListener(@NotNull View... viewArr) {
            ClickAction.DefaultImpls.setOnClickListener(this, viewArr);
        }

        @NotNull
        public B setOnClickListener(@IdRes int id, @NotNull OnClickListener<? extends View> listener) {
            BasicPopupWindow basicPopupWindow;
            View viewFindViewById;
            Intrinsics.checkNotNullParameter(listener, "listener");
            if (this.clickArray == null) {
                this.clickArray = new SparseArray<>();
            }
            SparseArray<OnClickListener<View>> sparseArray = this.clickArray;
            Intrinsics.checkNotNull(sparseArray);
            sparseArray.put(id, listener);
            if (isCreated() && (basicPopupWindow = this.popupWindow) != null && (viewFindViewById = basicPopupWindow.findViewById(id)) != null) {
                viewFindViewById.setOnClickListener(new ViewClickWrapper(this.popupWindow, listener));
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.popup.BasicPopupWindow.Builder");
            return this;
        }
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u0003B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$DismissListenerWrapper;", "Ljava/lang/ref/SoftReference;", "Landroid/widget/PopupWindow$OnDismissListener;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnDismissListener;", "referent", "(Landroid/widget/PopupWindow$OnDismissListener;)V", "onDismiss", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DismissListenerWrapper extends SoftReference<PopupWindow.OnDismissListener> implements OnDismissListener {
        public DismissListenerWrapper(@Nullable PopupWindow.OnDismissListener onDismissListener) {
            super(onDismissListener);
        }

        @Override // com.ykb.ebook.popup.BasicPopupWindow.OnDismissListener
        public void onDismiss(@Nullable BasicPopupWindow popupWindow) {
            PopupWindow.OnDismissListener onDismissListener = get();
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }
    }

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u001f\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$OnClickListener;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Landroid/view/View;", "", "onClick", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "view", "(Lcom/ykb/ebook/popup/BasicPopupWindow;Landroid/view/View;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnClickListener<V extends View> {
        void onClick(@Nullable BasicPopupWindow popupWindow, @NotNull V view);
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$OnCreateListener;", "", "onCreate", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnCreateListener {
        void onCreate(@Nullable BasicPopupWindow popupWindow);
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$OnDismissListener;", "", "onDismiss", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnDismissListener {
        void onDismiss(@Nullable BasicPopupWindow popupWindow);
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;", "", "onShow", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnShowListener {
        void onShow(@Nullable BasicPopupWindow popupWindow);
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\u0012\u0010\n\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\u000e\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$PopupBackground;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnDismissListener;", "()V", "alpha", "", "onDismiss", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "onShow", "setAlpha", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class PopupBackground implements OnShowListener, OnDismissListener {
        private float alpha;

        @Override // com.ykb.ebook.popup.BasicPopupWindow.OnDismissListener
        public void onDismiss(@Nullable BasicPopupWindow popupWindow) {
            if (popupWindow != null) {
                popupWindow.setActivityAlpha(1.0f);
            }
        }

        @Override // com.ykb.ebook.popup.BasicPopupWindow.OnShowListener
        public void onShow(@Nullable BasicPopupWindow popupWindow) {
            if (popupWindow != null) {
                popupWindow.setActivityAlpha(this.alpha);
            }
        }

        public final void setAlpha(float alpha) {
            this.alpha = alpha;
        }
    }

    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u0002\u0018\u0000 \u00182\u00020\u00012\u00020\u00022\u00020\u0003:\u0001\u0018B\u0019\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\fH\u0016J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0012\u0010\u0014\u001a\u00020\n2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\u0015\u001a\u00020\n2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\b\u0010\u0016\u001a\u00020\nH\u0002J\b\u0010\u0017\u001a\u00020\nH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$PopupWindowLifecycle;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnDismissListener;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "(Landroid/app/Activity;Lcom/ykb/ebook/popup/BasicPopupWindow;)V", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "onDismiss", "onShow", "registerActivityLifecycleCallbacks", "unregisterActivityLifecycleCallbacks", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class PopupWindowLifecycle implements Application.ActivityLifecycleCallbacks, OnShowListener, OnDismissListener {

        /* renamed from: Companion, reason: from kotlin metadata */
        @NotNull
        public static final Companion INSTANCE = new Companion(null);

        @Nullable
        private Activity activity;

        @Nullable
        private BasicPopupWindow popupWindow;

        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$PopupWindowLifecycle$Companion;", "", "()V", "with", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final void with(@NotNull Activity activity, @Nullable BasicPopupWindow popupWindow) {
                Intrinsics.checkNotNullParameter(activity, "activity");
                new PopupWindowLifecycle(activity, popupWindow);
            }
        }

        public PopupWindowLifecycle(@Nullable Activity activity, @Nullable BasicPopupWindow basicPopupWindow) {
            this.activity = activity;
            this.popupWindow = basicPopupWindow;
            if (basicPopupWindow != null) {
                basicPopupWindow.addOnShowListener(this);
            }
            BasicPopupWindow basicPopupWindow2 = this.popupWindow;
            if (basicPopupWindow2 != null) {
                basicPopupWindow2.addOnDismissListener(this);
            }
        }

        private final void registerActivityLifecycleCallbacks() {
            Activity activity = this.activity;
            if (activity != null) {
                if (Build.VERSION.SDK_INT >= 29) {
                    activity.registerActivityLifecycleCallbacks(this);
                } else {
                    activity.getApplication().registerActivityLifecycleCallbacks(this);
                }
            }
        }

        private final void unregisterActivityLifecycleCallbacks() {
            Activity activity = this.activity;
            if (activity != null) {
                if (Build.VERSION.SDK_INT >= 29) {
                    activity.unregisterActivityLifecycleCallbacks(this);
                } else {
                    activity.getApplication().unregisterActivityLifecycleCallbacks(this);
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(@NotNull Activity activity, @Nullable Bundle savedInstanceState) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(@NotNull Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            if (this.activity != activity) {
                return;
            }
            unregisterActivityLifecycleCallbacks();
            this.activity = null;
            BasicPopupWindow basicPopupWindow = this.popupWindow;
            if (basicPopupWindow == null) {
                return;
            }
            Intrinsics.checkNotNull(basicPopupWindow);
            basicPopupWindow.removeOnShowListener(this);
            BasicPopupWindow basicPopupWindow2 = this.popupWindow;
            Intrinsics.checkNotNull(basicPopupWindow2);
            basicPopupWindow2.removeOnDismissListener(this);
            BasicPopupWindow basicPopupWindow3 = this.popupWindow;
            Intrinsics.checkNotNull(basicPopupWindow3);
            if (basicPopupWindow3.isShowing()) {
                BasicPopupWindow basicPopupWindow4 = this.popupWindow;
                Intrinsics.checkNotNull(basicPopupWindow4);
                basicPopupWindow4.dismiss();
            }
            this.popupWindow = null;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(@NotNull Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(@NotNull Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(outState, "outState");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(@NotNull Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(@NotNull Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // com.ykb.ebook.popup.BasicPopupWindow.OnDismissListener
        public void onDismiss(@Nullable BasicPopupWindow popupWindow) {
            this.popupWindow = null;
            unregisterActivityLifecycleCallbacks();
        }

        @Override // com.ykb.ebook.popup.BasicPopupWindow.OnShowListener
        public void onShow(@Nullable BasicPopupWindow popupWindow) {
            this.popupWindow = popupWindow;
            registerActivityLifecycleCallbacks();
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$ShowPostAtTimeWrapper;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;", "runnable", "Ljava/lang/Runnable;", "uptimeMillis", "", "(Ljava/lang/Runnable;J)V", "onShow", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ShowPostAtTimeWrapper implements OnShowListener {

        @NotNull
        private final Runnable runnable;
        private final long uptimeMillis;

        public ShowPostAtTimeWrapper(@NotNull Runnable runnable, long j2) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            this.runnable = runnable;
            this.uptimeMillis = j2;
        }

        @Override // com.ykb.ebook.popup.BasicPopupWindow.OnShowListener
        public void onShow(@Nullable BasicPopupWindow popupWindow) {
            if (popupWindow != null) {
                popupWindow.removeOnShowListener(this);
            }
            if (popupWindow != null) {
                popupWindow.postAtTime(this.runnable, this.uptimeMillis);
            }
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$ShowPostDelayedWrapper;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;", "runnable", "Ljava/lang/Runnable;", "delayMillis", "", "(Ljava/lang/Runnable;J)V", "onShow", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ShowPostDelayedWrapper implements OnShowListener {
        private final long delayMillis;

        @NotNull
        private final Runnable runnable;

        public ShowPostDelayedWrapper(@NotNull Runnable runnable, long j2) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            this.runnable = runnable;
            this.delayMillis = j2;
        }

        @Override // com.ykb.ebook.popup.BasicPopupWindow.OnShowListener
        public void onShow(@Nullable BasicPopupWindow popupWindow) {
            if (popupWindow != null) {
                popupWindow.removeOnShowListener(this);
            }
            if (popupWindow != null) {
                popupWindow.postDelayed(this.runnable, this.delayMillis);
            }
        }
    }

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$ShowPostWrapper;", "Lcom/ykb/ebook/popup/BasicPopupWindow$OnShowListener;", "runnable", "Ljava/lang/Runnable;", "(Ljava/lang/Runnable;)V", "getRunnable", "()Ljava/lang/Runnable;", "onShow", "", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ShowPostWrapper implements OnShowListener {

        @NotNull
        private final Runnable runnable;

        public ShowPostWrapper(@NotNull Runnable runnable) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            this.runnable = runnable;
        }

        @NotNull
        public final Runnable getRunnable() {
            return this.runnable;
        }

        @Override // com.ykb.ebook.popup.BasicPopupWindow.OnShowListener
        public void onShow(@Nullable BasicPopupWindow popupWindow) {
            if (popupWindow != null) {
                popupWindow.removeOnShowListener(this);
            }
            if (popupWindow != null) {
                popupWindow.post(this.runnable);
            }
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006H\u0016R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/popup/BasicPopupWindow$ViewClickWrapper;", "Landroid/view/View$OnClickListener;", "popupWindow", "Lcom/ykb/ebook/popup/BasicPopupWindow;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/ykb/ebook/popup/BasicPopupWindow$OnClickListener;", "Landroid/view/View;", "(Lcom/ykb/ebook/popup/BasicPopupWindow;Lcom/ykb/ebook/popup/BasicPopupWindow$OnClickListener;)V", "onClick", "", "view", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ViewClickWrapper implements View.OnClickListener {

        @Nullable
        private final OnClickListener<View> listener;

        @Nullable
        private final BasicPopupWindow popupWindow;

        public ViewClickWrapper(@Nullable BasicPopupWindow basicPopupWindow, @Nullable OnClickListener<View> onClickListener) {
            this.popupWindow = basicPopupWindow;
            this.listener = onClickListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            OnClickListener<View> onClickListener = this.listener;
            if (onClickListener != null) {
                onClickListener.onClick(this.popupWindow, view);
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BasicPopupWindow(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setActivityAlpha(float alpha) {
        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        final WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        Intrinsics.checkNotNullExpressionValue(attributes, "activity.window.attributes");
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(attributes.alpha, alpha);
        Intrinsics.checkNotNullExpressionValue(valueAnimatorOfFloat, "ofFloat(params.alpha, alpha)");
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ykb.ebook.popup.a
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BasicPopupWindow.setActivityAlpha$lambda$0(attributes, activity, valueAnimator);
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setActivityAlpha$lambda$0(WindowManager.LayoutParams params, Activity activity, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(params, "$params");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        if (fFloatValue == params.alpha) {
            return;
        }
        params.alpha = fFloatValue;
        activity.getWindow().setAttributes(params);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setOnDismissListeners(List<OnDismissListener> listeners) {
        super.setOnDismissListener(this);
        this.dismissListeners = listeners;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setOnShowListeners(List<OnShowListener> listeners) {
        this.showListeners = listeners;
    }

    public void addOnDismissListener(@Nullable OnDismissListener listener) {
        if (this.dismissListeners == null) {
            this.dismissListeners = new ArrayList();
            super.setOnDismissListener(this);
        }
        List<OnDismissListener> list = this.dismissListeners;
        Intrinsics.checkNotNull(list);
        list.add(listener);
    }

    public void addOnShowListener(@Nullable OnShowListener listener) {
        if (this.showListeners == null) {
            this.showListeners = new ArrayList();
        }
        List<OnShowListener> list = this.showListeners;
        Intrinsics.checkNotNull(list);
        list.add(listener);
    }

    @Override // com.ykb.ebook.common_interface.KeyboardAction
    public void dialogHideKeyboard(@Nullable View view) {
        KeyboardAction.DefaultImpls.dialogHideKeyboard(this, view);
    }

    @Override // com.ykb.ebook.common_interface.KeyboardAction
    public void dialogShowKeyboard(@Nullable View view) {
        KeyboardAction.DefaultImpls.dialogShowKeyboard(this, view);
    }

    @Override // com.ykb.ebook.common_interface.KeyboardAction
    public void dialogToggleSoftInput(@Nullable View view) {
        KeyboardAction.DefaultImpls.dialogToggleSoftInput(this, view);
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        super.dismiss();
        removeCallbacks();
    }

    @Override // com.ykb.ebook.common_interface.ClickAction
    @Nullable
    public <V extends View> V findViewById(@IdRes int id) {
        return (V) getContentView().findViewById(id);
    }

    @Override // com.ykb.ebook.common_interface.ActivityAction
    @Nullable
    public Activity getActivity() {
        return ActivityAction.DefaultImpls.getActivity(this);
    }

    @Override // com.ykb.ebook.common_interface.ActivityAction, com.ykb.ebook.common_interface.ResourcesAction
    @NotNull
    public Context getContext() {
        return this.context;
    }

    @Override // com.ykb.ebook.common_interface.HandlerAction
    @NotNull
    public Handler getHandler() {
        return HandlerAction.DefaultImpls.getHandler(this);
    }

    @Override // com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
    public void onClick(@NotNull View view) {
        ClickAction.DefaultImpls.onClick(this, view);
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        List<OnDismissListener> list = this.dismissListeners;
        if (list == null) {
            return;
        }
        Intrinsics.checkNotNull(list);
        for (OnDismissListener onDismissListener : list) {
            if (onDismissListener != null) {
                onDismissListener.onDismiss(this);
            }
        }
    }

    @Override // com.ykb.ebook.common_interface.HandlerAction
    public boolean post(@NotNull Runnable runnable) {
        return HandlerAction.DefaultImpls.post(this, runnable);
    }

    @Override // com.ykb.ebook.common_interface.HandlerAction
    public boolean postAtTime(@NotNull Runnable runnable, long j2) {
        return HandlerAction.DefaultImpls.postAtTime(this, runnable, j2);
    }

    @Override // com.ykb.ebook.common_interface.HandlerAction
    public boolean postDelayed(@NotNull Runnable runnable, long j2) {
        return HandlerAction.DefaultImpls.postDelayed(this, runnable, j2);
    }

    @Override // com.ykb.ebook.common_interface.HandlerAction
    public void removeCallbacks() {
        HandlerAction.DefaultImpls.removeCallbacks(this);
    }

    public void removeOnDismissListener(@Nullable OnDismissListener listener) {
        List<OnDismissListener> list = this.dismissListeners;
        if (list != null) {
            list.remove(listener);
        }
    }

    public void removeOnShowListener(@Nullable OnShowListener listener) {
        List<OnShowListener> list = this.showListeners;
        if (list != null) {
            list.remove(listener);
        }
    }

    public void setBackgroundDimAmount(@FloatRange(from = 0.0d, to = 1.0d) float dimAmount) {
        float f2 = 1 - dimAmount;
        if (isShowing()) {
            setActivityAlpha(f2);
        }
        if (this.popupBackground == null) {
            if (!(f2 == 1.0f)) {
                PopupBackground popupBackground = new PopupBackground();
                this.popupBackground = popupBackground;
                addOnShowListener(popupBackground);
                addOnDismissListener(this.popupBackground);
            }
        }
        PopupBackground popupBackground2 = this.popupBackground;
        if (popupBackground2 == null || popupBackground2 == null) {
            return;
        }
        popupBackground2.setAlpha(f2);
    }

    @Override // com.ykb.ebook.common_interface.ClickAction
    public void setOnClickListener(@Nullable View.OnClickListener onClickListener, @IdRes @NotNull int... iArr) {
        ClickAction.DefaultImpls.setOnClickListener(this, onClickListener, iArr);
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(@Nullable View anchor, int xOff, int yOff, int gravity) {
        if (isShowing() || getContentView() == null) {
            return;
        }
        List<OnShowListener> list = this.showListeners;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            for (OnShowListener onShowListener : list) {
                if (onShowListener != null) {
                    onShowListener.onShow(this);
                }
            }
        }
        super.showAsDropDown(anchor, xOff, yOff, gravity);
    }

    @Override // android.widget.PopupWindow
    public void showAtLocation(@Nullable View parent, int gravity, int x2, int y2) {
        if (isShowing() || getContentView() == null) {
            return;
        }
        List<OnShowListener> list = this.showListeners;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            for (OnShowListener onShowListener : list) {
                if (onShowListener != null) {
                    onShowListener.onShow(this);
                }
            }
        }
        super.showAtLocation(parent, gravity, x2, y2);
    }

    @Override // com.ykb.ebook.common_interface.ActivityAction
    public void startActivity(@NotNull Intent intent) {
        ActivityAction.DefaultImpls.startActivity(this, intent);
    }

    @Override // com.ykb.ebook.common_interface.HandlerAction
    public void removeCallbacks(@NotNull Runnable runnable) {
        HandlerAction.DefaultImpls.removeCallbacks(this, runnable);
    }

    @Override // com.ykb.ebook.common_interface.ClickAction
    public void setOnClickListener(@Nullable View.OnClickListener onClickListener, @NotNull View... viewArr) {
        ClickAction.DefaultImpls.setOnClickListener(this, onClickListener, viewArr);
    }

    @Override // com.ykb.ebook.common_interface.ActivityAction
    public void startActivity(@NotNull Class<? extends Activity> cls) {
        ActivityAction.DefaultImpls.startActivity(this, cls);
    }

    @Override // com.ykb.ebook.common_interface.ClickAction
    public void setOnClickListener(@IdRes @NotNull int... iArr) {
        ClickAction.DefaultImpls.setOnClickListener(this, iArr);
    }

    @Override // com.ykb.ebook.common_interface.ClickAction
    public void setOnClickListener(@NotNull View... viewArr) {
        ClickAction.DefaultImpls.setOnClickListener(this, viewArr);
    }
}
