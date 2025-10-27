package com.petterp.floatingx.listener;

import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\b\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H&¨\u0006\f"}, d2 = {"Lcom/petterp/floatingx/listener/IFxConfigStorage;", "", PLVDocumentMarkToolType.CLEAR, "", "getX", "", "getY", "hasConfig", "", PLVUpdateMicSiteEvent.EVENT_NAME, "x", "y", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public interface IFxConfigStorage {
    void clear();

    float getX();

    float getY();

    boolean hasConfig();

    void update(float x2, float y2);
}
