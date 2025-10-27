package com.plv.foundationsdk.permission;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public interface PLVOnPermissionCallback {
    void onAllGranted();

    void onPartialGranted(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3);
}
