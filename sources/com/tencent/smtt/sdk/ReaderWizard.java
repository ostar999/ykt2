package com.tencent.smtt.sdk;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.sdk.TbsReaderView;

/* loaded from: classes6.dex */
public class ReaderWizard {

    /* renamed from: a, reason: collision with root package name */
    private DexLoader f20858a;

    /* renamed from: b, reason: collision with root package name */
    private TbsReaderView.ReaderCallback f20859b;

    public ReaderWizard(TbsReaderView.ReaderCallback readerCallback) {
        this.f20858a = null;
        this.f20859b = null;
        this.f20858a = a();
        this.f20859b = readerCallback;
    }

    private static DexLoader a() {
        u uVarC = g.a(true).c();
        if (uVarC != null) {
            return uVarC.c();
        }
        return null;
    }

    public static Drawable getResDrawable(int i2) {
        DexLoader dexLoaderA = a();
        if (dexLoaderA != null) {
            Object objInvokeStaticMethod = dexLoaderA.invokeStaticMethod("com.tencent.tbs.reader.TbsReader", "getResDrawable", new Class[]{Integer.class}, Integer.valueOf(i2));
            if (objInvokeStaticMethod instanceof Drawable) {
                return (Drawable) objInvokeStaticMethod;
            }
        }
        return null;
    }

    public static String getResString(int i2) {
        DexLoader dexLoaderA = a();
        if (dexLoaderA != null) {
            Object objInvokeStaticMethod = dexLoaderA.invokeStaticMethod("com.tencent.tbs.reader.TbsReader", "getResString", new Class[]{Integer.class}, Integer.valueOf(i2));
            if (objInvokeStaticMethod instanceof String) {
                return (String) objInvokeStaticMethod;
            }
        }
        return "";
    }

    public static boolean isSupportCurrentPlatform(Context context) {
        DexLoader dexLoaderA = a();
        if (dexLoaderA == null) {
            return false;
        }
        Object objInvokeStaticMethod = dexLoaderA.invokeStaticMethod("com.tencent.tbs.reader.TbsReader", "isSupportCurrentPlatform", new Class[]{Context.class}, context);
        if (objInvokeStaticMethod instanceof Boolean) {
            return ((Boolean) objInvokeStaticMethod).booleanValue();
        }
        return false;
    }

    public static boolean isSupportExt(String str) {
        DexLoader dexLoaderA = a();
        if (dexLoaderA == null) {
            return false;
        }
        Object objInvokeStaticMethod = dexLoaderA.invokeStaticMethod("com.tencent.tbs.reader.TbsReader", "isSupportExt", new Class[]{String.class}, str);
        if (objInvokeStaticMethod instanceof Boolean) {
            return ((Boolean) objInvokeStaticMethod).booleanValue();
        }
        return false;
    }

    public boolean checkPlugin(Object obj, Context context, String str, boolean z2) {
        String str2;
        DexLoader dexLoader = this.f20858a;
        if (dexLoader == null) {
            str2 = "checkPlugin:Unexpect null object!";
        } else {
            Object objInvokeMethod = dexLoader.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "checkPlugin", new Class[]{Context.class, String.class, Boolean.class}, context, str, Boolean.valueOf(z2));
            if (objInvokeMethod instanceof Boolean) {
                return ((Boolean) objInvokeMethod).booleanValue();
            }
            str2 = "Unexpect return value type of call checkPlugin!";
        }
        Log.e("ReaderWizard", str2);
        return false;
    }

    public void destroy(Object obj) {
        this.f20859b = null;
        DexLoader dexLoader = this.f20858a;
        if (dexLoader == null || obj == null) {
            Log.e("ReaderWizard", "destroy:Unexpect null object!");
        } else {
            dexLoader.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "destroy", new Class[0], new Object[0]);
        }
    }

    public void doCommand(Object obj, Integer num, Object obj2, Object obj3) {
        DexLoader dexLoader = this.f20858a;
        if (dexLoader == null) {
            Log.e("ReaderWizard", "doCommand:Unexpect null object!");
        } else {
            dexLoader.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "doCommand", new Class[]{Integer.class, Object.class, Object.class}, new Integer(num.intValue()), obj2, obj3);
        }
    }

    public Object getTbsReader() {
        return this.f20858a.newInstance("com.tencent.tbs.reader.TbsReader", new Class[0], new Object[0]);
    }

    public boolean initTbsReader(Object obj, Context context) {
        String str;
        DexLoader dexLoader = this.f20858a;
        if (dexLoader == null || obj == null) {
            str = "initTbsReader:Unexpect null object!";
        } else {
            Object objInvokeMethod = dexLoader.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "init", new Class[]{Context.class, DexLoader.class, Object.class}, context, dexLoader, this);
            if (objInvokeMethod instanceof Boolean) {
                return ((Boolean) objInvokeMethod).booleanValue();
            }
            str = "Unexpect return value type of call initTbsReader!";
        }
        Log.e("ReaderWizard", str);
        return false;
    }

    public void onCallBackAction(Integer num, Object obj, Object obj2) {
        TbsReaderView.ReaderCallback readerCallback = this.f20859b;
        if (readerCallback != null) {
            readerCallback.onCallBackAction(num, obj, obj2);
        }
    }

    public void onSizeChanged(Object obj, int i2, int i3) {
        DexLoader dexLoader = this.f20858a;
        if (dexLoader == null) {
            Log.e("ReaderWizard", "onSizeChanged:Unexpect null object!");
        } else {
            dexLoader.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "onSizeChanged", new Class[]{Integer.class, Integer.class}, new Integer(i2), new Integer(i3));
        }
    }

    public boolean openFile(Object obj, Context context, Bundle bundle, FrameLayout frameLayout) {
        String str;
        DexLoader dexLoader = this.f20858a;
        if (dexLoader == null) {
            str = "openFile:Unexpect null object!";
        } else {
            Object objInvokeMethod = dexLoader.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "openFile", new Class[]{Context.class, Bundle.class, FrameLayout.class}, context, bundle, frameLayout);
            if (objInvokeMethod instanceof Boolean) {
                return ((Boolean) objInvokeMethod).booleanValue();
            }
            str = "Unexpect return value type of call openFile!";
        }
        Log.e("ReaderWizard", str);
        return false;
    }

    public void userStatistics(Object obj, String str) {
        DexLoader dexLoader = this.f20858a;
        if (dexLoader == null) {
            Log.e("ReaderWizard", "userStatistics:Unexpect null object!");
        } else {
            dexLoader.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "userStatistics", new Class[]{String.class}, str);
        }
    }
}
