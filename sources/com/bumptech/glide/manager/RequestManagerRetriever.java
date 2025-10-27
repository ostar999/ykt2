package com.bumptech.glide.manager;

import android.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.GlideExperiments;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.HardwareConfigState;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class RequestManagerRetriever implements Handler.Callback {
    private static final RequestManagerFactory DEFAULT_FACTORY = new RequestManagerFactory() { // from class: com.bumptech.glide.manager.RequestManagerRetriever.1
        @Override // com.bumptech.glide.manager.RequestManagerRetriever.RequestManagerFactory
        @NonNull
        public RequestManager build(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context) {
            return new RequestManager(glide, lifecycle, requestManagerTreeNode, context);
        }
    };
    private static final String FRAGMENT_INDEX_KEY = "key";

    @VisibleForTesting
    static final String FRAGMENT_TAG = "com.bumptech.glide.manager";
    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;
    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 2;
    private static final String TAG = "RMRetriever";
    private volatile RequestManager applicationManager;
    private final RequestManagerFactory factory;
    private final FrameWaiter frameWaiter;
    private final Handler handler;

    @VisibleForTesting
    final Map<FragmentManager, RequestManagerFragment> pendingRequestManagerFragments = new HashMap();

    @VisibleForTesting
    final Map<androidx.fragment.app.FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments = new HashMap();
    private final ArrayMap<View, Fragment> tempViewToSupportFragment = new ArrayMap<>();
    private final ArrayMap<View, android.app.Fragment> tempViewToFragment = new ArrayMap<>();
    private final Bundle tempBundle = new Bundle();

    public interface RequestManagerFactory {
        @NonNull
        RequestManager build(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context);
    }

    public RequestManagerRetriever(@Nullable RequestManagerFactory requestManagerFactory, GlideExperiments glideExperiments) {
        this.factory = requestManagerFactory == null ? DEFAULT_FACTORY : requestManagerFactory;
        this.handler = new Handler(Looper.getMainLooper(), this);
        this.frameWaiter = buildFrameWaiter(glideExperiments);
    }

    @TargetApi(17)
    private static void assertNotDestroyed(@NonNull Activity activity) {
        if (activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    private static FrameWaiter buildFrameWaiter(GlideExperiments glideExperiments) {
        return (HardwareConfigState.HARDWARE_BITMAPS_SUPPORTED && HardwareConfigState.BLOCK_HARDWARE_BITMAPS_WHEN_GL_CONTEXT_MIGHT_NOT_BE_INITIALIZED) ? glideExperiments.isEnabled(GlideBuilder.WaitForFramesAfterTrimMemory.class) ? new FirstFrameAndAfterTrimMemoryWaiter() : new FirstFrameWaiter() : new DoNothingFirstFrameWaiter();
    }

    @Nullable
    private static Activity findActivity(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @TargetApi(26)
    @Deprecated
    private void findAllFragmentsWithViews(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        if (Build.VERSION.SDK_INT < 26) {
            findAllFragmentsWithViewsPreO(fragmentManager, arrayMap);
            return;
        }
        for (android.app.Fragment fragment : fragmentManager.getFragments()) {
            if (fragment.getView() != null) {
                arrayMap.put(fragment.getView(), fragment);
                findAllFragmentsWithViews(fragment.getChildFragmentManager(), arrayMap);
            }
        }
    }

    @Deprecated
    private void findAllFragmentsWithViewsPreO(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        android.app.Fragment fragment;
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            this.tempBundle.putInt(FRAGMENT_INDEX_KEY, i2);
            try {
                fragment = fragmentManager.getFragment(this.tempBundle, FRAGMENT_INDEX_KEY);
            } catch (Exception unused) {
                fragment = null;
            }
            if (fragment == null) {
                return;
            }
            if (fragment.getView() != null) {
                arrayMap.put(fragment.getView(), fragment);
                findAllFragmentsWithViews(fragment.getChildFragmentManager(), arrayMap);
            }
            i2 = i3;
        }
    }

    private static void findAllSupportFragmentsWithViews(@Nullable Collection<Fragment> collection, @NonNull Map<View, Fragment> map) {
        if (collection == null) {
            return;
        }
        for (Fragment fragment : collection) {
            if (fragment != null && fragment.getView() != null) {
                map.put(fragment.getView(), fragment);
                findAllSupportFragmentsWithViews(fragment.getChildFragmentManager().getFragments(), map);
            }
        }
    }

    @Nullable
    @Deprecated
    private android.app.Fragment findFragment(@NonNull View view, @NonNull Activity activity) {
        this.tempViewToFragment.clear();
        findAllFragmentsWithViews(activity.getFragmentManager(), this.tempViewToFragment);
        View viewFindViewById = activity.findViewById(R.id.content);
        android.app.Fragment fragment = null;
        while (!view.equals(viewFindViewById) && (fragment = this.tempViewToFragment.get(view)) == null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
        }
        this.tempViewToFragment.clear();
        return fragment;
    }

    @Nullable
    private Fragment findSupportFragment(@NonNull View view, @NonNull FragmentActivity fragmentActivity) {
        this.tempViewToSupportFragment.clear();
        findAllSupportFragmentsWithViews(fragmentActivity.getSupportFragmentManager().getFragments(), this.tempViewToSupportFragment);
        View viewFindViewById = fragmentActivity.findViewById(R.id.content);
        Fragment fragment = null;
        while (!view.equals(viewFindViewById) && (fragment = this.tempViewToSupportFragment.get(view)) == null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
        }
        this.tempViewToSupportFragment.clear();
        return fragment;
    }

    @NonNull
    @Deprecated
    private RequestManager fragmentGet(@NonNull Context context, @NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment, boolean z2) {
        RequestManagerFragment requestManagerFragment = getRequestManagerFragment(fragmentManager, fragment);
        RequestManager requestManager = requestManagerFragment.getRequestManager();
        if (requestManager == null) {
            requestManager = this.factory.build(Glide.get(context), requestManagerFragment.getGlideLifecycle(), requestManagerFragment.getRequestManagerTreeNode(), context);
            if (z2) {
                requestManager.onStart();
            }
            requestManagerFragment.setRequestManager(requestManager);
        }
        return requestManager;
    }

    @NonNull
    private RequestManager getApplicationManager(@NonNull Context context) {
        if (this.applicationManager == null) {
            synchronized (this) {
                if (this.applicationManager == null) {
                    this.applicationManager = this.factory.build(Glide.get(context.getApplicationContext()), new ApplicationLifecycle(), new EmptyRequestManagerTreeNode(), context.getApplicationContext());
                }
            }
        }
        return this.applicationManager;
    }

    private static boolean isActivityVisible(Context context) {
        Activity activityFindActivity = findActivity(context);
        return activityFindActivity == null || !activityFindActivity.isFinishing();
    }

    @NonNull
    private RequestManager supportFragmentGet(@NonNull Context context, @NonNull androidx.fragment.app.FragmentManager fragmentManager, @Nullable Fragment fragment, boolean z2) {
        SupportRequestManagerFragment supportRequestManagerFragment = getSupportRequestManagerFragment(fragmentManager, fragment);
        RequestManager requestManager = supportRequestManagerFragment.getRequestManager();
        if (requestManager == null) {
            requestManager = this.factory.build(Glide.get(context), supportRequestManagerFragment.getGlideLifecycle(), supportRequestManagerFragment.getRequestManagerTreeNode(), context);
            if (z2) {
                requestManager.onStart();
            }
            supportRequestManagerFragment.setRequestManager(requestManager);
        }
        return requestManager;
    }

    @NonNull
    public RequestManager get(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        }
        if (Util.isOnMainThread() && !(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return get((FragmentActivity) context);
            }
            if (context instanceof Activity) {
                return get((Activity) context);
            }
            if (context instanceof ContextWrapper) {
                ContextWrapper contextWrapper = (ContextWrapper) context;
                if (contextWrapper.getBaseContext().getApplicationContext() != null) {
                    return get(contextWrapper.getBaseContext());
                }
            }
        }
        return getApplicationManager(context);
    }

    @NonNull
    @Deprecated
    public RequestManagerFragment getRequestManagerFragment(Activity activity) {
        return getRequestManagerFragment(activity.getFragmentManager(), null);
    }

    @NonNull
    public SupportRequestManagerFragment getSupportRequestManagerFragment(androidx.fragment.app.FragmentManager fragmentManager) {
        return getSupportRequestManagerFragment(fragmentManager, null);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        Object obj;
        ComponentCallbacks componentCallbacksRemove;
        Object obj2;
        ComponentCallbacks componentCallbacks;
        int i2 = message.what;
        boolean z2 = true;
        if (i2 == 1) {
            obj = (FragmentManager) message.obj;
            componentCallbacksRemove = this.pendingRequestManagerFragments.remove(obj);
        } else {
            if (i2 != 2) {
                componentCallbacks = null;
                z2 = false;
                obj2 = null;
                if (z2 && componentCallbacks == null && Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Failed to remove expected request manager fragment, manager: " + obj2);
                }
                return z2;
            }
            obj = (androidx.fragment.app.FragmentManager) message.obj;
            componentCallbacksRemove = this.pendingSupportRequestManagerFragments.remove(obj);
        }
        ComponentCallbacks componentCallbacks2 = componentCallbacksRemove;
        obj2 = obj;
        componentCallbacks = componentCallbacks2;
        if (z2) {
            Log.w(TAG, "Failed to remove expected request manager fragment, manager: " + obj2);
        }
        return z2;
    }

    @NonNull
    private RequestManagerFragment getRequestManagerFragment(@NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment) {
        RequestManagerFragment requestManagerFragment = (RequestManagerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (requestManagerFragment != null) {
            return requestManagerFragment;
        }
        RequestManagerFragment requestManagerFragment2 = this.pendingRequestManagerFragments.get(fragmentManager);
        if (requestManagerFragment2 != null) {
            return requestManagerFragment2;
        }
        RequestManagerFragment requestManagerFragment3 = new RequestManagerFragment();
        requestManagerFragment3.setParentFragmentHint(fragment);
        this.pendingRequestManagerFragments.put(fragmentManager, requestManagerFragment3);
        fragmentManager.beginTransaction().add(requestManagerFragment3, FRAGMENT_TAG).commitAllowingStateLoss();
        this.handler.obtainMessage(1, fragmentManager).sendToTarget();
        return requestManagerFragment3;
    }

    @NonNull
    private SupportRequestManagerFragment getSupportRequestManagerFragment(@NonNull androidx.fragment.app.FragmentManager fragmentManager, @Nullable Fragment fragment) {
        SupportRequestManagerFragment supportRequestManagerFragment = (SupportRequestManagerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (supportRequestManagerFragment != null) {
            return supportRequestManagerFragment;
        }
        SupportRequestManagerFragment supportRequestManagerFragment2 = this.pendingSupportRequestManagerFragments.get(fragmentManager);
        if (supportRequestManagerFragment2 != null) {
            return supportRequestManagerFragment2;
        }
        SupportRequestManagerFragment supportRequestManagerFragment3 = new SupportRequestManagerFragment();
        supportRequestManagerFragment3.setParentFragmentHint(fragment);
        this.pendingSupportRequestManagerFragments.put(fragmentManager, supportRequestManagerFragment3);
        fragmentManager.beginTransaction().add(supportRequestManagerFragment3, FRAGMENT_TAG).commitAllowingStateLoss();
        this.handler.obtainMessage(2, fragmentManager).sendToTarget();
        return supportRequestManagerFragment3;
    }

    @NonNull
    public RequestManager get(@NonNull FragmentActivity fragmentActivity) {
        if (Util.isOnBackgroundThread()) {
            return get(fragmentActivity.getApplicationContext());
        }
        assertNotDestroyed(fragmentActivity);
        this.frameWaiter.registerSelf(fragmentActivity);
        return supportFragmentGet(fragmentActivity, fragmentActivity.getSupportFragmentManager(), null, isActivityVisible(fragmentActivity));
    }

    @NonNull
    public RequestManager get(@NonNull Fragment fragment) {
        Preconditions.checkNotNull(fragment.getContext(), "You cannot start a load on a fragment before it is attached or after it is destroyed");
        if (Util.isOnBackgroundThread()) {
            return get(fragment.getContext().getApplicationContext());
        }
        if (fragment.getActivity() != null) {
            this.frameWaiter.registerSelf(fragment.getActivity());
        }
        return supportFragmentGet(fragment.getContext(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
    }

    @NonNull
    public RequestManager get(@NonNull Activity activity) {
        if (Util.isOnBackgroundThread()) {
            return get(activity.getApplicationContext());
        }
        if (activity instanceof FragmentActivity) {
            return get((FragmentActivity) activity);
        }
        assertNotDestroyed(activity);
        this.frameWaiter.registerSelf(activity);
        return fragmentGet(activity, activity.getFragmentManager(), null, isActivityVisible(activity));
    }

    @NonNull
    public RequestManager get(@NonNull View view) {
        if (Util.isOnBackgroundThread()) {
            return get(view.getContext().getApplicationContext());
        }
        Preconditions.checkNotNull(view);
        Preconditions.checkNotNull(view.getContext(), "Unable to obtain a request manager for a view without a Context");
        Activity activityFindActivity = findActivity(view.getContext());
        if (activityFindActivity == null) {
            return get(view.getContext().getApplicationContext());
        }
        if (activityFindActivity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activityFindActivity;
            Fragment fragmentFindSupportFragment = findSupportFragment(view, fragmentActivity);
            return fragmentFindSupportFragment != null ? get(fragmentFindSupportFragment) : get(fragmentActivity);
        }
        android.app.Fragment fragmentFindFragment = findFragment(view, activityFindActivity);
        if (fragmentFindFragment == null) {
            return get(activityFindActivity);
        }
        return get(fragmentFindFragment);
    }

    @NonNull
    @TargetApi(17)
    @Deprecated
    public RequestManager get(@NonNull android.app.Fragment fragment) {
        if (fragment.getActivity() != null) {
            if (!Util.isOnBackgroundThread()) {
                if (fragment.getActivity() != null) {
                    this.frameWaiter.registerSelf(fragment.getActivity());
                }
                return fragmentGet(fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
            }
            return get(fragment.getActivity().getApplicationContext());
        }
        throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
    }
}
