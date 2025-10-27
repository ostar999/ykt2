package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.graphics.ColorUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public final class AppCompatDrawableManager {
    private static final boolean DEBUG = false;
    private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    private static AppCompatDrawableManager INSTANCE = null;
    private static final String TAG = "AppCompatDrawableManag";
    private ResourceManagerInternal mResourceManager;

    public static synchronized AppCompatDrawableManager get() {
        if (INSTANCE == null) {
            preload();
        }
        return INSTANCE;
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(int i2, PorterDuff.Mode mode) {
        return ResourceManagerInternal.getPorterDuffColorFilter(i2, mode);
    }

    public static synchronized void preload() {
        if (INSTANCE == null) {
            AppCompatDrawableManager appCompatDrawableManager = new AppCompatDrawableManager();
            INSTANCE = appCompatDrawableManager;
            appCompatDrawableManager.mResourceManager = ResourceManagerInternal.get();
            INSTANCE.mResourceManager.setHooks(new ResourceManagerInternal.ResourceManagerHooks() { // from class: androidx.appcompat.widget.AppCompatDrawableManager.1
                private final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL = {R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
                private final int[] TINT_COLOR_CONTROL_NORMAL = {R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
                private final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED = {R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl, R.drawable.abc_text_select_handle_middle_mtrl, R.drawable.abc_text_select_handle_right_mtrl};
                private final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY = {R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
                private final int[] TINT_COLOR_CONTROL_STATE_LIST = {R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};
                private final int[] TINT_CHECKABLE_BUTTON_LIST = {R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material, R.drawable.abc_btn_check_material_anim, R.drawable.abc_btn_radio_material_anim};

                private boolean arrayContains(int[] iArr, int i2) {
                    for (int i3 : iArr) {
                        if (i3 == i2) {
                            return true;
                        }
                    }
                    return false;
                }

                private ColorStateList createBorderlessButtonColorStateList(@NonNull Context context) {
                    return createButtonColorStateList(context, 0);
                }

                private ColorStateList createButtonColorStateList(@NonNull Context context, @ColorInt int i2) {
                    int themeAttrColor = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlHighlight);
                    return new ColorStateList(new int[][]{ThemeUtils.DISABLED_STATE_SET, ThemeUtils.PRESSED_STATE_SET, ThemeUtils.FOCUSED_STATE_SET, ThemeUtils.EMPTY_STATE_SET}, new int[]{ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorButtonNormal), ColorUtils.compositeColors(themeAttrColor, i2), ColorUtils.compositeColors(themeAttrColor, i2), i2});
                }

                private ColorStateList createColoredButtonColorStateList(@NonNull Context context) {
                    return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorAccent));
                }

                private ColorStateList createDefaultButtonColorStateList(@NonNull Context context) {
                    return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorButtonNormal));
                }

                private ColorStateList createSwitchThumbColorStateList(Context context) {
                    int[][] iArr = new int[3][];
                    int[] iArr2 = new int[3];
                    int i2 = R.attr.colorSwitchThumbNormal;
                    ColorStateList themeAttrColorStateList = ThemeUtils.getThemeAttrColorStateList(context, i2);
                    if (themeAttrColorStateList == null || !themeAttrColorStateList.isStateful()) {
                        iArr[0] = ThemeUtils.DISABLED_STATE_SET;
                        iArr2[0] = ThemeUtils.getDisabledThemeAttrColor(context, i2);
                        iArr[1] = ThemeUtils.CHECKED_STATE_SET;
                        iArr2[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
                        iArr[2] = ThemeUtils.EMPTY_STATE_SET;
                        iArr2[2] = ThemeUtils.getThemeAttrColor(context, i2);
                    } else {
                        int[] iArr3 = ThemeUtils.DISABLED_STATE_SET;
                        iArr[0] = iArr3;
                        iArr2[0] = themeAttrColorStateList.getColorForState(iArr3, 0);
                        iArr[1] = ThemeUtils.CHECKED_STATE_SET;
                        iArr2[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
                        iArr[2] = ThemeUtils.EMPTY_STATE_SET;
                        iArr2[2] = themeAttrColorStateList.getDefaultColor();
                    }
                    return new ColorStateList(iArr, iArr2);
                }

                private LayerDrawable getRatingBarLayerDrawable(@NonNull ResourceManagerInternal resourceManagerInternal, @NonNull Context context, @DimenRes int i2) throws Resources.NotFoundException {
                    BitmapDrawable bitmapDrawable;
                    BitmapDrawable bitmapDrawable2;
                    BitmapDrawable bitmapDrawable3;
                    int dimensionPixelSize = context.getResources().getDimensionPixelSize(i2);
                    Drawable drawable = resourceManagerInternal.getDrawable(context, R.drawable.abc_star_black_48dp);
                    Drawable drawable2 = resourceManagerInternal.getDrawable(context, R.drawable.abc_star_half_black_48dp);
                    if ((drawable instanceof BitmapDrawable) && drawable.getIntrinsicWidth() == dimensionPixelSize && drawable.getIntrinsicHeight() == dimensionPixelSize) {
                        bitmapDrawable = (BitmapDrawable) drawable;
                        bitmapDrawable2 = new BitmapDrawable(bitmapDrawable.getBitmap());
                    } else {
                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmapCreateBitmap);
                        drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                        drawable.draw(canvas);
                        bitmapDrawable = new BitmapDrawable(bitmapCreateBitmap);
                        bitmapDrawable2 = new BitmapDrawable(bitmapCreateBitmap);
                    }
                    bitmapDrawable2.setTileModeX(Shader.TileMode.REPEAT);
                    if ((drawable2 instanceof BitmapDrawable) && drawable2.getIntrinsicWidth() == dimensionPixelSize && drawable2.getIntrinsicHeight() == dimensionPixelSize) {
                        bitmapDrawable3 = (BitmapDrawable) drawable2;
                    } else {
                        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
                        Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                        drawable2.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                        drawable2.draw(canvas2);
                        bitmapDrawable3 = new BitmapDrawable(bitmapCreateBitmap2);
                    }
                    LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bitmapDrawable, bitmapDrawable3, bitmapDrawable2});
                    layerDrawable.setId(0, android.R.id.background);
                    layerDrawable.setId(1, android.R.id.secondaryProgress);
                    layerDrawable.setId(2, android.R.id.progress);
                    return layerDrawable;
                }

                private void setPorterDuffColorFilter(Drawable drawable, int i2, PorterDuff.Mode mode) {
                    if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                        drawable = drawable.mutate();
                    }
                    if (mode == null) {
                        mode = AppCompatDrawableManager.DEFAULT_MODE;
                    }
                    drawable.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(i2, mode));
                }

                @Override // androidx.appcompat.widget.ResourceManagerInternal.ResourceManagerHooks
                public Drawable createDrawableFor(@NonNull ResourceManagerInternal resourceManagerInternal, @NonNull Context context, int i2) {
                    if (i2 == R.drawable.abc_cab_background_top_material) {
                        return new LayerDrawable(new Drawable[]{resourceManagerInternal.getDrawable(context, R.drawable.abc_cab_background_internal_bg), resourceManagerInternal.getDrawable(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
                    }
                    if (i2 == R.drawable.abc_ratingbar_material) {
                        return getRatingBarLayerDrawable(resourceManagerInternal, context, R.dimen.abc_star_big);
                    }
                    if (i2 == R.drawable.abc_ratingbar_indicator_material) {
                        return getRatingBarLayerDrawable(resourceManagerInternal, context, R.dimen.abc_star_medium);
                    }
                    if (i2 == R.drawable.abc_ratingbar_small_material) {
                        return getRatingBarLayerDrawable(resourceManagerInternal, context, R.dimen.abc_star_small);
                    }
                    return null;
                }

                @Override // androidx.appcompat.widget.ResourceManagerInternal.ResourceManagerHooks
                public ColorStateList getTintListForDrawableRes(@NonNull Context context, int i2) {
                    if (i2 == R.drawable.abc_edit_text_material) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_edittext);
                    }
                    if (i2 == R.drawable.abc_switch_track_mtrl_alpha) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_switch_track);
                    }
                    if (i2 == R.drawable.abc_switch_thumb_material) {
                        return createSwitchThumbColorStateList(context);
                    }
                    if (i2 == R.drawable.abc_btn_default_mtrl_shape) {
                        return createDefaultButtonColorStateList(context);
                    }
                    if (i2 == R.drawable.abc_btn_borderless_material) {
                        return createBorderlessButtonColorStateList(context);
                    }
                    if (i2 == R.drawable.abc_btn_colored_material) {
                        return createColoredButtonColorStateList(context);
                    }
                    if (i2 == R.drawable.abc_spinner_mtrl_am_alpha || i2 == R.drawable.abc_spinner_textfield_background_material) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_spinner);
                    }
                    if (arrayContains(this.TINT_COLOR_CONTROL_NORMAL, i2)) {
                        return ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorControlNormal);
                    }
                    if (arrayContains(this.TINT_COLOR_CONTROL_STATE_LIST, i2)) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_default);
                    }
                    if (arrayContains(this.TINT_CHECKABLE_BUTTON_LIST, i2)) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_btn_checkable);
                    }
                    if (i2 == R.drawable.abc_seekbar_thumb_material) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_seek_thumb);
                    }
                    return null;
                }

                @Override // androidx.appcompat.widget.ResourceManagerInternal.ResourceManagerHooks
                public PorterDuff.Mode getTintModeForDrawableRes(int i2) {
                    if (i2 == R.drawable.abc_switch_thumb_material) {
                        return PorterDuff.Mode.MULTIPLY;
                    }
                    return null;
                }

                @Override // androidx.appcompat.widget.ResourceManagerInternal.ResourceManagerHooks
                public boolean tintDrawable(@NonNull Context context, int i2, @NonNull Drawable drawable) {
                    if (i2 == R.drawable.abc_seekbar_track_material) {
                        LayerDrawable layerDrawable = (LayerDrawable) drawable;
                        Drawable drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(android.R.id.background);
                        int i3 = R.attr.colorControlNormal;
                        setPorterDuffColorFilter(drawableFindDrawableByLayerId, ThemeUtils.getThemeAttrColor(context, i3), AppCompatDrawableManager.DEFAULT_MODE);
                        setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress), ThemeUtils.getThemeAttrColor(context, i3), AppCompatDrawableManager.DEFAULT_MODE);
                        setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(android.R.id.progress), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), AppCompatDrawableManager.DEFAULT_MODE);
                        return true;
                    }
                    if (i2 != R.drawable.abc_ratingbar_material && i2 != R.drawable.abc_ratingbar_indicator_material && i2 != R.drawable.abc_ratingbar_small_material) {
                        return false;
                    }
                    LayerDrawable layerDrawable2 = (LayerDrawable) drawable;
                    setPorterDuffColorFilter(layerDrawable2.findDrawableByLayerId(android.R.id.background), ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal), AppCompatDrawableManager.DEFAULT_MODE);
                    Drawable drawableFindDrawableByLayerId2 = layerDrawable2.findDrawableByLayerId(android.R.id.secondaryProgress);
                    int i4 = R.attr.colorControlActivated;
                    setPorterDuffColorFilter(drawableFindDrawableByLayerId2, ThemeUtils.getThemeAttrColor(context, i4), AppCompatDrawableManager.DEFAULT_MODE);
                    setPorterDuffColorFilter(layerDrawable2.findDrawableByLayerId(android.R.id.progress), ThemeUtils.getThemeAttrColor(context, i4), AppCompatDrawableManager.DEFAULT_MODE);
                    return true;
                }

                /* JADX WARN: Removed duplicated region for block: B:22:0x0051  */
                /* JADX WARN: Removed duplicated region for block: B:29:0x006c A[RETURN] */
                @Override // androidx.appcompat.widget.ResourceManagerInternal.ResourceManagerHooks
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public boolean tintDrawableUsingColorFilter(@androidx.annotation.NonNull android.content.Context r8, int r9, @androidx.annotation.NonNull android.graphics.drawable.Drawable r10) {
                    /*
                        r7 = this;
                        android.graphics.PorterDuff$Mode r0 = androidx.appcompat.widget.AppCompatDrawableManager.access$000()
                        int[] r1 = r7.COLORFILTER_TINT_COLOR_CONTROL_NORMAL
                        boolean r1 = r7.arrayContains(r1, r9)
                        r2 = 1
                        r3 = 0
                        r4 = -1
                        if (r1 == 0) goto L15
                        int r9 = androidx.appcompat.R.attr.colorControlNormal
                    L11:
                        r1 = r0
                        r5 = r2
                    L13:
                        r0 = r4
                        goto L4f
                    L15:
                        int[] r1 = r7.COLORFILTER_COLOR_CONTROL_ACTIVATED
                        boolean r1 = r7.arrayContains(r1, r9)
                        if (r1 == 0) goto L20
                        int r9 = androidx.appcompat.R.attr.colorControlActivated
                        goto L11
                    L20:
                        int[] r1 = r7.COLORFILTER_COLOR_BACKGROUND_MULTIPLY
                        boolean r1 = r7.arrayContains(r1, r9)
                        r5 = 16842801(0x1010031, float:2.3693695E-38)
                        if (r1 == 0) goto L32
                        android.graphics.PorterDuff$Mode r0 = android.graphics.PorterDuff.Mode.MULTIPLY
                    L2d:
                        r1 = r0
                        r0 = r4
                        r9 = r5
                        r5 = r2
                        goto L4f
                    L32:
                        int r1 = androidx.appcompat.R.drawable.abc_list_divider_mtrl_alpha
                        if (r9 != r1) goto L46
                        r9 = 1109603123(0x42233333, float:40.8)
                        int r9 = java.lang.Math.round(r9)
                        r1 = 16842800(0x1010030, float:2.3693693E-38)
                        r5 = r2
                        r6 = r0
                        r0 = r9
                        r9 = r1
                        r1 = r6
                        goto L4f
                    L46:
                        int r1 = androidx.appcompat.R.drawable.abc_dialog_material_background
                        if (r9 != r1) goto L4b
                        goto L2d
                    L4b:
                        r1 = r0
                        r9 = r3
                        r5 = r9
                        goto L13
                    L4f:
                        if (r5 == 0) goto L6c
                        boolean r3 = androidx.appcompat.widget.DrawableUtils.canSafelyMutateDrawable(r10)
                        if (r3 == 0) goto L5b
                        android.graphics.drawable.Drawable r10 = r10.mutate()
                    L5b:
                        int r8 = androidx.appcompat.widget.ThemeUtils.getThemeAttrColor(r8, r9)
                        android.graphics.PorterDuffColorFilter r8 = androidx.appcompat.widget.AppCompatDrawableManager.getPorterDuffColorFilter(r8, r1)
                        r10.setColorFilter(r8)
                        if (r0 == r4) goto L6b
                        r10.setAlpha(r0)
                    L6b:
                        return r2
                    L6c:
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatDrawableManager.AnonymousClass1.tintDrawableUsingColorFilter(android.content.Context, int, android.graphics.drawable.Drawable):boolean");
                }
            });
        }
    }

    public static void tintDrawable(Drawable drawable, TintInfo tintInfo, int[] iArr) {
        ResourceManagerInternal.tintDrawable(drawable, tintInfo, iArr);
    }

    public synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes int i2) {
        return this.mResourceManager.getDrawable(context, i2);
    }

    public synchronized ColorStateList getTintList(@NonNull Context context, @DrawableRes int i2) {
        return this.mResourceManager.getTintList(context, i2);
    }

    public synchronized void onConfigurationChanged(@NonNull Context context) {
        this.mResourceManager.onConfigurationChanged(context);
    }

    public synchronized Drawable onDrawableLoadedFromResources(@NonNull Context context, @NonNull VectorEnabledTintResources vectorEnabledTintResources, @DrawableRes int i2) {
        return this.mResourceManager.onDrawableLoadedFromResources(context, vectorEnabledTintResources, i2);
    }

    public boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes int i2, @NonNull Drawable drawable) {
        return this.mResourceManager.tintDrawableUsingColorFilter(context, i2, drawable);
    }

    public synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes int i2, boolean z2) {
        return this.mResourceManager.getDrawable(context, i2, z2);
    }
}
