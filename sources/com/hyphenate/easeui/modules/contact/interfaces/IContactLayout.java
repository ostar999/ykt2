package com.hyphenate.easeui.modules.contact.interfaces;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.hyphenate.easeui.modules.contact.EaseContactListLayout;

/* loaded from: classes4.dex */
public interface IContactLayout {
    void canUseRefresh(boolean z2);

    EaseContactListLayout getContactList();

    SwipeRefreshLayout getSwipeRefreshLayout();

    void showNormal();

    void showSimple();
}
