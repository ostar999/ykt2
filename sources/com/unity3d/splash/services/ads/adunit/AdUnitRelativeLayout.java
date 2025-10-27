package com.unity3d.splash.services.ads.adunit;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class AdUnitRelativeLayout extends RelativeLayout {
    private int _maxEvents;
    private final ArrayList _motionEvents;
    private boolean _shouldCapture;

    public AdUnitRelativeLayout(Context context) {
        super(context);
        this._motionEvents = new ArrayList();
        this._maxEvents = 10000;
        this._shouldCapture = false;
    }

    public void clearCapture() {
        synchronized (this._motionEvents) {
            this._motionEvents.clear();
        }
    }

    public void endCapture() {
        this._shouldCapture = false;
    }

    public int getCurrentEventCount() {
        int size;
        synchronized (this._motionEvents) {
            size = this._motionEvents.size();
        }
        return size;
    }

    public SparseIntArray getEventCount(ArrayList arrayList) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        synchronized (this._motionEvents) {
            Iterator it = this._motionEvents.iterator();
            while (it.hasNext()) {
                AdUnitMotionEvent adUnitMotionEvent = (AdUnitMotionEvent) it.next();
                Iterator it2 = arrayList.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        Integer num = (Integer) it2.next();
                        if (adUnitMotionEvent.getAction() == num.intValue()) {
                            sparseIntArray.put(num.intValue(), sparseIntArray.get(num.intValue(), 0) + 1);
                            break;
                        }
                    }
                }
            }
        }
        return sparseIntArray;
    }

    public SparseArray getEvents(SparseArray sparseArray) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        SparseArray sparseArray2 = new SparseArray();
        synchronized (this._motionEvents) {
            Iterator it = this._motionEvents.iterator();
            while (it.hasNext()) {
                AdUnitMotionEvent adUnitMotionEvent = (AdUnitMotionEvent) it.next();
                ArrayList arrayList = (ArrayList) sparseArray.get(adUnitMotionEvent.getAction());
                if (arrayList != null) {
                    int iIntValue = ((Integer) arrayList.get(0)).intValue();
                    if (sparseIntArray.get(adUnitMotionEvent.getAction(), 0) == iIntValue) {
                        if (sparseArray2.get(adUnitMotionEvent.getAction()) == null) {
                            sparseArray2.put(adUnitMotionEvent.getAction(), new SparseArray());
                        }
                        ((SparseArray) sparseArray2.get(adUnitMotionEvent.getAction())).put(iIntValue, adUnitMotionEvent);
                        arrayList.remove(0);
                    }
                    sparseIntArray.put(adUnitMotionEvent.getAction(), sparseIntArray.get(adUnitMotionEvent.getAction()) + 1);
                }
            }
        }
        return sparseArray2;
    }

    public int getMaxEventCount() {
        return this._maxEvents;
    }

    @Override // android.view.ViewGroup
    @TargetApi(14)
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        super.onInterceptTouchEvent(motionEvent);
        if (!this._shouldCapture || this._motionEvents.size() >= this._maxEvents) {
            return false;
        }
        boolean z2 = (motionEvent.getFlags() & 1) != 0;
        synchronized (this._motionEvents) {
            this._motionEvents.add(new AdUnitMotionEvent(motionEvent.getActionMasked(), z2, motionEvent.getToolType(0), motionEvent.getSource(), motionEvent.getDeviceId(), motionEvent.getX(0), motionEvent.getY(0), motionEvent.getEventTime(), motionEvent.getPressure(0), motionEvent.getSize(0)));
        }
        return false;
    }

    public void startCapture(int i2) {
        this._maxEvents = i2;
        this._shouldCapture = true;
    }
}
