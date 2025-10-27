package com.google.android.gms.dynamic;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ConnectionErrorMessages;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.LinkedList;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class DeferredLifecycleHelper<T extends LifecycleDelegate> {
    private T zaru;
    private Bundle zarv;
    private LinkedList<zaa> zarw;
    private final OnDelegateCreatedListener<T> zarx = new com.google.android.gms.dynamic.zaa(this);

    public interface zaa {
        int getState();

        void zaa(LifecycleDelegate lifecycleDelegate);
    }

    @KeepForSdk
    public DeferredLifecycleHelper() {
    }

    @KeepForSdk
    public static void showGooglePlayUnavailableMessage(FrameLayout frameLayout) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int iIsGooglePlayServicesAvailable = googleApiAvailability.isGooglePlayServicesAvailable(context);
        String errorMessage = ConnectionErrorMessages.getErrorMessage(context, iIsGooglePlayServicesAvailable);
        String errorDialogButtonMessage = ConnectionErrorMessages.getErrorDialogButtonMessage(context, iIsGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(errorMessage);
        linearLayout.addView(textView);
        Intent errorResolutionIntent = googleApiAvailability.getErrorResolutionIntent(context, iIsGooglePlayServicesAvailable, null);
        if (errorResolutionIntent != null) {
            Button button = new Button(context);
            button.setId(R.id.button1);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(errorDialogButtonMessage);
            linearLayout.addView(button);
            button.setOnClickListener(new zad(context, errorResolutionIntent));
        }
    }

    private final void zaa(Bundle bundle, zaa zaaVar) {
        T t2 = this.zaru;
        if (t2 != null) {
            zaaVar.zaa(t2);
            return;
        }
        if (this.zarw == null) {
            this.zarw = new LinkedList<>();
        }
        this.zarw.add(zaaVar);
        if (bundle != null) {
            Bundle bundle2 = this.zarv;
            if (bundle2 == null) {
                this.zarv = (Bundle) bundle.clone();
            } else {
                bundle2.putAll(bundle);
            }
        }
        createDelegate(this.zarx);
    }

    private final void zal(int i2) {
        while (!this.zarw.isEmpty() && this.zarw.getLast().getState() >= i2) {
            this.zarw.removeLast();
        }
    }

    @KeepForSdk
    public abstract void createDelegate(OnDelegateCreatedListener<T> onDelegateCreatedListener);

    @KeepForSdk
    public T getDelegate() {
        return this.zaru;
    }

    @KeepForSdk
    public void handleGooglePlayUnavailable(FrameLayout frameLayout) {
        showGooglePlayUnavailableMessage(frameLayout);
    }

    @KeepForSdk
    public void onCreate(Bundle bundle) {
        zaa(bundle, new zab(this, bundle));
    }

    @KeepForSdk
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        zaa(bundle, new zae(this, frameLayout, layoutInflater, viewGroup, bundle));
        if (this.zaru == null) {
            handleGooglePlayUnavailable(frameLayout);
        }
        return frameLayout;
    }

    @KeepForSdk
    public void onDestroy() {
        T t2 = this.zaru;
        if (t2 != null) {
            t2.onDestroy();
        } else {
            zal(1);
        }
    }

    @KeepForSdk
    public void onDestroyView() {
        T t2 = this.zaru;
        if (t2 != null) {
            t2.onDestroyView();
        } else {
            zal(2);
        }
    }

    @KeepForSdk
    public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        zaa(bundle2, new zac(this, activity, bundle, bundle2));
    }

    @KeepForSdk
    public void onLowMemory() {
        T t2 = this.zaru;
        if (t2 != null) {
            t2.onLowMemory();
        }
    }

    @KeepForSdk
    public void onPause() {
        T t2 = this.zaru;
        if (t2 != null) {
            t2.onPause();
        } else {
            zal(5);
        }
    }

    @KeepForSdk
    public void onResume() {
        zaa((Bundle) null, new zaf(this));
    }

    @KeepForSdk
    public void onSaveInstanceState(Bundle bundle) {
        T t2 = this.zaru;
        if (t2 != null) {
            t2.onSaveInstanceState(bundle);
            return;
        }
        Bundle bundle2 = this.zarv;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
    }

    @KeepForSdk
    public void onStart() {
        zaa((Bundle) null, new zag(this));
    }

    @KeepForSdk
    public void onStop() {
        T t2 = this.zaru;
        if (t2 != null) {
            t2.onStop();
        } else {
            zal(4);
        }
    }

    public static /* synthetic */ Bundle zaa(DeferredLifecycleHelper deferredLifecycleHelper, Bundle bundle) {
        deferredLifecycleHelper.zarv = null;
        return null;
    }
}
