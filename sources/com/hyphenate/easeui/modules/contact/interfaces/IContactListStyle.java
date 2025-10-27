package com.hyphenate.easeui.modules.contact.interfaces;

import android.graphics.drawable.Drawable;
import com.hyphenate.easeui.modules.interfaces.IAvatarSet;

/* loaded from: classes4.dex */
public interface IContactListStyle extends IAvatarSet, IContactTextStyle {
    void setHeaderBackGround(Drawable drawable);

    void setItemBackGround(Drawable drawable);

    void setItemHeight(int i2);
}
