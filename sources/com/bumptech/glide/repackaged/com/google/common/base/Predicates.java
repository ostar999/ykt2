package com.bumptech.glide.repackaged.com.google.common.base;

/* loaded from: classes2.dex */
public final class Predicates {
    private static final Joiner COMMA_JOINER = Joiner.on(',');

    public enum ObjectPredicate implements Predicate<Object> {
        ALWAYS_TRUE { // from class: com.bumptech.glide.repackaged.com.google.common.base.Predicates.ObjectPredicate.1
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Predicate
            public boolean apply(Object obj) {
                return true;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Predicates.alwaysTrue()";
            }
        },
        ALWAYS_FALSE { // from class: com.bumptech.glide.repackaged.com.google.common.base.Predicates.ObjectPredicate.2
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Predicate
            public boolean apply(Object obj) {
                return false;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Predicates.alwaysFalse()";
            }
        },
        IS_NULL { // from class: com.bumptech.glide.repackaged.com.google.common.base.Predicates.ObjectPredicate.3
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Predicate
            public boolean apply(Object obj) {
                return obj == null;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Predicates.isNull()";
            }
        },
        NOT_NULL { // from class: com.bumptech.glide.repackaged.com.google.common.base.Predicates.ObjectPredicate.4
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Predicate
            public boolean apply(Object obj) {
                return obj != null;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Predicates.notNull()";
            }
        };

        public <T> Predicate<T> withNarrowedType() {
            return this;
        }
    }

    public static <T> Predicate<T> notNull() {
        return ObjectPredicate.NOT_NULL.withNarrowedType();
    }
}
