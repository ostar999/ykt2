package org.apache.commons.codec.language.bm;

import cn.hutool.core.util.URLUtil;
import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/* loaded from: classes9.dex */
public class Languages {
    public static final String ANY = "any";
    public static final LanguageSet ANY_LANGUAGE;
    private static final Map<NameType, Languages> LANGUAGES = new EnumMap(NameType.class);
    public static final LanguageSet NO_LANGUAGES;
    private final Set<String> languages;

    public static abstract class LanguageSet {
        public static LanguageSet from(Set<String> set) {
            return set.isEmpty() ? Languages.NO_LANGUAGES : new SomeLanguages(set);
        }

        public abstract boolean contains(String str);

        public abstract String getAny();

        public abstract boolean isEmpty();

        public abstract boolean isSingleton();

        public abstract LanguageSet restrictTo(LanguageSet languageSet);
    }

    public static final class SomeLanguages extends LanguageSet {
        private final Set<String> languages;

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public boolean contains(String str) {
            return this.languages.contains(str);
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public String getAny() {
            return this.languages.iterator().next();
        }

        public Set<String> getLanguages() {
            return this.languages;
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public boolean isEmpty() {
            return this.languages.isEmpty();
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public boolean isSingleton() {
            return this.languages.size() == 1;
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public LanguageSet restrictTo(LanguageSet languageSet) {
            if (languageSet == Languages.NO_LANGUAGES) {
                return languageSet;
            }
            if (languageSet == Languages.ANY_LANGUAGE) {
                return this;
            }
            SomeLanguages someLanguages = (SomeLanguages) languageSet;
            if (someLanguages.languages.containsAll(this.languages)) {
                return this;
            }
            HashSet hashSet = new HashSet(this.languages);
            hashSet.retainAll(someLanguages.languages);
            return LanguageSet.from(hashSet);
        }

        public String toString() {
            return "Languages(" + this.languages.toString() + ")";
        }

        private SomeLanguages(Set<String> set) {
            this.languages = Collections.unmodifiableSet(set);
        }
    }

    static {
        for (NameType nameType : NameType.values()) {
            LANGUAGES.put(nameType, getInstance(langResourceName(nameType)));
        }
        NO_LANGUAGES = new LanguageSet() { // from class: org.apache.commons.codec.language.bm.Languages.1
            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean contains(String str) {
                return false;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the empty language set.");
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean isEmpty() {
                return true;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean isSingleton() {
                return false;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public LanguageSet restrictTo(LanguageSet languageSet) {
                return this;
            }

            public String toString() {
                return "NO_LANGUAGES";
            }
        };
        ANY_LANGUAGE = new LanguageSet() { // from class: org.apache.commons.codec.language.bm.Languages.2
            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean contains(String str) {
                return true;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the any language set.");
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean isEmpty() {
                return false;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean isSingleton() {
                return false;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public LanguageSet restrictTo(LanguageSet languageSet) {
                return languageSet;
            }

            public String toString() {
                return "ANY_LANGUAGE";
            }
        };
    }

    private Languages(Set<String> set) {
        this.languages = set;
    }

    public static Languages getInstance(NameType nameType) {
        return LANGUAGES.get(nameType);
    }

    private static String langResourceName(NameType nameType) {
        return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", nameType.getName());
    }

    public Set<String> getLanguages() {
        return this.languages;
    }

    public static Languages getInstance(String str) {
        HashSet hashSet = new HashSet();
        InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(str);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Unable to resolve required resource: " + str);
        }
        Scanner scanner = new Scanner(resourceAsStream, "UTF-8");
        while (true) {
            boolean z2 = false;
            while (scanner.hasNextLine()) {
                String strTrim = scanner.nextLine().trim();
                if (z2) {
                    if (strTrim.endsWith(URLUtil.WAR_URL_SEPARATOR)) {
                        break;
                    }
                } else if (strTrim.startsWith("/*")) {
                    z2 = true;
                } else if (strTrim.length() > 0) {
                    hashSet.add(strTrim);
                }
            }
            return new Languages(Collections.unmodifiableSet(hashSet));
        }
    }
}
