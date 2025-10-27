package com.hjq.permissions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
final class StartActivityManager {
    private static final String SUB_INTENT_KEY = "sub_intent_key";

    public interface IStartActivityDelegate {
        void startActivity(@NonNull Intent intent);

        void startActivityForResult(@NonNull Intent intent, int i2);
    }

    public static class StartActivityDelegateActivityImpl implements IStartActivityDelegate {
        private final Activity mActivity;

        @Override // com.hjq.permissions.StartActivityManager.IStartActivityDelegate
        public void startActivity(@NonNull Intent intent) {
            this.mActivity.startActivity(intent);
        }

        @Override // com.hjq.permissions.StartActivityManager.IStartActivityDelegate
        public void startActivityForResult(@NonNull Intent intent, int i2) {
            this.mActivity.startActivityForResult(intent, i2);
        }

        private StartActivityDelegateActivityImpl(@NonNull Activity activity) {
            this.mActivity = activity;
        }
    }

    public static class StartActivityDelegateContextImpl implements IStartActivityDelegate {
        private final Context mContext;

        @Override // com.hjq.permissions.StartActivityManager.IStartActivityDelegate
        public void startActivity(@NonNull Intent intent) {
            this.mContext.startActivity(intent);
        }

        @Override // com.hjq.permissions.StartActivityManager.IStartActivityDelegate
        public void startActivityForResult(@NonNull Intent intent, int i2) {
            Activity activityFindActivity = PermissionUtils.findActivity(this.mContext);
            if (activityFindActivity != null) {
                activityFindActivity.startActivityForResult(intent, i2);
            } else {
                startActivity(intent);
            }
        }

        private StartActivityDelegateContextImpl(@NonNull Context context) {
            this.mContext = context;
        }
    }

    public static class StartActivityDelegateFragmentImpl implements IStartActivityDelegate {
        private final Fragment mFragment;

        @Override // com.hjq.permissions.StartActivityManager.IStartActivityDelegate
        public void startActivity(@NonNull Intent intent) {
            this.mFragment.startActivity(intent);
        }

        @Override // com.hjq.permissions.StartActivityManager.IStartActivityDelegate
        public void startActivityForResult(@NonNull Intent intent, int i2) {
            this.mFragment.startActivityForResult(intent, i2);
        }

        private StartActivityDelegateFragmentImpl(@NonNull Fragment fragment) {
            this.mFragment = fragment;
        }
    }

    public static class StartActivityDelegateSupportFragmentImpl implements IStartActivityDelegate {
        private final androidx.fragment.app.Fragment mFragment;

        @Override // com.hjq.permissions.StartActivityManager.IStartActivityDelegate
        public void startActivity(@NonNull Intent intent) {
            this.mFragment.startActivity(intent);
        }

        @Override // com.hjq.permissions.StartActivityManager.IStartActivityDelegate
        public void startActivityForResult(@NonNull Intent intent, int i2) {
            this.mFragment.startActivityForResult(intent, i2);
        }

        private StartActivityDelegateSupportFragmentImpl(@NonNull androidx.fragment.app.Fragment fragment) {
            this.mFragment = fragment;
        }
    }

    public static Intent addSubIntentToMainIntent(@Nullable Intent intent, @Nullable Intent intent2) {
        if (intent == null && intent2 != null) {
            return intent2;
        }
        if (intent2 == null) {
            return intent;
        }
        getDeepSubIntent(intent).putExtra(SUB_INTENT_KEY, intent2);
        return intent;
    }

    public static Intent getDeepSubIntent(@NonNull Intent intent) {
        Intent subIntentInMainIntent = getSubIntentInMainIntent(intent);
        return subIntentInMainIntent != null ? getDeepSubIntent(subIntentInMainIntent) : intent;
    }

    public static Intent getSubIntentInMainIntent(@NonNull Intent intent) {
        return AndroidVersion.isAndroid13() ? (Intent) intent.getParcelableExtra(SUB_INTENT_KEY, Intent.class) : (Intent) intent.getParcelableExtra(SUB_INTENT_KEY);
    }

    public static boolean startActivity(@NonNull Context context, Intent intent) {
        return startActivity(new StartActivityDelegateContextImpl(context), intent);
    }

    public static boolean startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i2) {
        return startActivityForResult(new StartActivityDelegateActivityImpl(activity), intent, i2);
    }

    public static boolean startActivity(@NonNull Activity activity, Intent intent) {
        return startActivity(new StartActivityDelegateActivityImpl(activity), intent);
    }

    public static boolean startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i2) {
        return startActivityForResult(new StartActivityDelegateFragmentImpl(fragment), intent, i2);
    }

    public static boolean startActivity(@NonNull Fragment fragment, Intent intent) {
        return startActivity(new StartActivityDelegateFragmentImpl(fragment), intent);
    }

    public static boolean startActivityForResult(@NonNull androidx.fragment.app.Fragment fragment, @NonNull Intent intent, int i2) {
        return startActivityForResult(new StartActivityDelegateSupportFragmentImpl(fragment), intent, i2);
    }

    public static boolean startActivity(@NonNull androidx.fragment.app.Fragment fragment, Intent intent) {
        return startActivity(new StartActivityDelegateSupportFragmentImpl(fragment), intent);
    }

    public static boolean startActivityForResult(@NonNull IStartActivityDelegate iStartActivityDelegate, @NonNull Intent intent, int i2) {
        try {
            iStartActivityDelegate.startActivityForResult(intent, i2);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            Intent subIntentInMainIntent = getSubIntentInMainIntent(intent);
            if (subIntentInMainIntent == null) {
                return false;
            }
            return startActivityForResult(iStartActivityDelegate, subIntentInMainIntent, i2);
        }
    }

    public static boolean startActivity(@NonNull IStartActivityDelegate iStartActivityDelegate, @NonNull Intent intent) {
        try {
            iStartActivityDelegate.startActivity(intent);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            Intent subIntentInMainIntent = getSubIntentInMainIntent(intent);
            if (subIntentInMainIntent == null) {
                return false;
            }
            return startActivity(iStartActivityDelegate, subIntentInMainIntent);
        }
    }
}
