package com.plv.foundationsdk.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class PLVVoidFragment extends Fragment {
    private static final int PERMISSION_DENIED = -1;
    private static final int PERMISSION_GRANT = 0;
    private static final String S_BUNDLE_PERMISSIONS = "bundle_permissions";
    private static final int S_REQUEST_CODE = 100;
    private static final String TAG = "PLVVoidFragment";
    private Activity mActivity;
    private PLVOnPermissionCallback mCallback;

    public static PLVVoidFragment newInstance(ArrayList<String> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(S_BUNDLE_PERMISSIONS, arrayList);
        PLVVoidFragment pLVVoidFragment = new PLVVoidFragment();
        pLVVoidFragment.setArguments(bundle);
        return pLVVoidFragment;
    }

    private void removeVoidFragment() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Bundle arguments = getArguments();
        if (arguments == null) {
            Log.e(TAG, "没有要请求的权限");
            removeVoidFragment();
            this.mCallback.onAllGranted();
            return;
        }
        ArrayList<String> stringArrayList = arguments.getStringArrayList(S_BUNDLE_PERMISSIONS);
        if (stringArrayList != null) {
            requestPermissions((String[]) stringArrayList.toArray(new String[0]), 100);
            return;
        }
        Log.e(TAG, "没有要请求的权限");
        removeVoidFragment();
        this.mCallback.onAllGranted();
    }

    @Override // android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mActivity = (Activity) context;
        }
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i2, @NotNull String[] strArr, @NotNull int[] iArr) {
        boolean z2;
        super.onRequestPermissionsResult(i2, strArr, iArr);
        removeVoidFragment();
        int length = iArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                z2 = true;
                break;
            } else {
                if (iArr[i3] == -1) {
                    z2 = false;
                    break;
                }
                i3++;
            }
        }
        if (z2) {
            this.mCallback.onAllGranted();
            return;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        for (int i4 = 0; i4 < iArr.length; i4++) {
            String str = strArr[i4];
            if (iArr[i4] != -1) {
                arrayList.add(str);
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this.mActivity, str)) {
                arrayList2.add(str);
            } else {
                arrayList3.add(str);
            }
        }
        this.mCallback.onPartialGranted(arrayList, arrayList2, arrayList3);
    }

    public void setCallback(PLVOnPermissionCallback pLVOnPermissionCallback) {
        this.mCallback = pLVOnPermissionCallback;
    }
}
