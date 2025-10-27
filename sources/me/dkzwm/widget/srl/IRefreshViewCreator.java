package me.dkzwm.widget.srl;

import me.dkzwm.widget.srl.extra.IRefreshView;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes9.dex */
public interface IRefreshViewCreator {
    IRefreshView<IIndicator> createFooter(SmoothRefreshLayout smoothRefreshLayout);

    IRefreshView<IIndicator> createHeader(SmoothRefreshLayout smoothRefreshLayout);
}
