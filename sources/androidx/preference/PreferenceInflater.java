package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
class PreferenceInflater {
    private static final String EXTRA_TAG_NAME = "extra";
    private static final String INTENT_TAG_NAME = "intent";
    private static final String TAG = "PreferenceInflater";
    private final Object[] mConstructorArgs = new Object[2];
    private final Context mContext;
    private String[] mDefaultPackages;
    private PreferenceManager mPreferenceManager;
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    private static final HashMap<String, Constructor> CONSTRUCTOR_MAP = new HashMap<>();

    public PreferenceInflater(Context context, PreferenceManager preferenceManager) {
        this.mContext = context;
        init(preferenceManager);
    }

    private Preference createItem(@NonNull String str, @Nullable String[] strArr, AttributeSet attributeSet) throws InflateException, NoSuchMethodException, ClassNotFoundException, SecurityException {
        Class<?> clsLoadClass;
        Constructor<?> constructor = CONSTRUCTOR_MAP.get(str);
        if (constructor == null) {
            try {
                try {
                    ClassLoader classLoader = this.mContext.getClassLoader();
                    if (strArr == null || strArr.length == 0) {
                        clsLoadClass = classLoader.loadClass(str);
                    } else {
                        clsLoadClass = null;
                        ClassNotFoundException e2 = null;
                        for (String str2 : strArr) {
                            try {
                                clsLoadClass = classLoader.loadClass(str2 + str);
                                break;
                            } catch (ClassNotFoundException e3) {
                                e2 = e3;
                            }
                        }
                        if (clsLoadClass == null) {
                            if (e2 != null) {
                                throw e2;
                            }
                            throw new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
                        }
                    }
                    constructor = clsLoadClass.getConstructor(CONSTRUCTOR_SIGNATURE);
                    constructor.setAccessible(true);
                    CONSTRUCTOR_MAP.put(str, constructor);
                } catch (Exception e4) {
                    InflateException inflateException = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
                    inflateException.initCause(e4);
                    throw inflateException;
                }
            } catch (ClassNotFoundException e5) {
                throw e5;
            }
        }
        Object[] objArr = this.mConstructorArgs;
        objArr[1] = attributeSet;
        return (Preference) constructor.newInstance(objArr);
    }

    private Preference createItemFromTag(String str, AttributeSet attributeSet) {
        try {
            return -1 == str.indexOf(46) ? onCreateItem(str, attributeSet) : createItem(str, null, attributeSet);
        } catch (InflateException e2) {
            throw e2;
        } catch (ClassNotFoundException e3) {
            InflateException inflateException = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class (not found)" + str);
            inflateException.initCause(e3);
            throw inflateException;
        } catch (Exception e4) {
            InflateException inflateException2 = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
            inflateException2.initCause(e4);
            throw inflateException2;
        }
    }

    private void init(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        setDefaultPackages(new String[]{Preference.class.getPackage().getName() + StrPool.DOT, SwitchPreference.class.getPackage().getName() + StrPool.DOT});
    }

    @NonNull
    private PreferenceGroup onMergeRoots(PreferenceGroup preferenceGroup, @NonNull PreferenceGroup preferenceGroup2) {
        if (preferenceGroup != null) {
            return preferenceGroup;
        }
        preferenceGroup2.onAttachedToHierarchy(this.mPreferenceManager);
        return preferenceGroup2;
    }

    private void rInflate(XmlPullParser xmlPullParser, Preference preference, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if ("intent".equals(name)) {
                    try {
                        preference.setIntent(Intent.parseIntent(getContext().getResources(), xmlPullParser, attributeSet));
                    } catch (IOException e2) {
                        XmlPullParserException xmlPullParserException = new XmlPullParserException("Error parsing preference");
                        xmlPullParserException.initCause(e2);
                        throw xmlPullParserException;
                    }
                } else if ("extra".equals(name)) {
                    getContext().getResources().parseBundleExtra("extra", attributeSet, preference.getExtras());
                    try {
                        skipCurrentTag(xmlPullParser);
                    } catch (IOException e3) {
                        XmlPullParserException xmlPullParserException2 = new XmlPullParserException("Error parsing preference");
                        xmlPullParserException2.initCause(e3);
                        throw xmlPullParserException2;
                    }
                } else {
                    Preference preferenceCreateItemFromTag = createItemFromTag(name, attributeSet);
                    ((PreferenceGroup) preference).addItemFromInflater(preferenceCreateItemFromTag);
                    rInflate(xmlPullParser, preferenceCreateItemFromTag, attributeSet);
                }
            }
        }
    }

    private static void skipCurrentTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public String[] getDefaultPackages() {
        return this.mDefaultPackages;
    }

    public Preference inflate(int i2, @Nullable PreferenceGroup preferenceGroup) throws Resources.NotFoundException {
        XmlResourceParser xml = getContext().getResources().getXml(i2);
        try {
            return inflate(xml, preferenceGroup);
        } finally {
            xml.close();
        }
    }

    public Preference onCreateItem(String str, AttributeSet attributeSet) throws ClassNotFoundException {
        return createItem(str, this.mDefaultPackages, attributeSet);
    }

    public void setDefaultPackages(String[] strArr) {
        this.mDefaultPackages = strArr;
    }

    public Preference inflate(XmlPullParser xmlPullParser, @Nullable PreferenceGroup preferenceGroup) {
        int next;
        PreferenceGroup preferenceGroupOnMergeRoots;
        synchronized (this.mConstructorArgs) {
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
            this.mConstructorArgs[0] = this.mContext;
            do {
                try {
                    next = xmlPullParser.next();
                    if (next == 2) {
                        break;
                    }
                } catch (InflateException e2) {
                    throw e2;
                } catch (IOException e3) {
                    InflateException inflateException = new InflateException(xmlPullParser.getPositionDescription() + ": " + e3.getMessage());
                    inflateException.initCause(e3);
                    throw inflateException;
                } catch (XmlPullParserException e4) {
                    InflateException inflateException2 = new InflateException(e4.getMessage());
                    inflateException2.initCause(e4);
                    throw inflateException2;
                }
            } while (next != 1);
            if (next == 2) {
                preferenceGroupOnMergeRoots = onMergeRoots(preferenceGroup, (PreferenceGroup) createItemFromTag(xmlPullParser.getName(), attributeSetAsAttributeSet));
                rInflate(xmlPullParser, preferenceGroupOnMergeRoots, attributeSetAsAttributeSet);
            } else {
                throw new InflateException(xmlPullParser.getPositionDescription() + ": No start tag found!");
            }
        }
        return preferenceGroupOnMergeRoots;
    }
}
