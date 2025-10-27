package com.huawei.hms.common.internal;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.SparseArray;
import com.huawei.hms.api.HuaweiApiClient;

/* loaded from: classes4.dex */
public class AutoLifecycleFragment extends Fragment {

    /* renamed from: a, reason: collision with root package name */
    private final SparseArray<a> f7550a = new SparseArray<>();

    /* renamed from: b, reason: collision with root package name */
    private boolean f7551b;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final HuaweiApiClient f7552a;

        public a(int i2, HuaweiApiClient huaweiApiClient) {
            this.f7552a = huaweiApiClient;
        }

        public void a() {
            this.f7552a.disconnect();
        }
    }

    public static AutoLifecycleFragment getInstance(Activity activity) {
        Preconditions.checkMainThread("Must be called on the main thread");
        try {
            AutoLifecycleFragment autoLifecycleFragment = (AutoLifecycleFragment) activity.getFragmentManager().findFragmentByTag("HmsAutoLifecycleFrag");
            FragmentManager fragmentManager = activity.getFragmentManager();
            if (autoLifecycleFragment != null) {
                return autoLifecycleFragment;
            }
            AutoLifecycleFragment autoLifecycleFragment2 = new AutoLifecycleFragment();
            fragmentManager.beginTransaction().add(autoLifecycleFragment2, "HmsAutoLifecycleFrag").commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
            return autoLifecycleFragment2;
        } catch (ClassCastException e2) {
            throw new IllegalStateException("Fragment with tag HmsAutoLifecycleFrag is not a AutoLifecycleFragment", e2);
        }
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        this.f7551b = true;
        for (int i2 = 0; i2 < this.f7550a.size(); i2++) {
            this.f7550a.valueAt(i2).f7552a.connect((Activity) null);
        }
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.f7551b = false;
        for (int i2 = 0; i2 < this.f7550a.size(); i2++) {
            this.f7550a.valueAt(i2).f7552a.disconnect();
        }
    }

    public void startAutoMange(int i2, HuaweiApiClient huaweiApiClient) {
        Preconditions.checkNotNull(huaweiApiClient, "HuaweiApiClient instance cannot be null");
        Preconditions.checkState(this.f7550a.indexOfKey(i2) < 0, "Already managing a HuaweiApiClient with this clientId: " + i2);
        this.f7550a.put(i2, new a(i2, huaweiApiClient));
        if (this.f7551b) {
            huaweiApiClient.connect((Activity) null);
        }
    }

    public void stopAutoManage(int i2) {
        a aVar = this.f7550a.get(i2);
        this.f7550a.remove(i2);
        if (aVar != null) {
            aVar.a();
        }
    }
}
