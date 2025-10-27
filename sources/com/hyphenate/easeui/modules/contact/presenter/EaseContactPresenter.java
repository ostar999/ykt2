package com.hyphenate.easeui.modules.contact.presenter;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.modules.EaseBasePresenter;
import com.hyphenate.easeui.modules.ILoadDataView;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class EaseContactPresenter extends EaseBasePresenter {
    public IEaseContactListView mView;

    public abstract void addNote(int i2, EaseUser easeUser);

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void attachView(ILoadDataView iLoadDataView) {
        this.mView = (IEaseContactListView) iLoadDataView;
    }

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void detachView() {
        this.mView = null;
    }

    public abstract void loadData();

    @Override // com.hyphenate.easeui.modules.EaseBasePresenter
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    public abstract void sortData(List<EaseUser> list);
}
