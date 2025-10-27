package com.plv.linkmic.processor;

import com.plv.linkmic.PLVLinkMicEventHandler;
import java.util.Collection;

/* loaded from: classes4.dex */
public interface a {
    Collection<PLVLinkMicEventHandler> a();

    void addEventHandler(PLVLinkMicEventHandler pLVLinkMicEventHandler);

    Object b();

    void destroy();

    void removeAllEventHandler();

    void removeEventHandler(PLVLinkMicEventHandler pLVLinkMicEventHandler);
}
