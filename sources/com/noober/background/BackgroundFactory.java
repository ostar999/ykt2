package com.noober.background;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class BackgroundFactory implements LayoutInflater.Factory2 {
    private LayoutInflater.Factory mViewCreateFactory;
    private LayoutInflater.Factory2 mViewCreateFactory2;
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final Object[] mConstructorArgs = new Object[2];
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final HashMap<String, HashMap<String, Method>> methodMap = new HashMap<>();

    private static View createView(Context context, String str, String str2) throws InflateException, NoSuchMethodException, SecurityException {
        String str3;
        Map<String, Constructor<? extends View>> map = sConstructorMap;
        Constructor<? extends View> constructor = map.get(str);
        if (constructor == null) {
            try {
                ClassLoader classLoader = context.getClassLoader();
                if (str2 != null) {
                    str3 = str2 + str;
                } else {
                    str3 = str;
                }
                constructor = classLoader.loadClass(str3).asSubclass(View.class).getConstructor(sConstructorSignature);
                map.put(str, constructor);
            } catch (Exception unused) {
                Log.w("BackgroundLibrary", "cannot create 【" + str + "】 : ");
                return null;
            }
        }
        constructor.setAccessible(true);
        return constructor.newInstance(mConstructorArgs);
    }

    private static View createViewFromTag(Context context, String str, AttributeSet attributeSet) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        try {
            try {
                Object[] objArr = mConstructorArgs;
                objArr[0] = context;
                objArr[1] = attributeSet;
                if (-1 != str.indexOf(46)) {
                    View viewCreateView = createView(context, str, null);
                    objArr[0] = null;
                    objArr[1] = null;
                    return viewCreateView;
                }
                View viewCreateView2 = "View".equals(str) ? createView(context, str, "android.view.") : null;
                if (viewCreateView2 == null) {
                    viewCreateView2 = createView(context, str, "android.widget.");
                }
                if (viewCreateView2 == null) {
                    viewCreateView2 = createView(context, str, "android.webkit.");
                }
                objArr[0] = null;
                objArr[1] = null;
                return viewCreateView2;
            } catch (Exception unused) {
                Log.w("BackgroundLibrary", "cannot create 【" + str + "】 : ");
                Object[] objArr2 = mConstructorArgs;
                objArr2[0] = null;
                objArr2[1] = null;
                return null;
            }
        } catch (Throwable th) {
            Object[] objArr3 = mConstructorArgs;
            objArr3[0] = null;
            objArr3[1] = null;
            throw th;
        }
    }

    private static Method findDeclaredMethod(Class cls, String str) throws NoSuchMethodException, SecurityException {
        Method declaredMethod = null;
        try {
            declaredMethod = cls.getDeclaredMethod(str, new Class[0]);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (NoSuchMethodException unused) {
            return cls.getSuperclass() != null ? findDeclaredMethod(cls.getSuperclass(), str) : declaredMethod;
        }
    }

    private static Method findMethod(Class cls, String str) {
        try {
            return cls.getMethod(str, new Class[0]);
        } catch (NoSuchMethodException unused) {
            return findDeclaredMethod(cls, str);
        }
    }

    private static Method getMethod(Class cls, String str) {
        Method methodFindMethod;
        HashMap<String, HashMap<String, Method>> map = methodMap;
        HashMap<String, Method> map2 = map.get(cls.getCanonicalName());
        if (map2 != null) {
            methodFindMethod = map.get(cls.getCanonicalName()).get(str);
        } else {
            map2 = new HashMap<>();
            map.put(cls.getCanonicalName(), map2);
            methodFindMethod = null;
        }
        if (methodFindMethod == null && (methodFindMethod = findMethod(cls, str)) != null) {
            map2.put(str, methodFindMethod);
        }
        return methodFindMethod;
    }

    private static boolean hasGradientState(TypedArray typedArray) {
        return typedArray.hasValue(R.styleable.background_bl_checkable_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_checked_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_enabled_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_selected_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_pressed_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_focused_gradient_startColor);
    }

    private static boolean hasStatus(int i2, int i3) {
        return (i2 & i3) == i3;
    }

    private static void setBackground(Drawable drawable, View view, TypedArray typedArray) {
        int i2 = R.styleable.background_bl_stroke_width;
        float f2 = 0.0f;
        if (typedArray.hasValue(i2)) {
            int i3 = R.styleable.background_bl_stroke_position;
            if (typedArray.hasValue(i3)) {
                float dimension = typedArray.getDimension(i2, 0.0f);
                int i4 = typedArray.getInt(i3, 0);
                float f3 = hasStatus(i4, 2) ? 0.0f : -dimension;
                float f4 = hasStatus(i4, 4) ? 0.0f : -dimension;
                float f5 = hasStatus(i4, 8) ? 0.0f : -dimension;
                float f6 = hasStatus(i4, 16) ? 0.0f : -dimension;
                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable});
                layerDrawable.setLayerInset(0, (int) f3, (int) f4, (int) f5, (int) f6);
                drawable = layerDrawable;
            }
        }
        int i5 = R.styleable.background_bl_shape_alpha;
        if (typedArray.hasValue(i5)) {
            float f7 = typedArray.getFloat(i5, 0.0f);
            if (f7 >= 1.0f) {
                f2 = 255.0f;
            } else if (f7 > 0.0f) {
                f2 = f7 * 255.0f;
            }
            drawable.setAlpha((int) f2);
        }
        view.setBackground(drawable);
    }

    private static void setDrawable(Drawable drawable, View view, TypedArray typedArray, TypedArray typedArray2) {
        if (!(view instanceof TextView)) {
            setBackground(drawable, view, typedArray2);
            return;
        }
        int i2 = R.styleable.bl_other_bl_position;
        if (!typedArray.hasValue(i2)) {
            setBackground(drawable, view, typedArray2);
            return;
        }
        if (typedArray.getInt(i2, 0) == 1) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(drawable, null, null, null);
            return;
        }
        if (typedArray.getInt(i2, 0) == 2) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(null, drawable, null, null);
        } else if (typedArray.getInt(i2, 0) == 4) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(null, null, drawable, null);
        } else if (typedArray.getInt(i2, 0) == 8) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(null, null, null, drawable);
        }
    }

    @Nullable
    public static View setViewBackground(Context context, AttributeSet attributeSet, View view) {
        return setViewBackground(null, context, attributeSet, view);
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View viewOnCreateView = null;
        if (str.startsWith("com.noober.background.view")) {
            return null;
        }
        LayoutInflater.Factory2 factory2 = this.mViewCreateFactory2;
        if (factory2 != null) {
            View viewOnCreateView2 = factory2.onCreateView(str, context, attributeSet);
            viewOnCreateView = viewOnCreateView2 == null ? this.mViewCreateFactory2.onCreateView(null, str, context, attributeSet) : viewOnCreateView2;
        } else {
            LayoutInflater.Factory factory = this.mViewCreateFactory;
            if (factory != null) {
                viewOnCreateView = factory.onCreateView(str, context, attributeSet);
            }
        }
        return setViewBackground(str, context, attributeSet, viewOnCreateView);
    }

    public void setInterceptFactory(LayoutInflater.Factory factory) {
        this.mViewCreateFactory = factory;
    }

    public void setInterceptFactory2(LayoutInflater.Factory2 factory2) {
        this.mViewCreateFactory2 = factory2;
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x0173 A[Catch: Exception -> 0x0214, all -> 0x0216, TryCatch #0 {all -> 0x0216, blocks: (B:3:0x0040, B:5:0x0046, B:7:0x004c, B:9:0x0052, B:11:0x0058, B:13:0x005e, B:15:0x0064, B:17:0x006a, B:19:0x0070, B:25:0x0099, B:32:0x00c2, B:34:0x00c8, B:37:0x00cf, B:38:0x00d6, B:39:0x00d7, B:41:0x00dd, B:44:0x00e4, B:45:0x00eb, B:46:0x00ec, B:48:0x00f3, B:50:0x00f7, B:73:0x016f, B:75:0x0173, B:77:0x0179, B:88:0x01a9, B:90:0x01b2, B:92:0x01ba, B:95:0x01c1, B:96:0x01cd, B:98:0x01d5, B:100:0x01df, B:102:0x01ed, B:78:0x0184, B:80:0x0188, B:82:0x018e, B:83:0x0199, B:85:0x019d, B:87:0x01a3, B:51:0x0103, B:53:0x0109, B:55:0x0112, B:57:0x0118, B:58:0x0129, B:60:0x012f, B:61:0x0137, B:63:0x013d, B:65:0x0143, B:66:0x014b, B:67:0x0155, B:69:0x015b, B:71:0x016a, B:111:0x021b), top: B:116:0x0040 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0184 A[Catch: Exception -> 0x0214, all -> 0x0216, TryCatch #0 {all -> 0x0216, blocks: (B:3:0x0040, B:5:0x0046, B:7:0x004c, B:9:0x0052, B:11:0x0058, B:13:0x005e, B:15:0x0064, B:17:0x006a, B:19:0x0070, B:25:0x0099, B:32:0x00c2, B:34:0x00c8, B:37:0x00cf, B:38:0x00d6, B:39:0x00d7, B:41:0x00dd, B:44:0x00e4, B:45:0x00eb, B:46:0x00ec, B:48:0x00f3, B:50:0x00f7, B:73:0x016f, B:75:0x0173, B:77:0x0179, B:88:0x01a9, B:90:0x01b2, B:92:0x01ba, B:95:0x01c1, B:96:0x01cd, B:98:0x01d5, B:100:0x01df, B:102:0x01ed, B:78:0x0184, B:80:0x0188, B:82:0x018e, B:83:0x0199, B:85:0x019d, B:87:0x01a3, B:51:0x0103, B:53:0x0109, B:55:0x0112, B:57:0x0118, B:58:0x0129, B:60:0x012f, B:61:0x0137, B:63:0x013d, B:65:0x0143, B:66:0x014b, B:67:0x0155, B:69:0x015b, B:71:0x016a, B:111:0x021b), top: B:116:0x0040 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0199 A[Catch: Exception -> 0x0214, all -> 0x0216, TryCatch #0 {all -> 0x0216, blocks: (B:3:0x0040, B:5:0x0046, B:7:0x004c, B:9:0x0052, B:11:0x0058, B:13:0x005e, B:15:0x0064, B:17:0x006a, B:19:0x0070, B:25:0x0099, B:32:0x00c2, B:34:0x00c8, B:37:0x00cf, B:38:0x00d6, B:39:0x00d7, B:41:0x00dd, B:44:0x00e4, B:45:0x00eb, B:46:0x00ec, B:48:0x00f3, B:50:0x00f7, B:73:0x016f, B:75:0x0173, B:77:0x0179, B:88:0x01a9, B:90:0x01b2, B:92:0x01ba, B:95:0x01c1, B:96:0x01cd, B:98:0x01d5, B:100:0x01df, B:102:0x01ed, B:78:0x0184, B:80:0x0188, B:82:0x018e, B:83:0x0199, B:85:0x019d, B:87:0x01a3, B:51:0x0103, B:53:0x0109, B:55:0x0112, B:57:0x0118, B:58:0x0129, B:60:0x012f, B:61:0x0137, B:63:0x013d, B:65:0x0143, B:66:0x014b, B:67:0x0155, B:69:0x015b, B:71:0x016a, B:111:0x021b), top: B:116:0x0040 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01b2 A[Catch: Exception -> 0x0214, all -> 0x0216, TryCatch #0 {all -> 0x0216, blocks: (B:3:0x0040, B:5:0x0046, B:7:0x004c, B:9:0x0052, B:11:0x0058, B:13:0x005e, B:15:0x0064, B:17:0x006a, B:19:0x0070, B:25:0x0099, B:32:0x00c2, B:34:0x00c8, B:37:0x00cf, B:38:0x00d6, B:39:0x00d7, B:41:0x00dd, B:44:0x00e4, B:45:0x00eb, B:46:0x00ec, B:48:0x00f3, B:50:0x00f7, B:73:0x016f, B:75:0x0173, B:77:0x0179, B:88:0x01a9, B:90:0x01b2, B:92:0x01ba, B:95:0x01c1, B:96:0x01cd, B:98:0x01d5, B:100:0x01df, B:102:0x01ed, B:78:0x0184, B:80:0x0188, B:82:0x018e, B:83:0x0199, B:85:0x019d, B:87:0x01a3, B:51:0x0103, B:53:0x0109, B:55:0x0112, B:57:0x0118, B:58:0x0129, B:60:0x012f, B:61:0x0137, B:63:0x013d, B:65:0x0143, B:66:0x014b, B:67:0x0155, B:69:0x015b, B:71:0x016a, B:111:0x021b), top: B:116:0x0040 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01d5 A[Catch: Exception -> 0x0214, all -> 0x0216, TryCatch #0 {all -> 0x0216, blocks: (B:3:0x0040, B:5:0x0046, B:7:0x004c, B:9:0x0052, B:11:0x0058, B:13:0x005e, B:15:0x0064, B:17:0x006a, B:19:0x0070, B:25:0x0099, B:32:0x00c2, B:34:0x00c8, B:37:0x00cf, B:38:0x00d6, B:39:0x00d7, B:41:0x00dd, B:44:0x00e4, B:45:0x00eb, B:46:0x00ec, B:48:0x00f3, B:50:0x00f7, B:73:0x016f, B:75:0x0173, B:77:0x0179, B:88:0x01a9, B:90:0x01b2, B:92:0x01ba, B:95:0x01c1, B:96:0x01cd, B:98:0x01d5, B:100:0x01df, B:102:0x01ed, B:78:0x0184, B:80:0x0188, B:82:0x018e, B:83:0x0199, B:85:0x019d, B:87:0x01a3, B:51:0x0103, B:53:0x0109, B:55:0x0112, B:57:0x0118, B:58:0x0129, B:60:0x012f, B:61:0x0137, B:63:0x013d, B:65:0x0143, B:66:0x014b, B:67:0x0155, B:69:0x015b, B:71:0x016a, B:111:0x021b), top: B:116:0x0040 }] */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.view.View setViewBackground(java.lang.String r17, android.content.Context r18, android.util.AttributeSet r19, android.view.View r20) {
        /*
            Method dump skipped, instructions count: 604
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.noober.background.BackgroundFactory.setViewBackground(java.lang.String, android.content.Context, android.util.AttributeSet, android.view.View):android.view.View");
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return onCreateView(str, context, attributeSet);
    }
}
