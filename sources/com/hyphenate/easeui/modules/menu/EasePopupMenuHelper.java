package com.hyphenate.easeui.modules.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import com.hyphenate.easeui.utils.DarkModeHelper;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EasePopupMenuHelper implements PopupMenu.OnMenuItemClickListener, PopupMenu.OnDismissListener {
    private OnPopupMenuDismissListener dismissListener;
    private OnPopupMenuItemClickListener itemClickListener;
    private Menu menu;
    private List<MenuItemBean> menuItems;
    private PopupMenu pMenu;
    private View targetView;

    public EasePopupMenuHelper() {
        ArrayList arrayList = new ArrayList();
        this.menuItems = arrayList;
        arrayList.clear();
    }

    private void addMenuItem(Context context) {
        if (this.menuItems.isEmpty()) {
            return;
        }
        for (MenuItemBean menuItemBean : this.menuItems) {
            if (this.menu.findItem(menuItemBean.getItemId()) == null) {
                this.menu.add(menuItemBean.getGroupId(), menuItemBean.getItemId(), menuItemBean.getOrder(), menuItemBean.getTitle());
                SpannableString spannableString = new SpannableString(menuItemBean.getTitle());
                if (DarkModeHelper.isDarkMode(context)) {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#7380A9")), 0, spannableString.length(), 0);
                } else {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#303030")), 0, spannableString.length(), 0);
                }
                this.menu.findItem(menuItemBean.getItemId()).setTitle(spannableString);
            }
        }
    }

    public static float dip2px(Context context, float f2) {
        return TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    public void addItemMenu(MenuItemBean menuItemBean) {
        if (this.menuItems.contains(menuItemBean)) {
            return;
        }
        this.menuItems.add(menuItemBean);
    }

    public void clear() {
        this.menuItems.clear();
    }

    public void findItemVisible(int i2, boolean z2) {
        Menu menu = this.menu;
        if (menu == null) {
            throw new NullPointerException("PopupMenu must init first!");
        }
        try {
            menu.findItem(i2).setVisible(z2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initMenu(@NonNull View view, @Nullable Context context) {
        this.targetView = view;
        PopupMenu popupMenu = new PopupMenu(view.getContext(), this.targetView);
        this.pMenu = popupMenu;
        this.menu = popupMenu.getMenu();
        this.pMenu.setOnMenuItemClickListener(this);
        this.pMenu.setOnDismissListener(this);
        addMenuItem(context);
    }

    @Override // androidx.appcompat.widget.PopupMenu.OnDismissListener
    public void onDismiss(PopupMenu popupMenu) {
        OnPopupMenuDismissListener onPopupMenuDismissListener = this.dismissListener;
        if (onPopupMenuDismissListener != null) {
            onPopupMenuDismissListener.onDismiss(popupMenu);
        }
    }

    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem menuItem) {
        OnPopupMenuItemClickListener onPopupMenuItemClickListener = this.itemClickListener;
        if (onPopupMenuItemClickListener != null) {
            return onPopupMenuItemClickListener.onMenuItemClick(menuItem, -1);
        }
        return false;
    }

    public void setOnPopupMenuDismissListener(OnPopupMenuDismissListener onPopupMenuDismissListener) {
        this.dismissListener = onPopupMenuDismissListener;
    }

    public void setOnPopupMenuItemClickListener(OnPopupMenuItemClickListener onPopupMenuItemClickListener) {
        this.itemClickListener = onPopupMenuItemClickListener;
    }

    public void show(Context context) throws NoSuchFieldException, SecurityException {
        show(context, 0, 0);
    }

    @SuppressLint({"RestrictedApi"})
    public void show(Context context, int i2, int i3) throws NoSuchFieldException, SecurityException {
        if (this.menu == null) {
            throw new NullPointerException("PopupMenu must init first!");
        }
        addMenuItem(context);
        try {
            Field declaredField = this.pMenu.getClass().getDeclaredField("mPopup");
            declaredField.setAccessible(true);
            MenuPopupHelper menuPopupHelper = (MenuPopupHelper) declaredField.get(this.pMenu);
            Log.e("TAG", "show menu x = " + i2 + " y = " + i3);
            menuPopupHelper.show(i2, i3);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        }
    }

    public void addItemMenu(int i2, int i3, int i4, String str) {
        MenuItemBean menuItemBean = new MenuItemBean(i2, i3, i4, str);
        if (this.menuItems.contains(menuItemBean)) {
            return;
        }
        this.menuItems.add(menuItemBean);
    }
}
