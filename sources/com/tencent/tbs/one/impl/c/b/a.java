package com.tencent.tbs.one.impl.c.b;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import androidx.camera.core.CameraInfo;
import com.tencent.tbs.one.impl.a.g;
import java.lang.reflect.Constructor;
import java.util.HashMap;

/* loaded from: classes6.dex */
public final class a extends LayoutInflater {

    /* renamed from: b, reason: collision with root package name */
    public static final StackTraceElement[] f21932b = new StackTraceElement[0];

    /* renamed from: c, reason: collision with root package name */
    public static final Class<?>[] f21933c = {Context.class, AttributeSet.class};

    /* renamed from: a, reason: collision with root package name */
    public ClassLoader f21934a;

    /* renamed from: d, reason: collision with root package name */
    public final Object[] f21935d;

    /* renamed from: e, reason: collision with root package name */
    public final HashMap<String, Constructor<? extends View>> f21936e;

    /* renamed from: f, reason: collision with root package name */
    public HashMap<String, Boolean> f21937f;

    public a(Context context) {
        super(context);
        this.f21935d = new Object[2];
        this.f21936e = new HashMap<>();
        a();
    }

    public a(LayoutInflater layoutInflater, Context context) {
        super(layoutInflater, context);
        this.f21935d = new Object[2];
        this.f21936e = new HashMap<>();
        a();
    }

    private Class<? extends View> a(String str) {
        ClassLoader classLoader = this.f21934a;
        if (classLoader != null) {
            try {
                return classLoader.loadClass(str).asSubclass(View.class);
            } catch (ClassNotFoundException unused) {
            }
        }
        return a.class.getClassLoader().loadClass(str).asSubclass(View.class);
    }

    private void a() {
        try {
            setFactory2(new LayoutInflater.Factory2() { // from class: com.tencent.tbs.one.impl.c.b.a.1
                @Override // android.view.LayoutInflater.Factory2
                public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
                    return a.this.a(str, attributeSet);
                }

                @Override // android.view.LayoutInflater.Factory
                public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
                    return null;
                }
            });
        } catch (Exception unused) {
            setFactory(new LayoutInflater.Factory() { // from class: com.tencent.tbs.one.impl.c.b.a.2
                @Override // android.view.LayoutInflater.Factory
                public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
                    return a.this.a(str, attributeSet);
                }
            });
        }
    }

    private View b(String str, AttributeSet attributeSet) {
        View viewNewInstance;
        synchronized (this.f21935d) {
            boolean z2 = false;
            this.f21935d[0] = getContext();
            Constructor<? extends View> constructor = this.f21936e.get(str);
            Class<? extends View> clsA = null;
            try {
                try {
                    try {
                        try {
                            LayoutInflater.Filter filter = getFilter();
                            if (constructor == null) {
                                clsA = a(str);
                                if (filter != null && clsA != null && !filter.onLoadClass(clsA)) {
                                    c(str, attributeSet);
                                }
                                constructor = clsA.getConstructor(f21933c);
                                constructor.setAccessible(true);
                                this.f21936e.put(str, constructor);
                            } else if (filter != null) {
                                Boolean bool = this.f21937f.get(str);
                                if (bool == null) {
                                    clsA = a(str);
                                    if (clsA != null && filter.onLoadClass(clsA)) {
                                        z2 = true;
                                    }
                                    this.f21937f.put(str, Boolean.valueOf(z2));
                                    if (!z2) {
                                        c(str, attributeSet);
                                    }
                                } else if (bool.equals(Boolean.FALSE)) {
                                    c(str, attributeSet);
                                }
                            }
                            Object[] objArr = this.f21935d;
                            objArr[1] = attributeSet;
                            viewNewInstance = constructor.newInstance(objArr);
                            if (viewNewInstance instanceof ViewStub) {
                                ((ViewStub) viewNewInstance).setLayoutInflater(this);
                            }
                        } catch (Exception unused) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(attributeSet.getPositionDescription());
                            sb.append(": Error inflating class ");
                            sb.append(clsA == null ? CameraInfo.IMPLEMENTATION_TYPE_UNKNOWN : clsA.getName());
                            InflateException inflateException = new InflateException(sb.toString());
                            inflateException.setStackTrace(f21932b);
                            throw inflateException;
                        }
                    } catch (ClassCastException unused2) {
                        InflateException inflateException2 = new InflateException(attributeSet.getPositionDescription() + ": Class is not a View " + str);
                        inflateException2.setStackTrace(f21932b);
                        throw inflateException2;
                    }
                } catch (NoSuchMethodException e2) {
                    InflateException inflateException3 = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str, e2);
                    inflateException3.setStackTrace(f21932b);
                    throw inflateException3;
                }
            } catch (ClassNotFoundException e3) {
                throw e3;
            }
        }
        return viewNewInstance;
    }

    public static void c(String str, AttributeSet attributeSet) {
        throw new InflateException(attributeSet.getPositionDescription() + ": Class not allowed to be inflated " + str);
    }

    public final View a(String str, AttributeSet attributeSet) {
        View viewB;
        if (-1 != str.indexOf(46)) {
            try {
                viewB = b(str, attributeSet);
            } catch (Throwable th) {
                g.b("Failed to create view %s", str, th);
            }
        } else {
            viewB = null;
        }
        if (viewB != null) {
            return viewB;
        }
        try {
            return -1 == str.indexOf(46) ? onCreateView(str, attributeSet) : createView(str, null, attributeSet);
        } catch (Throwable th2) {
            g.b("Failed to create view %s", str, th2);
            return viewB;
        }
    }

    @Override // android.view.LayoutInflater
    public final LayoutInflater cloneInContext(Context context) {
        return new a(this, context);
    }

    @Override // android.view.LayoutInflater
    public final void setFilter(LayoutInflater.Filter filter) {
        super.setFilter(filter);
        if (filter != null) {
            this.f21937f = new HashMap<>();
        }
    }
}
