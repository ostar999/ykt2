package com.hyphenate.easeui.modules.chat.interfaces;

import android.view.View;
import android.widget.PopupWindow;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.modules.menu.EasePopupWindowHelper;
import com.hyphenate.easeui.modules.menu.MenuItemBean;

/* loaded from: classes4.dex */
public interface OnMenuChangeListener {
    void onDismiss(PopupWindow popupWindow);

    boolean onMenuItemClick(MenuItemBean menuItemBean, EMMessage eMMessage);

    void onPreMenu(EasePopupWindowHelper easePopupWindowHelper, EMMessage eMMessage, View view);
}
