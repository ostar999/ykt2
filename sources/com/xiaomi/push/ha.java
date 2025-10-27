package com.xiaomi.push;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes6.dex */
public class ha {

    /* renamed from: a, reason: collision with root package name */
    private static ha f25046a;

    /* renamed from: a, reason: collision with other field name */
    private Map<String, Object> f517a = new ConcurrentHashMap();

    /* renamed from: b, reason: collision with root package name */
    private Map<String, Object> f25047b = new ConcurrentHashMap();

    private ha() throws Throwable {
        m476a();
    }

    public static synchronized ha a() {
        if (f25046a == null) {
            f25046a = new ha();
        }
        return f25046a;
    }

    private String a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(str);
        sb.append("/>");
        if (str != null) {
            sb.append("<");
            sb.append(str2);
            sb.append("/>");
        }
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    private ClassLoader[] m474a() {
        ClassLoader[] classLoaderArr = {ha.class.getClassLoader(), Thread.currentThread().getContextClassLoader()};
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 2; i2++) {
            ClassLoader classLoader = classLoaderArr[i2];
            if (classLoader != null) {
                arrayList.add(classLoader);
            }
        }
        return (ClassLoader[]) arrayList.toArray(new ClassLoader[arrayList.size()]);
    }

    /* renamed from: a, reason: collision with other method in class */
    public Object m475a(String str, String str2) {
        return this.f517a.get(a(str, str2));
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m476a() throws Throwable {
        InputStream inputStreamOpenStream;
        Map<String, Object> map;
        Object objNewInstance;
        Map<String, Object> map2;
        Object objNewInstance2;
        try {
            for (ClassLoader classLoader : m474a()) {
                Enumeration<URL> resources = classLoader.getResources("META-INF/smack.providers");
                while (resources.hasMoreElements()) {
                    try {
                        inputStreamOpenStream = resources.nextElement().openStream();
                    } catch (Throwable th) {
                        th = th;
                        inputStreamOpenStream = null;
                    }
                    try {
                        XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                        xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
                        xmlPullParserNewPullParser.setInput(inputStreamOpenStream, "UTF-8");
                        int eventType = xmlPullParserNewPullParser.getEventType();
                        do {
                            if (eventType == 2) {
                                if (xmlPullParserNewPullParser.getName().equals("iqProvider")) {
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText = xmlPullParserNewPullParser.nextText();
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText2 = xmlPullParserNewPullParser.nextText();
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText3 = xmlPullParserNewPullParser.nextText();
                                    String strA = a(strNextText, strNextText2);
                                    if (!this.f25047b.containsKey(strA)) {
                                        try {
                                            Class<?> cls = Class.forName(strNextText3);
                                            if (gy.class.isAssignableFrom(cls)) {
                                                map2 = this.f25047b;
                                                objNewInstance2 = cls.newInstance();
                                            } else if (gr.class.isAssignableFrom(cls)) {
                                                map2 = this.f25047b;
                                                objNewInstance2 = cls;
                                            }
                                            map2.put(strA, objNewInstance2);
                                        } catch (ClassNotFoundException e2) {
                                            e = e2;
                                            e.printStackTrace();
                                            eventType = xmlPullParserNewPullParser.next();
                                        }
                                    }
                                } else if (xmlPullParserNewPullParser.getName().equals("extensionProvider")) {
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText4 = xmlPullParserNewPullParser.nextText();
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText5 = xmlPullParserNewPullParser.nextText();
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText6 = xmlPullParserNewPullParser.nextText();
                                    String strA2 = a(strNextText4, strNextText5);
                                    if (!this.f517a.containsKey(strA2)) {
                                        try {
                                            Class<?> cls2 = Class.forName(strNextText6);
                                            if (gz.class.isAssignableFrom(cls2)) {
                                                map = this.f517a;
                                                objNewInstance = cls2.newInstance();
                                            } else if (gu.class.isAssignableFrom(cls2)) {
                                                map = this.f517a;
                                                objNewInstance = cls2;
                                            }
                                            map.put(strA2, objNewInstance);
                                        } catch (ClassNotFoundException e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            eventType = xmlPullParserNewPullParser.next();
                                        }
                                    }
                                }
                            }
                            eventType = xmlPullParserNewPullParser.next();
                        } while (eventType != 1);
                        inputStreamOpenStream.close();
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            inputStreamOpenStream.close();
                        } catch (Exception unused) {
                        }
                        throw th;
                    }
                }
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    public void a(String str, String str2, Object obj) {
        if (!(obj instanceof gz) && !(obj instanceof Class)) {
            throw new IllegalArgumentException("Provider must be a PacketExtensionProvider or a Class instance.");
        }
        this.f517a.put(a(str, str2), obj);
    }
}
