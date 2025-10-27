package com.easefun.polyv.livecommon.module.utils.rotaion;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVOrientationManager {
    private static volatile PLVOrientationManager singleton;
    private PLVRotationObserver lastRotationObserver;
    private boolean isStartObserving = false;
    private List<PLVRotationObserver> rotationObservers = new ArrayList();
    private List<OnRequestedOrientationListener> orientationListeners = new ArrayList();
    private List<OnConfigurationChangedListener> configurationChangedListeners = new ArrayList();

    public interface OnConfigurationChangedListener {
        void onCall(Context context, boolean isLandscape);
    }

    public interface OnRequestedOrientationListener {
        void onCall(Context context, boolean isRequestedLandscape);
    }

    private PLVOrientationManager() {
    }

    public static PLVOrientationManager getInstance() {
        if (singleton == null) {
            synchronized (PLVOrientationManager.class) {
                if (singleton == null) {
                    singleton = new PLVOrientationManager();
                }
            }
        }
        return singleton;
    }

    private void updateLastRationObserver() {
        if (this.rotationObservers.isEmpty()) {
            this.lastRotationObserver = null;
        } else {
            this.lastRotationObserver = this.rotationObservers.get(r0.size() - 1);
        }
        if (this.isStartObserving) {
            start();
        }
    }

    public void addOnConfigurationChangedListener(OnConfigurationChangedListener listener) {
        if (listener == null || this.configurationChangedListeners.contains(listener)) {
            return;
        }
        this.configurationChangedListeners.add(listener);
    }

    public void addOnRequestedOrientationListener(OnRequestedOrientationListener listener) {
        if (listener == null || this.orientationListeners.contains(listener)) {
            return;
        }
        this.orientationListeners.add(listener);
    }

    public void addRotationObserver(final PLVRotationObserver observer, boolean enableRotation) {
        if (observer == null || this.rotationObservers.contains(observer)) {
            return;
        }
        observer.setRotationEnabled(enableRotation);
        observer.setOrientationListener(new PLVOrientationListener.OrientationListener() { // from class: com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager.1
            @Override // com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationListener.OrientationListener
            public void onOrientationChanged(PLVOrientationListener.Orientation orientation) {
                if (orientation.isLandscape()) {
                    PLVOrientationManager.this.setLandscape(observer.getContext(), orientation.isReverse());
                } else {
                    PLVOrientationManager.this.setPortrait(observer.getContext(), orientation.isReverse());
                }
            }
        });
        this.rotationObservers.add(observer);
        updateLastRationObserver();
    }

    public void lockOrientation() {
        PLVRotationObserver pLVRotationObserver = this.lastRotationObserver;
        if (pLVRotationObserver != null) {
            pLVRotationObserver.lockOrientation();
        }
    }

    public void notifyConfigurationChanged(Activity activity, Configuration newConfig) {
        List<OnConfigurationChangedListener> list = this.configurationChangedListeners;
        if (list != null) {
            Iterator<OnConfigurationChangedListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().onCall(activity, newConfig.orientation == 2);
            }
        }
    }

    public void removeOnConfigurationChangedListener(OnConfigurationChangedListener listener) {
        this.configurationChangedListeners.remove(listener);
    }

    public void removeOnRequestedOrientationListener(OnRequestedOrientationListener listener) {
        this.orientationListeners.remove(listener);
    }

    public void removeRotationObserver(PLVRotationObserver observer) {
        this.rotationObservers.remove(observer);
        observer.stop(true);
        updateLastRationObserver();
    }

    public void setLandscape(Activity activity) {
        setLandscape(activity, false);
    }

    public void setPortrait(Activity activity) {
        setPortrait(activity, false);
    }

    public void start() {
        PLVRotationObserver pLVRotationObserver = this.lastRotationObserver;
        if (pLVRotationObserver != null) {
            pLVRotationObserver.start();
        }
        this.isStartObserving = true;
    }

    public void stop() {
        stop(true);
    }

    public void unlockOrientation() {
        PLVRotationObserver pLVRotationObserver = this.lastRotationObserver;
        if (pLVRotationObserver != null) {
            pLVRotationObserver.unlockOrientation();
        }
    }

    public void setLandscape(Activity activity, boolean isReverse) {
        PLVRotationObserver pLVRotationObserver = this.lastRotationObserver;
        if (pLVRotationObserver == null || !pLVRotationObserver.isLockOrientation()) {
            activity.setRequestedOrientation(isReverse ? 8 : 6);
            List<OnRequestedOrientationListener> list = this.orientationListeners;
            if (list != null) {
                Iterator<OnRequestedOrientationListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onCall(activity, true);
                }
            }
        }
    }

    public void setPortrait(Activity activity, boolean isReverse) {
        PLVRotationObserver pLVRotationObserver = this.lastRotationObserver;
        if (pLVRotationObserver == null || !pLVRotationObserver.isLockOrientation()) {
            activity.setRequestedOrientation(isReverse ? 9 : 7);
            List<OnRequestedOrientationListener> list = this.orientationListeners;
            if (list != null) {
                Iterator<OnRequestedOrientationListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onCall(activity, false);
                }
            }
        }
    }

    public void stop(boolean isLifecycleStop) {
        PLVRotationObserver pLVRotationObserver = this.lastRotationObserver;
        if (pLVRotationObserver != null) {
            pLVRotationObserver.stop(isLifecycleStop);
        }
        this.isStartObserving = false;
    }
}
