package com.beizi.ad.internal.animation;

import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes2.dex */
public class AnimationFactory {

    /* renamed from: com.beizi.ad.internal.animation.AnimationFactory$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$beizi$ad$internal$animation$TransitionType;

        static {
            int[] iArr = new int[TransitionType.values().length];
            $SwitchMap$com$beizi$ad$internal$animation$TransitionType = iArr;
            try {
                iArr[TransitionType.FADE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$beizi$ad$internal$animation$TransitionType[TransitionType.PUSH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$beizi$ad$internal$animation$TransitionType[TransitionType.MOVEIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$beizi$ad$internal$animation$TransitionType[TransitionType.REVEAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static Transition create(TransitionType transitionType, long j2, TransitionDirection transitionDirection) {
        TransitionType transitionType2 = TransitionType.RANDOM;
        if (transitionType == transitionType2) {
            ArrayList arrayList = new ArrayList();
            Collections.addAll(arrayList, TransitionType.values());
            arrayList.remove(TransitionType.NONE);
            arrayList.remove(transitionType2);
            Collections.shuffle(arrayList);
            transitionType = (TransitionType) arrayList.get(0);
        }
        int i2 = AnonymousClass1.$SwitchMap$com$beizi$ad$internal$animation$TransitionType[transitionType.ordinal()];
        if (i2 == 1) {
            return new Fade(j2);
        }
        if (i2 == 2) {
            return new Push(j2, transitionDirection);
        }
        if (i2 == 3) {
            return new MoveIn(j2, transitionDirection);
        }
        if (i2 != 4) {
            return null;
        }
        return new Reveal(j2, transitionDirection);
    }
}
