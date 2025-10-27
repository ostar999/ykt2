package com.bumptech.glide.repackaged.com.squareup.javapoet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Modifier;

/* loaded from: classes2.dex */
final class Util {
    static final Modifier DEFAULT;

    static {
        Modifier modifierValueOf;
        try {
            modifierValueOf = Modifier.valueOf("DEFAULT");
        } catch (IllegalArgumentException unused) {
            modifierValueOf = null;
        }
        DEFAULT = modifierValueOf;
    }

    public static String characterLiteralWithoutSingleQuotes(char c3) {
        if (c3 == '\f') {
            return "\\f";
        }
        if (c3 == '\r') {
            return "\\r";
        }
        if (c3 == '\"') {
            return "\"";
        }
        if (c3 == '\'') {
            return "\\'";
        }
        if (c3 == '\\') {
            return "\\\\";
        }
        switch (c3) {
            case '\b':
                return "\\b";
            case '\t':
                return "\\t";
            case '\n':
                return "\\n";
            default:
                return Character.isISOControl(c3) ? String.format("\\u%04x", Integer.valueOf(c3)) : Character.toString(c3);
        }
    }

    public static void checkArgument(boolean z2, String str, Object... objArr) {
        if (!z2) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    public static <T> T checkNotNull(T t2, String str, Object... objArr) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(String.format(str, objArr));
    }

    public static void checkState(boolean z2, String str, Object... objArr) {
        if (!z2) {
            throw new IllegalStateException(String.format(str, objArr));
        }
    }

    public static boolean hasDefaultModifier(Collection<Modifier> collection) {
        Modifier modifier = DEFAULT;
        return modifier != null && collection.contains(modifier);
    }

    public static <T> List<T> immutableList(Collection<T> collection) {
        return Collections.unmodifiableList(new ArrayList(collection));
    }

    public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
        return Collections.unmodifiableMap(new LinkedHashMap(map));
    }

    public static <K, V> Map<K, List<V>> immutableMultimap(Map<K, List<V>> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                linkedHashMap.put(entry.getKey(), immutableList(entry.getValue()));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    public static <T> Set<T> immutableSet(Collection<T> collection) {
        return Collections.unmodifiableSet(new LinkedHashSet(collection));
    }

    public static String join(String str, List<String> list) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0));
        for (int i2 = 1; i2 < list.size(); i2++) {
            sb.append(str);
            sb.append(list.get(i2));
        }
        return sb.toString();
    }

    public static void requireExactlyOneOf(Set<Modifier> set, Modifier... modifierArr) {
        int i2 = 0;
        for (Modifier modifier : modifierArr) {
            if ((modifier != null || DEFAULT != null) && set.contains(modifier)) {
                i2++;
            }
        }
        checkArgument(i2 == 1, "modifiers %s must contain one of %s", set, Arrays.toString(modifierArr));
    }

    public static String stringLiteralWithDoubleQuotes(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append('\"');
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\'') {
                sb.append("'");
            } else if (cCharAt == '\"') {
                sb.append("\\\"");
            } else {
                sb.append(characterLiteralWithoutSingleQuotes(cCharAt));
                if (cCharAt == '\n' && i2 + 1 < str.length()) {
                    sb.append("\"\n");
                    sb.append(str2);
                    sb.append(str2);
                    sb.append("+ \"");
                }
            }
        }
        sb.append('\"');
        return sb.toString();
    }

    public static <T> Set<T> union(Set<T> set, Set<T> set2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(set);
        linkedHashSet.addAll(set2);
        return linkedHashSet;
    }
}
