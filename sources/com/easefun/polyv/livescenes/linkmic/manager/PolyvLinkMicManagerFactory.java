package com.easefun.polyv.livescenes.linkmic.manager;

import com.easefun.polyv.livescenes.linkmic.IPolyvLinkMicManager;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManagerFactory;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvLinkMicManagerFactory extends PLVLinkMicManagerFactory {
    public static IPolyvLinkMicManager createNewLinkMicManager() {
        return (IPolyvLinkMicManager) PLVLinkMicManagerFactory.createNewLinkMicManager();
    }
}
