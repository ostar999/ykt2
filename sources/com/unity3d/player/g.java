package com.unity3d.player;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.unity3d.player.UnityPermissions;

/* loaded from: classes6.dex */
public final class g extends Fragment {

    /* renamed from: a, reason: collision with root package name */
    private final IPermissionRequestCallbacks f24094a;

    /* renamed from: b, reason: collision with root package name */
    private final Activity f24095b;

    /* renamed from: c, reason: collision with root package name */
    private final Looper f24096c;

    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private IPermissionRequestCallbacks f24100b;

        /* renamed from: c, reason: collision with root package name */
        private String f24101c;

        /* renamed from: d, reason: collision with root package name */
        private int f24102d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f24103e;

        public a(IPermissionRequestCallbacks iPermissionRequestCallbacks, String str, int i2, boolean z2) {
            this.f24100b = iPermissionRequestCallbacks;
            this.f24101c = str;
            this.f24102d = i2;
            this.f24103e = z2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i2 = this.f24102d;
            if (i2 != -1) {
                if (i2 == 0) {
                    this.f24100b.onPermissionGranted(this.f24101c);
                }
            } else if (Build.VERSION.SDK_INT >= 30 || this.f24103e) {
                this.f24100b.onPermissionDenied(this.f24101c);
            } else {
                this.f24100b.onPermissionDeniedAndDontAskAgain(this.f24101c);
            }
        }
    }

    public g() {
        this.f24094a = null;
        this.f24095b = null;
        this.f24096c = null;
    }

    public g(Activity activity, IPermissionRequestCallbacks iPermissionRequestCallbacks) {
        this.f24094a = iPermissionRequestCallbacks;
        this.f24095b = activity;
        this.f24096c = Looper.myLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String[] strArr) {
        for (String str : strArr) {
            this.f24094a.onPermissionDenied(str);
        }
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestPermissions(getArguments().getStringArray("PermissionNames"), 96489);
    }

    @Override // android.app.Fragment
    public final void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        if (i2 != 96489) {
            return;
        }
        if (strArr.length != 0) {
            for (int i3 = 0; i3 < strArr.length && i3 < iArr.length; i3++) {
                IPermissionRequestCallbacks iPermissionRequestCallbacks = this.f24094a;
                if (iPermissionRequestCallbacks != null && this.f24095b != null && this.f24096c != null) {
                    if (iPermissionRequestCallbacks instanceof UnityPermissions.ModalWaitForPermissionResponse) {
                        iPermissionRequestCallbacks.onPermissionGranted(strArr[i3]);
                    } else {
                        String str = strArr[i3];
                        if (str == null) {
                            str = "<null>";
                        }
                        String str2 = str;
                        new Handler(this.f24096c).post(new a(this.f24094a, str2, iArr[i3], this.f24095b.shouldShowRequestPermissionRationale(str2)));
                    }
                }
            }
        } else if (this.f24094a != null && this.f24095b != null && this.f24096c != null) {
            final String[] stringArray = getArguments().getStringArray("PermissionNames");
            if (this.f24094a instanceof UnityPermissions.ModalWaitForPermissionResponse) {
                a(stringArray);
            } else {
                new Handler(this.f24096c).post(new Runnable() { // from class: com.unity3d.player.g.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        g.this.a(stringArray);
                    }
                });
            }
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.remove(this);
        fragmentTransactionBeginTransaction.commit();
    }
}
