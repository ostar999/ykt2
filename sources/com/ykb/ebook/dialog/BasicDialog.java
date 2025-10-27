package com.ykb.ebook.dialog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.ykb.ebook.R;
import com.ykb.ebook.common_interface.ActivityAction;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.common_interface.ClickAction;
import com.ykb.ebook.common_interface.HandlerAction;
import com.ykb.ebook.common_interface.KeyboardAction;
import com.ykb.ebook.common_interface.ResourcesAction;
import com.ykb.ebook.dialog.BasicDialog;
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

@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001a\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u00072\u00020\b2\u00020\t2\u00020\n:\u0011ABCDEFGHIJKLMNOPQB\u0017\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\b\u0003\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0012\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0012H\u0016J\u0012\u0010\u001c\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0014H\u0016J\u0012\u0010\u001d\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u001e\u001a\u00020\u001aH\u0016J\n\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010!\u001a\u00020\u000eH\u0016J\b\u0010\"\u001a\u00020\u000eH\u0016J\u0012\u0010#\u001a\u00020\u001a2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\u0012\u0010&\u001a\u00020\u001a2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\u0012\u0010'\u001a\u00020\u001a2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\u0012\u0010(\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0012H\u0016J\u0012\u0010)\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0014H\u0016J\u0012\u0010*\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0018H\u0016J\u0012\u0010+\u001a\u00020\u001a2\b\b\u0001\u0010,\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u001a2\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020\u001a2\u0006\u00102\u001a\u00020\u000eH\u0016J\u0010\u00103\u001a\u00020\u001a2\u0006\u00104\u001a\u00020\u000eH\u0016J\u001a\u00105\u001a\u00020\u001a2\u0010\u0010\u0015\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0018\u00010\u0011H\u0002J\u001a\u00106\u001a\u00020\u001a2\u0010\u0010\u0015\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0014\u0018\u00010\u0011H\u0002J\u0012\u00107\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u000108H\u0016J\u001a\u00109\u001a\u00020\u001a2\u0010\u0010\u0015\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0018\u0018\u00010\u0011H\u0002J\u0010\u0010:\u001a\u00020\u001a2\u0006\u0010;\u001a\u00020\u000eH\u0016J\u0012\u0010<\u001a\u00020\u001a2\b\b\u0001\u0010=\u001a\u00020\u000eH\u0016J\u0010\u0010>\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u000eH\u0016J\u0010\u0010@\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u000eH\u0016R\u0018\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0013\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0014\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00000\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0017\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0018\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006R"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog;", "Landroidx/appcompat/app/AppCompatDialog;", "Lcom/ykb/ebook/common_interface/ResourcesAction;", "Lcom/ykb/ebook/common_interface/ActivityAction;", "Lcom/ykb/ebook/common_interface/HandlerAction;", "Lcom/ykb/ebook/common_interface/ClickAction;", "Lcom/ykb/ebook/common_interface/AnimAction;", "Lcom/ykb/ebook/common_interface/KeyboardAction;", "Landroid/content/DialogInterface$OnShowListener;", "Landroid/content/DialogInterface$OnCancelListener;", "Landroid/content/DialogInterface$OnDismissListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "themeResId", "", "(Landroid/content/Context;I)V", "cancelListeners", "", "Lcom/ykb/ebook/dialog/BasicDialog$OnCancelListener;", "dismissListeners", "Lcom/ykb/ebook/dialog/BasicDialog$OnDismissListener;", "listeners", "Lcom/ykb/ebook/dialog/BasicDialog$ListenersWrapper;", "showListeners", "Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;", "addOnCancelListener", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "addOnDismissListener", "addOnShowListener", "dismiss", "getContentView", "Landroid/view/View;", "getGravity", "getWindowAnimations", "onCancel", "dialog", "Landroid/content/DialogInterface;", "onDismiss", "onShow", "removeOnCancelListener", "removeOnDismissListener", "removeOnShowListener", "setBackgroundDimAmount", "dimAmount", "", "setBackgroundDimEnabled", "enabled", "", "setGravity", "gravity", "setHeight", "height", "setOnCancelListeners", "setOnDismissListeners", "setOnKeyListener", "Lcom/ykb/ebook/dialog/BasicDialog$OnKeyListener;", "setOnShowListeners", "setWidth", "width", "setWindowAnimations", "id", "setXOffset", "offset", "setYOffset", "Builder", "CancelListenerWrapper", "DialogLifecycle", "DismissListenerWrapper", "KeyListenerWrapper", "ListenersWrapper", "OnCancelListener", "OnClickListener", "OnCreateListener", "OnDismissListener", "OnKeyListener", "OnShowListener", "ShowListenerWrapper", "ShowPostAtTimeWrapper", "ShowPostDelayedWrapper", "ShowPostWrapper", "ViewClickWrapper", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public class BasicDialog extends AppCompatDialog implements ResourcesAction, ActivityAction, HandlerAction, ClickAction, AnimAction, KeyboardAction, DialogInterface.OnShowListener, DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    @Nullable
    private List<OnCancelListener> cancelListeners;

    @Nullable
    private List<OnDismissListener> dismissListeners;

    @NotNull
    private final ListenersWrapper<BasicDialog> listeners;

    @Nullable
    private List<OnShowListener> showListeners;

    @Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\r\n\u0002\b\u0015\b\u0016\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00002\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u00101\u001a\u00028\u00002\u0006\u00102\u001a\u00020\u0011H\u0016¢\u0006\u0002\u00103J\u0015\u00104\u001a\u00028\u00002\u0006\u00102\u001a\u00020\"H\u0016¢\u0006\u0002\u00105J\u0015\u00106\u001a\u00028\u00002\u0006\u00102\u001a\u00020*H\u0016¢\u0006\u0002\u00107J\b\u00108\u001a\u00020 H\u0016J\u001a\u00109\u001a\u00020 2\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010-\u001a\u00020\nH\u0014J\b\u0010:\u001a\u00020;H\u0016J%\u0010<\u001a\u0004\u0018\u0001H=\"\n\b\u0001\u0010=*\u0004\u0018\u00010\u001b2\b\b\u0001\u0010>\u001a\u00020\nH\u0016¢\u0006\u0002\u0010?J\n\u0010@\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010A\u001a\u00020\u0007H\u0016J\n\u0010B\u001a\u0004\u0018\u00010 H\u0016J\b\u0010C\u001a\u00020\u000eH\u0016J\b\u0010D\u001a\u00020\u000eH\u0016J\u0010\u0010E\u001a\u00020;2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010H\u001a\u00020;2\u0006\u0010F\u001a\u00020G2\u0006\u0010I\u001a\u00020JH\u0016J\u0018\u0010K\u001a\u00020;2\u0006\u0010F\u001a\u00020G2\u0006\u0010L\u001a\u00020JH\u0016J\u0017\u0010M\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\nH\u0016¢\u0006\u0002\u0010NJ!\u0010O\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\n2\b\u0010P\u001a\u0004\u0018\u00010QH\u0016¢\u0006\u0002\u0010RJ!\u0010O\u001a\u00028\u00002\b\b\u0001\u0010S\u001a\u00020\n2\b\b\u0001\u0010T\u001a\u00020\nH\u0016¢\u0006\u0002\u0010UJ\u0017\u0010V\u001a\u00028\u00002\b\b\u0001\u0010W\u001a\u00020\fH\u0016¢\u0006\u0002\u0010XJ\u0015\u0010Y\u001a\u00028\u00002\u0006\u0010Z\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010[J\u0015\u0010\\\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010[J\u0015\u0010]\u001a\u00028\u00002\u0006\u0010^\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010[J\u0017\u0010_\u001a\u00028\u00002\b\u0010`\u001a\u0004\u0018\u00010\u001bH\u0016¢\u0006\u0002\u0010aJ\u0017\u0010_\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\nH\u0016¢\u0006\u0002\u0010NJ\u0015\u0010b\u001a\u00028\u00002\u0006\u0010%\u001a\u00020\nH\u0016¢\u0006\u0002\u0010NJ\u0015\u0010c\u001a\u00028\u00002\u0006\u0010&\u001a\u00020\nH\u0016¢\u0006\u0002\u0010NJ!\u0010d\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\n2\b\u0010e\u001a\u0004\u0018\u00010fH\u0016¢\u0006\u0002\u0010gJ!\u0010d\u001a\u00028\u00002\b\b\u0001\u0010S\u001a\u00020\n2\b\b\u0001\u0010h\u001a\u00020\nH\u0016¢\u0006\u0002\u0010UJ!\u0010i\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\n2\b\u0010P\u001a\u0004\u0018\u00010QH\u0016¢\u0006\u0002\u0010RJ!\u0010i\u001a\u00028\u00002\b\b\u0001\u0010S\u001a\u00020\n2\b\b\u0001\u0010T\u001a\u00020\nH\u0016¢\u0006\u0002\u0010UJ'\u0010j\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\n2\u000e\u00102\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u001aH\u0016¢\u0006\u0002\u0010kJ\u0015\u0010l\u001a\u00028\u00002\u0006\u00102\u001a\u00020\u001eH\u0016¢\u0006\u0002\u0010mJ\u0015\u0010n\u001a\u00028\u00002\u0006\u00102\u001a\u00020(H\u0016¢\u0006\u0002\u0010oJ!\u0010p\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\n2\b\u0010e\u001a\u0004\u0018\u00010fH\u0016¢\u0006\u0002\u0010gJ!\u0010p\u001a\u00028\u00002\b\b\u0001\u0010S\u001a\u00020\n2\b\b\u0001\u0010h\u001a\u00020\nH\u0016¢\u0006\u0002\u0010UJ!\u0010q\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\n2\b\b\u0001\u0010r\u001a\u00020\nH\u0016¢\u0006\u0002\u0010UJ\u0017\u0010s\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\nH\u0016¢\u0006\u0002\u0010NJ\u001f\u0010t\u001a\u00028\u00002\b\b\u0001\u0010>\u001a\u00020\n2\u0006\u0010u\u001a\u00020\nH\u0016¢\u0006\u0002\u0010UJ\u0015\u0010v\u001a\u00028\u00002\u0006\u0010.\u001a\u00020\nH\u0016¢\u0006\u0002\u0010NJ\u0015\u0010w\u001a\u00028\u00002\u0006\u0010x\u001a\u00020\nH\u0016¢\u0006\u0002\u0010NJ\u0015\u0010y\u001a\u00028\u00002\u0006\u0010x\u001a\u00020\nH\u0016¢\u0006\u0002\u0010NJ\b\u0010z\u001a\u00020;H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0018\u001a\u0012\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001a\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0015\u001a\u0004\b#\u0010\u0013R\u000e\u0010%\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010*0\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u0015\u001a\u0004\b+\u0010\u0013R\u000e\u0010-\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006{"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "B", "Lcom/ykb/ebook/common_interface/ActivityAction;", "Lcom/ykb/ebook/common_interface/ResourcesAction;", "Lcom/ykb/ebook/common_interface/ClickAction;", "Lcom/ykb/ebook/common_interface/KeyboardAction;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "animStyle", "", "backgroundDimAmount", "", "backgroundDimEnabled", "", "cancelListeners", "", "Lcom/ykb/ebook/dialog/BasicDialog$OnCancelListener;", "getCancelListeners", "()Ljava/util/List;", "cancelListeners$delegate", "Lkotlin/Lazy;", "cancelable", "canceledOnTouchOutside", "clickArray", "Landroid/util/SparseArray;", "Lcom/ykb/ebook/dialog/BasicDialog$OnClickListener;", "Landroid/view/View;", "contentView", "createListener", "Lcom/ykb/ebook/dialog/BasicDialog$OnCreateListener;", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "dismissListeners", "Lcom/ykb/ebook/dialog/BasicDialog$OnDismissListener;", "getDismissListeners", "dismissListeners$delegate", "gravity", "height", "keyListener", "Lcom/ykb/ebook/dialog/BasicDialog$OnKeyListener;", "showListeners", "Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;", "getShowListeners", "showListeners$delegate", "themeId", "width", "xOffset", "yOffset", "addOnCancelListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "(Lcom/ykb/ebook/dialog/BasicDialog$OnCancelListener;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "addOnDismissListener", "(Lcom/ykb/ebook/dialog/BasicDialog$OnDismissListener;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "addOnShowListener", "(Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "create", "createDialog", "dismiss", "", "findViewById", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "id", "(I)Landroid/view/View;", "getContentView", "getContext", "getDialog", "isCreated", "isShowing", "post", "runnable", "Ljava/lang/Runnable;", "postAtTime", "uptimeMillis", "", "postDelayed", "delayMillis", "setAnimStyle", "(I)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "setBackground", "drawable", "Landroid/graphics/drawable/Drawable;", "(ILandroid/graphics/drawable/Drawable;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "viewId", "drawableId", "(II)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "setBackgroundDimAmount", "dimAmount", "(F)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "setBackgroundDimEnabled", "enabled", "(Z)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "setCancelable", "setCanceledOnTouchOutside", "cancel", "setContentView", "view", "(Landroid/view/View;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "setGravity", "setHeight", "setHint", "text", "", "(ILjava/lang/CharSequence;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "stringId", "setImageDrawable", "setOnClickListener", "(ILcom/ykb/ebook/dialog/BasicDialog$OnClickListener;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "setOnCreateListener", "(Lcom/ykb/ebook/dialog/BasicDialog$OnCreateListener;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "setOnKeyListener", "(Lcom/ykb/ebook/dialog/BasicDialog$OnKeyListener;)Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "setText", "setTextColor", "color", "setThemeStyle", "setVisibility", "visibility", "setWidth", "setXOffset", "offset", "setYOffset", "show", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static class Builder<B extends Builder<B>> implements ActivityAction, ResourcesAction, ClickAction, KeyboardAction {
        private int animStyle;
        private float backgroundDimAmount;
        private boolean backgroundDimEnabled;

        /* renamed from: cancelListeners$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cancelListeners;
        private boolean cancelable;
        private boolean canceledOnTouchOutside;

        @Nullable
        private SparseArray<OnClickListener<View>> clickArray;

        @Nullable
        private View contentView;

        @NotNull
        private final Context context;

        @Nullable
        private OnCreateListener createListener;

        @Nullable
        private BasicDialog dialog;

        /* renamed from: dismissListeners$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy dismissListeners;
        private int gravity;
        private int height;

        @Nullable
        private OnKeyListener keyListener;

        /* renamed from: showListeners$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy showListeners;
        private int themeId;
        private int width;
        private int xOffset;
        private int yOffset;

        public Builder(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.context = context;
            this.themeId = R.style.BaseDialogTheme;
            this.animStyle = -1;
            this.width = -2;
            this.height = -2;
            this.cancelable = true;
            this.canceledOnTouchOutside = true;
            this.backgroundDimEnabled = true;
            this.backgroundDimAmount = 0.5f;
            this.showListeners = LazyKt__LazyJVMKt.lazy(new Function0<ArrayList<OnShowListener>>() { // from class: com.ykb.ebook.dialog.BasicDialog$Builder$showListeners$2
                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final ArrayList<BasicDialog.OnShowListener> invoke() {
                    return new ArrayList<>();
                }
            });
            this.cancelListeners = LazyKt__LazyJVMKt.lazy(new Function0<ArrayList<OnCancelListener>>() { // from class: com.ykb.ebook.dialog.BasicDialog$Builder$cancelListeners$2
                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final ArrayList<BasicDialog.OnCancelListener> invoke() {
                    return new ArrayList<>();
                }
            });
            this.dismissListeners = LazyKt__LazyJVMKt.lazy(new Function0<ArrayList<OnDismissListener>>() { // from class: com.ykb.ebook.dialog.BasicDialog$Builder$dismissListeners$2
                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final ArrayList<BasicDialog.OnDismissListener> invoke() {
                    return new ArrayList<>();
                }
            });
        }

        private final List<OnCancelListener> getCancelListeners() {
            return (List) this.cancelListeners.getValue();
        }

        private final List<OnDismissListener> getDismissListeners() {
            return (List) this.dismissListeners.getValue();
        }

        private final List<OnShowListener> getShowListeners() {
            return (List) this.showListeners.getValue();
        }

        @NotNull
        public B addOnCancelListener(@NotNull OnCancelListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            getCancelListeners().add(listener);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B addOnDismissListener(@NotNull OnDismissListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            getDismissListeners().add(listener);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B addOnShowListener(@NotNull OnShowListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            getShowListeners().add(listener);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public BasicDialog create() {
            if (this.contentView == null) {
                throw new IllegalArgumentException("are you ok?");
            }
            if (isShowing()) {
                dismiss();
            }
            if (this.gravity == 0) {
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
            BasicDialog basicDialogCreateDialog = createDialog(this.context, this.themeId);
            this.dialog = basicDialogCreateDialog;
            Intrinsics.checkNotNull(basicDialogCreateDialog);
            View view = this.contentView;
            Intrinsics.checkNotNull(view);
            basicDialogCreateDialog.setContentView(view);
            basicDialogCreateDialog.setCancelable(this.cancelable);
            if (this.cancelable) {
                basicDialogCreateDialog.setCanceledOnTouchOutside(this.canceledOnTouchOutside);
            }
            basicDialogCreateDialog.setOnShowListeners(getShowListeners());
            basicDialogCreateDialog.setOnCancelListeners(getCancelListeners());
            basicDialogCreateDialog.setOnDismissListeners(getDismissListeners());
            basicDialogCreateDialog.setOnKeyListener(this.keyListener);
            Window window = basicDialogCreateDialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                Intrinsics.checkNotNullExpressionValue(attributes, "window.attributes");
                attributes.width = this.width;
                attributes.height = this.height;
                attributes.gravity = this.gravity;
                attributes.x = this.xOffset;
                attributes.y = this.yOffset;
                attributes.windowAnimations = this.animStyle;
                if (this.backgroundDimEnabled) {
                    window.addFlags(2);
                    window.setDimAmount(this.backgroundDimAmount);
                } else {
                    window.clearFlags(2);
                }
                window.setAttributes(attributes);
            }
            SparseArray<OnClickListener<View>> sparseArray = this.clickArray;
            if (sparseArray != null) {
                for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                    View view2 = this.contentView;
                    Intrinsics.checkNotNull(view2);
                    View viewFindViewById = view2.findViewById(sparseArray.keyAt(i3));
                    if (viewFindViewById != null) {
                        viewFindViewById.setOnClickListener(new ViewClickWrapper(basicDialogCreateDialog, sparseArray.valueAt(i3)));
                    }
                }
            }
            Activity activity = getActivity();
            if (activity != null) {
                DialogLifecycle.INSTANCE.with(activity, basicDialogCreateDialog);
            }
            OnCreateListener onCreateListener = this.createListener;
            if (onCreateListener != null) {
                onCreateListener.onCreate(basicDialogCreateDialog);
            }
            BasicDialog basicDialog = this.dialog;
            Intrinsics.checkNotNull(basicDialog);
            return basicDialog;
        }

        @NotNull
        public BasicDialog createDialog(@NotNull Context context, @StyleRes int themeId) {
            Intrinsics.checkNotNullParameter(context, "context");
            return new BasicDialog(context, themeId);
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
            BasicDialog basicDialog;
            Activity activity = getActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed() || (basicDialog = this.dialog) == null) {
                return;
            }
            basicDialog.dismiss();
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

        @Nullable
        public BasicDialog getDialog() {
            return this.dialog;
        }

        @Override // com.ykb.ebook.common_interface.ResourcesAction
        @Nullable
        public Drawable getDrawable(@DrawableRes int i2) {
            return ResourcesAction.DefaultImpls.getDrawable(this, i2);
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
            return this.dialog != null;
        }

        public boolean isShowing() {
            if (isCreated()) {
                BasicDialog basicDialog = this.dialog;
                Intrinsics.checkNotNull(basicDialog);
                if (basicDialog.isShowing()) {
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
            BasicDialog basicDialog = this.dialog;
            if (basicDialog != null) {
                basicDialog.post(runnable);
            }
        }

        public void postAtTime(@NotNull Runnable runnable, long uptimeMillis) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            if (!isShowing()) {
                addOnShowListener(new ShowPostAtTimeWrapper(runnable, uptimeMillis));
                return;
            }
            BasicDialog basicDialog = this.dialog;
            if (basicDialog != null) {
                basicDialog.postAtTime(runnable, uptimeMillis);
            }
        }

        public void postDelayed(@NotNull Runnable runnable, long delayMillis) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            if (!isShowing()) {
                addOnShowListener(new ShowPostDelayedWrapper(runnable, delayMillis));
                return;
            }
            BasicDialog basicDialog = this.dialog;
            if (basicDialog != null) {
                basicDialog.postDelayed(runnable, delayMillis);
            }
        }

        @NotNull
        public B setAnimStyle(@StyleRes int id) {
            BasicDialog basicDialog;
            this.animStyle = id;
            if (isCreated() && (basicDialog = this.dialog) != null) {
                basicDialog.setWindowAnimations(id);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setBackground(@IdRes int viewId, @DrawableRes int drawableId) {
            return (B) setBackground(viewId, ContextCompat.getDrawable(this.context, drawableId));
        }

        @NotNull
        public B setBackgroundDimAmount(@FloatRange(from = 0.0d, to = 1.0d) float dimAmount) {
            BasicDialog basicDialog;
            this.backgroundDimAmount = dimAmount;
            if (isCreated() && (basicDialog = this.dialog) != null) {
                basicDialog.setBackgroundDimAmount(dimAmount);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setBackgroundDimEnabled(boolean enabled) {
            BasicDialog basicDialog;
            this.backgroundDimEnabled = enabled;
            if (isCreated() && (basicDialog = this.dialog) != null) {
                basicDialog.setBackgroundDimEnabled(enabled);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setCancelable(boolean cancelable) {
            BasicDialog basicDialog;
            this.cancelable = cancelable;
            if (isCreated() && (basicDialog = this.dialog) != null) {
                basicDialog.setCancelable(cancelable);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setCanceledOnTouchOutside(boolean cancel) {
            BasicDialog basicDialog;
            this.canceledOnTouchOutside = cancel;
            if (isCreated() && this.cancelable && (basicDialog = this.dialog) != null) {
                basicDialog.setCanceledOnTouchOutside(cancel);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setContentView(@LayoutRes int id) {
            return (B) setContentView(LayoutInflater.from(this.context).inflate(id, (ViewGroup) new FrameLayout(this.context), false));
        }

        @NotNull
        public B setGravity(int gravity) {
            BasicDialog basicDialog;
            this.gravity = Gravity.getAbsoluteGravity(gravity, getResources().getConfiguration().getLayoutDirection());
            if (isCreated() && (basicDialog = this.dialog) != null) {
                basicDialog.setGravity(gravity);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setHeight(int height) {
            this.height = height;
            if (isCreated()) {
                BasicDialog basicDialog = this.dialog;
                if (basicDialog != null) {
                    basicDialog.setHeight(height);
                }
                Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
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
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
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
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setOnKeyListener(@NotNull OnKeyListener listener) {
            BasicDialog basicDialog;
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.keyListener = listener;
            if (isCreated() && (basicDialog = this.dialog) != null) {
                basicDialog.setOnKeyListener(listener);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
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
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setThemeStyle(@StyleRes int id) {
            this.themeId = id;
            if (isCreated()) {
                throw new IllegalStateException("are you ok?");
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setVisibility(@IdRes int id, int visibility) {
            View viewFindViewById = findViewById(id);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(visibility);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setWidth(int width) {
            this.width = width;
            if (isCreated()) {
                BasicDialog basicDialog = this.dialog;
                if (basicDialog != null) {
                    basicDialog.setWidth(width);
                }
                Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
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
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setXOffset(int offset) {
            BasicDialog basicDialog;
            this.xOffset = offset;
            if (isCreated() && (basicDialog = this.dialog) != null) {
                basicDialog.setXOffset(offset);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setYOffset(int offset) {
            BasicDialog basicDialog;
            this.yOffset = offset;
            if (isCreated() && (basicDialog = this.dialog) != null) {
                basicDialog.setYOffset(offset);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        public void show() {
            BasicDialog basicDialog;
            Activity activity = getActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (!isCreated()) {
                create();
            }
            if (isShowing() || (basicDialog = this.dialog) == null) {
                return;
            }
            basicDialog.show();
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
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setHint(@IdRes int id, @Nullable CharSequence text) {
            TextView textView = (TextView) findViewById(id);
            if (textView != null) {
                textView.setHint(text);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }

        @NotNull
        public B setImageDrawable(@IdRes int id, @Nullable Drawable drawable) {
            ImageView imageView = (ImageView) findViewById(id);
            if (imageView != null) {
                imageView.setImageDrawable(drawable);
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
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
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
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
                    BasicDialog basicDialog = this.dialog;
                    if (basicDialog != null) {
                        basicDialog.setContentView(view);
                    }
                    Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
                    return this;
                }
                View view2 = this.contentView;
                ViewGroup.LayoutParams layoutParams = view2 != null ? view2.getLayoutParams() : null;
                if (layoutParams != null && this.width == -2 && this.height == -2) {
                    setWidth(layoutParams.width);
                    setHeight(layoutParams.height);
                }
                if (this.gravity == 0) {
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
                Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
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
            BasicDialog basicDialog;
            View viewFindViewById;
            Intrinsics.checkNotNullParameter(listener, "listener");
            if (this.clickArray == null) {
                this.clickArray = new SparseArray<>();
            }
            SparseArray<OnClickListener<View>> sparseArray = this.clickArray;
            Intrinsics.checkNotNull(sparseArray);
            sparseArray.put(id, listener);
            if (isCreated() && (basicDialog = this.dialog) != null && (viewFindViewById = basicDialog.findViewById(id)) != null) {
                viewFindViewById.setOnClickListener(new ViewClickWrapper(this.dialog, listener));
            }
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type B of com.ykb.ebook.dialog.BasicDialog.Builder");
            return this;
        }
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u0003B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$CancelListenerWrapper;", "Ljava/lang/ref/SoftReference;", "Landroid/content/DialogInterface$OnCancelListener;", "Lcom/ykb/ebook/dialog/BasicDialog$OnCancelListener;", "referent", "(Landroid/content/DialogInterface$OnCancelListener;)V", "onCancel", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class CancelListenerWrapper extends SoftReference<DialogInterface.OnCancelListener> implements OnCancelListener {
        public CancelListenerWrapper(@Nullable DialogInterface.OnCancelListener onCancelListener) {
            super(onCancelListener);
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.OnCancelListener
        public void onCancel(@Nullable BasicDialog dialog) {
            DialogInterface.OnCancelListener onCancelListener = get();
            if (onCancelListener != null) {
                onCancelListener.onCancel(dialog);
            }
        }
    }

    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u0002\u0018\u0000 \u001a2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001\u001aB\u0019\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0012\u0010\u0016\u001a\u00020\f2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\u0017\u001a\u00020\f2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\b\u0010\u0018\u001a\u00020\fH\u0002J\b\u0010\u0019\u001a\u00020\fH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$DialogLifecycle;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;", "Lcom/ykb/ebook/dialog/BasicDialog$OnDismissListener;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "(Landroid/app/Activity;Lcom/ykb/ebook/dialog/BasicDialog;)V", "dialogAnim", "", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "onDismiss", "onShow", "registerActivityLifecycleCallbacks", "unregisterActivityLifecycleCallbacks", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DialogLifecycle implements Application.ActivityLifecycleCallbacks, OnShowListener, OnDismissListener {

        /* renamed from: Companion, reason: from kotlin metadata */
        @NotNull
        public static final Companion INSTANCE = new Companion(null);

        @Nullable
        private Activity activity;

        @Nullable
        private BasicDialog dialog;
        private int dialogAnim;

        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$DialogLifecycle$Companion;", "", "()V", "with", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final void with(@NotNull Activity activity, @Nullable BasicDialog dialog) {
                Intrinsics.checkNotNullParameter(activity, "activity");
                new DialogLifecycle(activity, dialog);
            }
        }

        public DialogLifecycle(@Nullable Activity activity, @Nullable BasicDialog basicDialog) {
            this.activity = activity;
            this.dialog = basicDialog;
            if (basicDialog != null) {
                basicDialog.addOnShowListener(this);
            }
            BasicDialog basicDialog2 = this.dialog;
            if (basicDialog2 != null) {
                basicDialog2.addOnDismissListener(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onActivityResumed$lambda$1$lambda$0(BasicDialog it, DialogLifecycle this$0) {
            Intrinsics.checkNotNullParameter(it, "$it");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (it.isShowing()) {
                it.setWindowAnimations(this$0.dialogAnim);
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
            BasicDialog basicDialog = this.dialog;
            if (basicDialog != null) {
                basicDialog.removeOnShowListener(this);
                basicDialog.removeOnDismissListener(this);
                if (basicDialog.isShowing()) {
                    basicDialog.dismiss();
                }
            }
            this.dialog = null;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(@NotNull Activity activity) {
            BasicDialog basicDialog;
            Intrinsics.checkNotNullParameter(activity, "activity");
            if (this.activity == activity && (basicDialog = this.dialog) != null && basicDialog.isShowing()) {
                this.dialogAnim = basicDialog.getWindowAnimations();
                basicDialog.setWindowAnimations(0);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(@NotNull Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            final BasicDialog basicDialog = this.dialog;
            if (basicDialog == null || !basicDialog.isShowing()) {
                return;
            }
            basicDialog.postDelayed(new Runnable() { // from class: com.ykb.ebook.dialog.i
                @Override // java.lang.Runnable
                public final void run() {
                    BasicDialog.DialogLifecycle.onActivityResumed$lambda$1$lambda$0(basicDialog, this);
                }
            }, 100L);
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

        @Override // com.ykb.ebook.dialog.BasicDialog.OnDismissListener
        public void onDismiss(@Nullable BasicDialog dialog) {
            this.dialog = null;
            unregisterActivityLifecycleCallbacks();
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.OnShowListener
        public void onShow(@Nullable BasicDialog dialog) {
            this.dialog = dialog;
            registerActivityLifecycleCallbacks();
        }
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u0003B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$DismissListenerWrapper;", "Ljava/lang/ref/SoftReference;", "Landroid/content/DialogInterface$OnDismissListener;", "Lcom/ykb/ebook/dialog/BasicDialog$OnDismissListener;", "referent", "(Landroid/content/DialogInterface$OnDismissListener;)V", "onDismiss", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DismissListenerWrapper extends SoftReference<DialogInterface.OnDismissListener> implements OnDismissListener {
        public DismissListenerWrapper(@Nullable DialogInterface.OnDismissListener onDismissListener) {
            super(onDismissListener);
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.OnDismissListener
        public void onDismiss(@Nullable BasicDialog dialog) {
            DialogInterface.OnDismissListener onDismissListener = get();
            if (onDismissListener != null) {
                onDismissListener.onDismiss(dialog);
            }
        }
    }

    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$KeyListenerWrapper;", "Landroid/content/DialogInterface$OnKeyListener;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/ykb/ebook/dialog/BasicDialog$OnKeyListener;", "(Lcom/ykb/ebook/dialog/BasicDialog$OnKeyListener;)V", "onKey", "", "dialog", "Landroid/content/DialogInterface;", "keyCode", "", NotificationCompat.CATEGORY_EVENT, "Landroid/view/KeyEvent;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class KeyListenerWrapper implements DialogInterface.OnKeyListener {

        @Nullable
        private final OnKeyListener listener;

        public KeyListenerWrapper(@Nullable OnKeyListener onKeyListener) {
            this.listener = onKeyListener;
        }

        @Override // android.content.DialogInterface.OnKeyListener
        public boolean onKey(@Nullable DialogInterface dialog, int keyCode, @Nullable KeyEvent event) {
            OnKeyListener onKeyListener = this.listener;
            if (onKeyListener == null || !(dialog instanceof BasicDialog)) {
                return false;
            }
            return onKeyListener.onKey((BasicDialog) dialog, event);
        }
    }

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0010\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u0003*\u00020\u00042\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00010\u00052\u00020\u00022\u00020\u00032\u00020\u0004B\u000f\u0012\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0007J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0012\u0010\f\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0012\u0010\r\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¨\u0006\u000e"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$ListenersWrapper;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/content/DialogInterface$OnShowListener;", "Landroid/content/DialogInterface$OnCancelListener;", "Landroid/content/DialogInterface$OnDismissListener;", "Ljava/lang/ref/SoftReference;", "referent", "(Landroid/content/DialogInterface$OnShowListener;)V", "onCancel", "", "dialog", "Landroid/content/DialogInterface;", "onDismiss", "onShow", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ListenersWrapper<T extends DialogInterface.OnShowListener & DialogInterface.OnCancelListener & DialogInterface.OnDismissListener> extends SoftReference<T> implements DialogInterface.OnShowListener, DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
        public ListenersWrapper(@Nullable T t2) {
            super(t2);
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(@Nullable DialogInterface dialog) {
            T t2 = get();
            if (t2 != null) {
                t2.onCancel(dialog);
            }
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(@Nullable DialogInterface dialog) {
            T t2 = get();
            if (t2 != null) {
                t2.onDismiss(dialog);
            }
        }

        @Override // android.content.DialogInterface.OnShowListener
        public void onShow(@Nullable DialogInterface dialog) {
            T t2 = get();
            if (t2 != null) {
                t2.onShow(dialog);
            }
        }
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$OnCancelListener;", "", "onCancel", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnCancelListener {
        void onCancel(@Nullable BasicDialog dialog);
    }

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u001f\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$OnClickListener;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Landroid/view/View;", "", "onClick", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "view", "(Lcom/ykb/ebook/dialog/BasicDialog;Landroid/view/View;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnClickListener<V extends View> {
        void onClick(@Nullable BasicDialog dialog, @NotNull V view);
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$OnCreateListener;", "", "onCreate", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnCreateListener {
        void onCreate(@Nullable BasicDialog dialog);
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$OnDismissListener;", "", "onDismiss", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnDismissListener {
        void onDismiss(@Nullable BasicDialog dialog);
    }

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&¨\u0006\b"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$OnKeyListener;", "", "onKey", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", NotificationCompat.CATEGORY_EVENT, "Landroid/view/KeyEvent;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnKeyListener {
        boolean onKey(@Nullable BasicDialog dialog, @Nullable KeyEvent event);
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;", "", "onShow", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnShowListener {
        void onShow(@Nullable BasicDialog dialog);
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u0003B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$ShowListenerWrapper;", "Ljava/lang/ref/SoftReference;", "Landroid/content/DialogInterface$OnShowListener;", "Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;", "referent", "(Landroid/content/DialogInterface$OnShowListener;)V", "onShow", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ShowListenerWrapper extends SoftReference<DialogInterface.OnShowListener> implements OnShowListener {
        public ShowListenerWrapper(@Nullable DialogInterface.OnShowListener onShowListener) {
            super(onShowListener);
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.OnShowListener
        public void onShow(@Nullable BasicDialog dialog) {
            DialogInterface.OnShowListener onShowListener = get();
            if (onShowListener != null) {
                onShowListener.onShow(dialog);
            }
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$ShowPostAtTimeWrapper;", "Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;", "runnable", "Ljava/lang/Runnable;", "uptimeMillis", "", "(Ljava/lang/Runnable;J)V", "onShow", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ShowPostAtTimeWrapper implements OnShowListener {

        @NotNull
        private final Runnable runnable;
        private final long uptimeMillis;

        public ShowPostAtTimeWrapper(@NotNull Runnable runnable, long j2) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            this.runnable = runnable;
            this.uptimeMillis = j2;
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.OnShowListener
        public void onShow(@Nullable BasicDialog dialog) {
            if (dialog != null) {
                dialog.removeOnShowListener(this);
            }
            if (dialog != null) {
                dialog.postAtTime(this.runnable, this.uptimeMillis);
            }
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$ShowPostDelayedWrapper;", "Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;", "runnable", "Ljava/lang/Runnable;", "delayMillis", "", "(Ljava/lang/Runnable;J)V", "onShow", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ShowPostDelayedWrapper implements OnShowListener {
        private final long delayMillis;

        @Nullable
        private final Runnable runnable;

        public ShowPostDelayedWrapper(@Nullable Runnable runnable, long j2) {
            this.runnable = runnable;
            this.delayMillis = j2;
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.OnShowListener
        public void onShow(@Nullable BasicDialog dialog) {
            if (this.runnable == null) {
                return;
            }
            if (dialog != null) {
                dialog.removeOnShowListener(this);
            }
            if (dialog != null) {
                dialog.postDelayed(this.runnable, this.delayMillis);
            }
        }
    }

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$ShowPostWrapper;", "Lcom/ykb/ebook/dialog/BasicDialog$OnShowListener;", "runnable", "Ljava/lang/Runnable;", "(Ljava/lang/Runnable;)V", "onShow", "", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ShowPostWrapper implements OnShowListener {

        @Nullable
        private final Runnable runnable;

        public ShowPostWrapper(@Nullable Runnable runnable) {
            this.runnable = runnable;
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.OnShowListener
        public void onShow(@Nullable BasicDialog dialog) {
            if (this.runnable == null) {
                return;
            }
            if (dialog != null) {
                dialog.removeOnShowListener(this);
            }
            if (dialog != null) {
                dialog.post(this.runnable);
            }
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/dialog/BasicDialog$ViewClickWrapper;", "Landroid/view/View$OnClickListener;", "dialog", "Lcom/ykb/ebook/dialog/BasicDialog;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/ykb/ebook/dialog/BasicDialog$OnClickListener;", "Landroid/view/View;", "(Lcom/ykb/ebook/dialog/BasicDialog;Lcom/ykb/ebook/dialog/BasicDialog$OnClickListener;)V", "onClick", "", "view", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ViewClickWrapper implements View.OnClickListener {

        @Nullable
        private final BasicDialog dialog;

        @Nullable
        private final OnClickListener<View> listener;

        public ViewClickWrapper(@Nullable BasicDialog basicDialog, @Nullable OnClickListener<View> onClickListener) {
            this.dialog = basicDialog;
            this.listener = onClickListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            OnClickListener<View> onClickListener = this.listener;
            if (onClickListener != null) {
                onClickListener.onClick(this.dialog, view);
            }
        }
    }

    public /* synthetic */ BasicDialog(Context context, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? R.style.BaseDialogTheme : i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setOnCancelListeners(List<OnCancelListener> listeners) {
        super.setOnCancelListener(this.listeners);
        this.cancelListeners = listeners;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setOnDismissListeners(List<OnDismissListener> listeners) {
        super.setOnDismissListener(this.listeners);
        this.dismissListeners = listeners;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setOnShowListeners(List<OnShowListener> listeners) {
        super.setOnShowListener(this.listeners);
        this.showListeners = listeners;
    }

    public void addOnCancelListener(@Nullable OnCancelListener listener) {
        if (this.cancelListeners == null) {
            this.cancelListeners = new ArrayList();
            super.setOnCancelListener(this.listeners);
        }
        List<OnCancelListener> list = this.cancelListeners;
        if (list != null) {
            list.add(listener);
        }
    }

    public void addOnDismissListener(@Nullable OnDismissListener listener) {
        if (this.dismissListeners == null) {
            this.dismissListeners = new ArrayList();
            super.setOnDismissListener(this.listeners);
        }
        List<OnDismissListener> list = this.dismissListeners;
        if (list != null) {
            list.add(listener);
        }
    }

    public void addOnShowListener(@Nullable OnShowListener listener) {
        if (this.showListeners == null) {
            this.showListeners = new ArrayList();
            super.setOnShowListener(this.listeners);
        }
        List<OnShowListener> list = this.showListeners;
        if (list != null) {
            list.add(listener);
        }
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

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        removeCallbacks();
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService(InputMethodManager.class)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        super.dismiss();
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
        View viewFindViewById = findViewById(android.R.id.content);
        if (!(viewFindViewById instanceof ViewGroup)) {
            return viewFindViewById;
        }
        ViewGroup viewGroup = (ViewGroup) viewFindViewById;
        return viewGroup.getChildCount() == 1 ? viewGroup.getChildAt(0) : viewFindViewById;
    }

    @Override // com.ykb.ebook.common_interface.ResourcesAction
    @Nullable
    public Drawable getDrawable(@DrawableRes int i2) {
        return ResourcesAction.DefaultImpls.getDrawable(this, i2);
    }

    public int getGravity() {
        WindowManager.LayoutParams attributes;
        Window window = getWindow();
        if (window == null || (attributes = window.getAttributes()) == null) {
            return 0;
        }
        return attributes.gravity;
    }

    @Override // com.ykb.ebook.common_interface.HandlerAction
    @NotNull
    public Handler getHandler() {
        return HandlerAction.DefaultImpls.getHandler(this);
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

    public int getWindowAnimations() {
        Window window = getWindow();
        if (window == null) {
            return -1;
        }
        return window.getAttributes().windowAnimations;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(@Nullable DialogInterface dialog) {
        List<OnCancelListener> list = this.cancelListeners;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnCancelListener onCancelListener = list.get(i2);
                if (onCancelListener != null) {
                    onCancelListener.onCancel(this);
                }
            }
        }
    }

    @Override // com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
    public void onClick(@NotNull View view) {
        ClickAction.DefaultImpls.onClick(this, view);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(@Nullable DialogInterface dialog) {
        List<OnDismissListener> list = this.dismissListeners;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnDismissListener onDismissListener = list.get(i2);
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(this);
                }
            }
        }
    }

    @Override // android.content.DialogInterface.OnShowListener
    public void onShow(@Nullable DialogInterface dialog) {
        List<OnShowListener> list = this.showListeners;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnShowListener onShowListener = list.get(i2);
                if (onShowListener != null) {
                    onShowListener.onShow(this);
                }
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

    public void removeOnCancelListener(@Nullable OnCancelListener listener) {
        List<OnCancelListener> list = this.cancelListeners;
        if (list != null) {
            list.remove(listener);
        }
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
        Window window = getWindow();
        if (window != null) {
            window.setDimAmount(dimAmount);
        }
    }

    public void setBackgroundDimEnabled(boolean enabled) {
        if (enabled) {
            Window window = getWindow();
            if (window != null) {
                window.addFlags(2);
                return;
            }
            return;
        }
        Window window2 = getWindow();
        if (window2 != null) {
            window2.clearFlags(2);
        }
    }

    public void setGravity(int gravity) {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(gravity);
        }
    }

    public void setHeight(int height) {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes != null) {
            attributes.height = height;
        }
        window.setAttributes(attributes);
    }

    @Override // com.ykb.ebook.common_interface.ClickAction
    public void setOnClickListener(@Nullable View.OnClickListener onClickListener, @IdRes @NotNull int... iArr) {
        ClickAction.DefaultImpls.setOnClickListener(this, onClickListener, iArr);
    }

    public void setOnKeyListener(@Nullable OnKeyListener listener) {
        super.setOnKeyListener(new KeyListenerWrapper(listener));
    }

    public void setWidth(int width) {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes != null) {
            attributes.width = width;
        }
        window.setAttributes(attributes);
    }

    public void setWindowAnimations(@StyleRes int id) {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(id);
        }
    }

    public void setXOffset(int offset) {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes != null) {
            attributes.x = offset;
        }
        window.setAttributes(attributes);
    }

    public void setYOffset(int offset) {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes != null) {
            attributes.y = offset;
        }
        window.setAttributes(attributes);
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BasicDialog(@NotNull Context context, @StyleRes int i2) {
        super(context, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.listeners = new ListenersWrapper<>(this);
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
