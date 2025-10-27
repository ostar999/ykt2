package com.ykb.ebook.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.ykb.ebook.R;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0012\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0012\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\b\u001a\u0014\u0010\t\u001a\u00020\u0004*\u00020\n2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u001a\u0012\u0010\t\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u0006\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007\u001a \u0010\f\u001a\u00020\u0004*\u00020\n2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\r\u001a\u00020\bH\u0007\u001a\u001c\u0010\f\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u0006\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\b\u001a\u0012\u0010\f\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0012\u0010\f\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\b\u001a\u0012\u0010\u000e\u001a\u00020\u0004*\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007\"\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"toast", "Landroid/widget/Toast;", "toastLegacy", "longToast", "", "Landroidx/fragment/app/Fragment;", "message", "", "", "longToastOnUi", "Landroid/content/Context;", "longToastOnUiLegacy", "toastOnUi", "duration", "toastOnUiLegacy", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ToastUtilsKt {

    @Nullable
    private static Toast toast;

    @Nullable
    private static Toast toastLegacy;

    public static final void longToast(@NotNull Fragment fragment, int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context contextRequireContext = fragment.requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        longToastOnUi(contextRequireContext, i2);
    }

    public static final void longToastOnUi(@NotNull Context context, int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        toastOnUi(context, i2, 1);
    }

    public static final void longToastOnUiLegacy(@NotNull final Context context, @NotNull final CharSequence message) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        HandlerUtilsKt.runOnUI(new Function0<Unit>() { // from class: com.ykb.ebook.util.ToastUtilsKt.longToastOnUiLegacy.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                Unit unit;
                Context context2 = context;
                CharSequence charSequence = message;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    if (ToastUtilsKt.toastLegacy == null) {
                        ToastUtilsKt.toastLegacy = Toast.makeText(context2, charSequence, 1);
                    } else {
                        Toast toast2 = ToastUtilsKt.toastLegacy;
                        if (toast2 != null) {
                            toast2.setText(charSequence);
                        }
                        Toast toast3 = ToastUtilsKt.toastLegacy;
                        if (toast3 != null) {
                            toast3.setDuration(1);
                        }
                    }
                    Toast toast4 = ToastUtilsKt.toastLegacy;
                    if (toast4 != null) {
                        toast4.show();
                        unit = Unit.INSTANCE;
                    } else {
                        unit = null;
                    }
                    Result.m783constructorimpl(unit);
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.INSTANCE;
                    Result.m783constructorimpl(ResultKt.createFailure(th));
                }
            }
        });
    }

    public static final void toastOnUi(@NotNull Context context, int i2, int i3) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        toastOnUi(context, context.getString(i2), i3);
    }

    public static /* synthetic */ void toastOnUi$default(Context context, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i3 = 0;
        }
        toastOnUi(context, i2, i3);
    }

    public static final void toastOnUiLegacy(@NotNull final Context context, @NotNull final CharSequence message) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        HandlerUtilsKt.runOnUI(new Function0<Unit>() { // from class: com.ykb.ebook.util.ToastUtilsKt.toastOnUiLegacy.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                Unit unit;
                Context context2 = context;
                CharSequence charSequence = message;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    if (ToastUtilsKt.toastLegacy == null) {
                        ToastUtilsKt.toastLegacy = Toast.makeText(context2, charSequence, 0);
                    } else {
                        Toast toast2 = ToastUtilsKt.toastLegacy;
                        if (toast2 != null) {
                            toast2.setText(charSequence);
                        }
                        Toast toast3 = ToastUtilsKt.toastLegacy;
                        if (toast3 != null) {
                            toast3.setDuration(0);
                        }
                    }
                    Toast toast4 = ToastUtilsKt.toastLegacy;
                    if (toast4 != null) {
                        toast4.show();
                        unit = Unit.INSTANCE;
                    } else {
                        unit = null;
                    }
                    Result.m783constructorimpl(unit);
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.INSTANCE;
                    Result.m783constructorimpl(ResultKt.createFailure(th));
                }
            }
        });
    }

    public static final void longToast(@NotNull Fragment fragment, @NotNull CharSequence message) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        Context contextRequireContext = fragment.requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        longToastOnUi(contextRequireContext, message);
    }

    public static final void longToastOnUi(@NotNull Context context, @Nullable CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        toastOnUi(context, charSequence, 1);
    }

    @SuppressLint({"InflateParams"})
    public static final void toastOnUi(@NotNull final Context context, @Nullable final CharSequence charSequence, final int i2) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        HandlerUtilsKt.runOnUI(new Function0<Unit>() { // from class: com.ykb.ebook.util.ToastUtilsKt.toastOnUi.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                Context context2 = context;
                CharSequence charSequence2 = charSequence;
                int i3 = i2;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    Toast toast2 = ToastUtilsKt.toast;
                    if (toast2 != null) {
                        toast2.cancel();
                    }
                    ToastUtilsKt.toast = new Toast(context2);
                    int i4 = R.layout.view_toast;
                    Object systemService = context2.getSystemService("layout_inflater");
                    if (systemService == null) {
                        throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
                    }
                    Unit unit = null;
                    View viewInflate = ((LayoutInflater) systemService).inflate(i4, (ViewGroup) null, false);
                    if (viewInflate == null) {
                        throw new NullPointerException("null cannot be cast to non-null type V of splitties.views.LayoutInflaterKt.inflate");
                    }
                    Toast toast3 = ToastUtilsKt.toast;
                    if (toast3 != null) {
                        toast3.setView(viewInflate);
                    }
                    Toast toast4 = ToastUtilsKt.toast;
                    if (toast4 != null) {
                        toast4.setGravity(80, 0, ScreenUtil.getPxByDp(context2, 100.0f));
                    }
                    ((TextView) viewInflate.findViewById(R.id.tv_text)).setText(charSequence2);
                    Toast toast5 = ToastUtilsKt.toast;
                    if (toast5 != null) {
                        toast5.setDuration(i3);
                    }
                    Toast toast6 = ToastUtilsKt.toast;
                    if (toast6 != null) {
                        toast6.show();
                        unit = Unit.INSTANCE;
                    }
                    Result.m783constructorimpl(unit);
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.INSTANCE;
                    Result.m783constructorimpl(ResultKt.createFailure(th));
                }
            }
        });
    }

    public static /* synthetic */ void toastOnUi$default(Context context, CharSequence charSequence, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        toastOnUi(context, charSequence, i2);
    }

    public static final void toastOnUi(@NotNull Fragment fragment, int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        FragmentActivity fragmentActivityRequireActivity = fragment.requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity()");
        toastOnUi$default(fragmentActivityRequireActivity, i2, 0, 2, (Object) null);
    }

    public static final void toastOnUi(@NotNull Fragment fragment, @NotNull CharSequence message) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        FragmentActivity fragmentActivityRequireActivity = fragment.requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity()");
        toastOnUi$default(fragmentActivityRequireActivity, message, 0, 2, (Object) null);
    }
}
