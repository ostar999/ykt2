package com.plv.livescenes.document;

import com.plv.livescenes.document.event.PLVAbsDocumentEvent;

/* loaded from: classes4.dex */
public interface IPLVDocumentContainerView {

    public interface OnReceiveEventListener {
        void onReceive(String str, String str2);
    }

    void loadWeb();

    void sendEvent(PLVAbsDocumentEvent pLVAbsDocumentEvent);

    void setOnReceiveEventListener(OnReceiveEventListener onReceiveEventListener);

    IPLVDocumentContainerView setSessionId(String str);

    IPLVDocumentContainerView setUserType(String str);

    IPLVDocumentContainerView setViewerId(String str);

    IPLVDocumentContainerView setViewerName(String str);
}
