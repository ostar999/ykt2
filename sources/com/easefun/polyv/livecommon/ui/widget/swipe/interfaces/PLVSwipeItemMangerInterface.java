package com.easefun.polyv.livecommon.ui.widget.swipe.interfaces;

import com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout;
import com.easefun.polyv.livecommon.ui.widget.swipe.util.PLVAttributes;
import java.util.List;

/* loaded from: classes3.dex */
public interface PLVSwipeItemMangerInterface {
    void closeAllExcept(PLVSwipeLayout layout);

    void closeAllItems();

    void closeItem(int position);

    PLVAttributes.Mode getMode();

    List<Integer> getOpenItems();

    List<PLVSwipeLayout> getOpenLayouts();

    boolean isOpen(int position);

    void openItem(int position);

    void removeShownLayouts(PLVSwipeLayout layout);

    void setMode(PLVAttributes.Mode mode);
}
