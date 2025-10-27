package com.catchpig.mvvm.ext;

import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.lang.reflect.Field;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002\u001a\u0012\u0010\u0005\u001a\u00020\u0004*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u001c\u0010\t\u001a\u00020\u0004*\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001a\u0010\u000e\u001a\u00020\u0004*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001¨\u0006\u0011"}, d2 = {"getMaxLength", "", "Landroid/widget/EditText;", "selectionEndGo", "", "setBackgroundJellyBean16", "Landroid/view/View;", "drawable", "Landroid/graphics/drawable/Drawable;", "setDebounceClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Landroid/view/View$OnClickListener;", CrashHianalyticsData.TIME, "", "setWh", "w", "h", "mvvm_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ViewExtKt {
    public static final int getMaxLength(@NotNull EditText editText) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        int iIntValue = Integer.MAX_VALUE;
        try {
            InputFilter[] inputFilters = editText.getFilters();
            Intrinsics.checkNotNullExpressionValue(inputFilters, "inputFilters");
            for (InputFilter inputFilter : inputFilters) {
                Class<?> cls = inputFilter.getClass();
                if (Intrinsics.areEqual(cls.getName(), "android.text.InputFilter$LengthFilter")) {
                    Field[] declaredFields = cls.getDeclaredFields();
                    Intrinsics.checkNotNullExpressionValue(declaredFields, "c.declaredFields");
                    for (Field field : declaredFields) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            Object obj = field.get(inputFilter);
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                            iIntValue = ((Integer) obj).intValue();
                        }
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return iIntValue;
    }

    public static final void selectionEndGo(@NotNull EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        String string = editText.getText().toString();
        if (string == null || string.length() == 0) {
            editText.setSelection(0);
        } else {
            editText.setSelection(string.length());
        }
    }

    public static final void setBackgroundJellyBean16(@NotNull View view, @NotNull Drawable drawable) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        view.setBackground(drawable);
    }

    public static final void setDebounceClickListener(@NotNull View view, @NotNull final View.OnClickListener listener, final long j2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(listener, "listener");
        final Ref.LongRef longRef = new Ref.LongRef();
        view.setOnClickListener(new View.OnClickListener() { // from class: com.catchpig.mvvm.ext.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ViewExtKt.setDebounceClickListener$lambda$0(longRef, j2, listener, view2);
            }
        });
    }

    public static /* synthetic */ void setDebounceClickListener$default(View view, View.OnClickListener onClickListener, long j2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            j2 = 300;
        }
        setDebounceClickListener(view, onClickListener, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setDebounceClickListener$lambda$0(Ref.LongRef lastClickTime, long j2, View.OnClickListener listener, View view) {
        Intrinsics.checkNotNullParameter(lastClickTime, "$lastClickTime");
        Intrinsics.checkNotNullParameter(listener, "$listener");
        if (System.currentTimeMillis() - lastClickTime.element >= j2) {
            lastClickTime.element = System.currentTimeMillis();
            listener.onClick(view);
        }
    }

    public static final void setWh(@NotNull View view, int i2, int i3) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (i2 > 0) {
            layoutParams.width = i2;
        }
        if (i3 > 0) {
            layoutParams.height = i3;
        } else {
            layoutParams.height = -2;
        }
        view.setLayoutParams(layoutParams);
    }
}
