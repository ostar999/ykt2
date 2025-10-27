package com.hyphenate.easeui.modules.contact.interfaces;

import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.contact.adapter.EaseContactCustomAdapter;

/* loaded from: classes4.dex */
public interface IContactCustomListLayout {
    void addCustomItem(int i2, int i3, String str);

    void addCustomItem(int i2, String str, String str2);

    EaseContactCustomAdapter getCustomAdapter();

    void setOnContactLoadListener(OnContactLoadListener onContactLoadListener);

    void setOnCustomItemClickListener(OnItemClickListener onItemClickListener);
}
