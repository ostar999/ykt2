package com.arialyy.aria.orm;

/* loaded from: classes2.dex */
public enum ActionPolicy {
    NO_ACTION("NO ACTION"),
    RESTRICT("RESTRICT"),
    SET_NULL("SET NULL"),
    SET_DEFAULT("SET ERROR"),
    CASCADE("CASCADE");

    String function;

    ActionPolicy(String str) {
        this.function = str;
    }
}
