package net.tsz.afinal;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import java.lang.reflect.Field;
import net.tsz.afinal.annotation.view.EventListener;
import net.tsz.afinal.annotation.view.Select;
import net.tsz.afinal.annotation.view.ViewInject;

/* loaded from: classes9.dex */
public class FinalActivity extends Activity {
    public static void initInjectedView(Activity activity) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        initInjectedView(activity, activity.getWindow().getDecorView());
    }

    private static void setItemClickListener(Object obj, Field field, String str) throws IllegalAccessException, IllegalArgumentException {
        try {
            Object obj2 = field.get(obj);
            if (obj2 instanceof AbsListView) {
                ((AbsListView) obj2).setOnItemClickListener(new EventListener(obj).itemClick(str));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void setItemLongClickListener(Object obj, Field field, String str) throws IllegalAccessException, IllegalArgumentException {
        try {
            Object obj2 = field.get(obj);
            if (obj2 instanceof AbsListView) {
                ((AbsListView) obj2).setOnItemLongClickListener(new EventListener(obj).itemLongClick(str));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void setViewClickListener(Object obj, Field field, String str) throws IllegalAccessException, IllegalArgumentException {
        try {
            Object obj2 = field.get(obj);
            if (obj2 instanceof View) {
                ((View) obj2).setOnClickListener(new EventListener(obj).click(str));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void setViewLongClickListener(Object obj, Field field, String str) throws IllegalAccessException, IllegalArgumentException {
        try {
            Object obj2 = field.get(obj);
            if (obj2 instanceof View) {
                ((View) obj2).setOnLongClickListener(new EventListener(obj).longClick(str));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void setViewSelectListener(Object obj, Field field, String str, String str2) throws IllegalAccessException, IllegalArgumentException {
        try {
            Object obj2 = field.get(obj);
            if (obj2 instanceof View) {
                ((AbsListView) obj2).setOnItemSelectedListener(new EventListener(obj).select(str).noSelect(str2));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    public void setContentView(int i2) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        super.setContentView(i2);
        initInjectedView(this);
    }

    public static void initInjectedView(Object obj, View view) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        if (declaredFields == null || declaredFields.length <= 0) {
            return;
        }
        for (Field field : declaredFields) {
            ViewInject viewInject = (ViewInject) field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int iId = viewInject.id();
                try {
                    field.setAccessible(true);
                    field.set(obj, view.findViewById(iId));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                String strClick = viewInject.click();
                if (!TextUtils.isEmpty(strClick)) {
                    setViewClickListener(view, field, strClick);
                }
                String strLongClick = viewInject.longClick();
                if (!TextUtils.isEmpty(strLongClick)) {
                    setViewLongClickListener(view, field, strLongClick);
                }
                String strItemClick = viewInject.itemClick();
                if (!TextUtils.isEmpty(strItemClick)) {
                    setItemClickListener(view, field, strItemClick);
                }
                String strItemLongClick = viewInject.itemLongClick();
                if (!TextUtils.isEmpty(strItemLongClick)) {
                    setItemLongClickListener(view, field, strItemLongClick);
                }
                Select select = viewInject.select();
                if (!TextUtils.isEmpty(select.selected())) {
                    setViewSelectListener(view, field, select.selected(), select.noSelected());
                }
            }
        }
    }

    @Override // android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        super.setContentView(view, layoutParams);
        initInjectedView(this);
    }

    @Override // android.app.Activity
    public void setContentView(View view) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        super.setContentView(view);
        initInjectedView(this);
    }
}
