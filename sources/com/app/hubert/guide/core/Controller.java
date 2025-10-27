package com.app.hubert.guide.core;

import android.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.GuideLayout;
import com.app.hubert.guide.lifecycle.FragmentLifecycleAdapter;
import com.app.hubert.guide.lifecycle.ListenerFragment;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.util.LogUtil;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.List;

/* loaded from: classes2.dex */
public class Controller {
    private static final String LISTENER_FRAGMENT = "listener_fragment";
    private Activity activity;
    private boolean alwaysShow;
    private int current;
    private GuideLayout currentLayout;
    private Fragment fragment;
    private List<GuidePage> guidePages;
    private int indexOfChild;
    private boolean isShowing;
    private String label;
    private FrameLayout mParentView;
    private OnGuideChangedListener onGuideChangedListener;
    private OnPageChangedListener onPageChangedListener;
    private int showCounts;
    private SharedPreferences sp;

    public Controller(Builder builder) {
        this.indexOfChild = -1;
        Activity activity = builder.activity;
        this.activity = activity;
        this.fragment = builder.fragment;
        this.onGuideChangedListener = builder.onGuideChangedListener;
        this.onPageChangedListener = builder.onPageChangedListener;
        this.label = builder.label;
        this.alwaysShow = builder.alwaysShow;
        this.guidePages = builder.guidePages;
        this.showCounts = builder.showCounts;
        View viewFindViewById = builder.anchor;
        viewFindViewById = viewFindViewById == null ? activity.findViewById(R.id.content) : viewFindViewById;
        if (viewFindViewById instanceof FrameLayout) {
            this.mParentView = (FrameLayout) viewFindViewById;
        } else {
            FrameLayout frameLayout = new FrameLayout(this.activity);
            ViewGroup viewGroup = (ViewGroup) viewFindViewById.getParent();
            this.indexOfChild = viewGroup.indexOfChild(viewFindViewById);
            viewGroup.removeView(viewFindViewById);
            int i2 = this.indexOfChild;
            if (i2 >= 0) {
                viewGroup.addView(frameLayout, i2, viewFindViewById.getLayoutParams());
            } else {
                viewGroup.addView(frameLayout, viewFindViewById.getLayoutParams());
            }
            frameLayout.addView(viewFindViewById, new ViewGroup.LayoutParams(-1, -1));
            this.mParentView = frameLayout;
        }
        this.sp = this.activity.getSharedPreferences(NewbieGuide.TAG, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addListenerFragment() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Fragment fragment = this.fragment;
        if (fragment != null) {
            compatibleFragment(fragment);
            FragmentManager childFragmentManager = this.fragment.getChildFragmentManager();
            ListenerFragment listenerFragment = (ListenerFragment) childFragmentManager.findFragmentByTag(LISTENER_FRAGMENT);
            if (listenerFragment == null) {
                listenerFragment = new ListenerFragment();
                childFragmentManager.beginTransaction().add(listenerFragment, LISTENER_FRAGMENT).commitAllowingStateLoss();
            }
            listenerFragment.setFragmentLifecycle(new FragmentLifecycleAdapter() { // from class: com.app.hubert.guide.core.Controller.4
                @Override // com.app.hubert.guide.lifecycle.FragmentLifecycleAdapter, com.app.hubert.guide.lifecycle.FragmentLifecycle
                public void onDestroyView() {
                    LogUtil.i("ListenerFragment.onDestroyView");
                    Controller.this.remove();
                }
            });
        }
    }

    private void compatibleFragment(Fragment fragment) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = Fragment.class.getDeclaredField("mChildFragmentManager");
            declaredField.setAccessible(true);
            declaredField.set(fragment, null);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (NoSuchFieldException e3) {
            throw new RuntimeException(e3);
        }
    }

    private void removeListenerFragment() {
        Fragment fragment = this.fragment;
        if (fragment != null) {
            FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            ListenerFragment listenerFragment = (ListenerFragment) childFragmentManager.findFragmentByTag(LISTENER_FRAGMENT);
            if (listenerFragment != null) {
                childFragmentManager.beginTransaction().remove(listenerFragment).commitAllowingStateLoss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGuidePage() {
        GuideLayout guideLayout = new GuideLayout(this.activity, this.guidePages.get(this.current), this);
        guideLayout.setOnGuideLayoutDismissListener(new GuideLayout.OnGuideLayoutDismissListener() { // from class: com.app.hubert.guide.core.Controller.3
            @Override // com.app.hubert.guide.core.GuideLayout.OnGuideLayoutDismissListener
            public void onGuideLayoutDismiss(GuideLayout guideLayout2) {
                Controller.this.showNextOrRemove();
            }
        });
        this.mParentView.addView(guideLayout, new FrameLayout.LayoutParams(-1, -1));
        this.currentLayout = guideLayout;
        OnPageChangedListener onPageChangedListener = this.onPageChangedListener;
        if (onPageChangedListener != null) {
            onPageChangedListener.onPageChanged(this.current);
        }
        this.isShowing = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNextOrRemove() {
        if (this.current < this.guidePages.size() - 1) {
            this.current++;
            showGuidePage();
            return;
        }
        OnGuideChangedListener onGuideChangedListener = this.onGuideChangedListener;
        if (onGuideChangedListener != null) {
            onGuideChangedListener.onRemoved(this);
        }
        removeListenerFragment();
        this.isShowing = false;
    }

    public boolean isShowing() {
        return this.isShowing;
    }

    public void remove() {
        GuideLayout guideLayout = this.currentLayout;
        if (guideLayout != null && guideLayout.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) this.currentLayout.getParent();
            viewGroup.removeView(this.currentLayout);
            if (!(viewGroup instanceof FrameLayout)) {
                ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
                View childAt = viewGroup.getChildAt(0);
                viewGroup.removeAllViews();
                if (childAt != null) {
                    int i2 = this.indexOfChild;
                    if (i2 > 0) {
                        viewGroup2.addView(childAt, i2, viewGroup.getLayoutParams());
                    } else {
                        viewGroup2.addView(childAt, viewGroup.getLayoutParams());
                    }
                }
            }
            OnGuideChangedListener onGuideChangedListener = this.onGuideChangedListener;
            if (onGuideChangedListener != null) {
                onGuideChangedListener.onRemoved(this);
            }
            this.currentLayout = null;
        }
        this.isShowing = false;
    }

    public void resetLabel() {
        resetLabel(this.label);
    }

    public void show() {
        final int i2 = this.sp.getInt(this.label, 0);
        if ((this.alwaysShow || i2 < this.showCounts) && !this.isShowing) {
            this.isShowing = true;
            this.mParentView.post(new Runnable() { // from class: com.app.hubert.guide.core.Controller.1
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    if (Controller.this.guidePages == null || Controller.this.guidePages.size() == 0) {
                        throw new IllegalStateException("there is no guide to show!! Please add at least one Page.");
                    }
                    Controller.this.current = 0;
                    Controller.this.showGuidePage();
                    if (Controller.this.onGuideChangedListener != null) {
                        Controller.this.onGuideChangedListener.onShowed(Controller.this);
                    }
                    Controller.this.addListenerFragment();
                    Controller.this.sp.edit().putInt(Controller.this.label, i2 + 1).apply();
                }
            });
        }
    }

    public void showPage(int i2) {
        if (i2 < 0 || i2 > this.guidePages.size() - 1) {
            throw new InvalidParameterException("The Guide page position is out of range. current:" + i2 + ", range: [ 0, " + this.guidePages.size() + " )");
        }
        if (this.current == i2) {
            return;
        }
        this.current = i2;
        GuideLayout guideLayout = this.currentLayout;
        if (guideLayout == null) {
            showGuidePage();
        } else {
            guideLayout.setOnGuideLayoutDismissListener(new GuideLayout.OnGuideLayoutDismissListener() { // from class: com.app.hubert.guide.core.Controller.2
                @Override // com.app.hubert.guide.core.GuideLayout.OnGuideLayoutDismissListener
                public void onGuideLayoutDismiss(GuideLayout guideLayout2) {
                    Controller.this.showGuidePage();
                }
            });
            this.currentLayout.remove();
        }
    }

    public void showPreviewPage() {
        int i2 = this.current - 1;
        this.current = i2;
        showPage(i2);
    }

    public void resetLabel(String str) {
        this.sp.edit().putInt(str, 0).apply();
    }
}
