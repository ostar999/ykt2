package com.daimajia.swipe.interfaces;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import java.util.List;

/* loaded from: classes3.dex */
public interface SwipeItemMangerInterface {
    void closeAllExcept(SwipeLayout swipeLayout);

    void closeAllItems();

    void closeItem(int i2);

    Attributes.Mode getMode();

    List<Integer> getOpenItems();

    List<SwipeLayout> getOpenLayouts();

    boolean isOpen(int i2);

    void openItem(int i2);

    void removeShownLayouts(SwipeLayout swipeLayout);

    void setMode(Attributes.Mode mode);
}
