package com.plv.socket.eventbus.ppt;

import com.plv.foundationsdk.rx.PLVRxBus;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import io.reactivex.Observable;

/* loaded from: classes5.dex */
public class PLVOnSliceStartEventBus {
    public static Observable<PLVOnSliceStartEvent> get() {
        return PLVRxBus.get().toObservable(PLVOnSliceStartEvent.class);
    }

    public static void post(PLVOnSliceStartEvent pLVOnSliceStartEvent) {
        PLVRxBus.get().post(pLVOnSliceStartEvent);
    }
}
