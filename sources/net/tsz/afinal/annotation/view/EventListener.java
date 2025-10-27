package net.tsz.afinal.annotation.view;

import android.view.View;
import android.widget.AdapterView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.tsz.afinal.exception.ViewException;

/* loaded from: classes9.dex */
public class EventListener implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemLongClickListener {
    private String clickMethod;
    private Object handler;
    private String itemClickMethod;
    private String itemLongClickMehtod;
    private String itemSelectMethod;
    private String longClickMethod;
    private String nothingSelectedMethod;

    public EventListener(Object obj) {
        this.handler = obj;
    }

    private static Object invokeClickMethod(Object obj, String str, Object... objArr) throws NoSuchMethodException, SecurityException {
        if (obj == null) {
            return null;
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, View.class);
            if (declaredMethod != null) {
                return declaredMethod.invoke(obj, objArr);
            }
            throw new ViewException("no such method:" + str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static Object invokeItemClickMethod(Object obj, String str, Object... objArr) throws NoSuchMethodException, SecurityException {
        if (obj == null) {
            return null;
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, AdapterView.class, View.class, Integer.TYPE, Long.TYPE);
            if (declaredMethod != null) {
                return declaredMethod.invoke(obj, objArr);
            }
            throw new ViewException("no such method:" + str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static boolean invokeItemLongClickMethod(Object obj, String str, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (obj == null) {
            throw new ViewException("invokeItemLongClickMethod: handler is null :");
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, AdapterView.class, View.class, Integer.TYPE, Long.TYPE);
            if (declaredMethod != null) {
                Object objInvoke = declaredMethod.invoke(obj, objArr);
                return Boolean.valueOf(objInvoke == null ? false : Boolean.valueOf(objInvoke.toString()).booleanValue()).booleanValue();
            }
            throw new ViewException("no such method:" + str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static Object invokeItemSelectMethod(Object obj, String str, Object... objArr) throws NoSuchMethodException, SecurityException {
        if (obj == null) {
            return null;
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, AdapterView.class, View.class, Integer.TYPE, Long.TYPE);
            if (declaredMethod != null) {
                return declaredMethod.invoke(obj, objArr);
            }
            throw new ViewException("no such method:" + str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static boolean invokeLongClickMethod(Object obj, String str, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (obj == null) {
            return false;
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, View.class);
            if (declaredMethod != null) {
                Object objInvoke = declaredMethod.invoke(obj, objArr);
                if (objInvoke == null) {
                    return false;
                }
                return Boolean.valueOf(objInvoke.toString()).booleanValue();
            }
            throw new ViewException("no such method:" + str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static Object invokeNoSelectMethod(Object obj, String str, Object... objArr) throws NoSuchMethodException, SecurityException {
        if (obj == null) {
            return null;
        }
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, AdapterView.class);
            if (declaredMethod != null) {
                return declaredMethod.invoke(obj, objArr);
            }
            throw new ViewException("no such method:" + str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public EventListener click(String str) {
        this.clickMethod = str;
        return this;
    }

    public EventListener itemClick(String str) {
        this.itemClickMethod = str;
        return this;
    }

    public EventListener itemLongClick(String str) {
        this.itemLongClickMehtod = str;
        return this;
    }

    public EventListener longClick(String str) {
        this.longClickMethod = str;
        return this;
    }

    public EventListener noSelect(String str) {
        this.nothingSelectedMethod = str;
        return this;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws NoSuchMethodException, SecurityException {
        invokeClickMethod(this.handler, this.clickMethod, view);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) throws NoSuchMethodException, SecurityException {
        invokeItemClickMethod(this.handler, this.itemClickMethod, adapterView, view, Integer.valueOf(i2), Long.valueOf(j2));
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i2, long j2) {
        return invokeItemLongClickMethod(this.handler, this.itemLongClickMehtod, adapterView, view, Integer.valueOf(i2), Long.valueOf(j2));
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j2) throws NoSuchMethodException, SecurityException {
        invokeItemSelectMethod(this.handler, this.itemSelectMethod, adapterView, view, Integer.valueOf(i2), Long.valueOf(j2));
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        return invokeLongClickMethod(this.handler, this.longClickMethod, view);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) throws NoSuchMethodException, SecurityException {
        invokeNoSelectMethod(this.handler, this.nothingSelectedMethod, adapterView);
    }

    public EventListener select(String str) {
        this.itemSelectMethod = str;
        return this;
    }
}
