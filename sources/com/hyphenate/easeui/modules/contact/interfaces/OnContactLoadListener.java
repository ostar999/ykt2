package com.hyphenate.easeui.modules.contact.interfaces;

import com.hyphenate.easeui.domain.EaseUser;
import java.util.List;

/* loaded from: classes4.dex */
public interface OnContactLoadListener {
    void loadDataFail(String str);

    void loadDataFinish(List<EaseUser> list);
}
