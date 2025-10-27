package com.hyphenate.easeui.modules.interfaces;

import com.hyphenate.easeui.modules.chat.interfaces.OnMenuChangeListener;
import com.hyphenate.easeui.modules.menu.EasePopupWindowHelper;
import com.hyphenate.easeui.modules.menu.MenuItemBean;

/* loaded from: classes4.dex */
public interface IPopupWindow {
    void addItemMenu(int i2, int i3, int i4, String str);

    void addItemMenu(MenuItemBean menuItemBean);

    void clearMenu();

    MenuItemBean findItem(int i2);

    void findItemVisible(int i2, boolean z2);

    EasePopupWindowHelper getMenuHelper();

    void setOnPopupWindowItemClickListener(OnMenuChangeListener onMenuChangeListener);

    void showItemDefaultMenu(boolean z2);
}
