package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
class DefaultSpecialEffectsController extends SpecialEffectsController {

    /* renamed from: androidx.fragment.app.DefaultSpecialEffectsController$10, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class AnimationInfo extends SpecialEffectsInfo {

        @Nullable
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim;

        public AnimationInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal, boolean z2) {
            super(operation, cancellationSignal);
            this.mLoadedAnim = false;
            this.mIsPop = z2;
        }

        @Nullable
        public FragmentAnim.AnimationOrAnimator getAnimation(@NonNull Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            FragmentAnim.AnimationOrAnimator animationOrAnimatorLoadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mAnimation = animationOrAnimatorLoadAnimation;
            this.mLoadedAnim = true;
            return animationOrAnimatorLoadAnimation;
        }
    }

    public static class SpecialEffectsInfo {

        @NonNull
        private final SpecialEffectsController.Operation mOperation;

        @NonNull
        private final CancellationSignal mSignal;

        public SpecialEffectsInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal) {
            this.mOperation = operation;
            this.mSignal = cancellationSignal;
        }

        public void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }

        @NonNull
        public SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        @NonNull
        public CancellationSignal getSignal() {
            return this.mSignal;
        }

        public boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State state;
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            return stateFrom == finalState || !(stateFrom == (state = SpecialEffectsController.Operation.State.VISIBLE) || finalState == state);
        }
    }

    public DefaultSpecialEffectsController(@NonNull ViewGroup viewGroup) {
        super(viewGroup);
    }

    private void startAnimations(@NonNull List<AnimationInfo> list, @NonNull List<SpecialEffectsController.Operation> list2, boolean z2, @NonNull Map<SpecialEffectsController.Operation, Boolean> map) {
        int i2;
        boolean z3;
        Context context;
        View view;
        int i3;
        final SpecialEffectsController.Operation operation;
        final ViewGroup container = getContainer();
        Context context2 = container.getContext();
        ArrayList arrayList = new ArrayList();
        Iterator<AnimationInfo> it = list.iterator();
        boolean z4 = false;
        while (true) {
            i2 = 2;
            if (!it.hasNext()) {
                break;
            }
            final AnimationInfo next = it.next();
            if (next.isVisibilityUnchanged()) {
                next.completeSpecialEffect();
            } else {
                FragmentAnim.AnimationOrAnimator animation = next.getAnimation(context2);
                if (animation == null) {
                    next.completeSpecialEffect();
                } else {
                    final Animator animator = animation.animator;
                    if (animator == null) {
                        arrayList.add(next);
                    } else {
                        final SpecialEffectsController.Operation operation2 = next.getOperation();
                        Fragment fragment = operation2.getFragment();
                        if (Boolean.TRUE.equals(map.get(operation2))) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            next.completeSpecialEffect();
                        } else {
                            boolean z5 = operation2.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (z5) {
                                list2.remove(operation2);
                            }
                            final View view2 = fragment.mView;
                            container.startViewTransition(view2);
                            final boolean z6 = z5;
                            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.2
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    container.endViewTransition(view2);
                                    if (z6) {
                                        operation2.getFinalState().applyState(view2);
                                    }
                                    next.completeSpecialEffect();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v(FragmentManager.TAG, "Animator from operation " + operation2 + " has ended.");
                                    }
                                }
                            });
                            animator.setTarget(view2);
                            animator.start();
                            if (FragmentManager.isLoggingEnabled(2)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Animator from operation ");
                                operation = operation2;
                                sb.append(operation);
                                sb.append(" has started.");
                                Log.v(FragmentManager.TAG, sb.toString());
                            } else {
                                operation = operation2;
                            }
                            next.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.3
                                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                public void onCancel() {
                                    animator.end();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v(FragmentManager.TAG, "Animator from operation " + operation + " has been canceled.");
                                    }
                                }
                            });
                            z4 = true;
                        }
                    }
                }
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            final AnimationInfo animationInfo = (AnimationInfo) it2.next();
            final SpecialEffectsController.Operation operation3 = animationInfo.getOperation();
            Fragment fragment2 = operation3.getFragment();
            if (z2) {
                if (FragmentManager.isLoggingEnabled(i2)) {
                    Log.v(FragmentManager.TAG, "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo.completeSpecialEffect();
            } else if (z4) {
                if (FragmentManager.isLoggingEnabled(i2)) {
                    Log.v(FragmentManager.TAG, "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                }
                animationInfo.completeSpecialEffect();
            } else {
                final View view3 = fragment2.mView;
                Animation animation2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo.getAnimation(context2))).animation);
                if (operation3.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    view3.startAnimation(animation2);
                    animationInfo.completeSpecialEffect();
                    z3 = z4;
                    context = context2;
                    i3 = i2;
                    view = view3;
                } else {
                    container.startViewTransition(view3);
                    FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation2, container, view3);
                    z3 = z4;
                    context = context2;
                    view = view3;
                    endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation3) {
                            container.post(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                    container.endViewTransition(view3);
                                    animationInfo.completeSpecialEffect();
                                }
                            });
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Animation from operation " + operation3 + " has ended.");
                            }
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation3) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation3) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Animation from operation " + operation3 + " has reached onAnimationStart.");
                            }
                        }
                    });
                    view.startAnimation(endViewTransitionAnimation);
                    i3 = 2;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "Animation from operation " + operation3 + " has started.");
                    }
                }
                final View view4 = view;
                animationInfo.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.5
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        view4.clearAnimation();
                        container.endViewTransition(view4);
                        animationInfo.completeSpecialEffect();
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v(FragmentManager.TAG, "Animation from operation " + operation3 + " has been cancelled.");
                        }
                    }
                });
                i2 = i3;
                z4 = z3;
                context2 = context;
            }
        }
    }

    @NonNull
    private Map<SpecialEffectsController.Operation, Boolean> startTransitions(@NonNull List<TransitionInfo> list, @NonNull List<SpecialEffectsController.Operation> list2, final boolean z2, @Nullable final SpecialEffectsController.Operation operation, @Nullable final SpecialEffectsController.Operation operation2) {
        String str;
        String str2;
        String str3;
        View view;
        Object objMergeTransitionsTogether;
        ArrayList<View> arrayList;
        Object objMergeTransitionsTogether2;
        ArrayList<View> arrayList2;
        Object obj;
        SpecialEffectsController.Operation operation3;
        View view2;
        ArrayMap arrayMap;
        SpecialEffectsController.Operation operation4;
        SpecialEffectsController.Operation operation5;
        HashMap map;
        ArrayList<View> arrayList3;
        View view3;
        FragmentTransitionImpl fragmentTransitionImpl;
        ArrayList<View> arrayList4;
        final Rect rect;
        SharedElementCallback enterTransitionCallback;
        SharedElementCallback exitTransitionCallback;
        ArrayList<String> arrayList5;
        int i2;
        View view4;
        final View view5;
        String strFindKeyForValue;
        ArrayList<String> arrayList6;
        boolean z3 = z2;
        SpecialEffectsController.Operation operation6 = operation;
        SpecialEffectsController.Operation operation7 = operation2;
        HashMap map2 = new HashMap();
        final FragmentTransitionImpl fragmentTransitionImpl2 = null;
        for (TransitionInfo transitionInfo : list) {
            if (!transitionInfo.isVisibilityUnchanged()) {
                FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
                if (fragmentTransitionImpl2 == null) {
                    fragmentTransitionImpl2 = handlingImpl;
                } else if (handlingImpl != null && fragmentTransitionImpl2 != handlingImpl) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.getOperation().getFragment() + " returned Transition " + transitionInfo.getTransition() + " which uses a different Transition  type than other Fragments.");
                }
            }
        }
        if (fragmentTransitionImpl2 == null) {
            for (TransitionInfo transitionInfo2 : list) {
                map2.put(transitionInfo2.getOperation(), Boolean.FALSE);
                transitionInfo2.completeSpecialEffect();
            }
            return map2;
        }
        View view6 = new View(getContainer().getContext());
        Rect rect2 = new Rect();
        ArrayList<View> arrayList7 = new ArrayList<>();
        ArrayList<View> arrayList8 = new ArrayList<>();
        ArrayMap arrayMap2 = new ArrayMap();
        Iterator<TransitionInfo> it = list.iterator();
        Object obj2 = null;
        View view7 = null;
        boolean z4 = false;
        while (true) {
            boolean zHasNext = it.hasNext();
            str = FragmentManager.TAG;
            if (!zHasNext) {
                break;
            }
            TransitionInfo next = it.next();
            if (!next.hasSharedElementTransition() || operation6 == null || operation7 == null) {
                arrayMap = arrayMap2;
                operation4 = operation6;
                operation5 = operation7;
                map = map2;
                arrayList3 = arrayList7;
                view3 = view6;
                fragmentTransitionImpl = fragmentTransitionImpl2;
                arrayList4 = arrayList8;
                rect = rect2;
                view7 = view7;
            } else {
                Object objWrapTransitionInSet = fragmentTransitionImpl2.wrapTransitionInSet(fragmentTransitionImpl2.cloneTransition(next.getSharedElementTransition()));
                ArrayList<String> sharedElementSourceNames = operation2.getFragment().getSharedElementSourceNames();
                ArrayList<String> sharedElementSourceNames2 = operation.getFragment().getSharedElementSourceNames();
                ArrayList<String> sharedElementTargetNames = operation.getFragment().getSharedElementTargetNames();
                View view8 = view7;
                HashMap map3 = map2;
                int i3 = 0;
                while (i3 < sharedElementTargetNames.size()) {
                    int iIndexOf = sharedElementSourceNames.indexOf(sharedElementTargetNames.get(i3));
                    ArrayList<String> arrayList9 = sharedElementTargetNames;
                    if (iIndexOf != -1) {
                        sharedElementSourceNames.set(iIndexOf, sharedElementSourceNames2.get(i3));
                    }
                    i3++;
                    sharedElementTargetNames = arrayList9;
                }
                ArrayList<String> sharedElementTargetNames2 = operation2.getFragment().getSharedElementTargetNames();
                if (z3) {
                    enterTransitionCallback = operation.getFragment().getEnterTransitionCallback();
                    exitTransitionCallback = operation2.getFragment().getExitTransitionCallback();
                } else {
                    enterTransitionCallback = operation.getFragment().getExitTransitionCallback();
                    exitTransitionCallback = operation2.getFragment().getEnterTransitionCallback();
                }
                int size = sharedElementSourceNames.size();
                View view9 = view6;
                int i4 = 0;
                while (i4 < size) {
                    arrayMap2.put(sharedElementSourceNames.get(i4), sharedElementTargetNames2.get(i4));
                    i4++;
                    size = size;
                    rect2 = rect2;
                }
                Rect rect3 = rect2;
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(FragmentManager.TAG, ">>> entering view names <<<");
                    for (Iterator<String> it2 = sharedElementTargetNames2.iterator(); it2.hasNext(); it2 = it2) {
                        Log.v(FragmentManager.TAG, "Name: " + it2.next());
                    }
                    Log.v(FragmentManager.TAG, ">>> exiting view names <<<");
                    for (Iterator<String> it3 = sharedElementSourceNames.iterator(); it3.hasNext(); it3 = it3) {
                        Log.v(FragmentManager.TAG, "Name: " + it3.next());
                    }
                }
                ArrayMap<String, View> arrayMap3 = new ArrayMap<>();
                findNamedViews(arrayMap3, operation.getFragment().mView);
                arrayMap3.retainAll(sharedElementSourceNames);
                if (enterTransitionCallback != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "Executing exit callback for operation " + operation6);
                    }
                    enterTransitionCallback.onMapSharedElements(sharedElementSourceNames, arrayMap3);
                    int size2 = sharedElementSourceNames.size() - 1;
                    while (size2 >= 0) {
                        String str4 = sharedElementSourceNames.get(size2);
                        View view10 = arrayMap3.get(str4);
                        if (view10 == null) {
                            arrayMap2.remove(str4);
                            arrayList6 = sharedElementSourceNames;
                        } else {
                            arrayList6 = sharedElementSourceNames;
                            if (!str4.equals(ViewCompat.getTransitionName(view10))) {
                                arrayMap2.put(ViewCompat.getTransitionName(view10), (String) arrayMap2.remove(str4));
                            }
                        }
                        size2--;
                        sharedElementSourceNames = arrayList6;
                    }
                    arrayList5 = sharedElementSourceNames;
                } else {
                    arrayList5 = sharedElementSourceNames;
                    arrayMap2.retainAll(arrayMap3.keySet());
                }
                final ArrayMap<String, View> arrayMap4 = new ArrayMap<>();
                findNamedViews(arrayMap4, operation2.getFragment().mView);
                arrayMap4.retainAll(sharedElementTargetNames2);
                arrayMap4.retainAll(arrayMap2.values());
                if (exitTransitionCallback != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(FragmentManager.TAG, "Executing enter callback for operation " + operation7);
                    }
                    exitTransitionCallback.onMapSharedElements(sharedElementTargetNames2, arrayMap4);
                    for (int size3 = sharedElementTargetNames2.size() - 1; size3 >= 0; size3--) {
                        String str5 = sharedElementTargetNames2.get(size3);
                        View view11 = arrayMap4.get(str5);
                        if (view11 == null) {
                            String strFindKeyForValue2 = FragmentTransition.findKeyForValue(arrayMap2, str5);
                            if (strFindKeyForValue2 != null) {
                                arrayMap2.remove(strFindKeyForValue2);
                            }
                        } else if (!str5.equals(ViewCompat.getTransitionName(view11)) && (strFindKeyForValue = FragmentTransition.findKeyForValue(arrayMap2, str5)) != null) {
                            arrayMap2.put(strFindKeyForValue, ViewCompat.getTransitionName(view11));
                        }
                    }
                } else {
                    FragmentTransition.retainValues(arrayMap2, arrayMap4);
                }
                retainMatchingViews(arrayMap3, arrayMap2.keySet());
                retainMatchingViews(arrayMap4, arrayMap2.values());
                if (arrayMap2.isEmpty()) {
                    arrayList7.clear();
                    arrayList8.clear();
                    arrayMap = arrayMap2;
                    arrayList4 = arrayList8;
                    operation4 = operation6;
                    operation5 = operation7;
                    arrayList3 = arrayList7;
                    fragmentTransitionImpl = fragmentTransitionImpl2;
                    view7 = view8;
                    view3 = view9;
                    map = map3;
                    rect = rect3;
                    obj2 = null;
                } else {
                    FragmentTransition.callSharedElementStartEnd(operation2.getFragment(), operation.getFragment(), z3, arrayMap3, true);
                    arrayMap = arrayMap2;
                    ArrayList<View> arrayList10 = arrayList8;
                    OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.6
                        @Override // java.lang.Runnable
                        public void run() {
                            FragmentTransition.callSharedElementStartEnd(operation2.getFragment(), operation.getFragment(), z2, arrayMap4, false);
                        }
                    });
                    arrayList7.addAll(arrayMap3.values());
                    if (arrayList5.isEmpty()) {
                        i2 = 0;
                        view7 = view8;
                    } else {
                        i2 = 0;
                        View view12 = arrayMap3.get(arrayList5.get(0));
                        fragmentTransitionImpl2.setEpicenter(objWrapTransitionInSet, view12);
                        view7 = view12;
                    }
                    arrayList10.addAll(arrayMap4.values());
                    if (sharedElementTargetNames2.isEmpty() || (view5 = arrayMap4.get(sharedElementTargetNames2.get(i2))) == null) {
                        rect = rect3;
                        view4 = view9;
                    } else {
                        rect = rect3;
                        OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.7
                            @Override // java.lang.Runnable
                            public void run() {
                                fragmentTransitionImpl2.getBoundsOnScreen(view5, rect);
                            }
                        });
                        view4 = view9;
                        z4 = true;
                    }
                    fragmentTransitionImpl2.setSharedElementTargets(objWrapTransitionInSet, view4, arrayList7);
                    arrayList3 = arrayList7;
                    view3 = view4;
                    obj2 = objWrapTransitionInSet;
                    fragmentTransitionImpl = fragmentTransitionImpl2;
                    fragmentTransitionImpl2.scheduleRemoveTargets(objWrapTransitionInSet, null, null, null, null, obj2, arrayList10);
                    Boolean bool = Boolean.TRUE;
                    operation4 = operation;
                    arrayList4 = arrayList10;
                    map = map3;
                    map.put(operation4, bool);
                    operation5 = operation2;
                    map.put(operation5, bool);
                }
            }
            rect2 = rect;
            arrayList7 = arrayList3;
            view6 = view3;
            arrayList8 = arrayList4;
            fragmentTransitionImpl2 = fragmentTransitionImpl;
            z3 = z2;
            operation7 = operation5;
            map2 = map;
            operation6 = operation4;
            arrayMap2 = arrayMap;
        }
        View view13 = view7;
        ArrayMap arrayMap5 = arrayMap2;
        SpecialEffectsController.Operation operation8 = operation6;
        SpecialEffectsController.Operation operation9 = operation7;
        HashMap map4 = map2;
        ArrayList<View> arrayList11 = arrayList7;
        View view14 = view6;
        FragmentTransitionImpl fragmentTransitionImpl3 = fragmentTransitionImpl2;
        ArrayList<View> arrayList12 = arrayList8;
        Rect rect4 = rect2;
        ArrayList arrayList13 = new ArrayList();
        Object obj3 = null;
        Object obj4 = null;
        for (TransitionInfo transitionInfo3 : list) {
            if (transitionInfo3.isVisibilityUnchanged()) {
                map4.put(transitionInfo3.getOperation(), Boolean.FALSE);
                transitionInfo3.completeSpecialEffect();
            } else {
                Object objCloneTransition = fragmentTransitionImpl3.cloneTransition(transitionInfo3.getTransition());
                SpecialEffectsController.Operation operation10 = transitionInfo3.getOperation();
                boolean z5 = obj2 != null && (operation10 == operation8 || operation10 == operation9);
                if (objCloneTransition == null) {
                    if (!z5) {
                        map4.put(operation10, Boolean.FALSE);
                        transitionInfo3.completeSpecialEffect();
                    }
                    str3 = str;
                    arrayList = arrayList11;
                    view = view14;
                    arrayList2 = arrayList12;
                    objMergeTransitionsTogether = obj3;
                    view2 = view13;
                } else {
                    str3 = str;
                    final ArrayList<View> arrayList14 = new ArrayList<>();
                    Object obj5 = obj3;
                    captureTransitioningViews(arrayList14, operation10.getFragment().mView);
                    if (z5) {
                        if (operation10 == operation8) {
                            arrayList14.removeAll(arrayList11);
                        } else {
                            arrayList14.removeAll(arrayList12);
                        }
                    }
                    if (arrayList14.isEmpty()) {
                        fragmentTransitionImpl3.addTarget(objCloneTransition, view14);
                        arrayList = arrayList11;
                        view = view14;
                        arrayList2 = arrayList12;
                        operation3 = operation10;
                        objMergeTransitionsTogether2 = obj4;
                        obj = objCloneTransition;
                        objMergeTransitionsTogether = obj5;
                    } else {
                        fragmentTransitionImpl3.addTargets(objCloneTransition, arrayList14);
                        view = view14;
                        objMergeTransitionsTogether = obj5;
                        arrayList = arrayList11;
                        objMergeTransitionsTogether2 = obj4;
                        arrayList2 = arrayList12;
                        obj = objCloneTransition;
                        fragmentTransitionImpl3.scheduleRemoveTargets(objCloneTransition, objCloneTransition, arrayList14, null, null, null, null);
                        if (operation10.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                            operation3 = operation10;
                            list2.remove(operation3);
                            ArrayList<View> arrayList15 = new ArrayList<>(arrayList14);
                            arrayList15.remove(operation3.getFragment().mView);
                            fragmentTransitionImpl3.scheduleHideFragmentView(obj, operation3.getFragment().mView, arrayList15);
                            OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    FragmentTransition.setViewVisibility(arrayList14, 4);
                                }
                            });
                        } else {
                            operation3 = operation10;
                        }
                    }
                    if (operation3.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList13.addAll(arrayList14);
                        if (z4) {
                            fragmentTransitionImpl3.setEpicenter(obj, rect4);
                        }
                        view2 = view13;
                    } else {
                        view2 = view13;
                        fragmentTransitionImpl3.setEpicenter(obj, view2);
                    }
                    map4.put(operation3, Boolean.TRUE);
                    if (transitionInfo3.isOverlapAllowed()) {
                        objMergeTransitionsTogether2 = fragmentTransitionImpl3.mergeTransitionsTogether(objMergeTransitionsTogether2, obj, null);
                    } else {
                        objMergeTransitionsTogether = fragmentTransitionImpl3.mergeTransitionsTogether(objMergeTransitionsTogether, obj, null);
                    }
                    obj4 = objMergeTransitionsTogether2;
                }
                view13 = view2;
                obj3 = objMergeTransitionsTogether;
                str = str3;
                view14 = view;
                arrayList11 = arrayList;
                arrayList12 = arrayList2;
            }
        }
        String str6 = str;
        ArrayList<View> arrayList16 = arrayList11;
        ArrayList<View> arrayList17 = arrayList12;
        Object objMergeTransitionsInSequence = fragmentTransitionImpl3.mergeTransitionsInSequence(obj4, obj3, obj2);
        if (objMergeTransitionsInSequence == null) {
            return map4;
        }
        for (final TransitionInfo transitionInfo4 : list) {
            if (!transitionInfo4.isVisibilityUnchanged()) {
                Object transition = transitionInfo4.getTransition();
                final SpecialEffectsController.Operation operation11 = transitionInfo4.getOperation();
                boolean z6 = obj2 != null && (operation11 == operation8 || operation11 == operation9);
                if (transition == null && !z6) {
                    str2 = str6;
                } else if (ViewCompat.isLaidOut(getContainer())) {
                    str2 = str6;
                    fragmentTransitionImpl3.setListenerForTransitionEnd(transitionInfo4.getOperation().getFragment(), objMergeTransitionsInSequence, transitionInfo4.getSignal(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.9
                        @Override // java.lang.Runnable
                        public void run() {
                            transitionInfo4.completeSpecialEffect();
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Transition for operation " + operation11 + "has completed");
                            }
                        }
                    });
                } else {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        str2 = str6;
                        Log.v(str2, "SpecialEffectsController: Container " + getContainer() + " has not been laid out. Completing operation " + operation11);
                    } else {
                        str2 = str6;
                    }
                    transitionInfo4.completeSpecialEffect();
                }
                str6 = str2;
            }
        }
        String str7 = str6;
        if (!ViewCompat.isLaidOut(getContainer())) {
            return map4;
        }
        FragmentTransition.setViewVisibility(arrayList13, 4);
        ArrayList<String> arrayListPrepareSetNameOverridesReordered = fragmentTransitionImpl3.prepareSetNameOverridesReordered(arrayList17);
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(str7, ">>>>> Beginning transition <<<<<");
            Log.v(str7, ">>>>> SharedElementFirstOutViews <<<<<");
            Iterator<View> it4 = arrayList16.iterator();
            while (it4.hasNext()) {
                View next2 = it4.next();
                Log.v(str7, "View: " + next2 + " Name: " + ViewCompat.getTransitionName(next2));
            }
            Log.v(str7, ">>>>> SharedElementLastInViews <<<<<");
            Iterator<View> it5 = arrayList17.iterator();
            while (it5.hasNext()) {
                View next3 = it5.next();
                Log.v(str7, "View: " + next3 + " Name: " + ViewCompat.getTransitionName(next3));
            }
        }
        fragmentTransitionImpl3.beginDelayedTransition(getContainer(), objMergeTransitionsInSequence);
        fragmentTransitionImpl3.setNameOverridesReordered(getContainer(), arrayList16, arrayList17, arrayListPrepareSetNameOverridesReordered, arrayMap5);
        FragmentTransition.setViewVisibility(arrayList13, 0);
        fragmentTransitionImpl3.swapSharedElementTargets(obj2, arrayList16, arrayList17);
        return map4;
    }

    public void applyContainerChanges(@NonNull SpecialEffectsController.Operation operation) {
        operation.getFinalState().applyState(operation.getFragment().mView);
    }

    public void captureTransitioningViews(ArrayList<View> arrayList, View view) {
        if (!(view instanceof ViewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(view);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(viewGroup);
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                captureTransitioningViews(arrayList, childAt);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00a8  */
    @Override // androidx.fragment.app.SpecialEffectsController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void executeOperations(@androidx.annotation.NonNull java.util.List<androidx.fragment.app.SpecialEffectsController.Operation> r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.executeOperations(java.util.List, boolean):void");
    }

    public void findNamedViews(Map<String, View> map, @NonNull View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            map.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt.getVisibility() == 0) {
                    findNamedViews(map, childAt);
                }
            }
        }
    }

    public void retainMatchingViews(@NonNull ArrayMap<String, View> arrayMap, @NonNull Collection<String> collection) {
        Iterator<Map.Entry<String, View>> it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(ViewCompat.getTransitionName(it.next().getValue()))) {
                it.remove();
            }
        }
    }

    public static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;

        @Nullable
        private final Object mSharedElementTransition;

        @Nullable
        private final Object mTransition;

        public TransitionInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal, boolean z2, boolean z3) {
            super(operation, cancellationSignal);
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                this.mTransition = z2 ? operation.getFragment().getReenterTransition() : operation.getFragment().getEnterTransition();
                this.mOverlapAllowed = z2 ? operation.getFragment().getAllowReturnTransitionOverlap() : operation.getFragment().getAllowEnterTransitionOverlap();
            } else {
                this.mTransition = z2 ? operation.getFragment().getReturnTransition() : operation.getFragment().getExitTransition();
                this.mOverlapAllowed = true;
            }
            if (!z3) {
                this.mSharedElementTransition = null;
            } else if (z2) {
                this.mSharedElementTransition = operation.getFragment().getSharedElementReturnTransition();
            } else {
                this.mSharedElementTransition = operation.getFragment().getSharedElementEnterTransition();
            }
        }

        @Nullable
        public FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl handlingImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl handlingImpl2 = getHandlingImpl(this.mSharedElementTransition);
            if (handlingImpl == null || handlingImpl2 == null || handlingImpl == handlingImpl2) {
                return handlingImpl != null ? handlingImpl : handlingImpl2;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        @Nullable
        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        @Nullable
        public Object getTransition() {
            return this.mTransition;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }

        @Nullable
        private FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.PLATFORM_IMPL;
            if (fragmentTransitionImpl != null && fragmentTransitionImpl.canHandle(obj)) {
                return fragmentTransitionImpl;
            }
            FragmentTransitionImpl fragmentTransitionImpl2 = FragmentTransition.SUPPORT_IMPL;
            if (fragmentTransitionImpl2 != null && fragmentTransitionImpl2.canHandle(obj)) {
                return fragmentTransitionImpl2;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
