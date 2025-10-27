package com.hyphenate.easeui.modules.contact.interfaces;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.modules.contact.adapter.EaseContactListAdapter;
import com.hyphenate.easeui.modules.contact.presenter.EaseContactPresenter;
import com.hyphenate.easeui.modules.interfaces.IRecyclerView;

/* loaded from: classes4.dex */
public interface IContactListLayout extends IRecyclerView {
    EaseUser getItem(int i2);

    EaseContactListAdapter getListAdapter();

    void setPresenter(EaseContactPresenter easeContactPresenter);

    void showItemDefaultMenu(boolean z2);

    void showItemHeader(boolean z2);
}
