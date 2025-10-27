package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;

@GwtIncompatible
/* loaded from: classes3.dex */
interface PatternCompiler {
    CommonPattern compile(String str);

    boolean isPcreLike();
}
