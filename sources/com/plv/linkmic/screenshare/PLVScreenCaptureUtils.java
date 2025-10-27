package com.plv.linkmic.screenshare;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 21)
/* loaded from: classes4.dex */
public class PLVScreenCaptureUtils {

    public interface IPLVOnRequestResultListener {
        void onRequestResult(int i2, int i3, Intent intent);
    }

    public static class PLVScreenCaptureAssistantFragment extends Fragment {
        private static final int REQUEST_CODE = 1001;
        private static IPLVOnRequestResultListener listener;
        public MediaProjectionManager mMediaProjectManager;

        public static void requestPermission(Activity activity, IPLVOnRequestResultListener iPLVOnRequestResultListener) {
            listener = iPLVOnRequestResultListener;
            FragmentManager fragmentManager = activity.getFragmentManager();
            if (fragmentManager.findFragmentByTag(activity.getLocalClassName()) == null) {
                fragmentManager.beginTransaction().add(new PLVScreenCaptureAssistantFragment(), activity.getLocalClassName()).commitAllowingStateLoss();
            }
        }

        @Override // android.app.Fragment
        public void onActivityCreated(@Nullable Bundle bundle) {
            super.onActivityCreated(bundle);
            if (this.mMediaProjectManager == null) {
                this.mMediaProjectManager = (MediaProjectionManager) getActivity().getSystemService("media_projection");
            }
            MediaProjectionManager mediaProjectionManager = this.mMediaProjectManager;
            if (mediaProjectionManager != null) {
                startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 1001);
            }
        }

        @Override // android.app.Fragment
        public void onActivityResult(int i2, int i3, Intent intent) {
            IPLVOnRequestResultListener iPLVOnRequestResultListener = listener;
            if (iPLVOnRequestResultListener != null) {
                iPLVOnRequestResultListener.onRequestResult(i2, i3, intent);
                listener = null;
            }
            getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        }
    }

    public static void requestCapture(Activity activity, IPLVOnRequestResultListener iPLVOnRequestResultListener) {
        PLVScreenCaptureAssistantFragment.requestPermission(activity, iPLVOnRequestResultListener);
    }
}
