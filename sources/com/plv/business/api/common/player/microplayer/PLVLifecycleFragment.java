package com.plv.business.api.common.player.microplayer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/* loaded from: classes4.dex */
public class PLVLifecycleFragment extends Fragment {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10766a = "PLVLifecycleFragment";

    /* renamed from: b, reason: collision with root package name */
    private a f10767b;

    public static class a {
        public void onDestroy() {
        }

        public void onStart() {
        }

        public void onStop() {
        }
    }

    public static void a(Activity activity, a aVar) {
        PLVLifecycleFragment pLVLifecycleFragment = new PLVLifecycleFragment();
        pLVLifecycleFragment.a(aVar);
        FragmentTransaction fragmentTransactionBeginTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.add(pLVLifecycleFragment, f10766a);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        a aVar = this.f10767b;
        if (aVar != null) {
            aVar.onDestroy();
        }
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        a aVar = this.f10767b;
        if (aVar != null) {
            aVar.onStart();
        }
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        a aVar = this.f10767b;
        if (aVar != null) {
            aVar.onStop();
        }
    }

    private void a(a aVar) {
        this.f10767b = aVar;
    }
}
