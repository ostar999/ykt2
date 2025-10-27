package com.hyphenate.easeui.modules.contact.presenter;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.modules.ILoadDataView;
import java.util.List;

/* loaded from: classes4.dex */
public interface IEaseContactListView extends ILoadDataView {
    void addNote(int i2);

    void addNoteFail(int i2, String str);

    void loadContactListFail(String str);

    void loadContactListNoData();

    void loadContactListSuccess(List<EaseUser> list);

    void refreshList();

    void refreshList(int i2);

    void sortContactListSuccess(List<EaseUser> list);
}
