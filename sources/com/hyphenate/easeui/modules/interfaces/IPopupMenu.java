package com.hyphenate.easeui.modules.interfaces;

import com.hyphenate.easeui.modules.menu.EasePopupMenuHelper;
import com.hyphenate.easeui.modules.menu.OnPopupMenuDismissListener;
import com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener;
import com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener;

/* loaded from: classes4.dex */
public interface IPopupMenu {
    void addItemMenu(int i2, int i3, int i4, String str);

    void clearMenu();

    void findItemVisible(int i2, boolean z2);

    EasePopupMenuHelper getMenuHelper();

    void setOnPopupMenuDismissListener(OnPopupMenuDismissListener onPopupMenuDismissListener);

    void setOnPopupMenuItemClickListener(OnPopupMenuItemClickListener onPopupMenuItemClickListener);

    void setOnPopupMenuPreShowListener(OnPopupMenuPreShowListener onPopupMenuPreShowListener);
}
