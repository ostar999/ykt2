package com.google.common.graph;

import com.google.common.annotations.Beta;

@Beta
/* loaded from: classes4.dex */
public interface SuccessorsFunction<N> {
    Iterable<? extends N> successors(N n2);
}
