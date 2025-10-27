package com.just.agentweb;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class AgentActionFragment extends Fragment {
    public static final String FRAGMENT_TAG = "AgentWebActionFragment";
    public static final String KEY_FROM_INTENTION = "KEY_FROM_INTENTION";
    public static final String KEY_URI = "KEY_URI";
    public static final int REQUEST_CODE = 596;
    private static final String TAG = "AgentActionFragment";
    private boolean isViewCreated = false;
    private Action mAction;

    public interface ChooserListener {
        void onChoiceResult(int i2, int i3, Intent intent);
    }

    public interface PermissionListener {
        void onRequestPermissionsResult(@NonNull String[] strArr, @NonNull int[] iArr, Bundle bundle);
    }

    public interface RationaleListener {
        void onRationaleResult(boolean z2, Bundle bundle);
    }

    private void captureCamera() {
        try {
            if (this.mAction.getChooserListener() == null) {
                resetAction();
                return;
            }
            File fileCreateImageFile = AgentWebUtils.createImageFile(getActivity());
            if (fileCreateImageFile == null) {
                this.mAction.getChooserListener().onChoiceResult(596, 0, null);
            }
            Intent intentCaptureCompat = AgentWebUtils.getIntentCaptureCompat(getActivity(), fileCreateImageFile);
            this.mAction.setUri((Uri) intentCaptureCompat.getParcelableExtra("output"));
            startActivityForResult(intentCaptureCompat, 596);
        } catch (Throwable th) {
            LogUtils.e(TAG, "找不到系统相机");
            if (this.mAction.getChooserListener() != null) {
                this.mAction.getChooserListener().onChoiceResult(596, 0, null);
            }
            resetAction();
            if (LogUtils.isDebug()) {
                th.printStackTrace();
            }
        }
    }

    private void choose() {
        try {
            if (this.mAction.getChooserListener() == null) {
                return;
            }
            Intent intent = this.mAction.getIntent();
            if (intent == null) {
                resetAction();
            } else {
                startActivityForResult(intent, 596);
            }
        } catch (Throwable th) {
            LogUtils.i(TAG, "找不到文件选择器");
            chooserActionCallback(-1, null);
            if (LogUtils.isDebug()) {
                th.printStackTrace();
            }
        }
    }

    private void chooserActionCallback(int i2, Intent intent) {
        if (this.mAction.getChooserListener() != null) {
            this.mAction.getChooserListener().onChoiceResult(596, i2, intent);
        }
        resetAction();
    }

    private void recordVideo() {
        try {
            if (this.mAction.getChooserListener() == null) {
                resetAction();
                return;
            }
            File fileCreateVideoFile = AgentWebUtils.createVideoFile(getActivity());
            if (fileCreateVideoFile == null) {
                this.mAction.getChooserListener().onChoiceResult(596, 0, null);
                resetAction();
            } else {
                Intent intentVideoCompat = AgentWebUtils.getIntentVideoCompat(getActivity(), fileCreateVideoFile);
                this.mAction.setUri((Uri) intentVideoCompat.getParcelableExtra("output"));
                startActivityForResult(intentVideoCompat, 596);
            }
        } catch (Throwable th) {
            LogUtils.e(TAG, "找不到系统相机");
            if (this.mAction.getChooserListener() != null) {
                this.mAction.getChooserListener().onChoiceResult(596, 0, null);
            }
            resetAction();
            if (LogUtils.isDebug()) {
                th.printStackTrace();
            }
        }
    }

    @RequiresApi(api = 23)
    private void requestPermission(Action action) {
        ArrayList<String> permissions = action.getPermissions();
        if (AgentWebUtils.isEmptyCollection(permissions)) {
            resetAction();
            return;
        }
        boolean zShouldShowRequestPermissionRationale = false;
        if (this.mAction.getRationaleListener() == null) {
            if (this.mAction.getPermissionListener() != null) {
                requestPermissions((String[]) permissions.toArray(new String[0]), 1);
            }
        } else {
            Iterator<String> it = permissions.iterator();
            while (it.hasNext() && !(zShouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale(it.next()))) {
            }
            this.mAction.getRationaleListener().onRationaleResult(zShouldShowRequestPermissionRationale, new Bundle());
            resetAction();
        }
    }

    private void resetAction() {
    }

    private void runAction() {
        Action action = this.mAction;
        if (action == null) {
            resetAction();
            return;
        }
        if (action.getAction() == 1) {
            requestPermission(this.mAction);
            return;
        }
        if (this.mAction.getAction() == 3) {
            captureCamera();
        } else if (this.mAction.getAction() == 4) {
            recordVideo();
        } else {
            choose();
        }
    }

    public static void start(Activity activity, Action action) {
        FragmentManager supportFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
        AgentActionFragment agentActionFragment = (AgentActionFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (agentActionFragment == null) {
            agentActionFragment = new AgentActionFragment();
            supportFragmentManager.beginTransaction().add(agentActionFragment, FRAGMENT_TAG).commitAllowingStateLoss();
        }
        agentActionFragment.mAction = action;
        if (agentActionFragment.isViewCreated) {
            agentActionFragment.runAction();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        Action action = this.mAction;
        if (action == null) {
            return;
        }
        if (i2 == 596) {
            if (action.getUri() != null) {
                chooserActionCallback(i3, new Intent().putExtra(KEY_URI, this.mAction.getUri()));
            } else {
                chooserActionCallback(i3, intent);
            }
        }
        resetAction();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.isViewCreated = true;
            runAction();
            return;
        }
        LogUtils.i(TAG, "savedInstanceState:" + bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (this.mAction.getPermissionListener() != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_FROM_INTENTION, this.mAction.getFromIntention());
            this.mAction.getPermissionListener().onRequestPermissionsResult(strArr, iArr, bundle);
        }
        resetAction();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }
}
