package com.angcyo.tablayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.luck.picture.lib.config.CustomIntentKey;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000n\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005H\u0000\u001a \u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0000\u001a \u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0000\u001a \u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0005H\u0000\u001a\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0001H\u0000\u001a\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005H\u0000\u001a\u0010\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001fH\u0000\u001aD\u0010 \u001a\u00020!*\u00020\b2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u00052\b\b\u0002\u0010'\u001a\u00020\u00052\b\b\u0002\u0010(\u001a\u00020\u0005H\u0000\u001a\u0016\u0010)\u001a\u0004\u0018\u00010\b*\u00020\b2\u0006\u0010*\u001a\u00020\u0005H\u0000\u001a\"\u0010+\u001a\u00020,*\u00020\b2\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010.\u001a\u00020,H\u0000\u001a\u0016\u0010/\u001a\u00020,*\u00020\b2\b\b\u0002\u0010.\u001a\u00020,H\u0000\u001a&\u0010/\u001a\u00020,*\u00020\b2\u0006\u00100\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u00052\b\b\u0002\u0010.\u001a\u00020,H\u0000\u001a\u0014\u00102\u001a\u000203*\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0005H\u0000\u001a \u00104\u001a\u00020\b*\u0002052\b\b\u0001\u00106\u001a\u00020\u00052\b\b\u0002\u00107\u001a\u000203H\u0000\u001a*\u00108\u001a\u000203\"\u0004\b\u0000\u00109*\n\u0012\u0004\u0012\u0002H9\u0018\u00010:2\u000e\u0010;\u001a\n\u0012\u0004\u0012\u0002H9\u0018\u00010:H\u0000\u001a\n\u0010<\u001a\u000203*\u00020\u0005\u001a\n\u0010=\u001a\u000203*\u00020\u0005\u001a\f\u0010>\u001a\u00020?*\u00020@H\u0000\u001a\f\u0010A\u001a\u00020?*\u00020@H\u0000\u001a\f\u0010B\u001a\u00020?*\u00020@H\u0000\u001a\u0014\u0010C\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0005H\u0000\u001a\u0010\u0010\u0013\u001a\u00020\u0005*\b\u0012\u0002\b\u0003\u0018\u00010D\u001a\u000e\u0010E\u001a\u00020\u0001*\u0004\u0018\u00010FH\u0000\u001a\u0018\u0010G\u001a\u00020\u0001*\u0004\u0018\u00010F2\b\u0010H\u001a\u0004\u0018\u00010#H\u0000\u001a\u0018\u0010I\u001a\u0004\u0018\u00010J*\u0004\u0018\u00010J2\u0006\u0010K\u001a\u00020\u0005H\u0000\u001a\u0016\u0010I\u001a\u00020?*\u0004\u0018\u00010\b2\u0006\u0010K\u001a\u00020\u0005H\u0000\"\u0014\u0010\u0000\u001a\u00020\u00018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u00058@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\"\u0018\u0010\u0004\u001a\u00020\u0005*\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\t\"\u0018\u0010\n\u001a\u00020\u0005*\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t\"\u0018\u0010\f\u001a\u00020\u0005*\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\t\"\u0018\u0010\u000e\u001a\u00020\u0005*\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\t\"\u0018\u0010\u0010\u001a\u00020\u0005*\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\t¨\u0006L"}, d2 = {"dp", "", "getDp", "()F", "dpi", "", "getDpi", "()I", "Landroid/view/View;", "(Landroid/view/View;)I", "screenHeight", "getScreenHeight", "screenWidth", "getScreenWidth", "viewDrawHeight", "getViewDrawHeight", "viewDrawWidth", "getViewDrawWidth", "atmostMeasure", DatabaseManager.SIZE, "clamp", "value", "min", "max", "evaluateColor", "fraction", "startColor", "endColor", "exactlyMeasure", "navBarHeight", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "calcLayoutWidthHeight", "", "rLayoutWidth", "", "rLayoutHeight", "parentWidth", "parentHeight", "rLayoutWidthExclude", "rLayoutHeightExclude", "getChildOrNull", "index", "getLocationInParent", "Landroid/graphics/Rect;", "parentView", "result", "getViewRect", CustomIntentKey.EXTRA_OFFSET_X, CustomIntentKey.EXTRA_OFFSET_Y, "have", "", "inflate", "Landroid/view/ViewGroup;", "layoutId", "attachToRoot", "isChange", ExifInterface.GPS_DIRECTION_TRUE, "", "other", "isHorizontal", "isVertical", "loge", "", "", "logi", "logw", PLVRemoveMicSiteEvent.EVENT_NAME, "", "textHeight", "Landroid/graphics/Paint;", "textWidth", "text", "tintDrawableColor", "Landroid/graphics/drawable/Drawable;", "color", "TabLayout_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class LibExKt {
    public static final int atmostMeasure(int i2) {
        return View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE);
    }

    @NotNull
    public static final int[] calcLayoutWidthHeight(@NotNull View view, @Nullable String str, @Nullable String str2, int i2, int i3, int i4, int i5) {
        Float floatOrNull;
        Float floatOrNull2;
        Intrinsics.checkNotNullParameter(view, "<this>");
        int[] iArr = {-1, -1};
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            return iArr;
        }
        if (!TextUtils.isEmpty(str)) {
            Intrinsics.checkNotNull(str);
            if (StringsKt__StringsKt.contains((CharSequence) str, (CharSequence) "sw", true)) {
                Float floatOrNull3 = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(StringsKt__StringsJVMKt.replace(str, "sw", "", true));
                if (floatOrNull3 != null) {
                    floatOrNull3.floatValue();
                    iArr[0] = (int) (floatOrNull3.floatValue() * (getScreenWidth(view) - i4));
                }
            } else if (StringsKt__StringsKt.contains((CharSequence) str, (CharSequence) "pw", true) && (floatOrNull2 = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(StringsKt__StringsJVMKt.replace(str, "pw", "", true))) != null) {
                floatOrNull2.floatValue();
                iArr[0] = (int) (floatOrNull2.floatValue() * (i2 - i4));
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            Intrinsics.checkNotNull(str2);
            if (StringsKt__StringsKt.contains((CharSequence) str2, (CharSequence) c.c.f2218l, true)) {
                Float floatOrNull4 = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(StringsKt__StringsJVMKt.replace(str2, c.c.f2218l, "", true));
                if (floatOrNull4 != null) {
                    floatOrNull4.floatValue();
                    iArr[1] = (int) (floatOrNull4.floatValue() * (getScreenHeight(view) - i5));
                }
            } else if (StringsKt__StringsKt.contains((CharSequence) str2, (CharSequence) "ph", true) && (floatOrNull = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(StringsKt__StringsJVMKt.replace(str2, "ph", "", true))) != null) {
                floatOrNull.floatValue();
                iArr[1] = (int) (floatOrNull.floatValue() * (i3 - i5));
            }
        }
        return iArr;
    }

    public static final float clamp(float f2, float f3, float f4) {
        return f2 < f3 ? f3 : f2 > f4 ? f4 : f2;
    }

    public static final int clamp(int i2, int i3, int i4) {
        return i2 < i3 ? i3 : i2 > i4 ? i4 : i2;
    }

    public static final int evaluateColor(float f2, int i2, int i3) {
        float fClamp = MathUtils.clamp(f2, 0.0f, 1.0f);
        return ((((i2 >> 24) & 255) + ((int) ((((i3 >> 24) & 255) - r0) * fClamp))) << 24) | ((((i2 >> 16) & 255) + ((int) ((((i3 >> 16) & 255) - r1) * fClamp))) << 16) | ((((i2 >> 8) & 255) + ((int) ((((i3 >> 8) & 255) - r2) * fClamp))) << 8) | ((i2 & 255) + ((int) (fClamp * ((i3 & 255) - r7))));
    }

    public static final int exactlyMeasure(int i2) {
        return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
    }

    @Nullable
    public static final View getChildOrNull(@NotNull View view, int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (!(view instanceof ViewGroup)) {
            return view;
        }
        boolean z2 = false;
        if (i2 >= 0 && i2 < ((ViewGroup) view).getChildCount()) {
            z2 = true;
        }
        if (z2) {
            return ((ViewGroup) view).getChildAt(i2);
        }
        return null;
    }

    public static final float getDp() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static final int getDpi() {
        return (int) getDp();
    }

    @NotNull
    public static final Rect getLocationInParent(@NotNull View view, @Nullable View view2, @NotNull Rect result) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(result, "result");
        if (view2 == null) {
            Object parent = view.getParent();
            view2 = parent instanceof View ? (View) parent : null;
        }
        if (view2 == null) {
            getViewRect(view, result);
        } else {
            result.set(0, 0, 0, 0);
            if (!Intrinsics.areEqual(view, view2)) {
                getLocationInParent$doIt(view, view2, result);
            }
            result.right = result.left + view.getMeasuredWidth();
            result.bottom = result.top + view.getMeasuredHeight();
        }
        return result;
    }

    public static /* synthetic */ Rect getLocationInParent$default(View view, View view2, Rect rect, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            view2 = null;
        }
        if ((i2 & 2) != 0) {
            rect = new Rect();
        }
        return getLocationInParent(view, view2, rect);
    }

    private static final void getLocationInParent$doIt(View view, View view2, Rect rect) {
        Object parent = view.getParent();
        if (parent instanceof View) {
            rect.left += view.getLeft();
            rect.top += view.getTop();
            if (Intrinsics.areEqual(parent, view2)) {
                return;
            }
            getLocationInParent$doIt((View) parent, view2, rect);
        }
    }

    public static final int getScreenHeight(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static final int getScreenWidth(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static final int getViewDrawHeight(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return (view.getMeasuredHeight() - view.getPaddingTop()) - view.getPaddingBottom();
    }

    public static final int getViewDrawWidth(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return (view.getMeasuredWidth() - view.getPaddingLeft()) - view.getPaddingRight();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0033  */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final android.graphics.Rect getViewRect(@org.jetbrains.annotations.NotNull android.view.View r4, @org.jetbrains.annotations.NotNull android.graphics.Rect r5) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "result"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            android.content.Context r0 = r4.getContext()
            boolean r1 = r0 instanceof android.app.Activity
            if (r1 == 0) goto L15
            android.app.Activity r0 = (android.app.Activity) r0
            goto L16
        L15:
            r0 = 0
        L16:
            r1 = 0
            if (r0 == 0) goto L33
            android.view.Window r2 = r0.getWindow()
            android.view.View r2 = r2.getDecorView()
            r2.getGlobalVisibleRect(r5)
            int r2 = r5.width()
            int r3 = r5.height()
            if (r2 <= r3) goto L33
            int r0 = navBarHeight(r0)
            goto L34
        L33:
            r0 = r1
        L34:
            android.graphics.Rect r4 = getViewRect(r4, r0, r1, r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.angcyo.tablayout.LibExKt.getViewRect(android.view.View, android.graphics.Rect):android.graphics.Rect");
    }

    public static /* synthetic */ Rect getViewRect$default(View view, Rect rect, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            rect = new Rect();
        }
        return getViewRect(view, rect);
    }

    public static final boolean have(int i2, int i3) {
        if (i2 == 0 || i3 == 0) {
            return false;
        }
        return (i2 == 0 && i3 == 0) || (((i2 > 0 && i3 > 0) || (i2 < 0 && i3 < 0)) && (i2 & i3) == i3);
    }

    @NotNull
    public static final View inflate(@NotNull ViewGroup viewGroup, @LayoutRes int i2, boolean z2) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        if (i2 == -1) {
            return viewGroup;
        }
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(i2, viewGroup, false);
        if (z2) {
            viewGroup.addView(rootView);
        }
        Intrinsics.checkNotNullExpressionValue(rootView, "rootView");
        return rootView;
    }

    public static /* synthetic */ View inflate$default(ViewGroup viewGroup, int i2, boolean z2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        return inflate(viewGroup, i2, z2);
    }

    public static final <T> boolean isChange(@Nullable List<? extends T> list, @Nullable List<? extends T> list2) {
        if (size(list) != size(list2)) {
            return true;
        }
        if (list != null) {
            int i2 = 0;
            for (T t2 : list) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                if (!Intrinsics.areEqual(t2, list2 != null ? CollectionsKt___CollectionsKt.getOrNull(list2, i2) : null)) {
                    return true;
                }
                i2 = i3;
            }
        }
        return false;
    }

    public static final boolean isHorizontal(int i2) {
        return i2 == 0;
    }

    public static final boolean isVertical(int i2) {
        return i2 == 1;
    }

    public static final void loge(@NotNull Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<this>");
        Log.e("DslTabLayout", String.valueOf(obj));
    }

    public static final void logi(@NotNull Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<this>");
        Log.i("DslTabLayout", String.valueOf(obj));
    }

    public static final void logw(@NotNull Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<this>");
        Log.w("DslTabLayout", String.valueOf(obj));
    }

    public static final int navBarHeight(@NotNull Context context) {
        int iWidth;
        int iWidth2;
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(context instanceof Activity)) {
            return 0;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        Activity activity = (Activity) context;
        activity.getWindow().getDecorView().getGlobalVisibleRect(rect);
        activity.getWindow().findViewById(android.R.id.content).getGlobalVisibleRect(rect2);
        if (rect.width() > rect.height()) {
            iWidth = rect.width();
            iWidth2 = rect2.width();
        } else {
            iWidth = rect.bottom;
            iWidth2 = rect2.bottom;
        }
        return iWidth - iWidth2;
    }

    public static final int remove(int i2, int i3) {
        return i2 & (~i3);
    }

    public static final int size(@Nullable Collection<?> collection) {
        if (collection != null) {
            return collection.size();
        }
        return 0;
    }

    public static final float textHeight(@Nullable Paint paint) {
        if (paint != null) {
            return paint.descent() - paint.ascent();
        }
        return 0.0f;
    }

    public static final float textWidth(@Nullable Paint paint, @Nullable String str) {
        if (TextUtils.isEmpty(str) || paint == null) {
            return 0.0f;
        }
        return paint.measureText(str);
    }

    @Nullable
    public static final Drawable tintDrawableColor(@Nullable Drawable drawable, int i2) {
        if (drawable == null) {
            return drawable;
        }
        Drawable drawableMutate = DrawableCompat.wrap(drawable).mutate();
        Intrinsics.checkNotNullExpressionValue(drawableMutate, "wrap(this).mutate()");
        DrawableCompat.setTint(drawableMutate, i2);
        return drawableMutate;
    }

    public static final int exactlyMeasure(float f2) {
        return exactlyMeasure((int) f2);
    }

    public static final int getDpi(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return (int) view.getContext().getResources().getDisplayMetrics().density;
    }

    public static /* synthetic */ Rect getViewRect$default(View view, int i2, int i3, Rect rect, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            rect = new Rect();
        }
        return getViewRect(view, i2, i3, rect);
    }

    public static final void tintDrawableColor(@Nullable View view, int i2) {
        if (view instanceof TextView) {
            Drawable[] drawableArr = new Drawable[4];
            TextView textView = (TextView) view;
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            Intrinsics.checkNotNullExpressionValue(compoundDrawables, "compoundDrawables");
            int length = compoundDrawables.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length) {
                Drawable drawable = compoundDrawables[i3];
                int i5 = i4 + 1;
                drawableArr[i4] = drawable != null ? tintDrawableColor(drawable, i2) : null;
                i3++;
                i4 = i5;
            }
            textView.setCompoundDrawables(drawableArr[0], drawableArr[1], drawableArr[2], drawableArr[3]);
            return;
        }
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            Drawable drawable2 = imageView.getDrawable();
            imageView.setImageDrawable(drawable2 != null ? tintDrawableColor(drawable2, i2) : null);
        }
    }

    @NotNull
    public static final Rect getViewRect(@NotNull View view, int i2, int i3, @NotNull Rect result) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(result, "result");
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i4 = iArr[0] + i2;
        int i5 = iArr[1] + i3;
        result.set(i4, i5, view.getMeasuredWidth() + i4, view.getMeasuredHeight() + i5);
        return result;
    }
}
